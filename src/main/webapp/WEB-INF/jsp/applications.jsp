<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ include file="/WEB-INF/jsp/grd-vw-hdr.jsp" %>

<script>	
	$(document).ready(function(){
		var lastsel;
		$.ajaxSetup({
			beforeSend: function(jqXHR, settings){
				jqXHR.setRequestHeader('${_csrf.headerName}','${_csrf.token}');
			}
		});
		
		$(document).tooltip({
			content: function(callback){
				callback($(this).prop('title').replace('|','<br />'));
			}
		});
		
		$("#list").jqGrid({
			url: "applications.json",
			datatype: "json",
			mtype: "GET",
			key: true,
			shrinkToFit: true,
			colModel: [
						{name: "passthrough", 
							label: " ",
							width: 25, 
							editable: false,
							search: false,
							sortable: false,
							formatter: passthroughFormatter
						},
						{name: "icons", 
							label: " ",
							width: 25,  
							editable: false,
							search: false,
							sortable: false,
							align: "center",
							formatter: iconsFormatter
						},
						{name: "read_by_client", 
							label: "Read/Unread",
							width: 30,  
							editable: false,
							search: false,
							sortable: false,
							formatter: readByClientFormatter
						},
						{name: "report_state", 
							label: "State",
							width: 30,  
							editable: false,
							search: false,
							sortable: false
						},
						{name: "status", 
							label: "Status",
							width: 80,  
							editable: false,
							search: true,
							searchoptions:{sopt:['eq']},
							sortable: true,
							align: "center",
							formatter: statusFormatter
						},
						{name: "type_label", 
							label: "Type",
							width: 60,  
							editable: false,
							search: false,
							sortable: false
						},
						{name: "payee", 
							label: "Payee",
							width: 100,  
							editable: false,
							search: false,
							sortable: false,
							formatter: payeeFormatter
						},
						{name: "name", 
							label: "Name",
							width: 100,  
							editable: false,
							search: true,
							searchoptions:{sopt:['cn','bw','bn','eq','nc','ew','en']},
							sortable: true
						},
						{name: "client", 
							label: "Client",
							width: 100,  
							editable: false,
							search: true,
							searchoptions:{sopt:['cn','bw','bn','eq','nc','ew','en']},
							sortable: true
						},
						{name: "unit", 
							label: "Unit",
							width: 30,  
							editable: false,
							search: false,
							sortable: false
						},
						{name: "view_report", 
							label: "Reports",
							width: 45,  
							editable: false,
							search: false,
							sortable: false,
							align: "center",
							formatter: viewReportFormatter
						},
						{name: "submission_date", 
							label: "Submitted",
							width: 100,  
							editable: false,
							search: true,
							stype: 'text',
							searchoptions:{sopt:['eq','gt','ge','lt','le'], dataInit:datePick},
							sortable: true
						},
						{name: "completion_date", 
							label: "Completed",
							width: 100,  
							editable: false,
							search: true,
							stype: 'text',
							searchoptions:{sopt:['eq','gt','ge','lt','le'], dataInit:datePick},
							sortable: true
						},
						{name: "investigator", 
							label: "Investigator",
							width: 100,  
							editable: false,
							search: true,
							searchoptions:{sopt:['cn','bw','bn','eq','nc','ew','en']},
							sortable: true
						},
						{name: "csr", 
							label: "CS Agent",
							width: 100,  
							editable: false,
							search: true,
							searchoptions:{sopt:['cn','bw','bn','eq','nc','ew','en']},
							sortable: true
						},
						{name: "approved", 
							label: "Approve/Decline",
							width: 100,  
							editable: false,
							search: false,
							sortable: false,
							formatter: approvedFormatter
						},
						{name: "abandoned", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "warning", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "hold", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "rush", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "military", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "signing_status", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "status_label", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "reports_complete", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "pdf_access_code", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "offer_status_report", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "review_docs", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "source", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "need_approval", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "was_approved", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "was_declined", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "pending_approval", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "approver_name", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "approval_date", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "variable_security_deposit", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						},
						{name: "application_id", 
							label: "Application ID",
							width: 0,
							editable: false,
							sortable: false,
							search: true,
							searchtype: "integer",
							searchrules: {number: true},
							searchoptions: {sopt:['eq','gt','ge','lt','le'], searchhidden: true},
							hidden: true
						},
						{name: "statusTip", 
							label: "",
							width: 0,
							editable: false,
							sortable: false,
							search: false,
							hidden: true
						}
						],
			pager: "#pager",
			rowNum: 10,
			rowList: [10,20,40],
			sortname: "submission_date",
			sortorder: "desc",
			viewrecords: true,
			gridview: true,
			hidegrid: false,
			caption: "Applications",
			height: "100%",
			multiselect: true,
			multiboxonly: true,
			altRows: true
		});
		$("#list").jqGrid('navGrid',"#pager", 
				{del: false, search: true, edit: false, add: false, save: false, cancel: false},
				{},
				{},
				{},
				{closeAfterSearch: true, closeAfterReset: false, closeOnEscape: true, searchOnEnter: true, multipleSearch: true}
		);
		
		if(!${isClientUser}){
			$('#list').jqGrid('hideCol',['read_by_client']);
		}
		
		if(!${viewAssignments}){
			$('#list').jqGrid('hideCol',['csr']);
			$('#list').jqGrid('hideCol',['investigator']);
			$('#list').jqGrid.setColProp('csr',{search: false});
			$('#list').jqGrid.setColProp('investigator',{search: false});
		}
				
	});
	
	datePick = function(elem){
		$(elem).datepicker();
	}
	
	function passthroughFormatter(cellValue, options, rowObject){
		var result = '';
		if(cellValue){
			if (cellValue == 'collected'){
				result = '<img src="images/money_24.png" title="Deposits have been transferred to the association."/>';
			} else if (cellValue == 'requested'){
				result = '<img src="images/wait.png" title="Deposits/Fees Requested. Waiting for payment. Click to resend." onclick="resend(' + options.rowId + ')"/>';
			} else if (cellValue == 'waiting'){
				if (rowObject[29]) {
					result = '<img src="images/info_24.png" title="Waiting to send Deposit Request. Click to resend." onclick="resend(' + options.rowId + ')"/>';
				} else {
					result = '<img src="images/info_24.png" title="Waiting for management approval."/>';
				}
				
			}
		}

		return result;
	}
	
	function iconsFormatter(cellValue, options, rowObject){
		var result = '';
		if(rowObject[16]){
			if(result.length > 0){ result = result + '<br>'};
			result = result + '<img src="images/services/crass.png" title="Applicant abandoned application."/>';
		}
		if(rowObject[17]){
			if(result.length > 0){ result = result + '<br>'};
			result = result + '<img src="images/warning.png" title="One or more reports has a warning."/>';
		}
		if(rowObject[18]){
			if(result.length > 0){ result = result + '<br>'};
			result = result + '<img src="images/stop_25.png" title="Hold for payment clearance."/>';
		}
		if(rowObject[19]){
			if(result.length > 0){ result = result + '<br>'};
			result = result + '<img src="images/urgent.png" title="Expedite!"/>';
		}
		if(rowObject[20]){
			if(result.length > 0){ result = result + '<br>'};
			result = result + '<img src="images/FloridaDepartmentLawEnforcement.png" title="One or more applicants are active duty military."/>';
		}
		if(rowObject[21]){
			if(result.length > 0){ result = result + '<br>'};
			if(rowObject[21] == 'unsigned'){
				result = result + '<img src="images/wait.png" title="Waiting for signature."/>';
			} else if(rowObject[21] == 'signed'){
				result = result + '<img src="images/complete.png" title="Signing is complete."/>';
			}
		}
		return result;
	}
	
	function readByClientFormatter(cellValue, options, rowObject){
		if(cellValue){
			if(cellValue == 'Unread'){
				return '<font color="red">' + cellValue + '</font>';
			} else {
				return cellValue;
			} 
		} else {
			return '';
		}
	}
	
	function statusFormatter(cellValue, options, rowObject){
		var content = '';
		var lastLine = 1;
		if(${isPartnerUser}){
			if(cellValue == 'S'){
				content = content + '<a href="application.htm?applicationId=' + options.rowId + '" title="' + rowObject[36] + '" >' + rowObject[22] + '</a>';
			} else {
				content = content + '<a href="appstatus.htm?applicationId=' + options.rowId + '" title="' + rowObject[36] + '" >' + rowObject[22] + '</a>';
			}
			if(rowObject[23]){
				var newContent = '<img src="images/greenbulb.png" title="Reports are complete.">';
					content = content + '<br/>' + newContent;
					lastLine = 2;
			}
			if(rowObject[26]){
				var newContent = '<img src="images/flag.png" title="There are new documents to review.">';
				if (lastLine == 1){
					content = content + '<br/>';
					lastLine = 2;
				}
				content = content + newContent;
			}
		} else {
			content = content + '<span style="color: green; font-weight: bold;" title="' + rowObject[36] + '" >' + rowObject[22] + '</span>';
		}
		if(rowObject[25]){
			if (lastLine == 1){
				content = content + '<br/>';
			}
			content = content + '<a title="Click to view the application status." href="myAppStatusSingle.pdf?appId=' + options.rowId + '&pdfCode=' + rowObject[24] + '" title="' + rowObject[36] + '" ><img src="images/pages-24.png" /> </a>';
		}

		return content;
	}
	
	function payeeFormatter(cellValue, options, rowObject){
		if(cellValue){
			return '<a href="paymentinfo.htm?applicationId=' + options.rowId + '" title="' + cellValue + '">' + cellValue + '</a>';
		} else {
			return '';
		}
	}
	
	function viewReportFormatter(cellValue, options, rowObject){
		if(cellValue){
			var atag = '';
			if(rowObject[4] == 'C'){
				atag = '<a title="Click to view final report."'
				+ ' href="finalreport.pdf?applicationId=' + options.rowId + '&summary=n"'
				+ ' target="_blank">'
				+ '<img src="images/documents-24.png" /></a>';
			} else if(rowObject[27] == 'W' && (rowObject[4] == 'N' || rowObject[4] == 'S' || rowObject[4] == 'P')){
				atag = '<a title="Click to view original standard application."'
				+ ' href="showimage.htm?id=' + options.rowId + '&doctype=4"'
				+ ' target="_blank">'
				+ '<img src="images/pages-24.png" /></a>';
			}

			return atag;
		}
	}
	
	function approvedFormatter(cellValue, options, rowObject){
		var tip = 'Approve/Decline not applicable';
		var content = '';
		if(rowObject[28]){
			tip = 'Approve or Decline';
		} else {
			var tipSuffix = rowObject[32] + ' on ' + rowObject[33];
			if(rowObject[29]){
				tip = 'Approved by ' + tipSuffix;
			} else if(rowObject[30]){
				tip = 'Declined by ' + tipSuffix;
			}
		}
		if(rowObject[28]){
			if(rowObject[34]){
				content = '<a class="button" href="applicationDisposition.htm?id=' + options.rowId + '&approvalUserId=' + ${loginUser.id} + '" title="' + tip + '"> Approve</a>';
			} else {
				content = '<input type="button" value="Approve" class="button" onclick="approveOneApplication(' + options.rowId + ')" title="' + tip + '">';
			}
			content = content + '<input type="button" value="Disapprove" class="button" onclick="disapproveOneApplication(' + options.rowId + ')" title="' + tip + '">';
		} else {
			content = '<span title="' + tip + '">' + cellValue + '</span>';
		}
		
		
		
		
		
		return content;
	}

		function abandonSelected() {
			if (confirmChecked()){
				var s;
				s = $('#list').jqGrid('getGridParam','selarrrow');
				$.ajax({
					url: "abandonApplications.json",
					type: "POST",
					data: {selectedApplications: s}
				})
				.done(function(data){
					$('#list').trigger('reloadGrid');
				})
				.fail(function(xhr, textStatus, errorThrown){
					alert(errorThrown);
				});
			}
		}

		function markReadSelected() {
			if (confirmChecked()){
				var s;
				s = $('#list').jqGrid('getGridParam','selarrrow');
				$.post("markReadApplications.json",{selectedApplications: s})
					.done(function(data){
						$('#list').trigger('reloadGrid');
					})
					.fail(function(xhr, textStatus, errorThrown){
						alert(errorThrown);
					});
			}
		}

		function deleteSelected() {
			if (confirmChecked()){
				var s;
				s = $('#list').jqGrid('getGridParam','selarrrow');
				$.post("deleteApplications.json",{selectedApplications: s})
					.done(function(data){
						$('#list').trigger('reloadGrid');
					})
					.fail(function(xhr, textStatus, errorThrown){
						alert(errorThrown);
					});
			}
		}

		function assignInvestigatorSelected() {
			if (confirmChecked()){
				var s;
				var inv = $('#selectedInvestigator').val();
				if (inv && inv > 0){
					s = $('#list').jqGrid('getGridParam','selarrrow');
					$.post("assignInvestigatorApplications.json",{selectedApplications: s, selectedInvestigator: inv})
						.done(function(data){
							$('#list').trigger('reloadGrid');
						})
						.fail(function(xhr, textStatus, errorThrown){
							alert(errorThrown);
						});
				}
			}
		}

		function assignCsrSelected() {
			if (confirmChecked()){
				var s;
				var csr = $('#selectedCsr').val();
				if (csr && csr > 0){
					s = $('#list').jqGrid('getGridParam','selarrrow');
					$.post("assignCsrApplications.json",{selectedApplications: s, selectedCsr: csr})
						.done(function(data){
							$('#list').trigger('reloadGrid');
						})
						.fail(function(xhr, textStatus, errorThrown){
							alert(errorThrown);
						});
				}
			}
		}

		function approveOneApplication(appId) {
					$.post("approveApplication.json",{appId: appId})
						.done(function(data){
							alert(data);
							$('#list').trigger('reloadGrid');
						})
						.fail(function(xhr, textStatus, errorThrown){
							alert(errorThrown);
						});
		}

		function disapproveOneApplication(appId) {
					$.post("disapproveApplication.json",{appId: appId})
						.done(function(data){
							$('#list').trigger('reloadGrid');
						})
						.fail(function(xhr, textStatus, errorThrown){
							alert(errorThrown);
						});
		}

		function resend(appId) {
					$.post("requestDeposits.json",{appId: appId})
						.done(function(data){
							alert(data);
							$('#list').trigger('reloadGrid');
						})
						.fail(function(xhr, textStatus, errorThrown){
							alert(errorThrown);
						});
		}

		function confirmChecked() {
			  var s;
			  s = $('#list').jqGrid('getGridParam','selarrrow');
			  if (s.length > 0){
				  return true;
			  } else {
				  alert("Please select at least one application");
			      return false;
			  }
		}

	
