package tp1.datacontract;

import java.io.Serializable;


public class CoupleIpPort implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3320400091375895243L;
	private String ip;
	private int port;
	
	public CoupleIpPort(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CoupleIpPort))
			return false;
		CoupleIpPort other = (CoupleIpPort) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[ip=" + ip + ", port=" + port + "]";
	}
	
}
