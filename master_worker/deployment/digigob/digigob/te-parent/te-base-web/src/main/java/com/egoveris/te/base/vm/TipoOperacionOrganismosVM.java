package com.egoveris.te.base.vm;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionOrganismoDTO;
import com.egoveris.te.base.service.iface.ITipoOperacionOrganismoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TipoOperacionOrganismosVM {
	
	private static final Logger logger = LoggerFactory.getLogger(TipoOperacionOrganismosVM.class);
	public static final String LITERAL_KEY_TITULO_ERROR_SERVICIO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicio.title";
	public static final String LITERAL_KEY_ERROR_SERVICIO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicioOrganismos";
	
	@WireVariable(ConstantesServicios.TIPO_OPERACION_ORGANISMO_SERVICE)
	private ITipoOperacionOrganismoService tipoOperOrganismoService;
	
	private TipoOperacionDTO tipoOperacion;
	
	// Se usa un aux (por el buscador) y el *Sel hace referencia a los elemento seleccionados
	private List<TipoOperacionOrganismoDTO> organismos;
	private List<TipoOperacionOrganismoDTO> organismosAux;
	private List<TipoOperacionOrganismoDTO> organismosSel;
	private String inputBusqueda;
	
	/**
	 * Init de TipoOperacionOrganismosVM
	 * Inicializa los beans de servicio y carga la grilla de organismos
	 * 
	 * @param tipoOperacion
	 */
	@Init
	public void init(@ExecutionArgParam("tipoOperacion") TipoOperacionDTO tipoOperacion) {
		setTipoOperacion(tipoOperacion);
		cargarOrganismos();
		BindUtils.postGlobalCommand(null, null, "refreshOrganismos", null);
		updateOrganismosSeleccionados();
	}
	
	/**
	 * Metodo que se encarga de cargar los organismos, tanto los posibles para seleccionar
	 * como los que tenga previamente seleccionados el tipo de operacion
	 */
	private void cargarOrganismos() {
		try {
			setOrganismos(tipoOperOrganismoService.getPosiblesOrganismos(tipoOperacion));
			
			if (organismos != null && tipoOperacion != null && tipoOperacion.getTiposOpDocumento() != null) {
				setOrganismosSeleccionados(getOrganismos(), tipoOperacion); // Refactor
			}
		} catch (ServiceException e) {
			setOrganismos(null);
			setOrganismosSel(null);
			
			logger.error("Error en TipoOperacionOrganismosVM.cargarOrganismos(): ", e);
			Messagebox.show(Labels.getLabel(LITERAL_KEY_ERROR_SERVICIO), Labels.getLabel(LITERAL_KEY_TITULO_ERROR_SERVICIO), Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	/**
	 * Arma una List<TipoOperacionOrganismoDTO> con los organismos
	 * previamente seleccionados. Si bien esto se puede obtener a traves de TipoOperacion.getOrganismos()
	 * se recorre la lista completa de organismos, seleccionando unicamente los que le correspondan
	 * previamente al tipo de operacion. Se hace asi para que a nivel de serializacion de objeto sean iguales y
	 * funcione correctamente con la propiedad selectedItems del Listbox
	 * 
	 * @param organismos
	 * @param tipoOperacion
	 */
	private void setOrganismosSeleccionados(List<TipoOperacionOrganismoDTO> organismos, TipoOperacionDTO tipoOperacion) {

		for (TipoOperacionOrganismoDTO organismoTipoOper : tipoOperacion.getOrganismos()) {
			for (TipoOperacionOrganismoDTO organismoPosible : organismos) {
				if (organismoPosible.getReparticion().getId() == organismoTipoOper.getReparticion().getId()) {
					getOrganismosSel().add(organismoPosible);
				}
			}
		}
	}
	
	/**
	 * Comando global que refresca la
	 * grilla de organismos
	 */
	@GlobalCommand
	@NotifyChange({"organismos", "organismosSel"})
	public void refreshOrganismos() {
		// Refresh
	}
	
	/**
	 * Busca organismos por filtro
	 */
	@Command
	@NotifyChange({"organismos", "inputBusqueda"})
	public void onBusquedaOrganismos() {
		if (inputBusqueda != null && "".equals(StringUtils.trim(inputBusqueda))) {
			onLimpiarBusqueda();
		}
		else if (inputBusqueda != null && organismos != null) {
			if (organismosAux == null) {
				organismosAux = new ArrayList<>();
				organismosAux.addAll(organismos);
			}
			
			Pattern pattern = Pattern.compile(".*" + inputBusqueda.toLowerCase() + ".*");
			
			organismos.clear();
			organismos.addAll(busquedaOrganismos(organismosAux, pattern)); // Refactor
		}
	}
	
	/**
	 * Busca los organismos por codigo y por nombre
	 * segun el pattern dado
	 * 
	 * @param organismos
	 * @param pattern
	 * @return
	 */
	private List<TipoOperacionOrganismoDTO> busquedaOrganismos(List<TipoOperacionOrganismoDTO> organismos, Pattern pattern) {
		List<TipoOperacionOrganismoDTO> organismosEncontrados = new ArrayList<>();
		
		for (TipoOperacionOrganismoDTO tipoOperacionOrganismoDTO : organismos) {
			boolean added = false;
			
			if (tipoOperacionOrganismoDTO.getReparticion() != null
					&& tipoOperacionOrganismoDTO.getReparticion().getCodigoReparticion() != null
					&& pattern.matcher(tipoOperacionOrganismoDTO.getReparticion().getCodigoReparticion().toLowerCase()).matches()) {
				organismosEncontrados.add(tipoOperacionOrganismoDTO);
				added = true;
			}
			
			if (!added && tipoOperacionOrganismoDTO.getReparticion() != null
					&& tipoOperacionOrganismoDTO.getReparticion().getNombreReparticion() != null
					&& pattern.matcher(tipoOperacionOrganismoDTO.getReparticion().getNombreReparticion().toLowerCase()).matches()) {
				organismosEncontrados.add(tipoOperacionOrganismoDTO);
			}
		}
		
		return organismosEncontrados;
	}
	
	/**
	 * Limpia los filtros del buscador de organismos
	 */
	@Command
	@NotifyChange({"organismos", "inputBusqueda"})
	public void onLimpiarBusqueda() {
		inputBusqueda = "";
		
		if (organismosAux != null) {
			organismos.clear();
			organismos.addAll(organismosAux);
		}
	}
	
	/**
	 * Postea el comando global 'updOrganismosSeleccionados' que
	 * esta en TipoOperacionesVM para que se actualice la lista
	 * de organismos en dicha clase
	 */
	@Command
	public void updateOrganismosSeleccionados() {
		Map<String, Object> args = new HashMap<>();
		args.put("organismosSel", organismosSel);
		
		BindUtils.postGlobalCommand(null, EventQueues.APPLICATION, "updOrganismosSeleccionados", args);
	}
	
	// GETTERS - SETTERS
	
	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	

	public List<TipoOperacionOrganismoDTO> getOrganismos() {
		return organismos;
	}

	public void setOrganismos(List<TipoOperacionOrganismoDTO> organismos) {
		this.organismos = organismos;
	}

	public List<TipoOperacionOrganismoDTO> getOrganismosAux() {
		return organismosAux;
	}

	public void setOrganismosAux(List<TipoOperacionOrganismoDTO> organismosAux) {
		this.organismosAux = organismosAux;
	}

	public List<TipoOperacionOrganismoDTO> getOrganismosSel() {
		if (organismosSel == null) {
			organismosSel = new ArrayList<>();
		}
		
		return organismosSel;
	}

	public void setOrganismosSel(List<TipoOperacionOrganismoDTO> organismosSel) {
		this.organismosSel = organismosSel;
	}

	public String getInputBusqueda() {
		return inputBusqueda;
	}

	public void setInputBusqueda(String inputBusqueda) {
		this.inputBusqueda = inputBusqueda;
	}
}