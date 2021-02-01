package com.egoveris.tica.base.service.impl;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.egoveris.tica.base.exception.TicaHtmlToPdfException;
import com.egoveris.tica.base.exception.TicaServiceException;
import com.egoveris.tica.base.exception.TicaSignPdfException;
import com.egoveris.tica.base.model.FirmaInput;
import com.egoveris.tica.base.model.ResponseDocumento;
import com.egoveris.tica.base.service.ExternalTicaPdfService;
import com.egoveris.tica.base.service.TicaPdfService;

/**
 * Clase que implementa la interfaz <b>{@link ExternalTicaPdfService}</b> ademas
 * utilizará el servicio <b>{@link TicaPdfService}</b> para invocar a sus
 * metodos, los cuales transforman y firman documentos PDF
 *
 * @author lmancild
 *
 */

@Service
public class ExternalTicaPdfServiceImpl implements ExternalTicaPdfService {
	private static final Logger logger = LoggerFactory.getLogger(ExternalTicaPdfServiceImpl.class);

	@Autowired
	TicaPdfService ticaPdfService;

	@Value("${app.ticapdf.keystore}")
	String keyStore;

	@Value("${app.ticapdf.algoritmo}")
	String algoritmo;

	@Value("${app.ticapdf.password}")
	String password;

	@Value("${app.ticapdf.alias}")
	String alias;

	@Override
	public byte[] firmarDocumento(final ResponseDocumento responseDocumento, boolean importado, Map<String, String> labels) throws TicaServiceException {
		byte[] pdf;
		try {
			validarInputs(responseDocumento);
			logger.debug("PARAMS: " + responseDocumento.toString());
			final FirmaInput firmaInput = new FirmaInput();
			firmaInput.setAlgoritmoHash(algoritmo);
			firmaInput.setData(responseDocumento.getData());
			firmaInput.setAliasCert(alias);
			firmaInput.setSignatureFieldName(responseDocumento.getCampoFirma());
			firmaInput.setKeyStore(getKeyStore());
			firmaInput.setLocation(responseDocumento.getLocation());
			firmaInput.setPassword(password);
			firmaInput.setUsuario(responseDocumento.getUsuario() == null ? "" : responseDocumento.getUsuario());
			firmaInput.setCargo(responseDocumento.getCargo() == null ? "" : responseDocumento.getCargo());
			firmaInput.setOrganismo(responseDocumento.getOrganismo() == null ? "" : responseDocumento.getOrganismo());
			firmaInput.setSector(responseDocumento.getSector() == null ? "" : responseDocumento.getSector());				
			firmaInput.setLabelsFirma(labels);
			logger.debug("invocando al metodo firmarDocumento(FirmaInput firmaInput)  >> TicaPdfService");
			pdf = ticaPdfService.firmarDocumento(firmaInput, importado);
		} catch (final TicaSignPdfException e) {
			throw new TicaServiceException("ERROR signing PDF: " + responseDocumento, e);
		}
		return pdf;
	}

	@Override
	public byte[] htmlToPdf(final byte[] data) throws TicaServiceException {
		byte[] pdf;
		if (data == null || data.length == 0) {
			throw new TicaServiceException("Data nula");
		}
		try {
			logger.debug("invocando al metodo htmlToPdf(byte[] data) >> TicaPdfService");
			pdf = ticaPdfService.htmlToPdf(data);
		} catch (final TicaHtmlToPdfException e) {
			throw new TicaServiceException("ERROR converting html to PDF", e);
		}
		return pdf;
	}

	private void validarInputs(final ResponseDocumento response) throws TicaServiceException {
		if (response == null) {
			throw new TicaServiceException("Error Objeto nulo");
		} else if (response.getData() == null || response.getData().length == 0) {
			throw new TicaServiceException("Error Data nula o vacia");
		} else if (response.getCampoFirma() == null || response.getCampoFirma().isEmpty()) {
			throw new TicaServiceException("Error CampoFirma nulo o vacio");
		} else if (response.getLocation() == null || response.getLocation().isEmpty()) {
			throw new TicaServiceException("Error Location nula o vacia");
		}
	}

	private File getKeyStore() throws TicaServiceException {
		try {
			return new File(keyStore);
		} catch (final Exception e) {
			throw new TicaServiceException("Error Al Recuperar KeyStore ", e);
		}
	}
}
