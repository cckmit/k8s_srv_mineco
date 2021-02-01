package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class BuscadorTipoDocumentoComposer extends GenericForwardComposer {

  private static final long serialVersionUID = 1081323236289527265L;

  private static final String PUNTOS_SUSPENSIVOS = "...";
  private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_DESC = 23;
  private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE = 21;
  public static final String TIPO_PRODUCCION_LIBRE = "1";
  public static final String TIPO_PRODUCCION_IMPORTADO = "2";
  public static final String TIPO_PRODUCCION_TEMPLATE = "3";
  public static final String TIPO_PRODUCCION_IMPORTADO_TEMPLATE = "4";
  public static final String IMG_ES_FIRMA_CONJUNTA = "/imagenes/IconoFirmaConjunta.png";
  public static final String IMG_TIENE_TOKEN = "/imagenes/IconoToken.png";
  public static final String IMG_ES_CONFIDENCIAL = "/imagenes/IconoConfidencial.png";
  public static final String IMG_ES_FIRMA_EXTERNA = "/imagenes/IconoFirmaExterna.png";
  public static final String IMG_ES_ESPECIAL = "/imagenes/IconoEspecial.png";
  public static final String IMG_TIENE_LIBRE = "/imagenes/IconoLibre.png";
  public static final String IMG_TIENE_TEMPLATE = "/imagenes/IconoTemplate.png";
  public static final String IMG_TIENE_IMPORTADO_TEMPLATE = "/imagenes/IconoImportadoTemplate.png";
  public static final String IMG_TIENE_IMPORTADO = "/imagenes/IconoImportado.png";
  public static final String IMG_COPIAR_OBLIGATORIO = "/imagenes/copiarObligatorio.png";
  public static final String IMG_COPIAR_NO_OBLIGATORIO = "/imagenes/copiarNoObligatorio.png";

  @Autowired
  public Tree familiaTipoTree;
  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  private TreeModel treeModel;
  @Autowired
  private Textbox textoTipoDocumento;
  private ArrayList<TipoDocumentoDTO> listaFamiliasSeleccionadas;
  private ArrayList<TipoDocumentoDTO> listaFamiliasTotal;
  
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;
  
  @Autowired
  private Bandbox buscadorTipoDocumento;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    comp.addEventListener(Events.ON_NOTIFY, new BuscadorTipoDocumentoComposerListener(this));
    this.listaFamiliasTotal = new ArrayList<TipoDocumentoDTO>(
        tipoDocumentoService.buscarTipoDocumentoByEstadoFiltradosManual("ALTA", true));
    this.listaFamiliasSeleccionadas = new ArrayList<TipoDocumentoDTO>(this.listaFamiliasTotal);
    this.binder = new AnnotateDataBinder(this.familiaTipoTree);
    cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
    this.binder.loadAll();

  }

  @SuppressWarnings("null")
  private void cargarReparticionesPorEstructura(ArrayList<TipoDocumentoDTO> familias) {

    List<DefaultTreeNode> listaEstructuradaTree = new ArrayList<DefaultTreeNode>();
    List<DefaultTreeNode> listaDocumentosfamilia = new ArrayList<DefaultTreeNode>();

    if (familias != null) {
      listaEstructuradaTree.clear();
      TipoDocumentoDTO tipoDocumento;
      String nombrefamilia;
      int i = 0;
      int ce = familias.size();
      while (i < ce) {
        listaDocumentosfamilia.clear();
        nombrefamilia = familias.get(i).getFamilia().getNombre();
        while (i < ce && familias.get(i).getFamilia().getNombre().equals(nombrefamilia)) {
          listaDocumentosfamilia.add(new DefaultTreeNode(familias.get(i)));
          i++;
        }

        if (listaDocumentosfamilia.size() == 0) {
          tipoDocumento = new TipoDocumentoDTO();
          tipoDocumento.setAcronimo("");
          tipoDocumento.setNombre("");
          listaDocumentosfamilia.add(new DefaultTreeNode(tipoDocumento));
        }

        listaEstructuradaTree.add(new DefaultTreeNode(nombrefamilia, listaDocumentosfamilia));
      }
      this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT", listaEstructuradaTree));
    }

    familiaTipoTree.setTreeitemRenderer(new TreeitemRenderer() {

      public void render(Treeitem item, Object data, int arg1) throws Exception {
        DefaultTreeNode tn = (DefaultTreeNode) data;
        if (tn.getData() instanceof String) {
          Treerow tr = new Treerow();
          tr.setParent(item);
          tr.setId(null);
          tr.appendChild(new Treecell((String) tn.getData()));
        }
        if (tn.getData() instanceof TipoDocumentoDTO) {
          TipoDocumentoDTO repa = (TipoDocumentoDTO) tn.getData();
          Treerow tr = new Treerow();
          tr.setParent(item);
          tr.setId(repa.getAcronimo());

          if (repa.getNombre().length() > CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE) {
            String nombreAMostrar = repa.getNombre().trim().substring(0, 20)
                .concat(PUNTOS_SUSPENSIVOS);
            Treecell treeCell = new Treecell(nombreAMostrar);
            treeCell.setTooltiptext(repa.getNombre());
            tr.appendChild(treeCell);
          } else {
            tr.appendChild(new Treecell(repa.getNombre()));
          }

          tr.appendChild(new Treecell(repa.getAcronimo()));

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
    });
    familiaTipoTree.setModel(this.treeModel);
    this.binder.loadComponent(familiaTipoTree);
  }

  private Treecell obtenerCaracteristicasTipoDocumento(TipoDocumentoDTO repa, Treerow tr)
      throws IOException {
    Treecell tc = new Treecell();

    Image imagenDocumentoEspecial;
    Image imagenDocumentoFirmaConToken = null;
    Image imagenDocumentoProduccion = null;
    Image imagenDocumentoFirmaConjunta = new Image();
    Image imagenDocumentoFirmaExterna;
    Image imagenDocumentoReservado = null;

    if (repa.getTipoProduccion() == TIPO_PRODUCCION_LIBRE) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(IMG_TIENE_LIBRE);
      imagenDocumentoProduccion
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.libre"));
      tc.appendChild(imagenDocumentoProduccion);
    } else if (repa.getTipoProduccion() == TIPO_PRODUCCION_IMPORTADO) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(IMG_TIENE_IMPORTADO);
      imagenDocumentoProduccion
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.importado"));
      tc.appendChild(imagenDocumentoProduccion);
    } else if (repa.getTipoProduccion() == TIPO_PRODUCCION_TEMPLATE) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(IMG_TIENE_TEMPLATE);
      imagenDocumentoProduccion
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.template"));
      tc.appendChild(imagenDocumentoProduccion);
    } else if (repa.getTipoProduccion() == TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
      imagenDocumentoProduccion = new Image();
      imagenDocumentoProduccion.setSrc(IMG_TIENE_IMPORTADO_TEMPLATE);
      imagenDocumentoProduccion
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.importadoTemplate"));
    }

    if (repa.getEsEspecial().equals(Boolean.TRUE)) {
      imagenDocumentoEspecial = new Image();
      imagenDocumentoEspecial.setSrc(IMG_ES_ESPECIAL);
      imagenDocumentoEspecial
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.especial"));
      tc.appendChild(imagenDocumentoEspecial);
    }

    if (repa.getEsFirmaExterna().equals(Boolean.TRUE)) {
      imagenDocumentoFirmaExterna = new Image();
      imagenDocumentoFirmaExterna.setSrc(IMG_ES_FIRMA_EXTERNA);
      imagenDocumentoFirmaExterna
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.firmaExterna"));
      tc.appendChild(imagenDocumentoFirmaExterna);
    }

    if (repa.getEsConfidencial().equals(Boolean.TRUE)) {
      imagenDocumentoReservado = new Image();
      imagenDocumentoReservado.setSrc(IMG_ES_CONFIDENCIAL);
      imagenDocumentoReservado
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.reservado"));
      tc.appendChild(imagenDocumentoReservado);
    }

    if (repa.getTieneToken().equals(Boolean.TRUE)) {
      imagenDocumentoFirmaConToken = new Image();
      imagenDocumentoFirmaConToken.setSrc(IMG_TIENE_TOKEN);
      imagenDocumentoFirmaConToken
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.token"));
      tc.appendChild(imagenDocumentoFirmaConToken);
    }

    if (repa.getEsFirmaConjunta().equals(Boolean.TRUE)) {
      imagenDocumentoFirmaConjunta = new Image();
      imagenDocumentoFirmaConjunta.setSrc(IMG_ES_FIRMA_CONJUNTA);
      imagenDocumentoFirmaConjunta
          .setTooltiptext(Labels.getLabel("ee.general.imagenesCaracteristicas.firmaConjunta"));
      tc.appendChild(imagenDocumentoFirmaConjunta);
    }
    return tc;
  }

  public void onSelect$familiaTipoTree() throws InterruptedException {
    String codigoId = familiaTipoTree.getSelectedItem().getTreerow().getId();
    TipoDocumentoDTO SelecDocumArbol = buscarDocumento(codigoId);
    if (codigoId != null && !codigoId.trim().equals("")) {
      this.textoTipoDocumento.setText(null);
      this.buscadorTipoDocumento.close();
      Event event = new Event(Events.ON_NOTIFY, this.self.getParent(), SelecDocumArbol);
      Events.sendEvent(event);
    }
  }

  private TipoDocumentoDTO buscarDocumento(String acronimo) {
    TipoDocumentoDTO salida = null;
    salida = this.tipoDocumentoService.consultarTipoDocumentoPorAcronimo(acronimo);
    return salida;
  }

  public void onChanging$buscadorTipoDocumento(InputEvent e) {
    this.buscadorTipoDocumento.setValue(e.getValue());
    this.textoTipoDocumento.setText(e.getValue());
    this.onChanging$textoTipoDocumento(e);
  }

  public void onChanging$textoTipoDocumento(InputEvent e) {
    this.cargarFamilias(e);
  }

  public void cargarFamilias(InputEvent e) {
    String matchingText = e.getValue();
    if (!matchingText.equals("") && (matchingText.length() >= 2)) {
      this.listaFamiliasSeleccionadas.clear();
      if (this.listaFamiliasTotal != null) {
        matchingText = matchingText.toUpperCase();
        Iterator<TipoDocumentoDTO> iterator = listaFamiliasTotal.iterator();
        TipoDocumentoDTO tipoDocumento;
        while ((iterator.hasNext())) {
          tipoDocumento = iterator.next();
          if ((tipoDocumento != null)) {
            if ((tipoDocumento.getAcronimo().contains(matchingText))) {
              this.listaFamiliasSeleccionadas.add(tipoDocumento);
            }
          }
        }
      }
      this.cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
      this.binder.loadComponent(familiaTipoTree);
    } else if (matchingText.equals("")) {
      this.listaFamiliasSeleccionadas.clear();

      this.listaFamiliasSeleccionadas = new ArrayList<TipoDocumentoDTO>(this.listaFamiliasTotal);
      this.cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
      this.binder.loadComponent(familiaTipoTree);
    }
  }

  public void recargarTiposDeDocumentos(boolean todosLosEstados) {

    this.textoTipoDocumento.setText(null);
    this.listaFamiliasSeleccionadas.clear();
    this.listaFamiliasSeleccionadas = new ArrayList<TipoDocumentoDTO>(this.listaFamiliasTotal);
    this.binder = new AnnotateDataBinder(this.familiaTipoTree);
    cargarReparticionesPorEstructura(this.listaFamiliasSeleccionadas);
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

  public ArrayList<TipoDocumentoDTO> getListaFamiliasSeleccionadas() {
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

  public void onBlur$buscadorTipoDocumento() {
    this.buscadorTipoDocumento.setText(this.buscadorTipoDocumento.getValue().toUpperCase());
    if (!this.buscadorTipoDocumento.getValue().trim().equals("")) {
      TipoDocumentoDTO tipoDocumento = this.tipoDocumentoService
          .consultarTipoDocumentoPorAcronimo(this.buscadorTipoDocumento.getValue());
      if (tipoDocumento != null) {
        Executions.getCurrent().getDesktop().setAttribute("TipoDocumento", tipoDocumento);
        Event event = new Event(Events.ON_NOTIFY, this.self.getParent(), tipoDocumento);
        Events.sendEvent(event);

      } else {
        throw new WrongValueException(this.buscadorTipoDocumento,
            Labels.getLabel("ee.general.tipoDocumentoInvalido"));
      }
    } else {
      Executions.getCurrent().getDesktop().removeAttribute("TipoDocumento");
    }
  }

}

final class BuscadorTipoDocumentoComposerListener implements EventListener {
  private BuscadorTipoDocumentoComposer composer;

  public BuscadorTipoDocumentoComposerListener(BuscadorTipoDocumentoComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings("unchecked")
  public void onEvent(Event event) throws Exception {

    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        Object data = event.getData();
        this.composer.recargarTiposDeDocumentos((Boolean) data);
      }
    }
  }
}
