package com.egoveris.te.ws.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.OperationAppDTO;
import com.egoveris.te.base.model.SubProcesoDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.model.SubprocessAppDTO;
import com.egoveris.te.base.model.SubprocessOpAppDTO;

public class OperationHelper {

  public static final String STR_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  private OperationHelper() {
    // Private constructor
  }
  
  /**
   * Busca en la lista de sectores el sector cuyo codigo de sector interno
   * coincida con el parametro dado
   * 
   * @param sectores
   *            Lista de sectores
   * @param codigoSectorInterno
   *            Codigo de sector interno
   * @return
   */
  public static Integer searchSectorListByCodigo(List<SectorInternoBean> sectores, String codigoSectorInterno) {
    Integer sector = null;

    if (sectores != null && codigoSectorInterno != null) {
      for (SectorInternoBean sectorInternoBean : sectores) {
        if (sectorInternoBean.getCodigo() != null
            && sectorInternoBean.getCodigo().equals(codigoSectorInterno)) {
          sector = sectorInternoBean.getId();
          break;
        }
      }
    }

    return sector;
  }
  
  public static OperationAppDTO mapToOperationAppDTO(OperacionDTO operacionDTO) {
    // dateFormat
    SimpleDateFormat sdf = new SimpleDateFormat(STR_DATE_FORMAT);
    
    // operation
    OperationAppDTO operationUser = new OperationAppDTO();
    // get data operation
    operationUser.setCode(operacionDTO.getNumOficial());
    // initDate
    operationUser.setInitDate(null == operacionDTO.getFechaInicio() ? null : sdf.format(operacionDTO.getFechaInicio()));
    // endDate
    operationUser.setEndDate(null == operacionDTO.getFechaFin() ? null : sdf.format(operacionDTO.getFechaFin()));
    // codigo
    operationUser.setOperationType(operacionDTO.getTipoOperacionOb().getCodigo());
    // bloked
    operationUser.setIsBloked(operacionDTO.getEstadoBloq());
    // status
    operationUser.setStatus(operacionDTO.getEstadoOperacion());
    // jbpmCode
    operationUser.setJbpmCode(operacionDTO.getJbpmExecutionId());
    
    return operationUser;
  }
  
  public static List<SubprocessOpAppDTO> mapToSubprocessAppDTO(List<SubProcesoOperacionDTO> subProcessOp) {
    // dateFormat
    SimpleDateFormat sdf = new SimpleDateFormat(STR_DATE_FORMAT);
    
    List<SubprocessOpAppDTO> subprocess = new ArrayList<>();
    
    if (null != subProcessOp && !subProcessOp.isEmpty()) {
      // iterate
      for (SubProcesoOperacionDTO subproIte : subProcessOp) {
        // subprocesAppDTO
        SubprocessOpAppDTO unqSub = new SubprocessOpAppDTO();
        // set code
        unqSub.setCode(subproIte.getExpediente().getIdWorkflow());
        // set initDate
        unqSub.setInitDate(null == subproIte.getExpediente().getFechaCreacion() ? null : sdf.format(subproIte.getExpediente().getFechaCreacion()));
        // set endDate
        unqSub.setEndDate(null == subproIte.getExpediente().getFechaModificacion() ? null : sdf.format(subproIte.getExpediente().getFechaModificacion()));
        // set status
        unqSub.setStatus(subproIte.getSubproceso().getStateName());
        // set lockType
        unqSub.setLockType(subproIte.getSubproceso().getLockType());
        // set subProcessType
        unqSub.setSubprocessType(subproIte.getSubproceso().getStartType());
        // add to list
        subprocess.add(unqSub);
      }
    }

    return subprocess;
  }
  
  public static List<SubprocessAppDTO> mapToSubprocessDTO(List<SubProcesoDTO> listSubprocessDTO) {
    List<SubprocessAppDTO> listSubprocessApp = new ArrayList<>();
    
    if (listSubprocessDTO != null && !listSubprocessDTO.isEmpty()) {
      for (SubProcesoDTO subprocessDTO : listSubprocessDTO) {
        SubprocessAppDTO subprocessAppDTO = new SubprocessAppDTO();
        subprocessAppDTO.setStateName(subprocessDTO.getStateName());
        subprocessAppDTO.setSubprocessCode(subprocessDTO.getId().toString());
        subprocessAppDTO.setStateFlow(subprocessDTO.getStateFlow());
        subprocessAppDTO.setProcedureType(subprocessDTO.getTramite().getCodigoTrata());
        subprocessAppDTO.setLockType(subprocessDTO.getLockType());
        subprocessAppDTO.setSubprocessType(subprocessDTO.getStartType());
        listSubprocessApp.add(subprocessAppDTO);
      }
    }
    
    return listSubprocessApp;
  }
  
}
