<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>掲示板ホーム</title>
<script type="text/javascript">
function check(){
	if(window.confirm('削除してよろしいですか？')){ // 確認ダイアログを表示
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
background-color: #A9A9A9;
}

	div.close {
	padding:10px;
	width:230px;
	background-color: #DCDCDC;
	}
	div.closetext {
	margin-left: 5%;
	margin-right: 5%;
	}
	div.main {
	max-width: 100%;
	background-color: #FFFFFF;
	padding-bottom: 1%;
	}
	div.message{
	margin-left: 5%;
	margin-right: 5%;
	}
	div.comment {
	max-width: 100%;
	background-color: #DCDCDC;
	margin-left: 1%;
	margin-right: 1%;
	}
	div.commentcontents{
	margin-left: 5%;
	margin-right: 5%;
	}
	div.date{
	margin-left: 80%;
	margin-right: 1%;
	}
	textarea{
	width:100%;
	height:30%;
	}
	div.head{
	margin-left: 5%;
	margin-right: 5%;
	}
	div.messagetext{
	margin-left: 0%;
	margin-right: 0%;
	border-bottom: 1px solid blue;
	}
	div.messagetextup{
	margin-left: 1%;
	margin-right: 1%;
	border-bottom: 1px solid blue;

	}
	div.commentbox{
	background:#F8F8FF;
	 border : 1px solid #A9A9A9 ;
	padding-top : 1%;
	padding-left : 1%;
	padding-right : 1%;
	}
	div.messagename{
	font-size:20px;
	}

	input#submit_deletebutton {
	max-width: 30%;
	font-size:12px;
	color:#FF0000;
	text-align:center;
	display:block;
	padding:2px 0 2px;
	background:#FFFFFF;
	box-shadow:1px 1px #1A6EA0;
	margin-left: 85%;
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
}
div.color{
color:#FF0000;
}
	</style>
</head>
<body>
	<div class="head">
		<a href="newmessage">新規投稿画面</a>
		<c:if test="${loginUser.positionId==1}">
			<a href="management">ユーザー管理画面</a>
		</c:if>
		<a href="logout">ログアウト</a>
		<c:out value="【ログイン中：${loginUser.name}さん】"></c:out>

		<h1>ホーム画面</h1>
		<div class="color">
		<c:if test="${ not empty errorMessages }">
			<ul>
				<c:forEach items="${errorMessages}" var="message">
					<li><c:out value="${message}" />
				</c:forEach>
			</ul>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
		</div>

		<form action="./" method="get">
			<div class="close">
				<div class="closetext">
					投稿日
					<br />
					<label for="fromdate"></label>
					<input type="date" name="fromdate" value="${fromdate}"/>から
					<br />
					<label for="todate"></label>
					<input type="date"  name="todate"  value="${todate}" />まで
					<br />
					<br />
					カテゴリ
					<br />
					<select name="categorySelect">
						<option value="" >
						</option>
						<c:forEach items="${messageCategories}" var="messageCategorie">
							<option value="${messageCategorie}" <c:if test="${messageCategorie == categorySelect}">selected</c:if>><c:out value="${messageCategorie}"/>
							</option>
				    	</c:forEach>
					</select>
					<br />
				</div>
			</div>
			<input id="submit_button" type="submit" value="絞込み">
		</form>
	</div>
	<br/>
	<br/>
	<c:forEach items="${messages}" var="message">
		<div class="main">
			<br/>
			<div class="message">

				<form action="deleteMessage" method="post" onSubmit="return check()">
					<div class="messagetext">
						<div class="messagename">
						 	<c:out value="${message.name}  " />さんの投稿　　
						 </div>
						件名: <c:out value="${message.subject}  " />　　
						カテゴリ: <c:out value="${message.category}  " />
					</div>
					<br/>


					<c:forEach var="s" items="${fn:split(message.text, '
							')}">
						<c:out value="${s}"/>
					</c:forEach>
					<br/>
					<br/>
					<div class="messagetextup">
						<div class="date">
							<fmt:formatDate value="${message.insertDate}"
								pattern="yyyy/MM/dd HH:mm:ss" />
						</div>
					</div>
					<input type="hidden" name="id" value="${message.id}" />
					<c:if test="${message.loginId == loginUser.loginId || loginUser.positionId==2 || loginUser.positionId==3 && loginUser.branchId==onecomment.branchId}">
						<input id="submit_deletebutton" type="submit" value="投稿削除">
					</c:if>
				</form>
			</div>
			<br/>
			<div class="comment">
				<div class="commentcontents">
					<c:forEach items="${comments}" var="onecomment">
						<c:if test="${onecomment.messageId == message.id}" >
							<br/>
							<form action="deleteComment" method="post" onSubmit="return check()">
								<label for="id"></label>
								<input type="hidden" name="id" value="${onecomment.id}" />

								<c:out value="${onecomment.name}" />さんのコメント
								<br/>
								<div class="commentbox">
								<c:forEach var="s" items="${fn:split(onecomment.text, '
									')}">
   									<c:out value="${s}"/>
								</c:forEach>
								<br/>

									<div class="date">
									<fmt:formatDate value="${onecomment.insertDate}"
											pattern="yyyy/MM/dd HH:mm:ss" />
									</div>
								</div>

								<c:if test="${onecomment.loginId == loginUser.loginId || loginUser.positionId==2 || loginUser.positionId==3 && loginUser.branchId==onecomment.branchId}">
									<input id="submit_deletebutton" type="submit" value="コメント削除">
								</c:if>
							</form>
						</c:if>
					</c:forEach>

					<br/>
					<form action="./" method="post" >
						<input type="hidden"  name=messageId value="${message.id }">
						<textarea name="text" cols="70" rows="2" class="text-box"  wrap="hard">${comment.text}<c:remove var="comment.text" scope="session" /></textarea>
						<br />
						<input id="submit_button" type="submit" value="コメント登録">(500文字まで)
						<br /><br />
					</form>
				</div>
			</div>
		</div>
		<br/>
		<br />
	</c:forEach>


	<div class="copyright">Copyright(c)Ito Kenta</div>

</body>
</html>