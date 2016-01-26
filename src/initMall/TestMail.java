package initMall;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestMail {
	public static void main(String[] args) {
		java.util.Properties property = System.getProperties();
		// property.put("mail.smtp.host","smtp.kbronet.com.tw"); //凱擘..失敗!
//		property.put("mail.smtp.host", "msa.hinet.net");// 資策會
//		property.put("mail.transport.protocol", "SMTP");
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(property);
try{
		String username = "azure_8577427aa2856a934cf5e69abc1021ec@azure.com";
		String password = "pnNfy9kZXY8mgS8";
		   
//		java.util.Properties property = System.getProperties();
		property.put("mail.transport.protocol", "smtp");
		property.put("mail.smtp.host", "smtp.sendgrid.net");
		property.put("mail.smtp.port", 587);
		property.put("mail.smtp.auth", "true");
//		InternetAddress send = new InternetAddress("domoto02@yahoo.com.tw");
		InternetAddress receive = new InternetAddress("milovexm@hotmail.com");
		
		URLConnection urlConn = new URL("http://tw.yahoo.com").openConnection();
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
//					System.out.println(str);
					strBuild.append(str);
					break;
				}
			
			if(isTable){
				strBuild.append(str);
//				System.out.println(str);
			}
		}
//		Message message = new MimeMessage(session);
//		message.setContent(strBuild.toString(),"text/html; charset=utf-8");
//		
////		System.out.println("5");
//		message.setFrom(send);
//		message.setRecipient(Message.RecipientType.TO, receive);
//		message.setSubject("您的朋友" + memberVO.getChineseName() + "向您推薦WOW商城的商品");
////		message.setText(urlSend);
//		
//
////		message.setText("有圖片嗎");
//		Transport.send(message);
//		System.out.println("end");
////		} catch (AddressException e) {
////			System.out.println(2);
////			e.printStackTrace();
////		} catch (MessagingException e) {
////			System.out.println(2);
////			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}