package com.egoveris.sharedsecurity.base.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.sharedsecurity.base.model.DatosUsuarioRol;

public interface DatosUsuarioRolRepository extends JpaRepository<DatosUsuarioRol, Integer> {

	@Query("select dr from DatosUsuarioRol dr "
			+ "inner join dr.id.datosUsuario d "
			+ "inner join dr.id.rol r where d.usuario = :username")
	public List<DatosUsuarioRol> findByUsername(@Param("username") String username);
}
