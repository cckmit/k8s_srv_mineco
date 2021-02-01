package com.egoveris.edt.base.service.impl.actuacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.actuacion.Actuacion;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionDTO;
import com.egoveris.edt.base.repository.eu.actuacion.ActuacionRepository;
import com.egoveris.edt.base.service.actuacion.IActuacionService;
import com.egoveris.edt.model.model.ActuacionSadeDTO;
import com.egoveris.shared.map.ListMapper;

@Service("actuacionService")
@Transactional
public class ActuacionServiceImpl implements IActuacionService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private ActuacionRepository actuacionRepository;

  @Autowired
	@Qualifier("tipoDocumentoService")
  private IExternalTipoDocumentoService externalTipoDocumentoService;
  
  @Override
  public List<ActuacionDTO> listActuacion() {
    List<Actuacion> listaActuacion = actuacionRepository.findByEstadoRegistroTrue();
    List<ActuacionDTO> listaActuacionDTO = new ArrayList<>(listaActuacion.size());
    
    for (Actuacion dato : listaActuacion) {
    	if (dato.getEstadoRegistro()) {
    		listaActuacionDTO.add(mapper.map(dato, ActuacionDTO.class));
    	}
    }
    
    return listaActuacionDTO;
  }

  @Override
  public List<ActuacionDTO> buscarActuacionByCodigoYNombreComodin(String codigo) {
    List<Actuacion> listaActuacion = actuacionRepository
        .findByEstadoRegistroTrueAndNombreActuacionContainingOrCodigoActuacionContaining(codigo,
            codigo);
    List<ActuacionDTO> listaAtuacionDTO = new ArrayList<>();
    
    for (Actuacion dato : listaActuacion) {
    	if (dato.getEstadoRegistro()) {
    		listaAtuacionDTO.add(mapper.map(dato, ActuacionDTO.class));
    	}
    }
    
    return listaAtuacionDTO;
  }

  @Override
  public ActuacionDTO getActuacionByCodigo(String codigo) {
    ActuacionDTO retorno = null;
    final Actuacion resultado = actuacionRepository.findByCodigoActuacionAndEstadoRegistro(codigo, true);
    
    if (resultado != null) {
      retorno = mapper.map(resultado, ActuacionDTO.class);
    }
    
    return retorno;
  }

  @Override
  public void guardarActuacion(ActuacionDTO actuacion) throws NegocioException {
    try {
      actuacionRepository.save(mapper.map(actuacion, Actuacion.class));
    } catch (ConstraintViolationException ex) {
      throw new NegocioException(
          "Error al generar el ID de la actuacion a guardar. Ya existe el mismo", ex);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ActuacionSadeDTO> obtenerListaTodasLasActuacionesActivasVigentesYEsDocumento(
      Date vigenciaDesde, Date vigenciaHasta) {
    final Date dateNow = new Date();
    List<ActuacionSadeDTO> retorno = new ArrayList<>();
    List<Actuacion> resultado = actuacionRepository
        .obtenerListaTodasLasActuacionesActivasVigentesYEsDocumento(dateNow, dateNow);
    if (resultado != null && !resultado.isEmpty()) {
      retorno = ListMapper.mapList(resultado, mapper, ActuacionSadeDTO.class);
    }
    return retorno;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<ActuacionSadeDTO> obtenerActuacion() {
   
    List<ActuacionSadeDTO> retorno = new ArrayList<>();
    List<Actuacion> resultado = actuacionRepository
        .findAll();
    if (resultado != null && !resultado.isEmpty()) {
      retorno = ListMapper.mapList(resultado, mapper, ActuacionSadeDTO.class);
    }
    return retorno;
  }

	@Override
	public boolean existeDocumentosAsociados(Integer idActuacion) {
		return externalTipoDocumentoService.existeDocumentosAsociados(idActuacion);
	}

}
