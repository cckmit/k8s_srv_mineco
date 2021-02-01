package com.egoveris.vucfront.base.service;

import com.egoveris.vucfront.model.model.GrupoDTO;
import com.egoveris.vucfront.model.model.TipoTramiteDTO;

import java.util.List;

/**
 * The Interface ApplicationService.
 */
public interface ApplicationService {

  /**
   * Gets the all Grupos.
   *
   * @return the all Grupos
   */
  List<GrupoDTO> getAllGrupos();

  /**
   * Gets the TipoTramite by id.
   *
   * @param TipoTramite
   *          id
   * @return TipoTramite
   */
  TipoTramiteDTO getTipoTramiteById(Long id);

  /**
   * Get's all the Tipo Tramite
   * 
   * @return
   */
  List<TipoTramiteDTO> getAllTipoTramite();
}
