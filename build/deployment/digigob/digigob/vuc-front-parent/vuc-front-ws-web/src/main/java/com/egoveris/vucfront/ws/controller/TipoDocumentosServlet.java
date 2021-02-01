package com.egoveris.vucfront.ws.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

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

import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.ws.model.ResponseDTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TipoDocumentosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(TipoDocumentosServlet.class);

	private ObjectMapper objectMapper = null;

	private WebApplicationContext ctx = null;

	private DocumentoService baseService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String jsonResponse = null;
		Integer status = HttpStatus.OK.value();

		try {
			List<TipoDocumentoDTO> result = this.getDocumentoService().getAllTipoDocumento();
			jsonResponse = toJSON(result);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.INTERNAL_SERVER_ERROR.value();
			jsonResponse = toJSON(new ResponseDTO(status, e.getMessage()));
		}
		resp.setContentType("application/json");
		resp.setStatus(status);
		resp.getWriter().write(jsonResponse);
	}

	private String toJSON(Object response) throws IOException, JsonGenerationException, JsonMappingException {
		StringWriter sw = new StringWriter();
		this.getObjectMapper().writeValue(sw, response);
		return sw.toString();
	}

	private ObjectMapper getObjectMapper() {
		if (this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}

	private DocumentoService getDocumentoService() {
		if (this.baseService == null) {
			this.baseService = (DocumentoService) this.getCtx().getBean("documentoServiceImpl");
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
}
