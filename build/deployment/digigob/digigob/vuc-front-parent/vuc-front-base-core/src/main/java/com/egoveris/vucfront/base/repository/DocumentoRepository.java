package com.egoveris.vucfront.base.repository;

import com.egoveris.vucfront.base.model.Documento;
import com.egoveris.vucfront.base.model.Persona;
import com.egoveris.vucfront.base.model.TipoDocumento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

  /**
   * Get's all DEO documents.
   * 
   * @param persona
   * @return
   */
  List<Documento> findByPersonaAndUrlTemporalIsNullOrderByFechaCreacionDesc(Persona persona);

  /**
   * Get's all DEO documents from a specified TipoDocumento.
   * 
   * @param persona
   * @return
   */
  List<Documento> findByPersonaAndTipoDocumentoAndUrlTemporalIsNullOrderByFechaCreacionDesc(
      Persona persona, TipoDocumento tipoDoc);
  //INI - + de 1 formulario
  List<Documento> findByUrlTemporal(String urlTemporal);
  //FIN - + de 1 formulario
  
  List <Documento> findByUrlTemporalOrderByIdTransaccionDesc(String urlTemporal);
  
  Documento findByIdTransaccion(Long idTransaccion);
  
  Documento findByNumeroDocumento(String numeroDocumento);
}
