<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="notVisited" value="true" />
<c:forEach items="${myRequests}" var="item" varStatus="loopIdx">
	<c:if test="${ item.isAccepted &&notVisited}">
	<c:set var="notVisited" value="false" />
    <h3>Accepted Outgoing Requests</h3>
    <table>
				<thead>
                	<tr>
                    	<th><h4>Tutor</h4></th>
                        <th><h4>E-Mail</h4></th>
                        <th><h4>Course</h4></th>
                        <th></th>
                    </tr>
                </thead>
               
                <tbody>
				</c:if>
				<c:if test="${ item.isAccepted}">
                	<tr>
		                	
		                	 
		                		<tr>
									<td>
										<c:out value="${item.tutor.name}" />
									</td>
									<td>
										<c:out value="${item.tutor.email}" />
									</td>
									<td>
								   		<c:out value="${item.course.courseName}" />
								   	</td>
								   	<td>
								   		<form:form method="post" action="profile" modelAttribute="searchForm" id="results" cssClass="form-horizontal"  autocomplete="off">
										<button type=submit name=itemUser value="${item.tutor.email}">Visit Profile</button>
								   		</form:form>
								   	</td>
								</tr>
					</tr>
					</c:if>
</c:forEach> 
  </tbody>
</table>