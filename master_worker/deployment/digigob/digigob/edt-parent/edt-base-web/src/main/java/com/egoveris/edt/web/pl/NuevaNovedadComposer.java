package com.egoveris.edt.web.pl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NovedadException;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.CategoriaDTO;
import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.ICategoriaService;
import com.egoveris.edt.base.service.novedad.INovedadHelper;
import com.egoveris.edt.base.service.novedad.INovedadHistService;
import com.egoveris.edt.base.service.novedad.INovedadService;
import com.egoveris.edt.web.admin.pl.renderers.ApLayOut;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.shared.date.DateUtil;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

public class NuevaNovedadComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -6250261717036565696L;

  private static Logger logger = LoggerFactory.getLogger(NuevaNovedadComposer.class);
  private Textbox nombreTextbox;
  private Datebox fechaInicioDatebox;
  private Datebox fechaFinDatebox;
  private List<NovedadDTO> novedades;

  @Autowired
  private List<ApLayOut> aplicacionesLayout;

  private List<CategoriaDTO> categorias;
  private List<AplicacionDTO> aplicaciones;

  @Autowired
  private ApLayOut aplicacionLayOut;

  @Autowired
  private Combobox cbx_categoria;

  @Autowired
  private Listbox cbx_aplicacion;

  private NovedadDTO novedad;

  private CategoriaDTO categoria;

  private String estado;

  @Autowired
  private ICategoriaService categoriaService;

  @Autowired
  private INovedadService novedadService;

  @Autowired
  private IAplicacionService aplicacionService;

  private INovedadHelper novedadHelper;

  String usuario;

  private String[] listaEstados;

  private Boolean esAlta;
  private AnnotateDataBinder binder;

  @Autowired
  private Window win_novedadNueva;

  @Autowired
  private Set<Listitem> selectedItems = new HashSet<>();

  private INovedadHistService novedadHistService;

  private Integer operacion;

  @Override
  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    binder = new AnnotateDataBinder(c);
    this.categoriaService = (ICategoriaService) SpringUtil.getBean("categoriaService");
    this.novedadService = (INovedadService) SpringUtil.getBean("novedadService");
    this.aplicacionService = (IAplicacionService) SpringUtil.getBean("aplicacionesService");

    novedadHistService = (INovedadHistService) SpringUtil.getBean("novedadHistService");
    this.novedadHelper = (INovedadHelper) SpringUtil.getBean("novedadHelper");

    this.novedades = (List<NovedadDTO>) Executions.getCurrent().getArg().get("novedades");
    this.novedad = (NovedadDTO) Executions.getCurrent().getArg().get("novedad");

    this.esAlta = (Boolean) Executions.getCurrent().getArg().get("alta");

    loadComboModulo();

    loadComboCategoria();

    if (this.novedad != null) {
      this.nombreTextbox.setValue(this.novedad.getNovedad());
      this.fechaInicioDatebox.setValue(this.novedad.getFechaInicio());
      this.fechaFinDatebox.setValue(this.novedad.getFechaFin());
      this.novedades.remove(this.novedad);
      categoria = this.novedad.getCategoria();

      for (AplicacionDTO apiNovedad : novedad.getAplicaciones()) {
        for (Iterator it = this.aplicacionesLayout.iterator(); it.hasNext();) {
          ApLayOut ap = ((ApLayOut) it.next());
          if (apiNovedad.getIdAplicacion() == ap.getIdAplicacion()) {
            ap.setEsSeleccionado(true);
          }
        }
      }

      this.binder.loadComponent(cbx_aplicacion);
    }

    c.addEventListener(Events.ON_CHANGE, new NuevaNovedadComposerListener(this));
  }

  private void setearListaSeleccionados() {

    for (Iterator it = this.cbx_aplicacion.getItems().iterator(); it.hasNext();) {
      Listitem l = ((Listitem) it.next());

      for (AplicacionDTO a : this.novedad.getAplicaciones()) {
        if (l.getValue().equals(a.getIdAplicacion())) {
          l.setSelected(true);
          selectedItems.add(l);
        }
      }
    }
    binder.loadComponent(this.cbx_aplicacion);
  }

  public void onClick$refresh() throws InterruptedException {
    setearListaSeleccionados();
  }

  public void onClick$guardar() throws InterruptedException {
    try {
      usuario = (String) Executions.getCurrent().getSession()
          .getAttribute(ConstantesSesion.SESSION_USERNAME);
      validarDatos();

      NovedadDTO savedNovedad = this.novedadService.save(this.novedad);

      this.novedades.add(savedNovedad);

      HashMap<String, Object> hm = new HashMap<>();
      hm.put("novedad", savedNovedad);

      if (esAlta) {
        hm.put("operacion", 0);
        Messagebox.show(
            Labels.getLabel("eu.configuracionNovedades.crearNovedad.alta",
                new String[] { this.novedad.getNovedad() }),
            Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      } else {
        hm.put("operacion", 1);
        Messagebox.show(
            Labels.getLabel("eu.configuracionNovedades.crearNovedad.modifica",
                new String[] { this.novedad.getNovedad() }),
            Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      }
      Events.sendEvent(Events.ON_CHANGE, this.self, hm);

      novedadHelper.cachearListaNovedades();

      this.closeAndNotify();
    } catch (NovedadException e) {
      logger.error(Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.validacion"), e);
      Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Cierra la ventana y notifica a la ventana padre para el refreco de la lista
   */
  private void closeAndNotify() {
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("novedades", this.novedades);
    Events.sendEvent(Events.ON_NOTIFY, this.self.getParent(), hm);
    this.self.detach();
  }

  private void validarDatos() throws NovedadException {
    if (StringUtils.isEmpty(this.nombreTextbox.getValue())) {
      throw new WrongValueException(nombreTextbox,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.IngresarMotivo"));
    }

    if (this.cbx_categoria.getSelectedItem() == null) {
      throw new WrongValueException(cbx_categoria,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.Categoria"));
    }
    Integer categoriaId = (Integer) this.cbx_categoria.getSelectedItem().getValue();
    if (categoriaId == null) {
      throw new WrongValueException(cbx_categoria,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.Categoria"));
    } else {
      categoria = categoriaService.getById(categoriaId);
    }

    Set<Listitem> selectItems = this.cbx_aplicacion.getSelectedItems();

    if (selectItems.isEmpty()) {
      throw new WrongValueException(cbx_aplicacion,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.Modulo"));
    }

    for (Listitem item : selectItems) {
      try {
        aplicacionesLayout.get((Integer) item.getIndex());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw new WrongValueException(cbx_aplicacion,
            Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.Modulo"));
      }
    }

    if (this.cbx_aplicacion.getSelectedItems() == null) {
      throw new WrongValueException(cbx_aplicacion,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.Modulo"));
    }

    if (this.fechaInicioDatebox.getValue() == null) {
      throw new WrongValueException(fechaInicioDatebox,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.IngresarFecha"));
    }

    if (this.fechaFinDatebox.getValue() == null) {
      throw new WrongValueException(fechaFinDatebox,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.IngresarFin"));
    }

    if (this.fechaFinDatebox.getValue().compareTo(this.fechaInicioDatebox.getValue()) < 0) {
      throw new WrongValueException(fechaFinDatebox,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.FechasErrorInicio"));
    }

    if (DateUtil.getZeroTimeDate(this.fechaInicioDatebox.getValue())
            .compareTo(DateUtil.getZeroTimeDate(new Date())) < 0) {
          throw new WrongValueException(fechaInicioDatebox,
              Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.FechasErrorActual"));
    }
    
    
    if (DateUtil.getZeroTimeDate(this.fechaFinDatebox.getValue())
        .compareTo(DateUtil.getZeroTimeDate(new Date())) < 0) {
      throw new WrongValueException(fechaFinDatebox,
          Labels.getLabel("eu.configuracionNovedades.crearNovedad.error.FechasErrorActual"));
    }

    this.armarNovedad();
  }

  private void armarNovedad() {
    if (this.novedad == null) {
      this.novedad = new NovedadDTO();
    }
    this.novedad.setFechaInicio(this.fechaInicioDatebox.getValue());
    this.novedad.setFechaFin(this.fechaFinDatebox.getValue());
    this.novedad.setNovedad(this.nombreTextbox.getValue());
    this.novedad.setCategoria(categorias.get(this.cbx_categoria.getSelectedIndex()));

    Set<AplicacionDTO> nuevaAplicaciones = new HashSet<>();

    Set<Listitem> selectItems = this.cbx_aplicacion.getSelectedItems();
    for (Listitem item : selectItems) {
      ApLayOut aL = aplicacionesLayout.get((Integer) item.getIndex());
      AplicacionDTO ap = aplicacionService.getAplicacionPorId(aL.getIdAplicacion());
      nuevaAplicaciones.add(ap);
    }
    this.novedad.setAplicaciones(nuevaAplicaciones);
    this.novedad.setUsuario(usuario);

    boolean desde = this.novedad.getFechaInicio().before(new Date());

    boolean hasta = this.novedad.getFechaFin().after(new Date());

    if (desde && hasta) {
      this.novedad.setEstado("ACTIVO");
    } else {
      this.novedad.setEstado("PENDIENTE");
    }
  }

  public void onClick$cancelar() throws InterruptedException {
    if (this.novedad != null) {
      this.novedades.add(this.novedad);
    }
    Messagebox.show(Labels.getLabel("eu.nuevoFeriadoComposer.msgBox.datosSePerderan"),
        Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
        new org.zkoss.zk.ui.event.EventListener() {

          @Override
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              cerrar();
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  private boolean validaModificacion() {

    if (this.novedad == null) {
      return false;
    }
    Integer categoriaIdOriginal = this.novedad.getCategoria().getId();

    if (this.cbx_categoria.getSelectedItem() == null) {
      return false;
    } else {
      Integer categoriaIdSelect = (Integer) this.cbx_categoria.getSelectedItem().getValue();

      if (!categoriaIdSelect.equals(categoriaIdOriginal)) {
        return true;
      }
    }

    Set<Listitem> selectItems = this.cbx_aplicacion.getSelectedItems();

    Set<AplicacionDTO> originales = this.novedad.getAplicaciones();

    // Esta validacion se realiza para saber si se modifico o no la lista de
    // aplicaciones
    for (Listitem item : selectItems) {
      boolean existe = false;
      for (AplicacionDTO app : originales) {
        Integer appSeleccionada = (Integer) item.getValue();
        if (appSeleccionada.equals(app.getIdAplicacion())) {
          existe = true;
        }
      }

      if (!existe) {
        return existe;
      }
    }

    if (this.fechaInicioDatebox == null || this.fechaFinDatebox == null) {
      return true;
    }

    try {
      if (StringUtils.isEmpty(this.nombreTextbox.getValue())) {
        return true;
      } else {
        if (!this.nombreTextbox.getValue().equals(this.novedad.getNovedad())) {
          return true;
        }
      }

      if (this.fechaInicioDatebox.getValue() == null
          || !this.fechaInicioDatebox.getValue().equals(this.novedad.getFechaInicio())) {
        return true;
      }
      if (this.fechaFinDatebox.getValue() == null
          || !this.fechaFinDatebox.getValue().equals(this.novedad.getFechaFin())) {
        return true;
      }
    } catch (WrongValueException ex) {
      logger.error(ex.getMessage(), ex);
      return true;
    }

    return false;
  }

  private void loadComboCategorias(final CategoriaDTO categoria) {
    cbx_categoria.setModel(new ListModelArray(this.categoriaService.getAll()));
    cbx_categoria.setItemRenderer(new ComboitemRenderer() {

      @Override
      public void render(Comboitem item, Object data, int arg2) throws Exception {
        CategoriaDTO cat = (CategoriaDTO) data;
        item.setLabel(cat.getCategoriaNombre());
        item.setValue(cat.getId());

        if (categoria != null && cat.getId().equals(categoria.getId())) {
          cbx_categoria.setSelectedItem(item);
        }
      }
    });
  }

  private void loadComboCategoria() {
    setCategorias(this.categoriaService.getAll());
    this.cbx_categoria.setModel(new ListModelArray(categorias));
    this.binder.loadComponent(cbx_categoria);
  }

  private void loadComboModulo() {
    aplicaciones = this.aplicacionService.getAll();
    aplicacionesLayout = TransformerAplicacion.transformarAplicacionALayout(aplicaciones);
    this.cbx_aplicacion.setModel(new ListModelArray(aplicacionesLayout));
    this.binder.loadComponent(cbx_aplicacion);
  }

  private void cerrar() {
    this.self.detach();
  }

  public NovedadDTO getNovedad() {
    return novedad;
  }

  public void setNovedad(NovedadDTO novedad) {
    this.novedad = novedad;
  }

  public CategoriaDTO getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaDTO categoria) {
    this.categoria = categoria;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String[] getListaEstados() {
    return listaEstados;
  }

  public void setListaEstados(String[] listaEstados) {
    this.listaEstados = listaEstados;
  }

  public Listbox getCbx_aplicacion() {
    return cbx_aplicacion;
  }

  public void setCbx_aplicacion(Listbox cbx_aplicacion) {
    this.cbx_aplicacion = cbx_aplicacion;
  }

  public Combobox getCbx_categoria() {
    return cbx_categoria;
  }

  public void setCbx_categoria(Combobox cbx_categoria) {
    this.cbx_categoria = cbx_categoria;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public void setCategorias(List<CategoriaDTO> categorias) {
    this.categorias = categorias;
  }

  public List<CategoriaDTO> getCategorias() {
    return categorias;
  }

  public void setAplicaciones(List<AplicacionDTO> aplicaciones) {
    this.aplicaciones = aplicaciones;
  }

  public List<AplicacionDTO> getAplicaciones() {
    return aplicaciones;
  }

  public Set getSelectedItems() {
    return selectedItems;
  }

  public void setSelectedItems(Set selectedItems) {
    this.selectedItems = selectedItems;
  }

  public void setWin_novedadNueva(Window win_novedadNueva) {
    this.win_novedadNueva = win_novedadNueva;
  }

  public Window getWin_novedadNueva() {
    return win_novedadNueva;
  }

  public List<ApLayOut> getAplicacionesLayout() {
    return aplicacionesLayout;
  }

  public void setAplicacionesLayout(List<ApLayOut> aplicacionesLayout) {
    this.aplicacionesLayout = aplicacionesLayout;
  }

  public void setAplicacionLayOut(ApLayOut aplicacionLayOut) {
    this.aplicacionLayOut = aplicacionLayOut;
  }

  public ApLayOut getAplicacionLayOut() {
    return aplicacionLayOut;
  }

  final class NuevaNovedadComposerListener implements EventListener {
    @SuppressWarnings("unused")
    private NuevaNovedadComposer composer;

    private INovedadHistService novedadHistService;

    public NuevaNovedadComposerListener(NuevaNovedadComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {

      this.novedadHistService = (INovedadHistService) SpringUtil.getBean("novedadHistService");

      if (event.getName().equals(Events.ON_CHANGE)) {
        Map<String, Object> mapa = (Map<String, Object>) event.getData();
        novedadHistService.guardarHistorico((NovedadDTO) mapa.get("novedad"),
            (Integer) mapa.get("operacion"));
      }
    }

  }

  final static class TransformerAplicacion {

    private static List<ApLayOut> transformarAplicacionALayout(List<AplicacionDTO> aplicaciones) {

      List<ApLayOut> listOut = new ArrayList<>();

      for (AplicacionDTO ap : aplicaciones) {
        ApLayOut a = new ApLayOut();
        a.setEsSeleccionado(false);
        a.setIdAplicacion(ap.getIdAplicacion());
        a.setNombreAplicacion(ap.getNombreAplicacion());
        listOut.add(a);
      }

      return listOut;
    }

  }
}