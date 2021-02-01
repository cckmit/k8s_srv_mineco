package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.NroSADEProceso;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;


/**
 * Servicio que se encarga de realizar todos los aspectos relacionados a la caratulacion.
 * 
 * @author Rocco Gallo Citera
 *
 */
public interface CaratulacionService {
	
	/**
	 * Obtiene el numero SADE de SADE y crea un pdf en gedo
	 * con el contenido de la caratula.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param solicitud the solicitud
	 * @param username the username
	 * @return the expediente electronico
	 */
	public ExpedienteElectronicoDTO caratular(ExpedienteElectronicoDTO expedienteElectronico, SolicitudExpedienteDTO solicitud ,String username);
	
	/**
	 * Buscar numeros.
	 *
	 * @param anio the anio
	 * @param numerosTransitorios Busca si los números que han quedado en estado transitorio en el numerador han sido empleados en algún documento
	 * @return the list
	 */
	public List<NroSADEProceso> buscarNumeros(int anio, List<Integer> numerosTransitorios);
		
}
