package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.base.util.UtilsDate;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.produccion.AgregarReparticionesComposer;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ReparticionesItemRenderer implements ListitemRenderer {

	private static final String ESTILO_TODAS = "font-weight: bold; color: black";
	private static final String FIRMAR = "Firmar";
	private static final String INICIAR = "Iniciar";

	private AgregarReparticionesComposer composer;

	public ReparticionesItemRenderer(AgregarReparticionesComposer composer) {
		super();
		this.composer = composer;
	}

	public ReparticionesItemRenderer() {

	}

	public void render(Listitem item, Object data, int arg2) throws Exception {

		ReparticionHabilitadaDTO reparticionHabilitada = (ReparticionHabilitadaDTO) data;
		Set<ReparticionHabilitadaDTO> auxReparticionesHabilitadas = this.composer.getAuxReparticionesHabilitadas();

		// Solo se le presentan al usuario los registros de reparticiones
		// activas
		Listcell reparticionCodigo = new Listcell(reparticionHabilitada.getCodigoReparticion());
		reparticionCodigo.setParent(item);
		Checkbox permisoIniciar = new Checkbox();
		permisoIniciar.setChecked(reparticionHabilitada.getPermisoIniciar());
		Listcell permisoIniciarCell = new Listcell();
		permisoIniciar.setParent(permisoIniciarCell);
		permisoIniciarCell.setParent(item);
		Checkbox permisoFirmar = new Checkbox();
		permisoFirmar.setChecked(reparticionHabilitada.getPermisoFirmar());
		Listcell permisoFirmarCell = new Listcell();
		permisoFirmar.setParent(permisoFirmarCell);
		permisoFirmarCell.setParent(item);
		Listcell ultimoNumero = new Listcell();
		Listcell anioReparticion = new Listcell();
		if (reparticionHabilitada.getTipoDocumento().getEsEspecial() && reparticionHabilitada.getCodigoReparticion()
				.compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) != 0) {
			// Si el tipo de documento tiene numeración especial, se validan las
			// condiciones para permitir editar dicha información.
			if (reparticionHabilitada.getPermisoFirmar().booleanValue()
					&& !existeReparticion(reparticionHabilitada, auxReparticionesHabilitadas)) {
				mostrarCampos(reparticionHabilitada, ultimoNumero, anioReparticion);
			} else if (existeReparticion(reparticionHabilitada, auxReparticionesHabilitadas)
					&& reparticionHabilitada.getPermisoFirmar().booleanValue()) {
				if (reparticionHabilitada.getNumeracionEspecial().getNumero() == null
						|| (reparticionHabilitada.getNumeracionEspecial().getNumero() != null
								&& reparticionHabilitada.getNumeracionEspecial().getNumero()
										.equals(reparticionHabilitada.getNumeracionEspecial().getNumeroInicial()))) {
					mostrarCampos(reparticionHabilitada, ultimoNumero, anioReparticion);
					// Remuevo la Reparticion para que no se tenga en cuenta al
					// momento de las valdiciones
					auxReparticionesHabilitadas.remove(reparticionHabilitada);
				} else {
					ultimoNumero.setLabel(reparticionHabilitada.getNumeracionEspecial().getNumero().toString());
					anioReparticion.setLabel(reparticionHabilitada.getNumeracionEspecial().getAnio());
				}
			} else {
				ultimoNumero.setLabel(StringUtils.EMPTY);
				anioReparticion.setLabel(StringUtils.EMPTY);
			}
		}
		ultimoNumero.setParent(item);
		anioReparticion.setParent(item);

		Listcell currentCell = new Listcell();
		currentCell.setParent(item);
		if (reparticionHabilitada.getCodigoReparticion().compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) == 0) {
			reparticionCodigo.setStyle(ESTILO_TODAS);
			reparticionCodigo.setLabel(Constantes.NOMBRE_REPARTICION_TODAS);
			if (reparticionHabilitada.getTipoDocumento().getEsEspecial()) {
				permisoFirmar.setDisabled(true);
				permisoFirmar.setChecked(false);
			}
		}
		permisoIniciar.addEventListener(Events.ON_CHECK,
				new ReparticionesHabilitadasListener(this.composer, reparticionHabilitada, INICIAR));
		permisoFirmar.addEventListener(Events.ON_CHECK,
				new ReparticionesHabilitadasListener(this.composer, reparticionHabilitada, FIRMAR));
	}

	private void mostrarCampos(ReparticionHabilitadaDTO reparticionHabilitada, Listcell ultimoNumero,
			Listcell anioReparticion) {

		Intbox textBoxNumero = reparticionHabilitada.getNumeracionEspecial().getNumero() != null
				? new Intbox(reparticionHabilitada.getNumeracionEspecial().getNumero()) : new Intbox(0);
		textBoxNumero.setReadonly(false);
		textBoxNumero.setMaxlength(8);
		textBoxNumero.setWidth("40px");
		textBoxNumero.setParent(ultimoNumero);
		textBoxNumero.setDisabled(false);
		Intbox textBoxAnio = reparticionHabilitada.getNumeracionEspecial().getAnio() != null
				? new Intbox(Integer.valueOf(reparticionHabilitada.getNumeracionEspecial().getAnio()))
				: new Intbox(Integer.valueOf(UtilsDate.obtenerAnioActual()));
		textBoxAnio.setReadonly(false);
		textBoxAnio.setMaxlength(6);
		textBoxAnio.setWidth("40px");
		textBoxAnio.setParent(anioReparticion);
		textBoxAnio.setDisabled(true);
		textBoxNumero.addEventListener("onChange",
				new ReparticionesHabilitadasListener(this.composer, reparticionHabilitada, "numeroEspecial"));
		textBoxAnio.addEventListener("onChange",
				new ReparticionesHabilitadasListener(this.composer, reparticionHabilitada, "anio"));
	}

	private boolean existeReparticion(ReparticionHabilitadaDTO reparticionABuscar,
			Set<ReparticionHabilitadaDTO> reparticiones) {
		boolean existeReparticion = false;
		for (Iterator<ReparticionHabilitadaDTO> iterator = reparticiones.iterator(); iterator.hasNext();) {
			ReparticionHabilitadaDTO reparticionHabilitadaAux = iterator.next();
			if (reparticionHabilitadaAux.getId().equals(reparticionABuscar.getId())) {
				existeReparticion = true;
				break;
			}
		}
		return existeReparticion;
	}
}

