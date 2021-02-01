package com.egoveris.te.base.repository.tipo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.ParametroActividad;

public interface IActividadParamRepository extends JpaRepository<ParametroActividad, Long> {
 
	@Query("SELECT DISTINCT act FROM ParametroActividad act where act.campo = 'PEDIDO_GEDO' and act.valor = :proceso ")
	ParametroActividad buscarProceso(@Param("proceso") String proceso);
}
