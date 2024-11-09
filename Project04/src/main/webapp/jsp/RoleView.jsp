<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%@ include file="Header.jsp"%>
<body>

	<jsp:useBean id="bean" class="in.co.rays.bean.RoleBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.ROLE_CTL%>" method="post">
		<div align="center">
			<h1>
				<font color="navy"> Add Role </font>
			</h1>
		</div>

		<!-- Hidden Fields -->
		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
			type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=bean.getModifiedBy()%>"> <input type="hidden"
			name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

<table>
<tr>
					<th>Name:</th>
					<td><input type="text" placeholder="Enter Role Name" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Description:</th>
					<td><input type="text" placeholder="Enter Short description"
						name="description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>
</table>
	</form>
</body>
</html>