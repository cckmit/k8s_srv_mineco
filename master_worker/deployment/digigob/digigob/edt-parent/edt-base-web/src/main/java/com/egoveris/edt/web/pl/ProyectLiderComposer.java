package com.egoveris.edt.web.pl;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Tab;

import com.egoveris.edt.web.common.BaseComposer;

public class ProyectLiderComposer extends BaseComposer {
  private static final long serialVersionUID = 1086214946834993385L;
  private AnnotateDataBinder binder;
  private Tab adminTAB;
  @Autowired
  private String ldapEntorno;

  @Override
  public void doAfterCompose(Component comp) throws Exception {

    super.doAfterCompose(comp);
    // seteo el rol del usuario si es q es proyect lider (PL)
    Executions.getCurrent().getDesktop().getSession().setAttribute("ROL_PL", Boolean.TRUE);
    binder = new AnnotateDataBinder(comp);
    adminTAB.setTooltiptext(Labels.getLabel("eu.panelAdmin.tabAdministracionUsuarios.help",
        new String[] { ldapEntorno }));
    binder.loadAll();

  }

  public String getLdapEntorno() {
    return ldapEntorno;
  }

  public void setLdapEntorno(String ldapEntorno) {
    this.ldapEntorno = ldapEntorno;
  }

}