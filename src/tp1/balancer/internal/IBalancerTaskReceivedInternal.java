package tp1.balancer.internal;

import tp1.datacontract.Task;
//TODO doc
public interface IBalancerTaskReceivedInternal {

	boolean sendResponse(Task task);

	/**
	 * Stop the sender thread.
	 * @param stop the thread after the current send. In worst case : wait 3s before interrupt the thread.</br>
	 * */
	void stopAndJoinReplyThread();

}
