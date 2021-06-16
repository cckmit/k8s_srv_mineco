package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class TransportDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long idDeclaracion;
	protected Long idTransporte;
	protected MercanciaDTO mercancia;
	protected ParticipantesDTO emisordocumentotransporte;
	protected ParticipantesDTO transportista;
	protected ParticipantesDTO agenciaTransportista;
	protected ReservaDTO reserva;
	protected String medioDeTransporte;
	protected DocumentoTransporteDTO documento;

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

	public MercanciaDTO getMercancia() {
		return mercancia;
	}

	public void setMercancia(MercanciaDTO mercancia) {
		this.mercancia = mercancia;
	}

	public ParticipantesDTO getEmisordocumentotransporte() {
		return emisordocumentotransporte;
	}

	public void setEmisordocumentotransporte(ParticipantesDTO emisordocumentotransporte) {
		this.emisordocumentotransporte = emisordocumentotransporte;
	}

	public ParticipantesDTO getTransportista() {
		return transportista;
	}

	public void setTransportista(ParticipantesDTO transportista) {
		this.transportista = transportista;
	}

	public ParticipantesDTO getAgenciaTransportista() {
		return agenciaTransportista;
	}

	public void setAgenciaTransportista(ParticipantesDTO agenciaTransportista) {
		this.agenciaTransportista = agenciaTransportista;
	}

	public ReservaDTO getReserva() {
		return reserva;
	}

	public void setReserva(ReservaDTO reserva) {
		this.reserva = reserva;
	}

	public String getMedioDeTransporte() {
		return medioDeTransporte;
	}

	public void setMedioDeTransporte(String medioDeTransporte) {
		this.medioDeTransporte = medioDeTransporte;
	}

	public DocumentoTransporteDTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoTransporteDTO documento) {
		this.documento = documento;
	}

}
