package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoDetalle;
import com.egoveris.deo.model.model.DocumentoSolr;
import com.egoveris.deo.model.model.TipoDocumentoDTO;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public interface BuscarDocumentosGedoService {

  public boolean existenDocumentosTipo(TipoDocumentoDTO tipoDocumento);

  /**
   * Busca el documento generado a partir del proceso dado.
   * 
   * @param workflowId
   *          : Identificador del proceso.
   * @return Documento: Objeto Documento correspondiente.
   */
  public DocumentoDTO buscarDocumentoPorProceso(String workflowId);

  /**
   * Busca directamente en la base por numero sade. SIN SOLR!
   * 
   * @param numeroSADE
   * @return
   */
  public DocumentoDTO buscarDocumentoPorNumero(String numeroSADE);

  /**
   * TODO poner paginador
   * 
   * @param reqSolr
   * @return
   */
  public Page<DocumentoSolr> buscarDocumentos(ConsultaSolrRequest reqSolr);

  /**
   * Busca directamente en la base por numero sade. SIN SOLR!
   * 
   * @param numeroSADE
   * @return
   */
  public Page<DocumentoSolr> buscarDocPorNumero(String numeroSADE, String acronimoDoc);

  /**
   * Busca directamente en la base por numero especial. SIN SOLR!
   * 
   * @param nroDocEspecial
   * @return
   */
  public Page<DocumentoSolr> buscarDocEspecial(String nroDocEspecial);

  /**
   * Busca directamente en la base por numero sade papel. SIN SOLR!
   * 
   * @param nroDocSadePapel
   * @return
   */
  public Page<DocumentoSolr> buscarDocSadePapel(String nroDocSadePapel);

  /**
   * Valida el permiso de visualización para documentos confidenciales. Si el
   * usuario cumple con una de las siguientes condiciones: 1. Es el generador
   * del documento. Aplica en especial para documentos importados o
   * incorporados. 2. Participó en tareas de producción, revisión y/o firma,
   * durante el proceso de generación del documento. 3. Pertenece a la
   * repartición del usuario que genero del documento, y tiene habilitado el
   * permiso en el LDAP para ver documentos confidenciales.
   * 
   * @param documento
   * @param usuario
   * @param reparticionUsuario
   * @return true si el usuario dado puede ver el documento confidencial, false
   *         en caso contrario.
   */
  public Boolean puedeVerDocumentoConfidencial(DocumentoDTO documento, String usuario,
      String reparticionUsuario);

  public DocumentoDetalle buscarDocumentoDetalle(String numeroSade, String loggedUsername);

  /**
   * Busca por algun criterio los documentos, usa una transformacion de tipo de
   * objeto
   * 
   * @param <code>java.util.Map</code>
   *          inputMap con la siguiente estructura, <lu>
   *          <li><code>java.lang.String</code>numeroEspecial (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Integer</code>id (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>numero (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>reparticion (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>anio (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code> usuarioGenerador (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>motivo (el valor es opcional)
   *          <li/>
   *          <li><code>List<java.lang.String></code>firmantes (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>TipoDocumento</code>tipoDocumento (el valor es opcional)
   *          <li/>
   *          <li><code>java.util.Date</code> fechaCreacion (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code>mostrarDescargas (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code> noMostrarDescargas (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>List<DocumentoMetadata></code>listaMetadatos (el valor
   *          es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>workflowOrigen (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code> workflowNoDisponible = false
   *          (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code>workflowDisponible = true; (el
   *          valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code> usuarioIniciador (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>numeroSadePapel (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>TipoReserva</code>tipoReserva (el valor es opcional)
   *          <li/>
   *          <li><code>Set<ReparticionAcumulada></code> reparticionesAcumuladas
   *          (el valor es opcional)
   *          <li/></lu>
   * @return <code>java.util.List<Documento></code> documento
   */
  public List<DocumentoDTO> buscarDocumentosPorCriterio(Map inputMap);

  /**
   * Busca por algun criterio en el detalle
   * 
   * @param <code>java.util.Map</code>
   *          inputMap con la siguiente estructura, <lu>
   *          <li><code>java.lang.String</code>numeroEspecial (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Integer</code>id (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>numero (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>reparticion (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>anio (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code> usuarioGenerador (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>motivo (el valor es opcional)
   *          <li/>
   *          <li><code>List<java.lang.String></code>firmantes (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>TipoDocumento</code>tipoDocumento (el valor es opcional)
   *          <li/>
   *          <li><code>java.util.Date</code> fechaCreacion (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code>mostrarDescargas (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code> noMostrarDescargas (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>List<DocumentoMetadata></code>listaMetadatos (el valor
   *          es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>workflowOrigen (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code> workflowNoDisponible = false
   *          (el valor es opcional)
   *          <li/>
   *          <li><code>java.lang.Boolean</code>workflowDisponible = true; (el
   *          valor es opcional)
   *          <li/>
   *          <li><code>java.lang.String</code> usuarioIniciador (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>java.lang.String</code>numeroSadePapel (el valor es
   *          opcional)
   *          <li/>
   *          <li><code>TipoReserva</code>tipoReserva (el valor es opcional)
   *          <li/>
   *          <li><code>Set<ReparticionAcumulada></code> reparticionesAcumuladas
   *          (el valor es opcional)
   *          <li/></lu>
   * @return <code>java.util.List<DocumentoDetalle></code> documento
   */
  public List<DocumentoDetalle> buscarDocumentosDetallePorCriterio(Map inputMap);

  public Page<DocumentoSolr> buscarDocPorActuacion(String numeroSADE, String acronimoActuacion);

  public boolean puedeVerDocumentoGedo(DocumentoDTO documento, String reparticion, String usuario);

  public DocumentoDetalle buscarDocumentoDetalle(String numeroSade, String loggedUsername,
      boolean assignee);

  public List<DocumentoDTO> buscarDocumentosADepurar();
}
