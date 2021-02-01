package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;
import org.zkoss.zk.ui.WrongValueException;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TareaParaleloInbox;

/**
 * The Interface TareaParaleloService.
 */
public interface TareaParaleloService {

	 
	 
 	/**
 	 * Modificar tarea paralelo.
 	 *
 	 * @param idTask the id task
 	 * @param estado the estado
 	 */
	public void modificarTareaParalelo(String idTask, String estado);
 	
 	
 	public void guardar(TareaParaleloDTO tareaParaleloDto);
	
	/**
	 * Buscar tareas en paralelo by username.
	 *
	 * @param username the username
	 * @return the list
	 */
	public List<TareaParaleloInbox> buscarTareasEnParaleloByUsername(String username);
	
	/**
	 * Buscar tareas pendientes by expediente.
	 *
	 * @param idExpediente the id expediente
	 * @return the list
	 */
	public List<TareaParaleloDTO> buscarTareasPendientesByExpediente(Long idExpediente);
	
	/**
	 * Eliminar tareas paralelo by expediente.
	 *
	 * @param idExpediente the id expediente
	 */
	public void eliminarTareasParaleloByExpediente(Long idExpediente);
	
	/**
	 * Modificar motivo tarea paralelo.
	 *
	 * @param idTask the id task
	 * @param motivo the motivo
	 */
	public void modificarMotivoTareaParalelo(String idTask, String motivo);
	
	/**
	 * Buscar tarea en paralelo by id task.
	 *
	 * @param taskId the task id
	 * @return the tarea paralelo
	 */
	public TareaParaleloDTO buscarTareaEnParaleloByIdTask(String taskId);
	
 /**
  * Modificar.
  *
  * @param tareaParalelo the tarea paralelo
  */
 public void modificar(TareaParaleloDTO tareaParalelo);
	
	/**
	 * Buscar tarea en paralelo by expediente Y destino.
	 *
	 * @param idExpediente the id expediente
	 * @param destino the destino
	 * @return the tarea paralelo
	 */
	public TareaParaleloDTO buscarTareaEnParaleloByExpedienteYDestino(Long idExpediente, String destino);
	
	/**
	 * Modificar motivo respuesta tarea paralelo.
	 *
	 * @param idTask the id task
	 * @param motivoRespuesta the motivo respuesta
	 */
	public void modificarMotivoRespuestaTareaParalelo(String idTask, String motivoRespuesta);
	
	/**
	 * Verifica si la tarea paralela dada, presenta documentos oficiales sin definir.
	 *
	 * @param tareaParalelo the tarea paralelo
	 * @return true, if successful
	 */
	public boolean poseeDocumentosOficialesPendientes(TareaParaleloDTO tareaParalelo);
	
	/**
	 * Verifica si la tarea paralela dada, presenta documentos de trabajo sin definir.
	 *
	 * @param tareaParalelo the tarea paralelo
	 * @return true, if successful
	 */
	public boolean poseeDocumentosDeTrabajoPendientes(TareaParaleloDTO tareaParalelo);
	
	/**
	 * Verifica si la tarea paralela dada, presenta expedientes sin asociar definitivamente.
	 *
	 * @param tareaParalelo the tarea paralelo
	 * @return true, if successful
	 */
	public boolean poseeExpedientesAsociadosPendientes(TareaParaleloDTO tareaParalelo);

//	/**
//	 * Metodo para buscar las tramitaciones libres.
//	 *
//	 * @param usuario String usuario para poder filtrar
//	 * @return the list
//	 */
//	public List<TramitacionLibre> findTramitacionLibre(String usuario);
	
	
	
	/**
	 * Integrar tarea.
	 *
	 * @param usuarioProductorInfo the usuario productor info
	 * @param detalles the detalles
	 * @param usuario the usuario
	 * @param tareaParalelo the tarea paralelo
	 * @param motivoStr the motivo str
	 * @param expedienteElectronico the expediente electronico
	 * @param workingTask the working task
	 * @param paseComun the pase comun
	 * @throws WrongValueException the wrong value exception
	 * @throws InterruptedException the interrupted exception
	 */
	public void integrarTarea(Usuario usuarioProductorInfo,
			Map<String, String> detalles, String usuario,
			TareaParaleloDTO tareaParalelo,String motivoStr,ExpedienteElectronicoDTO expedienteElectronico,Task workingTask,boolean paseComun)
			throws  InterruptedException;
}
