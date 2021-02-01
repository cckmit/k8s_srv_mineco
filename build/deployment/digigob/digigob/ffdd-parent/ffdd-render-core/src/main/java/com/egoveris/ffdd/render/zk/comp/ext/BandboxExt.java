package com.egoveris.ffdd.render.zk.comp.ext;

import com.egoveris.ffdd.model.model.ComplexData;
import com.egoveris.ffdd.render.model.SelectableComponent;

import java.util.List;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;


public class BandboxExt extends Bandbox implements ConstrInputComponent, SelectableComponent {

	private static final long serialVersionUID = 3528108295042269215L;

	private List<ComplexData> listaDataCompleta;
	private String dataTypeName;
	private String serviceEndpoint;
	private Integer idComponentForm;
	private String tipoComponente;
	private MultiConstrData multiConstrData;
	private Listbox busquedaListbox;
	
	@Override
	public Integer getIdComponentForm() {
		return idComponentForm;
	}

	@Override
	public void setIdComponentForm(Integer idComponentForm) {
		this.idComponentForm = idComponentForm;
	}

	public List<ComplexData> getListaDataCompleta() {
		return listaDataCompleta;
	}

	public void setListaDataCompleta(List<ComplexData> listaDataCompleta) {
		this.listaDataCompleta = listaDataCompleta;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public String getServiceEndpoint() {
		return serviceEndpoint;
	}

	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}
	
	@Override
	public void setValueDirectly(Object o) {
		_value = o;
	}

	public String getTipoComponente() {
		return tipoComponente;
	}

	public void setTipoComponente(String tipoComponente) {
		this.tipoComponente = tipoComponente;
	}

	@Override
	public MultiConstrData getMultiConstrData() {
		return multiConstrData;
		
	}

	@Override
	public void setMultiConstrData(MultiConstrData multiConstrStruct) {
		this.multiConstrData = multiConstrStruct;
	}

	public void setBusquedaListbox(Listbox busquedaListbox) {
		this.busquedaListbox = busquedaListbox;
	}
	
	public Listbox getBusquedaListbox() {
		return this.busquedaListbox;
	}

	@Override
	public boolean addEventListener(String evtnm, EventListener listener) {
		return super.addEventListener(evtnm, listener);
	}
}