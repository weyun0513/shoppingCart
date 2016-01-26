package proj.basic.mall;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import proj.basic.blackList.controller.BlackListService;
import proj.basic.blackList.model.BlackListVO;

public class BlockFilter implements Filter {
	FilterConfig fConfig;
    public void init(FilterConfig fConfig) throws ServletException {
    	this.fConfig = fConfig;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("hi filter");
		HttpServletResponse res = (HttpServletResponse)response;
		BlackListService blkSrv = new BlackListService();
		List<BlackListVO> list = blkSrv.getList();
		for(BlackListVO b:list){
			if(request.getRemoteAddr().equals(b.getIp()))
				res.sendRedirect("block.jsp");
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}


}
