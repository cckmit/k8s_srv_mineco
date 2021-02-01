package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.service.ConversorPdf;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosEmbebidosWebDavService;
import com.egoveris.deo.base.util.DocumentoUtil;
import com.egoveris.deo.base.util.DocumentoUtilImpl;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.util.Constantes;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
public class GenerarDocumentoImportado extends GenerarDocumentoServiceImpl
    implements GenerarDocumentoService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(GenerarDocumentoImportado.class);

  @Autowired
  private ConversorPdf conversorPdf;
  @Autowired
  protected DocumentoRepository documentoRepo;
  @Autowired
  GestionArchivosEmbebidosWebDavService gestionArchivosEmbebidosWebDabService;
  @Value("${app.sistema.gestor.documental}")
  private String gestorDocumental;

  @Autowired
  private DocumentoUtil documentoUtil;
  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.impl.GenerarDocumentoServiceImpl#
   * validacionContenidoDocumento(byte[])
   */
  public void validarContenidoDocumento(String tipoContenido) throws ValidacionContenidoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarContenidoDocumento(String) - start"); //$NON-NLS-1$
    }

    if (!Constantes.extensionesPermitidas.contains(tipoContenido))
      throw new ValidacionContenidoException(
          "El contenido del documento no es consistente, con la operación solicitada, "
              + "este debe ser de tipo soportado por liveCycle para la conversión");

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarContenidoDocumento(String) - end"); //$NON-NLS-1$
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.impl.GenerarDocumentoServiceImpl#
   * generarDocumentoPDF(com.egoveris.deo.core.api.satra.services.io.
   * RequestGenerarDocumento, java.lang.Integer, java.lang.Boolean)
   */
  public void generarDocumentoPDF(RequestGenerarDocumento request, Integer numeroFirmas,
      Boolean almacenarRepositorioTemporal) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoPDF(RequestGenerarDocumento, Integer, Boolean) - start"); //$NON-NLS-1$
    }

    byte[] contenidoArchivo = null;
    String nombreArchivoTemporal = null;
    try {
      if (request.getNombreArchivo() == null) {

        // WEBDAV
        nombreArchivoTemporal = this.gestionArchivosWebDavService.crearNombreArchivoTemporal();

      } else {
        nombreArchivoTemporal = request.getNombreArchivo();
      }
      request.setNombreArchivo(nombreArchivoTemporal.toString());

      contenidoArchivo = request.getData();

      if (request.getTipoDocumentoGedo().getEsComunicable()) {
        contenidoArchivo = pdfService.adicionarCamposNuevaPagina(contenidoArchivo, numeroFirmas,
            request.getTipoDocumentoGedo().getAcronimo(), request.getMotivo(), request);
      } else {
        contenidoArchivo = pdfService.adicionarCamposNuevaPagina(contenidoArchivo, numeroFirmas,
            request.getTipoDocumentoGedo().getAcronimo(), request.getMotivo());
      }

      if (request.getListaArchivosEmbebidos() != null
          && request.getListaArchivosEmbebidos().size() > 0) {
        contenidoArchivo = this.addAttachments(contenidoArchivo,
            request.getListaArchivosEmbebidos());
      }

	  // Agrego QR
	  if (request != null && request.getTipoDocumentoGedo() != null
				&& request.getTipoDocumentoGedo().getEsPublicable()) {
		  contenidoArchivo = this.documentoUtil.agregarCodigoQRDocumentoPublicable(contenidoArchivo, request);
	  }    
		
      if (almacenarRepositorioTemporal) {
        // WEBDAV
        super.gestionArchivosWebDavService
            .subirArchivoTemporalWebDav(nombreArchivoTemporal.toString(), contenidoArchivo);

      }  
      
      request.setData(contenidoArchivo);
    } catch (Exception e) {
      LOGGER.error("Error creando el documento en importacion", e);
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
      String extension = src.getNombreArchivo().substring(src.getNombreArchivo().lastIndexOf("."));

      file = File.createTempFile(src.getNombreArchivo(), extension);

      org.apache.commons.io.FileUtils.writeByteArrayToFile(file, src.getDataArchivo());

      PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(writer, file.getAbsolutePath(),
          src.getNombreArchivo(), src.getDataArchivo());

      writer.addFileAttachment(
          src.getNombreArchivo().substring(0, src.getNombreArchivo().indexOf(".")), fs);

    } catch (Exception ex) {
      LOGGER.error("Mensaje de error", ex);

    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("addAttachment(PdfWriter, ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.impl.GenerarDocumentoServiceImpl#
   * cerrarDocumento(com.egoveris.deo.core.api.satra.services.io.
   * RequestGenerarDocumento, java.util.List)
   */
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

  public ResponseGenerarDocumento generarDocumentoExterno(RequestGenerarDocumento request)
      throws ValidacionContenidoException, CantidadDatosException, ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoExterno(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    ResponseGenerarDocumento returnResponseGenerarDocumento = null;
    try {
      returnResponseGenerarDocumento = super.generarDocumentoExterno(request);
    } catch (IOException e) {
      LOGGER.error("Error al generar documento externo", e);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarDocumentoExterno(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return returnResponseGenerarDocumento;
  }

  @Override
  public void validarArchivoASubirPorSusFirmas(byte[] data)
      throws ValidacionCampoFirmaException, ArchivoFirmadoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarArchivoASubirPorSusFirmas(byte[]) - start"); //$NON-NLS-1$
    }

    try {
      if (super.pdfService.estaFirmadoOConEspaciosDeFirma(data)) {
        throw new ArchivoFirmadoException(
            "El Archivo se encuentra firmado o con espacios de Firma, "
                + "por lo tanto no es compatible con este Tipo de Documento");
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
    // TODO Auto-generated method stub

  }
}
