package proj.basic.evaluate.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import proj.basic.evaluate.model.EvaluateService;
import proj.basic.evaluate.model.EvaluateVO;
import proj.basic.member.model.MemberVO;


public class EvaluateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Logger log = Logger.getLogger(EvaluateServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderNo = null;
		String itemNo = null;
		String action = null;
		String evaluateBody = null;
		String evaluateStarStr = null;
		EvaluateVO evaluateVO = null;
		MemberVO memberVO = null;
		
		orderNo = request.getParameter("orderNo");
		itemNo = request.getParameter("itemNo");
		action = request.getParameter("action");
		
		log.debug("orderNo :" + orderNo);
		log.debug("itemNo :" + itemNo);
		log.debug("action :" + action);
		log.debug("------------");
		
		HttpSession session = request.getSession(true);
		memberVO = (MemberVO) session.getAttribute("memberVO");
		
		EvaluateService service = new EvaluateService();
		
		
		if(action == null){
			
			evaluateVO = service.findByCompositeKey(Integer.valueOf(orderNo), memberVO.getMemberID(), Integer.valueOf(itemNo));
			
			if(evaluateVO == null){ //尚未評價
				session.setAttribute("orderNo", orderNo);
				session.setAttribute("itemNo", itemNo);
				
				RequestDispatcher rd = request.getRequestDispatcher("/evaluateItem.jsp");
				rd.forward(request, response);
				return;
			}else{
				request.setAttribute("evaluated", "您以評價過此商品");
				RequestDispatcher rd = request.getRequestDispatcher("/evaluateItem.jsp");
				rd.forward(request, response);
				return;
			}
			
		}
		
		if(action != null && action.equalsIgnoreCase("evaluate")){
			
			Integer evaluateStar = null;
			Map<String, String> errorMsg = new HashMap<String, String>();
			evaluateBody = request.getParameter("evaluateBody");
			evaluateBody = new String(evaluateBody.getBytes("ISO-8859-1"), "UTF-8");
			
			System.out.println("evaluateBody" + evaluateBody);
			
			evaluateStarStr = request.getParameter("evaluateStar");
			
			/**錯誤處理*/
			if(evaluateBody == null || evaluateBody.trim().length() == 0)
				errorMsg.put("evaluateBody", "請輸入商品評語");
			
			if(evaluateStarStr == null || evaluateStarStr.trim().length() == 0)
				errorMsg.put("evaluateStar", "請評價商品滿意度");
			else{
				try {
					evaluateStar = Integer.valueOf(evaluateStarStr);
				} catch (Exception e) {
					errorMsg.put("evaluateStar", "請輸入數字1-5");
				}
			}
			
			/**轉回原頁面*/
			if(!errorMsg.isEmpty()){
				request.setAttribute("errorMsg", errorMsg);
				RequestDispatcher rd = request.getRequestDispatcher("/evaluateItem.jsp");
				rd.forward(request, response);
				return;
			}
			
			/**通過驗證，呼叫永續層**/
			evaluateVO = new EvaluateVO();
			
			evaluateVO.setEvaluateBody(evaluateBody);
			evaluateVO.setEvaluateStar(evaluateStar);
			evaluateVO.setItemNo(Integer.valueOf(itemNo));
			evaluateVO.setMemberID(memberVO.getMemberID());
			evaluateVO.setOrderNo(Integer.valueOf(orderNo));
			
			service.insert(evaluateVO);

			String orderStatus = (String) session.getAttribute("orderStatus");
			
			log.debug("orderStatus :" +orderStatus);
			log.debug("URL :" +  
					this.getServletContext().getContextPath() + 
					"/query.do?action=query_detail&orderNo=" 
					+ orderNo + "&orderStatus=" + URLEncoder.encode(orderStatus,"UTF-8"));
			
			/**跳轉回評價前畫面 呼叫/query.do*/
			response.sendRedirect(response.encodeRedirectURL( 
					this.getServletContext().getContextPath() + 
					"/query.do?action=query_detail&orderNo=" 
					+ orderNo + "&orderStatus=" + URLEncoder.encode(orderStatus,"UTF-8")));
		}
	}

}
