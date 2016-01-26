package proj.basic.mall;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.itextpdf.text.Document;

import proj.basic.item.controller.SearchItemService;
import proj.basic.item.model.ItemVO;

public class ExportItemDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("fileType"));
		System.out.println(request.getParameter("itemNo"));
		
		String fileType = request.getParameter("fileType");
		String itemNo = request.getParameter("itemNo");
		
//		ItemVO itemVO = new ItemVO();
//		itemVO.setItemClassNo(Integer.valueOf(request.getParameter("itemClassNo")));
//		itemVO.setItemNo(Integer.valueOf(request.getParameter("itemNo")));
//		itemVO.setItemName(request.getParameter("itemName"));
//		itemVO.setDiscount(Double.valueOf(request.getParameter("discount")));
//		itemVO.setItemDscrp(request.getParameter("itemDscrp"));
//		itemVO.setPrice(Double.valueOf(request.getParameter("price")));
//		ExportProcess ep = new ExportProcess();
//		XWPFDocument dFile = ep.exportWord(itemVO, request.getHeader("referer"));
//		
//		dFile.write(response.getOutputStream());
		
		ItemVO itemVO = new SearchItemService().findByItemNo(Integer.valueOf(itemNo));
		ExportProcess ep = new ExportProcess();
		
		if(fileType.equals("doc")){
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			XWPFDocument dFile = ep.exportWord(itemVO, request.getHeader("referer"));
			
			dFile.write(response.getOutputStream());
			return;
		}
		
		if(fileType.equals("pdf")){
			response.setContentType("application/pdf");
			ServletOutputStream out = response.getOutputStream();
			Document pdfFile = ep.ExportPDF(itemVO, request.getHeader("referer"), out);

			out.flush();
			out.close();
			
			return;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String fileType = request.getParameter("fileType");
		
		ItemVO itemVO = new ItemVO();
		itemVO.setItemClassNo(Integer.valueOf(request.getParameter("itemClassNo")));
		itemVO.setItemNo(Integer.valueOf(request.getParameter("itemNo")));
		itemVO.setItemName(request.getParameter("itemName"));
		itemVO.setDiscount(Double.valueOf(request.getParameter("discount")));
		itemVO.setItemDscrp(request.getParameter("itemDscrp"));
		itemVO.setPrice(Double.valueOf(request.getParameter("price")));
		ExportProcess ep = new ExportProcess();
		
		if(fileType.equals("doc")){
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			XWPFDocument dFile = ep.exportWord(itemVO, request.getHeader("referer"));
			
			dFile.write(response.getOutputStream());
			return;
		}
		
		if(fileType.equals("pdf")){
			response.setContentType("application/pdf");
			ServletOutputStream out = response.getOutputStream();
			Document pdfFile = ep.ExportPDF(itemVO, request.getHeader("referer"), out);

			out.flush();
			out.close();
			
			return;
		}
	}
}
