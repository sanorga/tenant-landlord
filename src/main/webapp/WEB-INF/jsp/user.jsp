<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Create New User" />
<c:set var="theBodyID" value="admin" />
<c:set var="zipChecker" value="true" />
<c:set var="numerFormat" value="true" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<form:form method="post" modelAttribute="user" >

<ol class="breadcrumb">
  	<li><a href="home.htm">Home</a></li>

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
<form:hidden path="id"/>  
<form:hidden path="role.role"/>
<form:hidden path="country"/>
<form:hidden path="openIdIdentifier"/>
<form:hidden path="status"/>
<form:hidden path="socialAcctType"/>


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
					<h4>Settings</h4>
					<br>

										<fieldset class="form-group">
											<label for="">*Email (Username)</label>
											<form:input path="username" size="40" maxlength="50" />
											<c:if test="${!user.isNew() and (fn:startsWith(user.status,'A') or fn:startsWith(user.status,'X')) }">
<!-- 											<br><br><input type="submit" value="Reset Password" name="_resetPassword" class="btn btn-sm by_cr"/> -->
											</c:if>				
										</fieldset>

										
										<fieldset class="form-group">
											<label for="">*First Name</label>
											<form:input path="firstName" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*Last Name</label>
											<form:input path="lastName" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">Address</label>
											<form:input path="address" size="40" maxlength="120" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">Additional Address</label>
											<form:input path="address2" size="40" maxlength="50" />
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">Zip Code</label>
											<form:input path="zipcode" id="zipcode" maxlength="10" onblur="getStateCityScript('zipcode','city','state')"/>
										</fieldset>

										<fieldset class="form-group">
											<label for="">City</label>
											<form:input path="city" id="city" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">State</label>
											<form:select path="state" id="state" items="${usStateOptions}" />
										</fieldset>
						</div>
						<div class="col-xs-6">

<br><br><br>

										<fieldset class="form-group">
											<label for="">Phone</label>
											<form:input path="phone" maxlength="20" />
										</fieldset>
							
<%-- 										<c:if test="${userRoleOptions != null}"> --%>
<!-- 											<fieldset class="form-group"> -->
<%-- 												<label for="">Role: ${user.getRole().getRole()}</label> --%>
													
<%-- <%-- 												<form:select path="role.role" items="${userRoleOptions}" onchange="setPropertyBlockVisibility(this.selectedIndex, this.options[this.selectedIndex].text)"/> --%> 
<!-- 											</fieldset> -->
<%-- 										</c:if> --%>
									
									<div id="changepasswdiv"  >		
									<br>
									    <p><strong> <a class="a" href="javascript:hideAndShow();" id="testdiv" >Change password</a></strong></p>
<!-- 										<input type="button" value="Change Password"  id ="changepassw" name="changepassw" class="button" onClick="this.checked=true; hideAndShow()"/> -->
									</div>
									<br>	
									<div id="hideandshowdiv" class="hidden" >			
										<fieldset class="form-group">
											<label for="">*Current Password</label>
											<form:password path="oldPassword" size="30"/>
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">Enter Password</label>
											<form:password path="newPassword" size="30"/>
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">Repeat Password</label>
											<form:password path="rePassword" size="30"/>
										</fieldset>
										
									    <br>
									    <p><strong>We strongly recommend the use of <a href="javascript:alert('A good password should have the following minimum characteristics: At least 8 characters. Contain upper case letters, lower case letters, numeric characters, special characters such as @ and $. Do not contain personal information such as names or birthdays')" id="complexitymsg" title= "Characteristics for Complex Passwords">complex passwords</a></strong></p>
		   
									</div>
								<br>


<%-- 								<c:if test="${loginUser.hasRole('save.client.user') || loginUser.hasRole('save.system.user')}"> --%>
						 				<input type="submit" value="Save User"  name="_btn" class="btn by_cr" onclick="return confirmPassword('password', 'rePassword')"/> 
<%-- 								</c:if> --%>

									<input type="submit" value="Cancel" name="_cancel" class="btn by_cr"/>
					</div>
			</div>
	</div>
</div>
</form:form>



<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>

<!--  Custom JavaScript Functions for Password Confirmation -->
<script src="js/passwordconfirmation.js" type="text/javascript"></script>

<script type="text/javascript">

 window.onload=function(){
	 
  			$("#hideandshowdiv").hide();
	
}

function hideAndShow() {		

		$("#changepasswdiv").hide()
		$("#hideandshowdiv").removeClass('hidden');	
		$("#hideandshowdiv").show();	
	

}
			jQuery(function($){
				 $('#phone').mask("(999) 999-9999");
				 $('#fax').mask("(999) 999-9999");});
			 
		

	     	function setPropertyBlockVisibility(selected_index, selected_option_text) {
					if(selected_index > -1)	{
						if (selected_option_text == "Board Director" || selected_option_text == "Property Manager"){
			     			$("#propertyBlock").show();
			            	
			     		} else {
			     			$("#propertyBlock").hide();
			     		}
					}
  		
	     	}
</script>

<%@ include file="/WEB-INF/jsp/footernew.jsp"%>