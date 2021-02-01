package com.egoveris.te.base.repository.trata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.te.base.model.trata.TrataTipoResultado;

public interface TrataTipoResultadoRepository extends JpaRepository<TrataTipoResultado, Long> {
  
  List<TrataTipoResultado> findByClave(String property);
  
}