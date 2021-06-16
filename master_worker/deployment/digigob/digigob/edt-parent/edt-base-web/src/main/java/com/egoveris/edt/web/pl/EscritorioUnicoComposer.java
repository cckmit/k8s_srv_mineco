/**
 * 
 */
package com.egoveris.edt.web.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Window;

import com.egoveris.edt.web.common.BaseComposer;

/**
 * @author pfolgar
 * 
 */
@SuppressWarnings("serial")
public class EscritorioUnicoComposer extends BaseComposer {

  private Window escritorioUnico;
  private AnnotateDataBinder binderEscritorioUnico;
  private String userName;
  Session csession = null;

  @Override
  public void doAfterCompose(Component c) throws Exception {

    super.doAfterCompose(c);
    csession = Executions.getCurrent().getDesktop().getSession();
    userName = getUserName();
    binderEscritorioUnico = new AnnotateDataBinder(c);
    String ldapEntorno = (String) SpringUtil.getBean("ldapEntorno");
    
    binderEscritorioUnico.loadAll();
  }

  public void refreshInbox() {
    this.binderEscritorioUnico.loadAll();
  }

  public Window getEscritorioUnico() {
    return escritorioUnico;
  }

  public void setEscritorioUnico(Window escritorioUnico) {
    this.escritorioUnico = escritorioUnico;
  }

  public AnnotateDataBinder getBinderEscritorioUnico() {
    return binderEscritorioUnico;
  }

  public void setBinderEscritorioUnico(AnnotateDataBinder binderEscritorioUnico) {
    this.binderEscritorioUnico = binderEscritorioUnico;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
