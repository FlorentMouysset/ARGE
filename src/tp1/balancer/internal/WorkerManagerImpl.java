package tp1.balancer.internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import tp1.datacontract.CoupleIpPort;


public class WorkerManagerImpl implements IWorkerManager{

	private Queue<WorkerManagerImpl.ValuedWorker> currentWorkers = new PriorityQueue<WorkerManagerImpl.ValuedWorker>(10, new ValuedWorkerComparator());
	
	@Override
	public CoupleIpPort getMinLoadWorker() {
		if(currentWorkers.peek()==null){
			return null;
		}
		System.out.println("[B-MANAGER] minLoad " + currentWorkers.peek().getWorker());
		return currentWorkers.peek().getWorker();
	}

	@Override
	public void removeWorker(CoupleIpPort worker) {
		System.out.println("[B-MANAGER] remove " + worker);
		currentWorkers.remove(new ValuedWorker(worker,0));
	}

	@Override
	public void addWorker(CoupleIpPort worker) {
		System.out.println("[B-MANAGER] add " + worker);
		currentWorkers.add(new ValuedWorker(worker, 0));
	}
	
	@Override
	public void addJob(CoupleIpPort worker) {
		System.out.println("[B-MANAGER] job+ " + worker);
		Integer currentNbJobs = getCurrentNbJobs(worker);
		currentWorkers.add(new ValuedWorker(worker, currentNbJobs+1));
	}
	

	@Override
	public void finishJob(CoupleIpPort worker) {
		System.out.println("[B-MANAGER] job- " + worker);
		Integer currentNbJobs = getCurrentNbJobs(worker);
		currentWorkers.add(new ValuedWorker(worker, currentNbJobs-1));
	}
	
	/** remove the worker and return the nbJobs*/
	private Integer getCurrentNbJobs(CoupleIpPort worker) {
		Iterator<WorkerManagerImpl.ValuedWorker> valuedWorkers = currentWorkers.iterator();
		boolean found = false;
		ValuedWorker vw = null;
		while( valuedWorkers.hasNext() && !found){
			vw = valuedWorkers.next();
			if(vw.getWorker().equals(worker)){
				found = true;
			}
		}
		Integer currentNbJobs = vw.getNbJobs();
		currentWorkers.remove(vw);
		return currentNbJobs;
	}
	
	private class ValuedWorker{
		private CoupleIpPort worker;
		private int liveJob;
		
		public ValuedWorker(CoupleIpPort worker, int liveJob) {
			super();
			this.worker = worker;
			this.liveJob = liveJob;
		}

		public int getNbJobs() {
			return liveJob;
		}

		public CoupleIpPort getWorker() {
			return worker;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((worker == null) ? 0 : worker.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof ValuedWorker))
				return false;
			ValuedWorker other = (ValuedWorker) obj;
			if (worker == null) {
				if (other.worker != null)
					return false;
			} else if (!worker.equals(other.worker))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ValuedWorker [worker=" + worker + ", liveJob=" + liveJob
					+ "]";
		}
	}
	
	
	private class ValuedWorkerComparator implements Comparator<ValuedWorker>{
		
		@Override
		public int compare(ValuedWorker o1, ValuedWorker o2) {
			if(o1 == null && o2 == null){
				return 0;
			}
			
			if(o1 == null) return 1;
			if(o2 == null) return -1;
			
			return o1.getNbJobs() - o2.getNbJobs();
		}
	}

	
}
