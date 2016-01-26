package proj.basic.member.controller;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import proj.basic.loginRecord.model.LoginRecordDAO;
import proj.basic.loginRecord.model.LoginRecordVO;
import proj.basic.loginRecord.model.LoginService;
import proj.basic.member.model.MemberVO;


public class MemberLogInOutListener implements HttpSessionAttributeListener, Serializable {

   
	private static final long serialVersionUID = 1L;

	
	@Override
	public void attributeAdded(HttpSessionBindingEvent addAttr) {
		//其實login成功後會setAttribute, 寫入資料庫的動作也許可以移來這裡?
    	HttpSession session = addAttr.getSession();
    	if(addAttr.getName().equals("memberVO")){
    		System.out.println(((MemberVO)session.getAttribute("memberVO")).getChineseName() + "登入成功");
    	}
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent delAttr) {
		HttpSession session = delAttr.getSession();
    	if(delAttr.getName().equals("memberVO")){
    		System.out.println("account === "+((MemberVO)delAttr.getValue()).getAccount() + "登出了");
    		LoginService loginSrv = new LoginService(); 
//    		LoginRecordDAO loginDao = new LoginRecordDAO();
    		LoginRecordVO loginRecordVO = new LoginRecordVO();
    		loginRecordVO.setAccount(((MemberVO)delAttr.getValue()).getAccount());
    		loginRecordVO.setLoginMsg("登出");
    		loginRecordVO.setIp("");//想辦法取得IP
    		loginRecordVO.setLoginTime(new Timestamp(new java.util.Date().getTime()));
    		loginRecordVO.setMemberID(((MemberVO)delAttr.getValue()).getMemberID());
    		loginSrv.insert(loginRecordVO);
    	}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
