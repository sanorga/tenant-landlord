<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>

<script>

  window.onload=function(){
  	 
// 	  $("#hideandshowdiv").hide();
	 hideAndShow();
  }
  
  function hideAndShow() {		

				if (document.getElementById("Login").checked ) {
					$("#loginquestion").hide()
					$("#hideandshowdiv").removeClass('hidden');	
					$("#hideandshowdiv").show();	
  				}
				 else {
					 $("#hideandshowdiv").addClass('hidden');	
					 $("#hideandshowdiv").hide();	
				 }
			
	}
  
  function onSignIn(googleUser) {
	  var profile = googleUser.getBasicProfile();
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  console.log('Name: ' + profile.getName());
	  console.log('Image URL: ' + profile.getImageUrl());
	  console.log('Email: ' + profile.getEmail());
// 	  var tgt = "appstatusview.rpt?fromDt=" + fromDateVal + "&toDt=" + toDateVal + "&clientStrVal=" + clientStrVal + "&charType=" + charType + "&propertyIdVal=" + propertyIdVal;
// 		window.open(tgt);
// onClick="location.href='packge.htm?subscriberId=${subscriber.id}'"
// 	  window.location='/landlordapp/login.htm?email='+profile.getEmail();


// window.location='/landlordapp/home.htm';


// 	  window.location='viewinvoicepayments.htm?pageNo='+object.value+'&sortBy=${sortBy}&sortType=${sortType}';	 
	}

 
  

  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

  
</script>

<script src="https://apis.google.com/js/platform.js" async defer></script>


<style>
.hidden{ display:none;}
</style>

<head>
	<meta name="google-signin-client_id" content="223689506587-urt3a489epu7308tv9ooiomgu0uijil1.apps.googleusercontent.com">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<title>Landlord and Renter Portal Login</title>
    <style type="text/css" media="all">
      @import url("styles/login/style.css");
      @import url("styles/login/jquery.jscrollpane.css");
      @import url("styles/login/fonts/stylesheet.css");
    </style>
</head>
<body class="html not-front not-logged-in no-sidebars page-node page-node- page-node-14 node-type-page">

<div id="t-main-container">
  <div class="t-header">
    <div>
	    <a href="/" title="Home" rel="home" id="logo"> <img src="images/login/logo.png" alt="Home"></a>
        <div class="region region-header">
	    
		    <div id="block-block-1" class="block block-block">
			  <div class="content">
			    <p><strong>CALL US: 1.855.383.6268</strong></p>
			  </div>
			</div>
	
			<div id="block-system-main-menu" class="block block-system block-menu">
				<div class="content">
					<ul class="menu"><li class="first leaf"><a href="http://www.tenantevaluation.com/content/about-tenant-evaluation">About Us</a></li>
						<li class="leaf"><a href="http://www.tenantevaluation.com/content/our-services">Services</a></li>
						<li class="leaf"><a href="http://www.tenantevaluation.com/content/testimonials">Testimonials</a></li>
						<li class="leaf"><a href="http://www.tenantevaluation.com/content/databases">Databases</a></li>
						<li class="last leaf"><a href="http://www.tenantevaluation.com/content/contact-us">Contact Us</a></li>
					</ul>  
				</div>
			</div>
  		</div>
    </div>
  </div> 
  
  <div class="t-content">
    <div class="tabs"></div>        
    <div class="t-wrap">
       <div class="region region-content">
       	<div id="block-system-main" class="block block-system">

    
  			<div class="content">
    			<div id="node-14" class="node node-page clearfix" about="/login" typeof="foaf:Document">


  			<div class="content">
		    <div class="field field-name-field-blackbody field-type-text-long field-label-hidden">
		    <div class="field-items">
		    <div class="field-item even">
			 
			 
<%-- 	<form:form onsubmit="alert('You will need to log in again with your new password.');"> --%>
<form:form method="post" modelAttribute="user">
  
  <table align="right" border="0" cellpadding="0" cellspacing="0" style="width:421px">
			    <tbody>
			    <tr>	
				<td><br><p><strong>CREATE AN ACCOUNT</strong><br></p>
  
    <!-- Message Starts here -->	   
    <c:if test="${! empty message}">
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <fmt:message key="${message}"/>
    </div>
    </c:if> 
    
    <spring:bind path="*">
      <c:if test="${! empty status.errors.allErrors}">
        <div class="alert alert-danger alert-dismissible fade in" role="alert">
          <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <strong>Please fix the following errors</strong><br/>
          <form:errors path="*" cssClass="error"></form:errors>
        </div>
      </c:if>
    </spring:bind>  
    
			    </td>
			    <tr>	
				<td>

 

  <!-- contents starts --> 
