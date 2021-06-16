package com.egoveris.te.base.repository.tipo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egoveris.te.base.model.tipo.TipoCombo;

public interface ITipoComboRepository extends JpaRepository<TipoCombo, Long> {
    
 // @Query("SELECT TipoCombo FROM TipoCombo WHERE grupo IN (SELECT id FROM DatosVariablesComboGrupos WHERE nombreGrupo = :nombre) AND fechaBaja = null ORDER BY id")
	@Query("SELECT ti FROM TipoCombo ti WHERE grupo IN (SELECT dt.id FROM DatosVariablesComboGrupos dt WHERE dt.nombreGrupo = :nombre)")
	List<TipoCombo> buscarDatosCombo(@Param("nombre") String nombre);
}