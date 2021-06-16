package com.egoveris.te.base.vm;

import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.te.base.helper.DocumentosHelper;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.FiltroEE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DocumentosGridVM {

  private static final Logger logger = LoggerFactory.getLogger(DocumentosGridVM.class);
  private static final String LABEL_DOCUMENTO_NO_EXISTE = "ee.tramitacion.informacion.documentoNoExiste";

  // Servicio
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;

  // Helper
  private DocumentosHelper documentosHelper;
  private Map<String, String> mapTipoDocumento = new HashMap<>();

  // "Beans"
  private ExpedienteElectronicoDTO expediente;
  private boolean sinPase;
  private boolean conFiltro;
  private DocumentoDTO ultimoPase;
  private List<DocumentoDTO> listaDocumentosSinFiltrar;
  private List<DocumentoDTO> listaDocumentosFiltrada;
  private String totalDocumentos;

  /**
   * Init de DocumentosGridVM. Carga la grilla de documentos segun los
   * parametros dados.
   * 
   * @param sinPase
   *          Boolean que indica si la grilla de documentos es sin pase
   * @param conFiltro
   *          Boolean que indica si la grilla de documentos es con filtro
   * @param expediente
   *          Expediente
   */
  @Init
  public void init(@ExecutionArgParam("sinPase") Boolean sinPase,
      @ExecutionArgParam("conFiltro") Boolean conFiltro,
      @ExecutionArgParam("expediente") ExpedienteElectronicoDTO expediente) {

    setExpediente(expediente);

    if (sinPase != null) {
      setSinPase(sinPase);
    }

    if (conFiltro != null) {
      setConFiltro(conFiltro);
    }

    setListaDocumentosSinFiltrar(filtradoInicial(expediente.getDocumentos()));
    // Se deja en orden descendente
    Collections.reverse(listaDocumentosSinFiltrar);

    if (listaDocumentosSinFiltrar != null) {
      if (isSinPase()) {
        // Caso sin pase
        setListaDocumentosFiltrada(FiltroEE.filtrarSinPase(listaDocumentosSinFiltrar));
      } else {
        setListaDocumentosFiltrada(listaDocumentosSinFiltrar);
      }
    }

    documentosHelper = new DocumentosHelper(expediente);
  }

  /**
   * Comando que muestra el ultimo pase al presionar dicho check en la pantalla.
   * 
   * @param checked
   *          Boolean con el valor del check, (muestra o no el pase)
   */
  @Command
  @NotifyChange({ "listaDocumentosFiltrada", "totalDocumentos" })
  public void onMostrarUltimoPase(@BindingParam("checked") boolean checked) {
    if (checked) {
      if (ultimoPase == null) {
        setUltimoPase(FiltroEE.buscarUltimoPase(getListaDocumentosSinFiltrar()));
      }

      setListaDocumentosFiltrada(FiltroEE.filtrarConUltimoPase(getListaDocumentosSinFiltrar()));
    } else {
      listaDocumentosFiltrada.remove(ultimoPase);
    }
  }

  /**
   * Comando que previsualiza un documento dado de la grilla
   * 
   * @param documento
   */
  @Command
  public void onVisualizarDocumento(@BindingParam("documento") DocumentoDTO documento) {
    try {
      documentosHelper.visualizarDocumento(documento);
    } catch (SinPrivilegiosException e) {
      logger.error("Error en DocumentosPaseVM.onVisualizarDocumento(): ", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoReservadoNoVisualizable"),
          Labels.getLabel(LABEL_DOCUMENTO_NO_EXISTE), Messagebox.OK, Messagebox.EXCLAMATION);
    } catch (Exception e) {
      logger.error("Error en DocumentosPaseVM.onVisualizarDocumento(): ", e);
      Messagebox.show(
    		  Labels.getLabel("ee.tramitacion.error.previsualizacion"),
          documento.getNumeroSade().concat(".pdf"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Comando que descarga un documento dado de la grilla
   * 
   * @param documento
   */
  @Command
  public void onDescargarDocumento(@BindingParam("documento") DocumentoDTO documento) {
    try {
      documentosHelper.descargarDocumento(documento);
    } catch (SinPrivilegiosException e) {
      logger.error("Error en DocumentosPaseVM.onDescargarDocumento() - SinPrivilegiosException: ",
          e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoReservadoNoVisualizable"),
          Labels.getLabel(LABEL_DOCUMENTO_NO_EXISTE), Messagebox.OK, Messagebox.EXCLAMATION);
    } catch (DocumentoNoExisteException e) {
      logger.error(
          "Error en DocumentosPaseVM.onDescargarDocumento() - DocumentoNoExisteException: ", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExisteEnRepositorio"),
          Labels.getLabel(LABEL_DOCUMENTO_NO_EXISTE), Messagebox.OK, Messagebox.EXCLAMATION);
    } catch (ParametroInvalidoConsultaException | ErrorConsultaDocumentoException e) {
      logger.error("Error en DocumentosPaseVM.onDescargarDocumento(): ", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.errorDescargaDocumento"),
          Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Comando que descarga todos los documentos de la grilla. Sea esta con pase,
   * sin pase, o con filtro.
   */
  @Command
  public void onDescargarTodos() {
    try {
      String descargando = "CON PASE";

      if (conFiltro) {
        descargando = "FILTRO";
      } else if (sinPase) {
        descargando = "SIN PASE";
      }

      documentosHelper.descargarTodos("Documentos-" + getExpediente().getCodigoCaratula(),
          getListaDocumentosFiltrada(), descargando, null);
    } catch (Exception e) {
      logger.error("Error en DocumentosPaseVM.onDescargarTodos(): ", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.errorDescargaDocumento"),
          Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Realiza el filtrado inicial de los documentos
   * 
   * @param documentos
   * @return
   */
  public List<DocumentoDTO> filtradoInicial(List<DocumentoDTO> documentos) {
    List<DocumentoDTO> listaFiltrada = new ArrayList<>();

    for (DocumentoDTO documento : documentos) {
      if (documento.getDefinitivo() != null && documento.getDefinitivo()) {
        listaFiltrada.add(documento);
      }
    }

    return listaFiltrada;
  }

  // - LOGICA PERSONALIZADA GRILLA -

  /**
   * Obtiene el valor de la columna Tipo Documento para un documento dado
   * 
   * @param documento
   * @return
   */
  public String labelTipoDocumento(DocumentoDTO documento) {
    String tipoDocStr = "";

    if (documento.getTipoDocAcronimo() != null) {
      if (mapTipoDocumento.containsKey(documento.getTipoDocAcronimo())) {
        tipoDocStr = mapTipoDocumento.get(documento.getTipoDocAcronimo());
      } else {
        try {
          tipoDocStr = tipoDocumentoService.obtenerTipoDocumento(documento.getTipoDocAcronimo())
              .toString();
        } catch (NullPointerException e) {
          logger.warn("Error en DocumentosGridVM.labelTipoDocumento(): ", e);
          tipoDocStr = documento.getTipoDocAcronimo();
        }

        mapTipoDocumento.put(documento.getTipoDocAcronimo(), tipoDocStr);
      }
    }

    return tipoDocStr;
  }

  /**
   * Obtiene el valor de la columna Numero Documento para un documento dado
   * 
   * @param documento
   * @return
   */
  public String labelNumeroDocumento(DocumentoDTO documento) {
    String numeroDoc = "";

    if (documento.isSubsanadoLimitado()) {
      numeroDoc = documento.getNumeroSade().substring(0, 12) + "XXXX"
          + documento.getNumeroSade().substring(16, 22) + "XXXX";
    } else if (documento.getNumeroSade() != null) {
      numeroDoc = documento.getNumeroSade();
    }

    return numeroDoc;
  }

  /**
   * Retorna si el usuario tiene permisos para ver o descargar on documento dado
   * 
   * @param documento
   * @return
   */
  public boolean tienePermisoDocVistaDescarga(DocumentoDTO documento) {
    boolean permiso = true;
    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    if (documento.isSubsanadoLimitado() || (documento.getUsuarioSubsanador() != null
        && !documento.getUsuarioSubsanador().equals(loggedUsername))) {
      permiso = false;
    }

    return permiso;
  }

  // Getters - setters

  public ExpedienteElectronicoDTO getExpediente() {
    return expediente;
  }

  public void setExpediente(ExpedienteElectronicoDTO expediente) {
    this.expediente = expediente;
  }

  public boolean isSinPase() {
    return sinPase;
  }

  public void setSinPase(boolean sinPase) {
    this.sinPase = sinPase;
  }

  public boolean isConFiltro() {
    return conFiltro;
  }

  public void setConFiltro(boolean conFiltro) {
    this.conFiltro = conFiltro;
  }

  public DocumentoDTO getUltimoPase() {
    return ultimoPase;
  }

  public void setUltimoPase(DocumentoDTO ultimoPase) {
    this.ultimoPase = ultimoPase;
  }

  public List<DocumentoDTO> getListaDocumentosSinFiltrar() {
    return listaDocumentosSinFiltrar;
  }

  public void setListaDocumentosSinFiltrar(List<DocumentoDTO> listaDocumentosSinFiltrar) {
    this.listaDocumentosSinFiltrar = listaDocumentosSinFiltrar;
  }

  public List<DocumentoDTO> getListaDocumentosFiltrada() {
    return listaDocumentosFiltrada;
  }

  public void setListaDocumentosFiltrada(List<DocumentoDTO> listaDocumentosFiltrada) {
    this.listaDocumentosFiltrada = listaDocumentosFiltrada;
  }

  public String getTotalDocumentos() {
    totalDocumentos = "0";

    if (getListaDocumentosFiltrada() != null) {
      totalDocumentos = String.valueOf(getListaDocumentosFiltrada().size());
    }

    return totalDocumentos;
  }

  public void setTotalDocumentos(String totalDocumentos) {
    this.totalDocumentos = totalDocumentos;
  }

}
