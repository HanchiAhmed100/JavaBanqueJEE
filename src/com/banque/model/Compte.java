package com.banque.model;
import java.util.UUID;


public class Compte {
	private String id;
	private String numcompte;
	private Client titulaire;
	private int solde;
	private String date_creation;
	
	public Compte(String id, String numcompte, Client titulaire, int solde,String date_creation) {
		super();
		this.id = id;
		this.numcompte = numcompte;
		this.titulaire = titulaire;
		this.solde = solde;
		this.date_creation = date_creation;
	}
	public Compte(String numcompte, Client titulaire, int solde, String date_creation) {
		super();
		this.id = UUID.randomUUID().toString(); 
		this.numcompte = numcompte;
		this.titulaire = titulaire;
		this.solde = solde;
		this.date_creation = date_creation;
	}
	public Compte(String numcompte, Client titulaire, int solde) {
		super();
		this.id = UUID.randomUUID().toString(); 
		this.numcompte = numcompte;
		this.titulaire = titulaire;
		this.solde = solde;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumcompte() {
		return numcompte;
	}
	public void setNumcompte(String numcompte) {
		this.numcompte = numcompte;
	}
	public Client getTitulaire() {
		return titulaire;
	}
	public void setTitulaire(Client titulaire) {
		this.titulaire = titulaire;
	}
	public int getSolde() {
		return solde;
	}
	public void setSolde(int solde) {
		this.solde = solde;
	}

	public String getDate_creation() {
		return date_creation;
	}
	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}
	public String toString() {
		return "compte [id=" + id + ", numcompte=" + numcompte + ", titulaire="+ titulaire + ", solde=" + solde+ ", date_creation=" + date_creation + "]" +" \n ";
	}

	
}
