<%@page import="com.banque.service.Client_Service" %>
<%@page import="com.banque.model.Client" %>
<%@page import="java.util.ArrayList"%>


	<%
		Client_Service cs = new Client_Service();
		ArrayList<Client> Client_list = new ArrayList<Client>();
		cs.Connexion();
		Client_list = cs.Get_Client();
	%>
		 <option value="" disabled selected>Liste des client </option>
	<%	
		for (int i = 0 ; i < Client_list.size(); i++) {
	%>
			<option value="<%= Client_list.get(i).getId() %>"><%= Client_list.get(i).getNom()+" "+Client_list.get(i).getPrenom()  %></option>
	<%  } %>