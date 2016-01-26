<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"	href="${pageContext.request.contextPath }/css/style.css"
	type="text/css" media="all" />
<script src="${pageContext.request.contextPath }/js/jquery-1.4.1.min.js" type="text/javascript"></script>
<script>
jQuery(document).ready(
		function(){
			jQuery("#addItemMedia").click(
					function(){
						jQuery("tr:last").after("<tr><td>商品圖案</td><td><input type='file' name='itemMedia'/></td></tr>");
					});
		});


</script>
</head>
<body>
<table>
<tr><td>商品名稱</td><td></td></tr>
<tr><td>商品類型</td><td></td></tr>
<tr><td>商品主圖檔</td><td><input type='file' name='itemMedia'/></td></tr>
</table>
<input type="button" value="增加檔案" id="addItemMedia" onclick="addItemMedia()"/>
</body>
</html>