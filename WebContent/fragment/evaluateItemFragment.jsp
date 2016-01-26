<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 評價商品Evaluate開始 -->
			<c:if test="${not empty orderListList}">
					${evaluated}
			</c:if>
			
			<form action='<c:url value="/evaluate.do" />' method="post">
				<table>
					<thead><tr><td></td><td></td><td></td></tr></thead>
					<tr>
						<td>評價星級</td>
						<td>
							<select name="evaluateStar">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
						</td>
						<td>${errorMsg.evaluateStar }</td>
					</tr>
					<tr>
						<td>商品評語</td>
						<td><textarea name="evaluateBody" rows="4" cols="50">${param.evaluateBody}</textarea></td>
						<td>${errorMsg.evaluateBody }</td>
					</tr>
					<tr><td></td>
						<td>
							<input name="action" value="evaluate" type="hidden">
							<input name="orderNo" value="${orderNo }" type="hidden">
							<input name="itemNo" value="${ itemNo}" type="hidden">
							<input type="submit" value="送出">
						</td>
					</tr>
				</table>
			</form>
<!-- 評價商品Evaluate結束 --> 