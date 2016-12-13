<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Admin" />
<c:set var="theBodyID" value="main_menu" />
<%@ include file="/WEB-INF/jsp/headernewstyle.jsp" %>


<script type="text/javascript">

		function newDoc() {
			window.location.assign("login.htm")
		}
  
  function onSignIn(googleUser) {
	  
	  if (document.getElementById('socialService').value == "1") {
		  document.getElementById('j_username1').value = null;
		  document.getElementById('j_password').value = null;
		  document.getElementById('socialService').value = null;
		  signOut();
		  return;
	  }
	  var profile = googleUser.getBasicProfile();
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  document.getElementById('j_username1').value = profile.getEmail();
	  
	  console.log('Name: ' + profile.getName());
	
	  console.log('Given Name: ' + profile.getGivenName());
      console.log('Family Name: ' + profile.getFamilyName());
      console.log("Email: " + profile.getEmail());
      console.log('Image URL: ' + profile.getImageUrl());

      // The ID token you need to pass to your backend:
      var id_token = googleUser.getAuthResponse().id_token;
      document.getElementById('j_password1').value = id_token
      document.getElementById('socialService').value = 1;
      console.log("ID Token: " + id_token);
document.googleForm.submit();

 //     window.location.assign("/landlordapp/googlelogin.htm?email=tenant.developer@gmail.com&idToken="+ id_token+"&name=" + profile.getName());
// 	  window.location.assign("http://localhost:8080/landlordapp/login.htm?email=tenant.developer@gmail.com");
	  

// 	  window.location='viewinvoicepayments.htm?pageNo='+object.value+'&sortBy=${sortBy}&sortType=${sortType}';	 
	};

 
  

  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

  
</script>

<script src="https://apis.google.com/js/platform.js" async defer></script>

    <c:if test="${! empty message}">
    <div class="alert alert-danger alert-dismissible fade in" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <fmt:message key="${message}"/>
    </div>
    </c:if> 
  
<%--     <spring:bind path="*"> --%>
<%--       <c:if test="${! empty status.errors.allErrors}"> --%>
<!--         <div class="alert alert-danger alert-dismissible fade in" role="alert"> -->
<!--           <button type="button" class="close" data-dismiss="alert" aria-label="Close"> -->
<!--             <span aria-hidden="true">&times;</span> -->
<!--           </button> -->
<!--           <strong>Please fix the following errors</strong><br/> -->
<%--           <form:errors path="*" cssClass="error"></form:errors> --%>
<!--         </div> -->
<%--       </c:if> --%>
<%--     </spring:bind>   --%>
    
 <div id="main" class="container">
        <div class="control-box mailbox_area">

       
<!-- 		<div class="row">           -->
		
<!-- 						<div class="col-xs-12"> -->
							<div >
							<img src="images/main.jpg" data-holder-rendered="true"  width="80%" style="align-items:center;height:auto;position:relative;">				
							<div id="loginquestion" style="position:absolute;right:400px;top:50px;">
									<table border="0" cellpadding="0" cellspacing="0" style="width:260px">
										
											<tr>
												
												<td style="text-align:right">
<%-- 												<form:form method="post" modelAttribute="user" > --%>
												    <input type="button" value="Client Login"  id ="_login" name="_login" class="button" onclick="newDoc()" />
<%-- 												</form:form> --%>
												</td>
											</tr>
										
									</table>
								</div>
												
								
								<br>
						<div id="landlord" style="position:absolute;right:1400px;top:500px;">
								<table >
										<tr>	
										    	<td>
								<%--              	<c:if test="${loginUser.hasRole('USER')}"> --%>
		<!-- 						             		<input type="submit" value="START A FREE REGISTRATION" name="_new" class="button"/> -->
									              	<a href="landlordmain.htm"><img src="images/login/landlordservices.png" alt="New User"></a>
										<%-- 				</c:if> --%>
							          			</td>
											</tr>
											<
								</table>
										
							
  								
			
			</div>	
			
						<div id="all" style="position:absolute;right:1800px;top:620px;">
								
											
												<a href="landlordmain.htm">	<img class="image1" src="images/landlords.png" alt="landlords"></a>
						</div>					
												
												
						
						<div id="renter" style="position:absolute;right:1380px;top:620px;">
								<a href="rentermain.htm"> <img class="image2" src="images/renters.png" alt="Renters"></a>
							
						</div>
			
			
						<br> <br><br> <br> <br> <br> 
				
			</div>
 
 	</div>
 
 </div>
<%@ include file="/WEB-INF/jsp/footerregistration.jsp"%>



