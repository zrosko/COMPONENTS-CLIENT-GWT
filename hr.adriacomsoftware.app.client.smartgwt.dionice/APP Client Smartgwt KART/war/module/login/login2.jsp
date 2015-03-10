<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	
		<title>Prijava</title>
		<!-- CSS -->
		<!--  U PRODUKCIJI PROMIJENITI NA /aplikacija/module/login/... -->
		<link rel="stylesheet" href="/module/login/css/login_reset.css">
		<link rel="stylesheet" href="/module/login/css/login_animate.css">
		<link rel="stylesheet" href="/module/login/css/login_styles.css">	
	</head>
	<body>
	<!-- Begin Page Content -->
		<div id="container">
			<!--  U PRODUKCIJI PROMIJENITI NA /aplikacija/j_security_check_as2 -->
			<form action="/j_security_check_as2" method="post" id="LoginForm">
				<input type="hidden" name="action" value="login">
				<label for="name">Korisničko ime:</label>
				<input type="name" name="j_username">
				<label for="password">Lozinka:</label>
				<input type="password" name="j_password">
				<div id="lower">
				<input type="submit" value="Prijava">
				</div>
			</form>
		</div>
	<!-- End Page Content -->
</body>
</html>