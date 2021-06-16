package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.TipoDocumentoDTO;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class TrataTipoDocumentoItemRenderer implements ComboitemRenderer {

  public void render(Comboitem item, Object data, int arg1) throws Exception {

    TipoDocumentoDTO tipodocumento = (TipoDocumentoDTO) data;

    item.setValue(tipodocumento.toStringGedo());

  }

}
