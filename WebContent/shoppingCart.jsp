<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cart</title>

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
							<li><a href="#"><img src="http://cecj04g2.cloudapp.net/Zproj0813_3/advclient/adv_mediaServlet.do?advno=200"
									width="720" height="125" alt="" /></a></li>
<!-- 							<li><a href="#"><img src="../css/images/2.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="../css/images/3.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="../css/images/4.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="../css/images/5.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
						</ul>
					</div>
					<div id="slider-nav">
						<a href="#" class="active">1</a> <a href="#">2</a> <a href="#">3</a>
						<a href="#">4</a>
					</div>
				</div>
				<!--2014/07/29 Huang 購物車頁面改為c:import  -->
					<!-- 購物車開始 -->
						<c:import url="/fragment/cart.jsp"></c:import>
					<!-- 購物車結束 -->
				<!--2014/07/29 Huang 購物車頁面改為c:import  -->
				
				<!-- End Content Slider -->
				<%--<!-- Products -->
				<div class="products">
					<div class="cl">&nbsp;</div>
		<c:if test="${empty cartList}">
		<h2>目前購物車內尚無商品</h2>
		</c:if>
<table>
<c:forEach items="${cartList}" var="item">
<tr><td rowspan="6" width="130px" align = center >
<img width="130" height="150" src="ShowItemMedia?itemNo=${item.value.itemNo}&itemMediaNo=1"/>
</td></tr>
<tr><td colspan="2" width="80px"><b>項目編號</b></td><td  width="280px">${item.value.itemNo}</td></tr>
<tr><td colspan="2" width="80px">商品名稱</td><td  width="280px">${item.value.itemName}</td></tr>
<tr><td colspan="2" width="80px">價格</td><td  width="280px">${item.value.itemPrice}</td></tr>
<tr><td colspan="2" width="80px">折扣</td><td  width="280px">${item.value.discount}</td></tr>
<tr><td colspan="2" width="80px">購買數量</td><input type="hidden" name="quantity" value="${item.value.quantity}"/>
<td  width="280px">
<form action="addToCart" method="POST">
<input type="hidden" name="action" value="updateCartQ"/>
<input type="hidden" name="itemNo" value="${item.value.itemNo}"/>
<input type="text" name="buyQty" value="${item.value.quantity}"/>
<span style="float:right"><input type="submit" value="修改"></span>
</form></td>
<td><span style="float:right"><form action="addToCart" method="POST">
<input type="hidden" name="action" value="deleteCartQ"/>
<input type="hidden" name="itemNo" value="${item.value.itemNo}"/>
<span style="float:right"><input type="submit" value="刪除"></span>
</form></span></td>
<tr></tr>
</c:forEach>
</table>
<form action="confirmOrder.jsp" method="post">		
<div style="float:right">總價 : <c:choose><c:when test="${not empty cartListQty}">${cartListPrice}</c:when><c:otherwise>0</c:otherwise></c:choose></div>
<br>
<div style="float:right"><input type="submit" value="確認訂單"></div>
</form>
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