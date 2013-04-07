<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login</title>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<w:wrapper>
<h1>Login</h1>

<div id="login-error">${error}</div>

	<form action="/poi/j_spring_security_check" method="post">

		<p>
			<label for="j_username">Username</label> <input id="j_username" name="j_username" type="text" />
		</p>

		<p>
			<label for="j_password">Password</label> <input id="j_password" name="j_password" type="password" />
		</p>

		<input type="submit" value="Login" />

	</form>
	
	<div id="register">
		<h1>Register new user</h1>
		<form>
			<p>
				<label>Username</label>
				<input type="text" id="username" /> 
			</p>
			
			<p>
				<label>Password</label>
				<input type="password" id="password" />  
			</p>
			
			<p>
				<input type="button" value="Register" onclick="register()"/> 
			</p>
		</form>
	</div>
	</w:wrapper>
	<script type="text/javascript"> 
	
	function register() {
		jq(function() {
			jq.post("/poi/register",	
				{ 	
					username:  jq("#username").val(),
					password:  jq("#password").val()
				},
				function(data){
					jq("#register").replaceWith('<div id="result">'+ data + '</div>');
				});
		});
	}
	
	</script>

</body>
</html>