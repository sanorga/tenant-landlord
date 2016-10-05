<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

 <head>
	<link rel="icon" href="images/tenant_favicon.ico" type="image/x-icon"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="keywords" content="screening, tenant, employee, evaluation"/>
	<meta name="description" content="The Smart Background Screening Company"/>
	<meta name="page-topic" content="The Smart Background Screening Company"/>

	<title>Tenant, Employee Screening from Tenant Evaluation</title>

	<!-- JavaScript file for jQuery  -->
	<link rel="stylesheet" href="styles/jquery-ui/ui-lightness/jquery-ui.min.css" />
	<link rel="stylesheet" href="styles/ui.jqgrid.css"></link>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/jquery/jquery-ui.min.js"></script>
	<script src="js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/js/i18n/grid.locale-en.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jqgrid/4.6.0/js/jquery.jqGrid.min.js"></script>
	<%@ include file="/WEB-INF/jsp/include/blockenter.jsp" %>
	
	<!--  Pagination script and css start here -->
    <script type="text/javascript" src="js/jquery/jquery.tablesorter.min.js"></script>
    <script type="text/javascript" src="js/jquery/jquery.tablesorter.pager.js"></script>
	<!--  Pagination script and css end here -->

	<!-- JavaScript file for jQuery J breadcrumbs-->
	<script type="text/javascript" src="js/jquery/jquery.jBreadCrumb.1.1.js"></script>
	
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="lib/fancybox/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="lib/fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />

	<!-- Add jquery validator -->
	<script type="text/javascript" src="js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="js/jquery/additional-methods-new.min.js"></script>
	
	<!-- Block the Back button Script -->
	<script type="text/javascript">
		history.forward();
	</script>

    <!-- Pagination css -->
	<link rel="stylesheet" media="all" href="styles/tablesorter.css" />
	<!-- New CSS Starts here -->
	<link rel="stylesheet" type="text/css" href="styles/commoncss/commoncss.css" />
	<!-- New CSS Ends here -->

	<!-- jquery breadcrumbs starts -->
	<link rel="stylesheet" media="all" href="styles/breadcrumbs/BreadCrumb.css" />
	<!-- jquery breadcrumbs ends -->

     <script type="text/javascript">
         jQuery(document).ready(function()
         {
              jQuery("#breadCrumb0").jBreadCrumb();
         })
     </script>
     
     <!-- common scripts -->
	<script src="js/common.js" type="text/javascript"></script>
 </head>

<body>
 <div id="body">
	<!-- header starts -->
	<%@ include file="/WEB-INF/jsp/include/headerBlock.jsp"%>
	<!-- header ends -->
