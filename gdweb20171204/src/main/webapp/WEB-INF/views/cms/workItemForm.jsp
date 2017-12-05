<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生作业管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#inputForm").validate({
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
		<li><a href="${ctx}/cms/workItem/">作业列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/workItem/form?id=${workItem.id}'></c:url>">作业<shiro:hasPermission name="cms:workItem:edit">${not empty workItem.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:workItem:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="workItem" action="${ctx}/cms/workItem/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">作业名称:</label>
			<div class="controls">
				<form:input path="work.workName" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">年级:</label>
			<div class="controls">
				<form:input path="student.grade.gradeName" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">班级:</label>
			<div class="controls">
				<form:input path="student.classes.className" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">班组:</label>
			<div class="controls">
				<form:input path="student.classGroup.groupName" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">学生:</label>
			<div class="controls">
				<form:input path="student.name" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">附件:</label>
			<div class="controls">
                <input type="hidden" id="attach" name="attach" value="${workItem.attach}" />
				<tags:ckfinder input="attach" type="files" uploadPath="/cms/workItem" selectMultiple="false"/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="cms:workItem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>