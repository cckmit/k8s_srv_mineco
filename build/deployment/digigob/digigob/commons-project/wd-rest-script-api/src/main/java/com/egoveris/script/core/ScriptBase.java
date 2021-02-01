package com.egoveris.script.core;

import java.util.ArrayList;
import java.util.List;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;

public class ScriptBase {
  
  private static final String DEFAULT_SWAGGER_LOCATION = "v2/api-docs";
  
  private static ScriptBase instance; 
  private List<ScriptApi> availableApis;
  private AppProperty appProperty;
  private boolean testScope;
  
  private ScriptBase() {
    // Private constructor
  }
  
  /**
   * Returns an instance of this utility class
   * 
   * @param appProperty Properties of the application, used to retrieve base urls
   * @return
   */
  public static ScriptBase getInstance(AppProperty appProperty) {
    if (instance == null) {
      instance = new ScriptBase();
      
      if (appProperty != null) {
        instance.setAppProperty(appProperty);
      }
    }
    
    return instance;
  }

  // GETTERS - SETTERS
  
  /**
   * This getter returns the availableApis list.
   * If the list is null, we initialize it by each module
   * (edt, te, deo, ffdd, wd)
   * 
   * @return
   */
  public List<ScriptApi> getAvailableApis() {
    if (availableApis == null && appProperty != null) {
      availableApis = new ArrayList<>();
      availableApis.add(new ScriptApi("edtApi", appProperty.getString("host.edt"), DEFAULT_SWAGGER_LOCATION, testScope));
      availableApis.add(new ScriptApi("teApi", appProperty.getString("host.te"), DEFAULT_SWAGGER_LOCATION, testScope));
      availableApis.add(new ScriptApi("deoApi", appProperty.getString("host.deo"), DEFAULT_SWAGGER_LOCATION, testScope));
      availableApis.add(new ScriptApi("ffddApi", appProperty.getString("host.ffdd"), DEFAULT_SWAGGER_LOCATION, testScope));
      availableApis.add(new ScriptApi("wdApi", appProperty.getString("host.wd"), DEFAULT_SWAGGER_LOCATION, testScope));
    }
    
    return availableApis;
  }

  public void setAvailableApis(List<ScriptApi> availableApis) {
    this.availableApis = availableApis;
  }

  public AppProperty getAppProperty() {
    return appProperty;
  }

  public void setAppProperty(AppProperty appProperty) {
    this.appProperty = appProperty;
  }

  public boolean isTestScope() {
    return testScope;
  }

  public void setTestScope(boolean testScope) {
    this.testScope = testScope;
  }
  
}
