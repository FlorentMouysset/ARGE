package tp1.balancer.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;
import tp1.datacontract.Task.STATUS_TASK;
import tp1.utils.ARGELogger;
import tp1.utils.ARGELogger.LOG_LEVEL;


public class BalancerDispatcherInternalImpl implements IBalancerDispatcherInternal {

	private Thread balancerInternalThread;
	private static BalancerDispatcherInternalImpl instance = null;
	private BlockingQueue<Task> todoTask = new ArrayBlockingQueue<Task>(10);
	private BlockingQueue<CoupleIpPort> addWorker = new ArrayBlockingQueue<CoupleIpPort>(10);
	private BlockingQueue<CoupleIpPort> removeWorker = new ArrayBlockingQueue<CoupleIpPort>(10);
	private BlockingQueue<CoupleIpPort> finishJobWorker = new ArrayBlockingQueue<CoupleIpPort>(10);
	private boolean stopDispatcherThread = false;
	
	private BalancerDispatcherInternalImpl(){
		instance = this;
		balancerInternalThread =  new Thread(new BalancerWorkerThread());
		balancerInternalThread.start();	
	}


	@Override
	public boolean addTask(Task task) {
		System.out.println("Balancer task offer.");
		return todoTask.offer(task);
	}


	@Override
	public boolean addWorker(CoupleIpPort worker) {
		return addWorker.offer(worker);
	}
	


	@Override
	public boolean finishTask(CoupleIpPort worker) {
		return finishJobWorker.offer(worker);
	}


	@Override
	public boolean removeWorker(CoupleIpPort worker) {
		return removeWorker.offer(worker);
	}

	public static IBalancerDispatcherInternal getBalancerWorkerInternal() {
		if(instance == null){
			instance = new BalancerDispatcherInternalImpl();
		}
		return instance;
	}


	@Override
	public void stopAndJoinDispatcherThread(){
		if(!balancerInternalThread.isAlive()) return;
		stopDispatcherThread = true;
		try {
			balancerInternalThread.join();
		} catch (InterruptedException e) {
			ARGELogger.log("Balancer", " join dispatcher " + e.getMessage(), LOG_LEVEL.CRIT);
			e.printStackTrace();
		}
	}

	private class BalancerWorkerThread implements Runnable {
		private IWorkerManager workerManager = new WorkerManagerImpl();

		public void run() {
			while(!stopDispatcherThread){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateWorkersList();
				if(!finishJobWorker.isEmpty()) updateWorkerLoad();
				
				if( !todoTask.isEmpty()){
					dispatchTask(todoTask.poll());
				}
			}
		}

		private void updateWorkerLoad() {
			int nbFinishTask=finishJobWorker.size();
			//ARGELogger.log("balancer","nb finish task " + nbFinishTask, ARGELogger.LOG_LEVEL.DEBG);
			for(int index=0 ; index<nbFinishTask ; index++){
				CoupleIpPort worker = finishJobWorker.poll();
				workerManager.finishJob(worker);
			}
		}

		private void dispatchTask(Task task) {
			CoupleIpPort worker = workerManager.getMinLoadWorker();
			System.out.println("Balancer dispatch to " + worker);
			if(worker == null){
				task.setStatus(STATUS_TASK.ERROR_NO_WORKER);
				BalancerServicesImpl.getServiceInstance().sendTaskResponseToClient(task);
			}
			workerManager.addJob(worker);
			BalancerServicesImpl.getServiceInstance().sendTaskToWorker(task, worker);
		}

		private void updateWorkersList() {
			if( !removeWorker.isEmpty()){
				removeWorker(removeWorker.poll());
			}
			if( !addWorker.isEmpty()){
				addWorker(addWorker.poll());
			}
		}

		private void addWorker(CoupleIpPort coupleIpPort) {
			workerManager.addWorker(coupleIpPort);
		}

		private void removeWorker(CoupleIpPort coupleIpPort) {
			workerManager.removeWorker(coupleIpPort);
		}
	}



}
