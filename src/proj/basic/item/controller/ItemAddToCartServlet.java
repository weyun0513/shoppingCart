package proj.basic.item.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.item.model.ItemDAO;
import proj.basic.item.model.ItemVO;
import proj.basic.orderDetail.model.OrderDetailVO;

public class ItemAddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Map<String, OrderDetailVO> cartList = new LinkedHashMap<String, OrderDetailVO>();
		OrderDetailVO orderDetailVO = null;
		
		String path = request.getContextPath();
		
		if (request.getParameter("action") == null) {
			if (session.getAttribute("cartList") == null)
				session.setAttribute("cartList", cartList);
			else
				cartList = (Map<String, OrderDetailVO>) session.getAttribute("cartList");

			if (cartList.get(request.getParameter("itemNo")) == null) {
				orderDetailVO = new OrderDetailVO();
				orderDetailVO.setItemNo(Integer.valueOf(request	.getParameter("itemNo")));
				orderDetailVO.setItemName(request.getParameter("itemName"));
				orderDetailVO.setItemPrice(Double.valueOf(request.getParameter("price")));
				orderDetailVO.setDiscount(Double.valueOf(request.getParameter("discount")));
				orderDetailVO.setQuantity(Integer.valueOf(request.getParameter("buyQty")));
			} else {
				orderDetailVO = cartList.get(request.getParameter("itemNo"));
				orderDetailVO.setQuantity(orderDetailVO.getQuantity() + Integer.valueOf(request.getParameter("buyQty")));
			}
			cartList.put(request.getParameter("itemNo"), orderDetailVO);
		}else if(request.getParameter("action").equals("deleteCartQ")){
			cartList = (Map<String, OrderDetailVO>) session.getAttribute("cartList");
			cartList.remove(request.getParameter("itemNo"));
		}else{ // 2014/07/29Huang 這邊是執行 action = updateCartQ 的方法嗎?????  是的,這段已經以AJAX UpdateCartQAJAX 做完了，所以不會進去
			
			cartList = (Map<String, OrderDetailVO>) session.getAttribute("cartList");
			orderDetailVO = cartList.get(request.getParameter("itemNo"));
			orderDetailVO.setQuantity(Integer.valueOf(request.getParameter("buyQty")));
		}
		
		Double priceOrg = 0.0;
		Integer priceNow = 0;
		Double discountCor = 0.0;
		Iterator<OrderDetailVO> iDetail = cartList.values().iterator();
		while (iDetail.hasNext()) {
			orderDetailVO = iDetail.next();
			if(orderDetailVO.getDiscount()==0.0)
				discountCor = 1.0;
			else
				discountCor = orderDetailVO.getDiscount();
			priceOrg = orderDetailVO.getItemPrice()	* discountCor * orderDetailVO.getQuantity();
			priceNow += new Integer(priceOrg.toString().substring(0, priceOrg.toString().indexOf(".")));
		}

		session.setAttribute("cartListQty", cartList.size());
		session.setAttribute("cartListPrice", priceNow);
		
		/**2014/07/29 Huang 判斷購物車商品是否小於資料庫商品項目庫存 直接呼叫ItemVO 未透過service*/
		try {
			ItemDAO itemDAO = new ItemDAO();
			List<ItemVO> itemList = itemDAO.getAll();
			session.setAttribute("itemList", itemList);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		
		/**結束2014/07/29 Huang 判斷購物車商品是否小於資料庫商品項目庫存  直接呼叫ItemVO 未透過service*/

		response.sendRedirect(request.getHeader("referer"));

	}

}
