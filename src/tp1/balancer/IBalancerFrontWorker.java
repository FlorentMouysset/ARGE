package tp1.balancer;

import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;

/**
 * It's the balancer front interface for workers
 * */
public interface IBalancerFrontWorker {

	/*****************************************
	 * 
	 * 			Business services
	 * 
	 *****************************************/
	
	/**
	 * Call by a worker when the task has been treated.
	 * @param task : the treated task
	 * @param worker : the worker who send the task
	 * @return boolean : true if the task are correctly post, false else
	 * */
	boolean postTreatedTask(Task task, CoupleIpPort worker);
	
	
	
	/*****************************************
	 * 
	 * 			Technical services
	 * 
	 *****************************************/
	
	/**
	 * Call by a worker for resisted him.
	 * @param ip :  worker local ip
	 * @param port : worker port
	 * @return true if the registration are correctly done, false else
	 * */
	boolean registerNewWorker(CoupleIpPort worker);
	
	/**
	 * Call by a worker for unresisted him.
	 * @param ip :  worker local ip
	 * @param port : worker port
	 * @return true if the unregistration are correctly done, false else
	 * */
	boolean unRegisterWorker(CoupleIpPort worker);


}
