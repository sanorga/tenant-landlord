<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="New Renter Personal Information" />
<c:set var="theBodyID" value="admin" />
<c:set var="zipChecker" value="true" />
<c:set var="numerFormat" value="true" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<form:form method="post" modelAttribute="user" >

<ol class="breadcrumb">
  	<li><a href="login.htm">Login</a></li>

</ol>

<%-- <div id="subheader"><p>Add/Edit User Details for ${user.subscriber.name}</p></div> --%>

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
  
<form:hidden path="role.role"/>
<form:hidden path="country"/>
<form:hidden path="openIdIdentifier"/>
<form:hidden path="status"/>

    <div id="main" class="container">
        <div class="control-box mailbox_area">
            <div class="row">
                <div class="section_icon_head">
                    <img src="img/icon_users.png" alt="Users">
                </div>
            </div>
        <!-- <div class="control_box row"> -->

				<div class="row">
					<div class="col-xs-6">
					<br>
					<h4>Renter Registration - STEP 1 OF 3</h4>
					<br>
					
										<fieldset class="form-group">
											<label for="">* required field</label>
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">*Email(UserName)</label>
											<form:input path="username" size="40" maxlength="50" />
											<c:if test="${!user.isNew() and (fn:startsWith(user.status,'A') or fn:startsWith(user.status,'X')) }">
											<br><br><input type="submit" value="Reset Password" name="_resetPassword" class="btn btn-sm by_cr"/>
											</c:if>				
										</fieldset>

										

						</div>
						<div class="col-xs-6">

<br><br><br>
							

		
										<fieldset class="form-group">
											<label for="">Enter Password</label>
											<form:password path="newPassword" size="30"/>
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">Repeat Password</label>
											<form:password path="rePassword" size="30"/>
										</fieldset>
										
<!-- 										<fieldset class="form-group"> -->
<!-- 											<label for="">Status</label> -->
<!-- 						               <div class="radio-inline-combo"> -->
<%-- 												<form:radiobuttons path="status" items="${userStatusOptions}" /> --%>
<!-- 											</div> -->
<!-- 										</fieldset> -->
							
							    <br>
							    <p><strong>We strongly recommend the use of <a href="javascript:alert('A good password should have the following minimum characteristics: At least 8 characters. Contain upper case letters, lower case letters, numeric characters, special characters such as @ and $. Do not contain personal information such as names or birthdays')" id="complexitymsg" title= "Characteristics for Complex Passwords">complex passwords</a></strong></p>
							    
								
								</div>
								</div>
								
<div class="row">
<div class="col-xs-12">
	

<fieldset class="form-group">
	<label for="">Service Agreement and Terms and Conditions</label>
	<div style="height: 300px; overflow: scroll; padding-right:20px; border-style:ridge;">
		<%@ include file="/WEB-INF/jsp/include/ServicesTermsForLandlord.jsp" %>
	</div>
</fieldset>

		<fieldset class="form-group">
		<div class="checkboxer">
			<span>
 			<form:checkbox path="acceptSATC" id="Accept" label="*I accept the Service Agreement and Terms and Conditions" onclick="EnableSubmit(this)" /> 

			</span>
		</div>
		</fieldset>
	
<br>
									<input type="submit" value="Cancel" name="_cancel" class="btn by_cr"/>
<%-- 								<c:if test="${loginUser.hasRole('save.client.user') || loginUser.hasRole('save.system.user')}"> --%>
						 				<input type="submit" value="Submit"  id="createaccount" name="_btn" class="btn by_cr" disabled="disabled" onclick="alert('Thanks for Registering! You will need to log in with your new password.');return confirmPassword('password', 'rePassword')"/> 
<%-- 								</c:if> --%>

									
					</div>
			</div>
	</div>
</div>
</form:form>



<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>

<!--  Custom JavaScript Functions for Password Confirmation -->
<script src="js/passwordconfirmation.js" type="text/javascript"></script>

<script type="text/javascript">
			jQuery(function($){
				 $('#phone').mask("(999) 999-9999");
				 $('#fax').mask("(999) 999-9999");});
			 
// 			$(document).ready(function(){	

				
// 				jQuery.validator.addMethod("zipcode", function(value, element) {
// 					  return this.optional(element) || /^\d{5}(?:-\d{4})?$/.test(value);
// 					}, "Please Enter a valid zipcode.");
				
				
// 				$('form').validate({
// 					errorPlacement: function(error, element) {  
// 				         error.insertAfter("#"+element.attr("name")+"_error"); 
// 				    },
// 					rules: {
						
// 						address: "required",
// 						zipCode: {
// 							required:true,
// 							zipcode:true
// 						},
// 						city: "required",
// 						state: "required",
						
// 					},
// 					messages:{
						
// 						address: "Street Address is required",
// 						zipCode: {
// 							required:"zipcode is required",
// 							zipcode:"zipcode not valid"
// 						},
// 						city: "City is required",
// 						state: "State is Required",
						
// 					}
// 				});
				
				
// 			});
//	  	}
	
	     	
	     	function EnableSubmit(val)
	     	{
	     	    var sbmt = document.getElementById("createaccount");

	     	    if (val.checked == true)
	     	    {
	     	        sbmt.disabled = false;
	     	    }
	     	    else
	     	    {
	     	        sbmt.disabled = true;
	     	    }
	     	}
	     	
	     	
	     	
	     	
</script>

<%@ include file="/WEB-INF/jsp/footerregistration.jsp"%>