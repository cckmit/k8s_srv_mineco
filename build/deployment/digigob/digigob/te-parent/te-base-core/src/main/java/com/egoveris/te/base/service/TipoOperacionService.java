package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;
import com.egoveris.te.base.model.TipoOperacionOrganismoDTO;
import com.egoveris.te.base.model.tipo.TipoOperacion;
import com.egoveris.te.base.model.tipo.TipoOperacionFormulario;
import com.egoveris.te.base.model.tipo.TipoOperacionOrganismo;
import com.egoveris.te.base.repository.tipo.ITipoOperacionFormularioRepository;
import com.egoveris.te.base.repository.tipo.ITipoOperacionRepository;
import com.egoveris.te.base.service.iface.ITipoOperacionDocService;
import com.egoveris.te.base.service.iface.ITipoOperacionOrganismoService;
import com.egoveris.te.base.service.iface.ITipoOperacionService;
import com.egoveris.te.base.util.ConstantesServicios;

@Service(ConstantesServicios.TIPO_OPERACION_SERVICE)
@Transactional
public class TipoOperacionService implements ITipoOperacionService {

  private static final Logger logger = LoggerFactory.getLogger(TipoOperacionService.class);

  @Autowired
  private ITipoOperacionRepository tipoOperacionRepository;

  @Autowired
  private ITipoOperacionFormularioRepository tipoOperacionFormularioRepository;

  @Autowired
  private ITipoOperacionDocService tipoOperDocService;
  
  @Autowired
  private ITipoOperacionOrganismoService tipoOperOrganismoService;
  
  @Autowired
  @Qualifier("teCoreMapper")
  private Mapper mapper;

  @Override
  public List<TipoOperacionDTO> getAllTiposOperacion() throws ServiceException {
    if (logger.isDebugEnabled()) {
      logger.debug("getAllTiposOperacion() - start");
    }

    List<TipoOperacionDTO> tiposOperacionDto = new ArrayList<>();
    List<TipoOperacion> tiposOperEnt = tipoOperacionRepository.findAll();

    if (tiposOperEnt != null) {
      // Se mapea con el metodo entityToDtoMapper, para tambien mapear listas y
      // campos aux
      for (TipoOperacion tipoOperacionEnt : tiposOperEnt) {
        tiposOperacionDto.add(entityToDtoMapper(tipoOperacionEnt));
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getAllTiposOperacion(TipoOperacionDTO) - end - return value={}", tiposOperacionDto);
    }

    return tiposOperacionDto;
  }
  
  @Override
  public List<TipoOperacionDTO> getTiposOperacionOrganismoVigentes(Integer idOrganismo)
      throws com.egoveris.te.base.exception.ServiceException {
	  if (logger.isDebugEnabled()) {
			logger.debug("getTiposOperacionOrganismo(idOrganismo={}) - start", idOrganismo);
		}
		
	  List<TipoOperacionDTO> tiposOperacionDto = new ArrayList<>();
	  List<TipoOperacion> allTiposOperacion = tipoOperacionRepository.findAll();
	  
	  // Recorre la lista de todos los tipos de operacion
	  if (allTiposOperacion != null) {
	    for (TipoOperacion tipoOperacion : allTiposOperacion) {
        if (tipoOperacion.getOrganismos() == null || tipoOperacion.getOrganismos().isEmpty()) {
          // Si el tipo de operacion no restringe organismos, es valido
          tiposOperacionDto.add(entityToDtoMapper(tipoOperacion));
        }
        else if (tipoOperacion.getOrganismos() != null) {
          // Si el organismo esta habilitado para el tipo de operacion, es valido
          addTipoOperacionIfOrganismoVigente(idOrganismo, tipoOperacion, tiposOperacionDto);
        }
      }
	  }
	  
		if (logger.isDebugEnabled()) {
			logger.debug("getTiposOperacionOrganismo(TipoOperacionDTO) - end - return value={}", tiposOperacionDto);
		}
		
		return tiposOperacionDto;
  }
  
  /**
   * Agrega a la lista de tipos operaciones, el tipo de operacion dado siempre
   * que este habilitado para el organismo dado
   * 
   * @param idOrganismo
   * @param tipoOperacion
   * @param tiposOperacionDto
   */
  private void addTipoOperacionIfOrganismoVigente(Integer idOrganismo, TipoOperacion tipoOperacion, List<TipoOperacionDTO> tiposOperacionDto) {
    for (TipoOperacionOrganismo tipoOperOrganismo : tipoOperacion.getOrganismos()) {
      if (tipoOperOrganismo.getReparticion() != null && tipoOperOrganismo.getReparticion().getId() == idOrganismo) {
        tiposOperacionDto.add(entityToDtoMapper(tipoOperacion));
      }
    }
  }
  
  @Override
  public Boolean isCodigoDuplicado(TipoOperacionDTO tipoOperacionDto) throws ServiceException {
    if (logger.isDebugEnabled()) {
      logger.debug("isRegistroDuplicado(tipoOperacionDto={}) - start", tipoOperacionDto);
    }

    Boolean isDuplicado = false;

    List<TipoOperacion> registrosDuplicados = tipoOperacionRepository.findByCodigo(tipoOperacionDto.getCodigo());

    if (!registrosDuplicados.isEmpty() && tipoOperacionDto.getCodigoAux() != null) {
      for (int i = 0; i < registrosDuplicados.size(); i++) {
        if (registrosDuplicados.get(i).getCodigo().equalsIgnoreCase(tipoOperacionDto.getCodigoAux())) {
          registrosDuplicados.remove(i);
          break;
        }
      }
    }

    if (!registrosDuplicados.isEmpty()) {
      isDuplicado = true;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("isRegistroDuplicado(Boolean) - end - return value={}", isDuplicado);
    }

    return isDuplicado;
  }

  @Override
  public Boolean saveOrUpdateTipoOperacion(TipoOperacionDTO tipoOperacionDto,
      List<FormularioDTO> formulariosTipoOperacionDto, List<TipoOperacionDocDTO> tipoDocumentosDto,
      List<TipoOperacionOrganismoDTO> organismosDto) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("guardarTipoOperacion(tipoOperacionDto={}) - start", tipoOperacionDto);
    }

