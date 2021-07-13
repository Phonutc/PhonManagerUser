<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
</head>
<body>
	<!-- Begin vùng header -->
	<jsp:include page="Header.jsp"></jsp:include>
	<!-- End vùng header -->
	<!-- Begin vung input-->
	<form action="listUser.do" method="get" name="inputform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" colspan="2">
					<div style="height: 50px"></div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2"><font color="red">
						${error }</font></td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<div style="height: 70px"></div>
				</td>
			</tr>
		</table>
	</form>

	<!-- Begin vùng Footer -->
	<jsp:include page="Footer.jsp"></jsp:include>
	<!-- End vùng Footer -->
</body>
</html>