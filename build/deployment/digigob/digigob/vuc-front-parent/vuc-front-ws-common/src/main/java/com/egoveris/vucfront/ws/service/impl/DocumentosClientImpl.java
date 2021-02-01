package com.egoveris.vucfront.ws.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

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

import com.egoveris.vucfront.ws.exception.DocumentosException;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ResponseDTO;
import com.egoveris.vucfront.ws.model.VincularDocVucDTO;
import com.egoveris.vucfront.ws.service.DocumentosClient;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DocumentosClientImpl implements DocumentosClient {

	private ObjectMapper objectMapper = null;
	private static final Logger LOG = LoggerFactory.getLogger(DocumentosClient.class);
	private String url;

	@Override
	public List<ExternalDocumentoVucDTO> getDocumentosByCodigoExpediente(String codigoExpediente,
			String codigoTramite) {

		List<ExternalDocumentoVucDTO> response;
		HttpGet getRequest = new HttpGet(
				(url + "?codigoExpediente=" + codigoExpediente + "&codigoTramite=" + codigoTramite).replace(" ",
						"%20"));

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			CloseableHttpResponse responseEntity = httpClient.execute(getRequest);
			response = this.toResponseListDoc(responseEntity);
		} catch (Exception e) {
			String msg = "Ha ocurrido un error al obtener los documentos del expediente ["
					+ codigoExpediente + " - " + codigoTramite + " ].";
			LOG.error(msg,e);
			throw new DocumentosException(msg);
		}
		return response;
	}

	@Override
	public ExternalTipoDocumentoVucDTO getTipoDocumentoByAcronimoVuc(String acronimoVuc) {

		ExternalTipoDocumentoVucDTO response;
		HttpGet getRequest = new HttpGet((url + "?acronimoVuc=" + acronimoVuc).replace(" ", "%20"));

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			CloseableHttpResponse responseEntity = httpClient.execute(getRequest);
			response = this.toResponseTipoDocAcronimo(responseEntity);
		} catch (Exception e) {
			String msg = "Ha ocurrido un error al obtener el tipo documento del acronimo [" + acronimoVuc + " ].";
			LOG.error(msg,e);
			throw new DocumentosException(msg, e);
		}
		return response;
	}

	@Override
	public List<ExternalDocumentoVucDTO> getDocumentosByCodigoTramite(String trata) {

		List<ExternalDocumentoVucDTO> response;
		HttpGet getRequest = new HttpGet((url + "?codigoTramite=" + trata).replace(" ", "%20"));

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			CloseableHttpResponse responseEntity = httpClient.execute(getRequest);
			response = this.toResponseListDoc(responseEntity);
		} catch (Exception e) {
			String msg = "Ha ocurrido un error al obtener los documentos del trámite [" + trata + " ].";
			LOG.error(msg,e);
			throw new DocumentosException(msg, e);
		}
		return response;
	}
	
	@Override
	public void vincularDocVuc(String expediente, String documento) {
		try {
			VincularDocVucDTO request = new VincularDocVucDTO();
			request.setExpediente(expediente);
			request.setDocumento(documento);
			this.post(url, request);
		} catch (IOException e) {
			String msg = "Ha ocurrido un error al generar el JSON.";
			LOG.error(msg, e);
			throw new DocumentosException(msg, e);
		}
	}

	private List<ExternalDocumentoVucDTO> toResponseListDoc(CloseableHttpResponse response) throws IOException {
		String stringResponse = EntityUtils.toString(response.getEntity());
		return this.getObjectMapper().readValue(stringResponse, this.getObjectMapper().getTypeFactory()
				.constructCollectionType(List.class, ExternalDocumentoVucDTO.class));
	}

	private ExternalTipoDocumentoVucDTO toResponseTipoDocAcronimo(CloseableHttpResponse response) throws IOException {
		String stringResponse = EntityUtils.toString(response.getEntity());
		return this.getObjectMapper().readValue(stringResponse, ExternalTipoDocumentoVucDTO.class);
	}
	
	private ResponseDTO post(String url, VincularDocVucDTO request)
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
				String msg = "Ha ocurrido un error al vincular el documento [" + request.getExpediente() + " - "
						+ request.getDocumento() + " ]: " + response.getMessage() + ".";
				LOG.error(msg);
				throw new DocumentosException(msg);
			}
		} catch (Exception e) {
			String msg = "Ha ocurrido un error al vincular el documento ["
					+ request.getExpediente() + " - " + request.getDocumento() + " ].";
			LOG.error(msg, e);
			throw new DocumentosException(msg, e);
		}
		return response;
	}

	private String toJSON(VincularDocVucDTO request) throws JsonGenerationException, JsonMappingException, IOException {
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
