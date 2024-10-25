<%@page import="in.co.rays.ctl.LoginCtl"%>
<%@page import="in.co.rays.ctl.UserRegistrationCtl"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%@include file="Header.jsp" %>
<body>
	<form action="<%=ORSView.LOGIN_CTL%>" method="post">
		<div align="center">

			<jsp:useBean id="bean" class="in.co.rays.bean.UserBean"
				scope="request"></jsp:useBean>
			<h1>
				<font color="navy">Login</font>
			</h1>
			
			<div style="height: 15px; margin-bottom: 12px">
				<H4 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H4>
				
			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreateDateTime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

			<table>
				<tr>
					<th align="left">Login Id<span style="color: red">*</span></th>
					<td align="center"><input type="text" placeholder="Enter Email Id"
						name="login"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>

				</tr>
				<tr>
					<th align="left">Password<span style="color: red">*</span></th>
					<td align="center"><input type="password" placeholder="Enter Password"
						name="password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th></th>
					<td></td>
				</tr>
				<tr >
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_IN%>">&nbsp; <input
						type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_UP%>"></td>
				</tr>
				<tr>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th></th>
					<td><a href=""><b>Forget my Password</b></a>&nbsp;</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>