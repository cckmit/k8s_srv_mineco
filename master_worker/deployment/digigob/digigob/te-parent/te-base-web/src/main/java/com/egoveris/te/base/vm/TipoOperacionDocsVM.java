package com.egoveris.te.base.vm;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;
import com.egoveris.te.base.service.iface.ITipoOperacionDocService;
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
import org.zkoss.bind.annotation.BindingParam;
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
public class TipoOperacionDocsVM {
	
	private static final Logger logger = LoggerFactory.getLogger(TipoOperacionDocsVM.class);
	public static final String LITERAL_KEY_TITULO_ERROR_SERVICIO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicio.title";
	public static final String LITERAL_KEY_ERROR_SERVICIO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.errorServicioTipoDocs";
	
	@WireVariable(ConstantesServicios.TIPO_OPERACION_DOC_SERVICE)
	private ITipoOperacionDocService tipoOperDocService;
	
	private TipoOperacionDTO tipoOperacion;
	
	// Se usa un aux (por el buscador) y el *Sel hace referencia a los elemento seleccionados
	private List<TipoOperacionDocDTO> tiposDocumentos;
	private List<TipoOperacionDocDTO> tiposDocumentosAux;
	private List<TipoOperacionDocDTO> tiposDocumentosSel;
	private String inputBusqueda;
	
	/**
	 * Init de TipoOperacionDocsVM
	 * Inicializa los beans de servicio y carga la grilla de tipo documentos
	 * 
	 * @param tipoOperacion
	 */
	@Init
	public void init(@ExecutionArgParam("tipoOperacion") TipoOperacionDTO tipoOperacion) {
		setTipoOperacion(tipoOperacion);
		cargarTiposDocumentos();
		BindUtils.postGlobalCommand(null, null, "refreshTiposDocumentos", null);
		updateTipoDocsSeleccionados();
	}
	
