package com.egoveris.te.base.dao;

import com.egoveris.te.base.model.BuzonBean;

import java.util.List;


public interface IBuzonDAO {

	public List<BuzonBean> obtenerListaTaskSinAsignacion();	
}
