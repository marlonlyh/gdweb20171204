<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级管理</title>
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
		<li class="active"><a href="${ctx}/cms/classes/">班级列表</a></li>
		<shiro:hasPermission name="cms:classes:edit"><li><a href="${ctx}/cms/classes/form">班级添加</a></li></shiro:hasPermission>		
	</ul>
	<form:form id="searchForm" modelAttribute="classes" action="${ctx}/cms/classes/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>班级名称：</label><form:input path="className" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>年级</th><th>班级名称</th><th>年级班级</th><shiro:hasPermission name="cms:classes:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="classes">
			<tr>
				<td>${classes.grade.gradeName}</td>
				<td><a href="${ctx}/cms/classes/form?id=${classes.id}" title="${classes.className}">${classes.className}</a></td>
				<td>${classes.grade.gradeName}${classes.className}</td>
				<shiro:hasPermission name="cms:classes:edit"><td>
    				<a href="${ctx}/cms/classes/form?id=${classes.id}">修改</a>
    				<a href="${ctx}/cms/classes/delete?id=${classes.id}" onclick="return confirmx('确实要删除记录吗？', this.href)">删除</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>