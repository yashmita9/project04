<%@page import="in.co.rays.ctl.ProductCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.ctl.ProductListCtl"%>
<%@page import="in.co.rays.bean.ProductBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
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
	<form action="<%=ORSView.PRODUCT_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.ProductBean"></jsp:useBean>
		<div align="center">
			<h1>
				<font color="navy">Product List</font>
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
			List productList = (List) request.getAttribute("productList");
			
		%>
		
		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">
		
		
		<div align="center">
			<table>
				<tr>
					
					<th class="table-header">Product Name:</th>
					<td><input type="text" placeholder="Enter the Name"
						name="name"></td>
					<th>&nbsp;</th>
					<th class="table-header">Category:</th>
					<th><%=HTMLUtility.getList("productId", DataUtility.getString(bean.getCategory()), productList)%></th>
					<th>&nbsp;</th>
					<td><input type="submit" value="<%=ProductListCtl.OP_SEARCH%>"
						name="operation"></td>
					<td><input type="submit" value="<%=ProductListCtl.OP_RESET%>"
						name="operation"></td>
				</tr>
			</table>
		</div>
<br>
		<table border="1" style="width: 100%;">
			<tr bgcolor="#D3D3D3">
				<th class="table-header"><input type="checkbox" id="selectall"></th>
				<th class="table-header">S.No.</th>
				<th class="table-header">Product Name</th>
				<th class="table-header">Description</th>
				<th class="table-header">Price</th>
				<th class="table-header">Date Of Purchase</th>
				<th class="table-header">Category</th>
				<th class="table-header">Edit</th>

			</tr>
			<%
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (ProductBean)it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getDescription()%></td>
				<td><%=bean.getPrice()%></td>
				<td><%=bean.getDop()%></td>
				<td><%=bean.getCategory()%></td>
				<td><a href="<%=ORSView.PRODUCT_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			
			<%
				}
			%>
		</table>
		
		<br>
		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=ProductListCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=ProductListCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=ProductListCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=ProductListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			
			</tr>
		</table>
	</form>
</body>