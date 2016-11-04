<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<c:set var="thePageTitle" value="Invite To Apply" />
<c:set var="theBodyID" value="admin" />
<c:set var="zipChecker" value="true" />
<c:set var="numerFormat" value="true" />

<%@ include file="/WEB-INF/jsp/headernew.jsp" %>

<!-- formatting libraries -->
<%@ include file="/WEB-INF/jsp/includenew/formatting.jsp"%>

<!-- jquery auto completion js -->
<script src="js/jquery/jquery.autocomplete.js" type="text/javascript"></script>

<!-- Jquery auto completion css -->
<link rel="stylesheet" href="styles/autocomplete/jquery.autocomplete.css" type="text/css" media="screen">

<script type="text/javascript">
	window.onload = function() {

		<c:if test = "${empty clients}">document.getElementById("SendEMailBtn").disabled=true;</c:if>

	};
	
	 $(document).ready(function() {
	  
		// auto complete
		    var data = [<c:forEach var="c" items="${clients}">'${c.value}',</c:forEach>''];
		    $("#clientIDS").autocomplete(data).result(function(event, item) {
				item = item +"";
				item = item.replace(/&amp;/g, "&");

			});
			
			$("#clientIDS").unbind("keypress");
			$("#clientIDS").keypress(function(e) {
				if (e.which==13) {

					return false;
				}
				return true;
			}); 
		   
	 });
	
	function showAndHideQuestions() {
		if (document.getElementById("applicationType1").checked) {
			$("#questionsDiv").show();
			$("#questionsDiv").slideUp("slow");
		} else {
			$("#questionsDiv").slideDown("slow");
			$("#questionsDiv").show();
		}
	}
	
	function anonymousUserCancelClickEvent() {
		$('#anonymousUserCancelDiv').hide();
		$('#anonymousUserDisabledDivShow').show();
		return true;
	}
	
	function anonymousUserSendEmailClickEvent(){
		$('#anonymousUserSendEmailDiv').hide();
		$('#anonymousUserSendEmailDisabledDivShow').show();
		return true;
		
	}

 	function getPropertyDetails(clientName, charType) {
 		var testvar = 1;
 	}
	
	function doAjax(url){
		var jqxhr = $.ajax({
			url: url,
			dataType: "json",
			timeout: 5000
		})
		.done(function(po){
			getPropertyMapCallBack(po);
		})
	}
	
	var getPropertyMapCallBack = function(properties) {
		var propertyOptions = properties;
		var select = $('#property');
		
		setSelectOptions(select,propertyOptions);
	}
	
	function setSelectOptions(selectObj, map){
		if(selectObj.prop){
			var options = selectObj.prop('options');
		} else {
			var options = selectObj.attr('options');
		}
		$('option', selectObj).remove();
		$.each(map, function(val, text){
			options[options.length] = new Option(text,val);
		});
	}

	// get packages depending on client
	function getPackageOptions(clientName, charType) {	
		var jqxhr = $.ajax({
			url: "lookup/"+clientName+"/"+charType+"/packageOptions.json",
			dataType: "json",
			timeout: 5000
		})
		.done(function(map){
			var select = $('#package');
			setSelectOptions(select, map);
		})
		
	}
</script>

<form:form method="post" modelAttribute="anonymousUser">

<!-- breadcrumbs starts -->
 
  
<ol class="breadcrumb">
  <li><a href="home.htm">Home</a></li>
<!--   	<li><a href="anonymousUser.htm">Users</a></li> -->

</ol>
<!-- breadcrumbs ends -->

	<!-- sub heading starts -->
	<div id="subheader"><p>Create Invitation</p></div>
	
	<!-- sub heading ends -->

  <!-- messages starts -->
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
                    <img src="img/icon_inviteToApply.png" alt="Invite To Apply">
                </div>
            </div>

			<div class="row">
					<div class="col-xs-6">
