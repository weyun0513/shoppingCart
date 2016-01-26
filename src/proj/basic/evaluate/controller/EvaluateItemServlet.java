package proj.basic.evaluate.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.evaluate.model.EvaluateVO;
import proj.basic.member.model.MemberVO;

//filter要檢查登入
public class EvaluateItemServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer orderNo = Integer.valueOf(request.getParameter("orderNo"));
		Integer itemNo = Integer.valueOf(request.getParameter("itemNo"));
		Integer evaluateStar = Integer.valueOf(request.getParameter("evaluateStar"));
		String evaluateBody = request.getParameter("evaluateBody");
		System.out.println("evaluateBody" + evaluateBody);
		
		MemberVO memberVO = (MemberVO)(((HttpSession)request.getSession()).getAttribute("memberVO"));
		
		EvaluateVO eVO = new EvaluateVO();
		eVO.setOrderNo(orderNo);
		eVO.setMemberID(memberVO.getMemberID());
		eVO.setItemNo(itemNo);
		eVO.setEvaluateStar(evaluateStar);
		eVO.setEvaluateBody(evaluateBody);
		
		new EvaluateService_Wu().doEvaluate(eVO);
		response.sendRedirect(response.encodeRedirectURL("/Proj/orderDetail.jsp"));
		
//		if(evaluateStar == null){
//			request.setAttribute("starNull", "請選擇滿意度");
//			request.getRequestDispatcher("").forward(request, response);
//			return;
//		}
//		
//		//照理來說不會用到這段...
//		if(request.getParameter("itemStatus").equals("shipping")||request.getParameter("itemStatus").equals("process")){
//			request.setAttribute("couldntRefund", "訂單狀態為 \"" + request.getParameter("itemStatus") + " \"時不得退貨");
//			request.getRequestDispatcher("").forward(request, response);
//			return;
//		}
//		
//		//照理來說不會用到這段..
//		if(request.getParameter("itemStatus").equals("Refunded")){
//			request.setAttribute("refundOK", "退款已完成");
//			request.getRequestDispatcher("").forward(request, response);
//			return;
//		}
	}
}
