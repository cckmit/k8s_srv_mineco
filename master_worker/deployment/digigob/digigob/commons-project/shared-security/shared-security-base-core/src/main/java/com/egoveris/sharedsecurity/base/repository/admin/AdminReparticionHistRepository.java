package com.egoveris.sharedsecurity.base.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.sharedsecurity.base.model.admin.AdminReparticionHistorico;

/**
 * The Interface AdminReparticionHistRepository.
 */
public interface AdminReparticionHistRepository
    extends JpaRepository<AdminReparticionHistorico, Integer> {

  /**
   * Método que retorna una lista de registros ordenados por fecha de revisión
   * (desc) según nombre de usuario entregado por parámetro.
   *
   * @param nombreUsuario
   *          the nombre usuario
   * @return the list
   */
  List<AdminReparticionHistorico> findByNombreUsuarioOrderByFechaRevisionDesc(
      String nombreUsuario);
}