<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var oldEmail = $(".email").val();
			$("#userName").focus();
			$("#inputForm").validate({
				rules: {
					userName: {remote: "${ctx}/cms/member/checkuserName?olduserName=" + encodeURIComponent('${member.userName}')},
					mobile:{
						maxlength:11,
						minlength:11
					},
					email:{remote: "${ctx}/cms/member/checkuserEmail?oldEmail="+oldEmail}
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
		<li><a href="${ctx}/cms/member/">会员列表</a></li>
		<li class="active"><a href="${ctx}/cms/member/form?id=${member.id}">会员<shiro:hasPermission name="cms:member:edit">${not empty member.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:member:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="member" action="${ctx}/cms/member/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<input id="olduserName" name="olduserName" type="hidden" value="${member.userName}">
				<form:input path="userName" htmlEscape="false" maxlength="50" class="required userName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty member.id?'required':''}"/>
				<c:if test="${not empty member.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机串号:</label>
			<div class="controls">
				<form:input path="serialNo" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人昵称:</label>
			<div class="controls">
				<form:input path="nickname" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">个人头像:</label>
				<div class="controls">
	                <input type="hidden" id="head" name="head" value="${member.imageSrc}" />
					<tags:ckfinder input="head" type="thumb" uploadPath="/cms/user" selectMultiple="false"/>
				</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">密码提示问题:</label>
			<div class="controls">
				<form:input path="problem" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码提示答案:</label>
			<div class="controls">
				<form:input path="answer" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">真实姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
				
				 	<form:select path="gender" htmlEscape="false" >
						<form:option value="" style="color:#000" >请选择</form:option>
							<form:option value="男" style="color:#000">男</form:option>
							<form:option value="女" style="color:#000">女</form:option>
					</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
	
			<div class="control-group">
			<label class="control-label">单位名称:</label>
			<div class="controls">
				<form:input path="tCName" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">单位地址:</label>
			<div class="controls">
				<form:input path="unitAddress" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">详细地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">固定电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">app个人反馈:</label>
				<div class="controls">
					<form:textarea path="feedback" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
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
			<shiro:hasPermission name="cms:member:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>