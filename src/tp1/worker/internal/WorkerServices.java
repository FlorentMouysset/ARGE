package tp1.worker.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;

public class WorkerServices implements IWorkerServices{

	private static IWorkerServices serviceInstance = null;
	private XmlRpcClient client;
	private CoupleIpPort worker;
	
	private WorkerServices(){
	}
	
	@Override
	public void initWorkerService(CoupleIpPort balancer){
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://"+ balancer.getIp() + ":" + balancer.getPort() +"/xmlrpc"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		config.setEnabledForExtensions(true);  
		config.setConnectionTimeout(60 * 1000);
		config.setReplyTimeout(60 * 1000);

		client = new XmlRpcClient();

		// use Commons HttpClient as transport
		client.setTransportFactory(
				new XmlRpcCommonsTransportFactory(client));
		// set configuration
		client.setConfig(config);
	}
	
	public static IWorkerServices getServiceInstance(){
		if(serviceInstance==null){
			serviceInstance = new WorkerServices();
		}
		return serviceInstance;
	}
	
	@Override
	public void regitryToBalancer(CoupleIpPort worker){
		this.worker = worker;
		Object[] params = new Object[]  { worker };
		Boolean result = null;
		do{
			try {
				result = (Boolean) client.execute("Balancer.registerNewWorker", params);
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		}while(!result);
	}

	@Override
	public void unRegistryToBalancer(CoupleIpPort worker){
		Object[] params = new Object[]  { worker };
		Boolean result = null;
		do{
			try {
				result = (Boolean) client.execute("Balancer.unRegisterWorker", params);
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		}while(!result);
	}

	@Override
	public void sendTaskResponse(Task task){
		Object[] params = new Object[]  { task, worker };
		Boolean result = null;
		do{
			try {
				System.out.println("Worker send response to balancer " + task);
				result = (Boolean) client.execute("Balancer.postTreatedTask", params);
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		}while(!result);
	}

	/*@Override
	public void prepareMigrateWorkerService() {
		//TODO prepareMigrateWorkerService
		//wait send task are finish
		//pause the calculator 
		//destroy current server
	}*/

/*	@Override
	public void finishMigrateWorkerService(CoupleIpPort newWorker) {
		//restart caculator
		// TODO finishMigrateWorkerService initWorkerService
	}*/


}
