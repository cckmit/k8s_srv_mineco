package com.egoveris.te.base.rendered;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.UtilsDate;
import com.egoveris.te.base.util.VisualizacionActividadPopup;

import java.text.SimpleDateFormat;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;



public class ActivExpItemRender implements ListitemRenderer {
	 
	private UsuariosSADEService usuariosSADEService = (UsuariosSADEService) SpringUtil
			.getBean("usuariosSADEServiceImpl");

  public void render(Listitem item, Object data, int arg1) {

    final ActividadInbox data1 = (ActividadInbox) data;

    if ((data1.getEstado().equals(ConstEstadoActividad.ESTADO_CERRADA)
        || data1.getEstado().equals(ConstEstadoActividad.ESTADO_PENDIENTE_VINCULAR))
        && "MASIVOMANUAL".equals(data1.getUsuarioCierre()) && data1.getFechaBaja() != null) {
      item.setVisible(false);
      return;
    }
    final Component window = item.getParent().getParent();
    // Nro Exp
    Listcell currentCell = new Listcell(data1.getNroExpediente());
    currentCell.setParent(item);

    // actividad
    currentCell = new Listcell(data1.getTipoActividadDecrip());
    currentCell.setParent(item);

    // fechas
    cellsFechas(item, data1); 
    Usuario usuario = this.usuariosSADEService.obtenerUsuarioActual();
    // se obtiene el usario actual
    //Usuario usuarioProductorInfo = usuariosSADEService.getDatosUsuario(Executions.getCurrent().getRemoteUser());
    // set usuarioActual
    data1.setUsuarioActual(usuario.getEmail());
    cellsExtra(item, data1);
    this.permisoParaModificar();
    // estado
    if (data1.getEstado() != null && ConstEstadoActividad.ESTADO_ABIERTA.equals(data1.getEstado())
        && (ConstantesWeb.TIPO_ACTIVIDIDAD_RESULTADO_TAD
            .equalsIgnoreCase(data1.getTipoActividadDecrip())
            || ConstantesWeb.TIPO_ACTIVIDAD_APROBACION_SUBSANACION_TAD
                .equalsIgnoreCase(data1.getTipoActividadDecrip()))) {
      currentCell = new Listcell("COMPLETADA");
    } else {
      currentCell = new Listcell(data1.getEstado());
    }
    currentCell.setParent(item);

    // trata
    currentCell = new Listcell(data1.getTrata());
    currentCell.setParent(item);
    
    // accion
    currentCell = new Listcell();
    currentCell.setParent(item);
    if (data1.getForm() != null) {
      Label label;
      if ("ABIERTA".equalsIgnoreCase(data1.getEstado()) && (data1.getMailCreador() == null ||(data1.getUsuarioActual().equals(data1.getMailCreador())))) {
        label = new Label(Labels.getLabel("ee.inbox.ejecutar"));
      } else {
        label = new Label(Labels.getLabel("ee.inbox.ver"));
      }
      Hbox hbox = new Hbox();
      Image runImage = new Image("/imagenes/play.png");
      runImage.setParent(hbox);
      label.setParent(hbox);
      hbox.setParent(currentCell);
      hbox.addEventListener(Events.ON_CLICK, new EventListener() {
        @Override
        public void onEvent(Event event) throws Exception {
          VisualizacionActividadPopup.show(window, data1, null, permisoParaModificar());
        }
      });
    }
  }

  protected boolean permisoParaModificar() {
    return true;
  } 

  protected void cellsExtra(Listitem item, ActividadInbox data1) {
    //
  }

  protected void cellsFechas(Listitem item, final ActividadInbox data1) {
    // fecha alta
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    Listcell currentCell = new Listcell(sdf.format(data1.getFechaAlta()));
    currentCell.setParent(item);
    // Creado desde
    int cantDias = UtilsDate.diasEntreHoyYOtraFecha(data1.getFechaAlta(), true);

    String dias;
    switch (cantDias) {
    case 0:
      dias = Labels.getLabel("ee.act.dias.hoy");
      break;
    case 1:
      dias = Labels.getLabel("ee.act.dias.ayer");
      break;
    default:
      dias = Labels.getLabel("ee.act.dias", new Object[] { cantDias });
      break;
    }

    currentCell = new Listcell(dias);
    currentCell.setParent(item);
  }
  
  
  
}
