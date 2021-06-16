package com.egoveris.sharedsecurity.base.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.sharedsecurity.base.model.Cargo;
import com.egoveris.sharedsecurity.base.model.DatosUsuario;

/**
 * The Interface DatosUsuarioRepository.
 */
public interface DatosUsuarioRepository extends JpaRepository<DatosUsuario, Integer> {

  /**
   * Find by usuario.
   *
   * @param userName
   *          the user name
   * @return the datos usuario
   */
  DatosUsuario findByUsuario(String userName);

  /**
   * Trae todos los usuarios en los que figure como asesor.
   *
   * @param userName
   *          the user name
   * @return the list
   */
  List<DatosUsuario> findByUsuarioAsesor(String userName);

  /**
   * Trae todos los usuarios en los figure como Superior.
   *
   * @param userName
   *          the user name
   * @return the list
   */
  List<DatosUsuario> findByUserSuperior(String userName);

  /**
   * Trae todos los usuarios en los figure como secretario.
   *
   * @param userName
   *          the user name
   * @return the list
   */
  List<DatosUsuario> findBySecretario(String userName);

  /**
   * Indica verifica si ya existe el cuil/cuit en la tabla de DatosUsuarios.
   *
   * @param cuit
   *          the cuit
   * @return true si existe, caso contrario false
   */
  List<DatosUsuario> findByNumeroCuit(String cuit);


  /**
   * Buscar por sector.
   *
   * @param idSector
   *          the id sector
   * @return the list
   */
  @Query(value = "SELECT * FROM datos_usuario a INNER JOIN sade_sector_usuario b WHERE a.usuario = b.nombre_usuario AND b.id_sector_interno = ?1", nativeQuery = true)
  List<DatosUsuario> buscarPorSector(Integer idSector);

  /**
   * Find by id sector interno.
   *
   * @param id
   *          the id
   * @return the list
   */
  List<DatosUsuario> findByIdSectorInterno(Integer id);

  /**
   * Find by cargo.
   *
   * @param cargo the cargo
   * @return the list
   */
  List<DatosUsuario> findByCargoAsignado(Cargo cargo);

}