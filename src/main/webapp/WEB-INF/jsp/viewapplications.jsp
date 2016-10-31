<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<!-- tool tip for services -->
<script type="text/javascript" src="js/jquery/jquery.qtip-1.0.0-rc3.js"></script>

<!-- jquery auto completion js -->
<script src="js/jquery/jquery.autocomplete.js" type="text/javascript"></script>
<!-- Jquery auto completion css -->
<link rel="stylesheet" href="styles/autocomplete/jquery.autocomplete.css" type="text/css" media="screen">




<script type="text/javascript">

	//alert("RF : ${refreshWorkQueue}");
	//var refreshWorkQueue = "${refreshWorkQueue}";
	
	//alert("RT : ${refreshTime}");
	<c:if test="${refreshWorkQueue == 'true'}">
	
		setTimeout("checkRefreshWorkQueue()",${refreshTime});
		function checkRefreshWorkQueue() {
			var jqxhr = $.ajax({
				url: "lookup/workQueueStatus.json",
				dataType: "json",
				timeout: 5000
			})
			.done(function(status){
				if (status == 'true') {
					//alert("status"+status);
					setTimeout("checkRefreshWorkQueue()",${refreshTime});
				} else {
					// refresh the work queue page.
					window.location.href = "${returnUrl}";
				}
			});
		}
	</c:if>
	 
	 function delbtncheck(){
	    var tbl = document.getElementById('searchTable');
	    var rows = tbl.tBodies[0].rows.length;	    
	    if (rows < 1) {
	      alert("Unable to search without search value(s)");			  
	      addRowToTable();	  
	      return false;
	    }
	    return true;		
	}
	function setDecisionFlag(appId,appState){
		debugger;
		 //document.getElementsByName('appToDecide').value=appId;
		 document.getElementById('searchResultsForm').action = "viewapplications.htm?appId="+appId+"&state="+appState;
		 document.getElementById('searchResultsForm').submit();
	}
	
	function sortColumn(columnIndex) {
		var sortType = 'Desc';
		if (sortTypeArr[columnIndex] == 'Desc') {
			sortType = 'Asc';
		} else if (sortTypeArr[columnIndex] == 'Asc') {
			sortType = 'Desc';
		}
		location.href='viewapplications.htm?pageNo=0&sortBy=' + columnIndex + '&sortType=' + sortType;
	}
	function clickFirst(){
		document.getElementById('search').action = "searchapplications.htm?pageNo=0&sortBy=${sortBy}&sortType=${sortType}";
		document.getElementById('search').submit();
		}
	function clickNext(){
		document.getElementById('search').action = "searchapplications.htm?pageNo=${pageNo+1}&sortBy=${sortBy}&sortType=${sortType}";
		document.getElementById('search').submit();
		}

	function clickPrevious(){
		document.getElementById('search').action = "searchapplications.htm?pageNo=${pageNo-1}&sortBy=${sortBy}&sortType=${sortType}";
		document.getElementById('search').submit();
		}
	function clickLast(){
		document.getElementById('search').action = "searchapplications.htm?pageNo=${pageCount-1}&sortBy=${sortBy}&sortType=${sortType}";
		document.getElementById('search').submit();
		}
		
	$(function() {
		$('.toolTip2').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id');
						var valueTip = $('#showhidesrvs'+selectedAttributes).html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});
		
		
		$('.toolTip3').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
						var valueTip = $('#signingserviceTip'+selectedAttributes).html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});
		
		
		$('.toolTip4').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
						var valueTip = $('#rushTip'+selectedAttributes).html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});
		
		
		$('.toolTip5').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
						var valueTip = $('#warnTip'+selectedAttributes).html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});
		
		
		$('.toolTipDep').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id');
						var valueTip = $('#' + selectedAttributes + 'Tip').html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});
		
		
		$('.toolTip6').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
						var valueTip = $('#holdTip'+selectedAttributes).html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});

		$('.toolTip7').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
						var valueTip = $('#actDutyMTip'+selectedAttributes).html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});		
		
		$('.toolTipAbandoned').each(function()
				{ $(this).qtip({
					content: {
						text: 'Loading...'
					},	
					show: {
						show: 'mouseover', // Only show one tooltip at a time
						solo: true // And hide all other tooltips
					},
					position: {
						corner: {
							target: 'bottomMiddle', // Position the tooltip above the link
							tooltip: 'topMiddle'
						},
						adjust: {
							screen: true // Keep the tooltip on-screen at all times
						}
					},
					api: {
						// Retrieve the content when tooltip is first rendered
						onRender: function() {
						var self = this;
						var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
						var valueTip = $('#abandonedTip'+selectedAttributes).html();
						self.updateContent(valueTip);
					}
				},	
				style: {
					border: {
						width: 0,
						radius:2
					},
					tip: true,
					name: 'dark', // Use the default light style
					width: 450 // Set the tooltip width
				}
			});
		});
		

	$('.toolTipApp').each(function()
			{ $(this).qtip({
				content: {
					text: 'Loading...'
				},	
				show: {
					show: 'mouseover', // Only show one tooltip at a time
					solo: true // And hide all other tooltips
				},
				position: {
					corner: {
						target: 'bottomMiddle', // Position the tooltip above the link
						tooltip: 'topMiddle'
					},
					adjust: {
						screen: true // Keep the tooltip on-screen at all times
					}
				},
				api: {
					// Retrieve the content when tooltip is first rendered
					onRender: function() {
					var self = this;
					var selectedAttributes = $(self.elements.target).attr('id').split("_")[1];
					var valueTip = $('#showhideapp'+selectedAttributes).html();
					self.updateContent(valueTip);
				}
			},	
			style: {
				border: {
					width: 0,
					radius:2
				},
				tip: true,
				name: 'dark', // Use the default light style
				width: 450 // Set the tooltip width
			}
		});
	});
	
});
	
	$(function() {
		$(document).tooltip();
	});

	// Auto completion

