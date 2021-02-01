package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ProcesoLogDTO;

import java.util.List;

public interface ProcesoLogService {

  /**
   * Accede a ProcesoLogServiceRepository
   * 
   * @param proceso
   * @param estado
   * @return
   */
  public List<ProcesoLogDTO> findByProcesoAndEstado(String proceso, String estado);
  
  /**
   * 
   * @param proceso
   */
  public void save(ProcesoLogDTO proceso);

}
