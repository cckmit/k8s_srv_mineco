package com.egoveris.script.caller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.egoveris.script.core.ScriptApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;

public class ScriptCaller {

  private static final Logger logger = LoggerFactory.getLogger(ScriptCaller.class);
  
  private String servicePath;
  private ScriptApi scriptApi;
  
  /**
   * Constructor for this class
   * 
   * @param servicePath path of the service we need to call
   * @param scriptApi ScriptApi class that belongs to this "Service"
   */
  public ScriptCaller(String servicePath, ScriptApi scriptApi) {
    this.scriptApi = scriptApi;
    
    if (servicePath != null && !servicePath.startsWith("/")) {
      this.servicePath = "/" + servicePath;
    }
    else {
      this.servicePath = servicePath;
    }
  }
  
  // Others operations may be implemented in a future (PUT, DELETE, ...)
  
  /**
   * Performs a HttpMethod.GET over this service/operation
   * 
   * @return
   * @throws ScriptException
   */
  public Object get() throws ScriptException {
    return invoke(HttpMethod.GET);
  }
  
  /**
   * Performs a HttpMethod.GET over this service/operation
   * 
   * @param params
   * @return
   * @throws ScriptException
   */
  public Object get(String... params) throws ScriptException {
    return invoke(HttpMethod.GET, params);
  }
  
  /**
   * Performs a HttpMethod.POST over this service/operation
   * 
   * @param params
   * @return
   * @throws ScriptException
   */
  public Object post() throws ScriptException {
    return invoke(HttpMethod.POST);
  }
  
  /**
   * Performs a HttpMethod.POST over this service/operation
   * 
   * @param params
   * @return
   * @throws ScriptException
   */
  public Object post(String... params) throws ScriptException {
    return invoke(HttpMethod.POST, params);
  }
  
  /**
   * Invokes the service
   * 
   * @param httpMethod HttpMethod
   * @param params parameters
   * @return
   * @throws ScriptException
   */
  private Object invoke(HttpMethod httpMethod, String... params) throws ScriptException {
    Object result = null;
    Swagger apiData = getScriptApi().getApiData();
    
    if (apiData == null) {
      throw new ScriptException(
          "Error: Couldn't load Swagger data for " + getScriptApi().getApiName() + " object!");
    }

    boolean found = false;
    
    // We iterate over apiData paths, to search for the service + operation combination
    if (apiData.getPaths() != null && !apiData.getPaths().isEmpty()) {
      for (Map.Entry<String, Path> entry : apiData.getPaths().entrySet()) {
        if (entry.getKey().contains(servicePath)) {
          found = true;
          // Get operation object
          Operation operation = getOperationFromPath(entry.getValue(), httpMethod);
          // Get and arms parameters (from String... params)
          Map<Parameter, String> parameters = armParameters(operation, params);
          // 
          result = invoke(entry.getKey(), httpMethod, parameters);
          break;
        }
      }
    }

    if (!found) {
      throw new ScriptException(
          "Error: Path " + servicePath + " not found in " + getScriptApi().getApiName() + "!");
    }

    return result;
  }
  
  /**
   * Refactor of invoke method
   * 
   * @param pathUrl
   * @param httpMethod
   * @param parameters
   * @return
   * @throws ScriptException 
   */
  private Object invoke(String pathUrl, HttpMethod httpMethod, Map<Parameter, String> parameters) throws ScriptException {
    String path = pathUrl;
    
    if (pathUrl.startsWith("/")) {
      path = pathUrl.replaceFirst("/", "");
    }
    
    HttpEntity<JsonNode> httpEntity = null;
    JsonNode bodyParam = null;
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getScriptApi().getBaseUrl() + path);

    // Uri parameters
    Map<String, String> uriParams = new HashMap<>();
    
    for (Map.Entry<Parameter, String> entry : parameters.entrySet()) { 
      if (entry.getKey() instanceof QueryParameter
          || (entry.getKey() instanceof BodyParameter && HttpMethod.GET.equals(httpMethod))) {
        builder.queryParam(entry.getKey().getName(), entry.getValue());
      }
      else if (entry.getKey() instanceof PathParameter) {
        uriParams.put(entry.getKey().getName(), entry.getValue());
      }
      else if (entry.getKey() instanceof BodyParameter && bodyParam == null) {
        // Only one BodyParameter is supported
        ObjectMapper mapper = new ObjectMapper();
        
        try {
          bodyParam = mapper.readValue(entry.getValue(), JsonNode.class);
          httpEntity = new HttpEntity<>(bodyParam); 
        } catch (IOException e) {
          logger.debug("Error: Couldn't serialize provided " + entry.getKey().getName() + " parameter: ", e);
          throw new ScriptException("Error: Couldn't serialize provided " + entry.getKey().getName() + " parameter");
        }
      }
    }
    
    // Rest template
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    
    // Response and output
    ResponseEntity<Object> response = restTemplate.exchange(builder.buildAndExpand(uriParams).toUri(), httpMethod, httpEntity, Object.class);
    Object output = null;
    
    if (response != null && response.getBody() != null) {
      output = response.getBody();
    }
    
    return output;
  }
  
  /**
   * Depending of the HttpRequest method
   * returns the operation object inside given path object
   * 
   * @param path
   * @param method
   * @return
   * @throws ScriptException
   */
  private Operation getOperationFromPath(Path path, HttpMethod method) throws ScriptException {
    Operation operation = null;
    
    switch (method) {
      case GET:
        operation = (path.getGet() != null) ? path.getGet() : null;
        break;
      case POST:
        operation = (path.getPost() != null) ? path.getPost() : null;
        break;
      default:
        break;
    }
    
    if (operation == null) {
      throw new ScriptException("Error: " + method.toString() + " is not supported for " + servicePath + "!");
    }
    
    return operation;
  }
  
  /**
   * Arms a Map<Parameter, String>
   * 
   * The params arg format is expected to be the following:
   * [0] login=jquintau [This mean the parameter name is login, and his value jquintau
   * [1] password=******
   * 
   * @param operation Operation object of the api 
   * @param params User provided params
   * @return
   * @throws ScriptException
   */
  private Map<Parameter, String> armParameters(Operation operation, String ... params) throws ScriptException {
    Map<Parameter, String> parameters = new HashMap<>();
    
    if (operation.getParameters() != null && !operation.getParameters().isEmpty()) {
      for (Parameter parameter : operation.getParameters()) {
        searchAndPutParam(parameters, parameter, params);
        
        boolean found = parameters.containsKey(parameter);
        
        if (!found && parameter.getRequired()) {
          throw new ScriptException(
              "Error: Missing obligatory parameter (" + parameter.getName() + ") for " + servicePath + "!");
        }
      }
    }
    
    return parameters;
  }
  
  /**
   * Refactor of armParameters method
   * 
   * @param parameters
   * @param parameter
   * @param params
   */
  private void searchAndPutParam(Map<Parameter, String> parameters, Parameter parameter, String... params) {
    for (int i = 0; i < params.length; i++) {
      if (params[i].contains("=")) {
        String[] parts = params[i].split("=", 2);
        
        if (parameter.getName().equalsIgnoreCase(parts[0])) {
          parameters.put(parameter, parts[1]);
          break;
        }
      }
    }
  }
  
  // GETTERS - SETTERS

  public String getServicePath() {
    return servicePath;
  }

  public void setServicePath(String servicePath) {
    this.servicePath = servicePath;
  }

  public ScriptApi getScriptApi() {
    return scriptApi;
  }

  public void setScriptApi(ScriptApi scriptApi) {
    this.scriptApi = scriptApi;
  }
  
}
