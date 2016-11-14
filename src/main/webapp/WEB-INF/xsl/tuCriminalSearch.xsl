<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet version="1.0" xmlns:t="http://tlo.com/" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html"/>
<xsl:template match="/">
	<xsl:apply-templates/>
	<!-- No Matching record found then choose following check box-->
	<input type="checkbox" name="matchedOffender" value="No Match"/><label> No Match </label>
	
</xsl:template>
<xsl:template match="text()"/>

<xsl:template match="t:CriminalRecord" priority="1">
		  		<table class="inputtable" style="background-color: #000000;width: 100%;">
					<tr>
						<td width="50%"><h2><xsl:value-of select="concat(t:Suspect/t:Name/t:FirstName,' ',t:Suspect/t:Name/t:MiddleName,' ',t:Suspect/t:Name/t:LastName)" /></h2></td>
						<td align="right" width="25%">ID #<xsl:value-of select="../t:RecordId"/><br /><input type="checkbox" name="matchedOffender"><xsl:attribute name="value"><xsl:value-of select="../t:RecordId"/></xsl:attribute></input></td>
						<td rowspan="4" width="25%"><xsl:apply-templates select="t:Suspect/t:PictureURL"/>&#160;</td>
					</tr>
					<xsl:apply-templates select="t:Suspect/t:DateOfBirth"/>
					<xsl:apply-templates select="t:Suspect/t:SSN"/>
					<xsl:apply-templates select="t:Suspect/t:CurrentAge"/>
					<tr>
						<td colspan="2">
							Aliases, AKA's and Maiden Names: 

							<!-- We need to place a comma after each name and obviously make sure the last name -->
							<!-- does not have one. So we first we find the count of alias names available. -->
							<xsl:variable name="namesCount" select="count(t:Suspect/t:AKAs)" />

							<xsl:for-each select="t:Suspect/t:AKAs">
								<!-- Display the alias name .&#160; is to add space. Equivalent to &nbsp; in HTML -->
								<xsl:value-of select="concat(t:FirstName,' ',t:MiddleName,' ',t:LastName)" />&#160;
								
								<!-- Check if we need to add a comma or a period following the name. -->											
								<xsl:choose>
									<xsl:when test="$namesCount = position()"> . </xsl:when>
									<xsl:otherwise>, </xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							Alternate DOBs: 
								
							<!-- We need to place a comma after each DOB and obviously make sure the last DOB -->
							<!-- does not have one. So we first we find the count of alias DOBs available. -->
							<xsl:variable name="dobCount" select="count(t:Suspect/t:OtherDatesOfBirth)" />
					
							<xsl:for-each select="t:Suspect/t:OtherDatesOfBirth">
								<!-- Display the alias DOB .&#160; is to add space. Equivalent to &nbsp; in HTML -->
								<xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)" />
						
								<!-- Check if we need to add a comma or nothing following the DOB. -->											
								<xsl:choose>
									<xsl:when test="$dobCount = position()"></xsl:when>
									<xsl:otherwise>, </xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>											
						</td>
					</tr>
					<tr>
						<td>
							<xsl:if test="t:Suspect/t:Ethnicity != ''">
								Race: <xsl:value-of select="t:Suspect/t:Ethnicity" /> <br />
							</xsl:if>

							<xsl:if test="Suspect/Gender != ''">
								<xsl:value-of select="t:Suspect/t:Gender" />
								<br />
							</xsl:if>

							<xsl:if test="t:Suspect/t:Hair != ''">
								Hair: <xsl:value-of select="t:Suspect/t:Hair" /> <br />
							</xsl:if>
							<xsl:if test="t:Suspect/t:Eyes != ''">
								Eyes: <xsl:value-of select="t:Suspect/t:Eyes" /> <br />
							</xsl:if>

							<xsl:if test="t:Suspect/t:Height != ''">
								Heigh <xsl:value-of select="t:Suspect/t:Height" /><br />
							</xsl:if>
							<xsl:if test="t:Suspect/t:Weight != ''">
								Weigh <xsl:value-of select="t:Suspect/t:Weight" /> <br />
							</xsl:if>
						</td>

						<!-- Address information. -->
						<td>

							<!-- Street information. -->
							<xsl:if test="t:Suspect/t:Address/t:Line1 != ''">
								<xsl:value-of select="t:Suspect/t:Address/t:Line1" /><br/>
							</xsl:if>

							<!-- City, State and Zipcode info need to displayed side by side. -->
							<xsl:if test="t:Suspect/t:Address/t:City != ''">
								<xsl:value-of select="t:Suspect/t:Address/t:City" />
							</xsl:if>

							<xsl:choose>
								<xsl:when test="t:Suspect/t:Address/t:State != ''">
									<xsl:choose>
										<!-- Display comma after city only if there's state AND city info available. -->
										<xsl:when test="t:Suspect/t:Address/t:City != ''">
										, &#160;<xsl:value-of select="t:Suspect/t:Address/t:State" />
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="t:Suspect/t:Address/t:State" />
										</xsl:otherwise>
									</xsl:choose>
								</xsl:when>
							</xsl:choose>

							<!-- Zipcode information. -->
							<xsl:if test="t:Suspect/t:Address/t:Zip != ''">
								&#160;<xsl:value-of select="t:Suspect/t:Address/t:Zip" />
							</xsl:if>

							<!-- County information. -->
							<xsl:if test="t:Suspect/t:Address/t:County != ''">
								&#160;<xsl:value-of select="t:Suspect/t:Address/t:County" />
							</xsl:if>
						</td>
					</tr>
				</table>
				
				<!-- Display details of each offense for each offender when record has Crime Details-->
				<xsl:for-each select="t:CrimeDetails/t:Details">
					<h2><xsl:value-of select="t:OffenseDescription1"/></h2>
					<h2><xsl:value-of select="t:OffenseDescription2"/></h2>
					<sub><xsl:value-of select="t:SourceName"/></sub>	

					<table class="tableddata">
						
						<xsl:apply-templates select="t:Disposition"/>
						<xsl:apply-templates select="t:DegreeOfOffense"/>
						<xsl:apply-templates select="t:SourceState"/>
						<xsl:apply-templates select="t:CaseNumber"/>
						<xsl:apply-templates select="t:ArrestingAgency"/>
						<xsl:apply-templates select="t:County"/>
						<xsl:apply-templates select="t:CrimeCounty"/>
						<xsl:apply-templates select="t:Status"/>
						<xsl:apply-templates select="t:StatusDate"/>
						<xsl:apply-templates select="t:Warrant"/>
						<xsl:apply-templates select="t:WarrantDate"/>
						<xsl:apply-templates select="t:CrimeType"/>
						<xsl:apply-templates select="t:Category"/>
						<xsl:apply-templates select="t:Classification"/>
						<xsl:apply-templates select="t:OffenseCode"/>
						<xsl:apply-templates select="t:GradeOfOffense"/>
						<xsl:apply-templates select="t:NCICCode"/>
						<xsl:apply-templates select="t:Counts"/>
						<xsl:apply-templates select="t:CaseType"/>
						<xsl:apply-templates select="t:Statute"/>
						<xsl:apply-templates select="t:Violation"/>
						<xsl:apply-templates select="t:Court"/>
						<xsl:apply-templates select="t:CourtCosts"/>
						<xsl:apply-templates select="t:Fines"/>
						<xsl:apply-templates select="t:Plea"/>
						<xsl:apply-templates select="t:ConvictionPlace"/>
						<xsl:apply-templates select="t:Sentence"/>
						<xsl:apply-templates select="t:Probation"/>
						<xsl:apply-templates select="t:ChargesFiledDate"/>
						<xsl:apply-templates select="t:OffenseDate"/>
						<xsl:apply-templates select="t:ViolationDate"/>
						<xsl:apply-templates select="t:ArrestDate"/>
						<xsl:apply-templates select="t:ConvictionDate"/>
						<xsl:apply-templates select="t:DispositionDate"/>
						<xsl:apply-templates select="t:RegistrationDate"/>
						<xsl:apply-templates select="t:RegistrationEndDate"/>
						<xsl:apply-templates select="t:Predator"/>
						<xsl:apply-templates select="t:Victim"/>
						<xsl:apply-templates select="t:VictimGender"/>
						<xsl:apply-templates select="t:VictimAge"/>
						<xsl:apply-templates select="t:VictimIsMinor"/>	
					</table>
