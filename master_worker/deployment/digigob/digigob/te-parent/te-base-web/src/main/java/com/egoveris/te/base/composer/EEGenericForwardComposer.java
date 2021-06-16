package com.egoveris.te.base.composer;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.te.base.util.ConstantesServicios;

/**
 * The Class EEGenericForwardComposer.
 */
@SuppressWarnings("rawtypes")
public class EEGenericForwardComposer extends GenericForwardComposer {

	@WireVariable("dBProperty")
	private AppProperty dBProperty;
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7027484320891889785L;

  /** The usuario service. */
  @WireVariable("usuarioServiceImpl")
  public IUsuarioService usuarioService;
  
	/**
	 * Closes the associated window and sent a notify event to parent component
	 */
	protected final void closeAssociatedWindow() {
		if (this.self==null){
			throw new IllegalAccessError("The self associated component is not present");
		}
		Events.sendEvent(this.self.getParent(), new Event(Events.ON_NOTIFY));
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}
	
	/**
	 * Closes the associated window and sent a notify event to parent component
	 */
	protected final void closeAndNotifyAssociatedWindow(Object eventData) {
		if (this.self == null) {
			throw new IllegalAccessError(
					"The self associated component is not present");
		}
		
		// Avisa a la ventana padre que debe refrescar su contenido.
		if (this.self.getParent()!=null){
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),eventData));}
		
		// Avisa a la propia ventana que debe cerrarse. En este caso se
		// auto-setea el atributo dontAskBeforeClose para que no muestre popup
		// de confirmación de abandono.
		this.self.setAttribute("dontAskBeforeClose", true);
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	public AppProperty getdBProperty() {
		if (dBProperty == null) {
			dBProperty = (AppProperty) SpringUtil.getBean(ConstantesServicios.APP_PROPERTY);
		}
		return dBProperty;
	}

	public void setdBProperty(AppProperty dBProperty) {
		this.dBProperty = dBProperty;
	}

	/**
	 * @return the usuarioService
	 */
	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
}
