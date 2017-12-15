<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/products">

<title>produits</title>
</head>
<body>
<div id ="js" data-js="js/products" ></div>	
<button id="add" class="addbtn">Ajouter</button>
<div>
  <a id="save" class="float-top">Sauver</a>
	<a id="cancel" class="float-bot" >Annuler</a>
 </div>
 <form id ="add-item-form" style="display: none" autocomplete="off">
	<input name ="reference" value="" type="text" placeholder="Reference" class="input-standard" required>
	<input name = "name" value ="" type="text" placeholder="Nom"class="product-name" required>
	<input name = "label" value ="" type="text" placeholder="Certif"class="input-standard" required>
	<input name = "TVA" value ="" type="number" step="0.01" min=0 placeholder="TVA"class="input-standard" required>
	<input min=0 name = "byBundle" value ="" type="number" placeholder="UVC"class="input-standard" required>
	<input step="0.01" min=0 name = "priceHT" value ="" type="number" placeholder="Prix HT" class="input-standard"required>
	<input min=0 name = "price" value ="" type="number" placeholder="TTC" class="input-standard"required>
	<br>
	<button type= "submit">Sauvegarder</button>
</form>
<form class="search-product" autocomplete="off" method="search">
		<p type="button" class="search" >rechercher</p>
		<br>
		<input name ="reference" placeholder="ref" type="text" class="input-standard"required>
		<input name = "name"  placeholder="Nom"type="text" class="product-name"required>
		<input name = "label"  placeholder="Label"type="text" class="input-standard" required>
		<input type ="text" placeholder="TVA" name ="TVA" class="input-standard" required>
		<input type ="text"  placeholder="par Lot"name= "byBundle"  class="input-standard" required>
		<input type ="text" placeholder="HT" name= "priceHT" class="input-standard"required>
		<input type ="text" placeholder="TTC" name= "price" class="input-standard" required>	
		<p> &nbsp;</p>
</form>

<c:forEach items = "${it}" var = "product">
	<form class="product-form" autocomplete="off" method="${product.id}">
		<input name ="reference" value="${product.reference}" type="text" data-id="${product.id}" class="input-standard"required>
		<input name = "name" value ="${product.name}"type="text" data-id="${product.id}" class="product-name"required>
		<input name = "label" value ="${product.label}" type="text" data-id="${product.id}" class="input-standard" required>
		<input type ="text" name ="TVA" value = "${product.TVA}"  data-id="${product.id}"class="input-standard" required>
		<input type ="text" name= "byBundle"  value="${product.byBundle}" data-id="${product.id}"class="input-standard" required>
		<input type ="text" name= "priceHT"  value="${product.priceHT}" data-id="${product.id}"class="input-standard"required>
		<input type ="text" name= "price"  value="${product.price}" data-id="${product.id}"class="input-standard" required>	
		<button type="button" class="delete" data-id="${product.id}">supprimer</button>
	</form>
</c:forEach>

</html>