<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>

	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">
			<h3 align="right">${sessionScope['current.user.nick']}</h3>
			<p align="right">${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}</p>
		</c:when>
		<c:otherwise>
			<h3 align="right">Anonimno</h3>
		</c:otherwise>
	</c:choose>
	
	<h3><a href="<% out.write(request.getContextPath());%>/servleti/main">Početna</a></h3>
	
	<h2>Blogovi</h2>

	<c:choose>
		<c:when test="${entries==null}">
      <p>Nema unosa!</p>
    </c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="blogEntry" items="${entries}">
					<li><a href="${userNick}/${blogEntry.id}">${blogEntry.title}</a></li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${editable}">
			<p><a href="${userNick}/${blogEntry.id}new">Novi blog</a></p>
		</c:when>
	</c:choose>

</body>
</html>
