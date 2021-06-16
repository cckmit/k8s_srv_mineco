
package com.egoveris.ffdd.render.zk.comp.cc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class CommonEDAComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8989623688678301110L;

	Cell ecnDiv;

	Cell ecbDiv;

	Cell ic2Div;

	Cell ntcDiv;

	Cell skbDiv;

	Cell mtcDiv;

	Cell chDiv;

	//
	// Cell chbDiv;
	//
	// Cell ecdDiv;
	//
	Cell emcDiv;
	//
	Cell emnDiv;
	//
	Cell emaDiv;
	//
	Cell emyDiv;
	//
	Cell emtDiv;
	//
	Cell stDiv;
	//
	Cell eccDiv;
	//
	// Cell cgcDiv;
	//
	Cell cgnDiv;
	//
	Cell cgpDiv;
	//
	Cell cgaDiv;
	//
	Cell cgkDiv;
	//
	// Cell eknDiv;
	//
	Cell awbDiv;
	//
	Cell noDiv;
	//
	// Cell notDiv;
	//
	Cell gwDiv;
	//
	// Cell gwtDiv;
	//
	// Cell nwDiv;
	//
	// Cell nwtDiv;
	//
	// Cell mrkDiv;
	//
	// Cell vynDiv;
	//
	// Cell vscDiv;
	//
	Cell vsnDiv;
	//
	Cell symDiv;
	//
	Cell pscDiv;
	//
	// Cell viaDiv;
	//
	// Cell vinDiv;
	//
	Cell dscDiv;
	//
	// Cell dsnDiv;
	//
	// Cell cocDiv;
	//
	// Cell icoDiv;
	//
	// Cell chkDiv;
	//
	// Cell iv1Div;
	//
	// Cell iv2Div;
	//
	// Cell iv3Div;
	//
	Cell ip1Div;
	Cell ip2Div;

	Cell ip3Div;

	Cell ip4Div;
	//
	// Cell fcdDiv;
	//
	// Cell fkkDiv;
	//
	// Cell tpDiv;
	//
	// Cell pfDiv;
	//
	// Cell vcDiv;
	//
	// Cell vnDiv;
	//
	// Cell vaDiv;
	//
	// Cell nt1Div;
	//
	// Cell nt2Div;
	//
	// Cell refDiv;
	//
	// Cell ecaDiv;
	//
	// Cell eaaDiv;
	//
	// Cell dpsDiv;
	//
	// Cell prcDiv;
	//
	// Cell fgrDiv;
	//
	// Cell tsrDiv;

	Cell lcDiv;

	Cell lcnDiv;

	Cell lcdDiv;

	Cell banDiv;

	Cell autDiv;

	Cell otcDiv;
	//
	// Cell sbiDiv;
	//
	// Cell hkeDiv;
	//
	// Cell kbnDiv;
	//
	// Cell tjcDiv;
	//
	// Cell horDiv;

	InputElement otc;

	InputElement ecn;

	InputElement ecb;

	InputElement ic2;

	InputElement ntc;

	InputElement skb;

	InputElement mtc;

	InputElement ch;

	//
	// InputElement chb;
	//
	// InputElement ecd;
	//
	InputElement emc;
	//
	InputElement emn;
	//
	InputElement ema;
	//
	InputElement emy;
	//
	InputElement emt;
	//
	InputElement st;
	//
	InputElement ecc;
	//
	// InputElement cgc;
	//
	InputElement cgn;
	//
	InputElement cgp;
	//
	InputElement cga;
	//
	InputElement cgk;
	// InputElement ekn;
	//
	InputElement awb;

	//
	InputElement no;
	//
	// InputElement not;
	//
	InputElement gw;
	//
	// InputElement gwt;
	//
	// InputElement nw;
	//
	// InputElement nwt;
	//
	// InputElement mrk;
	//
	// InputElement vyn;
	//
	// InputElement vsc;
	//
	InputElement vsn;
	//
	InputElement sym;
	//
	InputElement psc;
	//
	// InputElement via;
	//
	// InputElement vin;
	//
	InputElement dsc;
	//
	// InputElement dsn;
	//
	// InputElement coc;
	//
	// InputElement ico;
	//
	// InputElement chk;
	//
	// InputElement iv1;
	//
	// InputElement iv2;
	//
	// InputElement iv3;
	//
	InputElement ip1;
	InputElement ip2;

	InputElement ip3;

	InputElement ip4;

	//
	// InputElement fcd;
	//
	// InputElement fkk;
	//
	// InputElement tp;
	//
	// InputElement pf;
	//
	// InputElement vc;
	//
	// InputElement vn;
	//
	// InputElement va;
	//
	// InputElement nt1;
	//
	// InputElement nt2;
	//
	// InputElement ref;
	//
	// InputElement eca;
	//
	// InputElement eaa;
	//
	// InputElement dps;
	//
	// InputElement prc;
	//
	// InputElement fgr;
	//
	// InputElement tsr;
	//
	InputElement lc;
	InputElement lcn;

	InputElement lcd;

	InputElement ban;

	InputElement aut;
	//
	// InputElement sbi;
	//
	// InputElement hke;
	//
	// InputElement kbn;
	//
	// InputElement tjc;
	//
	// InputElement hor;

	@Override
	protected String getName() {
		return "commonEda";
	}

	@Override
	protected void setDefaultValues(final String name) {
		this.ecb.setRawValue("D");
		this.ic2.setText("B");
		this.skb.setText("1");
		this.mtc.setText("4");
		this.ch.setText("A1");
		this.emc.setText("C000000030000");
		this.emn.setText("D");
		this.ema.setText("D");
		this.emy.setText("D");
		this.emt.setText("123002");
		this.st.setText("A3W231");
		this.ecc.setText("10018");
		this.cgn.setText("A");
		this.cgp.setText("A");
		this.cga.setText("A");
		this.cgk.setText("JP");
		this.awb.setText("TOM2016070801");
		this.no.setText("100");
		this.gw.setText("100.0");
		this.vsn.setText("JL1001/06MAY");
		final DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			this.sym.setRawValue(df.parse("20160926"));
		} catch (final ParseException e) {

		}
		this.psc.setText("MMRGN");
		this.dsc.setText("USLAX");
		this.ip1.setText("A");
		this.ip2.setText("FOB");
		this.ip3.setText("MMK");
		this.ip4.setText("10000000");
		this.lc.setText("T/T");
		this.lcn.setText("TT01234567890123456789012");
		this.lcd.setText("20160630");
		this.ban.setText("BNKNO11111222223333344444");
		this.aut.setText("AGREE");
		this.otc.setText("MF00");
	}

}
