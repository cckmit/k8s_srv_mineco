package com.egoveris.deo.web.satra.tipos;

import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.FormatoTamanoArchivoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosPKDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.renderers.ExtensionTipoDocRowRenderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ExtensionesPermitidasTipoDocComposer extends GEDOGenericForwardComposer {

	/**
	* 
	*/
	private static final long serialVersionUID = 3487295074112444769L;

	private static final String FORMATO = "formato";

	private Window extensionesArchivosWindow;
	private Grid grillaExtensiones;
	private List<FormatoTamanoArchivoDTO> listaExtensiones;
	private List<TipoDocumentoEmbebidosDTO> listaExtensionesPermitidas;
	private FormatoTamanoArchivoDTO selectedExtension;
	private Textbox descripcionTB;
	private Combobox comboExtensiones;
	private Button agregarExt;
	private AnnotateDataBinder binder;
	private TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos;
	private TipoDocumentoDTO tipoDocumento;
	String loggedUsername;
	private boolean vacio = false;

	@WireVariable("tipoDocumentoServiceImpl")
	private TipoDocumentoService tipoDocumentoService;

	@Override
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(Constantes.SESSION_USERNAME);
		this.tipoDocumento = (TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDocumento");
		this.listaExtensionesPermitidas = new ArrayList<>(tipoDocumento.getTipoDocumentoEmbebidos());

		if (this.listaExtensionesPermitidas == null) {
			listaExtensionesPermitidas = new ArrayList<>();
		}
		this.agregarExt.setDisabled(true);
		this.listaExtensiones = this.tipoDocumentoService.buscarFormatosArchivos();
		binder = new AnnotateDataBinder(component);
		this.binder.getAllBindings();
		this.binder.loadAll();
	}

	public void onChange$comboExtensiones() {
		this.agregarExt.setDisabled(false);
		this.descripcionTB.setValue(this.selectedExtension.getDescripcion());

	}

	public void onClick$agregarExt() throws InterruptedException {

		boolean flag = false;
		if (this.selectedExtension != null) {
			if (listaExtensionesPermitidas != null) {
				for (TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidosPP : listaExtensionesPermitidas) {
					if (tipoDocumentoEmbebidosPP.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato()
							.equals(this.selectedExtension.getFormato())) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				Messagebox.show(Labels.getLabel("gedo.extensionesPermitidasTipDoc.msgbox.extSeEncuentra"),
						Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
			} else {
				this.aniadirExtension();
			}
		} else {
			Messagebox.show(Labels.getLabel("gedo.extensionesPermitidasTipDoc.msgbox.seleccionarExtension"),
					Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
		}

	}

	private void aniadirExtension() {

		this.tipoDocumentoEmbebidos = new TipoDocumentoEmbebidosDTO();
		TipoDocumentoEmbebidosPKDTO tipoDocumentoEmbebidosPKDTO = new TipoDocumentoEmbebidosPKDTO();
		this.tipoDocumentoEmbebidos.setTipoDocumentoEmbebidosPK(tipoDocumentoEmbebidosPKDTO);
		if (this.tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().getTipoDocumentoId() == null) {
			this.tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().setTipoDocumentoId(this.tipoDocumento);
		}
		this.tipoDocumentoEmbebidos.setUserName(this.loggedUsername);
		this.tipoDocumentoEmbebidos.setFechaCreacion(new Date());
		this.tipoDocumentoEmbebidos.setDescripcion(this.descripcionTB.getValue());
		this.tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().setFormatoTamanoId(this.selectedExtension);
		this.listaExtensionesPermitidas.add(this.tipoDocumentoEmbebidos);

		this.descripcionTB.setText("");
		this.comboExtensiones.setValue("");
		this.grillaExtensiones.setRowRenderer(new ExtensionTipoDocRowRenderer());
		this.binder.loadComponent(this.grillaExtensiones);
	}

	public void onIntbox(Event evt) {
		ForwardEvent fe = (ForwardEvent) evt;
		Event event = fe.getOrigin();
		Intbox tamanio = (Intbox) event.getTarget();

		if (tamanio.getValue() == null) {
			this.vacio = true;
			throw new WrongValueException(tamanio, "Vacio o nulo no es permitido");
		} else {
			if (tamanio.getValue() <= 0) {
				this.vacio = true;
				throw new WrongValueException(tamanio,
						Labels.getLabel("gedo.extensionesPermitidasTipDoc.exception.valorMayorCero"));
			} else {
				this.vacio = false;
				String formato = (String) tamanio.getAttribute(FORMATO);
				for (int i = 0; i < listaExtensionesPermitidas.size(); i++) {
					if (listaExtensionesPermitidas.get(i).getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato().equals(formato)) {
						listaExtensionesPermitidas.get(i).setSizeArchivoEmb(tamanio.getValue());
					}
				}
			}
		}

	}

	public void onCheck(Event evt) {
		ForwardEvent fe = (ForwardEvent) evt;
		Event event = fe.getOrigin();
		Checkbox check = (Checkbox) event.getTarget();

		String formato = (String) check.getAttribute(FORMATO);
		for (int i = 0; i < listaExtensionesPermitidas.size(); i++) {

			if (listaExtensionesPermitidas.get(i).getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato().equals(formato)) {
				if (check.isChecked()) {
					listaExtensionesPermitidas.get(i).setObligatorio(true);
				} else if (!check.isChecked()) {
					listaExtensionesPermitidas.get(i).setObligatorio(false);
				}
			}
		}
	}

	public void onEliminar(Event evt) {

		// The event is a ForwardEvent...
		ForwardEvent fe = (ForwardEvent) evt;
		// Getting the original Event
		Event event = fe.getOrigin();
		// Getting the component that triggered the original event (i.e. the
		// button)
		Button btn = (Button) event.getTarget();

		String formato = (String) btn.getAttribute(FORMATO);

		for (int i = 0; i < listaExtensionesPermitidas.size(); i++) {

			if (listaExtensionesPermitidas.get(i).getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getFormato().equals(formato)) {
				listaExtensionesPermitidas.remove(i);
			}
		}

		this.binder.loadComponent(this.grillaExtensiones);
	}

	public void onClick$btnGuardar() {
		Map<String, Object> map = new HashMap<>();

		if (!this.vacio) {
			if (this.listaExtensionesPermitidas.isEmpty()) {
				throw new WrongValueException(
						Labels.getLabel("gedo.extensionesPermitidasTipDoc.exception.ingresarExtencion"));
			} else {
				for (TipoDocumentoEmbebidosDTO tde : listaExtensionesPermitidas) {
					if (tde.getSizeArchivoEmb() == null) {
						tde.setSizeArchivoEmb(tde.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getTamano());
					}
				}
				this.tipoDocumento.getTipoDocumentoEmbebidos().clear();
				this.tipoDocumento.getTipoDocumentoEmbebidos().addAll(listaExtensionesPermitidas);
			}
			map.put("extensiones", this.listaExtensionesPermitidas);
			map.put("origen", Constantes.EVENTO_TIPO_DOC_EMBEBIDOS);
			this.closeAndNotifyAssociatedWindow(map);
		}

	}

	public Window getExtensionesArchivosWindow() {
		return extensionesArchivosWindow;
	}

	public void setExtensionesArchivosWindow(Window extensionesArchivosWindow) {
		this.extensionesArchivosWindow = extensionesArchivosWindow;
	}

	public List<FormatoTamanoArchivoDTO> getListaExtensiones() {
		return listaExtensiones;
	}

	public void setListaExtensiones(List<FormatoTamanoArchivoDTO> listaExtensiones) {
		this.listaExtensiones = listaExtensiones;
	}

	public List<TipoDocumentoEmbebidosDTO> getListaExtensionesPermitidas() {
		return listaExtensionesPermitidas;
	}

	public void setListaExtensionesPermitidas(List<TipoDocumentoEmbebidosDTO> listaExtensionesPermitidas) {
		this.listaExtensionesPermitidas = listaExtensionesPermitidas;
	}

	public FormatoTamanoArchivoDTO getSelectedExtension() {
		return selectedExtension;
	}

	public void setSelectedExtension(FormatoTamanoArchivoDTO selectedExtension) {
		this.selectedExtension = selectedExtension;
	}

	public Textbox getDescripcionTB() {
		return descripcionTB;
	}

	public void setDescripcionTB(Textbox descripcionTB) {
		this.descripcionTB = descripcionTB;
	}

	public Combobox getComboExtensiones() {
		return comboExtensiones;
	}

	public void setComboExtensiones(Combobox comboExtensiones) {
		this.comboExtensiones = comboExtensiones;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Grid getGrillaExtensiones() {
		return grillaExtensiones;
	}

	public void setGrillaExtensiones(Grid grillaExtensiones) {
		this.grillaExtensiones = grillaExtensiones;
	}

	public TipoDocumentoEmbebidosDTO getTipoDocumentoEmbebidos() {
		return tipoDocumentoEmbebidos;
	}

	public void setTipoDocumentoEmbebidos(TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos) {
		this.tipoDocumentoEmbebidos = tipoDocumentoEmbebidos;
	}

	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}
