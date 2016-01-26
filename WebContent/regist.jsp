<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="all" />
<link href="${pageContext.request.contextPath }/jquery-ui.css" rel="stylesheet"> <!-- 日曆 -->

<!--jQuery-->
<script src="${ pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath }/external/jquery/jquery.js"></script><!-- 日曆 -->
<script src="${pageContext.request.contextPath }/jquery-ui.js"></script><!-- 日曆 -->
<style type="text/css">
.image-proview {
	height:100px; 
	width:120px; 
	background-color:#999999;
	line-height:100px;
	text-align:center;
	border:1px #CCCCCC solid;
	font-size:15px; color:#CCCCCC;
}

#preview_wrapper {
	display:inline-block;
	width:300px;
	height:300px;
	background-color:#CCC;
}
#image-proview-layer {
	/* 該對象用於在IE下顯示預覽圖片 */
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);
}

#preview_size_fake {
	/* 該對象只用來在IE下獲得圖片的原始尺寸，無其它用途 */
	filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
	visibility:hidden;
}

#preview {
	/* 該對象用於在FF下顯示預覽圖片 */
	width:300px;
	height:300px;
}
</style>

	
<script>
jQuery(document).ready(
		function() {
		    $( "#birthday" ).datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat: "yy-mm-dd",
		      minDate: new Date('1914','01','01'),
		      maxDate: new Date('1994','11','31'),
		      defaultDate:"88-01-01",
		    });
		    
		    jQuery("input").click(function(){
		    	var id = $(this).attr('id');
    			jQuery("span[name='"+id+"']").text('');
		    	
		    });
		   
		    jQuery("input").blur(
		    		function(){
		    			var id = $(this).attr('id');
		    			jQuery("span[name='"+id+"']").text('');
		    			var postData = null;
		    			if($(this).attr('id')!='pwdChk')
		    				postData = 'passby=ajax&'+$(this).attr('id') + '=' + $(this).val();
		    			else
		    				postData = 'passby=ajax&'+$(this).attr('id') + '=' + $(this).val() + '&pwd=' + $("#pwd").val();
		    			jQuery.ajax({
		    				url: "register",
		    				type: "POST",
		    				data: postData,
		    				success: function(returnMsg){
		    					jQuery("span[name='"+id+"']").html(returnMsg);
		    				}
		    			})
		    });
		  })//End of ready
	
		  
		  var isIE=function() {
			   return (document.all) ? true : false;
			}

			function ImagesProview(obj) {

				var newPreview = document.getElementById("image-proview-layer");

				var imagelayer = document.getElementById('image-proview') 
				if(imagelayer){
					newPreview.removeChild(imagelayer);
				}

				if (isIE()) {

					obj.select();  
					var imgSrc = document.selection.createRange().text;  
					var objPreviewFake = document.getElementById('image-proview-layer');
					objPreviewFake.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;  

				} else {

					window.URL = window.URL || window.webkitURL;

					newPreview.innerHTML = "<img src='"+window.URL.createObjectURL(obj.files[0])+"' id='image-proview' height='100' width='120' />"

				}
			}

			function autoSizePreview( objPre, originalWidth, originalHeight ) {  
				var zoomParam = clacImgZoomParam( 300, 300, originalWidth, originalHeight );  
				objPre.style.width = zoomParam.width + 'px';  
				objPre.style.height = zoomParam.height + 'px';  
				objPre.style.marginTop = zoomParam.top + 'px';  
				objPre.style.marginLeft = zoomParam.left + 'px';  
			}  
		      
		    function clacImgZoomParam( maxWidth, maxHeight, width, height ) {  
		        var param = { width:width, height:height, top:0, left:0 };  
		          
		        if ( width>maxWidth || height>maxHeight ) {  
		            rateWidth = width / maxWidth;  
		            rateHeight = height / maxHeight;  
		              
		            if( rateWidth > rateHeight ) {  
						param.width =  maxWidth;  
						param.height = height / rateWidth;  
		            } else {  
						param.width = width / rateHeight;  
						param.height = maxHeight;  
		            }  
		        }  
		          
		        param.left = (maxWidth - param.width) / 2;  
		        param.top = (maxHeight - param.height) / 2;  
		          
		        return param;  
		    }  
  </script>

<title>Regist Form</title>
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
							<li><a href="#"><img src="../css/images/1.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="../css/images/2.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="../css/images/3.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="../css/images/4.jpg"
									width="720" height="252" alt="" /></a></li>
							<li><a href="#"><img src="../css/images/5.jpg"
									width="720" height="252" alt="" /></a></li>
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
<form action="register" method="post" enctype="multipart/form-data">
<table>
<tr><td>註冊帳號</td><td><span><input type="text" name="account" id="account"/></span><span name="account" style="float:right"></span></td></tr>
<tr><td>密碼</td><td><span><input type="password" name="pwd" id="pwd"/></span><span name="pwd" style="float:right"></span></td></tr>
<tr><td>密碼</td><td><span><input type="password" name="pwdChk" id="pwdChk"/></span><span name="pwdChk" style="float:right"></span></td></tr>
<tr><td>中文姓名</td><td><span><input type="text" name="chineseName" id="chineseName"/></span></td></tr>		
<tr><td>性別</td><td><span><select name="gender"><option value="男">男</option><option value="女">女</option></select></span></td></tr>
<tr><td>生日</td><td><span><input type="text" name="birthday" id="birthday"/></span></td></tr>
<tr><td>E-mail</td><td><span><input type="text" name="email" id="email"/></span><span name="email" style="float:right"></span></td></tr>
<tr><td>地址</td><td><span><input type="text" name="addr"/></span></td></tr>		
<tr><td>電話</td><td><span><input type="text" name="phone"/></span></td></tr>		
<tr><td>照片</td><td><span><input type="file" name="photo" onchange="ImagesProview(this)"/></span></td></tr>
<tr><td>
	<div class="image-proview" id="image-proview-layer">
		<!--<img src="" id="image-proview" height="80" width="100" />-->
		<span id="image-layer">圖片預覽</span>
	</div>
</td></tr>
<tr><td colspan="2"><span><input type="submit" value="送出"></span></td></tr>		
</table>
</form>
<%-- <c:forEach items="${errorMsg}" var="i"> --%>
<%-- ${i.value}<br> --%>
<%-- </c:forEach> --%>

		</div>
				<!-- End Products -->
			</div>
			<!-- End Content -->
			<!-- Sidebar -->
			<div id="sidebar">


<%-- <%@include file="/03_mall/leftSelect.jsp" %> --%>
<%@include file="/03_mall/side4.jsp" %>
			</div>
			<!-- End Sidebar -->
			<div class="cl">&nbsp;</div>
		</div>
		<!-- End Main -->

					<%@include file="/FootItems.jsp" %>

		<!-- Footer -->
		<div id="footer" style="position: relative;">
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



		</div>
		<!-- End Footer -->

		<!-- End Shell -->
</body>



</html>