</xsl:for-each>

	        <!-- Display details of each offense for each offender when record has Arrest Details -->
				<xsl:for-each select="t:ArrestDetails/t:Details">
					<h2><xsl:value-of select="t:Charges"/></h2>

					<table class="tableddata">
						
						<xsl:apply-templates select="t:SourceState"/>
						<xsl:apply-templates select="t:CaseNumber"/>
						<xsl:apply-templates select="t:Court"/>
						<xsl:apply-templates select="t:ArrestDate"/>
												
						<xsl:apply-templates select="t:PrisonerId"/>
						<xsl:apply-templates select="t:Agency"/>
						<xsl:apply-templates select="t:AgencyIdLabel"/>
						<xsl:apply-templates select="t:AgencyIdValue"/>
						<xsl:apply-templates select="t:FederalJurisdiction"/>
						<xsl:apply-templates select="t:StateJurisdiction"/>
						<xsl:apply-templates select="t:CountyJurisdiction"/>
						<xsl:apply-templates select="t:CityJurisdiction"/>
						<xsl:apply-templates select="t:ArrestLocation"/>
						<xsl:apply-templates select="t:Officer"/>
						<xsl:apply-templates select="t:Facility"/>
						<xsl:apply-templates select="t:ChargeClass"/>
						<xsl:apply-templates select="t:CurrentLocation"/>
						<xsl:apply-templates select="t:CurrentStatus"/>
						<xsl:apply-templates select="t:Division"/>
						<xsl:apply-templates select="t:CourtAppearance"/>
						<xsl:apply-templates select="t:Bail"/>
						<xsl:apply-templates select="t:Bond"/>
						<xsl:apply-templates select="t:BookingNumber"/>
						<xsl:apply-templates select="t:BookingDate"/>
						<xsl:apply-templates select="t:Fine"/>
						<xsl:apply-templates select="t:SupervisionType"/>
						<xsl:apply-templates select="t:SupervisionDate"/>
						<xsl:apply-templates select="t:ConfineDate"/>
						<xsl:apply-templates select="t:ReleaseDate"/>
						<xsl:apply-templates select="t:Remarks"/>

					</table>
