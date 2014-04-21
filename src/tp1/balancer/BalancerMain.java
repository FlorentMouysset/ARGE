package tp1.balancer;

import java.util.Scanner;

import tp1.balancer.internal.BalancerDispatcherInternalImpl;
import tp1.balancer.internal.BalancerTaskReplyInternalImpl;

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
		sc.close();
		
		BalancerServer.stopAndJoinServerThread();
		BalancerTaskReplyInternalImpl.getTaskReplyInternal().stopAndJoinReplyThread();
		BalancerDispatcherInternalImpl.getBalancerWorkerInternal().stopAndJoinDispatcherThread();
	}

	

	
}
