package com.egoveris.deo.base.util;

import java.io.IOException;

import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.itextpdf.text.DocumentException;

public interface DocumentoUtil {

	public byte[] agregarCodigoQR(byte[] contenidoPDF, String texto) throws IOException, DocumentException;
	public byte[] agregarCodigoQRDocumentoPublicable(byte[] contenidoPDF, String workflowId) throws IOException, DocumentException;
	public byte[] agregarCodigoQRDocumentoPublicable(byte[] contenidoPDF, RequestGenerarDocumento request) throws IOException, DocumentException;
	
}
