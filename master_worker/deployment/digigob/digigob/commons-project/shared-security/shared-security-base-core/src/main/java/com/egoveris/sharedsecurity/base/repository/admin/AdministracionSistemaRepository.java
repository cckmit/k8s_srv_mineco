package com.egoveris.sharedsecurity.base.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.sharedsecurity.base.model.admin.AdministradorSistema;

/**
 * The Interface AdministracionSistemaRepository.
 */
public interface AdministracionSistemaRepository
    extends JpaRepository<AdministradorSistema, Integer> {

  /**
   * Método que retorna una lista de registros realizando la búsqueda según el
   * nombre usuario entregado por parámetro.
   *
   * @param nombreUsuario
   *          the nombre usuario
   * @return the list
   */
  List<AdministradorSistema> findByNombreUsuario(String nombreUsuario);

}