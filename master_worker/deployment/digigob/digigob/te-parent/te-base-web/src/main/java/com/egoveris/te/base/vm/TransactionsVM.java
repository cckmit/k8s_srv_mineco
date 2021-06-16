package com.egoveris.te.base.vm;

import com.egoveris.te.base.model.ExpedientTransactionDTO;
import com.egoveris.te.base.service.rmi.RemoteRestService;
import com.egoveris.trade.ws.fusedemoservice.ExternalRequest;
import com.egoveris.trade.ws.fusedemoservice.FuseDemoServiceService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TransactionsVM {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(TransactionsVM.class);

	// Pantalla
	@Wire("#transactionsTemp")
	private Window window;
	private List<ExpedientTransactionDTO> transactions;
	@WireVariable("rmiServiceImpl")
	private RemoteRestService rmiServiceImpl;
	@WireVariable
	private String appFuseRequest; 

	@Init
	public void init() {
		transactions =  rmiServiceImpl.getTransactions();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

	}
	
	@Command
	@NotifyChange("transactions")
	public void onConfirmOperation(@BindingParam("transaction") ExpedientTransactionDTO transaction){
		executeESBCallBack("DEMO001", transaction);
		refresh();
	}
	
	@Command
	@NotifyChange("transactions")
	public void onInitSubprocess(@BindingParam("transaction") ExpedientTransactionDTO transaction){
		executeESBCallBack("DEMO002", transaction);
		refresh();
	}

	@Command
	@NotifyChange("transactions")
	public void onRefresh(){
		refresh();
	}

	private void refresh(){
		try {
			transactions =  rmiServiceImpl.getTransactions();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	private void executeESBCallBack(String message, ExpedientTransactionDTO transaction){
	     try {
	    	ExternalRequest ex = new ExternalRequest();
			ex.setIdMessage("DEMO001");
			ex.setIdTransaction("12312");
			ex.setTypeForm("FORMLUIS");
			URL url = new URL(appFuseRequest); 
			FuseDemoServiceService demo1 = new FuseDemoServiceService(url);
			demo1.getFuseDemoServicePort().fuseDemoCallback(ex);

		} catch (MalformedURLException e) {
			logger.error("error", e);
			Messagebox.show("URL del endpoint esta mal formada[URL: " + appFuseRequest + "]", "Error",
						Messagebox.OK, Messagebox.ERROR);
		     return;
		} catch (Exception e) {
			logger.error("error", e);
		
		}
	     
	     Messagebox.show("Se ha ejecutado correctamente la peticiòn a FUSE", "Información",
					Messagebox.OK, Messagebox.INFORMATION);
	}
	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public List<ExpedientTransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<ExpedientTransactionDTO> transactions) {
		this.transactions = transactions;
	}

}
