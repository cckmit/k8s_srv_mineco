package com.egoveris.te.base.repository.trata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.trata.Trata;
import com.egoveris.te.base.model.trata.TrataTipoDocumento;

public interface TrataTipoDocumentoRepository extends JpaRepository<TrataTipoDocumento, Long> {

  List<TrataTipoDocumento> findByTrata(Trata trata);
  
  @Modifying
  @Query("DELETE FROM TrataTipoDocumento ttd WHERE ttd.acronimoGEDO = :acronimoGedo AND ttd.trata.id = :idTrata")
  void deleteByAcronimoGedoAndTrataId(@Param("acronimoGedo") String acronimoGedo, @Param("idTrata") Long idTrata);
  
}