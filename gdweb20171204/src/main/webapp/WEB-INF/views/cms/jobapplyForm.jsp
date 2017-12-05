<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>招聘管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/jobapply/">应聘列表</a></li>
		<li class="active"><a href="${ctx}/cms/jobapply/form?id=${jobapply.id}">应聘<shiro:hasPermission name="cms:jobapply:edit">${not empty jobapply.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:jobapply:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jobapply" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
				<form:input path="sex" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期:</label>
			<div class="controls">
				<input id="birthDate" name="birthDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobapply.birthDate}" pattern="yyyy-MM-dd"/>"
					readonly="readonly"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族:</label>
			<div class="controls">
				<form:input path="nation" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">籍贯:</label>
			<div class="controls">
				<form:input path="origin" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户口所在地:</label>
			<div class="controls">
				<form:input path="houseHolds" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业院校:</label>
			<div class="controls">
				<form:input path="institutions" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历:</label>
			<div class="controls">
				<form:input path="graduate" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业:</label>
			<div class="controls">
				<form:input path="specialty" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型:</label>
			<div class="controls">
				<form:input path="documentType" htmlEscape="false" maxlength="255" class="input-xlarge" readonly="true"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码:</label>
			<div class="controls">
				<form:input path="certificate" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">移动电话:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教育经历</label>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间:</label>
			<div class="controls">
				<input id="beginDate" name="beginDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobapply.beginDate}" pattern="yyyy-MM-dd"/>"
					readonly="readonly"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间:</label>
			<div class="controls">
				<input id="endDate" name="endDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobapply.endDate}" pattern="yyyy-MM-dd"/>"
					readonly="readonly"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学校名称:</label>
			<div class="controls">
				<form:input path="school" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历:</label>
			<div class="controls">
				<form:input path="college" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业:</label>
			<div class="controls">
				<form:input path="discipline" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作经历</label>			
		</div>
		<div class="control-group">
			<label class="control-label">开始时间:</label>
			<div class="controls">
				<input id="startDate" name="startDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobapply.startDate}" pattern="yyyy-MM-dd"/>"
					readonly="readonly"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间:</label>
			<div class="controls">
				<input id="terminalDate" name="terminalDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${jobapply.terminalDate}" pattern="yyyy-MM-dd"/>"
					readonly="readonly"/>					
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业名称:</label>
			<div class="controls">
				<form:input path="enterprise" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位名称:</label>
			<div class="controls">
				<form:input path="jobName" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在部门:</label>
			<div class="controls">
				<form:input path="department" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作描述:</label>
			<div class="controls">
				<form:textarea path="detail" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge" readonly="true"/>				
			</div>
		</div>
		<div class="form-actions">			
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>