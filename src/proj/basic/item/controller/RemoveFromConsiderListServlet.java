package proj.basic.item.controller;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj.basic.item.model.ItemVO;

@WebServlet("/RemoveFromConsiderListServlet")
public class RemoveFromConsiderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("8");
		HttpSession session = request.getSession();
		Set<ItemVO> orderSet = (LinkedHashSet<ItemVO>)session.getAttribute("consider");
		Object[] iSet = orderSet.toArray();
		String[] list = request.getParameter("removeList").split(",");
		
		for(String s:list){
			for(Object i:iSet){
				if(((ItemVO)i).getItemNo().equals(Integer.valueOf(s)))
					orderSet.remove(i);
			}
		}
		
		session.setAttribute("consider",orderSet);
		response.getOutputStream().print("success");
	}
}
