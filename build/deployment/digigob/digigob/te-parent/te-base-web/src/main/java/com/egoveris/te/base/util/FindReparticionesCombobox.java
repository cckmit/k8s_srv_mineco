package com.egoveris.te.base.util;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;

public class FindReparticionesCombobox extends Combobox {

  // @Autowired
  // private ObtenerReparticionServices obtenerReparticionService;

  /**
  * 
  */
  private static final long serialVersionUID = -8643296801162174514L;
  private List<ReparticionBean> listaReparticionSADECompleta;

  public FindReparticionesCombobox() {
    refresh(""); // init the child comboitems

  }

  public FindReparticionesCombobox(String value) {
    super(value); // it invokes setValue(), which inits the child comboitems
  }

  public void setValue(String value) {
    super.setValue(value);
    refresh(value); // refresh the child comboitems
  }

  // Listens what an user is entering.

  public void onChanging(InputEvent evt) {
    if (!evt.isChangingBySelectBack())
      refresh(evt.getValue().toUpperCase());
  }

  // Refresh comboitem based on the specified value.

  private void refresh(String val) {
    if (StringUtils.isNotEmpty(val) && val.length() >= 3) {

    }
  }

  public void setListaReparticionSADECompleta(List<ReparticionBean> listaReparticionSADECompleta) {
    this.listaReparticionSADECompleta = listaReparticionSADECompleta;
  }

  public List<ReparticionBean> getListaReparticionSADECompleta() {
    return listaReparticionSADECompleta;
  }

}
