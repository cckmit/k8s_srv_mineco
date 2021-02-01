package com.egoveris.te.ws.service;

import java.util.List;

import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.TrataEE;

/**
 * La presente interfaz de servicio, expone los métodos necesarios para la
 * administración de los tramites presentes en la base de EE.
 * 
 * @author cearagon
 * 
 */
public interface IAdministracionTrataService {

	/**
	 * @return una lista conteniendo la totalidad de los tramites presentes en la
	 *         base de EE.
	 */
	public List<TrataEE> obtenerTratasDeEE() throws ProcesoFallidoException;
	
	/**
	 * @param codigoTrata: codigo de Trata.
	 * @return una trata conteniendo a partir de su codigo
	 *         
	 */
	public TrataEE obtenerTrataDeEEPorCodigo(String codigoTrata) throws ProcesoFallidoException;

	/**
	 * Returns Trata list by tramitation type subprocess
	 * 
	 * @return
	 */
	public List<TrataEE> buscarTratasEEByTipoSubproceso() throws ProcesoFallidoException;
	
}
