<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>留言管理</title>
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
		<li class="active"><a href="${ctx}/cms/guestbook/">留言列表</a></li><%--
		<shiro:hasPermission name="cms:guestbook:edit"><li><a href="${ctx}/cms/guestbook/form?id=${guestbook.id}">留言添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="guestbook" action="${ctx}/cms/guestbook/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>分类：</label><form:select id="type" path="type" class="input-small"><form:option value="" label=""/><form:options items="${fns:getDictList('cms_guestbook')}" itemValue="value" itemLabel="label" htmlEscape="false"/></form:select>
		<label>内容 ：</label><form:input path="content" htmlEscape="false" maxlength="50" class="input-small"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" />
	</form:form>
	<tags:message content="${message}"/>
	<table width="95%" id="contentTable" style="table-layout:fixed;" class="table table-striped table-bordered table-condensed">
		<thead><tr><th width="100px">标题</th><th width="50px">运单号</th><th width="50px">留言分类</th>
		<th nowrap>留言内容</th><th width="100px">留言人</th><th width="100px">留言时间</th>
		<th width="100px">回复人</th><th nowrap>回复内容</th><th width="100px">回复时间</th>
		<shiro:hasPermission name="cms:guestbook:edit"><th width="5%">操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="guestbook">
			<tr style="table-layout:fixed;word-wrap:break-word;">
			   <td width="100px">${guestbook.title}</td>
			   <td width="50px">${guestbook.orderid}</td>
				<td width="50px">${fns:getDictLabel(guestbook.type, 'cms_guestbook', '无分类')}</td>
				<td style="word-wrap:break-word;"><a href="${ctx}/cms/guestbook/form?id=${guestbook.id}">${fns:abbr(guestbook.content,40)}</a></td>
				<td width="100px">${guestbook.name}</td>
				<td width="100px"><fmt:formatDate value="${guestbook.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${guestbook.reUser.name}</td>
				<td style="word-wrap:break-word;">${fns:abbr(guestbook.reContent,40)}</td>
				<td width="100px"> <fmt:formatDate value="${guestbook.reDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
				<shiro:hasPermission name="cms:guestbook:edit"><td>
					<c:if test="${guestbook.delFlag ne '2'}"><a href="${ctx}/cms/guestbook/delete?id=${guestbook.id}${guestbook.delFlag ne 0?'&isRe=true':''}" 
						onclick="return confirmx('确认要${guestbook.delFlag ne 0?'恢复审核':'删除'}该留言吗？', this.href)">${guestbook.delFlag ne 0?'恢复审核':'删除'}</a></c:if>
					<c:if test="${guestbook.delFlag eq '2'}"><a href="${ctx}/cms/guestbook/form?id=${guestbook.id}">审核</a></c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>