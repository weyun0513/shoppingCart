<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Set"%>
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/horizontal.css">
<!-- jCarousel core-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jcarousel-core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/core_plugin.js"></script>
<!-- jCarousel autoscroll plugin  -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jcarousel-autoscroll.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.js"></script>

 <!-- Side Full -->
 <div id="dialog-logout" title="提示">
<div id="dailog-lougoutMsg"> </div>
</div>
<div class="side-full jcarousel-skin-default">
	最近瀏覽過<br>
	<!-- More Products -->
	<div class="more-products ">

		<div class="more-products-holder jcarousel">
		
			<ul>
				<c:forEach items="${iHistoryNoSet}" var="iHistory" varStatus="historyEnd">
					<li>
					<a href='<c:url value="ShowItemDetail?classNo=${iHistory.value}&itemNo=${iHistory.key}"/>'><img src="${pageContext.request.contextPath}/ShowItemMedia?classNo=${iHistory.value}&itemNo=${iHistory.key}&itemMediaNo=1"  width="100%" /></a>
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

