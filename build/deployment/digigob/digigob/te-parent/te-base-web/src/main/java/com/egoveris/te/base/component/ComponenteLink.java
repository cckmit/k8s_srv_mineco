package com.egoveris.te.base.component;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.PermisoException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TrataIntegracionReparticionDTO;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;


public class ComponenteLink extends BaseComponenteLink {
 
 private static Logger logger = LoggerFactory.getLogger(ComponenteLink.class);
 
    @Autowired
    private ConfiguracionInicialModuloEEFactory configuracionInicialModuloEEFactory;

    protected ExpedienteElectronicoDTO expedienteElectronico = new ExpedienteElectronicoDTO();
    private Tarea selectedTask;
    private Tarea task;
    private Hbox hbox;
    private Image linkImage = null;
    private Label linkLabel = null;
    private String eventoClick = null;

    public ComponenteLink() {
    }

    /**
     * Constructor con parámetros
     * @param <code>ExpedienteElectronico</code>EE
     * @param <code>Task</code>selectedTask
     * @param <code>Task</code>task
     * @param <code>Hbox</code>hbox
     */
    public ComponenteLink(ExpedienteElectronicoDTO expedienteElectronico, Tarea task, Hbox hbox, String eventoClick) {
        if (expedienteElectronico != null) {
            this.expedienteElectronico = expedienteElectronico;
        }

        this.task = task;
        this.hbox = hbox;
        this.eventoClick = eventoClick;

        render();
    }

    protected void initComponentLink(ExpedienteElectronicoDTO expedienteElectronico, Tarea task, Hbox hbox, String eventoClick) {
        // Control para que las solicitudes no tengas link a BAC
     if(!"".equals(task.getCodigoExpediente())&& expedienteElectronico!= null){
         this.expedienteElectronico = expedienteElectronico;
         this.task = task;
         this.hbox = hbox;
         this.eventoClick = eventoClick;
         
         render();
        }
    }
    
    
    public boolean usuarioPerteneceAReparticionParaSistemaExterno(String usuario,ParametrosSistemaExternoDTO params){
     UsuariosSADEService service = (UsuariosSADEService) SpringUtil.getBean(ConstantesServicios.USUARIOS_SADE_SERVICE);
     Usuario u = service.getDatosUsuario(usuario);
     if(u == null){
      return false;
     }
     for(TrataIntegracionReparticionDTO r : params.getReparticionesIntegracion()){
      if(r.isHabilitada() && (r.getCodigoReparticion().equalsIgnoreCase("--TODAS--") 
        || r.getCodigoReparticion().equalsIgnoreCase(u.getCodigoReparticion()))
        ){
       return true;
      }
     }
     return false;
    }

    protected void render() {
        try {
         String sistemaCreador = expedienteElectronico.getSistemaCreador();
         ParametrosSistemaExternoDTO parametrosSistemaExterno;
         this.task.setSistemaExterno(sistemaCreador);
         parametrosSistemaExterno= this.getConfiguracionInicialModuloEEFactory().obtenerParametrosPorTrata(expedienteElectronico.getTrata().getId());
          
      if (ConstantesWeb.SISTEMA_BAC.equals(sistemaCreador)){
       
       if (checkeoPermisoPorFuncion(parametrosSistemaExterno, expedienteElectronico.getSistemaCreador())) {
        this.setSelectedTask(task);
        armoLinkSistema(parametrosSistemaExterno);
       }
       return;
      }
      if(parametrosSistemaExterno.getCodigo().equalsIgnoreCase(ConstantesWeb.SISTEMA_AFJG)){
       return;
      }
      String[] workflow = task.getTask().getExecutionId().split("\\.");
      if(workflow[0] == null  ||  !"solicitud".equals(workflow[0])){
       return;
      }
      
      if(!usuarioPerteneceAReparticionParaSistemaExterno(task.getTask().getAssignee(),parametrosSistemaExterno)){
       return;
      }
       
       this.setSelectedTask(task);
       armoLinkSistema(parametrosSistemaExterno);
    return;
        } catch (Exception e) {
         logger.error("error al obtener parametros", e);
        }

        return;
    }
    
    
    
 private void armoLinkSistema(
   ParametrosSistemaExternoDTO parametrosSistemaExterno) {
  this.linkImage = new Image("/imagenes/arrowright16x16.png");
  this.linkImage.setHover("/imagenes/arrowrightgreen16x16.png");
  setComponentDisabled(this.eventoClick, this.linkImage);
  this.linkImage.setParent(this.hbox);
  String prefijo = "Ir a "; 
  this.linkLabel = new Label(prefijo + parametrosSistemaExterno.getCodigo());
  this.linkLabel.setTooltiptext(prefijo + "sistema " + parametrosSistemaExterno.getCodigo());  
  
  setComponentDisabled(this.eventoClick, this.linkLabel);
  this.linkLabel.setParent(this.hbox);
 }


 /**
    * Link.setDisabled(<code>Component</code> component,<code>Boolean</code>) : Se chequea los permisos de la
    * accion para habilitar o deshabilitar el link.
    */
    public void setComponentDisabled(String onClickPath, Component componentRef) {
        try {
            String eventString = (((this.getSelectedTask().getCodigoExpediente() != null) && !"".equalsIgnoreCase(this.getSelectedTask().getCodigoExpediente())) ? ("onClick=" + onClickPath)
                                                                                                                                                                 : "onClick=null");
            org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(componentRef, eventString);
        } catch (Exception e) {
         logger.error("error al formar expediente", e);
        }
    }

    private ExpedienteElectronicoDTO getExpedienteElectronico() {
        return this.expedienteElectronico;
    }

    public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
        this.expedienteElectronico = expedienteElectronico;
    }

    public Tarea getSelectedTask() {
        return this.selectedTask;
    }

    public void setSelectedTask(Tarea selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Tarea getTask() {
        return this.task;
    }

    public void setTask(Tarea task) {
        this.task = task;
    }

    public Hbox getHbox() {
        return this.hbox;
    }

    public void setHbox(Hbox hbox) {
        this.hbox = hbox;
    }

    public Image getLinkImage() {
        return this.linkImage;
    }

    public void setLinkImage(Image linkImage) {
        this.linkImage = linkImage;
    }

    public Label getLinkLabel() {
        return this.linkLabel;
    }

    public void setLinkLabel(Label linkLabel) {
        this.linkLabel = linkLabel;
    }

    public ConfiguracionInicialModuloEEFactory getConfiguracionInicialModuloEEFactory() {
        return this.configuracionInicialModuloEEFactory;
    }

    public void setConfiguracionInicialModuloEEFactory(ConfiguracionInicialModuloEEFactory configuracionInicialModuloEEFactory) {
        this.configuracionInicialModuloEEFactory = configuracionInicialModuloEEFactory;
    }

    /***
     * Aca se pueden cambiar la logica de permisos
     */
    @Override
    protected boolean checkeoPermiso(String permisoFuncionId)
        throws PermisoException {
        
        return false;
    }
}
