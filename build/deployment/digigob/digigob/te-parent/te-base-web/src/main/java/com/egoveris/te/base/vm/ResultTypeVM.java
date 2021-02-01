package com.egoveris.te.base.vm;

import com.egoveris.te.base.model.PropertyConfigurationDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.MantainerPrefix;

import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@Init(superclass = true)
public class ResultTypeVM extends PropertyMantainerVM {
  
  static {
    mantainerPrefix = MantainerPrefix.RESULT_TYPE;
    formWindowZul = "/tipoResultados/formTipoResultado.zul";
  }
  
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;
  
  /**
   * Method overrided for custom on delete validation.
   * We can't delete a result type if it's used on a Trata
   */
  @Override
  public boolean onDeleteValidation(PropertyConfigurationDTO property) {
    boolean result = true;
    
    if (trataService.isTipoResultadoEnUso(property.getClave())) {
      result = false;
      
      Messagebox.show(Labels.getLabel("ee.tipoResultados.delete.enUso.message"),
          Labels.getLabel("ee.tipoResultados.delete.enUso.title"), Messagebox.OK, Messagebox.ERROR);
    }
    
    return result;
  }
  
}