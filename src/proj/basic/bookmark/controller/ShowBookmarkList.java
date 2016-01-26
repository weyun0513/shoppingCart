package proj.basic.bookmark.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.bookmark.model.BookmarkVO;
import proj.basic.item.model.ItemVO;
import proj.basic.member.model.MemberVO;

public class ShowBookmarkList extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("memberVO") == null){
			return;
		}
		
		List<ItemVO> bookmarkList = new BookmarkService().getAllBookmark((MemberVO)session.getAttribute("memberVO")); 
		request.setAttribute("bookmarkList", bookmarkList); 
		
		request.getRequestDispatcher("memberFavor.jsp").forward(request, response);
	}

}
