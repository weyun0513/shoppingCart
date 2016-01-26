<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>忘記密碼</title>

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
<!-- 							<li><a href="#"><img src="css/images/2.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="css/images/3.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="css/images/4.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="css/images/5.jpg" -->
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
					<c:if test="${empty rqsucc}">
<form action="${pageContext.request.contextPath}/askPwd" method="post">
請輸入您在本網站的帳號：<input type="text" name="RQaccount"/>${rqErrorMsg.rqAccountNull}${rqErrorMsg.rqAccountError}<br>
請輸入您設定在本網站的e-mail地址：<input type="text" name="RQmail"/>${rqErrorMsg.rqMailNull}${rqErrorMsg.rqMailError}<br>
<input type="submit" value="確定"/>
</form>
</c:if>
${rqsucc}<br>
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
				<div id="Div1" class="container_48" style="text-align:center">
    
<!--              <span style="padding-left:15px"><a href="http://www.lativ.com.tw/Page/About">聯絡WOW</a></span> -->
<!--              <span style="padding-left:15px"><a href="http://www.lativ.com.tw/Page/About">購物說明</a></span> -->
<!--              <span style="padding-left:15px"><a href="http://www.lativ.com.tw/Page/About">品牌日誌</a></span> -->
<!--              <span style="padding-left:15px"><a href="http://www.lativ.com.tw/Page/About">網站使用條款</a></span> -->
<!--              <span style="padding-left:15px"><a href="http://www.lativ.com.tw/Page/About">隱私權政策</a></span> -->
<!--              <span style="padding-left:15px"><a href="http://www.lativ.com.tw/Page/About">免責聲明</a></span> -->
<!--              <span style="padding-left:15px"><a href="http://www.lativ.com.tw/Page/About">最新消息</a></span> -->


            <span>CECJ04 GROUP2 ©2014 MEGO,Inc.</span>
            
            
</div>

			</table>


		</div>
		<!-- End Footer -->

		<!-- End Shell -->
</body>
</html>
