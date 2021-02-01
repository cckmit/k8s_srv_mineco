package com.egoveris.vucfront.ws.model;

public class NotificacionRequestDTO {

	private String codSadeExpediente;
	
	private DocumentoDTO documento;
	
	private String motivo;

	public String getCodSadeExpediente() {
		return codSadeExpediente;
	}

	public void setCodSadeExpediente(String codSadeExpediente) {
		this.codSadeExpediente = codSadeExpediente;
	}

	public DocumentoDTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoDTO documento) {
		this.documento = documento;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
}
