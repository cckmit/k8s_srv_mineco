package com.egoveris.vucfront.base.sections;

import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.service.ExpedienteService;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MyApplicationsMb extends AbstractMb {

  private static final long serialVersionUID = 5875730572380540968L;

  @ManagedProperty("#{expedienteServiceImpl}")
  private ExpedienteService expedienteService;
  @ManagedProperty("#{loginMb}")
  private LoginMb login;

  private List<ExpedienteBaseDTO> expedientesList;

  public void init() {
    // Login
    if (login.getPersona() == null) {
      redirect(ConstantsUrl.LOGIN);
    }

    expedientesList = expedienteService.getExpedientesBaseByPersona(login.getPersona());
  }

	public void cmdViewExpediente(ExpedienteBaseDTO expediente) {
		if (expediente.getEstadoTramite() != null && expediente.getEstadoTramite().getId().equals(1l)) {
			ExpedienteFamiliaSolicitudDTO expedienteFamiliaSolicitud = expedienteService.getExpedienteFamiliaSolicitudById(expediente.getId());
			int step = expedienteFamiliaSolicitud.getStep().intValue();
			String url = null;
			switch(step) {
			case 1:
				url = ConstantsUrl.STEP1WITHEXP.concat(expediente.getId().toString());
				break;
			case 2:
				url = ConstantsUrl.STEP2.concat(expediente.getId().toString());
				break;
			default:
				url = ConstantsUrl.STEP3.concat(expediente.getId().toString());
			}
			
			redirect(url);
		} else {
			redirect(ConstantsUrl.TOMAVISTA.concat(expediente.getId().toString()));
		}
	}

  public List<ExpedienteBaseDTO> getExpedientesList() {
    return expedientesList;
  }

  public void setExpedienteService(ExpedienteService expedienteService) {
    this.expedienteService = expedienteService;
  }

  public void setLogin(LoginMb login) {
    this.login = login;
  }

}