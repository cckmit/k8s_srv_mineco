package com.egoveris.vucfront.base.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egoveris.vucfront.base.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>{

	public Optional<Template> findByCodigo(String codigo);

}
