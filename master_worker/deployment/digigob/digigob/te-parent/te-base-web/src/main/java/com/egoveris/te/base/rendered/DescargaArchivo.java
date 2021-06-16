package com.egoveris.te.base.rendered;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.te.base.exception.DescargarDocumentoException;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;

import java.io.BufferedInputStream;
import java.io.File;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

public class DescargaArchivo extends GenericItemRender {

  private final static Logger logger = LoggerFactory.getLogger(DescargaArchivo.class);

  public DescargaArchivo() {
    super();
  }

  /**
   * Metodo que visuliza un documento oficial
   * 
   * @autor IES
   * 
   *        Servicios utilizados:IAccesoWebDavService visualizaDocumentoService
   *        Métodos utilizados del servicio:visualizarDocumento(String path)
   * 
   *        Variables importantes utilizadas:
   * 
   * @param String
   *          path :Cadena que se usa como parámetro en el método
   *          BufferedInputStream visualizarDocumento(String path) para busqueda
   *          del Archivo de trabajo, la cual se completa con:
   * 
   *          ° pathDocumentoDeTrabajo .- Ubicación del Documento de Trabajo. °
   *          nombreSpaceWebDav .- Nombre del Espacio WebDav. °
   *          archivoDeTrabajo.getNombreArchivo() .- Nombre del Archivo.
   * 
   * @param File
   *          fichero :Fichero utilizado para obtener el tipo de
   *          fichero(MimeType).
   * @param String
   *          tipoFichero : Tipo de fichero del Archivo.
   * @param BufferedInputStream
   *          file : Variable que recibe el resultado tipo Inputstream del
   *          Servicio IAccesoWebDavService visualizaDocumentoService.
   * 
   * 
   * 
   */
  public static void descargar(DocumentoDTO documento) throws DescargarDocumentoException {

    String numeroDocumento = documento.getNumeroSade();

    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    try {
      puedeDescargarDocumento(documento, loggedUsername);

      String numeroSadeConEspacio = numeroDocumento;
      String pathDocumento = "SADE";
      String fileName;
      if (ConstantesWeb.MEMORANDUM.equals(numeroDocumento.substring(0, 2))
          || ConstantesWeb.NOTA.equals(numeroDocumento.substring(0, 2))) {
        // Los documentos de comunicaciones caen sobre
        // guarda-documental/Actuacion/numeroSade
        String pathComunicaciones = BusinessFormatHelper
            .nombreCarpetaWebDavComunicaciones(numeroSadeConEspacio);
        fileName = pathDocumento + "/" + pathComunicaciones + "/" + documento.getNombreArchivo();
      } else {
        // Los documentos de gedo caen sobre
        // guarda-documental/numeroSade
        String pathGedo = BusinessFormatHelper.nombreCarpetaWebDavGedo(numeroSadeConEspacio);
        fileName = pathDocumento + "/" + pathGedo + "/" + documento.getNombreArchivo();
      }
      File fichero = new File(documento.getNumeroSade());
      String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);
      String nombreArchivo = documento.getNombreArchivo();
      BufferedInputStream file = getVisualizaDocumentoService().visualizarDocumento(fileName);
      Filedownload.save(file, tipoFichero, nombreArchivo);

    } catch (SinPrivilegiosException ex) {
      logger.error("error al descargar() - SinPrivilegiosException", ex);
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoReservadoNoVisualizable"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (DocumentoOArchivoNoEncontradoException e) {
      logger.error("error al descargar() - SinPrivilegiosException", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExisteEnRepositorio"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (Exception e) {
      logger.error("Error al descargar documento : ", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.errorDescargaDocumento"),
          Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.ERROR);

    }
  }

  /***
   * Valida si es posible descargar el documento GEDO, verifica si es reservado
   * y si el usuario tienen permisos para ver documentos confidenciales *
   * 
   * @param documento
   * @param loggedUsername
   * @throws SinPrivilegiosException
   * @throws ParametroInvalidoConsultaException
   * @throws DocumentoNoExisteException
   * @throws ErrorConsultaDocumentoException
   */
  private static void puedeDescargarDocumento(DocumentoDTO documento, String loggedUsername)
      throws SinPrivilegiosException, ParametroInvalidoConsultaException,
      DocumentoNoExisteException, ErrorConsultaDocumentoException {

    RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
    request.setNumeroDocumento(documento.getNumeroSade());
    request.setUsuarioConsulta(loggedUsername);

    verificarDocumentoSubsanado(documento, loggedUsername);
  }

  private static void verificarDocumentoSubsanado(DocumentoDTO documento, String loggedUsername)
      throws SinPrivilegiosException {
    if (documento.isSubsanado()) {
      if (!documento.getUsuarioSubsanador().equals(loggedUsername)) {
        throw new SinPrivilegiosException(loggedUsername);
      }
    }
  }
}
