package tp1.client.internal;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import tp1.utils.ARGELogger;
import tp1.utils.ARGELogger.LOG_LEVEL;


/**
 * TODO singleton
 * */
public class ClientServer {
	private static int port;
	private static WebServer webServer;

	public static void start(int port){
		ClientServer.port=port;

		Thread thd = new Thread(new Runnable() {		
			@Override
			public void run() {
				try {
					ClientServer.startClientServer();
				} catch (Exception e) {
					ARGELogger.log("Client", " start server " + e.getMessage(), LOG_LEVEL.CRIT);
					e.printStackTrace();
				}
			}
		});

		thd.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void startClientServer() throws Exception {    	
		webServer = new WebServer(ClientServer.port);
		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		/* Load handler definitions from a property file.
		 * The property file might look like:
		 *   Calculator=org.apache.xmlrpc.demo.Calculator
		 *   org.apache.xmlrpc.demo.proxy.Adder=org.apache.xmlrpc.demo.proxy.AdderImpl
		 */
		phm.load(Thread.currentThread().getContextClassLoader(),
				"XmlRpcServletClient.properties");

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

		webServer.start();
	}

	public static void stop(){
		webServer.shutdown();
	}


}