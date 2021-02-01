package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OperationDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String codigoOficinaAduana;
	protected String codOperacion;
	protected String nombreAgenciaAduana;
	protected String processingStatus;
	protected String codigoDestinacion;
	protected String codigoAgenciaAduanas;
	protected String codigoTransbordo;
	protected String actualizadoPor;
	protected String creadoPor;

	protected List<ProductOperationDTO> listProductOperacion;
	protected List<ItemDTO> listaItem;
	protected List<BultoDTO> listaBulto;
	protected List<AutorizacionDTO> listAutorizacion;

	protected TransportDTO transport;
	protected ParticipantesDTO contacto;
	protected ParticipantesDTO importador;
	protected ParticipantesDTO consignatario;
	protected ParticipantesDTO intermediario;
	protected ParticipantesDTO exportadorPrincipal;
	protected ParticipantesDTO representante;
	protected ParticipantesDTO exportadorSecundario;
	protected InteropHeaderDTO interopHeader;
	protected HeaderDTO header;
	protected FinancieroDTO financiero;
	protected DetallesPuertoDTO detallesPuerto;
	protected DeclaracionDTO din;
	protected DeclaracionDTO dusAt;
	protected DeclaracionDTO dusLeg;

	protected Date fechaCreacion;
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


	public List<ProductOperationDTO> getListProductOperacionDTOs() {
		return listProductOperacion;
	}

	public void setListProductOperacionDTOs(List<ProductOperationDTO> listProductOperacionDTOs) {
		this.listProductOperacion = listProductOperacionDTOs;
	}

	public List<ItemDTO> getListaItem() {
		return listaItem;
	}

	public void setListaItem(List<ItemDTO> listaItem) {
		this.listaItem = listaItem;
	}

	public List<BultoDTO> getListBultoDTOs() {
		return listaBulto;
	}

	public void setListBultoDTOs(List<BultoDTO> listBultoDTOs) {
		this.listaBulto = listBultoDTOs;
	}

	public List<AutorizacionDTO> getListAutorizacionDTOs() {
		return listAutorizacion;
	}

	public void setListAutorizacionDTOs(List<AutorizacionDTO> listAutorizacionDTOs) {
		this.listAutorizacion = listAutorizacionDTOs;
	}

	public TransportDTO getTransportDTO() {
		return transport;
	}

	public void setTransportDTO(TransportDTO transportDTO) {
		this.transport = transportDTO;
	}

	public ParticipantesDTO getContacto() {
		return contacto;
	}

	public void setContacto(ParticipantesDTO contacto) {
		this.contacto = contacto;
	}

	public ParticipantesDTO getImportador() {
		return importador;
	}

	public void setImportador(ParticipantesDTO importador) {
		this.importador = importador;
	}

	public ParticipantesDTO getConsignatario() {
		return consignatario;
	}

	public void setConsignatario(ParticipantesDTO consignatario) {
		this.consignatario = consignatario;
	}

	public ParticipantesDTO getIntermediario() {
		return intermediario;
	}

	public void setIntermediario(ParticipantesDTO intermediario) {
		this.intermediario = intermediario;
	}

	public ParticipantesDTO getExportadorPrincipal() {
		return exportadorPrincipal;
	}

	public void setExportadorPrincipal(ParticipantesDTO exportadorPrincipal) {
		this.exportadorPrincipal = exportadorPrincipal;
	}

	public ParticipantesDTO getRepresentante() {
		return representante;
	}

	public void setRepresentante(ParticipantesDTO representante) {
		this.representante = representante;
	}

	public ParticipantesDTO getExportadorSecundario() {
		return exportadorSecundario;
	}

	public void setExportadorSecundario(ParticipantesDTO exportadorSecundario) {
		this.exportadorSecundario = exportadorSecundario;
	}

	public InteropHeaderDTO getInteropHeaderDTO() {
		return interopHeader;
	}

	public void setInteropHeaderDTO(InteropHeaderDTO interopHeaderDTO) {
		this.interopHeader = interopHeaderDTO;
	}

	public HeaderDTO getHeaderDTO() {
		return header;
	}

	public void setHeaderDTO(HeaderDTO headerDTO) {
		this.header = headerDTO;
	}

	public FinancieroDTO getFinancieroDTO() {
		return financiero;
	}

	public void setFinancieroDTO(FinancieroDTO financieroDTO) {
		this.financiero = financieroDTO;
	}

	public DetallesPuertoDTO getDetallesPuertoDTO() {
		return detallesPuerto;
	}

	public void setDetallesPuertoDTO(DetallesPuertoDTO detallesPuertoDTO) {
		this.detallesPuerto = detallesPuertoDTO;
	}

	public DeclaracionDTO getDin() {
		return din;
	}

	public void setDin(DeclaracionDTO din) {
		this.din = din;
	}

	public DeclaracionDTO getDusAt() {
		return dusAt;
	}

	public void setDusAt(DeclaracionDTO dusAt) {
		this.dusAt = dusAt;
	}

	public DeclaracionDTO getDusLeg() {
		return dusLeg;
	}

	public void setDusLeg(DeclaracionDTO dusLeg) {
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
