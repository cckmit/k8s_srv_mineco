package com.egoveris.te.base.util.comparator;

import com.egoveris.te.base.model.Tarea;

import java.io.Serializable;
import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class InboxComparator implements Comparator, Serializable {
  private static final long serialVersionUID = 5257991707445552623L;
  private boolean asc = true;
  private int type = 0;
  public static final int greater = 1;
  public static final int less = -1;
  public static final int equals = 0;

  public InboxComparator(boolean asc, int type) {
    this.asc = asc;
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int compare(Object o1, Object o2) {
    int result = 0;

    Tarea contributor1 = (Tarea) o1;
    Tarea contributor2 = (Tarea) o2;
    switch (type) {
    case 1:
      if (contributor1.getNombreTarea() == null) {
        result = -1;
      } else if (contributor2.getNombreTarea() == null) {
        result = 1;
      } else {
        result = contributor1.getNombreTarea().compareTo(contributor2.getNombreTarea())
            * (asc ? 1 : -1);
      }

      break;

    case 2:

      if (contributor1.getFechaCreacion() == null) {
        result = -1;
      } else if (contributor2.getFechaCreacion() == null) {
        result = 1;
      } else {
        if (contributor1.getFechaModificacion() != null) {
          if (contributor2.getFechaModificacion() != null) {
            result = contributor1.getFechaModificacion()
                .compareTo(contributor2.getFechaModificacion()) * (asc ? 1 : -1);
          } else {
            result = contributor1.getFechaModificacion().compareTo(contributor2.getFechaCreacion())
                * (asc ? 1 : -1);
          }

        } else {
          if (contributor2.getFechaModificacion() != null) {
            result = contributor1.getFechaCreacion().compareTo(contributor2.getFechaModificacion())
                * (asc ? 1 : -1);
          } else {
            result = contributor1.getFechaCreacion().compareTo(contributor2.getFechaCreacion())
                * (asc ? 1 : -1);
          }
        }

      }
      break;

    case 3:
      if (contributor1.getCodigoExpediente() == null) {
        result = -1;
      } else if (contributor2.getCodigoExpediente() == null) {
        result = 1;
      } else {
        result = contributor1.getCodigoExpediente().compareTo(contributor2.getCodigoExpediente())
            * (asc ? 1 : -1);
      }
      break;

    case 4:
      if (contributor1.getCodigoTrata() == null) {
        result = -1;
      } else if (contributor2.getCodigoTrata() == null) {
        result = 1;
      } else {
        result = contributor1.getCodigoTrata().compareTo(contributor2.getCodigoTrata())
            * (asc ? 1 : -1);
      }
      break;

    case 5:
      if (contributor1.getDescripcionTrata() == null) {
        contributor1.setDescripcionTrata("");
      }
      if (contributor2.getDescripcionTrata() == null) {
        contributor2.setDescripcionTrata("");
      }

      if (contributor1.getDescripcionTrata() == null) {
        result = -1;
      } else if (contributor2.getDescripcionTrata() == null) {
        result = 1;
      } else {
        result = contributor1.getDescripcionTrata().compareTo(contributor2.getDescripcionTrata())
            * (asc ? 1 : -1);
      }
      break;

    case 6:
      if (contributor1.getUsuarioAnterior() == null) {
        result = -1;
      } else if (contributor2.getUsuarioAnterior() == null) {
        result = 1;
      } else {
        result = contributor1.getUsuarioAnterior().compareTo(contributor2.getUsuarioAnterior())
            * (asc ? 1 : -1);
      }
      break;

    case 7:
      if (contributor1.getMotivo() == null) {
        result = -1;
      } else if (contributor2.getMotivo() == null) {
        result = 1;
      } else {
        result = contributor1.getMotivo().compareTo(contributor2.getMotivo()) * (asc ? 1 : -1);
      }
      break;

    default:
      result = 0;
    }

    return result;

  }
}