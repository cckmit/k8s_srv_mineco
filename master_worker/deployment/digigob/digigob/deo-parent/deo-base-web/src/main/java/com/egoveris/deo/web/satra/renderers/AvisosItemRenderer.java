package com.egoveris.deo.web.satra.renderers;

import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.model.model.AvisoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.utils.Utilitarios;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Separator;

public class AvisosItemRenderer implements ListitemRenderer {

  private static final String ESTILO_ERROR = "font-weight: bold; color: red";

  public void render(Listitem item, Object data, int arg2) throws Exception {

    AvisoDTO aviso = (AvisoDTO) data;
    IUsuarioService usuarioService = (IUsuarioService) SpringUtil.getBean("usuarioServiceImpl");
    BuscarDocumentosGedoService buscarDocumentoGedoService = (BuscarDocumentosGedoService) SpringUtil
        .getBean("buscarDocumentosGedoServiceImpl");

    Listcell currentCell;
    new Listcell("").setParent(item);

    Map<String, Usuario> mapaUsuarios = usuarioService.obtenerMapaUsuarios();
    Usuario datosUsuario = mapaUsuarios.get(aviso.getUsuarioAccion());

    if (datosUsuario != null) {
      new Listcell(datosUsuario.getNombreApellido()).setParent(item);
    } else {
      new Listcell(aviso.getUsuarioAccion()).setParent(item);
    }

    String redirigidoPor = aviso.getRedirigidoPor();

    if (redirigidoPor != null) {
      datosUsuario = mapaUsuarios.get(redirigidoPor);
      if (datosUsuario != null) {
        new Listcell(datosUsuario.getNombreApellido()).setParent(item);
      } else {
        new Listcell(redirigidoPor).setParent(item);
      }
    } else {
      new Listcell("").setParent(item);
    }

    Listcell motivoCell;
    String motivoAviso = aviso.getMotivo();
    motivoAviso = motivoAviso == null ? StringUtils.EMPTY : motivoAviso;
    String motivo = Utilitarios.motivoParseado(motivoAviso, 20);
    motivoCell = new Listcell(motivo);
    motivoCell.setTooltiptext(motivoAviso);
    motivoCell.setParent(item);

    Listcell referenciaCell;
    String referenciaDocumento;
    String numeroSADE;
    String numeroEspecial;

    referenciaDocumento = aviso.getReferenciaDocumento();
    numeroSADE = aviso.getNumeroSadePapel() != null ? aviso.getNumeroSadePapel()
        : StringUtils.EMPTY;
    numeroEspecial = aviso.getNumeroEspecial() != null ? aviso.getNumeroEspecial()
        : StringUtils.EMPTY;

    motivo = null;
    motivo = Utilitarios.motivoParseado(referenciaDocumento, 20);
    referenciaCell = new Listcell(motivo);
    referenciaCell.setTooltiptext(referenciaDocumento);
    referenciaCell.setParent(item);

    new Listcell(
        Utilitarios.fechaToString(aviso.getFechaEnvio(), Constantes.FORMATO_FECHA_SIN_SEC))
            .setParent(item);
    new Listcell(
        Utilitarios.fechaToString(aviso.getFechaAccion(), Constantes.FORMATO_FECHA_SIN_SEC))
            .setParent(item);
    new Listcell(numeroSADE).setParent(item);
    new Listcell(numeroEspecial).setParent(item);

    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();

    if (aviso.getDocumento() != null) {
      Usuario usuario = usuarioService.obtenerUsuario(aviso.getUsuarioReceptor());
      Boolean puedeVisualizarDocumento = buscarDocumentoGedoService.puedeVerDocumentoConfidencial(
          buscarDocumentoGedoService.buscarDocumentoPorNumero(aviso.getNumeroSadePapel()),
          aviso.getUsuarioReceptor(), usuario.getCodigoReparticion());

      Image visualizarImage;
      if (puedeVisualizarDocumento) {
        visualizarImage = new Image("/imagenes/Descargar.png");
        visualizarImage.setTooltiptext(Labels.getLabel("gedo.inbox.avisos.descargar"));
        org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
            "onClick=inboxWindow$InboxComposer.onVisualizarDocumentoAviso");
      } else {
        visualizarImage = new Image("/imagenes/Prohibido.png");
        visualizarImage.setTooltiptext(Labels.getLabel("gedo.consultaDocumentos.noDescargar"));
      }
      visualizarImage.setParent(hbox);
      Separator separator = new Separator("horizontal");
      separator.setWidth("2px");
      separator.setParent(hbox);
    } else {
      Image visualizarImage = new Image("/imagenes/Prohibido.png");
      visualizarImage.setTooltiptext(Labels.getLabel("gedo.inbox.avisos.noDescargar"));
      visualizarImage.setParent(hbox);
      Separator separator = new Separator("horizontal");
      separator.setWidth("2px");
      separator.setParent(hbox);
    }
    Image redirigirImage = new Image("/imagenes/Redirigir.png");
    redirigirImage.setTooltiptext(Labels.getLabel("gedo.inbox.avisos.redirigir"));
    redirigirImage.setParent(hbox);
    Popup popup = (Popup) item.getFellowIfAny("usuariosListPopUp");
    redirigirImage.setPopup(popup);
    Separator separator1 = new Separator("horizontal");
    separator1.setWidth("2px");
    separator1.setParent(hbox);

    Image eliminarImage = new Image("/imagenes/Eliminar.png");
    eliminarImage.setTooltiptext(Labels.getLabel("gedo.inbox.avisos.eliminar"));
    eliminarImage.setParent(hbox);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminarImage,
        "onClick=inboxWindow$InboxComposer.onEliminarAvisosSeleccionados");
    hbox.setParent(currentCell);

    // Si no tiene número SADE, entonces es un error. Se muestra en rojo
    // TODO IDENTIFICAR MEJOR UN AVISO DE ERROR
    if (aviso.getDocumento() == null) {
      Iterator iteratorListItem = item.getChildren().iterator();
      while (iteratorListItem.hasNext()) {
        Object hijo = iteratorListItem.next();
        if (hijo instanceof Listcell) {
          ((Listcell) hijo).setStyle(ESTILO_ERROR);
        }
      }
    }

    // TODO
    /*
     * Tener en cuenta que al avanzar en la versión de ZK, es posible que el
     * comportamiento del checkMark cambie, por lo que quizás sea necesario
     * adicionarle instrucciones, con el fin de asegurar que al hacer click
     * sobre el item, solo tome éste.
     */
  }

}
