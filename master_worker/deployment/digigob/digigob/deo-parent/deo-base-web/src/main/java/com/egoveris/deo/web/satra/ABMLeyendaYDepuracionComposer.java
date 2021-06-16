package com.egoveris.deo.web.satra;

import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.PropertyConfiguracionesService;

import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMLeyendaYDepuracionComposer extends GenericForwardComposer {

  private static final long serialVersionUID = 8097585628277595000L;

  @WireVariable("propertyConfiguracionesServiceImpl")
  private PropertyConfiguracionesService propertyConfiguracionesService;

  @WireVariable("pdfServiceImpl")
  PdfService pdfService;

  private static final Logger logger = LoggerFactory
      .getLogger(ABMLeyendaYDepuracionComposer.class);

  private static final String LEYENDA_CONMEMORATIVA = "gedo.leyendaConmemorativa";
  private static final String LIMPIEZA_TEMPORALES_CRON_PL = "gedo.limpiezaTemporales.cronProcesoLimpieza";
  private static final String LIMPIEZA_TEMPORALES_AVISO_MAIL = "gedo.limpiezaTemporales.cronAvisoMail";
  private static final String LIMPIEZA_TEMPORALES_DIFERENCIA_DIAS = "gedo.limpiezaTemporales.diferenciaDeDiasEntreMailYLimpieza";

  private Textbox datoLeyenda;
  private Datebox fechaDepuracionAvisoMail;
  private Datebox fechaDepuracionLimpieza;
  private String usuarioActual = "";

  private String leyenda = "";
  private String depuracionConProcesoLimpieza = "";
  private String depuracionCronAvisoMail = "";

  private Toolbarbutton creardepuracion;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    inicializarTextBox();
    obtenerUsuarioActual();
    this.fechaDepuracionAvisoMail.setConstraint(new ValidacionDepuracionConstraint());
    this.fechaDepuracionLimpieza.setConstraint(new ValidacionDepuracionConstraint());
  }

  public void onClick$limpiarLeyenda() {
    datoLeyenda.setValue(leyenda);
  }

  @SuppressWarnings("unchecked")
  public void onClick$crearleyenda() throws InterruptedException {
    Messagebox.show(Labels.getLabel("gedo.confirmar.ABM.leyenda"),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener() {
          @Override
          public void onEvent(Event evt) throws InterruptedException, SQLException {
            if ("onYes".equals(evt.getName())) {
              try {
                boolean res = propertyConfiguracionesService
                    .modificarProperty(LEYENDA_CONMEMORATIVA, datoLeyenda.getValue());
                notificarActualizacion(res, " leyenda.");
                limpiarTextBoxLeyenda();
                logger.info("El usuario " + usuarioActual + " ha modificado la leyenda de GEDO");
              } catch (WrongValueException ex) {
                logger.error("El dato ingresado no es correcto");
                throw ex;
              } catch (Exception e) {
                logger.error("Error al crear leyenda " + e.getMessage(), e);
                Messagebox.show(e.getMessage(), Labels.getLabel("gedo.general.error"),
                    Messagebox.OK, Messagebox.ERROR);
              }
            }
          }
        });
  }

  @SuppressWarnings("unchecked")
  public void onClick$creardepuracion() throws InterruptedException {
    Messagebox.show(Labels.getLabel("gedo.confirmar.ABM.depuracion"),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new EventListener() {
          @Override
          public void onEvent(Event evt) throws InterruptedException {
            if ("onYes".equals(evt.getName())) {
              try {
                boolean res;
                verificarTiempoCorrecto(fechaDepuracionLimpieza.getValue(),
                    fechaDepuracionAvisoMail.getValue());
                String diferenciaDias = obtenerDiferenciaDias(fechaDepuracionLimpieza.getValue(),
                    fechaDepuracionAvisoMail.getValue());

                res = propertyConfiguracionesService
                    .modificarProperty(LIMPIEZA_TEMPORALES_DIFERENCIA_DIAS, diferenciaDias);
                res = propertyConfiguracionesService.modificarProperty(LIMPIEZA_TEMPORALES_CRON_PL,
                    depuracionConProcesoLimpieza);
                res = propertyConfiguracionesService
                    .modificarProperty(LIMPIEZA_TEMPORALES_AVISO_MAIL, depuracionCronAvisoMail);

                notificarActualizacion(res, " depuración.");
                logger.info("El usuario " + usuarioActual + " ha modificado la depuracion");
              } catch (SQLException e) {
                logger.error("Error al actualizar la property Limpieza_Temporales: " + e);
                Messagebox.show(
                    Labels.getLabel("gedo.error.ABM.leyendaYDepuracion",
                        new String[] { e.getMessage() }),
                    Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
              } catch (WrongValueException ex) {
                logger.error("El dato ingresado no es correcto" + ex.getMessage(), ex);
                throw ex;
              } catch (Exception e) {
                Messagebox.show(e.getMessage(), Labels.getLabel("gedo.general.error"),
                    Messagebox.OK, Messagebox.ERROR);
              }
            }
          }
        });
  }

  /**
   * Permite verificar si los dias son correctos.
   */
  private void verificarTiempoCorrecto(Date dateDepuracionCronLimpieza,
      Date dateDepuracionCronAvisoMail) throws Exception {
    if (dateDepuracionCronAvisoMail.compareTo(dateDepuracionCronLimpieza) == 0) {
      throw new Exception(Labels.getLabel("gedo.abmLeyendaDepu.exception.fechasIguales"));
    }
    long diff = dateDepuracionCronLimpieza.getTime() - dateDepuracionCronAvisoMail.getTime();
    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 0) {
      throw new Exception(Labels.getLabel("gedo.error.ABM.leyendaYDepuracion.datosIncorrectos"));
    }
    if (dateDepuracionCronAvisoMail.getTime() < new Date().getTime()
        || dateDepuracionCronLimpieza.getTime() < new Date().getTime()) {
      throw new Exception(Labels.getLabel("gedo.error.ABM.leyendaYDepuracion.fechaHoy"));
    }
    if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > new Long(31)) {
      throw new Exception(Labels.getLabel("gedo.error.ABM.leyendaYDepuracion.fechaEnvioErronea"));
    }
    if (dateDepuracionCronAvisoMail.getTime() > dateDepuracionCronLimpieza.getTime()) {
      throw new Exception(
          Labels.getLabel("gedo.error.ABM.leyendaYDepuracion.fechaLimpiezaMayorEnvio"));
    }
  }

  /**
   * Permite limpiar los textbox del abm.
   */
  private void limpiarTextBoxLeyenda() {
    datoLeyenda.setValue(" ");
  }

  /**
   * Permite obtener el usuario actual de la sesion.
   */
  private void obtenerUsuarioActual() {
    usuarioActual = Executions.getCurrent().getUserPrincipal().getName();
  }

  /**
   * Permite notificar al usuario que se realizó la actualización correctamente.
   * 
   * @param res
   * @param nombreProperty
   * @throws InterruptedException
   */
  private void notificarActualizacion(boolean res, String nombreProperty)
      throws InterruptedException {
    if (res) {
      Messagebox.show(Labels.getLabel("gedo.notificar.ABM.leyendaYDepuracion") + nombreProperty,
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  /**
   * Permite obtener el parametro dia, realizando la diferencia entre el proceso
   * de limpieza y el aviso mail.
   * 
   * @param datoDepuracionCronLimpieza
   * @param datoDepuracionCronAvisoMail
   * @return dia
   */
  private String obtenerDiferenciaDias(Date dateDepuracionCronLimpieza,
      Date dateDepuracionCronAvisoMail) {

    long diff = dateDepuracionCronLimpieza.getTime() - dateDepuracionCronAvisoMail.getTime();
    return String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
  }

  /**
   * Inicializa los textbox del abm, realiza una consulta a la tabla
   * property_configuration para obtener los valores actuales de las
   * propiedades.
   */
  private void inicializarTextBox() {
    leyenda = propertyConfiguracionesService.obtenerValorProperty(LEYENDA_CONMEMORATIVA);
    datoLeyenda.setValue(leyenda);

  }

  /**
   * Clase privada que permite crear una propia constraint para validar.
   */
  private class ValidacionDepuracionConstraint implements Constraint {
    @Override
    public void validate(Component comp, Object value) {
      if (value == null) {
        creardepuracion.setDisabled(true);
        throw new WrongValueException(comp,
            Labels.getLabel("gedo.crearTipoDocComposer.exception.vacioNoPermitido"));
      }
      creardepuracion.setDisabled(false);
    }
  }

}
