package com.egoveris.ffdd.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.ffdd.base.model.Formulario;

public interface FormularioRepository extends JpaRepository<Formulario, Integer> {

	Formulario findByNombre(String nombre);

}
