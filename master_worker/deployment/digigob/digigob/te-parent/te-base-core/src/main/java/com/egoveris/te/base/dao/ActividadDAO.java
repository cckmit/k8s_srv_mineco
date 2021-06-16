package com.egoveris.te.base.dao;

import com.egoveris.te.base.model.Actividad;
import com.egoveris.te.base.model.TipoActividad;

import java.util.List;

public interface ActividadDAO {

	public Long crearActividad(Actividad obj);
	
	public Actividad findActividad(Long id);
	
	public void deleteActividad(Actividad obj);

	public void actualizarActividad(Actividad obj);
	
	public List<Actividad> buscarHistoricoActividades(String idObj);
	
	public List<Actividad> buscarActividadesVigentes(String idObj);
	
	public List<Actividad> buscarActividadesVigentes(List<String> idObj);
	
	public List<Actividad> buscarSubActividades(Long activid);
	
	public List<Actividad> buscarSubActividadesPorTipo(Long activId, String tipo);
	
	public List<Actividad> buscarActividadesPendientes(TipoActividad tipo);
	
	public List<Actividad> buscarActividadesVigentesPorEstado(List<String> idObj, List<String> estados);
	
	public List<Actividad>buscarActividadPorEstado(String estado);
}
