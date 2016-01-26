package proj.basic.bookmark.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.member.model.MemberVO;


public class BookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	//AJAX
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		
		request.getSession().setAttribute("refererPage", request.getHeader("referer"));
		
		PrintWriter out = response.getWriter();
		String returnMsg = null;
		
		if(request.getSession().getAttribute("memberVO") == null){
			returnMsg = "請先登入";
			out.write(returnMsg);
			out.close();	
			return;
		}
		
		Integer itemNo = Integer.valueOf(request.getParameter("itemNo"));
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		BookmarkService bkSrv = new BookmarkService();
		int errorCode = bkSrv.addBookmark(memberVO, itemNo);
		
		if(errorCode == 2627){
			returnMsg = "商品已收藏在我的最愛";
		}else
			returnMsg = "商品成功加到我的最愛";
		
		out.write(returnMsg);
		out.close();	
		return;
	}

	//servlet
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		button點選直接呼叫servlet的方法
//		//用filter檢查是否登入
//		//登入才會進到此servlet
//		Integer itemNo = Integer.valueOf(request.getParameter("itemNo"));
//		HttpSession session = request.getSession();
//		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
//		BookmarkService bkSrv = new BookmarkService();
//		int errorCode = bkSrv.addBookmark(memberVO, itemNo);
//		if(errorCode == 2627){
//			//重複加入, 可顯示訊息給使用者看?
//			
//		}
//		response.sendRedirect(response.encodeRedirectURL(request.getHeader("referer")));
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		
		request.getSession().setAttribute("refererPage", request.getHeader("referer"));
		
		PrintWriter out = response.getWriter();
		StringBuffer returnMsg = new StringBuffer();
		
		if(request.getSession().getAttribute("memberVO") == null){
			returnMsg.append("請先登入");
			out.write(returnMsg.toString());
			out.close();	
			return;
		}
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		
		String[] itemArray = request.getParameter("itemArray").split(",");
		BookmarkService bkSrv = new BookmarkService();
		Integer itemNo = null;
//		int errorCode = 0;
		
		for(String itemsNo:itemArray){
			itemNo = Integer.valueOf(itemsNo);
			bkSrv.addBookmark(memberVO, itemNo);
//			errorCode = bkSrv.addBookmark(memberVO, itemNo);
//			if(errorCode==2627)
//				returnMsg.append(itemsNo + "商品已收藏在我的最愛");
//			else
//				returnMsg.append(itemsNo + "商品成功加到我的最愛");
		}
		returnMsg.append("商品成功加到我的最愛");
		out.write(returnMsg.toString());
		out.close();	
		return;
			
	}

}
