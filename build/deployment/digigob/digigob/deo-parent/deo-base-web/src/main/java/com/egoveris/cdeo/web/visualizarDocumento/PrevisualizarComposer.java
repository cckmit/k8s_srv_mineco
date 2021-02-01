package com.egoveris.cdeo.web.visualizarDocumento;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import com.egoveris.deo.base.service.IVisualizarDocumentosService;
import com.egoveris.deo.model.model.VisualizarDocumentoDTO;

// Se hace el extends para que llame a VisualizacionDocumentoComposer primero
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PrevisualizarComposer extends VisualizacionDocumentoComposer {

	/**
	* 
	*/
	private static final long serialVersionUID = 7707343020063172131L;
	private static final Logger logger = LoggerFactory.getLogger(PrevisualizarComposer.class);

	@WireVariable("visualizarDocumentosServiceImpl")
	private IVisualizarDocumentosService visualizarDocumentosService;

	@WireVariable
	private Desktop desk;

	private Iframe imagePreviewer;
	private Label textoLeyenda;

	private Row rowImagePreviewer;

	protected byte[] contenidoTemporal;

	/*
	 * Estas variables están seteadas en el Desktop de ZK.
	 * 
	 */
	private String popupUserDocumento;
	private String popupNumeroSade;
	private VisualizarDocumentoDTO popupDocumentoDTO;
	private Map<String, Boolean> popupParametrosVisualizacion;

	@Override
	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		if (this.popupDocumentoDTO == null) {
			this.popupDocumentoDTO = (VisualizarDocumentoDTO) desk.getAttribute(Constantes.DOCUMENTODTO);
		}

		if (this.popupDocumentoDTO != null) {
			this.generarPreviewInicial();
			this.filtrarFilasAccesoPrev();
		}
	}

	private void generarPreviewInicial() {
		try {
			int maxPreview = visualizarDocumentosService.obtenerMaxCantPrevisualizar();
			this.textoLeyenda.setValue("La vista previa muestra solamente las primeras " + maxPreview
					+ " hojas. En caso de querer visualizar la totalidad del documento proceda a descargarlo.");
			AMedia amedia = new AMedia("previsualizacion.pdf", "pdf", "application/pdf", visualizarDocumentosService
					.obtenerPrimerasHojasPrevisualizacionDocumento(popupDocumentoDTO.getNumeroSade()));
			this.imagePreviewer.setContent(amedia);
		} catch (Exception e) {
			logger.debug("Error en PrevisualizarComposer.generarPreviewInicial(): ", e);
//			if(popupDocumentoDTO != null 
//					&& popupDocumentoDTO.getNumeroSade() != null) {
//				Messagebox.show(
//						"Error al generar la previsualización del documento, por favor, consulte con su administrador "
//								+ popupDocumentoDTO.getNumeroSade(),
//						popupDocumentoDTO.getNumeroSade().concat(".pdf"), Messagebox.OK, Messagebox.ERROR);	
//			} else {
//				Messagebox.show(
//						"Error al generar la previsualización del documento, por favor, consulte con su administrador", "", Messagebox.OK, Messagebox.ERROR);	
//			}
		}
	}

	private void filtrarFilasAccesoPrev() {
		if (this.popupDocumentoDTO != null
				&& !this.popupDocumentoDTO.getPuedeVerDocumento()) {
			this.rowImagePreviewer.setVisible(false);
		}
	}

	public Row getRowImagePreviewer() {
		return rowImagePreviewer;
	}

	public void setRowImagePreviewer(Row rowImagePreviewer) {
		this.rowImagePreviewer = rowImagePreviewer;
	}

	public String getPopupUserDocumento() {
		return popupUserDocumento;
	}

	public void setPopupUserDocumento(String popupUserDocumento) {
		this.popupUserDocumento = popupUserDocumento;
	}

	public String getPopupNumeroSade() {
		return popupNumeroSade;
	}

	public void setPopupNumeroSade(String popupNumeroSade) {
		this.popupNumeroSade = popupNumeroSade;
	}

	@Override
	public VisualizarDocumentoDTO getPopupDocumentoDTO() {
		return popupDocumentoDTO;
	}

	@Override
	public void setPopupDocumentoDTO(VisualizarDocumentoDTO popupDocumentoDTO) {
		this.popupDocumentoDTO = popupDocumentoDTO;
	}

	public Map<String, Boolean> getPopupParametrosVisualizacion() {
		return popupParametrosVisualizacion;
	}

	public void setPopupParametrosVisualizacion(Map<String, Boolean> popupParametrosVisualizacion) {
		this.popupParametrosVisualizacion = popupParametrosVisualizacion;
	}

	public Label getTextoLeyenda() {
		return textoLeyenda;
	}

	public void setTextoLeyenda(Label textoLeyenda) {
		this.textoLeyenda = textoLeyenda;
	}

}
