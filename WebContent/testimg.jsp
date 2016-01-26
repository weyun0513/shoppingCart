<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="CopyOfShowItemMedia" method="POST">
<table>
<tr><td><input type="text" name="itemNo"/></td></tr>
<!-- <tr><td><input type="text" name="itemMediaNo"/></td></tr> -->
<tr><td><input type="submit" value="送出"></td></tr>
</table>
</form>


<%-- <embed src = "CopyOfShowItemMedia?itemNo=${itemMedia.itemNo}&itemMediaNo=${itemMedia.itemMediaNo}" width="320" height="266"  type="application/x-ms-wmp"/>${itemMedia.itemMedia} --%>
<!-- <embed src="CopyOfShowItemMedia?itemNo=6&itemMediaNo=3" width="320" height="266"  type="application/x-ms-wmp"/> -->
<c:forEach items="${itemMedia}" var="i">
商品編號：${i.itemNo}<br>
商品媒體檔編號：${i.itemMediaNo}<br>
媒體檔類型：${i.mediaType}<br>
<c:choose>
<c:when test="${i.mediaType!='image/jpeg'}">
<video  src="CopyOfShowItemMedia?itemNo=${i.itemNo}&itemMediaNo=${i.itemMediaNo}"  controls="controls">
您的瀏覽器不支援播放
</video> 
</c:when>
<c:otherwise>
<img src="CopyOfShowItemMedia?itemNo=${i.itemNo}&itemMediaNo=${i.itemMediaNo}"/><br>
</c:otherwise>
</c:choose>
</c:forEach>
</body>
</html>