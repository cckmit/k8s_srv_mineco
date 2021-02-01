package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.UsuarioPlantilla;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioPlantillaRepository extends JpaRepository<UsuarioPlantilla, Integer> {
  
  List<UsuarioPlantilla> findByUsuario(String usuario);

}
