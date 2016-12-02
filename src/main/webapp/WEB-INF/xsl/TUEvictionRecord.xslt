<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:rap="http://www.nlets.org/rapsheet/3.0" 
xmlns:turss="http://schemas.turss.com/1.0.0/" 
xmlns:crim="http://schemas.turss.com/criminal/1.0.0/" 
xmlns:cred="http://schemas.turss.com/credit/1.0.0/" 
xmlns:j="http://www.it.ojp.gov/jxdm/3.0.2" 
xmlns:rap-code="http://www.nlets.org/rapsheet/proxy/codes/1.0" 
xmlns:evic="http://schemas.turss.com/eviction/1.0.0/" 
xmlns="http://schemas.turss.com/SmartMove/1.0.0/">
 <xsl:output  method="html"/>
  
 <xsl:template match="/*">
 <html>
 <body>

   <div id="main" class="container">
<div class="row">
					<div class="col-xs-12">
   <xsl:for-each select="EvictionRecord">
   
   <p><strong>Eviction Record </strong></p>
     <fieldset class="form-group">
  <label for="">  </label>
    <table border="1">
	<tr>

		<th>State</th>			
	</tr>
 	<tr>
	<td> <xsl:value-of select="State"/> 
	</td>
	</tr>
	</table>
 </fieldset>  
	
	   
	  <xsl:for-each select="Eviction/EvictionDetails">
	    <p><strong> Eviction Details </strong></p>
		<fieldset class="form-group">
		<label for="">Description:</label>
		<span><xsl:value-of select="ComponentDescription" /></span>
		</fieldset>
		<fieldset class="form-group">
		<label for="">Record Id:</label>
		<span><xsl:value-of select="RecordId" /></span>
		</fieldset>
 		<fieldset class="form-group">
		<label for="">File Number:</label>
		<span><xsl:value-of select="FileNumber" /></span>
		</fieldset>
		 <fieldset class="form-group">
		<label for="">County:</label>
		<span><xsl:value-of select="County" /></span>
		</fieldset>
		<fieldset class="form-group">
		<label for="">Action Date:</label>
		<span><xsl:value-of select="ActionDate" /></span>
		</fieldset>
		<fieldset class="form-group">
		<label for="">CaseType:</label>
		<span><xsl:value-of select="CaseType" /></span>
		</fieldset>
		<fieldset class="form-group">
		<label for="">Plaintiff:</label>
		<span><xsl:value-of select="Plaintiff" /></span>
		</fieldset>
		<fieldset class="form-group">
		<label for="">Judgment Amount:</label>
		<span><xsl:value-of select="JudgmentAmount" /></span>
		</fieldset>
		
			<fieldset class="form-group">
				<label for=""> Name </label>
				<table border="1">
				<tr>
					<th>First Name</th>
					<th>Last Name</th>	
					<th>Middle Name</th>	
					<th>Age</th>
							
				</tr>
				<tr>
				<td>
				<xsl:value-of select="Subject/Firstname"/> 
				</td>
				<td>
				<xsl:value-of select="Subject/LastName"/> 
				</td>
				<td>
				<xsl:value-of select="Subject/MiddleName"/> 
				</td>
				<td>
				<xsl:value-of select="Subject/Age"/> 
				</td>
				</tr>
			
				</table>
				<br></br>
				<table border="1">
				<tr>
					<th>Street Address</th>
					<th>City</th>
					<th>State</th>
					<th>Zip Code</th>					
				</tr>
				<tr>
				<td>
				<xsl:value-of select="Subject/StreetAddress"/> 
				</td>
				<td>
				<xsl:value-of select="Subject/City"/> 
				</td>
				<td>
				<xsl:value-of select="Subject/State"/> 
				</td>
				<td>
				<xsl:value-of select="Subject/ZipCode"/> 
				</td>
				</tr>
				</table>
			</fieldset>

		 			
	
   </xsl:for-each>	
   </xsl:for-each>	

</div>
</div>

 </div>
 </body>
</html>
 </xsl:template> 

</xsl:stylesheet>