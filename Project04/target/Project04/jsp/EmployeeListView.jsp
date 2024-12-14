<%@page import="in.co.rays.ctl.EmployeeListCtl"%>
<%@page import="in.co.rays.ctl.EmployeeCtl"%>
<%@page import="in.co.rays.bean.EmployeeBean"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
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
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.EMPLOYEE_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.EmployeeBean"
			scope="request"></jsp:useBean>
		<div align="center">
			<h1>
				<font color="navy">Employee List</font>
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
			if (list.size() != 0) {
		%>
		
		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">
		<br>
		<table border="1" style="width: 100%;">

			<tr bgcolor="#D3D3D3">
				<th class="table-header"><input type="checkbox" id="selectall"></th>
				<th class="table-header">S.No.</th>
				<th class="table-header">Employee Name</th>
				<th class="table-header">User Name</th>
				<th class="table-header">Date of Birth</th>
				<th class="table-header">Mobile Number</th>
				<th class="table-header">Edit</th>

			</tr>
			<%
				Iterator it = list.iterator();
					while (it.hasNext()) {
						bean = (EmployeeBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getUserName()%></td>
				<td><%=bean.getDob()%></td>
				<td><%=bean.getMobileNo()%></td>
				<td><a href="<%=ORSView.EMPLOYEE_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
				}
			%>
			</table>
			<br>
		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=EmployeeListCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=EmployeeListCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=EmployeeListCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=EmployeeListCtl.OP_NEXT%>"
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
					value="<%=EmployeeListCtl.OP_BACK%>"></td>
			</tr>
		</table>
		<%
			}
		%>
	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>