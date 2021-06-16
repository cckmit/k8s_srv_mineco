package com.egoveris.te.base.vm;

import java.util.Date;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.helper.OperacionesHelper;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.service.OperacionService;
import com.egoveris.te.base.service.iface.ITipoOperacionService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class OperacionesVM {

	private static final Logger logger = LoggerFactory.getLogger(OperacionesVM.class);

	// Variables
	private List<TipoOperacionDTO> listTipoOperacion;
	private TipoOperacionDTO tipoOperacion;
	private List<OperacionDTO> operaciones;

	// Servicios
	@WireVariable(ConstantesServicios.TIPO_OPERACION_SERVICE)
	private ITipoOperacionService tipoOperacionService;
	
	@WireVariable(ConstantesServicios.OPERACION_SERVICE)
	private OperacionService operacionService;
	
	@WireVariable(ConstantesServicios.ORGANISMO_SERVICE)
	private IReparticionEDTService organismoService;

	@Wire("#misOperaciones")
	Window misOperacionesWindow;
	@Wire
	Popup popupIniciarOp;

	/**
	 * Init. Inicializa la grilla de operaciones y los datos del combo de tipo
	 * operaciones
	 *
	 * @param view
	 */
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) final Component view) {
		Selectors.wireComponents(view, this, false);
		
		try {
			operaciones = operacionService.getOperacionesBySector(OperacionesHelper.getSectorActualUsuario());
			Long longReparticion = OperacionesHelper.getReparticionActualUsuario();
			Integer idReparticion = longReparticion != null ? longReparticion.intValue() : null;
			
			if (idReparticion != null) {
			  listTipoOperacion = tipoOperacionService.getTiposOperacionOrganismoVigentes(idReparticion);
			}
			else {
			  listTipoOperacion = tipoOperacionService.getAllTiposOperacion();
			}
		} catch (ServiceException | NegocioException | SecurityNegocioException e) {
			logger.error("Error en OperacionesVM.init(): ", e);
			Messagebox.show(Labels.getLabel("te.operaciones.error.lista"), "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * Comando que abre el popup flotante de iniciar operacion
	 */
	@Command
	public void openPopup() {
		popupIniciarOp.open(misOperacionesWindow, "center");
	}

	/**
	 * Comando que se ejecuta al seleccionar un tipo de operacion y pulsar
	 * aceptar. Da de alta una nueva operacion en estado borrador y redirige la
	 * pantalla hacia ella.
	 */
	@Command
	@NotifyChange("operaciones")
	public void aceptar() {
		try {
			if (tipoOperacion != null) {
				OperacionDTO operacion = new OperacionDTO();
				operacion.setFechaInicio(new Date());
				operacion.setTipoOperacionOb(tipoOperacion);
				operacion.setEstadoBloq("");
				operacion.setEstadoOperacion("");
				operacion.setIdSectorInterno(OperacionesHelper.getSectorActualUsuario());
				operacion.setVersionProcedure(operacionService.
						getVersionProcedureProject(tipoOperacion.getWorkflow()));
				// Inserts user and repartition (organism)
				if (Executions.getCurrent().getSession().hasAttribute(ConstantesWeb.SESSION_USERNAME)) {
				  operacion.setUsuarioCreador(Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString());
				}
				
				Long idReparticion = OperacionesHelper.getReparticionActualUsuario();
				
				if (idReparticion != null) {
				  operacion.setIdReparticion(idReparticion.intValue());
				}
				
				operacion = operacionService.saveOrUpdate(operacion);
				operaciones = operacionService.getOperaciones();
				popupIniciarOp.detach();
				Messagebox.show(Labels.getLabel("te.operaciones.exito"), Labels.getLabel("ee.general.information"), Messagebox.OK,
						Messagebox.INFORMATION);
				onEjecutarOp(operacion);
			} else {
				Messagebox.show(Labels.getLabel("te.operaciones.seleccione"), Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (final ServiceException | SecurityNegocioException e) {
			logger.error("Error en OperacionesVM.aceptar(): ", e);
			Messagebox.show(Labels.getLabel("te.operaciones.error.iniciar"), "Error", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * Comando que cierra el popup flotante de iniciar operacion
	 */
	@Command
	@NotifyChange("tipoOperacion")
	public void cancelar() {
		tipoOperacion = null;
		popupIniciarOp.close();
	}

	/**
	 * Comando que carga la pantalla de resumen de la operacion
	 *
	 * @param operacion
	 *            Dto con los datos de la operacion
	 */
	@Command
	public void onEjecutarOp(@BindingParam("operacion") final OperacionDTO operacion) {
		OperacionesHelper.redirectResumenOperacion(operacion);
	}

	public List<TipoOperacionDTO> getListTipoOperacion() {
		return listTipoOperacion;
	}

	public void setListTipoOperacion(final List<TipoOperacionDTO> listTipoOperacion) {
		this.listTipoOperacion = listTipoOperacion;
	}

	public ITipoOperacionService getTipoOperacionService() {
		return tipoOperacionService;
	}

	public void setTipoOperacionService(final ITipoOperacionService tipoOperacionService) {
		this.tipoOperacionService = tipoOperacionService;
	}

	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(final TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public List<OperacionDTO> getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(final List<OperacionDTO> operaciones) {
		this.operaciones = operaciones;
	}


}
