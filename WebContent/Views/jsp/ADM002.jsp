<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="Views/css/style.css" rel="stylesheet" type="text/css" />
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<jsp:include page="./Header.jsp"></jsp:include>
	<!-- End vung header -->
	<!-- Begin vung dieu kien tim kiem -->
	<form action="listUser.do" method="get" name="mainform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text"
								name="fullName" value="${fn:escapeXml(fullName)}" size="20"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" autofocus /></td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>
							<td align="left" width="80px"><select name="groupId"
								class="wrap_text" style="width: 150px;">
									<option value="0">全て</option>
									<c:forEach items="${listGroup}" var="mstgroup">
										<c:if test="${mstgroup.groupId eq groupId }">
											<option value="${mstgroup.groupId}" selected>${fn:escapeXml(mstgroup.groupName) }</option>
										</c:if>
										<c:if test="${mstgroup.groupId != groupId }">
											<option value="${mstgroup.groupId}">${fn:escapeXml(mstgroup.groupName) }</option>
										</c:if>

									</c:forEach>
							</select></td>
							<td align="left"><input type="hidden" name="action"
								value="search" /> <input class="btn" type="submit" value="検索" />
								<input class="btn" onclick="location.href='addUserInput.do'"
								type="button" value="新規追加" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->
	<c:choose>
		<c:when test="${!empty listUser}">

			<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
				width="80%">
				<c:url value="listUser.do?currentPage=${currentPage}" var="urlSort">
					<c:param name="action" value="sort"></c:param>
					<c:param name="fullName" value="${fullName}"></c:param>
					<c:param name="groupId" value="${groupId}"></c:param>
				</c:url>
				<tr class="tr2">
					<th align="center" width="20px">ID</th>
					<th align="left">氏名 <c:choose>
							<c:when test="${sortValue eq 'DESC' && sortType eq 'full_name'}">
								<a href="${urlSort}&sortType=full_name&sortValue=ASC">△▼</a>
							</c:when>
							<c:otherwise>
								<a href="${urlSort}&sortType=full_name&sortValue=DESC">▲▽</a>
							</c:otherwise>
						</c:choose>
					</th>
					<th align="left">生年月日</th>
					<th align="left">グループ</th>
					<th align="left">メールアドレス</th>
					<th align="left" width="70px">電話番号</th>
					<th align="left">日本語能力 <c:choose>
							<c:when test="${sortValue eq 'DESC' && sortType eq 'code_level'}">
								<a href="${urlSort}&sortType=code_level&sortValue=ASC">△▼</a>
							</c:when>
							<c:otherwise>
								<a href="${urlSort}&sortType=code_level&sortValue=DESC">▲▽</a>
							</c:otherwise>
						</c:choose>
					</th>
					<th align="left">失効日 <c:choose>
							<c:when test="${sortValue eq 'ASC' && sortType eq 'end_date'}">
								<a href="${urlSort}&sortType=end_date&sortValue=DESC">▲▽</a>
							</c:when>
							<c:otherwise>
								<a href="${urlSort}&sortType=end_date&sortValue=ASC">△▼</a>
							</c:otherwise>
						</c:choose>
					</th>
					<th align="left">点数</th>
				</tr>

				<c:forEach items="${listUser}" var="listUser">
					<tr class="wrap_text">
						<td align="right"><a
							href="viewDetailUser.do?userId=${listUser.userId}">${listUser.userId}</a></td>
						<td>${fn:escapeXml(listUser.fullName)}</td>
						<td align="center">${fn:replace(listUser.birthday, '-', '/') }</td>
						<td>${fn:escapeXml(listUser.groupName)}</td>
						<td>${fn:escapeXml(listUser.email)}</td>
						<td>${fn:escapeXml(listUser.tel)}</td>
						<td>${fn:escapeXml(listUser.nameLevel)}</td>
						<td align="center">${fn:replace(listUser.endDate, '-', '/')}</td>
						<td align="right">${listUser.total}</td>
					</tr>
				</c:forEach>

			</table>
			<!-- End vung hien thi danh sach user -->
			<!-- Begin hiện thị vung paging -->
			<table>
				<c:url
					value="listUser.do?sortType=${sortType}&sortValue=${sortValue}"
					var="urlUser">
					<c:param name="action" value="paging"></c:param>
					<c:param name="fullName" value="${fullName}"></c:param>
					<c:param name="groupId" value="${groupId}"></c:param>
				</c:url>
				<tr>
					<td class="lbl_paging"><c:if test="${PRE != null}">
							<a href="${urlUser}&currentPage=${PRE}"> << </a>  &nbsp;
						</c:if> <c:forEach items="${listPaging}" var="listPaging">
							<c:choose>
								<c:when test="${listPaging eq currentPage}">
								${listPaging} &nbsp;
							</c:when>
								<c:otherwise>
									<a href="${urlUser}&currentPage=${listPaging}">${listPaging}</a> &nbsp;
							</c:otherwise>
							</c:choose>

						</c:forEach> <c:if test="${NEXT != null}">
							<a href="${urlUser}&currentPage=${NEXT}"> >> </a>  &nbsp;
						</c:if></td>
				</tr>
			</table>
			<!-- End vung paging -->

		</c:when>
		<c:otherwise>
			<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
				width="80%">
				<c:url value="listUser.do?currentPage=${currentPage}" var="urlSort">
					<c:param name="action" value="sort"></c:param>
					<c:param name="fullName" value="${fullName}"></c:param>
					<c:param name="groupId" value="${groupId}"></c:param>
				</c:url>
				<tr class="tr2">
					<th align="center" width="20px">ID</th>
					<th align="left">氏名 <c:choose>
							<c:when test="${sortValue eq 'DESC' && sortType eq 'full_name'}">
								<a href="${urlSort}&sortType=full_name&sortValue=ASC">△▼</a>
							</c:when>
							<c:otherwise>
								<a href="${urlSort}&sortType=full_name&sortValue=DESC">▲▽</a>
							</c:otherwise>
						</c:choose>
					</th>
					<th align="left">生年月日</th>
					<th align="left">グループ</th>
					<th align="left">メールアドレス</th>
					<th align="left" width="70px">電話番号</th>
					<th align="left">日本語能力 <c:choose>
							<c:when test="${sortValue eq 'DESC' && sortType eq 'code_level'}">
								<a href="${urlSort}&sortType=code_level&sortValue=ASC">△▼</a>
							</c:when>
							<c:otherwise>
								<a href="${urlSort}&sortType=code_level&sortValue=DESC">▲▽</a>
							</c:otherwise>
						</c:choose>
					</th>
					<th align="left">失効日 <c:choose>
							<c:when test="${sortValue eq 'ASC' && sortType eq 'end_date'}">
								<a href="${urlSort}&sortType=end_date&sortValue=DESC">▲▽</a>
							</c:when>
							<c:otherwise>
								<a href="${urlSort}&sortType=end_date&sortValue=ASC">△▼</a>
							</c:otherwise>
						</c:choose>
					</th>
					<th align="left">点数</th>
				</tr>
			</table>
			<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
				width="80%">
				<tr>
					<td>${listError}</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	<!-- Begin vung footer -->
	<jsp:include page="Footer.jsp"></jsp:include>
	<!-- End vung footer -->
</body>
</html>