package proj.basic.member.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import proj.basic.blackList.controller.BlackListService;
import proj.basic.loginRecord.model.LoginRecordDAO;
import proj.basic.loginRecord.model.LoginRecordVO;
import proj.basic.loginRecord.model.LoginService;
import proj.basic.member.model.MemberVO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Random random = new Random();
	private char[] character = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '8', '9' };

	private Color buildColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	private Color reverseColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
 
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String path = request.getContextPath(); 
		
		String action = request.getParameter("action");
		
		if (action.equals("registForm")) {
			MemberService memberService = new MemberService();
			Map<String, String> memberMap = new HashMap<String, String>();
			Map<String, String> errorMsg = new HashMap<String, String>();

			FileItemFactory fileFac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fileFac);

			InputStream in = null;
			String pwd = null;
			
			try {
				List<FileItem> fileList = upload.parseRequest(request);
				
				for (FileItem i : fileList) {

					if (i.isFormField()){
						
						if(i.getFieldName().equals("account") && (i.getString("UTF-8") == null || i.getString("UTF-8").trim().length()==0))
							errorMsg.put("accountNull", "帳號不可空白");
						if(i.getFieldName().equals("pwd") && (i.getString("UTF-8") == null || i.getString("UTF-8").trim().length()==0))
							errorMsg.put("pwdNull", "密碼不可空白");
						if(i.getFieldName().equals("chineseName") && (i.getString("UTF-8") == null || i.getString("UTF-8").trim().length()==0))
							errorMsg.put("chineseNameNull", "姓名不可空白");
						if(i.getFieldName().equals("email") && (i.getString("UTF-8") == null || i.getString("UTF-8").trim().length()==0))
							errorMsg.put("emailNull", "Email不可空白");
						
						if(i.getFieldName().equals("pwd"))
							pwd = i.getString("UTF-8");
						if(i.getFieldName().equals("pwdChk") && (!i.getString("UTF-8").equals(pwd)|| i.getString("UTF-8") == null || i.getString("UTF-8").trim().length()==0))
							errorMsg.put("pwdChkNull", "密碼比對失敗! 請重新輸入");
						
						if(errorMsg.size() != 0){
							session.setAttribute("errorMsg", errorMsg);
							response.sendRedirect("regist.jsp");
							return;
						}
						memberMap.put(i.getFieldName(), i.getString("UTF-8"));
					}
					else {
						in = i.getInputStream();
						memberService.procPhoto(in);
					}
				}

				memberService.regist(memberMap);
				String account = memberMap.get("account");
				MemberVO memberVO = memberService.getMemberVO(account);
				if(memberVO == null){//表示註冊失敗
					session.setAttribute("memberMap", memberMap);
					response.sendRedirect("regist.jsp");
					return;
				}
				
				//註冊成功
				String uri = (String)session.getAttribute("userURI");
				session.setAttribute("memberVO", memberVO);
				
				if(uri==null)
					response.sendRedirect(path + "/index.jsp");
				else
					response.sendRedirect(uri);
				
				return;

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}// End of registForm

		else if (action.equals("login")) {

			Map<String, String> errorMsg = new LinkedHashMap<String, String>();
			session.setAttribute("errorMsg", errorMsg);
			MemberService memberService = new MemberService();
			
			String ip = request.getRemoteAddr();
			String account = request.getParameter("account");//取得使用者輸入的帳號
			String pwd = request.getParameter("pwd");
			String verifyText = request.getParameter("verifyText");
			int maxErrorTimes = 3;
			int maxIPErrorTimes = 5;
			Map<String, Integer> ipMap = new HashMap<String, Integer>();
			Integer count = 0;//針對同帳號連續錯誤次數
			Integer countForIP = 0;//針對同個IP記錄
			
			ServletContext cntxt = getServletContext();
			
			if(cntxt.getAttribute("ipMap") == null)
				cntxt.setAttribute("ipMap", ipMap);
			else
				ipMap = (Map<String, Integer>)cntxt.getAttribute("ipMap");
			
			if(ipMap.get(ip) != null)
				countForIP = ipMap.get(ip);
			
			ipMap.put(ip, countForIP);
			
			
			
			String verifyCode_cor = String.valueOf(session.getAttribute("verifyCode"));//正確驗證碼

			if(account==null || account.trim().length()==0)
				errorMsg.put("accountEmpty", "帳號不得空白");
			if(pwd==null || pwd.trim().length()==0)
				errorMsg.put("pwdEmpty", "請輸入密碼");
			if(verifyText==null || verifyText.trim().length()==0)
				errorMsg.put("verifyCodeEmpty", "請輸入驗證碼");
			if(errorMsg.size()!=0){
				response.sendRedirect("login.jsp");//回到自己
				session.setAttribute("account", account);//將輸入的帳號存入session中, 以讓回到前一頁面可不用重新輸入帳號
				return;
			}
			
			//檢查驗證碼是否正確
			if (!verifyCode_cor.equals(request.getParameter("verifyText").toUpperCase())) {//若驗證碼錯誤
				session.setAttribute("account", account);
				errorMsg.put("verifyError", "驗證碼輸入錯誤");
				session.setAttribute("account", account);//將輸入的帳號存入session中, 以讓回到前一頁面可不用重新輸入帳號
				response.sendRedirect("login.jsp");
				return;
			}
			//將使用者輸入的帳號密碼與資料庫作比對, 帳號存在且密碼正確才會回傳memberVO
			MemberVO memberVO = memberService.login(account, pwd);
			//先準備將登入紀錄寫進資料庫
//			LoginRecordDAO loginDao = new LoginRecordDAO();
			LoginService loginSrv = new LoginService(); 
			LoginRecordVO loginRecord = new LoginRecordVO();
			
			if (memberVO != null) {//帳號密碼正確
				if(memberVO.getIsBlock() != 1){//帳號密碼正確但若五分鐘內已被鎖定
					//寫入登入紀錄table
					loginRecord.setMemberID(memberVO.getMemberID());
					loginRecord.setAccount(memberVO.getAccount());
					loginRecord.setIp(ip);
					loginRecord.setLoginTime(new java.sql.Timestamp(new java.util.Date().getTime()));
					loginRecord.setLoginMsg("密碼正確, 目前因帳號被鎖定無法登入");
					
					loginSrv.insert(loginRecord);
					
					errorMsg.put("isBlack", "帳號異常, 請稍候再試!");
					response.sendRedirect("login.jsp");
					return;
				}
				//將錯誤次數歸零
				count = 0;
				memberService.updateErrorNum(memberVO, count);
//				memberService.chgBlockStatus(memberVO, 1);//成功要將狀態改為1, 可登入==>應該是要時間到郵機器人操作才對!
				session.setAttribute("memberVO", memberVO);//轉移頁面要用memberVO.account取得帳號
				session.removeAttribute("errorMsg");
				
				//寫入登入紀錄table
				loginRecord.setMemberID(memberVO.getMemberID());
				loginRecord.setAccount(memberVO.getAccount());
				loginRecord.setIp(ip);
				loginRecord.setLoginTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				loginRecord.setLoginMsg("登入成功");
				loginSrv.insert(loginRecord);
				
				String uri = (String)session.getAttribute("userURI");
				if(uri==null)
					response.sendRedirect(path + "/index.jsp");
				else
					response.sendRedirect(uri);
				
				return;
				
			}//帳號密碼登入成功結束, 開始判別不成功的狀況
			
			//同個IP不論帳號是否重覆輸入 / 存在, 錯誤次數都計算一次
			countForIP++;
			ipMap.put(ip, countForIP);

			//判斷輸入的帳號是否存在
			MemberVO mVOaccount = memberService.getMemberVO(account);
			
			//若帳號存在
			if(mVOaccount !=null){
				count = mVOaccount.getWrongtimes() + 1;
				loginRecord.setMemberID(mVOaccount.getMemberID());
				loginRecord.setAccount(mVOaccount.getAccount());
				loginRecord.setIp(ip);
				loginRecord.setLoginTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				loginRecord.setLoginMsg("密碼錯誤, 登入失敗");
				
				loginSrv.insert(loginRecord);
			
			//更新錯誤次數至資料庫
			if(count < maxErrorTimes)
				memberService.updateErrorNum(mVOaccount, count);
			
			//若本次是第三次錯誤, 將帳號的狀態改為不可登入(2)
			if(count == maxErrorTimes){
				memberService.updateErrorNum(mVOaccount, count);
				memberService.chgBlockStatus(mVOaccount, 2);
				new BlackListService().addBlackListFromAccount(mVOaccount, ip);
				
				errorMsg.put("isBlack", "帳號異常, 請稍候再試!");
				response.sendRedirect("login.jsp");
				return;
			}
			
			//已被鎖定但使用者還是嘗試登入動作
			if(count > maxErrorTimes){
				loginRecord.setMemberID(mVOaccount.getMemberID());
				loginRecord.setAccount(mVOaccount.getAccount());
				loginRecord.setIp(ip);
				loginRecord.setLoginTime(new java.sql.Timestamp(new java.util.Date().getTime()));
				loginRecord.setLoginMsg("帳號已被鎖定使用者持續登入.. ");
				
				loginSrv.insert(loginRecord);
				
				errorMsg.put("isBlack", "帳號異常, 請稍候再試!");
				response.sendRedirect("login.jsp");
				return;
				}
			}
			//若是連續重複登入=>攻擊, 加入黑名單
			if(countForIP >= maxIPErrorTimes){//未來要設定/*作filter, 檢查若是黑名單中的ip則顯是錯誤訊息
				errorMsg.clear();
				errorMsg.put("IPligonError", "連續登入錯誤!");
				new BlackListService().addBlackListFromIP(ip);
				session.setAttribute("account", account);//==>需要嗎?
				response.sendRedirect("login.jsp");//回到自己
				return;
			}				
				
			errorMsg.put("accountError", "帳號或密碼錯誤!");
		
			session.setAttribute("account", account);
			response.sendRedirect("login.jsp");//回到自己
			return;
				
		}// End of login

		if (action.equals("verifyImg")) {
			response.setContentType("image/jpeg");
			StringBuffer verifyCode = new StringBuffer();

			for (int i = 0; i < 6; i++) {
				verifyCode.append(character[random.nextInt(character.length)]);
			}
			session.setAttribute("verifyCode", verifyCode);

			int width = 120, height = 30;

			Color c = buildColor();
			Color reverseC = reverseColor(c);
			BufferedImage imgBuf = new BufferedImage(width, height,	BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphic = imgBuf.createGraphics();
			graphic.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

			graphic.setColor(c);
			graphic.fillRect(0, 0, width, height);

			graphic.setColor(reverseC);
			graphic.drawString(verifyCode.toString(), 18, 20);

			System.out.println(verifyCode.toString());
			
			for (int i = 0, n = random.nextInt(100); i < n; i++) {
				graphic.drawRect(random.nextInt(width), random.nextInt(height),1, 1);
			}

			ServletOutputStream out = response.getOutputStream();

			JPEGImageEncoder imgEncoder = JPEGCodec.createJPEGEncoder(out);

			imgEncoder.encode(imgBuf);

			out.flush();

		}//End of verifyImg
		
		if(action.equals("memberUpdate")){
			MemberService memberService = new MemberService();
			Map<String, String> memberMap = new HashMap<String, String>();
			
			FileItemFactory fileFac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fileFac);

			InputStream in = null;

			try {
				List<FileItem> fileList = upload.parseRequest(request);
				for (FileItem i : fileList) {

					if (i.isFormField()){
						memberMap.put(i.getFieldName(), i.getString("UTF-8"));
					}
					else if(!i.isFormField() && i.getSize()!=0){
						in = i.getInputStream();
						memberService.procPhoto(in);
					}
				}

				MemberVO memberVO_update = memberService.update(memberMap, in);
				
				//將session裡面的資料更新
				session.setAttribute("memberVO", memberVO_update);
				
				//return要改成轉向頁面(顯示訊息)
				response.sendRedirect(path + "/index.jsp");

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}// End of update
		
	}// End of doPost
}

