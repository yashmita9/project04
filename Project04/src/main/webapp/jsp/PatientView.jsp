<%@page import="in.co.rays.ctl.PatientCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
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
	<form action="<%=ORSView.PATIENT_CTL%>" method="post">
		<jsp:useBean id="bean" class="in.co.rays.bean.PatientBean"
			scope="request"></jsp:useBean>
		<div align="center">

			<h1>

				<font color="navy"> <%
 	if (bean != null && bean.getId() > 0) {
 %> Update <%
 	} else {
 %> Add <%
 	}
 %> Patient
				</font>
			</h1>

			<h3>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th>Patient Name:</th>
					<td><input type="text" placeholder="Enter Patient Name"
						name="name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Visit Date:</th>
					<td><input type="date" name="visitDate"
						value="<%=DataUtility.getStringData(bean.getVisitDate())%>"
						style="width: 95%"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("visitDate", request)%></font></td>

				</tr>
				<tr>
					<th>Mobile Number:</th>
					<td><input type="text" placeholder="Enter mobile number"
						name="mobileNo"
						value="<%=DataUtility.getStringData(bean.getMobile())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th>Decease:</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Asthma", "Asthma");
							map.put("Anemia", "Anemia");
							map.put("Diabetes", "Diabetes");
							map.put("Heart Attack", "Heart Attack");
							map.put("BP", "BP");
						%><%=HTMLUtility.getList("decease", bean.getDecease(), map)%></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("decease", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td></td>
					<td></td>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=PatientCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=PatientCtl.OP_CANCLE%>">
						<%
							} else {
						%>
					<td><input type="submit" name="operation"
						value="<%=PatientCtl.OP_SAVE%>">&nbsp; <input
						type="submit" name="operation" value="<%=PatientCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>

		</div>
	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>