<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Application details" />
<c:set var="theBodyID" value="admin" />
<c:set var="zipChecker" value="true" />
<c:set var="numerFormat" value="true" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<form:form method="post" modelAttribute="application">
<ol class="breadcrumb">
  <li><a href="home.htm">Home</a></li>
  <li><a href="viewapplications.htm">Applications</a></li>
</ol>

<%-- <div id="subheader"><p>Add/Edit Application Details for ${application.property.name}</p></div> --%>

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
  

    <div id="main" class="container">
        <div class="control-box mailbox_area">
            <div class="row">
                <div class="section_icon_head">
                    <img src="img/icon_applications.png" alt="Users">
                </div>
            </div>
        <!-- <div class="control_box row"> -->

				<div class="row">
					<div class="col-xs-6">
					
					<h4>Application Information</h4>

										<fieldset class="form-group">
											<label for="">*Application External Id</label>
											<form:input path="applicationExtId" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*Property External Id</label>
											<form:input path="application.property.propertyExtId" size="40" maxlength="128" />
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">*Property Address</label>
											<form:input path="application.property.AddressLine1" size="40" maxlength="128" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*Landlord Pays</label>
											<form:input path="application.landlordPays" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">*Credit Recommendation</label>
											<form:input path="application.creditRecommendation" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">Status</label>
											<form:input path="jobTitle" size="40" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">Rent Amount</label>
											<form:input path="application.rentalAmount" size="12" maxlength="13" />
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">Rent Deposit</label>
											<form:input path="application.rentalDeposit" size="40" maxlength="120" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">Can Request Reports</label>
											<form:input path="application.canRequestReport" size="40" maxlength="50" />
										</fieldset>
						</div>
						<div class="col-xs-6">

										<fieldset class="form-group">
											<label for="">Zip Code</label>
											<form:input path="application.property.zipcode" maxlength="10" onblur="getStateCityScript('zipcode','city','state')"/>
										</fieldset>

										<fieldset class="form-group">
											<label for="">City</label>
											<form:input path="application.property.city" maxlength="50" />
										</fieldset>

										<fieldset class="form-group">
											<label for="">State</label>
											<form:select path="application.property.state" items="${globals.usStateListOptions}" />
										</fieldset>

										
										<fieldset class="form-group">
											<label for="">Action</label>
						               <div class="radio-inline-combo">
											<select name="action" id="action">
											  <option value="None">Select one</option>
											  <option value="Approve">Approve Application</option>
											  <option value="Decline">Decline Application</option>
											  <option value="Cancel">Cancel Application</option>
											</select>
											</div>
										</fieldset>
						

									<c:if test="${loginUser.hasRole('view.my.application')}">
						 				<input type="submit" value="Save Application"  name="_btn" class="btn by_cr" /> 
										<input type="submit" value="Cancel" name="_cancel" class="btn by_cr"/>
									</c:if>
									
									
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
			 
		
			window.onload = function() {            
			    var y = "${user.role.role}";
			    if(y != "BD" &&  y !="PM"){
			      document.getElementById('propertyBlock').style.display = 'none';
			    }
		  	}
	
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