package com.egoveris.sharedsecurity.base.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.model.SectorUsuario;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.repository.impl.ReparticionEDTRepository;
import com.egoveris.sharedsecurity.base.repository.impl.SectorUsuarioEDTRepository;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;

@Service("reparticionEDTService")
@Transactional("jpaTransactionManagerSec")
public class ReparticionEDTServiceImpl implements IReparticionEDTService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private ReparticionEDTRepository reparticionRepository;
  @Autowired
  private SectorUsuarioEDTRepository sctrUserRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<ReparticionDTO> listReparticiones() {
    return ListMapper.mapList(reparticionRepository.findAllReparticion(new Date()), mapper, ReparticionDTO.class);
  }
  
	public List<String> obtenerReparticiones() {
		return reparticionRepository.obtenerReparticiones();
	}

  @Override
  public ReparticionDTO guardarReparticion(ReparticionDTO reparticion) throws SecurityNegocioException {
    try {
      Reparticion saved = reparticionRepository.save(mapper.map(reparticion, Reparticion.class));
      return mapper.map(saved, ReparticionDTO.class);
    } catch (ConstraintViolationException ex) {
      throw new SecurityNegocioException(
          "Error al generar el ID del organismo a guardar. Ya existe el mismo", ex);
    }
  }

  @Override
  public ReparticionDTO getReparticionById(Integer id) {
    ReparticionDTO retorno = null;
    final Reparticion resultado = reparticionRepository.getOne(id);
    if (resultado != null) {
      retorno = mapper.map(resultado, ReparticionDTO.class);
    }
    return retorno;
  }

  @Override
	public ReparticionDTO getReparticionByCodigo(String codigoReparticion) {
		ReparticionDTO retorno = null;
		final Reparticion resultado = reparticionRepository.findByCodigoAndEstadoRegistroTrue(codigoReparticion,
				new Date());
		if (resultado != null) {
			retorno = mapper.map(resultado, ReparticionDTO.class);
		}
		return retorno;
	}

  @Override
  public ReparticionDTO getReparticionActivaYInactivaByCodigo(String codigoReparticion) {
    Reparticion result = reparticionRepository.findByCodigo(codigoReparticion);
    if (result != null) {
      return mapper.map(result, ReparticionDTO.class);
    } else {
      return null;
    }
  }

  @Override
  public ReparticionDTO getReparticionByUserName(String userName) {
    ReparticionDTO retorno = null;

    SectorUsuario usrSctr = sctrUserRepository.findByNombreUsuarioAndEstadoTrue(userName);
    if (usrSctr != null) {
      retorno = mapper.map(usrSctr.getSector().getReparticion(), ReparticionDTO.class);
    }
    return retorno;

  }

  @Override
  public void modificarReparticion(ReparticionDTO reparticion) {
    reparticionRepository.save(mapper.map(reparticion, Reparticion.class));
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ReparticionDTO> buscarReparticionByCodigoYNombreComodin(String codigo) {
    return ListMapper.mapList(
        reparticionRepository.findByNombreContainingOrCodigoContaining(codigo, codigo),
        mapper, ReparticionDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ReparticionDTO> buscarReparticionByCodigoComodin(String codigoReparticion) {
    return ListMapper.mapList(
        reparticionRepository.findByCodigoContaining(codigoReparticion), mapper,
        ReparticionDTO.class);
  }
  
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getAllReparticionByUserName(String user) {
		return reparticionRepository.obtenerAllReparticionUsername(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReparticionDTO> getOrganismoByEstructura(int idEstructure) {
		return ListMapper.mapList(
		        reparticionRepository.findByEstructura_Id(idEstructure), mapper,
		        ReparticionDTO.class);
	}
}