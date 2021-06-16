package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ExpedienteTrack;
import com.egoveris.te.base.service.ConsultaExpedienteEnArchService;
import com.egoveris.te.base.service.SolrServiceTrack;
import com.egoveris.te.base.util.ConstantesServicios;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * Composer asociado a la ventana ExpedienteDetalleTrack.zul la misma muestra
 * los datos de un expediente papel.
 * 
 * @date 04/02/2015
 * @author joflores
 */
public class DetalleExpedienteTrackComposer extends ExpedienteTrackActuacionComposer {

  private static final long serialVersionUID = 7927419923292192850L;

  ExpedienteTrack model;

  @Autowired
  Window detalleExpedienteTrackWindow;
  @Autowired
  Textbox fecha;
  @Autowired
  Textbox r1tipo;
  @Autowired
  Textbox r1numero;
  @Autowired
  Radio r1radio;
  @Autowired
  Textbox r1Just;
  @Autowired
  Longbox r1anio;
  @Autowired
  Textbox r2razon;
  @Autowired
  Radio r2radio;
  @Autowired
  Textbox r2CABA;
  @Autowired
  Textbox r2numero;
  @Autowired
  Textbox IAletra;
  @Autowired
  Longbox IAanio;
  @Autowired
  Longbox IAnumero;
  @Autowired
  Textbox IAactuacionExtraEjecutiva;
  @Autowired
  Textbox IARep;
  @Autowired
  Textbox IASec;
  @Autowired
  Textbox IARepSolicitante;
  @Autowired
  Textbox IAcuerpoAnexo;
  @Autowired
  Textbox codigoTrata;
  @Autowired
  Textbox motivo;
  @Autowired
  Textbox issibTextbox;
  @Autowired
  Textbox dominio;
  @Autowired
  Longbox numeroDenuncia;
  @Autowired
  Radio radioInterno;
  @Autowired
  Radio radioExterno;
  @Autowired
  Longbox fojas;
  @Autowired
  Textbox calle;
  @Autowired
  Textbox calleNumero;
  @Autowired
  Longbox pisoNumero;
  @Autowired
  Longbox dptoNumero;
  @Autowired
  Textbox entreCalle;
  @Autowired
  Textbox altura;
  @Autowired
  Textbox uFunc;
  @Autowired
  Textbox local;
  @Autowired
  Textbox yCalle;
  @Autowired
  Label codigoExpedienteDigitalizado;
  @Autowired
  Label lblCodExpDigit;
  @Autowired
  Textbox partidaABL;
  @Autowired
  Textbox esquina;
  @Autowired
  Textbox numeroCatrastal;
  @Autowired
  Textbox comentarios;
  @Autowired
  Textbox sector;
  @Autowired
  Textbox reparticion;
  @Autowired
  Button volver;

  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  private ConsultaExpedienteEnArchService consultaExpedienteEnArch;

