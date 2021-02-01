package com.egoveris.te.base.util;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;

import org.apache.commons.collections.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Esta clase realiza una transformacion de numero especial a numero de documento.
 */
public class NumeroEspecial2NumeroDocumentoTransformer implements Transformer {
	private static final Logger logger = LoggerFactory.getLogger(NumeroEspecial2NumeroDocumentoTransformer.class);
	@Qualifier("consultaDocumento3Service")
	IExternalConsultaDocumentoService consultaDocumentoService;
	String uuid;
	String msgError;
	public NumeroEspecial2NumeroDocumentoTransformer(String uuid, IExternalConsultaDocumentoService  consultaDocumentoService) {
		this.uuid = uuid;
		this.consultaDocumentoService = consultaDocumentoService;
	}
    public Object transform(Object input) {
		if (logger.isDebugEnabled()) {
			logger.debug("transform(input={}) - start", input);
		}

    	try {
    	
	        RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
	        request.setUsuarioConsulta(this.uuid);
	        request.setNumeroEspecial(input.toString());
	        ResponseExternalConsultaDocumento response = consultaDocumentoService.consultarDocumentoPorNumero(request);
			Object returnObject = response.getNumeroDocumento();
			if (logger.isDebugEnabled()) {
				logger.debug("transform(Object) - end - return value={}", returnObject);
			}
			return returnObject;
		} catch (ParametroInvalidoConsultaException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} catch (DocumentoNoExisteException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} catch (SinPrivilegiosException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} catch (ErrorConsultaDocumentoException e) {
			logger.error("transform(Object)", e);

			this.msgError = e.getMessage();
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("transform(Object) - end - return value={null}");
		}
		return null;
    }
    
    public String getMsgError() {
    	return this.msgError;
    }
}
