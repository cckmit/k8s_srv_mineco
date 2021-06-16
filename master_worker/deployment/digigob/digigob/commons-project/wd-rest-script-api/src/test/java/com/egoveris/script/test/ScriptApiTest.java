package com.egoveris.script.test;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.egoveris.commons.databaseconfiguration.exceptions.DatabaseConfigurationException;
import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.commons.databaseconfiguration.propiedades.impl.DBAppPropertyImpl;
import com.egoveris.plugins.manager.tools.scripts.ScriptUtils;
import com.egoveris.script.core.ScriptApi;
import com.egoveris.script.core.ScriptBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-ds-config.xml" })
public class ScriptApiTest {

  @Autowired
  private DataSource dataSource;
  
  private AppProperty appProperty;
  private Map<String, Object> params;
  
  @Before
  public void initialize() throws DatabaseConfigurationException {
    appProperty = new DBAppPropertyImpl(dataSource, "PROPERTY_CONFIGURATION", "SISTEMA.HVOGELVA", null);
    params = new HashMap<>();
    
    ScriptBase scriptBase = ScriptBase.getInstance(appProperty);
    //scriptBase.setTestScope(true);
    
    for (ScriptApi scriptApi : scriptBase.getAvailableApis()) {
      params.put(scriptApi.getApiName(), scriptApi);
    }
  }
  
  @Ignore
  @Test
  public void testGetForm() throws ScriptException {
    ScriptUtils.executeScript("result = teApi.getService('operation/form').get('name=CERRE'); print (result);", params);
  }
  
  @Ignore
  @Test
  public void testUserLogin() throws ScriptException {
    ScriptUtils.executeScript("result = edtApi.getService('user/access').post('login=jquintau', 'password=654321');  print (result);", params);
  }
  
  @Test
  public void testGetCCEntity() throws ScriptException {
    String script = "items = teApi.getService('api/ccEntity/get').get('entity=product', 'idOperation=885');" + "\n";
    script += "for (i = 0; i < items.length; i++) {" + "\n";
    //script += "teApi.getService('api/operation/initProductReqSubprocess').post('idOperation=885', 'user=JQUINTAU', 'motivo=Product Request ' + items[i].id);" + "\n";
    script += "}" + "\n";
    
    ScriptUtils.executeScript(script, params);
  }
  
}
