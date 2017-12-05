<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/cms/front/include/taglib.jsp"%>
<%@ attribute name="topcategory" type="com.whir.ht.cms.entity.Category" required="true" description="栏目对象"%>
  <c:forEach items="${fnc:getCategoryList(category.site.id, category.parent.id, -1, '')}" var="tpl" varStatus="status">
  		<c:choose>
   			<c:when test="${not empty tpl.href}">
    			<c:choose>
	    			<c:when test="${fn:indexOf(tpl.href, '://') eq -1 && fn:indexOf(tpl.href, 'mailto:') eq -1}">
	    			<c:set var="url" value="${ctx}${tpl.href}"/></c:when><c:otherwise><c:set var="url" value="${tpl.href}"/></c:otherwise>
	    		</c:choose>
   			</c:when>
   			<c:otherwise><c:set var="url" value="${ctx}/list-${tpl.id}${urlSuffix}"/></c:otherwise>
   	</c:choose>
  		<li>
  			<a href="${url}" target="${tpl.target}" id="snav-${tpl.id}">${tpl.name}</a>
				<ul>
 				<c:forEach items="${tpl.validList}" var="ctpl" varStatus="cstatus">
 				<c:choose>
   			<c:when test="${not empty ctpl.href}">
    			<c:choose>
	    			<c:when test="${fn:indexOf(ctpl.href, '://') eq -1 && fn:indexOf(ctpl.href, 'mailto:') eq -1}">
	    			<c:set var="curl" value="${ctx}${ctpl.href}"/></c:when><c:otherwise><c:set var="curl" value="${ctpl.href}"/></c:otherwise>
	    		</c:choose>
   			</c:when>
   			<c:otherwise><c:set var="curl" value="${ctx}/list-${ctpl.id}${urlSuffix}"/></c:otherwise>
   			</c:choose>
			      <li class="active">				      	  
	      			<a class="active" href="${curl}" target="${ctpl.target}" id="snav-${tpl.id}-${ctpl.id}">${ctpl.name}</a>
			  				<c:forEach items="${ctpl.validList}" var="ptpl" varStatus="pstatus">
			  				<c:choose>
				   			<c:when test="${not empty ptpl.href}">
				    			<c:choose>
					    			<c:when test="${fn:indexOf(ptpl.href, '://') eq -1 && fn:indexOf(ptpl.href, 'mailto:') eq -1}">
					    			<c:set var="purl" value="${ctx}${ptpl.href}"/></c:when><c:otherwise><c:set var="purl" value="${ptpl.href}"/></c:otherwise>
					    		</c:choose>
				   			</c:when>
				   			<c:otherwise><c:set var="purl" value="${ctx}/list-${ptpl.id}${urlSuffix}"/></c:otherwise>
				   			</c:choose>
					      <li><a href="${purl}" target="${ptpl.target}" id="snav-${tpl.id}-${ctpl.id}-${ptpl.id}">${ptpl.name}</a></li>
					      </c:forEach>
			       </li>
	      </c:forEach>
      </ul>
  		</li>
  </c:forEach>
