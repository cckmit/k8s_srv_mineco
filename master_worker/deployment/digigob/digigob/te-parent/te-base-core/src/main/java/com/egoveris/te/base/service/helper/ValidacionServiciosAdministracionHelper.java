package com.egoveris.te.base.service.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Participation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.ValidaUsuarioExpedientesService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.ConstantesCore;
import com.egoveris.te.base.util.WorkFlow;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;


public class ValidacionServiciosAdministracionHelper {
    private static transient Logger logger = LoggerFactory.getLogger(ValidacionServiciosAdministracionHelper.class);
    private static ValidacionServiciosAdministracionHelper instance = null;

    public static ValidacionServiciosAdministracionHelper getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - start");
		}

        if (instance == null) {
            instance = new ValidacionServiciosAdministracionHelper();
        }

		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - end - return value={}", instance);
		}
        return instance;
    }

    /**
     * Se validará la asignación a un usuario que se hizo su derivación del <code>ExpedienteElectronico</code> para poder hacer la asociación al <code>ExpedienteElectronico</code>
     * @param <code>WorkFlow</code>workFlow
     * @param <code>HistorialOperacionService</code>historialOperacionService
     * @param <code>java.lang.String</code>sistemaUsuario
     * @param <code>java.lang.String</code>usuario
     * @param <code>java.lang.Boolean</code>isOperacionBloqueante
     * @exception <code>Exception</code> ESTADO: (TRAMITACION, EJECUCION, COMUnICACION, ETC...), Asignacion Grupal: (JBPM4_PARTICIPATION.GROUPID_)
     * Asignacion de usuario (En caso de Existir - JBPM4_TASK.ASSIGNEE_)
     */
    public static void validarAsignacion(WorkFlow workFlow, HistorialOperacionService historialOperacionService, String sistemaUsuario, String usuario, Boolean isOperacionBloqueante)
    throws ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarAsignacion(workFlow={}, historialOperacionService={}, sistemaUsuario={}, usuario={}, isOperacionBloqueante={}) - start", workFlow, historialOperacionService, sistemaUsuario, usuario, isOperacionBloqueante);
		}

    if (ConstantesCore.ESTADO_GUARDA_TEMPORAL.equalsIgnoreCase(workFlow.getExpedienteElectronico().getEstado())
    		|| ConstantesCore.ESTADO_SOLICITUD_ARCHIVO.equalsIgnoreCase(workFlow.getExpedienteElectronico().getEstado())){
			if (logger.isDebugEnabled()) {
				logger.debug("validarAsignacion(WorkFlow, HistorialOperacionService, String, String, Boolean) - end");
			}
	        return;
    }
    	usuario = usuario.toUpperCase();
        String usuarioAux = (workFlow.getExpedienteElectronico().getSistemaApoderado().equalsIgnoreCase(sistemaUsuario) ? new String(usuario + ConstantesCore.SUFIJO_BLOQUEADO) : usuario);
        
//        if (workFlow.getExpedienteElectronico().getBloqueado()){
//        	throw new ProcesoFallidoException("No se puede operar sobre un expediente que no se encuentra bloqueado ( " + workFlow.getExpedienteElectronico().getSistemaApoderado() + ")", null);
//        }
        
