<%@page import="com.banque.service.Employe_Service" %>
<%@page import="com.banque.model.Employe" %>

<%@page import="java.util.ArrayList"%>


	<%
		Employe_Service es = new Employe_Service();
		ArrayList<Employe> Employe_list = new ArrayList<Employe>();
		es.Connexion();
		Employe_list = es.Get_Emplye();
		for (int i = 0 ; i < Employe_list.size(); i++) {
		%>
			<tr>
				<td ><%= Employe_list.get(i).getId() %></td>			
				<td><%= Employe_list.get(i).getNom() %></td>
				<td><%= Employe_list.get(i).getPernom() %></td>
				<td><%= Employe_list.get(i).getMail() %></td>
				<td><a><i onclick="Delete('<%= Employe_list.get(i).getId() %>')" class="material-icons">delete_forever</i>
				</a></td>
			</tr>
    	<%  } %>