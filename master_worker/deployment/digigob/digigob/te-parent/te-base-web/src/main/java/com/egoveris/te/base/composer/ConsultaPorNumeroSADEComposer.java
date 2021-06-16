package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteTrack;
import com.egoveris.te.base.service.SolrServiceTrack;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.SearchResultData;
import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.model.exception.ProcesoFallidoException;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaPorNumeroSADEComposer extends EEGenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1569138975299328259L;
	
	
	@Autowired
	private Intbox numeroSADE;
	@Autowired
	private Bandbox reparticionBusquedaUsuario;
	@Autowired
	private Intbox anioSADE;
	@Autowired
	private Combobox expediente;
	@Autowired
	private Combobox reparticionActuacion;
	@WireVariable(ConstantesServicios.CONSTANTESDB)
	private ConstantesDB constantesDB;
	
	private Logger logger = LoggerFactory.getLogger(ConsultaPorNumeroSADEComposer.class);
	
	@Autowired
	private AnnotateDataBinder binder;
	
	private List<String> actuaciones;
	
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		this.binder = new AnnotateDataBinder(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		reparticionActuacion.setValue(constantesDB.getNombreReparticionActuacion());
		loadComboActuaciones();
		this.binder = new AnnotateDataBinder(c);
	}
	
	 private void loadComboActuaciones(){
		 expediente.setModel(new ListModelArray(this.getActuaciones()));
	    	expediente.setItemRenderer(new ComboitemRenderer() {
				
				@Override
				public void render(Comboitem item, Object data,int arg1) throws Exception {
					String actuacion = (String) data;
					item.setLabel(actuacion);
					item.setValue(actuacion);
					
				      if (actuacion.equals(ConstantesWeb.ACTUACION_EX)) {
				    	  expediente.setSelectedItem(item);
				      }
				}
			});
	    }

	public void onClick$buscar() throws InterruptedException {
		ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
		this.checkConstraints();

		// Auditoria de consulta
		grabarAuditoriaDeConsulta(this.expediente.getValue(),
				anioSADE.getValue(), numeroSADE.getValue()
						, this.reparticionActuacion.getValue()
						.trim(), this.reparticionBusquedaUsuario.getValue().trim());

		
		ExpedienteElectronicoDTO result = null;
		try {
			result = (ExpedienteElectronicoDTO) expedienteElectronicoService
					.obtenerExpedienteElectronicoSolr((String)expediente.getSelectedItem().getValue(),
							this.anioSADE.getValue(), this.numeroSADE
									.getValue(),
							this.reparticionBusquedaUsuario.getValue());
		} catch (WrongValueException e) {
			
			throw new WrongValueException(Labels
					.getLabel("ee.errorConsultaExpedientes.sinResultados"));
		}
		
		if (result==null) {
			try{
			
				SolrServiceTrack solrServiceTrack = (SolrServiceTrack) SpringUtil.getBean(ConstantesServicios.SOLR_TRACK_SERVICE);
				ExpedienteTrack resultado = solrServiceTrack.buscarExpedientePapel((String)expediente.getSelectedItem().getValue(), anioSADE.getValue(), numeroSADE.getValue(),
						reparticionActuacion.getValue(), reparticionBusquedaUsuario.getValue());
				Executions.getCurrent().getSession().setAttribute("repActuacionBusqueda", reparticionActuacion.getValue());
				Executions.getCurrent().getSession().setAttribute("repUsuarioBusqueda", reparticionBusquedaUsuario.getValue());
				this.closeAndNotifyAssociatedWindow(resultado);
				
				
			}catch (ProcesoFallidoException e){
				logger.error("No se ha encontrado el expediente: " +  (String)expediente.getSelectedItem().getValue() +" "+anioSADE.getValue() +" "+numeroSADE.getValue()+" "
			+reparticionActuacion.getValue()+" "+reparticionBusquedaUsuario.getValue());
				Messagebox.show(Labels
						.getLabel("ee.general.noHayResultados"), Labels
						.getLabel("ee.general.information"), Messagebox.OK,
						Messagebox.EXCLAMATION);
						return;
			}catch(Exception e){
				logger.error("Error de comunicacion con el servidor Solr de TRACK");
				Messagebox.show(Labels
						.getLabel("ee.general.noHayResultados"), Labels
						.getLabel("ee.general.information"), Messagebox.OK,
						Messagebox.EXCLAMATION);
					    return;
			}
		}else{
			SearchResultData data = new SearchResultData();
			List<ExpedienteElectronicoDTO> expedienteElectronicoList = new ArrayList<>();
			result.setSolicitarArchivo(true);
			expedienteElectronicoList.add(result);
			
			data.setResultado(expedienteElectronicoList);
			this.closeAndNotifyAssociatedWindow(data);			
		}
	
		
	}

	protected void checkConstraints() {
		if (this.anioSADE.getValue() != null) {
			if (this.anioSADE.getValue() <= 1854) {
				throw new WrongValueException(this.anioSADE, "Año incorrecto");
			}
		} else {
			throw new WrongValueException(this.anioSADE, "Ingrese un año");
		}
		if(this.numeroSADE.getValue()==null || this.numeroSADE.getValue()<0){
			throw new WrongValueException(this.numeroSADE, "Ingrese un número");
		}
		if(StringUtils.isEmpty(this.reparticionBusquedaUsuario.getValue())){
			throw new WrongValueException(this.reparticionBusquedaUsuario, "Ingrese un organismo");
		}
	}

	public void onClick$cerrar() {
		((Window) this.self).onClose();
	}
	
	private void grabarAuditoriaDeConsulta(String tipoActuacion,
			Integer anioActuacion, Integer numeroActuacion,
			String reparticionActuacion, String reparticionUsuario) {
		IAuditoriaService auditoriaService = (IAuditoriaService) SpringUtil.getBean(ConstantesServicios.AUDITORIA_SERVICE);
		AuditoriaDeConsultaDTO auditoriaDeConsulta = new AuditoriaDeConsultaDTO();

		auditoriaDeConsulta.setTipoActuacion(tipoActuacion);
		auditoriaDeConsulta.setAnioActuacion(anioActuacion);
		auditoriaDeConsulta.setNumeroActuacion(numeroActuacion);
		auditoriaDeConsulta.setReparticionActuacion(reparticionActuacion);
		auditoriaDeConsulta.setReparticionUsuario(reparticionUsuario);
		String usuario = (String) Executions.getCurrent().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		auditoriaDeConsulta.setUsuario(usuario);
		auditoriaDeConsulta.setFechaConsulta(new Date());

		auditoriaService.grabarAuditoriaDeConsulta(auditoriaDeConsulta);
	}
	
	/**
	 * @return the actuaciones
	 */
	public List<String> getActuaciones() {
		if (actuaciones==null) {
			this.actuaciones = TramitacionHelper.findActuaciones();
		}
		return actuaciones;
	}
	
	
	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}
}
