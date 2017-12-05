<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>年级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/grade/">年级列表</a></li>
		<shiro:hasPermission name="cms:grade:edit"><li><a href="${ctx}/cms/grade/form">年级添加</a></li></shiro:hasPermission>		
	</ul>
	<form:form id="searchForm" modelAttribute="grade" action="${ctx}/cms/grade/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>年级名称：</label><form:input path="gradeName" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>年级名称</th><shiro:hasPermission name="cms:grade:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="grade">
			<tr>
				<td><a href="${ctx}/cms/grade/form?id=${grade.id}" title="${grade.gradeName}">${grade.gradeName}</a></td>
				<shiro:hasPermission name="cms:grade:edit"><td>
    				<a href="${ctx}/cms/grade/form?id=${grade.id}">修改</a>
    				<a href="${ctx}/cms/grade/delete?id=${grade.id}" onclick="return confirmx('确实要删除记录吗？', this.href)">删除</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>