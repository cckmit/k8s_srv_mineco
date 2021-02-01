package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.ffdd.base.model.FormTrigger;

public interface FormTriggerRepository extends JpaRepository<FormTrigger, Integer> {

	List<FormTrigger> findByIdForm(Integer id);
	
	List<FormTrigger> findByIdFormAndType(Integer id, String type);
		
}
