<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/table_coustom.css" type="text/css" media="all" />
<style>
a{text-decoration: none;}
</style>
<!-- 訂單明細開始 -->

				<h3>訂單明細</h3>
				<h3>
					<a href='<c:url value="/query.do?action=query_list" />'>
						<img src="${pageContext.request.contextPath}/images/left-icon.png" width="20px" style="vertical-align:middle"/>
						返回訂單查詢
					</a>
				</h3>
				<div class="CSSTableGenerator" >
					<table>
							<tr>
								<td>訂單編號</td>
								<td>商品編號</td>
								<td>圖片瀏覽</td>
								<td>商品名稱</td>
								<td>商品價格</td>
								<td>商品折扣</td>
								<td>購買數量</td>
								<td>評價商品</td>
							</tr>
						<c:forEach items="${orderDetailVOs}" var="orderDetail">
							<tr>
								<td>${orderDetail.orderNo}</td>
								<td>
									<a href='<c:url value="/ShowItemDetail?itemNo=${orderDetail.itemNo}"/>'>${orderDetail.itemNo}</a>
								</td>
								<td>
									<a href='<c:url value="/ShowItemDetail?itemNo=${orderDetail.itemNo}"/>'>
										<img width="80px" src='<c:url value="/ShowItemMedia?itemNo=${orderDetail.itemNo}&itemMediaNo=1"/>' />
									</a>
								</td>
								<td>${orderDetail.itemName}</td>
								<td>
									<fmt:parseNumber var="itemPrice0" integerOnly="true" type="number" value="${orderDetail.itemPrice}"/>
									<fmt:formatNumber var="itemPrice" type="currency" maxFractionDigits="0" value="${itemPrice0}"/>
									<c:out value="${itemPrice}" />
								</td>
								<td>${orderDetail.discount}</td>
								<td>${orderDetail.quantity}</td>
								<td>
									<!-- 判斷訂單狀態是已到達，且從OrderQueryServlet 傳過來的 itemNoList 裡面並不包含itemNo，才可評價-->
									<c:choose>
										<c:when test="${orderStatus == '已簽收' && !itemNoList.contains(orderDetail.itemNo)}">
											<a href='<c:url value="/evaluate.do?orderNo=${orderDetail.orderNo}&itemNo=${orderDetail.itemNo}" />'>前往評價</a>
										</c:when>
										<c:when test="${orderStatus == '已簽收' && itemNoList.contains(orderDetail.itemNo)}">
											已評價
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
									<!-- 判斷結束-->
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
<!-- 訂單明細結束 -->