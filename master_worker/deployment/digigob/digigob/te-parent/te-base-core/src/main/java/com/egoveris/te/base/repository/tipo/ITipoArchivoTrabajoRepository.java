package com.egoveris.te.base.repository.tipo;

import com.egoveris.te.base.model.tipo.TipoArchivoTrabajo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoArchivoTrabajoRepository extends JpaRepository<TipoArchivoTrabajo, Long> {
  
  TipoArchivoTrabajo findByNombre(String nombre);


}
