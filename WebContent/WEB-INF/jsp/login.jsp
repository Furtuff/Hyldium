<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hyldium</title>
<link rel="stylesheet" type="text/css" href="css/login">
<script src="js/Login.js" type="text/javascript"></script>
</head>
<body>
<c:out value="${it.path}"></c:out>
<form action="/Hyldium/hoho/login/">
	<div class="imgcontainer">
		<img src="img/img_avatar2" alt="Avatar" class="avatar">
	</div>

	<div class="container">
		<label><b>Username</b></label> <input type="text"
			placeholder="Enter Username" id="userName" name="uname" required> <label><b>Password</b></label>
		<input type="password" placeholder="Enter Password" name="psw" id="password"
			required>

		<button type="submit" id="loginSubmit" onclick="">Login</button>
	</div>

	<div class="container" style="background-color: #f1f1f1">
		<button type="button" class="cancelbtn">Cancel</button>
		<span class="psw">Forgot <a href="#">password?</a></span>
	</div>
</form>
</body>
</html>
