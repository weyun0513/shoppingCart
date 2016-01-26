<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="java.util.List,proj.basic.itemClass.controller.ItemClassService,
    proj.basic.itemClass.model.ItemClassVO, 
    javax.servlet.http.HttpSession, java.util.ArrayList,proj.basic.item.controller.SearchItemService" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui-1.10.4.custom.min.css"> --%>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.theme.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.structure.min.css">
<%-- <script src="${pageContext.request.contextPath }/rcarousel/widget/lib/jquery-1.7.1.min.js" type="text/javascript"></script> --%>
<%-- <script src="${pageContext.request.contextPath }/js/jquery1.11.0-ui.js"></script> --%>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.4.custom.min.js"></script>
<%--   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/demos/style.css"> --%>

<% 
  ItemClassService iClassSrvc = new ItemClassService();
List<ItemClassVO> allClassList = iClassSrvc.getAllClassAlive(); 
List<ItemClassVO> fClassList = new ArrayList<ItemClassVO>();
pageContext.setAttribute("allClassList", allClassList);
int activeNo = -1;


for(ItemClassVO a:allClassList){
	if(a.getFatherClassno() == 0){
		fClassList.add(a);
		activeNo ++; 
		if(request.getParameter("classNo") !=  null && a.getItemClassNo() == Integer.valueOf(request.getParameter("classNo")) && ((ItemClassVO)iClassSrvc.findFatherClass(Integer.valueOf(request.getParameter("classNo")))).getFatherClassno()==0)
			pageContext.setAttribute("activeNo", activeNo);
	}
}

int openNo = 0;

if(request.getParameter("classNo") !=  null && request.getParameter("pageNo")==null){
	openNo = ((ItemClassVO)iClassSrvc.findFatherClass(Integer.valueOf(request.getParameter("classNo")))).getFatherClassno();
	
	for(ItemClassVO a:fClassList){
		if(a.getItemClassNo() == openNo){
			activeNo = fClassList.indexOf(a);
			break;
		}
	}
	pageContext.setAttribute("activeNo", activeNo);
}

if(request.getParameter("classNo")!=null && request.getParameter("pageNo")!=null){
	
    openNo = ((ItemClassVO)iClassSrvc.findFatherClass(Integer.valueOf(request.getParameter("classNo")))).getFatherClassno();
    
    if(openNo != 0){
	
	for(ItemClassVO a:fClassList){
		if(a.getItemClassNo() == openNo){
			activeNo = fClassList.indexOf(a);
			break;
		}
	}
	pageContext.setAttribute("activeNo", activeNo);
    }
}

if((request.getParameter("classNo")==null && request.getParameter("itemNo")!=null)){
	Integer classNo = new SearchItemService().findByItemNo(Integer.valueOf(request.getParameter("itemNo"))).getItemClassNo();
    openNo = ((ItemClassVO)iClassSrvc.findFatherClass(classNo)).getFatherClassno();
	
	for(ItemClassVO a:fClassList){
		if(a.getItemClassNo() == openNo){
			activeNo = fClassList.indexOf(a);
			break;
		}
	}
	pageContext.setAttribute("activeNo", activeNo);
}
pageContext.setAttribute("fClassList", fClassList);
	
%>
    <script>
    $(function() {
        $( "#boxSide" ).accordion({
          animate: false,
          collapsible: true,
          heightStyle: "content",
          active: parseInt("${activeNo}"),
        });
      });
    </script>

				<!-- Categories -->
				<div class="box categories">
<%-- 				<input type="hidden" name="iClassNo" value="${iClass.itemClassNo}"/> --%>
				
					<div class="box-content">
					<div id="boxSide">
						
						<c:forEach items="${fClassList}" var="fClassList">
							<h3><a href='<c:url value="/ShowItems?classNo=${fClassList.itemClassNo}&pageNo=1"/>'> ${fClassList.className}</a></h3>
							<div>
							<c:forEach items="${allClassList}" var="allClass">
							<c:if test="${allClass.fatherClassno == fClassList.itemClassNo}">
							<li><a href='<c:url value="/ShowItems?classNo=${allClass.itemClassNo}&pageNo=1"/>'> ${allClass.className}</a><li>
							</c:if>
							</c:forEach>
							</div>
						</c:forEach>
						
						</div>
					</div>
				</div>
				<!-- End Categories -->