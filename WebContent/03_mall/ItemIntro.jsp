<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% pageContext.setAttribute("lastPage", request.getHeader("referer")); %>
<head>
<title>${item.itemName}</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.theme.min.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.structure.min.css" type="text/css" media="all" />

<!--jQuery-->
<script src="${ pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/jquery-ui.js" type="text/javascript"></script>

<!-- 2014/8/1 Huang 新增評價 bootstrap star Script -->
<script src='${pageContext.request.contextPath }/js/jquery.MetaData.js' type="text/javascript" language="javascript"></script>
<script src='${pageContext.request.contextPath }/js/jquery.rating.js' type="text/javascript" language="javascript"></script>
<link href='${pageContext.request.contextPath }/css/jquery.rating.css' type="text/css" rel="stylesheet"/>
<!-- 結束2014/8/1 Huang 新增評價 bootstrap star Script -->

<script type="text/javascript">


var xmlHttpReq = null;
var downurl = null;
var contentPath = "${pageContext.request.contextPath}";
var member = "${memberVO}";
$(document).ready(function() {
	$("#dialogInfo").dialog({
			autoOpen: false,
	        modal: true,
	        width: 420,
			buttons:{'確認':function(){
				$(this).dialog("close");
			}
				}
	});
	$("#dialogLogin").dialog({
		autoOpen: false,
        modal: true,
        width: 420,
		buttons:{
			'登入': function(){
				$.ajax({
					url: contentPath + "/login",
					type: "POST",
					data:"ajx=true&account=" + $("#accountText").val() + "&pwd=" + $("#pwdText").val() + "&verifyText=" + $("#verifyText").val(),
					success: function(returnMsg){
						if(returnMsg == 'success')
							location.reload();
						else
							$("#dialogLoginMsg").html($("#dialogLoginMsg").load("${pageContext.request.contextPath}/01_login/login2.jsp"));
					}
					
				});
			},
			'取消': function(){
				$(this).dialog("close");
			}
		}
	});
	
	 $( "#dialog-send" ).dialog({
		 autoOpen: false,
	        modal: true,
// 	        width: 420,
	        buttons: {
				'確認': function() {
					if($("#addrSendTo").val().indexOf("@")!=-1){
						$.ajax({
							url:  contentPath +"/SendItemByEmail",
							type: "POST",
							data:  "addrSendTo=" + $("#addrSendTo").val(),
							success: function(returnMsg){
								if(returnMsg == 'success'){
//	 							$("#dialog-send").dialog("open");
								$("#dialogInfo").dialog("open");
								$("#dialogInfoMsg").text("郵件已通知系統發送! 謝謝您的支持!");
								}else {
//	 								$("#dialog-send").dialog("open");
									$("#dialogInfo").dialog("open");
									$("#dialogInfoMsg").text("請檢查郵件地址");
								}
							}
						});
					}else{
						$("#dialogInfo").dialog("open");
						$("#dialogInfoMsg").text("請輸入正確email地址");
					}
	            $( this ).dialog( "close" );
				}
	        ,'取消':function(){
					 $( this ).dialog( "close" );
				}
	        },
	      });
	 
	 jQuery("#sendOut").click(
				function(){
//	 				$("#dialog-send").dialog("open");
					
					if(member.length != 0){
						 $( "#dialog-send" ).dialog("open");
						$("#dialogMsg-send").html("請輸入收件人mail地址：<br><br><span><input type='text' id='addrSendTo'/></span>");
						
					}else{
						 $( "#dialogLogin" ).dialog("open");
						$("#dialogLoginMsg").html($("#dialogLoginMsg").load("${pageContext.request.contextPath}/01_login/login2.jsp"));
					}
					
				}
		);
	 
	//增加清單圖片
	$("#addList").dialog({
		 autoOpen: false,
	      modal: true,
	      buttons: {
	        Ok: function() {
	        	$("#addList").dialog("close");
	        	var existItemNo = [];
	        	$("#cartList_drag >li").not("[name='seatHere']").children("span").each(
	        			function(){
	        				existItemNo.push($(this).attr("id"));
	        			});

	        	var itemNo = $("#putInList").attr("name");
	        	var setOrNot = 1;
	        	for(var i=0; i<existItemNo.length;i++){
	        		if(itemNo==existItemNo[i]){
	        			setOrNot = 0;
	        			break;
	        		}
	        	}
	        	
	        	if(setOrNot==1){
	        		var price = $("#addPrice").text().split(".")[0];
		        	var span = '<span id="'+itemNo+'">';
		        	var hrefthis ="<a href=" +contentPath + "/ShowItemMedia?itemNo=" +itemNo+ ">";
		        	var imgSrc = "<img src='" + contentPath + "/ShowItemMedia?itemNo=" +itemNo+ "&itemMediaNo=1'" + " width='56' height='58'/></a><span width='58px'>$" + price + "&nbsp;&nbsp;";
		        	var box = '<input type="checkbox" name="doChkList" value="' + itemNo + '" style="folat:right"/></span></span>';
		        	$("#cartList_drag >li").not("[name!='seatHere']").slice(0,1).append(span + hrefthis + imgSrc + box);
		        	$("#cartList_drag >li").not("[name!='seatHere']").slice(0,1).removeAttr("name");
	        	}
	        	
	      }
	   },
	});
	
	$("#putInList").click(function(){
		if($("#considerSize").val() == 8){
			$("#addList").dialog({
				 autoOpen: false,
			      modal: true,
			      buttons: {
			        Ok: function() {
			        	$("#addList").dialog("close");
			      }
			   },
			});
			$("#addList").dialog("open");
			$("#addList-Msg").text("考慮清單最多八項, 請先移除考慮清單內的項目");
		}else{
			$.ajax({
				url: contentPath + "/AddToConsider",
				data:"itemNo=" + $(this).attr("name"),
				type:"POST",
				success:function(returnMsg){
					if(returnMsg == 'success'){
						$("#addList").dialog("open");
						$("#addList-Msg").text("已成功加入考慮清單, 頁面左下角可點選查看");
					}
				}
				});
		}
	});
	
	$( "#tabs" ).tabs();

	
    $( "#dialog-message" ).dialog({
      autoOpen: false,
      modal: true,
      buttons: {
        Ok: function() {
          $( this ).dialog( "close" );
          if(xmlHttpReq.responseText == '請先登入'){
        	  loginWay();
// 				window.location.href= contentPath+"/01_login/login.jsp";
					}	
        }
      }
    });
    
    $( "#dialog-download" ).dialog({
        autoOpen: false,
        modal: true,
        buttons: {
			'確認': function() {
            window.open(downurl);
            $( this ).dialog( "close" );
          },
         '取消':function(){
				 $( this ).dialog( "close" );
			}
        }
      });
    

	jQuery("#btnAddBookmark").click(
			function(){
				var itemNo = "${item.itemNo}";
				xmlHttpReq = new XMLHttpRequest();
				var method = "GET";
				var url = contentPath + "/addBookmark?itemNo=" + itemNo + "&dt=" + new Date().getTime(); 
				xmlHttpReq.open(method, url, true);
				xmlHttpReq.send();
				
				xmlHttpReq.onreadystatechange = function(){
				
					if(xmlHttpReq.readyState == 4 && xmlHttpReq.status == 200){
					$("#dialog-message" ).dialog("open");
					$("#dialogMsg").text(xmlHttpReq.responseText);
					 $( this ).dialog( "close" );
					}
				};   
						
			});
	
	jQuery("#downFile").change(function(){
		
		if($("#downFile").val() == 'doc' || $("#downFile").val() == 'pdf')
			jQuery("#downFileBtn").css("display","inline");
		if($("#downFile").val() != 'doc' && $("#downFile").val() != 'pdf')
			jQuery("#downFileBtn").css("display","none");
	
		jQuery("#downFileBtn").click(
			function(){
				var fileType = $("#downFile").val();
				downurl = contentPath + "/exportItem?itemNo=${item.itemNo}&fileType=" + fileType;
				
				if(fileType == 'doc')
					$("#dialogMsg-download").text("點選確認下載文件");
				if(fileType == 'pdf')
					$("#dialogMsg-download").html("點選確認開啟文件");
				
				$("#dialog-download").dialog("open");
			});
	});
});

