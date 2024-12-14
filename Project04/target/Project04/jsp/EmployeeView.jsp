<%@page import="in.co.rays.ctl.EmployeeCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
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
	<form action="<%=ORSView.EMPLOYEE_CTL%>" method="post">
		<div align="center"><jsp:useBean id="bean"
				class="in.co.rays.bean.EmployeeBean" scope="request"></jsp:useBean>
			<h1>
				<%
					if (bean != null && bean.getId() > 0) {

					}
				%>
				<font color="navy"> <%
 	if (bean != null && bean.getId() > 0) {
 %>Update Employee <%
 	} else {
 %>Add Employee<%
 	}
 %>
				</font>
			</h1>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th>Employee Name:</th>
					<td><input type="text" placeholder="Enter Employee Name"
						name="name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>

				<tr>
					<th>User Name:</th>
					<td><input type="text" placeholder="Enter Email Id"
						name="userName"
						value="<%=DataUtility.getStringData(bean.getUserName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font></td>

				</tr>
				<tr>
					<th>Password:</th>
					<td><input type="password" placeholder="Enter Password"
						name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<%
				if (bean == null || bean.getId() <= 0) {
					%>
				<tr>
					<th>Confirm Password:</th>
					<td><input type="password"
						placeholder="Enter Confirm Password" name="confirmPassword"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				<%} %>
				<tr>
					<th>Birth Date:</th>
					<td><input type="date" name="dob" style="width: 95%"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<th>Mobile No.:</th>
					<td><input type="text" placeholder="Enter mobile no"
						name="mobileNo"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=EmployeeCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=EmployeeCtl.OP_CANCLE%>"> <%
 	} else {
 %>
					<td><input type="submit" name="operation"
						value="<%=EmployeeCtl.OP_SAVE%>">&nbsp; <input type="submit"
						name="operation" value="<%=EmployeeCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>

		</div>
	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>