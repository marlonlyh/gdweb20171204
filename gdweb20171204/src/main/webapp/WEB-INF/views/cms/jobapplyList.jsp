<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应聘管理</title>
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
		<li class="active"><a href="${ctx}/cms/jobapply/">应聘列表</a></li>		
	</ul>
	<form:form id="searchForm" modelAttribute="jobapply" action="${ctx}/cms/jobapply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>姓名</th><th>性别</th><th>学历</th><th>专业</th><th>电话</th><shiro:hasPermission name="cms:jobapply:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="jobapply">
			<tr>
				<td><a href="${ctx}/cms/jobapply/form?id=${jobapply.id}" title="${jobapply.name}">${jobapply.name}</a></td>
				<td>${jobapply.sex}</td>
				<td>${jobapply.graduate}</td>
				<td>${jobapply.specialty}</td>
				<td>${jobapply.mobile}</td>
				<shiro:hasPermission name="cms:jobapply:edit"><td>
    				<a href="${ctx}/cms/jobapply/form?id=${jobapply.id}">查看</a>
    				<a href="${ctx}/cms/jobapply/delete?id=${jobapply.id}" onclick="return confirmx('确实要删除记录吗？', this.href)">删除</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>