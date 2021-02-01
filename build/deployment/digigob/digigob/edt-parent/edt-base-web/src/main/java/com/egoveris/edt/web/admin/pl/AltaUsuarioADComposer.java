package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.service.usuario.IUsuarioHelper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

public class AltaUsuarioADComposer extends AltaUsuarioComposer {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(AltaUsuarioADComposer.class);
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;
  private Textbox txbx_cuit;
  private AnnotateDataBinder binder;

  private IUsuarioService usuarioService;
  private IUsuarioHelper usuarioHelper;

  private UsuarioReducido superiorSeleccionado;
  private SectorDTO selectedSector;
  private CargoDTO selectedCargo;

  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
    binder = new AnnotateDataBinder(c);
    usuarioHelper = (IUsuarioHelper) SpringUtil.getBean("usuarioHelper");
    Window w = (Window) this.getInc_reparticionSectorSelector()
        .getFellow("win_reparticionSectorSelector");

    if (w != null) {
      Events.sendEvent(Events.ON_USER, w, c);
    }
  }

  public void onFocus$txbx_cuit() {
    txbx_mail.setValue(null);
 //   txbx_nombre.setValue(null);
    this.binder.loadAll();
  }

  /**
   * Validar cuit.
   */
  private void validarCuit() {
    if (StringUtils.isEmpty(txbx_cuit.getValue())) {
      throw new WrongValueException(txbx_cuit,
          Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.noVacio"));
    }
    if (txbx_cuit.getValue().length() < 11) {
      throw new WrongValueException(txbx_cuit,
          Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.digitos"));
    }
    if (!StringUtils.isNumeric(txbx_cuit.getValue())) {
      throw new WrongValueException(txbx_cuit,
          Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.soloNumeros"));
    }
  }

  /**
   * Validar campos CCOO.
   */
  public void validarCamposCCOO() {
    ReparticionDTO r = (ReparticionDTO) Executions.getCurrent().getSession()
        .getAttribute(ConstantesSesion.REPARTICION_SELECCIONADA);
    SectorDTO s = (SectorDTO) Executions.getCurrent().getSession()
        .getAttribute(ConstantesSesion.SECTOR_SELECCIONADO);
    Window w = (Window) this.getInc_reparticionSectorSelector()
        .getFellow("win_reparticionSectorSelector");
      if(w != null){
          Bandbox repaBandbox;
          Bandbox sectorBandbox;
     
            repaBandbox = (Bandbox) w.getFellow("bandBoxReparticion");
            sectorBandbox = (Bandbox) w.getFellow("bandBoxSector");     
            
          if (r == null && repaBandbox != null) {
            throw new WrongValueException(repaBandbox,
                Labels.getLabel("eu.altaUsuarioAdComposer.WrongValueException.selecOrg"));
          }
      
          if (s == null && sectorBandbox != null) {
            throw new WrongValueException(sectorBandbox,
                Labels.getLabel("eu.altaUsuarioAdComposer.WrongValueException.selecSec"));
          }
    }
  }

  /**
   * Validar usuario.
   *
   * @throws InterruptedException the interrupted exception
   */
  private void validarUsuario() throws InterruptedException {
    this.validarCuit();
    String cuit = txbx_cuit.getValue();
    List<Usuario> list = new ArrayList<Usuario>();
    try {
      list = usuarioService.obtenerUsuarios("cuit:" + cuit);
    } catch (Exception e) {
      logger.error("error al obtener usuarios", e);
      throw new InterruptedException();
    }
    if (!list.isEmpty()) {
      Messagebox.show(
          Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.cuitCorrecto") + "\n"
              + Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.cuitRegistradoOtroUsuario"),
          Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
          Messagebox.ERROR);
      txbx_mail.setValue(null);
//      txbx_nombre.setValue(null);
      txbx_cuit.setValue(null);
      return;
    }
  }

  /**
   * On click$btn alta usuario.
   *
   * @throws InterruptedException the interrupted exception
   */
  @Override
	public void onClick$btn_altaUsuario() throws InterruptedException {
		this.sectorSeleccionado = (SectorDTO) getSession().getAttribute(ConstantesSesion.SECTOR_SELECCIONADO);
		validarUsuario();
		validarCamposCCOO();
		if (validarCampos()) {
			try {
				// valido si el sector cuenta con algun usuario, sino pongo el
				// que se quiere dar de alta, con el rol de asignador
				if (!usuarioHelper.tieneUsuarioAsignador(sectorSeleccionado.getId())) {
					if (this.usuario.getPermisos() == null) {
						this.usuario.setPermisos(new ArrayList<String>());
					}
					PermisoDTO permiso = permisoService.obtenerPermisoAsignador();
					if (!usuario.getPermisos().contains(permiso.getPermiso())) {
						usuario.getPermisos().add(permiso.getPermiso());
					}
				}
			} catch (NegocioException e) {
				logger.error("error al obtener el permiso", e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
			} catch (SecurityNegocioException e) {
				logger.error("error al obtener el permiso asignador", e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.altaUsuarioAdComposer.msgbox.noActualizarUsuario"),
						Messagebox.OK, Messagebox.ERROR);
			}

			try {
				guardarUsuario();
			} catch (Exception e) {
				logger.error("error al modificar Datos del Usuario", e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
			}

			try {
				usuarioService.indexarUsuario(usuario.getNombre());
			} catch (Exception e) {
				logger.error("error al indexar datos del usuario", e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

  public UsuarioReducido getSuperiorSeleccionado() {
    return superiorSeleccionado;
  }

  public void setSuperiorSeleccionado(UsuarioReducido superiorSeleccionado) {
    this.superiorSeleccionado = superiorSeleccionado;
  }

  public SectorDTO getSelectedSector() {
    return selectedSector;
  }

  public void setSelectedSector(SectorDTO selectedSector) {
    this.selectedSector = selectedSector;
  }

  public CargoDTO getSelectedCargo() {
    return selectedCargo;
  }

  public void setSelectedCargo(CargoDTO selectedCargo) {
    this.selectedCargo = selectedCargo;
  }

}