  @Override
  public void doAfterCompose(Component comp) throws Exception {

    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    model = (ExpedienteTrack) Executions.getCurrent().getArg().get("expedienteTrack");
    if (model == null) {
      SolrServiceTrack solrServiceTrack = (SolrServiceTrack) SpringUtil
          .getBean(ConstantesServicios.SOLR_TRACK_SERVICE);
      String codigoExpedienteTrack = Executions.getCurrent().getParameter("codigoExpedienteTrack");
      String[] cod = desglosarCodigoTrack(codigoExpedienteTrack);
      model = solrServiceTrack.buscarExpedientePapel(cod[0], Integer.valueOf(cod[1]),
          Integer.valueOf(cod[2]), cod[4], cod[5]);
      volver.setDisabled(true);
      detalleExpedienteTrackWindow.setClosable(false);
    }
    String codigoExpedienteTrack = model.getLetraTrack() + "-" + model.getAnio() + "-"
        + completarConCeros(model.getNumero()) + "-" + "   " + "-"
        + model.getCodigoReparticionActuacion() + "-" + model.getCodigoReparticionUsuario();
    String numeroDigitalizado = null;
    try {
      numeroDigitalizado = consultaExpedienteEnArch
          .consultaNumeroDigitalizado(codigoExpedienteTrack);
    } catch (Exception e) {
    }
    if (numeroDigitalizado != null) {
      this.codigoExpedienteDigitalizado.setValue(numeroDigitalizado);
    } else {
      lblCodExpDigit.setVisible(false);
      codigoExpedienteDigitalizado.setVisible(false);
    }
    this.anio.setValue(Long.valueOf(model.getAnio()));
    this.numero.setValue(Long.valueOf(model.getNumero()));
    this.ex.setValue(model.getLetraTrack());
    this.codigoReparticion.setValue(model.getCodigoReparticionActuacion());
    this.codigoRepUsuario.setValue(model.getCodigoReparticionUsuario());
    this.codigoTrata.setValue(model.getCodigoTrataTrack());
    this.motivo.setValue(model.getDescripcion());
    this.issibTextbox.setValue(model.getIssibTrack());
    this.dominio.setValue(model.getDominioTrack());
    if (model.getDenunciaTrack() != null) {
      this.numeroDenuncia.setValue(Long.valueOf(model.getDenunciaTrack()));
    }
    this.calle.setValue(model.getCalleTrack());
    SimpleDateFormat d = new SimpleDateFormat("dd/MM");
    if (model.getFechaCreacion() != null) {
      this.fecha.setValue(d.format(model.getFechaCreacion()));
    }
    if (model.getNumerocalleTrack() != null) {
      // calleNumero.setValue(model.getNumerocalleTrack().toString());
    }
    this.partidaABL.setValue(model.getPartidaablTrack());
    if (model.getCatastralTrack() != null && !model.getCatastralTrack().equals("")) {
      this.numeroCatrastal.setValue(model.getCatastralTrack().toString());
    }
    if (model.getFojasTrack() != null) {
      fojas.setValue(Long.valueOf(model.getFojasTrack()));
    }
    if (model.getOrigenTrack() != null) {
      if (model.getOrigenTrack().equals("INTERNO")) {
        radioInterno.setChecked(true);
        sector.setValue(model.getSectorOrigenTrack());
      } else {
        radioExterno.setChecked(true);
        reparticion.setValue(model.getReparticionOrigenTrack());
      }
    }
    dominio.setValue(model.getDominioTrack());
    issibTextbox.setValue(model.getIssibTrack());
    sec.setValue(model.getSecuencia());
    r1tipo.setValue(model.getTipoDocumentoTrack());
    if (model.getNumeroDocumentoTrack() != null) {
      r1numero.setValue(model.getNumeroDocumentoTrack());
    }
    IAletra.setValue(model.getLetraIniciaTrack());
    if (model.getAnioIniciaTrack() != null) {
      IAanio.setValue(Long.valueOf(model.getAnioIniciaTrack()));
    }
    if (model.getNumeroActuacionIniciaTrack() != null) {
      IAnumero.setValue(Long.valueOf(model.getNumeroActuacionIniciaTrack()));
    }
    IARep.setValue(model.getReparticionActuacionIniciaTrack());
    IASec.setValue(model.getSecuenciaIniciaTrack());
    r2razon.setValue(model.getApellidonombrerazonTrack());
    partidaABL.setValue(model.getPartidaablTrack());
    comentarios.setValue(model.getObservacionesTrack());

  }

  private String completarConCeros(Integer numero) {
    String num = String.valueOf(numero);
    String resultado = "0";
    for (int i = 0; num.length() + i < 7; i++) {
      resultado += "0";
    }
    resultado += num;
    return resultado;
  }

  private String[] desglosarCodigoTrack(String codigoTrack) {
    return codigoTrack.split("-");
  }

  public void onClick$volver() {
    detalleExpedienteTrackWindow.detach();
  }

  public ConsultaExpedienteEnArchService getConsultaExpedienteEnArch() {
    return consultaExpedienteEnArch;
  }

  public void setConsultaExpedienteEnArch(
      ConsultaExpedienteEnArchService consultaExpedienteEnArch) {
    this.consultaExpedienteEnArch = consultaExpedienteEnArch;
  }

}
