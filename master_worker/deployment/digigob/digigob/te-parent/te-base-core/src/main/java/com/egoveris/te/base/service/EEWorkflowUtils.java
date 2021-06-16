package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.egoveris.commons.databaseconfiguration.propiedades.impl.DBAppPropertyImpl;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IMailGenericService;
import com.egoveris.te.base.service.iface.INotificacionEEService;

import freemarker.template.Template;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

@Service
public class EEWorkflowUtils implements IEEWorkflowUtils {

	private final String SISTEMA_EE = "EE";
	private final String LIBRE = "LIBRE";
	private final String TEMPLATE = "TEMPLATE";
	
	Logger logger = LoggerFactory.getLogger(EEWorkflowUtils.class);

	private ExpedienteElectronicoService expedienteElectronicoService; 

	private ExternalTransaccionService transaccionService;

	private TipoDocumentoService tipoDocumentoService;
	
	private IExternalGenerarDocumentoService generarDocumentoService;
	
	private ExternalFormularioService formularioService;
	
	
	private ProcessEngine processEngine;

	private DocumentoGedoService documentoGedoService;
	private DBAppPropertyImpl dBProperty;
	private INotificacionEEService notificacionEEService;
	private IMailGenericService iMailGenericService;
	private UsuariosSADEService usuariosSADEService;

	public EEWorkflowUtils() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public EEWorkflowUtils(ExpedienteElectronicoService expedienteElectronicoService,
			ExternalTransaccionService transaccionService, TipoDocumentoService tipoDocumentoService,
			IExternalGenerarDocumentoService generarDocumentoService, ExternalFormularioService formularioService,
			ProcessEngine processEngine, DocumentoGedoService documentoGedoService, DBAppPropertyImpl dBProperty,
			INotificacionEEService notificacionEEService, IMailGenericService iMailGenericService, 
			UsuariosSADEService usuariosSADEService) {
		super();
		this.expedienteElectronicoService = expedienteElectronicoService;
		this.transaccionService = transaccionService;
		this.tipoDocumentoService = tipoDocumentoService;
		this.generarDocumentoService = generarDocumentoService;
		this.formularioService = formularioService;
		this.processEngine = processEngine;
		this.documentoGedoService = documentoGedoService;
		this.dBProperty = dBProperty;
		this.notificacionEEService = notificacionEEService;
		this.iMailGenericService = iMailGenericService;
		this.usuariosSADEService = usuariosSADEService;
	}


	@Override
	public String generarDocumentoRespaldo(String acronimo, String referencia, Object json,
			ExpedienteElectronicoDTO ee) {
		String codigoDocumento = null;
		try {
			ScriptObjectMirror data = (ScriptObjectMirror)json;
			TipoDocumentoDTO tipoDocumento = tipoDocumentoService.consultarTipoDocumentoPorAcronimo(acronimo);
			Assert.notNull(tipoDocumento);
			RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
			request.setAcronimoTipoDocumento(acronimo);
			request.setReferencia(referencia);
			request.setSistemaOrigen(SISTEMA_EE);
			String assignee = this.getAssignee(ee.getIdWorkflow());
			Assert.notNull(assignee, "No se pudo obtener el assignee del workflow ID: " + ee.getIdWorkflow());
			request.setUsuario(assignee);

			if (tipoDocumento.getTipoProduccion().equals(TEMPLATE)) {
				Integer idTransaccion = this.crearTransaccion(tipoDocumento.getIdFormulario(), data);
				request.setIdTransaccion(idTransaccion);
			} else if (tipoDocumento.getTipoProduccion().equals(LIBRE)){
				request.setData(data.get("contenido").toString().getBytes());
			}
			ResponseExternalGenerarDocumento response = this.generarDocumentoService.generarDocumentoGEDO(request);
			codigoDocumento = response.getNumero();
		} catch (Exception e) { 
			logger.error("Ocurrió un error al generar el documento: " + e.getMessage(), e);
		}
		
		return codigoDocumento;
	}

	@Override
	public boolean vincularDocumentoRespaldo(ExpedienteElectronicoDTO ee, String nroDocumento) {
		try {
			String assignee = this.getAssignee(ee.getIdWorkflow());
			Assert.notNull(assignee, "No se pudo obtener el assignee del workflow ID: " + ee.getIdWorkflow());
			this.expedienteElectronicoService.vincularDocumentoGEDO_Definitivo(ee, nroDocumento, assignee);
			return checkDocVinculado(ee, nroDocumento);
		} catch (Exception e) {
			logger.error("Error al vincular el documento de respaldo. " + e.toString(), e);
			return false;
		}
	}

