package com.egoveris.te.base.service;

import java.util.Date;
import java.util.List;

import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.TipoActividadDTO;


public interface IActividadService {

	public ActividadDTO buscarActividad(Long actividadId);
	
	public Long generarActividad(ActividadDTO obj);
	
	public List<ActividadDTO>buscarActividadesPorEstado(String estado);
	
	public ActividadDTO aprobarActividad(ActividadDTO act, String userBaja);
	
	public ActividadDTO aprobarActividad(Long actId, String userBaja);
	
	public ActividadDTO rechazarActividad(ActividadDTO act, String userBaja);
	
	public ActividadDTO rechazarActividad(Long actId, String userBaja);
	
	public ActividadDTO cerrarActividad(ActividadDTO act, String userBaja);
	
	public ActividadDTO cerrarActividad(Long actId, String userBaja);
	
	public void eliminarActividad(ActividadDTO obj);
	
	public void actualizarActividad(ActividadDTO obj);
	
	public List<ActividadDTO> buscarActividadesVigentes(String idObjetivo);
	
	public List<ActividadDTO> buscarActividadesVigentes(List<String> idObjetivos);

	public List<ActividadDTO> buscarHistoricoActividades(String idObj);
	
	public List<ActividadDTO> buscarSubActividades(ActividadDTO actividadPadre);

	public List<ActividadDTO> buscarSubActividades(Long actividadPadreId);
	
	public List<ActividadDTO> buscarSubActividadesPorTipo(ActividadDTO actividad, String tipo);

	public List<ActividadDTO> buscarSubActividadesPorTipo(Long actividadId, String tipo);
	
	public TipoActividadDTO buscarTipoActividad(String tipoAct);
	
	public List<ActividadDTO> buscarActividadesPendientes(TipoActividadDTO tipo);
	
	public List<ActividadDTO> buscarActividadesVigentesPorEstado(List<String> idObj, List<String> estados);
	
	public List<ActividadDTO> buscarActividadesSubsanacionPendiente(Date fecha);
}
