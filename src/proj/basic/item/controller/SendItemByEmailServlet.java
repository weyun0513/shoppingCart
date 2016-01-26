package proj.basic.item.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.member.model.MemberVO;


@WebServlet("/SendItemByEmail")
public class SendItemByEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		System.out.println("servlet");
		PrintWriter out = response.getWriter();
		String returnMsg = null;
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		String addrSendTo = request.getParameter("addrSendTo");
		String urlSend = request.getHeader("referer");
		int code = new SendItemByEmailService().infoMail(memberVO, addrSendTo, urlSend);
		System.out.println(code);
		if(code == -1){
			returnMsg = "success";
		}else if(code == 1)
			returnMsg = "success";
		else
			returnMsg = "success";

		out.write(returnMsg);
		out.close();	
		return;
	}

}
