<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="category" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
<dt>${category.name}</dt>
<form action="${ctx}/list-${category.id}${urlSuffix}" method="post" id="form">
<input id="order" name="order" type="hidden" value=""/>
<dd class="searchjob"><label>时间</label>
    <div class="optbox"><a href="#" id="asc"></a><a href="#" id="desc"></a></div>
    <label>工作地点</label>
    <select name="jobAddress" id="jobAddress" class="addselect" >
      <option></option>
      <c:forEach items="${jobAddressList}" var="pjobAddress" varStatus="status">	
        <option value="${pjobAddress}"<c:if test="${pjobAddress==jobAddress}"> selected="selected"</c:if>>${pjobAddress}</option>
      </c:forEach>
    </select>
    <button type="submit" class="btnsearch2"></button> 
    <!-- <input type="submit" name="button" id="search" class="btnsearch2"> -->
  </dd>
</form>
<dd><a href="${ctx}/">首页</a>>
	<c:forEach items="${fnc:getCategoryListByIds(category.parentIds)}" var="tpl" varStatus="status">
			<c:if test="${status.index ne 0}"><cms:channelHref category="${fnc:getCategory(tpl.id)}"></cms:channelHref>></c:if>	
	</c:forEach><em>${category.name}</em>
</dd>
<script>
$("#asc").click(function() {
	$("#order").val("asc");
	$("#form").submit();
});
$("#desc").click(function() {
	$("#order").val("desc");
	$("#form").submit();
});
</script>