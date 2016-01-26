<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Set,proj.basic.item.controller.AddSugViewListService,javax.servlet.http.HttpSession,proj.basic.item.controller.SearchItemService"%>
<%
if(request.getParameter("itemNo")==null && request.getParameter("classNo") == null)
 	request.setAttribute("sugList", new AddSugViewListService().getSugItemsDef());
if(request.getParameter("itemNo")!=null && request.getParameter("classNo") != null)
	request.setAttribute("sugList",new AddSugViewListService().getSugUserClassItems(request.getParameter("classNo")));
if(request.getParameter("itemNo")==null && request.getParameter("classNo") != null)
	request.setAttribute("sugList",new AddSugViewListService().getSugUserClassItems(request.getParameter("classNo")));
if(request.getParameter("itemNo")!=null && request.getParameter("classNo") == null){
	Integer classNo = new SearchItemService().findByItemNo(Integer.valueOf(request.getParameter("itemNo"))).getItemClassNo();
	request.setAttribute("sugList",new AddSugViewListService().getSugUserClassItems(classNo.toString()));
	request.setAttribute("classNo",classNo);
}
%>

<!-- jCarousel css-->
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/horizontal.css">
<!-- jCarousel core-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jcarousel-core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/core_plugin.js"></script>
<!-- jCarousel autoscroll plugin  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jcarousel-autoscroll.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.js"></script>

 
 <!-- Side Full -->
<div class="side-full jcarousel-skin-default">
	本日推薦<br>
	<!-- More Products -->
	<div class="more-products ">

		<div class="more-products-holder jcarousel">
		
			<ul>
				<c:forEach items="${sugList}" var="iSug" varStatus="sugEnd">
					<li>
					<a href='<c:url value="ShowItemDetail?classNo=${iSug.itemClassNo}&itemNo=${iSug.itemNo}"/>'><img src="${pageContext.request.contextPath}/ShowItemMedia?classNo=${iSug.itemClassNo}&itemNo=${iSug.itemNo}&itemMediaNo=1"  width="100%" /></a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="more-nav">
			<a href="#" class="prev" onclick="$('.jcarousel').jcarousel('scroll', '-=1'); return false;">Prev</a>
			<a href="#" class="next" onclick="$('.jcarousel').jcarousel('scroll', '+=1'); return false;">Next</a>
		</div>
	</div>
	<!-- End More Products -->
</div>
<!-- End Side Full -->   
