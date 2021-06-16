package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.exception.SinPersistirException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.List;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface FirmaConjuntaService {

  /**
   * Crea una lista de objetos de tipo Firmante para posteriormente guardarla en
   * la base de datos.
   * 
   * @param usuariosFirmantes:
   *          Lista de usuarios participes en la firma conjunta.
   * @param workflowId:
   *          Identificación del actual proceso de generación de documento.
   * @throws Exception
   */

  public void guardarFirmantes(List<Usuario> usuariosFirmantes, String workflowId)
      throws SinPersistirException;

  /**
   * Busca los usuarios registrados para firma conjunta, del proceso actual de
   * generación de documento.
   * 
   * @param workflowId:
   *          Identificador del proceso.
   * @return Una lista de perfiles de usuario.
   */
  public List<Usuario> buscarFirmantesPorProceso(String workflowId);

  public List<FirmanteDTO> buscarFirmantesPorTarea(String workflowId);

  /**
   * Busca los usuarios registrados para firma conjunta, del proceso actual de
   * generación de documento.
   * 
   * @param workflowId:
   *          Identificador del proceso.
   * @return Una lista de perfiles de usuario.
   */
  public List<Usuario> buscarRevisoresPorProceso(String workflowId);

  /**
   * Obtiene los usuarios firmantes por estado.
   * 
   * @param workflowId:
   *          Identificador del proceso actual.
   * @param estadoFirma:
   *          FALSE: Pendiente de firmar el documento, TRUE: Ya firmo el
   *          documento.
   * @return Listado con los usuarios firmantes acorde al estado.
   */
  public List<FirmanteDTO> obtenerFirmantesPorEstado(String workflowId, boolean estadoFirma);

  /**
   * Busca los usuarios firmantes por estado
   * 
   * @param workflowId:
   *          Identificador del proceso actual.
   * @param estadoFirma:
   *          false: Pendiente de firmar el documento, true: Ya firmo el
   *          documento.
   * @return Listado con los usuarios firmantes acorde al estado.
   */
  public List<Usuario> buscarFirmantesPorEstado(String workflowId, boolean estadoFirma);

  public List<String> buscarTareasPorFirmante(String usuario) throws EjecucionSiglaException;

  /**
   * Busca los usuarios revisores por estado
   * 
   * @param workflowId:
   *          Identificador del proceso actual.
   * @param estadoRevision:
   *          FALSE: Pendiente de revisar el documento, TRUE: Ya reviso el
   *          documento.
   * 
   * @return Listado con los usuarios firmantes acorde al estado.
   */
  public List<Usuario> buscarRevisoresPorEstado(String workflowId, boolean estadoRevision);

  /**
   * Permite actualizar el estado de firma de un usuario en un proceso
   * determinado.
   * 
   * @param usuario:
   *          Usuario firmante.
   * @param estadoFirma:
   *          false: Pendiente de firmar el documento, true: Ya firmo el
   *          documento.
   * @param workflowId:
   *          Identificador del proceso actual.
   * @throws Exception
   */

  public void actualizarFirmante(String usuario, boolean estadoFirma, String workflowId,
      String apoderado) throws ValidacionCampoFirmaException;

  /**
   * Permite actualizar el estado de revision de un usuario en un proceso
   * determinado.
   * 
   * @param usuario:
   *          Usuario revisor.
   * @param estadoRevision:
   *          FALSE: Pendiente de revisar el documento, TRUE: Ya reviso el
   *          documento.
   * @param workflowId:
   *          Identificador del proceso actual.
   * @throws Exception
   */

  public void actualizarRevisor(String usuario, boolean estadoRevision, String workflowId)
      throws ApplicationException;

  /**
   * Devuelve el objeto firmante, para un usuario determinado.
   * 
   * @param usuario:
   *          Usuario firmante
   * @param workflowId:
   *          Identificador del proceso actual.
   * @param estadoFirma:
   *          Estado de la firma, true si ya firmo, false en caso contrario.
   * @returns Objeto Firmante
   */
  public FirmanteDTO buscarFirmante(String usuario, String workflowId, boolean estadoFirma);

  /**
   * Devuelve el objeto firmante, para un usuario determinado.
   * 
   * @param usuario:
   *          Usuario firmante
   * @param workflowId:
   *          Identificador del proceso actual.
   * @param estadoRevision:
   *          Estado de la revision, true si ya reviso, false en caso contrario.
   * @returns Objeto Firmante
   */
  public FirmanteDTO buscarRevisorPorFirmante(String usuario, String workflowId,
      boolean estadoRevision);

  /**
   * Devuelve el objeto firmante, para un usuario determinado.
   * 
   * @param usuario:
   *          Usuario revisor
   * @param workflowId:
   *          Identificador del proceso actual.
   * @param estadoRevision:
   *          Estado de la revision, true si ya reviso, false en caso contrario.
   * @returns Objeto Firmante
   */
  public FirmanteDTO buscarRevisor(String usuario, String workflowId, boolean estadoRevision);

  /**
   * Identifica si el usuario dado, es el �ltimo firmante en un proceso
   * determinado.
   * 
   * @param usuario:
   *          Usuario firmante actual.
   * @param workflowId:
   *          Identificador del proceso.
   * @return
   */
  public boolean esUltimoFirmante(String usuarioFirmante, String workflowId, boolean estadoFirma);

  /**
   * Identifica si el usuario dado, es el último revisor en un proceso
   * determinado.
   * 
   * @param usuario:
   *          Usuario revisor actual.
   * @param workflowId:
   *          Identificador del proceso.
   * @return
   */
  public boolean esUltimoRevisor(String usuarioRevisor, String workflowId, boolean estadoRevision);

  /**
   * Actualiza la lista de firmantes asociadas al proceso actual.
   * 
   * @param usuariosFirmantes:
   *          Nueva lista de firmantes.
   * @param workflowId:
   *          Identificador del proceso actual.
   * @throws Exception
   */

  public void actualizarFirmantes(List<Usuario> usuariosFirmantes, String workflowId)
      throws ApplicationException;

  /**
   * Reemplaza el usuario firmante actual, por el nuevo usuario indicado
   * 
   * @param usuarioFirmanteActual:
   *          Usuario actual.
   * @param usuarioFirmanteNuevo:
   *          Nuevo usuario.
   * @param workflowId:
   *          Identificador del proceso.
   * @throws Exception
   */

  public void reemplazarFirmante(String usuarioFirmanteActual, String usuarioFirmanteNuevo,
      String workflowId) throws SinPersistirException;

  /**
   * Permite actualizar el estado de todos los firmantes pertenecientes a la
   * lista de un flujo determinado.
   * 
   * @param workflowId:
   *          Identificador del flujo.
   * @param estadoFirma:
   *          true: firmado, false: no firmado.
   * @throws Exception
   */

  public void actualizarEstadoFirmantes(String workflowId, boolean estadoFirma)
      throws SinPersistirException;

  /**
   * Permite actualizar el estado de todos los revisores pertenecientes a la
   * lista de un flujo determinado.
   * 
   * @param workflowId:
   *          Identificador del flujo.
   * @param estadoRevision:
   *          true: revisado, false: no revisado.
   * @throws Exception
   */

  public void actualizarEstadoRevisores(String workflowId, boolean estadoRevision)
      throws ApplicationException;

  /**
   * Permite obtener el orden del firmante
   */
  public Integer nroFirmaFirmante(String usuario, String workflowId, boolean estadoFirma);

  public List<FirmanteDTO> buscarUsuarioFirmantesPorProceso(String workflowId);

  public void eliminarRevisor(String usuario) throws EjecucionSiglaException;

  public String buscarUsuarioFirmante(String workflowId, boolean estadoFirma)
      throws EjecucionSiglaException;
  
  /**
   * Permite actualizar el estado de firma de un usuario en un proceso
   * determinado.
   * 
   * @param usuario:
   *          Usuario firmante.
   * @param estadoFirma:
   *          false: Pendiente de firmar el documento, true: Ya firmo el
   *          documento.
   * @param workflowId:
   *          Identificador del proceso actual.
   * @throws Exception
   */
  
  public void actualizaFirmante(String usuario, boolean estadoFirma, String workflowId) throws ValidacionCampoFirmaException;

  public List<FirmanteDTO> buscarRevisorFirmante(String workflowId);

}