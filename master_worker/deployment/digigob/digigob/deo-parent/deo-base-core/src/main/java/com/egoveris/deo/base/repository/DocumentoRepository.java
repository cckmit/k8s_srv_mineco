package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.model.TipoDocumento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

  Documento findByNumeroLike(String numero);
  
  List<Documento> findByNumero(String numero);
  
  List<Documento> findByTipo(TipoDocumento tipo);
  
 // List<Documento> findByFechaDepuracionIsNullAndMotivoDepuracionNotNull();
  
  Documento findByWorkflowOrigen(String workflowOrigen);
  
  @Query("select documento from Documento documento join fetch documento.tipo tipoDoc where documento.numero =:numero and tipoDoc.acronimo =:acronimo")
  List<Documento> buscarDocumentoEstandarByNumeroConsulta(@Param("numero") String numero, @Param("acronimo") String acronimo);
  
  @Modifying
  @Query("update Documento set idGuardaDocumental=:idGuardaDocumental where numero=:numeroDocumento")
  void actualizarIdGuardaDocumental(@Param("idGuardaDocumental") String idGuardaDocumental, @Param("numeroDocumento") String numeroDocumento);
  
  @Query("select documento from Documento documento where documento.idPublicable = :idPublicable")
  Documento buscarPorIdPublicable(@Param("idPublicable") String idPublicable);
  
  @Query("select doc from Documento doc join fetch doc.tipo tipoDoc "
  		+ "where tipoDoc.codigoTipoDocumentoSade = :acronimoActuacion and "
  		+ "doc.numero = :numero")
  List<Documento> buscarDocumentoPorNumeroYactuacion(@Param("numero") String numero,
  		@Param("acronimoActuacion") String acronimoActuacion);
  
}
