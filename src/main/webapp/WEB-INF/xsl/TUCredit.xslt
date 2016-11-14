<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:t="http://www.transunion.com/namespace">

  <xsl:output  method="html"/>
  
  <xsl:template match="/">
  <style type="text/css">
.recBody {
	font-family: Arial;
	font-size: 10pt;
	font-weight: normal;
	color: black;
}

.recBody H2 {
	font-family: Arial;
	font-size: 12pt;
	font-weight: bold;
	color: black;
	WIDTH: 100%;
	BORDER-BOTTOM: black 1px solid;
}

.recBody TD {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
}

.cSpSm {
	FONT-WEIGHT: normal;
	FONT-SIZE: 7pt;
	COLOR: black;
	FONT-FAMILY: Arial;
	wrap: hard;
}

.recBody thead {
	display: table-header-group;
}

.recBody tfoot {
	display: table-footer-group;
}

eqinfo {
	FONT-SIZE: 7pt;
	FONT-FAMILY: Arial;
}

.eqinfo {
	FONT-SIZE: 7pt;
	FONT-FAMILY: Arial;
}

srchAltRow0 {
	BACKGROUND-COLOR: #FFFFFF;
}

srchAltRow1 {
	BACKGROUND-COLOR: #F5F5F5;
}

.srchAltRow0 {
	BACKGROUND-COLOR: #FFFFFF;
}

.srchAltRow1 {
	BACKGROUND-COLOR: #F5F5F5;
}

.ccreport {
	table-layout: fixed;
	width: 100%;
	background-color: white;
}

.ccreport TD {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	line-height: 12pt;
}

.ccreport .graphheader {
	font-family: Arial;
	font-size: 10pt;
	font-weight: bold;
	color: #FFFFFF;
	background-color: #0099CC;
}

.ccreport .sectiontitle {
	font-family: Arial;
	font-size: 12pt;
	font-weight: bold;
	color: #1A3385;
	padding-bottom: 5px;
}

.ccreport .SubSectiontitle {
	font-family: Arial;
	font-size: 10pt;
	font-weight: bold;
	color: #0099CC;
	padding-bottom: 3px;
}

.ccreport .rownr0 {
	BACKGROUND-COLOR: #FFFFFF;
}

.ccreport .rownr1 {
	BACKGROUND-COLOR: #E5F5FA;
}

.ccreport .rownr00 {
	BACKGROUND-COLOR: #FFFFFF;
}

.ccreport .rownr01 {
	BACKGROUND-COLOR: #E5F5FA;
}

.ccreport .rownr10 {
	BACKGROUND-COLOR: #FFFFFF;
}

.ccreport .rownr11 {
	BACKGROUND-COLOR: #E5F5FA;
}

.ccreport .underl {
	font-weight: bold;
}

.ccreport .leftmargin {
	padding-left: 3px;
	border-left: 3px solid #0099CC;
}

.ccreport .leftrightmargin {
	padding-left: 3px;
	padding-right: 3px;
	border-left: 3px solid #0099CC;
	border-right: 3px solid #0099CC;
}

.ccreport .rightmargin {
	padding-right: 3px;
	border-right: 3px solid #0099CC;
}

.ccreport .bottommargin {
	border-top: 3px solid #0099CC;
	height: 3px;
}

.ccreport .topmargin {
	border-bottom: 3px solid #0099CC;
}

.ccreport .underlleft {
	padding-left: 3px;
	font-weight: bold;
	border-left: 3px solid #0099CC;
}

.ccreport .underlright {
	padding-right: 3px;
	font-weight: bold;
	border-right: 3px solid #0099CC;
}

.ccreport .bolded {
	font-weight: bold;
	font-size: 11pt;
}

.ccreport1 {
	table-layout: fixed;
}

.ccreport1 TD {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	line-height: 14.5pt
}

.ccreport1 .graphheader {
	font-family: Arial;
	font-size: 10pt;
	font-weight: bold;
	color: #FFFFFF;
	background-color: #0099CC;
}

.ccreport1 .profileSubSectiontitle {
	font-family: Arial;
	font-size: 10pt;
	font-weight: bold;
	color: #0099CC;
	padding-bottom: 3px;
}

.ccreport1 .underl {
	font-weight: bold
}

