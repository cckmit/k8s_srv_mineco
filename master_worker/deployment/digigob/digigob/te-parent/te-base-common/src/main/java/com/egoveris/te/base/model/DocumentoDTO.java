package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

public class DocumentoDTO extends DocumentoSubsanableDTO implements Serializable {
  private static final long serialVersionUID = -1019229952034246571L;

  private Long id;
  private String numeroSade;
  private String numeroEspecial;
  private transient TipoDocumentoDTO tipoDocumento;
  private String tipoDocAcronimo;
  private String nombreUsuarioGenerador;
  private String motivo;
  private transient byte[] data;
  private String nombreArchivo;
  private Integer numeroFoliado;
  private Boolean definitivo = false;
  private Date fechaCreacion;
  private Date fechaAsociacion;
  private String usuarioAsociador;
  private Integer posicion;
  private String idTask;
  private Long tipoDocGedo;
  private Long idExpCabeceraTC;
  private Long tipoDocGenerado;
  private List<DocumentoArchivoDeTrabajoDTO> archivosDeTrabajo;
  private String aclaracion;
  private Long idTransaccionFC;
  private String motivoDepuracion;
  private String entidad;

  public List<DocumentoArchivoDeTrabajoDTO> getArchivosDeTrabajo() {
    return archivosDeTrabajo;
  }

  public void setArchivosDeTrabajo(List<DocumentoArchivoDeTrabajoDTO> archivosDeTrabajo) {
    this.archivosDeTrabajo = archivosDeTrabajo;
  }

  public String getNombreUsuarioGenerador() {
    return nombreUsuarioGenerador;
  }

  public void setNombreUsuarioGenerador(String nombreUsuarioGenerador) {
    this.nombreUsuarioGenerador = nombreUsuarioGenerador;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getNumeroSade() {
    return numeroSade;
  }

  public void setNumeroSade(String numeroSade) {
    this.numeroSade = numeroSade;
  }

  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getNumeroFoliado() {
    return numeroFoliado;
  }

  public void setNumeroFoliado(Integer numeroFoliado) {
    this.numeroFoliado = numeroFoliado;
  }

  public Boolean getDefinitivo() {
    return definitivo;
  }

  public void setDefinitivo(Boolean definitivo) {
    this.definitivo = definitivo;
  }

  public Date getFechaAsociacion() {
    return fechaAsociacion;
  }

  public void setFechaAsociacion(Date fechaAsociacion) {
    this.fechaAsociacion = fechaAsociacion;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nombreArchivo == null) ? 0 : nombreArchivo.hashCode());
    result = prime * result + ((numeroSade == null) ? 0 : numeroSade.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DocumentoDTO other = (DocumentoDTO) obj;
    // TODO: porque nombreArchivo?!?
    // los no definitivos tienen nombreArchivo==null
    if (nombreArchivo == null) {
      if (other.nombreArchivo != null)
        return false;
    } else if (!nombreArchivo.equals(other.nombreArchivo))
      return false;
    if (numeroSade == null) {
      if (other.numeroSade != null)
        return false;
    } else if (!numeroSade.equals(other.numeroSade))
      return false;
    return true;
  }

  public Integer getPosicion() {
    return posicion;
  }

  public void setPosicion(Integer posicion) {
    this.posicion = posicion;
  }

  public String getUsuarioAsociador() {
    return usuarioAsociador;
  }

  public void setUsuarioAsociador(String usuarioAsociador) {
    this.usuarioAsociador = usuarioAsociador;
  }

  public String getIdTask() {
    return idTask;
  }

  public void setIdTask(String idTask) {
    this.idTask = idTask;
  }

  public Long getTipoDocGedo() {
    return tipoDocGedo;
  }

  public void setTipoDocGedo(Long tipoDocGedo) {
    this.tipoDocGedo = tipoDocGedo;
  }

  public Long getIdExpCabeceraTC() {
    return idExpCabeceraTC;
  }

  public void setIdExpCabeceraTC(Long idExpCabeceraTC) {
    this.idExpCabeceraTC = idExpCabeceraTC;
  }

  public Long getTipoDocGenerado() {
    return tipoDocGenerado;
  }

  public void setTipoDocGenerado(Long tipoDocGenerado) {
    this.tipoDocGenerado = tipoDocGenerado;
  }

  public String getTipoDocAcronimo() {
    return tipoDocAcronimo;
  }

  public void setTipoDocAcronimo(String tipoDocAcronimo) {
    this.tipoDocAcronimo = tipoDocAcronimo;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("");
    sb.append("Documento [").append((id != null ? "id=" + id + ", " : ""))
        .append((numeroSade != null ? "numeroSade=" + numeroSade + ", " : ""))
        .append((numeroEspecial != null ? "numeroEspecial=" + numeroEspecial + ", " : ""))
        .append((tipoDocumento != null ? "tipoDocumento=" + tipoDocumento + ", " : ""))
        .append((tipoDocAcronimo != null ? "tipoDocAcronimo=" + tipoDocAcronimo + ", " : ""))
        .append((nombreUsuarioGenerador != null
            ? "nombreUsuarioGenerador=" + nombreUsuarioGenerador + ", " : ""))
        .append((motivo != null ? "motivo=" + motivo + ", " : ""))
        .append((nombreArchivo != null ? "nombreArchivo=" + nombreArchivo + ", " : ""))
        .append((numeroFoliado != null ? "numeroFoliado=" + numeroFoliado + ", " : ""))
        .append((definitivo != null ? "definitivo=" + definitivo + ", " : ""))
        .append((fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : ""))
        .append((fechaAsociacion != null ? "fechaAsociacion=" + fechaAsociacion + ", " : ""))
        .append((usuarioAsociador != null ? "usuarioAsociador=" + usuarioAsociador + ", " : ""))
        .append((posicion != null ? "posicion=" + posicion + ", " : ""))
        .append((idTask != null ? "idTask=" + idTask + ", " : ""))
        .append((tipoDocGedo != null ? "tipoDocGedo=" + tipoDocGedo + ", " : ""))
        .append((idExpCabeceraTC != null ? "idExpCabeceraTC=" + idExpCabeceraTC + ", " : ""))
        .append((tipoDocGenerado != null ? "tipoDocGenerado=" + tipoDocGenerado + ", " : ""))
        .append((archivosDeTrabajo != null ? "archivosDeTrabajo=" + archivosDeTrabajo : ""))
        .append((motivoDepuracion != null ? "motivoDepuracion=" + motivoDepuracion : ""))
        .append("]");

    return sb.toString();
  }

  public void setIdTransaccionFC(Long idTransaccionFC) {
    this.idTransaccionFC = idTransaccionFC;
  }

  public Long getIdTransaccionFC() {
    return idTransaccionFC;
  }

  public void setAclaracion(String aclaracion) {
    this.aclaracion = aclaracion;
  }

  public String getAclaracion() {
    return aclaracion;
  }

  public String getMotivoDepuracion() {
    return motivoDepuracion;
  }

  public void setMotivoDepuracion(String motivoDepuracion) {
    this.motivoDepuracion = motivoDepuracion;
  }

  public String getEntidad() {
    return entidad;
  }

  public void setEntidad(String entidad) {
    this.entidad = entidad;
  }

}