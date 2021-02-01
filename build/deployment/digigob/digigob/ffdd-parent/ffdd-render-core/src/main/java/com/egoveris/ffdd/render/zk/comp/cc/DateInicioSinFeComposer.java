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

public class DateInicioSinFeComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DateInicioSinFeComposer.class);

	Cell fechaDiv;
	InputElement fecha;

	private transient Optional<List<LocalDate>> feriadosOpt;

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

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
		this.fecha.addEventListener(Events.ON_BLUR, e -> settearFecha(feriadosOpt));

	}

	@Override
	protected String getName() {
		return "dateInicioSinFe";
	}

	@Override
	protected void setDefaultValues(final String name) {
	}

	private void settearFecha(Optional<List<LocalDate>> holidays) {
		if (validateFecha(holidays)) {
			throw new WrongValueException(this.fecha,
					"La fecha de inicio no debe ser anterior a más de 3 días corridos de la fecha actual");
		}
	}

	private boolean validateFecha(Optional<List<LocalDate>> holidays) {
		Date fInicioDate = ((Datebox) this.fecha).getValue();
		this.fecha.clearErrorMessage();
		LocalDate hoy = LocalDate.now();

		if (fInicioDate != null && fInicioDate.before(new Date())) {
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
	public class DateInicioListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				settearFecha(feriadosOpt);
			}
		}
	}

}