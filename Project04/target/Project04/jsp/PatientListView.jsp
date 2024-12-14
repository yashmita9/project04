<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.PatientListCtl"%>
<%@page import="in.co.rays.bean.PatientBean"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
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
	<form action="<%=ORSView.PATIENT_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.PatientBean"
			scope="request"></jsp:useBean>
		<div align="center">
			<h1>
				<font color="navy">Patient List</font>
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
			List patientList =(List) request.getAttribute("patientList");
			if (list.size() != 0) {
		%>
		<div align="center">
			<table>
				<tr>
					
					<th class="table-header">Name:</th>
					<td><input type="text" placeholder="Enter the Name"
						name="name"></td>
					<th>&nbsp;</th>
					<th class="table-header">Decease:</th>
					<th><%=HTMLUtility.getList("patientId", DataUtility.getString(bean.getDecease()), patientList)%></th>
					<th>&nbsp;</th>
					<td><input type="submit" value="<%=PatientListCtl.OP_SEARCH%>"
						name="operation"></td>
					<td><input type="submit" value="<%=PatientListCtl.OP_RESET%>"
						name="operation"></td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>"> <br>
		<table border="1" style="width: 100%;">

			<tr bgcolor="#D3D3D3">
				<th class="table-header"><input type="checkbox" id="selectall"></th>
				<th class="table-header">S.No.</th>
				<th class="table-header">Patient Name</th>
				<th class="table-header">Date of visit</th>
				<th class="table-header">Mobile No.</th>
				<th class="table-header">Decease</th>
				<th class="table-header">Edit</th>

			</tr>
			<%
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (PatientBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getVisitDate()%></td>
				<td><%=bean.getMobile()%></td>
				<td><%=bean.getDecease()%></td>
				<td><a href="<%=ORSView.PATIENT_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
		
		<br>
		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=PatientListCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=PatientListCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=PatientListCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=PatientListCtl.OP_NEXT%>"
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
					value="<%=PatientListCtl.OP_BACK%>"></td>
			</tr>
		</table>
		<%
			}
		%>
	</form>

	<%@include file="Footer.jsp"%>
</body>
</html>