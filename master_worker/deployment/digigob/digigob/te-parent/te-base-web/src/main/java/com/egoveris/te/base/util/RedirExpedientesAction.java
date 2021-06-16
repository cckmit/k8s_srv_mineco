package com.egoveris.te.base.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.zest.ActionContext;
import org.zkoss.zk.ui.WrongValueException;

public class RedirExpedientesAction extends RedirAction {

  private final static Logger logger = LoggerFactory.getLogger(RedirExpedientesAction.class);

  public String execute(ActionContext ac) {
    String response = "error";
    try {
      Map<String, Object> hm = super.obtenerVariablesRedireccion(ac);
      HttpServletRequest hsr = ac.getServletRequest();
      if (!CollectionUtils.isEmpty(hm)) {
        String codigoEE = (String) hm.get("ID1");
        hm.put(ConstantesWeb.REDIRECT, true);
        if (StringUtils.isNotEmpty(codigoEE)) {
          if (RedirUtils.esCodigoDeExpediente(codigoEE)) {
            String[] codigo = codigoEE.split("-");
            hm.put("actuacion", codigo[0]);
            hm.put("anio", codigo[1]);
            hm.put("numero", codigo[2]);
            hm.put("reparticionActuacion", codigo[4]);
            hm.put("reparticionUsuario", codigo[5]);
            hm.put(ConstantesWeb.REDIRECT, true);
            response = "ok";
          }
        } else {
          response = "ok";
        }
        hsr.getSession().setAttribute(ConstantesWeb.PATHMAP, hm);
      }
    } catch (WrongValueException e) {
      logger.error("codigo de expeidente incorrecto", e);
    } catch (Exception e) {
      logger.error("ocurrio un error al redirigir", e);
    }
    return response;
  }

}
