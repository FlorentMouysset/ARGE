package tp1.client;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
//  import org.apache.xmlrpc.demo.proxy.Adder;









import tp1.datacontract.CoupleIpPort;
import tp1.datacontract.Task;
import tp1.datacontract.Task.STATUS_TASK;
import tp1.utils.ARGELogger;
import tp1.utils.ARGELogger.LOG_LEVEL;

public class Client {

	private static int numReq = 0;
	private static Random rand;
	private static boolean stopClientThread = false;
	private static Thread clientThread;
	
	
	public static void start(final int nbResSec, final String ipBalancer, final int portBalancer, final CoupleIpPort localClient){
		
		clientThread =  new Thread(new Runnable() {
			
			@Override
			public void run() {
				 try {
					startInternal(nbResSec, ipBalancer, portBalancer, localClient);
				} catch (InterruptedException e) {
					ARGELogger.log("Client", " start " + e.getMessage(), LOG_LEVEL.CRIT);
					e.printStackTrace();
				}
			}
		});
		clientThread.start();
	}
	
	public static void stopAndJoinClientThread(){
		stopClientThread = true;
		try {
			clientThread.join();
		} catch (InterruptedException e) {
			ARGELogger.log("Client", " join " + e.getMessage(), LOG_LEVEL.CRIT);
			e.printStackTrace();
		}
	}

	
	private static void startInternal(int nbResSec, String ipBalancer, int portBalancer, CoupleIpPort localClient) throws InterruptedException {
		rand = new Random();

		// create configuration
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL("http://"+ ipBalancer + ":" + portBalancer +"/xmlrpc"));
		} catch (MalformedURLException e) {
			ARGELogger.log("Client", " send task " + e.getMessage(), LOG_LEVEL.CRIT);
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

		int cpt = 0;
		while(cpt<100 && !stopClientThread){
			cpt++;
			for(int i = 0 ; i <nbResSec; i++){
				Task newTask = new Task(getNewn(), getNewTaskID(localClient), localClient);
				newTask.setStatus(STATUS_TASK.UNTREAT);
				// make the a regular call
				Object[] params = new Object[]
						{ newTask };

				Boolean result = null;

				System.out.println("Balancer new submit task " + newTask.getTaskID() + " N="+  newTask.getN() + " cpt " + cpt);
				try {
					// TODO retry if false
					result = (Boolean) client.execute("Balancer.submitNewTask", params);
				} catch (XmlRpcException e) {
					ARGELogger.log("Client", " execute " + e.getMessage(), LOG_LEVEL.CRIT);
					e.printStackTrace();
				}
				System.out.println("balancer return for new submit = " + result);

				Thread.sleep(60000/nbResSec);
			}
		}

	}

	private static int getNewn() {
		return rand.nextInt(10)+15;
	}

	private static String getNewTaskID(CoupleIpPort localClient) {
		numReq++;
		return localClient + "-" + numReq;
	}
}