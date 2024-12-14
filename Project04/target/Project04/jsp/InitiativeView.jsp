<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.InitiativeCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.INITIATIVE_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.InitiativeBean"
			scope="request"></jsp:useBean>
		<div align="center">
			<h1>
				<font color="navy">Add Initiative</font>
			</h1>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th>Initiative Name:</th>
					<td><input type="text" placeholder="Enter Initiative Name"
						name="name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Type:</th>
					<td><%
							HashMap map = new HashMap();
							map.put("Technology", "Technology");
							map.put("Sustainability", "Sustainability");
							map.put("Social", "Social");
							map.put("Business", "Business");
							map.put("Research", "Research");
						%><%= HTMLUtility.getList("type", bean.getType(), map) %></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("type", request)%></font></td>
				</tr>
				<tr>
					<th>Start Date:</th>
					<td><input type="date" name="startDate"
						value="<%=DataUtility.getStringData(bean.getStartDate())%>" style="width: 95%"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("startDate", request)%></font></td>

				</tr>
				<tr>
					<th>Version Number:</th>
					<td><input type="text" placeholder="Enter version"
						name="versionNo"
						value="<%=DataUtility.getStringData(bean.getVersionNo())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("versionNo", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=InitiativeCtl.OP_SAVE%>">&nbsp;<input type="submit"
						name="operation" value="<%=InitiativeCtl.OP_RESET%>"></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>