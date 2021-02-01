package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.ITipoComponenteService;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.ItemDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddMultivaloresVM {

	private static final Logger logger = LoggerFactory.getLogger(AddMultivaloresVM.class);

	@WireVariable("tipoComponenteService")
	private ITipoComponenteService tipoComponenteService;

	private List<ComponenteDTO> listaTipoComponente;
	private List<ComponenteDTO> listaMultivalores;
	private List<ComponenteDTO> listaMultivaloresAux;

	private ComponenteDTO comboboxModificacionConsulta;
	private ComponenteDTO selectedComboComponente;

	private List<ItemDTO> listaItemsMultivalue = null;
	private List<ItemDTO> selectedlistaItemsMultivalue = null;
	private ComboboxDTO elementoSeleccionado;
	private boolean modoLectura = false;

	@Wire
	Window previsualizarMultivaloresWindow;

	@Init
	@SuppressWarnings("unchecked")
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		this.listaTipoComponente = new ArrayList<>();
		this.comboboxModificacionConsulta = (ComponenteDTO) Executions.getCurrent().getArg()
				.get("comboboxModificacionConsulta");
		this.elementoSeleccionado = (ComboboxDTO) Executions.getCurrent().getArg().get("elementoSeleccionado");
		this.listaMultivaloresAux = (List<ComponenteDTO>) Executions.getCurrent().getArg().get("listaMultivalores");
		// Obtiene los valores posibles del combo listaTipoComponente
		try {
			for (ComponenteDTO componenteDTO : listaMultivaloresAux) {
				if (!componenteDTO.getNombre().trim().contains(comboboxModificacionConsulta.getNombre().trim())) {
					this.listaTipoComponente.add(componenteDTO);
				}
			}
		} catch (Exception e) {
			logger.error("Error al traer listado de componentes" + e.getMessage());
		}
		if (null != this.elementoSeleccionado.getId()) {
			List<ItemDTO> valoresMultivaloresAux = tipoComponenteService
					.obtenerMultivalue(this.elementoSeleccionado.getId());
			if (!valoresMultivaloresAux.isEmpty()) {
				setModoLectura(true);
				List<ItemDTO> valoresMultivalores = tipoComponenteService
						.obtenerMultivaloresAsociados(valoresMultivaloresAux.get(0).getComponente().getId());
				if (!valoresMultivalores.isEmpty()) {

					for (ComponenteDTO componenteDTO : listaTipoComponente) {
						if (componenteDTO.getId().equals(valoresMultivalores.get(0).getComponente().getId())) {
							selectedComboComponente = componenteDTO;
							listaItemsMultivalue = valoresMultivalores;
							selectedlistaItemsMultivalue = new ArrayList<>();
							for (ItemDTO item : valoresMultivalores) {
								if (null != item.getMultivalor() && item.getMultivalor().trim()
										.contains(this.elementoSeleccionado.getId().toString())) {
									selectedlistaItemsMultivalue.add(item);

								}
							}
							break;
						}
					}

				}
			}
		}

	}

	@Command
	@NotifyChange("listaItemsMultivalue")
	public void onMostrar() {
		listaItemsMultivalue = tipoComponenteService.obtenerMultivalores(selectedComboComponente.getId());
	}

	@Command
	@NotifyChange("previsualizarMultivaloresWindow")
	public void onInsertar() {
		try {
			List<ItemDTO> multivalores = new ArrayList<>();
			if (null == selectedlistaItemsMultivalue) {
				Messagebox.show(Labels.getLabel("AddMultivaloresVM.informacion.sinValorSeleccionado"),
						Labels.getLabel("AddMultivaloresVM.informacion.title"), Messagebox.OK, Messagebox.ERROR);
			}
			multivalores.addAll(selectedlistaItemsMultivalue);

			if (!multivalores.isEmpty() || null != elementoSeleccionado) {
				tipoComponenteService.saveMultivalores(multivalores, elementoSeleccionado.getId(),
						listaItemsMultivalue);
			}
			
			Messagebox.show(Labels.getLabel("AddMultivaloresVM.informacion.guardadoExitoso"),
					Labels.getLabel("AddMultivaloresVM.informacion.title"), Messagebox.OK,
					Messagebox.INFORMATION);

			previsualizarMultivaloresWindow.onClose();
		} catch (Exception e) {
			logger.error("Error" + e.getMessage());
		}

	}

	// GETTER AND SETTERS

	public List<ComponenteDTO> getListaTipoComponente() {
		return listaTipoComponente;
	}

	public void setListaTipoComponente(List<ComponenteDTO> listaTipoComponente) {
		this.listaTipoComponente = listaTipoComponente;
	}

	public List<ComponenteDTO> getListaMultivalores() {
		return listaMultivalores;
	}

	public void setListaMultivalores(List<ComponenteDTO> listaMultivalores) {
		this.listaMultivalores = listaMultivalores;
	}

	public List<ComponenteDTO> getListaMultivaloresAux() {
		return listaMultivaloresAux;
	}

	public void setListaMultivaloresAux(List<ComponenteDTO> listaMultivaloresAux) {
		this.listaMultivaloresAux = listaMultivaloresAux;
	}

	public ComponenteDTO getComboboxModificacionConsulta() {
		return comboboxModificacionConsulta;
	}

	public void setComboboxModificacionConsulta(ComponenteDTO comboboxModificacionConsulta) {
		this.comboboxModificacionConsulta = comboboxModificacionConsulta;
	}

	public ComponenteDTO getSelectedComboComponente() {
		return selectedComboComponente;
	}

	public void setSelectedComboComponente(ComponenteDTO selectedComboComponente) {
		this.selectedComboComponente = selectedComboComponente;
	}

	public List<ItemDTO> getListaItemsMultivalue() {
		return listaItemsMultivalue;
	}

	public void setListaItemsMultivalue(List<ItemDTO> listaItemsMultivalue) {
		this.listaItemsMultivalue = listaItemsMultivalue;
	}

	public List<ItemDTO> getSelectedlistaItemsMultivalue() {
		return selectedlistaItemsMultivalue;
	}

	public void setSelectedlistaItemsMultivalue(List<ItemDTO> selectedlistaItemsMultivalue) {
		this.selectedlistaItemsMultivalue = selectedlistaItemsMultivalue;
	}

	public ComboboxDTO getElementoSeleccionado() {
		return elementoSeleccionado;
	}

	public void setElementoSeleccionado(ComboboxDTO elementoSeleccionado) {
		this.elementoSeleccionado = elementoSeleccionado;
	}

	public Window getPrevisualizarMultivaloresWindow() {
		return previsualizarMultivaloresWindow;
	}

	public void setPrevisualizarMultivaloresWindow(Window previsualizarMultivaloresWindow) {
		this.previsualizarMultivaloresWindow = previsualizarMultivaloresWindow;
	}

	public boolean isModoLectura() {
		return modoLectura;
	}

	public void setModoLectura(boolean modoLectura) {
		this.modoLectura = modoLectura;
	}

}