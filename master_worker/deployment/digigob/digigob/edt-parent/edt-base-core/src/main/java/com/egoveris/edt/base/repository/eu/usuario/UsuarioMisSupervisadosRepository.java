package com.egoveris.edt.base.repository.eu.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.edt.base.model.eu.usuario.UsuarioMisSupervisados;
import com.egoveris.edt.base.repository.UsuariosRepository;

/**
 * The Interface UsuarioMisSupervisadosRepository.
 */
public interface UsuarioMisSupervisadosRepository
    extends JpaRepository<UsuarioMisSupervisados, Integer>, UsuariosRepository {

  @Query(value = "select em.* from EU_APLICACION ea,EU_USUARIO_MISSUPERVISADOS em where ea.ID = em.APLICACIONID and ea.VISIBLEMISSUPERVISADOS = 1 and em.USUARIO = ?1", nativeQuery = true)
  List<UsuarioMisSupervisados> buscarAplicacionesPorUsuario(String userName);

}