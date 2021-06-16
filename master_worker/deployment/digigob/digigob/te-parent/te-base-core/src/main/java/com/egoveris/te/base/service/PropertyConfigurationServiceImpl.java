package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.PropertyConfiguration;
import com.egoveris.te.base.model.PropertyConfigurationDTO;
import com.egoveris.te.base.model.PropertyConfigurationPK;
import com.egoveris.te.base.repository.PropertyConfigurationRepository;
import com.egoveris.te.base.util.ConstantesServicios;

@Service(ConstantesServicios.PROPERTY_CONF_SERVICE)
@Transactional
@SuppressWarnings("unchecked")
public class PropertyConfigurationServiceImpl implements IPropertyConfigurationService {

  private static final Logger logger = LoggerFactory.getLogger(PropertyConfigurationServiceImpl.class);
  
  private static final String CONFIGURATION_SYSTEM = "SISTEMA";
  
  @Autowired
  private PropertyConfigurationRepository propertyConfRepository;
  
  @Autowired
  @Qualifier("teCoreMapper")
  private Mapper mapper;

  @Override
  public List<PropertyConfigurationDTO> getPropertiesWithPrefix(String prefix) {
    if (logger.isDebugEnabled()) {
      logger.debug("getProperties(prefix={}) - start", prefix);
    }
    
    List<PropertyConfigurationDTO> listPropertyConfigurationDTO = new ArrayList<>();
    List<PropertyConfiguration> listPropertyConfigurationEnt = propertyConfRepository
        .findByClaveStartingWithAndConfiguracion(prefix, CONFIGURATION_SYSTEM);
    
    if (listPropertyConfigurationEnt != null) {
      listPropertyConfigurationDTO
          .addAll(ListMapper.mapList(listPropertyConfigurationEnt, mapper, PropertyConfigurationDTO.class));
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getProperties(List<PropertyConfigurationDTO>) - end - return value={}", listPropertyConfigurationDTO);
    }
    
    return listPropertyConfigurationDTO;
  }

  @Override
  public List<PropertyConfigurationDTO> getPropertiesWithPrefix(String prefix, String configuration) {
    if (logger.isDebugEnabled()) {
      logger.debug("getProperties(prefix={}, configuration={}) - start", prefix, configuration);
    }
    
    List<PropertyConfigurationDTO> listPropertyConfigurationDTO = new ArrayList<>();
    List<PropertyConfiguration> listPropertyConfigurationEnt = propertyConfRepository
        .findByClaveStartingWithAndConfiguracion(prefix, configuration);
    
    if (listPropertyConfigurationEnt != null) {
      listPropertyConfigurationDTO
      .addAll(ListMapper.mapList(listPropertyConfigurationEnt, mapper, PropertyConfigurationDTO.class));
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getProperties(List<PropertyConfigurationDTO>) - end - return value={}", listPropertyConfigurationDTO);
    }
    
    return listPropertyConfigurationDTO;
  }
  
  @Override
  public void saveProperty(PropertyConfigurationDTO propertyConfigurationDTO) {
    if (logger.isDebugEnabled()) {
      logger.debug("saveProperty(propertyConfigurationDTO={}) - start", propertyConfigurationDTO);
    }
    
    if (propertyConfigurationDTO != null) {
      PropertyConfiguration entity = mapper.map(propertyConfigurationDTO, PropertyConfiguration.class);
      entity.setPk(new PropertyConfigurationPK(propertyConfigurationDTO.getClave(), propertyConfigurationDTO.getConfiguracion()));
      propertyConfRepository.save(entity);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("saveProperty() - end");
    }
  }
  
  @Override
  public void deleteProperty(PropertyConfigurationDTO propertyConfigurationDTO) {
    if (logger.isDebugEnabled()) {
      logger.debug("deleteProperty(propertyConfigurationDTO={}) - start", propertyConfigurationDTO);
    }
    
    if (propertyConfigurationDTO != null) {
      PropertyConfiguration entity = new PropertyConfiguration();
      entity.setPk(new PropertyConfigurationPK(propertyConfigurationDTO.getClave(), propertyConfigurationDTO.getConfiguracion()));
      propertyConfRepository.delete(entity);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("deleteProperty() - end");
    }
  }

  @Override
  public String getNextPropertyKey(String prefix) {
    if (logger.isDebugEnabled()) {
      logger.debug("getNextPropertyKey(prefix={}) - start", prefix);
    }
    
    Integer lastSequence = 0;
    
    List<PropertyConfigurationDTO> allPropertiesForPrefix = this.getPropertiesWithPrefix(prefix);
    
    for (PropertyConfigurationDTO propertyConfiguration : allPropertiesForPrefix) {
      String key = propertyConfiguration.getClave().replaceAll(prefix, "");
      
      if (StringUtils.isNumeric(key) && Integer.parseInt(key) > lastSequence) {
        lastSequence = Integer.parseInt(key);
      }
    }
    
    String nextPropertyKey = prefix + (lastSequence + 1);
    
    if (logger.isDebugEnabled()) {
      logger.debug("getNextPropertyKey() - end - return value={}", nextPropertyKey);
    }
    
    return nextPropertyKey;
  }

  @Override
  public boolean isUsedValue(String prefix, String value) {
    if (logger.isDebugEnabled()) {
      logger.debug("isUsedValue(prefix={}, value={}) - start", prefix, value);
    }
    
    List<PropertyConfiguration> listPropertyConfigurationEnt = propertyConfRepository
        .findByClaveStartingWithAndConfiguracionAndValor(prefix, CONFIGURATION_SYSTEM, value);
    
    boolean result = !listPropertyConfigurationEnt.isEmpty();
    
    if (logger.isDebugEnabled()) {
      logger.debug("isUsedValue() - end - return value={}", result);
    }
    
    return result;
  }
  
}