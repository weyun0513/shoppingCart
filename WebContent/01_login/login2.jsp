<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List,proj.basic.itemClass.controller.ItemClassService,proj.basic.itemClass.model.ItemClassVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% pageContext.setAttribute("activeTimer",new java.util.Date()); %>
<script>
jQuery(document).ready(
		function(){
			jQuery("input").click(
					function(){
						var thisId = jQuery(this).attr("name");
						jQuery("#"+thisId).text("");
					});
			jQuery("input").blur(
					function(){
						var thisId = jQuery(this).attr("name");
						if(jQuery(this).val().trim().length == 0){
								jQuery("#" + thisId).text("請勿空白");
							
						}
							
					});
		});

function reloadImg(){
	document.getElementById("verifyImg_e").src="${pageContext.request.contextPath}/verifyImg?"+ new Date().getTime();
}
</script>
<style type="text/css">  
.form1-control {
  display: block;
  width: 300px;
  height: 34px;
  padding: 6px 12px;
  font-size: 14px;
  line-height: 1.42857143;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
          box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
  -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
       -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
          transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}
.form1-control:focus {
  border-color: #66afe9;
  outline: 0;
  -moz-box-shadow: 0 0 2px 2px #66afe9;
-webkit-box-shadow: 0 0 2px 2px #66afe9;
box-shadow: 0 0 2px 2px #66afe9;
  
}
.form1-control::-moz-placeholder {
  color: #777;
  opacity: 1;
}
.form1-control:-ms-input-placeholder {
  color: #777;
}
.form1-control::-webkit-input-placeholder {
  color: #777;
}
.form1-control[disabled],
.form1-control[readonly],
fieldset[disabled] .form1control {
  cursor: not-allowed;
  background-color: #eee;
  opacity: 1;
}
textarea.form1-control {
  height: auto;
}
.form1-group {
  margin-bottom: 15px;
}
</style>
<form>
<input type="hidden" name="action" value="loginInclude"/>
<input type="hidden" name="needLogin" value="${param.needLogin}"/>
  <div class="form1-group">
    <label for="exampleInputEmail1">帳號</label>
    <input type="text" class="form1-control" name="account" id="accountText" placeholder="Enter account"><br><span id="account" style="color:red;font-size:16px;font-weight:bolder;float:right">${errorMsg}${errorMsgMap.accountEmpty}</span>
  </div>
  <div class="form1-group">
    <label for="exampleInputPassword1">密碼</label>
    <input type="password" class="form1-control" name="pwd" id="pwdText" placeholder="Password"><br><span id="pwd" style="color:red;font-size:16px;font-weight:bolder;float:right">${errorMsgMap.pwdEmpty}</span>
  </div>
  <div class="form1-group">
    <label for="exampleInputPassword1">驗證碼</label>
    <input type="text" class="form1-control" name="verifyText" id="verifyText" placeholder="Enter code"><br>
    <img src="${pageContext.request.contextPath}/verifyImg?lrn=${activeTimer}" id="verifyImg_e"/>
    <input type="button" value="換一張" onclick="reloadImg()" id="reloadBtn" />
  </div>
  <span style="color:#0053ed;font-size:18px;font-weight:bolder;float:right">
${errorMsgMap.accountError}
${errorMsgMap.verifyCodeEmpty}
${errorMsgMap.verifyError}
${errorMsgMap.isBlack}
</span>
</form>
<h3><a href="<c:url value='/index.jsp' />">回首頁</a></h3>
<h3><a href="<c:url value='/regist.jsp' />">註冊</a></h3>
<h3><a href="<c:url value='/askPwdPage.jsp' />">忘記密碼</a></h3>
<!-- <input type="hidden" name="action" value="login"/> -->
<%-- 帳號 :	<input type="text" name="account" value="${account}" id="accountText"/><span id="account"></span>${errorMsg}${errorMsgMap.accountEmpty}<br> --%>
<%-- 密碼 :	<input type="password" name="pwd" id="pwdText"/><span id="pwd"></span>${errorMsgMap.pwdEmpty}<br> --%>
<!-- 驗證碼:	<input type="text" name="verifyText" id="verifyText"/><br><img src="verifyImg" id="verifyImg"/><input type="button" value="refresh" onclick="reloadImg()" id="reloadBtn" /> -->
<%-- <input type="hidden" name="needLogin" value="${param.needLogin}"/> --%>
