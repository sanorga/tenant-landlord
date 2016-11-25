<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:rap="http://www.nlets.org/rapsheet/3.0" 
xmlns:turss="http://schemas.turss.com/1.0.0/" xmlns:crim="http://schemas.turss.com/criminal/1.0.0/" xmlns:cred="http://schemas.turss.com/credit/1.0.0/" 
xmlns:j="http://www.it.ojp.gov/jxdm/3.0.2" xmlns:rap-code="http://www.nlets.org/rapsheet/proxy/codes/1.0" xmlns:evic="http://schemas.turss.com/eviction/1.0.0/" 
xmlns="http://schemas.turss.com/SmartMove/1.0.0/">

 <xsl:output  method="html"/>
  
 <xsl:template match="/*">
 <html>
 <body>

<fieldset class="form-group">
	<label for="">Status</label>
	<span> <xsl:value-of select="cred:status/cred:reportDate"/> </span>
</fieldset>
	
<fieldset class="form-group">
	<label for="">Record Found</label>
	<span><xsl:value-of select="cred:status/cred:recordFound" /></span>
</fieldset>									
 
<fieldset class="form-group">
 <label for="">Frozen File</label>
 <span><xsl:value-of select="cred:status/cred:frozenFile" /></span>
</fieldset>
 
<fieldset class="form-group">
  <label for="">First Name</label>
  <xsl:value-of select="cred:firstName"/> 
</fieldset>

<fieldset class="form-group">
	<label for="">Last Name</label>
	<xsl:value-of select="cred:lastName"/> 
</fieldset>
 
  <fieldset class="form-group">
  <label for=""> Fraud Indicators </label>
  <table border="1">
  <xsl:for-each select="cred:fraudIndicators/cred:fraudIndicator">
	<tr>
	<td>
  	<!-- No Matching record found then choose following check box-->
	 <xsl:value-of select="."/> 
	</td>
	</tr>
   </xsl:for-each>	
</table>
 </fieldset>  

  <fieldset class="form-group">
  <label for=""> Addresses </label>

 				<table border="1">
					<tr>
						<th>Street Address</th>
						<th>City</th>
						<th>State</th>
						<th>Postal Code</th>
						<th>Date Reported</th>
						<th>Address Qualifier</th>
						
					</tr>
					<xsl:for-each select="cred:addresses/cred:address">
						<tr>
							<td>
								<xsl:value-of select="cred:streetAddress"/>
							</td>
							<td>
								<xsl:value-of select="cred:city"/>
							</td>
							<td>
								<xsl:value-of select="cred:state"/>
							</td>
							<td>
								<xsl:value-of select="cred:postalCode"/>
							</td>
							<td>
								<xsl:value-of select="cred:dateReported"/>
							</td>
							<td>
								<xsl:value-of select="cred:addressQualifier"/>
							</td>
						</tr>	
					</xsl:for-each>					
				</table> 
</fieldset>
 
   <fieldset class="form-group">
  <label for=""> Employments</label>
 				<table border="1">
					<tr>
						<th>Employer Name</th>
						<th>Date Verified</th>
						<th>Currently Employed?</th>
						
					</tr>
					<xsl:for-each select="cred:employments/cred:employment">
						<tr>
							<td>
								<xsl:value-of select="cred:employerName"/>
							</td>
							<td>
								<xsl:value-of select="cred:dateVerified"/>
							</td>
							<td>
								<xsl:value-of select="cred:dateEmployed"/>
							</td>
					</tr>	
					</xsl:for-each>					
				</table> 
</fieldset>


   <fieldset class="form-group">
  <label for=""> Inquiries</label>

 				<table border="1">
					<tr>
						<th>Subscriber Id</th>
						<th>Subscriber Name</th>
						<th>Inquiry Date</th>
						
					</tr>
					<xsl:for-each select="cred:inquiries/cred:inquiry">
						<tr>
							<td>
								<xsl:value-of select="cred:subscriberId"/>
							</td>
							<td>
								<xsl:value-of select="cred:subscriberName"/>
							</td>
							<td>
								<xsl:value-of select="cred:inquiryDate"/>
							</td>
					</tr>	
					</xsl:for-each>					
				</table> 
