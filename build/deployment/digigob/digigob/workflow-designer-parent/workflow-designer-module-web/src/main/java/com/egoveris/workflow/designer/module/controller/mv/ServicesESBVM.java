/**
 *
 */
package com.egoveris.workflow.designer.module.controller.mv;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.egoveris.workflow.designer.module.model.CodeDTO;
import com.egoveris.workflow.designer.module.model.ServicesESBDTO;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;



public class ServicesESBVM {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ServicesESBVM.class);
	@Wire("#servicesESB")
	private Window win;
	private List<ServicesESBDTO> services;
	private List<CodeDTO> codes;
	private CodeDTO codeSelected;
	private String message;
	private List<String> typesBlock;
	@Wire("#rowBlockExpedient")
	private Row rowBlockExpedient;
	private String type;

	@Init
	public void init(@ExecutionArgParam("typeWorkflow")  String type) {
		
		 this.type = type;		
		 logger.info("load services ...");
		 services = new ArrayList<>();
		 codes = new ArrayList<>();
		 // load services dummy
		 ServicesESBDTO s1 = new ServicesESBDTO();
		 s1.setId(1l);
		 s1.setName("Services SERNAPESCA");
		 s1.setUrl("rmi://desa.egoveris.trade:1499/sendDemo");
		 
		 ServicesESBDTO s2 = new ServicesESBDTO();
		 s2.setId(1l);
		 s2.setName("Services Registro Civil");
		 s2.setUrl("rmi://desa.egoveris.trade:1499/sendDemo");
		 
		 services.add(s1);
		 services.add(s2);
		 
		 CodeDTO c1 = new CodeDTO();
		 c1.setId(1l);
		 c1.setCode("COD01");
		 c1.setMessage("Init subprocess");
		 
		 CodeDTO c2 = new CodeDTO();
		 c2.setId(2l);
		 c2.setCode("COD02");
		 c2.setMessage("Notification start task");
		 
		 CodeDTO c3 = new CodeDTO();
		 c3.setId(3l);
		 c3.setCode("COD03");
		 c3.setMessage("Send Form");
		 
		 CodeDTO c4 = new CodeDTO();
		 c4.setId(4l);
		 c4.setCode("COD04");
		 c4.setMessage("Noitification end task");
		 
		 codes.add(c1);
		 codes.add(c2);
		 codes.add(c3);
		 codes.add(c4);
		 
		 
		 typesBlock = new ArrayList<>();
		 typesBlock.add("Si");
		 typesBlock.add("No");
		
	}

	@AfterCompose
	public void after(@ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		if(StringUtils.isNotBlank(type) && TypeWorkFlow.STATE.toString().equalsIgnoreCase(type)) {
			 rowBlockExpedient.setVisible(false);
		 }
	}
	
	
	@NotifyChange("message")
	@Command
	public void onLoadMessage(@BindingParam("entry") CodeDTO item){
		if(codeSelected != null){
			message = codeSelected.getMessage();
		}
	}
	
	
	
	// GETTERS && SETTERS
	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	public List<ServicesESBDTO> getServices() {
		return services;
	}

	public void setServices(List<ServicesESBDTO> services) {
		this.services = services;
	}

	public List<CodeDTO> getCodes() {
		return codes;
	}

	public void setCodes(List<CodeDTO> codes) {
		this.codes = codes;
	}

	public CodeDTO getCodeSelected() {
		return codeSelected;
	}

	public void setCodeSelected(CodeDTO codeSelected) {
		this.codeSelected = codeSelected;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getTypesBlock() {
		return typesBlock;
	}

	public void setTypesBlock(List<String> typesBlock) {
		this.typesBlock = typesBlock;
	}

	public Row getRowBlockExpedient() {
		return rowBlockExpedient;
	}

	public void setRowBlockExpedient(Row rowBlockExpedient) {
		this.rowBlockExpedient = rowBlockExpedient;
	}
	
	
}
