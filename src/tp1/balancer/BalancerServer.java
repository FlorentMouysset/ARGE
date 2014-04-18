package tp1.balancer;
//  import org.apache.xmlrpc.demo.webserver.proxy.impls.AdderImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import tp1.utils.ARGELogger;
import tp1.utils.ARGELogger.LOG_LEVEL;

public class BalancerServer {
	private static int port;
	private static WebServer webServer;
	private static Thread serverThread;

	
	public static void start(int port){
		BalancerServer.port=port;

		serverThread = new Thread(new Runnable() {		
			@Override
			public void run() {
				try {
					BalancerServer.startBalancerServer();
				} catch (Exception e) {
					ARGELogger.log("Balancer", " start server " + e.getMessage(), LOG_LEVEL.CRIT);
					e.printStackTrace();
				}
			}
		});

		serverThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void startBalancerServer() throws Exception {    	
		webServer = new WebServer(BalancerServer.port);
		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		/* Load handler definitions from a property file.
		 * The property file might look like:
		 *   Calculator=org.apache.xmlrpc.demo.Calculator
		 *   org.apache.xmlrpc.demo.proxy.Adder=org.apache.xmlrpc.demo.proxy.AdderImpl
		 */
		phm.load(Thread.currentThread().getContextClassLoader(),
				"XmlRpcServletBalancer.properties");

		/* You may also provide the handler classes directly,
		 * like this:
		 * phm.addHandler("Calculator",
		 *     org.apache.xmlrpc.demo.Calculator.class);
		 * phm.addHandler(org.apache.xmlrpc.demo.proxy.Adder.class.getName(),
		 *     org.apache.xmlrpc.demo.proxy.AdderImpl.class);
		 */
		xmlRpcServer.setHandlerMapping(phm);

		XmlRpcServerConfigImpl serverConfig =
				(XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		serverConfig.setEnabledForExtensions(true);
		serverConfig.setContentLengthOptional(false);
		System.out.println("[Balancer] ready.");
		webServer.start();
	}
	
	public static void stopAndJoinServerThread(){
		webServer.shutdown();
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			ARGELogger.log("Balancer", " join server " + e.getMessage(), LOG_LEVEL.CRIT);
			e.printStackTrace();
		}
	}
}
