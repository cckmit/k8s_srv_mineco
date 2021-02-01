package com.egoveris.ffdd.render.zk.manager;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.exception.NotFoundException;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.render.zk.form.IFormFactory;
import com.egoveris.ffdd.ws.service.ExternalCComplejosService;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Component;

@Service
public class ZkFormManagerFactory implements IFormManagerFactory<IFormManager<Component>> {

	@Autowired
	@Qualifier("externalFormularioService")
	private ExternalFormularioService externalFormularioService;

	@Autowired
	@Qualifier("externalTransaccionService")
	private ExternalTransaccionService transaccionService;

	@Autowired
	@Qualifier("externalCComplejosService")
	private ExternalCComplejosService cComplejosService;

	@Autowired
	private IFormFactory formFactory;

	@Autowired
	@Qualifier("sistemaOrigen")
	private String sistemaOrigen;

	@Autowired
	private IComplexComponentService complexComponentService;


	public IFormManager<Component> create(String formId) throws DynFormException {
		FormularioDTO form = buscarFormulario(formId);
		Component formZkComponent = formFactory.create(form);
		return new ZkFormManager(form, formZkComponent, this);
	}
	
	public IFormManager<Component> create(FormularioDTO form) throws DynFormException {
		Component formZkComponent = formFactory.create(form);
		return new ZkFormManager(form, formZkComponent, this);
	}	

	public FormularioDTO buscarFormulario(String formId) throws DynFormException {
		FormularioDTO form = externalFormularioService.buscarFormularioPorNombre(formId);
		if (form == null) {
			throw new NotFoundException("No se encontró el formulario solicitado");
		}
		return form;
	}
	
	public ExternalFormularioService getFormularioService() {
		return externalFormularioService;
	}

	public void setFormularioService(ExternalFormularioService formularioService) {
		this.externalFormularioService = formularioService;
	}

	public IFormFactory getFormFactory() {
		return formFactory;
	}

	public void setFormFactory(IFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public ExternalTransaccionService getTransaccionService() {
		return transaccionService;
	}

	public void setTransaccionService(ExternalTransaccionService transaccionService) {
		this.transaccionService = transaccionService;
	}

	public String getSistemaOrigen() {
		return sistemaOrigen;
	}

	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}

	/**
	 * @return the complexComponentService
	 */
	public IComplexComponentService getComplexComponentService() {
		return complexComponentService;
	}

	/**
	 * @param complexComponentService
	 *            the complexComponentService to set
	 */
	public void setComplexComponentService(IComplexComponentService complexComponentService) {
		this.complexComponentService = complexComponentService;
	}

	public ExternalCComplejosService getcComplejosService() {
		return cComplejosService;
	}

	public void setcComplejosService(ExternalCComplejosService cComplejosService) {
		this.cComplejosService = cComplejosService;
	}

}
