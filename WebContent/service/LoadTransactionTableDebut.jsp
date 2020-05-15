<%@page import="com.banque.service.Transaction_Service" %>
<%@page import="com.banque.model.Transaction" %>

<%@page import="java.util.ArrayList"%>


	<%
		Transaction_Service ts = new Transaction_Service();
		ArrayList<Transaction> Emp_list = new ArrayList<Transaction>();
		ts.Connexion();
		Emp_list = ts.Get_Client_Transaction_by_date_Only_Begin( request.getParameter("name") , request.getParameter("debut"));

		for (int i = 0 ; i < Emp_list.size(); i++) {
			String fullname = " ";
	        String emet = " ";
	        if(Emp_list.get(i).getBeneficiaire() != null) {
	        	fullname =  Emp_list.get(i).getBeneficiaire().getTitulaire().getNom()+" "+Emp_list.get(i).getBeneficiaire().getTitulaire().getPrenom();
	        	emet = Emp_list.get(i).getBeneficiaire().getNumcompte();	
	        }
		   	String transaction = Emp_list.get(i).getType();
		   	String montant = Emp_list.get(i).getMontant()+" DT ";
		   	String emetteur = Emp_list.get(i).getEmetteur().getTitulaire().getNom()+" "+Emp_list.get(i).getEmetteur().getTitulaire().getPrenom();
		   	String EmCompte = Emp_list.get(i).getEmetteur().getNumcompte();
		   	String Responsable = Emp_list.get(i).getResponsable().getMail();
		   	String date = Emp_list.get(i).getDate_transaction();
		%>
			<tr>
				<td><%= emetteur %></td>
				<td><%= EmCompte %></td>
				<td><%= transaction %></td>
				<td><%= fullname %></td>
				<td><%= emet %></td>
				<td><%= montant %></td>
				<td><%= Responsable %></td>
				<td><%= date %></td>
			</tr>
    	<%  } %>