package proj.basic.member.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class MemberVO implements Serializable {

//	/*會員*/ 首字改大寫
//	create table member(
//	 memberid int identity primary key, /*會員編號*/
//	 chinesename varchar(15), /*中文姓名*/
//	 gender varchar(1), /*性別*/
//	 birthday  datetime, /*生日*/
//	 email varchar(30), /*email*/
//	 account varchar(12) not null, /*帳號*/ //增加not null
//	 pwd varchar(20), /*密碼*/
//	 addr nvarchar(50), /*地址*/
//	 registdate datetime, /*註冊日期*/
//	 phone varchar(10), /*電話*/
//	 bonus int, /*紅利*/
//	 Deposit int, /*儲值金*/-->add
//	 photo varbinary(3000),/*不確定大小要多少*/
//	isBlock int,/*黑名單狀態1.無鎖定、2.鎖定IP、3.鎖定帳號*/-->add
//	 wrongtimes int,/*錯誤次數*/ -->add
//	 constraint gender check (gender in('男','女')),-->改中文
//	 constraint block check (isBlock in('1','2','3')),-->add約束條件
//	);
	
	private Integer memberID;
	private String chineseName;
	private String gender;
	private Date birthday;
	private String email;
	private String account;
	private String pwd;
	private String addr;
	private Date registDate;
	private String phone;
	private Integer bonus;
	private Integer deposit;//add
	private byte[] photo;
	private Integer isBlock;//add
	private Integer wrongtimes;//add
	
//	private Set<OrderListVO> orderListVO = new LinkedHashSet<OrderListVO>();
	
	public Integer getMemberID() {
		return memberID;
	}
	public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public Integer getIsBlock() {
		return isBlock;
	}
	public void setIsBlock(Integer isBlock) {
		this.isBlock = isBlock;
	}
	public Integer getWrongtimes() {
		return wrongtimes;
	}
	public void setWrongtimes(Integer wrongtimes) {
		this.wrongtimes = wrongtimes;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getBonus() {
		return bonus;
	}
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
//	public Set<OrderListVO> getOrderListVO() {
//		return orderListVO;
//	}
//	public void setOrderListVO(Set<OrderListVO> orderListVO) {
//		this.orderListVO = orderListVO;
//	}

}
