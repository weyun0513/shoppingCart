package proj.basic.item.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import proj.basic.item.model.ItemVO;


public class AddViewHistoryFilter implements Filter {

	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
//		Integer classNo = Integer.valueOf(req.getParameter("classNo"));	
//		Integer itemNo = Integer.valueOf(req.getParameter("itemNo"));
		String classNo = req.getParameter("classNo");
		String itemNo =req.getParameter("itemNo");
		
		List<ItemVO> sugList = null;
		
		Map<String, String> iHistoryNoSet = null;
		Map<String, String> iHistoryMap = null;
		if(session.getAttribute("iHistoryNoSet") == null){
			iHistoryNoSet = new LinkedHashMap<String, String>();
		}else
			iHistoryNoSet = (LinkedHashMap<String, String>)session.getAttribute("iHistoryNoSet");
		
		iHistoryNoSet.put(itemNo, classNo);
		
		Object[] tempA = iHistoryNoSet.keySet().toArray();
		iHistoryMap = new LinkedHashMap<String, String>();
		iHistoryMap.put(itemNo, classNo);
		for(int i = 0; i<=tempA.length-1; i++){
			iHistoryMap.put((String) tempA[i], iHistoryNoSet.get(tempA[i]));
		}
		iHistoryNoSet.clear();
		
		session.setAttribute("iHistoryNoSet", iHistoryMap);

		
		//推薦清單
		int rateCount = iHistoryMap.size() * 30 / 100;
		List<ItemVO> sugUserList = new AddSugViewListService().getSugUserItems(iHistoryMap, classNo);

		session.setAttribute("sugList", sugUserList);

		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
