package com.egoveris.ffdd.render.zk.form;

import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.render.model.InputComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;

public class ConditionImpl implements Condition {
	
	protected String sufijoRepetitivo;
	protected Condition condition;
	private Set<Component> participantsComps;

	public ConditionImpl(List<CondicionDTO> listCondDTO, Component vlayout) {
		this(listCondDTO, vlayout, null);
	}
	
	public ConditionImpl(List<CondicionDTO> listCondDTO, Component vlayout, String sufijoRepetitivo) {
		this.sufijoRepetitivo = sufijoRepetitivo;
		this.participantsComps = new HashSet<Component>();
		buildConditions(listCondDTO, vlayout);
	}
	
	private void buildConditions(List<CondicionDTO> listCondDTO, Component vlayout) {
		for (CondicionDTO condicionDTO : listCondDTO) {
			buildCondition(condicionDTO, vlayout);
		}
	}
	
	private void buildCondition(CondicionDTO condicionDTO, Component vlayout) {
		SimpleConditionImpl conditionSimple = new SimpleConditionImpl(condicionDTO, vlayout, sufijoRepetitivo);
		addParticipantsComps(conditionSimple);
		if (this.condition != null && condicionDTO.isAnd()) {
			this.condition = new AndOp(this.condition, conditionSimple);
		} else if (this.condition != null && condicionDTO.isOr()) {
			this.condition = new OrOp(this.condition, conditionSimple);
		} else {
			this.condition = conditionSimple;
		}
	}

	private void addParticipantsComps(SimpleConditionImpl conditionSimple){
		addParticipantsComps( conditionSimple.getInputNombreComponente());
		addParticipantsComps( conditionSimple.getInputNombreCompComparacion());
	}

	private void addParticipantsComps(Component inputComponente) {
		if (inputComponente != null) {
			participantsComps.add(inputComponente);
		}
	}

	public Set<Component> getParticipantsComps() {
		return participantsComps;
	}

	
	@Override
	public boolean validar() {
		return validar(null, null);
	}
	
	@Override
	public boolean validar(InputComponent comp, Object value) {
		return this.condition.validar(comp, value);
	}

}
