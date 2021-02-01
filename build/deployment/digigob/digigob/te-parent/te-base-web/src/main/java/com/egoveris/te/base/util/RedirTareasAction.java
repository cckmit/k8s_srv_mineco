package com.egoveris.te.base.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.zest.ActionContext;
import org.zkoss.zk.ui.WrongValueException;

public class RedirTareasAction extends RedirAction {

  private final static Logger logger = LoggerFactory.getLogger(RedirTareasAction.class);

  public String execute(ActionContext ac) {
    String response = "error";
    try {
      Map<String, Object> hm = super.obtenerVariablesRedireccion(ac);
      if (!CollectionUtils.isEmpty(hm)) {
        Utils.isNumericLength((String) hm.get("ID1"));
        HttpServletRequest hsr = ac.getServletRequest();
        hm.put(ConstantesWeb.REDIRECT, true);
        hsr.getSession().setAttribute(ConstantesWeb.PATHMAP, hm);
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
