<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>utilisateurs</title>
<link rel="stylesheet" type="text/css" href="css/users">
</head>
<body>
<div id ="js" data-js="js/users" ></div>	
<button id='add' class='addbtn'>Ajouter</button>
<form id="user-form">
<c:forEach items = "${it}" var = "user">
	<input name ="${user.login}" value="${user.login}" type="text">
	<input name = "${user.firstName}" value ="${user.firstName}"type="text">
	<input name = "${user.lastName}" value ="${user.lastName}"type="text">
	<button type ="button" class='resetpwd' >reset mot de passe</button>
	<input name ="password" type="hidden">
	<button type="button" class="delete">supprimer</button>
</c:forEach>
</form>
</body>
</html>