.externalborder {
	border-top: 3px solid #0099CC;
	border-left: 3px solid #0099CC;
	border-right: 3px solid #0099CC;
	border-bottom: 3px solid #0099CC;
}

.ccreport .title4 {
	font-weight: bold;
	font-size: 8pt;
	padding-bottom: 3px;
	padding-top: 7px;
}

.imp {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	text-indent: 0px;
	padding-top: 20px;
}

.ccreport .acc {
	font-family: Arial;
	font-size: 9pt;
	font-weight: bold;
	color: black;
	text-indent: 20px;
	padding-right: 5px;
	padding-left: 0px;
	padding-top: 10px;
}

.ccreport .acc1 {
	font-family: Arial;
	font-size: 8pt;
	font-weight: bold;
	color: black;
	text-indent: 20px;
	padding-right: 5px;
	padding-left: 10px;
}

.ccreport .acc2 {
	font-family: Arial;
	font-size: 8pt;
	font-weight: bold;
	color: black;
	text-indent: 20px;
	padding-right: 5px;
	padding-left: 40px;
}

.ccreport .nonacc {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	text-indent: 20px;
	padding-right: 0px;
	padding-left: 20px;
}

.ccreport .wtab {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	padding-right: 0px;
	text-indent: 20px;
	padding-left: 20px;
}

.ccreport .wtab2 {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	padding-right: 0px;
	text-indent: 20px;
	padding-left: 30px;
}

.ccreport .wtab3 {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	padding-right: 0px;
	text-indent: 20px;
	padding-left: 50px;
}

.ccreport .ftitle {
	font-family: Arial;
	font-size: 9pt;
	font-weight: bold;
	color: black;
	text-indent: 0;
	padding-top: 20px;
}

.ccreport .ftitleM {
	font-family: Arial;
	font-size: 10pt;
	font-weight: bold;
	color: black;
	text-indent: 0;
}

.ccreport .fspace {
	font-family: Arial;
	font-size: 1pt;
	color: white;
}

.ccreport .fheader {
	background-color: #F0F0F0;
}

.ccreport .fpair {
	background-color: #F8F8F8;
}

.ccreport .wtd {
	font-family: Arial;
	font-size: 8pt;
	font-weight: normal;
	color: black;
	padding-left: 20px;
}

.ccreport .requestl {
	color: blue;
}

.srchBorder {
	BORDER-LEFT: 1px dotted #D5D5D5;
}

.srchBorderHorz {
	BORDER-TOP: 1px dotted #D5D5D5;
}

.srchCol {
	FONT-WEIGHT: bold;
	FONT-SIZE: 9pt;
	COLOR: white;
	LINE-HEIGHT: 12pt;
	FONT-FAMILY: Arial;
	BACKGROUND-COLOR: #D5D5D5;
	TEXT-DECORATION: none
}

.cctable .cTradeLines {
	
}

.cTradeLines TD {
	line-height: 10pt;
	FONT-WEIGHT: normal;
	FONT-SIZE: 8pt;
	COLOR: black;
	FONT-FAMILY: Arial
}

.cTradeLines A {
	FONT-WEIGHT: normal;
	FONT-SIZE: 8pt;
	FONT-FAMILY: Arial
}

.cTradeLines A:hover {
	FONT-WEIGHT: normal;
	FONT-SIZE: 8pt;
	FONT-FAMILY: Arial
}
</style>
   
<div class="recBody"> 
	<xsl:apply-templates select="/t:creditBureau/t:product[t:code='07000']/t:subject/t:subjectRecord"/>
	<xsl:apply-templates select="/t:creditBureau/t:product[t:code='07000']/t:subject"/>	
  
  	<!-- No Matching record found then choose following check box-->
	<input type="checkbox" name="matchedOffender" value="No Match"/><label> No Match </label>
</div>
</xsl:template>

<xsl:template match="t:subjectRecord">
  	<table border="0" cellpadding="0" cellspacing="0" width="100%"
		class="ccreport">
		<tr>
			<td><table border="0" cellpadding="0" cellspacing="0"
					width="100%" class="ccreport">
					<tr>
						<td id="TDDecision" width="50%" valign="top"></td>
						<td width="50%" align="right" style="font-size: 8pt;" valign="top"><LABEL
							style="font-family: Verdana; font-size: 9pt; font-weight: bold; color: black;">Transunion Credit Report</LABEL>
						</td>
					</tr>
				</table></td>
		</tr>
		
