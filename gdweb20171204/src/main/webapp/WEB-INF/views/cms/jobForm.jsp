<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>职位管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
		<li><a href="${ctx}/cms/job/">职位列表</a></li>
		<li class="active"><a href="${ctx}/cms/job/form?id=${job.id}">职位<shiro:hasPermission name="cms:job:edit">${not empty job.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:job:edit">修改</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="job" action="${ctx}/cms/job/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">职位名称:</label>
			<div class="controls">
				<form:input path="jobName" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位名称:</label>
			<div class="controls">
				<form:select path="jobType" htmlEscape="false" maxlength="200" class="input-xlarge required">
				<form:option value="">--请选择--</form:option>
				<form:option value="social">社会招聘</form:option>
				<form:option value="school">校园招聘</form:option>
				<form:option value="height">高端人才</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属部门:</label>
			<div class="controls">
				<form:input path="departMent" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		

		<div class="control-group">
			<label class="control-label">学历要求:</label>
			<div class="controls">
				<form:select path="school" htmlEscape="false" maxlength="200" class="input-xlarge required">
				<form:option value="">--请选择--</form:option>
				<form:option value="小学及以下">小学及以下</form:option>
				<form:option value="初中">初中</form:option>
				<form:option value="高中">高中</form:option>
				<form:option value="专科">专科</form:option>
				<form:option value="本科">本科</form:option>
				<form:option value="研究生">研究生</form:option>
				<form:option value="博士">博士</form:option>
				<form:option value="其他">其他</form:option>						
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">工作年限:</label>
			<div class="controls">
					<form:select path="school" htmlEscape="false" maxlength="200" class="input-xlarge required">
				<form:option value="">--请选择--</form:option>
				<form:option value="social">1-3年</form:option>
				<form:option value="school">3-5年</form:option>
				<form:option value="height">5-8年</form:option>
				<form:option value="height">8年以上</form:option>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">薪资待遇:</label>
			<div class="controls">
				<form:input path="salary" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">福利待遇:</label>
			<div class="controls">
				<form:input path="welfare" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">工作地点:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属公司:</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务描述:</label>
			<div class="controls">			
				<form:textarea path="jobDesc" htmlEscape="false" maxlength="5000" rows="4" class="input-xxlarge required"/>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入职要求:</label>
			<div class="controls">			
				<form:textarea path="joinReqiure" htmlEscape="false" maxlength="5000" rows="4" class="input-xxlarge required"/>
		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间:</label>
			<div class="controls">
				<input id="publishDate" name="publishDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${job.publishDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:job:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