<!--     <div id="main" class="container"> -->
<!--         <div class="control-box mailbox_area"> -->
<!--             <div class="row"> -->
<!--                 <div class="section_icon_head"> -->
<!--                     <img src="img/icon_users.png" alt="Users"> -->
<!--                 </div> -->
<!--             </div> -->
<!--         <div class="control_box row"> -->

<!-- 				<div class="row"> -->
<!-- 					<div class="col-xs-6"> -->
					
					<h4>User Information</h4>

										<fieldset class="form-group">
											<label for="">*Username</label>
											<form:input path="username" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*First Name</label>
											<form:input path="firstName" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*Last Name</label>
											<form:input path="lastName" maxlength="50" />
										</fieldset>
										
											
	  <fieldset class="form-group">
	  <label for="">*New Password:</label>
	  <form:password path="newPassword" maxlength="30" />
	  </fieldset>
	
	 <fieldset class="form-group">
	  <label for="">*Repeat Password:</label>
	  <form:password path="rePassword" maxlength="30" />
	  </fieldset>
	  
	  
    <br>
    <p><strong>We strongly recommend the use of complex passwords.</strong></p>

						</div>
				
						<div class="col-xs-6">

<%-- 										<c:if test="${userRoleOptions != null}"> --%>
<!-- 											<fieldset class="form-group"> -->
<!-- 												<label for="">Role</label> -->
<%-- 												<form:select path="role.role" items="${userRoleOptions}" onchange="setPropertyBlockVisibility(this.selectedIndex, this.options[this.selectedIndex].text)"/> --%>
<!-- 											</fieldset> -->
<%-- 										</c:if> --%>
										
<!-- 										<fieldset class="form-group"> -->
<!-- 											<label for="">Status</label> -->
<!-- 						               <div class="radio-inline-combo"> -->
<%-- 												<form:radiobuttons path="status" items="${globals.userStatusOptions}" /> --%>
<!-- 											</div> -->
<!-- 										</fieldset> -->
							
<!-- 							<div id="propertyBlock" > -->
<!-- 								<h4>Authorized Properties</h4> -->
<!-- 									<fieldset class="form-group"> -->
<!-- 										<label for="">Properties</label> -->
<!-- 					               <div class="radio-inline-combo"> -->
<%-- 											<form:checkboxes path="authorizedPropertyIds" items="${propertyList}" /> --%>
<!-- 										</div> -->
<!-- 									</fieldset>   					 -->
<!-- 							</div> -->

<%-- 									<c:if test="${loginUser.hasRole('save.client.user') || loginUser.hasRole('save.system.user')}"> --%>
						 				<input type="submit" value="Save User"  name="_btn" class="btn by_cr" onclick="return confirmPassword('password', 'rePassword')"/> 
<%-- 									</c:if> --%>
									<%-- 
									<tea:ifAuthorized capability="save.user">
										<input type="submit" value="Save User" name="_btn"  class="btn by_cr" onclick="return confirmPassword('password', 'rePassword')"/>
									</tea:ifAuthorized> 
									--%>
									<input type="submit" value="Cancel" name="_cancel" class="btn by_cr"/>
					</div>
					
			</div>
	</div>
</div>
</td>
					</tr>
					</tbody>
					</table>
</form:form>  
			   

				
				
				
				
		
				<table id="primaryLogin" border="0" cellpadding="0" cellspacing="0" style="height:256px; width:520px">
					<tbody>
					<tr>
							<td><h2 class="rtecenter">Landlord and Renter Website</h2>
							<br>
