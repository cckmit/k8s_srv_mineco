package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.Documento;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.model.tipo.TipoDocumentoDepuracion;
import com.egoveris.te.base.repository.DocumentoRepository;
import com.egoveris.te.base.repository.expediente.ExpedienteElectronicoRepository;
import com.egoveris.te.base.repository.tipo.TipoDocumentoDepuracionRepository;

@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService {
  private static final Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);

  @Autowired
  private ExpedienteElectronicoRepository expedienteEle;

  @Autowired
  private DocumentoRepository documentoRepository;

  @Autowired
  private TipoDocumentoDepuracionRepository tipoDocRepository;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  public void guardar(DocumentoDTO documento) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardar(documento={}) - start", documento);
    }

    documentoRepository.save(mapper.map(documento, Documento.class));

    if (logger.isDebugEnabled()) {
      logger.debug("guardar(Documento) - end");
    }
  }

  public DocumentoDTO buscarDocumento(String numeroSade) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDocumento(numeroSade={}) - start", numeroSade);
    }
    Documento entidad = documentoRepository.findByNumeroSade(numeroSade);

    DocumentoDTO documentoDto = mapper.map(entidad, DocumentoDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarDocumento(String) - end - return value={}", documentoDto);
    }
    return documentoDto;
  }

  @Override
  public List<String> obtenerTipoDocDepuracion() {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoDocDepuracion() - start");
    }
    List<String> returnList = new ArrayList<String>();

    List<TipoDocumentoDepuracion> listTipoDocDep = tipoDocRepository.findAll();
    for (TipoDocumentoDepuracion tipoDocumentoDepuracion : listTipoDocDep) {
      returnList.add(tipoDocumentoDepuracion.getTipoDocAcronimo());
    }
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoDocDepuracion() - end - return value={}", returnList);
    }
    return returnList;
  }

  @Override
  public List<ExpedienteElectronicoDTO> buscarExpedientesPorNumeroDocumentoNoArchivo(
      String numeroSade) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarExpedientesPorNumeroDocumentoNoArchivo(numeroSade={}) - start",
          numeroSade);
    }

    List<ExpedienteElectronico> returnList = expedienteEle.findByEstado(numeroSade);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarExpedientesPorNumeroDocumentoNoArchivo(String) - end - return value={}",
          returnList);
    }
     
    return ListMapper.mapList(returnList, mapper, ExpedienteElectronicoDTO.class);
  }

}
