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
			<p class="h4"> Ajouter Client </p><br />
		      <div class="row">
		        <div class="input-field col s6">
		          <input type="text" id="nom" class="validate">
		          <label for="first_name">Nom</label>
		        </div>
		        <div class="input-field col s6">
		          <input type="text" id="prenom" class="validate">
		          <label for="first_name">Prenom</label>
		        </div>
		        <div class="input-field col s6">
		          <input type="number" id="tel" class="validate">
		          <label for="first_name">Tel</label>
		        </div>
		        <div class="input-field col s6">
		          <input type="text" id="adresse" class="validate">
		          <label for="first_name">Adresse</label>
		        </div>
		        <div class="row">
				    <button class="waves-effect waves-light btn" onclick="CreateClient()">Valider</button> 			
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
									<th>TELEPHONE</th>
									<th>ADRESSE</th>
									<th>EMPLOYE</th>
								</tr>
						        </thead>
						        <tbody id="Client_table">
						          
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
			$( "#Client_table" ).load( "./service/LoadClientTable.jsp" );
		}

		function CreateClient(){
			var nom = $('#nom').val();
			var prenom = $('#prenom').val();
			var tel = $('#tel').val();
			var adresse = $('#adresse').val();
			$.post( "CreateClient", { nom : nom, prenom : prenom , tel : tel , adresse : adresse },function(data,status){
				loadTable();
				M.toast({html: 'Client'+ nom +' '+ prenom +' Ajouter' , classes: 'rounded'})
				$('#nom').val("");
				$('#prenom').val("");
				$('#tel').val("");
				$('#adresse').val("");
			})
		}

	
	</script>
	
</html>