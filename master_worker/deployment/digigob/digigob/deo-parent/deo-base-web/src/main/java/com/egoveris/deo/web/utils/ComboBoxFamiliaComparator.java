package com.egoveris.deo.web.utils;

import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;

import java.util.Comparator;

public class ComboBoxFamiliaComparator implements Comparator<FamiliaTipoDocumentoDTO> {

  public static final String sinFamilia = "Sin Familia";

  @Override
  public int compare(FamiliaTipoDocumentoDTO o1, FamiliaTipoDocumentoDTO o2) {
    if (o1.getNombre().equalsIgnoreCase(sinFamilia)) {
      return -1;
    }
    return o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase());
  }

}
