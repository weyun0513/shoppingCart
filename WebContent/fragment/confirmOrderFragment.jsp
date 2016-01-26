<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/input_coustom.css" type="text/css" media="all" />
<!-- 檢查購物車是否低於庫存量 -->
<br><br>
				<c:if test="${not empty itemQtyError }">
					<c:forEach items="${itemQtyError}" var="aItemQtyError">
						<h3><strong style="color:red;">${aItemQtyError.value}</strong></h3>
					</c:forEach>
				</c:if>
				<!-- 檢查購物車是否低於庫存量 -->
				
				<!-- 訂單填寫開始 -->
				<c:if test="${not empty cartList }">
					<form method="post" action='<c:url value="cfmOrder"><c:param name="action" value="checkagain"></c:param></c:url>'>
							<div class="form1-group">
								<label>會員編號</label>
									<input name="memberID" class="form1-control input-sm"  type="text" readonly="readonly" placeholder="${ memberVO.memberID}" />
							</div>
							<div class="form1-group">
								<label>統一編號</label>
									<input name="invoiceIncNo"  class="form1-control input-sm"  type="text" value="${param.invoiceIncNo }">
							</div>
								${checkOutError.invoiceIncNo }
							<div class="form1-group">
								<label>發票抬頭</label>
									<input name="invoiceTitle"  class="form1-control input-sm" type="text" value="${param.invoiceTitle }">
							</div>
								${checkOutError.invoiceTitle }
							<div class="form1-group">
								<label>送貨地址</label>
									<input name="shippingAddr" class="form1-control input-sm"  type="text" value="${memberVO.addr }">
							</div>
								${checkOutError.shippingAddr }
							<div class="form1-group">
								<label>運費</label>
									<input name="shipppingRate" class="form1-control input-sm"  type="text" value="${cartListPrice < 1000 ? '80' : '免運費' }"
										readonly="readonly" >
							</div>
							<div class="form1-group">
								<c:set value="${cartListPrice < 1000 ? cartListPrice + 80 : cartListPrice}" var="cartListPriceWithShipppingRate"/>
								<c:set scope="request" value="${cartListPriceWithShipppingRate - memberVO.bonus >= 0 ?  memberVO.bonus : cartListPriceWithShipppingRate }" var="canUseBonus"/>
								紅利金共 <c:out value="${memberVO.bonus}"/>點<br>
								此次訂單可用<span id="canUseBonus">${canUseBonus < 0 ? 0 : canUseBonus }</span>點
							
								<input type="range" name="bonus" id="bonus_slider" min="0" max="${canUseBonus < 0 ? 0 : canUseBonus }" value="0">
								<h2 id="bonus_change" style="display:inline">0</h2>
							</div>
							<div class="form1-group">
								<c:set scope="request" value="${cartListPriceWithShipppingRate - memberVO.deposit >= 0 ?  memberVO.deposit : cartListPriceWithShipppingRate }" var="canUseDeposit"/>
								儲值金共 <c:out value="${memberVO.deposit}"/>元<br>
								此次訂單可用<span id="canUseDeposit">${canUseDeposit < 0 ? 0 : canUseDeposit }</span>元
							
								<input type="range" name="deposit" id="deposit_slider" min="0" max="${canUseDeposit < 0 ? 0 : canUseDeposit }" value="0">
								<h2 id="deposit_change" style="display:inline">0</h2>
							</div>
							<div class="form1-group">
								<label>帳單金額</label>
									<h2 style="display:inline; color:red;" id="finalPrice">${cartListPriceWithShipppingRate}</h2>元
							</div>
							<div class="form1-group">
								<label>付款方式</label>
								<div id="payWayType">
									<input type="radio" name="payWay" value="ATM">
									轉帳 <br>
									<input type="radio" name="payWay" value="creditcard">
									信用卡
								</div>
							</div>
								${checkOutError.payWay }
							<div class="form1-group">
								<input type="submit" class="btn btn-default" value="確認結帳">
							</div>
					</form>
				</c:if>
<!-- 訂單填寫結束 -->