    boolean resultado = false;

    try {
      TipoOperacion tipoOperEnt = mapper.map(tipoOperacionDto, TipoOperacion.class);
      // Se dejan las listas vacias para no persistirlas. Esto se hace mas abajo
      // en el codigo
      tipoOperEnt.setTiposOpDocumento(new ArrayList<>());
      tipoOperEnt.setOrganismos(new ArrayList<>());
      tipoOperacionRepository.save(tipoOperEnt);
      
      if (formulariosTipoOperacionDto != null) {
        deleteFormulariosTipoOperacion(tipoOperEnt.getId());
        saveFormulariosTipoOperacion(tipoOperEnt.getId(), formulariosTipoOperacionDto);
      }
      
			if (tipoDocumentosDto != null) {
				tipoOperDocService.deleteTiposDocsTipoOper(tipoOperEnt.getId());
				tipoOperDocService.saveDocumentosTipoOperacion(tipoOperEnt.getId(), tipoDocumentosDto);
			}
			
			if (organismosDto != null) {
				tipoOperOrganismoService.deleteOrganismosTipoOper(tipoOperEnt.getId());
				tipoOperOrganismoService.saveOrganismosTipoOperacion(tipoOperEnt.getId(), organismosDto);
			}
			
      resultado = true;
    } catch (Exception e) {
      logger.error("Error en TipoOperacionService.guardarTipoOperacion() - saveOrUpdate: ", e);
      throw new ServiceException(e.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarTipoOperacion(Boolean) - end - return value={}", resultado);
    }

    return resultado;
  }

