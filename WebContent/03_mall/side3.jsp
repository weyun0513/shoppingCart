<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="java.util.List,proj.basic.itemClass.controller.ItemClassService,
    proj.basic.itemClass.model.ItemClassVO, 
    javax.servlet.http.HttpSession" %>
    
<%
List<ItemClassVO> allClassList = new ItemClassService().getAllClassAlive(); 
pageContext.setAttribute("allClassList", allClassList);

if(request.getParameter("classNo")!=null){
	List<ItemClassVO> childList = new ItemClassService().getChild(Integer.valueOf(request.getParameter("classNo")));
	pageContext.setAttribute("childList", childList);
}
%>
				<!-- Categories -->
				<div class="box categories">
<%-- 				<input type="hidden" name="iClassNo" value="${iClass.itemClassNo}"/> --%>
				
					<div class="box-content">
					<div id="boxSide">
						<ul>
						<c:forEach items="${allClassList}" var="iClassList">
							<c:if test="${iClassList.fatherClassno == 0}">
							<li><span><a href='<c:url value="/ShowItems?classNo=${iClassList.itemClassNo}&pageNo=1" />'>${iClassList.className}</a></span></li>
							</c:if>
							<c:if test="${not empty childList && classNo == iClassList.itemClassNo}">
							<c:forEach items="${childList}" var="chldList">
							<li><span><a href='<c:url value="/ShowItems?classNo=${chldList.itemClassNo}&pageNo=1" />'>${chldList.className}</a></span></li>
							</c:forEach>
							</c:if>
						</c:forEach>
						</ul>
						</div>
					</div>
				</div>
				<!-- End Categories -->