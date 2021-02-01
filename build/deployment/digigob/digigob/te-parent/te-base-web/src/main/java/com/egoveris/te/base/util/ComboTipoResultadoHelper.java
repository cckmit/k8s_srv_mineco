package com.egoveris.te.base.util;

import com.egoveris.te.base.model.PropertyConfigurationDTO;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zul.Listitem;

public class ComboTipoResultadoHelper {
	
  private ComboTipoResultadoHelper() {
    // Private constructor
  }
  
  public static String getLabelComboTipoResultado(Set<Listitem> data, int total) {
    StringBuilder label = new StringBuilder("");
    int seleccionados = 0;
    
    if (data != null) {
      Iterator<Listitem> iterator = data.iterator();
      
      while (iterator.hasNext()) {
        Listitem listitem = iterator.next();
        PropertyConfigurationDTO property = listitem.getValue();
        seleccionados++;
        
        if (label.toString().isEmpty()) {
          label.append(property.getValor());
        }
        else {
          label.append(", ").append(property.getValor());
        }
      }
      
      if (seleccionados == total) {
        label = new StringBuilder("Todos");
      }
    }
    
    return label.toString();
  }
  
  public static String getLabelComboTipoResultado(List<PropertyConfigurationDTO> data, int total) {
    StringBuilder label = new StringBuilder("");
    int seleccionados = 0;
    
    if (data != null) {
      for (PropertyConfigurationDTO property : data) {
        seleccionados++;
        
        if (label.toString().isEmpty()) {
          label.append(property.getValor());
        }
        else {
          label.append(", ").append(property.getValor());
        }
      }
      
      if (seleccionados == total) {
        label = new StringBuilder("Todos");
      }
    }
    
    return label.toString();
  }
  
}
