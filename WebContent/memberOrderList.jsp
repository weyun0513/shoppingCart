<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="proj.basic.orderList.controller.LoadMemberOrderService, proj.basic.member.model.MemberVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui-1.10.4.custom.min.css" type="text/css" media="all" />
<!-- jCarousel css-->
 <link rel="stylesheet" type="text/css" href="${ pageContext.request.contextPath }/css/horizontal.css">

<!--jQuery-->
<script src="${pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>
<!-- jCarousel core-->
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.jcarousel-core.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/core_plugin.js"></script>
<!-- jCarousel autoscroll plugin  -->
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.jcarousel-autoscroll.js"></script>
<script src="/Proj/js/logoutset.js" type="text/javascript"></script>
<script>
var member = "${memberVO}";
$(document).ready(function() {
	if(member.length != 0){
		inactivityTime();
	};
});

</script>
</head>
<body>
<%
List orderList = new LoadMemberOrderService().getAllOrder((MemberVO)request.getSession().getAttribute("memberVO"));
pageContext.setAttribute("orderList",orderList);
%>
<!-- Shell -->
<div class="shell">

  <!-- Header -->
 <%@include file="/top.jsp" %>

  <!-- End Search -->
  <!-- Main -->
		<div id="main">

			<div class="cl">&nbsp;</div>
			<!-- Content -->
			<div id="content">
				<!-- Content Slider -->
				<div id="slider" class="box">
					<div id="slider-holder">
						<ul>
							<li><a href="#"><img src="css/images/1.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="css/images/2.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="css/images/3.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="css/images/4.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="css/images/5.jpg"
									width="720" height="252" alt="" /></a></li>
						</ul>
					</div>
					<div id="slider-nav">
						<a href="#" class="active">1</a> <a href="#">2</a> <a href="#">3</a>
						<a href="#">4</a>
					</div>
				</div>
				<!-- End Content Slider -->
				
				<!-- 2014/07/31 Huang 增加訂單主檔 片段------------------------------------- -->
					<%@include file="/fragment/memberOrderListFragment.jsp" %>
					
				<!-- 結束 2014/07/31 Huang 增加訂單主檔 片段------------------------------------- -->
				
				<%-- <!-- Products -->
				<div class="products">
					<div class="cl">&nbsp;</div>
		<table><tr><td>===訂單編號===</td><td>===總金額===</td><td>===訂購日期===</td><td>===出貨日期===</td><td>===發票號碼===</td><td>===訂單狀態===</td></tr>
<c:if test="${empty orderList}">
目前尚無訂單唷~^^
</c:if>
<c:forEach items="${orderList}" var="orderList">
<tr>
<td align=center><a href="showOrderDetail?orderNo=${orderList.orderNo}&orderStatus=${orderList.orderStatus}">${orderList.orderNo}</a></td>
<td align=center>${orderList.totalPrice}</td>
<td align=center>${orderList.orderDate} </td>
<c:choose>
<c:when test="${orderList.shippingDate == '1970-01-01'}">
<td align=center>尚未出貨</td></c:when>
<c:otherwise>
<td align=center>${orderList.shippingDate}</td>
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${empty orderList.invoiceNo || orderList.invoiceNo == ''}">
<td align=center>尚未產生發票</td></c:when>
<c:otherwise>
<td align=center>${orderList.invoiceNo}</td>
</c:otherwise>
</c:choose>

<td align=center>
<c:choose>
<c:when test="${orderList.orderStatus == 'process'}">
處理中
</c:when>
<c:when test="${orderList.orderStatus == 'shipping'}">
出貨中
</c:when>
<c:when test="${orderList.orderStatus == 'arrival'}">
已到貨 
<!-- <form action="DoEvaluateItem"> -->
<!-- <input type="hidden" name="" value=""/> -->
<!-- <input type="hidden" name="" value=""/> -->
<!-- <input type="hidden" name="" value=""/> -->
<!-- <input type="submit" value="我要退貨"/> -->
<!-- </form> -->
</c:when>
<c:when test="${orderList.orderStatus == 'Refund'}">
退貨處理中
</c:when>
<c:when test="${orderList.orderStatus == 'Refunded'}">
退款已完成
</c:when>
</c:choose> 

</td>
</tr>
</c:forEach>
</table>
<div class="cl">&nbsp;</div>
				</div>
				<!-- End Products --> --%>
			</div>
			<!-- End Content -->
			<!-- Sidebar -->
			<div id="sidebar">
<%-- <%@include file="/03_mall/leftSelect.jsp" %> --%>
<%@include file="/03_mall/side4.jsp" %>
			</div>
			<!-- End Sidebar -->
			<div class="cl">&nbsp;</div>
		</div>
		<!-- End Main -->

					<%@include file="/FootItems.jsp" %>

		<!-- Footer -->
		<div id="footer" style="position: relative;">
			<table>
				<div id="Div1" class="container_48">

					<span style="padding-left: 15px"><a
						href="http://www.lativ.com.tw/Page/About">聯絡WOW</a></span> <span
						style="padding-left: 15px"><a
						href="http://www.lativ.com.tw/Page/About">購物說明</a></span> <span
						style="padding-left: 15px"><a
						href="http://www.lativ.com.tw/Page/About">品牌日誌</a></span> <span
						style="padding-left: 15px"><a
						href="http://www.lativ.com.tw/Page/About">網站使用條款</a></span> <span
						style="padding-left: 15px"><a
						href="http://www.lativ.com.tw/Page/About">隱私權政策</a></span> <span
						style="padding-left: 15px"><a
						href="http://www.lativ.com.tw/Page/About">免責聲明</a></span> <span
						style="padding-left: 15px"><a
						href="http://www.lativ.com.tw/Page/About">最新消息</a></span> <span
						style="padding-left: 260px"> ©2014 MEGO,Inc. </span>


				</div>

			</table>


		</div>
		<!-- End Footer -->

		<!-- End Shell -->
</body>
</html>
