<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
	<meta name='layout' content='main'/>
  <title></title>
	<g:javascript>
		function like(postId) {
			alert(postId);
		}
	</g:javascript>
	<style type="text/css" media="screen">
	  textarea {
			width: 96%;
			font-size: 13px;
		}
		div.wrap {
			margin: 10px;
		}
		div.post {
			margin-top: 20px;
		}
		div.user {
			font-size: 13px;
			font-weight: bold;
		}
		.foot {
			font-size: 11px;
			font-weight: bold;
			color: #666;
		}
		div.comments {
			background-color: #ccc;
			padding: 1px 0 1px 0;
			width: 97%;
		}
		div.comments p {
			font-size: 13px;
			margin: 5px;
			padding: 5px;
			background-color: #fff;
		}
		div.comments textarea {
			width: 80%;
			margin: 5px;
		}
		div.head {
			margin-bottom: 2px;
			padding: 5px;
			background-color: #ABBF78;
			width: 96%;
		}
		div.comments span.foot {
			display: block;
		}
		input {
			font-size: 11px;
			font-weight: bold;
			margin-top: 5px;
		}
	</style>
</head>
<body>
<div class="wrap">
	<span style="float: right;"><g:link action="logout">log out</g:link></span>
	<h1>${person.firstName} ${person.lastName}</h1>
	<g:form action="addPost">
		<g:textArea name="text" rows="4" cols="80"/>
		<div>
			<g:submitButton name="submit" value="Say Something"/>
		</div>
	</g:form>

	<g:each in="${posts}" var="post" status="index">
		<div class="post">
		<g:set var="commentCount" value="${post.commentsCount()}"/>
			<div class="head">
				<div class="user">${post.person.firstName} ${post.person.lastName}</div>
				<div class="text">${post.text}</div>
				<div class="foot" id="like_${post.id}">
					<g:render template="postFoot" model="[person:person, commentCount: commentCount, post: post]"/>
				</div>
			</div>
			<div class="comments">
				<g:if test="${commentCount > 8}">
					<div>... ${commentCount - 8} more</div>
				</g:if>
				<g:each in="${post.comments(reversed:true, max:8)?.reverse()}" var="comment">
					<p>
						<span class="text"><strong>${comment.person.username}</strong> ${comment.text}</span>
						<span class="foot" id="like_${comment.uuid}"><g:render template="commentFoot" model="[person:person, comment: comment]"/></span>
					</p>
				</g:each>
			<g:form action="addComment" id="${post.id}">
				<g:textArea name="text" rows="2" cols="80"/>
				<g:submitButton name="comment" value="Submit Comment"/>
			</g:form>
			</div>

		</div>
	</g:each>
</div>
</body>
</html>