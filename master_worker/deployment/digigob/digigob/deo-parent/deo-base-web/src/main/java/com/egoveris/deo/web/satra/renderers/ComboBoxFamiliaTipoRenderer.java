package com.egoveris.deo.web.satra.renderers;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;

public class ComboBoxFamiliaTipoRenderer implements ComboitemRenderer<Object> {

  public void render(Comboitem item, Object data, int arg2) throws Exception {

    if (data instanceof FamiliaTipoDocumentoDTO) {
      FamiliaTipoDocumentoDTO familiaTipoDocumento = (FamiliaTipoDocumentoDTO) data;
      item.setLabel(familiaTipoDocumento.getNombre());
      item.setValue(familiaTipoDocumento.getNombre());
      item.setId(familiaTipoDocumento.getId().toString());
    } else if(data instanceof String){
    	String dataStr = (String) data;
    	item.setLabel(dataStr);
        item.setValue(dataStr);
    }
  }

}
