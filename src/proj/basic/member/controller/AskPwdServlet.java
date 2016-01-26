package proj.basic.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proj.basic.member.model.MemberVO;


public class AskPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String webPath = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/"));
		String rqAccount = request.getParameter("RQaccount");
		String rqMail = request.getParameter("RQmail");
		Map<String, String> rqErrorMsg = new LinkedHashMap<String, String>();
		request.setAttribute("rqErrorMsg", rqErrorMsg);
		
		if(rqAccount == null || rqAccount.trim().length() ==0 )
			rqErrorMsg.put("rqAccountNull","請輸入帳號");
		if(rqMail == null || rqMail.trim().length() ==0 )
			rqErrorMsg.put("rqMailNull","請輸入e-Mail");
		if(rqErrorMsg.size()!=0){
			request.getRequestDispatcher("askPwdPage.jsp").forward(request, response);
			return;
		}
		MemberService mSrv = new MemberService();
		
		MemberVO memberVO = mSrv.getMemberVO(rqAccount);
		
		if(memberVO == null){
			rqErrorMsg.put("rqAccountError","請輸入正確帳號");
			request.getRequestDispatcher("askPwdPage.jsp").forward(request, response);
			return;
		}
		
		if(!memberVO.getEmail().equals(rqMail)){
			rqErrorMsg.put("rqMailError","e-Mail驗證錯誤! 請輸入會員正確e-Mail");
			request.getRequestDispatcher("askPwdPage.jsp").forward(request, response);
			return;
		}
		//到此段代表會員帳號及mail正確, 可以去呼叫發送mail的服務了
		mSrv.RQresetMail(webPath, memberVO);
		
		request.setAttribute("rqsucc","請至信箱點選確認信函中的連結重設密碼");
		request.getRequestDispatcher("askPwdPage.jsp").forward(request, response);
	}

}
