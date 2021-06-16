package com.egoveris.te.base.service.iface;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.ActividadSirAprobDocTad;
import com.egoveris.te.model.exception.ProcesoFallidoException;

/**
 * The Interface IActivAsociarDocsSIRService.
 */
public interface IActivAsociarDocsSIRService {
	
	/**
	 * Buscar actividad.
	 *
	 * @param idAct the id act
	 * @return the actividad sir aprob doc tad
	 */
	public ActividadSirAprobDocTad buscarActividad(Long idAct);

	/**
	 * Rechazar actividad.
	 *
	 * @param actAprob the act aprob
	 * @param usuario the usuario
	 */
	public void rechazarActividad(ActividadSirAprobDocTad actAprob, String usuario);
	
	/**
	 * Aprobar actividad.
	 *
	 * @param actAprob the act aprob
	 * @param codigoExp the codigo exp
	 * @param usuario the usuario
	 * @throws ServiceException the service exception
	 * @throws ProcesoFallidoException the proceso fallido exception
	 */
	public void aprobarActividad(ActividadSirAprobDocTad actAprob, String codigoExp, String usuario) throws ServiceException, ProcesoFallidoException;
}
