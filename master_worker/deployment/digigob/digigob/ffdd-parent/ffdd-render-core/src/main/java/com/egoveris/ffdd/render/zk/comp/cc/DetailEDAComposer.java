
package com.egoveris.ffdd.render.zk.comp.cc;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class DetailEDAComposer extends ComplexComponentComposer {

	Cell cmdDiv;

	Cell cm2Div;

	Cell cmnDiv;

	Cell orDiv;

	Cell qn1Div;

	Cell qt1Div;

	Cell qn2Div;

	Cell qt2Div;

	Cell cmpDiv;

	Cell bpcDiv;

	Cell bpkDiv;

	Cell ectDiv;

	Cell eacDiv;

	InputElement cmd;

	InputElement cm2;

	InputElement cmn;

	InputElement or;

	InputElement qn1;

	InputElement qt1;

	InputElement qn2;

	InputElement qt2;

	InputElement cmp;

	InputElement bpc;

	InputElement bpk;

	InputElement ect;

	InputElement eac;

	@Override
	protected void setDefaultValues(final String repeat) {

		if (!repeat.contains("R")) {
			this.cmd.setText("0900000011");
			this.cm2.setText("1");
			this.cmn.setText("AAAA");
			this.or.setText("JP");
			this.qn1.setText("1");
			this.qt1.setText("NO");
			this.qn2.setText("1");
			this.qt2.setText("KG");
			this.cmp.setText("123456789012345678");
			this.bpc.setText("MMK");
			this.bpk.setText("1000");
			this.ect.setText("0006");
			this.eac.setText("");
			;
		}
		if (repeat.contains("R1")) {
			this.cmd.setText("0900000011");
			this.cm2.setText("1");
			this.cmn.setText("AAAA");
			this.or.setText("JP");
			this.qn1.setText("1");
			this.qt1.setText("NO");
			this.qn2.setText("1");
			this.qt2.setText("KG");
			this.cmp.setText("123456789012345678");
			this.bpc.setText("MMK");
			this.bpk.setText("2000");
			this.ect.setText("0005");
			this.eac.setText("22");
			;
		}
		if (repeat.contains("R2")) {
			this.cmd.setText("0100000001");
			this.cm2.setText("3");
			this.cmn.setText("AAAA");
			this.or.setText("JP");
			this.qn1.setText("1");
			this.qt1.setText("NO");
			this.qn2.setText("1");
			this.qt2.setText("KG");
			this.cmp.setText("123456789012345678");
			this.bpc.setText("");
			this.bpk.setText("");
			this.ect.setText("0005");
			this.eac.setText("");
			;
		}
		if (repeat.contains("R3")) {
			this.cmd.setText("0100000001");
			this.cm2.setText("3");
			this.cmn.setText("AAAA");
			this.or.setText("JP");
			this.qn1.setText("1");
			this.qt1.setText("NO");
			this.qn2.setText("1");
			this.qt2.setText("KG");
			this.cmp.setText("123456789012345678");
			this.bpc.setText("MMK");
			this.bpk.setText("40000");
			this.ect.setText("0005");
			this.eac.setText("77");
			;
		}
	}

}
