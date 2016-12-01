<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Admin" />
<c:set var="theBodyID" value="main_menu" />
<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

 <!-- messages starts -->
      <c:if test="${! empty message}">
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <fmt:message key="${message}"/>
    </div>
    </c:if> 
  

  
<div id="main" class="container">
     <div class="control-box" style="width: 700px; position: fixed; top: calc(50% + -150px); left: calc(50% - 350px);z-index: 900;">
<!--          <a class="mn_bx_btn" href="inviteToApply.htm"><img src="img/inviteToApply.png" alt="Invite to Apply" width="120"></a> -->
         <a class="mn_bx_btn" href="anonymousUser.htm"><img src="img/icon_inviteToApply.png" alt="Invite to Apply" width="120"></a>

<%--          <tea:ifAuthorized capability="show.wrkqueue">   --%>
				<a class="mn_bx_btn" href="viewapplications.htm?userId=${user.id}"><img src="img/icon_applications.png" alt="Applications" width="120"></a>
<!-- 				<a class="mn_bx_btn" href="applications.htm"><img src="img/applications.png" alt="Applications" width="120"></a> -->
<%--          </tea:ifAuthorized> --%>
<%--          <tea:ifAuthorized capability="show.mailbox"> --%>
				<a class="mn_bx_btn" href="user.htm?userId=${user.id}"><img src="img/icon_settings.png" alt="Settings" width="120"></a>
<!-- 				<a class="mn_bx_btn" href="settings.htm"><img src="img/settings.png" alt="Settings" width="120"></a> -->
<%--           </tea:ifAuthorized> --%>
        
     </div>
 </div>
<P>  The time on the server is ${serverTime}. </P>
<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/footernew.jsp" %>  
