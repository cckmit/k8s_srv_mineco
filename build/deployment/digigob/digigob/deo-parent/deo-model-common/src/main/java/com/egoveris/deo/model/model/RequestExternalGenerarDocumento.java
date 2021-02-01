package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Establece los parámetros de entrada requeridos para la generación automática
 * de documentos electrónicos en GEDO.
 * 
 * - acronimoTipoDocumento: Debe ser automático y cumplir con las siguientes
 * características para cada caso. Obligatorio. - data: Arreglo de bytes que
 * corresponde al contenido del documento, acorde a la siguiente configuración:
 * - metaDatos: Map con los datos propios y su valor correspondiente. Opcional
 * si el tipo de documento no tiene datos propios obligatorios. - sistemaOrigen:
 * Identificador del sistema solicitante. Obligatorio. - usuario: Usuario
 * generador del documento. Obligatorio. - referencia: Referencia del documento.
 * Obligatoria. - tipoArchivo: Especificar el tipo de contenido del documento
 * que se va a crear, referenciando la extensión del archivo correspondiente,
 * con el siguiente formato ".pdf". Opcional.
 */

public class RequestExternalGenerarDocumento implements Serializable {

  private static final long serialVersionUID = 8058182609128984731L;
  private String usuario;
  private String acronimoTipoDocumento;
  private String referencia;
  private byte[] data;
  private String sistemaOrigen;
  private Map<String, String> metaDatos;
  private Integer idTransaccion = null;
  private String tipoArchivo;
  private List<ArchivoEmbebidoDTO> listaArchivosEmbebidos;
  private List<String> listaUsuariosDestinatarios;
  private List<String> listaUsuariosDestinatariosCopia;
  private List<String> listaUsuariosDestinatariosCopiaOculta;
  private Map<String, String> listaUsuariosDestinatariosExternos;
  private String mensajeDestinatario;
  private Map<String, String> camposTemplate;

  public void setCamposTemplate(Map<String, String> camposTemplate) {
    this.camposTemplate = camposTemplate;
  }

  public Map<String, String> getCamposTemplate() {
    return camposTemplate;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getAcronimoTipoDocumento() {
    return acronimoTipoDocumento;
  }

  public void setAcronimoTipoDocumento(String acronimoTipoDocumento) {
    this.acronimoTipoDocumento = acronimoTipoDocumento;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getSistemaOrigen() {
    return sistemaOrigen;
  }

  public void setSistemaOrigen(String sistemaOrigen) {
    this.sistemaOrigen = sistemaOrigen;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public void setTipoArchivo(String tipoArchivo) {
    this.tipoArchivo = tipoArchivo;
  }

  public String getTipoArchivo() {
    return tipoArchivo;
  }

  public Map<String, String> getMetaDatos() {
    return metaDatos;
  }

  public Map<String, String> getListaUsuariosDestinatariosExternos() {
    return listaUsuariosDestinatariosExternos;
  }

  public void setListaUsuariosDestinatariosExternos(
      Map<String, String> listaUsuariosDestinatariosExternos) {
    this.listaUsuariosDestinatariosExternos = listaUsuariosDestinatariosExternos;
  }

  public void setMetaDatos(Map<String, String> metaDatos) {
    this.metaDatos = metaDatos;
  }

  public Integer getIdTransaccion() {
    return idTransaccion;
  }

  public void setIdTransaccion(Integer idTransaccion) {
    this.idTransaccion = idTransaccion;
  }

  public List<ArchivoEmbebidoDTO> getListaArchivosEmbebidos() {
    return listaArchivosEmbebidos;
  }

  public void setListaArchivosEmbebidos(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos) {
    this.listaArchivosEmbebidos = listaArchivosEmbebidos;
  }

  public List<String> getListaUsuariosDestinatarios() {
    return listaUsuariosDestinatarios;
  }

  public void setListaUsuariosDestinatarios(List<String> listaUsuariosDestinatarios) {
    this.listaUsuariosDestinatarios = listaUsuariosDestinatarios;
  }

  public List<String> getListaUsuariosDestinatariosCopia() {
    return listaUsuariosDestinatariosCopia;
  }

  public void setListaUsuariosDestinatariosCopia(List<String> listaUsuariosDestinatariosCopia) {
    this.listaUsuariosDestinatariosCopia = listaUsuariosDestinatariosCopia;
  }

  public List<String> getListaUsuariosDestinatariosCopiaOculta() {
    return listaUsuariosDestinatariosCopiaOculta;
  }

  public void setListaUsuariosDestinatariosCopiaOculta(
      List<String> listaUsuariosDestinatariosCopiaOculta) {
    this.listaUsuariosDestinatariosCopiaOculta = listaUsuariosDestinatariosCopiaOculta;
  }

  public String getMensajeDestinatario() {
    return mensajeDestinatario;
  }

  public void setMensajeDestinatario(String mensajeDestinatario) {
    this.mensajeDestinatario = mensajeDestinatario;
  }  

}
