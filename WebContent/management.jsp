<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>
<script type="text/javascript">
function check(){
	if(window.confirm('実行してよろしいですか？')){ // 確認ダイアログを表示
		return true; // 「OK」時は送信を実行
	}
	else{ // 「キャンセル」時の処理
		window.alert('キャンセルされました'); // 警告ダイアログを表示
		return false; // 送信を中止
	}
}
</script>
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
	<a href = "signup">ユーザー新規登録画面</a>
	<a href="logout">ログアウト</a>
	<c:out value="【ログイン中：${loginUser.name}さん】"></c:out>
	<br/>
	<h1>ユーザー管理画面</h1>
	<br/>
	<div class="color">
	<c:if test="${ not empty errorMessage }">
		<c:out value="${errorMessage}" />
		<c:remove var="errorMessage" scope="session" />
	</c:if>
	</div>
	<br/>
	<table  border="1">
		<tr>
		<th>ログインID</th><th>氏名</th><th>支店名</th><th>部署・役職</th><th>編集</th><th>復活・停止</th>
		</tr>
		<c:forEach items="${users}" var="user">
		<tr>
			<td><c:out value="${user.loginId}" /></td>
			<td><c:out value="${user.name}" /></td>
			<td>
			<c:forEach items="${branches}" var="branch">
				<c:if test="${user.branchId == branch.id}" >
					<c:out value="${branch.name}" />
				</c:if>
			</c:forEach>
			</td>
			<td>
			<c:forEach items="${positions}" var="position">
				<c:if test="${user.positionId == position.id}" >
					<c:out value="${position.name}" />
				</c:if>
			</c:forEach>
			</td>
			<td>
			<a href = "setting?id=${user.id}">編集</a>
			</td>
			<td>
			<c:if test="${user.positionId != 1}">
				<form action="stopReopen" method="post" onSubmit="return check()">
					<c:if test="${user.isStopped == 1}" >
						<input type="submit" value="停止" />
						<input type="hidden" name="isStopped" value="0" />
						<input type="hidden" name="id" value="${user.id}" />
					</c:if>

					<c:if test="${user.isStopped == 0}" >
						<input type="submit" value="復活" />
						<input type="hidden" name="isStopped" value="1" />
						<input type="hidden" name="id" value="${user.id}" />
					</c:if><br/>
				</form>
			</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>

	<br/>
	<br/>
	<a href="./">戻る</a>
	<div class="copyright">Copyright(c)Ito Kenta</div>
	</div>
</body>
</html>