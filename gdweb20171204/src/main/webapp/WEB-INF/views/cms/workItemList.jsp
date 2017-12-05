<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生作业管理</title>
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
					url: "${ctx}/cms/workItem/batchDelete.jhtml?",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					async:false,
					success: function(data) {
						$("#deleteButton").prop("disabled", false);
						if (data.type=="success"){
							top.$.jBox.tip(data.content,'success');								
							location.href = "${ctx}/cms/workItem/?id=${workItem.id}"; 
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
		<li class="active"><a href="${ctx}/cms/workItem/">作业列表</a></li>
	<%-- 	<shiro:hasPermission name="cms:workItem:edit"><li><a href="${ctx}/cms/work/form">作业添加</a></li></shiro:hasPermission>		 --%>
	</ul>
	<form:form id="searchForm" modelAttribute="workItem" action="${ctx}/cms/workItem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>作业名称：</label><form:input path="work.workName" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<label>附件名称：</label><form:input path="fileName" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<input id="deleteButton" class="btn btn-primary disabled" type="button" value="批量删除" style="float:right;"/>&nbsp;&nbsp;
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th class="check"><input type="checkbox" id="selectAll"/></th>
		<th>作业名称</th><th>年级</th><th>班级</th><th>班组</th><!-- <th>教师</th> --><th>学生</th><th>附件名称</th><shiro:hasPermission name="cms:work:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="workItem">
			<tr>
			   <td><input type="checkbox" name="ids" value="${workItem.id}" /></td>
				<td><a href="${ctx}/cms/workItem/form?id=${workItem.id}" title="${workItem.work.workName}">${workItem.work.workName}</a></td>
				<td>${workItem.student.grade.gradeName}</td>
				<td>${workItem.student.classes.className}</td>
				<td>${workItem.student.classGroup.groupName}</td>
				<%-- <td>${workItem.teacher.name}</td> --%>
				<td>${workItem.student.name}</td>
				<td>${workItem.fileName}</td>
				<shiro:hasPermission name="cms:workItem:edit"><td>
    				<a href="${ctx}/cms/workItem/form?id=${workItem.id}">修改</a>
    				<a href="${ctx}/cms/workItem/delete?id=${workItem.id}" onclick="return confirmx('确实要删除记录吗？', this.href)">删除</a>					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>