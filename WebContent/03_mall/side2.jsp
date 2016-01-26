<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="java.util.List,proj.basic.itemClass.controller.ItemClassService,
    proj.basic.itemClass.model.ItemClassVO, 
    javax.servlet.http.HttpSession" %>

<%
List<ItemClassVO> allClassList = new ItemClassService().getAllClassAlive(); 
pageContext.setAttribute("allClassList", allClassList);

%>

				<!-- Categories -->
				<div class="box categories">
<%-- 				<input type="hidden" name="iClassNo" value="${iClass.itemClassNo}"/> --%>
				
					<div class="box-content">
						<ul>
						<c:forEach items="${allClassList}" var="iClassList">
						<c:choose>
							<c:when test="${iClassList.fatherClassno == 0}">
							<li><span><a href='<c:url value="/ShowItems?classNo=${iClassList.itemClassNo}&pageNo=1" />'>${iClassList.className}</a></span></li>
							</c:when>
							<c:otherwise>
							<div style="visibility:hidden"><li><span><a href='<c:url value="/ShowItems?classNo=${iClassList.itemClassNo}&pageNo=1" />'>${iClassList.className}</a></span></li></div>
							</c:otherwise>
						</c:choose>
						</c:forEach>
						</ul>
					</div>
				</div>
				<!-- End Categories -->