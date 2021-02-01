package com.egoveris.ffdd.render.zk.comp.ext;

import java.text.DecimalFormatSymbols;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Doublebox;

public class DoubleboxExt extends Doublebox implements ConstrInputComponent {

	private static final long serialVersionUID = 75691511327953532L;

	private Integer idComponentForm;
	private MultiConstrData multiConstrData;

	@Override
	public Integer getIdComponentForm() {
		return idComponentForm;
	}

	@Override
	public void setIdComponentForm(Integer idComponentForm) {
		this.idComponentForm = idComponentForm;
	}

	@Override
	protected Object[] toNumberOnly(String val) {

		if (val == null)
			return new Object[] { null, null };

		final DecimalFormatSymbols symbols = new DecimalFormatSymbols(
				Locales.getCurrent());
		final char GROUPING = ',', DECIMAL = '.', PERCENT = symbols
				.getPercent(), PER_MILL = symbols.getPerMill(), // 1/1000
		// not support yet: INFINITY = symbols.getInfinity(), NAN =
		// symbols.getNaN(),
		MINUS = symbols.getMinusSign();
		final String fmt = getFormat();
		StringBuffer sb = null;
		int divscale = 0; // the second element
		boolean minus = false;
		for (int j = 0, len = val.length(); j < len; ++j) {
			final char cc = val.charAt(j);

			boolean ignore = false;
			// We handle percent and (nnn) specially
			if (cc == PERCENT) {
				divscale += 2;
				ignore = true;
			} else if (cc == PER_MILL) {
				divscale += 3;
				ignore = true;
			} else if (cc == '(') {
				minus = true;
				ignore = true;
			} else if (cc == '+') {
				ignore = true;
			}

			// We don't add if cc shall be ignored (not alphanum but in fmt)
			if (!ignore)
				ignore = (cc < '0' || cc > '9')
						&& cc != DECIMAL
						&& cc != MINUS
						&& cc != '+'
						&& (Character.isWhitespace(cc) || cc == GROUPING
								|| cc == ')' || (fmt != null && fmt.indexOf(cc) >= 0));
			if (ignore) {
				if (sb == null)
					sb = new StringBuffer(len).append(val.substring(0, j));
				if (cc == GROUPING) {
					sb.append('.');
				} else {
					if (cc == DECIMAL) {
						sb.append('.');
					}
				}
			} else {
				final char c2 = cc == MINUS ? '-' : cc == DECIMAL ? '.' : cc;
				if (cc != c2) {
					if (sb == null)
						sb = new StringBuffer(len).append(val.substring(0, j));
					sb.append(c2);
				} else if (sb != null) {
					sb.append(c2);
				}
			}
		}
		if (minus) {
			if (sb == null)
				sb = new StringBuffer(val.length() + 1).append(val);
			if (sb.length() > 0) {
				if (sb.charAt(0) == '-') {
					sb.deleteCharAt(0);
				} else {
					sb.insert(0, '-');
				}
			}
		}

		// handle '%'
		if (fmt != null && divscale > 0) {
			l_out: for (int j = 0, k, len = fmt.length(); (k = fmt.indexOf(
					'\'', j)) >= 0;) {
				while (++k < len) {
					final char cc = fmt.charAt(k);
					if (cc == '%')
						divscale -= 2;
					else if (cc == '\u2030')
						divscale -= 3;
					else if (cc == '\'') {
						++k;
						break;
					}
					if (divscale <= 0) {
						divscale = 0;
						break l_out;
					}
				}
				j = k;
			}
		}

		return new Object[] { (sb != null ? sb.toString() : val),
				Integer.valueOf(divscale) };
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
	public boolean addEventListener(String evtnm, EventListener listener) {
		return super.addEventListener(evtnm, listener);
	}
}
