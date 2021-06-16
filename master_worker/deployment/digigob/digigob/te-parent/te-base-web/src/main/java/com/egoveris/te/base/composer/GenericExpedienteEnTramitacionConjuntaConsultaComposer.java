/**
 * 
 */
package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;


/**
 * @author jnorvert
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenericExpedienteEnTramitacionConjuntaConsultaComposer extends GenericForwardComposer {
	
	final static Logger logger = LoggerFactory.getLogger(GenericExpedienteEnTramitacionConjuntaConsultaComposer.class);

	private static final long serialVersionUID = 8164654109958798706L;

	public List<ExpedienteAsociadoEntDTO> listaExpedienteEnTramitacionConjunta = new ArrayList<>();
	public ExpedienteAsociadoEntDTO selectedExpedienteEnTramitacionConjunta;
	private Listbox expedienteEnTramitacionConjuntaComponent;

	private String codigoExpedienteElectronico;
	protected Task workingTask = null;
	private ExpedienteElectronicoDTO expedienteElectronico;
	
	@Autowired
	private AnnotateDataBinder binder;
	
	/**
	 * Defino los servicios que voy a utilizar
	 */
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	@WireVariable(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE)
	private TramitacionConjuntaService tramitacionConjuntaService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		comp.addEventListener(Events.ON_NOTIFY, new TramitacionConjuntaOnNotifyWindowListener(this));
		
		this.setWorkingTask((Task) comp.getDesktop().getAttribute(
				"selectedTask"));

		try {
			this.codigoExpedienteElectronico = (String) Executions.getCurrent()
					.getDesktop().getAttribute("codigoExpedienteElectronico");
			
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new WrongValueException(
					"Error al obtener el Expediente Electrónico seleccionado.");
		}
			String tipoActaucion = BusinessFormatHelper
					.obtenerActuacion(codigoExpedienteElectronico);
			Integer anio = BusinessFormatHelper
					.obtenerAnio(codigoExpedienteElectronico);
			Integer numero = BusinessFormatHelper.obtenerNumeroSade(codigoExpedienteElectronico);
			String reparticion = BusinessFormatHelper
					.obtenerReparticionUsuario(codigoExpedienteElectronico);
			this.expedienteElectronico = expedienteElectronicoService
					.obtenerExpedienteElectronico(tipoActaucion, anio, numero,
							reparticion);

		/**
		 * Si es cabecera y la lista tiene datos = es expediente en tramitación
		 * conjunta.
		 */
		
		cargarExpedientesAsociadosATramitacionConjunta();

	}

	private void cargarExpedientesAsociadosATramitacionConjunta() {

		if (this.expedienteElectronico != null) {
			for (ExpedienteAsociadoEntDTO expTramitacionConjunta : this.expedienteElectronico
					.getListaExpedientesAsociados()) {

				if (expTramitacionConjunta.getEsExpedienteAsociadoTC() != null
						&& expTramitacionConjunta.getEsExpedienteAsociadoTC()) {

					this.listaExpedienteEnTramitacionConjunta
							.add(expTramitacionConjunta);
				}
			}

			expedienteEnTramitacionConjuntaComponent
					.setModel(new BindingListModelList(
							this.listaExpedienteEnTramitacionConjunta, true));
		}
	}

	
	public void onVerExpediente() throws SuspendNotAllowedException, InterruptedException {
		DetalleExpedienteElectronicoComposer.openInModal(this.self, this.selectedExpedienteEnTramitacionConjunta.getAsNumeroSade());
	}


	public void refreshInbox() {
		this.binder
				.loadComponent(this.expedienteEnTramitacionConjuntaComponent);
	}

	final class TramitacionConjuntaOnNotifyWindowListener implements
			EventListener {
		private GenericExpedienteEnTramitacionConjuntaConsultaComposer composer;

		public TramitacionConjuntaOnNotifyWindowListener(
				GenericExpedienteEnTramitacionConjuntaConsultaComposer comp) {
			this.composer = comp;
		}

		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				this.composer.refreshInbox();
			}
		}

	}

	/**
	 * GETTERS & SETTERS
	 */
	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public String getCodigoExpedienteElectronico() {
		return codigoExpedienteElectronico;
	}

	public void setCodigoExpedienteElectronico(
			String codigoExpedienteElectronico) {
		this.codigoExpedienteElectronico = codigoExpedienteElectronico;
	}

	public List<ExpedienteAsociadoEntDTO> getListaExpedienteEnTramitacionConjunta() {
		return listaExpedienteEnTramitacionConjunta;
	}

	public void setListaExpedienteEnTramitacionConjunta(
			List<ExpedienteAsociadoEntDTO> listaExpedienteEnTramitacionConjunta) {
		this.listaExpedienteEnTramitacionConjunta = listaExpedienteEnTramitacionConjunta;
	}

	public ExpedienteAsociadoEntDTO getSelectedExpedienteEnTramitacionConjunta() {
		return selectedExpedienteEnTramitacionConjunta;
	}

	public void setSelectedExpedienteEnTramitacionConjunta(
			ExpedienteAsociadoEntDTO selectedExpedienteEnTramitacionConjunta) {
		this.selectedExpedienteEnTramitacionConjunta = selectedExpedienteEnTramitacionConjunta;
	}

	public TramitacionConjuntaService getTramitacionConjuntaService() {
		return tramitacionConjuntaService;
	}

	public void setTramitacionConjuntaService(
			TramitacionConjuntaService tramitacionConjuntaService) {
		this.tramitacionConjuntaService = tramitacionConjuntaService;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

}
