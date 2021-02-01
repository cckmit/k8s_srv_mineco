package com.egoveris.ffdd.render.zk.comp.ext;

import com.egoveris.ffdd.model.exception.DynFormValorComponente;
import com.egoveris.ffdd.model.model.ConstraintDTO;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.constr.AppliContext;
import com.egoveris.ffdd.render.zk.constr.DynamicConstraint;
import com.egoveris.ffdd.render.zk.constr.ExtendedConstraint;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Constraint;

public class MultiConstrData {

	private String serviceConstraint;
	private boolean noEmpty;
	private String constraintEL;
	private List<ConstraintDTO> constraintDTOList;
	private List<Constraint> constraintList = new ArrayList<Constraint>();

	public String getServiceConstraint() {
		return serviceConstraint;
	}

	public void setServiceConstraint(String serviceConstraint) {
		this.serviceConstraint = serviceConstraint;
	}

	public boolean isNoEmpty() {
		return noEmpty;
	}

	public void setNoEmpty(boolean noEmpty) {
		this.noEmpty = noEmpty;
	}

	public String getConstraintEL() {
		return constraintEL;
	}

	public void setConstraintEL(String constraintEL) {
		this.constraintEL = constraintEL;
	}

	public List<ConstraintDTO> getConstraintDTOList() {
		return constraintDTOList;
	}

	public void setConstraintDTOList(List<ConstraintDTO> constraintList) {
		this.constraintDTOList = constraintList;
	}

	public List<Constraint> getConstraintList() {
		return constraintList;
	}

	public void setConstraintList(List<Constraint> constraintList) {
		this.constraintList = constraintList;
	}

	public Constraint buildConstraint(InputComponent comp) {
		return buildConstraint(comp, null);
	}
	
	public Constraint buildConstraint(InputComponent comp, String sufijoRep) {

		ExtendedConstraint constr = null;
		if (constraintEL != null) {
			constr = new ExtendedConstraint(constraintEL);
		} else {
			constr = new ExtendedConstraint(0);
		}

		if (noEmpty) {
			constr.setFlags(constr.getFlags() | ExtendedConstraint.NO_EMPTY);
		}
		
		if (constraintList != null) {
			for (Constraint constraint : constraintList) {
				constr.getExtraConstraints().add(constraint);
			}
		}
		
		
		if (serviceConstraint != null) {
			Constraint constrService = getServiceBean(serviceConstraint);
			constr.getExtraConstraints().add(constrService);

		}

		
		if (constraintDTOList != null) {
			for (ConstraintDTO constraintDTO : constraintDTOList) {
				constr.getExtraConstraints().add(new DynamicConstraint(comp, constraintDTO, sufijoRep));
			}
		}

		return constr;
	}

	private Constraint getServiceBean(String service) {
		Constraint constr = (Constraint) AppliContext.getApplicationContext().getBean(service);
		if (constr != null) {
			return constr;
		}
		throw new DynFormValorComponente("Error. No encuentra el servicio para la constraint");
	}
}