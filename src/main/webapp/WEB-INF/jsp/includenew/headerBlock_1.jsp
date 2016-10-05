   <div id="header">

      <h1>Welcome ${loginUser.firstName} ${loginUser.lastName}</h1>
      <h3>
         <c:if test="${isPartnerUser}">Partner:</c:if>
         <c:if test="${isClientUser}">Client:</c:if>
         <c:if test="${isSystemAdminUser}">System Admin: </c:if>
         ${loginUser.subscriber.name}
      </h3>
      <h2>The Smart Background<br />
         Screening Company
      </h2>
<c:if test="${ isAccountNotExpired}">
      <p>
         <c:set var="isRoleBD" value="${myPrimaryRole == 'BD'}" />
         <c:choose>
         <c:when test="${fn:startsWith(loginUser.subscriber.status,'P')}">
            <a href="viewinvoicepayments.htm?subscriberId=${loginUser.subscriber.id}">Invoices</a>   |   
         </c:when>
         <c:otherwise>  
            <tea:ifAuthorized capability="show.wrkqueue"><a href="viewapplications.htm">Work Queue</a>   |   </tea:ifAuthorized>
            <c:if test="${myPrimaryRole != 'BD'}">
               <tea:ifAuthorized capability="show.mailbox"><a href="viewapplications.htm">Mailbox</a>   |   </tea:ifAuthorized>
            </c:if>
   
            <c:choose>
            <c:when test="${isClientUser}">
               <sec:authorize access="hasRole('new.application')">
                  <sec:authorize access="hasRole('new.tenant.application')"><a href="applicationwizard.htm?appType=T">Tenant Application</a>   |</sec:authorize>
                  <sec:authorize access="hasRole('new.employment.application')"><a href="applicationwizard.htm?appType=E">Employment Application</a>   |</sec:authorize>
                  <a href="otherapplication.htm">Other Services</a>   |   
               </sec:authorize>
            </c:when>
            <c:when test="${ (myPrimaryRole == 'BD')}">
            <tea:ifAuthorized capability="show.mailbox"><a href="viewapplications.htm">Application List</a>   |   </tea:ifAuthorized>
            </c:when>
            <c:when test="${ (myPrimaryRole == 'EC')}">
                     <a href="home.htm?conditionId=1">Admin</a>  |   
                     <a href="logout.htm">Logout</a>
            </c:when>
            <c:otherwise>
            <tea:ifAuthorized capability="new.application">
               <a href="viewclients.htm?msg=chooseclient">Create New Application</a>   | 
            </tea:ifAuthorized>
            </c:otherwise>
            </c:choose>   
         </c:otherwise>
         </c:choose>

         <c:if test="${ !(myPrimaryRole == 'EC')}">
         <a href="home.htm">Admin</a>   |   
         <a href="logout.htm">Logout</a>
         </c:if>
      </p>
      </c:if>
   </div>
