package com.egoveris.te.base.tarjetausuario;

import com.egoveris.te.base.model.TarjetaDatosUsuarioBean;
import com.egoveris.te.base.service.tarjetausuario.TarjetaDatosUsuarioService;
import com.egoveris.te.base.util.ConstantesServicios;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;

@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TarjetaDatosUsuarioComposer extends GenericForwardComposer {

	private TarjetaDatosUsuarioBean tarjetaDatosUsuarioBean;

	@WireVariable(ConstantesServicios.TARJETA_USUARIO_SERVICE)
	private TarjetaDatosUsuarioService tarjetaDatosUsuarioService;

	private Label apellidoNombre;
	private Label user;
	private Label ocupacion;
	private Label codigoReparticion;
	private Label descripcionReparticion;
	private Label codigoSector;
	private Label descripcionSector;

	private Grid tarjetaDatosUsuarioPopupDetalle;
	private Label tarjetaDatosUsuarioPopupSinDetalle;

	// TODO MANDAR A i3-label:
	private static final String MSG_SIN_DETALLE_DEFECTO = "Usuario no encontrado o no tiene detalles.";

	public void setTarjetaDatosUsuarioService(final TarjetaDatosUsuarioService tarjetaDatosUsuarioService) {
		this.tarjetaDatosUsuarioService = tarjetaDatosUsuarioService;
	}

	public TarjetaDatosUsuarioService getTarjetaDatosUsuarioService() {
		return tarjetaDatosUsuarioService;
	}

	public void setTarjetaDatosUsuarioBean(final TarjetaDatosUsuarioBean tarjetaDatosUsuarioBean) {
		this.tarjetaDatosUsuarioBean = tarjetaDatosUsuarioBean;
	}

	public TarjetaDatosUsuarioBean getTarjetaDatosUsuarioBean() {
		return tarjetaDatosUsuarioBean;
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		String username = null;
		
		if (!arg.isEmpty()) {
			username = (String) arg.get(TarjetaDatosUsuario.ATTR_USUARIO);
		}
		
		this.cargarDatosPopup(username);
	}

	private void cargarDatosPopup(final String username) {

		this.tarjetaDatosUsuarioBean = this.tarjetaDatosUsuarioService.getTarjetaDatosUsuarioBean(username);

		if (tarjetaDatosUsuarioBean != null) {
			this.apellidoNombre.setValue(this.tarjetaDatosUsuarioBean.getApellidoNombre());
			this.user.setValue(this.tarjetaDatosUsuarioBean.getUser());
			this.ocupacion.setValue(this.tarjetaDatosUsuarioBean.getOcupacion());
			this.codigoReparticion.setValue(this.tarjetaDatosUsuarioBean.getCodigoReparticion());
			this.descripcionReparticion.setValue(this.tarjetaDatosUsuarioBean.getDescripcionReparticion());
			this.codigoSector.setValue(this.tarjetaDatosUsuarioBean.getCodigoSector());
			this.descripcionSector.setValue(this.tarjetaDatosUsuarioBean.getDescripcionSector());
			// this.mail.setValue(this.tarjetaDatosUsuarioBean.getMail());

			tarjetaDatosUsuarioPopupDetalle.setVisible(true);
			tarjetaDatosUsuarioPopupSinDetalle.setVisible(false);
		} else {
			tarjetaDatosUsuarioPopupDetalle.setVisible(false);
			final String mensajeSinDetalle = this.tarjetaDatosUsuarioService.getMensajeSinDetalle();
			tarjetaDatosUsuarioPopupSinDetalle
					.setValue(mensajeSinDetalle != null ? mensajeSinDetalle : MSG_SIN_DETALLE_DEFECTO);
			tarjetaDatosUsuarioPopupSinDetalle.setVisible(true);
		}
	}
}
