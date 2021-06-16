package com.egoveris.ffdd.render.service;

import org.zkoss.zk.ui.Component;

import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.model.ComplexField;

public interface IComplexFieldService {

	Component createInstance(ComplexField fieldSettings) throws ReflectiveOperationException;

	FormularioComponenteDTO createFormComp(DynamicComponentFieldDTO dynamicField, ComplexField fieldSettings);

}
