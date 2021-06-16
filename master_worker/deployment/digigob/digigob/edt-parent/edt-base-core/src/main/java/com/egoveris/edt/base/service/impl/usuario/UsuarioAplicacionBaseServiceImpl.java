package com.egoveris.edt.base.service.impl.usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.usuario.UsuarioBuzonGrupal;
import com.egoveris.edt.base.model.eu.usuario.UsuarioGenericoDTO;
import com.egoveris.edt.base.model.eu.usuario.UsuarioMisSistemas;
import com.egoveris.edt.base.model.eu.usuario.UsuarioMisSupervisados;
import com.egoveris.edt.base.model.eu.usuario.UsuarioMisTareas;
import com.egoveris.edt.base.repository.UsuariosRepository;
import com.egoveris.edt.base.repository.eu.usuario.UsuarioGenerico;
import com.egoveris.edt.base.service.usuario.IUsuarioAplicacionService;

@Transactional
public class UsuarioAplicacionBaseServiceImpl implements IUsuarioAplicacionService {

  private DozerBeanMapper mapper = new DozerBeanMapper();
  private UsuariosRepository usuariosRepository;

  public UsuariosRepository getUsuariosRepository() {
    return usuariosRepository;
  }

  public void setUsuariosRepository(UsuariosRepository usuariosRepository) {
    this.usuariosRepository = usuariosRepository;
  }

	@Override
	public List<Integer> buscarAplicacionesPorUsuario(String userName) {
	
		List<? extends UsuarioGenerico> usuarios = this.usuariosRepository.buscarAplicacionesPorUsuario(userName);
		HashSet<Integer> hasSet = new HashSet();
		//List<Integer> result = new ArrayList<>();
		for (UsuarioGenerico usuario : usuarios) {
			//if (!result.contains(usuario.getAplicacionID())) {
				//result.add(usuario.getAplicacionID()); 
				hasSet.add(usuario.getAplicacionID());
			//}
		}
		List<Integer> result = new ArrayList(hasSet);
		return result;
	}

  @Override
  public void insertarUsuarioAplicacion(UsuarioGenericoDTO usuarioAplicacion) {
    this.usuariosRepository.save(userFactory(usuarioAplicacion));

  }

  @Override
  public void borrarUsuarioAplicacion(UsuarioGenericoDTO usuarioAplicacion) {
    this.usuariosRepository.delete(userFactory(usuarioAplicacion));
  }

  @SuppressWarnings("unchecked")
  private <T extends UsuarioGenerico> T userFactory(UsuarioGenericoDTO usuario) {
    if (this.usuariosRepository instanceof com.egoveris.edt.base.repository.eu.usuario.UsuarioMisTareasRepository) {
      return (T) mapper.map(usuario, UsuarioMisTareas.class);
    } else if (this.usuariosRepository instanceof com.egoveris.edt.base.repository.eu.usuario.UsuarioMisSistemasRepository) {
      return (T) mapper.map(usuario, UsuarioMisSistemas.class);
    } else if (this.usuariosRepository instanceof com.egoveris.edt.base.repository.eu.usuario.UsuarioMisSupervisadosRepository) {
      return (T) mapper.map(usuario, UsuarioMisSupervisados.class);
    } else if (this.usuariosRepository instanceof com.egoveris.edt.base.repository.eu.usuario.UsuarioBuzonGrupalRepository) {
      return (T) mapper.map(usuario, UsuarioBuzonGrupal.class);
    } else {
      return null;
    }
  }

}