
package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DatosVariablesComboGruposDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.TipoDatoPropioDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.rendered.DatosTrataRenderer;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window; 

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DatosPropiosTrataComposer extends EEGenericForwardComposer
{
	/**
	 * @author jnorvert
	 * 
	 *         Se realizan todas las operaciones que se encuentran en la ventana
	 *         de los datos propios de la trata
	 */
	private static final long serialVersionUID = 2379520130043692172L;
	
	private Window datosPropiosTrataWindow;
	private Grid grillaDatos;
	
	private List<TipoDatoPropioDTO> tiposDatosPropios;
	private List<DatosVariablesComboGruposDTO> gruposCombos;

	private TipoDatoPropioDTO selected;
	private DatosVariablesComboGruposDTO selectedCombo;
	
	private Combobox tipo_campo;
	private Combobox tipo_combo;

	private Row rowNuevoNombre;
	private Row rowTipoCombo;

	@Autowired
	private ProcessEngine processEngine;
	
	private Textbox nuevo_nombre;
	private List<MetadataDTO> datos;
	private List<MetadataDTO> datos2;
	protected Task workingTask = null;
	private AnnotateDataBinder binder;
	
	private Button agregar;
	private Button agregarCombo;
	
	private TrataDTO trata = null;
	
	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;
	
	public AnnotateDataBinder getBinder() {
		return binder;
	}
	
	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}
	
	public void setDatos(List<MetadataDTO> datos) {
		this.datos = datos;
	}
	
	public static List<MetadataDTO> cloneList (List<MetadataDTO> datos){
		List<MetadataDTO> clone = new ArrayList<MetadataDTO>();
		for(MetadataDTO metadata : datos){
			MetadataDTO p = (MetadataDTO) SerializationUtils.clone(metadata);
			clone.add(p);
		}
		return clone;
			
	}
	
	public List<MetadataDTO> getDatos() {
		if(this.datos == null){
			this.datos = new ArrayList<MetadataDTO>();
			datos2 = cloneList(datos);
		}	
		return datos;
	}

	public Task getWorkingTask() {
		return workingTask;
	}
	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}
	
	public void onChange$tipo_campo()
	{
		if (this.tipo_campo.getValue().equals("Texto"))
		{
			////System.out.println("Texto");
			
			rowNuevoNombre.setVisible(true);
			rowTipoCombo.setVisible(false);
		}
		else
		{
			////System.out.println("Combo");
			
			rowNuevoNombre.setVisible(false);
			rowTipoCombo.setVisible(true);
		}
	}
	
	public void onClick$agregar(){
		String nuevo_nombre = this.nuevo_nombre.getValue();
	
		if(nuevo_nombre==null
				||nuevo_nombre.trim()==""){
			throw new WrongValueException(this.nuevo_nombre, "El nombre no puede ser vacío.");
		}

		if(this.tipo_campo.getSelectedIndex()<0)
		{
			throw new WrongValueException("Seleccione un tipo de campo.");
		}

		for (MetadataDTO metadata : this.datos) {
			if(metadata.getNombre().equals(nuevo_nombre)){
				throw new WrongValueException(this.nuevo_nombre, "Los nombres de los campos no pueden repetirse.");
			}
		}
		
		MetadataDTO newMetadata = new MetadataDTO();
		newMetadata.setNombre(nuevo_nombre);
		newMetadata.setTipo(this.getSelected().getId().intValue());
		this.datos.add(newMetadata);
		grillaDatos.setRowRenderer(new DatosTrataRenderer());
		
		refreshGrid();
	}
	
	public void onClick$agregarCombo()
	{

		String descripcioncombo = this.tipo_combo.getValue();
		
		if(descripcioncombo==null
				||descripcioncombo.trim()==""){
			throw new WrongValueException(this.tipo_combo, "El campo no puede estar vacío.");
		}
		
		for (MetadataDTO metadata : this.datos) {
			if(metadata.getNombre().equals(descripcioncombo)){
				throw new WrongValueException(this.tipo_combo, "Los nombres de los campos no pueden repetirse.");
			}
		}
		
		MetadataDTO newMetadata = new MetadataDTO();
		newMetadata.setNombre(descripcioncombo);
		newMetadata.setTipo(this.getSelected().getId().intValue());
		this.datos.add(newMetadata);
		grillaDatos.setRowRenderer(new DatosTrataRenderer());
				 
		
		refreshGrid();
	}

	public void refreshGrid() {
		
		if(this.datos.size()==15){
			this.agregar.setDisabled(true);
		}

		this.nuevo_nombre.setValue(null);
		this.binder.loadComponent(this.grillaDatos);
	}

	/**
	 * 
	 * @param name: nombre de la variable que quiero setear
	 * @param value: valor de la variable
	 */
	public void setVariableWorkFlow(String name, Object value) {
		this.processEngine.getExecutionService().setVariable(
				this.workingTask.getExecutionId(), name, value);
	}

	/**
	 * 
	 * @param nameTransition: nombre de la transición que voy a usar para la proxima tarea
	 * @param usernameDerivador: usuario que va a tener asignada la tarea
	 */
	public void signalExecution(String nameTransition, String usernameDerivador) {
		processEngine.getExecutionService().signalExecutionById(
				this.workingTask.getExecutionId(), nameTransition);
	}
	
	public void onClick$cancelar(){
		datos = cloneList(datos2);
		this.trata.setDatoVariable(this.datos);
		this.trataService.guardarDatosVariablesDeTrata(this.trata);
		this.datosPropiosTrataWindow.detach();
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$guardar() throws InterruptedException{
			
		List<Component> rows = (List<Component>) grillaDatos.getRows().getChildren();

		int i = 0;
		for (Component hijo : rows) {

			List<?> lista = hijo.getChildren();
			for (Object object : lista) {
				if ((object.getClass().equals(org.zkoss.zul.Checkbox.class))) {
					Checkbox check = (Checkbox) object;
					this.datos.get(i).setObligatoriedad(check.isChecked());
					i++;
					
					
				}

			}
		}
		this.trata.setDatoVariable(this.datos);
		this.trataService.guardarDatosVariablesDeTrata(this.trata);
		

		if (!(this.datos.size() > 0)) {
			this.datosPropiosTrataWindow.detach();
			Messagebox.show(Labels.getLabel("ee.datPropTrataComp.msgbox.noAgregValorTramite"), "Error", Messagebox.OK, Messagebox.INFORMATION);
		}else{		
			this.datosPropiosTrataWindow.detach();
		
			Messagebox.show(Labels.getLabel("te.base.composer.tipodocumentoscomposer.msj.guardados"),
					Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION,
					new EventListener() {
					public void onEvent(Event evt) {

					}
		});
		}

	}
	
	
	
	public void onEliminar(Event evt){

		// The event is a ForwardEvent...
		ForwardEvent fe = (ForwardEvent) evt;
		// Getting the original Event
		Event event = fe.getOrigin();
		// Getting the component that triggered the original event (i.e. the button)
		Button btn = (Button) event.getTarget();

		String nombre = (String) btn.getAttribute("nombre");
			
		for(int i=0; datos.size()>i; i++ ){
				
			if(datos.get(i).getNombre().equals(nombre)){
				datos.remove(i);		
			}
		}

		this.binder.loadComponent(this.grillaDatos);
	}
	
	public void onCheck(Event evt){
		ForwardEvent fe = (ForwardEvent) evt;
		Event event = fe.getOrigin();
		Checkbox check = (Checkbox) event.getTarget();
		
		String nombre = (String) check.getAttribute("nombre");
		for (int i = 0; i < datos.size(); i++) {
			if(datos.get(i).getNombre().equals(nombre)){
				if(check.isChecked()){
					datos.get(i).setObligatoriedad(true);
				}else if(check.isChecked() == false){
					datos.get(i).setObligatoriedad(false);
				}
			}
		}
	}
	
	public List<TipoDatoPropioDTO> getTiposDatosPropios()
	{
		return tiposDatosPropios;
	}
		
	public void setTiposDatosPropios(List<TipoDatoPropioDTO> tiposDatosPropios)
	{
		this.tiposDatosPropios = tiposDatosPropios;
	}
			
	public TipoDatoPropioDTO getSelected() {
		return selected;
	}
			
	public void setSelected(TipoDatoPropioDTO selected) {
		this.selected = selected;
	}

	public List<DatosVariablesComboGruposDTO> getGruposCombos() {
		return gruposCombos;
	}

	public void setGruposCombos(List<DatosVariablesComboGruposDTO> gruposCombos) {
		this.gruposCombos = gruposCombos;
	}

	public DatosVariablesComboGruposDTO getSelectedCombo() {
		return selectedCombo;
	}

	public void setSelectedCombo(DatosVariablesComboGruposDTO selectedCombo) {
		this.selectedCombo = selectedCombo;
	}
		
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		datos2 = new ArrayList<MetadataDTO>();
		this.setWorkingTask((Task) comp.getDesktop().getAttribute("selectedTask"));
		this.trata = (TrataDTO) Executions.getCurrent().getArg().get("trata");
			
		this.setTiposDatosPropios(this.trataService.buscarTiposDatosPropios());
		this.setGruposCombos(this.trataService.buscarComboGruposPorTipo(this.trata));		
		binder = new AnnotateDataBinder(comp);
		rowTipoCombo.setVisible(false);
		
	}
}
