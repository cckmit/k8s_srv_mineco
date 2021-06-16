package com.egoveris.te.base.composer;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.egoveris.edt.ws.service.IExternalPeriodoLicenciaService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

/**
 * The Class ValidarApoderamientoComposer.
 *
 * @author everis
 */
@SuppressWarnings("rawtypes")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class ValidarApoderamientoComposer extends EEGenericForwardComposer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9100200629195367462L;

	@WireVariable("externalPeriodoLicenciaService")
	private IExternalPeriodoLicenciaService periodoLicenciaService;

	/** The Constant FUNCION. */
	private static final String FUNCION = "funcion";

	/**
	 * Este metodo valida si el usuario seleccionado para realizar una tarea
	 * esta de licencia. Si esta de licencia válida que el usuario apoderado
	 * pertenezca a la misma repartición del usuario generador.
	 * 
	 * @param usuarioProductor
	 * @throws InterruptedException
	 */
	@SuppressWarnings({ "unchecked" })
	protected void validarApoderamiento(final Usuario usuarioProductor)
			throws InterruptedException {

		boolean licenciaActiva = periodoLicenciaService.licenciaActiva(usuarioProductor.getUsername());
		Usuario datosApoderado = null;

		if (licenciaActiva) {
			String strApoderado = periodoLicenciaService.obtenerApoderadoUsuarioLicencia(usuarioProductor.getUsername());

			if (strApoderado != null) {
				datosApoderado = usuarioService.obtenerUsuario(strApoderado);
			}
		}

		final Usuario usuarioApoderado = datosApoderado;
		Clients.clearBusy();

		// Consulto si el usuario al que envío a producir el documento
		// se encuentra de vacaciones.
		// Si esta de vacaciones pido confirmación de reasignación de tarea
		if (licenciaActiva && usuarioApoderado != null) {
			Messagebox.show(
					Labels.getLabel("ee.licencia.question.value",
							new String[] { usuarioProductor.getUsername(), usuarioApoderado.getUsername().toUpperCase() }) + "\n",
					Labels.getLabel("ee.licencia.question.title"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws Exception {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								Map<String, Object> datos = new HashMap<>();
								datos.put(FUNCION, "validarReparticion");
								datos.put("datos", usuarioApoderado);
								enviarBloqueoPantalla(datos);
								break;
							default:
								break;
							}
						}
					});
		} else if (licenciaActiva) {
			// El usuario esta de licencia, pero los apoderados tambien estan de
			// licencia
			Messagebox.show(
					Labels.getLabel("ee.licencia.sinApoderado.value", new String[] { usuarioProductor.getUsername() }),
					Labels.getLabel("ee.licencia.question.title"), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		// Si NO esta de vacaciones sigue el flujo normal.
		else {
			Map<String, Object> datos = new HashMap<>();
			datos.put(FUNCION, "validarReparticion");
			datos.put("datos", usuarioProductor);
			enviarBloqueoPantalla(datos);
		}
	}

	public void validacionesReparticion(Usuario usuario) throws Exception {
		Usuario usuarioLogueado = Utilitarios.obtenerUsuarioActual();
		if (!this.getUsuarioService().checkMismaReparticion(usuarioLogueado, usuario)) {
			this.validarReparticionUsuario(usuario);
		} else {
			asignarTarea();
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void validarReparticionUsuario(final Usuario usuario) {
		Clients.clearBusy();
		Messagebox.show(
				Labels.getLabel("ee.general.usuarioDistintaReparticion",
						new String[] { usuario.getNombreApellido() }),
				Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							Map<String, Object> datos = new HashMap<>();
							datos.put(FUNCION, "asignarTarea");
							datos.put("datos", usuario);
							enviarBloqueoPantalla(datos);
							break;
						default:
							return;
						}
					}
				});
	}

	protected Usuario getUsuarioApoderado(Usuario usuarioProductor) {
		// Busco si el usuario productor esta de licencia y en ese caso obtengo
		// el apoderado
		String apoderado = getUsuarioApoderado(usuarioProductor.getUsername());
		Usuario usuario = null;

		if (apoderado != null) {
			usuario = usuarioService.obtenerUsuario(apoderado);
		}

		return usuario;
	}

	/**
	 * Permite obtener el usuario apoderado de manera recursiva. Contempla los
	 * casos que sea lic de lic
	 * 
	 * @param usuarioProductor
	 * @return
	 * @throws SecurityNegocioException
	 */
	protected String getUsuarioApoderado(String usuarioProductor) {
		String retorno = null;

		if (periodoLicenciaService.licenciaActiva(usuarioProductor)) {
			retorno = periodoLicenciaService.obtenerApoderadoUsuarioLicencia(usuarioProductor);
		}

		return retorno;
	}

	protected abstract void asignarTarea() throws Exception;

	protected abstract void enviarBloqueoPantalla(Object data);

}
