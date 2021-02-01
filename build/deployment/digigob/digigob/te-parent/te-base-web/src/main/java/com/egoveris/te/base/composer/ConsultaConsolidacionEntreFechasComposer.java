package com.egoveris.te.base.composer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.dashboard.web.util.DashboardConstants;
import com.egoveris.vucfront.ws.model.ConsolidacionDTO;
import com.egoveris.vucfront.ws.service.ExternalConsolidacionService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaConsolidacionEntreFechasComposer extends EEGenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4018702140955440823L;

	@WireVariable("externalConsolidacionService")
	private ExternalConsolidacionService consolidacionService;
	
	private Datebox fechaDesde;
	
	private Datebox fechaHasta;
	
	private Textbox organismoUsuario;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaConsolidacionEntreFechasComposer.class);
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		if (Executions.getCurrent().getDesktop().getSession().hasAttribute(DashboardConstants.SESSION_USER_REPARTICION)) {
			String organismo = (String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(DashboardConstants.SESSION_USER_REPARTICION);
			
			if(consolidacionService!=null) {
				System.out.println("El servicio esta cargado por inyeccion");
			}else {
				this.consolidacionService = (ExternalConsolidacionService) SpringUtil.getBean("externalConsolidacionService");
				if(this.consolidacionService!=null) {
					System.out.println("El servicio se pudo cargar por contexto");
				}
			}
			
			this.organismoUsuario.setValue(organismo);
		}
	}
	
	public void onClick$buscar() {
		validarCampos();
		
		try {
			List<ConsolidacionDTO> resultados =  this.consolidacionService
					.consultarConsolidacionEntreFecha(getStartOfDay(fechaDesde.getValue()),
							getEndOfDay(fechaHasta.getValue()),
							organismoUsuario.getValue());
			
			Events.sendEvent(Events.ON_NOTIFY, this.self.getParent(), resultados);
			
		} catch (Exception e) {
			LOGGER.error("Error al consultar las consolidaciones: {}", e.getMessage(),e);
			Messagebox.show(Labels.getLabel("ee.consulta.consolidacion.fechas.error.mensaje"), 
					Labels.getLabel("ee.consulta.consolidacion.fechas.error.titulo"),
					Messagebox.OK, Messagebox.ERROR);
			
		}
		this.onClick$cerrar();
				
	}
	
	/**
	 * seteo la fecha con la hora del inicial del día dd/mm/yyyy (00:00:00 hs)
	 * @param date
	 * @return fecha
	 * @author carmarin
	 */
	private  Date getStartOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
	/**
	 * seteo la fecha con la hora del final del día dd/mm/yyyy (23:59:59 hs)
	 * @param date
	 * @return fecha
	 * @author carmarin
	 */
	private  Date getEndOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}
	
	public void onClick$cerrar() {
		((Window) this.self).onClose();
	}

	private void validarCampos() {
		if(fechaDesde.getValue()==null) {
			throw new WrongValueException(this.fechaDesde, 
					Labels.getLabel("ee.consulta.consolidacion.fechas.validar.desde.empty"));
		}
		if(fechaHasta.getValue()==null) {
			throw new WrongValueException(this.fechaHasta, 
					Labels.getLabel("ee.consulta.consolidacion.fechas.validar.hasta.empty"));
		}
		
		if(fechaHasta.getValue().before(fechaDesde.getValue())) {
			throw new WrongValueException(this.fechaHasta, 
					Labels.getLabel("ee.consulta.consolidacion.fechas.validar.hasta.menor"));
			
		}
		
		if(fechaDesde.getValue().after(fechaHasta.getValue())) {
			throw new WrongValueException(this.fechaHasta, 
					Labels.getLabel("ee.consulta.consolidacion.fechas.validar.desde.mayor"));
			
		}
	}

	public ExternalConsolidacionService getConsolidacionService() {
		return consolidacionService;
	}

	public void setConsolidacionService(ExternalConsolidacionService consolidacionService) {
		this.consolidacionService = consolidacionService;
	}
}
