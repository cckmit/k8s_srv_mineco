package com.egoveris.edt.web.pl.renderers;

import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ConfiguracionAplicacionesRenderer implements ListitemRenderer {

  private Session csession;

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    String userName = Executions.getCurrent().getRemoteUser();
    csession = Executions.getCurrent().getDesktop().getSession();
    AplicacionDTO aplicacion = (AplicacionDTO) data;

    Checkbox checkTareas = new Checkbox();
    Checkbox checkSistemas = new Checkbox();
    Checkbox checkSubordinados = new Checkbox();
    Checkbox checkBuzon = new Checkbox();

    checkTareas.setChecked(getCheckedMisTareas(userName, aplicacion.getIdAplicacion()));
    checkTareas.setVisible(aplicacion.isVisibleMisTareas());
    checkSistemas.setChecked(getCheckedMisSistemas(userName, aplicacion.getIdAplicacion()));
    checkSistemas.setVisible(aplicacion.isVisibleMisSistemas());
    checkSubordinados
        .setChecked(getCheckedMisSubordinados(userName, aplicacion.getIdAplicacion()));
    checkSubordinados.setVisible(aplicacion.isVisibleMisSupervisados());
    checkBuzon.setChecked(getCheckedVisibleBuzonTareas(userName, aplicacion.getIdAplicacion()));
    checkBuzon.setVisible(aplicacion.isVisibleBuzonGrupal());

    String nombreAplicacion = aplicacion.getNombreAplicacion();

    Listcell listcell = new Listcell(nombreAplicacion);
    listcell.setParent(item);
    listcell.setTooltiptext(aplicacion.getDescripcionAplicacion());

    Listcell tareas = new Listcell();
    tareas.setParent(item);
    checkTareas.setParent(tareas);
    checkTareas.setId("chktarea_" + nombreAplicacion);

    Listcell sistemas = new Listcell();
    sistemas.setParent(item);
    checkSistemas.setParent(sistemas);
    checkTareas.setId("chksistema_" + nombreAplicacion);

    Listcell subordinados = new Listcell();
    subordinados.setParent(item);
    checkSubordinados.setParent(subordinados);
    checkTareas.setId("chksubordinado_" + nombreAplicacion);

    Listcell buzon = new Listcell();
    buzon.setParent(item);
    checkBuzon.setParent(buzon);
    checkBuzon.setId("chkbuzon_" + nombreAplicacion);
  }

  public boolean getCheckedMisTareas(String userName, int idAplicacion) {

    List<Integer> listaAplicacionesIdMisTareas = (List<Integer>) csession
        .getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_TAREAS);

    if (listaAplicacionesIdMisTareas.contains(idAplicacion)) {
      return true;
    }

    return false;
  }

  public boolean getCheckedMisSistemas(String userName, int idAplicacion) {

    List<Integer> listaAplicacionesIdMisSistemas = (List<Integer>) csession
        .getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SISTEMAS);

    if (listaAplicacionesIdMisSistemas != null
        && listaAplicacionesIdMisSistemas.contains(idAplicacion)) {
      return true;
    }

    return false;
  }

  public boolean getCheckedMisSubordinados(String userName, int idAplicacion) {
    List<Integer> listaAplicacionesIdMisSupervisados = (List<Integer>) csession
        .getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_SUPERVISADOS);

    if (listaAplicacionesIdMisSupervisados != null
        && listaAplicacionesIdMisSupervisados.contains(idAplicacion)) {
      return true;
    }

    return false;
  }

  public boolean getCheckedVisibleBuzonTareas(String userName, int idAplicacion) {
    Map<Integer, List<String>> listaAplicacionesIdBuzon;
    listaAplicacionesIdBuzon = (Map<Integer, List<String>>) csession
        .getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_BUZONGRUPAL_TAREAS);

    boolean esta = false;
    if (listaAplicacionesIdBuzon != null && listaAplicacionesIdBuzon.containsKey(idAplicacion)) {
      esta = true;
    }

    return esta;
  }

}
