package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.SearchResultData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



public class ConsultaPorDomicilioComposer extends ConsultasComposer {

	private final static Logger logger = LoggerFactory.getLogger(ConsultaPorDomicilioComposer.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -544662218812674920L;

	protected AnnotateDataBinder binder;

	private Datebox fechaDesde;

	private Datebox fechaHasta;

	@Autowired
	private Textbox direccion;

	@Autowired
	private Textbox piso;

	@Autowired
	private Textbox departamento;

	@Autowired
	private Textbox codigoPostal;

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		setEstado((String) Executions.getCurrent().getArg().get("tipo"));

	}

	public void onClick$buscar() throws InterruptedException {
		ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
				.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);

		this.checkConstraints();

		// Auditoria de consulta
		grabarAuditoriaDeConsulta(direccion.getValue(), piso.getValue(), departamento.getValue(),
				codigoPostal.getValue());

		List<ExpedienteElectronicoDTO> result = new ArrayList<>();
		try {
			result = expedienteElectronicoService.buscarExpedienteElectronicoPorDireccion(this.fechaDesde.getValue(),
					this.fechaHasta.getValue(), this.direccion.getValue(), this.piso.getValue(),
					this.departamento.getValue(), this.codigoPostal.getValue(), this.getEstado());
		} catch (WrongValueException e) {

			throw new WrongValueException(Labels.getLabel("ee.errorConsultaExpedientes.sinResultados"));
		}

		if (result == null || result.size() == 0) {
			Messagebox.show(Labels.getLabel("ee.consultaExpedientes.sinResultados"),
					Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			SearchResultData data = new SearchResultData();
			data.setResultado(result);
			this.closeAndNotifyAssociatedWindow(data);
		}

	}

	protected void checkConstraints() {

		if (this.fechaDesde.getValue() == null && this.fechaHasta.getValue() != null) {
			throw new WrongValueException("Debe ingresar fecha desde");
		}
		if (this.fechaDesde.getValue() != null && this.fechaHasta.getValue() == null) {
			throw new WrongValueException("Debe ingresar fecha hasta");
		}

		if (fechaDesde.getValue() != null && fechaHasta.getValue() != null) {
			Date desde = fechaDesde.getValue();
			Date hasta = fechaHasta.getValue();
			if (desde.after(hasta)) {
				throw new WrongValueException(fechaDesde, "La fecha de inicio no debe ser mayor que la fecha de fin");
			}
		}

		if ((this.direccion.getValue() == null) || (this.direccion.getValue().equals(""))) {
			this.direccion.focus();
			throw new WrongValueException(this.direccion, Labels.getLabel("ee.nuevoexpediente.faltadomicilio"));
		}
		
		String direccion = this.direccion.getValue();
		this.direccion.setValue(direccion.toUpperCase());
	}

	public void onClick$cerrar() {
		((Window) this.self).onClose();
	}

	public void onClick$blanquearDatos() {
		this.fechaDesde.setText(null);
		this.binder.loadComponent(this.fechaDesde);
		this.fechaHasta.setText(null);
		this.binder.loadComponent(this.fechaHasta);

		this.direccion.setText("");
		this.direccion.setText(null);

		this.binder.loadComponent(this.direccion);

		this.piso.setText("");
		this.piso.setText(null);

		this.binder.loadComponent(this.piso);

		this.departamento.setText("");
		this.departamento.setText(null);

		this.binder.loadComponent(this.departamento);

		this.codigoPostal.setText("");
		this.codigoPostal.setText(null);

		this.binder.loadComponent(this.codigoPostal);
	}

	private void grabarAuditoriaDeConsulta(String domicilio, String piso, String departamento, String codigoPostal) {
		IAuditoriaService auditoriaService = (IAuditoriaService) SpringUtil.getBean(ConstantesServicios.AUDITORIA_SERVICE);
		AuditoriaDeConsultaDTO auditoriaDeConsulta = new AuditoriaDeConsultaDTO();

		auditoriaDeConsulta.setDomicilio(domicilio);
		auditoriaDeConsulta.setPiso(piso);
		auditoriaDeConsulta.setDepartamento(departamento);
		auditoriaDeConsulta.setCodigoPostal(codigoPostal);
		String usuario = (String) Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME);
		auditoriaDeConsulta.setUsuario(usuario);
		auditoriaDeConsulta.setFechaConsulta(new Date());

		auditoriaService.grabarAuditoriaDeConsulta(auditoriaDeConsulta);
	}

	public Textbox getDireccion() {
		return direccion;
	}

	public void setDireccion(Textbox direccion) {
		this.direccion = direccion;
	}

	public void setPiso(Textbox piso) {
		this.piso = piso;
	}

	public Textbox getPiso() {
		return piso;
	}

	public void setCodigoPostal(Textbox codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Textbox getCodigoPostal() {
		return codigoPostal;
	}

	public void setDepartamento(Textbox departamento) {
		this.departamento = departamento;
	}

	public Textbox getDepartamento() {
		return departamento;
	}

	public Datebox getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Datebox fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Datebox getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Datebox fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	@Override
	protected List<ExpedienteElectronicoDTO> buscar(String username, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> camposYvaloresMetadato, Date desde,
			Date hasta, String cuitCuil, String estado) {
		super.buscar();

		ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
				.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);

		this.checkConstraints();

		// Auditoria de consulta
		grabarAuditoriaDeConsulta(direccion.getValue(), piso.getValue(), departamento.getValue(),
				codigoPostal.getValue());

		List<ExpedienteElectronicoDTO> result = new ArrayList<>();
		try {
			result = expedienteElectronicoService.buscarExpedienteElectronicoPorDireccion(this.fechaDesde.getValue(),
					this.fechaHasta.getValue(), this.direccion.getValue(), this.piso.getValue(),
					this.departamento.getValue(), this.codigoPostal.getValue(), this.getEstado());
		} catch (WrongValueException e) {

			throw new WrongValueException(Labels.getLabel("ee.errorConsultaExpedientes.sinResultados"));
		}

		if (result == null || result.size() == 0) {
			Messagebox.show(Labels.getLabel("ee.consultaExpedientes.sinResultados"),
					Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			SearchResultData data = new SearchResultData();
			data.setResultado(result);
			this.closeAndNotifyAssociatedWindow(data);
		}

		return result;
	}
}
