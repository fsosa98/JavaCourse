<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Novi blog</title>
		
		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
	</head>

	<body>
		<h1>
		Novi blog
		</h1>

		<form action="" method="post">

		<div>
		 <div>
		  <span class="formLabel">Naslov</span><input type="text" name=title value='${titlee}' size="20">
		 </div>
		</div>

		<div>
		 <div>
		 <span class="formLabel">Tekst<textarea rows="4" cols="50" name="text">${textt}</textarea></span>
		 </div>
		</div>
		
		<p style="color:#FF0000;">${err}</p>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Dodaj blog">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>

	</body>
</html>
