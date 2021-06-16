package com.egoveris.edt.web.vm;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.google.gson.Gson;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PentahoDashboardVM {
  
  private static final Logger logger = LoggerFactory.getLogger(PentahoDashboardVM.class);
  
  @Wire
  Div cdeDashboard;
  
  @WireVariable("dBProperty")
  private AppProperty appProperty;
  
  private Map<String, String> parameters;
  private boolean existeDatosUsuario;
  
  @Init
  public void init() {
    try {
      existeDatosUsuario = (boolean) Executions.getCurrent().getAttribute(ConstantesSesion.KEY_EXISTE_DATO_USUARIO);
      
      parameters = new LinkedHashMap<>();
      parameters.put("username", (String) Executions.getCurrent().getDesktop().getSession().getAttribute(ConstantesSesion.SESSION_USERNAME));
      parameters.put("organism", (String) Executions.getCurrent().getDesktop().getSession().getAttribute(ConstantesSesion.SESSION_USER_REPARTICION));
      parameters.put("sector", (String) Executions.getCurrent().getDesktop().getSession().getAttribute(ConstantesSesion.SESSION_USER_SECTOR));
      
      
      if (appProperty != null && appProperty.getString("host.te") != null) {
    	  parameters.put("urlBaseTE", appProperty.getString("host.te"));
      }
    }
    catch (NullPointerException e) {
      logger.error("Error getting session parameters in PentahoDashboardVM.init(): " + e);
    }
  }
  
  @AfterCompose
  public void afterComposer(@ContextParam(ContextType.VIEW) final Component view) {
    Selectors.wireComponents(view, this, false);
    
    // Si el usuario entra por primera vez y aun no ha cambiado
    // sus datos iniciales, no debe mostrarse el pentaho dashboard.
    if (existeDatosUsuario && parameters != null) {
      Clients.evalJavaScript("drawPentahoDashboard('" + cdeDashboard.getUuid() + "', " + new Gson().toJson(parameters) + ");");
    }
  }
  
 

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

}
