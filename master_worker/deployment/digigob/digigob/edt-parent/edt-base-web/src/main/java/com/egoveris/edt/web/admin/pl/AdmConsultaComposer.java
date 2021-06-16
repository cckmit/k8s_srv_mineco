package com.egoveris.edt.web.admin.pl;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

import com.egoveris.edt.web.common.BaseComposer;

public class AdmConsultaComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 1694066960159715172L;

  @Autowired
  protected AnnotateDataBinder binder;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
  }
}