</script>


<style>label { display: inline-block; width: 5em;}</style>

<!-- SEARCH SCRIPT : START -->
<script type="text/javascript" src="js/searchtable_application.js"></script>
<script type="text/javascript" src="js/searchtable.js"></script>

<script type="text/javascript"> 
  // populate the search params of previous search, if exists 
  var criteriasArr = [];
  var modifiersArr = [];
  var valuesArr = [];
  var logic = '${logic}';
  <c:forEach var="criteria" items="${criterias}">criteriasArr.push("${criteria}");</c:forEach>
  <c:forEach var="modifier" items="${modifiers}">modifiersArr.push("${modifier}");</c:forEach>
  <c:forEach var="value" items="${values}">valuesArr.push("${value}");</c:forEach>


  // pagination sort variables
  var sortByArr = [];
  var sortTypeArr = [];
  
  $(document).ready(function() {
    fillInRows();
    populatePreviousSearchValues(criteriasArr, modifiersArr, valuesArr);
    if (logic == 'AND') { document.getElementById('and').checked = true; } 
    else if (logic == 'OR') { document.getElementById('or').checked = true; }

    // default sort variables initialization
	for (var i = 0; i < 13; i++) {
		sortByArr.push(i);
		if (i == '${sortBy}') {
			sortTypeArr.push('${sortType}');
			if ('${sortType}' == 'Desc') {
				$('#th_' + i).attr("class", "sortingDesc");
			} else {
				$('#th_' + i).attr("class", "sortingAsc");
			}
		} else {
			sortTypeArr.push('');
		}
	}
  });

  function goToPage(object) {
	  window.location='viewapplications.htm?pageNo='+object.value+'&sortBy=${sortBy}&sortType=${sortType}';	   
  }		
  
	function goToPageForSearch(object){
		document.getElementById('search').action = "searchapplications.htm?pageNo="+object.value+"&sortBy=${sortBy}&sortType=${sortType}";
		document.getElementById('search').submit();
	}

</script>
<!-- SEARCH SCRIPT : END -->

<!-- breadcrumbs starts -->
  <div style="background-color: red;">
	<div id="breadCrumb0" class="breadCrumb module" style="background-color: #47555a; color:#FFFFFF;">
        <ul>
            <li><a href="home.htm">Admin</a></li>
			<li><c:choose><c:when test="${fn:startsWith(appstatus, 'C')}">Mail Box</c:when><c:otherwise>Work Queue</c:otherwise></c:choose></li>
		</ul>
	</div>
  </div>
<!-- breadcrumbs ends -->

  <!-- sub heading starts -->
  <div id="subheader">
	<p><c:choose><c:when test="${fn:startsWith(appstatus, 'C')}">Mail Box</c:when><c:otherwise>Work Queue</c:otherwise></c:choose></p>
  </div>
  <!-- sub heading ends -->