<br>
					<h4>Invite applicant</h4>

										<fieldset class="form-group">
											<label for="">*Enter Applicant Email:</label>
											<form:input path="emailId" size="40" maxlength="50" />
														
										</fieldset>
										
										<fieldset class="form-group">
											<label for="">Enter Co-Applicant Email:</label>
											<form:input path="coappEmailId" size="40" maxlength="50" />
										</fieldset>

					
<!-- 					<h4>Please enter details for Property:</h4> -->
            					
									<fieldset class="form-group">
											<label for="">*Select Existing Property or Enter New Property Details:</label>
											<br>
											<form:select path="property.id" items="${clients}"  />
											 <br>
											
									</fieldset>
								<fieldset class="form-group">
											<label for="">*Name:</label>
											
											<form:input path="property.name" size="50" maxlength="50"  />
											
									</fieldset>
	           					<table>
								<tr>
								<td>&nbsp;</td>
								<td>	
									<fieldset class="form-group">
											<label for="">*Address:</label>
											
											<form:input path="property.street" size="40" maxlength="50"  />
											
									</fieldset>
								</td>
								<td>
									<fieldset class="form-group">
											<label for="">*Unit:</label>
											
											<form:input path="property.apartmentNo" size="10" maxlength="10"  />
											
									</fieldset>
								</td>
								</tr>
								</table>
					</div>
        
           
					<div class="col-xs-6">
								
								
									<fieldset class="form-group">
											<label for="">*City:</label>
									
											<form:input path="property.city" size="30" maxlength="30"  />
									
									</fieldset>
								
								<table>
								<tr>
								<td>&nbsp;</td>
								<td>	
									
									<fieldset class="form-group">
											<label for="">*State:</label>
									
											<form:input path="property.state" size="2" maxlength="2"  />
										
									</fieldset>
								</td>
								<td>
								
									<fieldset class="form-group">
											<label for="">*Zipcode:</label>
									
											<form:input path="property.zipcode" size="5" maxlength="5"  />
										
									</fieldset>
								</td>
								</tr>
								</table>
								<br>
									<fieldset class="form-group">
											<label for="">*Rent Amount:</label>
									
											<form:input path="rentalAmount" size="10" maxlength="10"  />
										
									</fieldset>
									
									<fieldset class="form-group">
											<label for="">Deposit:</label>
									
											<form:input path="rentalDeposit" size="10" maxlength="10"  />
										
									</fieldset>
									
									<fieldset class="form-group">
									       <tea:ifAuthorized capability="new.anonymous_user">
<table>
<tr>
<td>
	<img src="styles/tenapp/images/star.png" alt="" class="m_right08" /><font size="3" >Save this address for future Screenings?</font><br>
</td>
<td>
<form:select path="saveNewAddress" items="${yNOptions}" cssClass="select"   />
<%-- 	<form:radiobutton path="otherOccupantsExist" value="true" />Yes&nbsp;&nbsp; --%>
<%-- 	<form:radiobutton path="otherOccupantsExist" value="false" />No --%>
</td>
</tr></table>

        </tea:ifAuthorized>
		</fieldset>							
					</div>
            </div>
<!--              <div class="row"> -->
<!-- 					<div class="col-xs-12"> -->
<!-- 									<fieldset class="form-group"> -->
<!-- 											<label for="">*Enter Application Type:</label> -->
<!-- 											<div class="radio-inline-combo"> -->
<%-- 											<form:radiobuttons path="applicationType" items="${globals.applicationTypeOptions}" onclick="showAndHideQuestions()"/> --%>
<!-- 											</div> -->
<!-- 									</fieldset> -->
<!-- 					</div> -->
<!--             </div>					 -->
	<!-- contents starts -->
     
