<%@page import="com.banque.service.Transaction_Service" %>
<%@page import="com.banque.model.Transaction" %>

<%@page import="java.util.ArrayList"%>


	<%
		Transaction_Service ts = new Transaction_Service();
		ArrayList<ArrayList<Object>> Transaction_list = new ArrayList<ArrayList<Object>>();
		ts.Connexion();
		Transaction_list = ts.Load_Transaction_By_Employe(request.getParameter("Emp_id"));


		for(int i = 0 ; i < Transaction_list.size(); i++ ) {
			ArrayList<Object> ob = Transaction_list.get(i);
		
		
		%>
			<tr>
				<td><%= ob.get(0) %></td>			
				<td><%= ob.get(1) %></td>
				<td><%= ob.get(2) %></td>
				<td><%= ob.get(3) %></td>
				<td><%= ob.get(4) %></td>
			</tr>
    	<%  } %>