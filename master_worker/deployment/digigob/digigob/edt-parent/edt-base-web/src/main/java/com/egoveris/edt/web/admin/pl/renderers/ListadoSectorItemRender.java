package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.base.util.Utilidades;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

import java.util.HashMap;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ListadoSectorItemRender implements ListitemRenderer {

  private HashMap<String, ReparticionDTO> repasAdm;
  private IAdminReparticionService adminReparticionService;

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    if (!Utilitarios.isAdministradorCentral() && Utilitarios.isAdministradorLocalReparticion()) {
      if (repasAdm == null) {
        adminReparticionService = (IAdminReparticionService) SpringUtil
            .getBean("adminReparticionService");
        List<ReparticionDTO> listaRepartcionesDelUsuario = adminReparticionService
            .obtenerReparticionesRelacionadasByUsername(
                Utilitarios.obtenerUsuarioActual().getUsername());
        repasAdm = new HashMap<>();
        for (ReparticionDTO reparticion : listaRepartcionesDelUsuario) {
          repasAdm.put(reparticion.getCodigo(), reparticion);
        }
      }
    }

    SectorDTO sector = (SectorDTO) data;
    new Listcell(sector.getReparticion().getCodigo()).setParent(item);
    new Listcell(sector.getCodigo()).setParent(item);
    new Listcell(sector.getNombre()).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(sector.getVigenciaDesde())).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(sector.getVigenciaHasta())).setParent(item);
    new Listcell(sector.getSectorMesa()).setParent(item);
    new Listcell(
        sector.getReparticion().getEnRed() != null ? sector.getReparticion().getEnRed() : "N")
            .setParent(item);

    if (sector.getEstadoRegistro() == null)
      new Listcell("").setParent(item);
    else
      new Listcell(sector.getEstadoRegistro() ? "ACTIVO" : "INACTIVO").setParent(item);

    Listcell currentCell;

    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();

    // VISUALIZAR
    Image visualizarImage = new Image("/imagenes/edit-find.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=composer.onVisualizarSectores");
    visualizarImage.setTooltiptext("Visualizar");
    // Label visualizar = new Label("Visualizar");
    // org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,"onClick=composer.onVisualizarSectores");
    visualizarImage.setParent(hbox);
    // visualizar.setParent(hbox);
    hbox.setParent(currentCell);

    // MODIFICAR
    Image modificarImage = new Image("/imagenes/pencil.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificarImage,
        "onClick=composer.onModificarSector");
    modificarImage.setTooltiptext("Modificar");
    // org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificar,"onClick=composer.onModificarSector");
    modificarImage.setParent(hbox);
    // modificar.setParent(hbox);
    hbox.setParent(currentCell);

    if (!Utilitarios.isAdministradorCentral() && Utilitarios.isAdministradorLocalReparticion()) {
      if (!repasAdm.containsKey(sector.getReparticion().getCodigo())) {
        modificarImage.setVisible(false);
      }
    }
  }

}
