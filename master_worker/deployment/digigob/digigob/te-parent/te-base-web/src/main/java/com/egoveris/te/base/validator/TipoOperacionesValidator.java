package com.egoveris.te.base.validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.TipoOperacionDTO;
import com.egoveris.te.base.service.iface.ITipoOperacionService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.vm.TipoOperacionesVM;

public class TipoOperacionesValidator extends AbstractValidator {
	
	private static final Logger logger = LoggerFactory.getLogger(TipoOperacionesVM.class);
	
	private static final String KEY_MSG_FORM = "formTipoOperacion";
	private static final String KEY_MSG_CODIGO = "formTipoOperacion.codigo";
	private static final String KEY_MSG_NOMBRE = "formTipoOperacion.nombre";
	private static final String KEY_MSG_WORKFLOW = "formTipoOperacion.workflow";
	private static final String KEY_MSG_CABECERA = "formTipoOperacion.cabecera";
	
	private static final String KEY_LITERAL_CAMPO_REQUERIDO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.campoRequerido";
	private static final String KEY_LITERAL_VALIDACION_CABECERA = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.cabecera";
	private static final String KEY_LITERAL_REGISTRO_DUPLICADO = "ee.tipoOperacion.admTipoOperaciones.formulario.validacion.registroDuplicado";
	
	/**
	 * Valida el formulario completo de tipo operacion
	 */
	@Override
	public void validate(ValidationContext ctx) {
	  TipoOperacionesVM tipoOperacionesVM = null;
	  
	  if (ctx.getProperty().getBase() instanceof TipoOperacionesVM) {
	    tipoOperacionesVM = (TipoOperacionesVM) ctx.getProperty().getBase();
	  }
	  
	  if (tipoOperacionesVM != null) {
  		validaFormTipoOperacion(ctx, tipoOperacionesVM);
  		validaCabecera(ctx, tipoOperacionesVM);
	  }
	}
	
	/**
	 * Valida el formulario de tipo operacion
	 * (la parte correspondiente a datos base)
	 * 
	 * @param ctx ValidationContext
	 * @param tipoOperacionesVM
	 */
	private void validaFormTipoOperacion(ValidationContext ctx, TipoOperacionesVM tipoOperacionesVM) {
    if (tipoOperacionesVM.getTipoOperacion().getCodigo() == null
        || StringUtils.isBlank(tipoOperacionesVM.getTipoOperacion().getCodigo())) {
      this.addInvalidMessage(ctx, KEY_MSG_CODIGO, Labels.getLabel(KEY_LITERAL_CAMPO_REQUERIDO));
    } else {
      validaCodigoDuplicado(ctx, tipoOperacionesVM);
    }
    
    if (tipoOperacionesVM.getTipoOperacion().getNombre() == null
        || StringUtils.isBlank(tipoOperacionesVM.getTipoOperacion().getNombre())) {
      this.addInvalidMessage(ctx, KEY_MSG_NOMBRE, Labels.getLabel(KEY_LITERAL_CAMPO_REQUERIDO));
    }
		
		validaWorkflow(ctx, tipoOperacionesVM);
	}
	
	/**
	 * Valida si el codigo esta duplicado
	 * 
	 * @param ctx ValidationContext
	 * @param tipoOperacionesVM
	 */
	private void validaCodigoDuplicado(ValidationContext ctx, TipoOperacionesVM tipoOperacionesVM) {
		ITipoOperacionService tipoOperacionService = (ITipoOperacionService) SpringUtil.getBean(ConstantesServicios.TIPO_OPERACION_SERVICE);
		
		TipoOperacionDTO tipoOperacion = new TipoOperacionDTO();
		tipoOperacion.setCodigo(tipoOperacionesVM.getTipoOperacion().getCodigo());
		tipoOperacion.setCodigoAux(tipoOperacionesVM.getTipoOperacion().getCodigoAux());
		
    try {
      if (tipoOperacionService.isCodigoDuplicado(tipoOperacion)) {
        this.addInvalidMessage(ctx, KEY_MSG_CODIGO, Labels.getLabel(KEY_LITERAL_REGISTRO_DUPLICADO));
      }
    } catch (ServiceException e) {
      logger.error("Error en TipoOperacionesValidator.validaCodigoDuplicado(): ", e);
      Messagebox.show(Labels.getLabel(TipoOperacionesVM.LITERAL_KEY_ERROR_SERVICIO),
          Labels.getLabel(TipoOperacionesVM.LITERAL_KEY_TITULO_ERROR_SERVICIO), Messagebox.OK, Messagebox.ERROR);
      this.addInvalidMessage(ctx, KEY_MSG_FORM, " ");
    }
	}
	
	/**
	 * Valida si se selecciono el valor de workflow
	 * 
	 * @param ctx ValidationContext
	 * @param tipoOperacionesVM
	 */
	private void validaWorkflow(ValidationContext ctx, TipoOperacionesVM tipoOperacionesVM) {
		if (tipoOperacionesVM.getWorkflowSel() == null || tipoOperacionesVM.getWorkflowSel().getValue() == null) {
			this.addInvalidMessage(ctx, KEY_MSG_WORKFLOW, Labels.getLabel(KEY_LITERAL_CAMPO_REQUERIDO));
		}
	}
	
	/**
	 * Valida si hay algun formulario dinamico seleccionado en la seccion cabecera
	 * 
	 * @param ctx ValidationContext
	 * @param tipoOperacionesVM
	 */
	private void validaCabecera(ValidationContext ctx, TipoOperacionesVM tipoOperacionesVM) {
		if (tipoOperacionesVM.getFormulariosSel() == null || tipoOperacionesVM.getFormulariosSel().isEmpty()) {
			this.addInvalidMessage(ctx, KEY_MSG_CABECERA, Labels.getLabel(KEY_LITERAL_VALIDACION_CABECERA));
		}
	}
	
}
