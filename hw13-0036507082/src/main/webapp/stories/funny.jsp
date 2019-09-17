<%@page import="java.util.Random"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.awt.Color"%>
<%@page import="java.util.List"%>
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

	<font
		color=<%List<Color> colors = new ArrayList<Color>();
			colors.add(Color.WHITE);
			colors.add(Color.BLACK);
			colors.add(Color.BLUE);
			colors.add(Color.RED);
			colors.add(Color.GREEN);
			String hex = "#"
					+ Integer.toHexString(colors.get((new Random()).nextInt(colors.size())).getRGB()).substring(2);
			out.write(hex);%>>

		<p>Proći ću javu.</p>

	</font>
	
	<h3>
		<a href="<%out.print(request.getServletContext().getAttribute("path"));%>">HOME</a>
	</h3>

</body>
</html>