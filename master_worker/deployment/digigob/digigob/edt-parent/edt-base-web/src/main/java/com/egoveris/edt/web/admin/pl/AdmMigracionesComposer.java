package com.egoveris.edt.web.admin.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tab;

import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;


public class AdmMigracionesComposer extends BaseComposer {

  protected Tab tab_migrarReparticion;
  protected Tab tab_migrarSector;

  /**
  * 
  */
  private static final long serialVersionUID = 989874159754026275L;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    if (Utilitarios.isAdministradorCentral()) {
      return;
    }
    if (!Utilitarios.isAdministradorCentral()) {
      tab_migrarReparticion.setDisabled(true);
      tab_migrarReparticion.setSelected(false);
      tab_migrarSector.setSelected(true);
      tab_migrarSector.setDisabled(true);
    }
    if (Utilitarios.isAdministradorLocalReparticion() && !Utilitarios.isAdministradorCentral()
        && getAlsLimitado() == null) {
      tab_migrarReparticion.setDisabled(true);
      tab_migrarReparticion.setSelected(false);
      tab_migrarSector.setDisabled(false);
      tab_migrarSector.setSelected(true);
    }
  }

}