<!-- 								<table border="0" cellpadding="0" cellspacing="0" style="width:520px"> -->
<!-- 									<tbody> -->
<!-- 									<tr> -->
<!-- 										<td><p> <span style="color:#000000; line-height: 1.6em; ">	<strong> SIGN UP NOW</strong></span></p></td> -->
<!-- 										<td>&nbsp;</td> -->
<!-- 									</tr> -->
<!-- 									</tbody> -->
<!-- 								</table> -->
									<div id="loginquestion">
									<table border="0" cellpadding="0" cellspacing="0" style="width:260px">
									<tbody>
									<tr>
										<td><p> <span style="color:#000000; line-height: 1.6em; ">	<strong> SIGN UP NOW</strong></span></p></td>
										<td>&nbsp;</td>
									</tr>
									
									<tr>
										<td><p>Already have an account?</td>
										<td style="text-align:right">
			<!-- 							<input type="submit" class="submit-button" name="submit-button" value="Login" onclick="hideAndShowSpouse()"></td> -->
										 <input type="button" value="Login"  id ="Login" name="Login" class="button" onClick="this.checked=true; hideAndShow()"/>
										 </td>
									</tr>
									</tbody>
								</table>
									</div>
									
					<div id="hideandshowdiv" class="hidden" >					
						<form:form id="usernameForm" action="j_spring_security_check" method="post">
						
						<c:if test="${! empty message}">
							<div style="color: red;" >
								<input type="hidden" name="message" id="message" />
								"${message}"
							</div>
						</c:if>
						<c:if test="${! empty reset}">
							<c:if test="${reset == true}">
							<div style="color: red;" >
								<input type="hidden" name="reset" id="reset" />
								Your password has been reset and we have sent you a new temporary password.<br>
								Please check your email.
							</div>
							</c:if>
						</c:if>
			  			
						<c:if test="${param.error != null }">
							<div class="alert alert-error">
								Problem logging in.
								<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null }">
									<br>Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
								</c:if>
							</div>
						</c:if>
			  			
						<c:if test="${param.e405 != null }">
							<div class="alert alert-error">
								Unable to login. You must have cookies enabled to use this application.
								<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null }">
									<br>Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
								</c:if>
							</div>
						</c:if>
			  			
						<c:if test="${param.denied != null }">
							<div class="alert alert-error">
								Access Denied. Perhaps your session timed out.
								<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null }">
									<br>Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
								</c:if>
							</div>
						</c:if>
			  			
						<c:if test="${param.expired != null }">
							<div class="alert alert-success">
								Failed to login.
								<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null }">
									<br>Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
								</c:if>
							</div>
						</c:if>
						
						<p>ENTER YOUR USERNAME<br><input name="j_username" id="j_username" size="70" type="text"></p>
			
						<p>PASSWORD<br><input name="j_password" size="70" type="password" id="j_password" autocomplete="off"></p>
			
						<table border="0" cellpadding="0" cellspacing="0" style="width:520px">
							<tbody>
								<tr>
									<td><a href="forgotpwd.htm" style="line-height: 1.6em;">FORGOT YOUR PASSWORD</a></td>
									<td style="text-align:right"><input type="submit" class="submit-button" name="submit-button" value="Submit"></td>
								</tr>
							</tbody>
						</table>
			</form:form><br>
</div>					
					
					<br>

				<p> <span style="color:#000000; line-height: 1.6em; ">	<strong> EXPRESS REGISTRATION</strong></span></p>

				<p>Sign up using your Facebook or Google Account</p>
				<p>No password to remember Fast and Convenient</p>
<br> 
<!-- 				<p><span style="color:#000000">	<strong>Data Security: </strong></span> -->
<!-- 				Precise Background Technologies complies with the Fair Credit Reporting Act and the Federal Trade Commission Act which require that we provide reasonable security for your social security, date of birth, full name, etc. Your data will be encrypted using 256 bits and will be deleted from our system after 30 days. -->
<!-- 				</p> -->
						<table border="0" cellpadding="0" cellspacing="0" style="width:260px">
						<tbody>
<!-- 							<input type="submit" class="submit-button" name="submit-button" value="Login" onclick="hideAndShowSpouse()"></td> -->
							 
<%-- 							 <c:url value="/img/google-logo.png" var="googleLogoUrl"> --%>
<%-- <img src="${googleLogoUrl}" /> --%>
<%-- <form action="${openIDLoginUrl}" method="post"> --%>
<!--     For Google users: -->
<!--    <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id" /> -->
<!--    <input type="submit" value="Sign with Google" /> -->
<%-- </form> --%>
<%-- </c:url> --%>


<tr>
<td>
<%--  <form:form method="POST" action="/home.htm"> --%>
<!-- <div class="g-signin2" data-onsuccess="onSignIn"></div> -->
<%-- </form:form> --%>
<c:url value="j_spring_openid_security_check" var="form_url_openid" />
<form action="${fn:escapeXml(form_url_openid)}" id="google-login-form" method="post" class="text-centered">
<%-- <form action="j_spring_openid_security_check" id="google-login-form" method="post"> --%>

    <input id="openid_identifier" name="openid_identifier"  size="50" 
           maxlength="100" type="hidden"
           value="https://www.google.com/accounts/o8/id"/>
    <label class="fixed"><!-- intentionally left blank --></label>
    <div class="input">
       <input id="proceed-google" type="submit" value="Do it with Google" />
    </div>
</form>


