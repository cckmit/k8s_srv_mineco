package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



@SuppressWarnings("serial")
public class EnviarARevisarComposer extends ValidarApoderamientoComposer {

	protected Window enviarARevisar;
	protected Combobox usuarioRevisor;
	protected Textbox mensajeARevisor;

	public void doAfterCompose(Component component) throws Exception {

		super.doAfterCompose(component);
		this.usuarioRevisor.setModel(ListModels.toListSubModel(
				new ListModelList(getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));

	}

	public void onClick$aceptar() {

		if (usuarioRevisor.getSelectedItem() == null) {
			this.usuarioRevisor.setValue(null);
			throw new WrongValueException(this.usuarioRevisor,
					Labels.getLabel("gedo.error.faltaUsuarioRevisor"));
		} else {
			Map<String, Object> maps = new HashMap<>();
			maps.put("origen", Constantes.EVENTO_ENVIAR_A_REVISAR);
			maps.put("revisor", this.usuarioRevisor.getSelectedItem());
			maps.put("mensaje", this.mensajeARevisor.getText());
			Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),
					maps));
		}

	}

	public void onClick$cancelar() {

		this.enviarARevisar.onClose();

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
