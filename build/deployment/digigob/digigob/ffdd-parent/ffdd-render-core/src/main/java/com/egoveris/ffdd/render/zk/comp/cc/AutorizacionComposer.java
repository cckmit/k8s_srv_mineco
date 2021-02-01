
package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class AutorizacionComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell idAutorizacionDiv;
	Cell submittedDateDiv;
	Cell responseStatusDiv;
	Cell vigenciaDateDiv;
	Cell uRLDiv;
	Cell submittedByDiv;
	Cell respDateDiv;
	Cell responseCodeDiv;
	Cell documentoDiv;
	Cell listObservacionesDiv;
	Cell ssppEnumDiv;

	InputElement idAutorizacion;
	InputElement submittedDate;
	InputElement responseStatus;
	InputElement vigenciaDate;
	InputElement uRL;
	InputElement submittedBy;
	InputElement respDate;
	InputElement responseCode;
	SeparatorComplex documento;
	SeparatorComplex listObservaciones;
	InputElement ssppEnum;

	@Override
	protected String getName() {
		return "autorizacion";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.idAutorizacion.setText("12");
		this.ssppEnum.setText("DD");
		this.submittedBy.setText("EE");
		final DateFormat d = new SimpleDateFormat("ddmmyyyy");
		try {
			this.submittedDate.setRawValue(d.parse("07042017"));
			this.vigenciaDate.setRawValue(d.parse("07042017"));
			this.respDate.setRawValue(d.parse("07042017"));
		} catch (final ParseException e) {

		}
		this.responseStatus.setText("HH");
		this.responseCode.setText("II");
		this.uRL.setText("LL");
	}

}
