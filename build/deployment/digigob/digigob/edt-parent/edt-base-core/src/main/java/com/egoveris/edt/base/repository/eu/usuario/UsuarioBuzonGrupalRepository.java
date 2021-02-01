package com.egoveris.edt.base.repository.eu.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.edt.base.model.eu.usuario.UsuarioBuzonGrupal;
import com.egoveris.edt.base.repository.UsuariosRepository;

/**
 * The Interface UsuarioBuzonGrupalRepository.
 */
public interface UsuarioBuzonGrupalRepository
    extends JpaRepository<UsuarioBuzonGrupal, Integer>, UsuariosRepository {

  @Query(value = "select em.* from EU_APLICACION ea,EU_USUARIO_BUZONGRUPAL em where ea.ID = em.APLICACIONID and ea.VISIBLEBUZONGRUPAL = 1 and em.USUARIO = ?1", nativeQuery = true)
  List<UsuarioBuzonGrupal> buscarAplicacionesPorUsuario(String userName);

}