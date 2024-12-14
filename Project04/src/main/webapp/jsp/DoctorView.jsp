<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.ctl.DoctorCtl"%>
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
<%@include file="Header.jsp"%>
<body>
<form action="<%=ORSView.DOCTOR_CTL%>" method="post">
		<div align="center">
			<jsp:useBean id="bean" class="in.co.rays.bean.DoctorBean"
				scope="request"></jsp:useBean>

			<h1>
				<font color="navy"> <%
 	if (bean != null && bean.getId() > 0) {
 %> Update <%
 	} else {
 %> Add <%
 	}
 %> Doctor
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
					<th>Date of birth:</th>
					<td><input type="date" placeholder="Enter date" style="width: 95%"
						name="dob"
						value="<%=DataUtility.getStringData(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<th>Mobile No:</th>
					<td><input type="text" placeholder="Enter Mobile no" name="mobileNo"
						value="<%=DataUtility.getStringData(bean.getMobile())%>"></td>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th>Expertise:</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Cardiologist", "Cardiologist");
							map.put("Oncologist", "Oncologist");
							map.put("Dermatologist", "Dermatologist");
							map.put("Neurologist", "Neurologist");
							map.put("Gastroenterologist", "Gastroenterologist");
							map.put("Family medicine", "Family medicine");
							map.put("Anesthesiologist", "Anesthesiologist");
							map.put("Pediatrician", "Pediatrician");
							
						%> <%=HTMLUtility.getList("expertise",bean.getExpertise(), map)%></td>
						<td style="position: fixed;"><font color="red"><%= ServletUtility.getErrorMessage("expertise", request)%></font></td>
				</tr>
				
				<!-- Submit Buttons -->

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=DoctorCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=DoctorCtl.OP_CANCLE%>"> <%
 	} else {
 %>
					<td><input type="submit" name="operation"
						value="<%=DoctorCtl.OP_SAVE%>">&nbsp; <input type="submit"
						name="operation" value="<%=DoctorCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>
		</div>


	</form>

</body>
</html>