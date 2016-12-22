<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Create New Renter User" />
<c:set var="theBodyID" value="admin" />
<c:set var="zipChecker" value="true" />
<c:set var="numerFormat" value="true" />
<c:set var="datepicker" value="true" />
<c:set var="maskedinput" value="true" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<form:form method="post" modelAttribute="renterDto" >

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

<%-- <form:hidden path="username"/>  --%>
<%-- <form:hidden path="role.role"/> --%>
<%-- <form:hidden path="country"/> --%>
<%-- <form:hidden path="openIdIdentifier"/> --%>
<%-- <form:hidden path="status"/> --%>

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
					<h4>Renter Registration - STEP 2 OF 3 </h4>
					<h5>Personal Information</h5>
					<br>
					
										<fieldset class="form-group">
											<label for="">* required field</label>
										</fieldset>
										
																				
										<fieldset class="form-group">
											<label for="">*First Name</label>
											<form:errors path="firstName" cssClass="error" />
											<span id="firstName_error" />
											<form:input path="firstName" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*Last Name</label>
											<form:input path="lastName" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*Social Security Number (SSN )</label>
											<form:input  path="socialId" size="9"  maxlength="12"/>
										</fieldset>
										
<!-- 										<fieldset class="form-group"> -->
<!-- 											<label for="">*Date of Birth ( MM/DD/YYYY )</label> -->
<%-- <%-- 											<form:input class="form-control form-control-sm date_input" path="oldClearDOB" size="12" cssClass="toolTipTitle" title="You must be at least 18 years old." /> --%> 
<!-- 											<img src="img/icon_calendar.png" alt="">  -->
<%-- 										<form:errors path="oldClearDOB cssClass="error" /> --%>
<!-- 											<span id="oldClearDBO_error"/> -->
<!-- 											</p> -->
<%-- 											<form:input path="oldClearDOB" cssClass="textfield_part2 m_left15 left" cssStyle="width:340px;"  /> --%>
<!-- 											<div class="clear"></div> -->
<!-- 										</fieldset> -->
										
										<fieldset class="form-group datebox">
                    						<label for="">*Date of Birth ( MM/DD/YYYY )</label>
                    						<form:input  path="oldClearDOB" id="oldClearDOB" />
                    						<img src="img/icon_calendar.png" alt=""> 
               							 </fieldset>
                
<!-- 										 <fieldset class="form-group datebox"> -->
<!-- 			                                <label for="">Transaction date</label> -->
<%-- 			                                <form:input class="form-control form-control-sm date_input" path="oldClearDOB" id="rptDate"/> --%>
<!-- 			                                <img src="img/icon_calendar.png" alt=""> -->
<!--                             			</fieldset> -->
                            
										

										<fieldset class="form-group">
											<label for="">*Address</label>
											<form:input path="address" size="40" maxlength="120" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">Additional Address</label>
											<form:input path="address2" size="40" maxlength="50" />
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">*Zip Code</label>
											<form:input path="zipcode" maxlength="10" id="zipcode" onblur="getStateCityScript('zipcode','city','state')"/>
										</fieldset>


						</div>
						<div class="col-xs-6">

<br><br><br>
										<fieldset class="form-group">
											<label for="">*City</label>
											<form:input path="city" maxlength="50" id="city" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*State</label>
											<form:select path="state" id="state" items="${usStateOptions}" />
										</fieldset>
										<fieldset class="form-group">
											<label for="">*Phone</label>
											<form:input path="phone" maxlength="20" />
										</fieldset>

		
<!-- 										<fieldset class="form-group"> -->
<!-- 											<label for="">Enter Password</label> -->
<%-- 											<form:password path="newPassword" size="30"/> --%>
<!-- 										</fieldset> -->
										
<!-- 										<fieldset class="form-group"> -->
<!-- 											<label for="">Repeat Password</label> -->
<%-- 											<form:password path="rePassword" size="30"/> --%>
<!-- 										</fieldset> -->

							
							    <br>
							    <p><strong>We strongly recommend the use of <a href="javascript:alert('A good password should have the following minimum characteristics: At least 8 characters. Contain upper case letters, lower case letters, numeric characters, special characters such as @ and $. Do not contain personal information such as names or birthdays')" id="complexitymsg" title= "Characteristics for Complex Passwords">complex passwords</a></strong></p>
							    
								
								</div>
								</div>
								
