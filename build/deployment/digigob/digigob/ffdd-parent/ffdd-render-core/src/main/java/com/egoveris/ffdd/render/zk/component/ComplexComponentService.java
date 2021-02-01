package com.egoveris.ffdd.render.zk.component;

import com.egoveris.ffdd.render.model.ComplexComponent;
import com.egoveris.ffdd.render.service.IComplexComponentService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ComplexComponentService implements IComplexComponentService {
	private static final Logger logger = LoggerFactory.getLogger(ComplexComponentService.class);

	@Override
	//@Cacheable("complexcomponents")
	public ComplexComponent getComponent(final String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("getComponent(name={}) - start", name);
		}

		if (name == null) {
			return null;
		}
		JAXBContext jc;
		ComplexComponent comp = null;
		try {
			jc = JAXBContext.newInstance(ComplexComponent.class);

			final Unmarshaller unmarshaller = jc.createUnmarshaller();
			comp = (ComplexComponent) unmarshaller
					.unmarshal(this.getClass().getResourceAsStream("/components/" + name.toLowerCase()));
		} catch (final JAXBException e) {
			logger.error("getComponent(String)", e);

		}
		if (logger.isDebugEnabled()) {
			logger.debug("getComponent(String) - end - return value={}", comp);
		}
		return comp;
	}

	@Override
	public Boolean hasComponentDTO(String name) {

		Boolean exist = false;

		if (null != name) {
			ComplexComponent comp = getComponent(name);
			if (null != comp && null != comp.getMappingDTO()) {
				try {
					Class.forName(comp.getMappingDTO());
					exist = true;
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return exist;
	}

}
