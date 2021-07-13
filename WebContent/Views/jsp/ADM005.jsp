<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="Views/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="Views/js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="Header.jsp"></jsp:include>
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form action="editUserInput.do" method="get" name="inputform">
		<input name="userId" type="hidden" value="${userInfor.userId}"></input>
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">情報確認</div>
					<div style="padding-left: 100px;">&nbsp;</div>
				</th>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="1" width="70%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr class="wrap_text">
								<td class="lbl_left">アカウント名:</td>
								<td align="left">${fn:escapeXml(userInfor.loginName) }</td>
							</tr>
							<tr class="wrap_text">
								<td class="lbl_left">グループ:</td>
								<td align="left">${fn:escapeXml(userInfor.groupName) }</td>
							</tr>
							<tr class="wrap_text">
								<td class="lbl_left">氏名:</td>
								<td align="left">${fn:escapeXml(userInfor.fullName) }</td>
							</tr>
							<tr class="wrap_text">
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left">${fn:escapeXml(userInfor.fullNameKana) }</td>
							</tr>
							<tr>
								<td class="lbl_left">生年月日:</td>
								<td align="left">${fn:replace(userInfor.birthday, '-', '/') }</td>
							</tr>
							<tr class="wrap_text">
								<td class="lbl_left">メールアドレス:</td>
								<td align="left">${fn:escapeXml(userInfor.email) }</td>
							</tr>
							<tr>
								<td class="lbl_left">電話番号:</td>
								<td align="left">${fn:escapeXml(userInfor.tel) }</td>
							</tr>
							<tr>
								<th colspan="2"><a href="#" onclick="showHide()">日本語能力</a></th>
							</tr>
						</table>
						<div id="levelJapan" style="display: none;">
							<table border="1" width="70%" class="tbl_input" cellpadding="4"
								cellspacing="0">
								<tr class="wrap_text">
									<td class="lbl_left">資格:</td>
									<td align="left">${fn:escapeXml(userInfor.nameLevel)}</td>
								</tr>
								<tr>
									<td class="lbl_left">資格交付日:</td>
									<td align="left">${fn:replace(userInfor.startDate, '-', '/' ) }</td>
								</tr>
								<tr>
									<td class="lbl_left">失効日:</td>
									<td align="left">${fn:replace(userInfor.endDate, '-', '/' ) }</td>
								</tr>
								<tr>
									<td class="lbl_left">点数:</td>
									<td align="left">${userInfor.total }</td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 100px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input type="hidden" name="action" value="default" /> <input
						class="btn" type="submit" value="編集" /></td>
					<td><input class="btn" type="button" value="削除"
						onclick="confirmDelete(${userInfor.userId }, '${message }');" /></td>
					<td><input class="btn" type="button" value="戻る"
						onclick="location.href='listUser.do?action=back'" /></td>
				</tr>
			</table>
		</div>
		<!-- End vung button -->
	</form>
	<form id="formDelete" action="deleteUser.do" method="post">
		<input type="hidden" name="userId" value="${userInfor.userId }" />
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<div class="lbl_footer">
		<br> <br> <br> <br> Copyright © 2010
		ルビナソフトウエア株式会社. All rights reserved.
	</div>
	<!-- End vung footer -->
</body>
</html>