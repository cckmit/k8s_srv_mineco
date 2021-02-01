package com.egoveris.ffdd.ws.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.FormularioWDDTO;
import com.egoveris.ffdd.model.model.GrupoDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.shared.map.ListMapper;

@Service
public class ExternalFormularioServiceImpl implements ExternalFormularioService {

	@Autowired
	@Qualifier("formularioService")
	private com.egoveris.ffdd.base.service.IFormularioService baseService;

	private DozerBeanMapper mapper = new DozerBeanMapper();

	@Override
	public FormularioWDDTO buscarFormularioPorNombreWD(String nombre) throws DynFormException {
		FormularioWDDTO out = baseService.buscarFormularioPorNombreWD(nombre);
		return out;
	}
	
	@Override
	public FormularioDTO buscarFormularioPorNombre(String nombre) throws DynFormException {
		FormularioDTO out = baseService.buscarFormularioPorNombre(nombre);
		return out;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormularioDTO> obtenerTodosLosFormularios() throws DynFormException {
		return ListMapper.mapList(baseService.obtenerTodosLosFormularios(), mapper, FormularioDTO.class);
	}

	@Override
	public void eliminarFormulario(String nombre) throws DynFormException {
		baseService.eliminarFormulario(nombre);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComponenteDTO> obtenerTodosLosComponentes() throws DynFormException {
		return ListMapper.mapList(baseService.obtenerTodosLosComponentes(), mapper, ComponenteDTO.class);
	}

	@Override
	public ComponenteDTO buscarComponentePorNombre(String nombre) throws DynFormException {
		return mapper.map(baseService.buscarComponentePorNombre(nombre), ComponenteDTO.class);
	}

	@Override
	public ComponenteDTO buscarComponenteById(int idComponente) throws DynFormException {
		return mapper.map(baseService.buscarComponenteById(idComponente), ComponenteDTO.class);
	}

	@Override
	public String buscarMultilinea(Integer idComponente) throws DynFormException {
		return baseService.buscarMultilinea(idComponente);
	}

	@Override
	public void eliminarMultilinea(int idComponente) throws DynFormException {
		baseService.eliminarMultilinea(idComponente);
	}

	@Override
	public void guardarFormulario(FormularioDTO nombre) throws DynFormException {
		baseService.guardarFormulario(mapper.map(nombre, com.egoveris.ffdd.model.model.FormularioDTO.class));
	}

	@Override
	public void eliminarFormulariosTemporales() throws DynFormException {
		baseService.eliminarFormulariosTemporales();
	}

	@Override
	public void guardarGrupo(GrupoDTO grupo) throws DynFormException {
		baseService.guardarGrupo(mapper.map(grupo, com.egoveris.ffdd.model.model.GrupoDTO.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoDTO> obtenerTodosLosGrupos() throws DynFormException {
		return ListMapper.mapList(baseService.obtenerTodosLosGrupos(), mapper, GrupoDTO.class);
	}

	@Override
	public void eliminarCaja(String nombre) throws DynFormException {
		baseService.eliminarCaja(nombre);
	}

	@Override
	public GrupoDTO buscarGrupoPorNombre(String nombre) throws DynFormException {
		return mapper.map(baseService.buscarGrupoPorNombre(nombre), GrupoDTO.class);
	}

	@Override
	public void modificarGrupo(GrupoDTO grupo) throws DynFormException {
		baseService.modificarGrupo(mapper.map(grupo, com.egoveris.ffdd.model.model.GrupoDTO.class));
	}

	@Override
	public boolean verificarUsoComponente(String id) throws DynFormException {
		return baseService.verificarUsoComponente(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormularioComponenteDTO> buscarFormComponentPorFormulario(int idFormulario) throws DynFormException {
		return ListMapper.mapList(baseService.buscarFormComponentPorFormulario(idFormulario), mapper, FormularioComponenteDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormularioDTO> obtenerTodosLosFormulariosSinRelaciones() throws DynFormException {
		return ListMapper.mapList(baseService.obtenerTodosLosFormulariosSinRelaciones(), mapper, FormularioDTO.class);
	}

	@Override
	public void guardarComponenteMultilinea(FormularioDTO form) throws DynFormException {
		baseService.guardarComponenteMultilinea(mapper.map(form, com.egoveris.ffdd.model.model.FormularioDTO.class));
	}

}
