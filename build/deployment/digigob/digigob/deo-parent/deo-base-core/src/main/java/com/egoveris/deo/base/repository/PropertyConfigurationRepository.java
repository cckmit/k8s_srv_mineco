package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.PropertyConfiguration;
import com.egoveris.deo.base.model.PropertyConfigurationPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PropertyConfigurationRepository extends JpaRepository<PropertyConfiguration, PropertyConfigurationPK> {

	@Modifying
	@Query("update PropertyConfiguration set valor=?1 where clave=?2")
	int updateProperty(String valor, String clave);

	PropertyConfiguration findByClave(String clave);
	
}
