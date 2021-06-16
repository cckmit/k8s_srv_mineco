package com.egoveris.te.base.helper;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.te.base.model.OperacionDocumentoDTO;
import com.egoveris.te.base.util.ConstantesWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

public class DocumentosOperHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentosOperHelper.class);
	
	private DocumentosOperHelper() {
		// Private constructor
	}
	
	public static ResponseExternalGenerarDocumento uploadDocumentoOperGedo(OperacionDocumentoDTO documentoOperacion,
			Media media, boolean isObligatorio) {
		IExternalGenerarDocumentoService generarDocumentoService = (IExternalGenerarDocumentoService) SpringUtil.getBean("generarDocumentoService");
		
		RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
		request.setAcronimoTipoDocumento(documentoOperacion.getTipoDocumentoGedo().getAcronimo());
		request.setData(media.getByteData());
		
		// INI MOTIVO
		StringBuilder motivo = new StringBuilder("Documento ");
		
		if (!isObligatorio) {
			motivo.append("opcional ");
		}
		else {
			motivo.append("obligatorio ");
		}
		
		motivo.append("(").append(documentoOperacion.getTipoDocumentoGedo().getAcronimo()).append(") ");
		motivo.append("para operacion");
		
		request.setReferencia(motivo.toString());
		// FIN MOTIVO
		
		request.setSistemaOrigen(ConstantesWeb.NOMBRE_APLICACION); // EE
		
		if (Executions.getCurrent().getSession().hasAttribute(ConstantesWeb.SESSION_USERNAME)) {
			request.setUsuario(Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString());
		}
		
		ResponseExternalGenerarDocumento response = null;
		
		try {
			response = generarDocumentoService.generarDocumentoGEDO(request);
		} catch (ParametroInvalidoException | CantidadDatosNoSoportadaException | ErrorGeneracionDocumentoException
				| ClavesFaltantesException e) {
			logger.debug("Error en DocumentosOperHelper.uploadDocumentoOperGedo(): ", e);
			Messagebox.show(Labels.getLabel("te.documentosHelper.error") + "\n\n" + e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		}
		
		return response;
	}
	
}