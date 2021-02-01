package com.egoveris.te.base.model;

import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;

import java.io.Serializable;
import java.util.List;

public class SolicitudSubs implements Serializable {

  private static final long serialVersionUID = 8093434101206104460L;

  private String workFlowId;
  private String nroExpediente;
  private String tipo;
  private String motivo;
  private String usuarioAlta;
  private boolean formulario;
  private boolean subsDoc;
  private boolean pedidoDoc;
  private List<String> listaSubsDocs;
  private List<String> listaPedidoDocs;
  private List<ExternalDocumentoVucDTO> listaSubsDocConNombres;
  private List<ExternalDocumentoVucDTO> listaPedidoDocConNombres;
  private String destino;
  private String codigoTrata;

  public String getWorkFlowId() {
    return workFlowId;
  }

  public void setWorkFlowId(String workFlowId) {
    this.workFlowId = workFlowId;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public boolean isFormulario() {
    return formulario;
  }

  public void setFormulario(boolean formulario) {
    this.formulario = formulario;
  }

  public boolean isSubsDoc() {
    return subsDoc;
  }

  public void setSubsDoc(boolean subsDoc) {
    this.subsDoc = subsDoc;
  }

  public boolean isPedidoDoc() {
    return pedidoDoc;
  }

  public void setPedidoDoc(boolean pedidoDoc) {
    this.pedidoDoc = pedidoDoc;
  }

  public List<String> getListaSubsDocs() {
    return listaSubsDocs;
  }

  public void setListaSubsDocs(List<String> listaSubsDocs) {
    this.listaSubsDocs = listaSubsDocs;
  }

  public List<String> getListaPedidoDocs() {
    return listaPedidoDocs;
  }

  public void setListaPedidoDocs(List<String> listaPedidoDocs) {
    this.listaPedidoDocs = listaPedidoDocs;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getUsuarioAlta() {
    return usuarioAlta;
  }

  public void setUsuarioAlta(String usuarioAlta) {
    this.usuarioAlta = usuarioAlta;
  }

  public String getNroExpediente() {
    return nroExpediente;
  }

  public void setNroExpediente(String nroExpediente) {
    this.nroExpediente = nroExpediente;
  }

  public String getDestino() {
    return destino;
  }

  public void setDestino(String destino) {
    this.destino = destino;
  }

  public String getCodigoTrata() {
    return codigoTrata;
  }

  public void setCodigoTrata(String codigoTrata) {
    this.codigoTrata = codigoTrata;
  }

  public List<ExternalDocumentoVucDTO> getListaSubsDocConNombres() {
    return listaSubsDocConNombres;
  }

  public void setListaSubsDocConNombres(List<ExternalDocumentoVucDTO> listaSubsDocConNombres) {
    this.listaSubsDocConNombres = listaSubsDocConNombres;
  }

  public List<ExternalDocumentoVucDTO> getListaPedidoDocConNombres() {
    return listaPedidoDocConNombres;
  }

  public void setListaPedidoDocConNombres(List<ExternalDocumentoVucDTO> listaPedidoDocConNombres) {
    this.listaPedidoDocConNombres = listaPedidoDocConNombres;
  }

}
