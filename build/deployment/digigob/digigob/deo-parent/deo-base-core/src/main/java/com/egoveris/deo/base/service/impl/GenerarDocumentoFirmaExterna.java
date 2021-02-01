package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.NumeracionEspecialNoInicializadaException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.util.DocumentoUtil;
import com.egoveris.deo.base.util.UtilitariosServicios;
import com.egoveris.deo.base.util.UtilsDate;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.util.Constantes;
import com.egoveris.numerador.model.model.NumeroDTO;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.terasoluna.plus.common.exception.ApplicationException;
@Service
public class GenerarDocumentoFirmaExterna extends GenerarDocumentoServiceImpl
    implements GenerarDocumentoService {

  @Value("${app.sistema.gestor.documental}")
  private String gestorDocumental;
  @Autowired
  private DocumentoRepository documentoRepo;

  private static final Logger logger = LoggerFactory.getLogger(GenerarDocumentoFirmaExterna.class);

  @Value("${app.ecosistema}")
  private String ecosistema;

  @Autowired
  private DocumentoUtil documentoUtil;
  
  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.impl.GenerarDocumentoServiceImpl#
   * validacionContenidoDocumento(java.lang.String)
   */
  public void validarContenidoDocumento(String tipoContenido) throws ValidacionContenidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarContenidoDocumento(String) - start"); //$NON-NLS-1$
    }

    if (tipoContenido.compareTo(Constantes.CONTENIDO_GENEARCION_FIRMA_EXTERNA) != 0)
      throw new ValidacionContenidoException(
          "El contenido del documento no es consistente, con la operación solicitada, "
              + "este debe ser pdf");

    if (logger.isDebugEnabled()) {
      logger.debug("validarContenidoDocumento(String) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Valida que un arreglo de bytes que representa un archivo pdf, tiene al
   * menos un campo de firma lleno.
   * 
   * @param data
   * @throws ValidacionCampoFirmaException
   */
  private void validarCamposFirma(byte[] data) throws ValidacionCampoFirmaException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarCamposFirma(byte[]) - start"); //$NON-NLS-1$
    }

    try {
      super.pdfService.validarCamposFirma(data);
    } catch (ValidacionCampoFirmaException e) {
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarCamposFirma(byte[]) - end"); //$NON-NLS-1$
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.egoveris.deo.core.api.services.GenerarDocumentoService#
   * generarDocumentoPDF
   * (com.egoveris.deo.core.api.satra.services.io.RequestGenerarDocumento,
   * java.lang.Integer, java.lang.Boolean)
   */
  public void generarDocumentoPDF(RequestGenerarDocumento request, Integer numeroFirmas,
      Boolean almacenarRepositorioTemporal) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoPDF(RequestGenerarDocumento, Integer, Boolean) - start"); //$NON-NLS-1$
    }

    String nombreArchivoTemporal = null;
    /**
     * TODO Acorde a la funcionalidad actual de firma externa, no se requiere
     * modificar el documento original, por lo que pierde sentido la
     * implementación de este método, queda para usos futuros si el documento va
     * a tener algún tratamiento adicional.
     */
    try {
    	
  	  // Agrego QR
  	  if (request != null && request.getTipoDocumentoGedo() != null
  				&& request.getTipoDocumentoGedo().getEsPublicable()) {
  		  byte[] contenidoArchivo = this.documentoUtil.agregarCodigoQRDocumentoPublicable(request.getData(), request);
  		  request.setData(contenidoArchivo);
  	  }  
      if (almacenarRepositorioTemporal) {
        
          // WEBDAV
          nombreArchivoTemporal = this.gestionArchivosWebDavService.crearNombreArchivoTemporal();
          super.gestionArchivosWebDavService.subirArchivoTemporalWebDav(nombreArchivoTemporal,
              request.getData());
          request.setNombreArchivo(nombreArchivoTemporal);
        
      }
    } catch (Exception e) {
      logger.error("Error creando el documento de firma Externa", e);
      throw (ApplicationException)e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoPDF(RequestGenerarDocumento, Integer, Boolean) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Generación externa de documentos con firma externa, el cual esta conformado
   * por los siguientes pasos: 1. Validación tamaño del contenido. 2. Validación
   * del contenido del documento. 3. Validación que asegure que el archivo
   * subido no este firmado. 4. Actualmente no se está modificando el documento
   * original por lo que no se invoca al método que genera el documento. 5.
   * Cierre del documento.
   * 
   * @param request
   * @return
   * @throws ValidacionContenidoException
   * @throws CantidadDatosException
   * @throws Exception
   */
  public ResponseGenerarDocumento generarDocumentoExterno(RequestGenerarDocumento request)
      throws ValidacionContenidoException, ArchivoFirmadoException, CantidadDatosException,
      ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoExterno(RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    ResponseGenerarDocumento response;
    validarTamanioContenido(request.getData());
    validarContenidoTipoDocumento(request);
    if (request.getTipoArchivo().equalsIgnoreCase("pdf"))
      validarArchivoASubirPorSusFirmas(request.getData());
    validarCamposFirma(request.getData());
    // generarDocumentoPDF(request,1,false);
    response = this.cerrarDocumento(request, null);

    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoExterno(RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return response;
  }

  /**
   * 1. Obtener numero sade. (caratular). 2. Si tiene num. especial obtenerlo.
   * 3. Se almacena documento en repositorio.
   * 
   * @param request
   *          Datos necesarios para cerrar el documento.
   * @param receptoresAviso
   *          , lista de usuarios que solicitaron aviso de firma del documento.
   * @return Vacío.
   * @throws Exception
   * @throws NumeracionEspecialNoInicializadaException
   *           Si el numerador en cuestión no ha sido dado de alta en la lista
   *           de numeradores.
   */

  @Override
  public ResponseGenerarDocumento cerrarDocumento(RequestGenerarDocumento request,
      List<String> receptoresAviso) throws ApplicationException {
    if (logger.isDebugEnabled()) {
      logger.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO numeroEspecial = null;
    ResponseGenerarDocumento responseGenerarDocumento = null;
    NumerosUsadosDTO numerosUsados = null;
    NumeroDTO numeroSecuencia = null;
    try {
      responseGenerarDocumento = new ResponseGenerarDocumento();

      // Desde la aplicacion se busca el recurso temporal.
      // Desde el WS se toma los datos del request.
      if (request.getData() == null) {

        // WEBDAV
        request.setData(this.gestionArchivosWebDavService
            .obtenerRecursoTemporalWebDav(request.getNombreArchivo()));

      }

      byte[] contenidoTemporal = request.getData();

      // Bloquear número especial.
      if (Boolean.TRUE.equals(request.getTipoDocumentoGedo().getEsEspecial())) {
        numeroEspecial = super.obtenerNumeracionEspecialService.bloquearNumeroEspecial(request);
      }
      if (org.apache.commons.lang.StringUtils.isEmpty(request.getNumero())) {
        numeroSecuencia = super.obtieneNumeracionSade(request);
        String numeroSade = UtilitariosServicios.armarCodigoCaratula(
            request.getTipoDocumentoGedo().getCodigoTipoDocumentoSade(),
            numeroSecuencia.getNumero(), numeroSecuencia.getAnio(), request.getCodigoReparticion(),
            getEcosistema());
        request.setNumero(numeroSade);
        responseGenerarDocumento.setNumero(numeroSade);
      }
      responseGenerarDocumento.setCodigoReparticion(request.getCodigoReparticion());
      if (Boolean.TRUE.equals(request.getTipoDocumentoGedo().getEsEspecial())) {
        responseGenerarDocumento.setNumeroEspecial(numeroEspecial);
        request.setNumeroEspecial(numeroEspecial);
        numerosUsados = super.crearNumerosUsados(request, UtilsDate.obtenerAnioActual());
        super.obtenerNumeracionEspecialService.guardar(numerosUsados);
        logger.debug("numero SADE guardado " + numerosUsados.getNumeroSADE() + " usuario: "
            + request.getUsuario());
      }
      request.setData(contenidoTemporal);

      // WEBDAV
      String urlArchivoGenerado = this.gestionArchivosWebDavService.subirDocumentoWebDav(request);
      responseGenerarDocumento.setUrlArchivoGenerado(urlArchivoGenerado);
      // Para la copia de archivos de trabajo, se valida que el
      // requerimiento tenga un id de workflow asociado.
      super.cerrarDocumentoService.guardarCierreDocumento(request, receptoresAviso, numerosUsados);

      // Confirma en numero pedido al numerador
      // No propaga la excepción ya que en ese caso se estaría cancelando
      // la generación del documento
      // que ya fue confirmada por el método anterior
      // cerrarDocumentoService.guardarCierreDocumento
      // Existe un proceso batch que regulariza cualquier número que no
      // fue confirmado
      try {
        if (numeroSecuencia != null) {
          this.numeracionSadeService.confirmarNumeroSADE(numeroSecuencia.getAnio(),
              numeroSecuencia.getNumero());
        }

      } catch (Exception exNumerador) {
        if (numeroSecuencia != null) {
          logger.error("Error al confirmar el número " + numeroSecuencia.getAnio() + "-"
              + numeroSecuencia.getNumero() + ": " + exNumerador.getMessage(), exNumerador);
        } else {
          logger.error("Error al confirmar el número ");
        }

      }
    } catch (Exception e) {
      // Anula el numero solicitado al numerador en caso que se produzca
      // alguna excepción
      try {
        if (numeroSecuencia != null) {
          this.numeracionSadeService.anularNumeroSADE(numeroSecuencia.getAnio(),
              numeroSecuencia.getNumero());
        }

      } catch (Exception numerador) {
        if (numeroSecuencia != null) {
          logger.error("Error al anular el número " + numeroSecuencia.getAnio() + "-"
              + numeroSecuencia.getNumero() + ": " + e.getMessage(), numerador);
        }
      }
      // Valida si ya se subió el archivo al repositorio -> Borrar archivo
      if (responseGenerarDocumento != null
          && responseGenerarDocumento.getUrlArchivoGenerado() != null) {
        try {

          // SADE
          this.gestionArchivosWebDavService
              .borrarArchivoWebDav(responseGenerarDocumento.getNumero());

        } catch (ApplicationException ex) {
          logger.error("cerrarDocumento - No se pudo borrar archivo del repositorio " + ex, ex);
        }
      }

      // Valida si se adquirió el lock del número especial -> Desbloquear
      // número especial.
      if (this.ecosistema.trim().isEmpty()) {
        this.obtenerNumeracionEspecialService.rollbackNumeroEspecial(numeroEspecial);
      } else {
        this.obtenerNumeracionEspecialService.rollbackNumeroEspecialEcosistema(numeroEspecial);
      }
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cerrarDocumento(RequestGenerarDocumento, List<String>) - end"); //$NON-NLS-1$
    }
    return responseGenerarDocumento;
  }

  @Override
  public void validarArchivoASubirPorSusFirmas(byte[] data)
      throws ValidacionCampoFirmaException, ArchivoFirmadoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarArchivoASubirPorSusFirmas(byte[]) - start"); //$NON-NLS-1$
    }

    try {
      if (!super.pdfService.estaFirmado(data)) {
        throw new ArchivoFirmadoException("El Archivo no se encuentra firmado, "
            + "por lo tanto no es compatible con este Tipo de Documento");
      }
    } catch (ValidacionCampoFirmaException e) {
      throw e;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarArchivoASubirPorSusFirmas(byte[]) - end"); //$NON-NLS-1$
    }
  }

  public void setEcosistema(String ecosistema) {
    this.ecosistema = ecosistema;
  }

  public String getEcosistema() {
    return ecosistema;
  }

  @Override
  public void generarDocumentoFirmadoConCertificado(RequestGenerarDocumento request,
      boolean subirTemporalAlfresco) throws ApplicationException {    
  }

}
