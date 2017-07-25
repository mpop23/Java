package com.as.samples;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class TestServlet
 */

@Named
public class TestServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException   {
	
		//se specifica faptul ca aplicatia are in componenta un json
		response.setContentType("application/json");
		
		//PrintWriter writer = response.getWriter();
					
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String phone  = request.getParameter("phone");
		String country  = request.getParameter("country");
		
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(phone);
		System.out.println(country);
		
		// Se creaza o arborescenta a jsonului
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		//Construirea arborelui
		JsonObject json =builder.add("firstname",firstName)
								.add("lastname",	lastName)
								.add("phone",phone)
								.add("country", country).build();
		
		request.getSession().setAttribute("person",json.toString());		// trimiterea info catre jsp
		
		response.sendRedirect("jsp/printout.jsp");						// se face legatura cu servletul in acest moment
																		// se deschide fereastra jsp-ului
	}

}

