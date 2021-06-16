package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.SinPersistirException;
import com.egoveris.deo.base.model.Aviso;
import com.egoveris.deo.base.repository.AvisoRepository;
import com.egoveris.deo.base.service.AvisoService;
import com.egoveris.deo.model.model.AvisoDTO;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AvisoServiceImpl implements AvisoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AvisoServiceImpl.class);

  @Autowired
  private AvisoRepository avisoRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public List<AvisoDTO> buscarAvisosPorUsuario(Map<String, Object> parametrosConsulta,
      Map<String, String> parametrosOrden) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarAvisosPorUsuario(Map<String,Object>, Map<String,String>) - start"); //$NON-NLS-1$
    }
    PageRequest pr = null;
    List<AvisoDTO> returnList = new ArrayList<>();
    String usuario = (String) parametrosConsulta.get("usuario");
    String pagin =  parametrosOrden.get("tamanoPaginacion");
    String inicio =  parametrosOrden.get("inicioCarga");
    String criterio = parametrosOrden.get("criterio");
    String orden = parametrosOrden.get("orden");
    if ("descending".equalsIgnoreCase(orden)) {
    	  pr = new PageRequest(Integer.parseInt(inicio), Integer.parseInt(pagin), Sort.Direction.DESC, criterio );
    } else {
    	  pr = new PageRequest(Integer.parseInt(inicio), Integer.parseInt(pagin), Sort.Direction.ASC, criterio );
    } 
    List<Aviso> avisos = avisoRepo.findByUsuarioReceptor(usuario, pr);
    if (!avisos.isEmpty()) {
      returnList = ListMapper.mapList(avisos, mapper, AvisoDTO.class);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarAvisosPorUsuario(Map<String,Object>, Map<String,String>) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public List<AvisoDTO> buscarAvisosPorUsuarioYDocumentoComunicable(
      Map<String, Object> parametrosConsulta, Map<String, String> parametrosOrden) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "buscarAvisosPorUsuarioYDocumentoComunicable(Map<String,Object>, Map<String,String>) - start"); //$NON-NLS-1$
    }

    String usuario = (String) parametrosConsulta.get("usuario");
    List<AvisoDTO> returnList = ListMapper.mapList(
        avisoRepo.buscarAvisosPorUsuarioYDocumentoComunicable(usuario, 1), this.mapper,
        AvisoDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "buscarAvisosPorUsuarioYDocumentoComunicable(Map<String,Object>, Map<String,String>) - end"); //$NON-NLS-1$
    }
    return returnList;

  }

  @Override
  public void guardarAvisos(List<String> receptores, DocumentoDTO documento,
      String usuarioActual) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarAvisos(List<String>, DocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    List<AvisoDTO> avisos = new ArrayList<>();
    for (String receptor : receptores) {
      AvisoDTO aviso = new AvisoDTO();
      aviso.setUsuarioReceptor(receptor);
      aviso.setUsuarioAccion(usuarioActual);
      aviso.setFechaEnvio(new Date());
      aviso.setDocumento(documento);
      aviso.setFechaAccion(documento.getFechaCreacion());
      aviso.setReferenciaDocumento(documento.getMotivo());
      aviso.setNumeroSadePapel(documento.getNumero());
      aviso.setNumeroEspecial(documento.getNumeroEspecial());
      avisos.add(aviso);
    }
    try {
	  List<Aviso> avisoSa = ListMapper.mapList(avisos, this.mapper, Aviso.class);
      avisoRepo.save(avisoSa);
    } catch (SinPersistirException spe) {
      LOGGER.error("No se puede persistir el objeto,", spe);
      throw new SinPersistirException("No se ha podido generar el documento");
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarAvisos(List<String>, DocumentoDTO, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void guardarAvisosFalla(List<String> receptores, Map<String, String> datos,
      String usuarioActual) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarAvisosFalla(List<String>, Map<String,String>, String) - start"); //$NON-NLS-1$
    }

    List<AvisoDTO> avisos = new ArrayList<>();
    for (String receptor : receptores) {
      AvisoDTO aviso = new AvisoDTO();
      aviso.setUsuarioReceptor(receptor);
      aviso.setUsuarioAccion(usuarioActual);
      aviso.setFechaEnvio(new Date());
      aviso.setDocumento(null);
      aviso.setMotivo(datos.get("motivo"));
      aviso.setReferenciaDocumento(datos.get("referencia"));
      aviso.setFechaAccion(new Date());
      avisos.add(aviso);
    }
    try {
      avisoRepo.save(ListMapper.mapList(avisos, this.mapper, Aviso.class));
    } catch (SinPersistirException spe) {
      LOGGER.debug("No se puede persistir el objeto," + avisos);
      throw new SinPersistirException("No se ha podido generar el documento", spe);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarAvisosFalla(List<String>, Map<String,String>, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public Integer numeroAvisosPorUsuario(String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("numeroAvisosPorUsuario(String) - start"); //$NON-NLS-1$
    }

    Integer returnInteger = avisoRepo.findByUsuarioReceptor(usuario).size();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("numeroAvisosPorUsuario(String) - end"); //$NON-NLS-1$
    }
    return returnInteger;
  }

  @Override
  public Integer numeroAvisosPorUsuarioYDocumentoComunicable(String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("numeroAvisosPorUsuarioYDocumentoComunicable(String) - start"); //$NON-NLS-1$
    }

    Integer returnInteger = avisoRepo.numeroAvisosPorUsuarioYDocumentoComunicable(usuario, 1);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("numeroAvisosPorUsuarioYDocumentoComunicable(String) - end"); //$NON-NLS-1$
    }
    return returnInteger;
  }

  @Override
  public void eliminarAvisos(String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAvisos(String) - start"); //$NON-NLS-1$
    }

    avisoRepo.deleteByUsuario(usuario);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAvisos(String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void redirigirAvisos(List<AvisoDTO> avisosRedirigir, String nuevoReceptor) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("redirigirAvisos(List<AvisoDTO>, String) - start"); //$NON-NLS-1$
    }

    for (AvisoDTO aviso : avisosRedirigir) {
      aviso.setRedirigidoPor(aviso.getUsuarioReceptor());
      aviso.setUsuarioReceptor(nuevoReceptor);
      aviso.setFechaEnvio(new Date());
    }
    avisoRepo.save(ListMapper.mapList(avisosRedirigir, this.mapper, Aviso.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("redirigirAvisos(List<AvisoDTO>, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void eliminarAvisos(List<AvisoDTO> avisos) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAvisos(List<AvisoDTO>) - start"); //$NON-NLS-1$
    }

    avisoRepo.delete(ListMapper.mapList(avisos, this.mapper, Aviso.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAvisos(List<AvisoDTO>) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void guardarAvisosRechazo(List<String> receptores, Map<String, String> datos,
      String usuarioActual) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarAvisosRechazo(List<String>, Map<String,String>, String) - start"); //$NON-NLS-1$
    }

    List<Aviso> avisos = new ArrayList<>();
    for (String receptor : receptores) {
      Aviso aviso = new Aviso();
      aviso.setUsuarioReceptor(receptor);
      aviso.setUsuarioAccion(usuarioActual);
      aviso.setFechaEnvio(new Date());
      aviso.setDocumento(null);
      aviso.setMotivo(Constantes.MOTIVO_RECHAZADO + datos.get("motivo"));
      aviso.setReferenciaDocumento(datos.get("referencia"));
      aviso.setFechaAccion(new Date());
      avisos.add(aviso);
    }
    try {
      avisoRepo.save(avisos);
    } catch (SinPersistirException spe) {
      LOGGER.debug("No se puede persistir el objeto," + avisos);
      throw new SinPersistirException("No se ha podido generar el documento", spe);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarAvisosRechazo(List<String>, Map<String,String>, String) - end"); //$NON-NLS-1$
    }
  }

}
