package tp1.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tp1.balancer.internal.IWorkerManager;
import tp1.balancer.internal.WorkerManagerImpl;
import tp1.datacontract.CoupleIpPort;

public class WorkerManagerTests {
	private IWorkerManager workerManager;
	private List<CoupleIpPort> couplesIpPort;
	
	@Before
	public void setUp(){
		workerManager = new WorkerManagerImpl();
		couplesIpPort = new ArrayList<CoupleIpPort>();
		couplesIpPort.add(new CoupleIpPort("1", 1));
		couplesIpPort.add(new CoupleIpPort("2", 1));
		couplesIpPort.add(new CoupleIpPort("3", 2));
		couplesIpPort.add(new CoupleIpPort("1", 4));
	}
	
	
	@Test
	public void testAdd() {
		workerManager.addWorker(couplesIpPort.get(0));
		workerManager.addWorker(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(0));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(0)));
	}
	
	@Test
	public void testMultiplesAddJobs() {
		workerManager.addWorker(couplesIpPort.get(0));
		workerManager.addWorker(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(0));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(1));
		assertTrue(workerManager.getMinLoadWorker().equals(couplesIpPort.get(0)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(1)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(2)));
	}
	
	
	@Test
	public void testRemoveJobs() {
		workerManager.addWorker(couplesIpPort.get(0));
		workerManager.addWorker(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(0));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.finishJob(couplesIpPort.get(2));
		workerManager.finishJob(couplesIpPort.get(2));
		assertTrue(workerManager.getMinLoadWorker().equals(couplesIpPort.get(2)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(1)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(0)));
	}
	
	@Test
	public void testRemoveWorkers() {
		workerManager.addWorker(couplesIpPort.get(0));
		workerManager.addWorker(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(0));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.removeWorker(couplesIpPort.get(0));
		assertTrue(workerManager.getMinLoadWorker().equals(couplesIpPort.get(2)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(1)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(0)));
	}
	
	@Test
	public void testAddWorkers() {
		workerManager.addWorker(couplesIpPort.get(0));
		workerManager.addWorker(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(0));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(3));
		assertTrue(workerManager.getMinLoadWorker().equals(couplesIpPort.get(3)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(1)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(0)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(2)));
	}
	
	
	@Test
	public void testAddWorkersAndAddJobsAndRemoveJobs() {
		workerManager.addWorker(couplesIpPort.get(0));
		workerManager.addWorker(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(2));
		workerManager.addJob(couplesIpPort.get(0));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addJob(couplesIpPort.get(1));
		workerManager.addWorker(couplesIpPort.get(3));
		workerManager.addJob(couplesIpPort.get(3));
		workerManager.finishJob(couplesIpPort.get(2));
		workerManager.finishJob(couplesIpPort.get(2));
		assertTrue(workerManager.getMinLoadWorker().equals(couplesIpPort.get(2)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(1)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(0)));
		assertFalse(workerManager.getMinLoadWorker().equals(couplesIpPort.get(3)));
	}
}
