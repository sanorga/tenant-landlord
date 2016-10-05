<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tenant Evaluation Portal Logout/title>
</head>
<body>
	<% session.invalidate(); %>
	You have successfully logged out.
	
	<a href="${pageContext.request.contextPath}/login">Login</a>
</body>
</html>