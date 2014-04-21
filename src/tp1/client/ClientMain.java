package tp1.client;

import java.util.Scanner;

import tp1.client.internal.ClientServer;
import tp1.datacontract.CoupleIpPort;

public class ClientMain {

	/**
	 * args[0] = nb req/s
	 * args[1] = local ip
	 * args[2] = local port
	 * args[3] = dispatcher ip
	 * args[4] = dispatcher port
	 * */
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Client start " + args.length + " " + args[2] + " " + args[1] + " " + args[0]);
		// ip + port balancer
		int nbReq = Integer.parseInt(args[0]);
		String balancerIp = args[3];
		int balancerPort = Integer.parseInt(args[4]);
		
		String localIp = args[1];
		int localPort = Integer.parseInt(args[2]);

		CoupleIpPort localCouple = new CoupleIpPort(localIp, localPort);

		ClientServer.start(localPort);
		Client.start(nbReq, balancerIp, balancerPort, localCouple);
		
		System.out.println("Type exit for stop worker.");
		
		Scanner sc = new Scanner(System.in);
		String str = sc.next();		
		sc.close();

		ClientServer.stop();
		Client.stopAndJoinClientThread();
	}


}