</xsl:for-each>
					
				<!-- Display details of each offense for each offender when record has Warrant Details -->
				<xsl:for-each select="t:WarrantDetails/t:Details">
					<h2><xsl:value-of select="t:OffenseInformation"/></h2>

					<table class="tableddata">
						
						<xsl:apply-templates select="t:SourceState"/>
						<xsl:apply-templates select="t:CaseNumber"/>
						<xsl:apply-templates select="t:Court"/>
						<xsl:apply-templates select="t:ArrestDate"/>
												
						<xsl:apply-templates select="t:Agency"/>
						<xsl:apply-templates select="t:AgencyIdLabel"/>
						<xsl:apply-templates select="t:AgencyIdValue"/>
						<xsl:apply-templates select="t:NCICNumber"/>
						<xsl:apply-templates select="t:OffenseDate"/>
						<xsl:apply-templates select="t:OffenseType"/>
						<xsl:apply-templates select="t:WarrantNumber"/>
						<xsl:apply-templates select="t:WarrantAgency"/>
						<xsl:apply-templates select="t:WarrantType"/>
						<xsl:apply-templates select="t:WarrantDate"/>
						<xsl:apply-templates select="t:WarrantEnterDate"/>
						<xsl:apply-templates select="t:WarrantTerminationDate"/>
						<xsl:apply-templates select="t:Precautions"/>
						<xsl:apply-templates select="t:LastSeenLocation"/>
						<xsl:apply-templates select="t:LastSeenDate"/>
						<xsl:apply-templates select="t:SupervisionType"/>
						<xsl:apply-templates select="t:SupervisionDate"/>
						<xsl:apply-templates select="t:Officer"/>
						<xsl:apply-templates select="t:FederalJurisdiction"/>
						<xsl:apply-templates select="t:StateJurisdiction"/>
						<xsl:apply-templates select="t:CountyJurisdiction"/>
						<xsl:apply-templates select="t:CityJurisdiction"/>
						<xsl:apply-templates select="t:Division"/>
						<xsl:apply-templates select="t:CourtAppearance"/>
						<xsl:apply-templates select="t:Restitution"/>
						<xsl:apply-templates select="t:Bail"/>
						<xsl:apply-templates select="t:BailReturnable"/>
						<xsl:apply-templates select="t:PurgeAmount"/>
						<xsl:apply-templates select="t:CurrentLocation"/>
						<xsl:apply-templates select="t:CurrentStatus"/>
						<xsl:apply-templates select="t:Remarks"/>
												
					</table>
