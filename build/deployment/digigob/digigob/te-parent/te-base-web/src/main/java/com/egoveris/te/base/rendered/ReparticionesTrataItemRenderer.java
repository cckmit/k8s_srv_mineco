package com.egoveris.te.base.rendered;

import com.egoveris.te.base.composer.ReparticionesTrataComposer;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.util.ConstantesWeb;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;;

public class ReparticionesTrataItemRenderer implements ListitemRenderer {

  private final static String ESTILO_TODAS = "font-weight: bold; color: black";
  private ReparticionesTrataComposer composer;

  public ReparticionesTrataItemRenderer(ReparticionesTrataComposer composer) {
    super();
    this.composer = composer;
  }

  public ReparticionesTrataItemRenderer() {
  }

  public void render(Listitem item, Object data, int arg1) throws Exception {
    TrataReparticionDTO reparticionHabilitada = (TrataReparticionDTO) data;
    Listcell CodigoReparticion = new Listcell(reparticionHabilitada.getCodigoReparticion());
    CodigoReparticion.setParent(item);

    Checkbox habilitacion = new Checkbox();
    habilitacion.setChecked(reparticionHabilitada.getHabilitacion());
    Listcell habilitacionCell = new Listcell();
    habilitacion.setParent(habilitacionCell);
    habilitacionCell.setParent(item);

    Checkbox reserva = new Checkbox();
    reserva.setChecked(reparticionHabilitada.getReserva());
    Listcell reservaCell = new Listcell();
    reserva.setParent(reservaCell);
    reservaCell.setParent(item);

    Listcell currentCell = new Listcell();
    currentCell.setParent(item);
    if (reparticionHabilitada.getCodigoReparticion()
        .compareTo(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS) == 0) {
      CodigoReparticion.setStyle(ESTILO_TODAS);
      CodigoReparticion.setLabel(ConstantesWeb.NOMBRE_REPARTICION_TODAS);
    }
    habilitacion.addEventListener("onCheck",
        new TrataReparticionListener(this.composer, reparticionHabilitada, "Habilitar"));
    reserva.addEventListener("onCheck",
        new TrataReparticionListener(this.composer, reparticionHabilitada, "Reserva"));
  }

  final class TrataReparticionListener implements EventListener {

    private ReparticionesTrataComposer composer;
    private TrataReparticionDTO reparticionHabilitada;
    private String identificadorComponente;

    public TrataReparticionListener(ReparticionesTrataComposer composer,
        TrataReparticionDTO reparticionHabilitada, String identificadorComponente) {
      super();
      this.composer = composer;
      this.reparticionHabilitada = reparticionHabilitada;
      this.identificadorComponente = identificadorComponente;
    }

    public void onEvent(Event event) throws Exception {
      List<TrataReparticionDTO> reparticiones = this.composer.getReparticionesHabilitadas();
      if (event.getName().equals(Events.ON_CHECK)) {
        Checkbox checkBox = (Checkbox) event.getTarget();
        for (TrataReparticionDTO reparticion : reparticiones) {
          if (reparticion.getCodigoReparticion().trim()
              .compareTo(reparticionHabilitada.getCodigoReparticion().trim()) == 0) {
            if (identificadorComponente.compareTo("Habilitar") == 0)
              reparticion.setHabilitacion(checkBox.isChecked());

            if (identificadorComponente.compareTo("Reserva") == 0)
              reparticion.setReserva(checkBox.isChecked());

            if (reparticion.getCodigoReparticion().trim()
                .compareTo(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS) == 0) {
              checkTodas(identificadorComponente, reparticiones, checkBox.isChecked());
            } else if (!checkBox.isChecked())
              actualizarCheckReparticionTodas(identificadorComponente, reparticiones);
            break;
          }
        }
        this.composer.refreshListboxReparticionesAgregadas();
      }
      if (event.getName().equals(Events.ON_CLICK)) {
        // Desactivación de la repartición en el listado de reparticiones
        // habilitadas
        for (TrataReparticionDTO reparticion : reparticiones) {
          if (reparticion.getCodigoReparticion().trim()
              .compareTo(reparticionHabilitada.getCodigoReparticion().trim()) == 0) {
            reparticion.setHabilitacion(false);
            this.composer.refreshListboxReparticionesAgregadas();
            break;
          }
        }
      }
    }

    private void checkTodas(String permiso, List<TrataReparticionDTO> reparticiones,
        boolean habilitar) {
      for (TrataReparticionDTO reparticion : reparticiones) {
        if (reparticion.getCodigoReparticion().trim()
            .compareTo(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS) != 0) {
          if (permiso.compareTo("Habilitar") == 0)
            reparticion.setHabilitacion(habilitar);

          if (permiso.compareTo("Reserva") == 0)
            reparticion.setReserva(habilitar);
        }
      }
    }
  }

  private void actualizarCheckReparticionTodas(String permiso,
      List<TrataReparticionDTO> reparticiones) {
    for (TrataReparticionDTO reparticion : reparticiones) {
      if (reparticion.getCodigoReparticion().trim()
          .compareTo(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS) == 0) {
        if (permiso.compareTo("Habilitar") == 0 && reparticion.getHabilitacion())
          reparticion.setHabilitacion(false);
      }

      if (reparticion.getCodigoReparticion().trim()
          .compareTo(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS) == 0) {
        if (permiso.compareTo("Reserva") == 0 && reparticion.getReserva())
          reparticion.setReserva(false);
      }
    }
  }
}
