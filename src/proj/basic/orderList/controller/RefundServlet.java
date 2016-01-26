package proj.basic.orderList.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import proj.basic.orderList.model.OrderListDAO;
import proj.basic.orderList.model.OrderListVO;


public class RefundServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Logger log = Logger.getLogger(RefundServlet.class);

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer orderNo = null;
		OrderListDAO orderListDAO = null;
		try {
			orderListDAO = new OrderListDAO();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String orderNoStr = request.getParameter("orderNo");
		String action = request.getParameter("action");
		
		if(action != null && action.equalsIgnoreCase("refund")){
			
			try {
				orderNo = Integer.valueOf(orderNoStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			int update = orderListDAO.updateStatus(orderNo, "refund");
			
			log.debug(orderNo);
			log.debug(update);
			
			RequestDispatcher rd = request.getRequestDispatcher("/query.do?action=query_list");
			rd.forward(request, response);
			return;
		}
		
	}

}
