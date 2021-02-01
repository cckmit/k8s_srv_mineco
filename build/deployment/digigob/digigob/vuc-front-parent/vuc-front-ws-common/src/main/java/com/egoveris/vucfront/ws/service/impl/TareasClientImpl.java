package com.egoveris.vucfront.ws.service.impl;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.ws.exception.TareaException;
import com.egoveris.vucfront.ws.model.NuevaTareaRequest;
import com.egoveris.vucfront.ws.model.ResponseDTO;
import com.egoveris.vucfront.ws.service.TareasClient;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TareasClientImpl implements TareasClient {

	private ObjectMapper objectMapper = null;
	private static final Logger LOG = LoggerFactory.getLogger(TareasClient.class);
	private String url;

	@Override
	public void nuevaTareaSubsanacionRequest(NuevaTareaRequest request) {
		try {
			this.post(url, request);
		} catch (IOException e) {
			String msg = "Ha ocurrido un error al generar el JSON.";
			LOG.error(msg, e);
			throw new TareaException(msg, e);
		}

	}

	private ResponseDTO post(String url, NuevaTareaRequest request)
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
				String msg = "Ha ocurrido un error al generar la tarea [" + request.getCodigoExpediente() + " - "
						+ request.getTipoTarea() + " ]: " + response.getMessage() + ".";
				LOG.error(msg);
				throw new TareaException(msg);
			}
		} catch (Exception e) {
			String msg = "Ha ocurrido un error al generar la tarea[" + request.getCodigoExpediente() + " - "
					+ request.getTipoTarea() + " ].";
			LOG.error(msg, e);
			throw new TareaException(msg, e);
		}
		return response;
	}

	private String toJSON(NuevaTareaRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter jsonSW = new StringWriter();
		this.getObjectMapper().writeValue(jsonSW, request);
		return jsonSW.toString();
	}

	private ResponseDTO toResponse(CloseableHttpResponse response) throws ParseException, IOException {
		String stringResponse = EntityUtils.toString(response.getEntity());
		return this.getObjectMapper().readValue(stringResponse, ResponseDTO.class);
	}

	private ObjectMapper getObjectMapper() {
		if (this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
