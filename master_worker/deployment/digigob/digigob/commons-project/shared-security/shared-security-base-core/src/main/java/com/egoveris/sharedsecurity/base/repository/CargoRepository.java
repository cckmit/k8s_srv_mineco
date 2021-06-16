package com.egoveris.sharedsecurity.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.egoveris.sharedsecurity.base.model.Cargo;

// TODO: Auto-generated Javadoc
/**
 * The Interface CargoRepository.
 */
public interface CargoRepository extends JpaRepository<Cargo, Integer> {

  /**
   * Find by es tipo baja false.
   *
   * @return the list
   */
  List<Cargo> findByEsTipoBajaFalse();

  /**
   * Find by cargo nombre.
   *
   * @param cargo
   *          the cargo
   * @return the cargo
   */
  Cargo findByCargoNombre(String cargo);

  /**
   * Find by id reparticion and vigente true.
   *
   * @param idReparticion
   *          the id reparticion
   * @return the list
   */
  List<Cargo> findByIdReparticionAndVigenteTrue(Integer idReparticion);

  /**
   * Obtener cargo usuario.
   *
   * @param usuario
   *          the usuario
   * @return the cargo
   */
  @Query(value = "SELECT * FROM edt_cargos cargo INNER JOIN edt_usuario_cargo ucargo WHERE ucargo.usuario = ?1", nativeQuery = true)
  Cargo obtenerCargoUsuario(String usuario);

  /**
   * Find by lista roles id.
   *
   * @param id
   *          the id
   * @return the list
   */
//  List<Cargo> findByListaRoles_Id(Integer id);
  
  
  /**
   * Find by vigente true.
   *
   * @return the list
   */
  List<Cargo> findByVigenteTrue();

}