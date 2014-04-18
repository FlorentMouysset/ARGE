package tp1.balancer.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import tp1.datacontract.Task;
import tp1.utils.ARGELogger;
import tp1.utils.ARGELogger.LOG_LEVEL;


public class BalancerTaskReplyInternalImpl implements IBalancerTaskReceivedInternal {

	private Thread senderInternalThread;
	private static IBalancerTaskReceivedInternal instance = null;
	private BlockingQueue<Task> finishedTaskToSend = new ArrayBlockingQueue<Task>(10);
	private volatile boolean stopReplyThread = false;
	
	private BalancerTaskReplyInternalImpl(){
		instance = this;
		senderInternalThread =  new Thread(new SenderThread());
		senderInternalThread.start();	
	}

	public static IBalancerTaskReceivedInternal getTaskReplyInternal() {
		if(instance == null){
			instance = new BalancerTaskReplyInternalImpl();
		}
		return instance;
	}

	@Override
	public boolean sendResponse(Task task) {
		return finishedTaskToSend.offer(task);
	}
	
	@Override
	public void stopAndJoinReplyThread(){
		if(!senderInternalThread.isAlive()) return;
		stopReplyThread = true;
		sendFakeTaskToStopThread(3);
		try {
			senderInternalThread.join();
		} catch (InterruptedException e) {
			ARGELogger.log("Balancer", " join reply thread " + e.getMessage(), LOG_LEVEL.CRIT);
			e.printStackTrace();
		}
	}
	
	private void sendFakeTaskToStopThread(int secondesToWaitBeforeInterrupt){
		boolean result = false;
		try {
			//offer a fake task to stop the thread
			result = finishedTaskToSend.offer(new Task(-1, "", null), secondesToWaitBeforeInterrupt, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			ARGELogger.log("Balancer", " sendFakeTaskToStopThread " + e.getMessage(), LOG_LEVEL.CRIT);
			e.printStackTrace();
		}
		if(!result){
			senderInternalThread.interrupt();
		}
	}

	private class SenderThread implements Runnable {

		public void run() {
			while(!stopReplyThread ){				
				if( !finishedTaskToSend.isEmpty()){
					try {
						Task task = finishedTaskToSend.poll(1, TimeUnit.MINUTES);
						if(task != null){
							if(task.getTaskID().equals("-1") || stopReplyThread){
								break;
							}
							System.out.println("[Balancer] send task response.");
							sendTask(task);
						}
					} catch (InterruptedException e) {
						ARGELogger.log("Balancer", "SenderThread - " + e.getMessage(), LOG_LEVEL.CRIT);
						e.printStackTrace();
					}
				}
			}
		}
		private void sendTask(Task task) {
			BalancerServicesImpl.getServiceInstance().sendTaskResponseToClient(task);
		}

	}



}
