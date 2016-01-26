package proj.basic.orderDetail.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;








import proj.basic.evaluate.controller.EvaluateService_Wu;
import proj.basic.evaluate.model.EvaluateService;
import proj.basic.member.model.MemberVO;
import proj.basic.orderDetail.model.OrderDetailDAO;
import proj.basic.orderDetail.model.OrderDetailVO;
import proj.basic.orderList.controller.CfmOrderListService;
import proj.basic.orderList.model.OrderListDAO;
import proj.basic.orderList.model.OrderListVO;

//filter要檢查登入
public class ShowOrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**  2014/07/31 Huang 增加訂單細項查詢*****************************************************************/
		MemberVO memberVO = null;
		List<Integer> itemNoList = null;
		List<OrderDetailVO> orderDetailVOs = null;
		List<OrderListVO> orderListList = null;
		String action = null;
		String orderNo = null;
		String orderStatus = null;
		action = request.getParameter("action");
		orderNo = request.getParameter("orderNo");
		orderStatus = request.getParameter("orderStatus");
		
		HttpSession session = request.getSession();
		memberVO = (MemberVO) session.getAttribute("memberVO");
		OrderListDAO orderListDAO;
		try {
			orderListDAO = new OrderListDAO();
			orderListList = orderListDAO.getMemberAllOrders(memberVO);
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		
		if(orderListList.isEmpty()){
			request.setAttribute("noneOrder", "查無訂單");
			RequestDispatcher rd = request.getRequestDispatcher("orderDetail.jsp");
			rd.forward(request, response);
			return;
		}
		
		CfmOrderListService cfmOrderListService = new CfmOrderListService();
		/***************************查詢orderList主檔****************************/
		if(action != null && action.equalsIgnoreCase("query_list")){
			
			for(OrderListVO vo :orderListList) //轉換OrderStatus中英文
				vo.setOrderStatus(cfmOrderListService.translateOrderStatus(vo.getOrderStatus()));
			
			request.setAttribute("orderListList", orderListList);
			RequestDispatcher rd = request.getRequestDispatcher("orderDetail.jsp");
			rd.forward(request, response);
			return;
		}
		//showOrderDetail?orderNo=641&orderStatus=process
		/***************************查詢OrderDetail項目********************/
		//if(action != null && action.equalsIgnoreCase("query_detail")){
			
			/**用於判斷itemno 是否符合 orderDetail 裡面評價過的item*/
			EvaluateService service = new EvaluateService();
			itemNoList = service.whichEvaluated(Integer.valueOf(orderNo), memberVO.getMemberID());
			request.setAttribute("itemNoList", itemNoList);
			
			OrderDetailDAO orderDetailDAO;
			try {
				orderDetailDAO = new OrderDetailDAO();
				orderDetailVOs = orderDetailDAO.getAOrderDetail(Integer.valueOf(orderNo));
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
			/**這裡放session，是為了讓EvaluateServlet評價後的跳轉連結有orderStatus參數可取出*/
			session.setAttribute("orderStatus", orderStatus);
			request.setAttribute("orderDetailVOs", orderDetailVOs);
			RequestDispatcher rd = request.getRequestDispatcher("orderDetail.jsp");
			rd.forward(request, response);
			return;
		//}
		
		/** 結束 2014/07/31 Huang 增加訂單細項查詢*****************************************************************/

		
		/*Integer orderNo = Integer.valueOf(request.getParameter("orderNo"));
		String orderStatus = request.getParameter("orderStatus");
		
		List<OrderDetailVO> orderDetailList = new ShowOrderDetailService().getAOrderDetail(orderNo);
		request.setAttribute("orderDetailList", orderDetailList);
		request.setAttribute("orderStatus", orderStatus);
		
		if(orderStatus.equals("arrival")){
		Map<Integer , Boolean> evaChk = new LinkedHashMap<Integer , Boolean>();
		EvaluateService eService = new EvaluateService();
		Boolean e = false;
			for(OrderDetailVO o:orderDetailList){
				e = eService.chkEorNot(orderNo, (MemberVO)(request.getSession().getAttribute("memberVO")), o.getItemNo());
				evaChk.put(o.getItemNo(), e);
				}
			request.setAttribute("evaChk", evaChk);
		}
		
		request.getRequestDispatcher("orderDetail.jsp").forward(request, response);*/
	}

}
