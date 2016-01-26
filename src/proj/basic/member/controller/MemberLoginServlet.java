package proj.basic.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.blackList.controller.BlackListService;
import proj.basic.loginRecord.model.LoginRecordDAO;
import proj.basic.loginRecord.model.LoginRecordVO;
import proj.basic.loginRecord.model.LoginService;
import proj.basic.member.model.MemberVO;

public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final int ALIVETIME = 10;//分鐘
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("memberVO");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html; charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String path = request.getContextPath();
		//AJAX用
//		if(request.getParameter("ajx") != null && request.getParameter("URIPage")!=null)
//			session.setAttribute("refererPage",request.getParameter("URIPage"));
		if(request.getParameter("URIPage")!=null){
			if(request.getParameter("pageData")==null){
				session.setAttribute("refererPage", request.getContextPath()+request.getParameter("URIPage"));
				return;
			}
//			path = request.getContextPath() + request.getParameter("URIPage") + "?" + request.getParameter("pageData").replace(',', '&');
//			System.out.println("path without pagedata===" + request.getContextPath() + request.getParameter("URIPage") + "?" + request.getParameter("pageData").replace(',', '&'));
			session.setAttribute("refererPage", request.getContextPath() + request.getParameter("URIPage") + "?" + request.getParameter("pageData").replace(',', '&'));
			return;
		}
		
		Map<String, String> errorMsg = new LinkedHashMap<String, String>();
		session.setAttribute("errorMsgMap", errorMsg);
		MemberService memberService = new MemberService();

		String ip = request.getRemoteAddr();
		String account = request.getParameter("account");// 取得使用者輸入的帳號
		String pwd = request.getParameter("pwd");
		String verifyText = request.getParameter("verifyText");
		int maxErrorTimes = 3;
		int maxIPErrorTimes = 5;
		Map<String, Integer> ipMap = new HashMap<String, Integer>();
		Integer count = 0;// 針對同帳號連續錯誤次數
		Integer countForIP = 0;// 針對同個IP記錄

		ServletContext cntxt = getServletContext();

		if (cntxt.getAttribute("ipMap") == null)
			cntxt.setAttribute("ipMap", ipMap);
		else
			ipMap = (Map<String, Integer>) cntxt.getAttribute("ipMap");

		if (ipMap.get(ip) != null)
			countForIP = ipMap.get(ip);

		ipMap.put(ip, countForIP);

		String verifyCode_cor = String.valueOf(session.getAttribute("verifyCode"));// 正確驗證碼

		if (account == null || account.trim().length() == 0)
			errorMsg.put("accountEmpty", "帳號不得空白");
		if (pwd == null || pwd.trim().length() == 0)
			errorMsg.put("pwdEmpty", "請輸入密碼");
		if (verifyText == null || verifyText.trim().length() == 0)
			errorMsg.put("verifyCodeEmpty", "請輸入驗證碼");
		if (errorMsg.size() != 0) {
			session.setAttribute("account", account);// 將輸入的帳號存入session中,
														// 以讓回到前一頁面可不用重新輸入帳號
			
		if(request.getParameter("ajx") != null)
			response.getWriter().print("欄位不得空白");//應該不會run到這行
		else
			response.sendRedirect(path + "/01_login/login.jsp");// 回到自己
		return;
		}
		// 檢查驗證碼是否正確
		if (!verifyCode_cor.equals(request.getParameter("verifyText").toUpperCase())) {// 若驗證碼錯誤
			session.setAttribute("account", account);
			errorMsg.put("verifyError", "驗證碼輸入錯誤");
			session.setAttribute("account", account);// 將輸入的帳號存入session中,
														// 以讓回到前一頁面可不用重新輸入帳號
			if(request.getParameter("ajx") != null)
				response.getWriter().print("驗證碼輸入錯誤");
			else
				response.sendRedirect(path + "/01_login/login.jsp");
			return;
		}
		
		// 判斷輸入的帳號是否存在
		MemberVO mVOaccount = memberService.getMemberVO(account);
		MemberVO memberVO = null;
		// 將使用者輸入的帳號密碼與資料庫作比對, 帳號存在且密碼正確才會回傳memberVO
		if(mVOaccount!=null)
			memberVO = memberService.login(account, pwd);
		
		// 先準備將登入紀錄寫進資料庫
