package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.web.render.AbmConstraintRowRender;
import com.egoveris.ffdd.web.render.RestriccionesItemRender;

public class FormRestriccionGenericoEditComposer extends FormRestriccionComposer{

	private static final long serialVersionUID = 5295637832807050010L;

	@WireVariable("formularioService")
	private IFormularioService formularioService;
	
	@WireVariable("complexComponentService")
	private IComplexComponentService complexComponentService;
	
	@WireVariable("zkFormManagerFactory")	
	IFormManagerFactory<IFormManager<Component>> formManagerFact;
	
	private VisibilidadComponenteDTO restriccionDto;
	private FormularioDTO formulario;
	
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.self.addEventListener(Events.ON_NOTIFY,new FormRestriccionGenericoComposerListener(this));
		
		List<FormularioComponenteDTO> listFormularioComponenteDto = (List<FormularioComponenteDTO>) Executions.getCurrent().getArg().get("listComponentesSeleccionados");
		List listFormularioComponente = identificaComponenteComplejo(formManagerFact, listFormularioComponenteDto);
		this.setListaFormularioComponente(listFormularioComponente);
		this.mapaConstraints = (Map<String, Object>) Executions.getCurrent().getArg().get("mapaRestriccion");
		this.restriccionDto = (VisibilidadComponenteDTO) Executions.getCurrent().getArg().get("restriccion");
		this.listaComponentesRestringidos.addEventListener(Events.ON_SELECT, new FormRestriccionGenericoComposerListener(this));
        setearRenders();
		if (!this.restriccionDto.getComponentesOcultos().isEmpty()) {
			cargarRestricciones(this.restriccionDto.getComponentesOcultos());
		}
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
//				this.formulario = formularioService.buscarFormularioPorNombre(formularioComponenteDTO.getFormulario().getNombre());
//				IFormManager<Component>  formComponent = formManagerFact.create(formulario);
//				Map<String, String> mapa = formComponent.getComponentMapTypes();
//				
//				for (Map.Entry<String, String> entry : mapa.entrySet() ) {
//					
//					TipoComponenteDTO tipoComponente = new TipoComponenteDTO();
//					tipoComponente.setNombre(entry.getValue().toUpperCase());
//					FormularioComponenteDTO formularioComponente = new FormularioComponenteDTO();
//					ComponenteDTO componenteDto = new ComponenteDTO();
//					componenteDto.setNombre(entry.getKey());
//					componenteDto.setTipoComponenteEnt(tipoComponente);
//					formularioComponente.setComponente(componenteDto);
//					formularioComponente.setFormulario(this.formulario);
//					formularioComponente.setNombre(entry.getKey());
//					listFormularioComponente.add(formularioComponente);
//				}
				
//			}else{
				listFormularioComponente.add(formularioComponenteDTO);
//			}
		}
		return listFormularioComponente;
	}
	
	private void setearRenders(){
		this.listaComponentesRestringidos.setItemRenderer(new RestriccionesItemRender(restriccionDto));
		listaComponentesRestringidos.setModel(new ListModelList(this.listaFormularioComponente));
		this.grillaConstraint.setRowRenderer(new AbmConstraintRowRender(listaFormularioComponente));
		this.grillaConstraint.setModel(new ListModelList(this.restriccionDto.getCondiciones()));	
	}
	
}