package proj.basic.item.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.item.model.ItemVO;
import proj.basic.itemClass.model.ItemClassVO;

public class ShowItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int classNo = Integer.parseInt(request.getParameter("classNo"));
		
		ShowItemsService show = new ShowItemsService();
		List<ItemVO> items  = show.showItem(classNo, pageNo);
		int totalPage = 0;
		if(items.size() == 0){
			items = show.showClassItem(classNo, pageNo);
			
		}else{
			totalPage = show.itemCount(classNo);
		}
		request.setAttribute("classNo", classNo);
		request.setAttribute("items", items);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("totalPage", totalPage);
		
		RequestDispatcher rd = request.getRequestDispatcher("/03_mall/itemPage.jsp");
		rd.forward(request, response);
		
	}

}
