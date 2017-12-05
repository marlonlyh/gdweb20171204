<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/bootstrap-multiselect.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/bootstrap-multiselect.js" type="text/javascript"></script>
<html>
<head>
	<title>作业管理</title>
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
		$(document).ready(function() {
		var i = $("#classesIds span:first-child").html();
			
		});
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/work/">作业列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/work/form?id=${work.id}'></c:url>">作业<shiro:hasPermission name="cms:work:edit">${not empty work.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:work:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="work" action="${ctx}/cms/work/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">作业名称:</label>
			<div class="controls">
				<form:input path="workName" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年级:</label>
			<div class="controls">
			   <form:hidden path="grade.id"/>
				<form:input path="grade.gradeName" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班级:</label>
			<div class="controls">
			   <form:hidden path="classes.id"/>
				<form:input path="classes.className" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班组:</label>
			<div class="controls">
				<form:hidden path="classGroup.id"/>
				<form:input path="classGroup.groupName" htmlEscape="false" maxlength="200" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片:</label>
			<div class="controls">
                <input type="hidden" id="image" name="image" value="${work.imageSrc}" />
				<tags:ckfinder input="image" type="thumb" uploadPath="/cms/work" selectMultiple="false"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">附件:</label>
			<div class="controls">
                <input type="hidden" id="attach" name="attach" value="${work.attach}" />
				<tags:ckfinder input="attach" type="files" uploadPath="/cms/work" selectMultiple="false"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">作业详情:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/cms/work" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">布置时间:</label>
			<div class="controls">
				<input id="publishDate" name="publishDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${work.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成时间:</label>
			<div class="controls">
				<input id="finishDate" name="finishDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${work.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:work:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>