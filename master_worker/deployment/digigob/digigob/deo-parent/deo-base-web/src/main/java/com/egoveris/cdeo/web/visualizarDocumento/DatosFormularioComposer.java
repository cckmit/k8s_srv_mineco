package com.egoveris.cdeo.web.visualizarDocumento;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;

import com.egoveris.deo.model.model.VisualizarDocumentoDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DatosFormularioComposer extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 7481671818963519429L;

	@WireVariable("zkFormManagerFactory")
	private IFormManagerFactory<IFormManager<Component>> formManagerFactory;

	@WireVariable
  private Desktop desktop;
	
	/*
	 * Estas variables están seteadas en el Desktop de ZK.
	 */
	private String popupUserDocumento;
	private String popupNumeroSade;
	private Map<String, Boolean> popupParametrosVisualizacion;
	private VisualizarDocumentoDTO popupDocumentoDTO;
	private Div divDynForm;

	@Override
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
    // Setea las variables de desktop
    if (popupUserDocumento == null) {
      popupUserDocumento = (String) desktop.getAttribute(Constantes.USER);
    }

    if (popupNumeroSade == null) {
      popupNumeroSade = (String) desktop.getAttribute(Constantes.NUMERO_DOCUMENTO);
    }

    if (popupParametrosVisualizacion == null) {
      popupParametrosVisualizacion = (Map<String, Boolean>) desktop.getAttribute(Constantes.PARAMETROS_VISUALIZACION);
    }
    
    if (popupDocumentoDTO == null) {
      popupDocumentoDTO = (VisualizarDocumentoDTO) desktop.getAttribute(Constantes.DOCUMENTODTO);
    }
		
    if (popupDocumentoDTO != null) {
      IFormManager<Component> manager = formManagerFactory.create(popupDocumentoDTO.getIdFormulario());
      manager.getFormComponent(popupDocumentoDTO.getIdTransaccion(), true).setParent(divDynForm);
    }
	}
	
	public void setPopupUserDocumento(String popupUserDocumento) {
		this.popupUserDocumento = popupUserDocumento;
	}

	public String getPopupUserDocumento() {
		return popupUserDocumento;
	}

	public void setPopupNumeroSade(String popupNumeroSade) {
		this.popupNumeroSade = popupNumeroSade;
	}

	public String getPopupNumeroSade() {
		return popupNumeroSade;
	}

	public VisualizarDocumentoDTO getPopupDocumentoDTO() {
		return popupDocumentoDTO;
	}

	public void setPopupDocumentoDTO(VisualizarDocumentoDTO popupDocumentoDTO) {
		this.popupDocumentoDTO = popupDocumentoDTO;
	}

	public Map<String, Boolean> getPopupParametrosVisualizacion() {
		return popupParametrosVisualizacion;
	}

	public void setPopupParametrosVisualizacion(Map<String, Boolean> popupParametrosVisualizacion) {
		this.popupParametrosVisualizacion = popupParametrosVisualizacion;
	}

}
