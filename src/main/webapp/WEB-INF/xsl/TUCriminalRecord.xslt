<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:rap="http://www.nlets.org/rapsheet/3.0" 
	xmlns:turss="http://schemas.turss.com/1.0.0/" xmlns:crim="http://schemas.turss.com/criminal/1.0.0/" xmlns:cred="http://schemas.turss.com/credit/1.0.0/" 
	xmlns:j="http://www.it.ojp.gov/jxdm/3.0.2" xmlns:rap-code="http://www.nlets.org/rapsheet/proxy/codes/1.0" xmlns:evic="http://schemas.turss.com/eviction/1.0.0/" 
	xmlns="http://schemas.turss.com/SmartMove/1.0.0/">
 <xsl:output  method="html"/>
  
 <xsl:template match="/*">
 <html>
 <body>
   <div id="main" class="container">
<div class="row">
					<div class="col-xs-12">
   <xsl:for-each select="CriminalRecord">
   
   <p><strong>Criminal Record </strong></p>
     <fieldset class="form-group">
  <label for="">  </label>
    <table border="1">
	<tr>
		<th>Category</th>
		<th>State</th>			
	</tr>
 	<tr>
	<td> <xsl:value-of select="Category"/> 
	</td>
	<td> <xsl:value-of select="State"/> 
	</td>
	</tr>
	</table>
 </fieldset>  
	
	   
	  <xsl:for-each select="Rapsheet/CriminalEntity">
	    <p><strong> Criminal Entity </strong></p>
		<fieldset class="form-group">
		<label for="">Purpose Code:</label>
		<span><xsl:value-of select="rap:Introduction/rap:RapSheetRequest/rap:PurposeCode" /></span>
		</fieldset>
		<fieldset class="form-group">
		<label for="">Attention:</label>
		<span><xsl:value-of select="rap:Introduction/rap:RapSheetRequest/rap:Attention" /></span>
		</fieldset>
 
		 
			<fieldset class="form-group">
				<label for=""> Name </label>
				<table border="1">
				<tr>
					<th>Given Name</th>
					<th>Middle name</th>	
					<th>SurName</th>						
				</tr>
				<tr>
				<td>
				<xsl:value-of select="crim:Subject/j:PersonName/j:PersonGivenName"/> 
				</td>
				<td>
				<xsl:value-of select="crim:Subject/j:PersonName/j:PersonMiddleName"/> 
				</td>
				<td>
				<xsl:value-of select="crim:Subject/j:PersonName/j:PersonSurName"/> 
				</td>
				</tr>
				</table>
			</fieldset>

		 			
	
	   <xsl:for-each select="crim:Cycle">
	   <h4> Cycle </h4>
	       <table border="1">
		   
	    <xsl:for-each select="crim:CourtAction">
	
		   <tr><td>Activity Type:</td><td><xsl:value-of select="j:ActivityTypeText"/> </td></tr>
			 <tr><td>Record Id:</td><td><xsl:value-of select="rap:CourtRecordID/j:ID"/> </td></tr>
			 <tr><td>Jurisdiction Code:</td><td><xsl:value-of select="rap:CourtRecordID/j:IDJurisdictionCode"/> </td></tr>
			 <tr><td>Jurisdiction Description:</td><td><xsl:value-of select="j:Court/j:OrganizationJurisdiction/j:JurisdictionDescriptionText"/> </td></tr>
			 
			 <xsl:for-each select="crim:CourtCharge">
				<tr><td>Charge Description:</td><td><xsl:value-of select="j:ChargeDescriptionText"/> </td></tr>
				<tr><td>Charge Degree:</td><td><xsl:value-of select="j:ChargeClassification/ChargeDegreeText"/> </td></tr>
				 <tr><td>Charge Felony:</td><td><xsl:value-of select="j:ChargeClassification/ChargeFelonyIndicator"/> </td></tr>
				 <tr><td>Count Quantity:</td><td><xsl:value-of select="j:ChargeCountQuantity"/> </td></tr>
				<tr><td>Charge Disposition Date:</td><td><xsl:value-of select="j:ChargeDisposition/j:ChargeDispositionDate"/> </td></tr>
				<tr><td>Charge Disposition Description:</td><td><xsl:value-of select="j:ChargeDisposition/j:ChargeDispositionDescriptionText"/> </td></tr>
				<tr><td>Charge Filling Date:</td><td><xsl:value-of select="j:ChargeFillingDate"/> </td></tr>
			</xsl:for-each>	
			
			<tr><td>Appeal from lower Court:</td><td><xsl:value-of select="crim:AppealFromLowercourt"/> </td></tr>
			<tr><td>Activity Date:</td><td><xsl:value-of select="j:CaseFiling/j:ActivityDate"/> </td></tr>
			
	    </xsl:for-each>	
		</table>
	   </xsl:for-each>	
	   
	   <p> Native Record Number: <xsl:value-of select="crim:NativeRecordNumber"/> </p>
	   <p> Component Identifier: <xsl:value-of select="crim:ComponentIdentifier"/> </p>
		<p> Component Description: <xsl:value-of select="crim:ComponentDescription"/> </p>
		<p> Entity ID: <xsl:value-of select="crim:EntityID"/> </p>
		<p> Applicant Match Category: <xsl:value-of select="crim:ApplicantMatchCategory"/> </p>
		<p> Search Status: <xsl:value-of select="crim:SearchStatus"/> </p>

	   
	  </xsl:for-each>	
	
   </xsl:for-each>	

</div>
</div>

 </div>
 </body>
</html>
 </xsl:template> 

</xsl:stylesheet>