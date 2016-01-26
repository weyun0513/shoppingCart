package proj.basic.member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

import proj.basic.item.controller.SendItemByEmailAuth;
import proj.basic.member.model.MemberDAO;
import proj.basic.member.model.MemberVO;

public class MemberService {

	private byte[] bFile;
	private MemberVO memberVO;
	private MemberDAO memberDao;
	
	public MemberService(){
		try {
			memberDao = new MemberDAO();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void regist(Map<String, String> memberMap){
		
		memberVO = new MemberVO();
		memberVO.setAccount(memberMap.get("account"));
		System.out.println(memberMap.get("account") + "註冊的密碼為："+memberMap.get("pwd"));
		memberVO.setPwd(pwdEncoMD5(memberMap.get("pwd")));
		memberVO.setChineseName(memberMap.get("chineseName"));
		
		if(memberMap.get("addr") != null||memberMap.get("addr").trim().length()!=0)
			memberVO.setAddr(memberMap.get("addr"));
		else
			memberVO.setAddr("");
		
		memberVO.setEmail(memberMap.get("email"));
		memberVO.setGender(memberMap.get("gender"));
		memberVO.setBirthday(java.sql.Date.valueOf(memberMap.get("birthday")));
		
		if(memberMap.get("phone") != null && memberMap.get("phone").trim().length()!=0)
			memberVO.setPhone(memberMap.get("phone"));
		else
			memberVO.setPhone("");
		
		memberVO.setPhoto(bFile);
		memberVO.setBonus(0);//應該可以在MemberVO的地方給初值就不用在此另外設定?
		memberVO.setDeposit(0);
		memberVO.setIsBlock(1);
		memberVO.setWrongtimes(0);
		memberVO.setRegistDate(new java.sql.Date(new java.util.Date().getTime()));
		memberDao.insert(memberVO);
	}
	
	public byte[] procPhoto(InputStream in){
		try { 
			bFile = new byte[in.available()];
			in.read(bFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return bFile;
	}
	
	public MemberVO login(String account, String pwd){
		memberVO = memberDao.findByAccount(account);
		System.out.println(pwdEncoMD5(pwd));
		System.out.println(memberVO.getPwd());
		if(memberVO != null && pwdEncoMD5(pwd).equals(memberVO.getPwd()))
			return memberVO;
		else
			return null;
	}
	
	public MemberVO update(Map<String, String> memberMap, InputStream in){
		
        memberVO = getMemberVO(memberMap.get("account"));
		
		if(memberMap.get("pwd")==null || memberMap.get("pwd").trim().length()==0)
			memberVO.setPwd(memberVO.getPwd());
		else
		memberVO.setPwd(pwdEncoMD5(memberMap.get("pwd")));
		
		memberVO.setChineseName(memberMap.get("chineseName"));
		memberVO.setAddr(memberMap.get("addr"));
		memberVO.setEmail(memberMap.get("email"));
		memberVO.setGender(memberMap.get("gender"));
		memberVO.setBirthday(java.sql.Date.valueOf(memberMap.get("birthday")));
		memberVO.setPhone(memberMap.get("phone"));
		memberVO.setBonus(0);//應該可以在MemberVO的地方給初值就不用在此另外設定?
		memberVO.setDeposit(0);
		memberVO.setIsBlock(1);
		memberVO.setWrongtimes(0);
		memberVO.setRegistDate(new java.sql.Date(new java.util.Date().getTime()));
		
		if(in==null)//當會員選擇不更新圖片的時候要將原先的照片保留
			memberVO.setPhoto(memberVO.getPhoto());
		else
			memberVO.setPhoto(bFile);
		
		memberDao.update(memberVO);
		
		return memberVO;
	}
	
public void resetPwd(String pwd, String memberID){
	System.out.println("pwd==" + pwd);
	String newpwd = pwdEncoMD5(pwd);
	System.out.println("newpwd==" + newpwd);
		memberDao.resetPwd(newpwd, memberID);
		return;
	}
	
	public MemberVO getMemberVO(String account){
		MemberVO memberVO = memberDao.findByAccount(account);
		if(memberVO != null)
			return memberVO;
		else
			return null;
	}
	
	public MemberVO getMemberVObyID(String memberID){
		MemberVO memberVO = memberDao.findByID(memberID);
		if(memberVO != null)
			return memberVO;
		else
			return null;
	}
	
	public void updateErrorNum(MemberVO memberVO, Integer count){
		memberVO.setWrongtimes(count);
		memberDao.updateWrongTimes(memberVO);
	}
	
	public void chgBlockStatus(MemberVO memberVO, Integer status){
		memberDao.updateStatus(memberVO, status);
	}
	
	public void infoMail(MemberVO memberVO, java.util.Date loginDate){
		String SMTP_HOST_NAME = "smtp.sendgrid.net";

		String errorMsg = "您happy mall的帳號 " + memberVO.getAccount() +  "於" + DateFormat.getInstance().format(loginDate) + "有密碼輸入錯誤的登入紀錄";
		
		SendItemByEmailAuth auth = new SendItemByEmailAuth();
		java.util.Properties property = System.getProperties();
		property.put("mail.transport.protocol", "smtp");
		property.put("mail.smtp.host", SMTP_HOST_NAME);
		property.put("mail.smtp.port", 587);
		property.put("mail.smtp.auth", "true");
//		property.put("mail.smtp.host","msa.hinet.net");
//		property.put("mail.transport.protocol","SMTP");
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(property, auth);
		
		try {
			InternetAddress send = new InternetAddress("mallSuppport@mall.us");
			InternetAddress receive = new InternetAddress(memberVO.getEmail());
			
			System.out.println(memberVO.getEmail());
			Message message = new MimeMessage(session);
			message.setFrom(send);
			message.setRecipient(Message.RecipientType.TO, receive);
			message.setSubject("登入錯誤");
			message.setText(errorMsg);
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void RQresetMail(String webPath, MemberVO memberVO){
		String rqPath = "/RQResetPwdServlet";
		String resetPath = webPath + rqPath;
		String mailBody = resetPath + "?reid=" + memberVO.getMemberID() + "&t=" + new SimpleDateFormat("yyyyMMddkkmm").format(new java.util.Date()) + "&vCode=" + pwdEncoMD5(memberVO.getEmail()+memberVO.getMemberID());
		System.out.println("mailBody== " + mailBody);
		
		java.util.Properties property = System.getProperties();
		property.put("mail.smtp.host","msa.hinet.net");
		property.put("mail.transport.protocol","SMTP");
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(property);
		
		try {
			InternetAddress send = new InternetAddress("milovexm@hotmail.com");
			InternetAddress receive = new InternetAddress(memberVO.getEmail());
			Message message = new MimeMessage(session);
			message.setFrom(send);
			message.setRecipient(Message.RecipientType.TO, receive);
			message.setSubject("登入錯誤");
			message.setText(mailBody);
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	public MemberVO verifyResetMail(String resetID, String time, String vCode){//假設產生後72小時有效
		
		MemberVO mVO = getMemberVObyID(resetID);
		
		if(mVO == null)
			return null;
		
		if(!pwdEncoMD5(mVO.getEmail() + mVO.getMemberID()).equals(vCode))//假設隱碼為會員設定mail+id
			return null;
		
		try {
			if(new java.util.Date().getTime() - 72 * 60 * 60 *1000 > new SimpleDateFormat("yyyymmdd").parse(time).getTime())
				return null;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//至此所有不符合條件皆已排除
		
		return mVO;
	}
	
	
	private String pwdEncoMD5(String password){
		StringBuffer savePwd = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] pwd = md.digest(password.getBytes());
			savePwd = new StringBuffer();
			for(byte b:pwd)
				savePwd.append(b);
			System.out.println(savePwd.length());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savePwd.toString();
	}
	
//	字串或二進位資料會被截斷
//	public String getMD5Endocing(String str) {
//		final StringBuffer buffer = new StringBuffer();
//		try {
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(str.getBytes());
//			byte[] digest = md.digest();
//			
//			for (int i = 0; i < digest.length; ++i) {
//				final byte b = digest[i];
//				final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
//				buffer.append(value < 16 ? "0" : "");
//				buffer.append(Integer.toHexString(value));
//			}
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return buffer.toString();
//	}
}
