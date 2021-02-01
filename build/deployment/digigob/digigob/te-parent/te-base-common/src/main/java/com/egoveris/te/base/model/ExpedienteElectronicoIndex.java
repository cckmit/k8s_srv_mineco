/**
 * 
 */
package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpedienteElectronicoIndex implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ExpedienteElectronicoIndex.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field("id")
	private Long id;
	@Field("fecha_creacion")
	private Date fechaCreacion;
	@Field("fecha_modificacion")
	private Date fechamodificacion;
	@Field("descripcion")
	private String descripcion;
	@Field("usuario_creador")
	private String usuarioCreador;
	@Field("tipo_documento")
	private String tipoDocumento;
	@Field("anio")
	private Integer anio;
	@Field("numero")
	private Integer numero;
	@Field("codigo_reparticion_actuacion")
	private String codigoReparticionActuacion;
	@Field("codigo_reparticion_usuario")
	private String codigoReparticionUsuario;
	@Field("id_trata")
	private Long idTrata;
	@Field("codigo_trata")
	private String codigoTrata;
	@Field("nombre_metadato")
	private List<String> listNombreMetadato = new ArrayList<String>();
	@Field("valor_metadato")
	private List<String> listaValorMetadato = new ArrayList<String>();
	@Field("motivo")
	private String motivo;
	@Field("id_documento")
	private Long idDocumento;
	@Field("tipo_doc_ident")
	private String tipoDocumentoDocIdent;
	@Field("numero_documento")
	private String numeroDocumento;
	@Field("cuit_cuil")
	private String cuitCuil;
	@Field("domicilio")
	private String domicilio;
	@Field("piso")
	private String piso;
	@Field("departamento")
	private String departamento;
	@Field("codigo_postal")
	private String codigoPostal;
	
	@Field("assignee")
	private List<String> listaAssignee = new ArrayList<String>();
	@Field("estado")
	private String estado;
	
	@Field("value_str")
	private ArrayList<String> valueStr;
	@Field("value_int")
	private String valueInt;
	@Field("value_date")
	private String valueDate;
	@Field("value_double")
	private String valueDouble;
	
	@Field("id_workflow")
	private String idWorkflow;
	
	@Field("TIPO_DOCUMENTO_SOLICITANTE")
	private String tipoDocumentoSolicitante;
	@Field("NUMERO_DOCUMENTO_SOLICITANTE")
	private String numeroDocumentoSolicitante;
	@Field("ID_SOLICITANTE")
	private String idSolicitanteSolr;
	@Field("APELLIDO_SOLICITANTE")
	private String apellidoSolicitante;
	@Field("CODIGO_POSTAL")
	private String codigoPostalSolicitante;
	@Field("CUIT_CUIL_SOLICITANTE")
	private String cuitCuilSolicitante;
	@Field("DEPARTAMENTO")
	private String departamentoSolicitante;
	@Field("DOMICILIO")
	private String domicilioSolicitante;
	@Field("EMAIL")
	private String emailSolicitante;
	@Field("ID_DOCUMENTO")
	private String idTipoDocumentoSolicitante;
	@Field("NOMBRE_SOLICITANTE")
	private String nombreSolicitante;
	@Field("PISO")
	private String pisoSolicitante;
	@Field("RAZON_SOCIAL_SOLICITANTE")
	private String razonSocialSolicitante;
	@Field("SEGUNDO_APELLIDO_SOLICITANTE")
	private String segundoApellidoSolicitante;
	@Field("SEGUNDO_NOMBRE_SOLICITANTE")
	private String segundoNombreSolicitante;
	@Field("TERCER_APELLIDO_SOLICITANTE")
	private String tercerApellidoSolicitante;
	@Field("TERCER_NOMBRE_SOLICITANTE")
	private String tercerNombreSolicitante;
	@Field("ID_SOLICITANTE")
	private String solicitante;
	@Field("TELEFONO")
	private String telefono;
	@Field("MOTIVO_INTERNO")
	private String motivoInternoSolicitud;
	@Field("MOTIVO_EXTERNO")
	private String motivoExternoSolicitud;
	@Field("INTERNA")
	private String isInterna;
	
	@Field("SISTEMA_ORIGEN")
	private String sistemaOrigen;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getCodigoReparticionActuacion() {
		return codigoReparticionActuacion;
	}
	public void setCodigoReparticionActuacion(String codigoReparticionActuacion) {
		this.codigoReparticionActuacion = codigoReparticionActuacion;
	}
	public String getCodigoReparticionUsuario() {
		return codigoReparticionUsuario;
	}
	public void setCodigoReparticionUsuario(String codigoReparticionUsuario) {
		this.codigoReparticionUsuario = codigoReparticionUsuario;
	}
	public Long getIdTrata() {
		return idTrata;
	}
	public void setIdTrata(Long idTrata) {
		this.idTrata = idTrata;
	}
	public String getCodigoTrata() {
		return codigoTrata;
	}
	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}
	public List<String> getListNombreMetadato() {
		return listNombreMetadato;
	}
	public void setListNombreMetadato(List<String> listNombreMetadato) {
		this.listNombreMetadato = listNombreMetadato;
	}
	public void addListNombreMetadato(String nombreMetadato) {
		if (logger.isDebugEnabled()) {
			logger.debug("addListNombreMetadato(nombreMetadato={}) - start", nombreMetadato);
		}

		this.listNombreMetadato.add(nombreMetadato);

		if (logger.isDebugEnabled()) {
			logger.debug("addListNombreMetadato(String) - end");
		}
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public List<String> getListaValorMetadato() {
		return listaValorMetadato;
	}
	public void setListaValorMetadato(List<String> listaValorMetadato) {
		this.listaValorMetadato = listaValorMetadato;
	}
	public void addListValorMetadato(String valorMetadato) {
		if (logger.isDebugEnabled()) {
			logger.debug("addListValorMetadato(valorMetadato={}) - start", valorMetadato);
		}

		this.listaValorMetadato.add(valorMetadato);

		if (logger.isDebugEnabled()) {
			logger.debug("addListValorMetadato(String) - end");
		}
	}
	public Long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getTipoDocumentoDocIdent() {
		return tipoDocumentoDocIdent;
	}
	public void setTipoDocumentoDocIdent(String tipoDocumentoDocIdent) {
		this.tipoDocumentoDocIdent = tipoDocumentoDocIdent;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<String> getListaAssignee() {
		return listaAssignee;
	}
	public void setListaAssignee(List<String> listaAssignee) {
		this.listaAssignee = listaAssignee;
	}
	public void addListaAssignee(String assignee) {
		if (logger.isDebugEnabled()) {
			logger.debug("addListaAssignee(assignee={}) - start", assignee);
		}

		this.listaAssignee.add(assignee);

		if (logger.isDebugEnabled()) {
			logger.debug("addListaAssignee(String) - end");
		}
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getCuitCuil() {
		return cuitCuil;
	}
	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public ArrayList<String> getValueStr() {
		return this.valueStr;
	}
	public void setValueStr(ArrayList<String> valueStr) {
		this.valueStr = valueStr;
	}
	public String getValueInt() {
		return this.valueInt;
	}
	public void setValueInt(String valueInt) {
		this.valueInt = valueInt;
	}
	public String getValueDate() {
		return this.valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public String getValueDouble() {
		return this.valueDouble;
	}
	public void setValueDouble(String valueDouble) {
		this.valueDouble = valueDouble;
	}
	public String getIdWorkflow() {
		return idWorkflow;
	}
	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}
	public Date getFechamodificacion() {
		return fechamodificacion;
	}
	
	public void setTipoDocumentoSolicitante(String tipoDocumentoSolicitante) {
		this.tipoDocumentoSolicitante = tipoDocumentoSolicitante;
	}
	public String getTipoDocumentoSolicitante() {
		return tipoDocumentoSolicitante;
	}
	public void setNumeroDocumentoSolicitante(String numeroDocumentoSolicitante) {
		this.numeroDocumentoSolicitante = numeroDocumentoSolicitante;
	}
	public String getNumeroDocumentoSolicitante() {
		return numeroDocumentoSolicitante;
	}
	public void setIdSolicitanteSolr(String idSolicitanteSolr) {
		this.idSolicitanteSolr = idSolicitanteSolr;
	}
	public String getIdSolicitanteSolr() {
		return idSolicitanteSolr;
	}
	public void setApellidoSolicitante(String apellidoSolicitante) {
		this.apellidoSolicitante = apellidoSolicitante;
	}
	public String getApellidoSolicitante() {
		return apellidoSolicitante;
	}
	public void setCodigoPostalSolicitante(String codigoPostalSolicitante) {
		this.codigoPostalSolicitante = codigoPostalSolicitante;
	}
	public String getCodigoPostalSolicitante() {
		return codigoPostalSolicitante;
	}
	public void setCuitCuilSolicitante(String cuitCuilSolicitante) {
		this.cuitCuilSolicitante = cuitCuilSolicitante;
	}
	public String getCuitCuilSolicitante() {
		return cuitCuilSolicitante;
	}
	public void setDepartamentoSolicitante(String departamentoSolicitante) {
		this.departamentoSolicitante = departamentoSolicitante;
	}
	public String getDepartamentoSolicitante() {
		return departamentoSolicitante;
	}
	public void setDomicilioSolicitante(String domicilioSolicitante) {
		this.domicilioSolicitante = domicilioSolicitante;
	}
	public String getDomicilioSolicitante() {
		return domicilioSolicitante;
	}
	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}
	public String getEmailSolicitante() {
		return emailSolicitante;
	}
	public void setIdTipoDocumentoSolicitante(String idTipoDocumentoSolicitante) {
		this.idTipoDocumentoSolicitante = idTipoDocumentoSolicitante;
	}
	public String getIdTipoDocumentoSolicitante() {
		return idTipoDocumentoSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setPisoSolicitante(String pisoSolicitante) {
		this.pisoSolicitante = pisoSolicitante;
	}
	public String getPisoSolicitante() {
		return pisoSolicitante;
	}
	public void setRazonSocialSolicitante(String razonSocialSolicitante) {
		this.razonSocialSolicitante = razonSocialSolicitante;
	}
	public String getRazonSocialSolicitante() {
		return razonSocialSolicitante;
	}
	public void setSegundoApellidoSolicitante(String segundoApellidoSolicitante) {
		this.segundoApellidoSolicitante = segundoApellidoSolicitante;
	}
	public String getSegundoApellidoSolicitante() {
		return segundoApellidoSolicitante;
	}
	public void setSegundoNombreSolicitante(String segundoNombreSolicitante) {
		this.segundoNombreSolicitante = segundoNombreSolicitante;
	}
	public String getSegundoNombreSolicitante() {
		return segundoNombreSolicitante;
	}
	public void setTercerApellidoSolicitante(String tercerApellidoSolicitante) {
		this.tercerApellidoSolicitante = tercerApellidoSolicitante;
	}
	public String getTercerApellidoSolicitante() {
		return tercerApellidoSolicitante;
	}
	public void setTercerNombreSolicitante(String tercerNombreSolicitante) {
		this.tercerNombreSolicitante = tercerNombreSolicitante;
	}
	public String getTercerNombreSolicitante() {
		return tercerNombreSolicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setMotivoInternoSolicitud(String motivoInternoSolicitud) {
		this.motivoInternoSolicitud = motivoInternoSolicitud;
	}
	public String getMotivoInternoSolicitud() {
		return motivoInternoSolicitud;
	}
	public void setMotivoExternoSolicitud(String motivoExternoSolicitud) {
		this.motivoExternoSolicitud = motivoExternoSolicitud;
	}
	public String getMotivoExternoSolicitud() {
		return motivoExternoSolicitud;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setIsInterna(String isInterna) {
		this.isInterna = isInterna;
	}
	public String getIsInterna() {
		return isInterna;
	}
	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}
	public String getSistemaOrigen() {
		return sistemaOrigen;
	}
}
