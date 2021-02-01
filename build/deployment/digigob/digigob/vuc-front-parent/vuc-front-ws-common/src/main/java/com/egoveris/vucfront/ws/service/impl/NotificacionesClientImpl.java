package com.egoveris.vucfront.ws.service.impl;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.ws.exception.NotificacionException;
import com.egoveris.vucfront.ws.model.NotificacionRequestDTO;
import com.egoveris.vucfront.ws.model.ResponseDTO;
import com.egoveris.vucfront.ws.service.NotificacionesClient;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificacionesClientImpl implements NotificacionesClient {

	private ObjectMapper objectMapper = null;
	private static final Logger LOG = LoggerFactory.getLogger(NotificacionesClient.class);
	private String url;
	
	@Override
	public void altaNotificacionTAD(String codSadeExpediente, DocumentoDTO notificacionDTO, String motivo) {
		NotificacionRequestDTO request = new NotificacionRequestDTO();
		request.setCodSadeExpediente(codSadeExpediente);
		com.egoveris.vucfront.ws.model.DocumentoDTO documento = new com.egoveris.vucfront.ws.model.DocumentoDTO();
		documento.setId(notificacionDTO.getId());
		documento.setArchivo(notificacionDTO.getArchivo());
		documento.setBajaLogica(notificacionDTO.getBajaLogica());
		documento.setDocumentoEstados(notificacionDTO.getDocumentoEstados());
		documento.setFechaCreacion(notificacionDTO.getFechaCreacion());
		documento.setIdTransaccion(notificacionDTO.getIdTransaccion());
		documento.setNombreOriginal(notificacionDTO.getNombreOriginal());
		documento.setNumeroDocumento(notificacionDTO.getNumeroDocumento());
		documento.setPersona(notificacionDTO.getPersona());
		documento.setReferencia(notificacionDTO.getReferencia());
		documento.setTipoDocumento(notificacionDTO.getTipoDocumento());
		documento.setUrlTemporal(notificacionDTO.getUrlTemporal());
		documento.setUsuarioCreacion(notificacionDTO.getUsuarioCreacion());
		documento.setVersion(notificacionDTO.getVersion());
		request.setDocumento(documento);
		request.setMotivo(motivo);
		try {
			this.post(url, request);
		} catch (IOException e) {
			String msg = "Ha ocurrido un error al generar el JSON.";
			LOG.error(msg, e);
			throw new NotificacionException(msg, e);
		}
	}

	@Override
	public Boolean isDocumentNotified(String codSadeExpediente, String codSadeDocumento) {
		ResponseDTO response = null;
		
		HttpGet getRequest = new HttpGet((url + "?codSadeExpediente=" + codSadeExpediente + "&codSadeDocumento=" + codSadeDocumento).replace(" ", "%20"));
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			CloseableHttpResponse responseEntity = httpClient.execute(getRequest);
			response = this.toResponse(responseEntity);
		} catch (Exception e) {
			String msg = "Ha ocurrido un error al verificar la notificacion [" + codSadeExpediente + " - "
					+ codSadeDocumento + " ].";
			LOG.error(msg, e);
			throw new NotificacionException(msg, e);
		}
		return response.getStatus() == 200;
	}

	
	private ResponseDTO post(String url, NotificacionRequestDTO request)
			throws JsonGenerationException, JsonMappingException, IOException {
		ResponseDTO response = null;

		HttpPost postRequest = new HttpPost(url);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			StringEntity requestEntity = new StringEntity(this.toJSON(request));
			requestEntity.setContentType("application/json");
			postRequest.setEntity(requestEntity);
			CloseableHttpResponse responseEntity = httpClient.execute(postRequest);
			response = this.toResponse(responseEntity);
			if (response.getStatus() != HttpStatus.SC_OK) {
				String msg = "Ha ocurrido un error al enviar la notificacion [" + request.getCodSadeExpediente() + " - "
						+ request.getDocumento().getNumeroDocumento() + " ]: " + response.getMessage() + ".";
				LOG.error(msg);
				throw new NotificacionException(msg);
			}
		} catch (Exception e) {
			String msg = "Ha ocurrido un error al enviar la notificacion ["
					+ request.getCodSadeExpediente() + " - " + request.getDocumento().getNumeroDocumento() + " ].";
			LOG.error(msg, e);
			throw new NotificacionException(msg, e);
		}
		return response;
	}
	
	private String toJSON(NotificacionRequestDTO request) throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter jsonSW = new StringWriter();
		this.getObjectMapper().writeValue(jsonSW, request);
		return jsonSW.toString();
	}
	
	private ResponseDTO toResponse(CloseableHttpResponse response) throws ParseException, IOException {
		String stringResponse = EntityUtils.toString(response.getEntity());
		return this.getObjectMapper().readValue(stringResponse, ResponseDTO.class);
	}
	
	private ObjectMapper getObjectMapper() {
		if ( this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
