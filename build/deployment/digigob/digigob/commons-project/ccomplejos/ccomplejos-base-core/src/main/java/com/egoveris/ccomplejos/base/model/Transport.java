package com.egoveris.ccomplejos.base.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_TRANSPORT")
public class Transport extends AbstractCComplejoJPA {

	@Column(name = "ID_DECLARACION")
	protected Long idDeclaracion;
	@Column(name = "ID_TRANSPORTE")
	protected Long idTransporte;
	@Column(name = "MEDIO_DE_TRANSPORTE")
	protected String medioDeTransporte;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_MERCANCIA")
	protected Mercancia mercancia;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EMISOR_DOCUMENTO_TRANS_ID")
	protected Participantes emisorDocumentoTransporte;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TRANSPORTISTA_ID")
	protected Participantes transportista;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AGENCIA_TRANSPORTISTA_ID")
	protected Participantes agenciaTransportista;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_RESERVA")
	protected Reserva reserva;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DOC_TRANSPORTE")
	protected DocumentoTransporte documento;
	
	public Long getIdDeclaracion() {
		return idDeclaracion;
	}
	public void setIdDeclaracion(Long idDeclaracion) {
		this.idDeclaracion = idDeclaracion;
	}
	public Long getIdTransporte() {
		return idTransporte;
	}
	public void setIdTransporte(Long idTransporte) {
		this.idTransporte = idTransporte;
	}
	public String getMedioDeTransporte() {
		return medioDeTransporte;
	}
	public void setMedioDeTransporte(String medioDeTransporte) {
		this.medioDeTransporte = medioDeTransporte;
	}
	public Mercancia getMercancia() {
		return mercancia;
	}
	public void setMercancia(Mercancia mercancia) {
		this.mercancia = mercancia;
	}
	public Participantes getEmisorDocumentoTransporte() {
		return emisorDocumentoTransporte;
	}
	public void setEmisorDocumentoTransporte(Participantes emisorDocumentoTransporte) {
		this.emisorDocumentoTransporte = emisorDocumentoTransporte;
	}
	public Participantes getTransportista() {
		return transportista;
	}
	public void setTransportista(Participantes transportista) {
		this.transportista = transportista;
	}
	public Participantes getAgenciaTransportista() {
		return agenciaTransportista;
	}
	public void setAgenciaTransportista(Participantes agenciaTransportista) {
		this.agenciaTransportista = agenciaTransportista;
	}
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	public DocumentoTransporte getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoTransporte documento) {
		this.documento = documento;
	}
	
	
}
