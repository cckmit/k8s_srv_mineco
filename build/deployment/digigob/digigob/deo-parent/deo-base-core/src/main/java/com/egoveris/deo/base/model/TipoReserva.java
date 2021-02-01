package com.egoveris.deo.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPO_RESERVA")
public class TipoReserva {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "RESERVA")
  private String reserva;

  @OneToMany(mappedBy = "tipoReserva")
  private List<Documento> documento;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getReserva() {
    return reserva;
  }

  public void setReserva(String reserva) {
    this.reserva = reserva;
  }

}
