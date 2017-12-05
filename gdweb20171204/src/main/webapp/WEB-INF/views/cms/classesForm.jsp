<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/* var grade = null;
			$("#gradeId").change(function(){
				grade = $("#gradeId option:selected").val();
			}) */
			$("#name").focus();
			$("#inputForm").validate({
				
				rules: {
					className: {remote:{
						        url: "${ctx}/cms/classes/checkclassName",
	                       type: "POST",
	                        data: {
	                            gradeId: function () { return $("#gradeId option:selected").val();},
	                            className:function(){return $("#className").val();},
	                            oldclassName:function(){return '${classes.className}';}
	                        }

					  }
					}
				
				},
				messages: {
					className: {remote: "班级名称已存在"},
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
		
		$(document).ready(function() {
			$("#gradeId").change(function(){
				$("#className").val("");//改变年级后清空班级输入值
			})
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/classes/">班级列表</a></li>
		<li class="active"><a href="${ctx}/cms/classes/form?id=${classes.id}">班级<shiro:hasPermission name="cms:classes:edit">${not empty classes.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:classes:edit">修改</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="classes" action="${ctx}/cms/classes/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">年级:</label>
			<div class="controls">
				<select name="gradeId" id="gradeId" class="input-xlarge required">
					 <option value="" >请选择</option>    
					 <c:forEach items="${gradeList}" var="grade" varStatus="status">	
						<option value="${grade.id}" <c:if test="${gradeId==grade.id}"> selected="selected"</c:if> >${grade.gradeName}</option>
					 </c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班级名称:</label>
			<div class="controls">
			   <input id="oldclassName" name="oldclassName" type="hidden" value="${classes.className}">
				<form:input path="className" htmlEscape="false" maxlength="200" class="required className"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:classes:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
