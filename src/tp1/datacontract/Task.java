package tp1.datacontract;

import java.io.Serializable;


/**
 * This class is beans like. Here, it's the "data contract".</br>
 * </br>
 * taskID=-1 is correct just for technical reason, client please you shouldn't use -1 for taskID !
 * */
public class Task implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1561029352593830439L;

	
	private int n;
	private int result;
	private String taskID;
	private CoupleIpPort client;
	private STATUS_TASK status;
	
	public enum STATUS_TASK {UNTREAT, TREAT, ERROR_NO_WORKER};
	
	
	public Task(int n, String taskID, CoupleIpPort client) {
		super();
		this.n = n;
		this.taskID = taskID;
		this.client = client;
	}
	public CoupleIpPort getClient() {
		return client;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public STATUS_TASK getStatus() {
		return status;
	}
	public void setStatus(STATUS_TASK status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Task [taskID=" + taskID + "]";
	}
	
	
	
}
