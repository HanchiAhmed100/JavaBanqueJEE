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

import com.banque.model.Client;
import com.banque.model.Compte;
import com.banque.model.Employe;
import com.banque.model.Transaction;
import com.banque.service.Client_Service;
import com.banque.service.Compte_Service;
import com.banque.service.Employe_Service;
import com.banque.service.Transaction_Service;

@WebServlet("/CreateCompte")
public class CreateCompte extends HttpServlet {
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
		String Client = request.getParameter("Client");
		String numCompte = request.getParameter("numCompte");
		String solde = request.getParameter("solde");
		System.out.println(Client +" "+numCompte+"  "+solde+" sol" );
		Employe ep = new Employe(); 
		ep = (Employe) request.getSession(false).getAttribute("employe");
		Client C;
		Client_Service cs = new Client_Service();
		cs.Connexion();
		C = cs.Get_One_Client(Client);
		Compte_Service ccs = new Compte_Service();
		ccs.Connexion();
		Compte cp = new Compte(numCompte, C, Integer.parseInt(solde));
		ccs.Add_Compte(cp);
	}
	
}
