package garrag.shiti;

import java.io.Serializable;

public class User implements Serializable{
	private String name;
	private String id;
	private String mac;
	private String zhuangtai;
	
	public String getZhuangtai() {
		return zhuangtai;
	}

	public void setZhuangtai(String zhuangtai) {
		this.zhuangtai = zhuangtai;
	}

	public User() {
		super();
	}

	public User(String name, String id, String mac) {
		super();
		this.name = name;
		this.id = id;
		this.mac = mac;
		this.zhuangtai = "1";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
}
