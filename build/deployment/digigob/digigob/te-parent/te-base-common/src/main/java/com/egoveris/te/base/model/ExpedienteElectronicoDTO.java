package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.exception.RemoveDocException;
import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.util.BusinessFormatHelper;

/**
 * Expediente en formato electronico exclusivamente.
 *
 * @author rgalloci
 *
 */
public class ExpedienteElectronicoDTO extends Expediente implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ExpedienteElectronicoDTO.class);

	private static final long serialVersionUID = -6838276362923882254L;
	private Long id;
	private Integer anio;
	private String descripcion;
	private TrataDTO trata;
	private String usuarioCreador;
	private Date fechaCreacion;
	private String usuarioModificacion;
	private Date fechaModificacion;
	private String tipoDocumento;
	private Integer numero;
	private String secuencia;
	private Boolean definitivo = false;
	private String codigoReparticionUsuario;
	private String codigoReparticionActuacion;
	private Boolean esElectronico;
	private List<ExpedienteMetadataDTO> metadatosDeTrata = new ArrayList<>();
	private List<ArchivoDeTrabajoDTO> archivosDeTrabajo = new ArrayList<>();
	private List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados = new ArrayList<>();
	private String idWorkflow;
	private String estado;
	private Boolean esCabeceraTC = false;
	private Boolean esReservado;
	private String usuarioReserva;
	private Date fechaReserva;
	private Date fechaDepuracion;
	private Integer idOperacion;
	private boolean blocked;
	private Integer cantidadSubsanar;

	private transient Boolean existenExpedientesDefinitivos = false;
	private transient Boolean existenExpedientesFusionados = false;
	private transient Boolean existenExpedientesTramitacion = false;

	// VARIABLE QUE NO SE TIENE QUE MAPEAR EN EL HBN.XML
	private transient Boolean solicitarArchivo = false;

	// Almacena el código de carátula formateado
	private transient String codigoCaratula;

	/*
	 * BLOQUEO
	 */
	private String sistemaCreador = "EE";
	private String sistemaApoderado = "EE";
	private boolean bloqueado = false;
	private Boolean tramitacionLibre = false;
	private ExpedienteElectronicoConSuspensionDTO eeConSuspension = null;
	
	//
	private String resultado;
	private PropertyConfigurationDTO propertyResultado;
	
	private OperacionDTO operacion;
	
	/**
	 * Agrega el archivo a la lista de archivos de trabajo del expediente. Se
	 * verifica que el expediente no contenga previamente al mismo documento. Si
	 * esto sucediera, se agrega el archivo con el sufijo "-FUSIONADO".
	 *
	 */
	@Override
	public void agregarArchivoDeTrabajo(final ArchivoDeTrabajoDTO archivoJob) {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarArchivoDeTrabajo(archivoJob={}) - start", archivoJob);
		}

		final List<ArchivoDeTrabajoDTO> archivos = this.getArchivosDeTrabajo();
		for (final ArchivoDeTrabajoDTO arch : archivos) {
			if (arch.getNombreArchivo().equals(archivoJob.getNombreArchivo())) {
				final StringTokenizer token = new StringTokenizer(archivoJob.getNombreArchivo(), ".");
				final String nuevoNombre = token.nextToken() + "-FUSIONADO." + token.nextToken();
				archivoJob.setNombreArchivo(nuevoNombre);
			}
		}
		this.archivosDeTrabajo.add(archivoJob);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarArchivoDeTrabajo(ArchivoDeTrabajo) - end");
		}
	}

	/**
	 * Realiza la "foliacion" de los documentos del expediente al agregarlos
	 * dentro de la lista que los contiene. Se verifica que el expediente no
	 * contenga previamente al mismo documento. El orden de agregado es el
	 * numero de folio.
	 *
	 */
	@Override
	public void agregarDocumento(final DocumentoDTO documento) {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarDocumento(documento={}) - start", documento);
		}

		final List<DocumentoDTO> docs = super.getDocumentos();
		for (final DocumentoDTO doc : docs) {
			if (doc.getNumeroSade().equals(documento.getNumeroSade())) {
				if (logger.isDebugEnabled()) {
					logger.debug("agregarDocumento(Documento) - end");
				}
				return;
			}
		}
		super.agregarDocumento(documento);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarDocumento(Documento) - end");
		}
	}

	/**
	 * Agrega la vinculación a la lista de expedientes asociados al expediente.
	 * Se verifica que el expediente no contenga previamente al mismo
	 * expediente.
	 *
	 */
	@Override
	public void agregarExpedienteAsociado(final ExpedienteAsociadoEntDTO expedienteAsociado) {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarExpedienteAsociado(expedienteAsociado={}) - start", expedienteAsociado);
		}

		final List<ExpedienteAsociadoEntDTO> expAsoc = this.getListaExpedientesAsociados();
		for (final ExpedienteAsociadoEntDTO exp : expAsoc) {
			if (exp.getAsNumeroSade().equals(expedienteAsociado.getAsNumeroSade())) {
				if (logger.isDebugEnabled()) {
					logger.debug("agregarExpedienteAsociado(ExpedienteAsociado) - end");
				}
				return;
			}
		}
		this.listaExpedientesAsociados.add(expedienteAsociado);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarExpedienteAsociado(ExpedienteAsociado) - end");
		}
	}

	@Override
	public void agregarMetadatosDeTrata(final ExpedienteMetadataDTO espedienteMetadata) {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarMetadatosDeTrata(espedienteMetadata={}) - start", espedienteMetadata);
		}

		this.metadatosDeTrata.add(espedienteMetadata);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarMetadatosDeTrata(ExpedienteMetadataDTO) - end");
		}
	}

	/**
	 * No puede ser un doc null, ÃƒÂ³ definitivo
	 *
	 * @param <code>com.egoveris.te.core.api.entidades.tramitacion.expediente.Documento
	 *            </code> abstractDocumento
	 */
	private void assertDocGedo(final DocumentoDTO abstractDocumento)
			throws AsignacionException {
		if (logger.isDebugEnabled()) {
			logger.debug("assertDocGedo(abstractDocumento={}) - start", abstractDocumento);
		}

		if (abstractDocumento == null) {
			throw new TeException("No es un GEDO Doc valido!", null);
		}
		if (abstractDocumento.getDefinitivo()) {
			throw new TeException("El documento no puede ser desvinculado porque estÃƒÂ¡ en estado definitivo", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("assertDocGedo(com.egoveris.te.core.api.entidades.tramitacion.expediente.Documento) - end");
		}
	}

	private void assertDocsEnExpedienteElectronico(final Boolean isContains) throws AsignacionException {
		if (logger.isDebugEnabled()) {
			logger.debug("assertDocsEnExpedienteElectronico(isContains={}) - start", isContains);
		}

		if (!isContains) {
			throw new TeException("El documento que intenta desvincular, no se encuentra vinculado al expediente.", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("assertDocsEnExpedienteElectronico(Boolean) - end");
		}
	}

	private void assertExpedienteAsociado(final ExpedienteAsociadoEntDTO expedienteAsociado) throws AsignacionException {
		if (logger.isDebugEnabled()) {
			logger.debug("assertExpedienteAsociado(expedienteAsociado={}) - start", expedienteAsociado);
		}

		if (expedienteAsociado.getDefinitivo()) {
			throw new TeException("El expediente no puede ser desasociado porque estÃƒÂ¡ en estado definitivo.", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("assertExpedienteAsociado(ExpedienteAsociado) - end");
		}
	}

	public void borrarExpAsociadoByIdTask(final String idTask) {
		if (logger.isDebugEnabled()) {
			logger.debug("borrarExpAsociadoByIdTask(idTask={}) - start", idTask);
		}

		final Iterator<ExpedienteAsociadoEntDTO> it = getListaExpedientesAsociados().iterator();
		while (it.hasNext()) {
			final ExpedienteAsociadoEntDTO ee = it.next();
			if (ee.getIdTask().equals(idTask)) {
				it.remove();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("borrarExpAsociadoByIdTask(String) - end");
		}
	}

	public boolean contieneDoc(final String numeroDoc) {
		if (logger.isDebugEnabled()) {
			logger.debug("contieneDoc(numeroDoc={}) - start", numeroDoc);
		}

		for (final DocumentoDTO d : getDocumentos()) {
			if (d.getNumeroSade().equals(numeroDoc)
					|| d.getNumeroEspecial() != null && d.getNumeroEspecial().equals(numeroDoc)) {
				if (logger.isDebugEnabled()) {
					logger.debug("contieneDoc(String) - end - return value={}", true);
				}
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("contieneDoc(String) - end - return value={}", false);
		}
		return false;
	}

	@Override
	public void desagregarArchivoDeTrabajo(final ArchivoDeTrabajoDTO archivoTrabajo) {
		if (logger.isDebugEnabled()) {
			logger.debug("desagregarArchivoDeTrabajo(archivoTrabajo={}) - start", archivoTrabajo);
		}

		final List<ArchivoDeTrabajoDTO> archsTrabajo = getArchivosDeTrabajo();
		boolean eliminable = false;
		for (final ArchivoDeTrabajoDTO arch : archsTrabajo) {
			if (arch.getNombreArchivo().equals(archivoTrabajo.getNombreArchivo())) {
				if (arch.isDefinitivo() == false) {
					eliminable = true;
				}
			}
		}
		if (eliminable) {
			archivosDeTrabajo.remove(archivoTrabajo);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("desagregarArchivoDeTrabajo(ArchivoDeTrabajo) - end");
		}
	}

	@Override
	public void desagregarExpedienteAsociado(final ExpedienteAsociadoEntDTO expedienteAsoc) {
		if (logger.isDebugEnabled()) {
			logger.debug("desagregarExpedienteAsociado(expedienteAsoc={}) - start", expedienteAsoc);
		}

		final List<ExpedienteAsociadoEntDTO> expedientesAsoc = getListaExpedientesAsociados();
		boolean eliminable = false;
		for (final ExpedienteAsociadoEntDTO expAs : expedientesAsoc) {
			if (expAs.getAsNumeroSade().equals(expedienteAsoc.getAsNumeroSade())) {
				if (expAs.getDefinitivo() == false) {
					eliminable = true;
				}
			}
		}
		if (eliminable) {
			expedientesAsoc.remove(expedienteAsoc);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("desagregarExpedienteAsociado(ExpedienteAsociado) - end");
		}
	}

	// ********************************************
	// *** DESASOCIACION
	// ********************************************
	@Override
	public void desasociarPorNumeroDeDoc(final Integer numero) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("desasociarPorNumeroDeDoc(numero={}) - start", numero);
		}

		final List<ExpedienteAsociadoEntDTO> expedientesAsociadosList = new LinkedList<>();
		for (final ExpedienteAsociadoEntDTO expedienteAsociado : this.getListaExpedientesAsociados()) {
			if (expedienteAsociado.getNumero().equals(numero)) {
				assertExpedienteAsociado(expedienteAsociado);
				expedientesAsociadosList.add(expedienteAsociado);
			}
		}

		if (expedientesAsociadosList.isEmpty()) {
			throw new TeException("El expediente no se encuentra asociado.", null);
		}

		this.getListaExpedientesAsociados().removeAll(expedientesAsociadosList);

		if (logger.isDebugEnabled()) {
			logger.debug("desasociarPorNumeroDeDoc(Integer) - end");
		}
	}

	/**
	 * Elimina un determinado documento de la lista de documentos. Para poder
	 * eliminarlo el mismo debe poder ser eliminable (no haberse hecho un pase
	 * despues de que se añadio el documento. Esto se indica mediante el
	 * atributo definitivo del documento, true para no poder modificarlo.
	 * Tambien se controla que el expediente no este en estado archivado o
	 * comunicacion
	 *
	 * @param documento
	 *            a ser eliminado
	 */
	@Override
	public void eliminarDocumento(final DocumentoDTO documento) {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarDocumento(documento={}) - start", documento);
		}

		final List<DocumentoDTO> docs = super.getDocumentos();
		for (final DocumentoDTO doc : docs) {
			if (doc.getNumeroSade().equals(documento.getNumeroSade())) {
				super.desagregarDocumento(documento);
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarDocumento(Documento) - end");
		}
	}

	@Override
	public boolean existenExpedientesDefinitivos() {
		if (logger.isDebugEnabled()) {
			logger.debug("existenExpedientesDefinitivos() - start");
		}

		this.expedientesEnTramitacion();

		if (logger.isDebugEnabled()) {
			logger.debug("existenExpedientesDefinitivos() - end - return value={}", this.existenExpedientesDefinitivos);
		}
		return this.existenExpedientesDefinitivos;
	}

	@Override
	public boolean existenExpedientesFusionados() {
		if (logger.isDebugEnabled()) {
			logger.debug("existenExpedientesFusionados() - start");
		}

		this.expedientesEnTramitacion();

		if (logger.isDebugEnabled()) {
			logger.debug("existenExpedientesFusionados() - end - return value={}", this.existenExpedientesFusionados);
		}
		return this.existenExpedientesFusionados;
	}

	@Override
	public boolean existenExpedientesTramitacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("existenExpedientesTramitacion() - start");
		}

		this.expedientesEnTramitacion();

		if (logger.isDebugEnabled()) {
			logger.debug("existenExpedientesTramitacion() - end - return value={}", this.existenExpedientesTramitacion);
		}
		return this.existenExpedientesTramitacion;
	}

	private void expedientesEnTramitacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("expedientesEnTramitacion() - start");
		}

		final List<ExpedienteAsociadoEntDTO> asociados = this.getListaExpedientesAsociados();
		for (final Object element : asociados) {
			final ExpedienteAsociadoEntDTO expedienteAsociado = (ExpedienteAsociadoEntDTO) element;
			if (expedienteAsociado.getEsExpedienteAsociadoTC() == null
					&& expedienteAsociado.getEsExpedienteAsociadoFusion() != null) {
				this.existenExpedientesFusionados = true;
			} else if (expedienteAsociado.getEsExpedienteAsociadoFusion() == null
					&& expedienteAsociado.getEsExpedienteAsociadoTC() != null) {
				this.existenExpedientesTramitacion = true;
			}

			this.existenExpedientesDefinitivos = expedienteAsociado.getDefinitivo();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("expedientesEnTramitacion() - end");
		}
		return;
	}

	@Override
	public Integer getAnio() {
		return anio;
	}

	@Override
	public List<ArchivoDeTrabajoDTO> getArchivosDeTrabajo() {
		return archivosDeTrabajo;
	}

	@Override
	public boolean getBloqueado() {
		return bloqueado;
	}

	@Override
	public String getCodigoCaratula() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoCaratula() - start");
		}

		// Para no romper como está hecho actualmente, se carga la primera vez
		// que se requiere el código Carátula
		if (this.codigoCaratula == null) {
			final StringBuilder codigoCaratulaBuilder = new StringBuilder();

			if (this.tipoDocumento != null) {
				codigoCaratulaBuilder.append(this.tipoDocumento.toUpperCase());
			}

			if (this.secuencia == null) {
				this.secuencia = "   ";
			}

			codigoCaratulaBuilder.append("-");
			codigoCaratulaBuilder.append(this.anio);
			codigoCaratulaBuilder.append("-");
			codigoCaratulaBuilder.append(BusinessFormatHelper.completarConCerosNumActuacion(this.numero));
			codigoCaratulaBuilder.append("-");
			codigoCaratulaBuilder.append(this.secuencia);
			codigoCaratulaBuilder.append("-");
			if (this.codigoReparticionActuacion != null) {
				codigoCaratulaBuilder.append(this.codigoReparticionActuacion.toUpperCase());
			}

			codigoCaratulaBuilder.append("-");
			if (this.codigoReparticionUsuario != null) {
				codigoCaratulaBuilder.append(this.codigoReparticionUsuario.toUpperCase());
			}

			this.codigoCaratula = codigoCaratulaBuilder.toString();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoCaratula() - end - return value={}", this.codigoCaratula);
		}
		return this.codigoCaratula;
	}

	@Override
	public String getCodigoReparticionActuacion() {
		return codigoReparticionActuacion;
	}

	@Override
	public String getCodigoReparticionUsuario() {
		return codigoReparticionUsuario;
	}

	@Override
	public List<String> getCodigosDeDocumentos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodigosDeDocumentos() - start");
		}

		final List<String> resultado = new ArrayList<>();
		for (final DocumentoDTO d : getDocumentos()) {
			resultado.add(d.getNumeroSade());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodigosDeDocumentos() - end - return value={}", resultado);
		}
		return resultado;
	}
 
	/**
	 * @return the cantidadSubsanar
	 */
	public Integer getCantidadSubsanar() {
		return cantidadSubsanar;
	}

	/**
	 * @param cantidadSubsanar the cantidadSubsanar to set
	 */
	public void setCantidadSubsanar(Integer cantidadSubsanar) {
		this.cantidadSubsanar = cantidadSubsanar;
	}

	@Override
	public Boolean getDefinitivo() {
		return definitivo;
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}

	public ExpedienteElectronicoConSuspensionDTO getEeConSuspension() {
		return this.eeConSuspension;
	}

	@Override
	public Boolean getEsCabeceraTC() {
		return esCabeceraTC;
	}

	@Override
	public Boolean getEsElectronico() {
		return esElectronico;
	}

	@Override
	public Boolean getEsReservado() {
		return esReservado;
	}

	@Override
	public String getEstado() {
		return estado;
	}

	@Override
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public Date getFechaDepuracion() {
		return fechaDepuracion;
	}

	@Override
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	@Override
	public Date getFechaReserva() {
		return fechaReserva;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getIdWorkflow() {
		return idWorkflow;
	}

	@Override
	public List<ExpedienteAsociadoEntDTO> getListaExpedientesAsociados() {
		return listaExpedientesAsociados;
	}

	@Override
	public List<ExpedienteMetadataDTO> getMetadatosDeTrata() {
		return metadatosDeTrata;
	}

	@Override
	public Integer getNumero() {
		return numero;
	}

	@Override
	public String getSecuencia() {
		return secuencia;
	}

	@Override
	public String getSistemaApoderado() {
		return sistemaApoderado;
	}

	@Override
	public String getSistemaCreador() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSistemaCreador() - start");
		}

		if (sistemaCreador == null) {
			this.sistemaCreador = "";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSistemaCreador() - end - return value={}", this.sistemaCreador);
		}
		return this.sistemaCreador;
	}

	public Boolean getSolicitarArchivo() {
		return solicitarArchivo;
	}

	@Override
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	@Override
	public Boolean getTramitacionLibre() {
		return tramitacionLibre;
	}

	@Override
	public TrataDTO getTrata() {

		return this.trata;
	}

	@Override
	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	@Override
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	@Override
	public String getUsuarioReserva() {
		return usuarioReserva;
	}

	public void hacerDefinitivosArchivosDeTrabajo() {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosArchivosDeTrabajo() - start");
		}

		for (final ArchivoDeTrabajoDTO a : this.getArchivosDeTrabajo()) {
			if (!a.isDefinitivo()) {
				a.setDefinitivo(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosArchivosDeTrabajo() - end");
		}
	}

	public void hacerDefinitivosArchivosDeTranajoVinculadosPor(final String loggedUsername) {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosArchivosDeTranajoVinculadosPor(loggedUsername={}) - start", loggedUsername);
		}

		for (final ArchivoDeTrabajoDTO t : this.getArchivosDeTrabajo()) {
			if (!t.isDefinitivo() && t.getUsuarioAsociador().equals(loggedUsername)) {
				t.setDefinitivo(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosArchivosDeTranajoVinculadosPor(String) - end");
		}
	}

	public void hacerDefinitivosAsociados() {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosAsociados() - start");
		}

		for (final ExpedienteAsociadoEntDTO e : this.getListaExpedientesAsociados()) {
			if (!e.getDefinitivo()) {
				e.setDefinitivo(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosAsociados() - end");
		}
	}

	public void hacerDefinitivosDocumentosVinculadosPor(final String loggedUsername) {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosDocumentosVinculadosPor(loggedUsername={}) - start", loggedUsername);
		}

		for (final DocumentoDTO d : this.getDocumentos()) {
			if (!d.getDefinitivo() && d.getUsuarioAsociador().equals(loggedUsername)) {
				d.setDefinitivo(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerDefinitivosDocumentosVinculadosPor(String) - end");
		}
	}

	public void hacerDocsDefinitivos() {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerDocsDefinitivos() - start");
		}

		for (final DocumentoDTO d : getDocumentos()) {
			if (!d.getDefinitivo()) {
				d.setDefinitivo(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerDocsDefinitivos() - end");
		}
	}

	@Override
	public List<ArchivoDeTrabajoDTO> obtenerArchivosDeTrabajoAcumulado(final String reparticionUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerArchivosDeTrabajoAcumulado(reparticionUsuario={}) - start", reparticionUsuario);
		}

		final List<ArchivoDeTrabajoDTO> archivosDeTrabajoAUX = new ArrayList<>();
		for (final ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajo) {
			for (final ReparticionParticipanteDTO reparticionesParticipante : archivoDeTrabajo
					.getReparticionesParticipantes()) {
				if (reparticionesParticipante.getReparticion().equals(reparticionUsuario)) {
					archivosDeTrabajoAUX.add(archivoDeTrabajo);
					break;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerArchivosDeTrabajoAcumulado(String) - end - return value={}", archivosDeTrabajoAUX);
		}
		return archivosDeTrabajoAUX;
	}

	/**
	 * EstÃƒÂ© mÃƒÂ©todo vÃƒÂ­ncula uno o varios
	 * <code>com.egoveris.te.core.api.entidades.tramitacion.expediente.Documento</code> del
	 * <code>com.egoveris.te.core.api.entidades.tramitacion.expediente.ExpedienteElectronico</code>
	 *
	 * @param <code>com.egoveris.te.core.api.entidades.tramitacion.expediente.Documento
	 *            </code> documentoGEDO
	 * @throws <code>Exception</code>
	 *             excepciÃƒÂ³n REFACTORME Se deberÃƒÂ­a crear una jerarquÃƒÂ­a
	 *             para excepciones de <code>Business</code>, sacar
	 *             <code>Exception</code> para subir el error.
	 */
	@Override
	public void removeDoc(final DocumentoDTO documentoGEDO)
			throws RemoveDocException {
		if (logger.isDebugEnabled()) {
			logger.debug("removeDoc(documentoGEDO={}) - start", documentoGEDO);
		}

		assertDocGedo(documentoGEDO);
		for (final DocumentoDTO doc : getDocumentos()) {
			if (doc.getNumeroSade().equals(documentoGEDO.getNumeroSade())) {
				assertDocGedo(doc);
				assertDocsEnExpedienteElectronico(this.getDocumentos().contains(documentoGEDO));
				this.getDocumentos().remove(documentoGEDO);
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("removeDoc(com.egoveris.te.core.api.entidades.tramitacion.expediente.Documento) - end");
		}
	}

	@Override
	public void removeDocsNoDefinitivos() {
		if (logger.isDebugEnabled()) {
			logger.debug("removeDocsNoDefinitivos() - start");
		}

		final Iterator<DocumentoDTO> i = getDocumentos().iterator();
		while (i.hasNext()) {
			final DocumentoDTO d = i.next();
			if (!d.getDefinitivo()) {
				i.remove();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("removeDocsNoDefinitivos() - end");
		}
	}

	public void removeExpAsociadosNoDefinitivos() {
		if (logger.isDebugEnabled()) {
			logger.debug("removeExpAsociadosNoDefinitivos() - start");
		}

		final Iterator<ExpedienteAsociadoEntDTO> i = getListaExpedientesAsociados().iterator();
		while (i.hasNext()) {
			final ExpedienteAsociadoEntDTO e = i.next();
			if (!e.getDefinitivo()) {
				i.remove();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("removeExpAsociadosNoDefinitivos() - end");
		}
	}

	@Override
	public void setAnio(final Integer anio) {
		this.anio = anio;
	}

	@Override
	public void setArchivosDeTrabajo(final List<ArchivoDeTrabajoDTO> archivosDeTrabajo) {
		this.archivosDeTrabajo = archivosDeTrabajo;
	}

	@Override
	public void setBloqueado(final boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	@Override
	public void setCodigoReparticionActuacion(final String codigoReparticionActuacion) {
		this.codigoReparticionActuacion = codigoReparticionActuacion;
	}

	@Override
	public void setCodigoReparticionUsuario(final String codigoReparticionUsuario) {
		this.codigoReparticionUsuario = codigoReparticionUsuario;
	}

	@Override
	public void setDefinitivo(final Boolean definitivo) {
		this.definitivo = definitivo;
	}

	@Override
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEeConSuspension(final ExpedienteElectronicoConSuspensionDTO eeConSuspension) {
		this.eeConSuspension = eeConSuspension;
	}

	@Override
	public void setEsCabeceraTC(final Boolean esCabeceraTC) {
		this.esCabeceraTC = esCabeceraTC;
	}

	@Override
	public void setEsElectronico(final Boolean esElectronico) {
		this.esElectronico = esElectronico;
	}

	@Override
	public void setEsReservado(final Boolean esReservado) {
		this.esReservado = esReservado;
	}

	// ********************************************
	// *** TRAMITACION CONJUNTA
	// ********************************************

	@Override
	public void setEstado(final String estado) {
		this.estado = estado;
	}

	@Override
	public void setFechaArchivo(final Date date) {
	}

	@Override
	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setFechaDepuracion(final Date fechaDepuracion) {
		this.fechaDepuracion = fechaDepuracion;
	}

	@Override
	public void setFechaModificacion(final Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}
	
	// ********************************************
	// *** VINCULACION - DOCUMENTOS GEDO
	// ********************************************

    @Override
	public void setFechaReserva(final Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	@Override
	public void setFechaSolicitudArchivo(final Date date) {

	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public void setIdWorkflow(final String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	// ********************************************
	// *** DES-VINCULACION - DOCUMENTOS GEDO
	// ********************************************

	@Override
	public void setListaExpedientesAsociados(final List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados) {
		this.listaExpedientesAsociados = listaExpedientesAsociados;
	}

	@Override
	public void setMetadatosDeTrata(final List<ExpedienteMetadataDTO> metadatosDeTrata) {
		this.metadatosDeTrata = metadatosDeTrata;
	}

	@Override
	public void setNumero(final Integer numero) {
		this.numero = numero;
	}

	@Override
	public void setSecuencia(final String secuencia) {
		this.secuencia = secuencia;
	}


	@Override
	public void setSistemaApoderado(final String sistemaApoderado) {
		this.sistemaApoderado = sistemaApoderado;
	}

	@Override
	public void setSistemaCreador(final String sistemaCreador) {
		this.sistemaCreador = sistemaCreador;
	}

	public void setSolicitarArchivo(final Boolean solicitarArchivo) {
		this.solicitarArchivo = solicitarArchivo;
	}

	@Override
	public void setTipoDocumento(final String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@Override
	public void setTramitacionLibre(final Boolean tramitacionLibre) {
		this.tramitacionLibre = tramitacionLibre;
	}

	@Override
	public void setTrata(final TrataDTO trata) {
		this.trata = trata;
	}

	@Override
	public void setUsuarioCreador(final String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	@Override
	public void setUsuarioModificacion(final String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	@Override
	public void setUsuarioReserva(final String usuarioReserva) {
		this.usuarioReserva = usuarioReserva;
	}

	public boolean tienenExpedientesEnTCDefinitivos() {
		if (logger.isDebugEnabled()) {
			logger.debug("tienenExpedientesEnTCDefinitivos() - start");
		}

		boolean bool = false;
		for (final ExpedienteAsociadoEntDTO e : getListaExpedientesAsociados()) {
			if (e.getEsExpedienteAsociadoTC() != null) {
				bool = bool || e.getEsExpedienteAsociadoTC();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("tienenExpedientesEnTCDefinitivos() - end - return value={}", bool);
		}
		return bool;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("");
		sb.append("ExpedienteElectronico [").append(id != null ? "id=" + id + ", " : "")
				.append(descripcion != null ? "descripcion=" + descripcion + ", " : "")
				.append(trata != null ? "trata.toString() = " + trata.toString() + ", " : "")
				.append(usuarioCreador != null ? "usuarioCreador=" + usuarioCreador + ", " : "")
				.append(fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : "")
				.append(usuarioModificacion != null ? "usuarioModificacion=" + usuarioModificacion + ", " : "")
				.append(fechaModificacion != null ? "fechaModificacion=" + fechaModificacion + ", " : "")
				.append(fechaDepuracion != null ? "fechaDepuracion=" + fechaDepuracion + ", " : "")
				.append(tipoDocumento != null ? "tipoDocumento=" + tipoDocumento + ", " : "")
				.append(anio != null ? "anio=" + anio + ", " : "")
				.append(numero != null ? "numero=" + numero + ", " : "")
				.append(secuencia != null ? "secuencia=" + secuencia + ", " : "")
				.append(definitivo != null ? "definitivo=" + definitivo + ", " : "")
				.append(codigoReparticionUsuario != null ? "codigoReparticionUsuario=" + codigoReparticionUsuario + ", "
						: "")
				.append(codigoReparticionActuacion != null
						? "codigoReparticionActuacion=" + codigoReparticionActuacion + ", " : "")
				.append(esElectronico != null ? "esElectronico=" + esElectronico + ", " : "")
				.append(metadatosDeTrata != null ? "metadatosDeTrata=" + metadatosDeTrata + ", " : "")
				.append(archivosDeTrabajo != null ? "archivosDeTrabajo=" + archivosDeTrabajo + ", " : "")
				.append(listaExpedientesAsociados != null
						? "listaExpedientesAsociados=" + listaExpedientesAsociados + ", " : "")
				.append(idWorkflow != null ? "idWorkflow=" + idWorkflow + ", " : "")
				.append(estado != null ? "estado=" + estado + ", " : "")
				.append(esCabeceraTC != null ? "esCabeceraTC=" + esCabeceraTC + ", " : "")
				.append(esReservado != null ? "esReservado=" + esReservado + ", " : "")
				.append(usuarioReserva != null ? "usuarioReserva=" + usuarioReserva + ", " : "")
				.append(fechaReserva != null ? "fechaReserva=" + fechaReserva + ", " : "")
				.append(sistemaCreador != null ? "sistemaCreador=" + sistemaCreador + ", " : "")
				.append(sistemaApoderado != null ? "sistemaApoderado=" + sistemaApoderado + ", " : "")
				.append("bloqueado=").append(bloqueado).append(", ")
				.append(tramitacionLibre != null ? "tramitacionLibre=" + tramitacionLibre : "").append("]");

		return sb.toString();
	}

  public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

  public PropertyConfigurationDTO getPropertyResultado() {
    return propertyResultado;
  }

  public void setPropertyResultado(PropertyConfigurationDTO propertyResultado) {
    this.propertyResultado = propertyResultado;
  }

  public boolean getBlocked() {
		return blocked;
  }
	  
 public void setBlocked(boolean blocked) {
	this.blocked = blocked;
  }

public OperacionDTO getOperacion() {
	return operacion;
}

public void setOperacion(OperacionDTO operacion) {
	this.operacion = operacion;
}

}
