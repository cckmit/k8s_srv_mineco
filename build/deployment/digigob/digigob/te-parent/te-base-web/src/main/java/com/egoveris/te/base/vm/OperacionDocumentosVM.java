package com.egoveris.te.base.vm;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.OperacionDocumentoDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;
import com.egoveris.te.base.service.iface.IOperacionDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class OperacionDocumentosVM {
	
  private static final Logger logger = LoggerFactory.getLogger(OperacionDocumentosVM.class);
  
  public static final String KEY_ERR_CARGAR_DOCS_TITLE = "ee.operacion.documentosOperacion.error.cargarDocs.title";
  public static final String KEY_ERR_CARGAR_DOCS_MSG = "ee.operacion.documentosOperacion.error.cargarDocs.message";
  
	@WireVariable(ConstantesServicios.OPERACION_DOC_SERVICE)
	private IOperacionDocumentoService operacionDocumentoService;
	
	private OperacionDTO operacion;
	private List<OperacionDocumentoDTO> documentosObligatorios;
	private List<OperacionDocumentoDTO> documentosOpcionales;
	
	private boolean opTieneDocumentos;
	
	/**
	 * Init de la clase. Recibe la operacion correspondiente y llama
	 * a inicializar la presentacion de documentos segun su tipo (opcionales
	 * u obligatorios)
	 * 
	 * @param operacion
	 */
	@Init
	public void init(@ExecutionArgParam("operacion") OperacionDTO operacion) {
		setOperacion(operacion);
		initDocumentosSegunTipo(operacion);
		
		if (getDocumentosObligatorios() != null && !getDocumentosObligatorios().isEmpty()) {
		  setOpTieneDocumentos(true);
		}
		
		if (getDocumentosOpcionales() != null && !getDocumentosOpcionales().isEmpty()) {
      setOpTieneDocumentos(true);
    }
	}
	
	/**
	 * Separa los tipos de documento para la operacion
	 * segun los obligatorios y opcionales, lo cual viene dado
	 * en el tipo de operacion
	 * 
	 * @param operacion
	 */
	private void initDocumentosSegunTipo(OperacionDTO operacion) {
		List<TipoOperacionDocDTO> tiposDocOper = null;
		List<OperacionDocumentoDTO> docsPersistidosOper = null;
		
		documentosObligatorios = new ArrayList<>();
		documentosOpcionales = new ArrayList<>();
		
		// El tipo de operacion contiene los tipos de documentos posibles
		// para la operacion
		if (operacion != null && operacion.getTipoOperacion() != null) {
			tiposDocOper = operacion.getTipoOperacionOb().getTiposOpDocumento();
			
			try {
			  docsPersistidosOper = operacionDocumentoService.getDocumentosOperacion(operacion.getId());
			}
			catch (ServiceException e) {
			  logger.error("Error en OperacionDocumentosVM.initDocumentosSegunTipo(): ", e);
        Messagebox.show(Labels.getLabel(KEY_ERR_CARGAR_DOCS_MSG), Labels.getLabel(KEY_ERR_CARGAR_DOCS_TITLE), Messagebox.OK,
            Messagebox.ERROR);
        tiposDocOper = null;
			}
		}
		
		if (tiposDocOper != null) {
		  // Recorre los tipos de documentos dados por el tipo de operacion
			for (TipoOperacionDocDTO tipoOperacionDocDTO : tiposDocOper) {
				OperacionDocumentoDTO documentoOperacion = inicializaDocumentoOp(tipoOperacionDocDTO, docsPersistidosOper);
				
				// Opcionales
				if (tipoOperacionDocDTO.isOpcional() && tipoOperacionDocDTO.getTipoDocumentoGedo() != null) {
					documentoOperacion.setOperacion(operacion);
					documentoOperacion.setTipoDocumentoGedo(tipoOperacionDocDTO.getTipoDocumentoGedo());
					documentosOpcionales.add(documentoOperacion);
				}
				// Obligatorios
				else if (tipoOperacionDocDTO.isObligatorio() && tipoOperacionDocDTO.getTipoDocumentoGedo() != null) {
					documentoOperacion.setOperacion(operacion);
					documentoOperacion.setTipoDocumentoGedo(tipoOperacionDocDTO.getTipoDocumentoGedo());
					documentosObligatorios.add(documentoOperacion);
				}
			}
		}
	}
	
	/**
	 * Inicializa un objeto OperacionDocumentoDTO. Si la operacion tiene
	 * con datos el documento correspondiente a un tipo dado, utiliza dichos datos.
	 * De lo contrario es una nueva instancia.
	 * 
	 * @param tipoDocumento
	 * @param documentosOperacion
	 * @return
	 */
  private OperacionDocumentoDTO inicializaDocumentoOp(TipoOperacionDocDTO tipoDocumento,
      List<OperacionDocumentoDTO> documentosOperacion) {
	  OperacionDocumentoDTO documentoOp = new OperacionDocumentoDTO();
	  
	  if (tipoDocumento != null && documentosOperacion != null) {
	    for (OperacionDocumentoDTO documento : documentosOperacion) {
        if (tipoDocumento.getTipoDocumentoGedo() != null && documento.getTipoDocumentoGedo() != null
            && tipoDocumento.getTipoDocumentoGedo().getId().equals(documento.getTipoDocumentoGedo().getId())) {
          documentoOp = documento;
        }
      }
	  }
	  
	  return documentoOp;
	}
	
	// GETTERS - SETTERS
	
	public OperacionDTO getOperacion() {
		return operacion;
	}

	public void setOperacion(OperacionDTO operacion) {
		this.operacion = operacion;
	}

	public List<OperacionDocumentoDTO> getDocumentosObligatorios() {
		return documentosObligatorios;
	}

	public void setDocumentosObligatorios(List<OperacionDocumentoDTO> documentosObligatorios) {
		this.documentosObligatorios = documentosObligatorios;
	}

	public List<OperacionDocumentoDTO> getDocumentosOpcionales() {
		return documentosOpcionales;
	}

	public void setDocumentosOpcionales(List<OperacionDocumentoDTO> documentosOpcionales) {
		this.documentosOpcionales = documentosOpcionales;
	}

  public boolean isOpTieneDocumentos() {
    return opTieneDocumentos;
  }

  public void setOpTieneDocumentos(boolean opTieneDocumentos) {
    this.opTieneDocumentos = opTieneDocumentos;
  }
	
}