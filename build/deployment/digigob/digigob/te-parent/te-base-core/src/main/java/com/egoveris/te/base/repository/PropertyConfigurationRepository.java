package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.te.base.model.PropertyConfiguration;

public interface PropertyConfigurationRepository extends JpaRepository<PropertyConfiguration, String> {
  
  List<PropertyConfiguration> findByClaveStartingWithAndConfiguracion(String prefix, String configuration);
  
  List<PropertyConfiguration> findByClaveStartingWithAndConfiguracionAndValor(String prefix, String configuration, String value);
  
}
