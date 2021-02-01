package com.egoveris.edt.ws.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egoveris.edt.base.model.Status;
import com.egoveris.edt.base.model.UserRequest;
import com.egoveris.edt.base.model.ValidateUser;
import com.egoveris.edt.base.model.userOrganismosDTO;
import com.egoveris.edt.ws.service.IExternalReparticionService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@RestController
public class RemoteEndPointApp {

	private Log logger = LogFactory.getLog(RemoteEndPointApp.class);
	@Autowired
	public IUsuarioService usuarioService;
	@Autowired
	@Qualifier("externalReparticionService")
	public IExternalReparticionService repart;

	@RequestMapping(value = "/user/access", method = RequestMethod.POST) 
	public UserRequest access(@RequestParam("login") String user, @RequestParam("password") String pass) {

		// request out
		UserRequest requestOut= new UserRequest();
		 // user date
		ValidateUser userData = new ValidateUser(); 
		// List Status organismo
		List<userOrganismosDTO> userOrganismo = new ArrayList();
		//
		Usuario userFind = new Usuario();
		//
		Status status = new Status();
		// Usuario Inc
		Usuario userVali = new Usuario();
		try {
			  userFind = getUsuarioService().obtenerUsuario(user);
			if (userFind != null) {
				// get user
				userVali = (Usuario) getUsuariosGEDO(user, pass);
				// validacion de user valido
				if (null != userVali) {
					// nombre y apellido
					String [] nombre = userVali.getNombreApellido().split(" "); 
					// Request out idUser
					userData.setIdUser(userVali.getUsername());
					// name
					userData.setName(nombre[0]);
					// lastname
					userData.setLastName(nombre[1]);
					// 
					userData.setCurrentPosition(null);
					// get Organismo
					List<Map> organi = repart.getReparticionByUserName(user);
					// validation
					if (null != organi && !organi.isEmpty()) {
						// Iteration
						for (Object mapOrga : organi) { 
							// cat to Object[]
					    	Object[] obtenre = (Object[]) mapOrga;
					    	// In
							userOrganismosDTO userOr = new userOrganismosDTO();
							// setOrganismo
							userOr.setOrganism((String) obtenre[0]);
							// setSector
							userOr.setSector((String) obtenre[1]);
							// setPosition
							userOr.setPosition((String) obtenre[2]);
							// add list
							userOrganismo.add(userOr);
						}
					}
					// userValido
					status.setCode(1);
					status.setDesc("Usuario valido");  
					
				} else { 
					// Status out Error 
					status.setCode(2);
					status.setDesc("Usuario no valido: Contraseña incorrecta"); 
				}
				requestOut.setStatus(status);
				requestOut.setUser(userData);
			}  else {
				status.setCode(2);
				status.setDesc("Usuario no valido: Usuario no valido"); 
			}
		} catch (Exception e) {
			// Status out Error 
			status.setCode(4);
			status.setDesc("Error al obtener el usuario");  
		}
		
		// add status
		requestOut.setStatus(status);
		// add organismos
		userData.setPosition(userOrganismo);
		//
		requestOut.setUser(userData);
		return requestOut;
 
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public Usuario getUsuariosGEDO(String username, String password) {
		try {
			if (getUsuarioService().validarPasswordUsuario(username, password)) {
				Usuario userFind = getUsuarioService().obtenerUsuario(username);
				return userFind;
			}
		} catch (SecurityNegocioException e) {
			logger.error("Mensaje de error", e);
		}
		return null;
	}

}
