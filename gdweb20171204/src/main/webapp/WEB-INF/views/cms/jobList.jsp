<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职位管理</title>
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
		<li class="active"><a href="${ctx}/cms/job/">职位列表</a></li>
		<shiro:hasPermission name="cms:job:edit"><li><a href="${ctx}/cms/job/form">职位添加</a></li></shiro:hasPermission>		
	</ul>
	<form:form id="searchForm" modelAttribute="job" action="${ctx}/cms/job/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>职位名称</th><th>部门</th><th>工作地点</th><th>所属公司</th><th>发布时间</th><shiro:hasPermission name="cms:job:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="job">
			<tr>
				<td><a href="${ctx}/cms/job/form?id=${job.id}" title="${job.jobName}">${job.jobName}</a></td>
				<td>${job.departMent}</td>
				<td>${job.address}</td>
				<td>${job.company}</td>
				<td><fmt:formatDate value="${job.publishDate}" pattern="yyyy-MM-dd"/></td>
				<shiro:hasPermission name="cms:job:edit"><td>
    				<a href="${ctx}/cms/job/form?id=${job.id}">修改</a>
    				<a href="${ctx}/cms/job/delete?id=${job.id}" onclick="return confirmx('确实要删除记录吗？', this.href)">删除</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>