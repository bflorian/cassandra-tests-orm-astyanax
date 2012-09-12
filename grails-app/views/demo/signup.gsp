<%--
  Created by IntelliJ IDEA.
  User: bflorian
  Date: 9/11/12
  Time: 2:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta name='layout' content='main'/>
  <title>Sign Up</title>
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
<g:form action="createUser">
	<div>
	<label for="username">Username:</label>
	<g:textField name="username"/>
	</div>

	<div>
	<label for="username">First Name:</label>
	<g:textField name="firstName"/>
	</div>

	<div>
	<label for="username">Last Name:</label>
	<g:textField name="lastName"/>
	</div>

	<p><g:submitButton name="submit" value="Submit"/></p>

	<p>
		Already a member?
		<g:link action="index">log-in here!</g:link>
	</p>
</g:form>
</body>
</html>