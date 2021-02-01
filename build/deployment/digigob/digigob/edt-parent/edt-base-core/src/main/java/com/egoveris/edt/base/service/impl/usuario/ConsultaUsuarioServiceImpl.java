package com.egoveris.edt.base.service.impl.usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.exception.ServicioWebException;
import com.egoveris.edt.base.model.eu.usuario.ConsultaUsuarioResponse;
import com.egoveris.edt.base.service.ConsultaUsuarioService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.repository.impl.ReparticionEDTRepository;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

/**
 * La presente implementación se corresponde al servicio para la consulta de
 * usuarios de Comunicaciones Oficiales.
 * 
 * @author jsalinas
 * 
 */

@Service("consultaUsuarioSade")
public class ConsultaUsuarioServiceImpl implements ConsultaUsuarioService {

  private static Logger logger = LoggerFactory.getLogger(ConsultaUsuarioServiceImpl.class);

  @Autowired
  private IUsuarioService usuarioSADEService;

  private List<Usuario> listaUsuarios;

  @Autowired
  private ReparticionEDTRepository reparticionRepository;

  @Override
  public List<ConsultaUsuarioResponse> consultaUsuarioSade(String nombreUsuario)
      throws ServicioWebException {

    logger.info("Inicio consultaUsuarioSade()");

    if (nombreUsuario == null || nombreUsuario.length() < 3) {
      String mensaje = "El parametro 'nombreUsuario' debe ser >= a 3 caracteres";
      logger.error(mensaje);
      throw new ServicioWebException(mensaje);
    }

    List<ConsultaUsuarioResponse> listResponse = new ArrayList<>();

    try {

      listaUsuarios = this.findUsuariosCO(nombreUsuario);

      for (Usuario datosUsuario : listaUsuarios) {
        ConsultaUsuarioResponse response = this.obtenerDatosResponse(datosUsuario);
        listResponse.add(response);
      }

      if (listResponse.isEmpty()) {

        String mensaje2 = "El usuario ingresado no se encuentra dado de alta en CCOO ni pertenece a un organismo vigente.";
        throw new ServicioWebException(mensaje2);

      }

    } catch (ServicioWebException e) {
      logger.error(e.getMessage(), e);
      throw new ServicioWebException(
          "El usuario ingresado no se encuentra dado de alta en CCOO ni pertenece a un organismo vigente. ");

    } catch (Exception e) {
      logger.error("Error obteniendo los usuarios CCOO: " + e.getMessage(), e);
      throw new ServicioWebException("Error obteniendo los usuarios CCOO: ");
    } finally {
      logger.info("Fin consultaUsuarioSade()");
    }

    return listResponse;
  }

  private ConsultaUsuarioResponse obtenerDatosResponse(Usuario usuario) {

    ConsultaUsuarioResponse respuesta = new ConsultaUsuarioResponse();
    Reparticion repa = reparticionRepository.obtenerReparticionUsername(usuario.getUsername());

    // Armo el cuerpo de la respuesta
    respuesta.setApellidoNombre(usuario.getNombreApellido());
    respuesta.setUsuario(usuario.getUsername());
    respuesta.setReparticion(repa.getCodigo());
    respuesta.setSector(usuario.getCodigoSectorInterno());
    respuesta.setJurisdiccion(repa.getCodigo());
    respuesta.setCuitCuil(usuario.getCuit());
    respuesta.setCargo(usuario.getCargo());
    respuesta.setFechaAlta(repa.getVigenciaDesde());
    respuesta.setFechaBaja(repa.getVigenciaHasta());
    respuesta.setEmail(usuario.getEmail());

    return respuesta;

  }

  private List<Usuario> findUsuariosCO(String str) throws ServicioWebException {
    List<Usuario> result = new ArrayList<Usuario>();
    if (!StringUtils.isEmpty(str) && str.length() >= 3) {
      try {

        String criterio = "username:" + str + "*";
        result = usuarioSADEService.obtenerUsuarios(criterio);

      } catch (SecurityNegocioException e) {
        logger.error("Error en la busqueda de usuarios CCOO " + e.getMessage(), e);
      }
    }

    return result;

  }

}
