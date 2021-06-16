package com.egoveris.te.base.service;

import com.egoveris.te.base.model.SubProcesoDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;

/**
 * Please consider using this interface for operations
 * related to subprocess
 */
public interface SubprocessService {
  
  /**
   * Returns a subprocess from an Expedient
   * (if any)
   * 
   * @param idEe
   * @return
   */
  public SubProcesoOperacionDTO getSubprocessFromEE(Long idEe);
  
  /**
   * Returns a subprocess from subprocess id
   * 
   * @param id
   * @return
   */
  public SubProcesoDTO getSubprocessFromId(Long id);
  
  /**
   * If there isn't a "SolProductOp" subprocess for given stateFlow / state
   * creates it. Else returns it
   * 
   * @param stateFlow
   * @param stateName
   * @Param username
   * 
   * @return the subprocess
   */
  public SubProcesoDTO getSolProductSubprocessForState(String stateFlow, String stateName, String username);
  
}
