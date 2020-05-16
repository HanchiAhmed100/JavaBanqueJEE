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
          <li><a href="./Compte.jsp">compte</a></li>        
          <li><a href="./Client.jsp">client</a></li>
	      <li><a><%= ep.getNom() %> <%= ep.getPernom() %></a></li>
        </ul>
      </div>
    </nav>
  </div>
	<div class="container">
		<div class="row uk-margin-large-top">
			<div class="col-12">
				<div class="card">
		        	<div class="card-content">
		          		<p class="h4"> Creation d'une nouvelle transaction </p><br />
						<p>
					      <label>
					        <input class="with-gap" name="type" type="radio" value="virement" />
					        <span>Virment</span>
					      </label>
					    </p>
					    <p>
					      <label>
					        <input class="with-gap" name="type" type="radio" value="versement" />
					        <span>Versment</span>
					      </label>
					    </p>
					    <p>
					      <label>
					        <input class="with-gap" name="type" type="radio" value="retrait" />
					        <span>Retrait</span>
					      </label>
					    </p>
					    <button class="waves-effect waves-light btn" onclick="showForm()">Valider</button> 
					    
		        	</div>
		        	<div class="uk-margin uk-padding">
			        	<div id="virment">
			        		<div class="row">
			        			<div id="emet">
						        	<div class="input-field col s12">
						          		<input placeholder="Numero de compte emetteur" id="emetteur" type="text" class="validate">
						          		<label for="Emetteur">Emetteur</label>
						        	</div>			        			
			        			</div>
					        	<div id="benef">
						        	<div class="input-field col s12" >
						          		<input placeholder="Numero de compte bénéficiaire" id="beneficiaire" type="text" class="validate">
						          		<label for="Bénéficiaire">Bénéficiaire </label>
					        		</div>
					        	</div>
								<div id="mnt">
						        	<div class="input-field col s12" >
						          		<input placeholder="Montant" id="montant" type="text" class="validate">
						          		<label for="montant">Montant de la transaction</label>
									    <button class="waves-effect waves-light btn" onclick="valider_transaction()">Valider</button> 			
								    	<button class="waves-effect waves-light btn" onclick="annuler()">Annuler</button> 	
						        	</div>  
								</div>
					      	</div>
			        	</div>
		        	</div>
	      		</div>
			</div>
			
			<div class="col-12">
				<div class="card">
		        	<div class="card-content">		
		          		<p class="h4"> Tableau des transaction </p><br />

						<div class="row">
							<div class="input-field col s12" >
			          			<input id="num_compte_recherche" type="text" class="validate">
			          			<label for="Numero de compte">Numero de compte </label>
							</div>
							<div class="input-field col s6" >
								<input type="text" class="datepicker1">
 			          			<label for="Debut Recherche">Debut Recherche</label>
							</div>
							<div class="input-field col s6" >
								<input type="text" class="datepicker">
 			          			<label for="Fin Recherche">Fin Recherche </label>
							</div>
							<div class="uk-margin">
								<button class="waves-effect waves-light btn" onclick="valider_Recherche()">Recherche</button> 	
								<button class="waves-effect waves-light btn" onclick="Annuler_Recherche()">Annuler</button> 	
							</div>
							
			          		<div class="my-height col s12">
						      <table class="striped responsive-table ">
						        <thead>
						          <tr>
						            <th>EMETTERUR</th>
						              <th>COMPTE</th>
						              <th>TYPE</th>
	  					              <th>BENEFICIAIRE</th>
						              <th>COMPTE</th>
						              <th>MONTANT</th>
						              <th>RESPONSABLE</th>
						              <th>DATE</th>
						          </tr>
						        </thead>
						        <tbody id="Transaction_table">
						          
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
			loadForm();
			loadTable();
		});

		function loadTable(){
			$( "#Transaction_table" ).load( "./service/LoadTransactionTable.jsp" );
		}
		function loadForm(){
			$('#benef').hide();
			$('#emet').hide();
			$('#mnt').hide();
		    $('.datepicker').datepicker();
		    $('.datepicker1').datepicker();
		}

		function showForm(){
			var $type = $('input[name="type"]:checked').val();
			if($type == "virement"){
				$('#benef').show();
				$('#mnt').show();
				$('#emet').show();
			}else if($type == "versement"){
				$('#benef').show();
				$('#emet').hide();
				$('#mnt').show();
			}else{
				$('#benef').show();
				$('#mnt').show();
				$('#emet').hide();
			}
		}
		function annuler(){
			$('#beneficiaire').val('');
			$('#emetteur').val('');
			$('#montant').val('');
			$('#benef').hide();
			$('#emet').hide();
			$('#mnt').hide();
		}
		function valider_Recherche(){
			var $num =	$('#num_compte_recherche').val()
			if( $num != ''){
				if( $('.datepicker1').val() != '' && $('.datepicker').val() == ''   ){
					var date = new Date($('.datepicker1').val());
					var formatedDate = date.toISOString().slice(0,10)	
					$( "#Transaction_table" ).load( "./service/LoadTransactionTableDebut.jsp" ,{name : $num , debut : formatedDate } );
				}else if( $('.datepicker1').val() != '' && $('.datepicker').val() != ''   ){
					var date = new Date($('.datepicker1').val());
					var formatedDate = date.toISOString().slice(0,10)						
					var date2 = new Date($('.datepicker').val());
					var formatedDate2 = date2.toISOString().slice(0,10)	
					$( "#Transaction_table" ).load( "./service/LoadTransactionTableFin.jsp" ,{name : $num , debut : formatedDate , fin : formatedDate2 } );
				}else{
					$( "#Transaction_table" ).load( "./service/LoadTransactionTableByName.jsp" ,{name : $num} );
				} 
			}else{
			
			}
		}
		function Annuler_Recherche(){
			$('#num_compte_recherche').val('')
			$('.datepicker1').val('') 
			$('.datepicker').val('')
			loadTable();	
		}
		function valider_transaction(){
			var type = $('input[name="type"]:checked').val();
			var emetteur = $('#emetteur').val()
			var beneficiaire = $('#beneficiaire').val()
			var mnt = $('#montant').val()
			$.post( "SetTransaction", { type : type, emetteur : emetteur , beneficiaire : beneficiaire , mnt : mnt },function(data,status){
				loadTable();
				M.toast({html: 'Transaction '+type+' effectuer' , classes: 'rounded'})
			})
		}
	
	</script>
	
</html>