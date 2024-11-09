<%@page import="in.co.rays.ctl.AddCollegeCtl"%>
<%@page import="in.co.rays.ctl.BaseCtl"%>
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
<%@include file="Header.jsp"%>
<body>
	<form action="<%=ORSView.ADD_COLLEGE_CTL%>" method="post">
		<div align="center">
			<jsp:useBean id="bean" class="in.co.rays.bean.CollegeBean"
				scope="request"></jsp:useBean>

			<h1>
				<font color="navy"> <%
 	if (bean != null && bean.getId() > 0) {
 %> Update <%
 	} else {
 %> Add <%
 	}
 %> College
				</font>
			</h1>
			<!-- Success and Error Messages -->
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			
			 <!-- Hidden Fields -->
            <input type="hidden" name="id" value="<%= bean.getId() %>">
            <input type="hidden" name="createdBy" value="<%= bean.getCreatedBy() %>">
            <input type="hidden" name="modifiedBy" value="<%= bean.getModifiedBy() %>">
            <input type="hidden" name="createdDatetime" value="<%= DataUtility.getTimestamp(bean.getCreateDateTime()) %>">
            <input type="hidden" name="modifiedDatetime" value="<%= DataUtility.getTimestamp(bean.getModifiedDateTime()) %>">
			
			
			<table>
				<tr>
					<th>Name:</th>
					<td><input type="text" placeholder="Enter Name" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Address:</th>
					<td><input type="text" placeholder="Enter Address"
						name="address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>
				<tr>
					<th>State:</th>
					<td><input type="text" placeholder="Enter State" name="state"
						value="<%=DataUtility.getStringData(bean.getState())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("state", request)%></font></td>
				</tr>
				<tr>
					<th>City:</th>
					<td><input type="text" placeholder="Enter City" name="city"
						value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("city", request)%></font></td>
				</tr>
				<tr>
					<th>Phone No:</th>
					<td><input type="text" placeholder="Enter Phone Number"
						name="phoneNo"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
				</tr>

				<!-- Submit Buttons -->

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=AddCollegeCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=AddCollegeCtl.OP_CANCLE%>"> <%
 	} else {
 %>
					<td><input type="submit" name="operation"
						value="<%=BaseCtl.OP_SAVE%>">&nbsp; <input type="submit"
						name="operation" value="<%=BaseCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>
		</div>


	</form>

</body>
</html>