<!-- SEARCH CONTENT : START -->
<form:form action="searchapplications.htm?condition=viasearch" method="post" name="search" id="search">
  <!-- Message Starts here -->	
  <div class="messages-area">
    <c:if test="${! empty message}">
      <div class="msgs-confirm" id="msgs-confirm-fv">
      <div class="close"><a href="javascript: hide('msgs-confirm-fv');">[x] close</a></div>
        <fmt:message key="${message}"/>
      </div>
    </c:if>
    <c:if test="${! empty customErrors}">
      <div class="msgs-error" id="msgs-validation-fv">
      <div class="close"><a	href="javascript: hide('msgs-validation-fv');">[x] close</a></div>
        <h1>Please fix the following errors</h1>
        <c:forEach var="customError" items="${customErrors}">${customError}<br/></c:forEach>
      </div>
    </c:if>
  </div>
  <!-- Message Ends here -->	

	<!-- content starts -->
    <div id="content">
      <fieldset>
		<legend><strong>
			<c:choose>
        	<c:when test="${fn:startsWith(appstatus, 'C')}">Mail Box Search</c:when>
        	<c:otherwise>Work Queue Search</c:otherwise>
			</c:choose>
		</strong></legend>

        <table>
          <tr><td><span id="notes">NOTE: Please enter dates as	MM/DD/YYYY.</span></td></tr>
        </table>
        <table id="searchTable">
          <thead>
          <tr>
            <td>
              <input name="logic" type="radio" value="AND" checked="checked" size="10" maxlength="9" id="and"/>
              <label for="and">Match all conditions</label>
            </td>
            <td>
              <input name="logic" type="radio" value="OR" size="10" maxlength="9" id="or"/>
              <label for="or">Match any condition</label>
            </td>
            <td colspan="2"></td>
          </tr>
	      <tr class="heading-3">
	        <th>Item</th>
	        <th>Operator</th>
	        <th>Value</th>
	        <th></th>
	        <th></th>
	      </tr>
	      </thead>
	      <tbody></tbody>
        </table>

        <input name="search" type="submit" id="search" value="Search" name="_btn" style="float:right;" class="button" onclick="return delbtncheck();"/>        
      </fieldset>
    </div>
	<!-- content ends -->
</form:form>
<!-- SEARCH CONTENT : END -->

<!-- START CONTENT HERE -->
<form:form name="searchResultsForm" id="searchResultsForm" action="viewapplications.htm" method="post">
  <input name="target" type="hidden" value="/teappdelete"/>
  <input name="referer" type="hidden" value="search.jsp"/>
  
  <tea:hasAnyAuthority capability="view.any.application,view.partner.application,view.assigned.application"><c:set var="viewapp" value="true"/></tea:hasAnyAuthority>
  <tea:ifAuthorized capability="view.appstatus"><c:set var="viewstatus" value="true"/></tea:ifAuthorized>
