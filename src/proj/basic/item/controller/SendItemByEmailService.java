package proj.basic.item.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import proj.basic.itemmedia.controller.ItemMediaService;
import proj.basic.member.model.MemberVO;

public class SendItemByEmailService{
	private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
	
	
//	public static void main(String args[]){
	public int infoMail(MemberVO memberVO, String addrSendTo, String urlSend){
		int errorCode = 1; 
		
		java.util.Properties property = System.getProperties();
		property.put("mail.transport.protocol", "smtp");
		property.put("mail.smtp.host", SMTP_HOST_NAME);
		property.put("mail.smtp.port", 587);
		property.put("mail.smtp.auth", "true");
		Authenticator auth = new SendItemByEmailAuth();
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(property, auth);
		System.out.println("begging");
		try {
			InternetAddress send = new InternetAddress("domoto02@yahoo.com.tw");
			InternetAddress receive = new InternetAddress(addrSendTo);
			System.out.print("**"+addrSendTo);
			URLConnection urlConn = new URL(urlSend).openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"UTF-8"));
			String str = null;
			String tableTag = "[<][/]*[table>]+";
			StringBuilder strBuild = new StringBuilder();
			boolean isTable = false;
			while((str = rd.readLine())!=null){
				if(str.trim().matches(tableTag))
					if(isTable == false)
						isTable = true;
					else{
						isTable = false;
//						System.out.println(str);
						strBuild.append(str);
						break;
					}
				
				if(isTable){
					strBuild.append(str);
//					System.out.println(str);
				}
			}
			Message message = new MimeMessage(session);
//			System.out.println("5");
			message.setRecipient(Message.RecipientType.TO, receive);
			message.setSubject("您的朋友" + memberVO.getChineseName() + "向您推薦WOW商城的商品");
			message.setContent(strBuild.toString(),"text/html; charset=utf-8");
			message.setFrom(send);
//System.out.println(strBuild.toString());			
//			BodyPart b1 = new MimeBodyPart();
//			b1.setText("Check this special item:");
//			message.setText(urlSend);
			
			

//			message.setText("有圖片嗎");
//			Transport.send(message);
			Transport transport = session.getTransport();
			// Connect the transport object.
			transport.connect();
			// Send the message.
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			// Close the connection.
			transport.close();
			System.out.println("end");
			
		} catch (AddressException e) {
			e.printStackTrace();
			errorCode = -1;
		} catch (MessagingException e) {
			e.printStackTrace();
			errorCode = 0;
		} catch(Exception e){
			e.printStackTrace();
			errorCode = 0;
		}
		return errorCode;
	}
	
	public byte[] getMainPhoto(Integer itemNo){
		ItemMediaService imedaiSrv = new ItemMediaService();
		return imedaiSrv.getFirstPhoto(itemNo, 1).getItemMedia();
	}
	
	

}
