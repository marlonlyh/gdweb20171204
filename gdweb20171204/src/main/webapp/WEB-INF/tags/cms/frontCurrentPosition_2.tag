<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="category" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
<dt>
  <ul>
   <c:forEach items="${fnc:getCategoryList(category.site.id, category.parent.id, -1, '')}" var="tpl" varStatus="status">
        <li><a href="${ctx}/list-${tpl.id}${urlSuffix}" id="snav-${tpl.id}">${tpl.name}</a></li>
   </c:forEach>
  <%--  <c:forEach items="${fnc:getCategoryList(category.site.id, category.id, -1, '')}" var="tpl" varStatus="status">
		  <li><a href="${ctx}/listc-${category.id}-${subcategory.id}${urlSuffix}" id="ssnav${tpl.id}">${tpl.name}</a></li>	
	</c:forEach> --%>
  <!-- <li><a href="#" class="cur">研究生培养方案</a></li>
  <li><a href="#">职教师资培养方案</a></li>
  <li><a href="#">定向培养生培养方案</a></li> -->
  </ul>
</dt>
<dd><a href="${ctx}/">首页</a>>
			<c:if test="${status.index ne 0}"><a href="${ctx}/list-${category.parent.parent.id}${urlSuffix}">${category.parent.parent.name}</a>></c:if>	
	<em>${category.parent.name}</em>
</dd>
<script language="javascript" type="text/javascript">
try{
	 document.getElementById("snav-${category.id}").className= "subfield";
	 }catch(ex){}
</script>