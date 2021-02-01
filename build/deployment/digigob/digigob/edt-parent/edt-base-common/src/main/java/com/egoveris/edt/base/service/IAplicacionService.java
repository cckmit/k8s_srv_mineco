package com.egoveris.edt.base.service;

import com.egoveris.edt.base.model.eu.AplicacionDTO;

import java.util.List;

public interface IAplicacionService {

  /**
   * Gets all the applications.
   * 
   * @return List of applications
   */
  public List<AplicacionDTO> getAll();

  /**
   * Get an application by its name.
   * 
   * @param nombreAplicacion
   * @return Aplicacion
   */
  public AplicacionDTO getAplicacionPorNombre(String nombreAplicacion);

  /**
   * Get an application by its id.
   * 
   * @param idAplicacion
   * @return Aplicacion
   */
  public AplicacionDTO getAplicacionPorId(Integer idAplicacion);

  /**
   * Get all applications that are visible (visible = true).
   * 
   * @param userName
   * @return List of visible applications.
   */
  public List<AplicacionDTO> getTodasLasAplicaciones();

  /**
   * Get all visible applications that matches a List of given applications id.
   * 
   * @param List<Integer>
   *          listaIdAplicacion
   * @return List of applications
   */
  public List<AplicacionDTO> buscarAplicaciones(List<Integer> listaIdAplicacion);

}
