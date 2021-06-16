package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.PropertyConfigurationDTO;

public interface IPropertyConfigurationService {
  
  /**
   * Obtains all properties from PROPERTY_CONFIGURATION table
   * starting with the provided prefix
   * 
   * @param prefix
   * @return
   */
  public List<PropertyConfigurationDTO> getPropertiesWithPrefix(String prefix);
  
  /**
   * Obtains all properties from PROPERTY_CONFIGURATION table
   * starting with the provided prefix for a determined configuration
   * 
   * @param prefix
   * @param configuration
   * @return
   */
  public List<PropertyConfigurationDTO> getPropertiesWithPrefix(String prefix, String configuration);
  
  /**
   * Saves or updates a property into PROPERTY_CONFIGURATION table
   * 
   * @param propertyConfigurationDTO
   */
  public void saveProperty(PropertyConfigurationDTO propertyConfigurationDTO);
  
  /**
   * Deletes a property from PROPERTY_CONFIGURATION table
   * that does matchs the DTO parameters key and configuration
   * 
   * @param propertyConfigurationDTO
   */
  public void deleteProperty(PropertyConfigurationDTO propertyConfigurationDTO);
  
  /**
   * Obtains the next property key for a determinated prefix
   * For example, tipoResultado.1, tipoResultado.2, and so
   * 
   * @param prefix
   * @return
   */
  public String getNextPropertyKey(String prefix);
  
  /**
   * Checks if already exists a determinated value
   * for a determinated prefix
   * 
   * @param prefix
   * @param value
   * @return
   */
  public boolean isUsedValue(String prefix, String value);
  
}
