package com.egoveris.ccomplejos.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoJPA;

@NoRepositoryBean
public interface AbstractCComplejoRepository<T extends AbstractCComplejoJPA> extends JpaRepository<T, Integer>{

	Long removeByIdOperacionAndNombre(Integer idOperacion, String nombre);

	List<AbstractCComplejoJPA> findByIdOperacionAndNombreOrderByOrdenAsc(Integer idOperacion, String nombre);

	List<AbstractCComplejoJPA> findByIdOperacionOrderByOrdenAsc(Integer idOperacion);
	
}