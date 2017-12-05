<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="category" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
<dt>
  <ul>
   <c:forEach items="${categorypage.list}" var="subcategory">
        <li><a href="${ctx}/listc-${category.id}-${subcategory.id}${urlSuffix}" id="ssnav${subcategory.id}">${subcategory.name}</a></li>
   </c:forEach>
  </ul>
</dt>
<dd><a href="${ctx}/">首页</a>>
	<c:forEach items="${fnc:getCategoryListByIds(category.parentIds)}" var="tpl" varStatus="status">
			<c:if test="${status.index ne 1}"><cms:channelHref category="${fnc:getCategory(tpl.id)}"></cms:channelHref>></c:if>	
	</c:forEach><em>${category.name}</em>
</dd>

<script language="javascript" type="text/javascript">
try{
	 document.getElementById("snav-${category.id}").className= "subfield";
	 }catch(ex){}
</script>