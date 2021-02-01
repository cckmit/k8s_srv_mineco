package com.egoveris.deo.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.egoveris.deo.base.service.DocumentoPublicableService;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.util.Constantes;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Service
public class DocumentoUtilImpl implements DocumentoUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoUtilImpl.class);

	private static final int QR_FONT_SIZE = 8;

	@Autowired
	protected ProcessEngine processEngine;

	@Autowired
	private DocumentoPublicableService documentoPublicableService;

	@Value("${app.gedo.url}")
	private String host;

	public byte[] agregarCodigoQR(byte[] contenidoPDF, String texto) throws IOException, DocumentException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfReader pdfReader = new PdfReader(contenidoPDF);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
		PdfContentByte contentByte = pdfStamper.getOverContent(pdfReader.getNumberOfPages());

		BaseFont bf = BaseFont.createFont();
		contentByte.beginText();
		contentByte.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
		contentByte.setFontAndSize(bf, QR_FONT_SIZE);
		contentByte.setTextMatrix(10, 10);
		contentByte.showText(texto);
		contentByte.endText();

		BarcodeQRCode qrCode = new BarcodeQRCode(texto, 1000, 1000, null);
		Image qrCodeImage = qrCode.getImage();
		qrCodeImage.scaleAbsolute(100, 100);
		contentByte.addImage(qrCodeImage, 100, 0, 0, 100, 10 + (bf.getWidthPoint(texto, QR_FONT_SIZE) / 2) - 50, 20);
		pdfStamper.close();
		pdfReader.close();
		return baos.toByteArray();
	}

	@Override
	public byte[] agregarCodigoQRDocumentoPublicable(byte[] contenidoPDF, String workflowId)
			throws IOException, DocumentException {
		try {
			String uuid = UUID.randomUUID().toString();
			String url = this.host + "/descarga/documento.html?id=" + uuid;
			contenidoPDF = this.agregarCodigoQR(contenidoPDF, url);
			if (workflowId != null) {
				processEngine.getExecutionService().setVariable(workflowId, Constantes.VAR_DOCUMENTO_PUBLICABLE_ID,
						uuid);
			} else {
				LOGGER.info(
						"Se generó QR para documento publicable pero no se especificó en el request un workflowID para informar el id de documento publicable.");
			}
		} catch (DocumentException e) {
			LOGGER.error("Error al agregar codigo QR", e);
		}
		return contenidoPDF;
	}

	@Override
	public byte[] agregarCodigoQRDocumentoPublicable(byte[] contenidoPDF, RequestGenerarDocumento request)
			throws IOException, DocumentException {
		try {
			boolean existePublicable = false;
			String uuid = null;
			
			if (request != null && request.getNombreArchivo() != null) {
				uuid = this.documentoPublicableService
						.getIdPublicableByNombreArchivoTemporal(request.getNombreArchivo());				
			}
			if  (uuid != null) {
				existePublicable = true;
			} else {
				uuid = UUID.randomUUID().toString();
			}
			
			String url = this.host + "/descarga/documento.html?id=" + uuid;
			contenidoPDF = this.agregarCodigoQR(contenidoPDF, url);
			if (request != null) {
				request.setIdPublicable(uuid);
				if (request.getWorkflowId() != null) {
					processEngine.getExecutionService().setVariable(request.getWorkflowId(),
							Constantes.VAR_DOCUMENTO_PUBLICABLE_ID, uuid);
				}
				if (!existePublicable) {
					this.documentoPublicableService
							.guardarRelacionArchivoTemporalIdPublicable(request.getNombreArchivo(), uuid);
				}
			} else {
				LOGGER.info(
						"Se generó QR para documento publicable pero no se especificó en el request un workflowID para informar el id de documento publicable.");
			}

		} catch (DocumentException e) {
			LOGGER.error("Error al agregar codigo QR", e);
		}
		return contenidoPDF;
	}

}
