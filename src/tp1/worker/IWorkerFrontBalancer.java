package tp1.worker;

import tp1.datacontract.Task;


/**
 * It's the worker front interface for balancer
 * */
public interface IWorkerFrontBalancer {

	/*****************************************
	 * 
	 * 			Business services
	 * 
	 *****************************************/
	
	/**
	 * Submit a new task to worker
	 * @return boolean : true if the new task is correctly registered, false else 
	 * */
	boolean submitNewTask(Task task);
	
}
