package com.egoveris.deo.web.satra.consulta;

import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

public class ConsultaTareasUserComparator implements Comparator {

  @Override
  public int compare(Object o1, Object o2) {
    String userText = null;
    if (o1 instanceof String) {
      userText = ((String) o1).trim();
    }

    if (userText != null) {
      if ((o2 instanceof Usuario) && (StringUtils.isNotEmpty(userText))
          && (userText.length() > 1)) {
        Usuario dub = (Usuario) o2;
        String NyAp = dub.getNombreApellido();
        String usrName = dub.getUsername();
        if (NyAp != null) {
          if (NyAp.toLowerCase().contains(userText.toLowerCase())
              || usrName.toLowerCase().contains(userText.toLowerCase())) {
            return 0;
          }
        }
      }
    }
    return 1;
  }

}
