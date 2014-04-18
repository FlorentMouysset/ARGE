package tp1.balancer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import tp1.balancer.internal.BalancerDispatcherInternalImpl;
import tp1.balancer.internal.BalancerTaskReplyInternalImpl;
import tp1.datacontract.Task;
import tp1.datacontract.Task.STATUS_TASK;
import tp1.utils.ARGELogger;
import tp1.utils.ARGELogger.LOG_LEVEL;

public class BalancerMain {

	
	/**
	 * args[0] = local ip
	 * args[1] = local port
	 * 
	 * */
	public static void main(String args[]){

		Integer localPort = Integer.parseInt(args[1]);
	
		BalancerServer.start(localPort);
		System.out.println("Type exit for stop balancer.");
		
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		
		BalancerServer.stopAndJoinServerThread();
		BalancerTaskReplyInternalImpl.getTaskReplyInternal().stopAndJoinReplyThread();
		BalancerDispatcherInternalImpl.getBalancerWorkerInternal().stopAndJoinDispatcherThread();
	}

	

	
}
