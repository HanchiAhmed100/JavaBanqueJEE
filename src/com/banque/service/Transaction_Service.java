package com.banque.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import com.banque.model.Client;
import com.banque.model.Compte;
import com.banque.model.Employe;
import com.banque.model.Transaction;

public class Transaction_Service {
	String url="jdbc:mysql://localhost/java_banque";
	String Utilisateur="root";
	String motDepasse="";
	
	Connection con;
	Statement stmt , stmt1 , stmt2;
	ResultSet rs , rs1 , rs2 ;
	PreparedStatement pstmt ,pstmt1,pstmt2;
	
	Client clE , clR;
	Compte cpE;
	Compte cpR;
	Employe ep;
	Transaction tr;
	int mnt;
	String type;
	int id;

//	ArrayList<Compte> mylist;
	public void Connexion(){
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection(url , Utilisateur, motDepasse);
    	}catch(ClassNotFoundException c) {
    		System.out.println("Probleme de pilote de base de donnée");
    	} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void virement(Transaction tr){
		try {
			pstmt = con.prepareStatement("SELECT * FROM compte WHERE num_compte = ?");
			pstmt.setString(1, tr.getEmetteur().getNumcompte());
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt("solde") < tr.getMontant()){
					System.out.println("impossible");
				}else{
					int nsolde = rs.getInt("solde") - tr.getMontant();
					PreparedStatement pstmt2 = con.prepareStatement("UPDATE compte SET solde = ? WHERE num_compte = ? ");
					pstmt2.setInt(1, nsolde);
					pstmt2.setString(2, tr.getEmetteur().getNumcompte());
					pstmt2.executeUpdate();

					PreparedStatement pstmt3 = con.prepareStatement("SELECT * FROM compte WHERE num_compte = ?");
					pstmt3.setString(1, tr.getBeneficiaire().getNumcompte());
					ResultSet rs2 = pstmt3.executeQuery();
					while(rs2.next()){
						int nsolde2 = rs2.getInt("solde") + tr.getMontant();
						PreparedStatement pstmt4 = con.prepareStatement("UPDATE compte SET solde = ? Where num_compte = ? ");
						pstmt4.setInt(1, nsolde2);
						pstmt4.setString(2, tr.getBeneficiaire().getNumcompte());
						pstmt4.executeUpdate();
						
						PreparedStatement pstmt5 = con.prepareStatement("INSERT INTO transaction (t_type,c_emetteur,c_beneficiaire,montant,e_id) VALUES (?,?,?,?,?)");
						pstmt5.setString(1,tr.getType());
						pstmt5.setString(2, tr.getEmetteur().getNumcompte());
						pstmt5.setString(3, tr.getBeneficiaire().getNumcompte());
						pstmt5.setInt(4, tr.getMontant());
						pstmt5.setString(5, tr.getResponsable().getId());
						pstmt5.executeUpdate();
					}
				}
			}
    	}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public void Retrait(Transaction tr){
		try{
			pstmt = con.prepareStatement("SELECT * FROM compte WHERE num_compte = ?");
			pstmt.setString(1, tr.getEmetteur().getNumcompte());
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt("solde") < tr.getMontant()){
					System.out.println("impossible");
				}else{	
					int nv = rs.getInt("solde") - tr.getMontant();
					PreparedStatement pstmt2 = con.prepareStatement("UPDATE compte SET solde = ? WHERE num_compte = ? ");
					pstmt2.setInt(1, nv);
					pstmt2.setString(2,tr.getEmetteur().getNumcompte());
					pstmt2.executeUpdate();

					PreparedStatement pstmt3 = con.prepareStatement("INSERT INTO transaction (t_type,c_emetteur,montant,e_id) VALUES (?,?,?,?)");
					pstmt3.setString(1,tr.getType());
					pstmt3.setString(2, tr.getEmetteur().getNumcompte());
					pstmt3.setInt(3, tr.getMontant());
					pstmt3.setString(4, tr.getResponsable().getId());
					pstmt3.executeUpdate();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void Versement(Transaction tr){
		try{
			System.out.println(tr);
			pstmt = con.prepareStatement("SELECT * FROM compte WHERE num_compte = ?");
			pstmt.setString(1, tr.getBeneficiaire().getNumcompte());
			rs = pstmt.executeQuery();
			while(rs.next()){
				int nv = rs.getInt("solde") + tr.getMontant();
				PreparedStatement pstmt0 = con.prepareStatement("UPDATE compte SET solde = ? WHERE num_compte = ? ");
				pstmt0.setInt(1, nv);
				pstmt0.setString(2,tr.getBeneficiaire().getNumcompte());
				pstmt0.executeUpdate();
				
				PreparedStatement pstmt1 = con.prepareStatement("INSERT INTO transaction (t_type,c_emetteur,montant,e_id) VALUES (?,?,?,?)");
				pstmt1.setString(1,tr.getType());
				pstmt1.setString(2, tr.getBeneficiaire().getNumcompte());
				pstmt1.setInt(3, tr.getMontant());
				pstmt1.setString(4, tr.getResponsable().getId());
				pstmt1.executeUpdate();
			}	
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	 public ArrayList<Transaction> Get_transactions(){
		try {
			ArrayList<Transaction> mylist = new ArrayList<Transaction>();
			stmt = con.createStatement();
			String sql = "SELECT * FROM transaction as t JOIN employe as ep ON ep.e_id = t.e_id JOIN compte as c1 ON t.c_emetteur = c1.num_compte JOIN client AS cl1 ON C1.titulaire = cl1.c_id JOIN compte as c2 ON t.c_beneficiaire = c2.num_compte JOIN client AS cl2 ON c2.titulaire = cl2.c_id ORDER BY t.t_created_at ";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ep = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"));
				type = rs.getString("t_type");
				mnt = rs.getInt("montant");
				id = rs.getInt("t_id");
				String created_at = rs.getString("t_created_at");
				clE = new Client(rs.getString("cl1.c_id"),rs.getString("cl1.c_nom"),rs.getString("cl1.c_prenom"),rs.getInt("cl1.c_tel"),rs.getString("cl1.c_adress"));
				cpE = new Compte(rs.getString("c1.id"),rs.getString("c1.num_compte"),clE,rs.getInt("c1.solde"),rs.getString("c1.created_at"));
				clR = new Client(rs.getString("cl2.c_id"),rs.getString("cl2.c_nom"),rs.getString("cl2.c_prenom"),rs.getInt("cl2.c_tel"),rs.getString("cl2.c_adress"));
				cpR = new Compte(rs.getString("c2.id"),rs.getString("c2.num_compte"),clR,rs.getInt("c2.solde"),rs.getString("c2.created_at"));
				tr = new Transaction(type, cpE, cpR , mnt, ep,created_at);
				mylist.add(tr);
			}
			String sql2 = "SELECT * FROM transaction as t JOIN employe as ep ON ep.e_id = t.e_id JOIN compte as c1 ON t.c_emetteur = c1.num_compte JOIN client AS cl1 ON C1.titulaire = cl1.c_id WHERE t.t_type != 'virement' ORDER BY t.t_created_at";
			rs2 = stmt.executeQuery(sql2);
			while(rs2.next()){
				ep = new Employe(rs2.getString("e_id"),rs2.getString("e_nom"),rs2.getString("e_prenom"),rs2.getString("e_mail"));
				type = rs2.getString("t_type");
				mnt = rs2.getInt("montant");
				id = rs2.getInt("t_id");
				String created_at = rs2.getString("t_created_at");
				clE = new Client(rs2.getString("cl1.c_id"),rs2.getString("cl1.c_nom"),rs2.getString("cl1.c_prenom"),rs2.getInt("cl1.c_tel"),rs2.getString("cl1.c_adress"));
				cpE = new Compte(rs2.getString("c1.id"),rs2.getString("c1.num_compte"),clE,rs2.getInt("c1.solde"),rs2.getString("c1.created_at"));
				tr = new Transaction(type, cpE, mnt, ep,created_at);
				mylist.add(tr);
			}
			return mylist;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	 }
	 
     //Vector<Vector<Object>> data = new  Vector<Vector<Object>>();

	 public ArrayList<ArrayList<Object>> Load_Transaction_By_Employe(String e_id){
		try {
			ArrayList<ArrayList<Object>> data = new  ArrayList<ArrayList<Object>>();
			PreparedStatement  pstmt = con.prepareStatement("SELECT * FROM transaction WHERE e_id = ?");
			pstmt.setString(1, e_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ArrayList<Object> row = new ArrayList<Object>();
				row.add( rs.getString("t_type"));
				row.add( rs.getString("c_emetteur"));
				row.add( rs.getString("c_beneficiaire"));
				row.add( rs.getString("montant"));
				row.add( rs.getString("t_created_at"));
				data.add(row);
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	 }
	  
	 public ArrayList<Transaction> Get_Client_Transaction(String numCompte){
		 try {
				ArrayList<Transaction> mylist = new ArrayList<Transaction>();
				String sql = "SELECT * FROM transaction as t JOIN employe as ep ON ep.e_id = t.e_id JOIN compte as c1 ON t.c_emetteur = c1.num_compte JOIN client AS cl1 ON C1.titulaire = cl1.c_id JOIN compte as c2 ON t.c_beneficiaire = c2.num_compte JOIN client AS cl2 ON c2.titulaire = cl2.c_id WHERE t.c_emetteur = ? OR t.c_beneficiaire = ?  ORDER BY t.t_created_at ";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, numCompte);
				pstmt.setString(2, numCompte);
				rs = pstmt.executeQuery();
				while(rs.next()){
					ep = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"));
					type = rs.getString("t_type");
					mnt = rs.getInt("montant");
					id = rs.getInt("t_id");
					String created_at = rs.getString("t_created_at");
					clE = new Client(rs.getString("cl1.c_id"),rs.getString("cl1.c_nom"),rs.getString("cl1.c_prenom"),rs.getInt("cl1.c_tel"),rs.getString("cl1.c_adress"));
					cpE = new Compte(rs.getString("c1.id"),rs.getString("c1.num_compte"),clE,rs.getInt("c1.solde"),rs.getString("c1.created_at"));
					clR = new Client(rs.getString("cl2.c_id"),rs.getString("cl2.c_nom"),rs.getString("cl2.c_prenom"),rs.getInt("cl2.c_tel"),rs.getString("cl2.c_adress"));
					cpR = new Compte(rs.getString("c2.id"),rs.getString("c2.num_compte"),clR,rs.getInt("c2.solde"),rs.getString("c2.created_at"));
					tr = new Transaction(type, cpE, cpR , mnt, ep,created_at);
					mylist.add(tr);
				}
				String sql2 = "SELECT * FROM transaction as t JOIN employe as ep ON ep.e_id = t.e_id JOIN compte as c1 ON t.c_emetteur = c1.num_compte JOIN client AS cl1 ON C1.titulaire = cl1.c_id WHERE t.t_type != 'virement' AND t.c_emetteur = ? ORDER BY t.t_created_at";
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, numCompte);
				rs2 = pstmt2.executeQuery();
				while(rs2.next()){
					ep = new Employe(rs2.getString("e_id"),rs2.getString("e_nom"),rs2.getString("e_prenom"),rs2.getString("e_mail"));
					type = rs2.getString("t_type");
					mnt = rs2.getInt("montant");
					id = rs2.getInt("t_id");
					String created_at = rs2.getString("t_created_at");
					clE = new Client(rs2.getString("cl1.c_id"),rs2.getString("cl1.c_nom"),rs2.getString("cl1.c_prenom"),rs2.getInt("cl1.c_tel"),rs2.getString("cl1.c_adress"));
					cpE = new Compte(rs2.getString("c1.id"),rs2.getString("c1.num_compte"),clE,rs2.getInt("c1.solde"),rs2.getString("c1.created_at"));
					tr = new Transaction(type, cpE, mnt, ep,created_at);
					mylist.add(tr);
				}
				return mylist;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	 
	 //SELECT * FROM transaction JOIN compte ON transaction.c_emetteur = compte.num_compte JOIN client ON compte.titulaire = client.c_id WHERE transaction.t_type = 'virement' AND ( c_emetteur = "1050011" OR c_beneficiaire = "1050011") AND ( transaction.t_created_at <= '2020-05-05' AND transaction.t_created_at >= '2020-05-01' )	 
	 //SELECT * FROM transaction as t JOIN employe as ep ON ep.e_id = t.e_id JOIN compte as c1 ON t.c_emetteur = c1.num_compte JOIN client AS cl1 ON C1.titulaire = cl1.c_id JOIN compte as c2 ON t.c_beneficiaire = c2.num_compte JOIN client AS cl2 ON c2.titulaire = cl2.c_id WHERE t.c_emetteur = ? OR t.c_beneficiaire = ?  ORDER BY t.t_created_at 

			 
			 
			 
			 
	 public ArrayList<Transaction> Get_Client_Transaction_by_date(String numCompte,String begin,String end){
		 try {
				ArrayList<Transaction> mylist = new ArrayList<Transaction>();
				String sql = "SELECT * FROM TRANSACTION AS T JOIN EMPLOYE AS EP ON EP.E_ID = T.E_ID JOIN COMPTE AS C1 ON T.C_EMETTEUR = C1.NUM_COMPTE JOIN CLIENT AS CL1 ON C1.TITULAIRE = CL1.C_ID JOIN COMPTE AS C2 ON T.C_BENEFICIAIRE = C2.NUM_COMPTE JOIN CLIENT AS CL2 ON C2.TITULAIRE = CL2.C_ID WHERE T.T_TYPE = 'VIREMENT' AND ( T.C_EMETTEUR = ? OR T.C_BENEFICIAIRE = ?) AND ( T.T_CREATED_AT >= ? AND T.T_CREATED_AT <= ? )  ORDER BY t.t_created_at ";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, numCompte);
				pstmt.setString(2, numCompte);
				pstmt.setString(3, begin);
				pstmt.setString(4, end);
				rs = pstmt.executeQuery();
				while(rs.next()){
					ep = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"));
					type = rs.getString("t_type");
					mnt = rs.getInt("montant");
					id = rs.getInt("t_id");
					String created_at = rs.getString("t_created_at");
					clE = new Client(rs.getString("cl1.c_id"),rs.getString("cl1.c_nom"),rs.getString("cl1.c_prenom"),rs.getInt("cl1.c_tel"),rs.getString("cl1.c_adress"));
					cpE = new Compte(rs.getString("c1.id"),rs.getString("c1.num_compte"),clE,rs.getInt("c1.solde"),rs.getString("c1.created_at"));
					clR = new Client(rs.getString("cl2.c_id"),rs.getString("cl2.c_nom"),rs.getString("cl2.c_prenom"),rs.getInt("cl2.c_tel"),rs.getString("cl2.c_adress"));
					cpR = new Compte(rs.getString("c2.id"),rs.getString("c2.num_compte"),clR,rs.getInt("c2.solde"),rs.getString("c2.created_at"));
					tr = new Transaction(type, cpE, cpR , mnt, ep,created_at);
					mylist.add(tr);
				}
				String sql2 = "SELECT * FROM TRANSACTION AS T JOIN EMPLOYE AS EP ON EP.E_ID = T.E_ID JOIN COMPTE AS C1 ON T.C_EMETTEUR = C1.NUM_COMPTE JOIN CLIENT AS CL1 ON C1.TITULAIRE = CL1.C_ID  WHERE T.T_TYPE != 'VIREMENT' AND T.C_EMETTEUR = ?  AND ( T.T_CREATED_AT >= ? AND T.T_CREATED_AT <= ? )  ORDER BY t.t_created_at ";
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, numCompte);
				pstmt2.setString(2, begin);
				pstmt2.setString(3, end);
				rs2 = pstmt2.executeQuery();
				while(rs2.next()){
					ep = new Employe(rs2.getString("e_id"),rs2.getString("e_nom"),rs2.getString("e_prenom"),rs2.getString("e_mail"));
					type = rs2.getString("t_type");
					mnt = rs2.getInt("montant");
					id = rs2.getInt("t_id");
					String created_at = rs2.getString("t_created_at");
					clE = new Client(rs2.getString("cl1.c_id"),rs2.getString("cl1.c_nom"),rs2.getString("cl1.c_prenom"),rs2.getInt("cl1.c_tel"),rs2.getString("cl1.c_adress"));
					cpE = new Compte(rs2.getString("c1.id"),rs2.getString("c1.num_compte"),clE,rs2.getInt("c1.solde"),rs2.getString("c1.created_at"));
					tr = new Transaction(type, cpE, mnt, ep,created_at);
					mylist.add(tr);
				}
				return mylist;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	 public ArrayList<Transaction> Get_Client_Transaction_by_date_Only_Begin(String numCompte,String begin){
		 try {
				ArrayList<Transaction> mylist = new ArrayList<Transaction>();
				String sql = "SELECT * FROM TRANSACTION AS T JOIN EMPLOYE AS EP ON EP.E_ID = T.E_ID JOIN COMPTE AS C1 ON T.C_EMETTEUR = C1.NUM_COMPTE JOIN CLIENT AS CL1 ON C1.TITULAIRE = CL1.C_ID JOIN COMPTE AS C2 ON T.C_BENEFICIAIRE = C2.NUM_COMPTE JOIN CLIENT AS CL2 ON C2.TITULAIRE = CL2.C_ID WHERE T.T_TYPE = 'VIREMENT' AND ( T.C_EMETTEUR = ? OR T.C_BENEFICIAIRE = ?) AND ( T.T_CREATED_AT >= ? )  ORDER BY t.t_created_at ";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, numCompte);
				pstmt.setString(2, numCompte);
				pstmt.setString(3, begin);
				rs = pstmt.executeQuery();
				while(rs.next()){
					ep = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"));
					type = rs.getString("t_type");
					mnt = rs.getInt("montant");
					id = rs.getInt("t_id");
					String created_at = rs.getString("t_created_at");
					clE = new Client(rs.getString("cl1.c_id"),rs.getString("cl1.c_nom"),rs.getString("cl1.c_prenom"),rs.getInt("cl1.c_tel"),rs.getString("cl1.c_adress"));
					cpE = new Compte(rs.getString("c1.id"),rs.getString("c1.num_compte"),clE,rs.getInt("c1.solde"),rs.getString("c1.created_at"));
					clR = new Client(rs.getString("cl2.c_id"),rs.getString("cl2.c_nom"),rs.getString("cl2.c_prenom"),rs.getInt("cl2.c_tel"),rs.getString("cl2.c_adress"));
					cpR = new Compte(rs.getString("c2.id"),rs.getString("c2.num_compte"),clR,rs.getInt("c2.solde"),rs.getString("c2.created_at"));
					tr = new Transaction(type, cpE, cpR , mnt, ep,created_at);
					mylist.add(tr);
				}
				String sql2 = "SELECT * FROM TRANSACTION AS T JOIN EMPLOYE AS EP ON EP.E_ID = T.E_ID JOIN COMPTE AS C1 ON T.C_EMETTEUR = C1.NUM_COMPTE JOIN CLIENT AS CL1 ON C1.TITULAIRE = CL1.C_ID  WHERE T.T_TYPE != 'VIREMENT' AND T.C_EMETTEUR = ?  AND ( T.T_CREATED_AT >= ? )  ORDER BY t.t_created_at ";
				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, numCompte);
				pstmt2.setString(2, begin);
				rs2 = pstmt2.executeQuery();
				while(rs2.next()){
					ep = new Employe(rs2.getString("e_id"),rs2.getString("e_nom"),rs2.getString("e_prenom"),rs2.getString("e_mail"));
					type = rs2.getString("t_type");
					mnt = rs2.getInt("montant");
					id = rs2.getInt("t_id");
					String created_at = rs2.getString("t_created_at");
					clE = new Client(rs2.getString("cl1.c_id"),rs2.getString("cl1.c_nom"),rs2.getString("cl1.c_prenom"),rs2.getInt("cl1.c_tel"),rs2.getString("cl1.c_adress"));
					cpE = new Compte(rs2.getString("c1.id"),rs2.getString("c1.num_compte"),clE,rs2.getInt("c1.solde"),rs2.getString("c1.created_at"));
					tr = new Transaction(type, cpE, mnt, ep,created_at);
					mylist.add(tr);
				}
				return mylist;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	 
	 
	 
	 
}
