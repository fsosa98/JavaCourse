<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<body
	style="background-color: <c:choose>
         
         <c:when test = "${pickedBgCol != null}">
            ${pickedBgCol}
         </c:when>
         <c:otherwise>
            WHITE
         </c:otherwise>
      </c:choose>">

	<p>
		How long is this web application running:
		<%
	long startTime = (long)request.getServletContext().getAttribute("startTime");
	long diff = System.currentTimeMillis()-startTime;
	long days=0,hours=0,minutes=0,seconds=0,milliseconds=0;
	days = diff/(1000*60*60*24);
	diff-=days*(1000*60*60*24);
	hours = diff/(1000*60*60);
	diff-=hours*(1000*60*60);
	minutes = diff/(1000*60);
	diff-=minutes*(1000*60);
	seconds = diff/(1000);
	diff-=seconds*(1000);
	milliseconds=diff;
	out.print(days+" days "+hours+" hours "+minutes+" minutes "+seconds+" seconds "+milliseconds+" milliseconds");
	
	%>
	</p>
	
	<h3>
		<a href="index.jsp">HOME</a>
	</h3>
</body>
</html>