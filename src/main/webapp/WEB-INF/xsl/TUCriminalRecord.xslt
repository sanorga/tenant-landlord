<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:cred="http://schemas.turss.com/credit/1.0.0/" 
	xmlns:t="http://www.transunion.com/namespace" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
	xmlns:rap="http://www.nlets.org/rapsheet/3.0"  
 	xmlns:turss="http://schemas.turss.com/1.0.0/"  
 	xmlns:crim="http://schemas.turss.com/criminal/1.0.0/"  
	
	xmlns:j="http://www.it.ojp.gov/jxdm/3.0.2"  
 	xmlns:rap-code="http://www.nlets.org/rapsheet/proxy/codes/1.0" 
 	xmlns:evic="http://schemas.turss.com/eviction/1.0.0/"  
	xmlns="http://schemas.turss.com/SmartMove/1.0.0/">

 <xsl:output  method="html"/>
  
 <xsl:template match="/*">
 <html>
 <body>


 
 <fieldset class="form-group">
	<label for="">Category</label>
	<span> <xsl:value-of select="Category"/> </span>
</fieldset>
	
<fieldset class="form-group">
	<label for="">State</label>
	<span><xsl:value-of select="State" /></span>
</fieldset>									

<fieldset class="form-group">
 <label for="">Criminal Entity</label>
  <xsl:for-each select="Rapsheet/CriminalEntity">
  
<fieldset class="form-group">
 <label for="">Purpose Code</label>
 <span><xsl:value-of select="rap:Introduction/rap:RapSheetRequest/rap:PurposeCode" /></span>
</fieldset>

<fieldset class="form-group">
 <label for="">Purpose Code</label>
 <span><xsl:value-of select="rap:Introduction/rap:RapSheetRequest/rap:Attention" /></span>
</fieldset>
 
 
  <fieldset class="form-group">
  <label for=""> Subject </label>
   <xsl:for-each select="crim:Subject">
   
	  <fieldset class="form-group">
	  <label for=""> Name </label>
	  <xsl:value-of select="j:PersonName/j:PersonGivenName"/> 
	  </fieldset>
	  
	  <fieldset class="form-group">
	  <label for=""> Middle name </label>
	  <xsl:value-of select="j:PersonName/j:PersonMiddleName"/> 
	  
	  </fieldset>
	  <fieldset class="form-group">
	  <label for=""> Sur Name </label>
	  <xsl:value-of select="j:PersonName/j:PersonSurName"/> 
	  </fieldset>
  
 </xsl:for-each>
 </fieldset> 
 


  <fieldset class="form-group">
  <label for=""> Cycle </label>
   <xsl:for-each select="crim:Cycle">
  
  
  <fieldset class="form-group">
  <label for=""> Court Action </label>
   <xsl:for-each select="crim:CourtAction">
   <table>
   <tr><td>Activity Type:</td><td><xsl:value-of select="j:ActivityTypeText"/> </td></tr>
   	 <tr><td>Activity Type:</td><td><xsl:value-of select="rap:CourtRecordId/j:ID"/> </td></tr>
   	 <tr><td>Activity Type:</td><td><xsl:value-of select="rap:CourtRecordId/j:IDJurisdictionCode"/> </td></tr>
   	 <tr><td>Activity Type:</td><td><xsl:value-of select="j:Court/j:OrganizationJurisdiction/j:JurisdictionDescriptionText"/> </td></tr>
   	<tr><td>Activity Type:</td><td><xsl:value-of select="crim:CourtCharge/j:ChargeDescriptionText"/> </td></tr>
   	<tr><td>Activity Type:</td><td><xsl:value-of select="crim:CourtCharge/j:ChargeClassification/ChargeDegreeText"/> </td></tr>
   	 <tr><td>Activity Type:</td><td><xsl:value-of select="crim:CourtCharge/j:ChargeClassification/ChargeFelonyIndicator"/> </td></tr>
   	 <tr><td>Activity Type:</td><td><xsl:value-of select="crim:CourtCharge/j:ChargeCountQuantity"/> </td></tr>
   	<tr><td>Activity Type:</td><td><xsl:value-of select="crim:CourtCharge/j:ChargeDisposition/j:ChargeDispositionDate"/> </td></tr>
   	<tr><td>Activity Type:</td><td><xsl:value-of select="crim:CourtCharge/j:ChargeDisposition/j:ChargeDispositionDescriptionText"/> </td></tr>
   	<tr><td>Activity Type:</td><td><xsl:value-of select="crim:CourtCharge/j:ChargeFillingDate"/> </td></tr>
   	<tr><td>Activity Type:</td><td><xsl:value-of select="crim:AppealFromLowercourt"/> </td></tr>
   	<tr><td>Activity Type:</td><td><xsl:value-of select="j:CaseFiling/j:ActivityDate"/> </td></tr>
   	</table>
   </xsl:for-each>
   </fieldset>
  
  </xsl:for-each>
</fieldset>

<fieldset class="form-group">
  <label for=""> Native Record Number </label>
<xsl:value-of select="crim:NativeRecordNumber"/> 
</fieldset>

<fieldset class="form-group">
  <label for=""> Component identifier </label>
<xsl:value-of select="crim:Componentidentifier"/> 
</fieldset>

<fieldset class="form-group">
  <label for=""> Component identifier </label>
<xsl:value-of select="crim:ComponentDescription"/> 
</fieldset>

<fieldset class="form-group">
  <label for=""> Entity Id </label>
<xsl:value-of select="crim:EntityID"/> 
</fieldset>

<fieldset class="form-group">
  <label for=""> Search Status </label>
<xsl:value-of select="crim:SearchStatus"/> 
</fieldset>

</xsl:for-each>
 </fieldset> 

  

</body>
</html>
  
 </xsl:template> 

</xsl:stylesheet>