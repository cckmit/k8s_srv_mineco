package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class BusquedaUsuarioItemRenderer implements ListitemRenderer {
  private ISectorUsuarioService sectorUsuarioService;
  private ICargoService cargoService;

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    sectorUsuarioService = (ISectorUsuarioService) SpringUtil.getBean("sectorUsuarioService");
    cargoService = (ICargoService) SpringUtil.getBean("cargoService");

    UsuarioBaseDTO user = (UsuarioBaseDTO) data;
    SectorUsuarioDTO sectorUsuario = sectorUsuarioService.getByUsername(user.getUid());
    String reparticion = "";
    String sector = "";
    String cargoUsuario = "";
    if (sectorUsuario != null) {
      if (sectorUsuario.getSector() != null) {
        sector = sectorUsuario.getSector().getCodigo();
      }
      if (sectorUsuario.getSector().getReparticion() != null) {
        reparticion = sectorUsuario.getSector().getReparticion().getCodigo();
      }
      if (sectorUsuario.getCargoId() != null) {
        CargoDTO cargo = cargoService.obtenerById(sectorUsuario.getCargoId());
        if (cargo != null) {
          cargoUsuario = cargo.getCargoNombre();
        }
      }
    }

    new Listcell(user.getNombre()).setParent(item);
    new Listcell(user.getUid()).setParent(item);
    new Listcell(user.getMail()).setParent(item);
    new Listcell(reparticion).setParent(item);
    new Listcell(sector).setParent(item);
    new Listcell(cargoUsuario).setParent(item);

    Listcell currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();

    // VISUALIZAR
    Image visualizarImage = new Image("/imagenes/edit-find.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=composer.onVisualizarUsuario");
    visualizarImage.setTooltiptext("Visualizar");
    visualizarImage.setParent(hbox);
    hbox.setParent(currentCell);

    // MODIFICAR
    Image modificarImage = new Image("/imagenes/pencil.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificarImage,
        "onClick=composer.onModificarUsuario");
    modificarImage.setTooltiptext("Modificar");
    modificarImage.setParent(hbox);
    hbox.setParent(currentCell);

    // GESTION DE REPARTICIONES
//    if (Utilitarios.isAdministradorCentral()) {
//      Image gestionRepartcionImage = new Image("/imagenes/house.png");
//      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(gestionRepartcionImage,
//          "onClick=composer.onGestionarReparticionesDeUsuario");
//      gestionRepartcionImage.setTooltiptext("Gestion de Organismos");
//      gestionRepartcionImage.setParent(hbox);
//      hbox.setParent(currentCell);
//    }

    // AUDITAR
    // Image visualizarAudiImage = new
    // Image("/imagenes/edit-find-auditoria.png");
    // org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarAudiImage,
    // "onClick=composer.onAuditarUsuario");
    // visualizarAudiImage.setTooltiptext("Historial");
    // visualizarAudiImage.setParent(hbox);
    // hbox.setParent(currentCell);

  }

}
