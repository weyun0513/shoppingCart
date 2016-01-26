package proj.basic.loginRecord.model;

import java.sql.Timestamp;
public class LoginRecordVO {
//	/*登入紀錄檔(LoginRecord)*/
//	create table loginRecord(
//	loginrecordno int primary key identity (100 , 1),/*紀錄檔流水號*/
//	MemberID int, /*會員編號*/ 
//	ip varchar(30), /*IPAddress*/
//	account varchar(20),  /*帳號*/
//	loginTime datetime, /*登入時間*/
//	loginMsg varchar(300),/*登入系統的訊息*/
//	FOREIGN KEY (MemberID) REFERENCES member(MemberID)
//	)
	
	private Integer memberID;
	private String ip;
	private String account;
	private Timestamp loginTime;
	private String loginMsg;
	
	public Integer getMemberID() {
		return memberID;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginMsg() {
		return loginMsg;
	}
	public void setLoginMsg(String loginMsg) {
		this.loginMsg = loginMsg;
	}
	
}
