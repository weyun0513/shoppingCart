<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.min.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.theme.min.css" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery-ui.structure.min.css" type="text/css" media="all" />

<link rel="stylesheet" href="//cdn.datatables.net/1.10.1/css/jquery.dataTables.css" type="text/css" media="all" />
<!--jQuery-->
<style type="text/css">
.no-close .ui-dialog-titlebar-close {
  display: none;
}
</style>
<script src="${ pageContext.request.contextPath }/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/jquery-ui.js" type="text/javascript"></script>
<script src="//cdn.datatables.net/1.10.1/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/logoutset.js" type="text/javascript"></script>
<script>
var contentPath = "${pageContext.request.contextPath}";
var memberCHK = "${memberVO}";

$(document).ready(function(){

	if(memberCHK.length != 0){
		inactivityTime();
	};
	chkLogin();
	$("#loginBtn").click(loginWay);
	
	$("#loginSpace").dialog({
		autoOpen: false,
        modal: true,
        width: 420,
		buttons:{
			'登入': function(){
				$.ajax({
					url: contentPath+"/login",
					type: "POST",
					data:"ajx=true&account=" + $("#accountText").val() + "&pwd=" + $("#pwdText").val() + "&verifyText=" + $("#verifyText").val(),
					success: function(returnMsg){
						
						if(returnMsg == 'success')
							location.reload();
						else
							$("#loginSpace-Msg").html($("#loginSpace-Msg").load(contentPath + "/01_login/login2.jsp"));
					}
					
				});
			},
			'取消': function(){
				$(this).dialog("close");
			}
		}
	});
	$("#memberSpace").dialog({
		autoOpen: false,
        modal: true,
        width: 420,
		buttons:{
			'登入': function(){
				$.ajax({
					url: contentPath+"/login",
					type: "POST",
					data:"ajx=true&account=" + $("#accountText").val() + "&pwd=" + $("#pwdText").val() + "&verifyText=" + $("#verifyText").val(),
					success: function(returnMsg){
						if(returnMsg == 'success')
							window.location.href = contentPath +"/02_member/memberInfo.jsp";
						else
							$("#memberSpace-Msg").html($("#memberSpace-Msg").load(contentPath + "/01_login/login2.jsp"));
					}
					
				});
			},
			'取消': function(){
				$(this).dialog("close");
			}
		}
	});
});
function loginWay(){
	$("#loginSpace").dialog("open");
	$("#loginSpace-Msg").html($("#loginSpace-Msg").load(contentPath +"/01_login/login2.jsp"));
}
function memberLogin(){
	$("#memberSpace").dialog("open");
	$("#memberSpace-Msg").html($("#memberSpace-Msg").load(contentPath +"/01_login/login2.jsp"));
}

function chkLogin(){
	if(memberCHK.length == 0 )
		$("#memberBtn").click(memberLogin);
	else
		$("#memberBtn").attr("href", contentPath +"/02_member/memberInfo.jsp");
}

</script>
<% ItemClassService iClassSrv = new ItemClassService();
List<ItemClassVO> iClassList = iClassSrv.getAllAlive(); 
pageContext.setAttribute("iClassList", iClassList);


%>


<div id="loginSpace" title="登入" >
<div id="loginSpace-Msg"></div>
</div>

<div id="memberSpace" title="登入" >
<div id="memberSpace-Msg"></div>
</div>
<%
String pathHere = request.getRequestURI();
if(pathHere.lastIndexOf(".")!=-1)
	pathHere = pathHere.substring(pathHere.lastIndexOf("/")+1,pathHere.lastIndexOf("."));
pageContext.setAttribute("pathHere", pathHere);
%>
  <div id="header">
    <h1 id="logo"><a href="<c:url value='/index.jsp'/>">shoparound</a></h1>

    <!-- Cart -->
    <div id="cart"> <a href="<c:url value='/shoppingCart.jsp'/>" class="cart-link">您的購物車</a>
      <div class="cl">&nbsp;</div>
      <span>品項: <strong id="top_cart_Q"><c:choose><c:when test="${not empty cartListQty}">${cartListQty}</c:when><c:otherwise>0</c:otherwise></c:choose></strong></span> &nbsp;&nbsp; 
      <span>總價:
	      <strong id="top_total_price">
	      <c:choose>
		      <c:when test="${not empty cartListQty }">
		      	<fmt:formatNumber var="aCartListPrice" type="currency" maxFractionDigits="0" value="${cartListPrice}" />
		      	<c:out value="${aCartListPrice}"/>
		      </c:when>
		      <c:otherwise>0</c:otherwise>
	      </c:choose>
	      </strong>
	      </span> 
	</div>
    <!-- End Cart -->
    <!-- Navigation -->
    <div id="navigation">
      <ul> 
      <c:if test="${empty memberVO && pathHere != 'login'}">
        <li><a href="#" id="loginBtn">登入/註冊</a></li>
        </c:if>
		<c:forEach items="${iClassList}" var="iClass" varStatus="iStatus">
		<c:if test="${iStatus.count <= 5}">
         <li><a href='<c:url value="/ShowItems?classNo=${iClass.itemClassNo}&pageNo=1"/>'>${iClass.className}</a></li>
         </c:if>
       </c:forEach>
        <li>
        <c:if test="${pathHere != 'login'}">
        <a href="#" id="memberBtn">會員中心</a></li>
        </c:if>
        <c:if test="${not empty memberVO}">
        <li><a href='<c:url value="/index.jsp?action=logout"/>'>登出</a></li>
        </c:if>

      </ul>
    </div>
    <!-- End Navigation -->
  </div>
  <!-- End Header -->
    <!-- Search -->
<h2 style="color:black">

<c:if test="${empty memberVO}">
訪客
</c:if>
${memberVO.account}, welcome! 
</h2>
<div style="float:right">
<form action="${pageContext.request.contextPath}/searchItem" method="GET">
關鍵字搜尋<select name="itemType">
<option value="0">所有商品</option> 
<c:forEach items="${iClassList}" var="iClass">
         <option value="${iClass.itemClassNo}">${iClass.className}</option> 
</c:forEach>
</select>
<c:choose>
<c:when  test="${empty TextNullMsg}">
<input type="text" name="searchText" placeholder="請輸入關鍵字"/>
</c:when>
<c:otherwise>
<input type="text" name="searchText" placeholder="${TextNullMsg}"/>
</c:otherwise>
</c:choose>
<input type="hidden" name="multiSearch" value="false"/>
<input type="hidden" name="pageNo" value="1"/>
<input type="submit" value="搜尋"/>

</form>
</div>

