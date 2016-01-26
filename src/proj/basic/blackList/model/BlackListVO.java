package proj.basic.blackList.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class BlackListVO {
	
//	/*黑名單 BlackList*/
//	create table BlackList(
//	 MemberID int, /*會員編號*/
//	 Account varchar(20), /*帳號*/
//	 IP nvarchar(30), /*IPAddress*/
//	 unLockTime  time,/*鎖定時間*/
//	 FOREIGN KEY (MemberID) REFERENCES member(MemberID)
//	);
	
	private Integer memberID;
	private String account;
	private String ip;
	private Timestamp unLockTime;
	
	public Integer getMemberID() {
		return memberID;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Timestamp getUnLockTime() {
		return unLockTime;
	}
	public void setUnLockTime(Timestamp unLockTime) {
		this.unLockTime = unLockTime;
	}
}
