<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="category" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
<%@ attribute name="pageSize" type="java.lang.Integer" required="false" description="页面大小"%>
<div class="ban">
	<div class="bancon">
		<c:choose>
		 <c:when test="${not empty category.banner}">
		<img src="${ctx}${category.banner}"/>
		</c:when>
		<c:otherwise> 
		<img src="${ctx}${fnc:getCategory(category.topCategory.id).banner}">
		 </c:otherwise>
		</c:choose> 
	</div>
</div>