<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="category" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
<dt>${category.name}</dt>
<dd><a href="${ctx}/">首页</a>>
	<c:forEach items="${fnc:getCategoryListByIds(category.parentIds)}" var="tpl" varStatus="status">
			<c:if test="${status.index ne 0}"><cms:channelHref category="${fnc:getCategory(tpl.id)}"></cms:channelHref>></c:if>	
	</c:forEach><em>${category.name}</em>
</dd>