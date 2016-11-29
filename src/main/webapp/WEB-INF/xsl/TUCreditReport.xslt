<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:rap="http://www.nlets.org/rapsheet/3.0" 
xmlns:turss="http://schemas.turss.com/1.0.0/" xmlns:crim="http://schemas.turss.com/criminal/1.0.0/" xmlns:cred="http://schemas.turss.com/credit/1.0.0/" 
xmlns:j="http://www.it.ojp.gov/jxdm/3.0.2" xmlns:rap-code="http://www.nlets.org/rapsheet/proxy/codes/1.0" xmlns:evic="http://schemas.turss.com/eviction/1.0.0/" 
xmlns="http://schemas.turss.com/SmartMove/1.0.0/">

 <xsl:output  method="html"/>
  
 <xsl:template match="/*">

<div id="main" class="container">
<div class="row">
<div class="col-xs-12">


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
  <label for=""> akas </label>

	  <xsl:for-each select="cred:akas/cred:aka">
		  <p>Last Name: <xsl:value-of select="turss:lastName"/></p> 
		  <p>Birth Date: <xsl:value-of select="turss:birthDate"/></p> 
		  </xsl:for-each>	

 </fieldset>   
 
  <fieldset class="form-group">
  <label for=""> Scores </label>

  <xsl:for-each select="cred:scores/cred:score">
 	 <p><strong>Score: </strong><xsl:value-of select="cred:score"/></p> 
 	 <p>Model Type Indicator: <xsl:value-of select="cred:modelTypeIndicator"/></p> 
    <table border="0">
   	<tr>
   	<td>  	Score Factor 1:	</td>
	<td> <xsl:value-of select="cred:scoreFactor1"/> </td>
	</tr>
	<tr>
   	<td>  	Score Factor 2:	</td>
	<td> <xsl:value-of select="cred:scoreFactor2"/> </td>
	</tr>
	<tr>
   	<td>  	Score Factor 3:	</td>
	<td> <xsl:value-of select="cred:scoreFactor3"/> </td>
	</tr>
	<tr>
   	<td>  	Score Factor 4:	</td>
	<td> <xsl:value-of select="cred:scoreFactor4"/> </td>
	</tr>
	</table>
	<p>Derogatory Alert Code: <xsl:value-of select="cred:derogAlertCode"/></p> 
   </xsl:for-each>	

 </fieldset>   
 
 
 
  <fieldset class="form-group">
  <label for=""> Fraud Indicators </label>
  <br>-----------------------------------------------------------------------------------</br>	
 			
  <table border="1">
  <xsl:for-each select="cred:fraudIndicators/cred:fraudIndicator">
	<tr>
	<td>
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
<br>-----------------------------------------------------------------------------------</br>	
 			
 				<xsl:for-each select="cred:tradeLines/cred:tradeLine">
 					<table border="0">
						<tr><td><strong>Subscriber Id:</strong></td><td><xsl:value-of select="cred:subscriberId"/></td></tr>
						<tr><td><strong>Subscriber Name:</strong></td><td><xsl:value-of select="cred:subscriberName"/></td></tr>
						<tr><td><strong>Date Reported:</strong></td><td><xsl:value-of select="cred:dateReported"/></td></tr>
						<tr><td><strong>Account Type:</strong></td><td><xsl:value-of select="cred:accountType"/></td></tr>
						<tr><td><strong>Account Number:</strong></td><td><xsl:value-of select="cred:accountNumber"/></td></tr>
						<tr><td><strong>Industry Code:</strong></td><td><xsl:value-of select="cred:industryCode"/></td></tr>
						<tr><td><strong>High Credit:</strong></td><td><xsl:value-of select="cred:highCredit"/></td></tr>
						<tr><td><strong>Credit Limit:</strong></td><td><xsl:value-of select="cred:creditLimit"/></td></tr>
						<tr><td><strong>Loan Type:</strong></td><td><xsl:value-of select="cred:loanType"/></td></tr>
						<tr><td><strong>Amount Past Due:</strong></td><td><xsl:value-of select="cred:amountPastDue"/></td></tr>
						<tr><td><strong>Balance Amount:</strong></td><td><xsl:value-of select="cred:balanceAmount"/></td></tr>
					</table> 
					<br>-----------------------------------------------------------------------------------</br>
				</xsl:for-each>					
				
</fieldset>
				
				<fieldset class="form-group">
 				 	<label for=""> Collections</label>
	 				<br>-----------------------------------------------------------------------------------</br>	
						<xsl:for-each select="cred:collections/cred:collection">
						<table border="0">
						<tr><td><strong>Type:</strong></td><td><xsl:value-of select="cred:accountType"/></td></tr>
						<tr><td><strong>Customer Number:</strong></td><td><xsl:value-of select="cred:customerNumber"/></td></tr>
						<tr><td><strong>AgencyName:</strong></td><td><xsl:value-of select="cred:collectionAgencyName"/></td></tr>
						<tr><td><strong>Creditors Name:</strong></td><td><xsl:value-of select="cred:creditorsName"/></td></tr>
						<tr><td><strong>Industry Code:</strong></td><td><xsl:value-of select="cred:industryCode"/></td></tr>
						<tr><td><strong>Account Designator:</strong></td><td><xsl:value-of select="cred:accountDesignator"/></td></tr>
						<tr><td><strong>Current Balance:</strong></td><td><xsl:value-of select="cred:currentBalance"/></td></tr>
						<tr><td><strong>Remarks Code:</strong></td><td><xsl:value-of select="cred:remarksCode"/></td></tr>
						<tr><td><strong>High Credit:</strong></td><td><xsl:value-of select="cred:highCredit"/></td></tr>
						</table> 
						<br>-----------------------------------------------------------------------------------</br>		
						</xsl:for-each>					
					
				</fieldset>

</div>
</div>

</div>
  
 </xsl:template> 

</xsl:stylesheet>