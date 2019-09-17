<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body style="background-color: <c:choose>
         
         <c:when test = "${pickedBgCol != null}">
            ${pickedBgCol}
         </c:when>
         <c:otherwise>
            WHITE
         </c:otherwise>
      </c:choose>">
    
    
      
	<h1>Colors</h1>
	<a href="setcolor?color=WHITE">WHITE</a>
	<a href="setcolor?color=RED">RED</a>
	<a href="setcolor?color=GREEN">GREEN</a>
	<a href="setcolor?color=CYAN">CYAN</a>
	
	<h3><a href="index.jsp">HOME</a></h3>
</body>
</html>