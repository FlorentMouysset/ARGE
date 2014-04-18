package tp1.balancer.internal;

import tp1.datacontract.CoupleIpPort;

//TODO doc
public interface IWorkerManager {
	
	/***
	 * return the unload worker or null if none worker are registered.
	 */
	CoupleIpPort getMinLoadWorker();
	void removeWorker(CoupleIpPort worker);
	void addWorker(CoupleIpPort worker);
	void addJob(CoupleIpPort worker);
	void finishJob(CoupleIpPort worker);
}
