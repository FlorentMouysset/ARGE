package tp1.balancer;

import tp1.datacontract.Task;



/**
 * It's the  balancer front interface for clients
 * */
public interface IBalancerFrontClient {
	
	/*****************************************
	 * 
	 * 			Business services
	 * 
	 *****************************************/
	
	/**
	 * Submit a new task to {@link IBalancerFrontClient}
	 * @return boolean : true if the new task is correctly registered, false else 
	 * */
	boolean submitNewTask(Task task);
	
}
