package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.AvisoDTO;
import com.egoveris.deo.model.model.DocumentoDTO;

import java.util.List;
import java.util.Map;

public interface AvisoService {

  /**
   * Inserta uno o varios avisos en la bd.
   * 
   * @param receptores
   * @param documento
   * @param usuarioActual
   */

  public void guardarAvisos(List<String> receptores, DocumentoDTO documento, String usuarioActual);

  /**
   * Busca los avisos recibidos por el usuario dado, requiere de parámetros para
   * ordenamiento y paginación externa.
   * 
   * @param usuario:
   *          Usuario receptor
   * @param parametrosOrden:
   *          Parámetros para ordenamiento y paginación externa.
   * @return
   */
  public List<AvisoDTO> buscarAvisosPorUsuario(Map<String, Object> parametrosConsulta,
      Map<String, String> parametrosOrden);

  /**
   * Busca los avisos recibidos por el usuario dado y tipo de documento con
   * funcion especial Comunicable. requiere de parámetros para ordenamiento y
   * paginación externa.
   * 
   * @param usuario:
   *          Usuario receptor
   * @param parametrosOrden:
   *          Parámetros para ordenamiento y paginación externa.
   * @return
   */
  public List<AvisoDTO> buscarAvisosPorUsuarioYDocumentoComunicable(
      Map<String, Object> parametrosConsulta, Map<String, String> parametrosOrden);

  /**
   * Consulta el número de avisos registrados para este usuario.
   * 
   * @param usuario:
   *          Usuario receptor de avisos.
   * @return Número de avisos
   */
  public Integer numeroAvisosPorUsuario(String usuario);

  /**
   * Consulta el número de avisos registrados para este usuario.
   * 
   * @param usuario:
   *          Usuario receptor de avisos.
   * @return Número de avisos
   */
  public Integer numeroAvisosPorUsuarioYDocumentoComunicable(String usuario);

  /**
   * Borra de la base de datos los avisos recibidos por el usuario dado.
   * 
   * @param usuario:
   *          Usuario receptor.
   */

  public void eliminarAvisos(String usuario);

  /**
   * Permite redirigir los avisos seleccionados a otro usuario.
   * 
   * @param avisosRedirigir:
   *          Lista de avisos seleccionados
   * @param nuevoReceptor:
   *          Usuario al que se le van a re-dirigir los avisos.
   */

  public void redirigirAvisos(List<AvisoDTO> avisosRedirigir, String nuevoReceptor);

  /**
   * Elimina una lista de avisos
   * 
   * @param avisos:
   *          Lista de avisos a eliminar
   */

  public void eliminarAvisos(List<AvisoDTO> avisos);

  /**
   * Permite crear un aviso con un motivo particular, no se relaciona el
   * documento puesto que aplica para los casos en los que aún el documeno no ha
   * sido generado.
   * 
   * @param receptores:
   *          Lista de receptores del aviso.
   * @param datos:
   *          Map que almacena el motivo de la falla, y la referencia del
   *          documento.
   * @param usuarioActual
   *          Usuario que envía el aviso.
   */
  public void guardarAvisosFalla(List<String> receptores, Map<String, String> datos,
      String usuarioActual);

  /**
   * Permite crear un aviso con motivo: "RECHAZADO", cuando una tarea de firma
   * ha sido rechazada por un usuario, en el caso de que el tipo de documento
   * sea de firma conjunta será enviado a la lista de firmantes.
   * 
   * @param receptores
   * @param referencia
   * @param usuarioActual
   */

  public void guardarAvisosRechazo(List<String> receptores, Map<String, String> datos,
      String usuarioActual);

}