</script>
</head>

<body>

<!-- Shell -->
<div class="shell">

 <div id="dialogInfo" title="提示">
<div id="dialogInfoMsg">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>

 <div id="dialog-message" title="提示">
<div id="dialogMsg">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>

 <div id="addList" title="提示">
<div id="addList-Msg">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>

<div id="dialog-download" title="確認">
<div id="dialogMsg-download">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>

<div id="dialog-send" title="轉寄給朋友">
<div id="dialogMsg-send">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>

<div id="dialogLogin" title="請先登入">
<div id="dialogLoginMsg">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>

  <!-- Header -->
 <%@include file="/top.jsp" %>

  <!-- End Search -->
  <!-- Main -->
		<div id="main">

			<div class="cl">&nbsp;</div>
			<!-- Content -->
			<div id="content">
				<!-- Content Slider -->
<!-- 				<div id="slider" class="box"> -->
<!-- 					<div id="slider-holder"> -->
<!-- 						<ul> -->
<!-- 							<li><a href="#"><img src="css/images/1.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="css/images/2.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="css/images/3.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="css/images/4.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 							<li><a href="#"><img src="css/images/5.jpg" -->
<!-- 									width="720" height="252" alt="" /></a></li> -->
<!-- 						</ul> -->
<!-- 					</div> -->
<!-- 					<div id="slider-nav"> -->
<!-- 						<a href="#" class="active">1</a> <a href="#">2</a> <a href="#">3</a> -->
<!-- 						<a href="#">4</a> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<!-- End Content Slider -->
				<!-- Products -->
				<div class="products">
				<div class="cl">&nbsp;</div>
				<div style="width:700px;float:right"></div>
