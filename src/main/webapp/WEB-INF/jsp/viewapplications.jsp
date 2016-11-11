<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Applications" />
<c:set var="theBodyID" value="admin" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>


<ol class="breadcrumb">
  <li><a href="home.htm">Home</a></li>
<%--     <c:choose> --%>
<%--       <c:when test="${loginUser.subscriber.id eq subscriber.id}"> --%>
<!--         <li> -->
<%-- 	        <c:if test="${fn:startsWith(subscriber.subscriberType, 'S')}">System Level Users</c:if> --%>
<%-- 	    	<c:if test="${fn:startsWith(subscriber.subscriberType, 'P')}">List of Users for Partner : ${subscriber.name }</c:if> --%>
<%-- 	    	<c:if test="${fn:startsWith(subscriber.subscriberType, 'C')}">List of Users for Client : ${subscriber.name }</c:if> --%>
<!--         </li> -->
<%--       </c:when> --%>
<%--       <c:otherwise> --%>
<!--         <li><a href="viewclients.htm">Clients</a></li> -->
<!--         <li> -->
<%--             <c:if test="${fn:startsWith(subscriber.subscriberType, 'S')}">System Level Users</c:if> --%>
<%-- 		    <c:if test="${fn:startsWith(subscriber.subscriberType, 'P')}">List of Users for Partner : ${subscriber.name }</c:if> --%>
<%-- 		    <c:if test="${fn:startsWith(subscriber.subscriberType, 'C')}">List of Users for Client : ${subscriber.name }</c:if> --%>
<!--          </li> -->
<%--       </c:otherwise> --%>
<%--     </c:choose> --%>
</ol>

  <!-- sub heading starts -->
<!-- <div id="subheader"> -->
<!--   <p> -->
<%--     <c:if test="${fn:startsWith(subscriber.subscriberType, 'S')}">System Level Users</c:if> --%>
<%--     <c:if test="${fn:startsWith(subscriber.subscriberType, 'P')}">List of Users for Partner : ${subscriber.name }</c:if> --%>
<%--     <c:if test="${fn:startsWith(subscriber.subscriberType, 'C')}">List of Users for Client : ${subscriber.name }</c:if> --%>
<!--   </p>     -->
<!-- </div> -->

    <c:if test="${! empty message}">
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <fmt:message key="${message}"/>
    </div>
  </c:if> 

    <div id="main" class="container">
        <div class="control-box mailbox_area">
            <div class="row">
                <div class="section_icon_head">
                 <img src="img/icon_applications.png" alt="Applications">
		    	
                </div>
            </div>
        <!-- <div class="control_box row"> -->

<br>

<form:form method="post">
<div class="row">
    <div class="icon_main_nav col-xs-12">
       <c:if test="${loginUser.hasRole('view.my.applications') }"> 
       <table>
       <tr>
       		<td>&nbsp</td>
        	<td>           <button type="submit" name="_inprogress" class="btn by_cr">In Progress</button> </td>
           <td>&nbsp</td>
           
           <td> 			<button type="submit" name="_completed" class="btn by_cr">Completed</button> </td>
           <td>&nbsp</td>

           <td> 			<button type="submit" name="_declined" class="btn by_cr">Denied</button> </td>
           <td>&nbsp</td>
           <td> 			<button type="submit" name="_approved" class="btn by_cr">Approved</button> </td>
            <td>&nbsp</td>
           <td> 			<button type="submit" name="_allstatus" class="btn by_cr">All</button> </td>
       </tr>
       </table>
       </c:if>
       </div>
       </div>
      <div class="row">
    <div class="icon_main_nav col-xs-12">
       
        <table id="teaSortableTable" class="table table-sm sortableTable">
            <thead>
                <tr>

                    <th>SmartMove</th>
                    <th>Date Submitted</th>
                    <th>Email</th>
                    <th>Full Name</th>
                    <th>Address</th>

                    <th>Report</th>
                    <th>Status</th>
                </tr>
                <tbody>
                    <c:forEach var="app" items="${userApplications}">
                        <tr>
<%--                             <th scope="${app.id}"> --%>
<%--                                 <c:if test="${loginUser.hasRole('view.my.applications') }"> --%>
<%--                                     <a href="application.htm?applicationId=${app.id}"><img src="images/docs.jpg" alt="Check Report"></a> --%>
<%--                                 </c:if> --%>
<%--                                 <tea:ifAuthorized capability="view.user">
<%--                                       <a href="user.htm?userId=${usr.id}">View/Edit</a> --%>
<%--                                     </tea:ifAuthorized> --%> 
<!--                             </th> -->
                            <td>${app.applicationExtId}</td>
                            <td>${app.createdDate}</td>
                            <td class="ellipsis">${app.applicantEmailId}</td>
                            <td>${app.fullName}</td>
							<td>${app.addressLine1}</td>
<%-- 							<td>${app.city}</td> --%>
<%-- 							<td>${app.creditRecommendationLabel}</td> --%>
							<c:if test="${app.canRequestReport}"> 
								<td><a href="application.htm?applicationId=${app.id}"><img src="images/docs.jpg" alt="Check Report"></a></td>
							</c:if>
							<c:if test="${!app.canRequestReport}"> 
								<td>&nbsp;</td>
							</c:if>
                            <td>${app.stateLabel}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </thead>
        </table>
</form:form>

    </div>
</div>

<div class="row">
    <div class="icon_main_page_nav text-right">
          <div id="pager" class="pager">
              <form:form method="post">
<%--                    <c:if test="${loginUser.hasRole('view.my.applications') }"> --%>
<!--                       <button type="submit" name="_new" class="btn by_cr">New User</button> -->
<%--                   </c:if> --%>
                  <%-- <tea:ifAuthorized capability="new.user"><input type="submit" value="New User" name="_new" class="button"/></tea:ifAuthorized> --%>
                  <button type="submit" name="_cancel" class="btn by_cr">Cancel</button>

                  <div id="teaPaginationDiv" class="nav_pager">
                      <span class="fa-stack first pgr">
                        <i class="fa fa-square-o fa-stack-2x"></i>
                        <i class="fa fa-backward fa-stack-1x"></i>
                      </span>
                      <span class="fa-stack prev pgr">
                        <i class="fa fa-square-o fa-stack-2x"></i>
                        <i class="fa fa-chevron-left fa-stack-1x"></i>
                      </span>
                      <span class="pagedisplay"></span>
                      <span class="fa-stack next pgr">
                        <i class="fa fa-square-o fa-stack-2x"></i>
                        <i class="fa fa-chevron-right fa-stack-1x"></i>
                      </span>
                      <span class="fa-stack last pgr">
                        <i class="fa fa-square-o fa-stack-2x"></i>
                        <i class="fa fa-forward fa-stack-1x"></i>
                      </span>
                      <select class="pagesize">
                          <option selected="selected" value="10">10</option>
                          <option value="20">20</option>
                          <option value="30">30</option>
                          <option value="40">40</option>
                      </select>
                      <select class="gotoPage" title="Select page number"></select>
                  </div>
              </form:form>
          </div>         
    </div>
</div>

    <div class="icon_main_page_nav-global text-left">
        <a href="home.htm"><img src="img/btn_nav_arrow_left.png" alt="" width="32"></a>
    </div>
</div>

</div>
</div>

<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/footernew.jsp" %>