<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>確認訂單</title>
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
<!-- <script src="/Proj/js/logoutset.js" type="text/javascript"></script> -->
<script>
var member = "${memberVO}";
$(document).ready(function() {
	if(member.length != 0){
		inactivityTime();
	};
});

</script>
<!-- 20140731 Huang  為了type range 而設定的script-------------------------------->
<script>
	/**為了type range 而設定的script*/
	$(function(){
		var totalPrice = parseInt($('#totalPrice').text());
		totalPrice = totalPrice < 1000 ? totalPrice +80 : totalPrice;
		var dmax = $('#deposit_slider').attr('max');
		
		var bmax = $('#bonus_slider').attr('max');
		$('#bonus_slider').on('change', function(){
		    $('#bonus_change').text($('#bonus_slider').val());
		   
		    $('#deposit_slider').attr('max', totalPrice - parseInt($('#bonus_slider').val())>=dmax?
		    		dmax:totalPrice - parseInt($('#bonus_slider').val()));
		    
			$('#finalPrice').text(totalPrice - parseInt($('#bonus_slider').val()) - parseInt($('#deposit_slider').val()));
			
			if($('#finalPrice').text() == 0){
				$('#payWayType').html('<input type="radio" name="payWay" value="noPay" checked="ture"/>免付款');
			}else{
				$('#payWayType').html('<input type="radio" name="payWay" value="ATM">轉帳 <br><input type="radio" name="payWay" value="creditcard">信用卡');
			}
		});
		$('#deposit_slider').on('change', function(){
			$('#deposit_change').text($('#deposit_slider').val());
		  
		    $('#bonus_slider').attr('max', totalPrice - parseInt($('#deposit_slider').val())>=bmax?bmax:totalPrice - parseInt($('#deposit_slider').val()));
			$('#finalPrice').text(totalPrice - parseInt($('#bonus_slider').val()) - parseInt($('#deposit_slider').val()));
			
			if($('#finalPrice').text() == 0){
				//$('#payWayType').html('<input type="checkbox" name="payWay" value="noPay" checked="ture">免付款'); 
				$('#payWayType').html('<input type="radio" name="payWay" value="noPay" checked="ture"/>免付款');
			}else{
				$('#payWayType').html('<input type="radio" name="payWay" value="ATM">轉帳 <br><input type="radio" name="payWay" value="creditcard">信用卡');
			}
		});
		
		
	});

</script>
<!-- 結束20140731 Huang  為了type range 而設定的script-------------------------------->
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
				<!--2014/07/30 Huang 購物車頁面改為c:import  -->
					<!-- 購物車開始 -->
						<c:import url="/fragment/cart.jsp"></c:import>
					<!-- 購物車結束 -->
				<!--2014/07/30 Huang 購物車頁面改為c:import  -->
				<!--2014/07/30 Huang 結帳頁面改為fragment裡的confirmOrderFragment.jsp  -->
						<c:import url="/fragment/confirmOrderFragment.jsp"></c:import>
				<!--結束2014/07/30 Huang 結帳頁面改為fragment裡的confirmOrderFragment.jsp  -->
				 <!-- Products -->
				 
				<%--<div class="products">
					<div class="cl">&nbsp;</div>
<table>
<c:forEach items="${orderErrorList}" var="itemMsg">
抱歉! 商品 ${itemMsg} 已售完!
</c:forEach> 
<c:forEach items="${cartList}" var="item">
<tr><td rowspan="6" width="130px" align = center >
<img width="130" height="150" src="ShowItemMedia?itemNo=${item.value.itemNo}&itemMediaNo=1"/>
</td></tr>
<tr><td colspan="2" width="80px"><b>項目編號</b></td><td  width="280px">${item.value.itemNo}</td></tr>
<tr><td colspan="2" width="80px">商品名稱</td><td  width="280px">${item.value.itemName}</td></tr>
<tr><td colspan="2" width="80px">價格</td><td  width="280px">${item.value.itemPrice}</td></tr>
<tr><td colspan="2" width="80px">折扣</td><td  width="280px">${item.value.discount}</td></tr>
<tr><td colspan="2" width="80px">購買數量</td><td  width="280px">${item.value.quantity}</td>
<tr></tr>
</c:forEach>
</table>
<hr>
<form action="cfmOrder" method="post">	
<input type="hidden" name="cartListPrice"	value="${cartListPrice}"/>
運費：100 元<input type="hidden" name="shippingRate"	value="100"/><br>
付款方式：<select name="payWay"> 
<option value="0">請選擇付款方式</option> 
<option value="ATM">ATM轉帳</option> 
<option value="creditcard">信用卡</option> 

</select>${payWayError}<br>

收件地址：
<c:choose>
<c:when test="${empty ShippingAddr}">
<input type="text" name="shippingAddr" value="${memberVO.addr}"/><br>
</c:when>
<c:otherwise>
<input type="text" name="shippingAddr" value="${ShippingAddr}"/><br>
</c:otherwise>
</c:choose>
統一編號：<input type="text" name="invoiceIncNo" value="${invoiceIncNo}"/><br>
抬頭：<input type="text" name="invoiceTitle" value="${invoiceTitle}"/><br>
<input type="hidden" name="totalPrice"	value="${cartListPrice + 100}"/><br>
總金額 : <c:choose><c:when test="${not empty cartListQty}">${cartListPrice + 100}</c:when><c:otherwise>目前購物車中無商品</c:otherwise></c:choose><br>
<input type="submit" value="確認訂單">
</form>
					<div class="cl">&nbsp;</div>
				</div>
				<!-- End Products --> --%>
			</div>
			<!-- End Content -->
			<!-- Sidebar -->
			<div id="sidebar">
<%-- <%@include file="/03_mall/leftSelect.jsp" %> --%>
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