<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<style>
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

	<h1>OS usage</h1>

	<p>Here are the results of OS usage in survey that we completed.</p>

	<img src="reportImage">
	
	<h3>
		<a href="index.jsp">HOME</a>
	</h3>

</body>
</html>