package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedorganismo.base.exception.ReparticionGenericDAOException;
import com.egoveris.sharedorganismo.base.exception.SectorInternoGenericDAOException;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.TarjetaDatosUsuarioBean;
import com.egoveris.te.base.service.tarjetausuario.TarjetaDatosUsuarioService;

@Service
@Transactional
public class TarjetaDatosUsuarioServiceImpl implements TarjetaDatosUsuarioService {

	final static Logger logger = LoggerFactory.getLogger(TarjetaDatosUsuarioServiceImpl.class);

	private Usuario datosUsuarioBean = new Usuario();
	private ReparticionBean reparticionBean = new ReparticionBean();
	private SectorInternoBean sectorInternoBean = new SectorInternoBean();
	@Autowired
	private UsuariosSADEService usuariosSADEService;
	@Autowired
	private ReparticionServ reparticionServ;
	@Autowired
	private SectorInternoServ sectorInternoServ;

	private TarjetaDatosUsuarioBean tarjetaDatosUsuarioBean = null;

	private final static String MSG_SIN_DETALLE = "No hay detalle para mostrar.";

	@SuppressWarnings("finally")
	@Override
	public TarjetaDatosUsuarioBean getTarjetaDatosUsuarioBean(final String username) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTarjetaDatosUsuarioBean(username={}) - start", username);
		}

		this.tarjetaDatosUsuarioBean = null;

		try {

			this.datosUsuarioBean = this.usuariosSADEService.getDatosUsuario(username);

			if (this.datosUsuarioBean != null) {
				this.reparticionBean = this.reparticionServ.buscarReparticionPorUsuario(username);

				this.sectorInternoBean = this.sectorInternoServ.buscarSectorInternoUsername(username);

				this.tarjetaDatosUsuarioBean = new TarjetaDatosUsuarioBean();
				this.tarjetaDatosUsuarioBean.setUser(this.datosUsuarioBean.getUsername());
				this.tarjetaDatosUsuarioBean.setApellidoNombre(this.datosUsuarioBean.getNombreApellido());
				this.tarjetaDatosUsuarioBean.setOcupacion(this.datosUsuarioBean.getOcupacion());

				this.tarjetaDatosUsuarioBean.setCodigoReparticion(this.reparticionBean.getCodigo());
				this.tarjetaDatosUsuarioBean.setDescripcionReparticion(this.reparticionBean.getNombre());

				this.tarjetaDatosUsuarioBean.setCodigoSector(this.sectorInternoBean.getCodigo());
				this.tarjetaDatosUsuarioBean.setDescripcionSector(this.sectorInternoBean.getNombre());
			}

		} catch (final ReparticionGenericDAOException e) {
			logger.error("Error al calcular la repartición del usuario.", e);
		} catch (final SectorInternoGenericDAOException e) {
			logger.error("Error al calcular el sector interno del usuario.", e);
		} catch (NullPointerException e) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTarjetaDatosUsuarioBean(String) - end - return value={}", tarjetaDatosUsuarioBean);
		}
		
		return tarjetaDatosUsuarioBean;
	}

	public void setDatosUsuarioBean(final Usuario datosUsuarioBean) {
		this.datosUsuarioBean = datosUsuarioBean;
	}

	public Usuario getDatosUsuarioBean() {
		return datosUsuarioBean;
	}

	public void setUsuariosSadeService(final UsuariosSADEService usuariosSadeService) {
		this.usuariosSADEService = usuariosSadeService;
	}

	public UsuariosSADEService getUsuariosSadeService() {
		return usuariosSADEService;
	}

	public void setReparticionBean(final ReparticionBean reparticionBean) {
		this.reparticionBean = reparticionBean;
	}

	public ReparticionBean getReparticionBean() {
		return reparticionBean;
	}

	public void setSectorInternoBean(final SectorInternoBean sectorInternoBean) {
		this.sectorInternoBean = sectorInternoBean;
	}

	public SectorInternoBean getSectorInternoBean() {
		return sectorInternoBean;
	}

	public void setTarjetaDatosUsuarioBean(final TarjetaDatosUsuarioBean tarjetaDatosUsuarioBean) {
		this.tarjetaDatosUsuarioBean = tarjetaDatosUsuarioBean;
	}

	public TarjetaDatosUsuarioBean getTarjetaDatosUsuarioBean() {
		return tarjetaDatosUsuarioBean;
	}

	@Override
	public String getMensajeSinDetalle() {
		return MSG_SIN_DETALLE;
	}

}
