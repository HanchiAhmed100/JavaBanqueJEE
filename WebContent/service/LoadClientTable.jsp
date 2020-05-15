<%@page import="com.banque.service.Client_Service" %>
<%@page import="com.banque.model.Client" %>

<%@page import="java.util.ArrayList"%>


	<%
		Client_Service cs = new Client_Service();
		ArrayList<Client> Client_list = new ArrayList<Client>();
		cs.Connexion();
		Client_list = cs.Get_Client();
		for (int i = 0 ; i < Client_list.size(); i++) {
		%>
			<tr>
				<td><%= Client_list.get(i).getNom()+" "+Client_list.get(i).getPrenom() %></td>
				<td><%= Client_list.get(i).getTel() %></td>
				<td><%= Client_list.get(i).getAdress() %></td>
				<td><%= Client_list.get(i).getEmploye().getPernom() %></td>
			</tr>
    	<%  } %>