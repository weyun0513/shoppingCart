package proj.basic.orderList.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import proj.basic.item.model.ItemDAO;
import proj.basic.item.model.ItemVO;
import proj.basic.member.model.MemberDAO;
import proj.basic.member.model.MemberVO;
import proj.basic.orderDetail.model.OrderDetailVO;
import proj.basic.orderList.model.OrderListVO;

public class CfmOrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		
		Map<String, OrderDetailVO> cart =  (Map<String, OrderDetailVO>) session.getAttribute("cartList");
		
		/**2014/07/30 Huang 增加結帳按鈕按下去時做的第一次判斷****************************************************************/
		/************************第一次進入結帳畫面*******************************/
		String action = request.getParameter("action");
		if(action == null){
			Map<String, String> itemQtyError = new TreeMap<String, String>();
			request.setAttribute("itemQtyError", itemQtyError);
			
			/**結帳前要先確定庫存是否足夠*/
			ItemDAO itemDAO;
			try {
				itemDAO = new ItemDAO();
				request.setAttribute("itemQtyError", itemQtyError);
				Iterator<OrderDetailVO> iterator = cart.values().iterator();
			
				while(iterator.hasNext()){
					OrderDetailVO vo = iterator.next();
					ItemVO itemVO = itemDAO.findByPrimaryKey(vo.getItemNo());
					if(itemVO.getItemsQty() < vo.getQuantity()){
						itemQtyError.put(vo.getItemNo().toString() , "品號:" + (vo.getItemNo() + "\t 品名:" + vo.getItemName() + "\t銷售一空" ));
						iterator.remove();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			/**資料錯誤導回原畫面*/
			if(!itemQtyError.isEmpty()){
				RequestDispatcher rd = request.getRequestDispatcher("/confirmOrder.jsp");
				rd.forward(request, response);
				return;
			}
			
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/confirmOrder.jsp"));
			return;
			
//			int totalPrice = service.calculateTotalPrice(orderDetailVOs);
//			
//			orderListVO = orderListVO == null ? new OrderListVO() : orderListVO;
//			orderListVO.setTotalPrice(totalPrice);
//			session.setAttribute("orderListVO", orderListVO);
//			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() +"/front/member/checkout.jsp"));
//			return;
		}
		/************************ 結束第一次進入結帳畫面*******************************/
		/**結束2014/07/30 Huang 增加結帳按鈕按下去時做的第一次判斷*******************************************/
		
		
		
		
		/**2014/07/31 Huang 增加結帳按鈕按下去時做的第2次判斷*******************************************/
		/******************************第二次確認結帳*********************************/
		//訂單主檔
				
		OrderListVO orderListVO = new OrderListVO();
		
		if(action != null && action.equalsIgnoreCase("checkagain")){
			
			String payWay = request.getParameter("payWay");
			String invoiceIncNo = request.getParameter("invoiceIncNo");

			Map<String, String> checkOutError = new HashMap<String, String>();
			Map<String, String> itemQtyError = new TreeMap<String, String>();
			request.setAttribute("checkOutError", checkOutError);
			request.setAttribute("itemQtyError", itemQtyError);
			
			
			/*************************訂單輸入錯誤處理*****************************/
			if (payWay == null || payWay.trim().length() <= 0)
				checkOutError.put("payWay", "須選擇付款方式");
			
			if (invoiceIncNo != null && invoiceIncNo.trim().length() > 0) {
				try {
					Integer.valueOf(invoiceIncNo);
				} catch (Exception ex) {
					checkOutError.put("invoiceIncNo", "統一編號需為數字");
					ex.printStackTrace();
				}
			}
			
			/**結帳前要先確定庫存是否足夠*/
			ItemDAO itemDAO;
			try {
				itemDAO = new ItemDAO();
				request.setAttribute("itemQtyError", itemQtyError);
				Iterator<OrderDetailVO> iterator = cart.values().iterator();
			
				while(iterator.hasNext()){
					OrderDetailVO vo = iterator.next();
					ItemVO itemVO = itemDAO.findByPrimaryKey(vo.getItemNo());
					if(itemVO.getItemsQty() < vo.getQuantity()){
						itemQtyError.put(vo.getItemNo().toString() , "品號:" + (vo.getItemNo() + "\t 品名:" + vo.getItemName() + "\t銷售一空" ));
						iterator.remove();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			/**資料錯誤導回原畫面*/
			if(!itemQtyError.isEmpty()||!checkOutError.isEmpty()){
				RequestDispatcher rd = request.getRequestDispatcher("/confirmOrder.jsp");
				rd.forward(request, response);
				return;
			}
			
			/**計算紅利金及儲值金，扣減掉後的帳單金額*/
			String bonusStr = request.getParameter("bonus");
			String depositStr = request.getParameter("deposit");
			String invoiceTitle = null;
			String shippingAddr = null;
			String shipppingRateStr = null;
			Double shipppingRate = null;
			shipppingRateStr = request.getParameter("shipppingRate");
			CfmOrderListService service = new CfmOrderListService();
			
			Integer bonus = bonusStr != null ? Integer.valueOf(bonusStr.trim()) : Integer.valueOf(0);
			Integer deposit = depositStr != null ? Integer.valueOf(depositStr.trim()) : Integer.valueOf(0);
			
			/**計算最終帳單金額*/
			Iterator<OrderDetailVO> iterator = cart.values().iterator();
			int finalInt = service.calculateFinalPrice(iterator, bonusStr, depositStr);
			/**商業邏輯暫時定為 訂單最終金額 大於1000元就可以 1000元 換算獲得一點*/
			Integer receiveBonus = finalInt > 1000 ? finalInt / 1000 : 0 ;
			System.out.println("receiveBonus" + receiveBonus);
			
			/**2014/8/5 修改商業邏輯 還要紅利及儲值金還要扣減*/
			memberVO.setBonus(memberVO.getBonus() - bonus + receiveBonus);
			memberVO.setDeposit(memberVO.getDeposit() - deposit);
			
			try {
				MemberDAO memberDAO = new MemberDAO();
				memberDAO.update(memberVO);
			} catch (NamingException e1) {
				e1.printStackTrace();
			}
			
			invoiceIncNo = invoiceIncNo == null ? "" : invoiceIncNo ;
			invoiceTitle = invoiceTitle == null ? "" : invoiceTitle ;
			shippingAddr = shippingAddr == null ? "" : shippingAddr ;
			shipppingRateStr = shipppingRateStr == null ? "" : shipppingRateStr ;
			shipppingRateStr = shipppingRateStr.equalsIgnoreCase("免運費") ? "0.0" : shipppingRateStr;
			payWay = payWay == null ? "" : payWay ;
			
			try {
				shipppingRate = Double.valueOf(shipppingRateStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			orderListVO.setMemberID(memberVO.getMemberID());
			orderListVO.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis()));
			orderListVO.setMemberID(memberVO.getMemberID());
			orderListVO.setInvoiceIncNo(invoiceIncNo);
			orderListVO.setInvoiceTitle(invoiceTitle);
			orderListVO.setShippingAddr(shippingAddr);
			orderListVO.setShippingRate(shipppingRate);
			orderListVO.setPayWay(payWay);
			orderListVO.setShippingDate(null);
			orderListVO.setReceiveDate(null);
			orderListVO.setOrderStatus("process");
			orderListVO.setInvoiceNo(((Integer)((int)Math.random()*100000)).toString());
			orderListVO.setUseDeposit(deposit);
			orderListVO.setUseBonus(bonus);
			orderListVO.setReceiveBonus(receiveBonus);
			orderListVO.setTotalPrice(finalInt);
			
			
			List<Integer> emtyItemNo = service.makeOrder(orderListVO, cart, memberVO);
			List<String> orderErrorList = new ArrayList<String>();
			if(emtyItemNo.size()!=0){
				//Map<String, OrderDetailVO> cart2 =  (Map<String, OrderDetailVO>) session.getAttribute("cartList");
				for(Integer i:emtyItemNo){
					orderErrorList.add(cart.get(i.toString()).getItemName());
					cart.remove(i.toString());
				}
				session.setAttribute("orderErrorList", orderErrorList);
				session.setAttribute("cartList", cart);
				response.sendRedirect("confirmOrder.jsp");
			}else{
				session.removeAttribute("cartList");
				session.removeAttribute("cartListQty");
//				request.getRequestDispatcher("memberOrderList.jsp").forward(request, response);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/query.do?action=query_list"));
			}
			
			
			
		}
		
		/**結束2014/07/31 Huang 增加結帳按鈕按下去時做的第2次判斷*******************************************/

		
		/*if(request.getParameter("payWay").equals("0") ){
		request.setAttribute("payWayError", "請選擇付款方式");
		request.setAttribute("ShippingAddr",request.getParameter("shippingAddr"));
		
		if(request.getParameter("invoiceIncNo") != null && request.getParameter("invoiceIncNo").trim().length() != 0 )
			request.setAttribute("invoiceIncNo",request.getParameter("invoiceIncNo"));
		
		if(request.getParameter("invoiceTitle") != null && request.getParameter("invoiceTitle").trim().length() != 0 )
			request.setAttribute("invoiceTitle",request.getParameter("invoiceTitle"));
		
		request.setAttribute("shippingRate", request.getParameter("shippingRate"));
//		request.setAttribute("totalPrice", request.getParameter("totalPrice"));
		
		request.getRequestDispatcher("confirmOrder.jsp").forward(request, response);
		return;
	}
		orderListVO.setMemberID(memberVO.getMemberID());
		orderListVO.setShippingAddr(request.getParameter("shippingAddr"));
		if(request.getParameter("invoiceIncNo") == null || request.getParameter("invoiceIncNo").trim().length() == 0 )
			orderListVO.setInvoiceIncNo(" ");
		else
			orderListVO.setInvoiceIncNo(request.getParameter("invoiceIncNo"));
		
		if(request.getParameter("invoiceTitle") == null || request.getParameter("invoiceTitle").trim().length() == 0 )
			orderListVO.setInvoiceTitle(" ");
		else
			orderListVO.setInvoiceIncNo(request.getParameter("invoiceTitle"));
		
		orderListVO.setTotalPrice(Integer.valueOf(request.getParameter("totalPrice")));
		orderListVO.setShippingRate(Double.valueOf(request.getParameter("shippingRate")));
		orderListVO.setPayWay(request.getParameter("payWay"));
		orderListVO.setOrderStatus("process");
		
		CfmOrderListService cfmOrderSrv = new CfmOrderListService();
		List<Integer> emtyItemNo = cfmOrderSrv.makeOrder(orderListVO, (Map<String, OrderDetailVO>) session.getAttribute("cartList"), memberVO);
		List<String> orderErrorList = new ArrayList<String>();
		if(emtyItemNo.size()!=0){
			Map<String, OrderDetailVO> cart =  (Map<String, OrderDetailVO>) session.getAttribute("cartList");
			for(Integer i:emtyItemNo){
				orderErrorList.add(cart.get(i.toString()).getItemName());
				cart.remove(i.toString());
			}
			session.setAttribute("orderErrorList", orderErrorList);
			session.setAttribute("cartList", cart);
			response.sendRedirect("confirmOrder.jsp");
		}else{
			session.removeAttribute("cartList");
			session.removeAttribute("cartListQty");
//			request.getRequestDispatcher("memberOrderList.jsp").forward(request, response);
			response.sendRedirect("memberOrderList.jsp");
		}*/
	}

}
