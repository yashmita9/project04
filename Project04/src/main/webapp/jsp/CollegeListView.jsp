<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.bean.CollegeBean"%>
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
<%@include file="Header.jsp"%>
<body>
	<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="get">
		<jsp:useBean id="bean" class="in.co.rays.bean.CollegeBean"
			scope="request"></jsp:useBean>
		<%
			List list = ServletUtility.getList(request);
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			List collegeList =(List) request.getAttribute("collegeList");
		%>
		<div align="center">
			<h1>
				<font color="navy">College List</font>
			</h1>
			<table>
				<tr>
					<th>College Name:</th>
					<td><%=HTMLUtility.getList("collegeId", DataUtility.getStringData(bean.getId()), collegeList)%></td>
					
				</tr>
			</table>
		</div>

		<table border="1" style="width: 100%; border-collapse: collapse;">
			<tr>
				<th><input type="checkbox"></th>
				<th>S.No.</th>
				<th>College Name</th>
				<th>Address</th>
				<th>State</th>
				<th>City</th>
				<th>Phone No</th>
				<th>Edit</th>
			</tr>
			<%
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (CollegeBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox"></th>
				<td><%=bean.getId()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getAddress()%></td>
				<td><%=bean.getState()%></td>
				<td><%=bean.getCity()%></td>
				<td><%=bean.getPhoneNo()%></td>
				<td><a href="">edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
	</form>
</body>
</html>