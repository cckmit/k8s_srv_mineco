/**
 * 
 */
package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.model.NumeracionEspecial;
import com.egoveris.deo.base.model.NumeracionEspecialAuditoria;
import com.egoveris.deo.base.repository.NumeracionEspecialRepository;
import com.egoveris.deo.base.repository.NumeroEspecialAuditoriaRepository;
import com.egoveris.deo.base.service.NumeracionEspecialService;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pfolgar
 *
 */
@Service
@Transactional
public class NumeracionEspecialServiceImpl implements NumeracionEspecialService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory
      .getLogger(NumeracionEspecialServiceImpl.class);

  @Autowired
  private NumeracionEspecialRepository numeracionEspecialRepo;
  @Autowired
  private NumeroEspecialAuditoriaRepository numeracionEspecialAuditoriaRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public NumeracionEspecialDTO buscarNumeracionEspecial(String reparticion,
      TipoDocumentoDTO tipoDocumento, String anio) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarNumeracionEspecial(String, TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO returnNumeracionEspecialDTO = mapper.map(
        this.numeracionEspecialRepo.findByCodigoReparticionAndIdTipoDocumentoAndAnio(reparticion,
            tipoDocumento.getId(), anio),
        NumeracionEspecialDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarNumeracionEspecial(String, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
    return returnNumeracionEspecialDTO;
  }

  @Override
  public NumeracionEspecialDTO buscarNumeracionEspecialEcosistema(String reparticion,
      TipoDocumentoDTO tipoDocumento, String anio, String ecosistema) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "buscarNumeracionEspecialEcosistema(String, TipoDocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO returnNumeracionEspecialDTO = mapper.map(this.numeracionEspecialRepo
        .findByCodigoReparticionAndIdTipoDocumentoAndAnioAndCodigoEcosistema(reparticion,
            tipoDocumento.getId(), anio, ecosistema),
        NumeracionEspecialDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "buscarNumeracionEspecialEcosistema(String, TipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
    return returnNumeracionEspecialDTO;
  }

  @Override
  public Boolean existeNumeracionEspecial(Map<String, Object> parametrosConsulta)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumeracionEspecial(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    NumeracionEspecial result = this.numeracionEspecialRepo
        .findByCodigoReparticionAndIdTipoDocumentoAndAnioAndCodigoEcosistemaIsNull(
            (String) parametrosConsulta.get("reparticion"),
            (Integer) parametrosConsulta.get("tipoDocumento"),
            (String) parametrosConsulta.get("anio"));

    if (result != null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("existeNumeracionEspecial(Map<String,Object>) - end"); //$NON-NLS-1$
      }
      return true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumeracionEspecial(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return false;
  }

  @Override
  public Boolean existeNumeracionEspecialEcosistema(Map<String, Object> parametrosConsulta)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumeracionEspecialEcosistema(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    NumeracionEspecial result = this.numeracionEspecialRepo
        .findByCodigoReparticionAndIdTipoDocumentoAndAnioAndCodigoEcosistema(
            (String) parametrosConsulta.get("reparticion"),
            (Integer) parametrosConsulta.get("tipoDocumento"),
            (String) parametrosConsulta.get("anio"),
            (String) parametrosConsulta.get("codEcosistema"));
    if (result != null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("existeNumeracionEspecialEcosistema(Map<String,Object>) - end"); //$NON-NLS-1$
      }
      return true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumeracionEspecialEcosistema(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return false;
  }

  @Override
  public NumeracionEspecialDTO buscarNumeracionEspecialById(Integer id) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarNumeracionEspecialById(Integer) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO returnNumeracionEspecialDTO = mapper
        .map(this.numeracionEspecialRepo.findById(id), NumeracionEspecialDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarNumeracionEspecialById(Integer) - end"); //$NON-NLS-1$
    }
    return returnNumeracionEspecialDTO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NumeracionEspecialDTO> buscarNumeracionEspecialByReparticion(
      Map<String, Object> parametrosConsulta) throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarNumeracionEspecialByReparticion(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    List<NumeracionEspecialDTO> returnList = ListMapper.mapList(
        numeracionEspecialRepo.findByAnioAndCodigoReparticion(
            (String) parametrosConsulta.get("anio"),
            (String) parametrosConsulta.get("reparticion")),
        this.mapper, NumeracionEspecialDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarNumeracionEspecialByReparticion(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public void grabarNumeracionEspecial(NumeracionEspecialDTO numeracionEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarNumeracionEspecial(NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    this.numeracionEspecialRepo.save(mapper.map(numeracionEspecial, NumeracionEspecial.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarNumeracionEspecial(NumeracionEspecialDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void grabarNumeracionEspecialEcosistema(NumeracionEspecialDTO numeracionEspecial,
      String codEcosistema) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarNumeracionEspecialEcosistema(NumeracionEspecialDTO, String) - start"); //$NON-NLS-1$
    }

    numeracionEspecial.setCodigoEcosistema(codEcosistema);
    this.numeracionEspecialRepo.save(mapper.map(numeracionEspecial, NumeracionEspecial.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarNumeracionEspecialEcosistema(NumeracionEspecialDTO, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public boolean existeNumeradorEcosistema(String reparticion, TipoDocumentoDTO tipoDocumento,
      String anio, String codEcosistema) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumeradorEcosistema(String, TipoDocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    NumeracionEspecial result = this.numeracionEspecialRepo
        .findByCodigoReparticionAndIdTipoDocumentoAndAnioAndCodigoEcosistema(reparticion,
            tipoDocumento.getId(), anio, codEcosistema);
    if (result != null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("existeNumeradorEcosistema(String, TipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
      }
      return true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumeradorEcosistema(String, TipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
    return false;
  }

  @Override
  public boolean existeNumerador(String reparticion, TipoDocumentoDTO tipoDocumento, String anio) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumerador(String, TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    NumeracionEspecial result = this.numeracionEspecialRepo
        .findByCodigoReparticionAndIdTipoDocumentoAndAnioAndCodigoEcosistemaIsNull(reparticion,
            tipoDocumento.getId(), anio);
    if (result != null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("existeNumerador(String, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
      }
      return true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existeNumerador(String, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
    return false;
  }

  @Override
  public void lockNumeroEspecial(String reparticion, TipoDocumentoDTO tipoDocumento, String anio) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("lockNumeroEspecial(String, TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    Long timeNow = new Date().getTime();
    this.numeracionEspecialRepo.lockNumeroEspecial(timeNow, anio, tipoDocumento.getId(),
        reparticion, timeNow);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("lockNumeroEspecial(String, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void lockNumeroEspecialEcosistema(String reparticion, TipoDocumentoDTO tipoDocumento,
      String anio, String ecosistema) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER
          .debug("lockNumeroEspecialEcosistema(String, TipoDocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    Long timeNow = new Date().getTime();
    this.numeracionEspecialRepo.lockNumeroEspecialEcosistema(timeNow, anio, ecosistema,
        tipoDocumento.getId(), reparticion, timeNow);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("lockNumeroEspecialEcosistema(String, TipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void unlockNumeroEspecial(NumeracionEspecialDTO nEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("unlockNumeroEspecial(NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    this.numeracionEspecialRepo.unlockNumeroEspecial(nEspecial.getAnio(),
        nEspecial.getIdTipoDocumento(), nEspecial.getCodigoReparticion());

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("unlockNumeroEspecial(NumeracionEspecialDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void unlockNumeroEspecialEcosistema(NumeracionEspecialDTO nEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("unlockNumeroEspecialEcosistema(NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    this.numeracionEspecialRepo.unlockNumeroEspecialEcosistema(nEspecial.getAnio(),
        nEspecial.getIdTipoDocumento(), nEspecial.getCodigoEcosistema(),
        nEspecial.getCodigoReparticion());

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("unlockNumeroEspecialEcosistema(NumeracionEspecialDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public NumeracionEspecialDTO buscarUltimoNumeroEspecial(String reparticion,
      TipoDocumentoDTO tipoDocumento, String codEcosistema) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUltimoNumeroEspecial(String, TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialDTO result = null ;
    if (codEcosistema == null || codEcosistema.isEmpty()) {
    	NumeracionEspecial numeEnti = this.numeracionEspecialRepo
    	          .findByCodigoReparticionAndIdTipoDocumentoAndCodigoEcosistemaIsNullOrderByAnio(
    	                  reparticion, tipoDocumento.getId());
    	result = numeEnti != null ? mapper.map(numeEnti,
    	          NumeracionEspecialDTO.class) : null;
    } else {
    	NumeracionEspecial numerpEspEnt = this.numeracionEspecialRepo
    	          .findByCodigoReparticionAndIdTipoDocumentoAndCodigoEcosistemaOrderByAnio(reparticion,
    	                  tipoDocumento.getId(), codEcosistema);
    		result = numerpEspEnt != null ? mapper.map(numerpEspEnt,
          NumeracionEspecialDTO.class) : null;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarUltimoNumeroEspecial(String, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
    return result;
  }

  @Override
  public void guardar(NumeracionEspecialDTO numeracionEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardar(NumeracionEspecialDTO) - start"); //$NON-NLS-1$
    }

    this.numeracionEspecialRepo
        .save(this.mapper.map(numeracionEspecial, NumeracionEspecial.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardar(NumeracionEspecialDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void numeracionEspecialAuditoria(NumeracionEspecialDTO numeracionEspecial, String usuario,
      String operacion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("numeracionEspecialAuditoria(NumeracionEspecialDTO, String, String) - start"); //$NON-NLS-1$
    }

    NumeracionEspecialAuditoria numeracionEspecialAuditoria = new NumeracionEspecialAuditoria();
    numeracionEspecialAuditoria.setCodigoReparticion(numeracionEspecial.getCodigoReparticion());
    numeracionEspecialAuditoria.setIdTipoDocumento(numeracionEspecial.getIdTipoDocumento());
    numeracionEspecialAuditoria.setAnio(numeracionEspecial.getAnio());
    numeracionEspecialAuditoria.setNumero(numeracionEspecial.getNumero());
    numeracionEspecialAuditoria.setUserName(usuario);
    numeracionEspecialAuditoria.setTipoOperacion(operacion);
    this.numeracionEspecialAuditoriaRepo.save(numeracionEspecialAuditoria);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("numeracionEspecialAuditoria(NumeracionEspecialDTO, String, String) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void actualizarNumeracionEspecial(List<NumeracionEspecialDTO> listaNumeraciones)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarNumeracionEspecial(List<NumeracionEspecialDTO>) - start"); //$NON-NLS-1$
    }

    numeracionEspecialRepo
        .save(ListMapper.mapList(listaNumeraciones, this.mapper, NumeracionEspecial.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarNumeracionEspecial(List<NumeracionEspecialDTO>) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void eliminarNumeracionEspecial(TipoDocumentoDTO tipoDocumento, String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarNumeracionEspecial(TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    List<NumeracionEspecial> numerosEspeciales = this.numeracionEspecialRepo
        .findByIdTipoDocumento(tipoDocumento.getId());
    this.numeracionEspecialRepo.delete(numerosEspeciales);
    List<NumeracionEspecialDTO> nEspeciales = ListMapper.mapList(numerosEspeciales, this.mapper,
        NumeracionEspecialDTO.class);
    for (NumeracionEspecialDTO numeracion : nEspeciales) {
      numeracionEspecialAuditoria(numeracion, usuario, Constantes.AUDITORIA_OP_BAJA);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarNumeracionEspecial(TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void eliminarNumeracionEspecial(List<NumeracionEspecialDTO> listaNumeraciones)
      throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarNumeracionEspecial(List<NumeracionEspecialDTO>) - start"); //$NON-NLS-1$
    }

    this.numeracionEspecialRepo
        .delete(ListMapper.mapList(listaNumeraciones, this.mapper, NumeracionEspecial.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarNumeracionEspecial(List<NumeracionEspecialDTO>) - end"); //$NON-NLS-1$
    }
  }

}
