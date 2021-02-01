package com.egoveris.ffdd.render.zk.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoDTO;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.exception.DynFormValorComponente;
import com.egoveris.ffdd.model.model.ArchivoDTO;
import com.egoveris.ffdd.model.model.CampoBusqueda;
import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;
import com.egoveris.ffdd.render.model.ComplexComponent;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.model.SelectableComponent;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.zk.comp.ext.ComplexComponenLayout;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorExt;
import com.egoveris.ffdd.render.zk.comp.ext.TextboxExt;
import com.egoveris.ffdd.render.zk.form.IFormFactory;
import com.egoveris.ffdd.render.zk.form.VisibilityListener;
import com.egoveris.ffdd.render.zk.util.CComplejosHelper;
import com.egoveris.ffdd.render.zk.util.ComponentUtils;
import com.egoveris.ffdd.ws.service.ExternalCComplejosService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;

public class ZkFormManager implements IFormManager<Component> {

  private static final String REP_NUM_END = "]";
  private static final String REP_NUM_BEGIN = "[";
  private static final String SPACE_ = "_R";
  public static final String VACIO = "[Vacio]";
  private static final Logger logger = LoggerFactory.getLogger(ZkFormManager.class);

  private final FormularioDTO form;
  private final Component formZkComponent;
  private final ExternalTransaccionService transaccionService;
  private Map<String, InputComponent> inputCompZkMap;
  private final String sistemaOrigen;
  private final IFormFactory formFactory;
  private List<VisibilidadComponenteDTO> listaVistaListeners;

  private IComplexComponentService complexComponentService;

  private ExternalCComplejosService cComplejosService;

  public ZkFormManager(final FormularioDTO form, final Component formZkComponent,
      final ZkFormManagerFactory formManagerFactory) {
    this.form = form;
    this.formZkComponent = formZkComponent;
    this.transaccionService = formManagerFactory.getTransaccionService();
    this.sistemaOrigen = formManagerFactory.getSistemaOrigen();
    this.formFactory = formManagerFactory.getFormFactory();
    this.complexComponentService = formManagerFactory.getComplexComponentService();
    this.cComplejosService = formManagerFactory.getcComplejosService();
    fillComponentZkMap();
    setListeners();
  }

  @SuppressWarnings("unchecked")
  private void addContadorRepetitivos(final Set<ValorFormCompDTO> valoresComp) {
    final List<Component> listaGrids = this.formZkComponent.getChildren();
    for (final Component grid : listaGrids) {
      final Component sep = grid.getLastChild();
      if (sep instanceof SeparatorExt) {
        final int cantRepetidos = ((SeparatorExt) sep).getRepetidos();
        if (cantRepetidos > 0) {
          for (final FormularioComponenteDTO itemComp : this.form.getFormularioComponentes()) {
            if (itemComp.getId().equals(((SeparatorExt) sep).getIdComponentForm())) {
              valoresComp.add(
                  new ValorFormCompDTO(itemComp.getId(), itemComp.getNombre(), cantRepetidos));
              break;
            }
          }
        }
      }
    }
  }

  @SuppressWarnings("rawtypes")
  private void addListListener(final Component comp) {
    if (comp instanceof InputComponent) {
      final Iterator it = comp
          .getListenerIterator(ComponentUtils.eventForComp((InputComponent) comp));
      while (it.hasNext()) {
        final Object o = it.next();
        if (o instanceof VisibilityListener) {
          final VisibilityListener visListener = (VisibilityListener) o;
          if (!this.listaVistaListeners.contains(visListener.getVisibilidadComponenteDTO())) {
            this.listaVistaListeners.add(visListener.getVisibilidadComponenteDTO());
          }
        }

      }
    }

  }

  private void addVisibilityListeners(final Component vLayout, final String suffixRep) {
    for (final VisibilidadComponenteDTO visibilidadComponenteDTO : listaVistaListeners) {
      new VisibilityListener(vLayout, visibilidadComponenteDTO, suffixRep)
          .addListenerToParticipants();
    }
  }

  private void buildConstraints(final List<ComponentZkExt> newCloneComps, final String suffix) {
    for (final ComponentZkExt componentZkExt : newCloneComps) {
      ComponentUtils.buildConstraint(componentZkExt, suffix);
    }
  }

  private TransaccionDTO buscarTransCorresp(final Integer uuid) throws DynFormException {
    final TransaccionDTO trans = this.transaccionService.buscarTransaccionPorUUID(uuid);
    if (trans == null) {
      throw new DynFormException("No se encuentra la transaccion: " + uuid);
    }

    if (!trans.getNombreFormulario().equals(form.getNombre())) {
      throw new DynFormException("La transaccion: " + trans.getUuid()
          + " no corresponde con el formulario: " + form.getNombre());
    }

    return trans;
  }

