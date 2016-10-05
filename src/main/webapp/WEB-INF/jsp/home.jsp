<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Admin" />
<c:set var="theBodyID" value="main_menu" />
<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<div id="main" class="container">
     <div class="control-box" style="width: 700px; position: fixed; top: calc(50% + -150px); left: calc(50% - 350px);z-index: 900;">
<!--          <a class="mn_bx_btn" href="inviteToApply.htm"><img src="img/inviteToApply.png" alt="Invite to Apply" width="120"></a> -->
         <a class="mn_bx_btn" href="anonymousUser.htm"><img src="img/icon_inviteToApply.png" alt="Invite to Apply" width="120"></a>

<%--          <tea:ifAuthorized capability="show.wrkqueue">   --%>
				<a class="mn_bx_btn" href="example.htm"><img src="img/icon_applications.png" alt="Applications" width="120"></a>
<!-- 				<a class="mn_bx_btn" href="applications.htm"><img src="img/applications.png" alt="Applications" width="120"></a> -->
<%--          </tea:ifAuthorized> --%>
<%--          <tea:ifAuthorized capability="show.mailbox"> --%>
				<a class="mn_bx_btn" href="example.htm"><img src="img/icon_settings.png" alt="Settings" width="120"></a>
<!-- 				<a class="mn_bx_btn" href="settings.htm"><img src="img/settings.png" alt="Settings" width="120"></a> -->
<%--           </tea:ifAuthorized> --%>
        
     </div>
 </div>
<P>  The time on the server is ${serverTime}. </P>
<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/footernew.jsp" %>  
