package com.egoveris.vucfront.base.mbeans;

import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.model.service.TareaService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MenuMb extends AbstractMb {

  private static final long serialVersionUID = -4411697447942740489L;

  @ManagedProperty("#{loginMb}")
  private LoginMb login;
  @ManagedProperty("#{notificacionServiceImpl}")
  private NotificacionService notificacionService;
  @ManagedProperty("#{tareaServiceImpl}")
  private TareaService tareaService;

  @PostConstruct
  private void init() {
    // Login
    if (login.getPersona() == null) {
      redirect(ConstantsUrl.LOGIN);
    }
  }

  public Integer getUnseenNotifications() {
    return notificacionService.getUnseenNotifications(login.getPersona());
  }

  public Integer getUnseenTareas() {
    return tareaService.getUnseenTareas(login.getPersona());
  }

  public void setLogin(LoginMb login) {
    this.login = login;
  }

  public void setNotificacionService(NotificacionService notificacionService) {
    this.notificacionService = notificacionService;
  }

  public void setTareaService(TareaService tareaService) {
    this.tareaService = tareaService;
  }

}