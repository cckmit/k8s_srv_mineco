package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.AuditoriaNotificacionDTO;
import com.egoveris.te.base.model.TrataAuditoriaDTO;
import com.egoveris.te.base.model.TrataDTO;

/**
 * The Interface IAuditoriaService.
 *
 * @author lcichero Interfaz con la definicion de los metodos para la auditoria
 */
public interface IAuditoriaService {

	/**
	 * Grabar auditoria de consulta.
	 *
	 * @param auditoriaDeConsulta the auditoria de consulta
	 */
	public void grabarAuditoriaDeConsulta(AuditoriaDeConsultaDTO auditoriaDeConsulta);
	
	/**
	 * Grabar auditoria notificacion.
	 *
	 * @param auditoriaNotificacion the auditoria notificacion
	 */
	public void grabarAuditoriaNotificacion(AuditoriaNotificacionDTO auditoriaNotificacion);
	
	/**
	 * Buscar historial trata.
	 *
	 * @param trata the trata
	 * @return the list
	 */
	public List<TrataAuditoriaDTO> buscarHistorialTrata(TrataDTO trata);
}
