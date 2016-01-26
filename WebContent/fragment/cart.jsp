<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/table_coustom.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/input_coustom.css" type="text/css" media="all" />
<style>
a{ text-decoration: none; }
</style>
<script>
	$(document).ready(init);
	
	var thisObj;
	
	var context = "${pageContext.request.contextPath}";
	function init(){
		$("select[name='cart']").change(function(){
			thisObj = $(this);
			var buyQty = $(this).val();
			var itemNo = $(this).next().val();
 			var path = context+"/addToCartAJAX";
			
 			sendPostRequest(path, itemNo, buyQty);
			
		});
	}
	
	var xmlhttp = null;
	function sendPostRequest(path, itemNo, buyQty) {
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = callback;
		
		var queryString = "itemNo="+ itemNo + "&buyQty=" + buyQty;
		console.log(queryString);
		console.log(path);
		xmlhttp.open("POST", path);
		xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xmlhttp.send(queryString);
	}
	
	
	function callback() {
		if(xmlhttp.readyState==4) {
			if(xmlhttp.status==200) {
				processJSON(xmlhttp.responseText);
			} else {
				alert("Error!!! "+xmlhttp.statusText);
			}
		}
	}
	
	function processJSON(json) {
		
		var obj = jQuery.parseJSON( json );

		thisObj.parent().next().next().text('NT$' +obj[0].subTotalPrice.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,').split("." , 1));
		$("#totalPrice").text(obj[0].cartListPrice.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,').split("." , 1));
		$("#top_total_price").text('NT$' + obj[0].cartListPrice.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,').split("." , 1));
		
		
	}
</script>

</head>
<!-- 購物車開始 -->

<c:if test="${ not empty cartList }">
	<c:set var="url" value="${pageContext.request.requestURL}"/>
	
	<h3>購物車目前共${cartList.size() }樣商品</h3>
	
	<c:if test="${fn:contains(url,'confirmOrder')}">
		<h3>
			<img alt="" src="${pageContext.request.contextPath}/images/left-icon.png"  width="20px" style="vertical-align:middle">
			<a style="color:#FF3333;" href="${pageContext.request.contextPath}/shoppingCart.jsp">
				${fn:contains(url,"confirmOrder") ? "若修改數量請回上一頁" : "" }
			</a>
		</h3>
	</c:if>
	
	<div class="CSSTableGenerator" >
		<table>
				<tr>
					<td></td>
					<td>商品名稱</td>
					<td>商品價格</td>
					<td>購買數量</td>
					<td>折扣</td>
					<td>小計</td>
					<td></td>
				</tr>
				<!-- 計算訂單總金額 total -->
				<c:set var="total" value="0" />
				<c:forEach items="${cartList}" var="item">
					<tr>
						<td>
							<div>
								<a href='<c:url value="ShowItemDetail?itemNo=${item.value.itemNo}"/>'>
								<img width="100" src="${pageContext.request.contextPath}/ShowItemMedia?itemNo=${item.value.itemNo}&itemMediaNo=1"/>
								</a>
							</div>
						</td>
						<td width="80"><a href='<c:url value="ShowItemDetail?itemNo=${item.value.itemNo}"/>'>${item.value.itemName }</a></td>
						<td>
							<fmt:formatNumber var="itemPrice" type="currency" value="${item.value.itemPrice}"  pattern="#,###,###,###"/>
							<c:out value="${itemPrice}"/>
						</td>
						<td><select  class="form-control" name="cart">
								<c:forEach items="${itemList}" var="itemVO">
									<c:if test="${itemVO.itemNo == item.value.itemNo}">
										<c:forEach begin="1" end="${itemVO.itemsQty > 10 ? 10 : itemVO.itemsQty }" varStatus="status">
											<option <c:out value='${item.value.quantity == status.count ? "selected=selected" : "" }'/>
													<c:out value='${fn:contains(url,"confirmOrder") ? "disabled" : "" }'/>
											 		value="${status.count }">${status.count}</option>
										</c:forEach>
									</c:if>
								</c:forEach>
							</select>
							<input value="${item.value.itemNo}" type="hidden"/><!-- 為了讓AJAX傳送itemNo參數 -->
						</td>
							<td>
							
							<fmt:formatNumber var="discount" type="number" value="${item.value.discount*100 + 0.0001}"  pattern="#,###,###,###"/>
							<c:set var="discountEnd" value='${discount.toString().indexOf("0") == -1  ? discount : discount.toString().substring(0,1) }${"折"}'/>
							<c:out value="${discount != 0 ? discountEnd : '原價'}" />
						</td>
						<td>
							<fmt:parseNumber var="subPrice0" integerOnly="true" type="number" value="${item.value.discount != 0 ? item.value.discount * item.value.itemPrice * item.value.quantity : item.value.itemPrice * item.value.quantity }"/>
							<fmt:formatNumber var="subPrice" type="currency" maxFractionDigits="0" value="${subPrice0}"/>
							<%-- <fmt:formatNumber var="subPrice" type="currency" maxFractionDigits="0" value="${item.value.discount != 0 ? item.value.discount * item.value.itemPrice * item.value.quantity : item.value.itemPrice * item.value.quantity }"/>--%>
							<c:out value="${subPrice}" />
						</td>
						<td>
							<form method="POST" action='${pageContext.request.contextPath}/addToCart'>
								<input type="hidden" name="action" value="deleteCartQ"/>
								<input type="hidden" name="itemNo" value="${item.value.itemNo}"/>
								<input type="image" src="${pageContext.request.contextPath}/images/trash-512.png" />
							</form>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="5">總計</td>
					<td>
						NT$ <span id="totalPrice">
							${cartListPrice }
						</span>
						<%-- <fmt:formatNumber var="totalPrice" type="currency" maxFractionDigits="0" value="${cartListPrice }"/>
						<c:out value="${totalPrice}" /> --%>
					</td>
				<!-- 判斷是否在shoppoing_cart.jsp頁面 -->
				<c:set var="url" value="${pageContext.request.requestURL}"/>
				<c:if test="${fn:contains(url, 'shoppingCart')}">
					<td>
						<form action="<c:url value='cfmOrder'/>" method="post">
							<input type="submit" value="結帳" class="btn btn-default">
						</form>
					</td>
				</c:if>
				<c:if test="${!fn:contains(url, 'shoppingCart')}"><td></td></c:if>
				<!-- 結束 判斷是否在shoppoing_cart.jsp頁面 -->
				</tr>
		</table>
	</div>
	<!-- 購物車結束 -->
</c:if>
<!-- 無商品開始 -->
<c:if test="${empty cartList }">
				購物車目前還沒有商品
				<a href='<c:url value="ShowItems?pageNo=1&classNo=1"/>'>前往選購</a>&nbsp;<img src="${pageContext.request.contextPath}/images/right-icon.png" width="20px" style="vertical-align:middle" />
</c:if>
<!-- 無商品結束 -->