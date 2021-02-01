package com.egoveris.edt.web.pl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Window;

public class ABMescritorioComposer extends GenericForwardComposer {

	
  private static final long serialVersionUID = 5135211429061891529L;
  private Window abmEscritorio;

  @Override
  public void doAfterCompose(Component c) throws Exception {

    super.doAfterCompose(c);
  }

  public Window getAbmEscritorio() {
    return abmEscritorio;
  }

  public void setAbmEscritorio(Window abmEscritorio) {
    this.abmEscritorio = abmEscritorio;
  }

}
