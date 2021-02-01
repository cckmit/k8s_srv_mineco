package com.egoveris.edt.web.pl;

import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;

public class ActualizacionCargosUsuarioComposer extends GenericForwardComposer {

	
  /**
  * 
  */
  private Window actualizacionCargoUsuario;
  private static final long serialVersionUID = 3493562784148270225L;
  public List<CargoDTO> listaCargos;
  private ICargoService cargoService;
  private IDatosUsuarioService datosUsuarioService;
  private Combobox cargosCB;
  private CargoDTO selectedCargo;
  Map<String, Object> params;
  private AnnotateDataBinder binder = new AnnotateDataBinder();

  @Override
  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    cargoService = (ICargoService) SpringUtil.getBean("cargoService");
    datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");
    params = (Map<String, Object>) Executions.getCurrent().getArg();
    selectedCargo = new CargoDTO();
    listaCargos = cargoService.getCargosActivosVigentes();
    cargosCB.setRawValue(listaCargos.get(0).getCargoNombre());
    binder.loadAll();
  }

  public void onClick$guardarBT() throws Exception {
    if (selectedCargo.getId() != null && selectedCargo.getId() != 0) {
      datosUsuarioService.updateCargoUsuario((String) params.get(ConstantesSesion.SESSION_USERNAME),
          selectedCargo);
      actualizacionCargoUsuario.detach();
      Messagebox.show(Labels.getLabel("eu.actualCargoUsuario.msgbox.cargoExito"),
          Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"), Messagebox.OK,
          Messagebox.INFORMATION);
    } else {
      throw new WrongValueException(cargosCB,
          Labels.getLabel("eu.actualCargoUsuario.msgbox.especificarCargo"));
    }
  }

  public List<CargoDTO> getListaCargos() {
    return listaCargos;
  }

  public void setListaCargos(List<CargoDTO> listaCargos) {
    this.listaCargos = listaCargos;
  }

  public CargoDTO getSelectedCargo() {
    return selectedCargo;
  }

  public void setSelectedCargo(CargoDTO selectedCargo) {
    this.selectedCargo = selectedCargo;
  }
}