<!-- 	  <tr> -->
<!-- 		<td> -->
<!-- 			<fieldset> -->
<!--         	<legend><strong>Applicant's Information</strong></legend> -->
<!-- 			<table> -->
<!-- 				<tr> -->
<%-- 				<c:if test="${loginUser.hasRole('PA') || loginUser.hasRole('IN')}"> --%>
<%--       			    <td>*Client:<br><form:input path="client.name" id="clientIDS"/></td> --%>
<%-- 				</c:if>   --%>
<%-- 				<c:if test="${loginUser.hasRole('CA') || loginUser.hasRole('PM')}">   --%>
<%-- 					<td>*Client:<br><form:select path="client.id" items="${clients}" onchange="getPropertyOptions(document.getElementById('client.id').value, 'id')" /></td> --%>
<%-- 				</c:if> --%>
<%-- 				<c:if test="${loginUser.hasRole('PA') || loginUser.hasRole('IN') || loginUser.hasRole('CA') || loginUser.hasRole('PM')}"> --%>
<%-- 					<td>*Package:<br><form:select path="packge.id" id="package"  /></td> --%>
<%-- 				</c:if> --%>
<!-- 				<tr> -->
<!-- 					<td> -->
<!-- 						<div id="questionsDiv"> -->
<!-- 						<table> -->
<!-- 							<tr> -->
<%-- 								<td>*Property:<br><form:select path="property.id" id="property" /></td> --%>
<!-- 							</tr> -->
<!-- 							<tr> -->
<%-- 								<td>Does the applicant have a Spouse?<br><form:radiobuttons path="isCoapplicantAvailable" items="${globals.yesNoOptions}"/></td> --%>
<%-- 							</tr><tr>
<%-- 								<td>Does the applicant have any other occupants?<br><form:radiobuttons path="isOccupantsAvailable" items="${globals.yesNoOptions}"/></td> --%>
<%-- 							</tr> --%>
<%-- 								<td>Does the applicant have any vehicles?<br><form:radiobuttons path="isVehicleAvailable" items="${globals.yesNoOptions}"/></td> --%>
<!-- 							</tr><tr> -->
<%-- 								<td>Does the applicant have any pets?<br><form:radiobuttons path="isPetAvailable" items="${globals.yesNoOptions}"/></td> --%>
<!-- 							</tr> -->
<!-- 						</table> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 					<td>&nbsp;</td> -->
<!-- 				</tr>					 -->
<!-- 			</table> -->
<!-- 			</fieldset> -->
<!-- 		</td> -->
<!-- 		</tr> -->
	
    <!-- contents ends -->

  <!--  submit starts -->
 <div class="row">
					<div class="col-xs-6">
					</div>
					<div class="col-xs-6">
        <tea:ifAuthorized capability="new.anonymous_user">

<!--           <div id="anonymousUserSendEmailDiv" style="visibility: visible;"> -->
            <input type="submit" id="SendEMailBtn" value="Invite to Apply" name="_btn" class="btn by_cr"  onclick="anonymousUserSendEmailClickEvent()"/>
<!--           </div> -->
<!--            <div id="anonymousUserSendEmailDisabledDivShow" style="display: none;"> -->
<!--            <input type="submit" id="SendEMailBtn" value="Send eMail" name="_cancel" class="btn by_cr" disabled="disabled"/> -->
<!--            </div> -->
       <input type="submit" value="Cancel" name="_cancel" class="btn by_cr" onclick="anonymousUserCancelClickEvent()"/>
        </tea:ifAuthorized>

<!--         <div id="anonymousUserCancelDiv" style="visibility: visible;"> -->
<!--          <input type="submit" value="Cancel" name="_cancel" class="btn by_cr" onclick="anonymousUserCancelClickEvent()"/> -->
<!--          </div> -->
<!--          <div id="anonymousUserDisabledDivShow" style="display: none;"> -->
<!--           <input type="submit" value="Cancel" name="_cancel" class="button" disabled="disabled"/> -->
<!--          </div> -->
       
	</div>
            </div>    
            </div>
            </div>    	  
         
     

  <!--  submit starts -->

</form:form>
<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/footernew.jsp"%>