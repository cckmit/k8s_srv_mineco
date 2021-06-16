package com.egoveris.ffdd.render.zk.comp.ext;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Vlayout;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;

public class ComplexComponenLayout extends Vlayout implements ConstrInputComponent {

	private static final String BASE_DIR = "~./";

	private static final String FORM_COMP = "formComp";

	/**
	 * 
	 */
	private static final long serialVersionUID = 8256206181025939956L;

	private Integer idComponentForm;
	private String name;
	private MultiConstrData multiConstrData;
	private Constraint constraint;

	public ComplexComponenLayout(final FormularioComponenteDTO formComp, final String zul) {

		setIdComponentForm(formComp.getId());

		setName(formComp.getNombre());
		setVisible(!Boolean.TRUE.equals(formComp.getOculto()));

		final Map<String, Object> args = new HashMap<>();
		args.put(FORM_COMP, formComp);

		final String zkZul = new StringBuilder().append(BASE_DIR).append(zul).toString();

		final Component child = Executions.createComponents(zkZul, null, args);
		this.appendChild(child);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getIdComponentForm() {
		return idComponentForm;
	}

	@Override
	public void setIdComponentForm(final Integer idComponentForm) {
		this.idComponentForm = idComponentForm;
	}

	@Override
	public Constraint getConstraint() {
		return this.constraint;
	}

	@Override
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	@Override
	public MultiConstrData getMultiConstrData() {
		return multiConstrData;
	}

	@Override
	public void setMultiConstrData(MultiConstrData multiConstrStruct) {
		this.multiConstrData = multiConstrStruct;

	}

	@Override
	public Object getRawValue() {
		return null;
	}

	@Override
	public void setRawValue(Object obj) {
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public boolean isReadonly() {
		return isReadonly();
	}

	@Override
	public void setReadonly(boolean disable) {
		setReadonly(disable);
	}

	@Override
	public boolean addEventListener(String evtnm, EventListener listener) {
		return super.addEventListener(evtnm, listener);
	}

	@Override
	public void clearErrorMessage(boolean revalidateRequired) {
	}
}
