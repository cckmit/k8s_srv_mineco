package com.egoveris.edt.base.util.comparators;

import com.egoveris.edt.base.model.TareasPorSistemaBean;

import java.io.Serializable;
import java.util.Comparator;

public class MisTareasComparator implements Comparator, Serializable {

  private boolean asc = true;
  private int type = 0;

  public MisTareasComparator(boolean asc, int type) {
    this.asc = asc;
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override
  public int compare(Object o1, Object o2) {
    int result;

    TareasPorSistemaBean contributor1 = (TareasPorSistemaBean) o1;
    TareasPorSistemaBean contributor2 = (TareasPorSistemaBean) o2;
    switch (type) {
      case 1:
        if (contributor1.getPorcentajeFrecuenciaMenor() == null) {
          result = -1;
        } else if (contributor2.getPorcentajeFrecuenciaMenor() == null) {
          result = 1;
        } else {
          result = contributor1.getPorcentajeFrecuenciaMenor()
              .compareTo(contributor2.getPorcentajeFrecuenciaMenor()) * (asc ? 1 : -1);
        }
  
        break;
  
      case 2:
        if (contributor1.getPorcentajeFrecuenciaMayor() == null) {
          result = -1;
        } else if (contributor2.getPorcentajeFrecuenciaMayor() == null) {
          result = 1;
        } else {
          result = contributor1.getPorcentajeFrecuenciaMayor()
              .compareTo(contributor2.getPorcentajeFrecuenciaMayor()) * (asc ? 1 : -1);
        }
  
        break;
  
      case 3:
        if (contributor1.getFrecuencia1() == null) {
          result = -1;
        } else if (contributor2.getFrecuencia1() == null) {
          result = 1;
        } else {
          result = contributor1.getFrecuencia1().compareTo(contributor2.getFrecuencia1())
              * (asc ? 1 : -1);
        }
  
        break;
  
      case 4:
        if (contributor1.getFrecuencia2() == null) {
          result = -1;
        } else if (contributor2.getFrecuencia2() == null) {
          result = 1;
        } else {
          result = contributor1.getFrecuencia2().compareTo(contributor2.getFrecuencia2())
              * (asc ? 1 : -1);
        }
  
        break;
  
      case 5:
        if (contributor1.getFrecuencia3() == null) {
          result = -1;
        } else if (contributor2.getFrecuencia3() == null) {
          result = 1;
        } else {
          result = contributor1.getFrecuencia3().compareTo(contributor2.getFrecuencia3())
              * (asc ? 1 : -1);
        }
  
        break;
  
      case 6:
        if (contributor1.getFrecuencia4() == null) {
          result = -1;
        } else if (contributor2.getFrecuencia4() == null) {
          result = 1;
        } else {
          result = contributor1.getFrecuencia4().compareTo(contributor2.getFrecuencia4())
              * (asc ? 1 : -1);
        }
  
        break;
  
      case 7:
        if (contributor1.getTareasPendientes() == null) {
          result = -1;
        } else if (contributor2.getTareasPendientes() == null) {
          result = 1;
        } else {
          result = contributor1.getTareasPendientes().compareTo(contributor2.getTareasPendientes())
              * (asc ? 1 : -1);
        }
  
        break;
  
      default: 
        result = 0;
    }

    return result;

  }

}