<!-- Personal Info Section -->
				<tr>
			<td><table class="ccreport" width="100%">
					<tr>
						<td class="Sectiontitle">Personal Information</td>
					</tr>
					<tr>
						<td><table border="0" cellpadding="0" cellspacing="0"
								width="100%" class="ccreport">
								<col width="115"/>
								<col/>
								<col width="85"/>
								<col/>
								<thead>
									<tr>
										<td colspan="4" class="topmargin" height="3">&#160;</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="leftmargin"><b>Name: </b></td>
										<td><xsl:value-of select="t:indicative/t:name[1]/t:person/t:first"/>&#160;<xsl:value-of select="t:indicative/t:name[1]/t:person/t:middle"/>&#160;<xsl:value-of select="t:indicative/t:name[1]/t:person/t:last"/></td>
										<td><b>Birth Year: </b></td>
										<td class="rightmargin"><xsl:value-of select="substring(t:indicative/t:dateOfBirth,1,4)"/>&#160;</td>
									</tr>
									<tr height="10">
										<td colspan="4" class="leftrightmargin">&#160;</td>
									</tr>
<xsl:for-each select="t:indicative/t:address">									
									<tr class="rownr1">
										<td class="leftmargin"><b>Address: </b></td>
										<td class="rightmargin" colspan="3">
											<xsl:value-of select="t:street/t:number"/>&#160;
											<xsl:value-of select="t:street/t:preDirectional"/>&#160;
											<xsl:value-of select="t:street/t:name"/>&#160;
											<xsl:value-of select="t:street/t:type"/>
											&#160;
										</td>
									</tr>
									<tr class="rownr1">
										<td class="leftmargin">&#160;</td>
										<td colspan="3" class="rightmargin">
											<xsl:value-of select="t:location/t:city"/>,&#160;
											<xsl:value-of select="t:location/t:state"/>&#160;
											<xsl:value-of select="t:location/t:zipCode"/>&#160;
										</td>
									</tr>
									<tr class="rownr1">
										<td class="leftmargin"><b>Reported On: </b></td>
										<td class="rightmargin" colspan="3"><xsl:value-of select="t:dateReported"/>&#160;
										</td>
									</tr>
									<tr height="10">
										<td colspan="4" class="leftrightmargin">&#160;</td>
									</tr>
</xsl:for-each>
									<tr class="rownr1">
										<td colspan="2" class="leftmargin"><b>AKA</b></td>
										<td colspan="2" class="rightmargin"><b>SSN</b></td>
									</tr>
<xsl:for-each select="t:indicative/t:name[t:qualifier='alsoKnownAs']">									
									<tr class="rownr0">
										<td colspan="2" class="leftmargin"><xsl:value-of select="t:person/t:unparsed"/>&#160;</td>
										<td colspan="2" class="rightmargin">&#160;</td>
									</tr>
</xsl:for-each>
															</tbody>
															<tfoot>
																<tr>
																	<td colspan="4" class="bottommargin">&#160;</td>
																</tr>
															</tfoot>
														</table></td>
												</tr>
											</table></td>
									</tr>

<!-- Employment Section -->
									<tr>
										<td><table class="ccreport" width="100%">
												<tr>
													<td class="Sectiontitle">Employment Information</td>
												</tr>
												<tr>
													<td><table border="0" cellpadding="0" cellspacing="0"
															width="100%" class="ccreport" style="padding-right: 5px;">
															<col/>
															<col width="180"/>
															<col width="210"/>
															<thead>
																<tr>
																	<td colspan="3" class="topmargin" height="3">&#160;</td>
																</tr>
															</thead>
															<tbody>
