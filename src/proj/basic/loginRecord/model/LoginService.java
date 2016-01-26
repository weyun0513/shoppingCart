package proj.basic.loginRecord.model;

import javax.naming.NamingException;

public class LoginService {
	LoginRecordDAO loginDao;
public void insert(LoginRecordVO loginRecord){
	try {
		loginDao = new LoginRecordDAO();
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
