package com.egoveris.te.base.vm;

import com.egoveris.te.base.model.PropertyConfigurationDTO;
import com.egoveris.te.base.service.IPropertyConfigurationService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * For an example of use for a determined mantainer (prefix)
 * see ResultTypeVM
 * 
 * @see ResultTypeVM
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class PropertyMantainerVM {

  /**
   * The mantainer prefix, i.e. tipoResultado.
   * for search in PROPERTY_CONFIGURATION table
   */
  protected static String mantainerPrefix;
  
  /**
   * Default config variable in PROPERTY_CONFIGURATION table
   */
  protected static String defaultConfig = "SISTEMA";
  
  /**
   * Zul of the form window
   */
  protected static String formWindowZul;
  
  public static final String NEW = "NEW";
  public static final String EDIT = "EDIT";
  
  // Services
  @WireVariable(ConstantesServicios.PROPERTY_CONF_SERVICE)
  private IPropertyConfigurationService propertyConfigurationService;
  
  // Components
  private Window pageWindow;
  private Window formWindow;
  
  // Variables
  private String actionMode;
  public PropertyConfigurationDTO currentProperty;
  public List<PropertyConfigurationDTO> propertiesList;
  
  /**
   * Init, searchs properties that starts with
   * the provided prefix (initialized in a static way)
   * 
   * @param view
   */
  @Init
  public void init(@ContextParam(ContextType.VIEW) Component view) {
    if (mantainerPrefix == null || formWindowZul == null) {
      throw new RuntimeException("Parameters mantainerPrefix and formWindowZul should not be null");
    }
    
    if (view instanceof Window) {
      pageWindow = (Window) view;
    }
    
    setPropertiesList(propertyConfigurationService.getPropertiesWithPrefix(mantainerPrefix)); 
  }
  
  /**
   * Command that opens the window with the form
   * (formWindowZul, initialized in a static way)
   * in CREATION mode
   */
  @Command
  @NotifyChange("currentProperty")
  public void newPropertyForm() {
    actionMode = NEW;
    currentProperty = new PropertyConfigurationDTO();
    
    formWindow = (Window) Executions.createComponents(formWindowZul, pageWindow, null);
    formWindow.doModal();
  }
  
  /**
   * Command that opens the window with the form
   * (formWindowZul, initialized in a static way)
   * in EDIT mode
   */
  @Command
  public void editPropertyForm(@BindingParam("property") PropertyConfigurationDTO property) {
    actionMode = EDIT;
    currentProperty = property;
    property.setValorAux(property.getValor());
    
    formWindow = (Window) Executions.createComponents(formWindowZul, pageWindow, null);
    formWindow.doModal();
  }
  
  /**
   * Command that opens a delete property dialog
   * (YES/NO)
   * 
   * @param property
   */
  @Command
  public void deleteProperty(@BindingParam("property") PropertyConfigurationDTO property) {
    String message = Labels.getLabel("ee.propertyMantainer.delete.message", new Object[] { property.getValor() });
    String title = Labels.getLabel("ee.propertyMantainer.delete.title");
    
    Messagebox.show(message, title,
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {

          // If YES is pressed
          @Override
          public void onEvent(Event event) throws Exception {
            if (event.getName().equals(Messagebox.ON_YES)) {
              if (onDeleteValidation(property)) {
                propertyConfigurationService.deleteProperty(property);
                BindUtils.postGlobalCommand(null, null, "refreshPropertiesList", null);
              }
            }
          }
        });
  }
  
  /**
   * Command that saves a new property
   */
  @Command
  public void savePropertyForm() {
    if (actionMode.equals(NEW)) {
      // Form should be already validated here, we only need to generate
      // the property key and put the configuration constant
      currentProperty.setClave(propertyConfigurationService.getNextPropertyKey(mantainerPrefix));
      currentProperty.setConfiguracion(defaultConfig);
    }
    
    // Trims the value
    currentProperty.setValor(currentProperty.getValor().trim());
    
    boolean duplicated = propertyConfigurationService.isUsedValue(mantainerPrefix, currentProperty.getValor());
    
    if (currentProperty.getValorAux() != null && currentProperty.getValorAux().equalsIgnoreCase(currentProperty.getValor())) {
      duplicated = false;
    }
    
    if (!duplicated) {
      propertyConfigurationService.saveProperty(currentProperty);
      
      if (formWindow != null) {
        formWindow.onClose();
      }
      
      BindUtils.postGlobalCommand(null, null, "refreshPropertiesList", null);
    }
    else {
      Messagebox.show(Labels.getLabel("ee.propertyMantainer.duplicatedValue.message"),
          Labels.getLabel("ee.propertyMantainer.duplicatedValue.title"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }
  
  /**
   * Refresh properties list command
   */
  @GlobalCommand
  @NotifyChange("propertiesList")
  public void refreshPropertiesList() {
    setPropertiesList(propertyConfigurationService.getPropertiesWithPrefix(mantainerPrefix)); 
  }
  
  /**
   * On delete validation. This method may be overrided 
   * for custom delete validations, see ResultTypeVM 
   * for an example
   * 
   * @param property
   * @return
   */
  public boolean onDeleteValidation(PropertyConfigurationDTO property) {
    return true;
  }
  
  // Getters - setters
  
  public IPropertyConfigurationService getPropertyConfigurationService() {
    return propertyConfigurationService;
  }

  public void setPropertyConfigurationService(IPropertyConfigurationService propertyConfigurationService) {
    this.propertyConfigurationService = propertyConfigurationService;
  }

  public Window getPageWindow() {
    return pageWindow;
  }

  public void setPageWindow(Window pageWindow) {
    this.pageWindow = pageWindow;
  }

  public Window getFormWindow() {
    return formWindow;
  }

  public void setFormWindow(Window formWindow) {
    this.formWindow = formWindow;
  }

  public String getActionMode() {
    return actionMode;
  }

  public void setActionMode(String actionMode) {
    this.actionMode = actionMode;
  }

  public PropertyConfigurationDTO getCurrentProperty() {
    return currentProperty;
  }

  public void setCurrentProperty(PropertyConfigurationDTO currentProperty) {
    this.currentProperty = currentProperty;
  }

  public List<PropertyConfigurationDTO> getPropertiesList() {
    return propertiesList;
  }

  public void setPropertiesList(List<PropertyConfigurationDTO> propertiesList) {
    this.propertiesList = propertiesList;
  }
  
}