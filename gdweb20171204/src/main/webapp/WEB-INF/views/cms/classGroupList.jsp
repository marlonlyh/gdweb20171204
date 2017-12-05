<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班组管理</title>
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
		<li class="active"><a href="${ctx}/cms/classGroup/">班组列表</a></li>
		<shiro:hasPermission name="cms:classGroup:edit"><li><a href="${ctx}/cms/classGroup/form">班组添加</a></li></shiro:hasPermission>		
	</ul>
	<form:form id="searchForm" modelAttribute="classGroup" action="${ctx}/cms/classGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>班组名称：</label><form:input path="groupName" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>年级</th><th>班级</th><th>班组名称</th><shiro:hasPermission name="cms:classGroup:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="classGroup">
			<tr>
			   <td>${classGroup.grade.gradeName}</td>
			   <td>${classGroup.classes.className}</td>
				<td><a href="${ctx}/cms/classGroup/form?id=${classGroup.id}" title="${classGroup.groupName}">${classGroup.groupName}</a></td>
				<shiro:hasPermission name="cms:classGroup:edit"><td>
    				<a href="${ctx}/cms/classGroup/form?id=${classGroup.id}">修改</a>
    				<a href="${ctx}/cms/classGroup/delete?id=${classGroup.id}" onclick="return confirmx('确实要删除记录吗？', this.href)">删除</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>