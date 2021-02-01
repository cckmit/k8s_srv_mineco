package com.egoveris.ffdd.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.model.AtributoComponente;
import com.egoveris.ffdd.base.model.Componente;
import com.egoveris.ffdd.base.model.DynamicComponentField;
import com.egoveris.ffdd.base.model.Formulario;
import com.egoveris.ffdd.base.model.FormularioComponente;
import com.egoveris.ffdd.base.model.FormularioSinRelaciones;
import com.egoveris.ffdd.base.model.Grupo;
import com.egoveris.ffdd.base.model.GrupoComponente;
import com.egoveris.ffdd.base.model.Item;
import com.egoveris.ffdd.model.model.AtributoComponenteDTO;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.GrupoComponenteDTO;
import com.egoveris.ffdd.model.model.GrupoDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;

public class ConverterServiceImpl {

	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;

	public Formulario dTOToForm(final FormularioDTO formDTO) throws AccesoDatoException {

		if (formDTO == null) {
			return null;
		}

		final Formulario form = getMapper().map(formDTO, Formulario.class);
		int orden = 0;
		form.setFormularioComponentes(new LinkedHashSet<FormularioComponente>());
		for (final FormularioComponenteDTO formCompDTO : formDTO.getFormularioComponentes()) {
			final FormularioComponente formComp = getMapper().map(formCompDTO, FormularioComponente.class);
			formComp.setObligatorio(formCompDTO.getObligatorio() == null ? false : formCompDTO.getObligatorio());
			formComp.setOculto(formCompDTO.getOculto() == null ? false : formCompDTO.getOculto());
			// En ambos lados deberia ser un integer
			formComp.setRelevanciaBusqueda(
					formCompDTO.getRelevanciaBusqueda() != null ? formCompDTO.getRelevanciaBusqueda() : 0);
			formComp.setOrden(orden++);
			formComp.setFormulario(form);
			form.getFormularioComponentes().add(formComp);
		}
		return form;
	}

	public Grupo dTOToGrupo(final GrupoDTO grupoDTO) {
		if (grupoDTO == null) {
			return null;
		}
		final Grupo grupo = getMapper().map(grupoDTO, Grupo.class);
		int orden = 0;
		grupo.setGrupoComponentes(new LinkedHashSet<GrupoComponente>());
		for (final GrupoComponenteDTO grupoCompDTO : grupoDTO.getGrupoComponentes()) {
			final GrupoComponente grupoComp = getMapper().map(grupoCompDTO, GrupoComponente.class);
			grupoComp.setOrden(orden++);
			grupoComp.setGrupo(grupo);
			grupoComp.setComponente(getMapper().map(grupoCompDTO.getComponente(), Componente.class));
			grupo.getGrupoComponentes().add(grupoComp);
		}
		return grupo;
	}

	public List<FormularioDTO> formListToDTO(final List<Formulario> forms) throws AccesoDatoException {
		final List<FormularioDTO> formsDTO = new ArrayList<>();
		for (final Formulario formulario : forms) {
			formsDTO.add(formToDTO(formulario));
		}
		return formsDTO;
	}

	public List<FormularioDTO> formReducidoListToDTO(final List<FormularioSinRelaciones> forms)
			throws AccesoDatoException {
		final List<FormularioDTO> formsDTO = new ArrayList<>();
		for (final FormularioSinRelaciones formulario : forms) {
			formsDTO.add(formReducidoToDTO(formulario));
		}
		return formsDTO;
	}

	public FormularioDTO formReducidoToDTO(final FormularioSinRelaciones form) throws AccesoDatoException {
		return getMapper().map(form, FormularioDTO.class);
	}

	public FormularioDTO formToDTO(final Formulario form) throws AccesoDatoException {
		if (form == null) {
			return null;
		}
		final FormularioDTO formDTO = getMapper().map(form, FormularioDTO.class);

		if (form.getFormularioComponentes() != null) {
			formDTO.setFormularioComponentes(new LinkedHashSet<FormularioComponenteDTO>());
			for (final FormularioComponente formComp : form.getFormularioComponentes()) {
				final FormularioComponenteDTO formCompDTO = getMapper().map(formComp, FormularioComponenteDTO.class);
				// En ambos lados deberia ser un integer
				formCompDTO.setRelevanciaBusqueda(
						formComp.getRelevanciaBusqueda() != null ? formComp.getRelevanciaBusqueda() : 0);
				formCompDTO.getComponente().setTipoComponenteEnt(
						mapper.map(formComp.getComponente().getTipoComponenteEnt(), TipoComponenteDTO.class));
				formCompDTO.getComponente().setMascara(formComp.getComponente().getMascara());
				formCompDTO.getComponente().setMensaje(formComp.getComponente().getMensaje());
				formDTO.getFormularioComponentes().add(formCompDTO);
				validateFormReducidoListToDTO(formComp, formCompDTO);
			}
		}
		return formDTO;

	}
	
	
	public void validateFormReducidoListToDTO(final FormularioComponente formComp, final FormularioComponenteDTO formCompDTO){
   if (formComp.getComponente().getAtributos() != null) {
     formCompDTO.getComponente().setAtributos(new HashMap<String, AtributoComponenteDTO>());

     for (Entry<String, AtributoComponente> entry : formComp.getComponente().getAtributos().entrySet()) {
      formCompDTO.getComponente().getAtributos().put(entry.getKey(),
        mapper.map(entry.getValue(), AtributoComponenteDTO.class));
     }
    }

    if (formComp.getComponente().getItems() != null) {
     formCompDTO.getComponente().setItems(new HashSet<ItemDTO>());
     for (final Item item : formComp.getComponente().getItems()) {
      formCompDTO.getComponente().getItems().add(getMapper().map(item, ItemDTO.class));
     }
    }
	  
}

