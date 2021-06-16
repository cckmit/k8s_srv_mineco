package com.egoveris.edt.web.admin.pl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;

import com.egoveris.edt.base.generico.BandBoxUsuarioComposer;
import com.egoveris.edt.base.model.eu.PeriodoLicenciaDTO;
import com.egoveris.edt.base.service.IPeriodoLicenciaService;
import com.egoveris.edt.base.util.Utilidades;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class PeriodoLicenciaComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -3511772223690600417L;

  protected AnnotateDataBinder binder;
  private IPeriodoLicenciaService periodoLicenciaService;
  private Datebox dbx_vigenciaDesde;
  private Datebox dbx_vigenciaHasta;
  private PeriodoLicenciaDTO periodoLicencia;
  private Button btn_guardar;
  private Button btn_cancelar;  
	private Include inclBandboxUsuario;
  
  private UsuarioReducido usuarioSeleccionado;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    periodoLicenciaService = (IPeriodoLicenciaService) SpringUtil
        .getBean("periodoLicenciaService");
    periodoLicenciaService.terminarLicenciasPasadas();
    periodoLicencia = periodoLicenciaService.traerPeriodoLicenciaPorUserName(getUsername());
    configurarPantalla(comp);
    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
  }

  private void configurarPantalla(Component component) {
		configurarBandboxUsuario(component, false);

  	
  	
    if (periodoLicencia != null) {
      cargarDatos();
      btn_cancelar.setVisible(true);
      btn_guardar.setVisible(false);
      habilitarComponentes(false);
    } else {
      periodoLicencia = new PeriodoLicenciaDTO();
      btn_guardar.setVisible(true);
      btn_cancelar.setVisible(false);
    }
  }
  
	private void configurarBandboxUsuario(Component component, boolean deshabilitado) {
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.DISABLED_BANDBOX, deshabilitado);
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.COMPONENTE_PADRE, component);
    inclBandboxUsuario.setSrc(BandBoxUsuarioComposer.SRC);
    
		component.addEventListener(BandBoxUsuarioComposer.ON_SELECT_USUARIO,
				new PeriodoLicenciaListener());
    
	}

  private void habilitarComponentes(boolean habilitar) {
    dbx_vigenciaDesde.setDisabled(!habilitar);
    dbx_vigenciaHasta.setDisabled(!habilitar);
    disabledBandbox(!habilitar);
  }

  private void resetearCompoentes() {
    dbx_vigenciaDesde.setText(StringUtils.EMPTY);
    dbx_vigenciaHasta.setText(StringUtils.EMPTY);
    cleanBandboxUsuario();
    usuarioSeleccionado = null;
  }

  private void cargarDatos() {
    if (periodoLicencia != null) {
      dbx_vigenciaDesde.setValue(periodoLicencia.getFechaHoraDesde());
      dbx_vigenciaHasta.setValue(periodoLicencia.getFechaHoraHasta());
      this.cargarUsuarioBandbox(periodoLicencia.getApoderado());
    }
  }

  private void prepararPeriodoLicencia() {
    if (periodoLicencia != null) {
      periodoLicencia.setFechaHoraDesde(dbx_vigenciaDesde.getValue());
      
      if (dbx_vigenciaHasta.getValue() != null) {
      	periodoLicencia.setFechaHoraHasta(Utilidades.getEndOfDay(dbx_vigenciaHasta.getValue()));
      }
      
      periodoLicencia.setApoderado(usuarioSeleccionado.getUsername());
      periodoLicencia.setCondicionPeriodo(PeriodoLicenciaDTO.PENDIENTE);
      periodoLicencia.setUsuario(getUsername());
    }
  }

  private boolean esFechaActual(Date fechaIngresada) {
    Date fechaActual = new Date();
    Long diasEntre = Utilidades.diasEntreFechas(fechaActual, fechaIngresada);
    Long ceroDias = (long) 0;
    
    if (diasEntre.equals(ceroDias)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean validarCampos() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date fechaActual;
    
		try {
			fechaActual = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			fechaActual = new Date();
		}
    
    if (dbx_vigenciaDesde.getValue() == null) {
      throw new WrongValueException(this.dbx_vigenciaDesde,
          Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.validacion.fecha"));
    }
    if (dbx_vigenciaHasta.getValue() == null) {
      throw new WrongValueException(this.dbx_vigenciaHasta,
          Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.validacion.fecha"));
    }

    // Se quita validacion de 15 minutos
    /*
    if (esFechaActual(dbx_vigenciaDesde.getValue())
        && (Utilidades.diferenciaEnMinutos(new Date(), dbx_vigenciaDesde.getValue()) < 15)) {
      throw new WrongValueException(dbx_vigenciaDesde, Labels.getLabel(
          "eu.datosPersonales.periodoLicencia.tab.validacion.fecha.vigenciaDesde.quinceMinutosSuperiorALaActual"));
    } else {
    */
    if (dbx_vigenciaDesde.getValue().before(fechaActual)) {
      throw new WrongValueException(this.dbx_vigenciaDesde, Labels
          .getLabel("eu.datosPersonales.periodoLicencia.tab.validacion.fecha.vigenciaDesde"));
    }
    //}
      
    if (dbx_vigenciaHasta.getValue().before(dbx_vigenciaDesde.getValue())) {
      throw new WrongValueException(this.dbx_vigenciaHasta, Labels.getLabel(
          "eu.datosPersonales.periodoLicencia.tab.validacion.fecha.vigenciaDesdeMayorAVigenciaHasta"));
    }

    if (usuarioSeleccionado == null) {
    	this.mensajeValidadorBandboxUsuario(Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.validacion.apoderado"));
    	return false;
    }
    
    String userLog = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesSesion.SESSION_USERNAME);
    
    if (usuarioSeleccionado.getUsername().equals(userLog)) {
    	this.mensajeValidadorBandboxUsuario(Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.validacion.mismo.apoderado"));
    }

    return true;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btn_guardar() throws InterruptedException {
    
    if (validarCampos()) {
    	prepararPeriodoLicencia();
      periodoLicenciaService.save(periodoLicencia);
      habilitarComponentes(false);
      btn_guardar.setVisible(false);
      btn_cancelar.setVisible(true);
      Messagebox.show(Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.altaExitosa"),
          Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.informacion"), Messagebox.OK,
          Messagebox.INFORMATION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(final Event evt) throws InterruptedException {
							if ("onOK".equals(evt.getName())) {
					      reloadPagina();
							}
						}
					});
    

    
    }
    
  }

  public void onClick$btn_cancelar() {
		Messagebox.show(Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.cancelar.message"), 
				Labels.getLabel("eu.datosPersonales.periodoLicencia.tab.cancelar.title"),
		    Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
  		
      @Override
      public void onEvent(Event event) throws Exception {
        if (event.getName().equals(Messagebox.ON_YES)) {
        	periodoLicenciaService.cancelarLicencia(periodoLicencia);
          resetearCompoentes();
          habilitarComponentes(true);
          btn_guardar.setVisible(true);
          btn_cancelar.setVisible(false);
          binder.loadAll();
        }
      }
    });
  }

  public Datebox getDbx_vigenciaDesde() {
    return dbx_vigenciaDesde;
  }

  public void setDbx_vigenciaDesde(Datebox dbx_vigenciaDesde) {
    this.dbx_vigenciaDesde = dbx_vigenciaDesde;
  }

  public Datebox getDbx_vigenciaHasta() {
    return dbx_vigenciaHasta;
  }

  public void setDbx_vigenciaHasta(Datebox dbx_vigenciaHasta) {
    this.dbx_vigenciaHasta = dbx_vigenciaHasta;
  }

  public UsuarioReducido getUsuarioSeleccionado() {
    return usuarioSeleccionado;
  }

  public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
    this.usuarioSeleccionado = usuarioSeleccionado;
  }

	private void disabledBandbox(Boolean disabled) {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_DISABLED, inclBandboxUsuario.getChildren().get(0), disabled);
	}
	
	private void cleanBandboxUsuario() {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_CLEAN, inclBandboxUsuario.getChildren().get(0), null);
	}
	
	private void mensajeValidadorBandboxUsuario(String mensaje) {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_VALIDAR, inclBandboxUsuario.getChildren().get(0), mensaje);
	}
	
	private void cargarUsuarioBandbox(String usuario) {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_CARGAR_DATOS, inclBandboxUsuario.getChildren().get(0), usuario);
	}

  
  public class PeriodoLicenciaListener implements EventListener<Event>{

		@Override
		public void onEvent(Event event) throws Exception {
			if(event.getName().equals(BandBoxUsuarioComposer.ON_SELECT_USUARIO)) {
				if(event.getData()!=null) {
					setUsuarioSeleccionado((UsuarioReducido) event.getData());					
				}else {
					setUsuarioSeleccionado(null);
				}
			}
		}
  	
  }

}
