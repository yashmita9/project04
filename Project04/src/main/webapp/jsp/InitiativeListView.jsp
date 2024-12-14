<%@page import="in.co.rays.ctl.InitiativeListCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.InitiativeCtl"%>
<%@page import="in.co.rays.bean.InitiativeBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.util.ServletUtility"%>
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
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.INITIATIVE_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.InitiativeBean"
			scope="request"></jsp:useBean>
		<%
			List list = ServletUtility.getList(request);
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			List initiativeList = (List) request.getAttribute("initiativeList");
		%>
		<div align="center">
			<h1>
				<font color="navy">Initiative List</font>
			</h1>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>

			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<div align="center">
				<table>
					<tr>
						<th class="table-header">Initiative Name:</th>
						<td><input type="text" name="name"
							placeholder="Enter the Initiative name"></td>
						<th>&nbsp;</th>
						<th class="table-header">Date:</th>
						<td><input type="date" name="startDate"
							placeholder="Enter Start Date"></td>
						<th>&nbsp;</th>
						<th class="table-header">Type:</th>
						<th><%=HTMLUtility.getList("initiativeId", DataUtility.getString(bean.getType()), initiativeList)%></th>
						<th>&nbsp;</th>

						<td><input type="submit"
							value="<%=InitiativeListCtl.OP_SEARCH%>" name="operation"></td>
						<td><input type="submit"
							value="<%=InitiativeListCtl.OP_RESET%>" name="operation"></td>
					</tr>
				</table>
			</div>

			<table border="1" style="width: 100%">
				<tr bgcolor="#D3D3D3">
					<th class="table-header"><input type="checkbox" id="selectall"></th>
					<th class="table-header">S.No</th>
					<th class="table-header">Initiative Name</th>
					<th class="table-header">Type</th>
					<th class="table-header">Start Date</th>
					<th class="table-header">Version Number</th>
					<th class="table-header">Edit</th>
				</tr>
				<%
					Iterator it = list.iterator();
					while (it.hasNext()) {
						bean = (InitiativeBean) it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="case" name="ids"></td>
					<td><%=bean.getId()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getType()%></td>
					<td><%=bean.getStartDate()%></td>
					<td><%=bean.getVersionNo()%></td>
					<td><a href="">edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<br>
			<table style="width: 100%">
				<tr>
					<td style="width: 30%"><input type="submit" name="operation"
						value="<%=InitiativeCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<td style="width: 30%"><input type="submit" name="operation"
						value="<%=InitiativeCtl.OP_NEW%>"></td>
					<td style="width: 30%"><input type="submit" name="operation"
						value="<%=InitiativeCtl.OP_DELETE%>"></td>
					<td style="text-align: right;"><input type="submit"
						name="operation" value="<%=InitiativeCtl.OP_NEXT%>"></td>
				</tr>
			</table>
		</div>


	</form>
</body>
</html>