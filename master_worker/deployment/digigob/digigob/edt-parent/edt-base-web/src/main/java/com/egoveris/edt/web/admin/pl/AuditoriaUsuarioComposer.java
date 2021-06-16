package com.egoveris.edt.web.admin.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Tab;

import com.egoveris.edt.web.common.BaseComposer;

public class AuditoriaUsuarioComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -3380420592048599790L;

  private Tab tabAuditarDatosPersonales;
  private Tab tabAuditarRolesYPermisos;
  private Tab tabAuditarRepaAdministradas;
  private Tab tabAuditarRepaHabilitadas;

  private AnnotateDataBinder binder;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    binder.loadAll();
  }

}