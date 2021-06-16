/**
 * 
 */
package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ActuacionSADEBean;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author pfolgar
 *
 */
public interface TipoActuacionService {

	@Transactional
	public List<ActuacionSADEBean> obtenerListaTodasLasActuaciones();
	
	@Transactional
	public ActuacionSADEBean obtenerActuacionPorCodigo(String codigo);
}
