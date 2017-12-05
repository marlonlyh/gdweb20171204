<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${article.category.name}管理</title>
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
					url: "${ctx}/cms/article/batchDelete.jhtml?",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					async:false,
					success: function(data) {
						$("#deleteButton").prop("disabled", false);
						if (data.type=="success"){
							top.$.jBox.tip(data.content,'success');								
							location.href = "${ctx}/cms/article/?category.id=${article.category.id}"; 
						}else{
							top.$.jBox.tip(data.content,'warning');
						}												
					}
				});
			};
		});
	});		
	
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
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
		<li class="active"><a href="${ctx}/cms/article/?category.id=${article.category.id}">${article.category.name}列表</a></li>
		<shiro:hasPermission name="cms:article:edit"><li><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">${article.category.name}添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>栏目：</label><tags:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" notAllowSelectRoot="false" cssClass="input-small"/>
		<label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<label>内容：</label><form:input path="articleData.content" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<input id="deleteButton" class="btn btn-primary disabled" type="button" value="批量删除" style="float:right;"/>&nbsp;&nbsp;
		<!--  <label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>-->
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th class="check"><input type="checkbox" id="selectAll"/></th>
		<th>栏目</th><th>标题</th><th>权重</th><th>点击数</th><th>发布者</th><th>更新时间</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td><input type="checkbox" name="ids" value="${article.id}" /></td>
				<td><a href="javascript:" onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">${article.category.name}</a></td>
				<td><a href="${ctx}/cms/article/form?id=${article.id}" title="${article.title}">${fns:abbr(article.title,40)}</a></td>
				<td>${article.weight}</td>
				<td>${article.hits}</td>
				<td>${article.createBy.name}</td>
				<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
				<td>
					<!-- <a href="${pageContext.request.contextPath}${fns:getFrontPath()}/view-${article.category.id}-${article.id}${fns:getUrlSuffix()}" target="_blank">访问</a> -->
					<shiro:hasPermission name="cms:article:edit">
						<c:if test="${article.category.allowComment eq '1'}"><shiro:hasPermission name="cms:comment:view">
							<a href="${ctx}/cms/comment/?module=article&contentId=${article.id}&delFlag=2" onclick="return viewComment(this.href);">评论</a>
						</shiro:hasPermission></c:if>
	    				<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
	    				<shiro:hasPermission name="cms:article:audit">
							<a href="${ctx}/cms/article/delete?id=${article.id}${article.delFlag ne 0?'&isRe=true':''}&categoryId=${article.category.id}" onclick="return confirmx('确认要${article.delFlag ne 0?'发布':'删除'}该文章吗？', this.href)" >${article.delFlag ne 0?'发布':'删除'}</a>
						</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>