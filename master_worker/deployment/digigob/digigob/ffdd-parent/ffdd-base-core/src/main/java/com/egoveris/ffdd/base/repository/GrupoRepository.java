package com.egoveris.ffdd.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
	
	Grupo findOneByNombre(String nombre) throws AccesoDatoException;

}
