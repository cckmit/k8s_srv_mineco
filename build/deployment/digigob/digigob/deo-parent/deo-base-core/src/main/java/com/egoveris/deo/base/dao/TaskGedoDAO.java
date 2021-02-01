package com.egoveris.deo.base.dao;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.model.TareaBusqueda;
import com.egoveris.deo.base.model.TareaJBPM;

import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;

public interface TaskGedoDAO {

	/**
	 * Busca las tareas de jbpm por tipos de documento con la caracteristica
	 * solicitada (CCOO)
	 * 
	 * @param usuario
	 * @param caracteristica
	 *            "esComunicable", "esEspecial"
	 * @param inicioCarga
	 * @param tamanoPaginacion
	 * @param criterio
	 * @param orden
	 * @return Lista de tareas
	 */
	public List<Task> buscarTasksPorUsuarioYCaractDeTipoDoc(String usuario, String caracteristica, String inicioCarga,
			String tamanoPaginacion, String criterio, String orden);

	/**
	 * Cuenta las tareas de jbpm por tipos de documento con la caracteristica
	 * solicitada (CCOO)
	 * 
	 * @param usuario
	 * @param caracteristica
	 *            "esComunicable", "esEspecial"
	 * @param inicioCarga
	 * @param tamanoPaginacion
	 * @param criterio
	 * @param orden
	 * @return cant de tareas
	 */
	public long countTasksPorUsuarioYCaractDeTipoDoc(String usuario, String caractTipoDoc);

	public List<TareaJBPM> buscarTareasDelUsuarioPorActividad(String usuarioBaja, String actividad);

	public List<TareaJBPM> buscarTareasDelUsuarioPorActividadRevisionRechazo(String usuarioBaja);

	public List<TareaJBPM> buscarTareasPorUsuarioFirmante(String usuario) throws EjecucionSiglaException;

	public List<TareaJBPM> buscarTareasPorUsuarioRevisor(String usuario, String actividad)
			throws EjecucionSiglaException;

	public List<TareaBusqueda> buscarTareasPorUsuarioInvolucrado(Map<String, Object> parametrosConsulta);

	public Integer cantidadTotalTareasPorUsuarioInvolucrado(Map<String, Object> parametrosConsulta);

	public List<TareaBusqueda> buscarTareasPorUsuarioInvolucradoSinFiltrar(Map<String, Object> parametrosConsulta);

}
