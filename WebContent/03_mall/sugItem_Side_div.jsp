<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Set"%>

			<h3>最近瀏覽過</h3>
			
			<div id="viewList">

                           <c:if test="${not empty iHistoryNoSet}">
<c:forEach items="${iHistoryNoSet}" var="iHistory" varStatus="historyEnd">

<a href='<c:url value="ShowItemDetail?classNo=${iHistory.value}&itemNo=${iHistory.key}"/>'><img src="${pageContext.request.contextPath}/ShowItemMedia?classNo=${iHistory.value}&itemNo=${iHistory.key}&itemMediaNo=1" style="width:75px; height:75px;"/></a>

</c:forEach>
</c:if>
                  
			</div>
