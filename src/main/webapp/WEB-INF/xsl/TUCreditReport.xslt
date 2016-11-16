<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:cred="http://schemas.turss.com/credit/1.0.0/" >
<!-- 	xmlns:t="http://www.transunion.com/namespace" -->
<!-- 	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  -->
<!-- 	xmlns:rap="http://www.nlets.org/rapsheet/3.0"  -->
<!-- 	xmlns:turss="http://schemas.turss.com/1.0.0/"  -->
<!-- 	xmlns:crim="http://schemas.turss.com/criminal/1.0.0/"  -->
	
<!-- 	xmlns:j="http://www.it.ojp.gov/jxdm/3.0.2"  -->
<!-- 	xmlns:rap-code="http://www.nlets.org/rapsheet/proxy/codes/1.0"  -->
<!-- 	xmlns:evic="http://schemas.turss.com/eviction/1.0.0/"  -->
<!-- 	xmlns="http://schemas.turss.com/SmartMove/1.0.0/" -->


<!--   <xsl:output  method="html"/> -->
  
  <xsl:template match="/">
  <html>
  <body>
  				<table border="1">
					<tr>
						<th>Report Date</th>
						<th>Record Found</th>
						<th>Frozen File</th>
						
					</tr>
					
						<tr>
							<td>
								<xsl:value-of select="cred:status/cred:reportDate"/>
							</td>
							<td>
								<xsl:value-of select="cred:status/cred:recordFound"/>
							</td>
							<td>
								<xsl:value-of select="cred:status/cred:frozenFile"/>
							</td>
					</tr>				
				</table>
  
  

</body>	
</html>	
</xsl:template>

</xsl:stylesheet>