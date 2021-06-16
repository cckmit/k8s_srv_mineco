
package com.egoveris.ffdd.render.zk.comp.cc;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.impl.InputElement;

public class DifDatesSinFeriadosComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell fechaInicioDiv;
	Cell fechaFinDiv;
	Cell diferenciaDiv;

	InputElement fechaInicio;
	InputElement fechaFin;
	InputElement diferencia;

	private transient Optional<List<LocalDate>> feriadosOpt;

	private static final Logger LOGGER = LoggerFactory.getLogger(DifDatesSinFeriadosComposer.class);

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		comp.addEventListener(Events.ON_NOTIFY, new DifDatesListener());

		diferencia.setDisabled(true);

		List<LocalDate> feriados = null;

		try {

			Object service = SpringUtil.getBean("externalFeriadoService");

			Method method = service.getClass().getMethod("obtenerFeriados");

			feriados = ((List<Date>) method.invoke(service)).stream().map(input -> {
				Instant instant = input.toInstant();
				ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
				return zdt.toLocalDate();
			}).collect(Collectors.toList());

		} catch (Exception e) {
			LOGGER.error("Error al cargar los feriados: " + e.getMessage(), e);
			throw new WrongValueException("No se pudo cargar los feriados para calcular los días hábiles");

		}

		feriadosOpt = Optional.of(feriados);

		this.fechaInicio.addEventListener(Events.ON_CHANGE, e -> countBusinessDaysBetween(feriadosOpt));
		this.fechaFin.addEventListener(Events.ON_CHANGE, e -> countBusinessDaysBetween(feriadosOpt));
	}

	@Override
	protected String getName() {
		return "difDatesSinFeriados";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

	private void countBusinessDaysBetween(Optional<List<LocalDate>> holidays) {

		Date fInicioDate = ((Datebox) this.fechaInicio).getValue();
		Date fFinDate = ((Datebox) this.fechaFin).getValue();

		if (fInicioDate != null && fFinDate != null) {
			validateFechas(holidays);

			LocalDate fInicio = fInicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fFin = fFinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;
			long daysBetween = ChronoUnit.DAYS.between(fInicio.atStartOfDay(), fFin.atStartOfDay());
			long businessDays = Stream.iterate(fInicio, date -> date.plusDays(1)).limit(daysBetween + 1)
					.filter(isHoliday.negate()).count();

			diferencia.setRawValue(businessDays);
		}
	}

	private void validateFechas(Optional<List<LocalDate>> holidays) {
		if (validateFechaFinBefore()) {
			diferencia.setRawValue(null);
			throw new WrongValueException(this.fechaFin, "La fecha de fin no debe ser anterior a la fecha de inicio");
		} else if (validateFechaInicioAfter()) {
			diferencia.setRawValue(null);
			throw new WrongValueException(this.fechaInicio,
					"La fecha de inicio no debe ser posterior a la fecha de fin");
		} else if (validateFechaInicio(holidays)) {
			diferencia.setRawValue(null);
			throw new WrongValueException(this.fechaInicio,
					"La fecha de inicio no debe ser anterior a más de 3 días corridos de la fecha actual");
		}
	}

	private boolean validateFechaInicioAfter() {
		return ((Datebox) this.fechaInicio).getValue().after(((Datebox) this.fechaFin).getValue());
	}

	private boolean validateFechaFinBefore() {
		return ((Datebox) this.fechaFin).getValue().before(((Datebox) this.fechaInicio).getValue());
	}

	private boolean validateFechaInicio(Optional<List<LocalDate>> holidays) {
		Date fInicioDate = ((Datebox) this.fechaInicio).getValue();
		LocalDate hoy = LocalDate.now();

		if (fInicioDate.before(new Date())) {
			LocalDate fInicio = fInicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;
			long daysBetween = ChronoUnit.DAYS.between(fInicio.atStartOfDay(), hoy.atStartOfDay());
			long businessDays = Stream.iterate(fInicio, date -> date.plusDays(1)).limit(daysBetween)
					.filter(isHoliday.negate()).count();
			return businessDays > 3;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public class DifDatesListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				countBusinessDaysBetween(feriadosOpt);
			}
		}

	}
}