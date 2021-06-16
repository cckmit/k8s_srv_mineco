package com.egoveris.vucfront.ws.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.ws.exception.NotificacionException;
import com.egoveris.vucfront.ws.service.TipoDocumentosClient;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TipoDocumentosClientImpl implements TipoDocumentosClient {

	private ObjectMapper objectMapper = null;

	private String url;

	@Override
	public List<TipoDocumentoDTO> getAllTipoDocumento() {
		List<TipoDocumentoDTO> response;
		HttpGet getRequest = new HttpGet(url);
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			CloseableHttpResponse responseEntity = httpClient.execute(getRequest);
			response = this.toResponseListDoc(responseEntity);
		} catch (Exception e) {
			throw new NotificacionException("Ha ocurrido un error al obtener los tipos de documentos");
		}
		return response;
	}

	private List<TipoDocumentoDTO> toResponseListDoc(CloseableHttpResponse response) throws IOException {
		String stringResponse = EntityUtils.toString(response.getEntity());
		return this.getObjectMapper().readValue(stringResponse,
				this.getObjectMapper().getTypeFactory().constructCollectionType(List.class, TipoDocumentoDTO.class));
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
