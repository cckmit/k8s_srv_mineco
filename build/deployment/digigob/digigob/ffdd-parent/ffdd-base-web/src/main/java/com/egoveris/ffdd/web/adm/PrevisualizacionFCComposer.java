package com.egoveris.ffdd.web.adm;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;

@VariableResolver(DelegatingVariableResolver.class)
public class PrevisualizacionFCComposer extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PrevisualizacionFCComposer.class);

	private static final String FC_INFORMACION = "fc.informacion";
	private static final String PREVISUALIZACION_OK = "abmCajaDatos.previsualizacionOk";

	// Beans
	@WireVariable("zkFormManagerFactory")
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;
	@WireVariable
	private IFormularioService formularioService;

	private IFormManager<Component> formManager;
	// Componentes ZUL
	private Window previsualizacionFC;
	private Hbox fcWindowHbox;
	private Div fcWindowDiv;

	// Atributos
	private String nombreFC;
	private String modo;
	private FormularioDTO formulario;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.self.addEventListener(Events.ON_CLOSE, new PrevizualizacionFCComposerListener(this));

		this.nombreFC = (String) Executions.getCurrent().getArg().get("formularioControlado");
		this.modo = (String) Executions.getCurrent().getArg().get("modo");
		this.formulario = formularioService.buscarFormularioPorNombre(this.nombreFC);

		if (null != formulario) {
			this.fcWindowDiv.setVisible(true);
			formManager = formManagerFact.create(formulario);
			fcWindowHbox.appendChild(formManager.getFormComponent());
		}
	}

	public void onPruebaGuardar() throws InterruptedException {
		formManager.forzarValidacion(fcWindowHbox);
		Messagebox.show(Labels.getLabel(PREVISUALIZACION_OK), Labels.getLabel(FC_INFORMACION),
				Messagebox.OK,
				Messagebox.INFORMATION);
	}

	public void onClose() {
		this.previsualizacionFC.onClose();
	}

	public void borradoTemporal() {
		try {
			if (!StringUtils.isEmpty(this.nombreFC) && !this.modo.equals(ABMFCComposer.MODO_CONSULTA_FC)) {
				for (FormularioComponenteDTO componente : this.formulario.getFormularioComponentes()) {
					if (componente.isMultilinea()) {
						this.formularioService.eliminarMultilinea(componente.getId());
					}
				}
				this.formularioService.eliminarFormulario(this.nombreFC);
			}
		} catch (Exception e) {
			logger.error("Ha ocurrido un error al borrar el Formulario Controlado para previsualizacion: "
					+ this.nombreFC + " - " + e.getMessage(), e);
		}
	}
}

final class PrevizualizacionFCComposerListener implements EventListener<Event> {
	private PrevisualizacionFCComposer composer;

	public PrevizualizacionFCComposerListener(PrevisualizacionFCComposer comp) {
		this.composer = comp;
	}

	public void onEvent(Event event) throws Exception {

		if (event.getName().equals(Events.ON_CLOSE)) {
			composer.borradoTemporal();
		}
	}
}
