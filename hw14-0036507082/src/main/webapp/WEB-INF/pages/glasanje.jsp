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

	<h1>${poll.title}</h1>
	<p>${poll.message}</p>


	<table>
		<tr>
			<th>ID</th>
			<th>Naziv</th>
		</tr>
		<c:forEach var="val" items="${pollOptions}">
			<tr>
				<td>${val.id}</td>
				<td><a href="glasanje-glasaj?id=${val.id}&pollID=${poll.id}">${val.optionTitle}</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<p><a href="index.html">Popis anketa</a></p>

</body>
</html>