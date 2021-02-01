package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;

import java.util.List;
import java.util.Map;


public interface NumeracionEspecialService {

  /**
   * 
   * @param reparticion
   * @param tipoDocumento
   * @param anio
   * @return
   */
	public NumeracionEspecialDTO buscarNumeracionEspecial(String reparticion,
			TipoDocumentoDTO tipoDocumento, String anio);
	
	/**
	 * 
	 * @param reparticion
	 * @param tipoDocumento
	 * @param anio
	 * @param ecosistema
	 * @return
	 */
	public NumeracionEspecialDTO buscarNumeracionEspecialEcosistema(String reparticion,
			TipoDocumentoDTO tipoDocumento, String anio,String ecosistema);
	
	public Boolean existeNumeracionEspecial(Map<String,Object> parametrosConsulta)
		throws EjecucionSiglaException;
	
	public Boolean existeNumeracionEspecialEcosistema(
			Map<String, Object> parametrosConsulta)
			throws EjecucionSiglaException;

	public NumeracionEspecialDTO buscarNumeracionEspecialById(Integer id);
	
	public List<NumeracionEspecialDTO> buscarNumeracionEspecialByReparticion(Map<String, Object> parametrosConsulta)
	  throws EjecucionSiglaException;

	/**
	 * Actualiza el contador de numeros especiales pasado como parámetro. El próximo nro. a usar será el siguiente al registrado aquí.
	 * @param numeracionEspecial Instancia de NumeracionEspecial con que representa el numerador a actualizar
	 */
	public void grabarNumeracionEspecial(NumeracionEspecialDTO numeracionEspecial);

	public void grabarNumeracionEspecialEcosistema(NumeracionEspecialDTO numeracionEspecial, String codEcosistema);
	
	/**
	 * Busca si existe un numerador dado de alta para los parámetros ingresar
	 * 
	 * @param reparticion
	 *            Repartición del numerador a buscar
	 * @param tipoDocumento
	 *            Tipo de documento del numerador a buscar.
	 * @param anio
	 *            Anio del numerador a buscar.
	 */
	public boolean existeNumerador(String reparticion,
			TipoDocumentoDTO tipoDocumento, String anio);
	
	public boolean existeNumeradorEcosistema(String reparticion,
			TipoDocumentoDTO tipoDocumento, String anio, String codigoEcosistema);
	
	
	/**
	 * Lockea el numerador para evitar otro uso simultáneo del mismo. Esto es
	 * necesario dado que, si la operación falla, debe eliminarse el numero
	 * usado
	 * 
	 * @param reparticion
	 *            Repartición del numerador a lockear
	 * @param tipoDocumento
	 *            Tipo de documento del numerador a lockear.
	 * @param anio
	 *            Anio del numerador a lockear.
	 */
	public void lockNumeroEspecial(String reparticion,
			TipoDocumentoDTO tipoDocumento, String anio);
	
	public void lockNumeroEspecialEcosistema(String reparticion,
			TipoDocumentoDTO tipoDocumento, String anio,String ecosistema);

	/**
	 * Desbloquea el numerador luego de una operación de numeración (exitosa o
	 * no). Mientras no se desbloquee el registro no habrá posibilidades de
	 * realizar otra numeración para el mismo númerado especial
	 * 
	 * @param nEspecial Número especial del cual se desea desbloquear el numerador
	 */
	public void unlockNumeroEspecial(NumeracionEspecialDTO nEspecial);
	
	public void unlockNumeroEspecialEcosistema(NumeracionEspecialDTO nEspecial);

	 
	/**
	 * Retorna el último número especial generado para los parámetros dados.
	 * @param reparticion: Repartición
	 * @param tipoDocumento: : Tipo de Documento.
	 * @return Objeto NumeracionEspecial
	 */
	public NumeracionEspecialDTO buscarUltimoNumeroEspecial(String reparticion,
			TipoDocumentoDTO tipoDocumento, String codEcosistema);
	
	/**
	 * Crea un registro en la tabla de numeración especial.
	 * @param numeracionEspecial: Objeto numeración especial a crear.
	 * @param usuario: Usuario que realiza la operación.
	 */
	public void guardar(NumeracionEspecialDTO numeracionEspecial);
	
	/**
	 * 
	 * @param numeracionEspecial registro al que se le está haciendo la auditoria.
	 * @param usuario: Usuario que reaizó la modificación
	 * @param operacion: Tipo de operación.
	 */
	public void numeracionEspecialAuditoria(NumeracionEspecialDTO numeracionEspecial, String usuario, String operacion);
	
	public void actualizarNumeracionEspecial(List<NumeracionEspecialDTO> listaNumeraciones)
			throws EjecucionSiglaException;
	
	/**
	 * Elimina un registro de la numeración especial, y registra la actividad en la tabla de auditoria correspondiente.
	 * @param numeracionEspecial
	 * @param usuario
	 */
	public void eliminarNumeracionEspecial(TipoDocumentoDTO tipoDocumento, String usuario);
	
	public void eliminarNumeracionEspecial(List<NumeracionEspecialDTO> listaNumeraciones)
 		throws EjecucionSiglaException;
}
