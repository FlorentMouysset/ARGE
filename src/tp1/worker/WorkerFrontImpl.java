package tp1.worker;

import tp1.datacontract.Task;
import tp1.worker.internal.IWorkerInternal;
import tp1.worker.internal.WorkerInternalImpl;

public class WorkerFrontImpl implements IWorkerFrontBalancer {
	
	private static IWorkerInternal workerInstance = null;
	
	private static IWorkerInternal getWorkerInstance(){
		if(workerInstance == null){
			workerInstance = WorkerInternalImpl.getWorkerInternal();
		}
		return workerInstance;
	}
	
	@Override
	public synchronized boolean submitNewTask(Task task){
		System.out.println("New task are submit to worker !" + task);
		return  getWorkerInstance().sumbitNewTask(task);
	}
	
	
}
