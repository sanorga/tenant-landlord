<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Forgot Password" />
<c:set var="theBodyID" value="admin" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<%-- <%@ include file="/WEB-INF/views/pre_header.jsp" %> --%>

<!-- <div id="body"> -->

<!-- START CONTENT HERE -->
<form:form method="post" action="forgotpwd.htm" name="form" >
  
 
  <!-- messages starts -->
  <c:if test="${! empty message}">
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <fmt:message key="${message}"/>
    </div>
  </c:if> 
  <!-- messages ends -->
  
<div id="main" class="container">
 <div class="control-box mailbox_area">	
	
            
	<div class="row">
	<div class="col-xs-12">

  <!-- notes starts -->
 	 <h4>Forgot Password</h4>
 
    <hr class="hr-thin" />
    <p><strong>Contact your System Administrator if you cannot Login to the software. If you still experience problems contact 305-692-7900 ext 1</strong></p>
  <!-- notes ends -->
	<p>Enter your current username (the email with wich you registered).<p>
	<p>We will send you a temporary password to the email address we have on file.</p>
	<p>If you do not know your username, contact customer service at 305-692-7900 ext 1.</p>
	
	<fieldset class="form-group">
		<label for="">*Username(Email you registered):</label>
<%-- 		<form:password path="oldPassword" size="30"/> --%>
		<input name="username" type="text" value="" size="25" maxlength="128"  alt="E-Mail"/>
	</fieldset>



		<input type="submit" value="Submit"  name="_btn" class="btn by_cr" onclick="alert('Check your email. You will need to log in with your new temporary password.');"/> 

									<%-- 
									<tea:ifAuthorized capability="save.user">
										<input type="submit" value="Save User" name="_btn"  class="btn by_cr" onclick="return confirmPassword('password', 'rePassword')"/>
									</tea:ifAuthorized> 
									--%>
									<a href="login.htm" class="btn by_cr">Back</a>
	

	
<!-- <table> -->
<!-- 	  <tr> -->
<!-- 		<td>&nbsp;</td> -->
<!-- 		<td><br /> -->
<!-- 			<input type="image" src="images/btn_submit.gif" alt="Submit" name="Submit" value="submit" />  -->
<%-- 			<a href="<c:url value="logout.htm"/>"><img src="images/btn_back.gif" alt="Back to Login Screen" /></a> --%>
<!-- 		</td> -->
<!-- 	  </tr> -->
<!-- </table> -->
	
	
	
	
	</div>
	</div>
				<input type="hidden" name="${_csrm.parameterName }" value="${_csrf.token }"/>

<!-- END CONTENT HERE -->
</div>
</div>

</form:form>

<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>

<%@ include file="/WEB-INF/jsp/footernew.jsp" %>