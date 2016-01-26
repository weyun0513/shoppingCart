package proj.basic.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proj.basic.member.model.MemberVO;


public class VerifyResetLinkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberVO memberVO =  new MemberService().verifyResetMail(request.getParameter("reid"), request.getParameter("t"), request.getParameter("vCode"));
		if(memberVO == null)
			request.setAttribute("resetVerifyError", "連結驗證錯誤! 請重新點選連結或是重新申請寄發mail");
		else
			request.setAttribute("memberVO", memberVO);
		
		request.getSession().setAttribute("resetID", memberVO.getMemberID());
		request.getRequestDispatcher("ResetPwdPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
