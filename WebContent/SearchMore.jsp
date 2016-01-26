<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
進階搜尋<br>
<%-- <form action="searchItem?searchText=${searchText}&itemType=${itemType}" method="get"> --%>
<form action="searchItem" method="get">
<input type="hidden" name="searchText" value="${searchText}"/>
<input type="hidden" name="itemType" value="${itemType}"/>
<table>
<tr><td>價格 <input type="text" name="minPrice"/> 元 ~ <input type="text" name="maxPrice"/> 元</td></tr>
<tr><td><input type="checkbox" name="onSale"> 最近一個月上市</td></tr>
<tr><td><input type="checkbox" name="discount"> 折扣商品</td></tr>
<c:if test="${not empty classList}">
<tr><td>縮小搜尋類別</td></tr>
<c:forEach items="${classList}" var="iClass">
<tr><td><input type="checkbox" name="multiSearchBox" value="${iClass.itemClassNo}">${iClass.className}</checkbox></td></tr>
</c:forEach>
</c:if>
</table>
<input type="hidden" name="multiSearch" value="true"/>
<input type="submit" value="搜尋"/>
</form>
</html>