  private void convSeparatorRep(final SeparatorExt separator,
      final FormularioComponenteDTO formComp) {
    final Auxheader auxheader = (Auxheader) separator.getFirstChild();
    if (CollectionUtils.isEmpty(auxheader.getChildren())) {
      auxheader.setLabel("");
      final Label label = new Label(separator.getLabel());
      final Image imagMas = new Image(SeparatorExt.IMAGENES_MAS_PNG);
      final Image imagMenos = new Image(SeparatorExt.IMAGENES_MENOS_PNG);
      final Hbox box = new Hbox();
      imagMas.setParent(box);
      imagMenos.setParent(box);
      label.setParent(box);
      box.setParent(auxheader);

      imagMas.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
        @Override
        public void onEvent(final Event event) throws Exception {
          crearGridRepetido(separator, formComp);
        }
      });

      imagMenos.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
        @Override
        public void onEvent(final Event event) throws Exception {
          eliminarGridRepetido(separator);
        }
      });
    }
  }

  private void crearGridRepetido(final SeparatorExt target,
      final FormularioComponenteDTO formComp) {

    final Component grid = target.getParent();
    final Component layoutPadre = grid.getParent();
    listaVistaListeners = new ArrayList<VisibilidadComponenteDTO>();
    final List<Component> compGrids = grid.getChildren();
    for (final Component rowsGrid : compGrids) {
      if (rowsGrid instanceof Rows) {
        final List<Component> rowGroupGrid = rowsGrid.getChildren();
        if (!rowGroupGrid.isEmpty()) {
          target.setRepetidos(target.getRepetidos() + 1);
          final int beforComp = layoutPadre.getChildren().indexOf(grid) + target.getRepetidos();
          String suffixRep = "";
          final Component grilla = nuevaGrillaRepetida(layoutPadre, target.getLabel(), beforComp);

          List<FormularioComponenteDTO> listFormComponent = new ArrayList<>();
          if (formComp == null) {
            listFormComponent = new ArrayList<FormularioComponenteDTO>(
                form.getFormularioComponentes());
          } else {
            listFormComponent.add(formComp);
          }
          final List<ComponentZkExt> newCloneComps = new ArrayList<>();
          final List<Component> compGrid = grid.getChildren();

          if (listFormComponent != null && !listFormComponent.isEmpty()) {
            // Verifica si el formComp va a ser una lista de DTOs
            // anidados
            final FormularioComponenteDTO component = listFormComponent.get(0);
            if (component.getNombre().endsWith(REP_NUM_END)) {
              StringBuilder nombreComponente = new StringBuilder(component.getNombre().substring(0,
                  component.getNombre().lastIndexOf(REP_NUM_BEGIN)));
              nombreComponente.append(REP_NUM_BEGIN).append(target.getRepetidos())
                  .append(REP_NUM_END);
              final ComponentZkExt cloneComp = createCloneComponent(component,
                  nombreComponente.toString());
              generateNewRow(cloneComp, grilla, component);
              newCloneComps.add(cloneComp);
            } else {
              suffixRep = new StringBuilder().append(SPACE_).append(target.getRepetidos())
                  .toString();
              Collections.sort(listFormComponent);
              for (final Component rows : compGrid) {
                if (rows instanceof Rows) {
                  final List<Component> rowGroup = rows.getChildren();
                  for (final Component row : rowGroup) {
                    final List<Component> compRow = row.getChildren();
                    for (Component comp : compRow) {
                      if (comp instanceof ComponentZkExt) {
                        newCloneComps.add(enviarARepetirComponente(comp, listFormComponent,
                            layoutPadre, grilla, suffixRep));

                      } else {
                        if (comp instanceof Cell && !(comp.getFirstChild() instanceof Grid)) {
                          comp = comp.getFirstChild();
                          newCloneComps.add(enviarARepetirComponente(comp, listFormComponent,
                              layoutPadre, grilla, suffixRep));
                        } else {
                          if (comp instanceof Cell && comp.getFirstChild() instanceof Grid) {
                            enviarARepetirSeparadorInterno(comp, listFormComponent)
                                .setParent(grilla);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }

          buildConstraints(newCloneComps, suffixRep);
          addVisibilityListeners(layoutPadre, suffixRep);
          updateComponentZkMap(formZkComponent);
        }
      }
    }
  }

  private ComponentZkExt createCloneComponent(final FormularioComponenteDTO fcompDB,
      final String nuevoNombre) {
    final FormularioComponenteDTO newComponent = fcompDB.clone();
    newComponent.setNombre(nuevoNombre);
    return this.formFactory.obtenerCompFactory(newComponent.getComponente().getTipoComponenteEnt())
        .create(newComponent);
  }

  @Override
  public void deleteFormWeb(final Integer uuid) throws DynFormException {
    final TransaccionDTO trans = new TransaccionDTO();
    trans.setUuid(uuid);
    this.transaccionService.deleteFormWebExpt(trans);
  }

  public void deshabilitarSeparadores(final Boolean bool) {
    final List<Component> listGrids = this.formZkComponent.getChildren();
    for (final Component itemGrid : listGrids) {
      if (itemGrid.getLastChild() instanceof SeparatorExt) {
        final SeparatorExt sep = (SeparatorExt) itemGrid.getLastChild();
        if (sep.isSepRepetitivo()) {
          final Auxheader auxheader = (Auxheader) sep.getFirstChild();
          final Hbox hbox = (Hbox) auxheader.getFirstChild();

          if (bool) {
            final List<Component> childrens = hbox.getChildren();
            for (int i = 0; i < 2; i++) {
              if (childrens.get(0) instanceof Image) {
                hbox.removeChild(childrens.get(0));
              }
            }
          } else {
            auxheader.removeChild(hbox);
            convSeparatorRep(sep, null);
          }
          break;
        }
      }
    }
  }

  private void eliminarGridRepetido(final SeparatorExt target) {

    if (target.getRepetidos() > 0) {
      final Component grid = target.getParent();
      final Component layaoutPadre = grid.getParent();

      final int ultimoCompRepPos = layaoutPadre.getChildren().indexOf(grid)
          + target.getRepetidos();
      final Component ultimoCompRep = layaoutPadre.getChildren().get(ultimoCompRepPos);
      // elimino el ultimo
      target.setRepetidos(target.getRepetidos() - 1);
      ultimoCompRep.detach();
      // actualizo el map
      updateComponentZkMap(layaoutPadre);
    }
  }

  private ComponentZkExt enviarARepetirComponente(final Component comp,
      final List<FormularioComponenteDTO> listFormComponent, final Component vLayout,
      final Component grilla, final String suffixRep) {
    final int idComponent = ((ComponentZkExt) comp).getIdComponentForm();
    for (final FormularioComponenteDTO fcompDB : listFormComponent) {
      if (fcompDB.getId() == idComponent) {
        final ComponentZkExt cloneComp = createCloneComponent(fcompDB,
            fcompDB.getNombre() + suffixRep);
        generateNewRow(cloneComp, grilla, fcompDB);
        addListListener(comp);
        return cloneComp;
      }
    }

    return null;
  }

  private Component enviarARepetirSeparadorInterno(final Component comp,
      final List<FormularioComponenteDTO> listFormComponent) {
    FormularioComponenteDTO fcompDB = null;

    final int idComponent = ((SeparatorExt) comp.getFirstChild().getFirstChild())
        .getIdComponentForm();
    for (final FormularioComponenteDTO fcomp : listFormComponent) {
      if (fcomp.getId() == idComponent) {
        fcompDB = fcomp;
        break;
      }
    }

    if (fcompDB != null) {
      return ComponentUtils.generarHeaderInterno(fcompDB);
    } else {
      throw new DynFormValorComponente("Error al generar el separador interno");
    }
  }

  /**
   * 
   * @param comp
   */
  private void fillCompMapRecursive(final Component comp) {

    if (comp instanceof InputComponent) {
      if (comp instanceof TextboxExt) {
        final TextboxExt textBox = (TextboxExt) comp;
        final Integer idComponentForm = textBox.getIdComponentForm();
        FormularioComponenteDTO formComp = null;
        final Iterator<FormularioComponenteDTO> iterator = form.getFormularioComponentes()
            .iterator();
        while (formComp == null && iterator.hasNext()) {
          final FormularioComponenteDTO componente = iterator.next();
          if (idComponentForm.equals(componente.getId())) {
            formComp = componente;
          }
        }
        if (null == formComp || (!formComp.isMultilinea() || formComp.isMultilineaEditable())) {
          final InputComponent inputE = (InputComponent) comp;
          this.inputCompZkMap.put(inputE.getName(), inputE);
        }
      } else {
        final InputComponent inputE = (InputComponent) comp;
        this.inputCompZkMap.put(inputE.getName(), inputE);
      }
    } else if (comp instanceof SeparatorComplex) {
      Vlayout vl = (Vlayout) comp.getFirstChild();
      if (vl.getFirstChild() != null && vl.getFirstChild() instanceof Grid) {
        for (Object obj : vl.getFirstChild().getChildren()) {
          if (obj instanceof SeparatorExt) {
            convSeparatorRep((SeparatorExt) obj, ((SeparatorComplex) comp).getFormComp());
          }
        }
      }
    }

    if (comp.getChildren() != null) {
      for (final Object obj : comp.getChildren()) {
        fillCompMapRecursive((Component) obj);
      }
    }
  }

  private void fillComponentZkMap() {
    updateComponentZkMap(this.formZkComponent);
  }

  @Override
  public void fillCompValue(final String name, final Object value) throws DynFormException {
    final InputComponent comp = this.getComponent(name);
    comp.setRawValue(value);
  }

  @Override
  public void fillCompValues(Integer uuid) throws DynFormException {
    fillCompValues(null, uuid);
  }

  @Override
  public void fillCompValues(final Integer idOperacion, final Integer uuid)
      throws DynFormException {
    Boolean componenteRepetido = true;

    // Busca valores por transacci�n
    TransaccionDTO trans;
    if (null != uuid) {
      trans = buscarTransCorresp(uuid);
    } else {
      trans = new TransaccionDTO();
      trans.setValorFormComps(new HashSet<ValorFormCompDTO>());
    }

    // Busca valores por componente complejo
    if (null != idOperacion) {
      Map<String, List<ValorFormCompDTO>> cComplejos = obtenerCComplejosForm();
      List<ValorFormCompDTO> valoresCComplejos = obtenerValoresCComplejos(idOperacion,
          cComplejos.keySet());
      repetirCompSegunCComplejos(valoresCComplejos);
      trans.getValorFormComps().addAll(valoresCComplejos);
    }
    repetirCompSegunTransaccion(trans);

    for (final ValorFormCompDTO valorFormComp : trans.getValorFormComps()) {
      componenteRepetido = true;
      for (final FormularioComponenteDTO itemComponente : this.form.getFormularioComponentes()) {
        if (itemComponente.getNombre().equals(valorFormComp.getInputName())) {
          componenteRepetido = false;
          if (!itemComponente.getComponente().getTipoComponente()
              .equals(ComponentUtils.SEPARATOR)) {
            fillCompValue(valorFormComp.getInputName(), valorFormComp.getValor());
          }
        }
      }
      if (componenteRepetido) {
        fillCompValue(valorFormComp.getInputName(), valorFormComp.getValor());
      }
    }

  }

  /**
   * 
   * @param valoresCComplejos
   */
  private void repetirCompSegunCComplejos(List<ValorFormCompDTO> valoresCComplejos) {

    Map<String, Set<String>> repetidos = new HashMap<>();

    // Buscar una lista de los campos que son listas
    for (ValorFormCompDTO valor : valoresCComplejos) {
      String nombreContenedor = valor.getInputName().split("_")[0];
      if (nombreContenedor.endsWith(REP_NUM_END)) {
        String key = nombreContenedor.substring(0, nombreContenedor.indexOf(REP_NUM_BEGIN));
        if (!repetidos.containsKey(key)) {
          repetidos.put(key, new HashSet<>());
        }
        repetidos.get(key).add(nombreContenedor);
      }
    }

    List<Component> complexComponents = new ArrayList<>();

    // Buscar los componentes complejos
    recursiveFindComplexSeparator(this.formZkComponent.getChildren(), repetidos);

  }

  /**
   * 
   * @param components
   * @param repetidos
   */
  private void recursiveFindComplexSeparator(List<Component> components,
      Map<String, Set<String>> repetidos) {

    if (CollectionUtils.isNotEmpty(components)) {
      for (Component component : components) {
        if (component instanceof SeparatorComplex) {
          repeatComplexSeparator(repetidos, component);
        }
        recursiveFindComplexSeparator(component.getChildren(), repetidos);
      }
    }

  }

  /**
   * 
   * @param repetidos
   * @param component
   */
  private void repeatComplexSeparator(Map<String, Set<String>> repetidos, Component component) {
    final SeparatorComplex separator = (SeparatorComplex) component;
    final String fieldName = separator.getId();
    int sepIndex = fieldName.indexOf(REP_NUM_BEGIN);
    if (0 < sepIndex) {
      final String key = fieldName.substring(0, sepIndex);
      // Ver la cantidad de repeticiones que hay para dicho
      // separador
      Integer rep = CollectionUtils.size(repetidos.get(key));
      if (rep > 1) {
        for (int i = 1; i < rep; i++) {
          crearGridRepetido(
              (SeparatorExt) component.getFirstChild().getFirstChild().getLastChild(),
              separator.getFormComp());
        }
      }
    }
  }

  private List<ValorFormCompDTO> obtenerValoresCComplejos(Integer idOperacion,
      Set<String> nombres) {
    List<ValorFormCompDTO> valores = new ArrayList<>();

    for (String nombre : nombres) {
      ComplexComponent cComponent = buscarCComplejo(nombre);
      try {
        Class<?> clase = Class.forName(cComponent.getMappingDTO());
        AbstractCComplejoDTO inputDto = (AbstractCComplejoDTO) clase.newInstance();
        inputDto.setIdOperacion(idOperacion);
        inputDto.setNombre(nombre);
        List<AbstractCComplejoDTO> listaDto = cComplejosService.buscarDatosComponente(inputDto);
        valores.addAll(CComplejosHelper.obtenerValoresComponente(nombre, listaDto));
      } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        logger.error(e.getMessage(), e);
      }
    }
    return valores;
  }

  @Override
  public void fillCompValues(final Map<String, Object> presetValues) throws DynFormException {
    for (final Entry<String, Object> entry : presetValues.entrySet()) {
      fillCompValue(entry.getKey(), entry.getValue());
    }
  }

  private Component generarHeader(final String titleHeader) {
    final Component result = new Auxhead();
    final Auxheader header = new Auxheader(titleHeader);
    header.setColspan(2);
    header.setParent(result);
    return result;
  }

  private void generateNewRow(final ComponentZkExt comps, final Component filaNueva,
      final FormularioComponenteDTO fcompDB) {
    final Row fila = new Row();

    if (fcompDB.getTextoMultilinea() != null) {
      final Cell celda = new Cell();
      celda.setColspan(2);
      celda.setAlign("center");
      ((Textbox) comps).setWidth("99%");
      ((Textbox) comps).setHeight("175px");
      ((Textbox) comps).setRows(3);
      ((Textbox) comps).setReadonly(!fcompDB.isMultilineaEditable());
      ((Textbox) comps).setValue(fcompDB.getTextoMultilinea());
      celda.appendChild(comps);
      celda.setParent(fila);
    } else {
      if (StringUtils.isEmpty(fcompDB.getComponente().getNombreXml())) {
        new Label(fcompDB.getEtiqueta()).setParent(fila);
        comps.setParent(fila);
      } else {
        final Cell celda = new Cell();

        celda.setColspan(2);
        celda.setAlign("center");
        celda.appendChild(comps);
        celda.setParent(fila);
      }
    }

    fila.setParent(filaNueva);
  }

  @Override
  public InputComponent getComponent(final String name) throws DynFormException {
    final InputComponent comp = this.inputCompZkMap.get(name);
    if (comp == null) {
      throw new DynFormException("No existe el componente solicitado: " + name);
    } else {
      return comp;
    }
  }

  private Object getData(final Object obj) {
    Object data = obj;
    if (obj instanceof Media) {
      final Media media = (Media) obj;
      final ArchivoDTO arch = new ArchivoDTO();
      arch.setNombre(media.getName());
      arch.setData(media.getByteData());
      data = arch;
    }
    return data;
  }

  @Override
  public Component getFormComponent() {
    return this.formZkComponent;
  }

  @Override
  public Component getFormComponent(final Integer uuid) throws DynFormException {
    return getFormComponent(uuid, false);
  }

  @Override
  public Component getFormComponent(final Integer uuid, final boolean disabled)
      throws DynFormException {
    fillCompValues(null, uuid);
    dispararEventos(getFormComponent(),this.form.getListaComponentesOcultos());
    readOnlyMode(disabled);

    
    return getFormComponent();
  }
  
	public void dispararEventos(final Component grilla, final List<VisibilidadComponenteDTO> restriccionDTOs) {
		
		final Component rootComp = ComponentUtils.obtenerVlayoutRoot(grilla);
		if (restriccionDTOs != null) {
			for (final VisibilidadComponenteDTO restriccionDTO : restriccionDTOs) {
				VisibilityListener visible = new VisibilityListener(rootComp, restriccionDTO);
				visible.triggerToParticipants();
				
			}
		}
	}

  @Override
  public Component getFormComponent(Integer idOperacion, final Integer uuid,
      final boolean disabled) throws DynFormException {
    fillCompValues(idOperacion, uuid);
    readOnlyMode(disabled);
    return getFormComponent();
  }

  private Object getValue(final InputComponent comp) {
    // TODO fix temporal
		logger.info("entrnado getValue(). Visible: " + comp.isVisible());
		if (comp.isVisible()) {
			comp.getText();
			logger.info("Text: " + comp.getText());
		}
    final Object value = comp.getRawValue();
    // No guarda comp nulos o vacios
    if (value != null) {
      if (!(value instanceof String
          && (((String) value).isEmpty() || ((String) value).equals(VACIO)))) {
        return value;
      }
    }
//     }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getValues() {
    final Map<String, Object> mapValue = new LinkedHashMap<String, Object>();
    for (final InputComponent comp : this.inputCompZkMap.values()) {
      final Object obj = getValue(comp);
      if (obj != null) {
        mapValue.put(comp.getName(), obj);
      }
    }

    final List<Component> listGrid = this.formZkComponent.getChildren();
    final Set<FormularioComponenteDTO> listComponentsForm = this.form.getFormularioComponentes();
    for (final Component itemGrid : listGrid) {
      if (itemGrid.getLastChild() instanceof SeparatorExt) {
        final SeparatorExt sep = (SeparatorExt) itemGrid.getLastChild();
        if (sep.isSepRepetitivo()) {
          for (final FormularioComponenteDTO formCompDTO : listComponentsForm) {
            if (formCompDTO.getId().equals(sep.getIdComponentForm())) {
              mapValue.put(formCompDTO.getNombre(), sep.getRepetidos().longValue());
              break;
            }
          }
        }
      }
    }
    return mapValue;
  }

  @Override
  public Map<String, String> getComponentMapTypes() {

    final Map<String, String> mapValue = new LinkedHashMap<String, String>();
    for (final InputComponent comp : this.inputCompZkMap.values()) {
      final Object obj = getValue(comp);
      if (obj != null) {
        mapValue.put(comp.getName(), comp.getDefinition().getName());
      }
    }
    return mapValue;

  }

  private Rows nuevaGrillaRepetida(final Component layaoutPadre, final String titleHeader,
      final int beforeCompPos) {

    final Rows rows = ComponentUtils.nuevaGrilla();
    final Grid grid = rows.getGrid();

    Component beforeComp = null;
    if (beforeCompPos < layaoutPadre.getChildren().size()) {
      beforeComp = layaoutPadre.getChildren().get(beforeCompPos);
    }

    layaoutPadre.insertBefore(grid, beforeComp);

    generarHeader(titleHeader).setParent(grid);
    return rows;
  }

  @Override
  public void readOnlyMode(final boolean bool) throws DynFormException {
    for (final String compName : this.inputCompZkMap.keySet()) {
      readOnlyMode(compName, bool);
    }
    deshabilitarSeparadores(bool);
  }

  @Override
  public void readOnlyMode(final String compName, final boolean bool) throws DynFormException {
    final InputComponent comp = this.getComponent(compName);
    if (comp instanceof SelectableComponent) {
      if (comp instanceof Datebox) {
      	if(!comp.isReadonly()) {      		
      		((Datebox) comp).setReadonly(bool);
      	}
      } else if (comp instanceof Bandbox) {
      	if(!comp.isReadonly()) {      		
      		((Bandbox) comp).setReadonly(bool);
      	}
      } else if (comp instanceof Combobox) {
      	if(!comp.isReadonly()) {      		
      		comp.setReadonly(bool);
      	}
      	((Combobox) comp).setDisabled(bool);
      } else if (comp instanceof Textbox) {
      	if(!comp.isReadonly()) {      		
      		comp.setReadonly(bool);
      	}
      	((Textbox) comp).setDisabled(bool);
      }
      ((SelectableComponent) comp).setButtonVisible(!bool);
    } else if (comp instanceof ComplexComponenLayout){
//    	ComplexComponenLayout complex = (ComplexComponenLayout) comp;
//    	if (!complex.isReadonly()) {
//    		complex.setReadonly(bool);
//    	}
    }else {
    	if(!comp.isReadonly()) {    		
    		comp.setReadonly(bool);
    	}
    }
  }

  @SuppressWarnings("unchecked")
  private void repetirCompSegunTransaccion(final TransaccionDTO trans) {

    final List<Component> listaGrids = this.formZkComponent.getChildren();

    for (final ValorFormCompDTO valorFormComp : trans.getValorFormComps()) {
      for (final Component itemGrid : listaGrids) {
        final Component separator = itemGrid.getLastChild();
        if (separator instanceof SeparatorExt) {
          final SeparatorExt sep = (SeparatorExt) separator;
          if (valorFormComp.getValorLong() != null && null != valorFormComp.getIdFormComp()
              && valorFormComp.getIdFormComp().equals(sep.getIdComponentForm())) {
            if (Long.valueOf(sep.getRepetidos().toString()) < valorFormComp.getValorLong()) {
              for (int i = 0; i < valorFormComp.getValorLong(); i++) {
                crearGridRepetido(sep, null);
              }
              break;
            }
          }
        }
      }
    }
  }

  @Override
  public Integer saveValues() throws DynFormException {
    return saveValues(null);
  }

  @Override
  public Integer saveValues(final Integer idOperacion) throws DynFormException {
  	
  	this.forzarValidacion(formZkComponent);
  	
    Map<String, List<ValorFormCompDTO>> cComplejos = new HashMap<>();
    Map<String, Integer> mapIdComponentesComplejos = new HashMap<>();
    obtenerCComplejosForm(cComplejos, mapIdComponentesComplejos, idOperacion);
    
    final Set<ValorFormCompDTO> valoresComp = new HashSet<>();
    for (final InputComponent comp : this.inputCompZkMap.values()) {

      final Object obj = getValue(comp);
      if (obj != null) {
        final String cComplejoName = CComplejosHelper.cComplejoName(comp.getName(),
            cComplejos.keySet());
      	Integer idComponentForm = 0;
      	
      	idComponentForm = getIdComponentForm(mapIdComponentesComplejos, comp);
      	
        if (null != cComplejoName) {
        	
          cComplejos.get(cComplejoName)
              .add(new ValorFormCompDTO(idComponentForm, comp.getName(), getData(obj)));
        } else {
              	
          valoresComp
              .add(new ValorFormCompDTO(idComponentForm, comp.getName(), getData(obj)));
        }
      }
    }
    addContadorRepetitivos(valoresComp);

    Integer uuid = null;
    if (!valoresComp.isEmpty()) {
      final TransaccionDTO trans = new TransaccionDTO(this.form.getNombre());
      trans.setValorFormComps(valoresComp);
      trans.setSistOrigen(sistemaOrigen);
      uuid = this.transaccionService.grabarTransaccion(trans);
    }

    saveCComplejos(cComplejos, idOperacion);

    return uuid;
  }

	private Integer getIdComponentForm(Map<String, Integer> mapIdComponentesComplejos,
			final InputComponent comp) {
		Integer idComponentForm;
		if(comp.getIdComponentForm()==0) {
			String sufijo = comp.getName().split("_")[0];
			idComponentForm  = mapIdComponentesComplejos.get(sufijo);
		}else {
			idComponentForm = comp.getIdComponentForm();
		}
		return idComponentForm;
	}

  /**
   * @param camposComplejos 
   * @param cComplejos 
   * @param idOperacion 
   * @return
   */
  public void obtenerCComplejosForm(Map<String, List<ValorFormCompDTO>> cComplejos, Map<String, Integer> mapIdComponenteComplejos, Integer idOperacion) {
    
    for (FormularioComponenteDTO comp : this.form.getFormularioComponentes()) {
      if (null != comp.getComponente()
          && complexComponentService.hasComponentDTO(comp.getComponente().getNombreXml())) {
      	if(idOperacion!=null) {      		
      		cComplejos.put(comp.getNombre(), new ArrayList<ValorFormCompDTO>());
      	}
      	mapIdComponenteComplejos.put(comp.getNombre(), comp.getId());
      }
    }
  }
  
  
  /**
   * @return
   */
  public Map<String, List<ValorFormCompDTO>> obtenerCComplejosForm() {
    
  	
  	Map<String, List<ValorFormCompDTO>> cComplejos = new HashMap<String, List<ValorFormCompDTO>>();
  	
    for (FormularioComponenteDTO comp : this.form.getFormularioComponentes()) {
      if (null != comp.getComponente()
          && complexComponentService.hasComponentDTO(comp.getComponente().getNombreXml())) {
      		cComplejos.put(comp.getNombre(), new ArrayList<ValorFormCompDTO>());	
      }
    }
    
    return cComplejos;
  }

  /**
   * 
   * @param cComplejos
   */
  private void saveCComplejos(final Map<String, List<ValorFormCompDTO>> cComplejos,
      final Integer idOperacion) {

    Set<String> nombres = cComplejos.keySet();
    // No guarda los complejos por que no se completa el DTO!!!!!!!!!!!!!!!
    for (String nombre : nombres) {
      ComplexComponent cComponent = buscarCComplejo(nombre);
      try {
        Class<?> clase = Class.forName(cComponent.getMappingDTO());
        List<AbstractCComplejoDTO> componentes = CComplejosHelper.crearComponentesComplejos(nombre,
            cComplejos.get(nombre), clase, idOperacion);
        cComplejosService.guardarDatosComponentes(componentes);
      } catch (ClassNotFoundException e) {
        logger.error(e.getMessage(), e);
      }
    }

  }

  /**
   * @param nombre
   * @return
   */
  public ComplexComponent buscarCComplejo(String nombre) {
    String nombreXml = null;
    Iterator<FormularioComponenteDTO> iterator = this.form.getFormularioComponentes().iterator();
    while (null == nombreXml && iterator.hasNext()) {
      FormularioComponenteDTO formularioComponenteDTO = iterator.next();
      if (nombre.equals(formularioComponenteDTO.getNombre())) {
        nombreXml = formularioComponenteDTO.getComponente().getNombreXml();
      }
    }
    ComplexComponent cComponent = complexComponentService.getComponent(nombreXml);
    return cComponent;
  }

  @Override
  public List<CampoBusqueda> searchAllFields() {
    return ComponentUtils.getTodosCamposBusqueda(this.form);
  }

  @Override
  public List<CampoBusqueda> searchFields() {
    return ComponentUtils.getCamposBusqueda(this.form);
  }

  @SuppressWarnings("unchecked")
  public void setListeners() {
    final List<Component> listGrids = this.formZkComponent.getChildren();
    for (final Component itemGrid : listGrids) {
      if (itemGrid.getLastChild() instanceof SeparatorExt) {
        final SeparatorExt sep = (SeparatorExt) itemGrid.getLastChild();
        if (sep.isSepRepetitivo()) {
          convSeparatorRep(sep, null);
        }
      }
    }
  }

  private void updateComponentZkMap(final Component newFormZkComponent) {
    this.inputCompZkMap = new LinkedHashMap<String, InputComponent>();
    fillCompMapRecursive(newFormZkComponent);
  }

  @Override
  public void updateFormWeb(Integer uuid) throws DynFormException {
    updateFormWeb(null, uuid);
  }

  @Override
  public void updateFormWeb(final Integer idOperacion, final Integer uuid)
      throws DynFormException {
    try {
      logger.debug("Entro con valor de Transaction " + uuid);

      Map<String, List<ValorFormCompDTO>> cComplejos = new HashMap<>();
      if (null != idOperacion) {
        cComplejos = obtenerCComplejosForm();
      }
      final Set<ValorFormCompDTO> valoresComp = new HashSet<>();

      for (final InputComponent comp : this.inputCompZkMap.values()) {
        logger.debug(
            " Valores " + comp.getId() + " " + comp.getIdComponentForm() + " " + comp.getName());
        final Object obj = getValue(comp);
        if (obj != null) {
          logger.debug(" COMPONENTE comp.getId() " + comp.getId());
          logger.debug(" ID FORMCOMP comp.getIdComponentForm() " + comp.getIdComponentForm());
          final String cComplejoName = CComplejosHelper.cComplejoName(comp.getName(),
              cComplejos.keySet());
          if (null != cComplejoName) {
            cComplejos.get(cComplejoName).add(
                new ValorFormCompDTO(comp.getIdComponentForm(), comp.getName(), getData(obj)));
          } else {
            valoresComp.add(
                new ValorFormCompDTO(comp.getIdComponentForm(), comp.getName(), getData(obj)));
          }
        }
      }

      addContadorRepetitivos(valoresComp);

      if (!valoresComp.isEmpty()) {
        final TransaccionDTO trans = new TransaccionDTO(this.form.getNombre());
        trans.setValorFormComps(valoresComp);
        trans.setSistOrigen(sistemaOrigen);
        trans.setUuid(uuid);
        this.transaccionService.updateFormWebExpt(trans);
      }

      saveCComplejos(cComplejos, idOperacion);

    } catch (final Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Override
  public void forzarValidacion(final Component comp) {

    if (comp instanceof InputComponent && comp.isVisible()) {
      ((InputComponent) comp).getText();
    }
    
    if(comp instanceof ComplexComponenLayout && comp.isVisible()) {
    	Events.sendEvent(comp.getChildren().get(0), new Event(Events.ON_NOTIFY));
    }

    if (comp.getChildren() != null) {
      for (final Object obj : comp.getChildren()) {
        forzarValidacion((Component) obj);
      }
    }
  }
  
  @Override
  public Map<String, ?> getComponentsMap() {
    return this.inputCompZkMap;
  }
  
}