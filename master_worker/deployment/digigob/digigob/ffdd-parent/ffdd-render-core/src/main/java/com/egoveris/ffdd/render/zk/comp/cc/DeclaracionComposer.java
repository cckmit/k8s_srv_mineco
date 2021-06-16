
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.impl.InputElement;

public class DeclaracionComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell declarationIdDiv;

	Cell declaracionEnumDiv;

	Cell submittedByDiv;

	Cell submittedDateDiv;

	Cell respDateDiv;

	Cell responseStatusDiv;

	Cell responseCodeDiv;

	Cell uRLDiv;

	Cell fechaVencimientoDiv;

	Cell tipoFormularioDiv;

	Cell codigoDeclaracionDiv;

	Cell numeroIdentificacionDiv;
	Cell indicaDocumentoParcialDiv;
	Cell listObservacionesDiv;

	InputElement declarationId;

	InputElement declaracionEnum;

	InputElement submittedBy;

	InputElement submittedDate;

	InputElement respDate;

	InputElement responseStatus;

	InputElement responseCode;

	InputElement uRL;

	InputElement fechaVencimiento;

	InputElement tipoFormulario;

	InputElement codigoDeclaracion;

	InputElement numeroIdentificacion;
	InputElement indicaDocumentoParcial;
	SeparatorComplex listObservaciones;

	@Override
	protected String getName() {
		return "declaracion";
	}

	@Override
	protected void setDefaultValues(final String name) {

		this.declarationId.setText("15");
		((Combobox) this.declaracionEnum).setSelectedIndex(0);
		this.submittedBy.setText("d");
		final DateFormat d = new SimpleDateFormat("ddmmyyyy");
		try {
			this.submittedDate.setRawValue(d.parse("10042017"));
			this.respDate.setRawValue(d.parse("10042017"));
		} catch (final ParseException e) {

		}
		this.responseStatus.setText("sdf");
		this.responseCode.setText("ihi");
		this.uRL.setText("skjf");
	}

}
