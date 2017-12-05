<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="category" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
<c:choose>
		<c:when test="${not empty category.href}">
			<c:choose>
 			<c:when test="${fn:indexOf(category.href, '://') eq -1 && fn:indexOf(category.href, 'mailto:') eq -1}">
 			<c:set var="url" value="${ctx}${category.href}"/></c:when><c:otherwise><c:set var="url" value="${category.href}"/></c:otherwise>
 		</c:choose>
		</c:when>
		<c:otherwise><c:set var="url" value="${ctx}/list-${category.id}${urlSuffix}"/></c:otherwise>
</c:choose><a href="${url}" target="${category.target}">${category.name}</a>