package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DocumentoTemporalDTO;
import com.egoveris.deo.model.model.ProcesoLogDTO;
import com.egoveris.deo.model.model.RegistroTemporalDTO;

import java.util.Date;
import java.util.List;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface BorradoTemporal {

  public void ejecutarBorrado(String workflowId);

  public ProcesoLogDTO guardarLog(String workflowId, String descripcion, String proceso,
      String estado, String sistemaOrigen);

  public void cambiarEstadoLog(ProcesoLogDTO log, String estado);

  public List<RegistroTemporalDTO> obtenerRegistrosABorrar(Date fechaLimiteDocumentosTemporales)
      throws ApplicationException;

  public DocumentoTemporalDTO obtenerDocumentosABorrar(String workflowId)
      throws ApplicationException;

  public String subirRegistrosABorrarLimpiezaTemporales(
      List<RegistroTemporalDTO> listRegistrosTemporalesABorrar) throws ApplicationException;

  public void subirALimpiezaTemporales(byte[] file, String nameFile) throws ApplicationException;

  public void borrarRegistrosTemporales(String workflowId) throws ApplicationException;

  public void borrarDocumentosTemporales(DocumentoTemporalDTO documentoTemporal)
      throws ApplicationException;

  public void limpiarCarpetasTemporalesWebdav() throws ApplicationException;

  public List<RegistroTemporalDTO> messageRequestARegistroTemporal(
      Date fechaLimiteDocumentosTemporales);

}
