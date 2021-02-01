package com.egoveris.te.base.composer;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SubirDocumentoImportadoComposer extends EEGenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7693514837432124493L;
	
	private static final Logger logger = LoggerFactory.getLogger(SubirDocumentoImportadoComposer.class);

	private TipoDocumentoDTO selectedTipoDocumento;
	
	protected ExpedienteElectronicoDTO ee;

	
	@Autowired
	private Bandbox familiaEstructuraTree;
	
	@Autowired
	private Window subirDocumentoWindow;
	
	@Autowired
	private Bandpopup familia;
	
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	@WireVariable(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
	private DocumentoManagerService documentoManagerService;
	
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	protected TipoDocumentoService tipoDocumentoService;
	
	private DocumentoGedoService documentoGedoService;
	
	private UsuariosSADEService usuariosSADEService;
	
	public Tree familiaTipoTree;
	
	private Textbox textoTipoDocumento;
	
	@Autowired
	protected AnnotateDataBinder binder;
	
	private String usuario = null;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component component) throws Exception {
		
		super.doAfterCompose(component);
		
		documentoManagerService = (DocumentoManagerService) SpringUtil.getBean(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE);
		expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
		usuariosSADEService = (UsuariosSADEService) SpringUtil.getBean(ConstantesServicios.USUARIOS_SADE_SERVICE);
		tipoDocumentoService = (TipoDocumentoService) SpringUtil.getBean(ConstantesServicios.TIPO_DOCUMENTO_SERVICE);
		documentoGedoService = (DocumentoGedoService) SpringUtil.getBean("documentoGedoServiceImpl");
		
		component.addEventListener(Events.ON_NOTIFY, new SubirDocumentoListener(this));

		this.usuario = Executions.getCurrent().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		
		// cargar padre a componente hijo
		Event event = new Event(InicioDocumentoFamiliaTipoDocumentoBandBoxComposer.SET_COMPONENT_PARENT,familia , subirDocumentoWindow);
		Events.sendEvent(event);
		
		final Usuario datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();
		
		this.binder = new AnnotateDataBinder(component);
		
	}
	
	  public void onSelect$familiaTipoTree() throws InterruptedException {
		    String codigoId = familiaTipoTree.getSelectedItem().getTreerow().getId();
		    
		    if (codigoId != null && !"".equals(codigoId.trim())) {
			  TipoDocumentoDTO selecDocumArbol = buscarDocumento(codigoId);
		      
		      this.setSelectedTipoDocumento(selecDocumArbol);
		    }
		  }
	
	  private TipoDocumentoDTO buscarDocumento(String acronimo) {
		    return this.tipoDocumentoService.consultarTipoDocumentoPorAcronimo(acronimo);
		  }
	  
	public void onUpload$inciarDocumentoButtond(UploadEvent event) throws IOException {
		if (this.getSelectedTipoDocumento() == null ) {
			throw new WrongValueException(this.familiaEstructuraTree,
					Labels.getLabel("ee.general.tipoDocumentoInvalido"));
		}
		if(!this.getSelectedTipoDocumento().getTipoProduccion().equals(ConstantesWeb.TIPO_PRODUCCION_IMPORTADO)) {
			throw new WrongValueException(this.familiaEstructuraTree,"Solo se puede subir Documentos de tipo IMPORTADO");
		}
		
		int limitBytes = 5242880; // 5 Mb
		int fileSizeBytes = event.getMedia().getStreamData().available();
		if (event.getMedias() != null && fileSizeBytes <= limitBytes) {
			Media mediaUpld = event.getMedia();
			if ("pdf".equalsIgnoreCase(mediaUpld.getFormat())) {
				Messagebox.show(Labels.getLabel("te.inicioDocumento.question") + " " + mediaUpld.getName() + " " + Labels.getLabel("te.inicioDocumento.questions"),
						Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
						(EventListener<Event>) evt -> {
							if (((Integer) evt.getData()).equals(Messagebox.YES)) {
								Usuario usuario = this.usuariosSADEService.obtenerUsuarioActual();
								String acronimoGedo = documentoManagerService.generarDocumentoGedo(mediaUpld,
										this.getSelectedTipoDocumento().getAcronimo(),usuario);
								
								expedienteElectronicoService.vinculacionDocumentosAExpedienteTe(acronimoGedo,
										this.ee.getCodigoCaratula(), Executions.getCurrent().getSession()
										.getAttribute(ConstantesWeb.SESSION_USERNAME).toString());

								Messagebox.show(Labels.getLabel("te.inicioDocumento.agregado"), Labels.getLabel("ee.general.information"), Messagebox.OK,
										Messagebox.INFORMATION);
								Events.echoEvent(Events.ON_CLIENT_INFO, this.self.getParent(), null);
								this.subirDocumentoWindow.detach();
							}
						});
			} else {
				Messagebox.show(Labels.getLabel("te.inicioDocumento.pdf"), "Error", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.general.mensaje.fileupload"), Labels.getLabel("ee.general.advertencia"),
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	public TipoDocumentoDTO getSelectedTipoDocumento() {
		return selectedTipoDocumento;
	}


	public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
		this.selectedTipoDocumento = selectedTipoDocumento;
	}
	
	public void cargarTipoDocumento(TipoDocumentoDTO data) {
		this.familiaEstructuraTree.setText(data.getAcronimo().toUpperCase());
		this.familiaEstructuraTree.close();
		setSelectedTipoDocumento(data);
		//onSelectTipoDocumento();
		this.binder.loadAll();
	}
	
	@SuppressWarnings("rawtypes")
	public class SubirDocumentoListener implements EventListener{

		SubirDocumentoImportadoComposer composer;
		
		SubirDocumentoListener(SubirDocumentoImportadoComposer composer){
			this.composer = composer;
		}
		
		@Override
		public void onEvent(Event event) throws Exception {

			if (event.getName().equals(Events.ON_NOTIFY)) {
				TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
				this.composer.cargarTipoDocumento(data);
			}
			
		}
		
	}

	public Textbox getTextoTipoDocumento() {
		return textoTipoDocumento;
	}


	public void setTextoTipoDocumento(Textbox textoTipoDocumento) {
		this.textoTipoDocumento = textoTipoDocumento;
	}
	
	
}
