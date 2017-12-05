<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script src="${ctxStatic}/front/script/jquery.js"></script>

<html>
<head>
	<title>成果展示</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/front/css/css.css" rel="stylesheet" type="text/css">
	
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
			
			var pIndex = ${lenth};
			// 增加图片
			$("#addButton").click(function() {
				var imagesid = $("#imagesid").val();
				var articleid = $("#articleid").val();
				var imageid = $("#imageid").val();
				var pname = $("#pname").val();
					var trHtml = 
					  "<tr class='imgTr'>"+
						"<td>"+
						"<input type='file' accept='${imageStr}' name='articleImages["+pIndex+"].file' />"+
						"</td>"+
						"<td>"+
							"<a href='javascript:;' class='deletes' >[删除]</a>"+
						"</td>"+
					"</tr>";
					/*  "<tr>"+
					"<td>"+
					"<input type='hidden' name='articleImages["+pIndex+"].article.id' value='"+articleid+"'  />"+
					"<input type='text' name='articleImages["+pIndex+"].name' value='"+pname+"'/>"+
					"<td>"+
						"<a href='javascript:;' class='deleteprices' >[删除]</a>"+
					"</td>"+
				"</tr>";  */
				
				$("#imagesid").append(trHtml);
				pIndex ++;
			});
			
			
			// 删除文章图片
			$("a.deletes").live("click", function() {
				var $this = $(this);
				/* var i = $this.closest("tr").find(".flag").val("2");//设置删除标记 */
				/* $this.closest("tr").css("display","none"); */
				/* pIndex--; */
				 $this.closest("tr").remove();				
				
			});
			
		});
		$(document).ready(function() {

			//判断预览图片时，图片空值或者类型是否正确
			$('input[type="file"]').live('change',function(){
				var f=$(this).val();//获取上传图片完整路径
				 var g = f.substr(f.indexOf("."));//获取上传图片的后缀名
				 var h = g.replace(".","");//把后缀名的.去掉
				 var imageType = '${imageType}';//转换为字符串
		        if(f=="")
		        { alert("请上传图片");return false;}
		        else
		        {
		        	if(imageType.indexOf(h.toLowerCase())<0)//判断是否包含该图片的后缀名
		        {
		          alert("请上传图片且必须是"+'${imageType}'+"中的一种");
		          $(this).closest("tr").remove();
		          return false;
		        }
		        }
			});
		});

	</script>
	<style>
		.addMore{
			margin-left:280px;
		}
		#imagesid{
		width:280px;
		margin-top:-30px;
		}
	</style>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">${article.category.name}列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'></c:url>">${article.category.name}<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal" enctype="multipart/form-data" >
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
				<span>
				<!-- 	<input id="weightTop" type="checkbox" onclick="$('#weight').val(this.checked?'999':'0')"><label for="weightTop">置顶</label> -->
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
 	    <div class="control-group">
			<label class="control-label">缩略图:</label>
			<div class="controls">
                <input type="hidden" id="image" name="image" value="${article.imageSrc}" />
				<tags:ckfinder input="image" type="thumb" uploadPath="/cms/article" selectMultiple="false"/>
				
			</div>
		</div>  
		<div class="control-group">
			<label class="control-label">图片上传:</label>
			<div class="controls">
			       <!-- <input type="hidden" id="articleid"/>
                <input type="hidden" id="imageid"/>
				     <!-- <input type="text" id="pname"/> -->
				    <input id="addButton" class="btn btn-primary" type="button" value="增加图片" />
				    <div style="position: absolute;margin-left: 100px;margin-top: -25px;color: #ccc;">
                   <em>*</em><span>只支持上传格式为${imageType}的图片!</span>
                </div>
				    <div id="imagesid" style="margin-top:-10px;width:350px">		
						<table>
						<tr class="imgTr">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<c:forEach items="${article.articleImages}" var="tpl" varStatus="status">
							<tr class="imgTr" style="height:90px;">
								<td>
									<input type="hidden" name="articleImages[${status.index}].id" value="${tpl.id}" />
									<input type="file" accept="${imageStr}" name="articleImages[${status.index}].file" class="file" id="upload${status.count}" style="width:100px;height:80px;" />
									<img src="${tpl.url}" id="img${status.count}"style="width:100px;height:80px;">		
								</td>
								<td style="position: relative;left: 30px;">	
									<a href="${tpl.url}" target="_blank">[查看]</a>
									<a href='javascript:;' class='deletes' >&nbsp;&nbsp;[删除]&nbsp;&nbsp;</a>
								</td>
							</tr>
						</c:forEach>	
					</table>
						
					</div>
                  
			</div>
		</div>
		
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
	
	   <c:if test="${isRecommendHidden ne 1}">
			<div class="control-group">
				<label class="control-label">是否推荐到首页:</label>
				<div class="controls">
					<form:radiobuttons path="isRecommend" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