</fieldset>

  <fieldset class="form-group">
  <label for=""> Public Records</label>

 				<table border="1">
					<tr>
						<th>Type</th>
						<th>Member Code</th>
						<th>Industry Code</th>
						<th>Date Settled</th>
						<th>Date Reported</th>
						<th>Date Verified</th>
						<th>Amount</th>
						<th>Asset Amount</th>
						
					</tr>
					<xsl:for-each select="cred:publicRecords/cred:publicRecord">
						<tr>
							<td>
								<xsl:value-of select="cred:publicRecordType"/>
							</td>
							<td>
								<xsl:value-of select="cred:memberCode"/>
							</td>
							<td>
								<xsl:value-of select="industryCode"/>
							</td>
							<td>
								<xsl:value-of select="dateSettled"/>
							</td>
							<td>
								<xsl:value-of select="dateReported"/>
							</td>
							<td>
								<xsl:value-of select="dateVerified"/>
							</td>
							<td>
								<xsl:value-of select="amount"/>
							</td>
							<td>
								<xsl:value-of select="assetAmount"/>
							</td>
					</tr>	
					</xsl:for-each>					
				</table> 
</fieldset>

  <fieldset class="form-group">
  <label for=""> Trade lines</label>

 				<table border="1">
					<tr>
						<th>Subscriber Id</th>
						<th>Subscriber Name</th>
						<th>Date Reported</th>
						<th>Account Type</th>
						<th>Account Number</th>
						<th>Industry Code</th>
						<th>High Credit</th>
						<th>Credit Limit</th>
						<th>Loan Type</th>
						<th>Amount Past Due</th>
						<th>Balance Amount</th>
					</tr>
					<xsl:for-each select="cred:tradeLines/cred:tradeLine">
						<tr>
							<td>
								<xsl:value-of select="cred:subscriberId"/>
							</td>
							<td>
								<xsl:value-of select="cred:subscriberName"/>
							</td>
							<td>
								<xsl:value-of select="cred:dateReported"/>
							</td>
							<td>
								<xsl:value-of select="cred:accountType"/>
							</td>
							<td>
								<xsl:value-of select="cred:accountNumber"/>
							</td>
							<td>
								<xsl:value-of select="cred:industryCode"/>
							</td>
							<td>
								<xsl:value-of select="cred:highCredit"/>
							</td>
							<td>
								<xsl:value-of select="cred:creditLimit"/>
							</td>
							<td>
								<xsl:value-of select="cred:loanType"/>
							</td>
							<td>
								<xsl:value-of select="cred:amountPastDue"/>
							</td>
							<td>
								<xsl:value-of select="cred:balanceAmount"/>
							</td>
					</tr>	
					</xsl:for-each>					
				</table> 
				</fieldset>
				
				<fieldset class="form-group">
 				 	<label for=""> Collections</label>
	 				<table border="1">
						<tr>
							<th>Type</th>
							<th>Customer Number</th>
							<th>AgencyName</th>
							<th>Creditors Name</th>
							<th>Industry Code</th>
							<th>Account Designator</th>
							<th>Current Balance</th>
							<th>Remarks Code</th>
							<th>High Credit</th>
						</tr>
						<xsl:for-each select="cred:collections/cred:collection">
							<tr>
								<td>
									<xsl:value-of select="cred:accountType"/>
								</td>
								<td>
									<xsl:value-of select="cred:customerNumber"/>
								</td>
								<td>
									<xsl:value-of select="cred:collectionAgencyName"/>
								</td>
								<td>
									<xsl:value-of select="cred:creditorsName"/>
								</td>
								<td>
									<xsl:value-of select="cred:industryCode"/>
								</td>
								<td>
									<xsl:value-of select="cred:accountDesignator"/>
								</td>
								<td>
									<xsl:value-of select="cred:currentBalance"/>
								</td>
								<td>
									<xsl:value-of select="cred:remarksCode"/>
								</td>
								<td>
									<xsl:value-of select="cred:highCredit"/>
								</td>
						</tr>	
						</xsl:for-each>					
					</table> 
				</fieldset>

</body>
</html>
  
 </xsl:template> 

</xsl:stylesheet>