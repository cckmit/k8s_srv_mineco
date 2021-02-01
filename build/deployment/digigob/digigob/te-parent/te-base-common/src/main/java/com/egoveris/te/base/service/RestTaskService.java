package com.egoveris.te.base.service;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TaskUserAppDTO;
import com.egoveris.te.model.exception.ProcesoFallidoException;

public interface RestTaskService {

	public ExpedienteElectronicoDTO advanceTaskState(TaskUserAppDTO taskUser)
			throws ProcesoFallidoException, InterruptedException;
	
}
