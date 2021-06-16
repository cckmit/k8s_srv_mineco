package com.egoveris.deo.web.satra.zest;

import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.utils.RedirUtils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.zest.ActionContext;
import org.zkoss.zk.ui.WrongValueException;

public class RedirTareasAction extends RedirAction {

	private static final Logger logger = LoggerFactory.getLogger(RedirTareasAction.class);

	public String execute(ActionContext ac) {
		String response = "error";
		try {
			HashMap<String, Object> hm = (HashMap<String, Object>) super.obtenerVariablesRedireccion(ac);
			if (!CollectionUtils.isEmpty(hm)) {
				RedirUtils.isNumericLength((String) hm.get("ID1"));
				hm.put(Constantes.REDIRECT, true);
				HttpServletRequest hsr = ac.getServletRequest();
				hsr.getSession().setAttribute(Constantes.PATHMAP, hm);
				response = "ok";
			}
		} catch (WrongValueException e) {
			logger.error("el id debe ser numérico", e);
		} catch (Exception e) {
			logger.error("ocurrio un error al redirigir", e);
		}
		return response;
	}

}