<%--  <tea:ifAuthorized capability="view.report"><c:set var="viewreport" value="true"/></tea:ifAuthorized> --%>

    <!-- content starts -->
    <div id="content">
	<c:if test="${fn:length(applications) > 0}">
	<table class="tablesorter">
		<thead>
		<tr>
			<th></th>
			<th></th>
			<th><input type='checkbox' name='ckSelect' value='checkbox' onclick='doCheckAll();'/></th>
			<c:if test="${isClientUser}">
	          <th id="th_12" class="sortingNormal" onclick="javascript: sortColumn(12);">Read/Unread</th>
			</c:if>
			<th id="th_0" class="sortingNormal" onclick="javascript: sortColumn(0);"> State</th>
			<th id="th_1" class="sortingNormal" onclick="javascript: sortColumn(1);">Status</th>
			<th id="th_2" class="sortingNormal" onclick="javascript: sortColumn(2);">Type</th>
			<c:if test="${fn:contains('PAIN',logedInUser.role.role)}">
				<th id="th_13">Payee Info</th>
			</c:if>
			<th id="th_4" class="sortingNormal" onclick="javascript: sortColumn(4);">Name</th>
			<th id="th_6" class="sortingNormal" onclick="javascript: sortColumn(6);">Client/Property Name</th>
			<th>Unit</th>
			<sec:authorize access="hasRole('view.report')">
				<th id="th_7">Reports</th>
			</sec:authorize>
			<th id="th_8" class="sortingNormal" onclick="javascript: sortColumn(8);">Submission Date</th>
			<th id="th_9" class="sortingNormal" onclick="javascript: sortColumn(9);">Completion Date</th>
			<tea:ifAuthorized capability="view.assignments">
			<th id="th_10" class="sortingNormal" onclick="javascript: sortColumn(10);">Investigator</th>
			<th id="th_11" class="sortingNormal" onclick="javascript: sortColumn(11);">CallCenter Agent</th>
			<th id="th_14" class="sortingNormal;">External CSR</th>
			</tea:ifAuthorized>
 
			<th>Approve/Decline</th>

		</tr>
		</thead>
  		<tbody>
		<c:forEach var="appl" items="${applications}" varStatus="row">
			<tr <c:if test="${row.index % 2 == 0}">class="even"</c:if> <c:if test="${row.index % 2 == 1}">class="odd"</c:if>>
				<td>
					<c:choose>
						<c:when test="${fn:startsWith(appl.passthroughStatusCode, 'C') }">
							<div id="gooddep${appl.id}" class="toolTipDep">
						  		<img src="images/money_24.png">
						  	</div>
						  	<div id="gooddep${appl.id}Tip" style="display: none;">
								<strong>Deposits have been transferred to the association bank account.</strong>
							</div>
						</c:when>
						<c:when test="${fn:startsWith(appl.passthroughStatusCode, 'R') }">
							<div id="waitdep${appl.id}" class="toolTipDep">
						  		<img src="images/wait.png">
						  	</div>
						  	<div id="waitdep${appl.id}Tip" style="display: none;">
								<strong>Deposits/Fees Requested. Waiting for payment.</strong>
							</div>
						</c:when>
						<c:when test="${fn:startsWith(appl.passthroughStatusCode, 'W') && fn:startsWith(appl.statusCode, 'C') }">
							<div id="holddep${appl.id}" class="toolTipDep">
						  		<img src="images/info_24.png">
						  	</div>
						  	<div id="holddep${appl.id}Tip" style="display: none;">
								<strong>Waiting for management approval.</strong>
							</div>
						</c:when>
					</c:choose>
				</td>
				<td>
				
	<c:if test="${appl.abandoned }">
						<div id="abandoned_${appl.id}" class="toolTipAbandoned">
					  		<img src="images/services/crass.png">
					  	</div>
					  	<div id="abandonedTip${appl.id}" style="display: none;">
							<strong>Applicant abandoned application</strong>
						</div>
	</c:if>
				
				
				
					<c:if test="${fn:startsWith(appl.hasWarnings, 'Y')}">
						<div id="warn_${appl.id}" class="toolTip5">
					  		<img src="images/warning.png">
					  	</div>
					  	<div id="warnTip${appl.id}" style="display: none;">
							<strong>One or more reports has a warning.</strong>
						</div>
					</c:if>
					<c:if test="${fn:startsWith(appl.hold, 'P')}">
						<div id="hold_${appl.id}" class="toolTip6">
					  		<img src="images/stop_25.png">
					  	</div>
					  	<div id="holdTip${appl.id}" style="display: none;">
							<strong>Hold for payment clearance</strong>
						</div>
					</c:if>
					<c:if test="${appl.rushFee > 0}">
						<div id="rush_${appl.id}" class="toolTip4">
					  		<img src="images/urgent.png">
					  	</div>
					  	<div id="rushTip${appl.id}" style="display: none;">
							<strong>Expedite!</strong>
						</div>
					</c:if>
					
					<c:if test="${appl.hasActDutyMilitary()}">
					<div id="activeDutyM_${appl.id}" class="toolTip7">
						<img src="images/FloridaDepartmentLawEnforcement.png">
					</div>
					<div id="actDutyMTip${appl.id}" style="display: none;">
						<strong>One or more applicants are active duty military.</strong>
					</div>
					</c:if>
					
					<c:if test="${fn:startsWith(appl.signingStatusCode, 'S')}">
					<c:choose>
						<c:when test="${appl.signingProcessor == 'Docusign' }">
							<div id="sign_${appl.id}" class="toolTip3">
						  		<img src="images/wait.png" class="clickable" onclick="setDecisionFlag('${appl.id}','UP')" />
						  	</div>
						  	<div id="signingserviceTip${appl.id}" style="display: none;">
								<strong>Waiting for signature. Click icon to check/update status.</strong>
							</div>
						</c:when>
						<c:otherwise>
							<div id="sign_${appl.id}" class="toolTip3">
						  		<img src="images/wait.png" />
						  	</div>
						  	<div id="signingserviceTip${appl.id}" style="display: none;">
								<strong>Waiting for signature.</strong>
							</div>
						</c:otherwise>
					</c:choose>
					</c:if>
					
					<c:if test="${fn:startsWith(appl.signingStatusCode, 'C')}">
						<div id="sign_${appl.id}" class="toolTip3">
					  		<img src="images/complete.png">
					  	</div>
					  	<div id="signingserviceTip${appl.id}" style="display: none;">
							<strong>Signing is complete</strong>
						</div>
					</c:if>
				</td>
				<td><input type="checkbox" name="cbox" value="${appl.id}"/></td>
				
				<c:if test="${isClientUser}">
				<td>
						<c:choose>
							<c:when test="${appl.readByClient}">
							Read
							</c:when>
							<c:otherwise>
							<font color="red">Unread</font>
							</c:otherwise>
						</c:choose>
				</td>
				</c:if>

				<td>${globals.applicationStateOptions[appl.reportState]}</td>
				<c:choose>
					<c:when test="${(logedInUser.role.role != 'EC')}">
						<td nowrap="nowrap">
							
							<div id="${appl.id}" class="toolTip2">
				                <c:choose>
				                	<c:when test="${(viewstatus == 'true') && !fn:startsWith(appl.statusCode, 'S') && fn:contains('PAINSCCSEC', logedInUser.role.role)}">
					                    <a href="appstatus.htm?applicationId=${appl.id}">${appl.statusString} </a>
					    				<c:if test="${fn:contains('PAINSC',logedInUser.role.role)}">
											<c:set var="incAppCount" value="0" />
											<c:forEach var="aplnt" items="${appl.applicants}" varStatus="row">
												<c:forEach var="report" items="${aplnt.reports}" varStatus="row2">
													<c:choose>
														<c:when test="${fn:startsWith(report.status, 'C')}">
															<c:set var="incAppCount" value="${incAppCount+0}"/>
														</c:when>
														<c:otherwise>
															<c:set var="incAppCount" value="${incAppCount+1}"/>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											 </c:forEach>
											 <c:if test="${(incAppCount == '0')}"><img src="images/greenbulb.png"></c:if> 
										</c:if>
											 <c:if test="${appl.uploadedDocsToReview}"><img src="images/flag.png"></c:if> 	
				                  </c:when>
				                  
		
				                  <c:when test="${fn:startsWith(appl.statusCode, 'C') && fn:contains('CACUPM',logedInUser.role.role)}">
									<sec:authorize access="hasRole('view.report')">
										${appl.statusString}
									</sec:authorize>&nbsp;
				                  </c:when>
				                  <c:otherwise>
				                  <b style="color: green;">${appl.statusString}</b>
				                  <c:if test="${(viewstatus == 'true') && fn:startsWith(appl.statusCode, 'S') && fn:contains('PAINCSSC', logedInUser.role.role) && appl.uploadedDocsToReview}">                  
				                  <img src="images/flag.png">	
				                  </c:if>
				                  </c:otherwise>
				                </c:choose>
