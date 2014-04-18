package tp1.balancer;

import tp1.balancer.internal.BalancerDispatcherInternalImpl;
import tp1.balancer.internal.BalancerTaskReplyInternalImpl;
import tp1.balancer.internal.IBalancerDispatcherInternal;
import tp1.balancer.internal.IBalancerTaskReceivedInternal;
import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;

public class BalancerFrontImpl implements IBalancerFrontClient, IBalancerFrontWorker {
	
	private static IBalancerDispatcherInternal dispatcherInstance = null;
	private static IBalancerTaskReceivedInternal finishedTaskReceivedInstance = null;
	
	private static IBalancerDispatcherInternal getDispatcherInstance(){
		if(dispatcherInstance == null){
			dispatcherInstance = BalancerDispatcherInternalImpl.getBalancerWorkerInternal();
		}
		return dispatcherInstance;
	}
	
	private static IBalancerTaskReceivedInternal getTaskReceivedInstance(){
		if(finishedTaskReceivedInstance == null){
			finishedTaskReceivedInstance = BalancerTaskReplyInternalImpl.getTaskReplyInternal();
		}
		return finishedTaskReceivedInstance;
	}
	
	@Override
	public synchronized boolean submitNewTask(Task task){
		System.out.println("New task are submit !");
		return getDispatcherInstance().addTask(task);
	}
	
	@Override
	public synchronized boolean postTreatedTask(Task task, CoupleIpPort worker){
		System.out.println("dispatcher receive response from worker " + worker);
		getDispatcherInstance().finishTask(worker); //TODO result not use !
		System.out.println("dispatcher send response to client " + worker);
		return getTaskReceivedInstance().sendResponse(task);
	}
	
	@Override
	public synchronized boolean registerNewWorker(CoupleIpPort worker){
		System.out.println("New worker register !");
		return getDispatcherInstance().addWorker(worker);
	}
	
	@Override
	public synchronized boolean unRegisterWorker(CoupleIpPort worker){
		System.out.println("Worker unregister !");
		return getDispatcherInstance().removeWorker(worker);
	}
	
}
