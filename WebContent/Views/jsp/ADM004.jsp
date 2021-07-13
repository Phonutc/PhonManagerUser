<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<form
		<c:choose>
			<c:when test = "${userInfor.userId != 0 }">
				action="editUserOK.do"
			</c:when>
			<c:otherwise>
				action="addUserOK.do"
			</c:otherwise>
		</c:choose>
		method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">
						情報確認<br> 入力された情報をＯＫボタンクリックでＤＢへ保存してください
					</div>
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
								<tr>
									<td class="lbl_left">資格:</td>
									<td align="left"><c:choose>
											<c:when test="${userInfor.codeLevel != '' }">
											${fn:escapeXml(userInfor.nameLevel)}
										</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td class="lbl_left">資格交付日:</td>
									<td align="left"><c:choose>
											<c:when test="${userInfor.codeLevel != '' }">
											${fn:replace(userInfor.startDate, '-', '/' ) }
										</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td class="lbl_left">失効日:</td>
									<td align="left"><c:choose>
											<c:when test="${userInfor.codeLevel != '' }">
											${fn:replace(userInfor.endDate, '-', '/')}
										</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td class="lbl_left">点数:</td>
									<td align="left"><c:choose>
											<c:when test="${userInfor.codeLevel != '' }">
											${userInfor.total }
										</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 45px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input type="hidden" name="idSession" value="${idSession}" />
						<input type="hidden" name="userId" value="${userInfor.userId}" />
						<input class="btn" type="submit" value="OK" /></td>
					<td><c:if test="${userInfor.userId == 0 }">
							<input class="btn" type="button" value="戻る"
								onclick="location.href='addUserInput.do?action=back&idSession=${idSession}'" />
						</c:if> <c:if test="${userInfor.userId != 0 }">
							<input class="btn" type="button" value="戻る"
								onclick="location.href='editUserInput.do?action=back&idSession=${idSession}&userId=${userInfor.userId }'" />
						</c:if></td>
				</tr>
			</table>
		</div>
		<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<jsp:include page="Footer.jsp"></jsp:include>
	<!-- End vung footer -->
</body>

</html>