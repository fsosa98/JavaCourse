<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

	<head>
		<title>Blog</title>
		
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

	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">
			<h3 align="right">${sessionScope['current.user.nick']}</h3>
			<p align="right">${sessionScope['current.user.fn']}
				${sessionScope['current.user.ln']}</p>
		</c:when>
		<c:otherwise>
			<h3 align="right">Anonimno</h3>
		</c:otherwise>
	</c:choose>

	<h2>${entry.title}</h2>

	<p>${entry.text}</p>

	<c:if test="${!entry.comments.isEmpty()}">
		<ul>
			<c:forEach var="e" items="${entry.comments}">
				<li><div style="font-weight: bold">
						[Korisnik=
						<c:out value="${e.usersEMail}" />
						]
						<c:out value="${e.postedOn}" />
					</div>
					<div style="padding-left: 10px;">
						<c:out value="${e.message}" />
					</div></li>
			</c:forEach>
		</ul>
	</c:if>

	<form action="" method="post">

	<c:if test="${editable}">
		<input type="submit" name="metoda" value="Uredi blog">
	</c:if>

	<div>
		<div>
			<span class="formLabel">Komentar</span><input type="text" name=comm value='' size="30">
		</div>
	</div>
	<c:if test="${empty sessionScope['current.user.nick']}">
		<div>
			<span class="formLabel">Email</span><input type="text" name=email value='' size="10">
		</div>
	</c:if>
	
	<p style="color: #FF0000;">${err}</p>
	

	<div class="formControls">
		<span class="formLabel">&nbsp;</span>
		<input type="submit" name="metoda" value="Komentiraj">
	</div>
	</form>

</body>
</html>
