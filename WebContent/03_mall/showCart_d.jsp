<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Set,proj.basic.item.controller.AddSugViewListService,javax.servlet.http.HttpSession,
    proj.basic.item.controller.SearchItemService,java.util.Map,
    proj.basic.orderDetail.model.OrderDetailVO"%>
<style>
.cartList_drag { width: 56px; height: 60px; padding: 0.5em; }
</style>
<script>
$(document).ready(function() {
  $("div[name='cartList_drag']").draggable({
	  containment: "parent",
// 	  snap: true
  });
});
</script>
	<div style="height:250px">
<!-- 		<div class="ui-widget-content cartList_drag"> -->
				<c:forEach items="${cartList}" var="item" varStatus="cartS">
				<c:if test="${cartS.count <=9}">
				<div name="cartList_drag" class="ui-widget-content"  style="width:54px; float:left;margin:0px 5px 4px 0px">
					<a href='<c:url value="/ShowItemDetail?itemNo=${item.value.itemNo}"/>'>
					<img src="${pageContext.request.contextPath}/ShowItemMedia?itemNo=${item.value.itemNo}&itemMediaNo=1" width="56" height="58"/></a>
				<p class="ui-widget-header">	${item.value.itemPrice} </p>
				</div>
				</c:if>
				</c:forEach>
<!-- 		</div> -->
<%
int cartCount = 1;
if(session.getAttribute("cartList") != null)
	cartCount =  ((Map<String, OrderDetailVO>) session.getAttribute("cartList")).size();
while(cartCount<9){
%>
<div name="cartList_drag" style="width:56px;height:58px;border:0px;">
</div>
<%
cartCount++;
}
%>
		</div>
