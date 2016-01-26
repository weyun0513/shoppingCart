<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/table_coustom.css" type="text/css" media="all" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 訂單查詢開始 -->
				<h3>訂單查詢</h3>

				<h4>會員編號: ${memberVO.memberID}</h4>
				<h4>會員名稱: ${memberVO.chineseName}</h4>

				<c:if test="${not empty noneOrder}">
					<h4>${ noneOrder }</h4>
				</c:if>

				<c:if test="${not empty orderListList}">
					<div class="CSSTableGenerator" >
						<table>
								<tr>
									<td>訂單編號</td>
									<td>訂購日期</td>
									<td>訂單總金額</td>
									<td>送貨地址</td>
									<td>訂單狀態</td>
									<td>申請退貨</td>
								</tr>
							<c:forEach items="${orderListList}" var="orderList">
								<tr>
									<td>
										<a href='<c:url value="/query.do?action=query_detail&orderNo=${orderList.orderNo}&orderStatus=${orderList.orderStatus}" />'>${orderList.orderNo}</a>
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${orderList.orderDate}"/></td>
									<td>${orderList.totalPrice}</td>
									<td>${orderList.shippingAddr}</td>
									<td>${orderList.orderStatus}</td>
									<td>
										<!-- 判斷訂單狀態-->
										<c:choose>
											<c:when test="${orderList.orderStatus == '已簽收'}">
												<a
													href='<c:url value="/refund.do?action=refund&orderNo=${orderList.orderNo}" />'>退貨申請</a>
											</c:when>
											<%-- <c:when test="${orderList.orderStatus == '退貨處理中'}">
													退貨處理中
												</c:when>
												<c:when test="${orderList.orderStatus == '已退貨'}">
													已退貨
												</c:when> --%>
										</c:choose>
										<!-- 判斷結束-->
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>	
				</c:if>
<!-- 訂單查詢結束 -->