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
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>JABA - PRIJAVA</title>
		<!-- CSS -->
		
		<!--  U PRODUKCIJI PROMIJENITI NA /aplikacija/module/login/... -->
		<link href="/module/login/css/bootstrap.min.css" rel="stylesheet">
		<link href="/module/login/css/bootstrap-theme.min.css" rel="stylesheet">
		<link href="/module/login/css/bootstrap-select.min.css" rel="stylesheet">
		<link href="/module/login/css/font-awesome.min.css" rel="stylesheet">
		<link href="/module/login/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css">
		<link href="/module/login/css/image-picker.css" media="all" rel="stylesheet" type="text/css">
		<link href="/module/login/css/public.css" rel="stylesheet">
		
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
		
<!-- 	<script src="/module/login/js/jquery-1.11.1.min.js"></script>
		<script src="/module/login/js/fileinput.min.js" type="text/javascript"></script>
		<script src="/module/login/js/bootstrap.min.js"></script>
		<script src="/module/login/js/bootstrap3-typeahead.min.js"></script>
		<script src="/module/login/js/bootstrap-select.min.js"></script>
		<script src="/module/login/js/image-picker.min.js"></script>
		<script src="/module/login/js/ie10-viewport-bug-workaround.js"></script>
 		<script src="/module/login/js/public.js"></script> -->

		<style type="text/css">
		body {
			background:url("/module/images/jaba_background.png") #EFFFD8 no-repeat left top;
			background-size: 100%;
		}
		</style>

	</head>
	<body>
	<!-- Begin Page Content -->
	<div class="site-wrapper">
		<div class="site-wrapper-inner">
			<div class="cover-container animate">
				<div class="inner cover">
					<h1 class="cover-heading">JABA</h1>
					<p class="lead text-center">Molimo Vas, prijavite se kako biste započeli s korištenjem aplikacije. <noscript></noscript></p>
					<!--  U PRODUKCIJI PROMIJENITI NA /aplikacija/j_security_check_as2 --> 
						<form  role="form" class="center-block" action="/j_security_check_as2" method="post" id="loginform" name="loginform" novalidate="">
						<fieldset>
							<div class="form-group">
								<label for="name" class="sr-only">Korisničko ime</label>
								<div id="korisnik-group" class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
									<input class="form-control" id="j_username" type="name" name="j_username" placeholder="Korisničko ime" required="" autofocus="">
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="sr-only">Lozinka</label>
								<div id="lozinka-group" class="input-group">
									<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
									<input class="form-control" id="j_password" type="password" name="j_password" placeholder="Lozinka" required="" autocomplete="off">
								</div>
							</div>
							<button type="submit" id="login" name="login" class="btn btn-primary btn-lg btn-block" data-loading-text="Prijavljivanje...">Prijava</button>
						</fieldset>
					</form>
					<div class="mastfoot inner">
						<p id="alert">
													
						</p>
					</div>	
							
					
					<div class="mastfoot inner">
						<p><i class="fa fa-copyright fa-fw"></i> <a href="http://www.jadranska-banka.hr" class="btn btn-xs btn-success" title="Jadranska banka" target="_blank"><b>JABA</b></a> 2015.</p>
					</div>		
				</div>	
			</div>
		</div>
	</div>
	<!-- End Page Content -->

</body>
</html>