	public Mapper getMapper() {
		return mapper;
	}

	public List<GrupoDTO> grupoListToDTO(final List<Grupo> grupos) {
		final List<GrupoDTO> gruposDTO = new ArrayList<>();
		for (final Grupo grupo : grupos) {
			gruposDTO.add(grupoToDTO(grupo));
		}
		return gruposDTO;
	}

	public GrupoDTO grupoToDTO(final Grupo grupo) {
		if (grupo == null) {
			return null;
		}
		final GrupoDTO grupoDTO = getMapper().map(grupo, GrupoDTO.class);
		if (grupo.getGrupoComponentes() != null) {
			grupoDTO.setGrupoComponentes(new LinkedHashSet<GrupoComponenteDTO>());
			for (final GrupoComponente gc : grupo.getGrupoComponentes()) {
				final GrupoComponenteDTO gcDTO = new GrupoComponenteDTO();
				gcDTO.setComponente(getMapper().map(gc.getComponente(), ComponenteDTO.class));
				gcDTO.getComponente().setTipoComponenteEnt(
						mapper.map(gc.getComponente().getTipoComponenteEnt(), TipoComponenteDTO.class));
				gcDTO.setNombre(gc.getNombre());
				gcDTO.setEtiqueta(gc.getEtiqueta());
				grupoDTO.getGrupoComponentes().add(gcDTO);
				validateGrupoToDTO(gc, gcDTO);
			}
		}
		return grupoDTO;
	}
	
	
	public void validateGrupoToDTO(final GrupoComponente gc, final GrupoComponenteDTO gcDTO){
	  if (gc.getComponente().getAtributos() != null) {
     gcDTO.getComponente().setAtributos(new HashMap<String, AtributoComponenteDTO>());
     
      for (Entry<String, AtributoComponente> entry : gc.getComponente().getAtributos().entrySet()) {
       gcDTO.getComponente().getAtributos().put(entry.getKey(), mapper.map(entry.getValue(), AtributoComponenteDTO.class));
      }     
    }
    if (gc.getComponente().getItems() != null) {
     gcDTO.getComponente().setItems(new HashSet<ItemDTO>());
     for (final Item item : gc.getComponente().getItems()) {
      gcDTO.getComponente().getItems().add(getMapper().map(item, ItemDTO.class));
     }
    }  
	}
	
	
	public ComponenteDTO compToDTO(final Componente comp){
		if(comp == null){
			return null;
		}
		final ComponenteDTO compDTO = mapper.map(comp, ComponenteDTO.class);
		if (comp.getAtributos() != null) {
			compDTO.setAtributos(new HashMap<String, AtributoComponenteDTO>());
			for (Entry<String, AtributoComponente> entry : comp.getAtributos().entrySet()) {
				compDTO.getAtributos().put(entry.getKey(),
						mapper.map(entry.getValue(), AtributoComponenteDTO.class));
			}
		}
		if(comp.getItems() != null){
			compDTO.setItems(new HashSet<ItemDTO>());
			for(Item item : comp.getItems()){
				compDTO.getItems().add(mapper.map(item, ItemDTO.class));
			}
		}		
		return compDTO;
	}
	
	public Componente dTOToComp(final ComponenteDTO compDTO){
		if(compDTO == null){
			return null;
		}
		final Componente comp = mapper.map(compDTO, Componente.class);
		if (compDTO.getAtributos() != null) {
			comp.setAtributos(new HashMap<String, AtributoComponente>());
			for (Entry<String, AtributoComponenteDTO> entry : compDTO.getAtributos().entrySet()) {
			  AtributoComponente atrCompoToBeAdded = mapper.map(entry.getValue(), AtributoComponente.class);
			  atrCompoToBeAdded.setComponente(comp);
				comp.getAtributos().put(entry.getKey(), atrCompoToBeAdded);
			}
		}
		if(compDTO.getItems() != null){
			comp.setItems(new HashSet<Item>());
			for(ItemDTO itemDTO : compDTO.getItems()){
			  Item itemToBeAdded = mapper.map(itemDTO, Item.class);
			  itemToBeAdded.setComponente(comp);
				comp.getItems().add(itemToBeAdded);
			}
		}
		if (null != compDTO.getDynamicFields()) {
			comp.setDynamicFields(new HashSet<>());
			for (DynamicComponentFieldDTO componentFieldDTO : compDTO.getDynamicFields()) {
				DynamicComponentField componentField = mapper.map(componentFieldDTO, DynamicComponentField.class);
				componentField.setComponente(comp);
				comp.getDynamicFields().add(componentField);
			}
		}
		return comp;
	}
	
	public void setMapper(final Mapper mapper) {
		this.mapper = mapper;
	}

}