</script>

<style type="text/css">>

.ui-jqgrid .ui-jqgrid-view {position: relative;left:0; top: 0; padding: 0; font-size:12px; z-index:100;}

.ui-widget input,.ui-widget select,.ui-widget textarea,.ui-widget button{font-family:Arial,sans-serif;font-size:1.5em;border-style: ridge;border-color:#b76361}


 td a:link {color:#b76361; text-decoration:underline;}
 td a:visited {color: #b76361; text-decoration:underline;}
 
.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus {
 border: none;
background: #f6b8b8 50% 50% repeat-x;
font-weight: bold;
color: none;
}

</style>
<!-- breadcrumbs starts -->
  <div style="background-color: red;">
	<div id="breadCrumb0" class="breadCrumb module" style="background-color: #47555a; color:#FFFFFF;">
        <ul>
            <li><a href="home.htm">Admin</a></li>
			<li>Applications</li>
		</ul>
	</div>
  </div>
<!-- breadcrumbs ends -->
<div id="content">
	<table id="list"><tr><td></td></tr></table>
	<div id="pager"></div>
</div>

  <form:form method="POST" id="myForm">
  <!-- messages starts -->
  <div class="messages-area">
    <c:if test="${! empty message}">
      <div class="msgs-confirm" id="msgs-confirm-fv">
      <div class="close"><a href="javascript: hide('msgs-confirm-fv');">[x] close</a></div>
        <fmt:message key="${message}"/>
      </div>
    </c:if>
  </div>
  <!-- messages ends -->

  <!-- submits starts -->
    <div class="submit-form">
    	<input type="hidden" name="selectedApplications" id="selectedApplications"/>
      <table cellpadding="0" cellspacing="0" border="0" width="100%" class="navigaitons">
	    <tr valign="top">
		  <td align="left">
		   	<c:if test="${isClientUser}">
	          <input type="button" value="Mark Read" name="_markread" class="button" onclick="markReadSelected();" />
            </c:if>
		    <tea:ifAuthorized capability="can.abandon.app">
	          <input type="button" value="Abandon" name="_abandon" class="button" onclick="abandonSelected();"/>
            </tea:ifAuthorized>
		    <tea:ifAuthorized capability="remove.application">
	          <input type="button" value="Delete" name="_delete" class="button" onclick="deleteSelected();"/>
            </tea:ifAuthorized>
            <input type="submit" value="Cancel" name="_cancel" class="button"/>
          </td>
          </tr>
          <tr><td>&nbsp;&nbsp;</td></tr>
        <tr>
          <td align="left" colspan="2" style="color:#FFFFFF;">
		    <tea:ifAuthorized capability="reassign.applications">
		    	<table>
		    		<tr>
		    			<td>
		    				Investigator:
		    			</td>
		    			<td>
		    				<select name="investigatorId" id="selectedInvestigator" style="width:160px;"><c:forEach items="${investigators}" var="in"><option value="${in.id}">${in.firstName} ${in.lastName}</option></c:forEach></select>
		    			</td>
		    			<td>
		    				<input type="button" value="Assign selected investigator to selected applications" name="_assignInv" class="button" onclick="assignInvestigatorSelected();" style="width:265px;"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>
		    				CallCenter Agent:
		    			</td>
		    			<td>
		    				<select name="csrId" id="selectedCsr" style="width:160px;"><c:forEach items="${csrs}" var="in"><option value="${in.id}">${in.firstName} ${in.lastName}</option></c:forEach></select>
		    			</td>
		    			<td>
		    				<input type="button" value="Assign selected agent to selected applications" name="_assignCsr" class="button" onclick="assignCsrSelected();" style="width:265px;"/>
		    			</td>
		    		</tr>
		    	</table>
            </tea:ifAuthorized>
          </td>

	    </tr>

      </table>
    </div>
	<!-- submits ends -->
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>