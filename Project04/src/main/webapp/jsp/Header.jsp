<%@page import="in.co.rays.ctl.ORSView"%>
<%@page import="in.co.rays.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		UserBean user = (UserBean) session.getAttribute("user");
		boolean userLoggedIn = user != null;
		String welcomeMsg = "Hi, ";
		if (userLoggedIn) {
	%>

	<h3>
		<%
			welcomeMsg += user.getFirstName();
		%>
	</h3>
	<%
		} else {
	%>
	<h3>
		<%
			welcomeMsg += "Guest";
		%>
	</h3>
	<%
		}
	%>
	<h1 align="Right">
		<img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.jpg" width="318"
			height="90">
	</h1>
	<hr>
</body>
</html>