<%@page import="java.sql.ResultSet"%>
<%@page import="org.apache.catalina.Session" %>
<%@page import="com.banque.model.Employe" %>
	
	<% 
		Employe ep = new Employe(); 
	 	ep = (Employe) session.getAttribute("employe");
		if( ep == null ){
			response.sendRedirect("index.html");
			return ;
		} 
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Insert title here</title>
	    
    <link rel="stylesheet" type="text/css" media="screen" href="./assets/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="./assets/uikit/css/uikit.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="./assets/materialize/css/materialize.css" />
	  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<script src="./js/jquery.js"></script>
	<script src="./js/main.js"></script>
    <script src="./assets/bootstrap/js/bootstrap.js"></script>
    <script src="./assets/materialize/js/materialize.js"></script>
    <script src="./assets/uikit/js/uikit.js"></script>
    <style>
    .my-height{
		height : 400px;
		overflow-y: auto
	}
    </style>
</head>
<body class="uk-background-muted">


   <div class="navbar-fixed">
    <nav>
      <div class="nav-wrapper teal lighten-3">
        <a href="dashboard.jsp" class="brand-logo">JAVA Banque</a>
        <ul class="right hide-on-med-and-down">
          <li><a href="./Employe.jsp">employe</a></li>                    
          <li><a href="./Compte.jsp">compte</a></li>        
          <li><a href="./Client.jsp">client</a></li>
	        <li><a >Session au nom de <%= ep.getNom() %> <%= ep.getPernom() %></a></li>
        </ul>
      </div>
    </nav>
  </div>

	<div class="container">
		<div class="row  uk-margin-large-top">
			<p class="h4"> Ajouter un employe </p><br />
		      <div class="row">
		        <div class="input-field col s6">
		          <input type="text" id="nom" class="validate">
		          <label for="first_name">Nom de l'employe</label>
		        </div>
		        <div class="input-field col s6">
		          <input type="text" id="prenom" class="validate">
		          <label for="first_name">Prenom de l'employe</label>
		        </div>
		        <div class="input-field col s6">
		          <input type="email" id="mail" class="validate">
		          <label for="first_name">Email</label>
		        </div>
		        <div class="input-field col s6">
		          <input type="password" id="password" class="validate">
		          <label for="first_name">Mot de passe </label>
		        </div>
		        <div class="row">
				    <button class="waves-effect waves-light btn" onclick="CreateEmploye()">Valider</button> 			
		        </div>
	        </div>
		</div>
	</div>
	<div class="container">
		<div class="row uk-margin-large-top">			
			<div class="col-12">
				<div class="card">
		        	<div class="card-content">		
		          		<p class="h4"> Liste des employes  </p><br />
						<div class="row">							
			          		<div class="my-height col s12">
						      <table class="striped responsive-table ">
						        <thead>
								<tr>
									<th>Id </th>
									<th>NOM</th>
									<th>PRENOM</th>
									<th>EMAIL</th>
									<th>Action</th>			
									
								</tr>
						        </thead>
						        <tbody id="Employe_table">
						          
						        </tbody>
						      </table>
					      	</div>	
						<div>
		        	</div>
	      		</div>
			</div>
		</div> 
	</div>
	<div class="uk-row uk-card uk-card-body ">
		<div class="">
			<div class="col s4">
				<p class="h4"> Recherche transaction par employe id</p><br />
			</div>
			<div class="col s5">
	            <input class="uk-input" type="text" id="Emp_id" placeholder="Empoye ID">
			</div>
	        <div class="col s2">
			    <button class="waves-effect waves-light btn" onclick="Recherche()">Valider</button>		        	
	        </div>
	        <div class="col s1">
			    <button class="waves-effect waves-light btn" onclick="Annuler()">Annuler</button>		        	
	        </div>
		<div>
		<div id="search">
		<div class="my-height col s12 ">
	      <table class="striped responsive-table ">
	        <thead>
			<tr>
				<th>Transaction </th>
				<th>Emmeteur</th>
				<th>Beneficiaire</th>
				<th>Montant</th>
				<th>Date</th>			
				
			</tr>
	        </thead>
	        <tbody id="Recherche_table">
	          
	        </tbody>
	      </table>
	  	</div>	
	</div>

	
	<div id="modal-close-default" uk-modal>
	    <div class="uk-modal-dialog uk-modal-body">
	        <button class="uk-modal-close-default" type="button" uk-close></button>
	        <h2 class="uk-modal-title">Message de confiramation </h2>
	        <p>etez-vous sur de supprimer l'empolyer</p>
		    <button class="waves-effect waves-light btn" onclick="Supprimer()">Supprimer</button>
	    </div>
	</div>
</body>
	<script>
		
		$(document).ready(function(){
			loadTable();
			$("#search").hide();
		});

		function loadTable(){
			$( "#Employe_table" ).load( "./service/LoadEmployeTable.jsp" );
		}

		function CreateEmploye(){
			var nom = $('#nom').val();
			var prenom = $('#prenom').val();
			var mail = $('#mail').val();
			var password = $('#password').val();
			$.post( "CreateEmploye", { nom : nom, prenom : prenom , mail : mail , password : password },function(data,status){
				loadTable();
				M.toast({html: 'employe '+ nom +' '+ prenom +' Ajouter' , classes: 'rounded'})
				$('#nom').val("");
				$('#prenom').val("");
				$('#mail').val("");
				$('#password').val("");
			})
		}
		function Recherche(){
			var $Emp_id = $('#Emp_id').val();
			$("#search").show();
			$( "#Recherche_table" ).load( "./service/RechercheTransactionParEmploye.jsp",{Emp_id : $Emp_id} );		
		}
		function Annuler(){
			$('#Emp_id').val("");
			$("#search").hide()				
		}
		function Delete(id){
			$.post( "SupprimerEmploye", { id : id },function(data,status){
				M.toast({html: 'employe Supprimer' , classes: 'rounded'})			
				loadTable();		
			})
		}
	</script>
	
</html>