<xsl:for-each select="t:indicative/t:employment">															
																<tr class="rownr0">
																	<td class="leftmargin"><b>Company Name: </b><xsl:value-of select="t:employer/t:unparsed"/></td>
																	<td><b>Date Hired: </b><xsl:value-of select="t:dateHired"/></td>
																	<td class="rightmargin"><b>Occupation: </b><xsl:value-of select="t:occupation"/></td>
																</tr>
																<tr class="rownr0">
																	<td class="leftmargin" style="padding-left: 95px;">&#160;
																	</td>
																	<td><b>Date Reported: </b><xsl:value-of select="t:dateOnFileSince"/></td>
																	<td class="rightmargin"><b>Income: </b><xsl:value-of select="t:income/t:amount"/>&#160;<xsl:value-of select="t:income/t:paySchedule"/></td>
																</tr>
																<tr class="rownr0">
																	<td class="leftmargin" style="padding-left: 95px;">&#160;
																	</td>
																	<td><b>Separation Date: </b><xsl:value-of select="t:dateTerminated"/></td>
																	<td class="rightmargin"><b></b>&#160;</td>
																</tr>
</xsl:for-each>																	


															</tbody>
															<tfoot>
																<tr>
																	<td colspan="3" class="bottommargin">&#160;</td>
																</tr>
															</tfoot>
														</table></td>
												</tr>
											</table></td>
									</tr>
									
<!-- Report Summary Section   'subjectRecord/custom/credit/creditSummary'            -->
									<tr>
										<td><table class="ccreport" width="100%">
												<tr>
													<td class="Sectiontitle">Report Summary</td>
												</tr>
												<tr>
													<td><table border="0" cellpadding="0" cellspacing="0"
															width="100%" class="ccreport" style="padding-right: 5px;">
															<thead>
																<tr>
																	<td colspan="2" class="topmargin" height="3">&#160;</td>
																</tr>
															</thead>
															<tbody>
																<tr class="rownr1">
																	<td colspan="2" class="leftrightmargin"
																		style="padding-top: 3px; padding-bottom: 3px;"><table
																			border="0" cellpadding="0" cellspacing="0" class="ccreport"
																			width="100%">
																			<col width="180" align="left" valign="top"/>
																			<col/>
																			<col width="180" valign="top"/>
																			<col/>
																			<col width="200" align="right" valign="top"/>
																			<tr>
																				<td align="center"><table border="0" cellpadding="0"
																						cellspacing="0" class="ccreport">
																						<col width="100"/>
																						<col/>
																						<tr>
																							<td nowrap=""><b>Total # of Trades:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:totalTradeCount"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Current Trades:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:openTradeCount"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Unspecified Trades:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:unspecifiedTradeCount"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Curr Neg Trades:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:negativeTradeCount"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Hist Neg Trades:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:historicalNegativeTradeCount"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Past Due:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:totalAmount/t:pastDue"/></td>
																						</tr>
																					</table></td>
																				<td>&#160;</td>
																				<td align="center"><table border="0" cellpadding="0"
																						cellspacing="0" class="ccreport">
																						<col width="95"/>
																						<col/>
																						<!--  
																						<tr>
																							<td nowrap=""><b>30 Days:</b></td>
																							<td bgcolor="#FFFFFF" align="right">2</td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>60 Days:</b></td>
																							<td bgcolor="#FFFFFF" align="right">0</td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>90+ Days:</b></td>
																							<td bgcolor="#FFFFFF" align="right">0</td>
																						</tr>
																						-->
																						<tr>
																							<td nowrap=""><b>Inquiries:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:totalInquiryCount"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Public Records:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:publicRecordCount"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Collections:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:recordCounts/t:collectionCount"/></td>
																						</tr>
																					</table></td>
																				<td>&#160;</td>
																				<td align="center"><table border="0" cellpadding="0"
																						cellspacing="0" class="ccreport">
																						<col width="120"/>
																						<col/>
																						<tr>
																							<td nowrap=""><b>Accounts Balance:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:totalAmount/t:currentBalance"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Mnthly Payment:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:totalAmount/t:monthlyPayment"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Credit Limit:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:totalAmount/t:creditLimit"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>High Credit:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:totalAmount/t:highCredit"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Total Real Est. Bal.:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:mortgageAmount/t:currentBalance"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Total Rev. Bal.:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:revolvingAmount/t:currentBalance"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Tot. Installment Bal.:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:installmentAmount/t:currentBalance"/></td>
																						</tr>
																						<tr>
																							<td nowrap=""><b>Available %:</b></td>
																							<td bgcolor="#FFFFFF" align="right"><xsl:value-of select="t:custom/t:credit/t:creditSummary/t:revolvingAmount/t:percentAvailableCredit"/></td>
																						</tr>
																					</table></td>
																			</tr>
																		</table></td>
																</tr>
															</tbody>
															<tfoot>
																<tr>
																	<td colspan="2" class="bottommargin">&#160;</td>
																</tr>
															</tfoot>
														</table></td>
												</tr>
											</table></td>
									</tr>

