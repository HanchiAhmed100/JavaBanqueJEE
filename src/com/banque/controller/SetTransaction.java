package com.banque.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.jasper.tagplugins.jstl.core.Out;

import com.banque.model.Compte;
import com.banque.model.Employe;
import com.banque.model.Transaction;
import com.banque.service.Compte_Service;
import com.banque.service.Employe_Service;
import com.banque.service.Transaction_Service;

@WebServlet("/SetTransaction")
public class SetTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Employe e;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	/**
	 * @return 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mtype = request.getParameter("type");
		String emetteur = request.getParameter("emetteur");
		String beneficiaire = request.getParameter("beneficiaire");
		String mnt = request.getParameter("mnt");
		
		Compte compteEmet, compteBenef ;
		Transaction transaction;
		Compte_Service compte_service = new Compte_Service();
		compte_service.Connexion();
		Transaction_Service transaction_service = new Transaction_Service();
		transaction_service.Connexion();
		
		PrintWriter out = response.getWriter();

		
		Employe ep = new Employe(); 
		ep = (Employe) request.getSession(false).getAttribute("employe");
		
		if(ep != null) {
			if(mtype.equals("virement")) {
				compteEmet = compte_service.Get_One_Compte(emetteur);
				if(compteEmet != null) {
					compteBenef = compte_service.Get_One_Compte(beneficiaire);
					if(compteBenef != null) {
						transaction = new Transaction(mtype, compteEmet, compteBenef, Integer.parseInt(mnt) , ep);
						transaction_service.virement(transaction);
					}else {
						System.out.println("Numero de compte benef inccroecte");
					}
				}else {
					System.out.println("Numero de compte emet inccroecte");	
				}
			}else if(mtype.equals("retrait")) {
				compteBenef = compte_service.Get_One_Compte(beneficiaire);
				if(compteBenef != null) {
					transaction = new Transaction("retrait", compteBenef, Integer.parseInt(mnt) , ep);
					System.out.println(transaction.toString());
					transaction_service.Retrait(transaction);
				}else {
					System.out.println(" compte introuvalbe");
				}
			}else {
				compteEmet = compte_service.Get_One_Compte(beneficiaire);
				if(compteEmet != null) {
					System.out.println("compteEmet : "+compteEmet.toString());
					transaction = new Transaction("versement", compteEmet, Integer.parseInt(mnt) , ep);
					System.out.println(transaction.toString());
					transaction_service.Versement(transaction);
				}else {
					System.out.println(" compte introuvalbe");
				}
			}
		}else {
			System.out.println(" employe vide");
			out.print("");
		}
		


		

		
/*		}else {

		} */
	}
	
}
