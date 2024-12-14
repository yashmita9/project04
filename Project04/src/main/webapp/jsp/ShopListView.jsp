<%@page import="in.co.rays.bean.ShopBean"%>
<%@page import="in.co.rays.ctl.ShopListCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
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
<body>
	<%@ include file="Header.jsp" %>
<form action="<%=ORSView.SHOP_LIST_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.ShopBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>
				<font color="navy">Shopping Cart List</font>
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
			List shopList = (List) request.getAttribute("shopList");
			if (list.size() != 0) {
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

		<div align="center">
			<table>
				<tr>
				
					<th>Product Name:</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Laptop", "Laptop");
							map.put("Wireless Earbuds", "Wireless Earbuds");
							map.put("Smartphone", "Smartphone");
							map.put("Headphones", "Headphones");
							map.put("Computer", "Computer");
							map.put("Watch", "Watch");
							map.put("Smartwatch", "Smartwatch");
							map.put("EarBuds", "EarBuds");
							map.put("Camera", "Camera");
							map.put("Power Bank", "Power Bank");
							map.put("Gaming Console", "Gaming Console");
						%> <%=HTMLUtility.getList("productName", bean.getProductName(), map)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("productName", request)%></font></td>
				
					<th class="table-header">Name:</th>
					<td><input type="text" placeholder="Enter the name"
						name="name"></td>
					<th>&nbsp;</th>
					<td><input type="submit" value="<%=ShopListCtl.OP_SEARCH%>"
						name="operation"></td>
					<td><input type="submit" value="<%=ShopListCtl.OP_RESET%>"
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
				<th class="table-header">Product Name</th>
				<th class="table-header">Shop Date</th>
				<th class="table-header">Quantity</th>
				<th class="table-header">Edit</th>

			</tr>
			<%
				Iterator it = list.iterator();
					while (it.hasNext()) {
						bean = (ShopBean) it.next();
			%>
			<tr align="center">
				<th><input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>"></th>
				<td><%=index++%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getProductName()%></td>
				<td><%=bean.getShopDate()%></td>
				<td><%=bean.getQuantity()%></td>
				<td><a href="<%=ORSView.SHOP_CTL%>?id=<%=bean.getId()%>">edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
		<br>
		<table style="width: 100%">
			<tr>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=ShopListCtl.OP_PREVIOUS%>"
					<%=(pageNo == 1) ? "disabled" : ""%>></td>
				<td style="width: 30%"><input type="submit" name="operation"
					value="<%=ShopListCtl.OP_NEW%>"></td>
				<td style="width: 25%"><input type="submit" name="operation"
					value="<%=ShopListCtl.OP_DELETE%>"></td>
				<td style="text-align: right;"><input type="submit"
					name="operation" value="<%=ShopListCtl.OP_NEXT%>"
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
					value="<%=ShopListCtl.OP_BACK%>"></td>
			</tr>
		</table>
		<%
			}
		%>
	</form>
	<%@ include file="Footer.jsp" %>
</body>
</html>