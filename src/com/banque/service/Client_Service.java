package com.banque.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.banque.model.Client;
import com.banque.model.Employe;

public class Client_Service {
	String url="jdbc:mysql://localhost/java_banque";
	String Utilisateur="root";
	String motDepasse="";
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Employe e;
	Client c;
	ArrayList<Client> mylist;
	
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
	public ArrayList<Client> Get_Client(){
		 try {
			ArrayList<Client> mylist = new ArrayList<Client>();
			stmt = con.createStatement();
			String sql = "SELECT * FROM client LEFT JOIN employe ON client.employe = employe.e_id WHERE client.cli_etat = 1 ORDER BY client.c_nom";
			rs = stmt.executeQuery(sql);
			System.out.println(rs);
			while(rs.next()){
				e = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"),rs.getString("e_motdepasse"));
				c = new Client(rs.getString("c_id"),rs.getString("c_nom"),rs.getString("c_prenom"),rs.getInt("c_tel"),rs.getString("c_adress"),e);
				mylist.add(c);
			}
		    return mylist;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Client Get_One_Client(String id){
		 try {
			pstmt = con.prepareStatement("SELECT * FROM client LEFT JOIN employe ON client.employe = employe.e_id WHERE client.c_id = ?");
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				e = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"),rs.getString("e_motdepasse"));
				c = new Client(rs.getString("c_id"),rs.getString("c_nom"),rs.getString("c_prenom"),rs.getInt("c_tel"),rs.getString("c_adress"),e);
			}
			System.out.println(c.toString());
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}   
	}
	public void Add_Client(Client c){
		 try {
			 pstmt = con.prepareStatement("INSERT INTO client (c_id , c_nom ,c_prenom ,c_tel ,c_adress ,employe) VALUES (?,?,?,?,?,?)");
			 pstmt.setString(1, c.getId());
			 pstmt.setString(2, c.getNom());
			 pstmt.setString(3, c.getPrenom());
			 pstmt.setInt(4, c.getTel());
			 pstmt.setString(5, c.getAdress());
			 pstmt.setString(6, c.getEmploye().getId());
			 pstmt.executeUpdate();	
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Update_Client(Client c){
		 try {
			 pstmt = con.prepareStatement("UPDATE client SET c_nom = ?,c_prenom = ?,c_tel = ?,c_adress = ?,employe = ? WHERE c_id = ? ");
			 pstmt.setString(1, c.getNom());
			 pstmt.setString(2, c.getPrenom());
			 pstmt.setInt(3, c.getTel());
			 pstmt.setString(4, c.getAdress());
			 pstmt.setString(5, c.getEmploye().getId());
			 pstmt.setString(6, c.getId());
			 pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void set_Inactive(Client c) {
		 try {
			 pstmt = con.prepareStatement("UPDATE client SET cli_etat = 0 WHERE c_id = ?  ");
			 pstmt.setString(1, c.getId());
			 pstmt.executeUpdate();	
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Delete_Client(String id){
		 try {
			 pstmt = con.prepareStatement("DELETE FROM client where c_id = ? ");
			 pstmt.setString(1,id);
			 pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
