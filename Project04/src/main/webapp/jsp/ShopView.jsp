<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.ctl.ShopCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<form action="<%=ORSView.SHOP_CTL%>" method="post">
		<div align="center">
			<jsp:useBean id="bean" class="in.co.rays.bean.ShopBean"
				scope="request"></jsp:useBean>
<% List productList = (List)request.getAttribute("productList"); %>
			<h1>
				<font color="navy"> <%
 	if (bean != null && bean.getId() > 0) {
 %> Update <%
 	} else {
 %> Add <%
 	}
 %> Shopping Cart
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
            <input type="hidden" name="id" value="<%= bean.getId() %>">
            
			
			<table>
				<tr>
					<th>Name:</th>
					<td><input type="text" placeholder="Enter Name" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Shop Date:</th>
					<td><input type="date" placeholder="Enter date" name="shopDate" style="width: 98%"
						value="<%=DataUtility.getStringData(bean.getShopDate())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("shopDate", request)%></font></td>
				</tr>
				<tr>
					<th>Quantity:</th>
					<td><input type="text" placeholder="Enter quantity" name="quantity"
						value="<%=DataUtility.getStringData(bean.getQuantity())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("quantity", request)%></font></td>
				</tr>
				<tr>
				<th>Product Name:</th>
				<td><%=HTMLUtility.getList("productId", DataUtility.getStringData(bean.getProductId()), productList)%></td>
				<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("productId", request)%></font></td>
				</tr>
				
				<!-- Submit Buttons -->

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ShopCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=ShopCtl.OP_CANCLE%>"> <%
 	} else {
 %>
					<td><input type="submit" name="operation"
						value="<%=ShopCtl.OP_SAVE%>">&nbsp; <input type="submit"
						name="operation" value="<%=ShopCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>
		</div>


	</form>
<%@include file="Footer.jsp" %>
</body>
</html>