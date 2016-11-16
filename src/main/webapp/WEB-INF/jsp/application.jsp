<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Application details" />
<c:set var="theBodyID" value="admin" />
<c:set var="zipChecker" value="true" />
<c:set var="numerFormat" value="true" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<form:form method="post" modelAttribute="application" name="application">
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
  
  <!-- hidden field starts -->
	<input type="hidden" name="newStatus" id="newStatus" >

    <div id="main" class="container">
        <div class="control-box mailbox_area">
            <div class="row">
                <div class="section_icon_head">
                    <img src="img/icon_applications.png" alt="Applications">
                </div>
            </div>
        <!-- <div class="control_box row"> -->

				<div class="row">
					<div class="col-xs-6">
					
					<h4>Application Information</h4>

										<fieldset class="form-group">
											<label for="">*Application External Id</label>
											<form:input path="applicationExtId" size="20" maxlength="20" readonly="true" />
										</fieldset>

 										<fieldset class="form-group"> 
 											<label for="">Property External Id</label> 
 											<form:input path="property.propertyExtId" size="20" maxlength="20" readonly="true"  /> 
 										</fieldset> 
										
 										<fieldset class="form-group"> 
 											<label for="">*Property Address</label> 
 											<form:input path="property.AddressLine1" size="40" maxlength="128" readonly="true" /> 
 										</fieldset> 
 										<table>
										<tr>
										<td>
 										
										<fieldset class="form-group">
											<label for="">City</label>
											<form:input path="property.city" maxlength="50" readonly="true"/> 
										</fieldset>
										</td>
										<td>
										<fieldset class="form-group">
											<label for="">Zip Code</label>
											<form:input path="property.zipcode" maxlength="10" onblur="getStateCityScript('zipcode','city','state')" readonly="true"/>
 										</fieldset> 
										</td>
										</tr>
										</table>
										<fieldset class="form-group">
											<label for="">State</label>
											<form:select disabled="true" path="property.state" items="${globals.usStateListOptions}"  />
										</fieldset>
																
										<fieldset class="form-group">
											<label for="">Landlord Pays</label>
<%-- 											<form:select path="landlordPays" items="${globals.getTrueFalseOptions}"  /> --%>
										</fieldset>

										<fieldset class="form-group">
											<label for="">Credit Recommendation</label>
 											${creditRecommendationLabel}
										</fieldset>

										<fieldset class="form-group">
											<label for="">Rent Amount</label>
											<form:input path="rentalAmount" size="12" maxlength="12" readonly="true"/>
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">Rent Deposit</label>
											<form:input path="rentalDeposit" size="12" maxlength="12"  readonly="true"/>
										</fieldset>

<!-- 										<fieldset class="form-group"> -->
<!-- 											<label for="">Can Request Reports</label> -->
<%-- 											<form:input path="canRequestReport" size="40" maxlength="50" readonly="true"/> --%>
<!-- 										</fieldset> -->
										<fieldset class="form-group">
											<label for="">Report</label>
											<div>${creditReport1}</div>
        								</fieldset>
										<!-- Report ends-->
						
						</div>
						
						<br> <br> <br> <br>
						<div class="col-xs-6">


					 <c:forEach items="${applicants}" varStatus="row">
						<c:if test="${row.index == 0 }">
					  
					
										<fieldset class="form-group">
											<label for="">Applicant</label>
												${applicants[row.index].emailAddress } &nbsp; ${applicants[row.index].fullName } 
										</fieldset>
						</c:if>
						
						<c:if test="${row.index == 1 }">
					  
					
										<fieldset class="form-group">
											<label for="">Coapplicant</label>
											${applicants[row.index].emailAddress } &nbsp; ${applicants[row.index].fullName } 
										</fieldset>
						</c:if>
					  </c:forEach>		
		
									<c:if test="${application.canRequestReport}">
										<fieldset class="form-group">
											<label for="">Reports are available </label>
											<a href="<c:url value="getReports.htm"/>"><img src="images/docs.jpg" alt="Request Reports" /></a>
										</fieldset>
									</c:if>
									<c:if test="${!application.canRequestReport}">
										<fieldset class="form-group">
											<label for="">Reports are not available at this time</label>
											
										</fieldset>
									</c:if>
						
						<fieldset class="form-group">
							<label for="">Application Status</label>
<%-- 								${application.status}			 --%>
									<form:input path="status" size="12" maxlength="12"  readonly="true"/>									
						</fieldset>
						
						<c:if test="${application.status != 'Approved' && application.status != 'Declined' && application.status != 'Cancelled' }">
									<fieldset class="form-group">
											<label for="">Action</label>
						               <div class="radio-inline-combo">
											<select name="action" id="action" onchange="changeFunc();">
											  <option value="None">Select one</option>
											  <option value="Approved">Approve Application</option>
											  <option value="ApprovedWithCondition">Approve Application with Condition</option>
											  <option value="Declined">Decline Application</option>
<!-- 											  <option value="Cancelled">Cancel Application</option> -->
											</select>
											</div>
										</fieldset>
										<br><br><br><br><br><br><br><br><br><br><br><br>
										<c:if test="${loginUser.hasRole('view.my.applications')}">
						 				<input type="submit" value="Save Application"  name="_btn" class="btn by_cr" /> 
										<input type="submit" value="Cancel" name="_cancel" class="btn by_cr"/>
									</c:if>
						</c:if> 
<c:if test="${application.status == 'Approved' || application.status == 'Declined' || application.status == 'Cancelled' }">
									
									<c:if test="${loginUser.hasRole('view.my.applications')}">
						 			<br><br><br><br><br><br><br><br><br><br><br><br><br> <br>	
										<input type="submit" value="Go Back" name="_cancel" class="btn by_cr"/>
									</c:if>
										</c:if> 
									
					</div>
			</div>
	</div>
</div>
</form:form>



<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>

<!--  Custom JavaScript Functions for Password Confirmation -->


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
	     	
	     	function changeFunc() {
	     		var selectBox = document.getElementById("action");
	     	    var selectedValue = selectBox.options[selectBox.selectedIndex].value;

	     	    document.getElementById("status").value = selectedValue;
	     	}
	     	
</script>

<%@ include file="/WEB-INF/jsp/footernew.jsp"%>