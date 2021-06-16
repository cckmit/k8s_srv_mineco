package com.egoveris.te.base.repository.expediente;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.expediente.ExpedienteElectronico;

public interface ExpedienteElectronicoRepository
    extends JpaRepository<ExpedienteElectronico, Long> {

  List<ExpedienteElectronico> findByAnioAndNumeroIn(Integer anio, Collection<Integer> numeros);

  List<ExpedienteElectronico> findByEstado(String estado);

  ExpedienteElectronico findBySolicitudIniciadoraId(Long idSolicitudExpediente);

  List<ExpedienteElectronico> findByCodigoReparticionUsuarioAndAnioAndUsuarioCreador(
      String codigoRepar, Integer anio, String usuarioCreador);

  @Query(value = "SELECT ee FROM ExpedienteElectronico ee WHERE ee.tipoDocumento = :tipoDocumento AND ee.anio = :anio AND ee.numero = :numero AND ee.codigoReparticionUsuario = :codigoReparticionUsuario")
  ExpedienteElectronico obtenerExpedienteElectronico(@Param("tipoDocumento") String tipoDocumento,
      @Param("anio") Integer anio, @Param("numero") Integer numero,
      @Param("codigoReparticionUsuario") String codigoReparticionUsuario);

  ExpedienteElectronico findByidWorkflow(String idWorkflow);

  ExpedienteElectronico findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuario(
      String tipoDocumento, Integer anio, Integer numero, String codigoReparticionUsuario);

  List<ExpedienteElectronico> findByCodigoReparticionUsuarioAndAnioAndSistemaCreadorOrderByIdDesc(
      String codigoReparticionUsuario, Integer anio, String sistemaCreador);
}
