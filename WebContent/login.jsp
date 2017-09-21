<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン画面</title>
<style type="text/css">

div.main {
max-width: 30%;
background-color: #D3D3D3;
margin: 0 auto;
}
h1{text-align:center; }
p{text-align:center; }
div.errormessage{text-align:center; }
div.button{
margin-left: 37%;
margin-right: 45%;
}

input#submit_button {
	width:100px;
	font-size:20px;
	color:#FFFFFF;
	text-align:center;
	display:block;
	padding:5px 0 5px;
	background:#00008B;
	box-shadow:1px 1px #1A6EA0;
}
div.color{
color:#FF0000;
}

	</style>
</head>
<body>

<h1>ログイン画面</h1>
<div class="color">
<div  class="errormessage">
<c:if test="${ not empty errorMessages }">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<c:out value="${message}" />
			</c:forEach>
		</ul>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
</div>
</div>
<div class="main">
<form action="login" method="post"><br />
<p>
	<label for="loginId">ログインID</label>
	<input name="loginId" value="${missuser.loginId}" id="loginId"/> <br />
</p>
<p>
	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/> <br />
</p>
<div class="button">
	<input id="submit_button" type="submit" value="ログイン" />
</div>

</form>
</div>
<p>Copyright(c)Ito Kenta</p>

</body>
</html>