<%-- 	--%>			                
				                <c:if test="${fn:startsWith(appl.source,'W') && (fn:startsWith(appl.statusCode, 'S') || fn:startsWith(appl.statusCode, 'N') || fn:startsWith(appl.statusCode, 'P'))}">
											   <a title="Click to view the application status." 
					 				href="myAppStatusSingle.pdf?appId=${appl.id}&pdfCode=${appl.pdfAccessCode}">
					 						<br/><img src="images/pages-24.png" /> 
					 						</a> 
					 			</c:if>
					 			
							</div>
							<div id="showhidesrvs${appl.id}" style="display: none;">
												
								<c:choose>
									<c:when test="${fn:startsWith(appl.type,'O')}">
										<c:forEach var="aplnt" items="${appl.applicants}" varStatus="row" begin="0" end="0">
											<strong><c:if test="${row.index == 0}">Applicant Services:<br></c:if></strong>
											<div class="small">
												<ul style="margin-left: 25px; list-style-type: disc;">
													<c:forEach var="report" items="${aplnt.reports}" varStatus="row2" begin="0" end="0">
													<c:if test="${!fn:startsWith(report.status,'I')}">
													
												<c:choose>
												  	<c:when test="${(logedInUser.role.role == 'EC')}">
												  	<c:if test="${(report.service.id == '5')||(report.service.id == '6')||(report.service.id == '7')}">
														<li style="list-style: circle;">${report.service.name}</li>     
													</c:if>
												   </c:when>
												   <c:otherwise>
														<li style="list-style: circle;">${report.service.name}</li>   
													</c:otherwise>
												</c:choose>
														
													</c:if></c:forEach>
												</ul>
											</div>
										</c:forEach>
									
									</c:when>
									<c:otherwise>
										<c:forEach var="aplnt" items="${appl.applicants}" varStatus="row">
											<strong>
												<c:if test="${row.index == 0}">
													Applicant Services:<br>
												</c:if>
												<c:if test="${row.index == 1}">
													Co-Applicant Services:<br>
												</c:if>
											</strong>
											<div class="small">
												<ul style="margin-left: 25px; list-style-type: disc;">
													<c:forEach var="report" items="${aplnt.reports}" varStatus="row2">
														<c:if test="${!fn:startsWith(report.status, 'I')}">
															
															<c:choose>
															  	<c:when test="${(logedInUser.role.role == 'EC')}">
															  		<c:if test="${(report.service.id == '5')||(report.service.id == '6')||(report.service.id == '7')}">
																		<li style="list-style: circle;">${report.service.name}</li>
																	</c:if>
														   	</c:when>
															   	<c:otherwise>
																	<li style="list-style: circle;">${report.service.name}</li>
																</c:otherwise>
															</c:choose>
															
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</c:forEach>
									
									</c:otherwise>
								</c:choose>
							</div>
						
						</td>
					</c:when>
					<c:otherwise>
						<td>
			                <c:choose>
			                	<c:when test="${(viewstatus == 'true') && !fn:startsWith(appl.statusCode, 'S') && fn:contains('PAINSCCSEC', logedInUser.role.role)}">
			                    	<a href="appstatus.htm?applicationId=${appl.id}">${appl.statusString}</a>
			                  	</c:when>
			                  	<c:otherwise><b style="color: green;">${appl.statusString}</b></c:otherwise>
			                </c:choose>	
				    	</td>
					</c:otherwise>
				</c:choose>
		
		<td><p>${appl.typeString.concat("-").concat(appl.source) }</p></td>
		<c:if test="${fn:contains('PAINSC',logedInUser.role.role)}">
			 <td><a href="paymentinfo.htm?applicationId=${appl.id}">${appl.payeeInfo}</a></td>
		</c:if>

		<td> 
			    <c:choose>
                	<c:when test="${(viewapp == 'true')}">
						<a href="application.htm?applicationId=${appl.id}">${appl.firstApplicant.fullName}</a>
					</c:when>
                 
                  <c:otherwise>${appl.firstApplicant.fullName}</c:otherwise>
                </c:choose>		
		</td>
		<td>${appl.client.name} / ${appl.rentalBuildingName}</td>
		<td>${appl.rentalApartmentNo}</td>
		<sec:authorize access="hasRole('view.report')">
			<td style="test-align: center;">
				<c:choose>
				 <c:when test="${fn:startsWith(appl.statusCode, 'C')}">
				 	<a title="Click to view final report." 
					 	href="finalreport.pdf?applicationId=${appl.id}&summary=n" 
					 	target="_new" 
					 	onclick="javascript: window.open('','_new','menubar=no,scrollbars=no, width=800,height=800')">
						  			<img src="images/documents-24.png" />
					</a>
                  </c:when>
				 <c:when test="${fn:startsWith(appl.source, 'W') && (fn:startsWith(appl.statusCode, 'S') || fn:startsWith(appl.statusCode, 'N') || fn:startsWith(appl.statusCode, 'P'))}">
				 	<a title="Click to view original standard application." 
					 	href="showimage.htm?id=${appl.id}&doctype=4" 
					 	target="_blank">
					  			<img src="images/pages-24.png" />
					</a>
                  </c:when>
                  <c:otherwise>&nbsp;</c:otherwise>
               </c:choose>
			</td>
		</sec:authorize>
		<td>
			<fmt:formatDate pattern="MMM dd, yyyy HH:mm" value="${appl.submissionDate}"/>
		</td>
		<td>
			<fmt:formatDate pattern="MMM dd, yyyy HH:mm" value="${appl.completionDate}"/>
		</td>
	    <tea:ifAuthorized capability="view.assignments">              
        <td>${appl.investigator.firstName} ${appl.investigator.lastName}</td>
        <td>${appl.csr.firstName} ${appl.csr.lastName}</td>
          <td>${appl.externalCsr.firstName} ${appl.externalCsr.lastName}</td>
        </tea:ifAuthorized>
        
        <td>
        <div id="appTip_${appl.id}" class="toolTipApp">
        <c:choose>
        	<c:when test="${appl.getApprovalRequired() && (userId eq appl.getPropertyAppApprovalUserId())}">
        		<c:choose>
			   		<c:when test = "${appl.variableSecurityDeposit}">
			   			<a class="button" href="applicationDisposition.htm?id=${appl.id}&approvalUserId=${appl.getPropertyAppApprovalUserId()}"> Approve</a>
			   		</c:when>
			   		<c:otherwise>
			   			<input type="button" value="Approve" name="_approval" class="button" onclick="setDecisionFlag('${appl.id}','AP')">
			   		</c:otherwise>
			   	</c:choose>
	   			<input type="button" value="Decline" name="_decline" class="button" onclick="setDecisionFlag('${appl.id}','DE')">
        	</c:when>
        	<c:otherwise>
        		<c:choose>
        			<c:when test="${empty appl.approved }">&nbsp;</c:when>
        			<c:otherwise>
        				<c:choose>
        					<c:when test="${appl.approved }">Approved</c:when>
        					<c:otherwise>Disapproved</c:otherwise>
        				</c:choose>
        			</c:otherwise>
        		</c:choose>
        	</c:otherwise>
        </c:choose>
        </div>
        <div id="showhideapp${appl.id}" style="display: none;">
       			<c:choose>
        			<c:when test="${empty appl.approved && appl.getApprovalRequired() && (userId eq appl.getPropertyAppApprovalUserId())}">
        			Approve or Decline
        			</c:when>
        			<c:otherwise>
        				<c:choose>
        					<c:when test="${appl.approved }">
	        					<ul>
									<li><strong>Approved by ${appl.approvedBy.firstName} ${appl.approvedBy.lastName}</strong> </li>
									<li><strong> on <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${appl.approvalDate}" /> </strong>
								</ul>
	        				</c:when>
        					<c:when test="${empty appl.approved && appl.getApprovalRequired()}">			
        						<strong>Pending Approval</strong>
							</c:when>
							<c:when test="${empty appl.approved}">			
        						<strong>Approve/Decline not applicable</strong>
							</c:when>
							<c:otherwise>
							<ul>
									<li><strong>Disapproved by ${appl.approvedBy.firstName} ${appl.approvedBy.lastName}</strong> </li>
									<li><strong> on <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${appl.approvalDate}" /> </strong>
									</li>
							</ul>
							</c:otherwise>
        				</c:choose>
					</c:otherwise>
        		</c:choose>
         </div>
        </td>
        
        
		</tr>
		</c:forEach>
