package com.egoveris.edt.web.admin.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;

import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

public class PerfilUsuarioComposer extends BaseComposer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 4306917550580991667L;
  private AnnotateDataBinder binder;
  private Include inc_gestionDatosPersonalesUsuario;
  private Include inc_periodoLicencia;
  private Tab periodoLicenciaTab;
  private IUsuarioService usuarioService;
  private IDatosUsuarioService datosUsuarioService;
  private ISectorUsuarioService sectorUsuarioService;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
    sectorUsuarioService = (ISectorUsuarioService) SpringUtil.getBean("sectorUsuarioService");
    datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");

    inc_gestionDatosPersonalesUsuario
        .setSrc("/datosPersonales/tabGestionDatosPersonalesUsuario.zul");
    inc_periodoLicencia.setSrc("/datosPersonales/tabPeriodoLicencia.zul");

    boolean existeDatosUsuario = (boolean) Executions.getCurrent().getAttribute(ConstantesSesion.KEY_EXISTE_DATO_USUARIO);
    if (existeDatosUsuario) {
      Boolean mesaDesactualizada = sectorUsuarioService.sectorMesaActualizado(this.getUsername());
      Executions.getCurrent().setAttribute(ConstantesSesion.KEY_MESA_ACTUALIZADA, mesaDesactualizada);
    }

    Executions.getCurrent().setAttribute(ConstantesSesion.KEY_EXISTE_DATOS_USUARIO, existeDatosUsuario);
    if (!existeDatosUsuario) {
      periodoLicenciaTab.setDisabled(true);
    }

    this.binder = new AnnotateDataBinder(comp);
    binder.loadAll();
  }

  public Include getInc_gestionDatosPersonalesUsuario() {
    return inc_gestionDatosPersonalesUsuario;
  }

  public void setInc_gestionDatosPersonalesUsuario(Include inc_gestionDatosPersonalesUsuario) {
    this.inc_gestionDatosPersonalesUsuario = inc_gestionDatosPersonalesUsuario;
  }

  public Include getInc_periodoLicencia() {
    return inc_periodoLicencia;
  }

  public void setInc_periodoLicencia(Include inc_periodoLicencia) {
    this.inc_periodoLicencia = inc_periodoLicencia;
  }

}
