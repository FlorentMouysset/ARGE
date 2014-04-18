package tp1.worker.internal;

import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;

public interface IWorkerServices {
	
	void initWorkerService(CoupleIpPort balancer);
	
	/**
	 * terminate all task and update server.
	 * */
//	void prepareMigrateWorkerService();
//	void finishMigrateWorkerService(CoupleIpPort newWorker);
	
	//TODO doc
	void regitryToBalancer(CoupleIpPort worker);
	void unRegistryToBalancer(CoupleIpPort worker);
	void sendTaskResponse(Task task);

}
