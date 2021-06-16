package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosEmbebidosWebDavService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.util.DocumentoUtil;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.util.Constantes;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class GenerarDocumentoManual extends GenerarDocumentoServiceImpl implements GenerarDocumentoService{

	private static final Logger LOGGER = LoggerFactory
		.getLogger(GenerarDocumentoManual.class);
	
	private static final int QR_FONT_SIZE = 8;
	
	@Autowired
	GestionArchivosEmbebidosWebDavService gestionArchivosEmbebidosWebDabService;
	
	@Autowired
	private ProcesamientoTemplate procesamientoTemplate; 
	
	@Value("${app.sistema.gestor.documental}")
	private String gestorDocumental;
		
	@Autowired
	private DocumentoUtil documentoUtil;
	/*
	 * (non-Javadoc)
	 * @see com.egoveris.deo.core.api.services.impl.GenerarDocumentoServiceImpl#validacionContenidoDocumento(byte[])
	 */
	@Override
	public void validarContenidoDocumento(String tipoContenido) throws ValidacionContenidoException{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarContenidoDocumento(String) - start"); //$NON-NLS-1$
    }

		if(tipoContenido.compareTo(Constantes.CONTENIDO_GENERACION_MANUAL_TXT) != 0 && 
				tipoContenido.compareTo(Constantes.CONTENIDO_GENERACION_MANUAL_HTML)!= 0)
			throw new ValidacionContenidoException("El contenido del documento no es consistente, con la operación solicitada, " +
				"éste debe ser texto plano o html");

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarContenidoDocumento(String) - end"); //$NON-NLS-1$
    }
 	}
	
	/*
	 * (non-Javadoc)
	 * @see com.egoveris.deo.core.api.services.GenerarDocumentoService#generarArchivoPDF(com.egoveris.deo.core.api.satra.services.io.RequestGenerarDocumento, java.lang.Integer)
	 */
	@Override
	public void generarDocumentoPDF(RequestGenerarDocumento request,
			Integer numeroFirmas, Boolean almacenarRepositorioTemporal) throws ApplicationException, IOException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoPDF(RequestGenerarDocumento, Integer, Boolean) - start"); //$NON-NLS-1$
    }
		
		byte[] data;
		byte[] contenidoPDF = null;
		if(request.getTipoDocumentoGedo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE){
		
			try {
        data = procesamientoTemplate.armarDocumentoTemplate(request.getTipoDocumentoGedo(), request.getCamposTemplate());
      } catch (ApplicationException e) {
        LOGGER.debug("Error al armar documento template");
        throw e;
      }
			request.setDataOriginal(data);
			
		}else{
			data = request.getData();
		}
		
		String contenido = new String(data);
		byte [] contenidoCampos = null;
		
		if(request.getTipoDocumentoGedo().getEsComunicable()) {
			try {
        contenidoPDF = pdfService.generarPDFCCOO(contenido,
        		request.getMotivo(), request.getTipoDocumentoGedo(),
        		request);
      } catch (Exception e) {
        LOGGER.debug("Error al armar documento template", e);
      }
		}else{
			try {
        contenidoPDF = pdfService.generarPDF(contenido,
        		request.getMotivo(), request.getTipoDocumentoGedo());
      } catch (ApplicationException | IOException e) {
        throw e;
      }
		}     
		
	      // Agrego QR
	      if (request != null && request.getTipoDocumentoGedo() != null
	    		 && request.getTipoDocumentoGedo().getEsPublicable()) {
	    	  try {
				contenidoPDF = this.documentoUtil.agregarCodigoQRDocumentoPublicable(contenidoPDF, request);
			} catch (IOException | DocumentException e) {
				throw (ApplicationException)e;
			}
	      }  
		
				
		String nombreArchivoTemporal = null;
		try {
			if(request.getNombreArchivo() == null){
				
					//SADE
					nombreArchivoTemporal = this.gestionArchivosWebDavService
					.crearNombreArchivoTemporal();
				
			}
			else{
				nombreArchivoTemporal = request.getNombreArchivo();
			}			
			if(request.getTipoDocumentoGedo().getEsFirmaExternaConEncabezado()){
		        boolean importado = false;
		        if (request.getTipoDocumentoGedo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO  || request.getTipoDocumentoGedo().getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
		      	  importado = true;
		          }
				contenidoCampos = pdfService.firmarConCertificadoServidor(request, contenidoCampos, PdfService.SIGNATURE_+0, importado);
			}else{
			contenidoCampos = this.pdfService.adicionarCampos(contenidoPDF,numeroFirmas,false);
			}
			
			
			
			request.setNombreArchivo(nombreArchivoTemporal);
			if(request.getListaArchivosEmbebidos() != null && !request.getListaArchivosEmbebidos().isEmpty()){
				contenidoCampos = this.addAttachments(contenidoCampos, request.getListaArchivosEmbebidos());
			}	
			if(almacenarRepositorioTemporal){	
				
					//WEBDAV
					super.gestionArchivosWebDavService.subirArchivoTemporalWebDav(nombreArchivoTemporal,contenidoCampos);
																						
			}
			else
			{	
				request.setData(contenidoCampos);
			}
		} 
		catch (DocumentException e) {
			LOGGER.error("Error creando el documento con template", e);
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoPDF(RequestGenerarDocumento, Integer, Boolean) - end"); //$NON-NLS-1$
    }
 	}
		
	public byte[] addAttachments(byte[] src, List<ArchivoEmbebidoDTO> listaArchivosEmbebidos) throws IOException, DocumentException{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachments(byte[], List<ArchivoEmbebidoDTO>) - start"); //$NON-NLS-1$
    }

		ByteArrayOutputStream doc = new ByteArrayOutputStream();
		
		PdfReader reader = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader,doc);
		for (ArchivoEmbebidoDTO archivoEmbebido : listaArchivosEmbebidos) {
			try{
				if(archivoEmbebido.getDataArchivo() == null){
					
					
						//WEBDAV
						archivoEmbebido
								.setDataArchivo(gestionArchivosEmbebidosWebDabService
										.obtenerArchivosEmbebidosWebDav(
												archivoEmbebido.getPathRelativo(),
												archivoEmbebido.getNombreArchivo()));		
														
				}
				addAttachment(stamper.getWriter(), archivoEmbebido);
			}catch(Exception ex){
				LOGGER.error("Mensaje de error"+ex, ex);
			}	
		}
		stamper.close();
    byte[] returnbyteArray = doc.toByteArray();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachments(byte[], List<ArchivoEmbebidoDTO>) - end"); //$NON-NLS-1$
    }
  		return  returnbyteArray;
	}
	
		
	public void addAttachment(PdfWriter writer, ArchivoEmbebidoDTO src) throws IOException{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachment(PdfWriter, ArchivoEmbebidoDTO) - start"); //$NON-NLS-1$
    }

		File file = null;
		try{
			String extension = src.getNombreArchivo().substring(src.getNombreArchivo().lastIndexOf('.'));
			
			file = File.createTempFile(src.getNombreArchivo(), extension );
			
			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, src.getDataArchivo());		
			
			PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(writer, file.getAbsolutePath() , src.getNombreArchivo(), src.getDataArchivo());
		
			writer.addFileAttachment(src.getNombreArchivo().substring(0,src.getNombreArchivo().indexOf('.')), fs);
		
		}catch(Exception ex){
			LOGGER.error("Mensaje de error", ex);
			
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachment(PdfWriter, ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
    }
 	}
		
	public ResponseGenerarDocumento cerrarDocumento(
			RequestGenerarDocumento request, List<String> receptoresAviso)
	 {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - start"); //$NON-NLS-1$
    }

    ResponseGenerarDocumento returnResponseGenerarDocumentoDTO = super.cerrarDocumento(request, receptoresAviso);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - end"); //$NON-NLS-1$
    }
  		return returnResponseGenerarDocumentoDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.egoveris.deo.core.api.services.impl.GenerarDocumentoServiceImpl#generarDocumentoExterno(com.egoveris.deo.core.api.satra.services.io.RequestGenerarDocumento)
	 */
	public ResponseGenerarDocumento generarDocumentoExterno(RequestGenerarDocumento request) throws ApplicationException, IOException
	{
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoExterno(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    ResponseGenerarDocumento returnResponseGenerarDocumentoDTO = super.generarDocumentoExterno(request);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoExterno(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
  		return returnResponseGenerarDocumentoDTO;
	}

	public GestionArchivosEmbebidosWebDavService getGestionArchivosEmbebidosWebDabService() {
		return gestionArchivosEmbebidosWebDabService;
	}

	public void setGestionArchivosEmbebidosWebDabService(
			GestionArchivosEmbebidosWebDavService gestionArchivosEmbebidosWebDabService) {
		this.gestionArchivosEmbebidosWebDabService = gestionArchivosEmbebidosWebDabService;
	}
	
	@Override
	public void validarArchivoASubirPorSusFirmas(byte[] data) throws ValidacionCampoFirmaException, ArchivoFirmadoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarArchivoASubirPorSusFirmas(byte[]) - start"); //$NON-NLS-1$
    }

		try {
			if(super.pdfService.estaFirmadoOConEspaciosDeFirma(data)){
				throw new ArchivoFirmadoException("El Archivo se encuentra firmado o con espacios de Firma, " +
						"por lo tanto no es compatible con este Tipo de Documento");
			}
		} catch (ValidacionCampoFirmaException e) {
			throw e;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarArchivoASubirPorSusFirmas(byte[]) - end"); //$NON-NLS-1$
    }
 	}

  @Override
  public void generarDocumentoFirmadoConCertificado(RequestGenerarDocumento request,
      boolean subirTemporalAlfresco) throws ApplicationException {
    
  }
  
  private byte[] agregarCodigoQR(byte[] contenidoPDF, String texto, int cantFirmas) throws IOException, DocumentException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfReader pdfReader = new PdfReader(contenidoPDF);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
		PdfContentByte contentByte = pdfStamper.getOverContent(1);
		
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
		contentByte.addImage(qrCodeImage, 100, 0, 0, 100,
				10 + (bf.getWidthPoint(texto, QR_FONT_SIZE) / 2) - 50, 20);
		pdfStamper.close();
		pdfReader.close();
		return baos.toByteArray();
  }
	
}
