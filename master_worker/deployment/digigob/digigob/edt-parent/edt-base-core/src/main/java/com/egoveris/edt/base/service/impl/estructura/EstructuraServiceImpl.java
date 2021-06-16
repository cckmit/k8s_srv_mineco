package com.egoveris.edt.base.service.impl.estructura;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.Estructura;
import com.egoveris.edt.base.repository.eu.EstructuraRepository;
import com.egoveris.edt.base.service.estructura.IEstructuraService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.EstructuraDTO;

@Service("estructuraService")
@Transactional
public class EstructuraServiceImpl implements IEstructuraService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private EstructuraRepository estructuraRepository;

  @Override
	public List<EstructuraDTO> listEstructuras() {
		List<EstructuraDTO> estructurasDto = new ArrayList<>();
		List<Estructura> estructurasEnt = estructuraRepository.findAll();

		if (estructurasEnt != null) {
			for (Estructura estructura : estructurasEnt) {
				if (estructura.getEstadoRegistro()) {
					estructurasDto.add(mapper.map(estructura, EstructuraDTO.class));
				}
			}
		}
		
		return estructurasDto;
	}
  
  @Override
  public EstructuraDTO getById(Integer id) {
    EstructuraDTO retorno = null;
    Estructura resultado = estructuraRepository.getOne(id);
    if (resultado != null) {
      retorno = mapper.map(resultado, EstructuraDTO.class);
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<EstructuraDTO> getByPoderEjecutivo(String estructuraPoderEjecutivo) {
    return ListMapper.mapList(
        estructuraRepository.findByEstructuraPoderEjecutivo(estructuraPoderEjecutivo), mapper,
        EstructuraDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<EstructuraDTO> buscarEstructuraByCodigoYNombreComodin(String nombreOrCodigo) {
    
    return ListMapper.mapList(estructuraRepository
        .findByEstadoRegistroTrueAndNombreEstructuraContainingOrCodigoEstructuraContaining(
            nombreOrCodigo, nombreOrCodigo),
        mapper, EstructuraDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<EstructuraDTO> getByExepciones(List<String> nombresEstructura) {
    return ListMapper.mapList(estructuraRepository.findByNombreEstructuraIn(nombresEstructura),
        mapper, EstructuraDTO.class);
  }

  @Override
  public EstructuraDTO getEstructuraByCodigo(Integer codigoEstructura) throws NegocioException {
    EstructuraDTO retorno = null;
    final Estructura resultado = estructuraRepository.findByCodigoEstructura(codigoEstructura);
    if (resultado != null) {
      retorno = mapper.map(resultado, EstructuraDTO.class);
    }
    return retorno;
  }

  @Override
  public void guardarEstructura(EstructuraDTO estructura) throws NegocioException {
    estructuraRepository.save(mapper.map(estructura, Estructura.class));
  }

}