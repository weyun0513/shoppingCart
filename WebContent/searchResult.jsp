<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% pageContext.setAttribute("lastPage", request.getHeader("referer")); %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${item.itemName}</title>

<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui-1.10.4.custom.min.css" type="text/css" media="all" />
<!-- <link rel="stylesheet" href="//cdn.datatables.net/1.10.1/css/jquery.dataTables.css" type="text/css" media="all" /> -->

<!--jQuery-->
<script src="${ pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/jquery-ui-1.10.4.custom.min.js" type="text/javascript"></script>
<!-- <script src="//cdn.datatables.net/1.10.1/js/jquery.dataTables.min.js" type="text/javascript"></script> -->
<script>
var contentPath = "${pageContext.request.contextPath}";
var xmlHttpReq = null;
$(document).ready(function() {
	 $('#resultTb').dataTable( {
		 "pageLength": 5,
// 	        columnDefs: [ {
// 	            targets: [ 0 ],
// 	            orderData: [ 0, 1 ]
// 	        }, {
// 	            targets: [ 1 ],
// 	            orderData: [ 1, 0 ]
// 	        }, {
// 	            targets: [ 4 ],
// 	            orderData: [ 4, 0 ]
// 	        } ]
	    }
	 );
    
    $( "#dialog-message" ).dialog({
        autoOpen: false,
        modal: true,
        buttons: {
          Ok: function() {
            $( this ).dialog( "close" );
            if(xmlHttpReq.responseText == '請先登入'){
  				window.location.href="01_login/login.jsp";
  					}	
          }
        }
      });
    
    jQuery("#btnAddBookmark").click(addFunc);
} );


		function addFunc(){
			var itemArray = new Array();
			$('input:checkbox:checked[name="addBookmark"]').each(
					function(i) {
						itemArray[i] = this.value;
						}
					);
			xmlHttpReq = new XMLHttpRequest();
			var method = "POST";
			var url = contentPath + "/addBookmark"; 
			xmlHttpReq.open(method, url, true);
			xmlHttpReq.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlHttpReq.send("itemArray=" + itemArray);
			xmlHttpReq.onreadystatechange = function(){
			if(xmlHttpReq.readyState == 4 && xmlHttpReq.status == 200){
				$("#dialog-message" ).dialog("open");
				$("#dialogMsg").text(xmlHttpReq.responseText);
				}
			}
		}
</script>
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
							<li><a href="#"><img src="http://cecj04g2.cloudapp.net/Zproj0813_3/advclient/adv_mediaServlet.do?advno=200"
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
				<!-- Products -->
				<div class="products">
					<div class="cl">&nbsp;</div>

<div align=center>
<div id="dialog-message" title="提示">
<div id="dialogMsg">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>
<br>
<h1>查詢結果</h1><br>
<c:choose>
<c:when test="${not empty searchResult}">

關鍵字 "  <b>${searchText}</b> " 的查詢結果如下：
<br>
<span style="float:left"><input type="button" id="btnAddBookmark" value="加入我的最愛"/></span>
<table id="resultTb" class="display" cellspacing="0" width="100%">
<thead>
			<tr>
				<th>編號</th>
				<th>Photo</th>
                <th>商品名稱</th>
                <th>價格</th>
                <th>折扣</th>
            </tr>
</thead>
<tbody>

<c:forEach items="${searchResult}" var="i" varStatus="iStatus">
<tr>
<td  align = center >${i.itemNo}<br>
<input type="checkbox" name="addBookmark" value="${i.itemNo}">
</td>
<td  align = center >
<img width="130" height="150" src="ShowItemMedia?itemNo=${i.itemNo}&itemMediaNo=1"/>
</td>
<td><a href='<c:url value="/ShowItemDetail?itemNo=${i.itemNo}"/>'>${i.itemName}</a><br><br><span>
<form action="addToCart">
購買數量:
<select name="buyQty">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
</select>
<input type="hidden" name="itemClassNo" value="${i.itemClassNo}"/>
<input type="hidden" name="itemNo" value="${i.itemNo}"/>
<input type="hidden" name="itemName" value="${i.itemName}"/>
<input type="hidden" name="price" value="${i.price}"/>
<input type="hidden" name="discount" value="${i.discount}"/>
<input type="hidden" name="onSaleTime" value="${i.onSaleTime}"/>
<input type="hidden" name="offSaleTime" value="${i.offSaleTime}"/>
<input type="hidden" name="itemDscrp" value="${i.itemDscrp}"/>
<input type="submit" value="加入購物車"/>
</form>
</span></td>
<td>${i.price}</td>
<td>${i.discount}</td>

</c:forEach>
</tr>
</tbody>
</table>
</c:when>
<c:otherwise>
抱歉! <b>${searchText}</b> 查無相關結果! <br>
</c:otherwise>
</c:choose>
<a href='<c:url value="${lastPage}"/>'>回上一頁</a>
</div>
	<div class="cl">&nbsp;</div>
				</div>
				<!-- End Products -->
			</div>
			<!-- End Content -->
			<!-- Sidebar -->
			<div id="sidebar">
<%@include file="/03_mall/side4.jsp" %>
<%@include file="/SearchMore.jsp" %>
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