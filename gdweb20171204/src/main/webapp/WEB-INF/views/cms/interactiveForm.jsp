<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${article.category.name}</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#userName").focus();
			$("#inputForm").validate({
				rules: {
					userName: {remote: "${ctx}/cms/member/checkuserName?olduserName=" + encodeURIComponent('${member.userName}')},
					mobile:{
						maxlength:11,
						minlength:11
					},
					email:{remote: "${ctx}/cms/member/checkuserEmail"}
				},
				messages: {
					userName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"},
					email: {remote: "邮箱已存在"},
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/interactive/">${article.category.name}列表</a></li>
		<li class="active"><a href="${ctx}/cms/interactive/form?id=${interactive.id}">${article.category.name}<shiro:hasPermission name="cms:article:edit">${not empty interactive.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="interactive" action="${ctx}/cms/interactive/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="10" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补充说明:</label>
			<div class="controls">
			<form:textarea path="instruction" htmlEscape="false" rows="6" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否显示:</label>
			<div class="controls">			
				<form:radiobuttons path="isShow" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否置顶:</label>
			<div class="controls">			
				<form:radiobuttons path="isTop" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">答复:</label>
			<div class="controls">
				<form:textarea path="reply"  htmlEscape="true" rows="4" maxlength="200"  class="input-xxlarge"/>
				<tags:ckeditor replace="reply" uploadPath="/cms/article" />
			</div>
		</div>

		<c:if test="${not empty member.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${member.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>