<c:choose>
	<c:when test="${condition eq 'viasearch'}">
		<tr>
			<td colspan="16">
				<c:if test="${pageNo gt 0}">
					<a href="#" onclick="clickFirst();">First</a>&nbsp;
					| &nbsp;
					<a href="#" onclick="clickPrevious();">Previous</a>&nbsp;
					| &nbsp;
				</c:if>
				<c:if test="${pageNo le 0}">
					First&nbsp;
					| &nbsp;
					Previous&nbsp;
					| &nbsp;
				</c:if>

				<select onchange="goToPageForSearch(this)">
					<c:forEach step="1" begin="1"  end="${pageCount}" varStatus="pageNum">
					<c:choose>
					<c:when test="${pageNum.index-1 eq pageNo}">
					<option value="${pageNum.index-1}" selected>${pageNum.index}</option>
					</c:when>
					<c:otherwise>
					<option value="${pageNum.index-1}">${pageNum.index}</option>
					</c:otherwise>
					</c:choose>
					</c:forEach>
				</select>
				| &nbsp;

				<c:if test="${pageNo lt (pageCount -1)}">
					<a href="#" onclick="clickNext();">Next</a>&nbsp;
					| &nbsp;
					<a href="#" onclick="clickLast();">Last</a>&nbsp;
					| &nbsp;
				</c:if>
				<c:if test="${pageNo ge (pageCount-1)}">
					Next&nbsp;
					| &nbsp;
					Last&nbsp;
					| &nbsp;
				</c:if>
				${rowCount} items found, displaying ${(pageNo * rowsPerPage) + 1} to ${(pageNo * rowsPerPage) + fn:length(applications)}.
			</td>
		</tr>

	</c:when>
    <c:otherwise>
          <tr>
			<td colspan="16">
				<c:if test="${pageNo gt 0}">
					<a href="viewapplications.htm?pageNo=0&sortBy=${sortBy}&sortType=${sortType}">First</a>&nbsp;
					| &nbsp;
					<a href="viewapplications.htm?pageNo=${pageNo-1}&sortBy=${sortBy}&sortType=${sortType}">Previous</a>&nbsp;
					| &nbsp;
				</c:if>
				<c:if test="${pageNo le 0}">
					First&nbsp;
					| &nbsp;
					Previous&nbsp;
					| &nbsp;
				</c:if>

				<select onchange="goToPage(this)">
					<c:forEach step="1" begin="1"  end="${pageCount}" varStatus="pageNum">
					<c:choose>
					<c:when test="${pageNum.index-1 eq pageNo}">
					<option value="${pageNum.index-1}" selected>${pageNum.index}</option>
					</c:when>
					<c:otherwise>
					<option value="${pageNum.index-1}">${pageNum.index}</option>
					</c:otherwise>
					</c:choose>
					</c:forEach>
				</select>
				| &nbsp;

				<c:if test="${pageNo lt (pageCount -1)}">
					<a href="viewapplications.htm?pageNo=${pageNo+1}&sortBy=${sortBy}&sortType=${sortType}">Next</a>&nbsp;
					| &nbsp;
					<a href="viewapplications.htm?pageNo=${pageCount-1}&sortBy=${sortBy}&sortType=${sortType}">Last</a>&nbsp;
					| &nbsp;
				</c:if>
				<c:if test="${pageNo ge (pageCount-1)}">
					Next&nbsp;
					| &nbsp;
					Last&nbsp;
					| &nbsp;
				</c:if>
				${rowCount} items found, displaying ${(pageNo * rowsPerPage) + 1} to ${(pageNo * rowsPerPage) + fn:length(applications)}.
			</td>
		</tr>
            
    </c:otherwise>
 </c:choose>
       
	
		
		
	</tbody>
	</table>
	</c:if>
	</div>
	<!-- content ends -->

	<!-- submits starts -->
    <div class="submit-form">
      <table cellpadding="0" cellspacing="0" border="0" width="100%" class="navigaitons">
	    <tr valign="top">
		  <td align="left">
		   	<c:if test="${isClientUser}">
	          <input type="submit" value="Mark Read" name="_markread" class="button" />
            </c:if>
		    <tea:ifAuthorized capability="can.abandon.app">
	          <input type="submit" value="Abandon" name="_abandon" class="button" onclick="return confirmAbandon();"/>
            </tea:ifAuthorized>
		    <tea:ifAuthorized capability="remove.application">
	          <input type="submit" value="Delete" name="_delete" class="button" onclick="return confirmDelete();"/>
            </tea:ifAuthorized>
			 <c:if test="fn:contains('CACUPAPM',logedInUser.role.role)}">
				<input type="submit" value="Remove" name="_remove" class="button" onclick="return confirmDelete();"/>
			</c:if>
            <input type="submit" value="Cancel" name="_cancel" class="button"/>
          </td>
          </tr>
          <tr><td>&nbsp;&nbsp;</td></tr>
