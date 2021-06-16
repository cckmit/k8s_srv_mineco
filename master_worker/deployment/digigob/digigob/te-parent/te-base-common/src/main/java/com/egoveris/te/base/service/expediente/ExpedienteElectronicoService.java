package com.egoveris.te.base.service.expediente;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.DatosEnvioParalelo;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

/**
 * Funcionalidad para persistir los cambios en EE .
 *
 * @author Juan Pablo Norverto
 */
public interface ExpedienteElectronicoService {
	
	/**
	 * Obtener expediente electronico por codigo.
	 *
	 * @param codigoEE the codigo EE
	 * @return the expediente electronico
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	public ExpedienteElectronicoDTO obtenerExpedienteElectronicoPorCodigo(String codigoEE)
	throws ParametroIncorrectoException ;
	
	/**
	 * Obtener solicitud expediente.
	 *
	 * @param idSolicitudExpediente the id solicitud expediente
	 * @return the expediente electronico DTO
	 */
	public ExpedienteElectronicoDTO obtenerSolicitudExpediente(Long idSolicitudExpediente);
	
	/**
	 * Vincular documento GEDO.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param docGEDO the doc GEDO
	 * @param usuario the usuario
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws ProcesoFallidoException the proceso fallido exception
	 */
	@Transactional
	public void vincularDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronicoDto, String docGEDO, String usuario)
			throws ParametroIncorrectoException, ProcesoFallidoException;
	
	/**
	 * Vincular documento GEDO.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param docsGEDO the docs GEDO
	 * @param usuario the usuario
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws ProcesoFallidoException the proceso fallido exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void vincularDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronicoDto, List<String> docsGEDO, String usuario)
			throws ParametroIncorrectoException, ProcesoFallidoException;
	
	/**
	 * Vincula el documento al expediente de la misma manera que el método "vincularDocumentoGEDO" pero de manera definitiva. 
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param docGEDO the doc GEDO
	 * @param usuario the usuario
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws ProcesoFallidoException the proceso fallido exception
	 * @see #vincularDocumentoGEDO(ExpedienteElectronicoDTO, String, String)
	 */
	@Transactional
	public void vincularDocumentoGEDO_Definitivo(ExpedienteElectronicoDTO expedienteElectronicoDto, String docGEDO, String usuario)
			throws ParametroIncorrectoException, ProcesoFallidoException;
	
	/**
	 * Buscar id.
	 *
	 * @return the list
	 */
	public List<?> buscarId();
	
	/**
	 * Grabar expediente electronico.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @throws RuntimeException the runtime exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void grabarExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronicoDtoDto) throws DataAccessLayerException;
	
	 /**
 	 * Execute delta import.
 	 */
 	public void executeDeltaImport();
	
	/**
	 * Modificar expediente electronico.
	 *
	 * @param expedienteElectronico the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void modificarExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronicoDto);
	@Transactional(propagation = Propagation.REQUIRED)
	public ExpedienteElectronicoDTO modificarExpedienteElectronico2(ExpedienteElectronicoDTO expedienteElectronicoDto);
	
	/**
	 * Asignar permisos visualizacion archivo.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param usuario the usuario
	 * @param reparticionesRectora the reparticiones rectora
	 */
	public void asignarPermisosVisualizacionArchivo(ExpedienteElectronicoDTO expedienteElectronicoDto,Usuario usuario, List<ReparticionParticipanteDTO> reparticionesRectora);
	
	/**
	 * Obtener expedientes en guarda temporal mayor A 24 meses.
	 *
	 * @param cantidadDeDias the cantidad de dias
	 * @return the list
	 */
	public List<ExpedienteElectronicoDTO>obtenerExpedientesEnGuardaTemporalMayorA24Meses(Integer cantidadDeDias);
	
	/**
	 * Obtener expediente electronico envio automatico GT.
	 *
	 * @return the hash map
	 */
	public HashMap<String, ExpedienteElectronicoDTO> obtenerExpedienteElectronicoEnvioAutomaticoGT();
	
	/**
	 * Obtener expediente electronico con estado archivo.
	 *
	 * @return the list
	 */
	public List<ExpedienteElectronicoDTO> obtenerExpedienteElectronicoConEstadoArchivo();
	
	/**
	 * Obtener expedientes en guarda temporal por codigo expediente.
	 *
	 * @param codExpediente the cod expediente
	 * @return the list
	 */
	public List<ExpedienteElectronicoDTO>obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(String codExpediente);
	
	/**
	 * Merge expediente electronico.
	 *
	 * @param expElectronico the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void mergeExpedienteElectronico(ExpedienteElectronicoDTO expElectronico);
	
	/**
	 * Guardar documento en expediente electronico.
	 *
	 * @param expedienteElectronico the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void guardarDocumentoEnExpedienteElectronico (ExpedienteElectronicoDTO expedienteElectronicoDto);
	
	/**
	 * Buscar numeros expedientes por assignee.
	 *
	 * @param usuario the usuario
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<String> buscarNumerosExpedientesPorAssignee(String usuario);
	
	/**
	 * Buscar numeros expedientes bloqueados por assignee.
	 *
	 * @param usuario the usuario
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<String>buscarNumerosExpedientesBloqueadosPorAssignee(String usuario);
	
	/**
	 * Guardar archivo de trabajo en expediente electronico.
	 *
	 * @param expedienteElectronico the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void guardarArchivoDeTrabajoEnExpedienteElectronico (ExpedienteElectronicoDTO expedienteElectronicoDto);
	
	/**
	 * Buscar expedientes asociados.
	 *
	 * @param idExpedienteElectronico the id expediente electronico
	 * @return the list
	 */
	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(Integer idExpedienteElectronico);
	
	/**
	 * Obtener reparticiones rectoras.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param codigoTrata the codigo trata
	 * @return the list
	 */
	public List<ReparticionParticipanteDTO> obtenerReparticionesRectoras(ExpedienteElectronicoDTO expedienteElectronicoDto,String loggedUsername,String codigoTrata);
	
	/**
	 * Generar expediente electronico.
	 *
	 * @param workingTask the working task
	 * @param solicitud the solicitud
	 * @param selectedTrata the selected trata
	 * @param descripcion the descripcion
	 * @param metaDatosCargados the meta datos cargados
	 * @param username the username
	 * @param motivoExpediente the motivo expediente
	 * @param usuarioSolicitante the usuario solicitante
	 * @return the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ExpedienteElectronicoDTO generarExpedienteElectronico(Task workingTask, SolicitudExpedienteDTO solicitud, TrataDTO selectedTrata, String descripcion, 
			List<ExpedienteMetadataDTO> metaDatosCargados, String username, String motivoExpediente, String usuarioSolicitante);
	
	/**
	 * Generar pase expediente electronico.
	 *
	 * @param workingTask the working task
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param estadoSeleccionado the estado seleccionado
	 * @param motivoExpediente the motivo expediente
	 * @param detalles the detalles
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDto, 
			String loggedUsername, String estadoSeleccionado,String motivoExpediente,Map<String,String> detalles);

	/**
	 * Generar pase expediente electronico.
	 *
	 * @param workingTask the working task
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param estadoSeleccionado the estado seleccionado
	 * @param motivoExpediente the motivo expediente
	 * @param detalles the detalles
	 * @param status the status
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDto, 
			String loggedUsername, String estadoSeleccionado,String motivoExpediente,Map<String,String> detalles, TransactionStatus status) throws AsignacionException;

	/**
	 * Generar pase expediente electronico.
	 *
	 * @param workingTask the working task
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param estadoSeleccionado the estado seleccionado
	 * @param motivoExpediente the motivo expediente
	 * @param detalles the detalles
	 * @param persistirCambios the persistir cambios
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronico(Task workingTask,
			ExpedienteElectronicoDTO expedienteElectronicoDtoTV, String usuarioCreador, String activityName,
			String motivoExpediente, Map<String, String> detalles, boolean persistirCambios);
	
	/**
	 * Generar pase expediente electronico.
	 *
	 * @param workingTask the working task
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param estadoSeleccionado the estado seleccionado
	 * @param motivoExpediente the motivo expediente
	 * @param aclaracion the aclaracion
	 * @param detalles the detalles
	 * @param numeroSadePase the numero sade pase
	 * @param generarDoc the generar doc
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDto, 
			String loggedUsername, String estadoSeleccionado, String motivoExpediente, String aclaracion,
			Map<String,String> detalles,String numeroSadePase,Boolean generarDoc);
	
	/**
	 * Obtener working task.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @return the task
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Task obtenerWorkingTask(
			ExpedienteElectronicoDTO expedienteElectronicoDto)
			throws ParametroIncorrectoException;
	
	/**
	 * Guardar datos en historial operacion P.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param detalles the detalles
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void guardarDatosEnHistorialOperacionP(ExpedienteElectronicoDTO expedienteElectronicoDto, String loggedUsername,
			Map<String, String> detalles);
	
	/**
	 * Generar pase expediente electronico administrador.
	 *
	 * @param workingTask the working task
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param estadoSeleccionado the estado seleccionado
	 * @param motivoExpediente the motivo expediente
	 * @param detalles the detalles
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronicoAdministrador(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDto, 
			String loggedUsername, String estadoSeleccionado, String motivoExpediente,
			Map<String,String> detalles);
	
	
	/**
	 * Generar pase paralelo expediente electronico.
	 *
	 * @param workingTask the working task
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param estadoSeleccionado the estado seleccionado
	 * @param estadoAnterior the estado anterior
	 * @param motivoExpediente the motivo expediente
	 * @param motivosPropios the motivos propios
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseParaleloExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDto, 
			String loggedUsername, String estadoSeleccionado, String estadoAnterior, String motivoExpediente, List<DatosEnvioParalelo> motivosPropios);
	
	/**
	 * Buscar expediente electronico.
	 *
	 * @param idExpedienteElectronico the id expediente electronico
	 * @return the expediente electronico
	 */
	@Transactional(readOnly=true)
	public ExpedienteElectronicoDTO buscarExpedienteElectronico (Long idExpedienteElectronico);
	
	/**
	 * Buscar expediente electronico by id task.
	 *
	 * @param idTask the id task
	 * @return the expediente electronico
	 */
	@Transactional(readOnly=true)
	public ExpedienteElectronicoDTO buscarExpedienteElectronicoByIdTask (String idTask);
	
	/**
	 * Obtener expediente electronico.
	 *
	 * @param tipoExpediente the tipo expediente
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticion the reparticion
	 * @return the expediente electronico
	 */
	@Transactional(readOnly=true)
	public ExpedienteElectronicoDTO obtenerExpedienteElectronico(String tipoExpediente,Integer anio,Integer numero,String reparticion);
	
	/**
	 * Obtener expediente electronico solr.
	 *
	 * @param tipoExpediente the tipo expediente
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticion the reparticion
	 * @return the expediente electronico
	 */
	@Transactional(readOnly=true)
	public ExpedienteElectronicoDTO obtenerExpedienteElectronicoSolr(String tipoExpediente,Integer anio,Integer numero,String reparticion);
	
	/**
	 * Buscar id trata.
	 *
	 * @param trata the trata
	 * @return true, if successful
	 */
	@Transactional(readOnly=true)
	public boolean buscarIdTrata(TrataDTO trata);
	
	/**
	 * Generar expediente electronico caratulacion directa.
	 *
	 * @param solicitud the solicitud
	 * @param selectedTrata the selected trata
	 * @param descripcion the descripcion
	 * @param metaDatosCargados the meta datos cargados
	 * @param username the username
	 * @param motivoExpediente the motivo expediente
	 * @return the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ExpedienteElectronicoDTO generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud, TrataDTO selectedTrata, String descripcion, 
			List<ExpedienteMetadataDTO> metaDatosCargados, String username, String motivoExpediente);
	
	/**
	 * Generar expediente electronico caratulacion directa.
	 *
	 * @param solicitud the solicitud
	 * @param selectedTrata the selected trata
	 * @param descripcion the descripcion
	 * @param metaDatosCargados the meta datos cargados
	 * @param username the username
	 * @param motivoExpediente the motivo expediente
	 * @param nuevo the nuevo
	 * @return the map
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud, TrataDTO selectedTrata, String descripcion, 
			List<ExpedienteMetadataDTO> metaDatosCargados, String username, String motivoExpediente,boolean nuevo);
	
	/**
	 * Generar expediente electronico caratulacion directa.
	 *
	 * @param solicitud the solicitud
	 * @param selectedTrata the selected trata
	 * @param descripcion the descripcion
	 * @param metaDatosCargados the meta datos cargados
	 * @param username the username
	 * @param motivoExpediente the motivo expediente
	 * @param estadoExpediente the estado expediente
	 * @param estadoVariable the estado variable
	 * @return the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ExpedienteElectronicoDTO generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud, TrataDTO selectedTrata, String descripcion, 
			List<ExpedienteMetadataDTO> metaDatosCargados, String username, String motivoExpediente, String estadoExpediente, String estadoVariable);
	
	/**
	 * Generar expediente electronico caratulacion directa.
	 *
	 * @param solicitud the solicitud
	 * @param selectedTrata the selected trata
	 * @param descripcion the descripcion
	 * @param metaDatosCargados the meta datos cargados
	 * @param username the username
	 * @param motivoExpediente the motivo expediente
	 * @param estadoExpediente the estado expediente
	 * @param estadoVariable the estado variable
	 * @param origen the origen
	 * @return the map
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud, TrataDTO selectedTrata, String descripcion, 
			List<ExpedienteMetadataDTO> metaDatosCargados, String username, String motivoExpediente, String estadoExpediente, String estadoVariable, boolean origen);

	/**
	 * Buscar expediente electronico por usuario.
	 *
	 * @param username the username
	 * @param trata the trata
	 * @param expedienteMetaDataList the expediente meta data list
	 * @param metaDatos the meta datos
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param tipoDocumento the tipo documento
	 * @param numeroDocumento the numero documento
	 * @param cuitCuil the cuit cuil
	 * @param estado the estado
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuario(String username, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> metaDatos, Date desde,	Date hasta,String tipoDocumento,String numeroDocumento, String cuitCuil, String estado);
	
	/**
	 * Buscar expediente electronico por usuario tramitacion.
	 *
	 * @param username the username
	 * @param trata the trata
	 * @param expedienteMetaDataList the expediente meta data list
	 * @param metadatos the metadatos
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param tipoDocumento the tipo documento
	 * @param numeroDocumento the numero documento
	 * @param cuitCuil the cuit cuil
	 * @param estado the estado
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuarioTramitacion(String username, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList,List<DatosDeBusqueda> metadatos,Date desde,Date hasta ,String tipoDocumento,String numeroDocumento, String cuitCuil, String estado);
	
	/**
	 * Buscar expediente electronico por reparticion.
	 *
	 * @param username the username
	 * @param trata the trata
	 * @param expedienteMetaDataList the expediente meta data list
	 * @param metaDatos the meta datos
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param tipoDocumento the tipo documento
	 * @param numeroDocumento the numero documento
	 * @param cuitCuil the cuit cuil
	 * @param estado the estado
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorReparticion(String username, TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> metaDatos, Date desde,	Date hasta,String tipoDocumento, String numeroDocumento, String cuitCuil, String estado);
	
	/**
	 * Buscar expediente electronico por direccion.
	 *
	 * @param desde the desde
	 * @param hasta the hasta
	 * @param domicilio the domicilio
	 * @param piso the piso
	 * @param departamento the departamento
	 * @param caodigoPostal the caodigo postal
	 * @param esado the esado
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorDireccion(Date desde,	Date hasta, String domicilio, String piso, String departamento, String caodigoPostal,String esado);

	/**
	 * Obtener lista en fusionados.
	 *
	 * @param idExpedienteElectronico the id expediente electronico
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<ExpedienteAsociadoEntDTO> obtenerListaEnFusionados(Long idExpedienteElectronico);
	
	/**
	 * Obtener lista en tramitacion conjunta.
	 *
	 * @param idExpedienteElectronico the id expediente electronico
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<ExpedienteAsociadoEntDTO> obtenerListaEnTramitacionConjunta(Long idExpedienteElectronico);

	/**
	 * Mover archivo trabajo por web dav.
	 *
	 * @param codigoExpedienteElectronicoOrigen the codigo expediente electronico origen
	 * @param codigoExpedienteElectronicodestino the codigo expediente electronicodestino
	 * @param filenameOri the filename ori
	 * @param filenameDest the filename dest
	 */
	public void moverArchivoTrabajoPorWebDav(String codigoExpedienteElectronicoOrigen, String codigoExpedienteElectronicodestino,
			String filenameOri, String filenameDest);
	
	/**
	 * Modificacion del expediente en tramitación conjunta o fusion. Por problemas de persistencia.
	 *
	 * @param expedienteElectronico the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void modificarExpedienteElectronicoPorTramitacionConjuntaOFusion(ExpedienteElectronicoDTO expedienteElectronicoDto);	
	
	
	/*
	 * BLOQUEO DE EE
	 */
	/**
	 * Informa si el expediente de parámetros otorgados se encuentra bloqueado.
	 *
	 * @param tipoExpediente the tipo expediente
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticion the reparticion
	 * @return TRUE, el expediente se encuentra bloqueado. FALSE, caso contrario.
	 * @throws ExpedienteInexistenteException the expediente inexistente exception
	 */
	public Boolean isExpedienteElectronicoBloqueado(String tipoExpediente,Integer anio,Integer numero,String reparticion) ; 
	
	/**
	 * Bloquea el expediente para todo ajeno al sistema sistemaBloqueador.
	 *
	 * @param sistemaBloqueador the sistema bloqueador
	 * @param tipoExpediente the tipo expediente
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticion the reparticion
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws ExpedienteInexistenteException the expediente inexistente exception
	 * @throws ExpedienteNoDisponibleException the expediente no disponible exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void bloquearExpedienteElectronico(String sistemaBloqueador,String tipoExpediente,Integer anio,Integer numero,String reparticion)throws ParametroIncorrectoException,ExpedienteNoDisponibleException;
	
	/**
	 * Desbloquea el expediente, retornándolo a EE.
	 *
	 * @param sistemaSolicitante the sistema solicitante
	 * @param tipoExpediente the tipo expediente
	 * @param anio the anio
	 * @param numero the numero
	 * @param reparticion the reparticion
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws ExpedienteInexistenteException the expediente inexistente exception
	 * @throws ExpedienteNoDisponibleException the expediente no disponible exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void desbloquearExpedienteElectronico(String sistemaSolicitante,String tipoExpediente,Integer anio,Integer numero,String reparticion)throws ParametroIncorrectoException,ExpedienteNoDisponibleException;
	
	/**
	 * Limpia un string que tiene formato html. con un limite de 3500 caracteres 
	 * si se excede 
	 *
	 * @param motivo the motivo
	 * @return  String con el contenido sin el html
	 */
	public String formatoMotivo(String motivo);
	
	/**
	 * Limpia un string que tiene formato html.
	 *
	 * @param motivo the motivo
	 * @return String con el contenido sin el html
	 */
	public String PasarFormatoStringSinHTML(String motivo);
	
	/**
	 * Indexar expediente electronico.
	 *
	 * @param expedienteElectronico the expediente electronico
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void indexarExpedienteElectronico(
			ExpedienteElectronicoDTO expedienteElectronicoDto);
	
	/**
	 * Buscar expedientes electronicos por anio reparticion.
	 *
	 * @param anio the anio
	 * @param reparticion the reparticion
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<ExpedienteElectronicoDTO> buscarExpedientesElectronicosPorAnioReparticion(Integer anio, String reparticion);
	
	/**
	 * Obtener codigo caratula por numero expediente.
	 *
	 * @param expedienteCodigo the expediente codigo
	 * @return the string
	 * @throws ProcesoFallidoException the proceso fallido exception
	 */
	public String obtenerCodigoCaratulaPorNumeroExpediente(String expedienteCodigo) throws ProcesoFallidoException;

	/**
	 * Desvincular documentos oficiales.
	 *
	 * @param sistemaUsuario the sistema usuario
	 * @param usuario the usuario
	 * @param expedienteElectronico the expediente electronico
	 * @param listaDocumentos the lista documentos
	 * @throws ProcesoFallidoException the proceso fallido exception
	 * @throws ExpedienteInexistenteException the expediente inexistente exception
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws ExpedienteNoDisponibleException the expediente no disponible exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void desvincularDocumentosOficiales(String sistemaUsuario, String usuario, ExpedienteElectronicoDTO expedienteElectronicoDto, List<String> listaDocumentos) throws ProcesoFallidoException,  ParametroIncorrectoException, ExpedienteNoDisponibleException;
	
	/**
	 * Vincular documentos oficiales.
	 *
	 * @param sistemaUsuario the sistema usuario
	 * @param usuario the usuario
	 * @param expedienteElectronico the expediente electronico
	 * @param listaDocumentos the lista documentos
	 * @throws ProcesoFallidoException the proceso fallido exception
	 * @throws ExpedienteInexistenteException the expediente inexistente exception
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws ExpedienteNoDisponibleException the expediente no disponible exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
    public void vincularDocumentosOficiales(String sistemaUsuario, String usuario, ExpedienteElectronicoDTO expedienteElectronicoDto, List<String> listaDocumentos) throws ProcesoFallidoException,  ParametroIncorrectoException, ExpedienteNoDisponibleException;
	
	/**
	 * Realizar vinculacion definitiva.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param destinatario the destinatario
	 * @return true, if successful
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean realizarVinculacionDefinitiva(ExpedienteElectronicoDTO expedienteElectronicoDto, String destinatario);
	
	/**
	 * Vincular documento gedo.
	 *
	 * @param ee the expediente
	 * @param documentoGedo the doc
	 * @param usuario the usuario
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void vincularDocumentoGedo(ExpedienteElectronicoDTO ee,DocumentoDTO documentoGedo,String usuario) throws ParametroIncorrectoException;
	
	/**
	 * Actualizo work flow id en caratulacion.
	 *
	 * @param workingTask the working task
	 * @param idExpediente the id expediente
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void actualizoWorkFlowIdEnCaratulacion(Task workingTask, Long idExpediente);
	
	/**
	 * Buscar expedientes asociados.
	 *
	 * @param idExpedienteElectronico the id expediente electronico
	 * @param sistemaOrigen the sistema origen
	 * @return the list
	 */
	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(Long idExpedienteElectronico, String sistemaOrigen);
	
	/**
	 * Generar pase A solicitud archivo.
	 *
	 * @param expediente the expediente
	 * @param map the map
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseASolicitudArchivo(ExpedienteElectronicoDTO expediente,
			Map<String, String> map) throws ParametroIncorrectoException;

	/**
	 * Generar pase A archivo.
	 *
	 * @param expediente the expediente
	 * @param map the map
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseAArchivo(ExpedienteElectronicoDTO expediente,
			Map<String, String> map) throws ParametroIncorrectoException;
	
	/**
	 * Buscar expediente electronicopor trata en solr.
	 *
	 * @param expedienteEstado the expediente estado
	 * @param codigosTrataList the codigos trata list
	 * @return the list
	 */
	List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoporTrataEnSolr(
			String expedienteEstado, 
			List<String> codigosTrataList);
	
	/**
	 * Actualizar reserva en la tramitacion.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void actualizarReservaEnLaTramitacion(ExpedienteElectronicoDTO expedienteElectronicoDto,String loggedUsername );
	
	/**
	 * Actualizar workflows en guarda temporal calla ble statement.
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void actualizarWorkflowsEnGuardaTemporalCallaBleStatement();
	
	/**
	 * Generar pase expediente electronico sin documento migracion.
	 *
	 * @param workingTask the working task
	 * @param exp the expediente electronico
	 * @param user the user
	 * @param estadoSeleccionado the estado seleccionado
	 * @param motivoExpediente the motivo expediente
	 * @param detalles the detalles
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronicoSinDocumentoMigracion(Task workingTask, ExpedienteElectronicoDTO exp,
			Usuario user, String estadoSeleccionado, String motivoExpediente, Map<String, String> detalles);	
	
	/**
	 * Regularizar actividades GEDO.
	 */
	@Transactional
	public void regularizarActividadesGEDO();
	
	/**
	 * Actualizar reservas.
	 *
	 * @param repVieja the rep vieja
	 * @param repNueva the rep nueva
	 */
	@Transactional
	public void actualizarReservas(String repVieja, String repNueva);
	
	/**
	 * Actualizar reserva sector.
	 *
	 * @param repOrigen the rep origen
	 * @param secOrigen the sec origen
	 * @param repDestino the rep destino
	 * @param secDestino the sec destino
	 */
	@Transactional
	public void actualizarReservaSector(String repOrigen, String secOrigen, String repDestino, String secDestino);
	
	/**
	 * Obtener ids de task asociadas A grupo.
	 *
	 * @param repSec the rep sec
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<String>obtenerIdsDeTaskAsociadasAGrupo(String repSec);
	
	/**
	 * Obener ids de task asociadas A grupo bloqueado.
	 *
	 * @param repSec the rep sec
	 * @return the list
	 */
	@Transactional(readOnly=true)
	public List<String>obenerIdsDeTaskAsociadasAGrupoBloqueado(String repSec);
	
	/**
	 * Obtener working task.
	 *
	 * @param idTask the id task
	 * @return the task
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	@Transactional(readOnly=true)
	public Task obtenerWorkingTask(String idTask) throws ParametroIncorrectoException;
	
	/**
	 * Actualizar documentos en gedo.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param destinatario the destinatario
	 * @param user the user
	 * @param codigoTrata the codigo trata
	 */
	public void actualizarDocumentosEnGedo(
			ExpedienteElectronicoDTO expedienteElectronicoDto, String destinatario,Usuario user, String codigoTrata);
	
	/**
	 * Generar pase expediente electronico sin documento sin tocar definitivos.
	 *
	 * @param t the t
	 * @param ee the ee
	 * @param usuarioOrigen the usuario origen
	 * @param estado the estado
	 * @param motivo the motivo
	 * @param variables the variables
	 */
	@Transactional
	public void generarPaseExpedienteElectronicoSinDocumentoSinTocarDefinitivos(Task t,
			ExpedienteElectronicoDTO ee,
			Usuario usuarioOrigen,String estado, 
			String motivo ,Map<String,String> variables);
	
	/**
	 * Consulta expedientes por sistema origen reparticion.
	 *
	 * @param sistemaOrigen the sistema origen
	 * @param reparticion the reparticion
	 * @return the list
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	@Transactional(readOnly=true)
	public List<String> consultaExpedientesPorSistemaOrigenReparticion(String sistemaOrigen, String reparticion) throws ParametroIncorrectoException ;

	/**
	 * Consulta expedientes por sistema origen usuario.
	 *
	 * @param sistemaOrigen the sistema origen
	 * @param usuario the usuario
	 * @return the list
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	@Transactional(readOnly=true)
	public List<String> consultaExpedientesPorSistemaOrigenUsuario(String sistemaOrigen, String usuario) throws ParametroIncorrectoException;
	
	/**
	 * Realizar vinculacion definitiva vinculados por.
	 *
	 * @param ee the ee
	 * @param loggedUsername the logged username
	 * @return true, if successful
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean realizarVinculacionDefinitivaVinculadosPor(
			ExpedienteElectronicoDTO ee, String loggedUsername);

	/**
	 * Actualizar archivos de trabajo en la reserva por tramitacion.
	 *
	 * @param ee the expediente electronico
	 * @param loggedUsername the logged username
	 * @param listRepRect the list rep rect
	 * @param datosUsuario the datos usuario
	 */
	public void actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(ExpedienteElectronicoDTO ee, String loggedUsername, List<ReparticionParticipanteDTO> listRepRect, Usuario datosUsuario);

	/**
	 * Modificar expediente electronico.
	 *
	 * @param ee the ee
	 * @param camposFC the campos FC
	 * @throws NegocioException the negocio exception
	 */
	@Transactional
	public void modificarExpedienteElectronico(ExpedienteElectronicoDTO ee,
			Map<String, Object> camposFC) throws NegocioException;
 
	
	/**
	 * Guardado temporal expedientes.
	 */
	@Transactional
	public void guardadoTemporalExpedientes() throws InterruptedException;
	
	
	/**
	 * 
	 * Metodo encargado de vincular los documentos generados a Expediente de TE
	 * 
	 * @param solicitudDTO
	 */
	void vinculacionDocumentosAExpedienteTe(String acronimoGedo, String codigoExp, String usuario);

}