  @Override
  public Boolean deleteTipoOperacion(TipoOperacionDTO tipoOperacionDto) {
    if (logger.isDebugEnabled()) {
      logger.debug("deleteTipoOperacion(tipoOperacionDto={}) - start", tipoOperacionDto);
    }

    boolean resultado = false;
    boolean isEnUso = false;

    try {
      Integer tiposOperEnt = tipoOperacionRepository.isTipoOperacionEnUso(tipoOperacionDto.getId());

      if (tiposOperEnt > 0) {
        isEnUso = true;
      }

      if (!isEnUso) {
        TipoOperacion tipoOperEnt = mapper.map(tipoOperacionDto, TipoOperacion.class);

        deleteFormulariosTipoOperacion(tipoOperacionDto.getId());
        tipoOperDocService.deleteTiposDocsTipoOper(tipoOperacionDto.getId());
        tipoOperOrganismoService.deleteOrganismosTipoOper(tipoOperacionDto.getId());
        tipoOperacionRepository.delete(tipoOperEnt);
        resultado = true;
      }
    } catch (Exception e) {
      logger.error("Error en TipoOperacionService.deleteTipoOperacion(): ", e);
      throw new ServiceException(e.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("deleteTipoOperacion(Boolean) - end - return value={}", resultado);
    }

    return resultado;
  }

  @Override
  public List<FormularioDTO> getIdFormulariosTipoOperacion(Long idTipoOperacion) throws ServiceException {
    if (logger.isDebugEnabled()) {
      logger.debug("getIdFormulariosTipoOperacion(idTipoOperacion) - start", idTipoOperacion);
    }

    List<FormularioDTO> formulariosTipoOperacionDto = new ArrayList<>();

    List<TipoOperacionFormulario> formulariosTipoOperacion = tipoOperacionFormularioRepository
        .findByIdTipoOperacion(idTipoOperacion);

    if (formulariosTipoOperacion != null) {
      for (TipoOperacionFormulario tipoOperacionFormulario : formulariosTipoOperacion) {
        FormularioDTO formularioDTO = new FormularioDTO();
        formularioDTO.setId(tipoOperacionFormulario.getIdFormulario().intValue());
        formulariosTipoOperacionDto.add(formularioDTO);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getIdFormulariosTipoOperacion(FormularioDTO) - end - return value={}", formulariosTipoOperacionDto);
    }

    return formulariosTipoOperacionDto;
  }

  @Override
  public void saveFormulariosTipoOperacion(Long idTipoOperacion, List<FormularioDTO> formulariosTipoOperacionDto)
      throws ServiceException {
    if (logger.isDebugEnabled()) {
      logger.debug("saveTipoOperacionFormulario(idTipoOperacion, formulariosTipoOperacion={}) - start", idTipoOperacion,
          formulariosTipoOperacionDto);
    }

    if (idTipoOperacion != null && formulariosTipoOperacionDto != null) {
      List<TipoOperacionFormulario> formulariosTipoOperacion = new ArrayList<>();

      for (FormularioDTO formularioDTO : formulariosTipoOperacionDto) {
        TipoOperacionFormulario tipoOperacionFormulario = new TipoOperacionFormulario();
        tipoOperacionFormulario.setIdTipoOperacion(idTipoOperacion);
        tipoOperacionFormulario.setIdFormulario(formularioDTO.getId().longValue());
        formulariosTipoOperacion.add(tipoOperacionFormulario);
      }

      tipoOperacionFormularioRepository.save(formulariosTipoOperacion);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("saveTipoOperacionFormulario() - end");
    }
  }

  @Override
  public void deleteFormulariosTipoOperacion(Long idTipoOperacion) throws ServiceException {
    if (logger.isDebugEnabled()) {
      logger.debug("deleteFormulariosTipoOperacion(idTipoOperacion={}) - start", idTipoOperacion);
    }

    List<TipoOperacionFormulario> formulariosTipoOperacion = tipoOperacionFormularioRepository
        .findByIdTipoOperacion(idTipoOperacion);

    if (formulariosTipoOperacion != null) {
      for (TipoOperacionFormulario tipoOperacionFormulario : formulariosTipoOperacion) {
        tipoOperacionFormularioRepository.delete(tipoOperacionFormulario);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("deleteFormulariosTipoOperacion() - end");
    }
  }

  /**
   * Mapea de Entity a DTO
   * 
   * @param tipoOperacion
   *          Entity
   * @return
   */
  @SuppressWarnings("unchecked")
  private TipoOperacionDTO entityToDtoMapper(TipoOperacion tipoOperacion) {
    TipoOperacionDTO tipoOperacionDto = new TipoOperacionDTO();

    if (tipoOperacion.getId() != null) {
      tipoOperacionDto.setId(tipoOperacion.getId());
    }

    tipoOperacionDto.setCodigo(tipoOperacion.getCodigo());
    tipoOperacionDto.setCodigoAux(tipoOperacion.getCodigo());
    tipoOperacionDto.setNombre(tipoOperacion.getNombre());
    tipoOperacionDto.setDescripcion(tipoOperacion.getDescripcion());
    tipoOperacionDto.setWorkflow(tipoOperacion.getWorkflow());
    tipoOperacionDto.setEstado(tipoOperacion.getEstado());
    
    // Se mapea la lista de TipoOperacionDocumento
    if (tipoOperacion.getTiposOpDocumento() != null) {
      tipoOperacionDto.getTiposOpDocumento()
          .addAll(ListMapper.mapList(tipoOperacion.getTiposOpDocumento(), mapper, TipoOperacionDocDTO.class));
    }
    
    // Se mapea la lista de TipoOperacionOrganismo
    if (tipoOperacion.getOrganismos() != null) {
      tipoOperacionDto.getOrganismos()
      .addAll(ListMapper.mapList(tipoOperacion.getOrganismos(), mapper, TipoOperacionOrganismoDTO.class));
    }
    
    return tipoOperacionDto;
  }

}
