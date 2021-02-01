package com.egoveris.deo.web.satra;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Window;

import com.egoveris.cdeo.web.visualizarDocumento.Constantes;
import com.egoveris.cdeo.web.visualizarDocumento.ParametrosVisualizacionDocumento;

@SuppressWarnings("rawtypes")
public class LecturaDocumentoComposer extends GenericForwardComposer {

  private static final long serialVersionUID = 8789244233243763911L;
  private static final String ASSIGNEE = "assignee";
  
  @Wire
  Window winLect;

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    String numeroSade = Executions.getCurrent().getParameter("numeroSade");
    String usuario = (String) Executions.getCurrent().getSession().getAttribute("userName");
    String assignee = Executions.getCurrent().getParameter(ASSIGNEE);
    
    if (numeroSade == null || usuario == null) {
      throw new WrongValueException(
          Labels.getLabel("gedo.lecturaDocComposer.exception.errorDocSolicitado"));
    }

    Executions.getCurrent().getDesktop().setAttribute(Constantes.NUMERO_DOCUMENTO, numeroSade);
    Executions.getCurrent().getDesktop().setAttribute(Constantes.USER, usuario);
    
    Map<String, Boolean> mapParametrosVisualizacion = new HashMap<>();
    mapParametrosVisualizacion.put(ParametrosVisualizacionDocumento.PARAMETRO_NO_MOSTRAR_CERRAR, true);
    
    if (assignee != null) {
      mapParametrosVisualizacion.put(ASSIGNEE, true);
    } else {
      mapParametrosVisualizacion.put(ASSIGNEE, false);
    }
    
    Executions.getCurrent().getDesktop().setAttribute(Constantes.PARAMETROS_VISUALIZACION, mapParametrosVisualizacion);
    
    winLect = (Window) Executions.createComponents(Constantes._PARENT, null, null);
    winLect.doEmbedded();
  }

}
