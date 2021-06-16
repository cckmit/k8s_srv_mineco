package com.egoveris.deo.base.dao;

import com.egoveris.deo.model.model.ActuacionSADEBean;

import java.util.List;

public interface TipoActuacionDAO {

  List<ActuacionSADEBean> obtenerListaTodasLasActuaciones();

  ActuacionSADEBean obtenerActuacionPorCodigo(String codigo);

}