</xsl:for-each>
					<hr /> 

</xsl:template>
<xsl:template match="t:CurrentAge">
	<tr>
		<td colspan="2"><xsl:value-of select="concat('Current Age: ',.)"/></td>
	</tr>
</xsl:template>
<xsl:template match="t:SSN">
	<tr>
		<td colspan="2"><xsl:value-of select="concat('SSN: ',.)"/></td>
	</tr>
</xsl:template>
<xsl:template match="t:DateOfBirth">
	<xsl:if test="t:Month != ''">
		<tr>
			<td colspan="2"><xsl:value-of select="concat('DOB: ',t:Month,'/',t:Day,'/',t:Year)"/></td>
		</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:PictureURL">
	<img border="0" height="200">
		<xsl:attribute name="SRC">
			<xsl:value-of select="."/>
		</xsl:attribute>
	</img>
</xsl:template>
<xsl:template match="t:SourceState">
	<tr>
		<th>Source State</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CaseNumber">
	<tr>
		<th>Case Number</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:ArrestingAgency">
	<tr>
		<th>Arresting Agency</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:County">
	<tr>
		<th>County</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CrimeCounty">
	<tr>
		<th>Crime County</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Status">
	<tr>
		<th>Status</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:StatusDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Status Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:Warrant">
	<tr>
		<th>Warrant</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:WarrantDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Warrant Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:CrimeType">
	<tr>
		<th>Crime Type</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Category">
	<tr>
		<th>Category</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Classification">
	<tr>
		<th>Classification</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:OffenseCode">
	<tr>
		<th>Offense Code</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:GradeOfOffense">
	<tr>
		<th>Grade of Offense</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:DegreeOfOffense">
	<tr>
		<th>Degree of Offense</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:NCICCode">
	<tr>
		<th>NCIC Code</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:OffenseDescription1">
	<tr>
		<th>Offense Description</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:OffenseDescription2">
	<tr>
		<th>Offense Description</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Counts">
	<tr>
		<th>Counts</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CaseType">
	<tr>
		<th>Case Type</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Statute">
	<tr>
		<th>Statute</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Violation">
	<tr>
		<th>Violation</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Court">
	<tr>
		<th>Court</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CourtCosts">
	<tr>
		<th>Court Costs</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Fines">
	<tr>
		<th>Fines</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Plea">
	<tr>
		<th>Plea</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:ConvictionPlace">
	<tr>
		<th>Conviction Place</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Sentence">
	<tr>
		<th>Sentence</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Probation">
	<tr>
		<th>Probation</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Disposition">
	<tr>
		<th>Disposition</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:ChargesFiledDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Charges Filed Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:OffenseDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Offense Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:ViolationDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Violation Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:ArrestDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Arrest Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:ConvictionDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Conviction Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:DispositionDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Disposition Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:RegistrationDate">
	<tr>
		<th>Registration Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
