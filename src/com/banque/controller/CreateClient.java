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

@WebServlet("/CreateClient")
public class CreateClient extends HttpServlet {
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
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String tel = request.getParameter("tel");
		Employe ep = new Employe(); 
		ep = (Employe) request.getSession(false).getAttribute("employe");
		Client c = new Client(nom, prenom, Integer.parseInt(tel) , adresse, ep);
		Client_Service cs = new Client_Service();
		cs.Connexion();
		cs.Add_Client(c);
		
	}
	
}
