package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Establece los parámetros de entrada requeridos para la generación de una
 * tarea específica en GEDO.
 *
 * - usuarioEmisor: Usuario emisor de la tarea. Obligatorio. - usuarioReceptor:
 * Usuario receptore de la tarea. Obligatorio, excepto en la tarea "Firmar
 * Documento". Obligatorio, excepto en la tarea "Firmar Documento". -
 * usuarioFirmante: Usuario o Usuarios firmantes, de tratarse de Firma Conjunta,
 * deberan ser mas de dos firmantes. Obligatorios en el caso de la tarea "Firmar
 * Documento". - tarea: Tarea en la cual se empezara el proceso, Ej:
 * "Confeccionar Documento", "Revisar Documento", "Firmar Documento" .
 * Obligatorio. - acronimoTipoDocumento: Debe cumplir con las siguientes
 * características para cada caso. Obligatorio. - metaDatos: Map con los datos
 * propios y su valor correspondiente. Opcional si el tipo de documento no tiene
 * datos propios obligatorios. - camposTemplate: Map con los nombres de campos
 * de un tipo de documento Template y sus correspondientes valores. -
 * referencia: Referencia del documento. Obligatorio. - data: Arreglo de bytes
 * que corresponde al contenido del documento, Obligatorio, excepto en la tarea
 * "Confeccionar Documento". - tipoArchivo: Especificar el tipo de contenido del
 * documento que se va a crear, referenciando la extensión del archivo
 * correspondiente, con el siguiente formato ".pdf". Obligatorio, excepto en la
 * tarea "Confeccionar Documento". - mensaje: Mensaje que se le desea enviar al
 * receptor de la tarea, en el caso de la tarea "Firmar Documento" no se pueden
 * enviar mensajes. Opcional. - sistemaIniciador: Sistema que la da inicio a la
 * tarea correspondiente en GEDO. Obligatorio. - enviarCorreoReceptor: Opcion
 * para enviarle o no un correo electronico al receptor de la tarea. Opcional. -
 * recibirAvisoFirma: Opcion para que el usuario emisor reciba un aviso en la
 * aplicacion GEDO, una vez que se firma el documento. Opcional -
 * suscribirseAlDocumento: Opcion para que el usuario suscriba a su sistema de
 * origen a los suscriptores del workflowiId.
 */
public class RequestExternalGenerarTarea implements Serializable {

  private static final long serialVersionUID = 73208091824364688L;
  private String usuarioEmisor;
  private String usuarioReceptor;
  private String usuarioRevisor;
  private SortedMap<Integer, String> usuarioFirmante;
  private String tarea;
  private String acronimoTipoDocumento;
  private Map<String, String> metaDatos;
  private Integer idTransaccion;
  private String referencia;
  private byte[] data;
  private String tipoArchivo;
  private String mensaje;
  private String sistemaIniciador;
  private boolean enviarCorreoReceptor;
  private boolean recibirAvisoFirma;
  private Boolean suscribirseAlDocumento;
  private List<ArchivoEmbebidoDTO> listaArchivosEmbebidos;
  private List<String> listaUsuariosDestinatarios;
  private List<String> listaUsuariosDestinatariosCopia;
  private List<String> listaUsuariosDestinatariosCopiaOculta;
  private Map<String, String> listaUsuariosDestinatariosExternos;
  private String mensajeDestinatario;
  private Map<String, String> campoTemplate;

  public String getUsuarioEmisor() {
    return usuarioEmisor;
  }

  public Map<String, String> getCampoTemplate() {
    return campoTemplate;
  }

  public void setCampoTemplate(Map<String, String> campoTemplate) {
    this.campoTemplate = campoTemplate;
  }

  public void setUsuarioEmisor(String usuarioEmisor) {
    this.usuarioEmisor = usuarioEmisor;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public String getAcronimoTipoDocumento() {
    return acronimoTipoDocumento;
  }

  public void setAcronimoTipoDocumento(String acronimoTipoDocumento) {
    this.acronimoTipoDocumento = acronimoTipoDocumento;
  }

  public Map<String, String> getMetaDatos() {
    return metaDatos;
  }

  public void setMetaDatos(Map<String, String> metaDatos) {
    this.metaDatos = metaDatos;
  }

  public String getUsuarioReceptor() {
    return usuarioReceptor;
  }

  public void setUsuarioReceptor(String usuarioReceptor) {
    this.usuarioReceptor = usuarioReceptor;
  }

  public String getTipoArchivo() {
    return tipoArchivo;
  }

  public void setTipoArchivo(String tipoArchivo) {
    this.tipoArchivo = tipoArchivo;
  }

  public String getTarea() {
    return tarea;
  }

  public void setTarea(String tarea) {
    this.tarea = tarea;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public boolean isEnviarCorreoReceptor() {
    return enviarCorreoReceptor;
  }

  public void setEnviarCorreoReceptor(boolean enviarCorreoReceptor) {
    this.enviarCorreoReceptor = enviarCorreoReceptor;
  }

  public boolean isRecibirAvisoFirma() {
    return recibirAvisoFirma;
  }

  public void setRecibirAvisoFirma(boolean recibirAvisoFirma) {
    this.recibirAvisoFirma = recibirAvisoFirma;
  }

  public String getSistemaIniciador() {
    return sistemaIniciador;
  }

  public void setSistemaIniciador(String sistemaIniciador) {
    this.sistemaIniciador = sistemaIniciador;
  }

  public SortedMap<Integer, String> getUsuarioFirmante() {
    return usuarioFirmante;
  }

  public void setUsuarioFirmante(SortedMap<Integer, String> usuarioFirmante) {
    this.usuarioFirmante = usuarioFirmante;
  }

  public Boolean getSuscribirseAlDocumento() {
    return suscribirseAlDocumento;
  }

  public void setSuscribirseAlDocumento(Boolean suscribirseAlDocumento) {
    this.suscribirseAlDocumento = suscribirseAlDocumento;
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

  public Map<String, String> getListaUsuariosDestinatariosExternos() {
    return listaUsuariosDestinatariosExternos;
  }

  public void setListaUsuariosDestinatariosExternos(
      Map<String, String> listaUsuariosDestinatariosExternos) {
    this.listaUsuariosDestinatariosExternos = listaUsuariosDestinatariosExternos;
  }

  public String getMensajeDestinatario() {
    return mensajeDestinatario;
  }

  public void setMensajeDestinatario(String mensajeDestinatario) {
    this.mensajeDestinatario = mensajeDestinatario;
  }

  public String getUsuarioRevisor() {
    return usuarioRevisor;
  }

  public void setUsuarioRevisor(String usuarioRevisor) {
    this.usuarioRevisor = usuarioRevisor;
  }

}
