<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
	<style type="text/css">
	body {
		background-color:#A9A9A9;
		}
	div.head{
	margin-left: 5%;
	margin-right: 5%;
	}
	div.main {
	max-width: 80%;
	background-color: #FFFFFF;
	padding: 1%;

	}
	textarea{
	width:100%;
	height:30%;
	}
	input#submit_button {
	max-width: 60%;
	font-size:13px;
	color:#FFFFFF;
	text-align:center;
	display:block;
	padding:5px 2 5px;
	background:#00008B;
	box-shadow:1px 1px #1A6EA0;
	margin-left: 5%;
	}
	div.color{
	color:#FF0000;
	}
	</style>
</head>
<body>
<div class="head">
	<a href="./">ホーム画面</a>
	<a href="logout">ログアウト</a>
	<c:out value="【ログイン中：${loginUser.name}さん】"></c:out>
	<br/>
	<h1>新規投稿画面</h1>
	<br/>
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

<form action = "newmessage" method = "post">

<div class="main">
	<label for="subject">件名(30文字以下)<br/></label>
	<input name="subject" value="${message.subject}" />
	<br/>
		<label for="category">カテゴリ(10文字以下)<br/></label>
	<input name="category" value="${message.category}"  />
	 <br />
	本文<br/>
	<textarea name = "text" cols = "70" rows = "5" class = "text-box" wrap="hard">${message.text}</textarea>
	<br />
	(1000文字以下)
	</div>


	 <input  id="submit_button" type = "submit" value = "登録">
</form>
<br />
<a href="./">戻る</a>
<div class = "copyright">Copyright(c)Ito kenta</div>
</div>
</body>
</html>