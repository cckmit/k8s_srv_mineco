package com.egoveris.edt.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;


public class FeriadoResponse implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = -3957240997047835660L;
	
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
    if (!(obj instanceof FeriadoResponse)) {
      return false;
    }
    return this.getFecha().equals(((FeriadoResponse) obj).getFecha());
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
  public int compareTo(FeriadoResponse feriado) {
    int ret = 0;
    boolean fechaCompare = false;
    if (this.beginning(this.fecha).equals(this.beginning(feriado.getFecha()))) {
      ret = 1;
      fechaCompare = true;
    }
    if (this.motivo.trim().equalsIgnoreCase(feriado.getMotivo().trim())) {
      ret = 2;
    }
    if (fechaCompare && ret == 2) {
      ret = 3;
    }
    return ret;
  }
  
  private Date beginning(Date date) {
    GregorianCalendar calendar = this.getCalendar(date);
    int dia = calendar.get(GregorianCalendar.DATE);
    int mes = calendar.get(GregorianCalendar.MONTH);
    int anio = calendar.get(GregorianCalendar.YEAR);
    calendar = new GregorianCalendar(anio, mes, dia);
    return new Date(calendar.getTimeInMillis());
  }
  
  private GregorianCalendar getCalendar(Date fecha) {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(fecha);
    return calendar;
  }
}
