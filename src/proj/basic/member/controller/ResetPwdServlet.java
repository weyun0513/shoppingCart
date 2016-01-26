package proj.basic.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ResetPWD = request.getParameter("ResetPWD");
		String conResetPWD = request.getParameter("conResetPWD");
		String resetID = request.getParameter("resetID");
		
		request.setAttribute("resetID", resetID);
		
		//密碼驗證..目前僅驗證是否空白, 無特殊規定
		if(ResetPWD == null || ResetPWD.trim().length() ==0){
			request.setAttribute("resetNullError", "密碼不得空白");
			request.getRequestDispatcher("/ResetPwdPage.jsp").forward(request, response);
			return;
		}
		if(conResetPWD == null || conResetPWD.trim().length() ==0 || !conResetPWD.equals(ResetPWD) ){
			request.setAttribute("resetError", "密碼驗證不相符");
			request.getRequestDispatcher("/ResetPwdPage.jsp").forward(request, response);
			return;
		}
		System.out.println("conResetPWD==" + conResetPWD);
		System.out.println("ResetPWD==" + ResetPWD);
		System.out.println("memberId==" + resetID);
		//開始修改密碼的動作
		new MemberService().resetPwd(conResetPWD, resetID);
		//假設成功
		request.setAttribute("resetSucc", "密碼修改成功, 請重新登入");
		request.getRequestDispatcher("/ResetPwdPage.jsp").forward(request, response);

	}
}
