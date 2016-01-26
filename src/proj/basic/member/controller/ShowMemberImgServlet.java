package proj.basic.member.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proj.basic.member.model.MemberDAO;
import proj.basic.member.model.MemberVO;

public class ShowMemberImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		OutputStream out = response.getOutputStream();
//		MemberDAO memberDao = new MemberDAO();
//		MemberVO memberVO = memberDao.findByAccount(account);
		MemberVO memberVO = new MemberService().getMemberVO(account);
		byte[] bFile = memberVO.getPhoto();
		if(bFile!=null)
			out.write(bFile);
		else
			return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
