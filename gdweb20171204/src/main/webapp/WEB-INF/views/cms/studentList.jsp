<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {

		});
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
		<li class="active"><a href="${ctx}/cms/student/">学生列表</a></li>
		<shiro:hasPermission name="cms:student:edit"><li><a href="${ctx}/cms/student/form">学生添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="student" action="${ctx}/cms/student/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>学生姓名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort loginName">用户名</th><th class="sort name">电子邮箱</th><th>电话</th><th>手机</th><th>真实姓名</th><th>性别</th><th>年级</th><th>班级</th><th>班组</th><th>学号</th><shiro:hasPermission name="cms:student:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="student">
			<tr>
				<td>${student.userName}</td>
				<td>${student.email}</td>
				<td>${student.phone}</td>
				<td>${student.mobile}</td>
				<td>${student.name}</td>
				<td>${student.gender}</td>
			   <td>${student.grade.gradeName}</td>
				<td>${student.classes.className}</td>
				<td>${student.classGroup.groupName}</td> 
				<td>${student.studentNumber}</td>
				<shiro:hasPermission name="cms:student:edit"><td>
    				<a href="${ctx}/cms/student/form?id=${student.id}">修改</a>
					<a href="${ctx}/cms/student/delete?id=${student.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>