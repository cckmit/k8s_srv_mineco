package com.egoveris.edt.base.util.calendar;

import com.egoveris.edt.base.exception.DateException;
import com.egoveris.edt.base.model.eu.feriado.FeriadoDTO;

import java.util.Date;
import java.util.List;

/**
 * The Class CalendarGcaba.
 *
 * @author lfishkel
 */
public class CalendarGcaba {

  private static final long serialVersionUID = -6193677525937915369L;
  private static final int SABADO = 7;
  private static final int DOMINGO = 1;
  private List<FeriadoDTO> feriados;

  /** The instance. */
  // SINGLETON
  public static CalendarGcaba instance;

  /**
   * Gets the single instance of CalendarGcaba.
   *
   * @return single instance of CalendarGcaba
   */
  public static CalendarGcaba getInstance() {
    if (CalendarGcaba.instance == null) {
      CalendarGcaba.instance = new CalendarGcaba();
    }
    return CalendarGcaba.instance;
  }

  /**
   * Calcula la cantidad de días hábiles desde el momento en que se calcula
   * hasta la fecha parametrizada devuelve null en caso de que la fecha sea
   * anterior a la actual.
   *
   * @param fecha
   *          the fecha
   * @return the integer
   * @throws Exception
   *           the exception
   */
  public Integer cantidadDiasVencimiento(Date fecha) throws DateException {
    Integer dias = null;
    Date currentDate = DateUtil.beginning(new Date());
    if (fecha != null) {
      Date fechaVencimiento = DateUtil.beginning(fecha);
      if (currentDate.before(fechaVencimiento)) {
        dias = 0;
        while (currentDate.before(fechaVencimiento)) {
          if (!this.esDiaNoHabil(currentDate)) {
            dias++;
          }
          this.aumentarUnDia(currentDate);
        }
      }
    } else {
      throw new DateException("Sin fecha de vencimiento");
    }

    return dias;
  }

  /**
   * Devuelve true si una fecha es día no hábil y false si es dia hábil.
   *
   * @param fecha
   *          the fecha
   * @return true, if successful
   */
  public boolean esDiaNoHabil(Date fecha) {
    boolean esDiaNoHabil;

    int diaDeLaSemana = DateUtil.diaDeLaSemana(fecha);
    esDiaNoHabil = diaDeLaSemana == SABADO || diaDeLaSemana == DOMINGO;
    if (!esDiaNoHabil) {
      for (FeriadoDTO feriado : this.feriados) {
        if (DateUtil.beginning(feriado.getFecha()).equals(DateUtil.beginning(fecha))) {
          esDiaNoHabil = true;
        }
      }
    }
    return esDiaNoHabil;
  }

  public List<FeriadoDTO> getFeriados() {
    return feriados;
  }

  public void setFeriados(List<FeriadoDTO> feriados) {
    this.feriados = feriados;
  }

  /**
   * Devuelve la fecha y hora del día calculado a partir de la fecha 'fecha'
   * despues de 'cantidadDeDiasHabiles' salteando los dias no hábiles. Devuelve
   * null si alguno de los parámetros es null
   *
   * @param fecha
   *          the fecha
   * @param cantidadDeDiasHabiles
   *          the cantidad de dias habiles
   * @return the date
   */
  public Date fechaApartirDe(Date fecha, Integer cantidadDeDiasHabiles) {
    Date ret = null;
    Integer cant = cantidadDeDiasHabiles;
    if (fecha != null && cantidadDeDiasHabiles != null) {
      ret = fecha;
      if (cantidadDeDiasHabiles > 0) {
        while (cant > 0) {
          this.aumentarUnDia(ret);
          if (!this.esDiaNoHabil(ret)) {
            cant--;
          }
        }
      } else if (cantidadDeDiasHabiles < 0) {
        while (cant < 0) {
          this.disminuirUnDia(ret);
          if (!this.esDiaNoHabil(ret)) {
            cant++;
          }
        }
      }
    }
    return ret;

  }

  /**
   * Aumentar un dia.
   *
   * @param currentDate
   *          the current date
   */
  public void aumentarUnDia(Date currentDate) {
    currentDate.setTime(currentDate.getTime() + 86400000);
  }

  /**
   * Disminuir un dia.
   *
   * @param currentDate
   *          the current date
   */
  public void disminuirUnDia(Date currentDate) {
    currentDate.setTime(currentDate.getTime() - 86400000);
  }

}
