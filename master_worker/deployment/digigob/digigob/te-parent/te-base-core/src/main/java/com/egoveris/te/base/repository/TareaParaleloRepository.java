package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.TareaParalelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TareaParaleloRepository extends  JpaRepository<TareaParalelo, Long>{ 

	@Modifying
	@Query("UPDATE TareaParalelo e SET e.estado = :estado WHERE e.idTask = :idTask")
	void modificarTareaParalelo(@Param("estado") String estado, @Param("idTask") String idTask);
	
	@Modifying
	@Query("UPDATE TareaParalelo e SET e.motivo = :motivo WHERE e.idTask = :idTask")
	void modificarMotivoTareaParalelo(@Param("motivo") String motivo, @Param("idTask") String idTask);
	
	@Modifying
	@Query("UPDATE TareaParalelo e SET e.motivoRespuesta = :motivoRespuesta WHERE e.idTask = :idTask")
	public void modificarMotivoRespuestaTareaParalelo(@Param("motivoRespuesta") String motivoRespuesta, @Param("idTask") String idTask);
	 
	@Query("SELECT 	e "+ 
			" FROM TareaParalelo e "+ 
			" WHERE e.usuarioOrigen in (:username) ")
	public List<TareaParalelo> buscarTareasEnParaleloByUsername(String username); // Falta migrar Query
	 
	@Query("SELECT 	e "+ 
		" FROM TareaParalelo e"+ 
		" WHERE e.idExp in (:idExpediente)"+
		" AND	e.estado != 'Terminado' ")
	public List<TareaParalelo> buscarTareasPendientesByExpediente(Long idExpediente);  
	  
	public TareaParalelo findByIdTask(String taskId);
	  
	public TareaParalelo findByIdExpAndUsuarioGrupoDestino(Long idExpediente, String destino);
	
//	@Query("SELECT 	e"+ 
//			"FROM EE_TAREA_PARALELO e"+ 
//			"WHERE e.id_exp in (:usuario)"+
//			"AND	e.estado != 'Terminado' ")
//	public List<TramitacionLibre> findTramitacionLibre(String usuario); // Falta migrar Query
	
} 

