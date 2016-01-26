package proj.basic.member.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CHKLoginFilter implements Filter {

	FilterConfig fConfig;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		
//		if(session.getAttribute("account")!=null){
//			//登入成功
//			chain.doFilter(request, response);
//		}else{
//			//轉向登入頁面
//			session.setAttribute("userURI", req.getRequestURI());
//			String path = req.getContextPath();
//			resp.sendRedirect(path + "/01_login/login.jsp");
//		}
		if(session.getAttribute("memberVO")==null){
//			session.setAttribute("refererPage", req.getRequestURI());
			session.setAttribute("refererPage", req.getHeader("referer"));
			session.setAttribute("errorMsg", "請先登入");
			String path = req.getContextPath();
			resp.sendRedirect(path + "/01_login/login.jsp");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
	}
	
	public void destroy() {
		
	}

}
