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
			<p class="h4"> Ajouter compte </p><br />
			Nom et prenom du client : <br />
            <select id="client_liste" class="uk-select" onchange="Changed()">
                <option>Option 01</option>
                <option>Option 02</option>
            </select>
			<div class="row">
		        <div class="input-field col s12" id="num_holder">
		          <input disabled  id="disabled num_compte_hide" type="text" class="validate">
		          <label for="disabled">Numero de compte</label>
		        </div>
		      </div>
		      <div class="row">
		        <div class="input-field col s12">
		          <input type="number" id="solde" class="validate">
		          <label for="first_name">Solde</label>
		        </div>
		        <div class="row">
				    <button class="waves-effect waves-light btn" onclick="CreateCompte()">Valider</button> 			
		        </div>
	        </div>
		</div>
	</div>
	<div class="container">
		<div class="row uk-margin-large-top">			
			<div class="col-12">
				<div class="card">
		        	<div class="card-content">		
		          		<p class="h4"> Tableau des clients  </p><br />
						<div class="row">							
			          		<div class="my-height col s12">
						      <table class="striped responsive-table ">
						        <thead>
								<tr>
									<th>NOM PRENOM</th>
									<th>Numero de compte</th>
									<th>SOLDE</th>
									<th>DATE DE CREATION</th>
								</tr>
						        </thead>
						        <tbody id="Compte_table">
						          
						        </tbody>
						      </table>
					      	</div>	
						<div>
		        	</div>
	      		</div>
			</div>
		</div> 
	</div>

</body>
	<script>
		
		$(document).ready(function(){
		
			loadTable();

		});

		function loadTable(){
			$( "#Compte_table" ).load( "./service/LoadCompteTable.jsp" );
			$( "#client_liste" ).load( "./service/LoadClientSelect.jsp" );
			$( "#num_compte_hide" ).hide();
			
		}
		

		function Changed(){
			$( "#num_holder" ).load( "./service/LoadNumCompte.jsp" );			
		}
		function CreateCompte(){
			var Client = $('#client_liste').val();
			var numCompte = $('.numcompte').val();
			var solde = $('#solde').val()

			$.post( "CreateCompte", { Client : Client, numCompte : numCompte , solde : solde },function(data,status){
				loadTable();
				M.toast({html: 'Compte Ajouter' , classes: 'rounded'})
				$('.numcompte').val("")
				$('#solde').val("")
			})
		}
	
	</script>
	
</html>