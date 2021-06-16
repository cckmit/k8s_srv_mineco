package com.egoveris.te.base.tarjetausuario;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Popup;
import org.zkoss.zul.impl.XulElement;

//TODO REFACTORIZAR ESTA CLASE PARA: 1) EXTENDER POPUP DE FORMA TAL QUE CREE LA ESTRUCTURA BASE AL SETEAR EL POPUP PERO LOS DATOS SE BUSQUEN AL EVENTO "ONOPEN" 2) O BIEN CREAR UN SOLO POPUP REFERENCIADO POR TODOS LOS COMPONENTES QUE LO REQUIERAN, PERO CUYO CONTENIDO SE ADAPTE AL COMPONENTE INVOCADOR.
public class TarjetaDatosUsuario {

	// Con esta URI se puede obtener un archivo aún cuando se encuentre en un
	// jar del WEB-INF/lib
	private final static String _TEMPL = "/usuario/tarjetadatosusuario.zul";
	public final static String ID_POPUP = "tarjetaDatosUsuarioPopup";
	public final static String ATTR_USUARIO = "POP_USERNAME";

	// TODO pasar a archivo de i3-label
	private static final String MSG_TOOLTIP = "Haga click aquí para ver los datos del usuario";

	/**
	 * Adapta el componente recibido por parámetro para que al hacer click se
	 * muestre el popup de TarjetaDatosUsuario
	 *
	 * @param xulTarget
	 *            Componente que será modificado.
	 * @param usuario
	 *            Usuario sobre el que se mostrará la información detallada en
	 *            el Popup. De pasarse un usuario no válido, el Popup mostrará
	 *            un mensaje acorde.
	 */
	public static void setPopup(final XulElement xulTarget, final String usuario) {
		xulTarget.setTooltiptext(MSG_TOOLTIP);
		// TODO cambiar comportamiento de xulTarget para que al pasar el cursor
		// por encima se subraye, o muestre visualmente algún indicador que hay
		// una acción click posible
		xulTarget.setAttribute(ATTR_USUARIO, usuario);
		xulTarget.addEventListener(Events.ON_CLICK, new EventListener() {

			@Override
			public void onEvent(final Event event) throws Exception {
				final Component target = event.getTarget();

				final Component compPop = target.getRoot().getFellowIfAny(ID_POPUP);
				if (compPop != null) {
					compPop.detach();
				}
				final Map<String, Object> params = new HashMap<String, Object>(1);
				params.put(ATTR_USUARIO, event.getTarget().getAttribute(ATTR_USUARIO));
				final Popup pop = (Popup) Executions.createComponents(_TEMPL, target.getRoot(), params);
				pop.open(target);
			}

		});

	}
}