<table>
<tr>
<td rowspan="5" width="200px" align = center >
<img width="230" height="280" src="${pageContext.request.contextPath}/ShowItemMedia?itemNo=${item.itemNo}&itemMediaNo=1"/><br>
<!-- <input type="button" id="btnAddBookmark" value="加到我的最愛"/> -->
<img src="${pageContext.request.contextPath}/images/addtofavorite1.jpg" id="btnAddBookmark" width="100px"/>
<!-- <input type="button" id="sendOut" value="推薦給朋友"/> -->
<img src="${pageContext.request.contextPath}/images/sugtof.jpg" id="sendOut" width="100px"/>
<select name="downFile" id="downFile">
<option value="defult">匯出商品文件</option>
<option value="doc">.doc文件</option>
<option value="pdf">.pdf文件</option>
</select>
<input type='button' name='chkDownload' value='確定' style="display:none" id="downFileBtn"/>
</td>
<%-- <td colspan="2" width="80px"><b>項目編號</b></td><td  width="280px">${item.itemNo}</td></tr> --%>
<tr><td colspan="2" width="80px"></td><td  width="280px" style="font-size:36px;font-weight:bold;color:black;line-height:46px;">${item.itemName}</td></tr>
<tr><td colspan="2" width="80px"><span style="float:right">建議售價</span></td><td  width="280px" ><span style="font-size:20px;text-decoration:line-through">$<span id="price"><fmt:formatNumber var="itemPrice" value="${item.price + 0.0001}" pattern="#,###,###,###"/>${itemPrice}</span></span></td></tr>
<tr><td colspan="2" width="80px"><span style="float:right">特價</span></td><td width="280px" style="font-size:36px;color:red"><c:choose><c:when test="${item.discount!=0}">$<fmt:formatNumber var="itemPrice" value="${(item.price*item.discount) + 0.0001}" pattern="#,###,###,###"/><span id="addPrice"> ${itemPrice}</span></c:when><c:otherwise>$ <fmt:formatNumber var="itemPrice" value="${item.price + 0.0001}" pattern="#,###,###,###"/><span id="addPrice"> ${itemPrice}</span></c:otherwise></c:choose></td></tr>
<tr><td colspan="3" width="360px">
<span style="margin-bottom:2px"><img src="${pageContext.request.contextPath}/images/addtolist.jpg" id="putInList" width="120px" style="float:right" name="${item.itemNo}"/></span>
<span  style="float:left">
<form action="${pageContext.request.contextPath}/addToCart" method="POST">
<c:if test="${item.itemsQty != 0}">
&nbsp;&nbsp;&nbsp;&nbsp;購買數量:
<!--2014/07/29 Huang 增加加入購物車時，數量與資料庫商品項目庫存做判斷。  -->
	<select name='buyQty'>
		<c:if test="${item.itemsQty > 0 }">
			<c:forEach begin="1" end="${ item.itemsQty < 10 ? item.itemsQty : 10}" var="i">
				<option value="${i}">${i}</option>
			</c:forEach>
		</c:if>
		<c:if test="${item.itemsQty <= 0 }">
			<option value="0">尚無庫存</option>
		</c:if>
	</select>
