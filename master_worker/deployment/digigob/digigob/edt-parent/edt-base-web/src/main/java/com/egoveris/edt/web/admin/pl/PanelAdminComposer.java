package com.egoveris.edt.web.admin.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

public class PanelAdminComposer extends GenericForwardComposer {

  /*
  private Tab tabReparticiones;
  private Tab tabCargos;
  private Tab tabSectores;
  private Tab tabAdmin;
  private Tab tabCalendario;

  private String ldapEntorno;
  private String alsLimitado;
  */

  private static final long serialVersionUID = -3774404200605962656L;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    /*
    ldapEntorno = (String) SpringUtil.getBean("ldapEntorno");
    // Indica que solo los AC pueden Administrar las Migraciones.
    tabReparticiones.setVisible(Utilitarios.isAdministradorCentral());
    tabCargos.setVisible(Utilitarios.isAdministradorCentral());
    */
    /*
     * pedido especial de SB para la version 3.6.8
     */
    /*
    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    this.alsLimitado = appProperty.getString("eu.mig.als.limitado");
    */
    /*
     * pedido especial de SB para la version 3.6.8
     */
    /*
    tabReparticiones.setTooltiptext(Labels.getLabel(
        "eu.panelAdmin.tabAdministracionReparticion.help", new String[] { ldapEntorno }));
    tabCargos.setTooltiptext(Labels.getLabel("eu.panelAdmin.tabAdministracionCargo.help",
        new String[] { ldapEntorno }));
    tabSectores.setTooltiptext(Labels.getLabel("eu.panelAdmin.tabAdministracionSector.help",
        new String[] { ldapEntorno }));
    tabAdmin.setTooltiptext(Labels.getLabel("eu.panelAdmin.tabAdministracionUsuarios.help",
        new String[] { ldapEntorno }));
    tabCalendario.setVisible(Utilitarios.isAdministradorCentral());
    */
  }
}