//		LoginRecordDAO loginDao = new LoginRecordDAO();
		LoginService loginSrv = new LoginService(); 
		LoginRecordVO loginRecord = new LoginRecordVO();

		if (memberVO != null) {// 帳號密碼正確
			if (memberVO.getIsBlock() != 1) {// 帳號密碼正確但若五分鐘內已被鎖定
				// 寫入登入紀錄table
				loginRecord.setMemberID(memberVO.getMemberID());
				loginRecord.setAccount(memberVO.getAccount());
				loginRecord.setIp(ip);
				loginRecord.setLoginTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				loginRecord.setLoginMsg("密碼正確, 目前因帳號被鎖定無法登入");

				loginSrv.insert(loginRecord);

				errorMsg.put("isBlack", "帳號異常, 請稍候再試!");
				
				if(request.getParameter("ajx") != null)
					response.getWriter().print("帳號活動異常請稍後再試");
				else
					response.sendRedirect(path + "/01_login/login.jsp");
				return;
			}
			// 將錯誤次數歸零
			count = 0;
			memberService.updateErrorNum(memberVO, count);
			// memberService.chgBlockStatus(memberVO, 1);//成功要將狀態改為1,
			
			session.setAttribute("memberVO", memberVO);// 轉移頁面要用memberVO.account取得帳號
			session.setMaxInactiveInterval(ALIVETIME * 60);
			session.removeAttribute("errorMsg");

			// 寫入登入紀錄table
			loginRecord.setMemberID(memberVO.getMemberID());
			loginRecord.setAccount(memberVO.getAccount());
			loginRecord.setIp(ip);
			loginRecord.setLoginTime(new java.sql.Timestamp(new java.util.Date().getTime()));
			loginRecord.setLoginMsg("登入成功");
			loginSrv.insert(loginRecord);
			
			if(request.getParameter("ajx") != null){
				ServletOutputStream out = response.getOutputStream();
				out.print("success");
				out.close();
				return;
				}else if(session.getAttribute("refererPage") != null && !request.getParameter("action").equals("loginPage")){
				response.sendRedirect(response.encodeRedirectURL(session.getAttribute("refererPage").toString()));
				}else if(session.getAttribute("refererPage") != null){
					response.sendRedirect(response.encodeRedirectURL(session.getAttribute("refererPage").toString()));
					}
			else
				response.sendRedirect(response.encodeRedirectURL(path + "/index.jsp"));

			return;

		}// 帳號密碼登入成功結束, 開始判別不成功的狀況

		// 同個IP不論帳號是否重覆輸入 / 存在, 錯誤次數都計算一次
		countForIP++;
		ipMap.put(ip, countForIP);

		// 若帳號存在
		if (mVOaccount != null) {
			java.util.Date loginDate = new java.util.Date(); 
			count = mVOaccount.getWrongtimes() + 1;
			loginRecord.setMemberID(mVOaccount.getMemberID());
			loginRecord.setAccount(mVOaccount.getAccount());
			loginRecord.setIp(ip);
			loginRecord.setLoginTime(new java.sql.Timestamp(loginDate.getTime()));
			loginRecord.setLoginMsg("密碼錯誤, 登入失敗");

			loginSrv.insert(loginRecord);
			
			//在此呼叫傳送mail的方法
			memberService.infoMail(mVOaccount, loginDate);

			// 更新錯誤次數至資料庫
			if (count < maxErrorTimes)
				memberService.updateErrorNum(mVOaccount, count);

			// 若本次是第三次錯誤, 將帳號的狀態改為不可登入(2)
			if (count == maxErrorTimes) {
				memberService.updateErrorNum(mVOaccount, count);
				memberService.chgBlockStatus(mVOaccount, 2);
				new BlackListService().addBlackListFromAccount(mVOaccount, ip);

				errorMsg.put("isBlack", "帳號異常, 請稍候再試!");
				if(request.getParameter("ajx") != null)
					response.getWriter().print("帳號活動異常請稍後再試");
				else
					response.sendRedirect(path + "/01_login/login.jsp");
				return;
			}

			// 已被鎖定但使用者還是嘗試登入動作
			if (count > maxErrorTimes) {
				loginRecord.setMemberID(mVOaccount.getMemberID());
				loginRecord.setAccount(mVOaccount.getAccount());
				loginRecord.setIp(ip);
				loginRecord.setLoginTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				loginRecord.setLoginMsg("帳號已被鎖定使用者持續登入.. ");

				loginSrv.insert(loginRecord);

				errorMsg.put("isBlack", "帳號異常, 請稍候再試!");
				
				if(request.getParameter("ajx") != null)
					response.getWriter().print("帳號活動異常請稍後再試");
				else
					response.sendRedirect(path + "/01_login/login.jsp");
				return;
			}
		}
		// 若是連續重複登入=>攻擊, 加入黑名單
		if (countForIP >= maxIPErrorTimes) {// 未來要設定/*作filter,
											// 檢查若是黑名單中的ip則顯是錯誤訊息
			errorMsg.clear();
			errorMsg.put("IPligonError", "連續登入錯誤!");
			new BlackListService().addBlackListFromIP(ip);
			session.setAttribute("account", account);// ==>需要嗎?
			
			if(request.getParameter("ajx") != null)
				response.getWriter().print("IP活動異常");
			else
				response.sendRedirect(path + "/01_login/login.jsp");// 回到自己
			return;
		}

		errorMsg.put("accountError", "帳號或密碼錯誤!");

		session.setAttribute("account", account);
		
		if(request.getParameter("ajx") != null)
			response.getWriter().print("帳號或密碼錯誤");
		else
			response.sendRedirect(path + "/01_login/login.jsp");// 回到自己
		return;

	}// End of doPost
}
