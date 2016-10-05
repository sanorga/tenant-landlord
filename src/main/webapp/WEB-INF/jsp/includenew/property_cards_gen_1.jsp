<c:forEach items="${applications}" var="summary" end="35">
	<a class="property_card" href="#" onclick="loadDetail(${summary.applicationId});">
	
	    <div class="property_details">
	    	<span class="user_fullname">${summary.applicantName}</span>
	    	<span class="unit_id">Unit: ${summary.unitNumber}</span>
	    	<span class="property_name">${summary.propertyName}</span>
	    </div>

	    <div class="icon_status_box">
			<c:choose>
			    <c:when test="${summary.approvalStatus==true}"><img src="img/icon_stat_approved.png" alt="Approved" height="15" /></c:when>
			    <c:when test="${summary.approvalStatus==false}"><img src="img/icon_stat_denied.png" alt="Denied" height="15" /></c:when>
			</c:choose>

			<c:choose>
			    <c:when test="${summary.allServicesCompleted}"><img src="img/icon_light_off.png" alt="All completed" height="15" /></c:when>
			    <c:otherwise><img src="img/icon_light_on.png" alt="Not completed" height="15" /></c:otherwise>
			</c:choose>

			<c:choose>
			    <c:when test="${summary.readStatus}"><img src="img/icon_seen.png" alt="Read" height="15" /></c:when>
			    <c:otherwise><img src="img/icon_seen_not.png" alt="Not read" height="15" /></c:otherwise>
			</c:choose>

			<c:choose>
			    <c:when test="${summary.signatureStatus==true}"><img src="img/icon_clock.png" alt="Waiting for signing" height="15" /></c:when>
			    <c:when test="${summary.signatureStatus==false}"><img src="img/icon_checked.png" alt="Signed" height="15" /></c:when>
			</c:choose>

			<c:choose>
			    <c:when test="${summary.newUploadReceived==true}"><img src="img/icon_flag_r.png" alt="Upload received" height="15" /></c:when>
			    <c:when test="${summary.newUploadReceived==false}"><img src="img/icon_flag_y.png" alt="Upload not yet received" height="15" /></c:when>
			</c:choose>

			<c:choose>
			    <c:when test="${summary.depositStatus}"><img src="img/icon_dollar_r.png" alt="Desposited" height="15" /></c:when>
			    <c:when test="${summary.depositStatus==false}"><img src="img/icon_dollar_y.png" alt="Not yet desposited" height="15" /></c:when>
			</c:choose>

			<c:choose>
			    <c:when test="${summary.rushApplication}"><img src="img/icon_runner_r.png" alt="Rush application" height="15" /></c:when>
			    <c:otherwise><img src="img/icon_runner_y.png" alt="Dont rush" height="15" /></c:otherwise>
			</c:choose>

		    <div class="status_info">
				<c:choose>
				    <c:when test="${summary.applicationStatus=='Complete'}"><img src="img/label_completed.png" alt="Completed" height="16" /></c:when>
				    <c:when test="${summary.applicationStatus=='In process'}"><img src="img/label_in_process.png" alt="In process" height="16" /></c:when>
				    <c:when test="${summary.applicationStatus=='S'}"><img src="img/label_waiting_for_signature.png" alt="Waiting for signature" height="16" /></c:when>
				    <c:when test="${summary.applicationStatus=='Saved'}"><img src="img/label_saved.png" alt="Saved" height="16" /></c:when>
				    <c:when test="${summary.applicationStatus=='Archived'}"><img src="img/label_archived.png" alt="Archived" height="16" /></c:when>
				    <c:when test="${summary.applicationStatus=='New'}"><img src="img/label_new.png" alt="New" height="16" /></c:when>
				    <c:otherwise>${summary.applicationStatus}</c:otherwise>
				</c:choose>
			</div>
	    </div>
	</a>
</c:forEach>