package com.egoveris.deo.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egoveris.deo.base.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>{

	public Optional<Template> findByCodigo(String codigo);
	public List<Template> findByFormato(String formato);
	
}
