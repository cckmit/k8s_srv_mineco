package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReparticionHabilitadaService {

  /**
   * Carga las reparticiones habilitadas para el tipo de documento dado.
   * 
   * @param tipoDocumento
   * @return
   */
  public Set<ReparticionHabilitadaDTO> cargarReparticionesHabilitadas(
      TipoDocumentoDTO tipoDocumento);

  public boolean validacionParaIniciar(TipoDocumentoDTO tipoDocumento, String user);

  public boolean validacionParafirmar(String codigoReparticion, String user);

  /**
   * Adiciona a las reparticiones habilitadas, los números especiales en caso de
   * que sea requerido.
   * 
   * @param reparticionesHabilitadas
   * @param tipoDocumento
   */
  public void obtenerInformacionReparticiones(
      Set<ReparticionHabilitadaDTO> reparticionesHabilitadas);

  public Boolean existeReparticion(Map<String, Object> parametrosConsulta)
      throws EjecucionSiglaException;

  public List<ReparticionHabilitadaDTO> buscarReparticones(String reparticion)
      throws EjecucionSiglaException;

  /**
   * Valida los permisos de la repartición a la que pertenece un usuario
   * determinado.
   * 
   * @param tipoDocumento
   *          : Tipo de documento.
   * @param user:
   *          Usuario
   * @param permiso:
   *          Permiso que se requiere validar.
   * @return true si tiene permiso, false en caso contrario. Primera validacion:
   *         Se valida que la repartición del usuario esté entre las
   *         reparticiones habilitadas y tenga el permiso indicado. Segunda
   *         validación: Si no tiene permiso o no está, se verifican los
   *         permisos del registro que identifica a todas las reparticiones.
   */
  public boolean validarPermisoReparticion(TipoDocumentoDTO tipoDocumento, String user,
      String permiso);

  public boolean validarPermisosUsuariosDeSuListaReparticiones(TipoDocumentoDTO tipoDocumento,
      String user, String permiso);

  public void eliminarReparticionesTipoDocumento(TipoDocumentoDTO tipoDocumento);

  public void eliminarReparticiones(List<ReparticionHabilitadaDTO> listaReparticiones)
      throws EjecucionSiglaException;

  public void actualizarReparticionHabilitadaTipoDocumento(String reparticionOrigen,
      String reparticionDestino) throws EjecucionSiglaException;

  public void actualizarReparticionesHabilitadas(List<ReparticionHabilitadaDTO> listaReparticiones)
      throws EjecucionSiglaException;

  /**
   * Metodo propio del repository ReparticionHabilitadaRepository
   * 
   * @param tipoDocumento
   * @return
   */
  public List<ReparticionHabilitadaDTO> findByTipoDocumento(TipoDocumentoDTO tipoDocumento);
}