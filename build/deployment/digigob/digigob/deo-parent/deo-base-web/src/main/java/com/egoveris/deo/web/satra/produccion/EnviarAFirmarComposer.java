package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Window;


public class EnviarAFirmarComposer extends ValidarApoderamientoComposer {

	private static final long serialVersionUID = -3460675364277376046L;

	protected Window enviarAFirmar;
	protected Combobox usuarioFirmante;
	private Usuario usuarioDestinatarioLicencia;
	public static final String REPARTICION_PERMISO_FIRMAR = "permisoFirmar";

	public void doAfterCompose(Component component) throws Exception {

		super.doAfterCompose(component);
		this.usuarioDestinatarioLicencia = (Usuario) Executions.getCurrent().getArg().get(Constantes.VAR_USUARIO_DESTINATARIO_LICENCIA);
		this.usuarioFirmante.setModel(ListModels.toListSubModel(
				new ListModelList(this.getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));
		if(this.usuarioDestinatarioLicencia != null){
			usuarioFirmante.setValue(this.usuarioDestinatarioLicencia.toString());
		}
	}

	public void onClick$aceptar() throws InterruptedException {
		Map<String, Object> maps = new HashMap<>();
		maps.put("origen", Constantes.EVENTO_ENVIAR_A_FIRMAR);
		if (this.usuarioDestinatarioLicencia != null) {
			if (this.usuarioFirmante.getValue().equals(
					this.usuarioDestinatarioLicencia.toString())) {
				Comboitem comboItem = new Comboitem();
				comboItem.setValue(usuarioDestinatarioLicencia);
				maps.put("firmante", comboItem);
			} else {
				maps.put("mostrarMensaje", true);
				armarMapa(maps);
			}
		} else {
			armarMapa(maps);
		}

		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),
				maps));
	}

	private void armarMapa(Map<String, Object> maps){
		if(usuarioFirmante.getSelectedItem() == null){
			this.usuarioFirmante.setValue(null);
			throw new WrongValueException(this.usuarioFirmante,
					Labels.getLabel("gedo.error.faltaUsuarioFirmante"));
		}else{
			maps.put("firmante", this.usuarioFirmante.getSelectedItem());
		}
	}
	
	public void onClick$cancelar() {
		this.enviarAFirmar.onClose();
	}

	@Override
	protected void asignarTarea() throws InterruptedException, Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void enviarBloqueoPantalla(Object data) {
		// TODO Auto-generated method stub

	}

}
