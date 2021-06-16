package com.egoveris.te.base.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.dao.ExpedienteElectronicoDAO;
import com.egoveris.te.base.service.iface.IFullImportSolr;
import com.egoveris.te.base.util.ApplicationContextProvider;

@Service
@Transactional
public class FullImportSolrCoreEE extends HttpServlet implements IFullImportSolr {

  /**
  * 
  */
  private static final long serialVersionUID = -5258901177478914878L;

  private static transient Logger logger = LoggerFactory.getLogger(FullImportSolrCoreEE.class);

  private static String OP_TRIGGER = "trigger";
  private static String VAR_OP = "operacion";
  private static String VAR_JOB = "job";
  private static String VAR_GROUP = "group";

  @Autowired
  private ExpedienteElectronicoDAO daoEEIndexar;

  @SuppressWarnings({ "unchecked", "deprecation" })
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("doGet(request={}, response={}) - start", request, response);
    }

    response.setContentType("text/html");
    StringBuilder builder = new StringBuilder();
    builder.append("<HTML><BODY>");
    builder.append("<h1>Admin solr-Core-EE-full-import</h1>");
    builder.append("<b>Hora de sistema</b>: " + new Date());
    builder.append("<br>");
    builder.append("<br>");

    builder.append("<h2>Scheduler: " + "Full - Import - solr Core-ee" + "</h2>");

    builder.append(
        "<form name='form_" + OP_TRIGGER + "' id='form_" + OP_TRIGGER + "' method='post'>");
    builder.append("<li>");
    builder.append("<input type='submit' value='Iniciar ahora'/>");
    builder.append("<input type='hidden' name='" + VAR_JOB + "' id='" + VAR_JOB + "' value='"
        + "/home/everis/" + "'/>");
    builder.append("<input type='hidden' name='" + VAR_GROUP + "' id='" + VAR_GROUP + "' value='"
        + "script.sql" + "'/>");
    builder.append("<input type='hidden' name='" + VAR_OP + "' id='" + VAR_OP + "' value='"
        + OP_TRIGGER + "'/>");
    builder.append("</li>");
    builder.append("</form>");
    builder.append("</body>");
    builder.append("</html>");
    ServletOutputStream salida = response.getOutputStream();
    salida.println(builder.toString());
    salida.flush();

    if (logger.isDebugEnabled()) {
      logger.debug("doGet(HttpServletRequest, HttpServletResponse) - end");
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("doPost(request={}, response={}) - start", request, response);
    }

    response.setContentType("text/html");
    ServletOutputStream salida = response.getOutputStream();
    try {
      this.realizarFullImportSolrCoreEE();
      salida.println("Post correcto! <a href='." + request.getServletPath() + "'>volver</a>");
      // TODO Hacerlo Mas Pro

    } catch (Exception e) {
      logger.error("doPost(HttpServletRequest, HttpServletResponse)", e);

      throw new ServletException(e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("doPost(HttpServletRequest, HttpServletResponse) - end");
    }
  }

  public void realizarFullImportSolrCoreEE() {

    logger.info("Inicia Full Import EE");
    this.daoEEIndexar = (ExpedienteElectronicoDAO) ApplicationContextProvider
        .getApplicationContext().getBean("expedienteElectronicoDAO");
    this.daoEEIndexar.buscarTodoslosExpedientesElectronicosEindexar();
    logger.info("Finaliza Full Import EE");

  }

  public ExpedienteElectronicoDAO getDaoEEIndexar() {
    return daoEEIndexar;
  }

  public void setDaoEEIndexar(ExpedienteElectronicoDAO daoEEIndexar) {
    this.daoEEIndexar = daoEEIndexar;
  }

}
