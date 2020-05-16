<%@page import="com.banque.service.Compte_Service" %>
<%@page import="com.banque.model.Compte" %>
<%@page import="java.util.ArrayList"%>
<%
	Compte_Service cs = new Compte_Service();
	int numCompte;
	cs.Connexion();
	numCompte = cs.new_Compte_num();
%>

  <input disabled value="<%= numCompte %>" type="text" id="num_compte disabled" class="numcompte ">
	