	/**
	 * Metodo que se encarga de cargar los tipos de documentos, tanto los posibles para seleccionar
	 * como los que tenga previamente seleccionados el tipo de operacion
	 */
	private void cargarTiposDocumentos() {
		try {
			setTiposDocumentos(tipoOperDocService.getPosiblesTiposDocumentos(tipoOperacion));
			
			for (TipoOperacionDocDTO tipoOperacionDocDTO : tiposDocumentos) {
				tipoOperacionDocDTO.setOpcional(true); // Sera opcional por defecto
			}
			
			if (tiposDocumentos != null && tipoOperacion != null && tipoOperacion.getTiposOpDocumento() != null) {
				setTiposDocSeleccionados(getTiposDocumentos(), tipoOperacion); // Refactor
			}
		} catch (ServiceException e) {
			setTiposDocumentos(null);
			setTiposDocumentosSel(null);
			
			logger.error("Error en TipoOperacionDocsVM.cargarTiposDocumento(): ", e);
			Messagebox.show(Labels.getLabel(LITERAL_KEY_ERROR_SERVICIO), Labels.getLabel(LITERAL_KEY_TITULO_ERROR_SERVICIO), Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	/**
	 * Arma una List<TipoOperacionDocDTO> con los tipos de documento
	 * previamente seleccionados. Si bien esto se puede obtener a traves de TipoOperacion.getTiposOpDocumento()
	 * se recorre la lista completa de tipos de documentos, seleccionando unicamente los que le correspondan
	 * previamente al tipo de operacion. Se hace asi para que a nivel de serializacion de objeto sean iguales y
	 * funcione correctamente con la propiedad selectedItems del Listbox
	 * 
	 * @param tiposDocumentos
	 * @param tipoOperacion
	 */
	private void setTiposDocSeleccionados(List<TipoOperacionDocDTO> tiposDocumentos, TipoOperacionDTO tipoOperacion) {
		for (TipoOperacionDocDTO tipoOperacionDoc : tipoOperacion.getTiposOpDocumento()) {
			for (TipoOperacionDocDTO tipoOperacionDocPosible : tiposDocumentos) {
				if (tipoOperacionDocPosible.getTipoDocumentoGedo().getId().equals(tipoOperacionDoc.getTipoDocumentoGedo().getId())) {		
					tipoOperacionDocPosible.setOpcional(tipoOperacionDoc.isOpcional());
					tipoOperacionDocPosible.setObligatorio(tipoOperacionDoc.isObligatorio());
					getTiposDocumentosSel().add(tipoOperacionDocPosible);
				}
			}
		}
	}
	
	/**
	 * Comando global que refresca la
	 * grilla de tipos de documento
	 */
	@GlobalCommand
	@NotifyChange({"tiposDocumentos", "tiposDocumentosSel"})
	public void refreshTiposDocumentos() {
		// Refresh
	}
	
	/**
	 * Comando que se ejecuta al momento de seleccionar
	 * si un tipo de documento es obligatorio o opcional
	 * 
	 * @param tipoDocOper Tipo de documento
	 * @param valor Puede ser 1 = Opcional o 2 = Obligatorio
	 */
	@Command
	@NotifyChange({"tiposDocumentos"})
	public void checkRadioOpcObligatorio(@BindingParam("tipoDocOper") TipoOperacionDocDTO tipoDocOper,
			@BindingParam("valor") Integer valor) {
		// 1 : Opcional | 2: Obligatorio
		if (tipoDocOper != null && valor != null && valor.equals(1)) {
			tipoDocOper.setOpcional(true);
			tipoDocOper.setObligatorio(false);
		}
		else if (tipoDocOper != null && valor != null && valor.equals(2)) {
			tipoDocOper.setOpcional(false);
			tipoDocOper.setObligatorio(true);
		}
		
		updateTipoDocsSeleccionados();
	}
	
	/**
	 * Busca tipos de documento por filtro
	 */
	@Command
	@NotifyChange({"tiposDocumentos", "inputBusqueda"})
	public void onBusquedaTipoDocumentos() {
		if (inputBusqueda != null && "".equals(StringUtils.trim(inputBusqueda))) {
			onLimpiarBusqueda();
		}
		else if (inputBusqueda != null && tiposDocumentos != null) {
			if (tiposDocumentosAux == null) {
				tiposDocumentosAux = new ArrayList<>();
				tiposDocumentosAux.addAll(tiposDocumentos);
			}
			
			Pattern pattern = Pattern.compile(".*" + inputBusqueda.toLowerCase() + ".*");
			
			tiposDocumentos.clear();
			tiposDocumentos.addAll(busquedaTipoDocumentos(tiposDocumentosAux, pattern)); // Refactor
		}
	}
	
	/**
	 * Busca los tipos de documentos por nombre y por acronimo
	 * segun el pattern dado
	 * 
	 * @param tiposDocumentos
	 * @param pattern
	 * @return
	 */
	private List<TipoOperacionDocDTO> busquedaTipoDocumentos(List<TipoOperacionDocDTO> tiposDocumentos, Pattern pattern) {
		List<TipoOperacionDocDTO> tipoDocsEncontrados = new ArrayList<>();
		
		for (TipoOperacionDocDTO tipoOperacionDocDTO : tiposDocumentos) {
			boolean added = false;
			
			if (tipoOperacionDocDTO.getTipoDocumentoGedo() != null
					&& tipoOperacionDocDTO.getTipoDocumentoGedo().getNombre() != null
					&& pattern.matcher(tipoOperacionDocDTO.getTipoDocumentoGedo().getNombre().toLowerCase()).matches()) {
				tipoDocsEncontrados.add(tipoOperacionDocDTO);
				added = true;
			}
			
			if (!added && tipoOperacionDocDTO.getTipoDocumentoGedo() != null
					&& tipoOperacionDocDTO.getTipoDocumentoGedo().getAcronimo() != null
					&& pattern.matcher(tipoOperacionDocDTO.getTipoDocumentoGedo().getAcronimo().toLowerCase()).matches()) {
				tipoDocsEncontrados.add(tipoOperacionDocDTO);
			}
		}
		
		return tipoDocsEncontrados;
	}
	
	/**
	 * Limpia los filtros del buscador de tipos de documento
	 */
	@Command
	@NotifyChange({"tiposDocumentos", "inputBusqueda"})
	public void onLimpiarBusqueda() {
		inputBusqueda = "";
		
		if (tiposDocumentosAux != null) {
			tiposDocumentos.clear();
			tiposDocumentos.addAll(tiposDocumentosAux);
		}
	}
	
	/**
	 * Postea el comando global 'updTipoDocsSeleccionados' que
	 * esta en TipoOperacionesVM para que se actualice la lista
	 * de tipo documentos en dicha clase
	 */
	@Command
	public void updateTipoDocsSeleccionados() {
		Map<String, Object> args = new HashMap<>();
		args.put("tiposDocumentosSel", tiposDocumentosSel);
		
		BindUtils.postGlobalCommand(null, EventQueues.APPLICATION, "updTipoDocsSeleccionados", args);
	}
	
	public boolean isTipoDocSeleccionado(@BindingParam("tipoDocOper") TipoOperacionDocDTO tipoDocOper) {
		return getTiposDocumentosSel().contains(tipoDocOper);
	}
	
	// GETTERS - SETTERS
	
	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public List<TipoOperacionDocDTO> getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(List<TipoOperacionDocDTO> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public List<TipoOperacionDocDTO> getTiposDocumentosAux() {
		return tiposDocumentosAux;
	}

	public void setTiposDocumentosAux(List<TipoOperacionDocDTO> tiposDocumentosAux) {
		this.tiposDocumentosAux = tiposDocumentosAux;
	}

	public List<TipoOperacionDocDTO> getTiposDocumentosSel() {
		if (tiposDocumentosSel == null) {
			tiposDocumentosSel = new ArrayList<>();
		}
		
		return tiposDocumentosSel;
	}

	public void setTiposDocumentosSel(List<TipoOperacionDocDTO> tiposDocumentosSel) {
		this.tiposDocumentosSel = tiposDocumentosSel;
	}

	public String getInputBusqueda() {
		return inputBusqueda;
	}

	public void setInputBusqueda(String inputBusqueda) {
		this.inputBusqueda = inputBusqueda;
	}
	
}