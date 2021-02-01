package com.egoveris.te.base.repository.tipo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.tipo.TipoOperacion;

public interface ITipoOperacionRepository extends JpaRepository<TipoOperacion, Long>{
	
	/**
	 * Metodo que busca un tipo de operacion por codigo
	 * 
	 * @param codigo Codigo del registro
	 * @return Tipo de operacion con el codigo dado
	 */
	 List<TipoOperacion> findByCodigo(String codigo); 
	
	/**
	 * Metodo que evalua si un tipo de operacion esta siendo utilizado por una operacion
	 * 
     * @param tipoOperacion Entity con el registro de tipo operacion
	 * @return 
	 */
	 @Query(value = "SELECT COUNT(tOp.id) FROM TipoOperacion tOp WHERE tOp.id IN (SELECT op.tipoOperacionOb.id FROM Operacion op WHERE op.tipoOperacionOb.id = :tipoOperacion)")
	 Integer isTipoOperacionEnUso(@Param("tipoOperacion") Long tipoOperacion);
	 
	 @Query(value = "SELECT tOp FROM TipoOperacion tOp WHERE tOp.id IN (SELECT tOpOrg.tipoOperacion.id FROM TipoOperacionOrganismo tOpOrg WHERE tOpOrg.reparticion.id = :idOrganismo)")
	 List<TipoOperacion> getTiposOperacionByOrganismoVigentes(@Param("idOrganismo") Integer idOrganismo);
	 
}
