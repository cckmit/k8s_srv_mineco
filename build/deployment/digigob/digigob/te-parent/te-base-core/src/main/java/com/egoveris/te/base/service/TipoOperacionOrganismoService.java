package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.ReparticionDTO;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionOrganismoDTO;
import com.egoveris.te.base.model.tipo.TipoOperacionOrganismo;
import com.egoveris.te.base.model.tipo.TipoOperacionOrganismoPK;
import com.egoveris.te.base.repository.tipo.TipoOperacionOrganismoRepository;
import com.egoveris.te.base.service.iface.ITipoOperacionOrganismoService;
import com.egoveris.te.base.util.ConstantesServicios;

@Service(ConstantesServicios.TIPO_OPERACION_ORGANISMO_SERVICE)
@Transactional
public class TipoOperacionOrganismoService implements ITipoOperacionOrganismoService {
	
	private static final Logger logger = LoggerFactory.getLogger(TipoOperacionOrganismoService.class);
	
	@Autowired
	private ObtenerReparticionServices reparticionService;
	
	@Autowired
	private TipoOperacionOrganismoRepository tipoOperacionOrganismoRepository;
	
	@Autowired
	@Qualifier("teCoreMapper")
	private Mapper mapper;

	@Override
	public List<TipoOperacionOrganismoDTO> getPosiblesOrganismos(TipoOperacionDTO tipoOperacion)
			throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("getPosiblesOrganismos() - start");
		}
		
		List<TipoOperacionOrganismoDTO> organismos = new ArrayList<>();
		List<ReparticionBean> reparticiones = reparticionService.buscarReparticionesVigentesActivas();
		
		if (reparticiones != null) {
			for (ReparticionBean reparticionBean : reparticiones) {
				TipoOperacionOrganismoDTO tipoOperacionOrganismo = new TipoOperacionOrganismoDTO();
				tipoOperacionOrganismo.setReparticion(reparticionBeanToDto(reparticionBean));
				tipoOperacionOrganismo.setTipoOperacion(tipoOperacion);
				
				organismos.add(tipoOperacionOrganismo);
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getPosiblesOrganismos(TipoOperacionOrganismoDTO) - end - return value={}", organismos);
		}
		
		return organismos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoOperacionOrganismoDTO> getOrganismosByTipoOperacion(Long idTipoOperacion)
			throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrganismosByTipoOperacion(idTipoOperacion={}) - start", idTipoOperacion);
		}
		
		List<TipoOperacionOrganismoDTO> organismosTipoOperDto = new ArrayList<>();
		List<TipoOperacionOrganismo> organismosTipoOper = tipoOperacionOrganismoRepository.getOrganismosByTipoOperacion(idTipoOperacion);
		
		if (organismosTipoOper != null) {
			organismosTipoOperDto.addAll(ListMapper.mapList(organismosTipoOper, mapper, TipoOperacionOrganismoDTO.class));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getOrganismosByTipoOperacion(TipoOperacionOrganismoDTO) - end - return value={}", organismosTipoOperDto);
		}
		
		return organismosTipoOperDto;
	}

	@Override
	public void saveOrganismosTipoOperacion(Long idTipoOperacion, List<TipoOperacionOrganismoDTO> organismosOper)
			throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("saveOrganismosTipoOperacion(organismosOper={}) - start", organismosOper);
		}
		
		if (organismosOper != null) {
			List<TipoOperacionOrganismo> listaEntityOrganismos = new ArrayList<>();
			
			for (TipoOperacionOrganismoDTO tipoOperacionOrganismoDTO : organismosOper) {
				TipoOperacionOrganismoPK pk = new TipoOperacionOrganismoPK();
				pk.setIdReparticion(Long.valueOf(tipoOperacionOrganismoDTO.getReparticion().getId()));
				pk.setIdTipoOperacion(idTipoOperacion);
				
				TipoOperacionOrganismo entityOrganismo = new TipoOperacionOrganismo();
				entityOrganismo.setPk(pk);
				
				listaEntityOrganismos.add(entityOrganismo);
			}
			
			tipoOperacionOrganismoRepository.save(listaEntityOrganismos);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("saveOrganismosTipoOperacion() - end");
		}
	}

	@Override
	public void deleteOrganismosTipoOper(Long idTipoOperacion) throws ServiceException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteOrganismosTipoOper(idTipoOperacion={}) - start", idTipoOperacion);
		}
		
		List<TipoOperacionOrganismo> organismosTipoOper = tipoOperacionOrganismoRepository.getOrganismosByTipoOperacion(idTipoOperacion);
		
		if (organismosTipoOper != null && !organismosTipoOper.isEmpty()) {
			tipoOperacionOrganismoRepository.delete(organismosTipoOper);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("deleteOrganismosTipoOper() - end");
		}
	}
	
	/**
	 * Metodo que mapea de ReparticionBean a ReparticionDTO
	 * (para ser utilizado en la relacion de tipo operacion con organismos)
	 * 
	 * Se hace manualmente en lugar de utilizar mapper, porque las clases
	 * no son exactamente iguales
	 * 
	 * @param reparticionBean
	 * @return
	 */
	private ReparticionDTO reparticionBeanToDto(ReparticionBean reparticionBean) {
		ReparticionDTO reparticionDTO = new ReparticionDTO();
		reparticionDTO.setNombreReparticion(reparticionBean.getNombre());
		reparticionDTO.setCodigoReparticion(reparticionBean.getCodigo());
		reparticionDTO.setId(reparticionBean.getId().intValue());
		reparticionDTO.setIdEstructura(reparticionBean.getIdEstructura());
		reparticionDTO.setCodDgtal(reparticionBean.getCodDgtal());
		reparticionDTO.setEstadoRegistro(reparticionBean.getEstadoRegistro());
		reparticionDTO.setVigenciaDesde(reparticionBean.getVigenciaDesde());
		reparticionDTO.setVigenciaHasta(reparticionBean.getVigenciaHasta());
		reparticionDTO.setEsDgtal(reparticionBean.getEsDigital());
		
		return reparticionDTO;
	}
	
}
