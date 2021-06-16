package com.egoveris.te.base.composer;

import com.egoveris.ffdd.model.exception.NotFoundException;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

/**
 *
 * @author
 *
 */
@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ValidarFormularioControladoComposer extends EEGenericForwardComposer {

  // VARIABLES - BEGIN

  private final static Logger logger = LoggerFactory
      .getLogger(ValidarFormularioControladoComposer.class);

  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;

  private Window formularioControladoWindows;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
  }

  protected void crearFormularioControlado(IngresoSolicitudExpedienteDTO solicitud, Window win,
      TrataDTO trata) throws NotFoundException {

    TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
        .obtenerTipoDocumento(trata.getAcronimoDocumento());

    if (tipoDocumentoCaratulaDeEE == null) {
      throw new WrongValueException(Labels.getLabel("ee.nuevoexpediente.faltadocumentocaratula"));
    }

    String form = tipoDocumentoCaratulaDeEE.getIdFormulario();

    Map<String, Object> mapComp = new HashMap<>();
    mapComp.put("doBeforeExecuteTask", Boolean.FALSE);
    mapComp.put("nombreFormulario", form);
    mapComp.put("solicitud", solicitud);
    mapComp.put("numeroSade", solicitud.getExpedienteElectronico().getCodigoCaratula());
    mapComp.put("expElect", solicitud.getExpedienteElectronico());
    mapComp.put("tipoDoc", tipoDocumentoCaratulaDeEE);

    this.formularioControladoWindows = (Window) Executions
        .createComponents("/expediente/macros/formularioControlado.zul", win, mapComp);

    this.formularioControladoWindows.doModal();
    this.formularioControladoWindows.setPosition("center");
    this.formularioControladoWindows.setClosable(true);
  }
}