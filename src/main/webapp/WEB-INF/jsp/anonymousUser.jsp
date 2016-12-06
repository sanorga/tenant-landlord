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

// 		<c:if test = "${empty clients}">document.getElementById("SendEMailBtn").disabled=true;</c:if>
		if(document.getElementById("propertyid").value == -1){
			
			$("#hideandshowpropertyinfo").hide();
		}
	};
	
 	 $(document).ready(function() {
	  		if(document.getElementById("propertyid").value == -1){
			
				$("#hideandshowpropertyinfo").hide();
			}

		   
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
	
// 	function doAjax(url){
// 		var jqxhr = $.ajax({
// 			url: url,
// 			dataType: "json",
// 			timeout: 5000
// 		})
// 		.done(function(po){
// 			getPropertyMapCallBack(po);
// 		})
// 	}
	
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
	

	function openNewPropertyFields() {	
// // 		document.getElementById('propertyid').style.visibility = 'visible';
// 		document.getElementById('propertyid').removeAttribute('readonly');
		document.getElementById('propertyid').value = -1;
		document.getElementById('propertyid').setAttribute('checked','true');


		document.getElementById('propname').removeAttribute('readonly');
		document.getElementById('propname').value = '';
		document.getElementById('propstreet').removeAttribute('readonly');
		document.getElementById('propstreet').value = '';
		document.getElementById('propapartmentno').removeAttribute('readonly');
		document.getElementById('propapartmentno').value = '';
		document.getElementById('propcity').removeAttribute('readonly');
		document.getElementById('propcity').value = '';
		document.getElementById('propstate').removeAttribute('readonly');
		document.getElementById('propstate').value = '';
		document.getElementById('propzipcode').removeAttribute('readonly');
		document.getElementById('propzipcode').value = '';
		if(document.getElementById("propertyid").value == -1){
			
			$("#hideandshowpropertyinfo").hide();
		}
	
	}
	
	function enterNewPropertyFields() {	
		// // 		document.getElementById('propertyid').style.visibility = 'visible';
//		 		document.getElementById('propertyid').removeAttribute('readonly');
				document.getElementById('propertyid').value = -1;
				document.getElementById('propertyid').checked = true;


				document.getElementById('propname').removeAttribute('readonly');
				document.getElementById('propname').value = '';
				document.getElementById('propstreet').removeAttribute('readonly');
				document.getElementById('propstreet').value = '';
				document.getElementById('propapartmentno').removeAttribute('readonly');
				document.getElementById('propapartmentno').value = '';
				document.getElementById('propcity').removeAttribute('readonly');
				document.getElementById('propcity').value = '';
				document.getElementById('propstate').removeAttribute('readonly');
				document.getElementById('propstate').value = '';
				document.getElementById('propzipcode').removeAttribute('readonly');
				document.getElementById('propzipcode').value = '';
				if(document.getElementById("propertyid").value == -1){
					
					$("#hideandshowpropertyinfo").show();
				}
			
			}
	
	function getPropInfo() {
// 		lastSearchVal = $('#property.id').val();
		lastSearchVal = document.getElementById('propertyid').value;
		if (lastSearchVal == -1){
			openNewPropertyFields();
			
		}
		else {
		var jqxhr = $.ajax({
			
			url: "lookup/"+lastSearchVal+"/propertyById.json",
			dataType: "json",
			timeout: 50000
		})
		
		.done(function(result) {
				if (result != null) {
					found = true;
// 					$('#propertyid').val(result.id);
					$("#hideandshowpropertyinfo").show()
					document.getElementById('propname').value = result.name;
					document.getElementById('propname').setAttribute('readonly','true');
					document.getElementById('propstreet').value = result.street;
					document.getElementById('propstreet').setAttribute('readonly','true');
					document.getElementById('propapartmentno').value = result.apartmentNo;
					document.getElementById('propapartmentno').setAttribute('readonly','true');
					document.getElementById('propcity').value = result.city;
					document.getElementById('propcity').setAttribute('readonly','true');
					document.getElementById('propstate').value = result.state;
					document.getElementById('propstate').setAttribute('readonly','true');
					document.getElementById('propzipcode').value = result.zipcode;
					document.getElementById('propzipcode').setAttribute('readonly','true');
// 					document.getElementById('propertyid').style.visibility = 'hidden';
					
				}
		})
		.fail(function(){
			
// 			$('#pop_heading').text("Sorry, we couldn't find a property with that code");

			openNewPropertyFields();
			
			alert ("Sorry, we couldn't find a property with that code. Please select another one or contact technical support");
		});
		}
	return false;
					
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
											<form:select path="property.id" id="propertyid" items="${clients}"  onchange="getPropInfo()" />
											<input type="button"  name="enterNewProperty" id="enterNewProperty" value="New Property" class="button"
										    onclick="enterNewPropertyFields()"/>
										    
											 <br>
											
									</fieldset>
									
								<div id="hideandshowpropertyinfo">
								<fieldset class="form-group">
											<label for="">*Name:</label>
											
											<form:input path="property.name" id="propname"  size="50" maxlength="50"  />
											
									</fieldset>
	           					<table>
								<tr>
								<td>&nbsp;</td>
								<td>	
									<fieldset class="form-group">
											<label for="">*Address:</label>
											
											<form:input path="property.street" id="propstreet"  size="40" maxlength="50"  />
											
									</fieldset>
								</td>
								<td>
									<fieldset class="form-group">
											<label for="">*Unit:</label>
											
											<form:input path="property.apartmentNo" id="propapartmentno" size="10" maxlength="10"  />
											
									</fieldset>
								</td>
								</tr>
								
								</table>
								
								
								
									<fieldset class="form-group">
											<label for="">*Zipcode:</label>
									
											<form:input path="property.zipcode" id="propzipcode" size="5" maxlength="5"  onblur="getStateCityScript('propzipcode','propcity','propstate')"/>
										
									</fieldset>
								
								
								<table>
								<tr>
								<td>&nbsp;</td>
								<td>
								<fieldset class="form-group">
											<label for="">*City:</label>
									
											<form:input path="property.city" id="propcity" size="30" maxlength="30"  />
									
									</fieldset>
								</td>
								<td>	
									
									<fieldset class="form-group">
											<label for="">*State:</label>
									
											<form:input path="property.state" id="propstate" size="2" maxlength="2"  />
										
									</fieldset>
								</td>
								
								</tr>
								</table>
								</div>
					</div>
        
           
					<div class="col-xs-6">
								
								
								
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

       
	</div>
            </div>  
            <br>
              <p> <strong>As a Landlord we offer No setup fees, No minimums, No hassle, the applicant pays for the screening.</strong> </p>  
            </div>
            </div>    	  
      
     



</form:form>
<%@ include file="/WEB-INF/jsp/includenew/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/footernew.jsp"%>