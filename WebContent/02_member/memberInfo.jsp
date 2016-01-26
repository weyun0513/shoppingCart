<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO,
    proj.basic.bookmark.controller.BookmarkService, proj.basic.member.model.MemberVO,
    proj.basic.item.model.ItemVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>會員中心</title>
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
<style type="text/css">  
a{   text-decoration: none; }

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
<script>
	$(document).ready(function() {
		$("#updatePwd").click(function() {
// 			$(":password").removeAttr("readonly");

	if ($("#resetPwdDiv").attr("style") == 'display:none')
				$("#resetPwdDiv").removeAttr("style");
			else
				$("#resetPwdDiv").attr("style", "display:none");
		});
		
		 $( "#birthday" ).datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "yy-mm-dd",
		      minDate: new Date('1914','01','01'),
		      maxDate: new Date('1994','11','31'),
		      defaultDate:"88-01-01",
		    });
	});
</script>
</head>

<body>

<!-- Shell -->
<div class="shell">
<%
pageContext.setAttribute("bookmarkList", (List<ItemVO>)new BookmarkService().getAllBookmark((MemberVO)session.getAttribute("memberVO"))); 
%>
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
					<div style="float:right">

</div>
<form action="<c:url value='/memberUpdate'/>" method="post" enctype="multipart/form-data">
<div class="form1-group" style="float:right">
	<label for="exampleInputEmail1"></label>
	<img src="${pageContext.request.contextPath}/showMemberImg?account=${memberVO.account}" width="150px" height="200px">
<br>
<br>
    <label for="exampleInputEmail1"><input type="file" name="photo"/></label>
   <br>
   <br>
<div style="margin-left:280px"><h2><a href="<c:url value='/query.do?action=query_list'/>">訂單查詢&nbsp;<img src="${pageContext.request.contextPath}/images/right-icon.png" width="20px" style="vertical-align:middle" /></a></h2></div>
<br>
<div style="margin-left:140px"><h2>我的最愛預覽</h2></div>
<c:if test="${empty bookmarkList}">
目前還沒有將任何商品加入我的最愛
</c:if>
<c:if test="${not empty bookmarkList}">
<table >
<c:forEach items="${bookmarkList}" var="b" varStatus="bStatus">
<c:if test="${bStatus.count <=3}">
<tr>
	<td  rowspan="4"><a href='<c:url value="/ShowItemDetail?itemNo=${b.itemNo}"/>'><img height="90px" width="80px" src='<c:url value="/ShowItemMedia?itemNo=${b.itemNo}&itemMediaNo=1"/>' /></a></td>
</tr>
<tr><td>商品編號：${b.itemNo}</td></tr>											
<tr><td>商品名稱：<a href='<c:url value="/ShowItemDetail?itemNo=${b.itemNo}"/>'>${b.itemName.length() > 20 ? b.itemName.substring(0, 18).concat("..") : b.itemName }</a></td></tr>
<tr><td>商品價格：
	<fmt:formatNumber var="itemPrice" value="${b.price  + 0.0001}" pattern="#,###,###,###"/>${itemPrice}
	</td></tr>
<tr></tr>
</c:if>
</c:forEach>
</table>
<div style="margin-left:280px"><a href="<c:url value='/ShowBookmark'/>">前往我的最愛&nbsp;<img src="${pageContext.request.contextPath}/images/right-icon.png" width="20px" style="vertical-align:middle"/></a></div>
</c:if>
</div>
 <div class="form1-group"><label for="exampleInputEmail1">帳號</label><input type="text"  class="form1-control input-sm" name="account" readonly value="${memberVO.account}"></div>
 <div class="form1-group"><label for="exampleInputEmail1">中文姓名</label><input type="text"  class="form1-control input-sm" name="chineseName" value="${memberVO.chineseName}"/></div>
 <div class="form1-group"><label for="exampleInputEmail1">性別</label><input type="text"  class="form1-control input-sm" name="gender" value="${memberVO.gender}"/></div>
 <div class="form1-group"><label for="exampleInputEmail1">生日</label><input type="text"  class="form1-control input-sm" name="birthday" id="birthday" value="${memberVO.birthday}"/></div>
 <div class="form1-group"><label for="exampleInputEmail1">E-mail</label><input type="text"  class="form1-control input-sm" name="email" value="${memberVO.email }"/></div>
 <div class="form1-group"><label for="exampleInputEmail1">地址</label><input type="text"  class="form1-control input-sm" name="addr" value="${memberVO.addr}"/></div>		
 <div class="form1-group"><label for="exampleInputEmail1">電話</label><input type="text"  class="form1-control input-sm" name="phone" value="${memberVO.phone}"/></div>		
 <div class="form1-group">
    <label for="exampleInputEmail1">
    <button type="button" class="btn btn-default" id="updatePwd" >修改密碼</button>
    </label>
</div>		
 <div id="resetPwdDiv" style="display:none">
 <div style="margin-left:190px">若空白則原密碼不做變更</div>
 <div class="form1-group"><label for="exampleInputEmail1">密碼</label><input type="password" class="form1-control input-sm" name="pwd" id="pwd"/></div>
 <div class="form1-group"><label for="exampleInputEmail1">確認密碼</label><input type="password" class="form1-control input-sm" name="pwdChk"/></div>${pwdChkError}
 </div>
  <div class="form1-group">
    <label for="exampleInputEmail1">
    <span  style="margin-left:270px"><button type="submit" class="btn btn-default">送出</button></span>
    </label>
</form>
</div>




					<div class="cl">&nbsp;</div>
				</div>
				<!-- End Products -->
			</div>
			<!-- End Content -->
			<!-- Sidebar -->
			<div id="sidebar">
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