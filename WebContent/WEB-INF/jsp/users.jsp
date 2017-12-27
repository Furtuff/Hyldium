<%@ page language="java" contentType="text/html; charset=utf-8"
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
<button id="add" class="addbtn">Ajouter</button>
<div>
  <a id="save" class="float-top">Sauver</a>
	<a id="cancel" class="float-bot" >Annuler</a>
 </div>
<a class="form-label">Login:</a> <a class="form-label">Prénom:</a> <a class="form-label">Nom:</a>
<form id ="add-form" style="display: none" autocomplete="off">
	<input name ="login" value="" type="text" placeholder="Login" required>
	<input name = "firstName" value ="" type="text" placeholder="Prénom" required>
	<input autocomplete="off"name = "lastName" value ="" type="text" placeholder="Nom" required>
	<input autocomplete="off"name = "password" type="password" value ="" type="text" placeholder="mot de passe" required>
	<input class="admin-box" type ="checkbox" name= "role" value="admin">Admin
	<button type= "submit">Sauvegarder</button>

</form>

<c:forEach items = "${it}" var = "user">
	<form class="user-form" autocomplete="off" method="${user.id}">
		<input name ="login" value="${user.login}" type="text" data-id="${user.id}">
		<input name = "firstName" value ="${user.firstName}"type="text" data-id="${user.id}">
		<input name = "lastName" value ="${user.lastName}" type="text" data-id="${user.id}" >
		<input type="password" name ="password" placeholder="mot de passe"style="display: none" class="${user.id}"  data-id="${user.id}" required>
		<input type ="checkbox" name= "admin-box" data-id="${user.id}" value=""
			<c:set var = "admin" scope="page" value = "admin"></c:set>
			 <c:forEach var= "item" items="${user.role}">
				<c:if test= "${item == admin}" >checked</c:if>
			 </c:forEach>/>Admin
		<button type ="button" class='resetpwd' data-id="${user.id}" >reset MDP</button>
		<button type="button" class="delete" data-id="${user.id}">supprimer</button>
	</form>
</c:forEach>

</body>
</html>