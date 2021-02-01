package com.egoveris.ccomplejos.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoDTO;
import com.egoveris.ccomplejos.base.model.AbstractCComplejoJPA;
import com.egoveris.ccomplejos.base.repository.AbstractCComplejoRepository;
import com.egoveris.ccomplejos.base.service.ICComplejosService;

/**
 * 
 * @author everis
 *
 */
@Service
@Transactional
public class CComplejosServiceImpl implements ICComplejosService {

	private static final Logger logger = LoggerFactory.getLogger(CComplejosServiceImpl.class);

	/**
	 * Mapper definido en el archivo src/main/resources/dozerCoreMapping.xml
	 */
	@Autowired
	@Qualifier("mapperCComplejos")
	private Mapper mapper;

	@Autowired
  private ListableBeanFactory factory;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.ccomplejos.base.service.ICComplejosService#
	 * guardarDatosComponentes(java.util.List)
	 */
	@Override
	public void guardarDatosComponentes(List<AbstractCComplejoDTO> cComplejoDTOs) {
		if (CollectionUtils.isNotEmpty(cComplejoDTOs)) {
			for (final AbstractCComplejoDTO cComplejoDTO : cComplejoDTOs) {
				borrarDatosComponente(cComplejoDTO);

			}
			for (AbstractCComplejoDTO cComplejoDTO : cComplejoDTOs) {
				guardarDatosComponente(cComplejoDTO);
			}
		}
	}

	/**
	 * 
	 * @param cComplejoDTO
	 */
	private void borrarDatosComponente(final AbstractCComplejoDTO cComplejoDTO) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("guardarDatosComponente input: " + cComplejoDTO);
		}
		
		AbstractCComplejoJPA entity=mapper.map(cComplejoDTO, AbstractCComplejoJPA.class);
		repositoryForClass(entity.getClass()).removeByIdOperacionAndNombre(entity.getIdOperacion(),
				entity.getNombre());
		

		if (logger.isDebugEnabled()) {
			logger.debug("guardarDatosComponente output: " + cComplejoDTO);
		}
	}

	/**
	 * 
	 * @param cComplejoDTO
	 */
	private void guardarDatosComponente(AbstractCComplejoDTO cComplejoDTO) {

		if (logger.isDebugEnabled()) {
			logger.debug("guardarDatosComponente input: " + cComplejoDTO);
		}

		AbstractCComplejoJPA entity = mapper.map(cComplejoDTO, AbstractCComplejoJPA.class);
		try {
			entity.fillListParent();
		} catch (ReflectiveOperationException e) {
			logger.error("guardarDatosComponente entity:" + entity, e);
		}

		((JpaRepository<AbstractCComplejoJPA, Integer>) repositoryForClass(entity.getClass())).save(entity);
		cComplejoDTO.setId(entity.getId());

		if (logger.isDebugEnabled()) {
			logger.debug("guardarDatosComponente output: " + cComplejoDTO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.ccomplejos.base.service.ICComplejosService#
	 * buscarDatosComponente(com.egoveris.ccomplejos.base.model.
	 * AbstractCComplejoDTO)
	 */
	@Override
	public List<AbstractCComplejoDTO> buscarDatosComponente(AbstractCComplejoDTO cComplejoDTO) {

		if (logger.isDebugEnabled()) {
			logger.debug("buscarDatosComponente input: " + cComplejoDTO);
		}

		List<AbstractCComplejoDTO> lista = new ArrayList<>();

		AbstractCComplejoJPA entity = mapper.map(cComplejoDTO, AbstractCComplejoJPA.class);
		List<AbstractCComplejoJPA> listaResult = null;
		
		if (cComplejoDTO.getIdOperacion() != null && cComplejoDTO.getNombre() != null) {
      listaResult = repositoryForClass(entity.getClass())
          .findByIdOperacionAndNombreOrderByOrdenAsc(entity.getIdOperacion(), entity.getNombre());
		}
		else if (cComplejoDTO.getIdOperacion() != null) {
		  listaResult = repositoryForClass(entity.getClass())
          .findByIdOperacionOrderByOrdenAsc(entity.getIdOperacion());
		}
		 
		if (listaResult != null && CollectionUtils.isNotEmpty(listaResult)) {
			for (AbstractCComplejoJPA jpa : listaResult) {
				lista.add(mapper.map(jpa, AbstractCComplejoDTO.class));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarDatosComponente output: " + lista);
		}
		
		return lista;
	}

	/**
	 * 
	 * @param clase
	 * @return
	 */
	AbstractCComplejoRepository<?> repositoryForClass(Class <?> clase) {
		
		Repositories repositories=new Repositories(factory);
		return (AbstractCComplejoRepository<?>) repositories.getRepositoryFor(clase);
		
	}
	
}
