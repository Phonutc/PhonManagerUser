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
			<c:when test = "${userInfor.userId == 0 }">
				action = "addUserValidate.do"
			</c:when>
			<c:otherwise>
				action="editUserValidate.do" 
			</c:otherwise>
		</c:choose>
		method="post" name="inputform">
		<input class="btn" type="hidden" name="action" value="confirm" /> <input
			type="hidden" name="userId" value="${userInfor.userId}" />
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>

			<c:forEach items="${listError}" var="err">
				<tr>
					<td class="errMsg" colspan="2"><div
							style="padding-left: 120px">${err}</div></td>
				</tr>
			</c:forEach>

			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<td align="left"><c:if test="${userInfor.userId != 0 }">
										<input class="txBox" type="text" name="loginName"
											value="${fn:escapeXml(userInfor.loginName)}" size="15"
											onfocus="this.style.borderColor='#0066ff';"
											onblur="this.style.borderColor='#aaaaaa';"
											readonly="readonly" />
									</c:if> <c:if test="${userInfor.userId == 0 }">
										<input class="txBox" type="text" name="loginName"
											value="${fn:escapeXml(userInfor.loginName)}" size="15"
											onfocus="this.style.borderColor='#0066ff';"
											onblur="this.style.borderColor='#aaaaaa';"
											autofocus />
									</c:if></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="groupId" class="wrap_text"
									style="width: 150px;">
										<option value="0">選択してください</option>
										<c:forEach items="${listGroup}" var="mstgroup">
											<c:set var="groupName" value="${mstgroup.groupName}"></c:set>
											<option value="${mstgroup.groupId}"
												<c:if test="${mstgroup.groupId == userInfor.groupId}"> selected</c:if>>${fn:escapeXml(mstgroup.groupName) }</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullName" value="${fn:escapeXml(userInfor.fullName) }"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullNameKana"
									value="${fn:escapeXml(userInfor.fullNameKana) }" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><c:set var="birthday"
										value="${fn:split(userInfor.birthday, '-')}" /> <select
									name="yearBirthday">
										<c:forEach items="${listYearBirthday}" var="listYearBirthday">
											<c:choose>
												<c:when test="${not empty userInfor.birthday }">
													<option value="${listYearBirthday}"
														<c:if test="${listYearBirthday eq birthday[0]}"> selected</c:if>>${fn:escapeXml(listYearBirthday) }</option>
												</c:when>
												<c:otherwise>
													<option value="${listYearBirthday}"
														<c:if test="${listYearBirthday eq year}"> selected</c:if>>${fn:escapeXml(listYearBirthday) }</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
								</select>年 <select name="monthBirthday">
										<c:forEach items="${listMonthBirthDay}"
											var="listMonthBirthDay">
											<c:choose>
												<c:when test="${not empty userInfor.birthday }">
													<option value="${listMonthBirthDay}"
														<c:if test="${listMonthBirthDay eq birthday[1]}"> selected</c:if>>${listMonthBirthDay}</option>
												</c:when>
												<c:otherwise>
													<option value="${listMonthBirthDay}"
														<c:if test="${listMonthBirthDay eq month}"> selected</c:if>>${listMonthBirthDay}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
								</select>月 <select name="dayBirthday">
										<c:forEach items="${listDayBirthDay}" var="listDayBirthDay">
											<c:choose>
												<c:when test="${not empty userInfor.birthday}">
													<option value="${listDayBirthDay}"
														<c:if test="${listDayBirthDay eq birthday[2]}"> selected</c:if>>${listDayBirthDay}</option>
												</c:when>
												<c:otherwise>
													<option value="${listDayBirthDay}"
														<c:if test="${listDayBirthDay eq day}"> selected</c:if>>${listDayBirthDay}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="${fn:escapeXml(userInfor.email)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${fn:escapeXml(userInfor.tel)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>

							<tr>
								<c:if test="${userInfor.userId != 0 }">
									<td class="lbl_left" style="display: none"><font
										color="red">*</font> パスワード:</td>
									<td align="left"><input class="txBox" type="password"
										name="password" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';"
										style="display: none;" /></td>
								</c:if>
								<c:if test="${userInfor.userId == 0}">
									<td class="lbl_left"><font color="red">*</font> パスワード:</td>
									<td align="left"><input class="txBox" type="password"
										name="password" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</c:if>
							</tr>
							<tr>
								<c:if test="${userInfor.userId != 0}">
									<td class="lbl_left" style="display: none">パスワード（確認）:</td>
									<td align="left"><input class="txBox" type="password"
										name="passwordConfirm" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';"
										style="display: none;" /></td>
								</c:if>
								<c:if test="${userInfor.userId == 0}">
									<td class="lbl_left">パスワード（確認）:</td>
									<td align="left"><input class="txBox" type="password"
										name="passwordConfirm" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</c:if>
							</tr>
							<tr>
								<th align="left" colspan="2"><a href="#"
									onclick="showHide()">日本語能力</a></th>
							</tr>
						</table>
						<table border="0" width="100%" id="levelJapan" cellpadding="4"
							class="tbl_input" cellspacing="0" style="display: none;">
							<tr>
								<td class="lbl_left">資格:</td>
								<td align="left"><select name="codeLevel" class="wrap_text"
									style="width: 172px;">
										<option value="">選択してください</option>
										<c:forEach items="${listJapan}" var="mstjapan">
											<option value="${mstjapan.codeLevel}"
												<c:if test="${mstjapan.codeLevel eq userInfor.codeLevel }"> selected</c:if>>${fn:escapeXml(mstjapan.nameLevel) }</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left"><c:set var="startDate"
										value="${fn:split(userInfor.startDate, '-')}"></c:set> <select
									name="yearStartDate">
										<c:forEach items="${listYearStartDate}"
											var="listYearStartDate">
											<c:choose>
												<c:when test="${not empty userInfor.startDate }">
													<option value="${listYearStartDate}"
														<c:if test="${listYearStartDate eq startDate[0] }"> selected</c:if>>${listYearStartDate}</option>
												</c:when>
												<c:otherwise>
													<option value="${listYearStartDate}"
														<c:if test="${listYearStartDate eq year }"> selected</c:if>>${listYearStartDate}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select>年 <select name="monthStartDate">
										<c:forEach items="${listMonthStartDate}"
											var="listMonthStartDate">
											<c:choose>
												<c:when test="${not empty userInfor.startDate }">
													<option value="${listMonthStartDate}"
														<c:if test="${listMonthStartDate eq startDate[1] }"> selected</c:if>>${listMonthStartDate}</option>
												</c:when>
												<c:otherwise>
													<option value="${listMonthStartDate}"
														<c:if test="${listMonthStartDate eq month }"> selected</c:if>>${listMonthStartDate}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
								</select>月 <select name="dayStartDate">
										<c:forEach items="${listDayStartDate}" var="listDayStartDate">
											<c:choose>
												<c:when test="${not empty userInfor.startDate }">
													<option value="${listDayStartDate}"
														<c:if test="${listDayStartDate eq startDate[2] }"> selected</c:if>>${listDayStartDate}</option>
												</c:when>
												<c:otherwise>
													<option value="${listDayStartDate}"
														<c:if test="${listDayStartDate eq day }"> selected</c:if>>${listDayStartDate}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><c:set var="endDate"
										value="${fn:split(userInfor.endDate, '-') }"></c:set><select
									name="yearEndDate">
										<c:forEach items="${listYearEndDate}" var="listYearEndDate">
											<c:choose>
												<c:when test="${not empty userInfor.endDate }">
													<option value="${listYearEndDate}"
														<c:if test="${listYearEndDate eq endDate[0] }"> selected</c:if>>${listYearEndDate}</option>
												</c:when>
												<c:otherwise>
													<option value="${listYearEndDate}"
														<c:if test="${listYearEndDate eq year }"> selected</c:if>>${listYearEndDate}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select>年 <select name="monthEndDate">
										<c:forEach items="${listMonthEndDate}" var="listMonthEndDate">
											<c:choose>
												<c:when test="${not empty userInfor.endDate }">
													<option value="${listMonthEndDate}"
														<c:if test="${listMonthEndDate eq endDate[1] }"> selected</c:if>>${listMonthEndDate}</option>
												</c:when>
												<c:otherwise>
													<option value="${listMonthEndDate}"
														<c:if test="${listMonthEndDate eq month }"> selected</c:if>>${listMonthEndDate}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
								</select>月 <select name="dayEndDate">
										<c:forEach items="${listDayEndDate}" var="listDayEndDate">
											<c:choose>
												<c:when test="${not empty userInfor.endDate }">
													<option value="${listDayEndDate}"
														<c:if test="${listDayEndDate eq endDate[2] }"> selected</c:if>>${listDayEndDate}</option>
												</c:when>
												<c:otherwise>
													<option value="${listDayEndDate}"
														<c:if test="${listDayEndDate eq day }"> selected</c:if>>${listDayEndDate}</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><input class="txBox" type="text"
									name="total" value="${fn:escapeXml(userInfor.total)}" size="5"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
						</table>
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
					<td><input class="btn" type="submit" value="確認" /></td>
					<td><c:if test="${userInfor.userId == 0 }">
							<input class="btn"
								onclick="location.href='listUser.do?action=back'" type="button"
								value="戻る" />
						</c:if> <c:if test="${userInfor.userId != 0 }">
							<input class="btn"
								onclick="location.href='viewDetailUser.do?userId=${userInfor.userId}'"
								type="button" value="戻る" />
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