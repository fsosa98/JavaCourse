<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Registracija</title>
		
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
		Novi korisnik
		</h1>

		<form action="" method="post">

		<div>
		 <div>
		  <span class="formLabel">Ime</span><input type="text" name="firstName" value='<c:out value="${form.firstName}"/>' size="20">
		 </div>
		 <c:if test="${form.containsError('firstName')}">
		 <div class="greska"><c:out value="${form.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Prezime</span><input type="text" name="lastName" value='<c:out value="${form.lastName}"/>' size="20">
		 </div>
		 <c:if test="${form.containsError('lastName')}">
		 <div class="greska"><c:out value="${form.getError('lastName')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Nadimak</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="20">
		 </div>
		 <c:if test="${form.containsError('nick')}">
		 <div class="greska"><c:out value="${form.getError('nick')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">EMail</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="50">
		 </div>
		 <c:if test="${form.containsError('email')}">
		 <div class="greska"><c:out value="${form.getError('email')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Lozinka</span><input type="password" name="passw" value='' size="20">
		 </div>
		 <c:if test="${form.containsError('passw')}">
		 <div class="greska"><c:out value="${form.getError('passw')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>

	</body>
</html>