<!--結束2014/07/29  Huang 增加加入購物車時，數量與資料庫商品項目庫存做判斷。 -->

<input type="hidden" name="itemClassNo" value="${item.itemClassNo}"/>
<input type="hidden" name="itemNo" value="${item.itemNo}"/>
<input type="hidden" name="itemName" value="${item.itemName}"/>
<input type="hidden" name="price" value="${item.price}"/>
<input type="hidden" name="discount" value="${item.discount}"/>
<input type="hidden" name="onSaleTime" value="${item.onSaleTime}"/>
<input type="hidden" name="offSaleTime" value="${item.offSaleTime}"/>
<input type="hidden" name="itemDscrp" value="${item.itemDscrp}"/>
<!-- <input type="submit" value="加入購物車"/> -->
<img src="${pageContext.request.contextPath}/images/addtocart.jpg" id="sendOut" width="120px" height="52px" onclick="submit()"/>
</span>
</c:if>
</form>
<span style="color:red;font-size:18px;float:right">
<c:choose>
<c:when test="${item.itemsQty < 10 && item.itemsQty>5}">數量稀少!</c:when>
<c:when test="${item.itemsQty <= 5 && item.itemsQty>3}">最後銷售!</c:when>
<c:when test="${item.itemsQty <= 3 && item.itemsQty>0}">最後搶購機會!</c:when>
</c:choose>
<c:if test="${item.itemsQty <=0 }">
銷售一空
</c:if>
</span>
<%-- <img src="${pageContext.request.contextPath}/images/addtolist.jpg" id="putInList" width="120px" style="float:right" name="${item.itemNo}"/> --%>
</td></tr>
<tr>
	<c:if test="${item.itemsQty != 0}">
	<td colspan="4" width="80px"  align = center>
		<h2 style="color:black">商品說明</h2>
<!-- 			<span style="float:right"> -->
<%-- 			<input type="button" id="putInList" value="加入考慮清單" name="${item.itemNo}"/> --%>
<!-- 		</span> -->
	</td>
	</c:if>
</tr>
<!-- 2014/8/1 Huang 移動修改評價內容 ---------------------------------------------------------------->


	<c:if test="${ not empty eList}">
		<c:set value="${0}" var="average" />
		<c:forEach items="${eList}" var="e" varStatus="status">
			<c:set value="${average + e.evaluateStar}" var="average"/>
			<c:if test="${status.last}">
				<c:set value="${status.count }" var="count"/>
			</c:if>
		</c:forEach>
		
		<fmt:parseNumber var="averageStar" integerOnly="true" type="number" value="${average/count}" />
		
		<tr>
			<td rowspan="${count}"><span style="float:left">使用者評價:</span> 
				<c:forEach begin="1" end="5" varStatus="starStatus">
					<input name="star3" type="radio" class="star" disabled="disabled" ${ starStatus.count == averageStar ? 'checked="checked"':'' }/>
				</c:forEach>
			</td>
			<c:forEach items="${eList}" var="e" varStatus="statusShow">
				<c:choose>
					<c:when test="${not empty memberVO}">
						<td>評價詳細內容：${e.evaluateBody}</td></tr>
					</c:when>
					<c:when test="${statusShow.first}">
						<td>需<a href="${pageContext.request.contextPath}/01_login/login.jsp?needLogin=${item.itemNo}">登入</a>才可查看評價意見</td>
						</tr>
					</c:when>
					<c:otherwise>
						<td></td></tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	</c:if>
	
	
