package com.egoveris.vucfront.base.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.egoveris.vucfront.base.service.UserService;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TadPersonaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6587412980593487399L;

	private Logger logger = LoggerFactory.getLogger(TadPersonaServlet.class);

	private ObjectMapper objectMapper = null;
	
	private WebApplicationContext ctx = null;
	
	private UserService userService = null;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.addHeader("Content-Type", "application/json");
		PrintWriter pw = resp.getWriter();
		int status = HttpStatus.OK.value();
		String message = "Persona creada exitosamente.";
		StringBuilder jsonRequest = new StringBuilder();
		String chunk = null;
		while ((chunk = req.getReader().readLine()) != null) { 
			jsonRequest.append(chunk); 
		}
		try {
			PersonaDTO personaDTO = this.getObjectMapper().readValue(jsonRequest.toString(), PersonaDTO.class);
			this.getUserService().save(personaDTO);			
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR.value();
			message = "ERROR: " + e.getMessage();
			logger.error("Error al crear persona", e);
		}
		pw.append(String.format("{\"status\":%d, \"message\":\"%s\"}", status, message));			

	}

	private ObjectMapper getObjectMapper() {
		if ( this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}
	
	private UserService getUserService() {
		if (this.userService == null) {
			this.userService = (UserService) this.getCtx().getBean("userServiceImpl");
		}
		return this.userService;
	}
	
	private WebApplicationContext getCtx() {
		if (this.ctx == null) {
			ServletContext context = this.getServletContext();
			this.ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		}
		return this.ctx;
	}
	
}
