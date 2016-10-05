<%@ include file="/WEB-INF/jsp/includenew.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title><c:out value="${thePageTitle}" default="Tenant Evaluation"/> | Tenant and Employee Screening</title>

    <link rel="shortcut icon" href="img/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="The Smart Background Screening Company">
    <meta name="author" content="Tenant evaluation">
    <meta name="keywords" content="screening, tenant, employee, evaluation"/>
    <meta name="page-topic" content="The Smart Background Screening Company"/>

<%@ include file="/WEB-INF/jsp/includenew/styles.jsp" %>

    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body id="<c:out value="${theBodyID}" default="pub"/>" role="document">
    <!-- header starts -->
    <%@ include file="/WEB-INF/jsp/includenew/headerBlock.jsp"%>
    <!-- header ends -->