
package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.rendered.DatosTrataCaratulaRenderer;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelMap;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DatosPropiosTrataCaratulaComposer extends EEGenericForwardComposer {

	/**
	 * @author jnorvert
	 * 
	 *         Se realizan todas las operaciones que se encuentran en la ventana
	 *         de los datos propios de la trata
	 */
	private static final long serialVersionUID = 2379520130043692172L;
	public static final String ES_SOLO_LECTURA = "esSoloLectura";
	public static final String METADATOS = "metadatos";
	@Autowired
	private Window datosPropiosDeTrataCaratulaWindow;
	@Autowired
	private Window datosPropiosTrataWindow;	
	@Autowired
	private Grid grillaDatos;
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private ProcessEngine processEngine;
	@Autowired
	private Textbox campoNuevo;
	@Autowired
	private Label idRequrido;
	@Autowired
	private Label idSinMetadatos;
	private List<ExpedienteMetadataDTO> datos;
	private List<ExpedienteMetadataDTO> metadatosOriginal;
	protected Task workingTask = null;
	@Autowired
	private AnnotateDataBinder binder;
	@Autowired
	private Button tramitacionGuardar;
	@Autowired 
	private Button guardar;
	ListModelMap strset = new ListModelMap();
	
	private Boolean esExpedienteElectronico;

	private static Logger logger = LoggerFactory
			.getLogger(DatosPropiosTrataCaratulaComposer.class);

	
	private boolean metaDatosTrataCargados;

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Window getDatosPropiosDeTrataCaratulaWindow() {
		return datosPropiosDeTrataCaratulaWindow;
	}

	public void setDatosPropiosDeTrataCaratulaWindow(
			Window datosPropiosDeTrataCaratulaWindow) {
		this.datosPropiosDeTrataCaratulaWindow = datosPropiosDeTrataCaratulaWindow;
	}

	public Button getButtonGuardar(){
		return guardar;
	}
	
	public void setButtonGuardar(Button guardar){
		this.guardar = guardar;
	}
	public Boolean getEsExpedienteElectronico() {
		if (esExpedienteElectronico == null) {
			this.esExpedienteElectronico = (Boolean) Executions.getCurrent()
					.getArg().get(ES_SOLO_LECTURA);
		}
		return esExpedienteElectronico;

	}
	

	public List<ExpedienteMetadataDTO> getDatos() {
		if (this.datos == null) {
			this.datos = new ArrayList<ExpedienteMetadataDTO>(this
					.getDatosPropiosTrataCaratula());
		}
		return datos;
	}

	public void setDatos(List<ExpedienteMetadataDTO> datos) {
		this.datos = datos;
	}

	public Grid getGrillaDatos() {
		return grillaDatos;
	}

	public void setGrillaDatos(Grid grillaDatos) {
		this.grillaDatos = grillaDatos;
	}

	public Textbox getCampoNuevo() {
		return campoNuevo;
	}

	public void setCampoNuevo(Textbox campoNuevo) {
		this.campoNuevo = campoNuevo;
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.tramitacionGuardar = (Button) Executions.getCurrent().getDesktop().getAttribute("guardar");
		grillaDatos.setRowRenderer(new DatosTrataCaratulaRenderer(getEsExpedienteElectronico()));
        guardar.setDisabled((Boolean) Executions.getCurrent().getArg().get(ES_SOLO_LECTURA));

		List<ExpedienteMetadataDTO> listaDP=this.getDatosPropiosTrataCaratula();
		if (listaDP.size() > 0)
		{
			for (int i=0;i<listaDP.size();i++)
			{
				ExpedienteMetadataDTO expedienteMetadata=(ExpedienteMetadataDTO) listaDP.get(i);
				
				if (expedienteMetadata.isObligatoriedad()) {
					idRequrido.setVisible(true);
					break;
				}
			}
		}
		else
		{
			this.grillaDatos.setVisible(false);
			this.idSinMetadatos.setVisible(true);
		}
	}

	@SuppressWarnings("unchecked")
	private List<ExpedienteMetadataDTO> getDatosPropiosTrataCaratula() {

		metadatosOriginal =  (List<ExpedienteMetadataDTO>) Executions.getCurrent().getArg().get(METADATOS);

		
		if(null == metadatosOriginal) {
			return new ArrayList<>();
		}
		return metadatosOriginal;
	}

	//	
	public void refreshGrid() {
		this.binder.loadComponent(this.grillaDatos);
	}

	/**
	 * 
	 * @param name
	 *            : nombre de la variable que quiero setear
	 * @param value
	 *            : valor de la variable
	 */
	public void setVariableWorkFlow(String name, Object value) {
		this.processEngine.getExecutionService().setVariable(
				this.workingTask.getExecutionId(), name, value);
	}

	/**
	 * 
	 * @param nameTransition
	 *            : nombre de la transición que voy a usar para la proxima tarea
	 * @param usernameDerivador
	 *            : usuario que va a tener asignada la tarea
	 */
	public void signalExecution(String nameTransition, String usernameDerivador) {
		processEngine.getExecutionService().signalExecutionById(
				this.workingTask.getExecutionId(), nameTransition);
	}

	@SuppressWarnings("unchecked")
	public void onGuardar()
	{
		List<Component> rows = (List<Component>) grillaDatos.getRows().getChildren();
		
		int i = 0;
		for (Component hijo : rows) 
		{
			List<?> lista = hijo.getChildren();
			for (Object object : lista) 
			{
				if ((object.getClass().equals(org.zkoss.zul.Hlayout.class))) 
				{
					Hlayout hlayout = (Hlayout) object;
					List<?> hijosHlayout = hlayout.getChildren();

					for (Object hijoLo : hijosHlayout)
					{
						if ((hijoLo.getClass().equals(org.zkoss.zul.Textbox.class)))
						{
							Textbox textbox = (Textbox) hijoLo;
							if ((this.datos.get(i).isObligatoriedad())
									&& (textbox.getValue() == null || textbox.getValue().equals("")))
								throw new WrongValueException(textbox,
										"Este campo es requerido. No puede contener valor nulo.");

							this.datos.get(i).setValor(textbox.getValue());
							i++;
						}

						if ((hijoLo.getClass().equals(org.zkoss.zul.Combobox.class)))
						{
							Combobox combobox = (Combobox) hijoLo;
							if ((this.datos.get(i).isObligatoriedad())
									&& (combobox.getValue() == null || combobox.getValue().equals("")))
								throw new WrongValueException(combobox,
										"Este campo es requerido. No puede contener valor nulo.");

							this.datos.get(i).setValor(combobox.getValue());
							i++;
						}
					}
				}
			}
		}
		if(tramitacionGuardar != null){
			tramitacionGuardar.setDisabled(false);
		}
		this.metadatosOriginal.clear();
		this.metadatosOriginal.addAll(this.datos);
		this.datosPropiosDeTrataCaratulaWindow.detach();
	}

	public void onCancelar()
	{
		if(this.datosPropiosDeTrataCaratulaWindow == null){
			this.datosPropiosTrataWindow.detach();
		}

		if(this.datosPropiosTrataWindow == null){
			this.datosPropiosDeTrataCaratulaWindow.detach();
		}
	}
	
	public void onClick$cerrar() {
		this.datosPropiosTrataWindow.detach();
	}

	public boolean isMetaDatosTrataCargados() {
		return metaDatosTrataCargados;
	}

	public void setMetaDatosTrataCargados(boolean metaDatosTrataCargados) {
		this.metaDatosTrataCargados = metaDatosTrataCargados;
	}
}

