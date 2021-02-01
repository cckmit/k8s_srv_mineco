package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_SUSCRIPCION")
public class Suscripcion {

  @EmbeddedId
  private SuscripcionPK suscripcionPK;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "REINTENTO")
  private Integer reintento;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "USUARIO_ALTA")
  private String usuarioAlta;

  public SuscripcionPK getSuscripcionPK() {
    return suscripcionPK;
  }

  public void setSuscripcionPK(SuscripcionPK suscripcionPK) {
    this.suscripcionPK = suscripcionPK;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Integer getReintento() {
    return reintento;
  }

  public void setReintento(Integer reintento) {
    this.reintento = reintento;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioAlta() {
    return usuarioAlta;
  }

  public void setUsuarioAlta(String usuarioAlta) {
    this.usuarioAlta = usuarioAlta;
  }

//  @Override
//  public String toString() {
//    StringBuilder buffer = new StringBuilder();
//    if(suscripcionPK != null){
//      buffer.append("workflow id = ");
//      buffer.append(suscripcionPK.getWorkflowId());
//      buffer.append(" sistema origen = ");
//      buffer.append(suscripcionPK.getSistemaOrigen());
//    }
//    
//    buffer.append(" estado = ");
//    buffer.append(estado);
//    buffer.append(" reintento = ");
//    buffer.append(reintento);
//    buffer.append(" fecha creacion = ");
//    buffer.append(fechaCreacion);
//    buffer.append(" usuario alta = ");
//    buffer.append(usuarioAlta);
//    return buffer.toString();
//  }
}
