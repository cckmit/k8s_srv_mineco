package com.egoveris.te.base.util;

import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsultaServiceDummy {
	private static final Logger logger = LoggerFactory.getLogger(ConsultaServiceDummy.class);
		
		/**
		 * Consulta un documento en GEDO, validando existencia, y tipos reservados.
		 * @param request
		 * @return un objeto ResponseExternalConsultaDocumento, con los datos básicos del documento a saber.
		 * 		   - numeroDocumento;
		 *         - usuarioGenerador;
		 *		   - fechaCreacion;
		 * @throws ParametroInvalidoConsultaException
		 * @throws DocumentoNoExisteException
		 * @throws SinPrivilegiosException
		 */
		public ResponseExternalConsultaDocumento consultarDocumentoPorNumero(RequestExternalConsultaDocumentoDummy request)
			throws ParametroInvalidoConsultaException, DocumentoNoExisteException, SinPrivilegiosException, ErrorConsultaDocumentoException{
		if (logger.isDebugEnabled()) {
			logger.debug("consultarDocumentoPorNumero(request={}) - start", request);
		}
			
			throw new SinPrivilegiosException("Sin privilegios");		
		}
}
