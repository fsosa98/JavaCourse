<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body>

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>

	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Naziv</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="res" items="${result}">
				<tr>
					<td>${res.name}</td>
					<td>${res.number}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika?pollID=${idPoll}" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${idPoll}">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="win" items="${winners}">
			<li><a href="${win.link}" target="_blank">${win.name}</a></li>
		</c:forEach>
	</ul>
	
	<p><a href="servleti/glasanje?pollID=${idPoll}">Natrag</a></p>
	
	<h3>
		<a href="index.html">HOME</a>
	</h3>



</body>
</html>