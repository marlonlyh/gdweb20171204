<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>作业管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		var $selectAll = $("#selectAll");
		var $deleteButton = $("#deleteButton");
		var $contentRow = $("#contentTable tr:gt(0)");
		var $contentTable = $("#contentTable");		
		var $ids = $("#contentTable input[name='ids']");
		// 选择
		$ids.click( function() {
			var $this = $(this);
			if ($this.prop("checked")) {
				$this.closest("tr").addClass("selected");
				$deleteButton.removeClass("disabled");

			} else {
				$this.closest("tr").removeClass("selected");
				if ($("#contentTable input[name='ids']:enabled:checked").size() > 0) {
					$deleteButton.removeClass("disabled");
				
				} else {
					$deleteButton.addClass("disabled");
				
				}
			}
		});
		// 全选
		$selectAll.click( function() {
			var $this = $(this);
			var $enabledIds = $("#contentTable input[name='ids']:enabled");
			if ($this.prop("checked")) {
				$enabledIds.prop("checked", true);
				if ($enabledIds.filter(":checked").size() > 0) {
					$deleteButton.removeClass("disabled");
			
					$contentRow.addClass("selected");
				} else {
					$deleteButton.addClass("disabled");
		
				}
			} else {
				$enabledIds.prop("checked", false);
				$deleteButton.addClass("disabled");

				$contentRow.removeClass("selected");
			}
		});
		// 批量删除
		$deleteButton.click( function() {
			var $this = $(this);
			if ($this.hasClass("disabled")) {
				return false;
			}
			var $checkedIds = $("#contentTable input[name='ids']:enabled:checked");				
			if (confirm('确定要批量删除吗？')) {
				$.ajax({
					url: "${ctx}/cms/work/batchDelete.jhtml?",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					async:false,
					success: function(data) {
						$("#deleteButton").prop("disabled", false);
						if (data.type=="success"){
							top.$.jBox.tip(data.content,'success');								
							location.href = "${ctx}/cms/work/?id=${work.id}"; 
						}else{
							top.$.jBox.tip(data.content,'warning');
						}												
					}
				});
			};
		});
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
		<li class="active"><a href="${ctx}/cms/work/">作业列表</a></li>
	<%-- 	<shiro:hasPermission name="cms:work:edit"><li><a href="${ctx}/cms/work/form">作业添加</a></li></shiro:hasPermission>		 --%>
	</ul>
	<form:form id="searchForm" modelAttribute="work" action="${ctx}/cms/work/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>作业名称：</label><form:input path="workName" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<label>作业内容：</label><form:input path="content" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<input id="deleteButton" class="btn btn-primary disabled" type="button" value="批量删除" style="float:right;"/>&nbsp;&nbsp;
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th class="check"><input type="checkbox" id="selectAll"/></th>
		<th>作业名称</th><th>年级</th><th>班级</th><th>班组</th><th>布置时间</th><th>完成时间</th><th>教师</th><shiro:hasPermission name="cms:work:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="work">
			<tr>
			   <td><input type="checkbox" name="ids" value="${work.id}" /></td>
				<td><a href="${ctx}/cms/work/form?id=${work.id}" title="${work.workName}">${work.workName}</a></td>
				<td>${work.grade.gradeName}</td>
				<%-- <td><c:forEach items="${work.classess}" var="classes">
				    	${classes.className},
					 </c:forEach>
				</td> --%>
				<td>${work.classes.className}</td>
				<td>${work.classGroup.groupName}</td>
				<td><fmt:formatDate value="${work.publishDate}" type="both"/></td>
				<td><fmt:formatDate value="${work.finishDate}" type="both"/></td>
				<td>${work.teacher.name}</td>
				<%-- <td>${work.student.name}</td> --%>
				<shiro:hasPermission name="cms:work:edit"><td>
    				<a href="${ctx}/cms/work/form?id=${work.id}">修改</a>
    				<a href="${ctx}/cms/work/delete?id=${work.id}" onclick="return confirmx('确实要删除记录吗？', this.href)">删除</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>