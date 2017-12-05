<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#title").focus();		
			
			$("#inputForm").validate({
				submitHandler: function(form){
                    if ($("#categoryId").val()==""){
                        $("#categoryName").focus();
                        top.$.jBox.tip('请选择归属栏目','warning');
                    }else if (CKEDITOR.instances.content.getData()=="" && $("#link").val().trim()==""){
                        top.$.jBox.tip('请填写正文','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
				$("#classesId option").remove();
				if($gradeId.val()!=""){
					$classesId.append('<option value="">请选择班级</option>');
				}else{
					$classesId.append('<option value=""></option>');
					$classesId.triggerHandler('change'); 
				}
				$.ajax({
						url:"${ctx}/cms/article/searchclasses",
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
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">${article.category.name}列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'></c:url>">${article.category.name}<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">归属栏目:</label>
			<div class="controls">
					<tags:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}" 
					title="栏目"  url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="false" cssClass="required"/>&nbsp;       
                <span>
                   <!-- <input id="url" type="checkbox" onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()">  <label for="url">外部链接</label>-->
                </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
				&nbsp;<label>颜色:</label>
				<form:select path="color" class="input-mini" style="width:65px">
					<form:option value="" label="默认"/>
					<form:options items="${fns:getDictList('color')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
        <div id="linkBody" class="control-group" style="display:none">
            <label class="control-label">外部链接:</label>
            <div class="controls">
                <form:input path="link" htmlEscape="false" maxlength="200" class="input-xlarge"/>
                <span class="help-inline">绝对或相对地址。</span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">权重:</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" maxlength="200" class="input-mini required digits"/>&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">年级:</label>
			<div class="controls">
				<select name="gradeId" id="gradeId" class="input-xlarge">
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
				<select name="classesId" id="classesId" class="input-xlarge">
				    <c:if test="${empty article.id}">
					 		<option value=""></option>
					 </c:if>  
					 <c:if test="${not empty article.id}">
						 <c:forEach items="${classesList}" var="classes" varStatus="status">	
							<option value="${classes.id}" <c:if test="${classesId==classes.id}"> selected="selected"</c:if> >${classes.className}</option>
						 </c:forEach>
					 </c:if>  
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业:</label>
			<div class="controls">
				<select name="majorId" id="majorId" class="input-xlarge">
					 <option value="" >请选择</option>    
					 <c:forEach items="${majorList}" var="major" varStatus="status">	
						<option value="${major.id}" <c:if test="${majorId==major.id}"> selected="selected"</c:if> >${major.majorName}</option>
					 </c:forEach>
				</select>
			</div>
		</div>
		<c:if test="${imageHidden ne 1}">
			<div class="control-group">
				<label class="control-label">缩略图:</label>
				<div class="controls">
	                <input type="hidden" id="image" name="image" value="${article.imageSrc}" />
					<tags:ckfinder input="image" type="thumb" uploadPath="/cms/article" selectMultiple="false"/>
				</div>
			</div>
		</c:if>	
		
		<div class="control-group">
			<label class="control-label">正文:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/cms/article" />
			</div>
		</div>
		<form:hidden path="articleData.copyfrom"/>
		<form:hidden path="articleData.relation"/>
		<form:hidden path="articleData.allowComment"/>
		<div class="control-group">
			<label class="control-label">发布时间:</label>
			<div class="controls">
				<input id="publishDate" name="publishDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${article.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<form:hidden path="delFlag"/>
		<form:hidden path="customContentView"/>
		<form:hidden path="viewConfig"/>
		<div class="form-actions">
			<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>