//        if(sistemaUsuario == null || !sistemaUsuario.equals(workFlow.getExpedienteElectronico().getSistemaApoderado())){
//			throw new ProcesoFallidoException("El sistema solicitante no coincide con el sistema que poseé el expediente", null);
//        }
            if (workFlow.existeTareaGrupalParaAssignee(usuarioAux)) {
                MessageBuilder message = new MessageBuilder();
                message.addMessage("[ThreadId=\"" + Thread.currentThread().getId() + "\" ], ");
                message.addMessage("[Estado=");
                message.addMessage("\"" + ((workFlow.getWorkingTask().getName() != null) ? (workFlow.getWorkingTask().getName()) : "") + "\" ");
                message.addMessage(" ], ");
                message.addMessage("[Asignacion Grupo Id=");

                List<Participation> participants = workFlow.getTaskParticipations(workFlow.getWorkingTask());

                if (participants.size() > 0) {
                    int i = 1;

                    for (Participation participant : participants) {
                        message.addMessage("\"" + ((participant.getGroupId() != null) ? (" " + participant.getGroupId() + "\n") : "") + "\" ");

                        if (i < participants.size()) {
                            message.addMessage(", ");
                        }

                        i++;
                    }
                } else {
                    message.addMessage("\"" + "\" ");
                }

                message.addMessage(" ], ");
                message.addMessage(" [Asignacion Usuario Id=");
                message.addMessage("\"" + ((workFlow.getWorkingTask().getAssignee() != null) ? (" " + workFlow.getWorkingTask().getAssignee()) : "") + "\" ");
                message.addMessage(" ], ");
                message.addMessage(" [Motivo=\"" + getMotivo(historialOperacionService, workFlow.getExpedienteElectronico()) + "\" ");
                message.addMessage(" ] ");
                throw new ProcesoFallidoException("El expediente se encuentra asignado a un usuario o grupo distinto. [ProcessFailuredException.message] " + message.getMessage(), null);
           }

		if (logger.isDebugEnabled()) {
			logger.debug("validarAsignacion(WorkFlow, HistorialOperacionService, String, String, Boolean) - end");
		}
        }
   //}

    public static String getMotivo(HistorialOperacionService historialOperacionService, ExpedienteElectronicoDTO expediente)
        throws ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("getMotivo(historialOperacionService={}, expediente={}) - start", historialOperacionService, expediente);
		}

        String motivo = new String("");
        List<HistorialOperacionDTO> historiales = historialOperacionService.buscarHistorialporExpediente(expediente.getId());
        int c = 0;

        for (HistorialOperacionDTO historialOperacion : historiales) {
            if (historialOperacion.getId() >= c) {
                motivo = historialOperacion.getMotivo();
                c = historialOperacion.getId();
            }
        }

        if (StringUtils.isEmpty(motivo)) {
            motivo = "No es posible obtener el motivo del ultimo pase del expediente " + expediente.getCodigoCaratula();
        }

		if (logger.isDebugEnabled()) {
			logger.debug("getMotivo(HistorialOperacionService, ExpedienteElectronico) - end - return value={}", motivo);
		}
        return motivo;
    }

    public static Usuario verificarExistenciaDeUsuario(ValidaUsuarioExpedientesService validaUsuarioExpedientesService, UsuariosSADEService usuariosSADEService, String usuario)
        throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarExistenciaDeUsuario(validaUsuarioExpedientesService={}, usuariosSADEService={}, usuario={}) - start", validaUsuarioExpedientesService, usuariosSADEService, usuario);
		}

        Usuario usuarioBean = null;

        try {
        	if (usuario == null || StringUtils.isEmpty(usuario) || !validaUsuarioExpedientesService.validaUsuarioExpedientes(usuario)) {
                throw new ParametroIncorrectoException("El usuario origen no es un usuario válido de CCOO.", null);
            }

            usuarioBean = usuariosSADEService.getDatosUsuario(usuario);
        } catch (InterruptedException ex) {
			logger.error("verificarExistenciaDeUsuario(ValidaUsuarioExpedientesService, UsuariosSADEService, String)", ex);
			Thread.currentThread().interrupt();
            throw new ProcesoFallidoException("No fue posible validar el usuario otorgado.", ex);
        }

		if (logger.isDebugEnabled()) {
			logger.debug("verificarExistenciaDeUsuario(ValidaUsuarioExpedientesService, UsuariosSADEService, String) - end - return value={}", usuarioBean);
		}
        return usuarioBean;
    }
    
    public static Usuario verificarExistenciaDeUsuarioParaSistemaExterno(ValidaUsuarioExpedientesService validaUsuarioExpedientesService, UsuariosSADEService usuariosSADEService, String usuario,
    		ExpedienteElectronicoDTO expedienteElectronico, String[] sistemasCaratuladoresExternos)
        throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarExistenciaDeUsuarioParaSistemaExterno(validaUsuarioExpedientesService={}, usuariosSADEService={}, usuario={}, ee={}, sistemasCaratuladoresExternos={}) - start", validaUsuarioExpedientesService, usuariosSADEService, usuario, expedienteElectronico, sistemasCaratuladoresExternos);
		}
    		
    	Usuario usuarioBean = null;
    	try{
        	if(esSistemaExterno(expedienteElectronico,sistemasCaratuladoresExternos)){
        		boolean existeUsuario= validaUsuarioExpedientesService.validaUsuarioExpedientes(usuario);
        		if(!existeUsuario){
        			throw new ParametroIncorrectoException("El usuario no existe,fue migrado o dado de baja, por favor actualice su tabla de equivalencias", null);
        		}
        	}        	
        usuarioBean = verificarExistenciaDeUsuario(validaUsuarioExpedientesService, usuariosSADEService, usuario);
        } catch (InterruptedException e1) {
			logger.error("verificarExistenciaDeUsuarioParaSistemaExterno(ValidaUsuarioExpedientesService, UsuariosSADEService, String, ExpedienteElectronico, String[])", e1);
			Thread.currentThread().interrupt();
            throw new ProcesoFallidoException("No fue posible validar el usuario otorgado.", e1);
        }

		if (logger.isDebugEnabled()) {
			logger.debug("verificarExistenciaDeUsuarioParaSistemaExterno(ValidaUsuarioExpedientesService, UsuariosSADEService, String, ExpedienteElectronico, String[]) - end - return value={}", usuarioBean);
		}
        return usuarioBean;
    }
    
    
    private static boolean esSistemaExterno(ExpedienteElectronicoDTO ee,String[] sistemasCaratuladoresExternos){
		if (logger.isDebugEnabled()) {
			logger.debug("esSistemaExterno(ee={}, sistemasCaratuladoresExternos={}) - start", ee, sistemasCaratuladoresExternos);
		}

    	for (int i=0;i < sistemasCaratuladoresExternos.length;i++){
    		if (sistemasCaratuladoresExternos[i] != null && sistemasCaratuladoresExternos[i].equals(ee.getSistemaCreador())){
				if (logger.isDebugEnabled()) {
					logger.debug("esSistemaExterno(ExpedienteElectronico, String[]) - end - return value={}", true);
				}
    			return true;
    		}
    	}

		if (logger.isDebugEnabled()) {
			logger.debug("esSistemaExterno(ExpedienteElectronico, String[]) - end - return value={}", false);
		}
    	return false;
    }
    
    
    
    
    
    

    public static void validarMotivoDePase(String motivoDePase)
        throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarMotivoDePase(motivoDePase={}) - start", motivoDePase);
		}

        if ((motivoDePase == null) || ("".equalsIgnoreCase(motivoDePase)) || (motivoDePase.isEmpty())) {
            throw new ParametroIncorrectoException("Debe ingresar un motivo de pase válido.", null);
        }

		if (logger.isDebugEnabled()) {
			logger.debug("validarMotivoDePase(String) - end");
		}
    }

    public static void validarActividadesPendientesDeResolucion(IActividadExpedienteService actividadExpedienteService, ExpedienteElectronicoDTO expedienteElectronico, String usuarioDestino)
        throws ExpedienteNoDisponibleException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarActividadesPendientesDeResolucion(actividadExpedienteService={}, expedienteElectronico={}, usuarioDestino={}) - start", actividadExpedienteService, expedienteElectronico, usuarioDestino);
		}

        // validacion que no haya actividades pendientes
        List<String> workIds = new ArrayList<String>();
        workIds.add(expedienteElectronico.getIdWorkflow());

        if (!actividadExpedienteService.buscarActividadesVigentes(workIds).isEmpty()) {
            throw new ExpedienteNoDisponibleException("El expediente tiene asociado actividades pendientes de resolucion.", null);
        }

		if (logger.isDebugEnabled()) {
			logger.debug("validarActividadesPendientesDeResolucion(IActividadExpedienteService, ExpedienteElectronico, String) - end");
		}
    }

    public static void validarApoderado(UsuariosSADEService usuariosSADEService, ExpedienteElectronicoDTO expedienteElectronico, String usuarioDestino, String nuevoEstadoSeleccionado) {
		if (logger.isDebugEnabled()) {
			logger.debug("validarApoderado(usuariosSADEService={}, expedienteElectronico={}, usuarioDestino={}, nuevoEstadoSeleccionado={}) - start", usuariosSADEService, expedienteElectronico, usuarioDestino, nuevoEstadoSeleccionado);
		}

        String estadoSeleccionado;

        if ((nuevoEstadoSeleccionado == null) || "".equalsIgnoreCase(nuevoEstadoSeleccionado) || "null".equalsIgnoreCase(nuevoEstadoSeleccionado)) {
            estadoSeleccionado = expedienteElectronico.getEstado();
        } else {
            estadoSeleccionado = nuevoEstadoSeleccionado;
        }

        // Refactor de código para mejorar la lectura del código. Validaciones.
        // No hace falta validar si son esUsuarioDestino, esSectorDestino y
        // esReparticionDestino, entrarÃ­a por orden a cualquiera de los mismos.
        if (!estadoSeleccionado.trim().equals(ConstantesCore.ESTADO_GUARDA_TEMPORAL)) {
            // Guarda temporal no requiere destino
            // Valido si el usuario está de vacaciones y obtengo su
            // apoderado en dado caso que estÃ© de vacaciones
            if ((usuarioDestino != null) && !"".equalsIgnoreCase(usuarioDestino) && !"null".equalsIgnoreCase(usuarioDestino)) {
                try {
                	String usuarioApoderado = null;
                	if(usuariosSADEService.licenciaActiva(usuarioDestino)){
                		usuarioApoderado = usuariosSADEService.getDatosUsuario(usuarioDestino).getApoderado();                		
                	}
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("[ThreadId= " + Thread.currentThread().getId() + "], hubo un error en el mÃ©todo usuariosSADEService.getDatos, al obtener el usuario=" +
                            (("".equalsIgnoreCase(usuarioDestino) || "null".equalsIgnoreCase(usuarioDestino)) ? "" : usuarioDestino), e);
                    }else{
                    	logger.info("Se obtuvo el usuario correctamente");
                    }
                }
            }
        }

		if (logger.isDebugEnabled()) {
			logger.debug("validarApoderado(UsuariosSADEService, ExpedienteElectronico, String, String) - end");
		}
    }

    public static void validarDestinoParaCCOO(ValidaUsuarioExpedientesService validaUsuarioExpedientesService, String usuarioDestino, Boolean isUsuarioDestino, String estado)
        throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarDestinoParaCCOO(validaUsuarioExpedientesService={}, usuarioDestino={}, isUsuarioDestino={}, estado={}) - start", validaUsuarioExpedientesService, usuarioDestino, isUsuarioDestino, estado);
		}
    	
    	if (ConstantesCore.ESTADO_GUARDA_TEMPORAL.equals(estado)){
			if (logger.isDebugEnabled()) {
				logger.debug("validarDestinoParaCCOO(ValidaUsuarioExpedientesService, String, Boolean, String) - end");
			}
    		return;
    	}

        if (isUsuarioDestino && (usuarioDestino != null) && !"".equalsIgnoreCase(usuarioDestino) && !"null".equalsIgnoreCase(usuarioDestino)) {
            try {
                if (!validaUsuarioExpedientesService.validaUsuarioExpedientes(usuarioDestino)) {
                    throw new ParametroIncorrectoException("El usuario destino (" + usuarioDestino + ") no es un usuario válido de CCOO.", null);
                }
            } catch (InterruptedException e) {
				logger.error("validarDestinoParaCCOO(ValidaUsuarioExpedientesService, String, Boolean, String)", e);
				Thread.currentThread().interrupt();
                throw new ProcesoFallidoException("No fue posible validar el usuario destino " + usuarioDestino, e);
            }
        }
        // si es usuario destino y  el usuario destino es null lanzo el error
        if(isUsuarioDestino && (usuarioDestino == null || "".equals(usuarioDestino))){
        	throw new ParametroIncorrectoException("El usuario destino es invalido", null);
        }
        
        if ((estado == null) || "".equalsIgnoreCase(estado) || "null".equalsIgnoreCase(estado) || estado.trim().equals(ConstantesCore.ESTADO_GUARDA_TEMPORAL)) {
			if (logger.isDebugEnabled()) {
				logger.debug("validarDestinoParaCCOO(ValidaUsuarioExpedientesService, String, Boolean, String) - end");
			}
            return;
        }

		if (logger.isDebugEnabled()) {
			logger.debug("validarDestinoParaCCOO(ValidaUsuarioExpedientesService, String, Boolean, String) - end");
		}
    }

    /**
     * Esta validación es sobre la repartición destino del expediente.
     * @param <code>ReparticionServ</code>reparticionServ
     * @param <code>SectorInternoServ</code>sectorInternoServ
     * @param <code>java.lang.String</code>reparticionDestino
     * @param <code>java.lang.String</code>sectorDestino
     * @param <code>java.lang.Boolean</code>isMesaDestino
     * @param <code>java.lang.Boolean</code>isSectorDestino
     * @param <code>java.lang.Boolean</code>isReparticionDestino
     * @throws <code>ProcesoFallidoException</code>
     */
    public static void validarRepaticion(ReparticionServ reparticionServ, SectorInternoServ sectorInternoServ, String reparticionDestino, String sectorDestino, Boolean isUsuarioDestino,
        Boolean isMesaDestino, Boolean isSectorDestino, Boolean isReparticionDestino, String estado)
        throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarRepaticion(reparticionServ={}, sectorInternoServ={}, reparticionDestino={}, sectorDestino={}, isUsuarioDestino={}, isMesaDestino={}, isSectorDestino={}, isReparticionDestino={}, estado={}) - start", reparticionServ, sectorInternoServ, reparticionDestino, sectorDestino, isUsuarioDestino, isMesaDestino, isSectorDestino, isReparticionDestino, estado);
		}
    	
    	if (ConstantesCore.ESTADO_GUARDA_TEMPORAL.equals(estado)){
			if (logger.isDebugEnabled()) {
				logger.debug("validarRepaticion(ReparticionServ, SectorInternoServ, String, String, Boolean, Boolean, Boolean, Boolean, String) - end");
			}
    		return;
    	}
    	
        if (!isUsuarioDestino) {
            // sector.
            if (StringUtils.isEmpty(reparticionDestino)) {
                throw new ParametroIncorrectoException("Debe ingresar una repartición válida.", null);
            }

            ReparticionBean reparticion = reparticionServ.buscarReparticionPorCodigo(reparticionDestino.trim());

            try {
                if (reparticion == null) {
                    throw new ParametroIncorrectoException("No fue posible obtener la repartición solicitada.", null);
                }else if(reparticion.getVigenciaHasta() == null || reparticion.getVigenciaHasta().before(new Date()) 
                		|| reparticion.getVigenciaDesde().after(new Date())){
                	throw new ParametroIncorrectoException("La reparticion: "+ reparticion.getCodigo() + "No esta vigente", null);
                }
            } catch (RuntimeException e) {
				logger.error("validarRepaticion(ReparticionServ, SectorInternoServ, String, String, Boolean, Boolean, Boolean, Boolean, String)", e);

                throw new ProcesoFallidoException("No fue posible encontrar la repartición: " + reparticionDestino, null);
            }

            // Si es mesa busca el sector para la reparticion y se setea en datospase
            if (isSectorDestino) {
                if (StringUtils.isEmpty(sectorDestino)) {
                    throw new ParametroIncorrectoException("Debe ingresar un sector destino válido.", null);
                }

                if (!sectorInternoServ.validarCodigoSector(sectorDestino, reparticion.getId())) {
                    throw new ParametroIncorrectoException("Debe ingresar un sector válido.", null);
                }
            } else if (!isReparticionDestino) {
                throw new ParametroIncorrectoException("Debe seleccionar si el destino del pase será un usuario, sector, organismo o mesa.", null);
            }
        }
        if ((estado == null) || "".equalsIgnoreCase(estado) || "null".equalsIgnoreCase(estado)) {
			if (logger.isDebugEnabled()) {
				logger.debug("validarRepaticion(ReparticionServ, SectorInternoServ, String, String, Boolean, Boolean, Boolean, Boolean, String) - end");
			}
         	return;
        }

		if (logger.isDebugEnabled()) {
			logger.debug("validarRepaticion(ReparticionServ, SectorInternoServ, String, String, Boolean, Boolean, Boolean, Boolean, String) - end");
		}
    }
    
	public void validarDestino(Boolean isMesaDestino,Boolean isSectorDestino, Boolean isReparticionDestino,
			Boolean isUsuarioDestino, String estado, String estadoEE) throws ProcesoFallidoException{
		if (logger.isDebugEnabled()) {
			logger.debug("validarDestino(isMesaDestino={}, isSectorDestino={}, isReparticionDestino={}, isUsuarioDestino={}, estado={}, estadoEE={}) - start", isMesaDestino, isSectorDestino, isReparticionDestino, isUsuarioDestino, estado, estadoEE);
		}

    	int contador = 0;
    	if (isMesaDestino){
    		contador++;
    	}
    	if (isSectorDestino){
    		contador++;
    	}
    	if (isReparticionDestino){
    		contador++;
    	}
    	if (isUsuarioDestino){
    		contador++;
    	}
    	
    	String estadoSel;
    	if(estado != null){
    		estadoSel = estado;
    	} else {
    		estadoSel = estadoEE;
    	}
    	
    	if(contador > 0 && EsEstadoGTSolicitudOArchivo(estadoSel)){
    		throw new ProcesoFallidoException("No es compatible el destino seleccionado con el estado seleccionado.", null);
    	}
    	
    	if(contador > 1 || (contador == 0 && !EsEstadoGTSolicitudOArchivo(estadoSel))){  		
    		throw new ProcesoFallidoException("Debe seleccionar un único destino.", null);
    	}

		if (logger.isDebugEnabled()) {
			logger.debug("validarDestino(Boolean, Boolean, Boolean, Boolean, String, String) - end");
		}
	}
	
	public boolean EsEstadoGTSolicitudOArchivo(String estado){
		if (logger.isDebugEnabled()) {
			logger.debug("EsEstadoGTSolicitudOArchivo(estado={}) - start", estado);
		}

		if (ConstantesCore.ESTADO_GUARDA_TEMPORAL.equals(estado) || ConstantesCore.ESTADO_SOLICITUD_ARCHIVO.equals(estado)
				|| ConstantesCore.ESTADO_ARCHIVO.equals(estado)){
			if (logger.isDebugEnabled()) {
				logger.debug("EsEstadoGTSolicitudOArchivo(String) - end - return value={}", true);
			}
			return true;
		} else{
			if (logger.isDebugEnabled()) {
				logger.debug("EsEstadoGTSolicitudOArchivo(String) - end - return value={}", false);
			}
			return false;
			
		}
	}

    public static void verificarExistenciaDeUsuarioCCOO(ValidaUsuarioExpedientesService validaUsuarioExpedientesService, UsuariosSADEService usuariosSADEService, String usuario)
        throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarExistenciaDeUsuarioCCOO(validaUsuarioExpedientesService={}, usuariosSADEService={}, usuario={}) - start", validaUsuarioExpedientesService, usuariosSADEService, usuario);
		}
 
        try {
            if (StringUtils.isEmpty(usuario) || !validaUsuarioExpedientesService.validaUsuarioExpedientes(usuario)) {
                throw new ParametroIncorrectoException("El usuario origen (" + usuario + ") no es un usuario válido de CCOO.", null);
            }
        } catch (InterruptedException e1) {
			logger.error("verificarExistenciaDeUsuarioCCOO(ValidaUsuarioExpedientesService, UsuariosSADEService, String)", e1);
			Thread.currentThread().interrupt();
            throw new ProcesoFallidoException("No fue posible validar el usuario otorgado.", e1);
        }

		if (logger.isDebugEnabled()) {
			logger.debug("verificarExistenciaDeUsuarioCCOO(ValidaUsuarioExpedientesService, UsuariosSADEService, String) - end");
		}
        return;
    }
    //
	public static void verificarEstadoSeleccionado(String estadoDatosPase) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarEstadoSeleccionado(estadoDatosPase={}) - start", estadoDatosPase);
		}

		if(estadoDatosPase == null || "".equals(estadoDatosPase) || (estadoDatosPase.equals(ConstantesCore.ESTADO_INICIACION)
				|| estadoDatosPase.equals(ConstantesCore.ESTADO_TRAMITACION) || estadoDatosPase.equals(ConstantesCore.ESTADO_EJECUCION)
				|| estadoDatosPase.equals(ConstantesCore.ESTADO_SUBSANACION) || estadoDatosPase.equals(ConstantesCore.ESTADO_COMUNICACION)
				|| estadoDatosPase.equals(ConstantesCore.ESTADO_GUARDA_TEMPORAL))){
			if (logger.isDebugEnabled()) {
				logger.debug("verificarEstadoSeleccionado(String) - end");
			}
				return;
		}
		throw new ParametroIncorrectoException("El estado: ( "+estadoDatosPase+" ) es un estado  invalido", null);
	}	

	public static void verificarEstadoParaEnvioASolicitudArchivo(String estadoExpediente,
			String estadoSeleccionado) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarEstadoParaEnvioASolicitudArchivo(estadoExpediente={}, estadoSeleccionado={}) - start", estadoExpediente, estadoSeleccionado);
		}
		
		if( (estadoSeleccionado == null) || estadoSeleccionado.equals("") || !estadoSeleccionado.equals(ConstantesCore.ESTADO_SOLICITUD_ARCHIVO)){
			throw new ParametroIncorrectoException ("El estado: (" +estadoSeleccionado+" ) es un estado inválido", null);
		}
		
		if (!estadoExpediente.equals(ConstantesCore.ESTADO_GUARDA_TEMPORAL)){
			throw new ParametroIncorrectoException ("El expediente no se encuentra en estado 'Guarda Temporal'", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarEstadoParaEnvioASolicitudArchivo(String, String) - end");
		}
	}
	
	
	public static void verificarEstadoParaEnvioAArchivo(String estadoExpediente,String estadoSeleccionado) throws ParametroIncorrectoException{
		if (logger.isDebugEnabled()) {
			logger.debug("verificarEstadoParaEnvioAArchivo(estadoExpediente={}, estadoSeleccionado={}) - start", estadoExpediente, estadoSeleccionado);
		}

		if( (estadoSeleccionado == null) || estadoSeleccionado.equals("") || !estadoSeleccionado.equals(ConstantesCore.ESTADO_ARCHIVO)){
			throw new ParametroIncorrectoException ("El estado: (" +estadoSeleccionado+" ) es un estado invalido", null);
		}
		
		if(!estadoExpediente.equals(ConstantesCore.ESTADO_SOLICITUD_ARCHIVO)){
			throw new ParametroIncorrectoException("El expediente no se encuentra en estado 'Solicitud Archivo'", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarEstadoParaEnvioAArchivo(String, String) - end");
		}
	}

	public static void validarSistemaSolicitante(String sistemaOrigen, String sistemasArchivadores) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarSistemaSolicitante(sistemaOrigen={}, sistemasArchivadores={}) - start", sistemaOrigen, sistemasArchivadores);
		}

		String[] sistemas = sistemasArchivadores.split(",");
		for (int i = 0; i<sistemas.length;i++){
			if(sistemas[i].equals(sistemaOrigen)){
				if (logger.isDebugEnabled()) {
					logger.debug("validarSistemaSolicitante(String, String) - end");
				}
				return ;
			}
		}
		throw new ParametroIncorrectoException("El sistema origen no tiene permisos para realizar esta accion", null);
	}

	public void validarTransicion(String estadoSeleccionado, String estadoExpediente) throws ParametroIncorrectoException{
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicion(estadoSeleccionado={}, estadoExpediente={}) - start", estadoSeleccionado, estadoExpediente);
		}
		
		if(estadoSeleccionado != null){
			if (estadoExpediente.equals(ConstantesCore.ESTADO_INICIACION)){
				validarTransicionesValidasParaIniciacion(estadoSeleccionado);

				if (logger.isDebugEnabled()) {
					logger.debug("validarTransicion(String, String) - end");
				}
				return;
			}
			if(estadoExpediente.equals(ConstantesCore.ESTADO_TRAMITACION)){
				validarTransicionesValidasParaTramitacion(estadoSeleccionado);

				if (logger.isDebugEnabled()) {
					logger.debug("validarTransicion(String, String) - end");
				}
				return;
			}
			if(estadoExpediente.equals(ConstantesCore.ESTADO_SUBSANACION)){
				validarTransicionesValidasParaSubsanacion(estadoSeleccionado);

				if (logger.isDebugEnabled()) {
					logger.debug("validarTransicion(String, String) - end");
				}
				return;
			}
			if(estadoExpediente.equals(ConstantesCore.ESTADO_COMUNICACION)){
				validarTransicionesValidasParaComunicacion(estadoSeleccionado);

				if (logger.isDebugEnabled()) {
					logger.debug("validarTransicion(String, String) - end");
				}
				return;
			}
			if(estadoExpediente.equals(ConstantesCore.ESTADO_EJECUCION)){
				validarTransicionesValidasParaEjecucion(estadoSeleccionado);

				if (logger.isDebugEnabled()) {
					logger.debug("validarTransicion(String, String) - end");
				}
				return;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicion(String, String) - end");
		}
	}
	
	
	public void validarTransicion(String estadoActual, String proximoEstado, List<String> estados) throws ParametroIncorrectoException{
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicion(estadoActual={}, proximoEstado={}, estados={}) - start", estadoActual, proximoEstado, estados);
		}

		if(proximoEstado == null){
			if (logger.isDebugEnabled()) {
				logger.debug("validarTransicion(String, String, List<String>) - end");
			}
			return;
		}
		for (String estado : estados) {
			if (proximoEstado.equals(estado)) return;
		}
		
		throw new ParametroIncorrectoException(String.format("No es posible cambiar el estado del expediente de: %s a: %s ",estadoActual,proximoEstado), null);
	}
	
	
	
	
	
	private void validarTransicionesValidasParaEjecucion(
			String estadoSeleccionado) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaEjecucion(estadoSeleccionado={}) - start", estadoSeleccionado);
		}

		String ejecucion = ConstantesCore.ESTADO_EJECUCION;
		String gt = ConstantesCore.ESTADO_GUARDA_TEMPORAL;
		String subsanacion = ConstantesCore.ESTADO_SUBSANACION;
		
		if ( !(ejecucion.equals(estadoSeleccionado) || gt.equals(estadoSeleccionado) || subsanacion.equals(estadoSeleccionado))){
			throw new ParametroIncorrectoException("No es posible cambiar el estado del expediente de: "+ejecucion+" a: "+estadoSeleccionado, null);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaEjecucion(String) - end");
		}
	}

	private void validarTransicionesValidasParaComunicacion(
			String estadoSeleccionado) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaComunicacion(estadoSeleccionado={}) - start", estadoSeleccionado);
		}

		String gt = ConstantesCore.ESTADO_GUARDA_TEMPORAL;
		String comunicacion = ConstantesCore.ESTADO_COMUNICACION;
		String ejecucion = ConstantesCore.ESTADO_EJECUCION;
		
		if (!(gt.equals(estadoSeleccionado) || comunicacion.equals(estadoSeleccionado) || ejecucion.equals(estadoSeleccionado))){
			throw new ParametroIncorrectoException("No es posible cambiar el estado del expediente de: "+comunicacion+" a: "+estadoSeleccionado, null);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaComunicacion(String) - end");
		}
	}

	private void validarTransicionesValidasParaSubsanacion(String estadoSeleccionado) throws ParametroIncorrectoException{
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaSubsanacion(estadoSeleccionado={}) - start", estadoSeleccionado);
		}

		String subsanacion = ConstantesCore.ESTADO_SUBSANACION;
		String ejecucion = ConstantesCore.ESTADO_EJECUCION;
		String gt = ConstantesCore.ESTADO_GUARDA_TEMPORAL;
		String tramitacion= ConstantesCore.ESTADO_TRAMITACION;
		
		if(!(subsanacion.equals(estadoSeleccionado) || ejecucion.equals(estadoSeleccionado) || gt.equals(estadoSeleccionado)
				|| tramitacion.equals(estadoSeleccionado))){
			throw new ParametroIncorrectoException("No es posible cambiar el estado del expediente de: "+subsanacion+" a: "+estadoSeleccionado, null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaSubsanacion(String) - end");
		}
	}

	private void validarTransicionesValidasParaTramitacion(
			String estadoSeleccionado) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaTramitacion(estadoSeleccionado={}) - start", estadoSeleccionado);
		}

		String comunicacion = ConstantesCore.ESTADO_COMUNICACION;
		String gt = ConstantesCore.ESTADO_GUARDA_TEMPORAL;
		String subsanacion = ConstantesCore.ESTADO_SUBSANACION;
		String tramitacion = ConstantesCore.ESTADO_TRAMITACION;
		
		if (! (tramitacion.equals(estadoSeleccionado) || gt.equals(estadoSeleccionado) || subsanacion.equals(estadoSeleccionado)
				|| comunicacion.equals(estadoSeleccionado))){
			throw new ParametroIncorrectoException("No es posible cambiar el estado del expediente de: "+tramitacion+" a: "+estadoSeleccionado, null);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaTramitacion(String) - end");
		}
	}

	private void validarTransicionesValidasParaIniciacion(
			String estadoSeleccionado)throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaIniciacion(estadoSeleccionado={}) - start", estadoSeleccionado);
		}

		String tramitacion = ConstantesCore.ESTADO_TRAMITACION;
		String iniciacion = ConstantesCore.ESTADO_INICIACION;
		String subsanacion = ConstantesCore.ESTADO_SUBSANACION;
		String gt = ConstantesCore.ESTADO_GUARDA_TEMPORAL;
		
		if(! ( tramitacion.equals(estadoSeleccionado) || iniciacion.equals(estadoSeleccionado)
				|| subsanacion.equals(estadoSeleccionado) || gt.equals(estadoSeleccionado))){
			throw new ParametroIncorrectoException("No es posible cambiar el estado del expediente de: "+iniciacion+" a: "+estadoSeleccionado, null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarTransicionesValidasParaIniciacion(String) - end");
		}
	}
	
	
	
}
