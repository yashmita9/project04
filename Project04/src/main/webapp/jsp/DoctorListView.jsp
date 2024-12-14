<%@page import="in.co.rays.bean.DoctorBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.ctl.DoctorListCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.List"%>
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
<style>
.table-header {
	font-size: 18px;
}
</style>
<%@include file="Header.jsp"%>
<body>
<form action="<%=ORSView.DOCTOR_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.DoctorBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>
				<font color="navy">Doctor List</font>
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
			List doctorList = (List) request.getAttribute("doctorList");
			if (list.size() != 0) {
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

		<div align="center">
			<table>
				<tr>
					<th class="table-header">Expertise</th>
					<th><%=HTMLUtility.getList("doctorId", DataUtility.getString(bean.getExpertise()), doctorList)%></th>
					<th>&nbsp;</th>
					<th class="table-header">Doctor Name:</th>
					<td><input type="text" placeholder="Enter the name"
						name="name"></td>
					<th>&nbsp;</th>
					<td><input type="submit" value="<%=DoctorListCtl.OP_SEARCH%>"
						name="operation"></td>
					<td><input type="submit" value="<%=DoctorListCtl.OP_RESET%>"
						name="operation"></td>
				</tr>
			</table>
		</div>

		<br>
		<table border="1" style="width: 100%;">

			<tr bgcolor="#D3D3D3">
				<th class="table-header"><input type="checkbox" id="selectall"></th>
				<th class="table-header">S.No.</th>
				<th class="table-header">Name</th>
				<th class="table-header">Birth Date</th>
				<th class="table-header">Mobile No</th>
				<th class="table-header">Expertise</th>
				<th class="table-header">Edit</th>

			</tr>
			<%
				Iterator it = list.iterator();
					while (it.hasNext()) {
						bean = (DoctorBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getDob()%></td>
				<td><%=bean.getMobile()%></td>
				<td><%=bean.getExpertise()%></td>
				<td><a href="<%=ORSView.DOCTOR_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
		<br>
		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=DoctorListCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=DoctorListCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=DoctorListCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=DoctorListCtl.OP_NEXT%>"
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
					value="<%=DoctorListCtl.OP_BACK%>"></td>
			</tr>
		</table>
		<%
			}
		%>
	</form>
</body>
</html>