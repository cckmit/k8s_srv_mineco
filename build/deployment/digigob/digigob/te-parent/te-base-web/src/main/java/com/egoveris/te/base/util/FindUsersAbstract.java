package com.egoveris.te.base.util;

import java.util.List;

import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;

public abstract class FindUsersAbstract extends Combobox {
  /**
  * 
  */
  private static final long serialVersionUID = 7498919996476316655L;

  public FindUsersAbstract() {
    this.refresh(""); // init the child comboitems
  }

  public FindUsersAbstract(String value) {
    super(value); // it invokes setValue(), which inits the child comboitems
  }

  public void setValue(String value) {
    super.setValue(value);
    this.refresh(value); // refresh the child comboitems
  }

  public void onChanging(InputEvent evt) {
    if (!evt.isChangingBySelectBack())
      this.refresh(evt.getValue());
  }

  protected void refresh(String value) {

  }

  protected abstract List<?> lookForItems(String value);

}
