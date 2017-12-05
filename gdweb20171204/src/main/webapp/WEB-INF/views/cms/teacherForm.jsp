<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/bootstrap-multiselect.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/common/bootstrap-multiselect.js" type="text/javascript"></script>
<html>
<head>
	<title>教师管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var oldEmail = $(".email").val();
			$("#userName").focus();
			$("#inputForm").validate({
				rules: {
					userName: {remote: "${ctx}/cms/teacher/checkuserName?olduserName=" + encodeURIComponent('${teacher.userName}')},
					mobile:{
						maxlength:11,
						minlength:11
					},
					email:{remote: "${ctx}/cms/teacher/checkuserEmail?oldEmail="+oldEmail}
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
			
		 	 /* var $gradeId=$("#gradeId"); 
			var $classesIds = $("#classesIds");
			
			//手动触发年级事件
			$classesIds.trigger("change");

			$classesIds.change(function(){
				 $("#classesIds option").remove();
				if($gradeId.val()!=""){
					$classesIds.append('<option value="" disabled="disabled">请选择班级</option>');
				}else{
					$classesIds.append('<option value="">无</option>');
					$classesIds.triggerHandler('change');
					return false;
				} 
				$.ajax({
						url:"${ctx}/cms/teacher/searchclasses",
						type:"GET",
						data:{classesIds:$classesIds.val()},
						dataType:"json",
						cache: false,
						error: function() { alert('加载数据异常，请重试!'); },
						success: function(classesList) {
								$(classesList).each(function(classes_index,classes){
									$classesIds.append('<option value="'+classes.id+'">'+classes.className+'</option>');
									$classesIds.triggerHandler('change'); 
								});
						}
				});	
			}); */  
			
		});
		 $(document).ready(function() {
			  if($("#gradeId").find("option:selected").val()!=""){
				$("#disabled").remove();
				$(".select2-choices").css("background-color","#fff");
			}   
			$("#gradeId").change(function(){
				$("#disabled").remove();
				$(".select2-choices").css("background-color","#fff");
			});
		}); 
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/teacher/">教师列表</a></li>
		<li class="active"><a href="${ctx}/cms/teacher/form?id=${teacher.id}">教师<shiro:hasPermission name="cms:teacher:edit">${not empty teacher.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:teacher:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="teacher" action="${ctx}/cms/teacher/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<input id="olduserName" name="olduserName" type="hidden" value="${teacher.userName}">
				<form:input path="userName" htmlEscape="false" maxlength="50" class="required userName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty teacher.id?'required':''}"/>
				<c:if test="${not empty teacher.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教师编号:</label>
			<div class="controls">
				<form:input path="teacherNumber" htmlEscape="false" maxlength="50"/>
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
			<label class="control-label">年级班级:</label>
			<div class="controls">
			<span style="position:relative;">
				<select name="classesIds" id="classesIds" class="multiselect" multiple="multiple">
					<option value="" disabled="disabled">请选择</option> 
					 <c:forEach items="${classesList}" var="classes" varStatus="status">	
						<option value="${classes.id}" <c:if test="${fn:contains(classIds,classes.id)}"> selected="selected"</c:if>>${classes.grade.gradeName}${classes.className}</option>
					 </c:forEach>
				</select>
				<!-- <div id="disabled" style="height: 40px;position: absolute;width: 100%;top: -10px;opacity: 0;"></div> -->
				<span class="help-inline">可多选</span>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="100" class="digits "/>
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
		<c:if test="${not empty teacher.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${teacher.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="cms:teacher:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>