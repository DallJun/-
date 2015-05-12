package garrag.shiti;

import java.io.Serializable;

public class MClass implements Serializable{
	private String className; //班级名称
	private String monitor; //班长
	private int classnum;  //班级人数
	private String subject; //课程
	private String dep; //系部
	
	@Override
	public String toString() {
		return "MClass [className=" + className + ", monitor=" + monitor
				+ ", classnum=" + classnum + ", subject=" + subject + ", dep="
				+ dep + "]";
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	public int getClassnum() {
		return classnum;
	}
	public void setClassnum(int classnum) {
		this.classnum = classnum;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	
}
