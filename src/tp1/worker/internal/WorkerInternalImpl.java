package tp1.worker.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import tp1.datacontract.Task;
import tp1.datacontract.Task.STATUS_TASK;


public class WorkerInternalImpl implements IWorkerInternal {

	private Thread workerInternalThread;
	private static WorkerInternalImpl instance = null;
	private BlockingQueue<Task> todoTask = new ArrayBlockingQueue<Task>(10);

	private WorkerInternalImpl(){
		instance = this;
		workerInternalThread =  new Thread(new WorkerThread());
		workerInternalThread.start();
	}

	@Override
	public boolean sumbitNewTask(Task task) {
		System.out.println("Worker offer " + task);
		return todoTask.offer(task);
	}

	public static IWorkerInternal getWorkerInternal() {
		if(instance == null){
			instance = new WorkerInternalImpl();
		}
		return instance;
	}


	private class WorkerThread implements Runnable {


		public void run() {
			while(true){
				if( !todoTask.isEmpty()){
					try {
						Task task = todoTask.poll(1, TimeUnit.MINUTES);
						if(task != null){
							executeTask(task);
						}
					} catch (InterruptedException e) {
						System.out.println("Something bad is happend !");
						e.printStackTrace();
					}
				}
				
			}
		}

		private void executeTask(Task task) {
			int result = compute(task.getN());
			task.setResult(result);
			task.setStatus(STATUS_TASK.TREAT);
			System.out.println("sleep 7 sec");
			try {
				Thread.sleep(7000);
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Worker compute finish !");
			WorkerServices.getServiceInstance().sendTaskResponse(task);
		}

		//TODO add thread ??
		private int compute(int n) {
			int cpt = 0;
			System.out.println("Worker compute begging ...");
			if(n<=0){
				cpt =0;
			}
			else{
				for(int num=1 ; num <Math.pow(2, n)-1 ; num++){
					if(num%n==0){
						cpt++;
					}
				}
			}
			return cpt;
		}

	}

}



