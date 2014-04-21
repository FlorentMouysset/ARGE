package tp1.worker.internal;

import tp1.datacontract.Task;
//TODO doc
public interface IWorkerInternal {

	boolean sumbitNewTask(Task task);
	
	void stopAndJoinThread();
	

}
