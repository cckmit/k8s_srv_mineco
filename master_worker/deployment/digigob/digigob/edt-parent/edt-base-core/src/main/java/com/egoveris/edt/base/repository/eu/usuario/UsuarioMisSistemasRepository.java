package com.egoveris.edt.base.repository.eu.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.edt.base.model.eu.usuario.UsuarioMisSistemas;
import com.egoveris.edt.base.repository.UsuariosRepository;

/**
 * The Interface UsuarioMisSistemasRepository.
 *
 * @author pfolgar
 */
public interface UsuarioMisSistemasRepository
    extends JpaRepository<UsuarioMisSistemas, Integer>,UsuariosRepository {

  @Query(value = "select em.* from EU_APLICACION ea,EU_USUARIO_MISSISTEMAS em where ea.ID = em.APLICACIONID and ea.VISIBLEMISSISTEMAS = 1 and em.USUARIO = ?1", nativeQuery = true)
  List<UsuarioMisSistemas> buscarAplicacionesPorUsuario(String userName);
}
