<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集画面</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
	<style type="text/css">
	body {
		background-color:#F5F5F5;
		}
	div.head{
	margin-left: 5%;
	margin-right: 5%;
	}
	div.color{
color:#FF0000;
}
	</style>
</head>
<body>
	<div class="head">
	<a href="./">ホーム画面</a>
	<a href="management">ユーザー管理画面</a>
	<a href="logout">ログアウト</a>
	<c:out value="【ログイン中：${loginUser.name}さん】"></c:out>
	<br/>
	<h1>ユーザー編集画面</h1>
	<div class="color">
		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="message">
						<li><c:out value="${message}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
		</div>

		<form action="setting" method="post">
			<br />
			<label for="loginId">ログインID</label>
			<input name="loginId" value="${editUser.loginId}" id="loginId" />(半角英数字、6文字以上20文字以下)
			<br />
			<label for="password">パスワード</label>
			<input name="password" type="password" id="password" />(記号含む半角文字、6文字以上20文字以下)
			<br />
			<label for="checkPassword">確認用パスワード</label>
			<input name="checkPassword" type="password" />
			<br />
			<label for="name">氏名</label>
			<input name="name" value="${editUser.name}" id="name" />(10文字以下)
			<br />

			支店名
			<select name="branchId">
				<c:forEach items="${branches}" var="branch">
					<c:if test="${editUser.branchId == branch.id}">
						<option value="${branch.id}" selected>${branch.name}
						</option>
					</c:if>
					<c:if test="${editUser.branchId != branch.id}">
						<option value="${branch.id}">${branch.name}
						</option>
					</c:if>
			    </c:forEach>
			</select>
			<br />

			部署・役職
			<select name="positionId">
				<c:forEach items="${positions}" var="position">
					<c:if test="${editUser.positionId == position.id}">
						<option value="${position.id}" selected>${position.name}
						</option>
					</c:if>
					<c:if test="${editUser.positionId != position.id}">
						<option value="${position.id}">${position.name}
						</option>
					</c:if>
				</c:forEach>
			</select>

			<br />

			<input type="hidden" name="id" value="${editUser.id}" id="id" />
			<br />



			<input type="submit" value="登録" />
			<br />
			<br />
			<br />
			<a href="management">戻る</a>
		</form>

		<div class="copyright">Copyright(c)Ito Kenta</div>
		</div>
</body>
</html>
