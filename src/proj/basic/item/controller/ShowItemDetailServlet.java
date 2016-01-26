package proj.basic.item.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.evaluate.controller.EvaluateService_Wu;
import proj.basic.evaluate.model.EvaluateVO;
import proj.basic.item.model.ItemVO;
import proj.basic.itemmedia.controller.ItemMediaService;
import proj.basic.itemmedia.model.ItemMediaVO;

public class ShowItemDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Integer itemNo = Integer.valueOf(request.getParameter("itemNo"));
		ShowItemsService itemSrv = new ShowItemsService();
		ItemVO itemVO = itemSrv.showItemDetail(itemNo);
		List<ItemMediaVO> list = new ItemMediaService().getAllMediaOfOneItem(Integer.valueOf(itemNo));
		request.setAttribute("item", itemVO);
		request.setAttribute("itemMedia", list);
		List<EvaluateVO> eList = new EvaluateService_Wu().getItemEAll(itemNo);
		System.out.println(eList.size());
		request.setAttribute("eList", eList);
		request.getRequestDispatcher("/03_mall/ItemIntro.jsp").forward(request, response);
	}

}
