package com.egoveris.ffdd.web.adm;

import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.base.util.FFDDConstants;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.GrupoComponenteDTO;
import com.egoveris.ffdd.model.model.GrupoDTO;
import com.egoveris.shared.collection.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelArray;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMCajaDeDatosComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(ABMFCComposer.class);

	@WireVariable("formularioService")
	private IFormularioService formularioService;
	private Window windowCajas;
	private Window hiddenView;
	private Combobox comboboxTipoComponentes;
	private Textbox nombreComponente;
	private Textbox etiquetaComponente;
	private Textbox busquedaCajaExistente;
	private Listbox listboxComponentes;
	private Listbox listboxComponentesInstancia;
	private Listitem componenteCaja;
	private Listitem itemComponente;
	private Listbox listboxCajasExistenSugerencias;
	private Listheader tituloExistenSugerencias;
	private Button eliminar;
	private Button modificar;
	private Textbox nombreCaja;
	private Textbox descripcionCaja;

	private List<ComponenteDTO> listaComponente;
	private ComponenteDTO selectedComponente;
	private GrupoDTO selectedGrupo;
	private List<GrupoComponenteDTO> listaGrupoComponente;
	private List<GrupoDTO> listaGrupos;
	private Set<String> listaTipoComponente;
	private boolean modificarGrupo = false;
	private boolean verExistentes = false;

	private String loggedUser;
	private String modo;
	
	private AnnotateDataBinder cajaDeDatosBinder;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		asignarListeners();
		obtenerDatos();
		this.cajaDeDatosBinder = new AnnotateDataBinder(windowCajas);
		this.cajaDeDatosBinder.loadAll();
	}

	private void asignarListeners() {
		windowCajas.addEventListener(Events.ON_CLOSE, new EventListener() {
			public void onEvent(Event event) throws Exception {
				onCancelar();
				event.stopPropagation();
			}
		});
	}

	public void obtenerDatos() throws DynFormException {

		this.loggedUser = Executions.getCurrent().getRemoteUser();
		this.listaGrupoComponente = new ArrayList<>();
		this.modo = (String) Executions.getCurrent().getArg().get("modo");
		fillListaComponente();
		fillListaTipoComponente();
		

	}

	private void fillListaComponente() throws DynFormException {
		List<ComponenteDTO> lComponentes = this.formularioService
				.obtenerTodosLosComponentes();
		ComponenteDTO componenteMultilinea = new ComponenteDTO();
		for(ComponenteDTO componente : lComponentes){
			if(componente.getNombre().equals(FormularioComponenteDTO.TEXTBOX_MULTILINEA)){
			   componenteMultilinea = componente;
			   break;
			}
		}	    
		lComponentes.remove(componenteMultilinea);
		setListaComponente(lComponentes);
	}

	private void fillListaTipoComponente() {
		getListaComponente();
		Set<String> result = new TreeSet<>();
		for (ComponenteDTO comp : getListaComponente()) {
			result.add(comp.getTipoComponente());
		}
		setListaTipoComponente(result);
	}

	public void onChanging$buscarComponente(InputEvent e) {
		List<ComponenteDTO> model = new ArrayList<>();
		ListModel model1 = listboxComponentes.getModel();
		for (int i = 0; i < model1.getSize(); i++) {
			ComponenteDTO element = (ComponenteDTO) model1.getElementAt(i);
			if (element.getNombre().toLowerCase()
					.contains(e.getValue().toLowerCase())) {
				model.add(element);
			}
		}
		// cargo los componentes filtrados
		if (!"".equals(e.getValue())) {
			listboxComponentes.setModel(new BindingListModelArray(model
					.toArray(), true));
		} else {
			// cargo todos los componentes
			if ("".equals(comboboxTipoComponentes.getValue())) {
				model = new ArrayList<>();
				for (ComponenteDTO componenteDTO : getListaComponente()) {
					model.add(componenteDTO);
				}
				Collections.sort(model, new Comparator<ComponenteDTO>() {
					public int compare(ComponenteDTO c1, ComponenteDTO c2) {
						return c1.getNombre().compareToIgnoreCase(
								c2.getNombre());
					}
				});
				
				listboxComponentes.setModel(new BindingListModelArray(model
						.toArray(), true));
			}
			// cargo los componentes segun el tipo de componente seleccionado
			else {
				onSelect$comboboxTipoComponentes();
			}
		}
	}

	public void onSelect$comboboxTipoComponentes() {

		List<ComponenteDTO> model = new ArrayList<>();

		for (ComponenteDTO componenteDTO : getListaComponente()) {
			if (comboboxTipoComponentes.getValue().equals(
					componenteDTO.getTipoComponente())) {
				model.add(componenteDTO);
			}
		}

		Collections.sort(model, new Comparator<ComponenteDTO>() {
			@Override
			public int compare(ComponenteDTO c1, ComponenteDTO c2) {
				return c1.getNombre().compareToIgnoreCase(c2.getNombre());
			}
		});

		listboxComponentes.setModel(new BindingListModelArray(model.toArray(),
				true));
	}

	@SuppressWarnings("unchecked")
  public void onDropItem(ForwardEvent fe) throws InterruptedException {
    DropEvent event = (DropEvent) fe.getOrigin();
    final Listitem draggedComp = (Listitem) event.getDragged();
    if (draggedComp.getParent().getId().equals(listboxComponentes.getId())
        || draggedComp.getParent().getId().equals(listboxComponentesInstancia.getId())) {
      validateOnDropItem(draggedComp, event );
    } else if (draggedComp.getParent().getId().equals(listboxCajasExistenSugerencias.getId())) {
      if ((!(event.getTarget() instanceof Button)) && (!this.listaGrupoComponente.isEmpty())) {

        Messagebox.show(Labels.getLabel("abmCombobox.mostrarCombo.vali"),
            Labels.getLabel(FFDDConstants.ATENCIONTITLE), Messagebox.YES | Messagebox.NO,
            Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
              public void onEvent(Event evt) throws Exception {
                if (FFDDConstants.ONYES.equals(evt.getName())) {
                  mostrarCajaExistente(draggedComp);
                }
              }
            });

      } else {
        mostrarCajaExistente(draggedComp);
      }
    }
  }
	
	public void validateOnDropItem(Listitem draggedComp, DropEvent event){
	  ListModelList model = (ListModelList) listboxComponentesInstancia.getModel();
     GrupoComponenteDTO gc = null;
  
     if (draggedComp.getParent() == listboxComponentes) {
      gc = new GrupoComponenteDTO();
      gc.setComponente((ComponenteDTO) draggedComp.getValue());
     } else if (draggedComp.getParent() == listboxComponentesInstancia) {
      gc = (GrupoComponenteDTO) draggedComp.getValue();
     }      
  
     if (event.getTarget() instanceof Listbox
       && draggedComp.getParent() == listboxComponentes) {
  
      model.add(model.size(), gc);
  
     } else if (event.getTarget() instanceof Listitem) {
      model.add(((Listitem) event.getTarget()).getIndex(), gc);
  
      if (draggedComp.getParent() == listboxComponentesInstancia) {
       model.remove(draggedComp.getIndex());
      }
     } else if (event.getTarget() instanceof Button
       && draggedComp.getParent() == listboxComponentesInstancia) {
      model.remove(draggedComp.getIndex());
     }
  
     setListaGrupoComponente(model.getInnerList());
	}
	

	public void mostrarCajaExistente(Listitem draggedComp) {

		prepararBotones();
		ListModelList model = (ListModelList) this.listboxComponentesInstancia
				.getModel();
		if (!this.listaGrupoComponente.isEmpty()) {
			model.removeAll(this.listaGrupoComponente);
		}

		GrupoDTO grupoComponente = (GrupoDTO) draggedComp.getValue();
		Set<GrupoComponenteDTO> listaComponentes = grupoComponente
				.getGrupoComponentes();
		this.nombreCaja.setValue(grupoComponente.getNombre());
		this.nombreCaja.setReadonly(true);
		this.nombreCaja.setTooltiptext(grupoComponente.getNombre());
		this.descripcionCaja.setValue(grupoComponente.getDescripcion());
		this.descripcionCaja.setReadonly(true);

		int indexItemGrupo = 0;
		this.nombreComponente.setReadonly(true);
		this.etiquetaComponente.setReadonly(true);
		for (GrupoComponenteDTO componente : listaComponentes) {

			model.add(indexItemGrupo, componente);
			++indexItemGrupo;
			setListaGrupoComponente(CollectionUtils.asList(model.getInnerList(), GrupoComponenteDTO.class));
		}
		setSelectedGrupo(grupoComponente);
		this.componenteCaja.setDraggable(FFDDConstants.FALSE);		
		this.itemComponente.setDraggable(FFDDConstants.FALSE);
		this.comboboxTipoComponentes.setValue(null);
		this.listboxComponentesInstancia.setDraggable(FFDDConstants.FALSE);
		this.cajaDeDatosBinder.loadAll();
	}

	public void onPrevisualizar() throws Exception {
		Map<String, Object> map = new HashMap<>();
		String nombreFC = null;
		try {
			if (this.esFormularioValido()) {
				FormularioDTO formTemp = generarFormularioTemporal();
				nombreFC = formTemp.getNombre();
				this.formularioService.guardarFormulario(formTemp);
			}

			if (nombreFC != null) {
				map.put("formularioControlado", nombreFC);
				map.put("modo", this.modo);
				if (this.hiddenView != null) {
					this.hiddenView.detach();
					this.hiddenView = (Window) Executions.createComponents(
							"/administradorFC/previsualizacionFC.zul", this.self, map);
						this.hiddenView.doModal();
						this.hiddenView.setTitle(Labels
								.getLabel("abmCajaDatos.tituloPrevisualizar")); 
				}
			}
		} catch (Exception e) {
			logger.error("Error al realizar la previsualizacion - "
					+ e.getMessage());
			throw e;
		}
	}
	
	public boolean formularioValidoIsEmpty(){
	  
	  if (StringUtils.isEmpty(this.nombreCaja.getValue())) {
     this.nombreCaja.setErrorMessage(Labels.getLabel("abmFC.nomForm.vali"));
     return false;}

  if (StringUtils.isEmpty(this.descripcionCaja.getValue())) {
     this.descripcionCaja.setErrorMessage(Labels.getLabel("abmFC.desForm.vali"));
     return false;}

  if (this.listboxComponentesInstancia.getItems() == null|| this.listboxComponentesInstancia.getItems().isEmpty()) {
  
     Messagebox.show(Labels.getLabel("abmCajaDatos.arrastrar.componente"),
       Labels.getLabel(FFDDConstants.ATENCIONTITLE), Messagebox.OK,
       Messagebox.ERROR);
     return false;
     }
  
  return true;
	  
	}
	
	private boolean esFormularioValido() throws InterruptedException,
			DynFormException {
	  
	     if(!formularioValidoIsEmpty()){
	         return false;
	         }
	  
		    if(!checkCiclo()) {
		        return false;
		        }

		    if(!listboxComponentesInstancia()){
	        return false;   
		        }

		    if(!listaGrupoComponente()){
	        return false;
		       }

		return true;
	}
	
	private boolean checkCiclo() {
    Map<String, Textbox> map = new HashMap<>();
    Pattern p = Pattern.compile("[^a-z0-9_]");
    Pattern pnum = Pattern.compile("[^a-z_]");
    Pattern pletra = Pattern.compile("[(?=.*[a-z])]");
    for (Object obj : this.listboxComponentesInstancia.getItems()) {
      if (obj instanceof Listitem) {
        Listitem li = (Listitem) obj;
        // Obtengo el segundo hijo del array (posicion 1 - celda con
        // Textbox)
        // Obtengo el primer y unico hijo de la celda (posicion 0 -
        // Textbox)
        Listcell lc = (Listcell) li.getChildren().get(1);
        Textbox tb = (Textbox) lc.getChildren().get(0);
        if (tb.getValue() == null || "".equals(tb.getValue())) {
          tb.setErrorMessage(Labels.getLabel("abmFC.nomComp.vali"));
          return false;
        }
        if (tb.getValue().length() > 30) {
          tb.setErrorMessage(Labels.getLabel("abmFC.nomLargo.vali"));
          return false;
        }
        
        if(!checkCicloRefactor(tb, pletra, pnum, map, p)){
          return false;
        }
      }
    }
    return true;
	}
	
	public boolean checkCicloRefactor(Textbox tb, Pattern pletra, Pattern pnum, Map<String, Textbox> map, Pattern p){
	  
	  Matcher m = p.matcher(tb.getValue());
   while (m.find()) {
     tb.setErrorMessage(Labels.getLabel("abmFC.nomCaract.vali"));
     return false;
   }

   m = pnum.matcher(tb.getValue());
   while (m.find()) {
     m = pletra.matcher(tb.getValue());
     if (m.find()) {
       break;
     } else {
       tb.setErrorMessage(Labels.getLabel("abmFC.nomCaract.vali.num"));
       return false;
     }
   }

   if (map.get(tb.getValue()) == null) {
     map.put(tb.getValue(), tb);
   } else {
     tb.setErrorMessage(Labels.getLabel("abmFC.nombIguales.vali"));
     return false;
   } 
   
   return true;
	}
	
	
	public boolean listboxComponentesInstancia(){
	  
	  for (Object obj : this.listboxComponentesInstancia.getItems()) {
	    if (obj instanceof Listitem) {
	     Listitem li = (Listitem) obj;
	     // Obtengo el segundo hijo del array (posicion 1 - celda con
	     // Textbox)
	     // Obtengo el primer y unico hijo de la celda (posicion 0 -
	     // Textbox)
	     Listcell lc = (Listcell) li.getChildren().get(2);
	     Textbox tb = (Textbox) lc.getChildren().get(0);
	     if (tb.getValue() == null || "".equals(tb.getValue())) {
	      tb.setErrorMessage(Labels.getLabel("abmFC.etiVacia.vali"));
	      return false;
	     }
	    }
	   }
	  return true;
	  
	}
	
	private boolean listaGrupoComponente(){

    Boolean separadorRepetidor = false;
    Boolean componentesVarios = false;
    for (GrupoComponenteDTO componente : listaGrupoComponente) {
      if ("Separator - Repetidor".equals(componente.getComponente().getNombre())) {
        if (separadorRepetidor && !componentesVarios) {
          break;
        } else {
          separadorRepetidor = true;
        }
      } else {
        if (!"Separator - Generico".equals(componente.getComponente().getNombre())) {
          componentesVarios = true;
          separadorRepetidor = false;
        }
      }
    }
    if (separadorRepetidor) {
      Messagebox.show(Labels.getLabel("abmFC.separadorRepetidor.vali"),
          Labels.getLabel(FFDDConstants.ATENCIONTITLE), Messagebox.OK, Messagebox.EXCLAMATION);
      return false;
    }
    if (!componentesVarios) {
      Messagebox.show(Labels.getLabel("abmFC.separadores.vali"),
          Labels.getLabel(FFDDConstants.ATENCIONTITLE), Messagebox.OK, Messagebox.EXCLAMATION);
      return false;
    }

    return true;
	}

	private FormularioDTO generarFormularioTemporal() {
		// NOMBRE TEMPORAL ids NULL
		FormularioDTO formTemp = new FormularioDTO();
		String nombreFC = "--TEMP"
				+ UUID.randomUUID().toString().substring(0, 5)
				+ this.nombreCaja.getValue();
		formTemp.setNombre(nombreFC);
		formTemp.setDescripcion(this.descripcionCaja.getValue());
		formTemp.setFormularioComponentes(new LinkedHashSet<FormularioComponenteDTO>());
		for (GrupoComponenteDTO formCompDto : getListaGrupoComponente()) {
			FormularioComponenteDTO fcDTO = new FormularioComponenteDTO();
			fcDTO.setComponente(formCompDto.getComponente());
			fcDTO.setEtiqueta(formCompDto.getEtiqueta());
			fcDTO.setNombre(formCompDto.getNombre());

			formTemp.getFormularioComponentes().add(fcDTO);
		}

		return formTemp;
	}

	public void prepararBotones() {
		this.eliminar.setVisible(true);
		this.modificar.setVisible(true);
	}

	public void onGuardar() throws Exception {
		try {
			if (esFormularioValido()) {
				if (!this.modificarGrupo) {
				  validateOnGuardarFormularioService();
				}else {
					modificarGrupo();
				}
				actualizarPantalla(this.verExistentes);
			} 
									
		} catch (Exception e) {
			logger.error("Error en el alta del Grupo - " + e.getMessage());
			throw e;
		}
	}
	
	public void validateOnGuardarFormularioService(){
	  
   if (formularioService.buscarGrupoPorNombre(this.nombreCaja
       .getValue()) == null) {
      this.formularioService.guardarGrupo(prepararGrupo());
      Messagebox
        .show(Labels.getLabel("abmCajaDatos.altaCaja"),
          Labels.getLabel("fc.informacion"),
          Messagebox.OK, Messagebox.INFORMATION);
     } else {
      Messagebox
        .show(Labels.getLabel("abmCajaDatos.errorAlta"),
          Labels.getLabel("fc.informacion"),
          Messagebox.OK, Messagebox.INFORMATION);
     }
	}
	
	public void onChanging$busquedaCajaExistente(InputEvent event) throws DynFormException {
		List<GrupoDTO> listaFiltrada = new ArrayList<>();
		for (GrupoDTO grupo : this.listaGrupos) {
			if (grupo.getNombre().toLowerCase()
					.contains(event.getValue().toLowerCase())) {
				listaFiltrada.add(grupo);
			}
		}
		if (!"".equals(event.getValue())) {
			setListaGrupos(listaFiltrada);
		} else {
			setListaGrupos(formularioService.obtenerTodosLosGrupos());
		}
		this.cajaDeDatosBinder.loadComponent(this.listboxCajasExistenSugerencias);
	}

	
	public void actualizarPantalla(boolean existentes) throws Exception{
		if (existentes) {
			actualizarGruposExistentes();
		} 
		ListModelList model = (ListModelList) this.listboxComponentesInstancia
				.getModel();
		model.removeAll(this.listaGrupoComponente);
		this.modificarGrupo = false;
		this.componenteCaja.setDraggable("true");
		this.componenteCaja.setDroppable("true");
		this.listboxComponentesInstancia.setDroppable("true");
		this.itemComponente.setDraggable("true");
		this.descripcionCaja.setReadonly(false);
		this.nombreCaja.setReadonly(false);
		this.etiquetaComponente.setReadonly(false);
		this.nombreComponente.setReadonly(false);
		this.descripcionCaja.setValue(null);
		this.nombreCaja.setValue(null);
		this.comboboxTipoComponentes.setValue(null);
		this.eliminar.setVisible(false);
		this.modificar.setVisible(false);
		this.cajaDeDatosBinder.loadAll();		
	}
	
	
	public void actualizarGruposExistentes()throws Exception{
		try {
			this.listaGrupos = formularioService.obtenerTodosLosGrupos();
			sortListaExistentes();
			this.listboxCajasExistenSugerencias.setModel(new ListModelArray(
					this.listaGrupos));
			this.cajaDeDatosBinder.loadComponent(this.listboxCajasExistenSugerencias);
		} catch (Exception e) {
			logger.error("Error al cargar los grupos - "+ e.getMessage());
			throw e;
		}
		
	}
	
	public void modificarGrupo()throws Exception{
		try {
			this.selectedGrupo
					.setGrupoComponentes(new LinkedHashSet<GrupoComponenteDTO>(
							this.listaGrupoComponente));
			this.selectedGrupo.setDescripcion(this.descripcionCaja.getValue());
			this.formularioService.modificarGrupo(getSelectedGrupo());
			Messagebox
			.show(Labels.getLabel("abmCajaDatos.modifCaja"),
					Labels.getLabel("abmCajaDatos.titulo"),
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			logger.error("Error en la Modificación del Grupo - "
					+ e.getMessage());
			throw e;
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private GrupoDTO prepararGrupo() {
		GrupoDTO grupoDTO = new GrupoDTO();
		grupoDTO.setId(null);
		grupoDTO.setNombre(this.nombreCaja.getValue());
		grupoDTO.setDescripcion(this.descripcionCaja.getValue());
		grupoDTO.setUsuarioCreador(loggedUser);
		grupoDTO.setFechaCreacion((Date) Calendar.getInstance().getTime());
		grupoDTO.setUsuarioModificador(loggedUser);
		grupoDTO.setFechaModificacion((Date) Calendar.getInstance().getTime());

		grupoDTO.setGrupoComponentes(new LinkedHashSet(
				getListaGrupoComponente()));

		return grupoDTO;
	}

	public void onCancelar() throws Exception {
		try {
			Messagebox.show(Labels.getLabel("abmFC.salirPreg.msg"),
					Labels.getLabel(FFDDConstants.CONFIRMACIONTITLE), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (FFDDConstants.ONYES.equals(evt.getName())) {
								closeWindow();
							}
						}
					});
		} catch (Exception e) {
			logger.error("Error en el alta del FC - " + e.getMessage());
			throw e;
		}
	}

	public void onEliminar() throws InterruptedException {
		Messagebox.show(Labels.getLabel("abmCajaDatos.eliminarCaja.pregunta"),
				Labels.getLabel("abmCajaDatos.eliminarCaja.titulo"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws Exception {
						if (FFDDConstants.ONYES.equals(evt.getName())) {
							eliminarCaja();
						}
					}
				});
	}

	public void eliminarCaja() throws Exception {
		try {
			this.formularioService.eliminarCaja(this.selectedGrupo.getNombre());
			Messagebox
			.show(Labels.getLabel("abmCajaDatos.eliminarCaja"),
					Labels.getLabel("abmCajaDatos.eliminarCaja.titulo"),
					Messagebox.OK, Messagebox.INFORMATION);
			actualizarPantalla(this.verExistentes);
		} catch (Exception e) {
			logger.error(
					"Error al intentar eliminar la Caja de Datos - ",
					e.getMessage());
			throw e;
		}
	}

	public void onNuevo() throws Exception{
		if (!this.listaGrupoComponente.isEmpty()
				|| !this.nombreCaja.getValue().isEmpty()
				|| !this.descripcionCaja.getValue().isEmpty()) {
				Messagebox.show(Labels.getLabel("abmCajaDatos.nuevo.msj"),
						Labels.getLabel("abmCajaDatos.nuevo"), Messagebox.YES
								| Messagebox.NO, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt)
									throws Exception {
								if (FFDDConstants.ONYES.equals(evt.getName())) {
									try {
										actualizarPantalla(getVerExistentes());
									} catch (Exception e) {
										logger.error("Error al reiniciar la pantalla - "+e.getMessage());
										throw e;
									}
								}
							}
						});
			}else{
				actualizarPantalla(this.verExistentes);
			}	
	}
	
	public void onVerExistentes() throws DynFormException {
		this.busquedaCajaExistente.setDisabled(false);
		this.tituloExistenSugerencias.setLabel(Labels
				.getLabel("abmCajaDatos.headerExistentes"));
		this.listaGrupos = formularioService.obtenerTodosLosGrupos();
		this.verExistentes = true;
		sortListaExistentes();
		this.listboxCajasExistenSugerencias.setModel(new ListModelArray(
				this.listaGrupos));
	}

	public void onModificar() {
		this.componenteCaja.setDraggable("true");
		this.componenteCaja.setDroppable("true");
		this.listboxComponentesInstancia.setDroppable("true");
		this.itemComponente.setDraggable("true");
		this.nombreComponente.setReadonly(false);
		this.etiquetaComponente.setReadonly(false);
		this.descripcionCaja.setReadonly(false);
		this.modificarGrupo = true;
		this.cajaDeDatosBinder.loadAll();
	}

	public void sortListaExistentes(){
		if(!this.listaGrupos.isEmpty()){
			Collections.sort(this.listaGrupos, new Comparator<GrupoDTO>(){
				@Override
		        public int compare(GrupoDTO object1, GrupoDTO object2){
					return object1.getNombre().trim().toLowerCase().compareTo(object2.getNombre().trim().toLowerCase());
				} 
			});
		}
	}
	
	public void closeWindow() {
		this.windowCajas.onClose();
	}

	public List<GrupoComponenteDTO> getListaGrupoComponente() {
		return listaGrupoComponente;
	}

	public void setListaGrupoComponente(
			List<GrupoComponenteDTO> listaGrupoComponente) {
		this.listaGrupoComponente = listaGrupoComponente;
	}

	public void setListaComponente(List<ComponenteDTO> listaComponente) {
		this.listaComponente = listaComponente;
	}

	public List<ComponenteDTO> getListaComponente() {
		return listaComponente;
	}

	public void setListboxComponentes(Listbox listboxComponentes) {
		this.listboxComponentes = listboxComponentes;
	}

	public Listbox getListboxComponentes() {
		return listboxComponentes;
	}

	public void setListaTipoComponente(Set<String> listaTipoComponente) {
		this.listaTipoComponente = listaTipoComponente;
	}

	public Set<String> getListaTipoComponente() {
		return listaTipoComponente;
	}

	public void setSelectedComponente(ComponenteDTO selectedComponente) {
		this.selectedComponente = selectedComponente;
	}

	public ComponenteDTO getSelectedComponente() {
		return selectedComponente;
	}

	public Listbox getListboxComponentesInstancia() {
		return listboxComponentesInstancia;
	}

	public void setListboxComponentesInstancia(
			Listbox listboxComponentesInstancia) {
		this.listboxComponentesInstancia = listboxComponentesInstancia;
	}

	public List<GrupoDTO> getListaGrupos() {
		return listaGrupos;
	}

	public GrupoDTO getSelectedGrupo() {
		return selectedGrupo;
	}

	public void setSelectedGrupo(GrupoDTO selectedGrupo) {
		this.selectedGrupo = selectedGrupo;
	}

	public void setListaGrupos(List<GrupoDTO> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public Textbox getEtiquetaComponente() {
		return etiquetaComponente;
	}

	public void setEtiquetaComponente(Textbox etiquetaComponente) {
		this.etiquetaComponente = etiquetaComponente;
	}

	public Listitem getItemComponente() {
		return itemComponente;
	}

	public void setItemComponente(Listitem itemComponente) {
		this.itemComponente = itemComponente;
	}

	public boolean getVerExistentes() {
		return verExistentes;
	}

	public void setVerExistentes(boolean verExistentes) {
		this.verExistentes = verExistentes;
	}
	
	
	
}
