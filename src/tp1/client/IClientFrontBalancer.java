package tp1.client;

import tp1.datacontract.Task;


/**
 * It's the client front interface for balancer
 * */
public interface IClientFrontBalancer {

	/*****************************************
	 * 
	 * 			Business services
	 * 
	 *****************************************/
	
	/**
	 * Submit response task to client
	 * @return boolean : true if the new task is correctly registered, false else 
	 * */
	boolean submitTaskResponse(Task task);
	
}
