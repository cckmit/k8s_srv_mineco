package com.egoveris.edt.web.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Window;

public class SindicaturaComposer extends GenericForwardComposer {

	
  /**
  * 
  */
  private static final long serialVersionUID = -2616445955751957100L;
  private Window sindicatura;

  @Override
  public void doAfterCompose(Component c) throws Exception {

    super.doAfterCompose(c);
  }

  public Window getSindicatura() {
    return sindicatura;
  }

  public void setSindicatura(Window sindicatura) {
    this.sindicatura = sindicatura;
  }

}
