<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Set,proj.basic.item.controller.AddSugViewListService,javax.servlet.http.HttpSession,
    proj.basic.item.controller.SearchItemService,java.util.LinkedHashSet,
    proj.basic.orderDetail.model.OrderDetailVO,
    proj.basic.item.model.ItemVO"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
#cartList_drag { list-style-type: none; margin: 0; padding: 0; width: 210px; }
#cartList_drag li { margin: 3px 3px 3px 0; padding: 1px; float: left; width: 58px; height: 78px; font-size: 0.5em; text-align: center; background:url(/ccs/images/ui-bg_loop_25_000000_21x21.png); border:none}
.btn {
  display: inline-block;
  padding: 6px 12px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.42857143;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
}
.btn:focus,
.btn:active:focus,
.btn.active:focus {
  outline: thin dotted;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}
.btn:hover,
.btn:focus {
  color: #333;
  text-decoration: none;
}
.btn:active,
.btn.active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
          box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
}
.btn.disabled,
.btn[disabled],
fieldset[disabled] .btn {
  pointer-events: none;
  cursor: not-allowed;
  filter: alpha(opacity=65);
  -webkit-box-shadow: none;
          box-shadow: none;
  opacity: .65;
}
.btn-default {
  color: #333;
  background-color: #fff;
  border-color: #ccc;
}
.btn-default:hover,
.btn-default:focus,
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default {
  color: #333;
  background-color: #e6e6e6;
  border-color: #adadad;
}
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default {
  background-image: none;
}
.btn-default.disabled,
.btn-default[disabled],
fieldset[disabled] .btn-default,
.btn-default.disabled:hover,
.btn-default[disabled]:hover,
fieldset[disabled] .btn-default:hover,
.btn-default.disabled:focus,
.btn-default[disabled]:focus,
fieldset[disabled] .btn-default:focus,
.btn-default.disabled:active,
.btn-default[disabled]:active,
fieldset[disabled] .btn-default:active,
.btn-default.disabled.active,
.btn-default[disabled].active,
fieldset[disabled] .btn-default.active {
  background-color: #fff;
  border-color: #ccc;
}
.btn-default .badge {
  color: #fff;
  background-color: #333;
}
.btn-primary {
  color: #fff;
  background-color: #428bca;
  border-color: #357ebd;
}
.btn-primary:hover,
.btn-primary:focus,
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary {
  color: #fff;
  background-color: #3071a9;
  border-color: #285e8e;
}
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary {
  background-image: none;
}
.btn-primary.disabled,
.btn-primary[disabled],
fieldset[disabled] .btn-primary,
.btn-primary.disabled:hover,
.btn-primary[disabled]:hover,
fieldset[disabled] .btn-primary:hover,
.btn-primary.disabled:focus,
.btn-primary[disabled]:focus,
fieldset[disabled] .btn-primary:focus,
.btn-primary.disabled:active,
.btn-primary[disabled]:active,
fieldset[disabled] .btn-primary:active,
.btn-primary.disabled.active,
.btn-primary[disabled].active,
fieldset[disabled] .btn-primary.active {
  background-color: #428bca;
  border-color: #357ebd;
}
.btn-primary .badge {
  color: #428bca;
  background-color: #fff;
}

