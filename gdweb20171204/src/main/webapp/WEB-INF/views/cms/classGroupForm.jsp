<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#groupName").focus();
			$("#inputForm").validate({
				rules: {
					groupName: {remote:{
						        url: "${ctx}/cms/classGroup/checkgroupName",
	                       type: "POST",
	                        data: {
	                            gradeId: function () { return $("#gradeId option:selected").val();},
	                            classesId:function(){return $("#classesId option:selected").val();},
	                            groupName:function(){return $("#groupName").val();},
	                            oldgroupName:function(){return '${classGroup.groupName}';}
	                        }

					  }
					}
				
				},
				messages: {
					groupName: {remote: "班组名称已存在"},
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
			//手动触发年级事件
			$gradeId.trigger("change");

			$gradeId.change(function(){
				$("#groupName").val("");//清空班组输入值
				$("#classesId option").remove();
				if($gradeId.val()!=""){
					$classesId.append('<option value="">请选择班级</option>');
				}else{
					$classesId.append('<option value="">无</option>');
					$classesId.triggerHandler('change'); 
				}
				$.ajax({
						url:"${ctx}/cms/classGroup/searchclasses",
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
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/classGroup/">班组列表</a></li>
		<li class="active"><a href="${ctx}/cms/classGroup/form?id=${classGroup.id}">班组<shiro:hasPermission name="cms:classGroup:edit">${not empty classGroup.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:classGroup:edit">修改</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="classGroup" action="${ctx}/cms/classGroup/save" method="post" class="form-horizontal">
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
			<label class="control-label">班级:</label>
			<div class="controls">
				<select name="classesId" id="classesId" class="input-xlarge required">
				    <c:if test="${empty classGroup.id}">
					 		<option value="">无</option>
					 </c:if>  
					 <c:if test="${not empty classGroup.id}">
						 <c:forEach items="${classesList}" var="classes" varStatus="status">	
							<option value="${classes.id}" <c:if test="${classesId==classes.id}"> selected="selected"</c:if> >${classes.className}</option>
						 </c:forEach>
					 </c:if>  
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班组名称:</label>
			<div class="controls">
			  <input id="oldgroupName" name="oldgroupName" type="hidden" value="${classGroup.groupName}">
				<form:input path="groupName" htmlEscape="false" maxlength="200" class="required groupName"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:classGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
