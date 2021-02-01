package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.iface.IAdministracionTrataService;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.ExpedienteMetadataExternal;
import com.egoveris.te.model.model.TipoReservaDTO;
import com.egoveris.te.model.model.TrataEE;

@Service
@Transactional
public class AdministracionTrataServiceImpl implements IAdministracionTrataService {
  private static final Logger logger = LoggerFactory
      .getLogger(AdministracionTrataServiceImpl.class);

  @Autowired
  TrataService trataService;

  public List<TrataEE> obtenerTratasDeEE() throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTratasDeEE() - start");
    }

    List<TrataEE> listaTratasEE = new ArrayList<>();
    
    try {
      // Cada trata en EE
      for (TrataDTO trataDTO : this.trataService.buscarTotalidadTratasEE()) {
        listaTratasEE.add(dtoToTrataEE(trataDTO));
      }
    } catch (Exception e) {
      logger.error("obtenerTratasDeEE()", e);
      
      throw new ProcesoFallidoException(
          "El proceso no ha podido llevarse a cabo a raíz de problemas de conexión con otros sistemas.",
          null);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTratasDeEE() - end - return value={}", listaTratasEE);
    }
    
    return listaTratasEE;
  }

  public TrataEE obtenerTrataDeEEPorCodigo(String codigoTrata) throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTrataDeEEPorCodigo(codigoTrata={}) - start", codigoTrata);
    }
    
    TrataEE trataEE = null;
    TrataDTO trataDTO = null;
    
    try {
      trataDTO = trataService.buscarTrataByCodigo(codigoTrata);
    } catch (Exception e) {
      logger.error("obtenerTrataDeEEPorCodigo(String)", e);

      throw new ProcesoFallidoException(
          "El proceso no ha podido llevarse a cabo a raíz de problemas de conexión con otros sistemas.",
          null);
    }

    if (trataDTO == null) {
      throw new ProcesoFallidoException("No se pudo obtener la trata con codigo: " + codigoTrata,
          null);
    } else {
      trataEE = dtoToTrataEE(trataDTO);

      if (logger.isDebugEnabled()) {
        logger.debug("obtenerTrataDeEEPorCodigo(String) - end - return value={}", trataEE);
      }
      
      return trataEE;
    }
  }
  
  @Override
  public List<TrataEE> buscarTratasEEByTipoSubproceso() throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasEEByTipoSubproceso() - start");
    }

    List<TrataEE> listaTratasEE = new ArrayList<>();
    
    try {
      // Cada trata en EE
      for (TrataDTO trataDTO : this.trataService.buscarTratasEEByTipoSubproceso()) {
        listaTratasEE.add(dtoToTrataEE(trataDTO));
      }
    } catch (Exception e) {
      logger.error("buscarTratasEEByTipoSubproceso()", e);
      
      throw new ProcesoFallidoException(
          "El proceso no ha podido llevarse a cabo a raíz de problemas de conexión con otros sistemas.",
          null);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTratasEEByTipoSubproceso() - end - return value={}", listaTratasEE);
    }
    
    return listaTratasEE;
  }
  
  private TrataEE dtoToTrataEE(TrataDTO trataDTO) {
    TrataEE trataEE = new TrataEE();
    
    trataEE.setId(trataDTO.getId());
    trataEE.setCodigoTrata(trataDTO.getCodigoTrata());
    trataEE.setDescripcionTrata(trataDTO.getDescripcion());
    trataEE.setEstado(trataDTO.getEstado());
    
    TipoReservaDTO tipoRes = new TipoReservaDTO();
    tipoRes.setId(trataDTO.getTipoReserva().getId());
    tipoRes.setTipoReserva(trataDTO.getTipoReserva().getTipoReserva());
    tipoRes.setDescripcion(trataDTO.getTipoReserva().getDescripcion());

    trataEE.setTipoReserva(tipoRes);
    trataEE.setEsAutomatica(trataDTO.getEsAutomatica());
    trataEE.setEsManual(trataDTO.getEsManual());

    // Cada uno de sus metadatas
    List<ExpedienteMetadataExternal> listaMetadataEE = new ArrayList<>();
    
    for (MetadataDTO metadata : trataDTO.getDatoVariable()) {
      ExpedienteMetadataExternal metadataEE = new ExpedienteMetadataExternal();
      metadataEE.setNombreMetadata(metadata.getNombre());
      metadataEE.setObligatoriedadMetadata(metadata.isObligatoriedad());
      metadataEE.setOrdenMetadata(metadata.getOrden());
      metadataEE.setTipoMetadata(metadata.getTipo());
      
      listaMetadataEE.add(metadataEE);
    }
    
    trataEE.setDatoVariable(listaMetadataEE);
    trataEE.setAcronimoGedo(trataDTO.getAcronimoDocumento());
    
    return trataEE;
  }

}
