package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;

import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.model.model.ExpedienteAsociadoDTO;

/**
 * The Interface TramitacionConjuntaService.
 */
public interface TramitacionConjuntaService {

	/**
	 * Obtener expediente tramitacion conjunta.
	 *
	 * @param expParaAsociar the exp para asociar
	 * @param loggedUsername the logged username
	 * @param estado the estado
	 * @return the expediente asociado
	 */
	public ExpedienteAsociadoEntDTO obtenerExpedienteTramitacionConjunta(ExpedienteElectronicoDTO expParaAsociar, String loggedUsername,
			String estado);

	/**
	 * Obtener expedientes de tramitacion conjunta.
	 *
	 * @param idExpedienteAsociado the id expediente asociado
	 * @return the expediente asociado
	 */
	public ExpedienteAsociadoEntDTO obtenerExpedientesDeTramitacionConjunta(int idExpedienteAsociado);

	/**
	 * Guardar expediente tramitacion conjunta.
	 *
	 * @param expedienteAsociado the expediente asociado
	 */
	public void guardarExpedienteTramitacionConjunta(ExpedienteAsociadoDTO expedienteAsociado);

	/**
	 * Eliminar expediente tramitacion conjunta.
	 *
	 * @param expedienteAsociado the expediente asociado
	 */
	public void eliminarExpedienteTramitacionConjunta(ExpedienteAsociadoDTO expedienteAsociado);

	/**
	 * Movimiento tramitacion conjunta.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param detalles the detalles
	 * @param estadoAnterior the estado anterior
	 * @param estadoSeleccionado the estado seleccionado
	 * @param motivoExpediente the motivo expediente
	 * @param destino the destino
	 */
	public void movimientoTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername,
			Map<String, String> detalles, String estadoAnterior, String estadoSeleccionado, String motivoExpediente,
			String destino);
	
	/**
	 * Actulizar documentos en tramitacion conjunta.
	 *
	 * @param expedienteElectronico the expediente electronico
	 */
	public void actulizarDocumentosEnTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico);
	
	/**
	 * Actualizar archivos de trabajo en tramitacion conjunta.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 */
	public void actualizarArchivosDeTrabajoEnTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername);
	
	/**
	 * Actualizar expedientes asociados en tramitacion conjunta.
	 *
	 * @param expedienteElectronico the expediente electronico
	 */
	public void actualizarExpedientesAsociadosEnTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico);
	
	/**
	 * Desvincular expedientes tramitacion conjunta.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param loggedUsername the logged username
	 * @param documentoTC the documento TC
	 * @return the expediente electronico
	 */
	public ExpedienteElectronicoDTO desvincularExpedientesTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico,
			String loggedUsername, DocumentoDTO documentoTC);
	
	/**
	 * Desvincular expediente hijo tramitacion conjunta.
	 *
	 * @param expPadre the exp padre
	 * @param expHijo the exp hijo
	 * @param loggedUsername the logged username
	 * @param documentoTCDesvinculacion the documento TC desvinculacion
	 * @return the expediente electronico
	 */
	public ExpedienteElectronicoDTO desvincularExpedienteHijoTramitacionConjunta(ExpedienteElectronicoDTO expPadre,
			ExpedienteElectronicoDTO expHijo, String loggedUsername, DocumentoDTO documentoTCDesvinculacion);

	/**
	 * Se fija si el expediente se encuentra en proceso de tramitaci�n conjunta
	 * Valida si el mismo esta como Expediente Asociado y en tramitacionConjunta
	 * = true.
	 *
	 * @param codigoExpediente            (p.e.: EX-2011-00000025- -MGEYA-MGEYA)
	 * @return boolean
	 */
	public boolean esExpedienteEnProcesoDeTramitacionConjunta(String codigoExpediente);

	/**
	 * Se obtiene el c�digo de de expediente cabecera al que pertenece el
	 * expediente seleccionado.
	 * 
	 * @param codigoExpedienteAsociado
	 *            (p.e.: EX-2011-00000025- -MGEYA-MGEYA)
	 * @return String del c�digo de expediente cabecera
	 */
	public String obtenerExpedienteElectronicoCabecera(String codigoExpedienteAsociado);

	/**
	 * Se creará un documento de vinculación de tramitación conjunta. Se agrega
	 * al expediente cabecera.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param movito the movito
	 * @param username the username
	 * @param workingTask the working task
	 * @return the documento
	 */
	public DocumentoDTO generarDocumentoDeVinculacionEnTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico,
			String movito, String username, Task workingTask);

	/**
	 * Se creará un documento para registrar la eliminacion de la reserva del EE. 
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param motivo the motivo
	 * @param username the username
	 * @param workingTask the working task
	 * @return the documento
	 */
	public DocumentoDTO generarDocumentoQuitarReserva(ExpedienteElectronicoDTO expedienteElectronico,
			String motivo, String username, Task workingTask);
	
	/**
	 * Se creará un documento de desvinculación de tramitación conjunta. Se
	 * agrega al expediente cabecera.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param movito the movito
	 * @param username the username
	 * @param workingTask the working task
	 * @return the documento
	 */
	public DocumentoDTO generarDocumentoDeDesvinculacionEnTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico,
			String movito, String username, Task workingTask);

	/**
	 * Se generará la vinculación de el/los expediente/s en tramitación conjunta
	 * al expediente cabecera.
	 *
	 * @param listaExpedienteEnTramitacionConjunta the lista expediente en tramitacion conjunta
	 * @param loggedUsername the logged username
	 * @param documentoTC the documento TC
	 * @param asignadoAnterior the asignado anterior
	 * @param expedienteElectronico the expediente electronico
	 * @return devuelve el ExpedienteElectronico modificado con la Tramitación
	 *         Conjunta resuetla.
	 * @throws Exception the exception
	 */
	public ExpedienteElectronicoDTO vincularExpedientesTramitacionConjunta(
			List<ExpedienteAsociadoEntDTO> listaExpedienteEnTramitacionConjunta, String loggedUsername, DocumentoDTO documentoTC,
			String asignadoAnterior, ExpedienteElectronicoDTO expedienteElectronico) throws AsignacionException;

}
