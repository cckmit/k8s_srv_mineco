package com.egoveris.edt.web.pl;

import com.egoveris.edt.base.exception.NuevoFeriadoException;
import com.egoveris.edt.base.model.eu.feriado.FeriadoDTO;
import com.egoveris.edt.base.service.feriado.FeriadoService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * 
 * @author lfishkel
 *
 */
public class NuevoFeriadosComposer extends GenericForwardComposer {

	
  private static Logger logger = LoggerFactory.getLogger(NuevoFeriadosComposer.class);
  private static final long serialVersionUID = 2052197627046717501L;
  private Textbox motivoTextbox;
  private Datebox fechaDatebox;
  private List<FeriadoDTO> feriados;
  private FeriadoDTO feriado;
  @Autowired
  private FeriadoService feriadoService;

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    this.feriadoService = (FeriadoService) SpringUtil.getBean("feriadoService");
    this.feriados = (List<FeriadoDTO>) Executions.getCurrent().getArg().get("feriados");
    this.feriado = (FeriadoDTO) Executions.getCurrent().getArg().get("feriado");
    if (this.feriado != null) {
      ((Window) this.self)
          .setTitle(Labels.getLabel("eu.configuracionFeriados.crearFeriado.modificar"));
      this.motivoTextbox.setValue(this.feriado.getMotivo());
      this.fechaDatebox.setValue(this.feriado.getFecha());
      this.feriados.remove(this.feriado);
    }
  }

  public void onClick$guardar() throws InterruptedException {
    try {
      validarDatos();
      String usuario = (String) Executions.getCurrent().getSession()
          .getAttribute(ConstantesSesion.SESSION_USERNAME);
      this.feriadoService.guardarOModificarFeriado(this.feriado, usuario);
      this.feriados.add(this.feriado);
      this.closeAndNotify();
    } catch (NuevoFeriadoException e) {
      logger.error(Labels.getLabel("eu.abm.configuracion.feriados.error.validacion"), e);
      Messagebox.show(e.getMessage().substring(1, e.getMessage().length()), Labels.getLabel("eu.adminSade.estructura.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Cierra la ventana y notifica a la ventana padre para el refreco de la lista
   */
  private void closeAndNotify() {
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("feriados", this.feriados);
    Events.sendEvent(Events.ON_NOTIFY, this.self.getParent(), hm);
    this.self.detach();

  }

  private void validarDatos() throws NuevoFeriadoException {
		if (StringUtils.isEmpty(this.motivoTextbox.getValue())) {
			throw new WrongValueException(this.motivoTextbox,
					Labels.getLabel("eu.abm.configuracion.feriados.error.IngresarMotivo"));
			// throw new
		} else if (this.fechaDatebox.getValue() == null) {
			throw new WrongValueException(this.fechaDatebox,
					Labels.getLabel("eu.abm.configuracion.feriados.error.IngresarFecha"));
			// throw new
		} else if (this.motivoTextbox.getValue().length() > 50) {
			throw new WrongValueException(this.motivoTextbox,
					Labels.getLabel("eu.abm.configuracion.feriados.error.TamanioMotivo"));
		} else {
			existeFeriado();
		}
	}
 

  private void existeFeriado() throws NuevoFeriadoException {
    this.armarFeriado();
    if (!CollectionUtils.isEmpty(this.feriados)) {
      for (FeriadoDTO feriado : feriados) {
    	Labels.reset();
        switch (this.feriado.compareTo(feriado)) {
        case 1:
          throw new NuevoFeriadoException(  
              Labels.getLabel("eu.abm.configuracion.feriados.error.FechaExistente"));
        case 2:
          throw new NuevoFeriadoException(
              Labels.getLabel("eu.abm.configuracion.feriados.error.MotivoExistente"));
        case 3:
          throw new NuevoFeriadoException(
              Labels.getLabel("eu.abm.configuracion.feriados.error.feriadoExistente"));
        }
      }
    }
  }

  private void armarFeriado() {
    if (this.feriado == null) {
      this.feriado = new FeriadoDTO();
    }
    this.feriado.setFecha(this.fechaDatebox.getValue());
    this.feriado.setMotivo(this.motivoTextbox.getValue());

  }

  public void onClick$cancelar() throws InterruptedException {

    if (validaCambios()) {
      Messagebox.show(
    		  Labels.getLabel("eu.nuevoFeriadoComposer.msgBox.datosSePerderan"),
          Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"), Messagebox.YES | Messagebox.NO,
          Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener<Event>() {

            @Override
            public void onEvent(Event evt) throws InterruptedException {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                cerrar();
                break;
              case Messagebox.NO:
                break;
              }
            }
          });
    } else {
      cerrar();
    }
  }

  private boolean validaCambios() {
    if (this.fechaDatebox != null && this.feriado != null) {
      Date fecha = this.fechaDatebox.getValue();
      return fecha != this.feriado.getFecha()
          || !this.motivoTextbox.getValue().equals(this.feriado.getMotivo());
    }
    return false;
  }

  private void cerrar() {
    if (this.feriado != null) {
      this.feriados.add(this.feriado);
    }
    this.self.detach();
  }

}
