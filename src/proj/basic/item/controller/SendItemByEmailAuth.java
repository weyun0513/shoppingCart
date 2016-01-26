package proj.basic.item.controller;

import javax.mail.PasswordAuthentication;

public class SendItemByEmailAuth extends javax.mail.Authenticator{
	private static final String SMTP_AUTH_USER = "azure_8577427aa2856a934cf5e69abc1021ec@azure.com";
	private static final String SMTP_AUTH_PWD = "pnNfy9kZXY8mgS8";
	
	
	public PasswordAuthentication getPasswordAuthentication() {
		   String username = SMTP_AUTH_USER;
		   String password = SMTP_AUTH_PWD;
		   return new PasswordAuthentication(username, password);
	}

}
