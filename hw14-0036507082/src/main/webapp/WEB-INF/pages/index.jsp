<%@ page session="true" contentType="text/html; charset=UTF-8 "
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
table, th, td {
  border: 1px solid black;
}
</style>
</head>
<body>

	<h1>Popis anketa</h1>
	<p>
		Odaberite anketu
	</p>

	<table>
		<tr>
			<th>ID</th>
			<th>Naziv</th>
		</tr>
		<c:forEach var="val" items="${polls}">
			<tr>
				<td>${val.id}</td>
				<td><a href="glasanje?pollID=${val.id}">${val.title}</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>