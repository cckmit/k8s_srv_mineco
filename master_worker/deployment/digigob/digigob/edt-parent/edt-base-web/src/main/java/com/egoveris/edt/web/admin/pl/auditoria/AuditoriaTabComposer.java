package com.egoveris.edt.web.admin.pl.auditoria;

import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.base.util.zk.ZkUtil;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;
import com.egoveris.sharedsecurity.base.service.IUsuarioReparticionHabilitadaService;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

public class AuditoriaTabComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 3914594834834941551L;

  private AnnotateDataBinder binder;

  @Autowired
  public IDatosUsuarioService datosUsuarioService;

  private DatosUsuarioDTO dato;

  private Tab datosPersonales;
  private Tab repaHabilitadas;
  private Tab repaAdministradas;

  private Boolean repaHabilitadasLoad;
  private Boolean repaAdministradasLoad;

  
  private IAdminReparticionService adminReparticionService;

  private IUsuarioReparticionHabilitadaService usuarioReparticionHabilitadaService;

  private List<AdminReparticionDTO> listaAdminReparticion = new ArrayList<AdminReparticionDTO>();

  private List<UsuarioReparticionHabilitadaDTO> listaAdminHabilitada = new ArrayList<UsuarioReparticionHabilitadaDTO>();

  private Window auditoriaUsuarioTab;

  private UsuarioBaseDTO usuario;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {

    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);

    usuario = (UsuarioBaseDTO) Executions.getCurrent().getAttribute(ConstantesSesion.KEY_USUARIO);

    this.datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");

    this.adminReparticionService = (IAdminReparticionService) SpringUtil
        .getBean("adminReparticionService");

    this.usuarioReparticionHabilitadaService = (IUsuarioReparticionHabilitadaService) SpringUtil
        .getBean("usuarioReparticionHabilitadaService");

    binder.loadAll();
  }

  public void onClick$btnCerrar() {
    this.self.detach();
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public IDatosUsuarioService getDatosUsuarioService() {
    return datosUsuarioService;
  }

  public void setDatosUsuarioService(IDatosUsuarioService datosUsuarioService) {
    this.datosUsuarioService = datosUsuarioService;
  }

  public DatosUsuarioDTO getDato() {
    return dato;
  }

  public void setDato(DatosUsuarioDTO dato) {
    this.dato = dato;
  }

  public Tab getRepaAdministradas() {
    return repaAdministradas;
  }

  public void setRepaAdministradas(Tab repaAdministradas) {
    this.repaAdministradas = repaAdministradas;
  }

  public Tab getRepaHabilitadas() {
    return repaHabilitadas;
  }

  public void setRepaHabilitadas(Tab repaHabilitadas) {
    this.repaHabilitadas = repaHabilitadas;
  }

  public Tab getDatosPersonales() {
    return datosPersonales;
  }

  public void setDatosPersonales(Tab datosPersonales) {
    this.datosPersonales = datosPersonales;
  }

  public Boolean getRepaAdministradasLoad() {
    return repaAdministradasLoad;
  }

  public void setRepaAdministradasLoad(Boolean repaAdministradasLoad) {
    this.repaAdministradasLoad = repaAdministradasLoad;
  }

  public Boolean getRepaHabilitadasLoad() {
    return repaHabilitadasLoad;
  }

  public void setRepaHabilitadasLoad(Boolean repaHabilitadasLoad) {
    this.repaHabilitadasLoad = repaHabilitadasLoad;
  }

  public IAdminReparticionService getAdminReparticionService() {
    return adminReparticionService;
  }

  public void setAdminReparticionService(IAdminReparticionService adminReparticionService) {
    this.adminReparticionService = adminReparticionService;
  }

  public IUsuarioReparticionHabilitadaService getUsuarioReparticionHabilitadaService() {
    return usuarioReparticionHabilitadaService;
  }

  public void setUsuarioReparticionHabilitadaService(
      IUsuarioReparticionHabilitadaService usuarioReparticionHabilitadaService) {
    this.usuarioReparticionHabilitadaService = usuarioReparticionHabilitadaService;
  }

  /**********************************/
  public void onSelect$tabAuditarRepaAdministradas() {
    Window hijo = ZkUtil.findById(auditoriaUsuarioTab, "win_auditoriaRepaAdm");
    Events.sendEvent(hijo, new Event(Events.ON_CHECK));
    this.binder.loadAll();
  }

  public void onSelect$tabAuditarRepaHabilitadas() {
    Window hijo = ZkUtil.findById(auditoriaUsuarioTab, "win_auditoriaRepaHab");
    Events.sendEvent(hijo, new Event(Events.ON_CHECK));
    this.binder.loadAll();
  }

  public List<AdminReparticionDTO> getListaAdminReparticion() {
    return listaAdminReparticion;
  }

  public void setListaAdminReparticion(List<AdminReparticionDTO> listaAdminReparticion) {
    this.listaAdminReparticion = listaAdminReparticion;
  }

  public List<UsuarioReparticionHabilitadaDTO> getListaAdminHabilitada() {
    return listaAdminHabilitada;
  }

  public void setListaAdminHabilitada(List<UsuarioReparticionHabilitadaDTO> listaAdminHabilitada) {
    this.listaAdminHabilitada = listaAdminHabilitada;
  }

  public UsuarioBaseDTO getUsuario() {
    return usuario;
  }

  public void setUsuario(UsuarioBaseDTO usuario) {
    this.usuario = usuario;
  }

  public Window getAuditoriaUsuarioTab() {
    return auditoriaUsuarioTab;
  }

  public void setAuditoriaUsuarioTab(Window auditoriaUsuarioTab) {
    this.auditoriaUsuarioTab = auditoriaUsuarioTab;
  }

  public static void export_to_csv(Listbox listbox, String nomberArchivo) {
    DateFormat df = new SimpleDateFormat("MMddyyyy_HHmmss");

    // Get the date today using Calendar object.
    Date today = Calendar.getInstance().getTime();
    // Using DateFormat format method we can create a string
    // representation of a date with the defined format.
    String reportDate = df.format(today);

    // Print what date is today!
    System.out.println("Report Date: " + reportDate);

    String s = ";";
    StringBuilder sb = new StringBuilder();

    for (Object head : listbox.getHeads()) {
      String h = "";
      for (Object header : ((Listhead) head).getChildren()) {
        h += ((Listheader) header).getLabel() + s;
      }
      sb.append(h + "\n");
    }
    for (Object item : listbox.getItems()) {
      String i = "";
      for (Object cell : ((Listitem) item).getChildren()) {
        i += ((Listcell) cell).getLabel() + s;
      }
      sb.append(i + "\n");
    }
    try {
      Filedownload.save(sb.toString().getBytes("ISO-8859-1"), "text/plain",
          nomberArchivo.concat(reportDate) + ".csv");
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      logger.info(e.getMessage(), e);
    }
  }
}
