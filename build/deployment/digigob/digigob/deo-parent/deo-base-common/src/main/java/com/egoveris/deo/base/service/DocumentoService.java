package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DocumentoDTO;

public interface DocumentoService {

  /**
   * 
   * @param numero
   * @return
   */
  public DocumentoDTO findByNumero(String numero);
  
  /**
   * 
   * @param documento
   */
  public void save(DocumentoDTO documento);
  
  public DocumentoDTO findByIdPublicable(String idPublicable);
}