	@Override
	public Map<String, Object> getMap(String numeroDEO, ExpedienteElectronicoDTO ee) {
		Map<String, Object> mapForm = new HashMap<>();
		Assert.notNull(numeroDEO);
		
		String assignee = this.getAssignee(ee.getIdWorkflow());
		Assert.notNull(assignee, "No se pudo obtener el assignee del workflow ID: " + ee.getIdWorkflow());

		ResponseExternalConsultaDocumento response = null;
		try {
			response = documentoGedoService.consultarDocumentoPorNumero(numeroDEO, assignee);
		} catch (Exception e) {
			logger.error("Ha ocurrido un error de Comunicación con GEDO al consultar el documento " + numeroDEO + ". "
					+ e.getMessage(), e);
		}

		Assert.notNull(response, "No se obtuvo respuesta buscando el documento " + numeroDEO);
		Assert.notNull(response.getIdTransaccion(), "El documento " + numeroDEO + " no posee transacción");

		TransaccionDTO transaccionDTO = transaccionService.buscarTransaccionPorUUID(response.getIdTransaccion());
		for (ValorFormCompDTO valorForm : transaccionDTO.getValorFormComps()) {
			mapForm.put(valorForm.getInputName(), valorForm.getValor());
		}

		return mapForm;
	}

	@Override
	public String obtenerUltimoDoc(ExpedienteElectronicoDTO ee, String acronimo) {
		String numeroDoc = null;
		DocumentoDTO docDTO = null;
		
		Assert.notNull(acronimo);

		for (DocumentoDTO doc : ee.getDocumentos()) {
			if (doc.getTipoDocAcronimo().equals(acronimo)
					&& (docDTO == null || docDTO.getFechaAsociacion().before(doc.getFechaAsociacion()))) {
				docDTO = doc;
			}
		}

		if (docDTO != null) {
			numeroDoc = docDTO.getNumeroSade();
		}

		return numeroDoc;
	}

	@Override
	public boolean notificarTad(ExpedienteElectronicoDTO ee, String acronimo, String referencia) {
		String assignee = this.getAssignee(ee.getIdWorkflow());
		Assert.notNull(assignee, "No se pudo obtener el assignee del workflow ID: " + ee.getIdWorkflow());
		Assert.notNull(acronimo);

		try {
			DocumentoDTO doc = obtenerNumeroDocumentoSAS(acronimo, ee);
			Assert.notNull(doc, "No se pudo encontro un documento con el acronimo: " + acronimo + " en el expediente.");
			List<DocumentoDTO> listaDocTad = new ArrayList<>();
			listaDocTad.add(doc);

			notificacionEEService.altaNotificacionVUC(assignee, ee, listaDocTad, referencia);

			return true;
		} catch (Exception e) {
			logger.error("Error al notificar a TAD: " + ee.getNumero() + " " + referencia + ". " + e.toString(), e);
		}

		return false;
	}

	@Override
	public String obtenerValorBBDD(String clave) {
		String valorCampo = null;
		try {
			Properties p = dBProperty.getProperties();
			valorCampo = p.getProperty(clave);
		} catch (Exception e) {
			logger.error("Error al obtener la clave " + clave + ". " + e.toString(), e);
		}
		return valorCampo;
	}

	private Integer crearTransaccion(String formulario, ScriptObjectMirror data) {		
		TransaccionDTO transaccion = this.armarTransaccion(formulario, data);
		return this.transaccionService.grabarTransaccion(transaccion);
	}
	
	private TransaccionDTO armarTransaccion(String formulario, ScriptObjectMirror data) {
		FormularioDTO formularioDTO = this.formularioService.buscarFormularioPorNombre(formulario);
		TransaccionDTO transaccion = new TransaccionDTO();
		transaccion.setNombreFormulario(formulario);
		transaccion.setSistOrigen(SISTEMA_EE);
		Integer idFormComp = null; 
		Set<ValorFormCompDTO> valores = data.entrySet()
					.stream()
					.map(e -> new ValorFormCompDTO(
										buscarFormularioComponente(formularioDTO, e.getKey()), 
										e.getKey(), 
										e.getValue()
							  )
					)
					.collect(Collectors.toSet());		
		transaccion.setValorFormComps(valores);
		return transaccion;
	}
	
