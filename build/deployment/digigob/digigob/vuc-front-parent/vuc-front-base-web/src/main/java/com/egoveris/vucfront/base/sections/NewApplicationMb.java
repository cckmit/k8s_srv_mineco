package com.egoveris.vucfront.base.sections;

import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.service.ApplicationService;
import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.model.model.GrupoDTO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class NewApplicationMb extends AbstractMb {

  private static final long serialVersionUID = -6421788032713102172L;

  @ManagedProperty("#{applicationServiceImpl}")
  private ApplicationService applicationService;
  @ManagedProperty("#{loginMb}")
  private LoginMb login;

  List<GrupoDTO> grupoList;

  
  @PostConstruct
  public void init() {
    // Login
    if (login.getPersona() == null) {
      redirect(ConstantsUrl.LOGIN);
    }

    if (grupoList == null) {
      grupoList = applicationService.getAllGrupos();
    }
  }

  public List<GrupoDTO> getGrupoList() {
    return grupoList;
  }

  public String getStep1Url() {
    return ConstantsUrl.STEP1;
  }

  public void setApplicationService(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  public void setLogin(LoginMb login) {
    this.login = login;
  }

}