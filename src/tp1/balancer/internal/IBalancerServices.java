package tp1.balancer.internal;

import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;

//TODO doc
public interface IBalancerServices {
	
	void sendTaskToWorker(Task task, CoupleIpPort worker);
	void sendTaskResponseToClient(Task task);

}
