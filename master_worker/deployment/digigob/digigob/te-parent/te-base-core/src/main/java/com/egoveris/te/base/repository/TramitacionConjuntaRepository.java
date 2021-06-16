package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.expediente.ExpedienteAsociado;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TramitacionConjuntaRepository extends  JpaRepository<ExpedienteAsociado, Long>{
	 
	
	/**
	 * Busca si existe el expediente como Asociado y si esta en proceso de
	 * tramitación conjunta.
	 * 
	 * @param tipoActaucion
	 * @param anio
	 * @param numero
	 * @param reparticion
	 * @return boolean
	 */
//	ExpedienteAsociado findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuario(
//			String tipoActaucion, Integer anio, Integer numero, String reparticion);

	/**
	 * Busca el expediente cabecera con el código de expediente asociado.
	 * 
	 * @param tipoActaucion
	 * @param anio
	 * @param numero
	 * @param reparticion 
	 */  
//	ExpedienteAsociado  findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuarioAndIdExpCabeceraTCNotNull(
//			String tipoActaucion, Integer anio, Integer numero, String reparticion);
	
}
