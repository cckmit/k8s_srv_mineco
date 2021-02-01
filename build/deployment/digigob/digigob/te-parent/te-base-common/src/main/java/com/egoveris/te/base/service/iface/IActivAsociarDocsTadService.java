package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.ActividadAprobDocTad;
import com.egoveris.te.model.exception.ProcesoFallidoException;

/**
 * The Interface IActivAsociarDocsTadService.
 */
public interface IActivAsociarDocsTadService {
	
	/**
	 * Buscar actividades aprob doc.
	 *
	 * @param idAct the id act
	 * @return the list
	 */
	public List<ActividadAprobDocTad> buscarActividadesAprobDoc(Long idAct);

	/**
	 * Rechazar actividad aprob doc.
	 *
	 * @param actAprob the act aprob
	 * @param usuario the usuario
	 */
	public void rechazarActividadAprobDoc(ActividadAprobDocTad actAprob, String usuario);
	
	/**
	 * Aprobar asociar doc tad.
	 *
	 * @param actAprob the act aprob
	 * @param codigoExp the codigo exp
	 * @param usuario the usuario
	 * @throws ServiceException the service exception
	 * @throws ProcesoFallidoException the proceso fallido exception
	 */
	public void aprobarAsociarDocTad(ActividadAprobDocTad actAprob, String codigoExp, String usuario) throws ServiceException, ProcesoFallidoException;
}
