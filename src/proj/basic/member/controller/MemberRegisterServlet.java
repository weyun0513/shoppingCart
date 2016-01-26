package proj.basic.member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import proj.basic.member.model.MemberVO;

public class MemberRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("regist goGet被呼叫");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		
		HttpSession session = request.getSession();
		String path = request.getContextPath(); 
		MemberService memberService = new MemberService();
		Map<String, String> memberMap = new HashMap<String, String>();
		Map<String, String> errorMsg = new HashMap<String, String>();

		if( request.getParameter("passby")!=null && request.getParameter("passby").equals("ajax")){
			ServletOutputStream out = response.getOutputStream();
			
			if(request.getParameter("account")!=null && request.getParameter("account").trim().length()==0){
				out.print("please enter an account");
				return;
			}
			
			MemberVO memberVO = memberService.getMemberVO(request.getParameter("account"));
			
			if(memberVO!=null){
				out.print("this account has been used");
				return;
			}else if(request.getParameter("account")!=null && memberVO==null)
				out.print("ok");
			
			if(request.getParameter("pwd")!=null && request.getParameter("pwd").trim().length()==0){
				out.print("please enter pwd");
				return;
			}
			
			if(request.getParameter("pwdChk")!=null && (request.getParameter("pwd").trim().length()==0 || !request.getParameter("pwdChk").equals(request.getParameter("pwd")))){
				out.print("password verify error");
				return;
			}else if(request.getParameter("pwdChk")!=null && (request.getParameter("pwd").trim().length()!=0 || request.getParameter("pwdChk").equals(request.getParameter("pwd"))))
				out.print("ok");
				
			if(request.getParameter("email")!=null && request.getParameter("email").trim().length()==0){
				out.print("please enter email");
				return;
			}else if(request.getParameter("email")!=null)
				out.print("ok");
			
			
			return;
		}
		
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
			session.removeAttribute("errorMsg");
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

}
