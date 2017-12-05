<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var oldEmail = $(".email").val();
			$("#userName").focus();
			$("#inputForm").validate({
				rules: {
					userName: {remote: "${ctx}/cms/student/checkuserName?olduserName=" + encodeURIComponent('${student.userName}')},
					mobile:{
						maxlength:11,
						minlength:11
					},
					email:{remote: "${ctx}/cms/student/checkuserEmail?oldEmail="+oldEmail}
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
			
			var $gradeId=$("#gradeId");
			var $classesId = $("#classesId");
			var $classGroupId = $("#classGroupId");
			//手动触发年级事件
			$gradeId.trigger("change");

			$gradeId.change(function(){
				$("#classesId option").remove();
				if($gradeId.val()!=""){
					$classesId.append('<option value="">请选择班级</option>');
				}else{
					$classesId.append('<option value="">无</option>');
					$classesId.triggerHandler('change');
					return false;
				}
				$.ajax({
						url:"${ctx}/cms/student/searchclasses",
						type:"GET",
						data:{gradeId:$gradeId.val()},
						dataType:"json",
						cache: false,
						error: function() { alert('加载数据异常，请重试!'); },
						success: function(classesList) {
								$(classesList).each(function(classes_index,classes){
									$classesId.append('<option value="'+classes.id+'">'+classes.className+'</option>');
									$classesId.triggerHandler('change'); 
								});
						}
				});	
			});
			
			//触发班级事件
			$classesId.change(function(){ 
				$("#classGroupId option").remove();
				if($classesId.val()!=""){
					$classGroupId.append('<option value="">请选择班组</option>');
				}else{
					$classGroupId.append('<option value="">无</option>');
					$classGroupId.triggerHandler('change'); 
					return false;
				}
				
				$.ajax({
						url:"${ctx}/cms/student/searchclassGroup",
						type:"GET",
						data:{classesId:$classesId.val()},
						dataType:"json",
						cache: false,
						error: function() { alert('加载数据异常，请重试!'); },
						success: function(classGroupList) {
							$(classGroupList).each(function(classGroup_index,classGroup){
								$classGroupId.append('<option value="'+classGroup.id+'">'+classGroup.groupName+'</option>');
								$classGroupId.triggerHandler('change'); 
							});
						}
				});	
			  });  
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/student/">学生列表</a></li>
		<li class="active"><a href="${ctx}/cms/student/form?id=${student.id}">学生<shiro:hasPermission name="cms:student:edit">${not empty student.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:student:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="student" action="${ctx}/cms/student/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<input id="olduserName" name="olduserName" type="hidden" value="${student.userName}">
				<form:input path="userName" htmlEscape="false" maxlength="50" class="required userName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty student.id?'required':''}"/>
				<c:if test="${not empty student.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
		<%-- <div class="control-group">
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
		</div> --%>
		<div class="control-group">
			<label class="control-label">学号:</label>
			<div class="controls">
				<form:input path="studentNumber" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">真实姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
			 	<form:select path="gender" htmlEscape="false" class="required" >
					<form:option value="" style="color:#000" >请选择</form:option>
						<form:option value="男" style="color:#000">男</form:option>
						<form:option value="女" style="color:#000">女</form:option>
				</form:select>
			</div>
		</div>
	   <div class="control-group">
			<label class="control-label">年级:</label>
			<div class="controls">
				<select name="gradeId" id="gradeId" class="required">
					 <option value="" >请选择</option>    
					 <c:forEach items="${gradeList}" var="grade" varStatus="status">	
						<option value="${grade.id}" <c:if test="${gradeId==grade.id}"> selected="selected"</c:if> >${grade.gradeName}</option>
					 </c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班级:</label>
			<div class="controls">
				<select name="classesId" id="classesId" class="required">
					 <c:if test="${empty student.id}">
					 		<option value="">无</option>
					 </c:if>  
					 <c:if test="${not empty student.id}">
						 <c:forEach items="${classesList}" var="classes" varStatus="status">	
							<option value="${classes.id}" <c:if test="${classesId==classes.id}"> selected="selected"</c:if> >${classes.className}</option>
						 </c:forEach>
					 </c:if>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班组:</label>
			<div class="controls">
				<select name="classGroupId" id="classGroupId" >
				 	 <c:if test="${empty student.id}">
					 		<option value="">无</option>
					 </c:if>  
					 <c:if test="${not empty student.id}">
					   <option value=""></option>	
						<c:forEach items="${classGroupList}" var="classGroup" varStatus="status">
							<option value="${classGroup.id}" <c:if test="${classGroupId==classGroup.id}"> selected="selected"</c:if> >${classGroup.groupName}</option>
						</c:forEach>
					 </c:if>  
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="100" class="digits "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">单位名称:</label>
			<div class="controls">
				<form:input path="tCName" htmlEscape="false" maxlength="100"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">家庭地址:</label>
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
		<c:if test="${not empty student.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${student.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="cms:student:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>