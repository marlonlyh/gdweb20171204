<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>app问题反馈</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/member/feedbacklist">app问题反馈列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="member" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<form:input path="userName" htmlEscape="false" maxlength="50" class="userName" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
				<label class="control-label">app个人反馈:</label>
				<div class="controls">
					<form:textarea path="feedback" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge" readonly="true"/>
				</div>
			</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>