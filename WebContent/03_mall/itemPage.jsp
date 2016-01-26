<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" media="all" />
<script src="${ pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
<script>
$(function() {
	
    $( "#tabs" ).tabs();
  });
</script>

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
							<li><a href="http://www.asos.com/women/sale/cat/pgehtml.aspx?cid=7046&CTARef=GlobalBanner1|WW"  target="_blank"><img src="images/up01.png"
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
				<!-- End Content Slider -->
				<!-- Products -->
				<div class="products">
					<div class="cl">&nbsp;</div>
					<ul>
						<c:forEach items="${items}" var="i" varStatus="iStatus">
							<c:choose>
								<c:when test="${iStatus.count % 3 ==0 }">
									<li class="last"><div style="width:230px;overflow:hidden"><a href='<c:url value="ShowItemDetail?classNo=${i.itemClassNo}&itemNo=${i.itemNo}"/>'><img src="${pageContext.request.contextPath}/ShowItemMedia?classNo=${i.itemClassNo}&itemNo=${i.itemNo}&itemMediaNo=1" alt="" height="230px" /></a></div>
										<div class="product-info">
											<h3>
												HAPPY SHOPPING
												<!-- ??名稱 -->
											</h3>
											<div class="product-desc">
												<h4>
													
													<!-- 類別名稱 -->
												</h4>
												<p>
													<!-- 商品名稱 -->
													<a href='<c:url value="ShowItemDetail?classNo=${i.itemClassNo}&itemNo=${i.itemNo}"/>'>${i.itemName}</a>
												</p>
												<strong class="price">$<fmt:formatNumber var="itemPrice" value="${i.price + 0.0001}" pattern="#,###,###,###"/>${itemPrice}<span style="float:right"><c:choose>
<c:when test="${i.itemsQty < 10 && i.itemsQty>5}">數量稀少!</c:when>
<c:when test="${i.itemsQty <= 5 && i.itemsQty>3}">最後銷售!</c:when>
<c:when test="${i.itemsQty <= 3 && i.itemsQty>0}">最後搶購機會!</c:when>
</c:choose>
<c:if test="${i.itemsQty <=0  }">
銷售一空
</c:if></span></strong>
											</div>
										</div>
										</li>
								</c:when>
								<c:otherwise>
									<li ><div style="width:230px;overflow:hidden;margin-top:15px"><a href='<c:url value="ShowItemDetail?classNo=${i.itemClassNo}&itemNo=${i.itemNo}"/>'><img src="${pageContext.request.contextPath}/ShowItemMedia?classNo=${i.itemClassNo}&itemNo=${i.itemNo}&itemMediaNo=1" alt=""  height="230px" /></a></div>
										<div class="product-info">
											<h3>
												HAPPY SHOPPING
												<!-- ??名稱 -->
											</h3>
											<div class="product-desc">
												<h4>
<!-- 													男人最愛 -->
													<!-- 類別名稱 -->
												</h4>
												<p>
													<!-- 商品名稱 -->
													<a href='<c:url value="ShowItemDetail?classNo=${i.itemClassNo}&itemNo=${i.itemNo}"/>'>${i.itemName}</a>
												</p>
												<strong class="price">$<fmt:formatNumber var="itemPrice" value="${i.price + 0.0001}" pattern="#,###,###,###"/>${itemPrice}<span style="float:right"><c:choose>
<c:when test="${i.itemsQty < 10 && i.itemsQty>5}">數量稀少!</c:when>
<c:when test="${i.itemsQty <= 5 && i.itemsQty>3}">最後銷售!</c:when>
<c:when test="${i.itemsQty <= 3 && i.itemsQty>0}">最後搶購機會!</c:when>
</c:choose>
<c:if test="${i.itemsQty <=0  }">
銷售一空
</c:if></span></strong>
											</div>
										</div></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
			
		<div class="cl">&nbsp;</div>
			<div  align="center">
			<c:if test="${not empty totalPage || totalPage != 0}">
			<c:if test="${pageNo > 1}">
			<a href='<c:url value="ShowItems?&iClassNo=${iClass.itemClassNo}&classNo=${classNo}&pageNo=1"/>'>第一頁</a>
			</c:if>
			<c:if test="${pageNo > 1}">
				<a href='<c:url value="ShowItems?iClassNo=${iClass.itemClassNo}&classNo=${classNo}&pageNo=${pageNo-1}"/>'>上一頁</a>
			</c:if>
			<c:if test="${pageNo < totalPage}">
				<a href='<c:url value="ShowItems?iClassNo=${iClass.itemClassNo}&classNo=${classNo}&pageNo=${pageNo+1}"/>'>下一頁</a>
			</c:if>
			<c:if test="${pageNo < totalPage}">
				<a href='<c:url value="ShowItems?iClassNo=${iClass.itemClassNo}&classNo=${classNo}&pageNo=${totalPage}"/>'>最後一頁</a>
			</c:if>
			</c:if>
			</div>
		</div>
				<!-- End Products -->
			</div>
			<!-- End Content -->
			<!-- Sidebar -->
			<div id="sidebar">
<%@include file="/03_mall/side4.jsp" %>
<div id="tabs"  style="height:320px;padding:0px;width:230px">
  <ul>
    <li><a href="#tabs1">你可能會喜歡</a></li>
    <li><a href="#tabs2">考慮清單</a></li>
    <li>
<%--   		<img src="${pageContext.request.contextPath}/images/informationicon.png" width="28px" id="list_information"> --%>
	</li>
  </ul>
  <div id="tabs1">
   <%@include file="/03_mall/sugItem_Side.jsp" %>
  </div>
  <div id="tabs2">
   <%@include file="/03_mall/showCart.jsp" %>
  </div>
  
</div>

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
</div><!-- 20140801_0956 add -->
		<!-- End Shell -->
</body>
</html>