package com.egoveris.deo.web.satra.zest;

import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.utils.RedirUtils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.zest.ActionContext;
import org.zkoss.zk.ui.WrongValueException;

public class RedirDocumentosAction extends RedirAction {

	private static final Logger logger = LoggerFactory.getLogger(RedirDocumentosAction.class);

	public String execute(ActionContext ac) {
		String response = "error";
		try {
			HashMap<String, Object> hm = (HashMap<String, Object>) super.obtenerVariablesRedireccion(ac);
			HttpServletRequest hsr = ac.getServletRequest();
			if (!CollectionUtils.isEmpty(hm)) {
				hm.put(Constantes.REDIRECT, true);
				String numDoc = (String) hm.get("ID1");
				if (StringUtils.isNotEmpty(numDoc)) {
					if (RedirUtils.esNumeroDocumento(numDoc)) {
						String[] codigo = numDoc.split("-");
						hm.put("tipo", codigo[0]);
						hm.put("anio", codigo[1]);
						hm.put("numero", codigo[2]);
						hm.put("reparticion", codigo[4]);
						response = "ok";
					}
				} else {
					response = "ok";
				}
				hsr.getSession().setAttribute(Constantes.PATHMAP, hm);
			}
		} catch (WrongValueException e) {
			logger.error("número de documento incorrecto", e);
		} catch (Exception e) {
			logger.error("ocurrio un error al redirigir", e);

		}
		return response;
	}

}
