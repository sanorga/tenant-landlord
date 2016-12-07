<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Change Password" />
<c:set var="theBodyID" value="admin" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<script type="text/javascript">
	
	var isNew = true;
	var map = new Object();

	$(document).ready(function() {
	});
	
	
</script>
	
<form:form method="post" modelAttribute="user" onsubmit="alert('You will need to log in again with your new password.');">

<form:hidden path="role.id"/>
<form:hidden path="id"/>
	
<ol class="breadcrumb">
	<li>Add/Edit User Details for ${user.username}</li>
</ol>

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
  <!-- Message Ends here -->

<div id="main" class="container">
        <div class="control-box mailbox_area">
            <div class="row">
                <div class="section_icon_head">
                    <img src="img/icon_change_my_password.png" alt="Change My Password">
                </div>
            </div>
        <!-- <div class="control_box row"> -->
	
			<div class="row">
					<div class="col-xs-6">
						<h4>Change My Password</h4>

										<fieldset class="form-group">
											<label for="">*Current Password</label>
											<form:password path="oldPassword" size="30"/>
										</fieldset>
										<fieldset class="form-group">
										<label for="">*New Password</label>
											<form:password path="newPassword" size="30"/>
										</fieldset>
										<fieldset class="form-group">
										<label for="">*Repeat Password</label>
											<form:password path="rePassword" size="30"/>
										</fieldset>
					</div>


  <!-- contents starts --> 
 <div class="col-xs-6">
<!--   <div id="content"> -->
    <table>    
	
    </table>
    <br>
<!--       <h6>We strongly recommend the use of complex passwords.</h6> -->
    <p><strong>We strongly recommend the use of complex passwords.</strong></p>
    <p>A good password should have the following minimum characteristics: </p>
    <ul>
    	<li>At least 8 characters</li>
    	<li>Contain upper case letters</li>
    	<li>Contain lower case letters</li>
    	<li>Contain numeric characters</li>
    	<li>Contain special characters such as @ and $</li>
    	<li>Do not contain personal information such as names or birthdays</li>
    	
    	
    </ul>
<br><br>
  <!-- contents ends -->

		<input type="submit" value="Continue"  name="_btn" class="btn by_cr" /> 
								
		<input type="submit" value="Cancel" name="_cancel" class="btn by_cr"  />

 
     
<!--       <table cellpadding="0" cellspacing="0" border="0" width="100%" class="navigaitons"> -->
<!--       	<tr><td>&nbsp;</td></tr> -->
<!-- 		<tr> -->
<!-- 		     <td><input type="submit" value="Continue" name="_btn" class="button"/>  -->
<!-- 		     <input type="submit" value="Cancel" name="_cancel" class="button"/></td> -->
<!-- 		</tr> -->
<!--  	 </table> -->
  
       <!-- Security Questions end --> <br />
<!--    </div> -->
   </div>
   
   </div>
</div>
</div>

</form:form>

<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>

<%@ include file="/WEB-INF/jsp/footernew.jsp" %>