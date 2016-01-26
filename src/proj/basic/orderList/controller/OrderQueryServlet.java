package proj.basic.orderList.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.evaluate.model.EvaluateService;
import proj.basic.member.model.MemberVO;
import proj.basic.orderDetail.controller.ShowOrderDetailService;
import proj.basic.orderDetail.model.OrderDetailVO;
import proj.basic.orderList.model.OrderListVO;


public class OrderQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		MemberVO memberVO = null;
		List<Integer> itemNoList = null;
		List<OrderDetailVO> orderDetailVOs = null;
		List<OrderListVO> orderListList = null;
		String action = null;
		String orderNo = null;
		String orderStatus = null;
		
		//OrderService orderService = new OrderService();
		LoadMemberOrderService loadMemberOrderService = new LoadMemberOrderService();
		HttpSession session = request.getSession();
		action = request.getParameter("action");
		orderNo = request.getParameter("orderNo");
		orderStatus = request.getParameter("orderStatus");
		
		memberVO = (MemberVO) session.getAttribute("memberVO");
		
		orderListList = loadMemberOrderService.getAllOrder(memberVO);
		
		if(orderListList.isEmpty()){
			request.setAttribute("noneOrder", "查無訂單");
			RequestDispatcher rd = request.getRequestDispatcher("/memberOrderList.jsp");
			rd.forward(request, response);
			return;
		}
		
		CfmOrderListService cfmOrderListService = new CfmOrderListService();
		/***************************查詢orderList主檔****************************/
		if(action != null && action.equalsIgnoreCase("query_list")){
			
			for(OrderListVO vo :orderListList) //轉換OrderStatus中英文
				vo.setOrderStatus(cfmOrderListService.translateOrderStatus(vo.getOrderStatus()));
			
			request.setAttribute("orderListList", orderListList);
			RequestDispatcher rd = request.getRequestDispatcher("/memberOrderList.jsp");
			rd.forward(request, response);
			return;
		}
		
		/***************************查詢OrderDetail項目********************/
		if(action != null && action.equalsIgnoreCase("query_detail")){
			
			orderStatus = new String(orderStatus.getBytes("ISO-8859-1"), "UTF-8");

			
			/**用於判斷itemno 是否符合 orderDetail 裡面評價過的item*/
			EvaluateService service = new EvaluateService();
			itemNoList = service.whichEvaluated(Integer.valueOf(orderNo), memberVO.getMemberID());
			request.setAttribute("itemNoList", itemNoList);
			
			ShowOrderDetailService showOrderDetailService = new ShowOrderDetailService();
			orderDetailVOs = showOrderDetailService.getAOrderDetail(Integer.valueOf(orderNo));
			
			/**這裡放session，是為了讓EvaluateServlet評價後的跳轉連結有orderStatus參數可取出*/
			session.setAttribute("orderStatus", orderStatus);
			request.setAttribute("orderDetailVOs", orderDetailVOs);
			RequestDispatcher rd = request.getRequestDispatcher("/orderDetail.jsp");
			rd.forward(request, response);
			return;
		}
		
		
	}

}
