<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
if(request.getParameter("action")!=null && request.getParameter("action").equals("logout") && request.getSession().getAttribute("memberVO") != null){
	 request.getSession().removeAttribute("memberVO");
	 response.sendRedirect("index.jsp");	
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style_index.css" type="text/css" media="all" />

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
            <li><a href="#"><img src="css/images/11.jpg" width="960" height="450" alt="" /></a></li>
            <li><a href="#"><img src="css/images/12.jpg" width="960" height="450" alt="" /></a></li>
            <li><a href="#"><img src="css/images/13.jpg" width="960" height="450" alt="" /></a></li>
            <li><a href="#"><img src="css/images/14.jpg" width="960" height="450" alt="" /></a></li>
            <li><a href="#"><img src="css/images/15.jpg" width="960" height="450" alt="" /></a></li>
          </ul>
        </div>
        <div id="slider-nav"> <a href="#" class="active">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> </div>
      </div>
      <!-- End Content Slider -->
      <!-- Products -->
      <div class="products">
        <div class="cl">&nbsp;</div>
        <ul>
        </ul>
          <div class="cl">&nbsp;</div>
      </div>
      <!-- End Products -->
    </div>
    <!-- End Content -->
    <!-- Sidebar -->
    <!-- End Sidebar -->
    <div class="cl">&nbsp;</div>
  </div>
  	<!-- End Main -->

					<%@include file="/FootItems_index.jsp" %>

  <!-- Footer -->
 <div id="footer" style="position:relative;">
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