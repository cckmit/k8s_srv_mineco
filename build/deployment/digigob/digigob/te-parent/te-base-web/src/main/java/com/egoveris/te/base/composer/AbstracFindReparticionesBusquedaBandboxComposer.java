package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.te.base.service.ReparticionSadeService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
 

public abstract class AbstracFindReparticionesBusquedaBandboxComposer
    extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 6524020723514095435L;

  protected ReparticionSadeService reparticionSadeService;
  @Autowired
  protected AnnotateDataBinder binder;
  protected List<ReparticionBean> listaReparticionSADESeleccionada;
  protected List<ReparticionBean> listaReparticionSeleccionada;
  protected ReparticionBean reparticionSeleccionada;
  @WireVariable(ConstantesServicios.REPARTICION_SERVICE)
  protected ReparticionServ reparticionServ;
}
