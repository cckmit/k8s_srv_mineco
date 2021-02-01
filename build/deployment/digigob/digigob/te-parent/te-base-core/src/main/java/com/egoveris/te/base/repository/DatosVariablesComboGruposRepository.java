package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.DatosVariablesComboGrupos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DatosVariablesComboGruposRepository
    extends JpaRepository<DatosVariablesComboGrupos, Long> {

  List<DatosVariablesComboGrupos> findByTipo(String tipo);

}
