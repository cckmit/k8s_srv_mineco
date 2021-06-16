package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.Suscripcion;
import com.egoveris.deo.base.model.SuscripcionPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, SuscripcionPK> {

  List<Suscripcion> findBySuscripcionPK(SuscripcionPK suscripcionPK);

}
