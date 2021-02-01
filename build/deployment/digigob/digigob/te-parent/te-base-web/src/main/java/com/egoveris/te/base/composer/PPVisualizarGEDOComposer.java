package com.egoveris.te.base.composer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Iframe;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PPVisualizarGEDOComposer extends EEGenericForwardComposer {

	private static final long serialVersionUID = -85524705646812652L;

	@Autowired
	private AnnotateDataBinder binder;

	private Iframe ifrGedo;
	
	@WireVariable("GEDOurl")
	private String gedoUrl;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
  public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		binder = new AnnotateDataBinder(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		gedoUrl = gedoUrl + "/lecturaDocumento.zul?numeroSade=";
		
    Map<String, Object> datos = (HashMap<String, Object>) Executions.getCurrent().getArg();
    
    if (datos.get("nombreArchivo") != null) {
      String result = gedoUrl + datos.get("nombreArchivo");
      
      if (datos.get("assignee") != null) {
        String assignee = "&assignee=" + datos.get("assignee");
        result = result + assignee;
      }
      
      ifrGedo.setSrc(result);
      binder.loadAll();
    }
	}
}
