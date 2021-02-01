package com.egoveris.ffdd.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.model.Formulario;
import com.egoveris.ffdd.base.repository.FormularioRepository;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.FormularioWDDTO;

@Service
@Transactional
public class FormCacheServiceImpl extends FormularioServiceImpl implements IFormularioService {
	private static final Logger logger = LoggerFactory.getLogger(FormCacheServiceImpl.class);

	@Autowired
	protected FormularioRepository formularioRepository;
	@Autowired
	protected ConverterServiceImpl converter;
	
	private DozerBeanMapper mapper = new DozerBeanMapper();

	
	private Map<String, FormularioDTO> mapForm = new HashMap<>();

	public void recargarFormularios() {
		if (logger.isDebugEnabled()) {
			logger.debug("recargarFormularios() - start");
		}

		final Map<String, FormularioDTO> mapFormTemp = new HashMap<>();
		List<Formulario> lista = null;
		try {
			lista = this.formularioRepository.findAll();
			for (final Formulario formulario : lista) {
				final FormularioDTO formularioDTO = this.converter.formToDTO(formulario);
				mapFormTemp.put(formulario.getNombre(), formularioDTO);
			}

		} catch (final AccesoDatoException e) {
			logger.warn("recargarFormularios() - exception ignored", e);
		}

		synchronized (this.mapForm) {
			this.mapForm = mapFormTemp;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("recargarFormularios() - end");
		}
	}

	@Override
	public FormularioDTO buscarFormularioPorNombre(final String nombre) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarFormularioPorNombre(nombre={}) - start", nombre);
		}

		final Formulario returnFormularioEnt = this.formularioRepository.findByNombre(nombre);
		FormularioDTO out = null; 
		
		if (null != returnFormularioEnt) {
			out = mapper.map(returnFormularioEnt, FormularioDTO.class);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarFormularioPorNombre(String) - end - return value={}", out);
		}
		return out;

	}
	
	@Override
	public FormularioWDDTO buscarFormularioPorNombreWD(final String nombre) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarFormularioPorNombre(nombre={}) - start", nombre);
		}

		final Formulario returnFormularioEnt = this.formularioRepository.findByNombre(nombre);
		FormularioWDDTO out = null; 
		
		if (null != returnFormularioEnt) {
			out = mapper.map(returnFormularioEnt, FormularioWDDTO.class);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarFormularioPorNombre(String) - end - return value={}", out);
		}
		return out;

	}


	@Override
	public List<FormularioDTO> obtenerTodosLosFormularios() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosLosFormularios() - start");
		}

		synchronized (this.mapForm) {
			final List<FormularioDTO> returnList = new ArrayList<>(this.mapForm.values());
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerTodosLosFormularios() - end - return value={}", returnList);
			}
			return returnList;
		}
	}

	@Override
	public void eliminarFormulario(final String nombre) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarFormulario(nombre={}) - start", nombre);
		}

		super.eliminarFormulario(nombre);
		mapForm.remove(nombre);

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarFormulario(String) - end");
		}
	}

	@Override
	public void guardarFormulario(final FormularioDTO form) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("guardarFormulario(form={}) - start", form);
		}

		super.guardarFormulario(form);
		this.mapForm.put(form.getNombre(), super.buscarFormularioPorNombre(form.getNombre()));

		if (logger.isDebugEnabled()) {
			logger.debug("guardarFormulario(FormularioDTO) - end");
		}
	}
}
