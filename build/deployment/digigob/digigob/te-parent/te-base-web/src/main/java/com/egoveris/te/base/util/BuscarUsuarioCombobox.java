package com.egoveris.te.base.util;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModels;

public class BuscarUsuarioCombobox extends Combobox {
  /**
  * 
  */
  private static final long serialVersionUID = -463443722305348207L;

  public void setModel(ListModel model) {
    super.setModel(ListModels.toListSubModel(model, new UsuariosComparatorCombobox(), 30));
  }
}

@SuppressWarnings("rawtypes")
class UsuariosComparatorCombobox implements Comparator {

  public int compare(Object o1, Object o2) {
    String userText = null;
    if (o1 instanceof String) {
      userText = (String) o1;
    }

    if (userText != null && (o2 instanceof UsuarioReducido) && (StringUtils.isNotEmpty(userText))
        && (userText.length() > 2)) {
      Usuario dub = (Usuario) o2;
      String NyAp = dub.getNombreApellido();
      if (NyAp != null) {
        if (NyAp.toLowerCase().contains(userText.toLowerCase())) {
          return 0;
        }
      }
    }

    if (userText != null && (o2 instanceof Usuario) && (StringUtils.isNotEmpty(userText))
        && (userText.length() > 2)) {
      Usuario dub = (Usuario) o2;
      String NyAp = dub.getNombreApellido();
      if (NyAp != null) {
        if (NyAp.toLowerCase().contains(userText.toLowerCase())) {
          return 0;
        }
      }
    }
    return 1;
  }
}