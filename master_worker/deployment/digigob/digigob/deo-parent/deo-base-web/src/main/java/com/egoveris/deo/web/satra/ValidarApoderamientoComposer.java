package com.egoveris.deo.web.satra;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.produccion.AbstractWorkFlowComposer;
import com.egoveris.edt.ws.service.IExternalPeriodoLicenciaService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;

/**
 * @author everis
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class ValidarApoderamientoComposer extends AbstractWorkFlowComposer {

	@WireVariable("reparticionHabilitadaServiceImpl")
	private ReparticionHabilitadaService reparticionesHabilitadaService;

	@WireVariable("externalPeriodoLicenciaService")
	private IExternalPeriodoLicenciaService periodoLicenciaService;

	/**
	* 
	*/
	private static final long serialVersionUID = 7073331193976230718L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidarApoderamientoComposer.class);
	private static final String FUNCION = "funcion";

	/**
	 * Este metodo valida si el usuario seleccionado para realizar una tarea
	 * esta de licencia. Si esta de licencia válida que el usuario apoderado
	 * pertenezca a la misma repartición del usuario generador.
	 * 
	 * @param usuarioProductor
	 * @throws InterruptedException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void validarApoderamiento(final Usuario usuarioProductor, TipoDocumentoDTO tipoDoc)
			throws InterruptedException {
		try {
			boolean licenciaActiva = periodoLicenciaService.licenciaActiva(usuarioProductor.getUsername());
			Usuario datosApoderado = null;

			if (licenciaActiva) {
				String strApoderado = periodoLicenciaService.obtenerApoderadoUsuarioLicencia(usuarioProductor.getUsername());

				if (strApoderado != null) {
					datosApoderado = usuarioService.obtenerUsuario(strApoderado);
				}
			}

			final Usuario usuarioApoderado = datosApoderado;

			
			// Consulto si el usuario al que envío a producir el documento
			// se encuentra de vacaciones.
			// Si esta de vacaciones pido confirmación de reasignación de tarea
			if (licenciaActiva && usuarioApoderado != null) {
				Messagebox.show(
						Labels.getLabel("gedo.licencia.question.value",
								new String[] { usuarioProductor.getUsername(), usuarioApoderado.getUsername().toUpperCase() }) + "\n",
						Labels.getLabel("gedo.licencia.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
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
						Labels.getLabel("gedo.licencia.sinApoderado.value", new String[] { usuarioProductor.getUsername() }),
						Labels.getLabel("gedo.licencia.question"), Messagebox.OK, Messagebox.EXCLAMATION);
			}
			// Si NO esta de vacaciones sigue el flujo normal.
			else {
				if (tipoDoc != null) {
					try {
						validarReparticionesFirmante(usuarioProductor, tipoDoc);
					} catch (InterruptedException e) {
						LOGGER.error("Error ", e.getMessage());
						Messagebox.show(
								Labels.getLabel("gedo.general.usuarioDistintaReparticionFirma",
										new String[] { usuarioProductor.getNombreApellido() }),
								Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
						return;
					}
				}

				Map<String, Object> datos = new HashMap<>();
				datos.put(FUNCION, "validarReparticion");
				datos.put("datos", usuarioProductor);
				enviarBloqueoPantalla(datos);
			}
			
		}catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			Messagebox.show(
					Labels.getLabel("gedo.general.periodoLicencia",
							new String[] { usuarioProductor.getNombreApellido() }),
					Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
		}finally {			
			Clients.clearBusy();
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

	public void validarReparticionesFirmante(Usuario usuario, TipoDocumentoDTO tipoDocumento)
			throws InterruptedException {
		if (!reparticionesHabilitadaService.validarPermisosUsuariosDeSuListaReparticiones(tipoDocumento,
				usuario.getUsername(), Constantes.REPARTICION_PERMISO_FIRMAR)) {
			throw new InterruptedException();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void validarReparticionUsuario(final Usuario usuario) {
		Clients.clearBusy();
		Messagebox.show(
				Labels.getLabel("gedo.general.usuarioDistintaReparticion",
						new String[] { usuario.getNombreApellido() }),
				Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
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
