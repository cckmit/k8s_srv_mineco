package com.egoveris.deo.base.service;

import java.io.IOException;
import java.util.List;

import org.terasoluna.plus.common.exception.ApplicationException;

import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.NumeracionEspecialNoInicializadaException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;

public interface GenerarDocumentoService {

  /**
   * 1. Obtener archivo temporal firmado de Alfresco. 2. Obtener numero sade.
   * (caratular). 3. Si tiene num. especial obtenerlo. 4. Actualizar campos
   * número del documento y fecha en archivo temporal. 5. Firmar con
   * certificado. 6. Borrar archivo temporal en alfresco.
   * 
   * @param request
   *          Datos necesarios para cerrar el documento.
   * @param receptoresAviso,
   *          lista de usuarios que solicitaron aviso de firma del documento.
   * @return Vacío.
   * @throws Exception
   * @throws NumeracionEspecialNoInicializadaException
   *           Si el numerador en cuestión no ha sido dado de alta en la lista
   *           de numeradores.
   */
  public ResponseGenerarDocumento cerrarDocumento(RequestGenerarDocumento request,
      List<String> receptoresAviso);

  /**
   * Actualiza el campo de sello correspondiente al usuario que firma, y firma
   * el documento con certificado de servidor.
   * 
   * @param request.
   *          Información del documento.
   * @param subirTemporalAlfresco.
   *          true para subir el archivo temporal a Alfresco, false se almacenan
   *          los bytes de éste en el objeto RequestGenerarDocumento. Esto con
   *          el fin de disminuir las conexiones a Alfresco cuando éstas no son
   *          necesarias.
   * @return
   * @throws Exception
   */
  public void generarDocumentoFirmadoConCertificado(RequestGenerarDocumento request,
      boolean subirTemporalAlfresco) throws ApplicationException;

  /**
   * Permite generar un documento y firmarlo con certificado. Utiliza el metodo
   * impactarEnBaseHistorialFirmantes.
   */

  public void generarDocumentoFirmadoConCertificadoManual(RequestGenerarDocumento request,
      boolean subirTemporal) throws SecurityNegocioException, ApplicationException;

  /**
   * Actualiza las tablas GEDO_HISTORIAL/ GEDO_FIRMANTES(si es Firma Conjunta).
   * Realiza Rollback en caso de fallar y no impacta los cambios en la base.
   */

  public void impactarEnBaseHistorialFirmantes(String workflowId, boolean esFirmaConjunta);

  /**
   * Genera un documento pdf con los campos de sello y firma.
   * 
   * @param request.
   *          Atributos obligatorios: data, tipoDocumento, motivo. Atributos
   *          opcionales: - tipoArchivo, requerido para la generación de
   *          documentos importados. - nombreArchivo, requerido para la
   *          generación manual interna de documentos.
   * @param numeroFirmas.
   * @param almacenarRepositorioTemporal.
   *          Indica si el archivo generado se almacena en el repositorio de
   *          manera temporal, o solo se mantiene en memoria en el objeto
   *          request.
   * @throws Exception
   */
  public void generarDocumentoPDF(RequestGenerarDocumento request, Integer numeroFirmas,
			Boolean almacenarRepositorioTemporal) throws IOException, ApplicationException;

  /**
   * Valida que el tipo de contenido de los datos este acorde al proceso de
   * generación que se solicita: - Generación Manual: "txt"; - Generación por
   * Importacion: Todos los mime-types permitidos por LiceCycle. - Generación
   * FirmaExterna: "pdf"
   * 
   * @param datos
   * @return String tipoContenido
   * @throws ValidacionContenidoException
   */
  public void validarContenidoDocumento(String tipoContenido) throws ValidacionContenidoException;

  /**
   * Realiza todas las validaciones y procesos requeridos para generar un
   * documento de manera automática sin interacción con tareas humanas. Las
   * operaciones que realiza dependen del tipo de generación requerida.(Manual,
   * Importacion, Firma Externa).
   * 
   * @param request
   * @return
   * @throws ValidacionContenidoException
   * @throws CantidadDatosException
   * @throws Exception
   */
  public ResponseGenerarDocumento generarDocumentoExterno(RequestGenerarDocumento request)
      throws IOException, ApplicationException;

  public void validarArchivoASubirPorSusFirmas(byte[] data)
      throws ValidacionCampoFirmaException, ArchivoFirmadoException;;
}
