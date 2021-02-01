package com.egoveris.deo.web.utils;

import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementación de comparator para usuarios
 * 
 * @author bfontana
 *
 */
public class UsuariosComparator implements Comparator {

  public int compare(Object o1, Object o2) {
    String userText = null;
    if (o1 instanceof String) {
      userText = ((String) o1).trim();
    }

    if (userText != null) {

      if ((o2 instanceof Usuario) && (StringUtils.isNotEmpty(userText))
          && (userText.length() > 3)) {
        Usuario dub = (Usuario) o2;
        String nomApe = dub.getNombreApellido();
        String userName = dub.getUsername();
        
        if (nomApe != null && userName != null) {
          if (nomApe.toLowerCase().contains(userText.toLowerCase()) || userName.toLowerCase().contains(userText.toLowerCase())) {
            return 0;
          }
        }
      }

    }

    return 1;
  }

}
