<%@page import="java.sql.ResultSet"%>
<%@page import="org.apache.catalina.Session" %>
<%@page import="com.banque.model.Employe" %>


	<%= session.getAttribute("employe");%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%="Hello Word"%>

	<%=" <h2>mmmmmm<h2/>"%>
 
 	<%
    	out.print("<h1> Ahmed </h1>");
	%>

</body>
</html>