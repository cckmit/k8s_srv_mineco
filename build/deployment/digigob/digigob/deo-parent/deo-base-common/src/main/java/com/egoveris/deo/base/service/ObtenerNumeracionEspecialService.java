package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;

import java.util.List;

public interface ObtenerNumeracionEspecialService {

	/**
	 * Actualiza el campo Locked de la tabla de numeracionEspecial, para indicar
	 * que actualmente hay un proceso que está generando un documento que
	 * requiere numeracion especial.
	 * 
	 * @param request
	 * @return Objeto NumeracionEspecial, con el próximo numero especial a
	 *         generar.
	 */

	public NumeracionEspecialDTO bloquearNumeroEspecial(RequestGenerarDocumento request);

	/**
	 * 
	 * @param response
	 */
	public void rollbackNumeroEspecial(ResponseGenerarDocumento response);

	/**
	 * 
	 * @param anio
	 * @param reparticion
	 * @return
	 */
	public List<NumerosUsadosDTO> getNumerosUsados(Integer anio, String reparticion);

	/**
	 * 
	 * @param numeracionEspecial
	 */
	public void rollbackNumeroEspecial(NumeracionEspecialDTO numeracionEspecial);
	
 /**
  * 
  * @param numeracionEspecial
  */
	public void rollbackNumeroEspecialEcosistema(NumeracionEspecialDTO numeracionEspecial);

	public void guardar(NumerosUsadosDTO numerosUsados);

	/**
	 * Actualiza los valores de numeracionEspecial y numerosUsados en las
	 * correspondientes tablas de la base de datos.
	 * 
	 * @param numeracionEspecial
	 * @param numerosUsados
	 */
	public void actualizarNumerosEspeciales(NumeracionEspecialDTO numeracionEspecial, NumerosUsadosDTO numerosUsados);

	/**
	 * Retorna el último número especial generado para los parámetros dados.
	 * 
	 * @param codigoReparticion
	 *            : Repartición
	 * @param tipoDocumento
	 *            : Tipo de Documento.
	 * @return Objeto NumeracionEspecial
	 */
	public NumeracionEspecialDTO buscarUltimoNumeroEspecial(String codigoReparticion, TipoDocumentoDTO tipoDocumento);

}