<!-- ScoreCards Section   'subjectRecord/addOnProduct[code='00P02']            -->
									<tr>
										<td><table class="ccreport" width="100%">
												<tr>
													<td class="Sectiontitle">Scorecards</td>
												</tr>
												<xsl:for-each select="t:addOnProduct[t:code='00P02']/t:scoreModel/t:score">
												<tr>
													<td><table border="0" cellpadding="0" cellspacing="0"
															width="100%" class="ccreport" style="padding-right: 5px;">
															<thead>
																<tr>
																	<td colspan="2" class="topmargin" height="3">&#160;</td>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td colspan="2" class="leftrightmargin"
																		style="padding-top: 3px; padding-bottom: 3px;"><table
																			border="0" cellpadding="0" cellspacing="0"
																			style="table-layout: fixed;" class="ccreport">
																			<col width="65"/>
																			<col/>
																			<tr class="rownr1">
																				<td><b>Scorecard: </b></td>
																				<td>FICO Score 4</td>
																			</tr>
																			<tr class="rownr1">
																				<td><b>Score: </b></td>
																				<td><b>
																				<xsl:choose>
																					<xsl:when test="t:results &gt; 0">
																						<xsl:value-of select="t:results"/>
																					</xsl:when>
																					<xsl:otherwise>
																						not scored
																					</xsl:otherwise>
																				</xsl:choose>
																				</b></td>
																			</tr>
																			<tr class="rownr1">
																				<td><b>Reasons: </b></td>
																				<td>
																					&#160;
																					<xsl:for-each select="t:factors/t:factor">
																						(<xsl:value-of select="t:code"/>)<br/>
																					</xsl:for-each>
																				</td>
																			</tr>
																			<tr class="rownr1">
																				<td colspan="2"><br/></td>
																			</tr>
																		</table></td>
																</tr>
															</tbody>
															<tfoot>
																<tr>
																	<td colspan="2" class="bottommargin">&#160;</td>
																</tr>
															</tfoot>
														</table></td>
												</tr>
												</xsl:for-each>
											</table></td>
									</tr>

<!-- Public Records Section   'subjectRecord/custom/credit/publicRecord'            -->
									<tr>
										<td><table class="ccreport" width="100%">
												<tr>
													<td class="Sectiontitle">Public Records</td>
												</tr>
												<tr>
													<td><table class="cTradeLines" border="0" cellpadding="0"
															cellspacing="0" width="100%" style="table-layout: fixed;">
															<COL VALIGN="TOP"/>
															<COL WIDTH="2"/>
															<COL WIDTH="120" VALIGN="TOP"/>
															<COL WIDTH="2"/>
															<COL WIDTH="120" VALIGN="TOP"/>
															<COL WIDTH="2"/>
															<COL WIDTH="100" ALIGN="RIGHT" VALIGN="TOP"/>
															<thead>
																<tr>
																	<td colspan="7" class="topmargin" height="3">&#160;</td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Court Name/Number</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Date Reported</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Member Number</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>Amount</b></u></td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Record Type</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Date Paid</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Plaintiff</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>Assets</b></u></td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Court Type</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>ACC Designator</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Attorney</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>Liability</b></u></td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Public Record
																				Disposition</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Docket</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Industry</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>Orig. Date</b></u></td>
																</tr>
															</thead>
															<tbody>
