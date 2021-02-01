package com.egoveris.ccomplejos.base.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cc_operation")
public class Operation extends AbstractCComplejoJPA {
	private static final long serialVersionUID = 1L;

	@Column(name = "CODIGO_OFICINA_ADUANA")
	protected String codigoOficinaAduana;
	@Column(name = "COD_OPERACION")
	protected String codOperacion;
	@Column(name = "NOMBRE_AGENCIA_ADUANA")
	protected String nombreAgenciaAduana;
	@Column(name = "PROCESSING_STATUS")
	protected String processingStatus;
	@Column(name = "CODIGO_DESTINACION")
	protected String codigoDestinacion;
	@Column(name = "CODIGO_AGENCIA_ADUANAS")
	protected String codigoAgenciaAduanas;
	@Column(name = "CODIGO_TRANSBORDO")
	protected String codigoTransbordo;
	@Column(name = "ACTUALIZADO_POR")
	protected String actualizadoPor;
	@Column(name = "CREADO_POR")
	protected String creadoPor;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "operation")
	protected List<ProductOperation> listProductOperacion;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "operation")
	protected List<ItemJPA> listaItem;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "operation")
	protected List<Bulto> listBulto;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "operation")
	protected List<Autorizacion> listAutorizacion;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_TRANSPORT")
	protected Transport transport;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_CONTACTO")
	protected Participantes contacto;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_IMPORTADOR")
	protected Participantes importador;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_CONSIGNATARIO")
	protected Participantes consignatario;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_INTERMEDIARIO")
	protected Participantes intermediario;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_EXPORTADOR_PRINCIPAL")
	protected Participantes exportadorPrincipal;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_REPRESENTANTE")
	protected Participantes representante;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_EXPORTADOR_SECUNDARIO")
	protected Participantes exportadorSecundario;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_INTEROP_HEADER")
	protected InteropHeader interopHeader;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_HEADER")
	protected Header header;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_FINANCIERO")
	protected Financiero financiero;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DETALLES_PUERTO")
	protected DetallesPuerto detallesPuerto;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DIN")
	protected Declaracion din;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DUS_AT")
	protected Declaracion dusAt;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DUS_LEG")
	protected Declaracion dusLeg;
	
	@Column(name = "FECHA_CREACION")
	protected Date fechaCreacion;
	@Column(name = "FECHA_ACTUALIZACION")
	protected Date fechaActualizacion;
	

	public String getCodigoOficinaAduana() {
		return codigoOficinaAduana;
	}

	public void setCodigoOficinaAduana(String codigoOficinaAduana) {
		this.codigoOficinaAduana = codigoOficinaAduana;
	}

	public String getCodOperacion() {
		return codOperacion;
	}

	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}

	public String getNombreAgenciaAduana() {
		return nombreAgenciaAduana;
	}

	public void setNombreAgenciaAduana(String nombreAgenciaAduana) {
		this.nombreAgenciaAduana = nombreAgenciaAduana;
	}

	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	public String getCodigoDestinacion() {
		return codigoDestinacion;
	}

	public void setCodigoDestinacion(String codigoDestinacion) {
		this.codigoDestinacion = codigoDestinacion;
	}

	public String getCodigoAgenciaAduanas() {
		return codigoAgenciaAduanas;
	}

	public void setCodigoAgenciaAduanas(String codigoAgenciaAduanas) {
		this.codigoAgenciaAduanas = codigoAgenciaAduanas;
	}

	public String getCodigoTransbordo() {
		return codigoTransbordo;
	}

	public void setCodigoTransbordo(String codigoTransbordo) {
		this.codigoTransbordo = codigoTransbordo;
	}

	public String getActualizadoPor() {
		return actualizadoPor;
	}

	public void setActualizadoPor(String actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}


	public List<ProductOperation> getListProductOperacions() {
		return listProductOperacion;
	}

	public void setListProductOperacions(List<ProductOperation> listProductOperacions) {
		this.listProductOperacion = listProductOperacions;
	}

	public List<ItemJPA> getListaItem() {
		return listaItem;
	}

	public void setListaItem(List<ItemJPA> listaItem) {
		this.listaItem = listaItem;
	}

	public List<Bulto> getListBultos() {
		return listBulto;
	}

	public void setListBultos(List<Bulto> listBultos) {
		this.listBulto = listBultos;
	}

	public List<Autorizacion> getListAutorizacions() {
		return listAutorizacion;
	}

	public void setListAutorizacions(List<Autorizacion> listAutorizacions) {
		this.listAutorizacion = listAutorizacions;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public Participantes getContacto() {
		return contacto;
	}

	public void setContacto(Participantes contacto) {
		this.contacto = contacto;
	}

	public Participantes getImportador() {
		return importador;
	}

	public void setImportador(Participantes importador) {
		this.importador = importador;
	}

	public Participantes getConsignatario() {
		return consignatario;
	}

	public void setConsignatario(Participantes consignatario) {
		this.consignatario = consignatario;
	}

	public Participantes getIntermediario() {
		return intermediario;
	}

	public void setIntermediario(Participantes intermediario) {
		this.intermediario = intermediario;
	}

	public Participantes getExportadorPrincipal() {
		return exportadorPrincipal;
	}

	public void setExportadorPrincipal(Participantes exportadorPrincipal) {
		this.exportadorPrincipal = exportadorPrincipal;
	}

	public Participantes getRepresentante() {
		return representante;
	}

	public void setRepresentante(Participantes representante) {
		this.representante = representante;
	}

	public Participantes getExportadorSecundario() {
		return exportadorSecundario;
	}

	public void setExportadorSecundario(Participantes exportadorSecundario) {
		this.exportadorSecundario = exportadorSecundario;
	}

	public InteropHeader getInteropHeader() {
		return interopHeader;
	}

	public void setInteropHeader(InteropHeader interopHeader) {
		this.interopHeader = interopHeader;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Financiero getFinanciero() {
		return financiero;
	}

	public void setFinanciero(Financiero financiero) {
		this.financiero = financiero;
	}

	public DetallesPuerto getDetallesPuerto() {
		return detallesPuerto;
	}

	public void setDetallesPuerto(DetallesPuerto detallesPuerto) {
		this.detallesPuerto = detallesPuerto;
	}

	public Declaracion getDin() {
		return din;
	}

	public void setDin(Declaracion din) {
		this.din = din;
	}

	public Declaracion getDusAt() {
		return dusAt;
	}

	public void setDusAt(Declaracion dusAt) {
		this.dusAt = dusAt;
	}

	public Declaracion getDusLeg() {
		return dusLeg;
	}

	public void setDusLeg(Declaracion dusLeg) {
		this.dusLeg = dusLeg;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
