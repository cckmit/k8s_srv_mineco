package com.egoveris.te.base.rendered;

import com.egoveris.deo.ws.service.IExternalConsultaDocumentoServiceExt;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.util.ConstantesServicios;

import org.zkoss.zkplus.spring.SpringUtil;

public class GenericItemRender {

	protected DocumentoGedoService documentoGedoService;
	private static IExternalConsultaDocumentoServiceExt consultaDocumentoService;
	private static IAccesoWebDavService visualizaDocumentoService;

	public DocumentoGedoService getDocumentoGedoService() {
		if (documentoGedoService == null) {
			documentoGedoService = (DocumentoGedoService) SpringUtil.getBean(ConstantesServicios.DOCUMENTO_GEDO_SERVICE);
		}
		
		return documentoGedoService;
	}

	public void setDocumentoGedoService(DocumentoGedoService documentoGedoService) {
		this.documentoGedoService = documentoGedoService;
	}
	
	public static void setVisualizaDocumentoService(IAccesoWebDavService visualizaDocumentoService) {
		GenericItemRender.visualizaDocumentoService = visualizaDocumentoService;
	}
	
	public static IAccesoWebDavService getVisualizaDocumentoService() {
		if (visualizaDocumentoService == null) {
			visualizaDocumentoService = (IAccesoWebDavService) SpringUtil
					.getBean(ConstantesServicios.ACCESO_WEBDAV_SERVICE);
		}
		
		return visualizaDocumentoService;
	}

	public static void setConsultaDocumentoService(IExternalConsultaDocumentoServiceExt consultaDocumentoService) {
		GenericItemRender.consultaDocumentoService = consultaDocumentoService;
	}

	public static IExternalConsultaDocumentoServiceExt getConsultaDocumentoService() {
		if (consultaDocumentoService == null) {
			consultaDocumentoService = (IExternalConsultaDocumentoServiceExt) SpringUtil
					.getBean(ConstantesServicios.CONSULTA_DOC_EXTERNAL_SERVICE);
		}

		return consultaDocumentoService;
	}
	
}