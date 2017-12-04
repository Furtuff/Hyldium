<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hyldium</title>
<link rel="stylesheet" type="text/css" href="css/login">
<script src="js/login" type="application/javascript"></script>
</head>
<body>
<form id="login-form">
	<div class="imgcontainer">
		<img src="img/img_avatar2.jpg" alt="Avatar" class="avatar">
	</div>
	<div>${it.message}</div>
	<div class="container">
		<label><b>Username</b></label> <input type="text"
			placeholder="Enter Username" name="login" required> <label>
		<b>Password</b></label>
		<input type="password" placeholder="Enter Password" name="password"
			required>

		<button type="submit">Login</button>
	</div>

	<div class="container" style="background-color: #f1f1f1">
		<button type="button" class="cancelbtn">Cancel</button>
		<span class="psw">Forgot <a href="#">password?</a></span>
	</div>
</form>
</body>
</html>
