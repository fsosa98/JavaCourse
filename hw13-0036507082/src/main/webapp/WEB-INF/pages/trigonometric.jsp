<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<style>
table, th, td {
  border: 1px solid black;
}
</style>
<body
	style="background-color: <c:choose>
         
         <c:when test = "${pickedBgCol != null}">
            ${pickedBgCol}
         </c:when>
         <c:otherwise>
            WHITE
         </c:otherwise>
      </c:choose>">

	<table>
		<tr>
			<th>x</th>
			<th>sin(x)</th>
			<th>cos(x)</th>
		</tr>
		<c:forEach var="val" items="${trigValues}">
			<tr>
				<td>${val.x}</td>
				<td>${val.sin}</td>
				<td>${val.cos}</td>
			</tr>
		</c:forEach>
	</table>
	
	<h3>
		<a href="index.jsp">HOME</a>
	</h3>
</body>
</html>