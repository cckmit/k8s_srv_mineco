package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ComunicacionesServiceDummy;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TiposDocumentosComposer extends AbstractComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8820462252742629622L;
	@Autowired
	private Window tiposDocumentosWindow;
	@Autowired
	private List<TipoDocumentoDTO> tipoDocumentosGEDOCompleto = new ArrayList<>();
	private List<TrataTipoDocumentoDTO> tiposDocumentosGEDOBD = new ArrayList<>();
	private List<TrataTipoDocumentoDTO> tiposDocumentosPorTrata = new ArrayList<>();
	private List<TrataTipoDocumentoDTO> ttdModificados = new ArrayList<>();
	@Autowired
	private AnnotateDataBinder binder;
	@Autowired
	private Listbox tiposDocumentos;
	private TrataDTO trata;
	private String usuario;
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	TipoDocumentoService tipoDocumentoService;
	private int cantidadTTDSeleccionadas;

	public Window getTiposDocumentosWindow() {
		return tiposDocumentosWindow;
	}

	public void setTiposDocumentosWindow(Window tiposDocumentosWindow) {
		this.tiposDocumentosWindow = tiposDocumentosWindow;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public TrataDTO  getTrata() {
		return trata;
	}

	public void setTrata(TrataDTO trata) {
		this.trata = trata;
	}

	public List<TrataTipoDocumentoDTO> getTiposDocumentosGEDOHabilitados() {
		return tiposDocumentosGEDOBD;
	}

	public void setTiposDocumentosGEDOHabilitados(List<TrataTipoDocumentoDTO> tiposDocumentosGEDOHabilitados) {
		this.tiposDocumentosGEDOBD = tiposDocumentosGEDOHabilitados;
	}

	public Listbox getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(Listbox tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public TipoDocumentoService getTipoDocumentoService() {
		return tipoDocumentoService;
	}

	public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getCantidadTTDSeleccionadas() {
		return cantidadTTDSeleccionadas;
	}

	public void setCantidadTTDSeleccionadas(int cantidadTTDSeleccionadas) {
		this.cantidadTTDSeleccionadas = cantidadTTDSeleccionadas;
	}

	public List<TrataTipoDocumentoDTO> getTiposDocumentosPorTrata() {
		return tiposDocumentosPorTrata;
	}

	public void setTiposDocumentosPorTrata(List<TrataTipoDocumentoDTO> tiposDocumentosPorTrata) {
		this.tiposDocumentosPorTrata = tiposDocumentosPorTrata;
	}

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.binder = new AnnotateDataBinder(comp);

		this.usuario = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
 
		Execution exec = Executions.getCurrent();
		@SuppressWarnings("unchecked")
		Map<?, ?> map = exec.getArg();
		this.trata = (TrataDTO) map.get("trata");
		
		this.tipoDocumentosGEDOCompleto = this.tipoDocumentoService.obtenerTiposDocumentoGEDOHabilitados();

		agregarTiposDocumentosComunicaciones();

		this.tiposDocumentosGEDOBD = tipoDocumentoService.buscarTrataTipoDocumentoPorTrata(trata);
		if (this.tiposDocumentosGEDOBD.size() == 1
				&& ConstantesWeb.SELECCIONAR_TODOS.equals(this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO())) {

			this.cantidadTTDSeleccionadas = this.tipoDocumentosGEDOCompleto.size();
		} else {
			this.cantidadTTDSeleccionadas = this.tiposDocumentosGEDOBD.size();
		}
		agregarTrataTipoDocumentosNoSeleccionados();

		this.binder.loadAll();

	}

	private void agregarTrataTipoDocumentosNoSeleccionados() {
		TrataTipoDocumentoDTO todosTTD = null;
		this.tiposDocumentosPorTrata.clear();
		if (this.tiposDocumentosGEDOBD != null && this.tiposDocumentosGEDOBD.size() == 1
				&& ConstantesWeb.SELECCIONAR_TODOS.equals(this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO())) {
			todosTTD = this.tiposDocumentosGEDOBD.get(0);
			todosTTD.setEstaHabilitado(true);
			setearTodosTiposDocumentos(true);
		} else {
			todosTTD = new TrataTipoDocumentoDTO(ConstantesWeb.SELECCIONAR_TODOS);
			todosTTD.setEstaHabilitado(false);
			for (TipoDocumentoDTO td : this.tipoDocumentosGEDOCompleto) {
				TrataTipoDocumentoDTO ttd = completarTrataTipoDocumento(td);
				if (!this.tiposDocumentosPorTrata.contains(ttd)) {
					this.tiposDocumentosPorTrata.add(ttd);
				}
			}
		}
		Collections.sort(this.tiposDocumentosPorTrata);
		this.tiposDocumentosPorTrata.add(0, todosTTD);
	}

	private void setearTodosTiposDocumentos(boolean habilitado) {

		for (TipoDocumentoDTO td : this.tipoDocumentosGEDOCompleto) {
			this.tiposDocumentosPorTrata.add(nuevaTrataTipoDocumento(td, habilitado));
		}
	}

	private TrataTipoDocumentoDTO completarTrataTipoDocumento(TipoDocumentoDTO td) {

		for (TrataTipoDocumentoDTO trataTipoDocumento : this.tiposDocumentosGEDOBD) {
			if (trataTipoDocumento.getAcronimoGEDO().equals(td.getAcronimo())) {
				trataTipoDocumento.setEstaHabilitado(true);
				trataTipoDocumento.setNombre(td.getDescripcionTipoDocumentoSade());
				trataTipoDocumento.setActuacion(td.getCodigoTipoDocumentoSade());
				return trataTipoDocumento;
			}
		}
		TrataTipoDocumentoDTO nuevaTTD = nuevaTrataTipoDocumento(td, false);
		return nuevaTTD;
	}

	private TrataTipoDocumentoDTO nuevaTrataTipoDocumento(TipoDocumentoDTO td, boolean habilitado) {
		TrataTipoDocumentoDTO nuevaTTD = new TrataTipoDocumentoDTO(td.getAcronimo());
		nuevaTTD.setEstaHabilitado(habilitado);
		nuevaTTD.setNombre(td.getDescripcionTipoDocumentoSade());
		nuevaTTD.setActuacion(td.getCodigoTipoDocumentoSade());
		nuevaTTD.setTrata(trata);
		return nuevaTTD;
	}

	private void agregarTiposDocumentosComunicaciones() {
		List<TipoDocumentoDTO> comunicaciones = ComunicacionesServiceDummy.obtenerTiposDocumentoComunicaciones();
		if (comunicaciones != null && this.tipoDocumentosGEDOCompleto != null) {
			for (TipoDocumentoDTO td : comunicaciones) {
				this.tipoDocumentosGEDOCompleto.add(td);
			}
		}
	}

	public void onClick$cancelar() {
		((Window) this.self).detach();
	}

	public void onClick$guardar() throws InterruptedException {
		this.ttdModificados.clear();
		TrataTipoDocumentoDTO
		todos = this.tiposDocumentosPorTrata.get(0);
		if (todos.getEstaHabilitado()) {
			if (!this.tiposDocumentosGEDOBD.contains(todos)) {
				todos.setTrata(trata);
				todos.setIdTrata(trata.getId());
				actualizarTrataTipoDocumento(todos);
				for (TrataTipoDocumentoDTO ttd : this.tiposDocumentosGEDOBD) {
					ttd.setEstaHabilitado(false);
					ttdModificados.add(ttd);
				}
			}
		} else {
			for (TrataTipoDocumentoDTO ttd : this.tiposDocumentosPorTrata) {
				actualizarTrataTipoDocumento(ttd);
			}
		}
		tipoDocumentoService.actualizarTrataTipoDocumentos(ttdModificados, this.usuario);
		Messagebox.show(Labels.getLabel("te.base.composer.tipodocumentoscomposer.msj.guardados"), 
				Labels.getLabel("te.base.composer.tipodocumentoscomposer.msj.mensaje"), Messagebox.OK, Messagebox.INFORMATION,
				new EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.OK:
							break;
						}
					}
				});
		this.tiposDocumentosWindow.detach();
	}

	private void actualizarTrataTipoDocumento(TrataTipoDocumentoDTO ttd) {
		if (this.tiposDocumentosGEDOBD.contains(ttd)) {
			if (!ttd.getEstaHabilitado()) {
				ttdModificados.add(ttd);
				return;
			}
		}
		if (!this.tiposDocumentosGEDOBD.contains(ttd)) {
			if (ttd.getEstaHabilitado()) {
				ttdModificados.add(ttd);
				return;
			}
		}
	}

	public void onSelectTTD(Event event) throws InterruptedException {
		TrataTipoDocumentoDTO ttd = (TrataTipoDocumentoDTO) event.getData();
		ttd.setEstaHabilitado(!ttd.getEstaHabilitado());
		if (ttd.getAcronimoGEDO().equals(ConstantesWeb.SELECCIONAR_TODOS)) {
			selectAll(ttd.getEstaHabilitado());
		}
		actualizarCantidadHabilitadas(ttd);
		this.tiposDocumentosPorTrata.get(0)
				.setEstaHabilitado(tipoDocumentosGEDOCompleto.size() == this.cantidadTTDSeleccionadas);
		this.binder.loadComponent(tiposDocumentos);
	}

	private void actualizarCantidadHabilitadas(TrataTipoDocumentoDTO ttd) {
		if (ConstantesWeb.SELECCIONAR_TODOS.equals(ttd.getAcronimoGEDO())) {
			if (ttd.getEstaHabilitado()) {
				this.cantidadTTDSeleccionadas = this.tipoDocumentosGEDOCompleto.size();
			} else {
				this.cantidadTTDSeleccionadas = 0;
			}
		} else {
			if (ttd.getEstaHabilitado()) {
				this.cantidadTTDSeleccionadas++;
			} else {
				this.cantidadTTDSeleccionadas--;
			}
		}
	}

	private void selectAll(boolean habilitado) {

		for (TrataTipoDocumentoDTO ttd : this.tiposDocumentosPorTrata) {
			ttd.setEstaHabilitado(habilitado);
		}
		this.binder.loadComponent(tiposDocumentos);
	}

}
