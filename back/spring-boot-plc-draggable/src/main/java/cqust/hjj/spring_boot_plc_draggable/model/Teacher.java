package cqust.hjj.spring_boot_plc_draggable.model;

import java.io.Serializable;

public class Teacher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tid;
	private Integer tno;
	private String tusername;
	private String tpassword;
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public Integer getTno() {
		return tno;
	}
	public void setTno(Integer tno) {
		this.tno = tno;
	}
	public String getTusername() {
		return tusername;
	}
	public void setTusername(String tusername) {
		this.tusername = tusername;
	}
	public String getTpassword() {
		return tpassword;
	}
	public void setTpassword(String tpassword) {
		this.tpassword = tpassword;
	}
	@Override
	public String toString() {
		return "Teacher [tid=" + tid + ", tno=" + tno + ", tusername=" + tusername + ", tpassword=" + tpassword + "]";
	}
	
	

}
