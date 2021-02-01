package com.egoveris.edt.web.pl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.AccesoDatosException;
import com.egoveris.edt.base.model.TipoEstadoAlertaAviso;
import com.egoveris.edt.base.model.eu.AlertaAvisoDTO;
import com.egoveris.edt.base.model.eu.NotificacionesPorAplicacionDTO;
import com.egoveris.edt.base.service.IAlertaAvisoService;
import com.egoveris.edt.base.service.IArchivoTrabajoService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class AvisosComposer extends BaseComposer {

  private static Logger logger = LoggerFactory.getLogger(AvisosComposer.class);

  private static final long serialVersionUID = -2597519784175366282L;

  private List<NotificacionesPorAplicacionDTO> listaAplicaciones;
  private String cabezera;
  private Listbox listboxAvisos;
  private Auxheader panelAvisosCabezeraId;
  private List<AlertaAvisoDTO> listaAlertaAvisosxModulo;
  private IAlertaAvisoService alertaAvisoService;
  private IArchivoTrabajoService archivoTrabajoService;
  private NotificacionesPorAplicacionDTO moduloSeleccionado;
  private AnnotateDataBinder binder;
  private AlertaAvisoDTO alertaAvisoSeleccionada;
  private Textbox txbx_filtro;
  private Datebox dateboxFiltroDesde;
  private Datebox dateboxFiltroHasta;
  private Button btn_filtro;
  private Boolean btn_filtroActivo;
  private Label labelAvisoTablaAvisos;
  private Button btn_eliminar;
  private Button btn_quitarfiltro;
  private Listbox misAvisosListaModulo;

  @Override
  public void doAfterCompose(Component comp) throws Exception {

    super.doAfterCompose(comp);
    this.listboxAvisos.setVisible(false);
    this.cabezera = panelAvisosCabezeraId.getLabel();
    alertaAvisoService = (IAlertaAvisoService) SpringUtil.getBean("alertaAvisoService");
    archivoTrabajoService = (IArchivoTrabajoService) SpringUtil.getBean("archivoTrabajoService");
    listaAplicaciones = alertaAvisoService
        .obtenerCantidadDeAlertasAvisosNoLeidasPorAplicacion(getUsername());
    listaAlertaAvisosxModulo = new ArrayList<>();
    moduloSeleccionado = new NotificacionesPorAplicacionDTO();
    btn_filtroActivo = false;
    comp.addEventListener(Events.ON_OK, new AvisosListener(this));
    binder = new AnnotateDataBinder(comp);
    binder.loadAll();

  }

  public List<NotificacionesPorAplicacionDTO> getListaAplicaciones() {
    return listaAplicaciones;
  }

  public void onSeleccionarModulo() throws Exception {

    this.btn_filtro.setDisabled(false);
    this.btn_eliminar.setDisabled(false);
    this.btn_quitarfiltro.setDisabled(false);
    panelAvisosCabezeraId.setLabel(
        cabezera + " de " + this.moduloSeleccionado.getAplicacion().getNombreAplicacion());
    refrescarListboxs();
    if (btn_filtroActivo) {
      aplicarFiltro();
    }

  }

  public void onVerDetalle() throws Exception {
    try {
			Utilitarios.closePopUps("win_detalle_alerta_aviso");
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_ALERTA_AVISO_DETALLE, alertaAvisoSeleccionada.getMotivo());
      Window win = (Window) Executions.createComponents("/administrator/detalleAlertaAviso.zul",
          this.self, parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.avisoComponente.winTitle.detalleAlerta"));
      win.setWidth("500px");
      win.setPosition("center");
      win.setVisible(true);
      win.setBorder("normal");
      win.doModal();

      if (alertaAvisoSeleccionada.getEstado().equals(TipoEstadoAlertaAviso.NO_LEIDO.getEstado())) {
        alertaAvisoSeleccionada.setEstado(TipoEstadoAlertaAviso.LEIDO.getEstado());
        alertaAvisoService.guardarAviso(alertaAvisoSeleccionada);
        refrescarListboxs();
      }

    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onRedireccionarNotifiacion() throws InterruptedException {
    try {
			Utilitarios.closePopUps("win_redireccion_alerta_aviso");
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.ALERTA_AVISO_SELECCIONADA, alertaAvisoSeleccionada);
      Window win = (Window) Executions.createComponents("/administrator/redireccionDeAviso.zul",
          this.self, parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.avisoComposer.winTitle.redireccion"));
      win.setWidth("500px");
      win.setPosition("center");
      win.setVisible(true);
      win.setBorder("normal");
      win.doModal();
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public void onEliminarSeleccionados() throws Exception {

    Messagebox.show(Labels.getLabel("eu.avisoComposer.msgbox.eliminarAvisos"),
        Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
        Messagebox.OK | Messagebox.CANCEL, null, new org.zkoss.zk.ui.event.EventListener() {
          @Override
          public void onEvent(Event evt) throws Exception {

            if ("onOK".equals(evt.getName())) {
              for (AlertaAvisoDTO aviso : obtenerAvisosSeleccionados()) {
                alertaAvisoService.eliminarAlerta(aviso);
              }
              refrescarListboxs();
            }
          }
        });
  }

  public List<AlertaAvisoDTO> obtenerAvisosSeleccionados() {
    final List<AlertaAvisoDTO> avisosSeleccionados = new ArrayList<>();
    Set listitems = listboxAvisos.getSelectedItems();
    for (Iterator<Listitem> iterator = listitems.iterator(); iterator.hasNext();) {
      Listitem listitem = iterator.next();

      AlertaAvisoDTO aviso = (AlertaAvisoDTO) listitem.getValue();

      avisosSeleccionados.add(aviso);
    }
    return avisosSeleccionados;
  }

  public void onClick$btn_quitarfiltro() throws Exception {
    dateboxFiltroDesde.setValue(null);
    dateboxFiltroHasta.setValue(null);
    txbx_filtro.setText(StringUtils.EMPTY);
    btn_filtroActivo = false;
    refrescarListboxs();
  }

  public void onClick$btn_filtro() throws Exception {
    if (validarDateboxFecha()) {
      btn_filtroActivo = true;
      aplicarFiltro();
    }

  }

  private void aplicarFiltro() throws AccesoDatosException {

    try {
      listaAlertaAvisosxModulo = alertaAvisoService.obtenerAlertasAvisosPorModuloYUserName(
          moduloSeleccionado.getAplicacion(), getUsername());

      cargarListboxAvisos(obtenerListaFiltrada());

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }

  }

  private List<AlertaAvisoDTO> obtenerListaFiltrada() {

    String parametroDelTexbox = txbx_filtro.getValue().trim();

    return alertaAvisoService.filtrarAlertasAvisosPorMotivoYReferenciaYFecha(parametroDelTexbox,
        moduloSeleccionado.getAplicacion(), getUsername(), dateboxFiltroDesde.getValue(),
        dateboxFiltroHasta.getValue());
  }

  private void cargarListboxAvisos(List<AlertaAvisoDTO> listaAvisos) {
    listaAlertaAvisosxModulo = listaAvisos;

    if (!this.listaAlertaAvisosxModulo.isEmpty()) {
      this.labelAvisoTablaAvisos.setVisible(false);
      this.listboxAvisos.setVisible(true);
      this.listboxAvisos.renderAll();
      this.binder.loadComponent(this.listboxAvisos);

    } else {
      this.labelAvisoTablaAvisos.setVisible(true);
      this.listboxAvisos.setVisible(false);
      if (btn_filtroActivo) {
        this.labelAvisoTablaAvisos.setValue(
            Labels.getLabel("eu.avisosalertas.misAlertas.notificacion.noExistenAvisosConFiltro"));
      } else {
        this.labelAvisoTablaAvisos
            .setValue(Labels.getLabel("eu.avisosalertas.misAlertas.notificacion.noExistenAvisos"));
      }

    }
  }

  public void onEliminarAviso() throws InterruptedException {
    Messagebox.show(Labels.getLabel("eu.avisoComposer.msgbox.elimAvis"),
        Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
        Messagebox.OK | Messagebox.CANCEL, null, new org.zkoss.zk.ui.event.EventListener() {
          @Override
          public void onEvent(Event evt) throws Exception {
            if ("onOK".equals(evt.getName())) {
              alertaAvisoService.eliminarAlerta(alertaAvisoSeleccionada);
              refrescarListboxs();
            }
          }
        });
  }

  private void refrescarListboxs() throws AccesoDatosException {
      listaAplicaciones = alertaAvisoService
          .obtenerCantidadDeAlertasAvisosNoLeidasPorAplicacion(getUsername());
      binder.loadComponent(misAvisosListaModulo);
      if (btn_filtroActivo) {
        aplicarFiltro();

      } else {
        cargarListboxAvisos(alertaAvisoService.obtenerAlertasAvisosPorModuloYUserName(
            moduloSeleccionado.getAplicacion(), getUsername()));
      }
  }

  public void onDescargarDocumento() throws FileNotFoundException, InterruptedException {

    String numeroGedo = alertaAvisoSeleccionada.getNroGedo();
    try {
      if (numeroGedo != null) {
        byte[] contenidoArchivo = archivoTrabajoService.obtenerArchivoTrabajo(numeroGedo,
            getUsername());
        Filedownload.save(contenidoArchivo, null, numeroGedo + ".pdf");
      } else {
        throw new FileNotFoundException();
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(Labels.getLabel("eu.avisoComposer.msgbox.noDescargarArchivo"),
          Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
          Messagebox.ERROR);

    }

  }

  // public byte[] obtenerArchivoTrabajo(String numeroGEDO) {
  // byte[] contenido = null;
  // String url = obtenerUrl(numeroGEDO);
  // url += numeroGEDO + ".pdf";
  // contenido = obtenerArchivoWebDav(url);
  // return contenido;
  // }
  //
  // private String obtenerUrl(String numeroGEDO) {
  // String espacios[] = numeroGEDO.split("-");
  // StringBuilder url = new StringBuilder("");
  // String urlArray[] = { Constantes.CARPETA_RAIZ_DOCUMENTOS, espacios[1],
  // espacios[4], espacios[2].substring(0, 2),
  // espacios[2].substring(2, 5), numeroGEDO };
  // for (String espacio : urlArray)
  // url.append(espacio + "/");
  // return url.toString();
  // }
  //
  // public byte[] obtenerArchivoWebDav(String urlRelativa)
  // throws NegocioException {
  // FileAsStreamConnectionWebDav file = null;
  // byte[] contenido = null;
  // try {
  // file = webDavService.getFileAsStream(urlRelativa, null, null);
  // contenido = IOUtils.toByteArray(file.getFileAsStream());
  // } catch (WebDAVConnectionException wde) {
  // throw new NegocioException("Error obteniendo archivo de WebDav: "
  // + wde.getMessage(), wde);
  // } catch (IOException ioe) {
  // throw new NegocioException(
  // "Error obteniendo contenido del archivo del WebDav: "
  // + ioe.getMessage(), ioe);
  // }
  // return contenido;
  // }

  public boolean validarDateboxFecha() {
    if (dateboxFiltroHasta.getValue() != null && dateboxFiltroDesde.getValue() != null) {
      if (dateboxFiltroHasta.getValue().before(dateboxFiltroDesde.getValue())) {
        throw new WrongValueException(this.dateboxFiltroHasta,
            Labels.getLabel("eu.avisosalertas.misAlertas.filtro.fechaHastaMenorQueDesde"));
      }
    }

    return true;
  }

  public List<AlertaAvisoDTO> getListaAlertaAvisosxModulo() {
    return listaAlertaAvisosxModulo;
  }

  public void setListaAplicaciones(List<NotificacionesPorAplicacionDTO> listaAplicaciones) {
    this.listaAplicaciones = listaAplicaciones;
  }

  public void setListaAlertaAvisosxModulo(List<AlertaAvisoDTO> listaAlertaAvisosxModulo) {
    this.listaAlertaAvisosxModulo = listaAlertaAvisosxModulo;
  }

  public NotificacionesPorAplicacionDTO getModuloSeleccionado() {
    return moduloSeleccionado;
  }

  public void setModuloSeleccionado(NotificacionesPorAplicacionDTO moduloSeleccionado) {
    this.moduloSeleccionado = moduloSeleccionado;
  }

  public AlertaAvisoDTO getAlertaAvisoSeleccionada() {
    return alertaAvisoSeleccionada;
  }

  public void setAlertaAvisoSeleccionada(AlertaAvisoDTO alertaAvisoSeleccionada) {
    this.alertaAvisoSeleccionada = alertaAvisoSeleccionada;
  }

  final class AvisosListener implements EventListener {
    @SuppressWarnings("unused")
    private AvisosComposer composer;

    public AvisosListener(AvisosComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_OK)) {
        refrescarListboxs();
      }
    }

  }
}
