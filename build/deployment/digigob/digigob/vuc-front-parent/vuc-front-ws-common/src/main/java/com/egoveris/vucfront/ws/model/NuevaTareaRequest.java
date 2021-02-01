package com.egoveris.vucfront.ws.model;

import java.io.Serializable;
import java.util.List;

public class NuevaTareaRequest implements Serializable {

  private static final long serialVersionUID = 5013386316204577445L;

  private String codigoExpediente;
  private String motivo;
  private String tipoTarea;
  private boolean esSubsanarFormulario;
  private boolean esSubsanarDocumentacion;
  private boolean esAgregarDocumentacion;
  private boolean esSubsanacionInterviniente;
  private List<String> acronimosTadDocumentosASubir;
  private String reparticionSectorGenerador;
  private Long idExpedienteBase;
  private Long idExpedienteElectronico;
  private Long idFormulario;

  public String getCodigoExpediente() {
    return codigoExpediente;
  }

  public void setCodigoExpediente(String codigoExpediente) {
    this.codigoExpediente = codigoExpediente;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getTipoTarea() {
    return tipoTarea;
  }

  public void setTipoTarea(String tipoTarea) {
    this.tipoTarea = tipoTarea;
  }

  public boolean esSubsanarFormulario() {
    return esSubsanarFormulario;
  }

  public void setEsSubsanarFormulario(boolean esSubsanarFormulario) {
    this.esSubsanarFormulario = esSubsanarFormulario;
  }

  public boolean esSubsanarDocumentacion() {
    return esSubsanarDocumentacion;
  }

  public void setEsSubsanarDocumentacion(boolean esSubsanarDocumentacion) {
    this.esSubsanarDocumentacion = esSubsanarDocumentacion;
  }

  public boolean esAgregarDocumentacion() {
    return esAgregarDocumentacion;
  }

  public void setEsAgregarDocumentacion(boolean esAgregarDocumentacion) {
    this.esAgregarDocumentacion = esAgregarDocumentacion;
  }

  public List<String> getAcronimosTadDocumentosASubir() {
    return acronimosTadDocumentosASubir;
  }

  public void setAcronimosTadDocumentosASubir(List<String> acronimosTadDocumentosASubir) {
    this.acronimosTadDocumentosASubir = acronimosTadDocumentosASubir;
  }

  public String getReparticionSectorGenerador() {
    return reparticionSectorGenerador;
  }

  public void setReparticionSectorGenerador(String reparticionSectorGenerador) {
    this.reparticionSectorGenerador = reparticionSectorGenerador;
  }

  public boolean esSubsanacionInterviniente() {
    return esSubsanacionInterviniente;
  }

  public void setEsSubsanacionInterviniente(boolean esSubsanacionInterviniente) {
    this.esSubsanacionInterviniente = esSubsanacionInterviniente;
  }

  public Long getIdExpedienteBase() {
    return idExpedienteBase;
  }

  public void setIdExpedienteBase(Long idExpedienteBase) {
    this.idExpedienteBase = idExpedienteBase;
  }

  public Long getIdExpedienteElectronico() {
    return idExpedienteElectronico;
  }

  public void setIdExpedienteElectronico(Long idExpedienteElectronico) {
    this.idExpedienteElectronico = idExpedienteElectronico;
  }

  public Long getIdFormulario() {
    return idFormulario;
  }

  public void setIdFormulario(Long idFormulario) {
    this.idFormulario = idFormulario;
  }

}
