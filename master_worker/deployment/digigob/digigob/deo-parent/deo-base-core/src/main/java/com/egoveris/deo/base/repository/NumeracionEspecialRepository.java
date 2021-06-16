package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.NumeracionEspecial;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NumeracionEspecialRepository extends JpaRepository<NumeracionEspecial, Integer> {

  NumeracionEspecial findByCodigoReparticionAndIdTipoDocumentoAndAnio(String codigoReparticion,
      Integer idTipoDocumento, String anio);

  NumeracionEspecial findByCodigoReparticionAndIdTipoDocumentoAndAnioAndCodigoEcosistema(
      String codigoReparticion, Integer idTipoDocumento, String anio, String codigoEcosistema);

  NumeracionEspecial findByCodigoReparticionAndIdTipoDocumentoAndAnioAndCodigoEcosistemaIsNull(
      String codigoReparticion, Integer idTipoDocumento, String anio);

  NumeracionEspecial findById(Integer id);

  List<NumeracionEspecial> findByAnioAndCodigoReparticion(String anio, String codigoReparticion);

  List<NumeracionEspecial> findByIdTipoDocumento(Integer idTipoDocumento);

  NumeracionEspecial findByCodigoReparticionAndIdTipoDocumentoAndCodigoEcosistemaIsNullOrderByAnio(
      String codigoReparticion, Integer idTipoDocumento);

  NumeracionEspecial findByCodigoReparticionAndIdTipoDocumentoAndCodigoEcosistemaOrderByAnio(
      String codigoReparticion, Integer idTipoDocumento, String codigoEcosistema);

  @Modifying
  @Query("UPDATE NumeracionEspecial SET locked= ?1 WHERE anio= ?2 and idTipoDocumento= ?3 and codigoReparticion= ?4 and codigoEcosistema is null and (locked IS NULL or locked< ?5)")
  void lockNumeroEspecial(Long timenow, String anio, Integer tipoDocumento, String reparticion,
      Long lockTime);

  @Modifying
  @Query("UPDATE NumeracionEspecial SET locked= ?1 WHERE anio= ?2 and codigoEcosistema = ?3 and idTipoDocumento= ?4 and codigoReparticion= ?5 and (locked IS NULL or locked< ?6)")
  void lockNumeroEspecialEcosistema(Long timenow, String anio, String codEcosistema,
      Integer idTipoDocumento, String reparticion, Long lockTime);

  @Modifying
  @Query("UPDATE NumeracionEspecial SET locked=NULL WHERE anio= ?1 and idTipoDocumento= ?2 and codigoReparticion= ?3 and codigoEcosistema is null")
  void unlockNumeroEspecial(String anio, Integer idTipoDocumento, String reparticion);

  @Modifying
  @Query("UPDATE NumeracionEspecial SET locked=NULL WHERE anio= ?1 and idTipoDocumento= ?2 and codigoEcosistema = ?3 and codigoReparticion= ?4")
  void unlockNumeroEspecialEcosistema(String anio, Integer idTipoDocumento, String ecosistema,
      String reparticion);

}
