package com.egoveris.vucfront.ws.model;

import com.egoveris.shared.date.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class ExternalDocumentoVucDTO implements Serializable {

  private static final long serialVersionUID = -6095847293360156065L;

  private String nombreOriginal;
  private Date fechaCreacion;
  private ExternalTipoDocumentoVucDTO tipoDocumento;
  private boolean seleccionado;
  private String numeroSade;
  private String motivo;

  public ExternalDocumentoVucDTO() {
    this.tipoDocumento = new ExternalTipoDocumentoVucDTO();
  }

  public String getFormattedDate() {
    if (fechaCreacion != null) {
      return DateUtil.getFormattedDate(fechaCreacion);
    }
    return "";
  }

  public String getNombreOriginal() {
    return nombreOriginal;
  }

  public void setNombreOriginal(String nombreOriginal) {
    this.nombreOriginal = nombreOriginal;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public ExternalTipoDocumentoVucDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(ExternalTipoDocumentoVucDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public boolean isSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(boolean seleccionado) {
    this.seleccionado = seleccionado;
  }

public String getNumeroSade() {
	return numeroSade;
}

public void setNumeroSade(String numeroSade) {
	this.numeroSade = numeroSade;
}

public String getMotivo() {
	return motivo;
}

public void setMotivo(String motivo) {
	this.motivo = motivo;
}

}