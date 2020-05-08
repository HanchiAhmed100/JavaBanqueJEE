package com.banque.model;

import java.util.UUID;

public class Client {
	private String id;
	private String nom;
	private String prenom;
	private int tel;
	private String adress;
	private Employe employe;
	
	public Client(String id,String nom, String prenom, int tel, String adress,Employe employe) {
		super();
		this.id = id; 
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.adress = adress;
		this.employe = employe;
	}
	public Client(String id,String nom, String prenom, int tel, String adress) {
		super();
		this.id = id; 
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.adress = adress;
	}
	public Client(String nom, String prenom, int tel, String adress,Employe employe) {
		super();
		this.id = UUID.randomUUID().toString(); 
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.adress = adress;
		this.employe = employe;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Employe getEmploye() {
		return employe;
	}
	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ ", tel=" + tel + ", adress=" + adress + ", employe="
				+ employe + "] \n";
	}
	
	
}
