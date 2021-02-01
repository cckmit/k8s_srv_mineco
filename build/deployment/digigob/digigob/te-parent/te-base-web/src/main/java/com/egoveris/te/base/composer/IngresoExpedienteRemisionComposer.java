package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TramitacionHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;





/**
 * @author joflores
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class IngresoExpedienteRemisionComposer extends GenericForwardComposer {

	
	private static final long serialVersionUID = -8905719442310346731L;

	@Autowired
	private Combobox tipoActuacion;
	@Autowired
	private Intbox anioSADE;
	@Autowired
	private Intbox numeroSADE;
	@Autowired
	private Textbox reparticionActuacion;
	@Autowired
	private Bandbox reparticionBusquedaSADE;
	@Autowired
	private Listbox consultaExpedienteList;
	@Autowired
	private Window popUpWindow;
	
	private List<String> actuaciones;
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		loadComboActuaciones(); 
	}
	

	private void validarDatos() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);
		int anioActual = Integer.parseInt(anioFormateado);
		Integer anioValido = new Integer(anioActual);
		
		if (StringUtils.isEmpty(anioSADE.getText())) {
			throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.historialpase.faltaanio"));
		}
		if (this.anioSADE.getValue() > anioValido) {
			throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.historialpase.anioInvalido"));
		}
		if (StringUtils.isEmpty(numeroSADE.getText())) {
			throw new WrongValueException(this.numeroSADE, Labels.getLabel("ee.historialpase.faltatnumero"));
		}
		if (StringUtils.isEmpty(reparticionBusquedaSADE.getText())) {
			throw new WrongValueException(this.reparticionBusquedaSADE, Labels.getLabel("ee.historialpase.faltaReparcicion"));
		}		
	}
	
	public void onCancelar(){
		this.self.detach();
	}
	
	
	public void limpiarCampos(){
		anioSADE.setText("");
		numeroSADE.setText("");
		reparticionBusquedaSADE.setText("");
	}
	
	
	// busca el expediente, y retorna el codigo de trata a la ventana padre para que 
	// valide si es una de los tramites validas para descargar los documentos del EE con estado archivo. 
	public void onBuscarExpediente(){
		this.validarDatos();
		String stTipoActuacion = (String) tipoActuacion.getSelectedItem().getValue();
		Integer stAnioSADE = anioSADE.getValue();
		Integer stNumeroSADE = numeroSADE.getValue();
		String stReparticionUsuario = reparticionBusquedaSADE.getText();
		limpiarCampos();
		
		ExpedienteElectronicoDTO expediente = expedienteElectronicoService.obtenerExpedienteElectronico(stTipoActuacion,
				stAnioSADE, stNumeroSADE, stReparticionUsuario);
		
		if (expediente == null){
			this.self.detach();
			alert("El expediente electronico no existe.");
			return;
		}
		Map<String,String>data = new HashMap<String,String>();
		data.put("codigoTrata", expediente.getTrata().getCodigoTrata());
		data.put("numeroSade", expediente.getCodigoCaratula());
		data.put("idExpedienteRemision",expediente.getId().toString());
		// envio el evento al padre (DocumentoConPaseComposer,DocumentoSinPaseComposer o
		// DocumentoFiltroComposer y cierro la ventana
		Events.sendEvent(Events.ON_USER, this.self.getParent(), data);
		this.self.detach();		
	}
	
	
	public static Window openInModal(Component parent) throws InterruptedException {
		Window detExp = (Window) Executions.createComponents("/consultas/IngresoExpedienteRemision.zul", parent,
				new HashMap<String, Object>());
		detExp.setPosition("center");
		detExp.setWidth("60%");
		detExp.doModal();
		return detExp;
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
	
	private void loadComboActuaciones(){
		tipoActuacion.setModel(new ListModelArray(this.getActuaciones()));
		tipoActuacion.setItemRenderer(new ComboitemRenderer() {
			
			@Override
			public void render(Comboitem item, Object data, int arg1) throws Exception {
				String actuacion = (String) data;
				item.setLabel(actuacion);
				item.setValue(actuacion);
				
			      if (actuacion.equals(ConstantesWeb.ACTUACION_EX)) {
			    	  tipoActuacion.setSelectedItem(item);
			      }
			}
		});
    }
}