<!--         <tr> -->
<!--           <td align="left" colspan="2" style="color:#FFFFFF;"> -->
<%-- 		    <tea:ifAuthorized capability="reassign.applications"> --%>
<%--               Investigator:&nbsp;<select name="investigatorId"><c:forEach items="${investigators}" var="in"><option value="${in.id}">${in.firstName} ${in.lastName}</option></c:forEach></select>&nbsp;&nbsp; --%>
<%--               CallCenter Agent:&nbsp;<select name="csrId"><c:forEach items="${csrs}" var="in"><option value="${in.id}">${in.firstName} ${in.lastName}</option></c:forEach></select>&nbsp;&nbsp; --%>
<%-- 	          External CallCenter Agent:&nbsp;<select name="ecsrId"><c:forEach items="${ecsrs}" var="in"><option value="${in.id}">${in.firstName} ${in.lastName}</option></c:forEach></select> --%>
<!-- 	          <input type="submit" value="Reassign" name="_reassign" class="button"/> -->
<%--             </tea:ifAuthorized> --%>
<!--           </td> -->

<!-- 	    </tr> -->

      </table>
    </div>
	<!-- submits ends -->
</form:form>
<!-- END CONTENT HERE -->

<%@ include file="/WEB-INF/jsp/footer.jsp" %>