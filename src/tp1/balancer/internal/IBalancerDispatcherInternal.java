package tp1.balancer.internal;

import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;

/**
 * Manage the tasks and the workers.
 * Use by BalancerFrontImpl.
 * */
public interface IBalancerDispatcherInternal {
	
	
	/**
	 * Registered a new worker.</br>
	 * <b>This call doesn't block the caller.</b>
	 * So after this call the new worker doesn't call immediately.</br>
	 * @param worker the new worker given by its IP and port.
	 * @return <code>true</code> if the demand is correctly register. <code>false</code> if demand buffer overflow, please try later.
	 * */
	boolean addWorker(CoupleIpPort worker);

	/**
	 * Registered a new task and send it to a <i> free </i> worker.</br>
	 * <b>This call doesn't block the caller.</b>
	 * So after this call the new task doesn't tread immediately.</br>
	 * @param task the new task (submit by client).
	 * @return <code>true</code> if the demand is correctly register. <code>false</code> if demand buffer overflow, please try later.
	 * */
	boolean addTask(Task task);

	/**
	 * Unregistered a worker.</br>
	 * <b>This call doesn't block the caller.
	 * <u>So after this call the worker could be again call a few time.</u></b></br>
	 * This call has no effect is the worker has ready remove.</br>
	 * @param worker the worker given by its IP and port.
	 * @return <code>true</code> if the demand is correctly register. <code>false</code> if demand buffer overflow, please try later.
	 * </br></br><b>TODO : Make this call synchronous : <i>after this call the worker cannot be call again ! </i></b>
	 * */
	boolean removeWorker(CoupleIpPort worker);

	/**
	 * Declared a task ending.</br>
	 * <b>This call doesn't block the caller.</b><br>
	 * <u>This method doesn't send the response to a client </u>: see {@link IBalancerTaskReceivedInternal}
	 * @param worker the new worker who has tread a task (worker given by its IP and port).
	 * @return <code>true</code> if the demand is correctly register. <code>false</code> if demand buffer overflow, please try later.
	 * */
	boolean finishTask(CoupleIpPort worker);

	/**
	 * Terminated the dispatcher thread.</br>
	 * <b>This termination is quick and "hard" : all the pending tasks/registration/deregistration are skips and lost.</b></br>
	 * This call block the caller until the thread are join.<br>
	 * TODO block until all client are warned, the response could be complete (waiting the worker response) or partial.
	 */
	void stopAndJoinDispatcherThread();
}
