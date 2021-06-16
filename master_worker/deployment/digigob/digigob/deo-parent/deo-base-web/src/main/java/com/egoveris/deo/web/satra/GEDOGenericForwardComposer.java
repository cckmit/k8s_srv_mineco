package com.egoveris.deo.web.satra;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GEDOGenericForwardComposer extends GenericForwardComposer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -7027484320891889785L;
  @WireVariable("usuarioServiceImpl")
  public IUsuarioService usuarioService;
  @WireVariable("sectorInternoServ")
  public SectorInternoServ sectorservice;

  @SuppressWarnings("unchecked")
	@Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
  }

  /**
   * Closes the associated window and sent a notify event to parent component
   */
  protected void closeAndNotifyAssociatedWindow(Object eventData) {
    if (this.self == null) {
      throw new IllegalAccessError("The self associated component is not present");
    }
    Boolean estadoExpediente = (Boolean) this.self.getAttribute(Constantes.VAR_NUMERO_SA);

    // Avisa a la ventana padre que debe refrescar su contenido.
    Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), eventData));

    // Avisa a la propia ventana que debe cerrarse. En este caso se
    // auto-setea el atributo dontAskBeforeClose para que no muestre popup
    // de confirmación de abandono.
    this.self.setAttribute("dontAskBeforeClose", true);
    this.self.setAttribute(Constantes.VAR_NUMERO_SA, null != estadoExpediente ? true : false);
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

	/**
	 * @return the sectorservice
	 */
	public SectorInternoServ getSectorservice() {
		return sectorservice;
	}

	/**
	 * @param sectorservice the sectorservice to set
	 */
	public void setSectorservice(SectorInternoServ sectorservice) {
		this.sectorservice = sectorservice;
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
