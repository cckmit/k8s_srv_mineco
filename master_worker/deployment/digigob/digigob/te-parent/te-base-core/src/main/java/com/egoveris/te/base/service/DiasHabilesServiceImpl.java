package com.egoveris.te.base.service;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.UtilsDate;


@Service
@Transactional
public class DiasHabilesServiceImpl extends Thread implements DiasHabilesService {
  private static Logger logger = (Logger) LoggerFactory.getLogger(DiasHabilesServiceImpl.class);
  private boolean stopThread;

  private long tiempo = 60000;

  public long getTiempo() {
    return tiempo;
  }

  public void setTiempo(long tiempo) {
    this.tiempo = tiempo;
  }

  public Date HabilitadoHasta(Date fecha) {
    if (logger.isDebugEnabled()) {
      logger.debug("HabilitadoHasta(fecha={}) - start", fecha);
    }

		Date fechaHabilitada = UtilsDate.fechaApartirDe(fecha, ConstantesCommon.CANTIDAD_DIAS);

    if (logger.isDebugEnabled()) {
      logger.debug("HabilitadoHasta(Date) - end - return value={}", fechaHabilitada);
    }
    return fechaHabilitada;
  }

  private void cargarFeriados() {
    logger.info("comenzando carga de feriados");
    setDaemon(true);
    start();
    try {
    } catch (Exception e) {
      logger.error("error al cargar feriados", e);
      logger.info("la carga de feriados no se realizo correctamente verifique la conexion con EU");
    }
    logger.info("la carga de feriados se realizo correctamente");
  }

  public void run() {
    if (logger.isDebugEnabled()) {
      logger.debug("run() - start");
    }

    while ((isAlive()) && (!(this.stopThread))) {
      synchronized (this) {
        try {
          super.wait(this.tiempo);
        } catch (InterruptedException e) {
          logger.error("Error de wait: " + e.getMessage(), e);
        }
      }

      logger.info("Cron Sincroniza Feriados");
      if ((isAlive()) && (!(this.stopThread))) {
        try {

        } catch (Exception e) {
          logger.error(
              "la carga de feriados no se realizo correctamente verifique la conexion con EU "
                  + e.getMessage(),
              e);
        }
      }

      logger.info("la carga de feriados se realizo correctamente ");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("run() - end");
    }
  }

  public void killThread() {
    if (logger.isDebugEnabled()) {
      logger.debug("killThread() - start");
    }

    logger.info("Stopping Cron Sincroniza Feriados");
    setStopThread(true);

    if (logger.isDebugEnabled()) {
      logger.debug("killThread() - end");
    }
  }

  public boolean isStopThread() {
    return stopThread;
  }

  public void setStopThread(boolean stopThread) {
    this.stopThread = stopThread;
  }

}
