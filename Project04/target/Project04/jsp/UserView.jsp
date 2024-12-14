<%@page import="in.co.rays.ctl.UserCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.UserRegistrationCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%@include file="Header.jsp"%>
<body>
	<%
		List roleList = (List) request.getAttribute("roleList");
	%>
	<form action="<%=ORSView.USER_CTL%>" method="post">
		<div align="center">

			<jsp:useBean id="bean" class="in.co.rays.bean.UserBean"
				scope="request"></jsp:useBean>
			<h1>
				<font color="navy">Add User</font>
			</h1>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>

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
					<th>First Name:</th>
					<td><input type="text" placeholder="Enter First Name"
						name="firstName"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th>Last Name:</th>
					<td><input type="text" placeholder="Enter Last Name"
						name="lastName"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th>Login Id:</th>
					<td><input type="text" placeholder="Enter Email Id"
						name="login"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>

				</tr>
				<tr>
					<th>Password:</th>
					<td><input type="password" placeholder="Enter Password"
						name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th>Confirm Password:</th>
					<td><input type="password"
						placeholder="Enter Confirm Password" name="confirmPassword"
						value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				<tr>
					<th>Birth Date:</th>
					<td><input type="date" placeholder="Select Date of Birth"
						name="dob" style="width: 95%"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<th>Gender:</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("male", "male");
							map.put("female", "female");
						%> <%=HTMLUtility.getList("gender", bean.getGender(), map)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>
				<tr>
				<th>Role:</th>
				<td><%=HTMLUtility.getList("roleId", DataUtility.getStringData(bean.getRoleId()), roleList)%></td>
				<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("roleId", request)%></font></td>
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
					<td><input type="submit" name="operation"
						value="<%=UserCtl.OP_SAVE%>">&nbsp; <input type="submit"
						name="operation" value="<%=UserCtl.OP_RESET%>"></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>