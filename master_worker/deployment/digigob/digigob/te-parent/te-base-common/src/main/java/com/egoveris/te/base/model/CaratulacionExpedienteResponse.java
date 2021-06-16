package com.egoveris.te.base.model;


import com.egoveris.te.model.model.ExpedienteMetadataExternal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CaratulacionExpedienteResponse implements Serializable {
	
	private static final long serialVersionUID = -7398173788590085334L;
	
	private Integer idEE;
	private String descripcionEE;
	
	//Datos de la trata del EE
	private Integer idTrata;
	private String codigoTrata;
	private String descripcionTrata;
	private Integer idTrataSade;
	private String trataSade;
	private String estadoTrata;
	private Boolean reservadoTrata;
	
	private String idWorkflow;
	private String estado;
	private String codigoCaratula = null;
		
	
	public Integer getIdEE() {
		return idEE;
	}

	public void setIdEE(Integer idEE) {
		this.idEE = idEE;
	}

	public String getDescripcionEE() {
		return descripcionEE;
	}

	public void setDescripcionEE(String descripcionEE) {
		this.descripcionEE = descripcionEE;
	}

	public Integer getIdTrata() {
		return idTrata;
	}

	public void setIdTrata(Integer idTrata) {
		this.idTrata = idTrata;
	}

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public Integer getIdTrataSade() {
		return idTrataSade;
	}

	public void setIdTrataSade(Integer idTrataSade) {
		this.idTrataSade = idTrataSade;
	}

	public String getTrataSade() {
		return trataSade;
	}

	public void setTrataSade(String trataSade) {
		this.trataSade = trataSade;
	}

	public String getEstadoTrata() {
		return estadoTrata;
	}

	public void setEstadoTrata(String estadoTrata) {
		this.estadoTrata = estadoTrata;
	}

	public Boolean getReservadoTrata() {
		return reservadoTrata;
	}

	public void setReservadoTrata(Boolean reservadoTrata) {
		this.reservadoTrata = reservadoTrata;
	}

	public String getIdWorkflow() {
		return idWorkflow;
	}

	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoCaratula() {
		return codigoCaratula;
	}

	public void setCodigoCaratula(String codigoCaratula) {
		this.codigoCaratula = codigoCaratula;
	}

	public List<ExpedienteMetadataExternal> getDatoVariable() {
		return datoVariable;
	}

	public void setDatoVariable(List<ExpedienteMetadataExternal> datoVariable) {
		this.datoVariable = datoVariable;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public Boolean getDefinitivo() {
		return definitivo;
	}

	public void setDefinitivo(Boolean definitivo) {
		this.definitivo = definitivo;
	}

	public String getCodigoReparticionUsuario() {
		return codigoReparticionUsuario;
	}

	public void setCodigoReparticionUsuario(String codigoReparticionUsuario) {
		this.codigoReparticionUsuario = codigoReparticionUsuario;
	}

	public String getCodigoReparticionActuacion() {
		return codigoReparticionActuacion;
	}

	public void setCodigoReparticionActuacion(String codigoReparticionActuacion) {
		this.codigoReparticionActuacion = codigoReparticionActuacion;
	}

	public Boolean getEsElectronico() {
		return esElectronico;
	}

	public void setEsElectronico(Boolean esElectronico) {
		this.esElectronico = esElectronico;
	}

	public List<ExpedienteMetadataExternal> getMetadatosDeTrata() {
		return metadatosDeTrata;
	}

	public void setMetadatosDeTrata(
			List<ExpedienteMetadataExternal> metadatosDeTrata) {
		this.metadatosDeTrata = metadatosDeTrata;
	}

	public List<ArchivoDeTrabajo> getArchivosDeTrabajo() {
		return archivosDeTrabajo;
	}

	public void setArchivosDeTrabajo(List<ArchivoDeTrabajo> archivosDeTrabajo) {
		this.archivosDeTrabajo = archivosDeTrabajo;
	}

	public List<ExpedienteAsociado> getListaExpedientesAsociados() {
		return listaExpedientesAsociados;
	}

	public void setListaExpedientesAsociados(
			List<ExpedienteAsociado> listaExpedientesAsociados) {
		this.listaExpedientesAsociados = listaExpedientesAsociados;
	}

	private List<ExpedienteMetadataExternal> datoVariable = new ArrayList<>();
	
	/** 
	 * @author cearagon
	 *	Inner Class: La presente clase interna es a los efectos de armar el tipo de dato Metadata.
	 */
	public class Metadata{
		private String nombreMetadata;
		private boolean obligatoriedadMetadata;
		private String tipoMetadata;
		private int ordenMetadata;
		public String getNombreMetadata() {
			return nombreMetadata;
		}
		
		public void setNombreMetadata(String nombreMetadata) {
			this.nombreMetadata = nombreMetadata;
		}
		public boolean isObligatoriedadMetadata() {
			return obligatoriedadMetadata;
		}
		public void setObligatoriedadMetadata(boolean obligatoriedadMetadata) {
			this.obligatoriedadMetadata = obligatoriedadMetadata;
		}
		public String getTipoMetadata() {
			return tipoMetadata;
		}
		public void setTipoMetadata(String tipoMetadata) {
			this.tipoMetadata = tipoMetadata;
		}
		public int getOrdenMetadata() {
			return ordenMetadata;
		}
		public void setOrdenMetadata(int ordenMetadata) {
			this.ordenMetadata = ordenMetadata;
		}		
	}//Fin class Metadata
	
	private String usuarioCreador;
	private Date fechaCreacion;
	private String usuarioModificacion;
	private Date fechaModificacion;
	private String tipoDocumento;
	private String anio;
	private String numero;
	private String secuencia;
	private Boolean definitivo = false;
	private String codigoReparticionUsuario;
	private String codigoReparticionActuacion;
	private Boolean esElectronico;
	private List<ExpedienteMetadataExternal> metadatosDeTrata = new ArrayList<>();
	
	
	private transient List<ArchivoDeTrabajo> archivosDeTrabajo = new ArrayList<>();
	/** 
	 * @author cearagon
	 *	Inner Class: La presente clase interna es a los efectos de armar el tipo de dato ArchivoDeTrabajo.
	 */
	public class ArchivoDeTrabajo{
		private Integer idArchivoDeTrabajo;
		private String nombreArchivoDeTrabajo;
		private byte[] dataArchivoDeTrabajo;
		private boolean definitivoArchivoDeTrabajo = false;
		private String usuarioAsociadorArchivoDeTrabajo;
		private Date fechaAsociacionArchivoDeTrabajo;
		private String idTaskArchivoDeTrabajo;
		public Integer getIdArchivoDeTrabajo() {
			return idArchivoDeTrabajo;
		}
		public void setIdArchivoDeTrabajo(Integer idArchivoDeTrabajo) {
			this.idArchivoDeTrabajo = idArchivoDeTrabajo;
		}
		public String getNombreArchivoDeTrabajo() {
			return nombreArchivoDeTrabajo;
		}
		public void setNombreArchivoDeTrabajo(String nombreArchivoDeTrabajo) {
			this.nombreArchivoDeTrabajo = nombreArchivoDeTrabajo;
		}
		public byte[] getDataArchivoDeTrabajo() {
			return dataArchivoDeTrabajo;
		}
		public void setDataArchivoDeTrabajo(byte[] dataArchivoDeTrabajo) {
			this.dataArchivoDeTrabajo = dataArchivoDeTrabajo;
		}
		public boolean isDefinitivoArchivoDeTrabajo() {
			return definitivoArchivoDeTrabajo;
		}
		public void setDefinitivoArchivoDeTrabajo(boolean definitivoArchivoDeTrabajo) {
			this.definitivoArchivoDeTrabajo = definitivoArchivoDeTrabajo;
		}
		public String getUsuarioAsociadorArchivoDeTrabajo() {
			return usuarioAsociadorArchivoDeTrabajo;
		}
		public void setUsuarioAsociadorArchivoDeTrabajo(
				String usuarioAsociadorArchivoDeTrabajo) {
			this.usuarioAsociadorArchivoDeTrabajo = usuarioAsociadorArchivoDeTrabajo;
		}
		public Date getFechaAsociacionArchivoDeTrabajo() {
			return fechaAsociacionArchivoDeTrabajo;
		}
		public void setFechaAsociacionArchivoDeTrabajo(
				Date fechaAsociacionArchivoDeTrabajo) {
			this.fechaAsociacionArchivoDeTrabajo = fechaAsociacionArchivoDeTrabajo;
		}
		public String getIdTaskArchivoDeTrabajo() {
			return idTaskArchivoDeTrabajo;
		}
		public void setIdTaskArchivoDeTrabajo(String idTaskArchivoDeTrabajo) {
			this.idTaskArchivoDeTrabajo = idTaskArchivoDeTrabajo;
		}	
	}//Fin class ArchivoDeTrabajo
	
	private transient List<ExpedienteAsociado> listaExpedientesAsociados = new ArrayList<>();
	/** 
	 * @author cearagon
	 *	Inner Class: La presente clase interna es a los efectos de armar el tipo de dato ExpedienteAsociado.
	 */
	public class ExpedienteAsociado{
		private Integer idExpedienteAsociado;	
		private String tipoDocumentoExpedienteAsociado;
		private String anioExpedienteAsociado;
		private String numeroExpedienteAsociado;
		private String secuenciaExpedienteAsociado;
		private Boolean definitivoExpedienteAsociado=false;
		private String codigoReparticionActuacionExpedienteAsociado;
		private String codigoReparticionUsuarioExpedienteAsociado;
		private Boolean esElectronicoExpedienteAsociado;
		private String trataExpedienteAsociado;
		private Integer idCodigoCaratulaExpedienteAsociado;
		private String usuarioAsociadorExpedienteAsociado;
		private Date fechaAsociacionExpedienteAsociado;
		private String idTaskExpedienteAsociado;
		
		public Integer getIdExpedienteAsociado() {
			return idExpedienteAsociado;
		}
		public void setIdExpedienteAsociado(Integer idExpedienteAsociado) {
			this.idExpedienteAsociado = idExpedienteAsociado;
		}
		public String getTipoDocumentoExpedienteAsociado() {
			return tipoDocumentoExpedienteAsociado;
		}
		public void setTipoDocumentoExpedienteAsociado(
				String tipoDocumentoExpedienteAsociado) {
			this.tipoDocumentoExpedienteAsociado = tipoDocumentoExpedienteAsociado;
		}
		public String getAnioExpedienteAsociado() {
			return anioExpedienteAsociado;
		}
		public void setAnioExpedienteAsociado(String anioExpedienteAsociado) {
			this.anioExpedienteAsociado = anioExpedienteAsociado;
		}
		public String getNumeroExpedienteAsociado() {
			return numeroExpedienteAsociado;
		}
		public void setNumeroExpedienteAsociado(String numeroExpedienteAsociado) {
			this.numeroExpedienteAsociado = numeroExpedienteAsociado;
		}
		public String getSecuenciaExpedienteAsociado() {
			return secuenciaExpedienteAsociado;
		}
		public void setSecuenciaExpedienteAsociado(String secuenciaExpedienteAsociado) {
			this.secuenciaExpedienteAsociado = secuenciaExpedienteAsociado;
		}
		public Boolean getDefinitivoExpedienteAsociado() {
			return definitivoExpedienteAsociado;
		}
		public void setDefinitivoExpedienteAsociado(Boolean definitivoExpedienteAsociado) {
			this.definitivoExpedienteAsociado = definitivoExpedienteAsociado;
		}
		public String getCodigoReparticionActuacionExpedienteAsociado() {
			return codigoReparticionActuacionExpedienteAsociado;
		}
		public void setCodigoReparticionActuacionExpedienteAsociado(
				String codigoReparticionActuacionExpedienteAsociado) {
			this.codigoReparticionActuacionExpedienteAsociado = codigoReparticionActuacionExpedienteAsociado;
		}
		public String getCodigoReparticionUsuarioExpedienteAsociado() {
			return codigoReparticionUsuarioExpedienteAsociado;
		}
		public void setCodigoReparticionUsuarioExpedienteAsociado(
				String codigoReparticionUsuarioExpedienteAsociado) {
			this.codigoReparticionUsuarioExpedienteAsociado = codigoReparticionUsuarioExpedienteAsociado;
		}
		public Boolean getEsElectronicoExpedienteAsociado() {
			return esElectronicoExpedienteAsociado;
		}
		public void setEsElectronicoExpedienteAsociado(
				Boolean esElectronicoExpedienteAsociado) {
			this.esElectronicoExpedienteAsociado = esElectronicoExpedienteAsociado;
		}
		public String getTrataExpedienteAsociado() {
			return trataExpedienteAsociado;
		}
		public void setTrataExpedienteAsociado(String trataExpedienteAsociado) {
			this.trataExpedienteAsociado = trataExpedienteAsociado;
		}
		public Integer getIdCodigoCaratulaExpedienteAsociado() {
			return idCodigoCaratulaExpedienteAsociado;
		}
		public void setIdCodigoCaratulaExpedienteAsociado(
				Integer idCodigoCaratulaExpedienteAsociado) {
			this.idCodigoCaratulaExpedienteAsociado = idCodigoCaratulaExpedienteAsociado;
		}
		public String getUsuarioAsociadorExpedienteAsociado() {
			return usuarioAsociadorExpedienteAsociado;
		}
		public void setUsuarioAsociadorExpedienteAsociado(
				String usuarioAsociadorExpedienteAsociado) {
			this.usuarioAsociadorExpedienteAsociado = usuarioAsociadorExpedienteAsociado;
		}
		public Date getFechaAsociacionExpedienteAsociado() {
			return fechaAsociacionExpedienteAsociado;
		}
		public void setFechaAsociacionExpedienteAsociado(
				Date fechaAsociacionExpedienteAsociado) {
			this.fechaAsociacionExpedienteAsociado = fechaAsociacionExpedienteAsociado;
		}
		public String getIdTaskExpedienteAsociado() {
			return idTaskExpedienteAsociado;
		}
		public void setIdTaskExpedienteAsociado(String idTaskExpedienteAsociado) {
			this.idTaskExpedienteAsociado = idTaskExpedienteAsociado;
		} 
	}//Fin class ExpedienteAsociado	
	
	//Metodos propios

	//Constructores
	public CaratulacionExpedienteResponse(){
		super();
	}
	
	public CaratulacionExpedienteResponse(Integer idEE,
			String descripcionEE, Integer idTrata, String codigoTrata,
			String descripcionTrata, Integer idTrataSade, String trataSade,
			String estadoTrata, Boolean reservadoTrata, String idWorkflow,
			String estado, String codigoCaratula, List<ExpedienteMetadataExternal> datoVariable,
			String usuarioCreador, Date fechaCreacion,
			String usuarioModificacion, Date fechaModificacion,
			String tipoDocumento, String anio, String numero, String secuencia,
			Boolean definitivo, String codigoReparticionUsuario,
			String codigoReparticionActuacion, Boolean esElectronico,
			List<ExpedienteMetadataExternal> metadatosDeTrata,
			List<ArchivoDeTrabajo> archivosDeTrabajo,
			List<ExpedienteAsociado> listaExpedientesAsociados) {
		super();
		this.idEE = idEE;
		this.descripcionEE = descripcionEE;
		this.idTrata = idTrata;
		this.codigoTrata = codigoTrata;
		this.descripcionTrata = descripcionTrata;
		this.idTrataSade = idTrataSade;
		this.trataSade = trataSade;
		this.estadoTrata = estadoTrata;
		this.reservadoTrata = reservadoTrata;
		this.idWorkflow = idWorkflow;
		this.estado = estado;
		this.codigoCaratula = codigoCaratula;
		this.datoVariable = datoVariable;
		this.usuarioCreador = usuarioCreador;
		this.fechaCreacion = fechaCreacion;
		this.usuarioModificacion = usuarioModificacion;
		this.fechaModificacion = fechaModificacion;
		this.tipoDocumento = tipoDocumento;
		this.anio = anio;
		this.numero = numero;
		this.secuencia = secuencia;
		this.definitivo = definitivo;
		this.codigoReparticionUsuario = codigoReparticionUsuario;
		this.codigoReparticionActuacion = codigoReparticionActuacion;
		this.esElectronico = esElectronico;
		this.metadatosDeTrata = metadatosDeTrata;
		this.archivosDeTrabajo = archivosDeTrabajo;
		this.listaExpedientesAsociados = listaExpedientesAsociados;
	}
	

	
}