</style>
<script>
var contextPath = "${pageContext.request.contextPath}";
$(document).ready(function() {
	
	$("#infoDialog").dialog({
		 autoOpen: false,
	      modal: true,
	      buttons: {
	        Ok: function() {
	        	$("#infoDialog").dialog("close");
	      }
	   },
	});

  $("#cartList_drag").sortable({
  });
  $("#cartList_drag").disableSelection();
 
  /* $("#removeList").click(function(){
	  var itemsRemove= [];
	  $("input:checked").each(function(){
		  itemsRemove.push($(this).val());
	  });
	 
	  $.ajax({
		  url:contextPath + "/RemoveFromConsiderListServlet",
		  type:"POST",
		  data:"removeList=" + itemsRemove,
		  success:function(returnMsg){
			  if(returnMsg=='success'){
				  for(var i=0; i<itemsRemove.length; i++){
					  $("span[id='"+itemsRemove[i]+"']").parent().attr("name", "seatHere");
					  $("span[id='"+itemsRemove[i]+"']").remove();
				  }
			  }
		  }
	  });
  }); */
  
  /**2014/8/6 Huang 更改*************************************************/
  $("#removeList").click(removeList);

  function removeList(){
	  var itemsRemove= [];
	  $("input:checked").each(function(){
		  itemsRemove.push($(this).val());
	  });
	 
	  $.ajax({
		  url:contextPath + "/RemoveFromConsiderListServlet",
		  type:"POST",
		  data:"removeList=" + itemsRemove,
		  success:function(returnMsg){
			  if(returnMsg=='success'){
				  for(var i=0; i<itemsRemove.length; i++){
					  $("span[id='"+itemsRemove[i]+"']").parent().attr("name", "seatHere");
					  $("span[id='"+itemsRemove[i]+"']").remove();
				  }
			  }
		  }
	  });
  }
  
  $("#addToCart").click(function(){
	  var addItems= [];
	  $("input:checked").each(function(){
		  addItems.push($(this).val());
		  removeList(); //增加若增加購物車，則把考慮清單移除
		  
	  });
	  
	  $.ajax({
		  url: contextPath + "/AddItemsToCartServlet",
		  data: "addItems="+addItems,
		  type: "POST",
		  success: function(json){
			  $("#infoDialogMsg").html("商品已成功加入購物車<br>若要增加數量可至購物車頁面變更");
			  $("#infoDialog").dialog("open");
			  
			  var obj = jQuery.parseJSON( json );
			  $("#top_cart_Q").text(obj[0].cartQ);
			  $("#top_total_price").text('NT$' + obj[0].cartListPrice.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,').split("." , 1));
		  },		  
	  });
  });
});

/** 結束 2014/8/6 Huang 更改*************************************************/
</script>

<div id="infoDialog" title="確認">
	<div id="infoDialogMsg">
  <p>
<!--     <span class="ui-icon ui-icon-circle-check" style="float:left;margin:0 7px 50px 0;"></span> -->
  </p>
  </div>
</div>
			<ul id="cartList_drag">
				
<%-- 				<c:forEach items="${cartList}" var="item" varStatus="cartS"> --%>
<c:if test="${not empty consider}">
<input type="hidden" value="${consider.size()}" id="considerSize"/>
				<c:forEach items="${consider}" var="item" varStatus="cartS">
					<c:if test="${cartS.count <=8}">
						<li class="ui-state-default" style="width:58px;margin-right:2px;margin-left:2px">
							<span id="${item.itemNo}">
								<a href='<c:url value="/ShowItemDetail?itemNo=${item.itemNo}"/>'>
								<img src="${pageContext.request.contextPath}/ShowItemMedia?itemNo=${item.itemNo}&itemMediaNo=1" width="56" height="58"/>
								</a>
							<span style="width:58px;font-size:11px">$<fmt:parseNumber var="priceInt" integerOnly="true" type="number" value="${item.price}" />${priceInt}&nbsp;&nbsp;<input type="checkbox" name="doChkList" value="${item.itemNo}" style="folat:right"/></span>
							</span>
						</li>
					</c:if>
				</c:forEach>
</c:if>
<%
int cartCount = 0;
if(session.getAttribute("consider") != null)
	cartCount =  ((LinkedHashSet<ItemVO>) session.getAttribute("consider")).size();
while(cartCount<8){
%>
<li  class="ui-state-default" name="seatHere">
</li>
<%
cartCount++;
}
%>
<li>
<img id="removeList" style="margin-top:50px" src="${pageContext.request.contextPath}/images/removeIcon.png" width="25px" style="margin-left:2px">
<img id="addToCart" style="margin-top:50px" src="${pageContext.request.contextPath}/images/shopping_cart.png" width="25px" style="margin-left:2px">
</li> 
	</ul>	
