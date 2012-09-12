<%--
  Created by IntelliJ IDEA.
  User: bflorian
  Date: 9/11/12
  Time: 1:57 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta name='layout' content='main'/>
  <title>Log In</title>
	<style type="text/css" media="screen">
		form {
			margin: 10px;
			border: 1px solid #ccc;
			padding: 10px;
			background-color: #eee;
			width: 320px;
			margin-left: 300px;
		}
		form p {
			margin: 10px 0;
		}
	</style>
</head>
<body>
<div class="wrap">
	<g:form action="authenticate">
		<label for="username">Username:</label>
		<g:textField name="username"/>
		<g:submitButton name="login" value="Log In"/>

		<p>
			Not a member yet?
			<g:link action="signup">join here!</g:link>
		</p>
	</g:form>
</div>
</body>
</html>