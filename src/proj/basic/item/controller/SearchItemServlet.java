package proj.basic.item.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.item.model.ItemVO;
import proj.basic.itemClass.controller.ItemClassService;
import proj.basic.itemClass.model.ItemClassVO;

public class SearchItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("searchText") == null || request.getParameter("searchText").trim().length() == 0){
			request.setAttribute("TextNullMsg", "關鍵字請勿空白");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		String searchText = new String(request.getParameter("searchText").getBytes("ISO-8859-1"), "UTF-8");
		Integer itemType = Integer.valueOf(request.getParameter("itemType"));
		
		SearchItemService iSrv = new SearchItemService();
		ItemClassService iClassSrc = new ItemClassService();
		List<ItemVO> list = null;//存放搜尋出來,符合條件的商品
		List<ItemClassVO> classList = null;
		StringBuffer multiSearchBuf = new StringBuffer();
		String[] multiSearchArray = null;//for class(進階搜尋類別)
		Boolean discount = false;
		Boolean onSale = false;
		Double minPrice = null;
		Double maxPrice = null;
		
		if(request.getParameter("multiSearch").equals("true")){
			if(request.getParameter("minPrice") != null && request.getParameter("minPrice").trim().length() != 0)
				minPrice = Double.valueOf(request.getParameter("minPrice"));
			if(request.getParameter("maxPrice") != null && request.getParameter("maxPrice").trim().length() != 0)
				maxPrice = Double.valueOf(request.getParameter("maxPrice"));
			if(request.getParameter("discount") != null)
				discount = true;
			if(request.getParameter("onSale") != null)
				onSale = true;			
		}
			
		if(request.getParameterValues("multiSearchBox") == null){//非進階搜尋
			multiSearchArray = new String[0];
			classList = iClassSrc.findClassChild(itemType);//搜尋選擇類別的子類別 //不是進階搜尋才需要找尋子類別
//			if(!itemType.equals(0)){
//				classList = iClassSrc.findClassChild(itemType);//搜尋選擇類別的子類別 //不是進階搜尋才需要找尋子類別
//				request.setAttribute("classList", classList);
//			}
		}else
			multiSearchArray = request.getParameterValues("multiSearchBox");
		
		list = iSrv.searchItem(searchText, itemType, multiSearchArray,minPrice,maxPrice,discount,onSale);//搜尋選擇類別下的關鍵字商品
		
		request.setAttribute("classList", classList);
		request.setAttribute("searchText", searchText);
		request.setAttribute("itemType", itemType);
		request.setAttribute("searchResult", list);
		request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
		return;
		}
		
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
