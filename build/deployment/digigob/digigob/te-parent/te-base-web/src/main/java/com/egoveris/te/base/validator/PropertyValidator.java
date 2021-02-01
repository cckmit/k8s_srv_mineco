package com.egoveris.te.base.validator;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;

import com.egoveris.te.base.vm.PropertyMantainerVM;

public class PropertyValidator extends AbstractValidator {
  
  @Override
  public void validate(ValidationContext ctx) {
    boolean validForm = false;
    PropertyMantainerVM propertyMantainerVM = null;
    
    if (ctx.getProperty().getBase() instanceof PropertyMantainerVM) {
      propertyMantainerVM = (PropertyMantainerVM) ctx.getProperty().getBase();
      
      if (propertyMantainerVM.getCurrentProperty() != null && StringUtils.isNotBlank(propertyMantainerVM.getCurrentProperty().getValor())) {
        validForm = true;
      }
    }
    
    if (!validForm) {
      this.addInvalidMessage(ctx, "invalidField.valor", Labels.getLabel("ee.propertyMantainer.required"));
    }
  }
  
}
