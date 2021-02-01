package com.egoveris.vucfront.ws.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.vucfront.model.model.DocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ResponseDTO;
import com.egoveris.vucfront.ws.model.VincularDocVucDTO;
import com.egoveris.vucfront.ws.service.ExternalDocumentoService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DocumentosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(DocumentosServlet.class);

	private ObjectMapper objectMapper = null;

	private WebApplicationContext ctx = null;

	private ExternalDocumentoService baseService;

	private DozerBeanMapper mapper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String mensaje = null;
		String jsonResponse = null;
		Integer status = HttpStatus.OK.value();

		String codigoExpediente = req.getParameter("codigoExpediente");
		String codigoTramite = req.getParameter("codigoTramite");
		String acronimoVuc = req.getParameter("acronimoVuc");

		try {
			if (codigoExpediente != null && !codigoExpediente.isEmpty()) {
				if (codigoTramite != null && !codigoTramite.isEmpty()) {
					List<ExternalDocumentoVucDTO> result = this.getDocumentoService()
							.getDocumentosByCodigoExpediente(codigoExpediente, codigoTramite);
					List<DocumentoVucDTO> resultTransform = toResponseListDoc(result);
					jsonResponse = toJSON(resultTransform);
				} else {
					mensaje = "Falta especificar un valor de consulta";
				}
			} else if (codigoTramite != null && !codigoTramite.isEmpty()) {
				List<ExternalDocumentoVucDTO> result = this.getDocumentoService()
						.getDocumentosByCodigoTramite(codigoTramite);
				List<DocumentoVucDTO> resultTransform = toResponseListDoc(result);
				jsonResponse = toJSON(resultTransform);
			} else if (acronimoVuc != null && !acronimoVuc.isEmpty()) {
				ExternalTipoDocumentoVucDTO result = this.getDocumentoService()
						.getTipoDocumentoByAcronimoVuc(acronimoVuc);
				jsonResponse = toJSON(result);
			} else {
				mensaje = "No se ha especificado ningun parametro de busqueda";
			}

			if (mensaje != null) {
				status = HttpStatus.BAD_REQUEST.value();
				jsonResponse = toJSON(new ResponseDTO(status, mensaje));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = HttpStatus.INTERNAL_SERVER_ERROR.value();
			jsonResponse = toJSON(new ResponseDTO(status, e.getMessage()));
		}
		resp.setContentType("application/json");
		resp.setStatus(status);
		resp.getWriter().write(jsonResponse);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Content-Type", "application/json");
		String response = null;
		try {
			VincularDocVucDTO request = this.getObjectMapper().readValue(this.getBody(req), VincularDocVucDTO.class);
			this.getDocumentoService().vincularDocVuc(request.getExpediente(), request.getDocumento());
			StringWriter sw = new StringWriter();
			this.getObjectMapper().writeValue(sw, new ResponseDTO(HttpStatus.OK.value(), "Vincular Documento sended."));
			response = sw.toString();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			this.getObjectMapper().writeValue(sw, new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
			response = sw.toString();
			resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		resp.getWriter().write(response);
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

	private ExternalDocumentoService getDocumentoService() {
		if (this.baseService == null) {
			this.baseService = (ExternalDocumentoService) this.getCtx().getBean("externalDocumentoServiceImpl");
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

	@SuppressWarnings("unchecked")
	private List<DocumentoVucDTO> toResponseListDoc(List<ExternalDocumentoVucDTO> response) throws IOException {
		return ListMapper.mapList(response, getMapper(), DocumentoVucDTO.class);
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

	private DozerBeanMapper getMapper() {
		if (this.mapper == null) {
			this.mapper = new DozerBeanMapper();
		}
		return this.mapper;
	}

}
