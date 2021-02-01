package com.egoveris.ffdd.render.zk.constr;

import com.egoveris.ffdd.model.exception.DynFormValorComponente;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.ConstraintDTO;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.form.ConditionImpl;
import com.egoveris.ffdd.render.zk.util.ComponentUtils;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Constraint;

public class DynamicConstraint implements Constraint {

	private InputComponent comp;
	private boolean firstCheck;
	private Object errorValue;
	private String errorMessage;
	private ConditionImpl condition;

	
	public DynamicConstraint(InputComponent comp, ConstraintDTO contraintDto, String sufijoRepetitivo) {
		this.comp = comp;
		this.errorMessage = contraintDto.getMensaje();
		initConditions(contraintDto.getCondiciones(), sufijoRepetitivo);
	}
	

	private void initConditions(List<CondicionDTO> condiciones, String sufijoRepetitivo) {
		Component vlayout = ComponentUtils.obtenerVlayoutRoot(this.comp);
		condition = new ConditionImpl(condiciones, vlayout, sufijoRepetitivo);
		addEventListeners();
	}

	private void addEventListeners() {
		for (Component inputComp: condition.getParticipantsComps()) {
			if (inputComp != this.comp) {
				ComponentUtils.addEventListener(inputComp, new ConstraintMember());
			}
		}
	}
	

	@Override
	public void validate(Component comp, Object value)  {	
		if (comp instanceof InputComponent) {
			firstCheck = true;
			if (this.condition.validar((InputComponent) comp, value)) {
				this.errorValue = value;
				throw new WrongValueException(comp, this.errorMessage);
			} else {
				this.errorValue = null;
			}
		} else {
			throw new DynFormValorComponente("Solo se permiten InputComponent");
		}
	}
	
	public class ConstraintMember implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			reValidate();
		}

		public void reValidate() {
			if (firstCheck) {
				comp.clearErrorMessage(true);
				if (errorValue != null) {
					comp.setRawValue(errorValue);
				}
				// validar - internamente hace checkuserError
				comp.getText();
			}
		}
	}
}
