package com.egoveris.te.base.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementacion de dozer para mapear objetos y lista de objetos
 * 
 * @author esRoveda
 */
@Component
public class CoreMapper {
	private static final Logger logger = LoggerFactory.getLogger(CoreMapper.class);

	/**
	 * Mapeador de dozer, al ser un una clase spring component el CoreMapper, no
	 * hace falta el autowired.
	 */
	private Mapper mapper = new DozerBeanMapper();

	/**
	 * Recibe un objeto y lo mapea contra la destinationClass, los atributos
	 * tienen que llamarse igual, los que no existen no los mapea
	 * 
	 * @param <T>
	 * @param source
	 * @param destinationClass
	 * @return
	 */
	public <T> T map(Object source, Class<T> destinationClass) {
		if (logger.isDebugEnabled()) {
			logger.debug("map(source={}, destinationClass={}) - start", source, destinationClass);
		}

		if (source == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("map(Object, Class<T>) - end - return value={null}");
			}
			return null;
		}
		T returnT = mapper.map(source, destinationClass);
		if (logger.isDebugEnabled()) {
			logger.debug("map(Object, Class<T>) - end - return value={}", returnT);
		}
		return returnT;
	}

	/**
	 * Recibe una lista de objetos tipo U y los mapea a una lista de objetos
	 * tipo T definido por la destinationClass.
	 * 
	 * @param <U>
	 * @param <T>
	 * @param sourceList
	 * @param destinationClass
	 * @return
	 */
	public <U, T> List<T> map(List<U> sourceList, Class<T> destinationClass) {
		if (logger.isDebugEnabled()) {
			logger.debug("map(sourceList={}, destinationClass={}) - start", sourceList, destinationClass);
		}

		List<T> returnObject = new ArrayList<T>();
		if (sourceList == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("map(List<U>, Class<T>) - end - return value={null}");
			}
			return null;
		}
		for (U source : sourceList) {
			returnObject.add(mapper.map(source, destinationClass));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("map(List<U>, Class<T>) - end - return value={}", returnObject);
		}
		return returnObject;
	}

}
