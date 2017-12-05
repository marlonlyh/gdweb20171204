<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>年级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#gradeName").focus();
			$("#inputForm").validate({
				rules: {
					gradeName: {
							remote: {
								url: "${ctx}/cms/grade/checkgradeName",
	                     type: "POST",
	                      data: {	     
	                        	gradeName:function(){return $("#gradeName").val();},
	                           oldgradeName:function(){return '${grade.gradeName}';}	                           			
	                            		}
									}
								}
						},
				messages: {
					gradeName: {remote: "年级名称已存在"}
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
		<li><a href="${ctx}/cms/grade/">年级列表</a></li>
		<li class="active"><a href="${ctx}/cms/grade/form?id=${grade.id}">年级<shiro:hasPermission name="cms:grade:edit">${not empty grade.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:grade:edit">修改</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="grade" action="${ctx}/cms/grade/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">年级名称:</label>
			<div class="controls">
				<input id="oldgradeName" name="oldgradeName" type="hidden" value="${grade.gradeName}">
				<form:input path="gradeName" htmlEscape="false" maxlength="200" class="required gradeName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="200"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:grade:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