</td>
<td>
<a href="#" onclick="signOut();">Google Sign out</a>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<%-- <form:form  action="googlelogin.htm" method="post"> --%>
<!--   <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id" />  -->
<!--    <input type="submit" class="submit-button" value="Sign with Google" />  -->
<%--  </form:form> --%>

	
				<td style="text-align:center">
<!-- 				<input type="submit" class="submit-button" name="submit-button" value="Login" onclick="hideAndShowSpouse()"></td> -->
					<input type="button" value="Facebook Account"  id ="FacebookAcc" name="FacebookAcc" class="button" "/>
				 </td>
				 <td>
					<div class="row social-button-row">
	                	<div class="col-lg-4">
	                    <!-- Add Facebook sign in button -->
	                    <a href="${pageContext.request.contextPath}/auth/facebook"><button class="btn btn-facebook"><i class="icon-facebook"></i> | <spring:message code="label.facebook.sign.in.button"/></button></a>
	                	</div>
            		</div>
            	</td>
						</tr>
						</tbody>
					</table>
<br> <br><br> <br> <br> <br> 
			</td>
		</tr>
		</tbody>
		</table>
		
		</div>
		</div>
		</div>
		<div class="field field-name-field-midbody field-type-text-long field-label-hidden">
		<div class="field-items">
		<div class="field-item even"><h2 class="rtecenter"><strong>FCRA</strong></h2>
<p><strong>You shall:</strong></p>
<p>a. Comply with all applicable federal, state and local laws governing the access and use of Screening Reports. This includes, without limitation, providing any Applicant with any consents, disclosures and reports required by the FCRA, and obtaining the prior written consent of the Applicant before procuring a Screening Report on the Applicant.</p>
<p>b. Comply with FCRA Section 606(a)(2) and applicable state and local laws each time You request an "Investigative Consumer Report" (as defined by the FCRA and/or state and local laws) from Tenant Evaluation LLC for any Applicant.</p>
<p>c. Assume full responsibility for the final verification of the Applicant's identity;</p>
<p>d. Base all Your Tenant or hiring decisions and related actions on Your policies and procedures and not rely on Tenant Evaluation LLC for (nor shall Tenant Evaluation LLC render) any opinions on or for any Screening Reports;Seek legal advice from and utilize qualified legal counsel for the use of any Screening Reports provided under this Agreement.</p>
<p>e. Keep strictly confidential in accordance with this Agreement any information and identification numbers and passwords You receive from or gain access through Tenant Evaluation LLC.</p>
<p>f. For six years from the date You receive the Screening Report, keep a copy or copies of: (i) the Screening Report, (ii) the consent form You obtained from the Applicant for the applicable Screening Report, and (iii) a description of how You used or relied on the Screening Report in making a decision about Applicant.</p>
<p>g. LIMITED WARRANTY AND REMEDY. Tenant Evaluation LLC warrants only that it will provide Screening Reports based on Tenant Evaluation LLC' then-current access to public databases. If Tenant Evaluation LLC materially breaches this warranty, it may, at its sole discretion, re-perform the applicable search or refund Your fees for that search. You understand and agree that this is Your sole and exclusive remedy for any material breach of this warranty.</p>
</div></div></div><div class="field field-name-field-scroll-red field-type-number-integer field-label-hidden"><div class="field-items"><div class="field-item even">300</div></div></div>  </div>

  
  
</div>
  </div>
</div>
  </div>
    </div>
  </div>
  <div class="t-footer">
    <div>
        <div class="region region-footer">
    <div id="block-menu-menu-menu-footer" class="block block-menu">

    
  <div class="content">
    <ul class="menu"><li class="first leaf"><a href="http://www.tenantevaluation.com/content/about-tenant-evaluation" title="">About Us</a></li>
<li class="leaf"><a href="http://www.tenantevaluation.com/content/our-services" title="">Services</a></li>
<li class="leaf"><a href="http://www.tenantevaluation.com/content/disputes" title="">Disputes</a></li>
<li class="leaf"><a href="http://www.tenantevaluation.com/content/forms" title="">Forms</a></li>
<li class="leaf"><a href="http://www.tenantevaluation.com/content/contact-us" title="">Contact Us</a></li>
<li class="last leaf"><a href="http://www.tenantevaluation.com/content/policies" title="">Policies</a></li>
</ul>  </div>
</div>
<div id="block-block-2" class="block block-block">

    
  <div class="content">
    <h2>TENANT EVALUATION</h2>
<p>The smart Background Screening Company</p>
  </div>
</div>
  </div>
          </div>
  </div>
</div>	

	
	
</body>
</html>
