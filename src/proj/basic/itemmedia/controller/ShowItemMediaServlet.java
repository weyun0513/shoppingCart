package proj.basic.itemmedia.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proj.basic.itemmedia.model.ItemMediaDAO;
import proj.basic.itemmedia.model.ItemMediaVO;

public class ShowItemMediaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OutputStream out = null;
		String itemNo = request.getParameter("itemNo");
		String itemMediaNo = request.getParameter("itemMediaNo");
		ShowItemMediaService show = new ShowItemMediaService();
		out = response.getOutputStream();
		out.write(show.pickItemMedia(Integer.valueOf(itemNo), Integer.valueOf(itemMediaNo)));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getOutputStream().print("錯誤的存取");

	}

}
