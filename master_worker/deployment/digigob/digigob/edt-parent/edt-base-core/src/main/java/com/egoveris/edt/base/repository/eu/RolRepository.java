package com.egoveris.edt.base.repository.eu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.edt.base.model.eu.Rol;

/**
 * The Interface RolRepository.
 */
public interface RolRepository extends JpaRepository<Rol, Integer> {

  /**
   * Find by es vigente true.
   *
   * @return the list
   */
  public List<Rol> findByEsVigenteTrue();
  
  public Rol findByRolNombre(String nombre);

}