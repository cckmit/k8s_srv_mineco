package com.egoveris.edt.base.repository.eu.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.edt.base.model.eu.usuario.UsuarioMisTareas;
import com.egoveris.edt.base.repository.UsuariosRepository;

/**
 * The Interface UsuarioMisTareasRepository.
 *
 * @author pfolgar
 */
public interface UsuarioMisTareasRepository
    extends JpaRepository<UsuarioMisTareas, Integer>, UsuariosRepository {

  @Query(value = "select em.* from EU_APLICACION ea,EU_USUARIO_MISTAREAS em where ea.ID = em.APLICACIONID and ea.VISIBLEMISTAREAS  = 1 and em.USUARIO = ?1", nativeQuery = true)
  List<UsuarioMisTareas> buscarAplicacionesPorUsuario(String userName);
}