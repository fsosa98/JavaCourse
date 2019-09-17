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
    
    <h1><a href="index.jsp">HOME</a></h1>
      
	<p>Invalid parameters.</p>
</body>
</html>