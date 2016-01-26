<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Set,proj.basic.item.controller.AddSugViewListService,
    javax.servlet.http.HttpSession,proj.basic.item.controller.SearchItemService,
    proj.basic.item.model.ItemVO,java.util.List"%>
<style>
#sugList div { margin: 3px 3px 3px 0; padding: 1px; float: left; width: 54px; height: 78px; font-size: 0.5em; text-align: center; } 
</style>    
<%
 if(request.getParameter("itemNo")==null && request.getParameter("classNo") == null)
 	request.setAttribute("sugList", new AddSugViewListService().getSugItemsDef());
 else if(request.getParameter("itemNo")!=null && request.getParameter("classNo") != null)
	request.setAttribute("sugList",new AddSugViewListService().getSugUserItems(request.getParameter("classNo")));
 else if(request.getParameter("itemNo")==null && request.getParameter("classNo") != null){
	 List<ItemVO> iList = new AddSugViewListService().getSugUserClassItems(request.getParameter("classNo"));
	 if(iList.size()==0)
		 iList = new AddSugViewListService().getSugFatherItems(request.getParameter("classNo"));
	request.setAttribute("sugList",iList);
 }
 else if(request.getParameter("itemNo")!=null && request.getParameter("classNo") == null){
	Integer classNo = new SearchItemService().findByItemNo(Integer.valueOf(request.getParameter("itemNo"))).getItemClassNo();
	request.setAttribute("sugList",new AddSugViewListService().getSugUserClassItems(classNo.toString()));
	request.setAttribute("classNo",classNo);
} 
%>
	<div>

		 <c:if test="${not empty sugList}">
			
				<c:forEach items="${sugList}" var="iSug" varStatus="sugEnd">
					<div id="#sugList">
					<a href='<c:url value="/ShowItemDetail?classNo=${iSug.itemClassNo}&itemNo=${iSug.itemNo}"/>'>
					<img src="${pageContext.request.contextPath}/ShowItemMedia?classNo=${iSug.itemClassNo}&itemNo=${iSug.itemNo}&itemMediaNo=1" style="margin: 6px 3px 10px 2px; padding: 1px; float: left; width: 56px; height: 68px;"/></a>
					</div>
				</c:forEach>

		</c:if>
		</div>


