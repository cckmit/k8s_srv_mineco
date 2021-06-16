package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosEmbebidosWebDavService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.util.DocumentoUtil;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class GenerarDocumentoImportadoTemplate extends GenerarDocumentoServiceImpl
    implements GenerarDocumentoService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(GenerarDocumentoImportado.class);
  @Autowired
  private ProcesamientoTemplate procesamientoTemplate;
  @Autowired
  GestionArchivosEmbebidosWebDavService gestionArchivosEmbebidosWebDabService;
  @Value("${app.sistema.gestor.documental}")
  private String gestorDocumental;

  @Autowired
  private DocumentoUtil documentoUtil;
  
  public void validarContenidoDocumento(String tipoContenido) throws ValidacionContenidoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarContenidoDocumento(String) - start"); //$NON-NLS-1$
    }

    if (!Constantes.extensionesPermitidas.contains(tipoContenido)) {
      throw new ValidacionContenidoException(
          "El contenido del documento no es consistente, con la operación solicitada, "
              + "este debe ser de tipo soportado por liveCycle para la conversión");
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarContenidoDocumento(String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void validarArchivoASubirPorSusFirmas(byte[] data)
      throws ValidacionCampoFirmaException, ArchivoFirmadoException {

  }

  public void generarDocumentoPDF(RequestGenerarDocumento request, Integer numeroFirmas,
      Boolean almacenarRepositorioTemporal) throws IOException, ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoPDF(RequestGenerarDocumento, Integer, Boolean) - start"); //$NON-NLS-1$
    }

    byte[] contenidoImportado = null;
    byte[] contenidoTemplate = null;
    byte[] contenidoFinal = null;
    String nombreArchivoTemporal = null;
    try {
      if (request.getNombreArchivo() == null) {

        // SADE
        nombreArchivoTemporal = this.gestionArchivosWebDavService.crearNombreArchivoTemporal();

      } else {
        nombreArchivoTemporal = request.getNombreArchivo();
      }
      
      contenidoImportado = request.getData();

      contenidoTemplate = procesamientoTemplate
          .armarDocumentoTemplate(request.getTipoDocumentoGedo(), request.getCamposTemplate());
      String textoTemplate = new String(contenidoTemplate);
      if (request.getTipoDocumentoGedo().getEsComunicable()) {
        contenidoFinal = pdfService.adicionarCamposNuevaPaginaImpTemp(contenidoImportado,
            numeroFirmas, request.getTipoDocumentoGedo().getAcronimo(), request.getMotivo(),
            textoTemplate, request);
      } else {
        contenidoFinal = pdfService.adicionarCamposNuevaPaginaImpTemp(contenidoImportado,
            numeroFirmas, request.getTipoDocumentoGedo().getAcronimo(), request.getMotivo(),
            textoTemplate);
      }

      if (request.getListaArchivosEmbebidos() != null
          && !request.getListaArchivosEmbebidos().isEmpty()) {
        contenidoFinal = this.addAttachments(contenidoFinal, request.getListaArchivosEmbebidos());
      }

      // Agrego QR
      if (request != null && request.getTipoDocumentoGedo() != null
    		 && request.getTipoDocumentoGedo().getEsPublicable()) {
    	  contenidoFinal = this.documentoUtil.agregarCodigoQRDocumentoPublicable(contenidoFinal, request);
      }  

      if (almacenarRepositorioTemporal) {

        // WEBDAV
        super.gestionArchivosWebDavService
            .subirArchivoTemporalWebDav(nombreArchivoTemporal, contenidoFinal);

      }
      		
      request.setData(contenidoFinal);
      request.setNombreArchivo(nombreArchivoTemporal);

    } catch (ApplicationException e) {
      LOGGER.error("Error creando el documento en importacion", e);
      throw e;
    } catch (DocumentException e) {
      LOGGER.error("Error creando el documento pdf", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoPDF(RequestGenerarDocumento, Integer, Boolean) - end"); //$NON-NLS-1$
    }
  }

  public byte[] addAttachments(byte[] src, List<ArchivoEmbebidoDTO> listaArchivosEmbebidos)
      throws IOException, DocumentException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachments(byte[], List<ArchivoEmbebidoDTO>) - start"); //$NON-NLS-1$
    }

    ByteArrayOutputStream doc = new ByteArrayOutputStream();

    PdfReader reader = new PdfReader(src);
    PdfStamper stamper = new PdfStamper(reader, doc);
    for (ArchivoEmbebidoDTO archivoEmbebido : listaArchivosEmbebidos) {
      try {
        if (archivoEmbebido.getDataArchivo() == null) {

          // WEBDAV
          archivoEmbebido
              .setDataArchivo(gestionArchivosEmbebidosWebDabService.obtenerArchivosEmbebidosWebDav(
                  archivoEmbebido.getPathRelativo(), archivoEmbebido.getNombreArchivo()));

        }
        addAttachment(stamper.getWriter(), archivoEmbebido);
      } catch (Exception ex) {
        LOGGER.error("Mensaje de error" + ex, ex);
      }
    }
    stamper.close();
    byte[] returnbyteArray = doc.toByteArray();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachments(byte[], List<ArchivoEmbebidoDTO>) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  public void addAttachment(PdfWriter writer, ArchivoEmbebidoDTO src) throws IOException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachment(PdfWriter, ArchivoEmbebidoDTO) - start"); //$NON-NLS-1$
    }

    File file = null;
    try {
      String extension = src.getNombreArchivo().substring(src.getNombreArchivo().lastIndexOf('.'));

      file = File.createTempFile(src.getNombreArchivo(), extension);

      org.apache.commons.io.FileUtils.writeByteArrayToFile(file, src.getDataArchivo());

      PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(writer, file.getAbsolutePath(),
          src.getNombreArchivo(), src.getDataArchivo());

      writer.addFileAttachment(
          src.getNombreArchivo().substring(0, src.getNombreArchivo().indexOf('.')), fs);

    } catch (Exception ex) {
      LOGGER.error("Mensaje de error", ex);

    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachment(PdfWriter, ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 1200, rollbackFor = Exception.class)
  public ResponseGenerarDocumento cerrarDocumento(RequestGenerarDocumento request,
      List<String> receptoresAviso) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - start"); //$NON-NLS-1$
    }

    ResponseGenerarDocumento returnResponseGenerarDocumento = super.cerrarDocumento(request,
        receptoresAviso);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - end"); //$NON-NLS-1$
    }
    return returnResponseGenerarDocumento;
  }

  public void generarDocumentoFirmadoConCertificado(RequestGenerarDocumento request,
      boolean subirTemporalAlfresco) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "generarDocumentoFirmadoConCertificado(RequestGenerarDocumento, boolean) - start"); //$NON-NLS-1$
    }

    super.generarDocumentoFirmadoConCertificado(request);

    if (LOGGER.isDebugEnabled()) {
      LOGGER
          .debug("generarDocumentoFirmadoConCertificado(RequestGenerarDocumento, boolean) - end"); //$NON-NLS-1$
    }
  }

  public ResponseGenerarDocumento generarDocumentoExterno(RequestGenerarDocumento request)
      throws ApplicationException, IOException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoExterno(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    ResponseGenerarDocumento returnResponseGenerarDocumento = super.generarDocumentoExterno(
        request);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoExterno(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return returnResponseGenerarDocumento;
  }

  public void generarDocumentoFirmadoConCertificadoManual(RequestGenerarDocumento request,
      boolean subirTemporal) throws ApplicationException, SecurityNegocioException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "generarDocumentoFirmadoConCertificadoManual(RequestGenerarDocumento, boolean) - start"); //$NON-NLS-1$
    }

    super.generarDocumentoFirmadoConCertificadoManual(request, subirTemporal);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "generarDocumentoFirmadoConCertificadoManual(RequestGenerarDocumento, boolean) - end"); //$NON-NLS-1$
    }
  }

  public void impactarEnBaseHistorialFirmantes(String workflowId, boolean esFirmaConjunta) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("impactarEnBaseHistorialFirmantes(String, boolean) - start"); //$NON-NLS-1$
    }

    super.impactarEnBaseHistorialFirmantes(workflowId, esFirmaConjunta);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("impactarEnBaseHistorialFirmantes(String, boolean) - end"); //$NON-NLS-1$
    }
  }

}
