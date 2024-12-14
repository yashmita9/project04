<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.ctl.TaskCtl"%>
<%@page import="in.co.rays.ctl.BaseCtl"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.TASK_CTL%>" method="post">
		<div align="center">
			<jsp:useBean id="bean" class="in.co.rays.bean.TaskBean"
				scope="request"></jsp:useBean>

			<h1>
				<font color="navy"> <%
 	if (bean != null && bean.getId() > 0) {
 %> Update <%
 	} else {
 %> Add <%
 	}
 %> Task
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
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th>Creation Date:</th>
					<td><input type="date" placeholder="Enter Date"
						name="creationDate" style="width: 95%"
						value="<%=DataUtility.getStringData(bean.getCreationDate())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("creationDate", request)%></font></td>
				</tr>
				<tr>
					<th>Task Title:</th>
					<td><input type="text" placeholder="Enter Title"
						name="taskTitle"
						value="<%=DataUtility.getStringData(bean.getTaskTitle())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("taskTitle", request)%></font></td>
				</tr>
				
				<tr>
					<th>Assigned To:</th>
					<td><input type="text" placeholder="Enter Name" name="assign"
						value="<%=DataUtility.getStringData(bean.getAssign())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("assign", request)%></font></td>
				</tr>
				<tr>
					<th>Task Status:</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("New", "New");
							map.put("Started", "Started");
							map.put("On Hold", "On Hold");
							map.put("Completed", "Completed");
							map.put("Close", "Close");
						%> <%=HTMLUtility.getList("taskStatus", bean.getTaskStatus(), map)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("taskStatus", request)%></font></td>
				</tr>
				<tr>
					<th>Details:</th>
					<td><textarea name="details" rows="4" cols="20"
							placeholder="Enter your details"><%=DataUtility.getStringData(bean.getDetails())%></textarea></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("details", request)%></font></td>
				</tr>

				<!-- Submit Buttons -->

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=TaskCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=TaskCtl.OP_CANCLE%>">
						<%
							} else {
						%>
					<td><input type="submit" name="operation"
						value="<%=TaskCtl.OP_SAVE%>">&nbsp; <input type="submit"
						name="operation" value="<%=TaskCtl.OP_RESET%>"></td>
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