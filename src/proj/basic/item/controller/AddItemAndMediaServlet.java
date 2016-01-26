package proj.basic.item.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import proj.basic.item.model.ItemVO;
import proj.basic.itemmedia.model.ItemMediaVO;


@WebServlet("/AddItemAndMediaServlet")
@MultipartConfig
public class AddItemAndMediaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getPart(String name) or request.getParts() 
		Collection<Part> parts = request.getParts();
		Iterator<Part> iPart = parts.iterator();
		Part p = null;
		ItemVO itemVO = new ItemVO();
//		Map<byte[], String> mMap = new LinkedHashMap<byte[], String>();
//		List<String> typeList = new ArrayList<String>();
//		List<String> dscrpList = new ArrayList<String>();
//		List<byte[]> fileList = new ArrayList<byte[]>();
		List<ItemMediaVO> iMediaList = new ArrayList<ItemMediaVO>();
		ItemMediaVO iMediaVO = null;
		byte[] iMedia = null;
		
		itemVO.setItemName(request.getParameter("itemName"));
		itemVO.setPrice(Double.valueOf(request.getParameter("price")));
		itemVO.setDiscount(Double.valueOf(request.getParameter("discount")));
		itemVO.setOnSaleTime(java.sql.Date.valueOf(request.getParameter("onSaleTime")));
		itemVO.setOffSaleTime(java.sql.Date.valueOf(request.getParameter("offSaleTime")));
		itemVO.setItemDscrp(request.getParameter("itemDscrp"));
		itemVO.setItemClassNo(Integer.valueOf(request.getParameter("classNo")));
		itemVO.setItemsQty(Integer.valueOf(request.getParameter("itemsQty")));
		itemVO.setItemStatus(1);
		InputStream in = null;
//		int count = 0; 

//		Enumeration e = request.getParameterNames();
//		while(e.hasMoreElements())
//			System.out.println(e.nextElement());
		
		int countDscrp = 1;
		while(iPart.hasNext()){
			
			p = iPart.next();
			if(p.getContentType() == null)
				continue;
			iMediaVO = new ItemMediaVO();
			in = p.getInputStream();
//			System.out.println("in size==>"+in.available());
			iMedia = new byte[in.available()];
			in.read(iMedia);
			
			iMediaVO.setItemMediaNo(countDscrp);
			if(countDscrp>1)
				iMediaVO.setMediaDscrp(request.getParameterValues("mediaDscrp")[countDscrp-2]);
			iMediaVO.setMediaType(p.getContentType());
			iMediaVO.setItemMedia(iMedia);
			
			iMediaList.add(iMediaVO);
			
//			fileList.add(iMedia);
//			typeList.add(p.getContentType());
			
//			mMap.put(iMedia, p.getContentType());
			
//			測試輸出圖片看看
//			p.write(p.getName() + count + "_.jpg");
			
			if(in != null)
				in.close();
//			count++;
			countDscrp++;
		}
//		System.out.println("map size=="+mMap.size());
//		Set<byte[]> key = mMap.keySet();
//		for(byte[] k:key){
//			System.out.println(mMap.get(k).length());
//		}
		
//		new AddItemAndMediaService().addItemAndMedia(itemVO, mMap);
		new AddItemAndMediaService().addItemAndMedia(itemVO, iMediaList);
		
	}

}