<xsl:for-each select="t:custom/t:credit/t:publicRecord">															
																<tr class="rownr1" height="10">
																	<td colspan="7" class="leftrightmargin">&#160;</td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin">-&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:dateReported"/></td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:subscriber/t:memberCode"/></td>
																	<td class="rownr0"></td>
																	<td class="rightmargin">-</td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin">-&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:datePaid"/>&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:plaintiff"/>&#160;</td>
																	<td class="rownr0"></td>
																	<td class="rightmargin">-&#160;</td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin"><xsl:value-of select="t:source/t:type"/>&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:ECOADesignator"/>&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:attorney"/>&#160;</td>
																	<td class="rownr0"></td>
																	<td class="rightmargin"><xsl:value-of select="t:liabilities"/>&#160;</td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin">-&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:docketNumber"/></td>
																	<td class="rownr0"></td>
																	<td>-</td>
																	<td class="rownr0"></td>
																	<td class="rightmargin"><xsl:value-of select="t:dateFiled"/>&#160;</td>
																</tr>
																<tr class="rownr1" height="10">
																	<td colspan="7" class="leftrightmargin">&#160;</td>
																</tr>
</xsl:for-each>																
															</tbody>
															<tfoot>
																<tr>
																	<td colspan="7" class="bottommargin">&#160;</td>
																</tr>
															</tfoot>
														</table></td>
												</tr>
											</table></td>
									</tr>

<!-- Collections Section -->
									<tr>
										<td><table class="ccreport" width="100%">
												<tr>
													<td class="Sectiontitle">Collections</td>
												</tr>
												<tr>
													<td><table class="cTradeLines" border="0" cellpadding="0"
															cellspacing="0" width="100%" style="table-layout: fixed;">
															<COL VALIGN="TOP"/>
															<COL WIDTH="2"/>
															<COL WIDTH="120" VALIGN="TOP"/>
															<COL WIDTH="2"/>
															<COL WIDTH="120" VALIGN="TOP"/>
															<COL WIDTH="2"/>
															<COL WIDTH="100" ALIGN="RIGHT" VALIGN="TOP"/>
															<thead>
																<tr>
																	<td colspan="7" class="topmargin" height="3">&#160;</td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Member Number</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Industry Code</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Date Reported</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>Amount</b></u></td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Industry Code</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Account Number</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Date Verified</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>Balance</b></u></td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Status</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>&#160;</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>Date Closed</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>&#160;</b></u></td>
																</tr>
																<tr>
																	<td class="leftmargin"><u><b>Narratives</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>&#160;</b></u></td>
																	<td class="rownr1"></td>
																	<td><u><b>&#160;</b></u></td>
																	<td class="rownr1"></td>
																	<td class="rightmargin"><u><b>&#160;</b></u></td>
																</tr>
															</thead>
															<tbody>
<xsl:for-each select="t:custom/t:credit/t:collection">															
																<tr class="rownr1" height="10">
																	<td colspan="7" class="leftrightmargin">&#160;</td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin"><xsl:value-of select="t:subscriber/t:memberCode"/>&#160;-&#160;<xsl:value-of select="t:subscriber/t:name/t:unparsed"/></td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:subscriber/t:industryCode"/></td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:dateOpened"/></td>
																	<td class="rownr0"></td>
																	<td class="rightmargin"><xsl:value-of select="t:original/t:balance"/></td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin"><xsl:value-of select="t:original/t:creditGrantor/t:unparsed"/></td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:accountNumber"/>&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:dateEffective"/>&#160;</td>
																	<td class="rownr0"></td>
																	<td class="rightmargin"><xsl:value-of select="t:currentBalance"/></td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin">-</td>
																	<td class="rownr0"></td>
																	<td>&#160;</td>
																	<td class="rownr0"></td>
																	<td><xsl:value-of select="t:dateClosed"/></td>
																	<td class="rownr0"></td>
																	<td class="rightmargin">&#160;</td>
																</tr>
																<tr class="rownr1">
																	<td class="leftmargin">-</td>
																	<td class="rownr0"></td>
																	<td>&#160;</td>
																	<td class="rownr0"></td>
																	<td>&#160;</td>
																	<td class="rownr0"></td>
																	<td class="rightmargin">&#160;</td>
																</tr>
																<tr class="rownr1" height="10">
																	<td colspan="7" class="leftrightmargin">&#160;</td>
																</tr>
</xsl:for-each>																
															</tbody>
															<tfoot>
																<tr>
																	<td colspan="7" class="bottommargin">&#160;</td>
																</tr>
															</tfoot>
														</table></td>
												</tr>
											</table></td>
									</tr>

									
