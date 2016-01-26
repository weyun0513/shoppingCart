<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="java.util.List,proj.basic.itemClass.controller.ItemClassService,
    proj.basic.itemClass.model.ItemClassVO, 
    javax.servlet.http.HttpSession" %>

<%
ItemClassService iClassSrvFRChd = new ItemClassService();
ItemClassVO iClass = null;
List<ItemClassVO> childList = iClassSrvFRChd.findClassChild(Integer.valueOf(request.getParameter("classNo"))); 
if(childList.size() == 0 && request.getParameter("iClassNo")!= null){
	 childList = iClassSrvFRChd.findClassChild(Integer.valueOf(request.getParameter("iClassNo")));
	 iClass = iClassSrvFRChd.findClassByClassNo(Integer.valueOf(request.getParameter("iClassNo")));
}else{
	iClass = iClassSrvFRChd.findClassByClassNo(Integer.valueOf(request.getParameter("classNo")));
}
// getServletContext().setAttribute("childList", childList);
// getServletContext().setAttribute("iClass", iClass);
request.getSession().setAttribute("childList", childList);
request.getSession().setAttribute("iClass", iClass);
%>
				<!-- Categories -->
				<div class="box categories">
<%-- 				<input type="hidden" name="iClassNo" value="${iClass.itemClassNo}"/> --%>
					<h2>
						${iClass.className}<span></span>
					</h2> 
					<div class="box-content">
						<ul>
						<c:forEach items="${childList}" var="chd">
							<c:choose>
							<c:when test="${chd.itemClassNo == classNo}">
							<li style="background:#AAAAAA"><span><a href='<c:url value="${pageContext.request.contextPath}/ShowItems?iClassNo=${iClass.itemClassNo}&classNo=${chd.itemClassNo}&pageNo=1" />'>${chd.className}</a></span></li>
							</c:when>
							<c:otherwise>
							<li><span  id ="${chd.itemClassNo}" ><a href='<c:url value="${pageContext.request.contextPath}/ShowItems?iClassNo=${iClass.itemClassNo}&classNo=${chd.itemClassNo}&pageNo=1" />'>${chd.className}</a></span></li>
							</c:otherwise>
							</c:choose>
						</c:forEach>
						</ul>
					</div>
				</div>
				<!-- End Categories -->