	private Integer buscarFormularioComponente(FormularioDTO formularioDTO, final String nombreFC) {
		FormularioComponenteDTO result = formularioDTO
											.getFormularioComponentes()
												.stream()
												.filter(fc -> fc.getNombre().equals(nombreFC))
												.findFirst().orElse(null);
		Assert.notNull(result, "El campo " + nombreFC + " no existe en el formulario " + formularioDTO.getNombre());
		return result.getId();
	}
	
	@Override
	public String getAssignee(ExpedienteElectronicoDTO ee) {
		return this.getAssignee(ee.getIdWorkflow());
	}
	
	@Override
	public String getAssignee(String workflowId) {
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery()
		          .executionId(workflowId);
		Assert.notNull(taskQuery, "No se pudo ejecutar la consulta para obtener el usuario asignado del workflow ID: " + workflowId);
		Task task = taskQuery.uniqueResult();
		Assert.notNull(task, "No se pudo obtener la tarea del workflow ID: " + workflowId);
		return task.getAssignee();
	}
	
	public boolean checkDocVinculado(ExpedienteElectronicoDTO ee, String nroDoc) {
		try {

			for (DocumentoDTO doc : ee.getDocumentos()) {
				if (doc.getNumeroSade().equalsIgnoreCase(nroDoc)) {
					return true;
				}
			}
			
		} catch (Exception e) {
			logger.error("Error al validar la vinculacion del documento de respaldo. " + e.toString(), e);
		}
		return false;
	}

	@Override
	public String get(String metodo) {
		Assert.notNull(metodo);

		HttpGet request = new HttpGet(metodo);
		String result = null;

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			logger.error("Error en el llamado al metodo GET. " + e.toString(), e);
		}
		
		Assert.notNull(result, "No se obtuvo respuesta del metodo GET " + metodo);

		return result;
	}

	@Override
	public String post(String metodo, Object json) {
		String result = null;
		HttpPost post = new HttpPost(metodo);

		if (json != null) {
			ScriptObjectMirror data = (ScriptObjectMirror)json;
		    ScriptObjectMirror jsonData = (ScriptObjectMirror) data.eval("JSON");
		    String stringify = (String) jsonData.callMember("stringify", json);
		    StringEntity requestEntity = new StringEntity(stringify, ContentType.APPLICATION_JSON);
			post.setEntity(requestEntity);
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			logger.error("Error en el llamado al metodo POST." + e.toString(), e);
		}

		Assert.notNull(result, "No se obtuvo respuesta del metodo POST " + metodo);

		return result;

	}

	private DocumentoDTO obtenerNumeroDocumentoSAS(String acronimo, ExpedienteElectronicoDTO ee) {
		List<DocumentoDTO> documentos = new ArrayList<>(ee.getDocumentos());
		Collections.sort(documentos, new Comparator<DocumentoDTO>() {
			public int compare(DocumentoDTO a1, DocumentoDTO a2) {
				return a2.getFechaAsociacion().compareTo(a1.getFechaAsociacion());
			}
		});

		for (DocumentoDTO doc : documentos) {
			if (doc.getTipoDocAcronimo().equals(acronimo)) {
				return doc;
			}
		}
		return null;
	}

	@Override
	public boolean enviarMail(String usuario, String asunto, Object json, String plantilla) {
		try {
			Assert.notNull(usuario, "El parametro usuario es oligatorio.");
			Assert.notNull(asunto, "El parametro asunto es oligatorio.");
			Assert.notNull(plantilla, "El parametro plantilla es oligatorio.");

			Template template = iMailGenericService.obtenerTemplate(plantilla);
			Map<String, String> variablesCorreo = new HashMap<>();
			Usuario user = usuariosSADEService.getDatosUsuario(usuario);
			Assert.notNull(user, "No se encontro el usuario " + usuario);

			if (json != null) {
				ScriptObjectMirror data = (ScriptObjectMirror) json;
				for (Entry<String, Object> entry : data.entrySet()) {
					if (entry.getValue() instanceof String) {
						variablesCorreo.put(entry.getKey(), (String) entry.getValue());
					} else {
						throw new NegocioException(
								"El valor del parametro " + entry.getKey() + " no es valido, solo se permite texto.");
					}
				}

			}

			iMailGenericService.enviarMail(template, user.getEmail(), variablesCorreo, asunto);

			return true;

		} catch (Exception e) {
			logger.error("Error al enviar mail al usuario " + usuario + ". " + e.toString(), e);
		}
		return false;
	}

}
