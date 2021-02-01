package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.expediente.ExpedienteFormulario;

public interface IExpedienteFormularioRepository
    extends JpaRepository<ExpedienteFormulario, Long> {

  List<ExpedienteFormulario> findByIdEeExpedientOrderByDateCrationDesc(Long idEeExpedient);

  @Modifying
  @Query("UPDATE ExpedienteFormulario  set isDefinitive = :isDefinitive where idEeExpedient = :idExpediente")
  void makeDefinitive(@Param("isDefinitive")  Integer isDefinitive, @Param("idExpediente") Long idExpediente);

}
