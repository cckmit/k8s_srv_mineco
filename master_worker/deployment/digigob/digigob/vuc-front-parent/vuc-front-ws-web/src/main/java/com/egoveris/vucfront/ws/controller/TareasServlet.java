package com.egoveris.vucfront.ws.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.egoveris.vucfront.ws.model.NuevaTareaRequest;
import com.egoveris.vucfront.ws.model.ResponseDTO;
import com.egoveris.vucfront.ws.service.ExternalTareaService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TareasServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ObjectMapper objectMapper = null;

	private WebApplicationContext ctx = null;

	private ExternalTareaService baseService;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Content-Type", "application/json");
		String response = null;
		try {
			NuevaTareaRequest request = this.getObjectMapper().readValue(this.getBody(req), NuevaTareaRequest.class);
			this.getTareaService().nuevaTareaSubsanacionRequest(request);
			StringWriter sw = new StringWriter();
			this.getObjectMapper().writeValue(sw, new ResponseDTO(HttpStatus.OK.value(), "New task sended."));
			response = sw.toString();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			this.getObjectMapper().writeValue(sw,
					new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
			response = sw.toString();
			resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		resp.getWriter().write(response);
	}

	private ObjectMapper getObjectMapper() {
		if (this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}

	private ExternalTareaService getTareaService() {
		if (this.baseService == null) {
			this.baseService = (ExternalTareaService) this.getCtx().getBean("externalTareaServiceImpl");
		}
		return this.baseService;
	}

	private WebApplicationContext getCtx() {
		if (this.ctx == null) {
			ServletContext context = this.getServletContext();
			this.ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		}
		return this.ctx;
	}

	private String getBody(HttpServletRequest req) throws IOException {
		StringBuilder jsonRequest = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String chunk = null;
		while ((chunk = br.readLine()) != null) {
			jsonRequest.append(chunk);
		}
		br.close();
		return jsonRequest.toString();
	}
}
