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
import com.banque.model.Employe;
import com.banque.service.Employe_Service;

@WebServlet("/Auth")
public class Auth extends HttpServlet {
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Employe_Service Es = new Employe_Service();
		Es.Connexion();
		e = Es.Login(email,password);	 
		if( e != null ) {
			HttpSession IdSession = request.getSession();
			IdSession.setAttribute("id",e.getId());
			HttpSession NameSession = request.getSession();
			NameSession.setAttribute("nom",e.getNom());
			HttpSession PrenomSession = request.getSession();
			PrenomSession.setAttribute("prenom",e.getPernom());
			HttpSession MailSession = request.getSession();
			MailSession.setAttribute("mail",e.getMail());
			HttpSession EmpolyeSession = request.getSession();
			EmpolyeSession.setAttribute("employe",e);
			PrintWriter out = response.getWriter();
			out.print(e);
		}else {
			PrintWriter out = response.getWriter();
			out.print("");
		}
	}
	
}
