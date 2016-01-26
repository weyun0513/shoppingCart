package proj.basic.item.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.item.model.ItemVO;

/**
 * Servlet implementation class AddToThinkAbout
 */
@WebServlet("/AddToConsider")
public class AddToConsider extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Set<ItemVO> orderSet = null;

//		List<ItemVO> itemConsiderList = null;
		if(session.getAttribute("consider") == null)
			orderSet = new LinkedHashSet<ItemVO>();
		else
			orderSet = (LinkedHashSet<ItemVO>)session.getAttribute("consider");
		
		Integer itemNo = Integer.valueOf(request.getParameter("itemNo"));
		boolean search = true;
		for(ItemVO i: orderSet){
			if(i.getItemNo().equals(itemNo)){
				search = false;
				break;
			}
		}
		
		if(search){
			ItemVO itemVO = new SearchItemService().findByItemNo(itemNo);
			orderSet.add(itemVO);
		}
		
		session.setAttribute("consider",orderSet);
		
		response.getOutputStream().print("success");
	}

}
