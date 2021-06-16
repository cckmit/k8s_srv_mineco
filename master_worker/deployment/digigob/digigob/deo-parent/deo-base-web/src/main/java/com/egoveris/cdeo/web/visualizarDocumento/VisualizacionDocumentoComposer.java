package com.egoveris.cdeo.web.visualizarDocumento;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import com.egoveris.deo.base.exception.VisualizacionDocumentoException;
import com.egoveris.deo.base.service.IVisualizarDocumentosService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.VisualizarDocumentoDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VisualizacionDocumentoComposer extends GenericForwardComposer<Component> {

	/**
	* 
	*/
	private static final long serialVersionUID = 8822488043271661635L;
	private static final Logger logger = LoggerFactory.getLogger(VisualizacionDocumentoComposer.class);

	private static final String DOCUMENTO_RESERVADO = "Documento reservado solo se visualizarán los datos básicos.";
	private static final String DOCUMENTO_DEPURADO = "Documento depurado solo se visualizarán los datos básicos.";

	private Tab datosFormulario;

	private Button btnDescargarDocumento;
	private Button btnCerrar;
	private Label auxiliarDocumentoReservado;

	@WireVariable("visualizarDocumentosServiceImpl")
	private IVisualizarDocumentosService visualizarDocumentosService;
	
	@WireVariable("zkFormManagerFactory")
	private IFormManagerFactory<IFormManager<Component>> formManagerFactory;

	@WireVariable
	private Desktop desktop;

	/*
	 * Estas variables están seteadas en el Desktop de ZK.
	 */
	private String popupUserDocumento;
	private String popupNumeroSade;
	private Boolean popupTieneTemplate;
	private Map<String, Boolean> popupParametrosVisualizacion;
	private VisualizarDocumentoDTO popupDocumentoDTO;

	private Tab previsualizarDocumento;
	private Tab datosDocumento;
	private Window visualizacionDocumentoWindow;

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		datosFormulario.setVisible(false);

		// Setea las variables de desktop
		if (popupUserDocumento == null) {
			popupUserDocumento = (String) desktop.getAttribute(Constantes.USER);
		}

		if (popupNumeroSade == null) {
			popupNumeroSade = (String) desktop.getAttribute(Constantes.NUMERO_DOCUMENTO);
		}
		
		if(popupTieneTemplate == null) {
			popupTieneTemplate = (Boolean) desktop.getAttribute(Constantes.TIENE_TEMPLATE);
			if(popupTieneTemplate!=null) {
				datosDocumento.setVisible(popupTieneTemplate);
				datosDocumento.setDisabled(!popupTieneTemplate);				
			}
		}

		if (popupParametrosVisualizacion == null) {
			popupParametrosVisualizacion = (Map<String, Boolean>) desktop
					.getAttribute(Constantes.PARAMETROS_VISUALIZACION);
		}

		if (popupDocumentoDTO == null) {
			try {
				if (!this.popupParametrosVisualizacion.containsKey("assignee")) {
					this.popupDocumentoDTO = this.visualizarDocumentosService
							.completarDocumentoDTO(this.popupNumeroSade, this.popupUserDocumento);
				} else {
					this.popupDocumentoDTO = this.visualizarDocumentosService.completarDocumentoDTO(
							this.popupNumeroSade, this.popupUserDocumento,
							this.popupParametrosVisualizacion.get("assignee"));
				}

				desktop.setAttribute(Constantes.DOCUMENTODTO, this.popupDocumentoDTO);
			} catch (VisualizacionDocumentoException e) {
				Messagebox
						.show("Se ha producido un error al visualizar el documento GEDO, por favor consulte con su administrador."
								+ e, "Error al buscar el documento", Messagebox.OK, Messagebox.ERROR);
			} catch (Exception e) {
				Messagebox
						.show("Se ha producido un error desconocido al visualizar el documento GEDO, por favor consulte con su administrador."
								+ e, "Error al buscar el documento", Messagebox.OK, Messagebox.ERROR);
			}
		}

		if (this.popupDocumentoDTO != null && btnDescargarDocumento != null) {
			this.filtrarFilasAcceso();
			this.filtrarArchivo();
			this.filtrarDatosPropios();
			this.ocultarBotonCerrar();

			if (this.popupDocumentoDTO.getMotivoDepuracion() != null) {
				this.btnDescargarDocumento.setDisabled(true);
				this.btnDescargarDocumento.detach();
			}
		}
		
		if (popupDocumentoDTO != null && StringUtils.isNotEmpty(popupDocumentoDTO.getIdFormulario())) {
			datosFormulario.setVisible(true);
			
			try {
				formManagerFactory.create(popupDocumentoDTO.getIdFormulario());
			} catch (Exception e) {
				datosFormulario.setVisible(false);
			}
		}
	}

	private void filtrarDatosPropios() {
		if (popupDocumentoDTO.getIdFormulario() == null || popupDocumentoDTO.getIdTransaccion() == null) {
			this.datosFormulario.setDisabled(true);
		}
	}

	private void ocultarBotonCerrar() {
		if (this.popupParametrosVisualizacion
				.get(ParametrosVisualizacionDocumento.PARAMETRO_NO_MOSTRAR_CERRAR) != null) {
			btnCerrar.detach();
		}
	}

	private void filtrarArchivo() {
		if (this.popupParametrosVisualizacion
				.get(ParametrosVisualizacionDocumento.PARAMETRO_PREVISUALIZAR_DOCUMENTO) != null) {
			this.previsualizarDocumento.setDisabled(!this.popupParametrosVisualizacion
					.get(ParametrosVisualizacionDocumento.PARAMETRO_PREVISUALIZAR_DOCUMENTO));
		}
		if (this.popupParametrosVisualizacion
				.get(ParametrosVisualizacionDocumento.PARAMETRO_DESCARGAR_DOCUMENTO) != null) {
			this.btnDescargarDocumento.setDisabled(!this.popupParametrosVisualizacion
					.get(ParametrosVisualizacionDocumento.PARAMETRO_DESCARGAR_DOCUMENTO));
		}
	}

	private void filtrarFilasAcceso() {
		if (!this.popupDocumentoDTO.getPuedeVerDocumento()) {
			this.auxiliarDocumentoReservado = new Label();
			this.auxiliarDocumentoReservado.setValue(DOCUMENTO_RESERVADO);
			this.visualizacionDocumentoWindow.appendChild(auxiliarDocumentoReservado);
			this.btnDescargarDocumento.setDisabled(true);
			this.btnDescargarDocumento.setTooltiptext(DOCUMENTO_RESERVADO);
			this.previsualizarDocumento.setDisabled(true);
			this.previsualizarDocumento.setTooltiptext(DOCUMENTO_RESERVADO);
			this.datosFormulario.setDisabled(true);
		}

		if (this.popupDocumentoDTO.getMotivoDepuracion() != null) {
			this.auxiliarDocumentoReservado = new Label();
			this.auxiliarDocumentoReservado.setValue(DOCUMENTO_DEPURADO);
			this.visualizacionDocumentoWindow.appendChild(auxiliarDocumentoReservado);
			this.btnDescargarDocumento.setDisabled(true);
			this.btnDescargarDocumento.setTooltiptext(DOCUMENTO_DEPURADO);
			this.previsualizarDocumento.setDisabled(true);
			this.previsualizarDocumento.setTooltiptext(DOCUMENTO_DEPURADO);
			this.datosFormulario.setDisabled(false);
		}
	}

	public void onClick$btnCerrar() {
		Executions.getCurrent().getDesktop().setAttribute(Constantes.USER, null);
		Executions.getCurrent().getDesktop().setAttribute(Constantes.NUMERO_DOCUMENTO, null);
		Executions.getCurrent().getDesktop().setAttribute(Constantes.PARAMETROS_VISUALIZACION, null);
		Executions.getCurrent().getDesktop().setAttribute(Constantes.DOCUMENTODTO, null);
		this.self.detach();
	}

	public void onClose$visualizacionDocumentoWindow() {
		Executions.getCurrent().getDesktop().setAttribute(Constantes.USER, null);
		Executions.getCurrent().getDesktop().setAttribute(Constantes.NUMERO_DOCUMENTO, null);
		Executions.getCurrent().getDesktop().setAttribute(Constantes.PARAMETROS_VISUALIZACION, null);
		Executions.getCurrent().getDesktop().setAttribute(Constantes.DOCUMENTODTO, null);
	}

	public void onClick$btnDescargarDocumento() {
		if (this.popupDocumentoDTO.getMotivoDepuracion() == null) {
			if (StringUtils.isNotEmpty(this.popupDocumentoDTO.getNumeroSade())) {
				try {
					Filedownload.save(this.visualizarDocumentosService.descargaDocumento(this.popupDocumentoDTO), null,
							this.popupDocumentoDTO.getNumeroSade().concat(".pdf"));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public void setPrevisualizarDocumentoGedo(Tab previsualizarDocumentoGedo) {
		this.previsualizarDocumento = previsualizarDocumentoGedo;
	}

	public Tab getPrevisualizarDocumentoGedo() {
		return previsualizarDocumento;
	}

	public void setBtnCerrar(Button btnCerrar) {
		this.btnCerrar = btnCerrar;
	}

	public Button getBtnCerrar() {
		return btnCerrar;
	}

	public Button getBtnDescargarDocumento() {
		return btnDescargarDocumento;
	}

	public void setBtnDescargarDocumento(Button btnDescargarDocumento) {
		this.btnDescargarDocumento = btnDescargarDocumento;
	}

	public void setNumeroSade(String numeroSade) {
		this.popupNumeroSade = numeroSade;
	}

	public String getNumeroSade() {
		return popupNumeroSade;
	}

	public void setLoggedUsername(String loggedUsername) {
		this.popupUserDocumento = loggedUsername;
	}

	public String getLoggedUsername() {
		return popupUserDocumento;
	}

	public VisualizarDocumentoDTO getPopupDocumentoDTO() {
		return popupDocumentoDTO;
	}

	public void setPopupDocumentoDTO(VisualizarDocumentoDTO popupDocumentoDTO) {
		this.popupDocumentoDTO = popupDocumentoDTO;
	}

	public Window getVisualizacionDocumentoWindow() {
		return visualizacionDocumentoWindow;
	}

	public void setVisualizacionDocumentoWindow(Window visualizacionDocumentoWindow) {
		this.visualizacionDocumentoWindow = visualizacionDocumentoWindow;
	}

}
