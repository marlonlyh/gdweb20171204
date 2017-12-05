<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#majorName").focus();
			$("#inputForm").validate({
				rules: {
					majorName: {
							remote: {
								url: "${ctx}/cms/major/checkmajorName",
	                     type: "POST",
	                      data: {	     
	                        	majorName:function(){return $("#majorName").val();},
	                           oldmajorName:function(){return '${major.majorName}';}	                           			
	                            		}
									}
								}
						},
				messages: {
					majorName: {remote: "专业名称已存在"}
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
		<li><a href="${ctx}/cms/major/">专业列表</a></li>
		<li class="active"><a href="${ctx}/cms/major/form?id=${major.id}">专业<shiro:hasPermission name="cms:major:edit">${not empty major.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:major:edit">修改</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="major" action="${ctx}/cms/major/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">专业名称:</label>
			<div class="controls">
				<input id="oldmajorName" name="oldmajorName" type="hidden" value="${major.majorName}">
				<form:input path="majorName" htmlEscape="false" maxlength="200" class="required majorName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="200"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:major:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
