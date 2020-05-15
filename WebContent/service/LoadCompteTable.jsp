<%@page import="com.banque.service.Compte_Service" %>
<%@page import="com.banque.model.Compte" %>

<%@page import="java.util.ArrayList"%>


	<%
		Compte_Service cs = new Compte_Service();
		ArrayList<Compte> Compte_list = new ArrayList<Compte>();
		cs.Connexion();
		Compte_list = cs.Get_Comptes();
		for (int i = 0 ; i < Compte_list.size(); i++) {
		%>
			<tr>
				<td><%= Compte_list.get(i).getTitulaire().getNom()+" "+Compte_list.get(i).getTitulaire().getPrenom() %></td>
				<td><%= Compte_list.get(i).getNumcompte() %></td>
				<td><%= Compte_list.get(i).getSolde() + " DT " %></td>
				<td><%= Compte_list.get(i).getDate_creation()%></td>
			</tr>
    	<%  } %>