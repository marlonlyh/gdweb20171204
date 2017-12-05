<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${article.category.name}</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
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
				if (confirm('确定要批量删除师生互动信息吗？')) {
					$.ajax({
						url: "${ctx}/cms/interactive/batchDelete.jhtml?",
						type: "POST",
						data: $checkedIds.serialize(),
						dataType: "json",
						cache: false,
						async:false,
						success: function(data) {
							$("#deleteButton").prop("disabled", false);
							if (data.type=="success"){
								top.$.jBox.tip(data.content,'success');								
								location.href = "${ctx}/cms/interactive/?id=${interactive.id}"; 
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
		<li class="active"><a href="${ctx}/cms/interactive/">${article.category.name}列表</a></li>
		<shiro:hasPermission name="cms:article:edit"><li><a href="${ctx}/cms/interactive/form">${article.category.name}添加</a></li></shiro:hasPermission>
	</ul>
<form:form id="searchForm" modelAttribute="interactive" action="${ctx}/cms/interactive/list" method="post" class="breadcrumb form-search">
<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
      <label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
      <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<label>描述：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
	<%-- <label>状态：</label>	<input name="status" onclick="$('#searchForm').submit();" type="radio" value="2" <c:if test="${status=='2'}">checked="checked"</c:if><c:if test="${status==null}">checked="checked"</c:if>  />全部
	<input name="status" onclick="$('#searchForm').submit();" type="radio" value="1" <c:if test="${status=='1'}">checked="checked"</c:if> />已解决
   <input name="status" onclick="$('#searchForm').submit();" type="radio" value="0" <c:if test="${status=='0'}">checked="checked"</c:if> />没解决 --%>
	<input id="deleteButton" class="btn btn-primary disabled" type="button" value="批量删除" style="float:right;"/>&nbsp;&nbsp;						

</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		
		<thead><tr>
		<th class="check"><input type="checkbox" id="selectAll"/></th>
		<th>标题</th><th class="sort loginName">描述</th><th class="sort name">补充说明</th><th>是否显示</th><th>发布日期</th><th>答复</th><shiro:hasPermission name="cms:article:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="interactive">
			<tr>
			   <td><input type="checkbox" name="ids" value="${interactive.id}" /></td>
			   
			   <td>${fns:abbr(interactive.title,36)}</td>
			   
				<td>${fns:abbr(interactive.description,36)}</td>
				
				<td>${fns:abbr(interactive.instruction,36)}</td>
				
				<td>${fns:getDictLabel(interactive.isShow, 'show_hide', '显示')}</td>
				
				<td><fmt:formatDate value="${interactive.updateDate}" type="both"/></td>
				
				<td>${fns:abbr(interactive.text,36)}</td>
				
				<shiro:hasPermission name="cms:article:edit"><td>
    				<a href="${ctx}/cms/interactive/form?id=${interactive.id}">修改</a>
					<a href="${ctx}/cms/interactive/delete?id=${interactive.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>