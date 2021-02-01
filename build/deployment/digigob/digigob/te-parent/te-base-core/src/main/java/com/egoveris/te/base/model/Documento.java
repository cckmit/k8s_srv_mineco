package com.egoveris.te.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Documento que conforma un expediente. El campo data sirve para la trasmición
 * de información al servidor para subir el archivo al Alfresco. El
 * codigoDocumento es el codigo obtenido al caratular. El nombreArchivo es el
 * nombre del archivos que se referncia en Alfresco (RUDO).
 * 
 * @author rgalloci
 *
 *
 */
@Entity
@Table(name = "DOCUMENTO")
public class Documento extends DocumentoSubsanable {
  private static final long serialVersionUID = -1019229952034246571L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "NUMERO_SADE")
  private String numeroSade;

  @Column(name = "NUMERO_ESPECIAL")
  private String numeroEspecial;

  @Column(name = "NOMBRE_USUARIO_GENERADOR")
  private String nombreUsuarioGenerador;

  @Column(name = "MOTIVO")
  private String motivo;

  @Column(name = "NOMBRE_ARCHIVO")
  private String nombreArchivo;

  @Column(name = "NUMERO_FOLIADO")
  private Long numeroFoliado;

  @Column(name = "DEFINITIVO")
  private Boolean definitivo;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "FECHA_ASOCIACION")
  private Date fechaAsociacion;

  @Column(name = "TIPO_DOC_ACRONIMO")
  private String tipoDocAcronimo;

  @Column(name = "USUARIO_ASOCIADOR")
  private String usuarioAsociador;

  @Column(name = "ID_TASK")
  private String idTask;

  @Column(name = "ID_EXP_CABECERA_TC")
  private Long idExpCabeceraTC;

  @Column(name = "ID_TIPO_DOCUMENTO_VINCULADO")
  private Long tipoDocGenerado;

  @Column(name = "SUBSANADO")
  private Long subsanado;

  @Column(name = "SUBSANADO_LIMITADO")
  private Long subsanadoLimitado;

  @Column(name = "USUARIO_SUBSANADOR")
  private String usuarioSubsanador;

  @Column(name = "FECHA_SUBSANACION")
  private Date fechaSubsanacion;

  @Column(name = "ID_TRANSACCION_FC")
  private Long idTransaccionFC;

  // Ojo, este campo era para VUCE
  @Column(name = "entidad")
  private String entidad;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the numeroSade
   */
  public String getNumeroSade() {
    return numeroSade;
  }

  /**
   * @param numeroSade
   *          the numeroSade to set
   */
  public void setNumeroSade(String numeroSade) {
    this.numeroSade = numeroSade;
  }

  /**
   * @return the numeroEspecial
   */
  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  /**
   * @param numeroEspecial
   *          the numeroEspecial to set
   */
  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  /**
   * @return the nombreUsuarioGenerador
   */
  public String getNombreUsuarioGenerador() {
    return nombreUsuarioGenerador;
  }

  /**
   * @param nombreUsuarioGenerador
   *          the nombreUsuarioGenerador to set
   */
  public void setNombreUsuarioGenerador(String nombreUsuarioGenerador) {
    this.nombreUsuarioGenerador = nombreUsuarioGenerador;
  }

  /**
   * @return the motivo
   */
  public String getMotivo() {
    return motivo;
  }

  /**
   * @param motivo
   *          the motivo to set
   */
  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  /**
   * @return the nombreArchivo
   */
  public String getNombreArchivo() {
    return nombreArchivo;
  }

  /**
   * @param nombreArchivo
   *          the nombreArchivo to set
   */
  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  /**
   * @return the numeroFoliado
   */
  public Long getNumeroFoliado() {
    return numeroFoliado;
  }

  /**
   * @param numeroFoliado
   *          the numeroFoliado to set
   */
  public void setNumeroFoliado(Long numeroFoliado) {
    this.numeroFoliado = numeroFoliado;
  }

  /**
   * @return the definitivo
   */
  public Boolean getDefinitivo() {
    return definitivo;
  }

  /**
   * @param definitivo
   *          the definitivo to set
   */
  public void setDefinitivo(Boolean definitivo) {
    this.definitivo = definitivo;
  }

  /**
   * @return the fechaCreacion
   */
  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  /**
   * @param fechaCreacion
   *          the fechaCreacion to set
   */
  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  /**
   * @return the fechaAsociacion
   */
  public Date getFechaAsociacion() {
    return fechaAsociacion;
  }

  /**
   * @param fechaAsociacion
   *          the fechaAsociacion to set
   */
  public void setFechaAsociacion(Date fechaAsociacion) {
    this.fechaAsociacion = fechaAsociacion;
  }

  /**
   * @return the tipoDocAcronimo
   */
  public String getTipoDocAcronimo() {
    return tipoDocAcronimo;
  }

  /**
   * @param tipoDocAcronimo
   *          the tipoDocAcronimo to set
   */
  public void setTipoDocAcronimo(String tipoDocAcronimo) {
    this.tipoDocAcronimo = tipoDocAcronimo;
  }

  /**
   * @return the usuarioAsociador
   */
  public String getUsuarioAsociador() {
    return usuarioAsociador;
  }

  /**
   * @param usuarioAsociador
   *          the usuarioAsociador to set
   */
  public void setUsuarioAsociador(String usuarioAsociador) {
    this.usuarioAsociador = usuarioAsociador;
  }

  /**
   * @return the idTask
   */
  public String getIdTask() {
    return idTask;
  }

  /**
   * @param idTask
   *          the idTask to set
   */
  public void setIdTask(String idTask) {
    this.idTask = idTask;
  }

  /**
   * @return the idExpCabeceraTC
   */
  public Long getIdExpCabeceraTC() {
    return idExpCabeceraTC;
  }

  /**
   * @param idExpCabeceraTC
   *          the idExpCabeceraTC to set
   */
  public void setIdExpCabeceraTC(Long idExpCabeceraTC) {
    this.idExpCabeceraTC = idExpCabeceraTC;
  }

  /**
   * @return the tipoDocGenerado
   */
  public Long getTipoDocGenerado() {
    return tipoDocGenerado;
  }

  /**
   * @param tipoDocGenerado
   *          the tipoDocGenerado to set
   */
  public void setTipoDocGenerado(Long tipoDocGenerado) {
    this.tipoDocGenerado = tipoDocGenerado;
  }

  /**
   * @return the subsanado
   */
  public Long getSubsanado() {
    return subsanado;
  }

  /**
   * @param subsanado
   *          the subsanado to set
   */
  public void setSubsanado(Long subsanado) {
    this.subsanado = subsanado;
  }

  /**
   * @return the subsanadoLimitado
   */
  public Long getSubsanadoLimitado() {
    return subsanadoLimitado;
  }

  /**
   * @param subsanadoLimitado
   *          the subsanadoLimitado to set
   */
  public void setSubsanadoLimitado(Long subsanadoLimitado) {
    this.subsanadoLimitado = subsanadoLimitado;
  }

  /**
   * @return the usuarioSubsanador
   */
  public String getUsuarioSubsanador() {
    return usuarioSubsanador;
  }

  /**
   * @param usuarioSubsanador
   *          the usuarioSubsanador to set
   */
  public void setUsuarioSubsanador(String usuarioSubsanador) {
    this.usuarioSubsanador = usuarioSubsanador;
  }

  /**
   * @return the fechaSubsanacion
   */
  public Date getFechaSubsanacion() {
    return fechaSubsanacion;
  }

  /**
   * @param fechaSubsanacion
   *          the fechaSubsanacion to set
   */
  public void setFechaSubsanacion(Date fechaSubsanacion) {
    this.fechaSubsanacion = fechaSubsanacion;
  }

  /**
   * @return the idTransaccionFC
   */
  public Long getIdTransaccionFC() {
    return idTransaccionFC;
  }

  /**
   * @param idTransaccionFC
   *          the idTransaccionFC to set
   */
  public void setIdTransaccionFC(Long idTransaccionFC) {
    this.idTransaccionFC = idTransaccionFC;
  }

  public String getEntidad() {
    return entidad;
  }

  public void setEntidad(String entidad) {
    this.entidad = entidad;
  }

}