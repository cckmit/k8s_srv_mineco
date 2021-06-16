package com.egoveris.te.base.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.TrataDTO;

public interface ExpedienteElectronicoDAO {

	public List<?> buscarId();

	@Transactional (propagation = Propagation.REQUIRED)
	public void guardar(Serializable obj);

	@Transactional (propagation = Propagation.REQUIRED)
	public void eliminar(Serializable obj);

	public void modificar(Serializable obj) ;

	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(Integer idExpedienteElectronico);

	public ExpedienteElectronicoDTO buscarExpedienteElectronico(Long idExpedienteElectronico);

	public boolean buscarIdTrata(TrataDTO trata);
	public ExpedienteElectronicoDTO buscarExpedienteElectronicoByIdTask(String idTask);
	
	/**
	 * DAOs para la búsqueda de CONSULTAS
	 */
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorReparticion(String reparticion, Date desde, Date hasta,String tipoDocumento,String numeroDocumento, String cuitCuil,String estado);

	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuario(final String username, final Date desde,
			final Date hasta,String tipoDocumento,String numeroDocumento, String cuitCuil,String estado);
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuarioTramitacion(final String username, final Date desde,
			final Date hasta, String tipoDocumento,String numeroDocumento, String cuitCuil,String estado);

	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorMetadatos(final String username, String reparticion,
			TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, final Date desde, final Date hasta,String tipoDocumento,String numeroDocumento, String cuitCuil,String estado);
	
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorMetadatosTramitacion(final String username,
			TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, final Date desde, final Date hasta,String tipoDocumento,String numeroDocumento , String cuitCuil,String estado);

	/**
	 * cearagon
	 */
	public List<ExpedienteAsociadoEntDTO> obtenerListaEnFusionados(Long idExpedienteElectronico);
	
	public List<String> buscarNumerosExpedientesPorAssignee(String usuario);
	
	public List<ExpedienteAsociadoEntDTO> obtenerListaEnTramitacionConjunta(Long idExpedienteElectronico);
	
	/**
	 * Se crea este metodo para el tratamiento de la persistencia entre fusiones y tramitaciones en conjunto
	 * @param obj
	 */
	@Transactional (propagation = Propagation.REQUIRED)
	public void modificarPorTramitacionConjuntaOFusion(Serializable obj);
	public List<String> buscarNumerosExpedientesBloqueadosPorAssignee(String usuario);
	
	 /**
	 * Retorna todos los Expedientes a partir de un año y una reparticion, Solo expedientes creados en EE
	 */
	public List<ExpedienteElectronicoDTO> buscarExpedientesElectronicosPorAnioReparticion(Integer anio, String reparticion);
	
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorDireccion(Date desde, Date hasta, String domicilio, String piso, String departamento, String codigoPostal);

	@Transactional (propagation = Propagation.REQUIRED)
	public void mergeExpedienteElectronico(
			Serializable obj);
	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(
			Long idExpedienteElectronico, String sistemaOrigen);

	List<ExpedienteElectronicoDTO> buscarExpedientesGuardaTemporalMayorA24Meses(Integer cantidadDeDias);
	
	public HashMap<String, ExpedienteElectronicoDTO> obtenerExpedienteElectronicoEnvioAutomaticoGT();
	
	public List<ExpedienteElectronicoDTO> obtenerExpedienteElectronicoConEstadoArchivo();

	List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoporTrata(
			String expedienteEstado, String expedienteUsuarioAsignado,
			List<String> codigosTrataList);
	
	public void buscarTodoslosExpedientesElectronicosEindexar();

	@Transactional (propagation = Propagation.REQUIRED)
	public void actualizarWorkflowsEnGuardaTemporalCallaBleStatement();
	/**
	 * autor: Jorge Flores
	 */
	public void regularizarActividadesStatement();
	
	public List<String>obtenerIdsDeTaskEnGrupo(String grupo);
	
	public void executeDeltaImport();

	@Transactional (propagation = Propagation.REQUIRED)
	public void actualizarReserva(String repVieja, String repNueva);

	@Transactional (propagation = Propagation.REQUIRED)
	public void actualizarReservaEnSectores(String repOrigen, String secOrigen,String repDestino, String secDestino);

	@Transactional (propagation = Propagation.REQUIRED)
	public List<String> consultaExpedientesPorSistemaOrigenReparticion(String sistemaOrigen, String reparticion);
	
	@Transactional (propagation = Propagation.REQUIRED)
	public List<String> consultaExpedientesPorSistemaOrigenUsuario(String sistemaOrigen, String usuario);
	
}
