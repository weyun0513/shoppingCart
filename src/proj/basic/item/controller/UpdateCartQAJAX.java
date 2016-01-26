package proj.basic.item.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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

/**2014/07/29 Huang新增AJAX判斷購物車修改數量*/
public class UpdateCartQAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Map<String, OrderDetailVO> cartList = new LinkedHashMap<String, OrderDetailVO>();
		OrderDetailVO orderDetailVO = null;
		
		String itemNoStr = request.getParameter("itemNo");
		cartList = (Map<String, OrderDetailVO>) session.getAttribute("cartList");
		orderDetailVO = cartList.get(itemNoStr);
		orderDetailVO.setQuantity(Integer.valueOf(request.getParameter("buyQty")));
		
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
		
		try {
			ItemDAO itemDAO = new ItemDAO();
			List<ItemVO> itemList = itemDAO.getAll();
			session.setAttribute("itemList", itemList);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		

		/*----------------------------以下是 AJAX處理過程-------------------------*/
		
		/****************存入點選的商品數量，放入購物車*******************/
		Iterator<OrderDetailVO> iDetail2 = cartList.values().iterator();
		int subTotalPrice = 0;
		while (iDetail2.hasNext()) {
			OrderDetailVO vo = iDetail2.next();
			if (vo.getItemNo().equals(Integer.valueOf(itemNoStr))) { // 對照商品編號是否符合
				Double discount = vo.getDiscount() == 0.0 ? 1.0 : vo.getDiscount();
				subTotalPrice = (int) Math.floor(vo.getItemPrice() * vo.getQuantity() * discount);
				break; // 沒有break，會產生ConcurrentModificationException
			}
		}
		System.out.println(subTotalPrice);
		
		response.setContentType("text/plain; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		JsonArrayBuilder builder = Json.createArrayBuilder();
		JsonObject obj = Json.createObjectBuilder()
				.add("itemNo", itemNoStr)
				.add("subTotalPrice", subTotalPrice)
				.add("cartListPrice", priceNow)
				.build();
		builder.add(obj);
		
		
		out.write(builder.build().toString());
		out.close();
		return;
	
	}

}
