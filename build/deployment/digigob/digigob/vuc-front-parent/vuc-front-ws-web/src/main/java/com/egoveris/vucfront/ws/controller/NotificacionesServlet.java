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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.ws.model.NotificacionRequestDTO;
import com.egoveris.vucfront.ws.model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificacionesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(NotificacionesServlet.class);

	private ObjectMapper objectMapper = null;
	
	private WebApplicationContext ctx = null;
	
	  private NotificacionService baseService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Content-Type", "application/json");

		ResponseDTO response = null;

		String codSadeExpediente = req.getParameter("codSadeExpediente");
		String codSadeDocumento = req.getParameter("codSadeDocumento");
		
		try {
			Boolean result = this.getNotificacionService().isDocumentNotified(codSadeExpediente, codSadeDocumento);
			response = new ResponseDTO(
						(result) ? HttpStatus.OK.value() : HttpStatus.NO_CONTENT.value(),
						(result) ? "El documento ya ha sido notificado." : "El documento no ha sido notificado"
					);
		} catch (Exception e) {
			response = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
		StringWriter sw = new StringWriter();
		this.getObjectMapper().writeValue(sw, response);		
		resp.getWriter().write(sw.toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Content-Type", "application/json");
		String response = null;
		try {
			NotificacionRequestDTO request = this.getObjectMapper().readValue(this.getBody(req), NotificacionRequestDTO.class);
			DocumentoDTO documento = new DocumentoDTO();
			documento.setId(request.getDocumento().getId());
			documento.setArchivo(request.getDocumento().getArchivo());
			documento.setBajaLogica(request.getDocumento().getBajaLogica());
			documento.setDocumentoEstados(request.getDocumento().getDocumentoEstados());
			documento.setFechaCreacion(request.getDocumento().getFechaCreacion());
			documento.setIdTransaccion(request.getDocumento().getIdTransaccion());
			documento.setNombreOriginal(request.getDocumento().getNombreOriginal());
			documento.setNumeroDocumento(request.getDocumento().getNumeroDocumento());
			documento.setPersona(request.getDocumento().getPersona());
			documento.setReferencia(request.getDocumento().getReferencia());
			documento.setTipoDocumento(request.getDocumento().getTipoDocumento());
			documento.setUrlTemporal(request.getDocumento().getUrlTemporal());
			documento.setUsuarioCreacion(request.getDocumento().getUsuarioCreacion());
			documento.setVersion(request.getDocumento().getVersion());
			this.getNotificacionService().altaNotificacionTAD(request.getCodSadeExpediente(), documento,
					request.getMotivo());
			StringWriter sw = new StringWriter();
			this.getObjectMapper().writeValue(sw, new ResponseDTO(HttpStatus.OK.value(), "Notification sended."));
			response = sw.toString();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			this.getObjectMapper().writeValue(sw, new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
			response = sw.toString();
			resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		resp.getWriter().write(response);
	}

	private ObjectMapper getObjectMapper() {
		if ( this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}
	
	private NotificacionService getNotificacionService() {
		if (this.baseService == null) {
			this.baseService = (NotificacionService) this.getCtx().getBean("notificacionServiceImpl");
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
