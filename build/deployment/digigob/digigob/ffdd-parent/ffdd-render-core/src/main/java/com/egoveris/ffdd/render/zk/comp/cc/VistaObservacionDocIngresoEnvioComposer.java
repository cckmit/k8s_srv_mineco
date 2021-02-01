package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class VistaObservacionDocIngresoEnvioComposer extends ComplexComponentComposer{

  
  private static final long serialVersionUID = 1L;
  
  
  Cell secuencialObservacionDiv;
  Cell codigoObservacionDiv;
  Cell descripcionObservacionDiv;

  InputElement secuencialObservacion;
  InputElement codigoObservacion;
  InputElement descripcionObservacion;

  
  
  
 @Override
 protected String getName() {
   return "observaciondocingresoenvio";
  }
 

 @Override
	protected void setDefaultValues(String prefixName) {
   
		
   }
}
