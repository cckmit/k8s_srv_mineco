/**
 *
 */
package com.egoveris.workflow.designer.module.controller.mv;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.model.model.FormularioComponenteWDDTO;
import com.egoveris.ffdd.model.model.FormularioWDDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;



public class FormsFFCCVM {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FormsFFCCVM.class);
	private List<ResponseTipoDocumento>  typesDocFFCC;
	private Set<FormularioComponenteWDDTO> fields;
	private ResponseTipoDocumento typeDocSelected;
	@WireVariable("tipoDocumentoService")
	private IExternalTipoDocumentoService tipoDocumentoService;
	@WireVariable("externalFormularioService")
	private ExternalFormularioService formularioService;
	@WireVariable("zkFormManagerFactory")
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;
	private IFormManager<Component> manager;
	@Wire("#formsFFCC")
	private Window win;
	@Wire("#rowFields")
	private Row rowFields;
	@Wire("#rowCondition")
	private Row rowCondition;
	@Wire("#rowFieldCompare")
	private Row rowFieldCompare;
	@Wire("#rowFieldType")
	private Row rowFieldType;
	@Wire("#g1")
	private Radiogroup radioGroup;
	private List<String> conditions;
	private List<String> types;
	
	
	
	
	@Init
	public void init() {
		typesDocFFCC = tipoDocumentoService.getDocumentTypeByProduction(ProductionEnum.TEMPLATE);
		conditions = new ArrayList<>();
		conditions.add("==");
		conditions.add("!=");
		conditions.add(">");
		conditions.add("<");
		conditions.add(">=");
		conditions.add("<=");
		
		types = new ArrayList<>();
		types.add("Numero");
		types.add("Alfanumerico");
		types.add("Fecha");
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) final Component view) {
		if (logger.isDebugEnabled()) {
			logger.debug("afterCompose(Component) - start"); //$NON-NLS-1$
		}
		Selectors.wireComponents(view, this, false);
		if (logger.isDebugEnabled()) {
			logger.debug("afterCompose(Component) - end"); //$NON-NLS-1$
		}
	}


	@NotifyChange("fields")
	@Command
	public void onLoadFields(@BindingParam("entry") ResponseTipoDocumento item){
		if(typeDocSelected != null){
			try {
			FormularioWDDTO formulario = formularioService.buscarFormularioPorNombreWD(typeDocSelected.getIdFormulario());
			fields = formulario.getFormularioComponentes();
			} catch (Exception e) {
				logger.error("Error get form",e);
			}
		}
	}
	
	
	@Command
	public void onCkecGroup(){
		Radio r = radioGroup.getSelectedItem();
		if("ra1".equalsIgnoreCase(r.getId())){
			rowFields.setVisible(false);
			rowCondition.setVisible(false);
			rowFieldCompare.setVisible(false);
			rowFieldType.setVisible(false);
		} else if("ra2".equalsIgnoreCase(r.getId())) {
			rowFields.setVisible(true);
			rowCondition.setVisible(true);
			rowFieldCompare.setVisible(true);
			rowFieldType.setVisible(true);
		} else {
			rowFields.setVisible(true);
			rowCondition.setVisible(false);
			rowFieldCompare.setVisible(false);
			rowFieldType.setVisible(false);
		}
	}
	
	// GETTERS && SETTERS
	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}


	public List<ResponseTipoDocumento> getTypesDocFFCC() {
		return typesDocFFCC;
	}


	public void setTypesDocFFCC(List<ResponseTipoDocumento> typesDocFFCC) {
		this.typesDocFFCC = typesDocFFCC;
	}


	public ResponseTipoDocumento getTypeDocSelected() {
		return typeDocSelected;
	}


	public void setTypeDocSelected(ResponseTipoDocumento typeDocSelected) {
		this.typeDocSelected = typeDocSelected;
	}


	public Set<FormularioComponenteWDDTO>  getFields() {
		return fields;
	}


	public void setFields(Set<FormularioComponenteWDDTO>  fields) {
		this.fields = fields;
	}


	public IExternalTipoDocumentoService getTipoDocumentoService() {
		return tipoDocumentoService;
	}


	public void setTipoDocumentoService(IExternalTipoDocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
	}


	public ExternalFormularioService getFormularioService() {
		return formularioService;
	}


	public void setFormularioService(ExternalFormularioService formularioService) {
		this.formularioService = formularioService;
	}


	public IFormManagerFactory<IFormManager<Component>> getFormManagerFact() {
		return formManagerFact;
	}


	public void setFormManagerFact(IFormManagerFactory<IFormManager<Component>> formManagerFact) {
		this.formManagerFact = formManagerFact;
	}


	public IFormManager<Component> getManager() {
		return manager;
	}


	public void setManager(IFormManager<Component> manager) {
		this.manager = manager;
	}

	public Row getRowFields() {
		return rowFields;
	}

	public void setRowFields(Row rowFields) {
		this.rowFields = rowFields;
	}

	public Radiogroup getRadioGroup() {
		return radioGroup;
	}

	public void setRadioGroup(Radiogroup radioGroup) {
		this.radioGroup = radioGroup;
	}

	public Row getRowCondition() {
		return rowCondition;
	}

	public void setRowCondition(Row rowCondition) {
		this.rowCondition = rowCondition;
	}

	public Row getRowFieldCompare() {
		return rowFieldCompare;
	}

	public void setRowFieldCompare(Row rowFieldCompare) {
		this.rowFieldCompare = rowFieldCompare;
	}

	public List<String> getConditions() {
		return conditions;
	}

	public void setConditions(List<String> conditions) {
		this.conditions = conditions;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Row getRowFieldType() {
		return rowFieldType;
	}

	public void setRowFieldType(Row rowFieldType) {
		this.rowFieldType = rowFieldType;
	}
	
}