/**
 * Listener que escucha los eventos onCheck, para los checkBox del listBox de
 * reparticionesHabilitadas.
 * 
 * @author kmarroqu
 *
 */

final class ReparticionesHabilitadasListener implements EventListener {
	private AgregarReparticionesComposer composer;
	private ReparticionHabilitadaDTO reparticionHabilitada;
	private String identificadorComponente;
	private static final String FIRMAR = "Firmar";
	private static final String INICIAR = "Iniciar";

	public ReparticionesHabilitadasListener(AgregarReparticionesComposer composer,
			ReparticionHabilitadaDTO reparticionHabilitada, String identificadorComponente) {
		super();
		this.composer = composer;
		this.reparticionHabilitada = reparticionHabilitada;
		this.identificadorComponente = identificadorComponente;
	}

	public void onEvent(Event event) throws Exception {
		Set<ReparticionHabilitadaDTO> reparticiones = this.composer.getReparticionesHabilitadas();
		if (event.getName().equals(Events.ON_CHECK)) {
			Checkbox checkBox = (Checkbox) event.getTarget();
			for (ReparticionHabilitadaDTO reparticion : reparticiones) {
				if (reparticion.getCodigoReparticion().trim()
						.compareTo(reparticionHabilitada.getCodigoReparticion().trim()) == 0) {
					if (identificadorComponente.compareTo(INICIAR) == 0) {
						reparticion.setPermisoIniciar(checkBox.isChecked());
					}
					if (identificadorComponente.compareTo(FIRMAR) == 0) {
						reparticion.setPermisoFirmar(checkBox.isChecked());
					}
					if (identificadorComponente.compareTo("Habilitar") == 0) {
						reparticion.setEstado(checkBox.isChecked());
					}
					if (reparticion.getCodigoReparticion().compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) == 0) {
						if (checkBox.isChecked()) {
							checkTodas(identificadorComponente, reparticiones);
						} else {
							unCheckTodas(identificadorComponente, reparticiones);
						}
					} else if (!checkBox.isChecked()) {
						actualizarCheckReparticionTodas(identificadorComponente, reparticiones);
					}
					break;
				}
			}
			this.composer.refreshListboxReparticionesAgregadas();
		}
		if (event.getName().equals(Events.ON_CLICK)) {
			// Desactivación de la repartición en el listado de reparticiones
			// habilitadas
			for (ReparticionHabilitadaDTO reparticion : reparticiones) {
				if (reparticion.getCodigoReparticion().trim()
						.compareTo(reparticionHabilitada.getCodigoReparticion().trim()) == 0) {
					reparticion.setEstado(false);
					this.composer.refreshListboxReparticionesAgregadas();
					break;
				}
			}
		}
		if (event.getName().equals(Events.ON_CHANGE)) {
			Intbox textBox = (Intbox) event.getTarget();
			if (textBox.getValue() != null) {
				// Desactivación de la repartición en el listado de
				// reparticiones habilitadas
				for (ReparticionHabilitadaDTO reparticion : reparticiones) {
					if (reparticion.getCodigoReparticion().trim()
							.compareTo(reparticionHabilitada.getCodigoReparticion().trim()) == 0) {
						if (identificadorComponente.compareTo("numeroEspecial") == 0) {
							if (reparticion.getNumeracionEspecial().getAnio() != null) {
								reparticion.getNumeracionEspecial()
										.setAnio(reparticionHabilitada.getNumeracionEspecial().getAnio());
							} else {
								reparticion.getNumeracionEspecial().setAnio(UtilsDate.obtenerAnioActual());
							}
							reparticion.getNumeracionEspecial().setNumero(textBox.getValue());
						}
						if (identificadorComponente.compareTo("anio") == 0) {
							if (reparticionHabilitada.getNumeracionEspecial() != null) {
								reparticion.setNumeracionEspecial(reparticionHabilitada.getNumeracionEspecial());
							} else {
								reparticion.getNumeracionEspecial().setNumero(0);
							}
							reparticion.getNumeracionEspecial().setAnio(Integer.toString(textBox.getValue()));
						}
						if (this.composer.getReparticionesIncompletas()
								.contains(reparticionHabilitada.getCodigoReparticion())
								&& reparticionHabilitada.getNumeracionEspecial().getAnio() != null
								&& reparticionHabilitada.getNumeracionEspecial() != null) {
							this.composer.getReparticionesIncompletas()
									.remove(reparticionHabilitada.getCodigoReparticion());
						}
						break;
					}
				}

				if (textBox.getValue() == null) {
					this.composer.getGuardar().setDisabled(true);
					if (!this.composer.getReparticionesIncompletas()
							.contains(reparticionHabilitada.getCodigoReparticion()))
						this.composer.getReparticionesIncompletas().add(reparticionHabilitada.getCodigoReparticion());
					throw new WrongValueException(textBox, "Seleccione un valor");
				}
			}
		}
	}

	/**
	 * Limpia la selección del permiso indicado.
	 * 
	 * @param permiso
	 * @param reparticiones
	 */
	private void checkTodas(String permiso, Set<ReparticionHabilitadaDTO> reparticiones) {
		for (ReparticionHabilitadaDTO reparticion : reparticiones) {
			if (reparticion.getCodigoReparticion().compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) != 0) {
				if (permiso.compareTo(INICIAR) == 0) {
					reparticion.setPermisoIniciar(true);
				}
				if (permiso.compareTo(FIRMAR) == 0) {
					reparticion.setPermisoFirmar(true);
				}
			}
		}
	}

	/**
	 * Limpia la selección del permiso indicado.
	 * 
	 * @param permiso
	 * @param reparticiones
	 */
	private void unCheckTodas(String permiso, Set<ReparticionHabilitadaDTO> reparticiones) {
		for (ReparticionHabilitadaDTO reparticion : reparticiones) {
			if (reparticion.getCodigoReparticion().compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) != 0) {
				if (permiso.compareTo(INICIAR) == 0) {
					reparticion.setPermisoIniciar(false);
				}
				if (permiso.compareTo(FIRMAR) == 0) {
					reparticion.setPermisoFirmar(false);
				}
			}
		}
	}

	/**
	 * Limpia la selección del check todas si al menos una de las reparticiones
	 * tiene habilitado el permiso indicado
	 * 
	 * @param permiso
	 * @param reparticiones
	 */
	private void actualizarCheckReparticionTodas(String permiso, Set<ReparticionHabilitadaDTO> reparticiones) {
		for (ReparticionHabilitadaDTO reparticion : reparticiones) {
			if (reparticion.getCodigoReparticion().compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) == 0) {
				if (permiso.compareTo(INICIAR) == 0 && reparticion.getPermisoIniciar()) {
					reparticion.setPermisoIniciar(false);
				}
				if (permiso.compareTo(FIRMAR) == 0 && reparticion.getPermisoFirmar()) {
					reparticion.setPermisoFirmar(false);
				}
			}
		}
	}
}