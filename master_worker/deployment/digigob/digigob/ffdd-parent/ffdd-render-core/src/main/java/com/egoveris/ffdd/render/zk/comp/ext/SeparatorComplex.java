package com.egoveris.ffdd.render.zk.comp.ext;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;

public class SeparatorComplex extends ComplexComponenLayout {

  private static final long serialVersionUID = 5378339918930005318L;

  private Boolean repetidor;
  private FormularioComponenteDTO formComp;

  public SeparatorComplex(FormularioComponenteDTO formComp, String zul, Boolean repetidor) {
    super(formComp, zul);
    this.formComp = formComp;
    this.repetidor = repetidor;
  }

  public Boolean getRepetidor() {
    return repetidor;
  }

  public void setRepetidor(Boolean repetidor) {
    this.repetidor = repetidor;
  }

  public FormularioComponenteDTO getFormComp() {
    return formComp;
  }

  public void setFormComp(FormularioComponenteDTO formComp) {
    this.formComp = formComp;
  }
  
  
 
}