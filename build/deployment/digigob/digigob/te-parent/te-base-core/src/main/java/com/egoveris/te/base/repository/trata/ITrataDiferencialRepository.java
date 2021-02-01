package com.egoveris.te.base.repository.trata;

import com.egoveris.te.base.model.trata.TrataDiferencial;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrataDiferencialRepository extends  JpaRepository<TrataDiferencial, Long>{
 
	TrataDiferencial findByCodigoTrata(String codigoTrata);
	
}
