package com.egoveris.edt.base.repository.eu.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuencia;

/**
 * The Interface UsuarioFrecuenciasRepository.
 */
public interface UsuarioFrecuenciasRepository extends JpaRepository<UsuarioFrecuencia, Integer> {

  /**
   * Find by usuario.
   *
   * @param userName
   *          the user name
   * @return the usuario frecuencia
   */
  UsuarioFrecuencia findByUsuario(String userName);

}