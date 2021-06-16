package com.egoveris.script.core;

import com.egoveris.script.caller.ScriptCaller;
import com.egoveris.script.parser.ParserUtils;

import io.swagger.models.Swagger;

public class ScriptApi {

  private String apiName;
  private String baseUrl;
  private String swaggerLocation;
  private boolean testScope;
  private Swagger apiData;

  /**
   * Constructor for this class
   * 
   * @param apiName desired name of the api, for example "teApi"
   * @param baseUrl base url
   * @param swaggerLocation location of file containing the swagger api info
   * @param testScope variable used for testing purposes (JUnit)
   * 
   * Usage:
   * edtApi.getService('user').getOperation('access').post('login=jquintau', 'password=******');
   *                                                 .get()
   */
  public ScriptApi(String apiName, String baseUrl, String swaggerLocation, boolean testScope) {
    this.apiName = apiName;
    this.baseUrl = baseUrl;
    this.swaggerLocation = swaggerLocation;
    this.testScope = testScope;
    
    if (baseUrl != null && !baseUrl.endsWith("/")) {
      this.baseUrl = baseUrl + "/";
    }
  }
  
  /**
   * Returns a ScriptCaller class instance 
   * Also loads the swagger info in apiData
   *  
   * @param servicePath path of the service we need to call
   * @return
   */
  public ScriptCaller getService(String servicePath) {
    if (!testScope) {
      apiData = ParserUtils.parseFromUrl(baseUrl + swaggerLocation);
    } 
    else {
      // Just for test scope (JUnit case)
      apiData = ParserUtils.parseFromFile("/" + apiName + ".json");
    }
    
    return new ScriptCaller(servicePath, this);
  }

  // GETTERS - SETTERS

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getSwaggerLocation() {
    return swaggerLocation;
  }

  public void setSwaggerLocation(String swaggerLocation) {
    this.swaggerLocation = swaggerLocation;
  }
  
  public boolean isTestScope() {
    return testScope;
  }

  public void setTestScope(boolean testScope) {
    this.testScope = testScope;
  }

  public Swagger getApiData() {
    return apiData;
  }

  public void setApiData(Swagger apiData) {
    this.apiData = apiData;
  }
  
}
