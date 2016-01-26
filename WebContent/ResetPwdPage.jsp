<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重設密碼</title>

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
					<div id="slider-nav">
						<a href="#" class="active">1</a> <a href="#">2</a> <a href="#">3</a>
						<a href="#">4</a>
					</div>
				</div>
				<!-- End Content Slider -->
				<!-- Products -->
				<div class="products">
					<div class="cl">&nbsp;</div>
<c:if test="${not empty memberVO}">
<form action="resetPWD" method="post">
<input type="hidden" value="${param.reid}" name="resetID"/>
請輸入您的新密碼：<input type="text" name="ResetPWD"/><br>
請再次輸入您的新密碼：<input type="text" name="conResetPWD"/><br>
<input type="submit" value="送出"/>
</form>
</c:if>
${resetVerifyError}<br>
<c:if test="${not empty resetSucc}">
${resetSucc}<br>
<a href="01_login/login.jsp">登入</a>
</c:if>
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
