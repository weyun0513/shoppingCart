<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂單明細</title>

<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" media="all" />

<!--jQuery-->
<script src="${ pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>

</head>
<body>

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
<!-- 					<div id="slider-nav"> -->
<!-- 						<a href="#" class="active">1</a> <a href="#">2</a> <a href="#">3</a> -->
<!-- 						<a href="#">4</a> -->
<!-- 					</div> -->
				</div>
				<!-- End Content Slider -->
				
				<!-- 2014/07/31 Huang  增加訂單明細片段------------------------------------------->
					<%@include file="/fragment/orderDetailFragment.jsp" %>
				<!-- 結束 2014/07/31 Huang  增加訂單明細片段------------------------------------------->
				
				<!-- Products -->
				<div class="products">
					<div class="cl">&nbsp;</div>
<%-- ${memberVO.account} 您的 ${od.orderNo} 明細如下：<br> --%>
<%-- <c:forEach items="${orderDetailList}" var="od"> --%>
<!-- <br> -->
<%-- ${od.orderNo}<br> --%>
<%-- ${od.itemNo}<br> --%>
<%-- ${od.itemName}<br> --%>
<%-- ${od.itemPrice}<br> --%>
<%-- ${od.discount}<br> --%>
<%-- ${od.quantity}<br> --%>
<%-- <c:if test="${not empty orderStatus}"> --%>
<%-- <c:choose> --%>
<%-- <c:when test="${orderStatus == 'process'}"> --%>
<!-- 處理中 -->
<%-- </c:when> --%>
<%-- <c:when test="${orderStatus == 'shipping'}"> --%>
<!-- 出貨中 -->
<%-- </c:when> --%>
<%-- <c:when test="${orderStatus == 'arrival'}"> --%>
<!-- 已到貨 <br><a href="#">我要退貨</a> -->
<%-- <c:choose> --%>
<%-- <c:when test="${not empty evaChk && (evaChk[od.itemNo] == true)}"> --%>
<%-- <a href="evaluateItem.jsp?orderNo=${od.orderNo}&itemNo=${od.itemNo}">評價商品</a> --%>
<%-- </c:when> --%>
<%-- <c:otherwise>查看評價</c:otherwise> --%>
<%-- </c:choose> --%>
<%-- </c:when> --%>
<%-- <c:when test="${orderStatus == 'Refund'}"> --%>
<!-- 退貨處理中 -->
<%-- </c:when> --%>
<%-- <c:when test="${orderStatus == 'Refunded'}"> --%>
<!-- 退款已完成 -->
<%-- </c:when> --%>
<%-- <c:otherwise></c:otherwise> --%>
<%-- </c:choose>  --%>
<%-- </c:if> --%>
<!-- <hr> -->
<%-- </c:forEach> --%>

<div class="cl">&nbsp;</div>
				</div>
				<!-- End Products -->
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
