<%@page import="in.co.rays.bean.TaskBean"%>
<%@page import="in.co.rays.ctl.TaskListCtl"%>
<%@page import="in.co.rays.ctl.TaskListCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.bean.CollegeBean"%>
<%@page import="in.co.rays.model.RoleModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
.table-header {
	font-size: 18px;
}
</style>
<%@include file="Header.jsp"%>
<body>
	<form action="<%=ORSView.TASK_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.TaskBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>
				<font color="navy">Task List</font>
			</h1>
		</div>


		<div align="center" style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			List list = ServletUtility.getList(request);
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			int index = ((pageNo - 1) * pageSize) + 1;
			List taskList = (List) request.getAttribute("taskList");
			if (list.size() != 0) {
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

		<div align="center">
			<table>
				<tr>
					<th class="table-header">Assign Name:</th>
					<th><%=HTMLUtility.getList("taskId", DataUtility.getString(bean.getAssign()), taskList)%></th>
					<th>&nbsp;</th>
					<th class="table-header">Task Status:</th>
					<th><%=HTMLUtility.getList2("taskId", DataUtility.getString(bean.getTaskStatus()), taskList)%></th>
					<th>&nbsp;</th>
					<td><input type="submit" value="<%=TaskListCtl.OP_SEARCH%>"
						name="operation"></td>
					<td><input type="submit" value="<%=TaskListCtl.OP_RESET%>"
						name="operation"></td>
				</tr>
			</table>
		</div>

		<br>
		<table border="1" style="width: 100%;">

			<tr bgcolor="#D3D3D3">
				<th class="table-header"><input type="checkbox" id="selectall"></th>
				<th class="table-header">S.No.</th>
				<th class="table-header">Creation Date</th>
				<th class="table-header">Task Title</th>
				<th class="table-header">Details</th>
				<th class="table-header">Assigned to</th>
				<th class="table-header">Task Satus</th>
				<th class="table-header">Edit</th>

			</tr>
			<%
				Iterator it = list.iterator();
					while (it.hasNext()) {
						bean = (TaskBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getCreationDate()%></td>
				<td><%=bean.getTaskTitle()%></td>
				<td><%=bean.getDetails()%></td>
				<td><%=bean.getAssign()%></td>
				<td><%=bean.getTaskStatus()%></td>
				<td><a href="<%=ORSView.TASK_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
		<br>
		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=TaskListCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=TaskListCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=TaskListCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=TaskListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			
			</tr>
		</table>
		<%
			}
			if (list.size() == 0) {
		%>
		<br>
		<table align="center">
			<tr>
				<td ><input type="submit" name="operation"
					value="<%=TaskListCtl.OP_BACK%>"></td>
			</tr>
		</table>
		<%
			}
		%>
	</form>
</body>
</html>