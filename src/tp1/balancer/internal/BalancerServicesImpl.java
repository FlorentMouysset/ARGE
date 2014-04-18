package tp1.balancer.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;

public class BalancerServicesImpl implements IBalancerServices{

	private static IBalancerServices serviceInstance = null;
	private Map<CoupleIpPort,XmlRpcClient> workerClient = new HashMap<CoupleIpPort,XmlRpcClient>();
	private Map<CoupleIpPort,XmlRpcClient> clientClient = new HashMap<CoupleIpPort,XmlRpcClient>();
	
	private BalancerServicesImpl(){
	}
	
	public static IBalancerServices getServiceInstance(){
		if(serviceInstance==null){
			serviceInstance = new BalancerServicesImpl();
		}
		return serviceInstance;
	}

	@Override
	public void sendTaskToWorker(Task task, CoupleIpPort worker) {
		Object[] params = new Object[]  { task };
		Boolean result = null;
		
		do{
			try {
				XmlRpcClient client = getOrCreateRcpClientToWorker(worker);
				System.out.println("Balancer service send to " + worker);
				result = (Boolean) client.execute("Worker.submitNewTask", params);
				System.out.println("Balancer service task is send to " + worker);
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		}while(!result);
	}

	@Override
	public void sendTaskResponseToClient(Task task) {
		Object[] params = new Object[]  { task };
		Boolean result = null;
		
		do{
			try {
				XmlRpcClient client = getOrCreateRcpClientToClient(task.getClient());
				System.out.println("Balancer service send to client " + task.getClient());

				result = (Boolean) client.execute("Client.submitTaskResponse", params);
			} catch (XmlRpcException e) {
				e.printStackTrace();
			}
		}while(!result);
		
	}
	
	private XmlRpcClient getOrCreateRcpClientToClient(CoupleIpPort client){
		XmlRpcClient clientRcpClient = clientClient.get(client);
		if(clientRcpClient==null){
			clientRcpClient = createNewRcpClient(client);
			clientClient.put(client, clientRcpClient);
		}
		return clientRcpClient;
	}

	private XmlRpcClient getOrCreateRcpClientToWorker(CoupleIpPort worker){
		XmlRpcClient workerRcpClient = workerClient.get(worker);
		if(workerRcpClient==null){
			workerRcpClient = createNewRcpClient(worker);
			workerClient.put(worker, workerRcpClient);
		}
		return workerRcpClient;
	}
	
	private static XmlRpcClient createNewRcpClient(CoupleIpPort newClient) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://"+ newClient.getIp() + ":" + newClient.getPort() +"/xmlrpc"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		config.setEnabledForExtensions(true);  
		config.setConnectionTimeout(60 * 1000);
		config.setReplyTimeout(60 * 1000);

		XmlRpcClient client = new XmlRpcClient();

		// use Commons HttpClient as transport
		client.setTransportFactory(
				new XmlRpcCommonsTransportFactory(client));
		// set configuration
		client.setConfig(config);
		
		return client;
	}


}
