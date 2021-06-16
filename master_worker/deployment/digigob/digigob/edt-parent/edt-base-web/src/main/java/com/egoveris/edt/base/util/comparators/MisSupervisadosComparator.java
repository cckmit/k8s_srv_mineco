package com.egoveris.edt.base.util.comparators;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class MisSupervisadosComparator implements Comparator, Serializable {

  private boolean asc = true;
  private int type = 0;
  private int col = 0;

  /**
   * Instantiates a new mis supervisados comparator.
   *
   * @param asc
   *          the asc
   * @param type
   *          the type
   * @param col
   *          the col
   */
  public MisSupervisadosComparator(boolean asc, int type, int col) {
    this.asc = asc;
    this.type = type;
    this.col = col;
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

    Listitem listitem1 = (Listitem) o1;
    Listitem listitem2 = (Listitem) o2;

    Listcell listcell1 = (Listcell) listitem1.getChildren().get(col);
    Listcell listcell2 = (Listcell) listitem2.getChildren().get(col);
    if (type == 1) {
      if (StringUtils.isEmpty(listcell1.getLabel())) {
        result = -1;
      } else if (StringUtils.isEmpty(listcell2.getLabel())) {
        result = 1;
      } else {
        result = (Integer.valueOf(listcell1.getLabel())
            .compareTo(Integer.valueOf(listcell2.getLabel()))) * (asc ? 1 : -1);
      }
    } else {
      return 0;
    }

    return result;

  }

}