package com.banque.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.banque.model.Client;
import com.banque.model.Compte;
import com.banque.model.Employe;

public class Compte_Service {
	String url="jdbc:mysql://localhost/java_banque";
	String Utilisateur="root";
	String motDepasse="";
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Compte cp;
	Client c;
	ArrayList<Compte> mylist;
	
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
	public ArrayList<Compte> Get_Comptes(){
		 try {
			ArrayList<Compte> mylist = new ArrayList<Compte>();
			stmt = con.createStatement();
			String sql = "SELECT * FROM compte LEFT JOIN client ON compte.titulaire = client.c_id WHERE compte.cp_etat = 1  ORDER BY client.c_nom";
			rs = stmt.executeQuery(sql);
			System.out.println(rs);
			while(rs.next()){
				c = new Client(rs.getString("c_id"),rs.getString("c_nom"),rs.getString("c_prenom"),rs.getInt("c_tel"),rs.getString("c_adress"));
				cp = new Compte(rs.getString("id"),rs.getString("num_compte"),c,rs.getInt("solde"),rs.getString("created_at"));
				mylist.add(cp);
			}
		    return mylist;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Compte Get_One_Compte(String num_compte){
		 try {
			pstmt = con.prepareStatement("SELECT * FROM compte LEFT JOIN client ON compte.titulaire = client.c_id WHERE compte.num_compte = ? AND compte.cp_etat = 1 ");
			pstmt.setString(1,num_compte);
			rs = pstmt.executeQuery();
			while(rs.next()){
				c = new Client(rs.getString("c_id"),rs.getString("c_nom"),rs.getString("c_prenom"),rs.getInt("c_tel"),rs.getString("c_adress"));
				cp = new Compte(rs.getString("id"),rs.getString("num_compte"),c,rs.getInt("solde"),rs.getString("created_at"));
			}
			return cp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}   
	}
	public void Add_Compte(Compte cp){
		 try {
			 pstmt = con.prepareStatement("INSERT INTO compte (id , num_compte ,titulaire ,solde) VALUES (?,?,?,?)");
			 pstmt.setString(1, cp.getId());
			 pstmt.setString(2, cp.getNumcompte());
			 pstmt.setString(3, cp.getTitulaire().getId());
			 pstmt.setFloat(4, cp.getSolde());
			 pstmt.executeUpdate();	 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int new_Compte_num() {
		 try {
			stmt = con.createStatement();
			String sql = "SELECT * FROM compte ORDER BY num_compte DESC LIMIT 1";
			rs = stmt.executeQuery(sql);
			int num = 0;
			while(rs.next()){
				num = Integer.parseInt((rs.getString("num_compte")));
				num = num + 1;
			}
			return num;
		 }catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}    
	}
	public void Set_Inactive(String id){
		 try {
			 pstmt = con.prepareStatement("UPDATE compte SET cp_etat = 0 WHERE id = ? ");
			 pstmt.setString(1,id);
			 pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Delete_Compte(String id){
		 try {
			 pstmt = con.prepareStatement("DELETE FROM compte where id = ? ");
			 pstmt.setString(1,id);
			 pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
