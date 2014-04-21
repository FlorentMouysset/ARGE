package tp1.worker;

import java.util.Scanner;

import tp1.datacontract.CoupleIpPort;
import tp1.worker.internal.IWorkerInternal;
import tp1.worker.internal.IWorkerServices;
import tp1.worker.internal.WorkerInternalImpl;
import tp1.worker.internal.WorkerServer;
import tp1.worker.internal.WorkerServices;

public class WorkerMain {

	/**
	 * args[0] = local ip
	 * args[1] = local port
	 * args[2] = balancer ip
	 * args[3] = balancer port
	 * 
	 * */
	public static void main(String args[]){

		Integer localPort = Integer.parseInt(args[1]);
		Integer balancerPort = Integer.parseInt(args[3]);

		CoupleIpPort localCouple = new CoupleIpPort(args[0], localPort);
		CoupleIpPort balancerCouple = new CoupleIpPort(args[2], balancerPort);

		WorkerServer.start(localPort);
		IWorkerServices workerServices = WorkerServices.getServiceInstance();
		workerServices.initWorkerService(balancerCouple);
		workerServices.regitryToBalancer(localCouple);

		System.out.println("Type m <ipBalancer> form migrate\nType exit for stop worker.");
		
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		System.out.println("ici");
		
		//TODO if migrate stop server 
		//prepareMigrateWorkerService
		//System.out.println("migrate !");
		//System.out.println("di moi quand c ok");
		//finishMigrateWorkerService 
		
		

		workerServices.unRegistryToBalancer(localCouple);
		IWorkerInternal workerInternal = WorkerInternalImpl.getWorkerInternal();
		if(workerInternal != null) workerInternal.stopAndJoinThread();
		WorkerServer.stop();
	}


}
