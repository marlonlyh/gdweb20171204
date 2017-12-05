<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="category" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
<div class="openmenu" style="z-index:99;">
      <div class="openmenusub clearfix">
      <c:forEach items="${fnc:getCategoryList(category.site.id, category.id, -1, '')}" var="ctpl" varStatus="cstatus" >
     	 <dl>
		    <dt><cms:channelHref category="${ctpl}"/></dt>
		    <c:if test="${fn:length(ctpl.validList) gt 0}">
			    <dd>
			      <ul>
			    	  <c:forEach items="${ctpl.validList}" var="ptpl" varStatus="pstatus">
			       	 <li><cms:channelHref category="${ptpl}"/></li>
			        </c:forEach>
			        </ul>
			      </dd>
		      </c:if>
		  </dl>
      </c:forEach> 
      </div>  
    </div>