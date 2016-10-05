<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${empty iframe || iframe==false }">
		<link href="styles/tenapp/css/style.css" rel="stylesheet" type="text/css" />
	</c:when>
	<c:otherwise>
		<link href="styles/tenapp/css/styleResponsive.css" rel="stylesheet" type="text/css" />
	</c:otherwise>
</c:choose>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/pepper-grinder/jquery-ui.css" type="text/css" media="screen">