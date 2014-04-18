package tp1.client;

import tp1.datacontract.Task;

public class ClientFrontImpl implements IClientFrontBalancer {
	
	@Override
	public boolean submitTaskResponse(Task task) {
		System.out.println("[Client] result to task " + task.getTaskID() + " result : " + task.getResult());
		return true;
	}
	
	
}
