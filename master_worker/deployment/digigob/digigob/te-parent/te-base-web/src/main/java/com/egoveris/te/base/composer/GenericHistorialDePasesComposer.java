/**
 * 
 */
package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

/**
 * @author jnorvert
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenericHistorialDePasesComposer extends GenericForwardComposer {
	
	final static Logger logger = LoggerFactory.getLogger(GenericHistorialDePasesComposer.class);
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -1221519815487999347L;
	
	private List<HistorialOperacionDTO> pases = new ArrayList<>();
	private HistorialOperacionDTO selectedPase;
	private String codigoExpedienteElectronico;
	protected Task workingTask = null;
	
	@WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
	private HistorialOperacionService historialOperacionService;
	
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	@Autowired
	private Paging historialDePasesPaginator;
	@Autowired
	private Label labelPagina;
	@Autowired
	private Listbox consultaPasesList;
	@Autowired
	private AnnotateDataBinder binder;
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.binder = new AnnotateDataBinder(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		this.setWorkingTask((Task) comp.getDesktop().getAttribute("selectedTask"));
		
		try {
			Long idExpedienteElectronico = (Long) Executions
							.getCurrent().getDesktop().getAttribute(
									"idExpedienteElectronico");
			this.pases = (List<HistorialOperacionDTO>) historialOperacionService
					.buscarHistorialporExpediente(idExpedienteElectronico);
			
			logger.debug("Obtiene pases");
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new WrongValueException("Error al obtener el Expediente Electrónico seleccionado.");
		}
		
		
		if (this.pases.size() < 1) {

			Messagebox
			.show(Labels
					.getLabel("ee.caratulas.historial.expedienteNoExiste"),
					Labels.getLabel("ee.caratulas.historial.informacion.caratulaNoExiste"),
					Messagebox.OK, Messagebox.EXCLAMATION);
		}else{
			this.pases = ordenarPases(this.pases);
		}
		
		if(historialDePasesPaginator.getPageSize()==10 && this.pases.size()>10){
			labelPagina.setVisible(true);
		}

		binder.loadComponent(consultaPasesList);
	}
	
	  public void onVerMapa() throws Exception {
		  Map<String, Object> datos = new HashMap<>();
		  datos.put("pase", selectedPase);
		  Window win = (Window) Executions.createComponents("/expediente/geoLocation.zul",
		          this.self, datos);
		      win.doModal();
	  }

		  
	/**
	 * Ordena la lista de documentos de acuerdo al criterio del más reciente
	 * primero.
	 * 
	 * @param tasks
	 *            Lista de tareas a ordenar
	 * @return La lista de tareas ordenada de acuerdo al criterio enunciado más
	 *         arriba.
	 */
	private List<HistorialOperacionDTO> ordenarPases(final List<HistorialOperacionDTO> listaPases) {
		Collections.sort(listaPases, new PaseComparator());
		return listaPases;
	}


	public List<HistorialOperacionDTO> getPases() {
		return pases;
	}

	public void setPases(List<HistorialOperacionDTO> pases) {
		this.pases = pases;
	}

	public HistorialOperacionDTO getSelectedPase() {
		return selectedPase;
	}

	public void setSelectedPase(HistorialOperacionDTO selectedPase) {
		this.selectedPase = selectedPase;
	}

	public HistorialOperacionService getHistorialOperacionService() {
		return historialOperacionService;
	}

	public void setHistorialOperacionService(
			HistorialOperacionService historialOperacionService) {
		this.historialOperacionService = historialOperacionService;
	}

	public ExpedienteElectronicoService getExpedienteElectronicoService() {
		return expedienteElectronicoService;
	}

	public void setExpedienteElectronicoService(
			ExpedienteElectronicoService expedienteElectronicoService) {
		this.expedienteElectronicoService = expedienteElectronicoService;
	}
	
	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}
	
	public Listbox getConsultaPasesList() {
		return consultaPasesList;
	}

	public void setConsultaPasesList(Listbox consultaPasesList) {
		this.consultaPasesList = consultaPasesList;
	}

	final class PaseComparator implements Comparator<HistorialOperacionDTO> {

		/**
		 * Compara dos instancias de Documento y devuelve la comparación usando el
		 * criterio de más reciente primero en el orden.
		 * 
		 * @param t1
		 *            Primer tarea a comparar
		 * @param t2
		 *            Segunda tarea a comparar con la primera
		 */
		public int compare(HistorialOperacionDTO t1, HistorialOperacionDTO t2) {
			return t2.getFechaOperacion().compareTo(t1.getFechaOperacion());

		}
	}
}
