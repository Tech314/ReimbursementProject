package stark.project.util;

import java.sql.Blob;
import java.sql.Date;

import javax.servlet.http.Part;

public class Requests {
	
	private int reqId;
	private int empId;
	private int manId;
	private Date reqDate;
	private Date expDate;
	private double reqAmt;
	private String reqDesc;
	private String reqStatus;
	private String reqDecision;
	private Part expReceipt;
	private String empFName;
	private String empLName;
	private String manFName;
	private String manLName;
	private Blob pic;
	
	public Requests() {
		
	}

	public Requests(int reqId, int empId, int manId, Date reqDate, Date expDate, double reqAmt, String reqDesc,
			String reqStatus, String reqDecision) {
		this.reqId = reqId;
		this.empId = empId;
		this.manId = manId;
		this.reqDate = reqDate;
		this.expDate = expDate;
		this.reqAmt = reqAmt;
		this.reqDesc = reqDesc;
		this.reqStatus = reqStatus;
		this.reqDecision = reqDecision;
	}

	public int getReqId() {
		return reqId;
	}

	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getManId() {
		return manId;
	}

	public void setManId(int manId) {
		this.manId = manId;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public double getReqAmt() {
		return reqAmt;
	}

	public void setReqAmt(double reqAmt) {
		this.reqAmt = reqAmt;
	}

	public String getReqDesc() {
		return reqDesc;
	}

	public void setReqDesc(String reqDesc) {
		this.reqDesc = reqDesc;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public String getReqDecision() {
		return reqDecision;
	}

	public void setReqDecision(String reqDecision) {
		this.reqDecision = reqDecision;
	}
	
	public Part getExpReceipt() {
		return expReceipt;
	}

	public void setExpReceipt(Part expReceipt) {
		this.expReceipt = expReceipt;
	}

	public String getEmpFName() {
		return empFName;
	}

	public void setEmpFName(String empFName) {
		this.empFName = empFName;
	}

	public String getEmpLName() {
		return empLName;
	}

	public void setEmpLName(String empLName) {
		this.empLName = empLName;
	}

	public String getManFName() {
		return manFName;
	}

	public void setManFName(String manFName) {
		this.manFName = manFName;
	}

	public String getManLName() {
		return manLName;
	}

	public void setManLName(String manLName) {
		this.manLName = manLName;
	}
	

	public Blob getPic() {
		return pic;
	}

	public void setPic(Blob pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return "Requests [reqId=" + reqId + ", empId=" + empId + ", manId=" + manId + ", reqDate=" + reqDate
				+ ", expDate=" + expDate + ", reqAmt=" + reqAmt + ", reqDesc=" + reqDesc + ", reqStatus=" + reqStatus
				+ ", reqDecision=" + reqDecision + ", expReceipt=" + expReceipt + "]";
	}

	
	
	

}