<!-- 結束2014/8/1 Huang 移動修改評價內容 ---------------------------------------------------------------->


<tr><td colspan="4" width="80px">
${item.itemDscrp}</td></tr>
<c:forEach var="i" items="${itemMedia}">
<tr><td colspan=4 align=center>
<c:choose>
<c:when test="${i.mediaType.substring(0,5) !='image'}">
<video  src="${pageContext.request.contextPath}/ShowItemMedia?itemNo=${i.itemNo}&itemMediaNo=${i.itemMediaNo}"  controls="controls">
您的瀏覽器不支援播放
</video><br> 
${i.mediaDscrp}<br>
</c:when>
<c:otherwise>
<img src="${pageContext.request.contextPath}/ShowItemMedia?itemNo=${i.itemNo}&itemMediaNo=${i.itemMediaNo}" width="600px"/><br>
${i.mediaDscrp}
</c:otherwise>
</c:choose>
<br>
</td></tr>
</c:forEach>
<c:forEach items="${eList}" var="e">
<tr><td>滿意度：${e.evaluateStar}</td>
<c:choose>
<c:when test="${not empty memberVO}">
<td colspan="4">評價詳細內容：${e.evaluateBody}</td></tr>
</c:when>
<c:otherwise>
<td>需<a href="${pageContext.request.contextPath}/01_login/login.jsp?needLogin=${item.itemNo}">登入</a>才可查看評價意見</td></tr>
</c:otherwise>
</c:choose>
</c:forEach>
</table>

<div style="text-align:center"><a href='<c:url value="${lastPage}"/>'>回上一頁</a></div>

<div class="cl">&nbsp;</div>
				</div>
				<!-- End Products -->
			</div>
			<!-- End Content -->
				
			<!-- Sidebar -->
<div id="sidebar">
<%@include file="/03_mall/side4.jsp" %>
<div id="tabs"  style="height:320px;padding:0px;width:230px">
  <ul>
    <li><a href="#tabs1"><span  style="font-size:12px">你可能會喜歡</span></a></li>
    <li><a href="#tabs2"><span  style="font-size:12px">考慮清單</span></a></li>
<%--     <li><img src="${pageContext.request.contextPath}/images/informationicon.png" width="28px" id="list_information"></li> --%>
  </ul>
  <div id="tabs1">
   <%@include file="/03_mall/sugItem_Side.jsp" %>
  </div>
  <div id="tabs2">
    <%@include file="/03_mall/showCart.jsp" %>
  </div>
  
</div>

			</div>
			<!-- End Sidebar -->
			<div class="cl">&nbsp;</div>
			
		<!-- End Main -->

					<%@include file="/FootItems.jsp" %>

		<!-- Footer -->
		<div id="footer" style="position: relative;">
			<table>
				<div id="Div1" class="container_48">
					<span style="padding-left: 15px"><a href="http://www.lativ.com.tw/Page/About">聯絡WOW</a></span>
					<span style="padding-left: 15px"><a href="http://www.lativ.com.tw/Page/About">購物說明</a></span> 
					<span style="padding-left: 15px"><a href="http://www.lativ.com.tw/Page/About">品牌日誌</a></span> 
					<span style="padding-left: 15px"><a href="http://www.lativ.com.tw/Page/About">網站使用條款</a></span> 
					<span style="padding-left: 15px"><a href="http://www.lativ.com.tw/Page/About">隱私權政策</a></span> 
					<span style="padding-left: 15px"><a href="http://www.lativ.com.tw/Page/About">免責聲明</a></span> 
					<span style="padding-left: 15px"><a href="http://www.lativ.com.tw/Page/About">最新消息</a></span> 
					<span style="padding-left: 260px"> ©2014 MEGO,Inc. </span>
				</div>
			</table>
		</div>
		<!-- End Footer -->
</div>
		<!-- End Shell -->
</body>
</html>