</xsl:template>
<xsl:template match="t:RegistrationEndDate">
	<tr>
		<th>Registration End Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Predator">
	<tr>
		<th>Predator</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Victim">
	<tr>
		<th>Victim</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:VictimGender">
	<tr>
		<th>Victim Gender</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:VictimAge">
	<tr>
		<th>Victim Age</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:VictimIsMinor">
	<tr>
		<th>Victim Is Minor</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:PrisonerId">
	<tr>
		<th>Prisoner Id</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Agency">
	<tr>
		<th>Agency</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:AgencyIdLabel">
	<tr>
		<th>Agency Id Label</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:AgencyIdValue">
	<tr>
		<th>Agency Id Value</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:FederalJurisdiction">
	<tr>
		<th>Federal Jurisdiction</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:StateJurisdiction">
	<tr>
		<th>State Jurisdiction</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CountyJurisdiction">
	<tr>
		<th>County Jurisdiction</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CityJurisdiction">
	<tr>
		<th>CityJurisdiction</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>

<xsl:template match="t:ArrestLocation">
	<tr>
		<th>Arrest Location</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Officer">
	<tr>
		<th>Officer</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Facility">
	<tr>
		<th>Facility</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:ChargeClass">
	<tr>
		<th>ChargeClass</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Charges">
	<tr>
		<th>Charges</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CurrentLocation">
	<tr>
		<th>Current Location</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CurrentStatus">
	<tr>
		<th>CurrentStatus</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Division">
	<tr>
		<th>Division</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:CourtAppearance">
	<tr>
		<th>Court Appearance</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Bail">
	<tr>
		<th>Bail</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Bond">
	<tr>
		<th>Bond</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:Fine">
	<tr>
		<th>Fine</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:BookingNumber">
	<tr>
		<th>BookingNumber</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:BookingDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Booking Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:SupervisionType">
	<tr>
		<th>Supervision Type</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:SupervisionDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Supervision Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:ConfineDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Confine Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:ReleaseDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Release Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:Remarks">
	<tr>
		<th>Remmarks</th>
		<td><xsl:value-of select="."/></td>
	</tr>
</xsl:template>
<xsl:template match="t:OffenseType">
	<tr>
		<th>Offense Type</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:NCICNumber">
	<tr>
		<th>NCIC Number</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:OffenseInformation">
	<tr>
		<th>Offense Information</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:WarrantNumber">
	<tr>
		<th>Warrant Number</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:WarrantAgency">
	<tr>
		<th>Warrant Agency</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:WarrantType">
	<tr>
		<th>Warrant Type</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:WarrantEnterDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Warrant Enter Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:WarrantTerminationDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Warrant Termination Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:Precautions">
	<tr>
		<th>Precautions</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:LastSeenLocation">
	<tr>
		<th>Last Seen Location</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:LastSeenDate">
	<xsl:if test="t:Month != ''">
	<tr>
		<th>Last Seen Date</th>
		<td><xsl:value-of select="concat(t:Month,'/',t:Day,'/',t:Year)"/></td>
	</tr>
	</xsl:if>
</xsl:template>
<xsl:template match="t:Restitution">
	<tr>
		<th>Restitution</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:BailReturnable">
	<tr>
		<th>Bail Returnable</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>
<xsl:template match="t:PurgeAmount">
	<tr>
		<th>Purge Amount</th>
		<td><xsl:value-of select="."/></td>
	</tr>	
</xsl:template>


</xsl:stylesheet>