package com.egoveris.te.base.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.direccion.DataLocalidadDTO;
import com.egoveris.te.base.model.direccion.DataPartidaDTO;
import com.egoveris.te.base.model.direccion.DataProvinciaDTO;
import com.egoveris.te.base.model.direccion.SolicitanteDireccionDTO;
import com.egoveris.te.base.service.direccion.SolicitanteDireccionService;

@SuppressWarnings({ "deprecation", "unchecked" })
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SolicitanteDireccionVM {

	private static Logger logger = LoggerFactory.getLogger(SolicitanteDireccionVM.class);

	@WireVariable("solicitanteDireccionServiceImpl")
	private SolicitanteDireccionService solicitanteDireccionService;

	private DozerBeanMapper mapper = new DozerBeanMapper();

	private List<DataProvinciaDTO> dataProvinciasList;
	private List<DataPartidaDTO> dataPartidasList;
	private List<DataLocalidadDTO> dataLocalidadList;
	private DataProvinciaDTO selectedProvincia;
	private DataPartidaDTO selectedPartida;
	private DataLocalidadDTO selectedLocalidad;
	private String direccion;
	private boolean disabledDireccion = true;
	private boolean readOnly = false;

	@Wire
	private Combobox provinciasCombo;

	@Wire
	private Combobox partidasCombo;

	@Wire
	private Combobox localidadCombo;

	@Autowired
	protected AnnotateDataBinder binder;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		binder = new AnnotateDataBinder(view);
	}

	@Init
	public void init() {
		dataProvinciasList = solicitanteDireccionService.getProvinciasCascade();
		dataPartidasList = new ArrayList<>();
		dataLocalidadList = new ArrayList<>();
	}

	@Command
	@NotifyChange({ "dataPartidasList", "dataLocalidadList", "selectedPartida", "selectedLocalidad",
			"disabledDireccion" })
	public void onChangeProvincia() {
		dataPartidasList.clear();
		dataLocalidadList.clear();
		selectedPartida = null;
		selectedLocalidad = null;
		partidasCombo.setSelectedItem(null);
		partidasCombo.setValue(null);
		localidadCombo.setSelectedItem(null);
		localidadCombo.setValue(null);
		disabledDireccion = true;

		if (selectedProvincia != null && !CollectionUtils.isEmpty(selectedProvincia.getPartidas())) {
			dataPartidasList = ListMapper.mapList(selectedProvincia.getPartidas(), mapper, DataPartidaDTO.class);
		}
	}

	@Command
	@NotifyChange({ "dataLocalidadList", "selectedLocalidad", "disabledDireccion" })
	public void onChangePartida() {
		dataLocalidadList.clear();
		selectedLocalidad = null;
		localidadCombo.setSelectedItem(null);
		localidadCombo.setValue(null);
		disabledDireccion = true;

		if (selectedPartida != null && !CollectionUtils.isEmpty(selectedPartida.getLocalidades())) {
			dataLocalidadList = ListMapper.mapList(selectedPartida.getLocalidades(), mapper, DataLocalidadDTO.class);
		}
	}

	@Command
	@NotifyChange({ "selectedPartida", "disabledDireccion" })
	public void onChangeLocalidad() {
		if (selectedPartida == null && !CollectionUtils.isEmpty(dataPartidasList)) {
			for (DataPartidaDTO dataPartidaDTO : dataPartidasList) {
				if (selectedLocalidad.getPartida().getId().equals(dataPartidaDTO.getId())) {
					selectedPartida = dataPartidaDTO;
					break;
				}
			}
		}
		
		disabledDireccion = false;
	}

	@GlobalCommand
	public void onSaveSolicitanteDireccion(@BindingParam("idSolicitante") Integer idSolicitante) {
		SolicitanteDireccionDTO solicitanteDireccionDTO = new SolicitanteDireccionDTO();
		solicitanteDireccionDTO.setIdSolicitante(idSolicitante);
		solicitanteDireccionDTO.setProvincia(selectedProvincia);
		solicitanteDireccionDTO.setPartida(selectedPartida);
		solicitanteDireccionDTO.setLocalidad(selectedLocalidad);
		solicitanteDireccionDTO.setDireccion(direccion);

		try {
			solicitanteDireccionService.save(solicitanteDireccionDTO);
		} catch (Exception e) {
			logger.error("Error al guardar direccion de solicitante: ", e);
		}
	}

	@GlobalCommand
	@NotifyChange({ "dataPartidasList", "dataLocalidadList", "selectedProvincia", "selectedPartida",
			"selectedLocalidad", "direccion", "disabledDireccion" })
	public void onClearData() {
		dataPartidasList = new ArrayList<>();
		dataLocalidadList = new ArrayList<>();
		selectedProvincia = null;
		selectedPartida = null;
		selectedLocalidad = null;
		direccion = null;
		disabledDireccion = true;
	}

	@GlobalCommand
	@NotifyChange({ "selectedProvincia", "dataPartidasList", "dataLocalidadList", "direccion", "disabledDireccion" })
	public void onLoadExistingData(@BindingParam("idSolicitante") Integer idSolicitante) {
		SolicitanteDireccionDTO solicitanteDireccionDTO = solicitanteDireccionService.load(idSolicitante);

		if (solicitanteDireccionDTO != null) {
			if (solicitanteDireccionDTO.getProvincia() != null && CollectionUtils.isNotEmpty(dataProvinciasList)) {
				for (DataProvinciaDTO dataProvinciaDTO : dataProvinciasList) {
					if (dataProvinciaDTO.getId().equals(solicitanteDireccionDTO.getProvincia().getId())) {
						selectedProvincia = dataProvinciaDTO;

						if (!CollectionUtils.isEmpty(selectedProvincia.getPartidas())) {
							dataPartidasList = ListMapper.mapList(selectedProvincia.getPartidas(), mapper,
									DataPartidaDTO.class);
							binder.loadComponent(partidasCombo);
							loadAndSetValuesCombo(partidasCombo, dataPartidasList,
									solicitanteDireccionDTO.getPartida());
						}

						break;
					}
				}
			}

			if (solicitanteDireccionDTO.getPartida() != null && CollectionUtils.isNotEmpty(dataPartidasList)) {
				for (DataPartidaDTO dataPartidaDTO : dataPartidasList) {
					if (dataPartidaDTO.getId().equals(solicitanteDireccionDTO.getPartida().getId())) {

						if (!CollectionUtils.isEmpty(dataPartidaDTO.getLocalidades())) {
							dataLocalidadList = ListMapper.mapList(dataPartidaDTO.getLocalidades(), mapper,
									DataLocalidadDTO.class);
							binder.loadComponent(localidadCombo);
							loadAndSetValuesCombo(localidadCombo, dataLocalidadList,
									solicitanteDireccionDTO.getLocalidad());
						}
					}
				}
			}

			direccion = solicitanteDireccionDTO.getDireccion();

			if (StringUtils.isNotEmpty(direccion)) {
				disabledDireccion = false;
			}
		}
	}

	@GlobalCommand
	@NotifyChange("readOnly")
	public void onReadOnly(@BindingParam("readOnly") Boolean readOnly) {
		this.setReadOnly(readOnly);
	}

	private void loadAndSetValuesCombo(Combobox combobox, Object dataList, Object selectedValue) {
		List<Object> values = (List<Object>) dataList;

		if (values != null && CollectionUtils.isNotEmpty(values)) {
			for (Object object : values) {
				if (object instanceof DataPartidaDTO) {
					DataPartidaDTO dataPartidaDTO = (DataPartidaDTO) object;
					combobox.getItems().add(new Comboitem(dataPartidaDTO.getPartidaName()));

					if (selectedValue != null && dataPartidaDTO.getId().equals(((DataPartidaDTO) selectedValue).getId())) {
						combobox.setValue(dataPartidaDTO.getPartidaName());
					}
				} else if (object instanceof DataLocalidadDTO) {
					DataLocalidadDTO dataLocalidadDTO = (DataLocalidadDTO) object;
					combobox.getItems().add(new Comboitem(dataLocalidadDTO.getLocalidadName()));

					if (selectedValue != null && dataLocalidadDTO.getId().equals(((DataLocalidadDTO) selectedValue).getId())) {
						combobox.setValue(dataLocalidadDTO.getLocalidadName());
					}
				}
			}
		}
	}

	public static void saveDireccion(Number idSolicitante) {
		Map<String, Object> mapParams = new HashMap<>();
		mapParams.put("idSolicitante", idSolicitante);
		BindUtils.postGlobalCommand(null, null, "onSaveSolicitanteDireccion", mapParams);
	}

	public static void precargaDireccion(Number idSolicitante) {
		BindUtils.postGlobalCommand(null, null, "onClearData", null);
		
		Map<String, Object> mapParams = new HashMap<>();
		mapParams.put("idSolicitante", idSolicitante);
		BindUtils.postGlobalCommand(null, null, "onLoadExistingData", mapParams);
	}

	public static void setReadOnlyDireccion(Boolean readOnly) {
		Map<String, Object> mapParams = new HashMap<>();
		mapParams.put("readOnly", readOnly);
		BindUtils.postGlobalCommand(null, null, "onReadOnly", mapParams);
	}

	// Getters - setters

	public List<DataProvinciaDTO> getDataProvinciasList() {
		return dataProvinciasList;
	}

	public void setDataProvinciasList(List<DataProvinciaDTO> dataProvinciasList) {
		this.dataProvinciasList = dataProvinciasList;
	}

	public List<DataPartidaDTO> getDataPartidasList() {
		return dataPartidasList;
	}

	public void setDataPartidasList(List<DataPartidaDTO> dataPartidasList) {
		this.dataPartidasList = dataPartidasList;
	}

	public List<DataLocalidadDTO> getDataLocalidadList() {
		return dataLocalidadList;
	}

	public void setDataLocalidadList(List<DataLocalidadDTO> dataLocalidadList) {
		this.dataLocalidadList = dataLocalidadList;
	}

	public DataProvinciaDTO getSelectedProvincia() {
		return selectedProvincia;
	}

	public void setSelectedProvincia(DataProvinciaDTO selectedProvincia) {
		this.selectedProvincia = selectedProvincia;
	}

	public DataPartidaDTO getSelectedPartida() {
		return selectedPartida;
	}

	public void setSelectedPartida(DataPartidaDTO selectedPartida) {
		this.selectedPartida = selectedPartida;
	}

	public DataLocalidadDTO getSelectedLocalidad() {
		return selectedLocalidad;
	}

	public void setSelectedLocalidad(DataLocalidadDTO selectedLocalidad) {
		this.selectedLocalidad = selectedLocalidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean isDisabledDireccion() {
		return disabledDireccion;
	}

	public void setDisabledDireccion(boolean disabledDireccion) {
		this.disabledDireccion = disabledDireccion;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

}
