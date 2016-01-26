<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<% pageContext.setAttribute("activeTimer",new java.util.Date()); %>
<!--jQuery-->
<script src="${ pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>

<script>
jQuery(document).ready(
		function(){
			jQuery("input").click(
					function(){
						var thisId = jQuery(this).attr("name");
						jQuery("#"+thisId).text("");
					});
			jQuery("input").blur(
					function(){
						var thisId = jQuery(this).attr("name");
						if(jQuery(this).val().trim().length == 0){
								jQuery("#" + thisId).text("請勿空白");
							
						}
							
					});
			
			
		});

function reloadImg(){
	document.getElementById("verifyImg").src="${pageContext.request.contextPath}/verifyImg?"+ new Date().getTime();
}
</script>
<style type="text/css">  
.form1-control {
  display: block;
  width: 300px;
  height: 34px;
  padding: 6px 12px;
  font-size: 14px;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}
.form1-control:focus {
  border-color: #66afe9;
  outline: 0;
  -moz-box-shadow: 0 0 2px 2px #66afe9;
-webkit-box-shadow: 0 0 2px 2px #66afe9;
box-shadow: 0 0 2px 2px #66afe9;
  
}
.form1-control::-moz-placeholder {
  color: #777;
  opacity: 1;
}
.form1-control:-ms-input-placeholder {
  color: #777;
}
.form1-control::-webkit-input-placeholder {
  color: #777;
}
.form1-control[disabled],
.form1-control[readonly],
fieldset[disabled] .form1control {
  cursor: not-allowed;
  background-color: #eee;
  opacity: 1;
}
textarea.form1-control {
  height: auto;
}
.form1-group {
  margin-bottom: 15px;
}
.btn {
  display: inline-block;
  padding: 6px 12px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.42857143;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
}
.btn-default {
  color: #333;
  background-color: #fff;
  border-color: #ccc;
}
.btn-default {
  color: #333;
  background-color: #fff;
  border-color: #ccc;
}
.btn-default:hover,
.btn-default:focus,
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default {
  color: #333;
  background-color: #e6e6e6;
  border-color: #adadad;
}
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default {
  background-image: none;
}
.btn-default.disabled,
.btn-default[disabled],
fieldset[disabled] .btn-default,
.btn-default.disabled:hover,
.btn-default[disabled]:hover,
fieldset[disabled] .btn-default:hover,
.btn-default.disabled:focus,
.btn-default[disabled]:focus,
fieldset[disabled] .btn-default:focus,
.btn-default.disabled:active,
.btn-default[disabled]:active,
fieldset[disabled] .btn-default:active,
.btn-default.disabled.active,
.btn-default[disabled].active,
fieldset[disabled] .btn-default.active {
  background-color: #fff;
  border-color: #ccc;
}
.btn-default .badge {
  color: #fff;
  background-color: #333;
}
</style>
<title>login</title>
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
							<li><a href="#"><img src="http://cecj04g2.cloudapp.net/Zproj0813_3/advclient/adv_mediaServlet.do?advno=201"
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
<form action="${pageContext.request.contextPath}/login" method="POST">
<input type="hidden" name="action" value="login"/>
<input type="hidden" name="needLogin" value="${param.needLogin}"/>
  <div class="form1-group">
    <label for="exampleInputEmail1">帳號</label>
    <input type="text" class="form1-control" name="account" id="accountText" placeholder="Enter account"><span id="account">${errorMsg}${errorMsgMap.accountEmpty}</span>
  </div>
  <div class="form1-group">
    <label for="exampleInputPassword1">密碼</label>
    <input type="password" class="form1-control" name="pwd" id="pwdText" placeholder="Password"><span id="pwd">${errorMsgMap.pwdEmpty}</span>
  </div>
  <div class="form1-group">
    <label for="exampleInputPassword1">驗證碼</label>
    <input type="text" class="form1-control" name="verifyText" id="verifyText" placeholder="Enter code"><br>
    <img src="${pageContext.request.contextPath}/verifyImg?lrn=${activeTimer}" id="verifyImg"/>
    <input type="button" value="換一張" onclick="reloadImg()" id="reloadBtn" />
  <span style="color:#0053ed;font-size:18px;font-weight:bolder">
${errorMsgMap.accountError}
${errorMsgMap.verifyCodeEmpty}
${errorMsgMap.verifyError}
${errorMsgMap.isBlack}
</span>
  </div>

<span style="margin-left:275px"><button type="submit" class="btn btn-default">登入</button></span>
</form>
<h3><a href="<c:url value='/index.jsp' />">回首頁</a></h3>
<h3><a href="<c:url value='/regist.jsp' />">註冊</a></h3>
<h3><a href="<c:url value='/askPwdPage.jsp' />">忘記密碼</a></h3>

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
						style="padding-left: 260px"> ©2013 MEGO,Inc. </span>


				</div>
		</div>
		<!-- End Footer -->

		<!-- End Shell -->
</body>
</html>