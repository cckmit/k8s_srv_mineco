package com.egoveris.deo.base.dao;

import com.egoveris.deo.model.model.RegistroTemporalDTO;

import java.util.Date;
import java.util.List;

public interface DocumentoTemporalDao {

  List<RegistroTemporalDTO> obtenerRegistrosABorrar(Date fechaLimiteDocumentosTemporales);

  void borrarDocumentosTemporales(String workflowId);

  List<RegistroTemporalDTO> messageRequestARegistroTemporal(Date fechaLimiteDocumentosTemporales);

  List<RegistroTemporalDTO> obtenerRegistrosTemporalesPF(Date fechaLimiteDocumentosTemporales);
  
  public boolean existeJBPMVariable(String value);

}