package com.banque.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.banque.model.Employe;

public class Employe_Service {
	String url="jdbc:mysql://localhost/java_banque";
	String Utilisateur="root";
	String motDepasse="";
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	Employe e;
	ArrayList<Employe> mylist;


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
	public ArrayList<Employe> Get_Emplye(){
		 try {
			ArrayList<Employe> mylist = new ArrayList<Employe>();
			stmt = con.createStatement();
			String sql = "SELECT * FROM employe WHERE emp_etat = 1";
			rs = stmt.executeQuery(sql);
			System.out.println(rs);
			while(rs.next()){
				e = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"),rs.getString("e_motdepasse"));
				mylist.add(e);
			}
		    return mylist;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Employe Get_One_Employe(String e_id){
		 try {
			pstmt = con.prepareStatement("SELECT * FROM employe WHERE e_id = ? ");
			pstmt.setString(1,e_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				e = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"),rs.getString("e_motdepasse"));
			}
			return e;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}   
	}
	public void Add_Employe(Employe em){
		 try {
			 System.out.println(em);
			 pstmt = con.prepareStatement("INSERT INTO employe (e_id , e_nom , e_prenom , e_mail , e_motdepasse) VALUES (?,?,?,?,?)");
			 pstmt.setString(1,em.getId());
			 pstmt.setString(2,em.getNom());
			 pstmt.setString(3,em.getPernom());
			 pstmt.setString(4,em.getMail());
			 pstmt.setString(5,em.getMotdepasse());
			 pstmt.executeUpdate();	 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Employe Login(String mail, String password) {
		 try {
			 System.out.println(mail + "  " + password);
			pstmt = con.prepareStatement("SELECT * FROM employe WHERE e_mail = ? AND e_motdepasse = ? ");
			pstmt.setString(1,mail);
			pstmt.setString(2,password);
			rs = pstmt.executeQuery();	
			while(rs.next()){
				e = new Employe(rs.getString("e_id"),rs.getString("e_nom"),rs.getString("e_prenom"),rs.getString("e_mail"),rs.getString("e_motdepasse"));
			}
			return e;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}  
	}
	
	public void Desactivate_Employe(String id){
		 try {
			 pstmt = con.prepareStatement("UPDATE employe SET emp_etat = 0 WHERE e_id = ?");
			 pstmt.setString(1,id);
			 pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Delete_Employe(String id){
		 try {
			 pstmt = con.prepareStatement("DELETE FROM employe where e_id = ? ");
			 pstmt.setString(1,id);
			 pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
