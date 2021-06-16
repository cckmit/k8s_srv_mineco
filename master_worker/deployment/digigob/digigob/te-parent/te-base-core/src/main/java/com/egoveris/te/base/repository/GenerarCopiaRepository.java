package com.egoveris.te.base.repository;



import com.egoveris.te.base.model.GenerarCopiaBean;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GenerarCopiaRepository extends JpaRepository<GenerarCopiaBean, Long> {
	
 List<GenerarCopiaBean> findByEstadoDeReintentoAndReintentosLessThan(String estadoPendiente ,Integer reintentos);
 

  
}
