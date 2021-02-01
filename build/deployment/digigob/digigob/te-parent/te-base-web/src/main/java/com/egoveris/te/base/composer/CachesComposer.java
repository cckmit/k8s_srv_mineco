package com.egoveris.te.base.composer;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.util.ConstantesServicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CachesComposer extends GenericForwardComposer {
	
	final static Logger logger = LoggerFactory.getLogger(CachesComposer.class);

	private static final long serialVersionUID = 5196565751450798082L;

	// DBPROPERTY
	private AppProperty dBProperty;
	private Label sizeDBProperty;
	// FORMULARIO CONTROLADO
	private ExternalFormularioService formularioService;
	private Label sizeFormulario;
	private Button reinicioFormulario;
	// TIPOS DOC
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	private TipoDocumentoService tipoDocumentoService;
	private Label sizeTipoDoc;
	private Button reinicioTipoDoc;
	// USUARIOSSADE
	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;
	private Label sizeUsuarios;
	private Button reinicioUsuarios;
	// TRAMITES
	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;
	private Label sizeTratas;
	private Button reinicioTratas;
	

	public final void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		// BEANS
		dBProperty = (AppProperty) SpringUtil.getBean("dBProperty");
		formularioService = (ExternalFormularioService) SpringUtil.getBean("formularioService");
		
		// labelSizes
		sizeDBProperty.setValue(String.valueOf(dBProperty.getProperties().size()));
		sizeFormulario.setValue(String.valueOf(formularioService.obtenerTodosLosFormularios().size()));
		sizeTipoDoc.setValue(String.valueOf(tipoDocumentoService.obtenerTodosDocumentosGedo().size()));
		sizeUsuarios.setValue(String.valueOf(usuariosSADEService.getTodosLosUsuarios().size()));
		sizeTratas.setValue(String.valueOf(trataService.buscarTotalidadTratasEE().size()));
	}
	
	public void onClick$reinicioFormulario() {
		reinicioFormulario.setDisabled(true);
		sizeFormulario.setValue(null);
		new Thread() {
			 public void run() {
				 try {
					formularioService.obtenerTodosLosFormularios();
				} catch (DynFormException e) {
					logger.error(e.getMessage());
				}
			 }
		}.start();
	}
	
	public void onClick$reinicioTipoDoc() {
		reinicioTipoDoc.setDisabled(true);
		sizeTipoDoc.setValue(null);
	}
	
	public void onClick$reinicioUsuarios() {
		reinicioUsuarios.setDisabled(true);
		sizeUsuarios.setValue(null);
		new Thread() {
			 public void run() {
				 try {
				 usuariosSADEService.getTodosLosUsuarios();		 
					} catch (SecurityNegocioException e) {
						logger.error(e.getMessage());
						throw new WrongValueException(Labels.getLabel("ee.error.usuario.label.invalidos"));
			 }
			 }
		}.start();
	}
	
	public void onClick$reinicioTratas() {
		reinicioTratas.setDisabled(true);
		sizeTratas.setValue(null);
	}
}