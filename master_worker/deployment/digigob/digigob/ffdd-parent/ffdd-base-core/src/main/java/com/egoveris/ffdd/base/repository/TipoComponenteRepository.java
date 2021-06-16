package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.model.TipoComponente;

public interface TipoComponenteRepository extends JpaRepository<TipoComponente, Integer> {

	TipoComponente findOneByNombre(final String nombre) throws AccesoDatoException;

	@Query("SELECT SUM(1) FROM Componente c WHERE c.tipoComponenteEnt.id=:idTipo")
	Integer findIfTypeIsUsed(@Param("idTipo") final Integer id);

	List<TipoComponente> findByFactory(final String factory);
	
	@Query("SELECT nombre FROM TipoComponente ")
	List<TipoComponente> findNombresComponente();
	
}
