package com.egoveris.ffdd.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.ffdd.base.model.Componente;

public interface ComponenteRepository extends JpaRepository<Componente, Integer>{

	Componente findOneByNombre(String nombre);
	
	@Query("SELECT comp FROM Componente comp join fetch comp.tipoComponenteEnt tipoComp where comp.abmComponent=1 and (tipoComp.nombre='TEXTBOX' or tipoComp.nombre='LONGBOX')")
	List<Componente> obtenerComponentesABMLongBoxYTextBox();
	
	@Query("SELECT C FROM Componente C WHERE C.id IN (SELECT M.componente.id FROM Item M)")
	List<Componente> obtenerTodosLosComponentesMultivalores();

	@Query("SELECT DISTINCT nombreXml FROM Componente WHERE not nombreXml is null ORDER BY nombreXml ASC")
	List<String> findDistinctNombreXml();

	List<Componente> findByNombreXmlNotNull();

	@Query("SELECT DISTINCT nombre FROM Componente WHERE nombreXml=:xml  ORDER BY nombre ASC")
	List<String> findComponentNameByXml(@Param("xml") String xml);

	@Query("SELECT sum(1) FROM FormularioComponente fc WHERE fc.componente.nombre LIKE :nombre GROUP BY fc.componente.id")
	Integer findUsedFormComponent(@Param("nombre") String nombre);

	@Query("SELECT sum(1) FROM DynamicComponentField dc where dc.parent like :nombre group by dc.parent")
	Integer findUsedSubComponent(@Param("nombre") String nombre);

	@Query("SELECT DISTINCT nombre FROM Componente WHERE tipoComponenteEnt.factory LIKE :factory  ORDER BY nombre ASC")
	List<String> findComponentNameByFactory(@Param("factory") String factory);
}