<!-- Trade Lines Section   'subjectRecord/custom/credit/trade'            -->
		<tr>
			<td><table class="ccreport" width="100%">
					<tr>
						<td class="Sectiontitle">Trade Lines</td>
					</tr>
					<tr>
						<td><table class="cTradeLines" border="0" cellpadding="0"
								cellspacing="0" width="100%" style="table-layout: fixed;">
								<COL VALIGN="TOP"/>
								<COL WIDTH="2"/>
								<COL WIDTH="68" VALIGN="TOP"/>
								<COL WIDTH="2"/>
								<COL WIDTH="82" VALIGN="TOP"/>
								<COL WIDTH="2"/>
								<COL WIDTH="78" VALIGN="TOP"/>
								<COL WIDTH="2"/>
								<COL WIDTH="88" VALIGN="TOP"/>
								<COL WIDTH="2"/>
								<COL WIDTH="33" ALIGN="CENTER" VALIGN="TOP"/>
								<COL WIDTH="2"/>
								<COL WIDTH="33" ALIGN="CENTER" VALIGN="TOP"/>
								<COL WIDTH="2"/>
								<COL WIDTH="33" ALIGN="CENTER" VALIGN="TOP"/>
								<thead>
									<tr>
										<td colspan="15" class="topmargin" height="3">&#160;</td>
									</tr>
									<tr>
										<td class="leftmargin"><b><u>Firm Name/ID</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Opened</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Credit Limit</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Balance</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>MoPmnt</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>30</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>60</u></b></td>
										<td class="rownr1"></td>
										<td class="rightmargin"><b><u>90</u></b></td>
									</tr>
									<tr>
										<td class="leftmargin"><b><u>Account Number</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Reported</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>High Credit</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Past Due</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>MoRep</u></b></td>
										<td class="rownr1"></td>
										<td colspan="5" align="left" class="rightmargin"><b><u>Pattern</u></b></td>
									</tr>
									<tr>
										<td class="leftmargin"><b><u>KOB</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Acct. Type</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Chargeoff</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Orig. Amt.</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Date Closed</u></b></td>
										<td class="rownr1"></td>
										<td colspan="5" align="left" class="rightmargin"><b>
												&#160; <u></u>
										</b></td>
									</tr>
									<tr>
										<td colspan="3" class="leftmargin"><b><u>Status</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Lst. Paymnt.</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Closed Ind</u></b></td>
										<td class="rownr1"></td>
										<td><b><u>Owner</u></b></td>
										<td class="rownr1"></td>
										<td colspan="5" align="left" class="rightmargin"><b><u>Terms</u></b></td>
									</tr>
									<tr>
										<td colspan="15" class="leftrightmargin"><b><u>Phone
													#</u></b></td>
									</tr>
								</thead>
								<tbody>
<xsl:for-each select="t:custom/t:credit/t:trade">								
									<tr class="rownr1">
										<td class="leftmargin"><b><xsl:value-of select="t:subscriber/t:name/t:unparsed"/>&#160; </b></td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:dateOpened"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:creditLimit"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:currentBalance"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:terms/t:scheduledMonthlyPayment"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:paymentHistory/t:historicalCounters/t:lage30DaysTotal"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:paymentHistory/t:historicalCounters/t:late60DaysTotal"/>&#160;</td>
										<td class="rownr0"></td>
										<td class="rightmargin"><xsl:value-of select="t:paymentHistory/t:historicalCounters/t:late90DaysTotal"/>&#160;</td>
									</tr>
									<tr class="rownr1">
										<td class="leftmargin">-&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:dateEffective"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:highCredit"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:pastDue"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:paymentHistory/t:historicalCounters/t:monthsReviewedCount"/>&#160;</td>
										<td class="rownr0"></td>
										<td colspan="5" align="left" class="rightmargin"><LABEL
											class="cSpSm">-</LABEL>&#160;</td>
									</tr>
									<tr class="rownr1">
										<td class="leftmargin"><xsl:value-of select="t:subscriber/t:industryCode"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:portfolioType"/>&#160;</td>
										<td class="rownr0"></td>
										<td>-</td>
										<td class="rownr0"></td>
										<td>-</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:dateClosed"/>&#160;</td>
										<td class="rownr0"></td>
										<td colspan="5" align="left" class="rightmargin"><LABEL
											class="cSpSm"></LABEL>&#160;</td>
									</tr>
									<tr class="rownr1">
										<td colspan="3" class="leftmargin">&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:mostRecentPayment/t:date"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:closedIndicator"/>&#160;</td>
										<td class="rownr0"></td>
										<td><xsl:value-of select="t:ECOADesignator"/>&#160;</td>
										<td class="rownr0"></td>
										<td colspan="5" align="left" class="rightmargin"><xsl:value-of select="t:terms/t:paymentScheduleMonthCount"/>&#160;&#160;
										</td>
									</tr>
									<tr class="rownr1">
										<td colspan="15" class="leftrightmargin">&#160;</td>
									</tr>
									<tr>
										<td class="leftmargin"
											style="line-height: 3pt; font-size: 2pt; color: #0099CC;"
											align="left" valign="top"><hr size="1px"
												style="line-height: 3pt; font-size: 2pt; color: #0099CC;"
												align="left"/>
										</td>
										<td colspan="14" class="rightmargin"
											style="line-height: 3pt; font-size: 2pt;"><hr size="1px"
												style="line-height: 3pt; font-size: 2pt; color: #0099CC;"
												align="left"/>
										</td>
									</tr>
