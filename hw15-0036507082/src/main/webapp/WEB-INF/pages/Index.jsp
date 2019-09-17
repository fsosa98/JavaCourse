<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>FER3.net</title>
		
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
		FER3.net
		</h1>
		
		<c:choose>
			<c:when test="${not empty sessionScope['current.user.nick']}">
			    <h3>
					${sessionScope['current.user.nick']}
				</h3>
				
				<form action="main" method="post">
				
				<div class="formControls">
				  <span class="formLabel">&nbsp;</span>
				  <input type="submit" name="metodaOdjava" value="Odjavi se">
				</div>
				
				</form>
				
			</c:when>
			<c:otherwise>
				<form action="main" method="post">
		
				<div>
				 <div>
				  <span class="formLabel">Nadimak</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="5">
				 </div>
				 <c:if test="${form.containsError('nick')}">
				 <div class="greska"><c:out value="${form.getError('nick')}"/></div>
				 </c:if>
				</div>
		
				<div>
				 <div>
				  <span class="formLabel">Lozinka</span><input type="password" name="passw" value='<c:out value=""/>' size="20">
				 </div>
				 <c:if test="${form.containsError('passw')}">
				 <div class="greska"><c:out value="${form.getError('passw')}"/></div>
				 </c:if>
				</div>
								
				<p style="color:#FF0000;">${err}</p>
		
				<div class="formControls">
				  <span class="formLabel">&nbsp;</span>
				  <input type="submit" name="metoda" value="Prijavi se">
				</div>
				
				</form>
				<p><a href="register">Registriraj se</a></p>
			</c:otherwise>
		</c:choose>

		<h3>Lista registriranih korisnika</h3>
	
		<ul>
			<c:choose>
			  <c:when test="${users.isEmpty()}">
			    <p>Nema registriranih korisnika.</p>
			  </c:when>
			  <c:otherwise>
			    <c:forEach var="var" items="${users}">
			    	<li><a href="author/${var.nick}">${var.nick}</a></li>
				</c:forEach>
			  </c:otherwise>
			</c:choose>
		</ul> 

	</body>
</html>
