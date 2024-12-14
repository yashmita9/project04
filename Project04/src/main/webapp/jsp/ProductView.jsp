<%@page import="in.co.rays.ctl.ProductCtl"%>
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
<body>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.PRODUCT_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.ProductBean"
			scope="request"></jsp:useBean>
		<!-- Hidden Fields -->
		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<div align="center">
			<h1>
				<font color="navy"> <%
 	if (bean != null && bean.getId() > 0) {
 %>Update Product <%
 	} else {
 %> Add Product
				</font>
				<%
					}
				%>
			</h1>

			<!-- Success and Error Messages -->
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>

			<table>
				<tr>
					<th style="text-align: left;">Name</th>
					<td><input type="text" placeholder="Enter Product Name"
						name="name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>

				<tr>
					<th style="text-align: left;">Price</th>
					<td><input type="text" placeholder="Enter Product Price"
						name="price"
						value="<%=DataUtility.getStringData(bean.getPrice())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("price", request)%></font></td>
				</tr>
				<tr>
					<th style="text-align: left;">Date Of Purchase</th>
					<td><input type="date" name="dop"
						value="<%=DataUtility.getStringData(bean.getDop())%>"
						style="width: 98%"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dop", request)%></font></td>
				</tr>
				<tr>
					<th style="text-align: left;">Category</th>
					<td><input type="text" placeholder="Enter Product Category"
						name="category"
						value="<%=DataUtility.getStringData(bean.getCategory())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("category", request)%></font></td>
				</tr>
				<tr>
					<th style="text-align: left;">Description</th>
					<td><textarea name="description" rows="4" cols="20"
							placeholder="Enter your description"><%=DataUtility.getStringData(bean.getDescription())%></textarea></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ProductCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=ProductCtl.OP_CANCLE%>">
						<%
							} else {
						%>
					<td><input type="submit" name="operation"
						value="<%=ProductCtl.OP_SAVE%>">&nbsp; <input
						type="submit" name="operation" value="<%=ProductCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>

			</table>
		</div>

	</form>
	<
</body>
</html>