<div class="row">
<div class="col-xs-12">
	
<br>
									<input type="submit" value="Cancel" name="_cancel" class="btn by_cr"/>
<%-- 								<c:if test="${loginUser.hasRole('save.client.user') || loginUser.hasRole('save.system.user')}"> --%>
<!-- 						 				<input type="submit" value="Submit"  id="createaccount" name="_btn" class="btn by_cr" disabled="disabled" onclick="alert('Thanks for Registering! You will need to log in with your new password.');return confirmPassword('password', 'rePassword')"/>  -->
										<input type="submit" value="Submit"  id="Submit" name="_btn" class="btn by_cr" /> 
									<%-- </c:if> --%>

									
					</div>
			</div>
	</div>
</div>
</form:form>



<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>
<script src="jsnew/jquery.formatDateTime.js" type="text/javascript"></script>


<!--  Custom JavaScript Functions for Password Confirmation -->
<script src="js/passwordconfirmation.js" type="text/javascript"></script>

<!-- <style type="text/css">  -->
/* .ui-datepicker { */
/*    	background: #215;  */
/*     border: 1px solid #333; */
/*     color: #CCC;  */
/* 	}  */
<!-- </style>  -->

<script type="text/javascript">
			jQuery(function($){
				 $('#phone').mask("(999) 999-9999");
				 $('#fax').mask("(999) 999-9999");});
			 
 			$(document).ready(function(){	

 				$('form').validate({
 					
 					errorPlacement: function(error, element) {
 								
 				         error.insertAfter("#"+element.attr("name")+"_error");
 				    },
 					
 					rules: {
 						firstName: "required",
 						lastName: "required",
 						oldClearDOB: {required: true},
 						phone: {required: true,
 							          minlength: 5,
 							          maxlength: 30}
 						
	
 					}
 				
 				});

				$(".socialId").mask("999-99-9999");
				
				$('#oldClearDOB').datepicker({
					showOn: 'button',
					changeYear: true,
 					changeMonth: true,
					maxDate: "-18y",
					minDate: "-130y",
					yearRange: "-80:-17"});
				$("#oldClearDOB.datepicker").mask("mm/dd/yyyy");

				
// 				$('#oldClearDOB').datepicker({
// 					showOn: 'button', 
// 					buttonImage: 'images/icons_calendar.gif', 
// 					buttonImageOnly: true,
// 					changeYear: true,
// 					changeMonth: true,
// 					dateFormat: 'mm/dd/yyyy',
// 					maxDate: "-18y",
// 					minDate: "-130y",
// 					yearRange: "-80:-17"
// 				}).inputmask('mm/dd/yyyy');
		
 			});
				
		     	
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
	     	
		     	
// 	    	function ssnValidation(){
// 	    		var ssnVal = $('#applicants_ssn0').val();
// 	    		if(ssnVal.length >= 11) {
// 	    		$.ajax({
// 	                type: "GET",
// 	                url: "ssnTrack.htm?ssnValue="+ssnVal,
// 	                cache: false,
// 	                success: function(ajaxResultHtml){
// 	                	$('#ssnTrackingDivAU').html(ajaxResultHtml);
// 	                	if(ajaxResultHtml.length > 0) {
// 	                	$.blockUI({ message: $('#dialogSSNDivAU'), 
// 	    			            css: { 
// 	    			            	width: 500,
// 	    			            	left: ($(window).width() - 500) /2 + 'px', 
// 	    			            	top: '200px',
// 	    			         	cursor: 'default'
// 	    			 			}
// 	    				});
// 	                }
// 	                },
// 	                error: function(){
// 	                	alert("Communication Error.");
// 	                }
// 	            });
	    		
// 	    		}
// 	    		return true;
// 	    	}
	     	
</script>

<%@ include file="/WEB-INF/jsp/footerregistration.jsp"%>