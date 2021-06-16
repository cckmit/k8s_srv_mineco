package com.egoveris.deo.web.satra;

import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InicioDocumentoFamiliaTipoDocumentoBandBoxComposer extends GenericForwardComposer {

  private static final String PUNTOS_SUSPENSIVOS = "...";
  private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_DESC = 23;
  private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE = 21;
  private static final long serialVersionUID = 1081323236289527265L;

  public Tree familiaTipoTree;
  private AnnotateDataBinder binder;
  private TreeModel treeModel;
  private ArrayList<TipoDocumentoDTO> listaFamiliasSeleccionadas;

  private ArrayList<TipoDocumentoDTO> listaFamiliasTotal; 
  private Textbox textoTipoDocumento;
  protected Bandbox familiaEstructuraTree;
  
  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  
  private static transient Logger logger = LoggerFactory
      .getLogger(InicioDocumentoFamiliaTipoDocumentoBandBoxComposer.class);

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    logger.info("comboIncioDoc - inicio");
    comp.addEventListener(Events.ON_NOTIFY,
        new InicioDocumentoFamiliaTipoDocumentoBandBoxComposerListener(this));
    this.familiaTipoTree.setTreeitemRenderer(new FamiliaTipoItemRender());
    this.binder = new AnnotateDataBinder(this.familiaTipoTree);
    cargarFamilias();
    this.binder.loadAll();
    logger.info("comboIncioDoc - fin");
  }

  private void cargarFamilias() {

    List<DefaultTreeNode<FamiliaTipoDocumentoDTO>> listaEstructuradaTree = new ArrayList<>();
    List<FamiliaTipoDocumentoDTO> listaFamilias;
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("manual", true);
    parametros.put("estado", "ALTA");

    if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
      parametros.put("comunicable", true);
      listaFamilias = this.tipoDocumentoService.buscarTodasLasFamiliasByComunicable(parametros);
    } else {
      listaFamilias = this.tipoDocumentoService.buscarTodasLasFamilias();
    }
    listaEstructuradaTree.clear();
    List<TipoDocumentoDTO> listaTiposDoc;

    for (FamiliaTipoDocumentoDTO familia : listaFamilias) {
      parametros.put("familia", familia.getId());

      if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
        listaTiposDoc = this.tipoDocumentoService
            .buscarTipoDocumentoByFamiliayComunicable(parametros);
      } else {
        listaTiposDoc = this.tipoDocumentoService.buscarTipoDocumentoByFamilia(parametros);
      }

      if (!listaTiposDoc.isEmpty()) {
        List<DefaultTreeNode<FamiliaTipoDocumentoDTO>> listaNodos = new ArrayList<>(
            listaTiposDoc.size());

        for (TipoDocumentoDTO defaultTreeNode : listaTiposDoc) {
          listaNodos.add(new DefaultTreeNode(defaultTreeNode));
        }
        listaEstructuradaTree
            .add(new DefaultTreeNode<FamiliaTipoDocumentoDTO>(familia, listaNodos));
      }

      parametros.remove("familia");
    }
    this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT", listaEstructuradaTree));
    this.familiaTipoTree.setModel(this.treeModel);
    this.binder.loadComponent(familiaTipoTree);
  }

  private void cargarFamiliasPorAcronimo(ArrayList<FamiliaTipoDocumentoDTO> familias,
      String acronimo, String ingreso) {

    List<DefaultTreeNode> listaEstructuradaTree = new ArrayList<DefaultTreeNode>();
    listaEstructuradaTree.clear();
    List<TipoDocumentoDTO> listaTiposDoc;
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("manual", true);
    parametros.put("estado", "ALTA");
    parametros.put("acronimo", acronimo);
    parametros.put("nombre", ingreso);
    parametros.put("descripcion", ingreso);

    for (FamiliaTipoDocumentoDTO familia : familias) {
      parametros.put("familia", familia.getId());

      if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
        parametros.put("comunicable", true);
        listaTiposDoc = this.tipoDocumentoService
            .buscarTipoDocumentoByFamiliaAcronimoYComunicable(parametros);
      } else {
        listaTiposDoc = this.tipoDocumentoService
            .buscarTipoDocumentoByFamiliaYAcronimo(parametros);
      }

      if (!listaTiposDoc.isEmpty()) {
        List<DefaultTreeNode<FamiliaTipoDocumentoDTO>> listaNodos = new ArrayList<>(
            listaTiposDoc.size());
        for (TipoDocumentoDTO defaultTreeNode : listaTiposDoc) {
          listaNodos.add(new DefaultTreeNode(defaultTreeNode));
        }
        listaEstructuradaTree.add(new DefaultTreeNode(familia, listaNodos));
      }

      parametros.remove("familia");

    }
    this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT", listaEstructuradaTree));
    this.familiaTipoTree.setModel(this.treeModel);
    this.binder.loadComponent(familiaTipoTree);
  }

  private void cargarFamiliasTodosLosEstados(ArrayList<FamiliaTipoDocumentoDTO> familias) {

    List<DefaultTreeNode> listaEstructuradaTree = new ArrayList<DefaultTreeNode>();
    listaEstructuradaTree.clear();
    List<TipoDocumentoDTO> listaTiposDoc;
    Map<String, Object> parametros = new HashMap<>();

    for (FamiliaTipoDocumentoDTO familia : familias) {
      parametros.put("familia", familia.getId());

      if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
        listaTiposDoc = this.tipoDocumentoService
            .buscarTipoDocumentoSinFiltroByFamiliayComunicable(parametros);
      } else {
        listaTiposDoc = this.tipoDocumentoService
            .buscarTipoDocumentoSinFiltroByFamilia(parametros);
      }
      if (!listaTiposDoc.isEmpty()) {
        List<DefaultTreeNode<FamiliaTipoDocumentoDTO>> listaNodos = new ArrayList<>(
            listaTiposDoc.size());
        for (TipoDocumentoDTO defaultTreeNode : listaTiposDoc) {
          listaNodos.add(new DefaultTreeNode(defaultTreeNode));
        }
        listaEstructuradaTree.add(new DefaultTreeNode(familia, listaNodos));
      }
      parametros.remove("familia");

    }
    this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT", listaEstructuradaTree));
    this.familiaTipoTree.setModel(this.treeModel);
    this.binder.loadComponent(familiaTipoTree);
  }

  private Treecell obtenerCaracteristicasTipoDocumento(TipoDocumentoDTO repa, Treerow tr)
      throws IOException {
    Treecell tc = new Treecell();

    Image imagenDocumentoEspecial;
    Image imagenDocumentoFirmaConToken;
    Image imagenDocumentoProduccion;
    Image imagenDocumentoFirmaConjunta;
    Image imagenDocumentoFirmaExterna;
    Image imagenDocumentoReservado;
    Image imagenDocumentoNotificable;

    if (repa.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_LIBRE);
      imagenDocumentoProduccion
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.libre"));
      tc.appendChild(imagenDocumentoProduccion);
    } else if (repa.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_IMPORTADO);
      imagenDocumentoProduccion
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.importado"));
      tc.appendChild(imagenDocumentoProduccion);
    } else if (repa.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_TEMPLATE);
      imagenDocumentoProduccion
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.template"));
      tc.appendChild(imagenDocumentoProduccion);
    } else if (repa.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(Constantes.IMG_TIENE_IMPORTADO_TEMPLATE);
      imagenDocumentoProduccion.setTooltiptext(
          Labels.getLabel("gedo.general.imagenesCaracteristicas.importadoTemplate"));
    }

    if (repa.getEsEspecial().equals(Boolean.TRUE)) {
      imagenDocumentoEspecial = new Image();
      imagenDocumentoEspecial.setSrc(Constantes.IMG_ES_ESPECIAL);
      imagenDocumentoEspecial
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.especial"));
      tc.appendChild(imagenDocumentoEspecial);
    }

    if (repa.getEsFirmaExterna().equals(Boolean.TRUE)) {
      imagenDocumentoFirmaExterna = new Image();
      imagenDocumentoFirmaExterna.setSrc(Constantes.IMG_ES_FIRMA_EXTERNA);
      imagenDocumentoFirmaExterna
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.firmaExterna"));
      tc.appendChild(imagenDocumentoFirmaExterna);
    }

    if (repa.getEsConfidencial().equals(Boolean.TRUE)) {
      imagenDocumentoReservado = new Image();
      imagenDocumentoReservado.setSrc(Constantes.IMG_ES_CONFIDENCIAL);
      imagenDocumentoReservado
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.reservado"));
      tc.appendChild(imagenDocumentoReservado);
    }

    if (repa.getEsNotificable().equals(Boolean.TRUE)) {
      imagenDocumentoNotificable = new Image();
      imagenDocumentoNotificable.setSrc(Constantes.IMG_ES_NOTIFICABLE);
      imagenDocumentoNotificable
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.notificable"));
      tc.appendChild(imagenDocumentoNotificable);
    }

    if (repa.getTieneToken().equals(Boolean.TRUE)) {
      imagenDocumentoFirmaConToken = new Image();
      imagenDocumentoFirmaConToken.setSrc(Constantes.IMG_TIENE_TOKEN);
      imagenDocumentoFirmaConToken
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.token"));
      tc.appendChild(imagenDocumentoFirmaConToken);
    }

    if (repa.getEsFirmaConjunta().equals(Boolean.TRUE)) {
      imagenDocumentoFirmaConjunta = new Image();
      imagenDocumentoFirmaConjunta.setSrc(Constantes.IMG_ES_FIRMA_CONJUNTA);
      imagenDocumentoFirmaConjunta
          .setTooltiptext(Labels.getLabel("gedo.general.imagenesCaracteristicas.firmaConjunta"));
      tc.appendChild(imagenDocumentoFirmaConjunta);
    }
    return tc;
  }

  public void onSelect$familiaTipoTree() throws InterruptedException {
    String codigoId = familiaTipoTree.getSelectedItem().getTreerow().getId();
    
    if (codigoId != null && !"".equals(codigoId.trim())) {
	  TipoDocumentoDTO selecDocumArbol = buscarDocumento(codigoId);
      this.textoTipoDocumento.setText(null);
      this.familiaEstructuraTree.close();
      Event event = new Event(Events.ON_NOTIFY, this.self.getParent(), selecDocumArbol);
      Events.sendEvent(event);
    }
  }

  private TipoDocumentoDTO buscarDocumento(String acronimo) {
    return this.tipoDocumentoService.buscarTipoDocumentoByAcronimo(acronimo);
  }

  public void onChanging$familiaEstructuraTree(InputEvent e) {
    this.familiaEstructuraTree.setValue(e.getValue());
    this.textoTipoDocumento.setText(e.getValue());
    this.onChanging$textoTipoDocumento(e);
  }

  public void onChanging$textoTipoDocumento(InputEvent e) {
    this.cargarFamiliasFiltradas(e);
  }

  public void cargarFamiliasFiltradas(InputEvent e) {
    String matchingText = e.getValue();
    if (!"".equals(matchingText) && (matchingText.length() >= 2)) {
      List<FamiliaTipoDocumentoDTO> listaFamilias;
      String acronimo = "%" + matchingText.toUpperCase() + "%";
      String sinAcento = StringUtils.stripAccents(acronimo);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("manual", true);
      parametros.put("estado", "ALTA");
      parametros.put("acronimo", acronimo);
      parametros.put("nombre", sinAcento);
      parametros.put("descripcion", sinAcento);

      if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
        parametros.put("comunicable", true);
        listaFamilias = this.tipoDocumentoService
            .buscarFamiliaPorTipoDocumentoYComunicable(parametros);
      } else {
        listaFamilias = this.tipoDocumentoService.buscarFamiliaPorTipoDocumento(parametros);
      }

      this.cargarFamiliasPorAcronimo((ArrayList<FamiliaTipoDocumentoDTO>) listaFamilias, acronimo,
          sinAcento);
      this.binder.loadComponent(familiaTipoTree);
    } else if ("".equals(matchingText)) {
      this.cargarFamilias();
      this.binder.loadComponent(familiaTipoTree);
    }
  }

  public void recargarTiposDeDocumentos(boolean todosLosEstados) {
    if (todosLosEstados) {

      if (Executions.getCurrent().getDesktop().getSession().getAttribute("CCOO") != null) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("comunicable", true);
        cargarFamiliasTodosLosEstados(
            (ArrayList) tipoDocumentoService.buscarFamiliasByComunicable(parametros));
      } else {
        cargarFamiliasTodosLosEstados((ArrayList) tipoDocumentoService.buscarFamilias());
      }
    } else {
      cargarFamilias();
    }
    this.textoTipoDocumento.setText(null);
    this.binder = new AnnotateDataBinder(this.familiaTipoTree);
    this.binder.loadAll();
  }

  public Tree getFamiliaTipoTree() {
    return familiaTipoTree;
  }

  public void setFamiliaTipoTree(Tree familiaTipoTree) {
    this.familiaTipoTree = familiaTipoTree;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  public List<TipoDocumentoDTO> getListaFamiliasSeleccionadas() {
    return listaFamiliasSeleccionadas;
  }

  public void setListaFamiliasSeleccionadas(
      ArrayList<TipoDocumentoDTO> listaFamiliasSeleccionadas) {
    this.listaFamiliasSeleccionadas = listaFamiliasSeleccionadas;
  }

  public ArrayList<TipoDocumentoDTO> getListaFamiliasTotal() {
    return listaFamiliasTotal;
  }

  public void setListaFamiliasTotal(ArrayList<TipoDocumentoDTO> listaFamiliasTotal) {
    this.listaFamiliasTotal = listaFamiliasTotal;
  }

  public Textbox getTextoTipoDocumento() {
    return textoTipoDocumento;
  }

  public void setTextoTipoDocumento(Textbox textoTipoDocumento) {
    this.textoTipoDocumento = textoTipoDocumento;
  }

  public void onBlur$familiaEstructuraTree() {
    this.familiaEstructuraTree.setText(this.familiaEstructuraTree.getValue().toUpperCase());
    if (!"".equals(this.familiaEstructuraTree.getValue().trim())) {
      TipoDocumentoDTO tipoDocumento = this.tipoDocumentoService
          .buscarTipoDocumentoByAcronimo(this.familiaEstructuraTree.getValue());

      if (tipoDocumento != null) {
        if (Executions.getCurrent().getDesktop().getAttribute("porTarea") == null) {
          if (tipoDocumento.getEsEspecial()) {
            if (tipoDocumento.getListaReparticiones().size() <= 1) {
              this.familiaEstructuraTree.setText(null);
              throw new WrongValueException(this.familiaEstructuraTree,
                  Labels.getLabel("gedo.iniciarDocumento.sinReparticionEspecial",
                      new String[] { tipoDocumento.getAcronimo() }));
            }
          }
        } else {
          Executions.getCurrent().getDesktop().removeAttribute("porTarea");
        }
        Executions.getCurrent().getDesktop().setAttribute("TipoDocumento", tipoDocumento);
        Event event = new Event(Events.ON_NOTIFY, this.self.getParent(), tipoDocumento);
        Events.sendEvent(event);
      } else {
        throw new WrongValueException(this.familiaEstructuraTree,
            Labels.getLabel("gedo.general.tipoDocumentoInvalido"));
      }
    } else {
      Executions.getCurrent().getDesktop().removeAttribute("TipoDocumento");
    }
  }

  private final class FamiliaTipoItemRender implements TreeitemRenderer {
    @Override
    public void render(Treeitem item, Object data, int arg2) throws Exception {
      DefaultTreeNode nodo = (DefaultTreeNode) data;
      if (nodo.getData() instanceof FamiliaTipoDocumentoDTO) {
        FamiliaTipoDocumentoDTO fi = (FamiliaTipoDocumentoDTO) nodo.getData();
        Treerow tr = new Treerow();
        tr.setParent(item);
        tr.setId(null);
        tr.appendChild(new Treecell(fi.getNombre()));
      }
      if (nodo.getData() instanceof TipoDocumentoDTO) {
        TipoDocumentoDTO repa = (TipoDocumentoDTO) nodo.getData(); 
        Treerow tr = new Treerow();
        tr.setParent(item);
        tr.setId(repa.getAcronimo());

        // Solución al Internal Bug #6739
        if (repa.getNombre().trim().length() > CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE) {
          String nombreAMostrar = repa.getNombre().trim().substring(0, 20)
              .concat(PUNTOS_SUSPENSIVOS);
          Treecell treeCell = new Treecell(nombreAMostrar);
          treeCell.setTooltiptext(repa.getNombre());
          tr.appendChild(treeCell);
        } else {
          tr.appendChild(new Treecell(repa.getNombre()));
        }

        tr.appendChild(new Treecell(repa.getAcronimo()));

        // Solución al Internal Bug #6739
        if (repa.getDescripcion().length() > CANT_MAX_CARACTERES_A_MOSTRAR_EN_DESC) {
          String descripcionAMostrar = repa.getDescripcion().trim().substring(0, 22)
              .concat(PUNTOS_SUSPENSIVOS);
          Treecell treeCell = new Treecell(descripcionAMostrar);
          treeCell.setTooltiptext(repa.getDescripcion());
          tr.appendChild(treeCell);
        } else {
          tr.appendChild(new Treecell(repa.getNombre()));
        }

        tr.appendChild(obtenerCaracteristicasTipoDocumento(repa, tr));
      }
    }
  }

}

final class InicioDocumentoFamiliaTipoDocumentoBandBoxComposerListener implements EventListener {
  private InicioDocumentoFamiliaTipoDocumentoBandBoxComposer composer;

  public InicioDocumentoFamiliaTipoDocumentoBandBoxComposerListener(
      InicioDocumentoFamiliaTipoDocumentoBandBoxComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY) && event.getData() != null) {
      Object data = event.getData();
      this.composer.recargarTiposDeDocumentos((Boolean) data);
    }
  }
}
