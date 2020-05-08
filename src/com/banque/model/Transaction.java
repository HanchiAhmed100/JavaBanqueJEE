package com.banque.model;

public class Transaction {
	private int id;
	private String type;
	private Compte emetteur;
	private Compte beneficiaire;
	private int montant;
	private String date_transaction;
	private Employe responsable;
	
	public Transaction(int id, String type, Compte emetteur,Compte beneficiaire,int montant, String date_transaction, Employe responsable) {
		super();
		this.id = id;
		this.type = type;
		this.emetteur = emetteur;
		this.beneficiaire = beneficiaire;
		this.montant = montant;
		this.date_transaction = date_transaction;
		this.responsable = responsable;
	}
	public Transaction(int id, String type, Compte tran,int montant, String date_transaction, Employe responsable) {
		super();
		this.id = id;
		this.type = type;
		if(this.type == "retrait"){this.emetteur = tran;}
		else{this.beneficiaire = tran;}
		this.montant = montant;
		this.date_transaction = date_transaction;
		this.responsable = responsable;
	}

	public Transaction(String type, Compte emetteur,Compte beneficiaire,int montant, Employe responsable) {
		super();
		this.type = type;
		this.emetteur = emetteur;
		this.beneficiaire = beneficiaire;
		this.montant = montant;
		this.responsable = responsable;
	}
	public Transaction(String type, Compte emetteur,Compte beneficiaire,int montant, Employe responsable,String created_at) {
		super();
		this.type = type;
		this.emetteur = emetteur;
		this.beneficiaire = beneficiaire;
		this.montant = montant;
		this.responsable = responsable;
		this.date_transaction = created_at;

	}
	public Transaction(String type, Compte tran,int montant, Employe responsable) {
		super();
		this.type = type;
		if(type == "retrait"){this.emetteur = tran;System.out.println("emmeteur");}
		else if (type == "versement"){this.beneficiaire = tran;System.out.println("benef");}
		this.montant = montant;
		this.responsable = responsable;
	}

	public Transaction(String type, Compte tran,int montant, Employe responsable,String created_at) {
		super();
		this.type = type;
		this.emetteur = tran;
		this.montant = montant;
		this.responsable = responsable;
		this.date_transaction = created_at;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Compte getEmetteur() {
		return emetteur;
	}
	public void setEmetteur(Compte emetteur) {
		this.emetteur = emetteur;
	}
	public Compte getBeneficiaire() {
		return beneficiaire;
	}
	public void setBeneficiaire(Compte beneficiaire) {
		this.beneficiaire = beneficiaire;
	}
	public String getDate_transaction() {
		return date_transaction;
	}
	public void setDate_transaction(String date_transaction) {
		this.date_transaction = date_transaction;
	}
	public Employe getResponsable() {
		return responsable;
	}
	public void setResponsable(Employe responsable) {
		this.responsable = responsable;
	}
	public int getMontant() {
		return montant;
	}
	public void setMontant(int montant) {
		this.montant = montant;
	}
	public String toString() {
		return "Transaction [id=" + id + ", type=" + type + ", emetteur="+ emetteur + ", beneficiaire=" + beneficiaire + ", montant =" +montant+", date_transaction=" + date_transaction + ", responsable= "+ responsable + "] \n";
	}
	
}	
