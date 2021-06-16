
package com.egoveris.ffdd.render.zk.comp.cc;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.render.zk.comp.ext.TimeboxExt;

public class DifHorasComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell horaInicioDiv;
	Cell horaFinDiv;
	Cell diferenciaDiv;

	InputElement horaInicio;
	InputElement horaFin;
	InputElement diferencia;

	static final int MINUTES_PER_HOUR = 60;
	static final int SECONDS_PER_MINUTE = 60;
	static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		comp.addEventListener(Events.ON_NOTIFY, new DifDatesListener());

		diferencia.setDisabled(true);

		this.horaInicio.addEventListener(Events.ON_CHANGE, e -> countBusinessHoursBetween());
		this.horaFin.addEventListener(Events.ON_CHANGE, e -> countBusinessHoursBetween());
	}

	@Override
	protected String getName() {
		return "difHoras";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

	private void countBusinessHoursBetween() {

		Date fInicioHora = ((TimeboxExt) this.horaInicio).getValue();
		Date fFinHora = ((TimeboxExt) this.horaFin).getValue();

		if (fInicioHora != null && fFinHora != null) {
			validateFechas();

			LocalDateTime fInicio = fInicioHora.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			LocalDateTime fFin = fFinHora.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			String[] time = getTime(fInicio, fFin);
			StringBuilder dif = new StringBuilder();
			dif.append(time[0]).append(":").append(time[1]).append(":").append(time[2]);

			diferencia.setRawValue(dif.toString());
		}
	}

	private static String[] getTime(LocalDateTime dob, LocalDateTime now) {
		LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), dob.getHour(),
				dob.getMinute(), dob.getSecond());
		Duration duration = Duration.between(today, now);

		long seconds = duration.getSeconds();

		String hours = String.format("%02d", seconds / SECONDS_PER_HOUR);
		String minutes = String.format("%02d", ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE));
		String secs = String.format("%02d", (seconds % SECONDS_PER_MINUTE));
		return new String[] { hours, minutes, secs };
	}

	private void validateFechas() {
		if (validateFechaFinBefore()) {
			diferencia.setRawValue(null);
			throw new WrongValueException(this.horaFin, "La hora de fin no debe ser anterior a la hora de inicio");
		}
	}

	private boolean validateFechaFinBefore() {
		return ((TimeboxExt) this.horaFin).getValue().before(((TimeboxExt) this.horaInicio).getValue());
	}

	@SuppressWarnings("rawtypes")
	public class DifDatesListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				countBusinessHoursBetween();
			}
		}

	}
}