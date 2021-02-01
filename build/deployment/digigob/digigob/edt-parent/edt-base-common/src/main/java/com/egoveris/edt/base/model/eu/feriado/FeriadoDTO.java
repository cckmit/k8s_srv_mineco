package com.egoveris.edt.base.model.eu.feriado;

import com.egoveris.edt.base.util.calendar.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class FeriadoDTO implements Serializable {

  private static final long serialVersionUID = 3623385534117379133L;
  private Integer id;
  private String motivo;
  private Date fecha;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof FeriadoDTO)) {
      return false;
    }
    return this.getFecha().equals(((FeriadoDTO) obj).getFecha());
  }

  @Override
  public int hashCode() {
    return id * motivo.hashCode() * fecha.hashCode();
  }

  /**
   * Compare to.
   *
   * @param feriado
   *          the feriado
   * @return the int
   */
  public int compareTo(FeriadoDTO feriado) {
    int ret = 0;
    boolean fecha = false;
    if (DateUtil.beginning(this.fecha).equals(DateUtil.beginning(feriado.getFecha()))) {
      ret = 1;
      fecha = true;
    }
    if (this.motivo.trim().equalsIgnoreCase(feriado.getMotivo().trim())) {
      ret = 2;
    }
    if (fecha && ret == 2) {
      ret = 3;
    }
    return ret;
  }

}