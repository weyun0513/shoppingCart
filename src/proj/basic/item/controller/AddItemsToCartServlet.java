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


@WebServlet("/AddItemsToCartServlet")
public class AddItemsToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Map<String, OrderDetailVO> cartList = null;
		
		if (session.getAttribute("cartList") == null)
			cartList = new LinkedHashMap<String, OrderDetailVO>();
		else
			cartList = (Map<String, OrderDetailVO>) session.getAttribute("cartList");
		
		SearchItemService itemSrv = new SearchItemService();
		String[] itemsAry = request.getParameter("addItems").split(",");
		OrderDetailVO orderDetailVO = null;
		ItemVO itemVO = null;
		
		for(String s:itemsAry){
			if(cartList.get(s) != null){
				cartList.get(s).setQuantity(cartList.get(s).getQuantity()+1);
				continue;
			}
			orderDetailVO = new OrderDetailVO();
			itemVO = itemSrv.findByItemNo(Integer.valueOf(s));
			orderDetailVO.setItemNo(itemVO.getItemNo());
			orderDetailVO.setItemName(itemVO.getItemName());
			orderDetailVO.setItemPrice(itemVO.getPrice());
			orderDetailVO.setDiscount(itemVO.getDiscount());
			orderDetailVO.setQuantity(1);
			
			cartList.put(s, orderDetailVO);
		}
		session.setAttribute("cartList", cartList);
		
		//增加在這
		//response.getOutputStream().print("success");
		
		/**2014/8/6 Huang 增加JASON處理****************************************/
		
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
		session.setAttribute("cartList", cartList);
		
		/**2014/08/07 Huang 判斷購物車商品是否小於資料庫商品項目庫存 直接呼叫ItemVO 未透過service*/
		try {
			ItemDAO itemDAO = new ItemDAO();
			List<ItemVO> itemList = itemDAO.getAll();
			session.setAttribute("itemList", itemList);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		
		/**結束2014/08/07 Huang 判斷購物車商品是否小於資料庫商品項目庫存  直接呼叫ItemVO 未透過service*/
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		JsonArrayBuilder builder = Json.createArrayBuilder();
		JsonObject obj = Json.createObjectBuilder()
				.add("cartQ", cartList.size())
				.add("cartListPrice", priceNow)
				.build();
		builder.add(obj);
		
		out.write(builder.build().toString());
		out.close();
		return;
		/** 結束2014/8/6 Huang 增加JASON處理****************************************/
		
	}

}