</xsl:for-each>									
								</tbody>
								<tfoot>
									<tr>
										<td colspan="15" class="bottommargin">&#160;</td>
									</tr>
								</tfoot>
							</table></td>
					</tr>
				</table></td>
		</tr>
		
<!-- Inquiries Section   'subjectRecord/custom/credit/inquiry'            -->
		<tr>
			<td><table class="ccreport" width="100%">
					<tr>
						<td class="Sectiontitle">Inquiries</td>
					</tr>
					<tr>
						<td><table class="cTradeLines" border="0" cellpadding="0"
								cellspacing="0" width="100%" style="table-layout: fixed;">
								<col width="80"/>
								<col/>
								<col width="100"/>
								<col width="110"/>
								<col width="40"/>
								<col width="60"/>
								<col/>
								<col width="82"/>
								<thead>
									<tr>
										<td colspan="8" class="topmargin" height="3">&#160;</td>
									</tr>
									<tr>
										<td nowrap="" class="leftmargin"><b><u> Date </u></b></td>
										<td nowrap=""><b><u> Subscriber Name </u></b></td>
										<td nowrap=""><b><u> Subscriber # </u></b></td>
										<td nowrap=""><b><u> Amount </u></b></td>
										<td nowrap=""><b><u> Mkt. </u></b></td>
										<td nowrap=""><b><u> SubMkt. </u></b></td>
										<td nowrap=""><b><u> KOB </u></b></td>
										<td nowrap="" class="rightmargin"><b><u> Phone #
											</u></b></td>
									</tr>
								</thead>
								<tbody>
<xsl:for-each select="t:custom/t:credit/t:inquiry">								
									<tr class="rownr1">
										<td class="leftmargin"><xsl:value-of select="t:date"/>&#160;</td>
										<td><xsl:value-of select="t:subscriber/t:name/t:unparsed"/>&#160;</td>
										<td><xsl:value-of select="t:subscriber/t:memberCode"/>&#160;</td>
										<td>Unknown amount</td>
										<td></td>
										<td></td>
										<td><xsl:value-of select="t:subscriber/t:industryCode"/>&#160;</td>
										<td class="rightmargin">&#160;</td>
									</tr>
</xsl:for-each>									
								</tbody>
								<tfoot>
									<tr>
										<td colspan="8" class="bottommargin">&#160;</td>
									</tr>
								</tfoot>
							</table></td>
					</tr>
				</table></td>
		</tr>

<!-- Bottom of dynamic sections -->
  	</table>
   
  </xsl:template>
  
  <xsl:template match="t:subject">
  
	<div id="content">
		Choose records to display with final report:<br/>    
		<input type="checkbox" name="matchedOffender">
			<xsl:attribute name="value">
				<xsl:value-of select="t:number"/> 
			</xsl:attribute>
		</input>
		&#160;<xsl:value-of select="t:subjectRecord/t:indicative/t:name/t:person/t:first"/>
		&#160;<xsl:value-of select="t:subjectRecord/t:indicative/t:name/t:person/t:last"/>
		<br/>
	</div>
  </xsl:template>
</xsl:stylesheet>
