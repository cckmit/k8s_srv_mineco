package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.ListModelList;

import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.web.render.AbmConstraintRowRender;
@VariableResolver(DelegatingVariableResolver.class)
public class FormRestriccionGenericoComposer extends FormRestriccionComposer{
	
	private static final long serialVersionUID = 7132617257854096366L;
	private FormularioDTO formulario;

	@WireVariable("formularioService")
	private IFormularioService formularioService;
	
	@WireVariable("complexComponentService")
	private IComplexComponentService complexComponentService;
	
	@WireVariable("zkFormManagerFactory")	
	IFormManagerFactory<IFormManager<Component>> formManagerFact;

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		List<FormularioComponenteDTO> listFormularioComponenteDto = (List<FormularioComponenteDTO>) Executions.getCurrent().getArg().get("listComponentesSeleccionados");
		List listFormularioComponente = identificaComponenteComplejo(formManagerFact, listFormularioComponenteDto);
		this.setListaFormularioComponente(listFormularioComponente);
		this.mapaConstraints = (Map<String, Object>) Executions.getCurrent().getArg().get("mapaRestriccion");
		setearModelos();
		this.self.addEventListener(Events.ON_NOTIFY,new FormRestriccionGenericoComposerListener(this));
		this.listaComponentesRestringidos.addEventListener(Events.ON_SELECT, new FormRestriccionGenericoComposerListener(this));
	
	}

	/**
	 * @param formManagerFact
	 * @param listFormularioComponenteDto
	 * @return
	 */
	private List identificaComponenteComplejo(IFormManagerFactory<IFormManager<Component>> formManagerFact,
			List<FormularioComponenteDTO> listFormularioComponenteDto) {
		List listFormularioComponente = new ArrayList<FormularioComponenteDTO>();
		for (FormularioComponenteDTO formularioComponenteDTO : listFormularioComponenteDto) {
			
//			if(complexComponentService.hasComponentDTO(formularioComponenteDTO.getComponente().getNombreXml())){
//				
//				if(formularioComponenteDTO.getFormulario()!=null) {
//
//					this.formulario = formularioService.buscarFormularioPorNombre(formularioComponenteDTO.getFormulario().getNombre());
//					IFormManager<Component>  formComponent = formManagerFact.create(formulario);
//					Map<String, String> mapa = formComponent.getComponentMapTypes();
//					
//					for (Map.Entry<String, String> entry : mapa.entrySet() ) {
//						
//						TipoComponenteDTO tipoComponente = new TipoComponenteDTO();
//						tipoComponente.setNombre(entry.getValue().toUpperCase());
//						FormularioComponenteDTO formularioComponente = new FormularioComponenteDTO();
//						ComponenteDTO componenteDto = new ComponenteDTO();
//						componenteDto.setNombre(entry.getKey());
//						componenteDto.setTipoComponenteEnt(tipoComponente);
//						formularioComponente.setComponente(componenteDto);
//						formularioComponente.setFormulario(this.formulario);
//						formularioComponente.setNombre(entry.getKey());
//						listFormularioComponente.add(formularioComponente);
//					}
//					
//				}else {
//					// validar si esto tiene sentido cuando no existe el formulario
//					listFormularioComponente.add(formularioComponenteDTO);
//				}
//			}else{
				listFormularioComponente.add(formularioComponenteDTO);
//			}
		}
		return listFormularioComponente;
	}

	/**
	 * Permite cargar a la ventana, los modelos y la grilla...
	 */
	private void setearModelos(){
		listaComponentesRestringidos.setModel(new ListModelList(this.listaFormularioComponente));
		this.grillaConstraint.setRowRenderer(new AbmConstraintRowRender(listaFormularioComponente));
		CondicionDTO constraint = new CondicionDTO();
		this.lstConstraint.add(constraint);
		this.grillaConstraint.setModel(new ListModelList(this.lstConstraint));
	}
	
	public Bandbox getComponentesRestringidos() {
		return componentesRestringidos;
	}

	public void setComponentesRestringidos(Bandbox componentesRestringidos) {
		this.componentesRestringidos = componentesRestringidos;
	}
	
	
	

}