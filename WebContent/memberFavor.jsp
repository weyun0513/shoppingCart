<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最愛清單</title>
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

<style>
a{   text-decoration: none; }
</style>
<script>
$(document).ready(function() {
	if(chkLogin().length != 0){
		inactivityTime();
	};
});

function chkLogin(){
	var member = "${memberVO}";
	return member;
}	
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
							<li><a href="#"><img src="http://cecj04g2.cloudapp.net/Zproj0813_3/advclient/adv_mediaServlet.do?advno=200"
									width="720" height="125" alt="" /></li>
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
<%-- 2014/8/7 Huang 變更顯示格式 -------------------------------------------------------%>									
<c:if test="${empty bookmarkList}">
目前還沒有將任何商品加入我的最愛
</c:if>
<c:if test="${not empty bookmarkList}">
<table >
<c:forEach items="${bookmarkList}" var="b" varStatus="bStatus">
<tr>
	<td  rowspan="4"><a href='<c:url value="/ShowItemDetail?itemNo=${b.itemNo}"/>'><img height="90px" width="80px" src='<c:url value="/ShowItemMedia?itemNo=${b.itemNo}&itemMediaNo=1"/>' /></a></td>
</tr>
<tr><td>商品編號：${b.itemNo}</td></tr>											
<tr><td>商品名稱：<a href='<c:url value="/ShowItemDetail?itemNo=${b.itemNo}"/>'>${b.itemName.length() > 20 ? b.itemName.substring(0, 18).concat("..") : b.itemName }</a></td></tr>
<tr><td>商品價格：
	<fmt:formatNumber var="itemPrice" value="${b.price  + 0.0001}" pattern="#,###,###,###"/>${itemPrice}
	</td></tr>
<tr></tr>
</c:forEach>
</table>
</c:if>
<%-- 結束 2014/8/7 Huang 變更顯示格式 -------------------------------------------------------%>		<%-- <c:if test="${empty bookmarkList}">
目前還沒有將任何商品加入我的最愛
</c:if>
<c:if test="${not empty bookmarkList}">
<c:forEach items="${bookmarkList}" var="b">
<a href='<c:url value="/ShowItemDetail?itemNo=${b.itemNo}"/>'>
	<img width="80px" src='<c:url value="/ShowItemMedia?itemNo=${b.itemNo}&itemMediaNo=1"/>' />
	商品編號 = ${b.itemNo},商品名稱 =  ${b.itemName},價格 =  ${b.price}
</a>
<br>
</c:forEach>
</c:if> --%>
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
