package com.egoveris.te.base.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.dozer.DozerBeanMapper;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.te.base.dao.ExpedienteElectronicoDAO;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.SinPersistirException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoDeIdentidadDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoIndex;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.service.iface.ISolrService;
import com.egoveris.te.base.util.ConstantesCore;
import com.egoveris.te.base.util.SolrUtils;

@Deprecated
/*
 * Si estás debugeando un servicio de aquí, migrarlo al nuevo repository.
 * @See ExpedienteElectronicoRepository
 */
@Repository
public class ExpedienteElectronicoDAOHbn extends HibernateDaoSupport implements ExpedienteElectronicoDAO {
  
 private static final Logger loger = LoggerFactory.getLogger(ExpedienteElectronicoDAOHbn.class);
 
	@Autowired
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void init() {
	  setSessionFactory(sessionFactory);
	}
	
	@Autowired
	private ExternalTransaccionService fcTransaction;

	@Autowired
	private String cantMesesEnvioAutomaticoGT;
	
	@Autowired
	private ExternalFormularioService fcFormulario;
	
	private static final int CANT_REGISTROS = 1000;
	
	@Autowired
	private ISolrService solrService;

	private static final Logger logger = LoggerFactory.getLogger(ExpedienteElectronicoDAOHbn.class);
	
	private DozerBeanMapper mapper = new DozerBeanMapper();

	@Transactional(propagation = Propagation.REQUIRED)
	public void guardar(Serializable obj) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("guardar(obj={}) - start", obj);
		}

		try {
			this.getHibernateTemplate().saveOrUpdate(obj);
		} catch (HibernateException he) {
			logger.error("guardar(Serializable)", he);

			throw new SinPersistirException("No se pudo persistir el documento: " + obj, he);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("guardar(Serializable) - end");
		}
	}

	public void eliminar(Serializable obj) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminar(obj={}) - start", obj);
		}

		try {
			this.getHibernateTemplate().delete(obj);
		} catch (HibernateException he) {
			logger.error("eliminar(Serializable)", he);

			throw new SinPersistirException("No se pudo eliminar el documento: " + obj, he);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminar(Serializable) - end");
		}
	}

	public void modificar(final Serializable obj) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("modificar(obj={}) - start", obj);
		}

		try {

			this.getHibernateTemplate().saveOrUpdate(obj);
		}

		catch (HibernateException he) {
			logger.error(he.toString());
			throw new SinPersistirException("No se pudo persistir el documento: " + he, he);
		} catch (Exception he) {
			logger.error(he.toString());
			throw new SinPersistirException("No se pudo persistir el documento: " + he, he);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modificar(Serializable) - end");
		}
	}

	@Override
	public void mergeExpedienteElectronico(Serializable obj) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("mergeExpedienteElectronico(obj={}) - start", obj);
		}

		Session s = null;
		try {
			s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			s.merge(obj);

		} catch (HibernateException he) {
			logger.error("mergeExpedienteElectronico(Serializable)", he);

			throw new SinPersistirException("No se pudo persistir el documento: " + obj, he);
		} finally {
			//this.releaseSession(s);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mergeExpedienteElectronico(Serializable) - end");
		}
	}

	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(Long idExpedienteElectronico, String sistemaOrigen) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(idExpedienteElectronico={}, sistemaOrigen={}) - start", idExpedienteElectronico, sistemaOrigen);
		}

		List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados;
		// este metodo sobrecarga el metodo de mismo nombre para devolver el
		// expediente con el codigo de trata ya que el actual no lo devuelve
		DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
		criteria.add(Restrictions.eq("id", idExpedienteElectronico));

		ExpedienteElectronicoDTO expedienteElectronico = (ExpedienteElectronicoDTO) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		listaExpedientesAsociados = expedienteElectronico.getListaExpedientesAsociados();
		List<ExpedienteAsociadoEntDTO> listaAux = new ArrayList<>();
		for (ExpedienteAsociadoEntDTO e : listaExpedientesAsociados) {
			ExpedienteAsociadoEntDTO easocaux;

			ExpedienteElectronicoDTO eaux = this.buscarExpedienteElectronico(e.getIdCodigoCaratula());
			if (eaux != null) {
				easocaux = e;
				easocaux.setTrata(eaux.getTrata().getCodigoTrata());
				listaAux.add(easocaux);
			} else {
				listaAux.add(e);
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(Integer, String) - end - return value={}", listaAux);
		}
		return listaAux;
	}

	public List<?> buscarId() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarId() - start");
		}

		final String QUERY = "select ee.id from ExpedienteElectronicoDTO  ee";

		try {
			List<?> find = this.getHibernateTemplate().find(QUERY);

			if (logger.isDebugEnabled()) {
				logger.debug("buscarId() - end - return value={}", find);
			}
			
			return find;
		} catch (HibernateException he) {
			logger.error("buscarId()", he);

			throw new SinPersistirException("No se pudo persistir el documento: " + he, he);
		}
	}

	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(Integer idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(idExpedienteElectronico={}) - start", idExpedienteElectronico);
		}

		List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados;

		DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
		criteria.add(Restrictions.eq("id", idExpedienteElectronico));

		ExpedienteElectronicoDTO expedienteElectronico = (ExpedienteElectronicoDTO) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		listaExpedientesAsociados = expedienteElectronico.getListaExpedientesAsociados();

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(Integer) - end - return value={}", listaExpedientesAsociados);
		}
		
		return listaExpedientesAsociados;
	}

	// Obtiene los EE que llevan 6 meses sin modificaciones, con tratas con
	// atributo esEnvioAutomaticoGT
	// y que no esten en Guarda Temporal.
	@SuppressWarnings("unchecked")
	public HashMap<String, ExpedienteElectronicoDTO> obtenerExpedienteElectronicoEnvioAutomaticoGT() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoEnvioAutomaticoGT() - start");
		}

		List<ExpedienteElectronicoDTO> listaExpedienteElectronico = null;
		HashMap<String, ExpedienteElectronicoDTO> mapEE = null;
		
		try {
			Calendar date = Calendar.getInstance();
			int cantMeses = Integer.parseInt(cantMesesEnvioAutomaticoGT.trim());
			date.add(Calendar.MONTH, -cantMeses);
			date.add(Calendar.DAY_OF_WEEK, +1);
			Session session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String query = "FROM ExpedienteElectronicoDTO EE JOIN FETCH EE.trata as trata "
					+ "WHERE trata.esEnvioAutomaticoGT = 1 AND EE.fechaModificacion < :fecha "
					+ "AND trata.workflow = 'solicitud' " + "AND EE.estado <> 'Guarda Temporal' "
					+ "AND EE.estado <> 'Solicitud Archivo' " + "AND EE.estado <> 'Archivo' ";

			Query hQuery = session.createQuery(query);
			hQuery.setDate("fecha", date.getTime());
			listaExpedienteElectronico = (List<ExpedienteElectronicoDTO>) hQuery.list();
			
			mapEE = new HashMap<>();
			for (ExpedienteElectronicoDTO ee : listaExpedienteElectronico) {
				mapEE.put(ee.getCodigoCaratula(), ee);
			}
		} catch (Exception e) {
		 loger.error("Error al buscar los expedientes", e);
			logger.error("Error al buscar los expedientes:" + e.getMessage(), e.getCause());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoEnvioAutomaticoGT() - end - return value={}", mapEE);
		}
		
		return mapEE;
	}

	public void executeDeltaImport() {
		if (logger.isDebugEnabled()) {
			logger.debug("executeDeltaImport() - start");
		}

		solrService.executeDeltaImport();

		if (logger.isDebugEnabled()) {
			logger.debug("executeDeltaImport() - end");
		}
	}

	@SuppressWarnings("unchecked")
	public List<ExpedienteElectronicoDTO> obtenerExpedienteElectronicoConEstadoArchivo() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoConEstadoArchivo() - start");
		}

		List<ExpedienteElectronicoDTO> listaExpedienteElectronico = null;
		
		try {
			Session session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String query = "FROM ExpedienteElectronicoDTO EE " + "WHERE EE.estado = 'Archivo'"
					+ "AND EE.fechaDepuracion is null";

			Query hQuery = session.createQuery(query);
			listaExpedienteElectronico = (List<ExpedienteElectronicoDTO>) hQuery.list();
		} catch (Exception e) {
		 loger.error("Error al buscar los expedientes:", e);
			logger.error("Error al buscar los expedientes:" + e.getMessage(), e.getCause());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoConEstadoArchivo() - end - return value={}", listaExpedienteElectronico);
		}
		
		return listaExpedienteElectronico;

	}

	public ExpedienteElectronicoDTO buscarExpedienteElectronico(Long idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronico(idExpedienteElectronico={}) - start", idExpedienteElectronico);
		}

		ExpedienteElectronicoDTO e = null;
		
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronico.class);
			criteria.add(Restrictions.eq("id", idExpedienteElectronico));
			e = (ExpedienteElectronicoDTO) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
			if (e != null) {
				e = subsanarOrdenDocumentos(e);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronico(Integer) - end - return value={}", e);
			}
			return e;
		} catch (HibernateException he) {
			logger.error("buscarExpedienteElectronico(Integer)", he);

			throw new SinPersistirException("No se pudo cargar el expediente de: " + idExpedienteElectronico, he);
		}

	}

	@SuppressWarnings("unchecked")
	// Este Metodo se utiliza solo para indexar los EE, al hacer un
	// full-import-desde servet
	public void buscarTodoslosExpedientesElectronicosEindexar() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodoslosExpedientesElectronicosEindexar() - start");
		}

		// List <ExpedienteElectronicoDTO> e =null;

		TransaccionDTO respFFCCDTO = null;
		HashMap<String, Object> mapValsffcc = null;
		Boolean flagIndexado = false;
		
		try {
			this.solrService.clearIndex();
			List<Integer> listaIds = (List<Integer>) this.buscarId();
			
			for (Integer id : listaIds) {
				DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
				criteria.add(Restrictions.eq("id", id));
				ExpedienteElectronicoDTO eAindexar = (ExpedienteElectronicoDTO) DataAccessUtils
						.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

				if (eAindexar != null) {
					flagIndexado = false;

					if (eAindexar.getDocumentos() != null && eAindexar.getDocumentos().size() > 0) {
						for (DocumentoDTO doc : eAindexar.getDocumentos()) {
							if (doc != null) {
								if (doc.getIdTransaccionFC() != null) {
									try {
										respFFCCDTO = this.fcTransaction
												.buscarTransaccionPorUUID(doc.getIdTransaccionFC().intValue());
										mapValsffcc = this.rellenarMapaEtiquetasComponentesFc(respFFCCDTO);
										if (mapValsffcc != null) {
											this.solrService.indexarFormularioControlado(eAindexar, mapValsffcc);
											flagIndexado = true;
										}
									} catch (Exception e1) {

										logger.error("Error FFCC Full-IMPORT-SOLR", e1);
									}

								}
							}
						}
					}
					
					if (!flagIndexado) {
						this.solrService.indexar(eAindexar);
					}

				}
			}

		} catch (HibernateException he) {
			logger.error("buscarTodoslosExpedientesElectronicosEindexar()", he);

			throw new SinPersistirException("Error", he);
		} catch (NegocioException he) {

			logger.error(he.getMessage(), he);
		}

		catch (Exception he) {
			logger.error("Exception en full-import para EE : ", he);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTodoslosExpedientesElectronicosEindexar() - end");
		}
	}

	private HashMap<String, Object> rellenarMapaEtiquetasComponentesFc(TransaccionDTO transaccionDTO)
			throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("rellenarMapaEtiquetasComponentesFc(transaccionDTO={}) - start", transaccionDTO);
		}

		HashMap<String, Object> mapaEtiquetas = new HashMap<>();
		FormularioDTO f = this.fcFormulario.buscarFormularioPorNombre(transaccionDTO.getNombreFormulario());
		
		for (ValorFormCompDTO vC : transaccionDTO.getValorFormComps()) {
			for (FormularioComponenteDTO fco : f.getFormularioComponentes()) {
				if (fco.getId().equals(vC.getIdFormComp())) {
					mapaEtiquetas.put(vC.getInputName(), fco.getEtiqueta());
				}
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("rellenarMapaEtiquetasComponentesFc(TransaccionDTO) - end - return value={}", mapaEtiquetas);
		}
		
		return mapaEtiquetas;
	}

	private ExpedienteElectronicoDTO subsanarOrdenDocumentos(ExpedienteElectronicoDTO e) {
		if (logger.isDebugEnabled()) {
			logger.debug("subsanarOrdenDocumentos(e={}) - start", e);
		}

		if (e.getDocumentos() != null && e.getDocumentos().size() > 0) {
			{
				List<DocumentoDTO> listaAuxiliarDepurada = tieneDuplicadosoNulos(e.getDocumentos());
				List<DocumentoDTO> listaSubSanada = new ArrayList<>();
				Integer i = 0;
				
				for (DocumentoDTO d : listaAuxiliarDepurada) {
					if (d != null && d.getNumeroSade() != null) {
						DocumentoDTO dSub;
						dSub = d;
						dSub.setPosicion(i);
						listaSubSanada.add(dSub);
						i++;
					}
				}
				
				e.setDocumentos(listaSubSanada);

				if (logger.isDebugEnabled()) {
					logger.debug("subsanarOrdenDocumentos(ExpedienteElectronicoDTO) - end - return value={}", e);
				}
				
				return e;
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("subsanarOrdenDocumentos(ExpedienteElectronicoDTO) - end - return value={}", e);
			}
			
			return e;
		}

	}

	private ArrayList<DocumentoDTO> tieneDuplicadosoNulos(List<DocumentoDTO> list) {
		if (logger.isDebugEnabled()) {
			logger.debug("tieneDuplicadosoNulos(list={}) - start", list);
		}

		ArrayList<DocumentoDTO> listaAux = new ArrayList<>();
		
		for (DocumentoDTO d : list) {
			if (d != null && d.getNumeroSade() != null) {
				listaAux.add(d);

			}
		}

		for (int i = 0; i < listaAux.size() - 1; i++) {
			for (int j = i + 1; j < listaAux.size(); j++) {
				if (listaAux.get(i).getNumeroSade() == listaAux.get(j).getNumeroSade()) {
					ArrayList<DocumentoDTO> returnArrayList = this.removerDocumentosDuplicadosCodigoSade(listaAux);
					if (logger.isDebugEnabled()) {
						logger.debug("tieneDuplicadosoNulos(List<DocumentoDTO>) - end - return value={}", returnArrayList);
					}
					
					return returnArrayList;
				}

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("tieneDuplicadosoNulos(List<DocumentoDTO>) - end - return value={}", listaAux);
		}
		
		return listaAux;
	}

	private ArrayList<DocumentoDTO> removerDocumentosDuplicadosCodigoSade(ArrayList<DocumentoDTO> lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("removerDocumentosDuplicadosCodigoSade(lista={}) - start", lista);
		}

		ArrayList<DocumentoDTO> depurados = new ArrayList<>();

		for (int i = 0; i < lista.size() - 1; i++) {
			for (int j = i + 1; j < lista.size(); j++) {
				if (lista.get(i).getNumeroSade().equals(lista.get(j).getNumeroSade())) {
					depurados.add(lista.get(j));
				}
			}
		}
		
		if (depurados.size() > 0) {
			lista.removeAll(depurados);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("removerDocumentosDuplicadosCodigoSade(ArrayList<DocumentoDTO>) - end - return value={}", lista);
		}
		
		return lista;
	}

	public ExpedienteElectronicoDTO buscarExpedienteElectronicoByIdTask(String idTask) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoByIdTask(idTask={}) - start", idTask);
		}

		ExpedienteElectronicoDTO e = null;
		
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
			criteria.add(Restrictions.eq("idWorkflow", idTask));
			e = (ExpedienteElectronicoDTO) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
			if (e != null) {
				e = subsanarOrdenDocumentos(e);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoByIdTask(String) - end - return value={}", e);
			}
			return e;
		} catch (HibernateException he) {
			logger.error("buscarExpedienteElectronicoByIdTask(String)", he);

			throw new SinPersistirException("No se pudo cargar el expediente de: " + idTask, he);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean buscarIdTrata(TrataDTO trata) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarIdTrata(trata={}) - start", trata);
		}

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
			criteria.add(Restrictions.eq("trata", trata));

			List<ExpedienteElectronicoDTO> listaExpedienteElectronico = (List<ExpedienteElectronicoDTO>) getHibernateTemplate()
					.findByCriteria(criteria);

			if (listaExpedienteElectronico.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarIdTrata(TrataDTO) - end - return value={}", true);
				}
				return true;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarIdTrata(TrataDTO) - end - return value={}", false);
				}
				return false;
			}
		} catch (HibernateException he) {
			logger.error("buscarIdTrata(TrataDTO)", he);

			throw new SinPersistirException("No se pudo obtener la trata", he);
		}
	}

	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorMetadatos(String username, String reparticion,
			TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoPorMetadatos(username={}, reparticion={}, trata={}, expedienteMetaDataList={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start", username, reparticion, trata, expedienteMetaDataList, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		Session session = null;

		try {
			this.getHibernateTemplate().setCacheQueries(true);

			List<ExpedienteElectronicoDTO> result = null;
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

			Query hqlQuery = null;
			result = crearQueryDinamica(username, reparticion, trata, expedienteMetaDataList, desde, hasta, session,
					hqlQuery, tipoDocumento, numeroDocumento, cuitCuil, estado);

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoPorMetadatos(String, String, TrataDTO, List<ExpedienteMetadata>, Date, Date, String, String, String, String) - end - return value={}", result);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> buscarNumerosExpedientesPorAssignee(String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesPorAssignee(usuario={}) - start", usuario);
		}

		List<String> resultado = null;
		try {
			Session session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String query = "SELECT (e.TIPO_DOCUMENTO || '-' || e.ANIO || '-' || e.NUMERO || '-   -' || e.CODIGO_REPARTICION_ACTUACION || '-' || e.CODIGO_REPARTICION_USUARIO) AS CODIGO_SADE "
					+ " FROM EE_EXPEDIENTE_ELECTRONICO e, JBPM4_TASK t  WHERE e.ID_WORKFLOW = "
					+ " t.execution_id_ and t.assignee_ = :usuario " + " ";
			Query hQuery = session.createSQLQuery(query);
			hQuery.setParameter("usuario", usuario);

			resultado = (List<String>) hQuery.list();
		} catch (Exception e) {
		 logger.error("", e);
			logger.error(e.getMessage(), e.getCause());
			logger.error("Error al buscar los expedientes del usuario: " + usuario);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesPorAssignee(String) - end - return value={}", resultado);
		}
		return resultado;

	}

	public List<String> buscarNumerosExpedientesBloqueadosPorAssignee(String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesBloqueadosPorAssignee(usuario={}) - start", usuario);
		}

		List<String> returnList = buscarNumerosExpedientesPorAssignee(usuario + ".bloqueado");
		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesBloqueadosPorAssignee(String) - end - return value={}", returnList);
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorMetadatosTramitacion(String username, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoPorMetadatosTramitacion(username={}, trata={}, expedienteMetaDataList={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start", username, trata, expedienteMetaDataList, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		Session session = null;
		Date hastaBusqueda = hasta;

		try {
			this.getHibernateTemplate().setCacheQueries(true);

			List<ExpedienteElectronicoDTO> result = null;
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

			StringBuilder inicio = new StringBuilder(); // (" where ");
			StringBuilder fin = new StringBuilder("  order by expedienteElectronico.fecha_creacion desc");
			StringBuilder medio = new StringBuilder();
			int i = 0;
			inicio.insert(0,
					" inner  join SOLICITUD_EXPEDIENTE  solicitud on expedienteElectronico.solicitud_iniciadora= solicitud.id  "
							+ " inner join SOLICITANTE  solicitante  on  solicitud.id= solicitante.id_solicitante "
							+ "inner join DOCUMENTO_DE_IDENTIDAD  datosPersonales on  solicitante.id_documento= datosPersonales.id ");
			inicio.insert(0, " inner join JBPM4_HIST_ACTO00005  on execution_ = expedienteElectronico.id_workflow");

			if (expedienteMetaDataList.size() > 0) {
				String nombre = "metaDato" + i;
				inicio.insert(0, " inner join METADATOS_TRATA  " + nombre + " on expedienteElectronico.id=" + nombre
						+ ".id_expediente ");
				fin.insert(0,
						nombre + ".nombre_metadato=:nombre" + i + " and " + nombre + ".valor_metadato like :valor" + i);

				while ((i + 1) < expedienteMetaDataList.size()) {
					i++;
					nombre = "metaDato" + i;
					inicio.insert(0, " inner join METADATOS_TRATA  " + nombre + " on expedienteElectronico.id=" + nombre
							+ ".id_expediente ");
					fin.insert(0, nombre + ".nombre_metadato=:nombre" + i + " and " + nombre
							+ ".valor_metadato like :valor" + i + " and ");
				}

				fin.insert(0, " and ");
			}

			medio.insert(0, " ASSIGNEE_=:username  ");
			medio.insert(0, " and  expedienteElectronico.id_trata=:trata and ");

			if ((desde != null) && (hasta != null)) {
				medio.insert(0, "  expedienteElectronico.fecha_creacion between :desde and :hasta and");
			}

			if (tipoDocumento != null) {
				medio.insert(0, " datosPersonales.tipo_documento=:tipoDocumento ) AND ");
				medio.insert(0, "(datosPersonales.numero_documento=:numeroDocumento  AND ");
			}

			if (cuitCuil != null) {
				medio.insert(0, " solicitante.cuit_cuil=:cuitCuil  AND ");

			}

			if (estado != null) {
				medio.insert(0, " expedienteElectronico.estado=:estado  AND ");

			}

			StringBuilder strExecute = new StringBuilder();
			strExecute.append(
					"SELECT distinct expedienteElectronico.* FROM EE_EXPEDIENTE_ELECTRONICO  expedienteElectronico ");
			strExecute.append(inicio);
			strExecute.append(medio);
			strExecute.append(fin);

			SQLQuery query = session.createSQLQuery(strExecute.toString()).addEntity(ExpedienteElectronicoDTO.class);

			query.setString("username", username);
			query.setLong("trata", trata.getId());

			if ((desde != null) && (hasta != null)) {
				Calendar cHasta = Calendar.getInstance();
				cHasta.setTime(hasta);
				cHasta.add(Calendar.DAY_OF_YEAR, 1);
				hastaBusqueda = cHasta.getTime();
				query.setDate("desde", desde);
				query.setDate("hasta", hastaBusqueda);
			}

			i = 0;

			for (ExpedienteMetadataDTO medaDato : expedienteMetaDataList) {
				query.setString("nombre" + i, medaDato.getNombre());
				query.setString("valor" + i, "%" + medaDato.getValor() + "%");
				i++;
			}

			if (tipoDocumento != null) {
				query.setString("tipoDocumento", tipoDocumento.substring(0, 3).trim());
				query.setString("numeroDocumento", numeroDocumento);
			}

			if (cuitCuil != null) {
				query.setString("cuitCuil", cuitCuil);
			}

			if (estado != null) {
				query.setString("estado", estado);
			}

			result = (List<ExpedienteElectronicoDTO>) query.list();

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoPorMetadatosTramitacion(String, TrataDTO, List<ExpedienteMetadata>, Date, Date, String, String, String, String) - end - return value={}", result);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorReparticion(String reparticion, Date desde,
			Date hasta, String tipoDocumento, String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoPorReparticion(reparticion={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start", reparticion, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		Session session = null;

		try {
			this.getHibernateTemplate().setCacheQueries(true);

			List<ExpedienteElectronicoDTO> result = null;
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

			Date hastaBusqueda = hasta;
			StringBuilder inicio = new StringBuilder(" where ");
			StringBuilder fin = new StringBuilder("order by expedienteElectronico.fechaCreacion desc");
			StringBuilder medio = new StringBuilder();
			medio.insert(0, "	expedienteElectronico.codigoReparticionUsuario=:reparticion ");

			if ((desde != null) && (hasta != null)) {
				medio.insert(0, "	 expedienteElectronico.fechaCreacion between :desde and :hasta and ");
			}

			if (estado != null) {
				medio.insert(0, "	 expedienteElectronico.estado =:estado and ");
			}

			if (tipoDocumento != null) {
				medio.insert(0,
						" expedienteElectronico.solicitudIniciadora.solicitante.documento.tipoDocumento=:tipoDocumento ) AND ");
				medio.insert(0,
						"( expedienteElectronico.solicitudIniciadora.solicitante.documento.numeroDocumento=:numeroDocumento  AND ");
			}

			if (cuitCuil != null) {
			  StringBuilder constanteCuil = new StringBuilder();
				constanteCuil.append(" expedienteElectronico.solicitudIniciadora.solicitante.cuitCuil=:cuitCuil AND ");
				medio.insert(0, constanteCuil.toString());
			}

			StringBuilder cadenaParcial = new StringBuilder();

			cadenaParcial.append("from  ExpedienteElectronicoDTO  expedienteElectronico ");
			cadenaParcial.append(inicio);
			cadenaParcial.append(medio);
			cadenaParcial.append(fin);

			Query hqlQueryFull = session.createQuery(cadenaParcial.toString());

			StringBuilder reparticionStr = new StringBuilder();

			reparticionStr.append(reparticion.trim());

			hqlQueryFull.setString("reparticion", reparticionStr.toString());

			if ((desde != null) && (hasta != null)) {
				Calendar cHasta = Calendar.getInstance();
				cHasta.setTime(hasta);
				cHasta.add(Calendar.DAY_OF_YEAR, 1);
				hastaBusqueda = cHasta.getTime();
				hqlQueryFull.setDate("desde", desde);
				hqlQueryFull.setDate("hasta", hastaBusqueda);
			}

			if (estado != null) {
				hqlQueryFull.setString("estado", estado);
			}

			if (tipoDocumento != null) {
				hqlQueryFull.setString("tipoDocumento", tipoDocumento.substring(0, 3).trim());
				hqlQueryFull.setString("numeroDocumento", numeroDocumento);
			}

			if (cuitCuil != null) {
				hqlQueryFull.setString("cuitCuil", cuitCuil);
			}

			hqlQueryFull.setMaxResults(300);
			result = hqlQueryFull.list();

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoPorReparticion(String, Date, Date, String, String, String, String) - end - return value={}", result);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuario(String username, Date desde, Date hasta,
			String tipoDocumento, String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoPorUsuario(username={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start", username, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		Session session = null;

		try {
			this.getHibernateTemplate().setCacheQueries(true);

			List<ExpedienteElectronicoDTO> result = null;
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

			StringBuilder inicio = new StringBuilder(" where ");
			StringBuilder fin = new StringBuilder("order by expedienteElectronico.fechaCreacion desc");
			StringBuilder medio = new StringBuilder();
			Date hastaBusqueda = hasta;

			if ((desde != null) && (hasta != null)) {
				medio.insert(0, "and expedienteElectronico.fechaCreacion between :desde and :hasta ");
			}

			if (estado != null) {
				medio.insert(0, "and expedienteElectronico.estado =:estado ");
			}

			medio.insert(0, "expedienteElectronico.usuarioCreador=:username ");

			if (tipoDocumento != null) {
				medio.insert(0,
						" expedienteElectronico.solicitudIniciadora.solicitante.documento.tipoDocumento=:tipoDocumento ) AND ");
				medio.insert(0,
						"( expedienteElectronico.solicitudIniciadora.solicitante.documento.numeroDocumento=:numeroDocumento  AND ");
			}

			if (cuitCuil != null) {
				medio.insert(0, " expedienteElectronico.solicitudIniciadora.solicitante.cuitCuil=:cuitCuil AND ");
			}

			StringBuilder queryExecution = new StringBuilder();

			queryExecution.append("from  ExpedienteElectronicoDTO  expedienteElectronico ");
			queryExecution.append(inicio);
			queryExecution.append(medio);
			queryExecution.append(fin);

			Query hqlQueryFull = session.createQuery(queryExecution.toString());

			hqlQueryFull.setString("username", username);

			if ((desde != null) && (hasta != null)) {
				Calendar cHasta = Calendar.getInstance();
				cHasta.setTime(hasta);
				cHasta.add(Calendar.DAY_OF_YEAR, 1);
				hastaBusqueda = cHasta.getTime();
				hqlQueryFull.setDate("desde", desde);
				hqlQueryFull.setDate("hasta", hastaBusqueda);
			}

			if (tipoDocumento != null) {
				hqlQueryFull.setString("tipoDocumento", tipoDocumento.substring(0, 3).trim());
				hqlQueryFull.setString("numeroDocumento", numeroDocumento);
			}

			if (cuitCuil != null) {
				hqlQueryFull.setString("cuitCuil", cuitCuil);
			}

			if (estado != null) {
				hqlQueryFull.setString("estado", estado);
			}

			hqlQueryFull.setMaxResults(300);
			result = hqlQueryFull.list();

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoPorUsuario(String, Date, Date, String, String, String, String) - end - return value={}", result);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuarioTramitacion(String username, Date desde,
			Date hasta, String tipoDocumento, String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoPorUsuarioTramitacion(username={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start", username, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		Session session = null;

		try {
			this.getHibernateTemplate().setCacheQueries(true);

			List<ExpedienteElectronicoDTO> result = null;
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

			Date hastaBusqueda = hasta;
			StringBuilder inicio = new StringBuilder(" where ");
			StringBuilder fin = new StringBuilder(" order by expedienteElectronico.fecha_creacion desc ");
			StringBuilder medio = new StringBuilder();
			inicio.insert(0, " ,  SOLICITUD_EXPEDIENTE  solicitud   " + " , SOLICITANTE  solicitante   "
					+ " , DOCUMENTO_DE_IDENTIDAD datosPersonales ");
			inicio.insert(0, " , JBPM4_HIST_TASK ");
			medio.insert(0,
					" ASSIGNEE_=:username and execution_= expedienteElectronico.id_workflow "
							+ " and execution_=expedienteElectronico.id_workflow "
							+ " and solicitante.id_documento= datosPersonales.id"
							+ " and solicitud.id= solicitante.id_solicitante "
							+ " and expedienteElectronico.solicitud_iniciadora= solicitud.id ");

			if ((desde != null) && (hasta != null)) {
				medio.insert(0, "  expedienteElectronico.fecha_creacion between :desde and :hasta and  ");
			}

			if (estado != null) {
				medio.insert(0, " expedienteElectronico.estado = :estado AND ");
			}

			if (tipoDocumento != null) {
				medio.insert(0, " datosPersonales.tipo_documento = :tipoDocumento ) AND ");
				medio.insert(0, "(datosPersonales.numero_documento = :numeroDocumento  AND ");
			}

			if (cuitCuil != null) {
				medio.insert(0, " solicitante.cuit_cuil = :cuitCuil AND ");
			}

			SQLQuery query = session
					.createSQLQuery(
							"SELECT DISTINCT expedienteElectronico.* FROM EE_EXPEDIENTE_ELECTRONICO expedienteElectronico "
									+ inicio.toString() + medio.toString() + fin.toString())
					.addEntity(ExpedienteElectronicoDTO.class);

			query.setString("username", username);

			if ((desde != null) && (hasta != null)) {
				Calendar cHasta = Calendar.getInstance();
				cHasta.setTime(hasta);
				cHasta.add(Calendar.DAY_OF_YEAR, 1);
				hastaBusqueda = cHasta.getTime();
				query.setDate("desde", desde);
				query.setDate("hasta", hastaBusqueda);
			}

			if (estado != null) {
				query.setString("estado", estado);
			}

			if (tipoDocumento != null) {
				query.setString("tipoDocumento", tipoDocumento.substring(0, 3).trim());
				query.setString("numeroDocumento", numeroDocumento);
			}

			if (cuitCuil != null) {
				query.setString("cuitCuil", cuitCuil);
			}

			query.setMaxResults(300);
			result = (List<ExpedienteElectronicoDTO>) query.list();

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoPorUsuarioTramitacion(String, Date, Date, String, String, String, String) - end - return value={}", result);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<ExpedienteElectronicoDTO> crearQueryDinamica(String username, String reparticion, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, Date desde, Date hasta, Session session, Query hqlQuery,
			String tipoDocumento, String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearQueryDinamica(username={}, reparticion={}, trata={}, expedienteMetaDataList={}, desde={}, hasta={}, session={}, hqlQuery={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start", username, reparticion, trata, expedienteMetaDataList, desde, hasta, session, hqlQuery, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		int i = 0;
		Date hastaBusqueda = hasta;

		// Crea query
		StringBuilder inicio = new StringBuilder(" where ");
		StringBuilder fin = new StringBuilder(" order by expedienteElectronico.fechaCreacion desc");
		StringBuilder medio = new StringBuilder();

		if (expedienteMetaDataList.size() > 0) {
			String nombre = "metaDato" + i;
			inicio.insert(0, "inner join expedienteElectronico.metadatosDeTrata " + nombre);
			fin.insert(0, "(" + nombre + ".nombre =:nombre" + i + " and " + nombre + ".valor like :valor" + i + ")");

			while ((i + 1) < expedienteMetaDataList.size()) {
				i++;
				nombre = "metaDato" + i;
				inicio.insert(0, " inner join expedienteElectronico.metadatosDeTrata " + nombre);
				fin.insert(0,
						"(" + nombre + ".nombre=:nombre" + i + "and " + nombre + ".valor like :valor" + i + ") AND ");
			}

			fin.insert(0, " and ");
		}

		medio.insert(0, " expedienteElectronico.trata=:trata   ");

		if ((desde != null) && (hasta != null)) {
			medio.insert(0, " expedienteElectronico.fechaCreacion between :desde and :hasta and ");
		}

		if (username != null) {
			medio.insert(0, " expedienteElectronico.usuarioCreador=:username AND ");
		} else {
			medio.insert(0, " expedienteElectronico.codigoReparticionUsuario=:reparticion AND ");
		}

		if (estado != null) {
			medio.insert(0, " expedienteElectronico.estado=:estado AND ");
		}

		if (tipoDocumento != null) {
			medio.insert(0,
					" expedienteElectronico.solicitudIniciadora.solicitante.documento.tipoDocumento=:tipoDocumento ) AND ");
			medio.insert(0,
					"( expedienteElectronico.solicitudIniciadora.solicitante.documento.numeroDocumento=:numeroDocumento  AND ");
		}

		if (cuitCuil != null) {
			medio.insert(0, " expedienteElectronico.solicitudIniciadora.solicitante.cuitCuil=:cuitCuil AND ");
		}

		hqlQuery = session.createQuery("from ExpedienteElectronicoDTO expedienteElectronico  " + inicio.toString() + " "
				+ medio.toString() + " " + fin.toString());
		// fin query

		// Carga las variables - valores
		i = 0;

		for (ExpedienteMetadataDTO medaDato : expedienteMetaDataList) {
			hqlQuery.setString("nombre" + i, medaDato.getNombre());
			hqlQuery.setString("valor" + i, "%" + medaDato.getValor() + "%");
			i++;
		}

		if (username != null) {
			hqlQuery.setString("username", username);
		} else {
			hqlQuery.setString("reparticion", reparticion.trim());
		}

		hqlQuery.setLong("trata", trata.getId());

		if ((desde != null) && (hasta != null)) {
			Calendar cHasta = Calendar.getInstance();
			cHasta.setTime(hasta);
			cHasta.add(Calendar.DAY_OF_YEAR, 1);
			hastaBusqueda = cHasta.getTime();
			hqlQuery.setDate("desde", desde);
			hqlQuery.setDate("hasta", hastaBusqueda);
		}

		if (tipoDocumento != null) {
			hqlQuery.setString("tipoDocumento", tipoDocumento.substring(0, 3).trim());
			hqlQuery.setString("numeroDocumento", numeroDocumento);
		}

		if (cuitCuil != null) {
			hqlQuery.setString("cuitCuil", cuitCuil);
		}

		if (estado != null) {
			hqlQuery.setString("estado", estado);
		}

		hqlQuery.setMaxResults(300);

		List<ExpedienteElectronicoDTO> returnList = hqlQuery.list();
		if (logger.isDebugEnabled()) {
			logger.debug("crearQueryDinamica(String, String, TrataDTO, List<ExpedienteMetadata>, Date, Date, Session, Query, String, String, String, String) - end - return value={}", returnList);
		}
		return returnList;
	}

	private void setearNombresValoresSinMetaData(Query hqlQuery, String username, TrataDTO trata) {
		if (logger.isDebugEnabled()) {
			logger.debug("setearNombresValoresSinMetaData(hqlQuery={}, username={}, trata={}) - start", hqlQuery, username, trata);
		}

		hqlQuery.setString("username", username);
		hqlQuery.setLong("trata", trata.getId());

		if (logger.isDebugEnabled()) {
			logger.debug("setearNombresValoresSinMetaData(Query, String, TrataDTO) - end");
		}
	}

	private void setearNombresValoresReparticionSinMetaData(Query hqlQuery, String reparticion, TrataDTO trata) {
		if (logger.isDebugEnabled()) {
			logger.debug("setearNombresValoresReparticionSinMetaData(hqlQuery={}, reparticion={}, trata={}) - start", hqlQuery, reparticion, trata);
		}

		hqlQuery.setString("reparticion", reparticion.trim());
		hqlQuery.setLong("trata", trata.getId());

		if (logger.isDebugEnabled()) {
			logger.debug("setearNombresValoresReparticionSinMetaData(Query, String, TrataDTO) - end");
		}
	}

	@SuppressWarnings("unchecked")
	public List<ExpedienteElectronicoDTO> buscarExpedientesBytrata(TrataDTO trata, String buffer, String username,
			String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesBytrata(trata={}, buffer={}, username={}, reparticion={}) - start", trata, buffer, username, reparticion);
		}

		Session session = null;

		try {
			this.getHibernateTemplate().setCacheQueries(true);

			List<ExpedienteElectronicoDTO> result = null;
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

			StringBuilder strExecute = new StringBuilder();

			strExecute.append("from  ExpedienteElectronicoDTO  expedienteElectronico ");
			strExecute.append(" where ");
			strExecute.append(buffer);
			strExecute.append("expedienteElectronico.trata=:trata");

			Query hqlQuery = session.createQuery(strExecute.toString());

			if (username != null) {
				setearNombresValoresSinMetaData(hqlQuery, username, trata);
			} else {
				setearNombresValoresReparticionSinMetaData(hqlQuery, reparticion, trata);
			}

			if (hqlQuery != null) {
				result = hqlQuery.list();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedientesBytrata(TrataDTO, String, String, String) - end - return value={}", result);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	public List<ExpedienteAsociadoEntDTO> obtenerListaEnFusionados(Long idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnFusionados(idExpedienteElectronico={}) - start", idExpedienteElectronico);
		}

		List<ExpedienteAsociadoEntDTO> lista = new ArrayList<>();

		DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
		criteria.add(Restrictions.eq("id", idExpedienteElectronico));

		ExpedienteElectronicoDTO expedienteElectronico = (ExpedienteElectronicoDTO) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados = expedienteElectronico.getListaExpedientesAsociados();

		for (ExpedienteAsociadoEntDTO expFusion : listaExpedientesAsociados) {
			if ((expFusion.getEsExpedienteAsociadoFusion() != null) && expFusion.getEsExpedienteAsociadoFusion()) {
				// busca la trata
				if (expFusion.getIdCodigoCaratula() != null) {
					ExpedienteElectronicoDTO ee = this.buscarExpedienteElectronico(expFusion.getIdCodigoCaratula());
					expFusion.setTrata(ee.getTrata().getCodigoTrata());
				}
				lista.add(expFusion);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnFusionados(Integer) - end - return value={}", lista);
		}
		return lista;
	}

	public List<ExpedienteAsociadoEntDTO> obtenerListaEnTramitacionConjunta(Long idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnTramitacionConjunta(idExpedienteElectronico={}) - start", idExpedienteElectronico);
		}

		List<ExpedienteAsociadoEntDTO> lista = new ArrayList<>();

		DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
		criteria.add(Restrictions.eq("id", idExpedienteElectronico));

		ExpedienteElectronicoDTO expedienteElectronico = (ExpedienteElectronicoDTO) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados = expedienteElectronico.getListaExpedientesAsociados();

		for (ExpedienteAsociadoEntDTO expTC : listaExpedientesAsociados) {
			if ((expTC.getEsExpedienteAsociadoTC() != null) && expTC.getEsExpedienteAsociadoTC()) {
				lista.add(expTC);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnTramitacionConjunta(Integer) - end - return value={}", lista);
		}
		return lista;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void modificarPorTramitacionConjuntaOFusion(Serializable obj) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			logger.debug("modificarPorTramitacionConjuntaOFusion(obj={}) - start", obj);
		}

		try {
			// TODO ver pq no devuelve el objeto actualizado
			ExpedienteElectronico exp = mapper.map(obj, ExpedienteElectronico.class);
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().merge(exp);
		} catch (HibernateException he) {
			logger.error("modificarPorTramitacionConjuntaOFusion(Serializable)", he);

			throw new SinPersistirException("No se pudo persistir el documento: " + obj, he);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("modificarPorTramitacionConjuntaOFusion(Serializable) - end");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ExpedienteElectronicoDTO> buscarExpedientesElectronicosPorAnioReparticion(Integer anio,
			String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesElectronicosPorAnioReparticion(anio={}, reparticion={}) - start", anio, reparticion);
		}

		Session session = null;

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ExpedienteElectronicoDTO.class);
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			criteria.add(Restrictions.eq("codigoReparticionUsuario", reparticion.trim()));
			criteria.add(Restrictions.eq("anio", anio));
			criteria.add(Restrictions.eq("sistemaCreador", ConstantesCore.NOMBRE_APLICACION));
			criteria.addOrder(Order.desc("id"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			List results = criteria.getExecutableCriteria(session).setMaxResults(CANT_REGISTROS).list();

			List<ExpedienteElectronicoDTO> returnList = (List<ExpedienteElectronicoDTO>) results;
			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedientesElectronicosPorAnioReparticion(Integer, String) - end - return value={}", returnList);
			}
			return returnList;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorDireccion(Date desde, Date hasta, String domicilio,
			String piso, String departamento, String codigoPostal) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoPorDireccion(desde={}, hasta={}, domicilio={}, piso={}, departamento={}, codigoPostal={}) - start", desde, hasta, domicilio, piso, departamento, codigoPostal);
		}

		Session session = null;

		try {
			this.getHibernateTemplate().setCacheQueries(true);

			List<ExpedienteElectronicoDTO> result = null;
			session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

			Date hastaBusqueda = hasta;
			StringBuilder inicio = new StringBuilder(" where ");
			StringBuilder fin = new StringBuilder("order by expedienteElectronico.fechaCreacion desc");
			StringBuilder medio = new StringBuilder();

			if ((desde != null) && (hasta != null)) {
				medio.insert(0, " expedienteElectronico.fechaCreacion between :desde and :hasta and ");
			}

			if (domicilio != null) {
				medio.insert(0, " expedienteElectronico.solicitudIniciadora.solicitante.domicilio=:domicilio AND ");
			}

			if (piso != null) {
				medio.insert(0, " expedienteElectronico.solicitudIniciadora.solicitante.piso=:piso AND ");
			}

			if (departamento != null) {
				medio.insert(0,
						" expedienteElectronico.solicitudIniciadora.solicitante.departamento=:departamento AND ");
			}

			if (codigoPostal != null) {
				medio.insert(0,
						" expedienteElectronico.solicitudIniciadora.solicitante.codigoPostal=:codigoPostal AND ");
			}

			StringBuilder strExecute = new StringBuilder();

			strExecute.append("from  ExpedienteElectronicoDTO  expedienteElectronico ");
			strExecute.append(inicio);
			strExecute.append(medio);
			strExecute.append(fin);

			Query hqlQueryFull = session.createQuery(strExecute.toString());

			if ((desde != null) && (hasta != null)) {
				Calendar cHasta = Calendar.getInstance();
				cHasta.setTime(hasta);
				cHasta.add(Calendar.DAY_OF_YEAR, 1);
				hastaBusqueda = cHasta.getTime();
				hqlQueryFull.setDate("desde", desde);
				hqlQueryFull.setDate("hasta", hastaBusqueda);
			}

			if (domicilio != null) {
				hqlQueryFull.setString("domicilio", domicilio);
			}
			if (piso != null) {
				hqlQueryFull.setString("piso", piso);
			}
			if (departamento != null) {
				hqlQueryFull.setString("departamento", departamento);
			}
			if (codigoPostal != null) {
				hqlQueryFull.setString("codigoPostal", codigoPostal);
			}

			hqlQueryFull.setMaxResults(300);
			result = hqlQueryFull.list();

			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoPorDireccion(Date, Date, String, String, String, String) - end - return value={}", result);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error: ", e);
			throw new IllegalAccessError("Ha ocurrido un error en la busqueda del Expediente. " + e);
		} finally {
			if (session != null) {
				//this.releaseSession(session);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedientesGuardaTemporalMayorA24Meses(Integer cantidadDeDias) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesGuardaTemporalMayorA24Meses(cantidadDeDias={}) - start", cantidadDeDias);
		}

		List<ExpedienteElectronicoIndex> repuestaSolr = null;
		Session session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();

		Calendar hoy = Calendar.getInstance();
		hoy.set(Calendar.DAY_OF_YEAR, hoy.get(Calendar.DAY_OF_YEAR) - cantidadDeDias);
		Date fecha = new java.sql.Date(hoy.getTimeInMillis());
		String estado = "\"Guarda Temporal\"";

		String queryString = "estado:" + estado + " AND fecha_modificacion:" + SolrUtils.dateFilter(null, fecha);
		try {
			repuestaSolr = solrService.obtenerExpedientesElectronicosSolr(queryString, true);
		} catch (Exception e) {
		 loger.error("error al buscar los expedientes mayores a 24 meses", e);
			logger.error(e.getMessage());
		}
		if (repuestaSolr != null && repuestaSolr.size() > 0) {
			List<ExpedienteElectronicoDTO> returnList = this.convertirSolrResponseAEE(repuestaSolr);
			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedientesGuardaTemporalMayorA24Meses(Integer) - end - return value={}", returnList);
			}
			return returnList;

		}
		;

		String str = "FROM ExpedienteElectronicoDTO ee WHERE ee.estado = :state AND ee.fechaModificacion <= :date ";
		Query hqlQuery = session.createQuery(str);
		hqlQuery.setString("state", "Guarda Temporal");
		hqlQuery.setDate("date", fecha);

		List<ExpedienteElectronicoDTO> returnList = (List<ExpedienteElectronicoDTO>) hqlQuery.list();
		
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesGuardaTemporalMayorA24Meses(Integer) - end - return value={}", returnList);
		}
		return returnList;
	}

	private String listTratasToString(List<String> lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("listTratasToString(lista={}) - start", lista);
		}

		String resultado = "";

		int i = 0;

		if (lista != null) {

			for (String l : lista) {
				i++;
				if (i == lista.size()) {
					resultado = resultado + l;
				} else {
					resultado = resultado + l + " OR ";
				}

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("listTratasToString(List<String>) - end - return value={}", resultado);
		}
		return resultado;

	}

	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoporTrata(String expedienteEstado,
			String expedienteUsuarioAsignado, List<String> codigosTrataList) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoporTrata(expedienteEstado={}, expedienteUsuarioAsignado={}, codigosTrataList={}) - start", expedienteEstado, expedienteUsuarioAsignado, codigosTrataList);
		}

		List<ExpedienteElectronicoIndex> repuestaSolr = null;

		String queryString;
		String tratas = this.listTratasToString(codigosTrataList);
		if (expedienteEstado != null && expedienteEstado.length() > 0) {
			queryString = "estado:" + expedienteEstado + " AND codigo_trata:(" + tratas + ")";
		} else {
			queryString = tratas;
		}
		try {
			repuestaSolr = solrService.obtenerExpedientesElectronicosSolr(queryString, true);
		} catch (Exception e) {
		 loger.error("error al buscar expediente por trata", e);
			logger.error(e.getMessage());
		}
		if (repuestaSolr != null && repuestaSolr.size() > 0) {
			List<ExpedienteElectronicoDTO> returnList = this.convertirSolrResponseAEE(repuestaSolr);
			if (logger.isDebugEnabled()) {
				logger.debug("buscarExpedienteElectronicoporTrata(String, String, List<String>) - end - return value={}", returnList);
			}
			return returnList;

		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoporTrata(String, String, List<String>) - end - return value=null");
		}
		return null;

	}

	private List<ExpedienteElectronicoDTO> convertirSolrResponseAEE(List<ExpedienteElectronicoIndex> solrResponse) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertirSolrResponseAEE(solrResponse={}) - start", solrResponse);
		}

		List<ExpedienteElectronicoDTO> respuesta = new ArrayList<>();
		for (ExpedienteElectronicoIndex es : solrResponse) {
			ExpedienteElectronicoDTO e = new ExpedienteElectronicoDTO();
			e.setTipoDocumento(es.getTipoDocumento());
			e.setAnio(es.getAnio());
			e.setCodigoReparticionActuacion(es.getCodigoReparticionActuacion());
			e.setCodigoReparticionUsuario(es.getCodigoReparticionUsuario());
			e.setNumero(es.getNumero());
			e.setDescripcion(es.getDescripcion());
			e.setId(es.getId());
			e.setEstado(es.getEstado());
			e.setFechaCreacion(es.getFechaCreacion());
			e.setFechaModificacion(es.getFechamodificacion());
			e.setUsuarioCreador(es.getUsuarioCreador());

			e.setIdWorkflow(es.getIdWorkflow());
			TrataDTO t = new TrataDTO();
			t.setCodigoTrata(es.getCodigoTrata());
			e.setTrata(t);
			SolicitudExpedienteDTO s = new SolicitudExpedienteDTO();
			if (es.getIsInterna() != null) {
				if (es.getIsInterna().equals("true")) {
					s.setEsSolicitudInterna(true);
				} else {
					s.setEsSolicitudInterna(false);
				}
				if (es.getIsInterna().equals("1")) {
					s.setEsSolicitudInterna(true);
				} else {
					s.setEsSolicitudInterna(false);
				}
			} else {
				s.setEsSolicitudInterna(true);
			}
			s.setMotivo(es.getMotivoInternoSolicitud());
			s.setMotivoExterno(es.getMotivoExternoSolicitud());
			SolicitanteDTO so = new SolicitanteDTO();
			DocumentoDeIdentidadDTO d = new DocumentoDeIdentidadDTO();
			d.setTipoDocumento(es.getTipoDocumentoSolicitante());
			d.setNumeroDocumento(es.getNumeroDocumentoSolicitante());
			so.setDocumento(d);
			so.setApellidoSolicitante(es.getApellidoSolicitante());
			so.setCodigoPostal(es.getCodigoPostalSolicitante());
			so.setCuitCuil(es.getCuitCuilSolicitante());
			so.setDepartamento(es.getDepartamentoSolicitante());
			so.setDomicilio(es.getDomicilioSolicitante());
			so.setEmail(es.getEmailSolicitante());
			so.setNombreSolicitante(es.getNombreSolicitante());
			so.setPiso(es.getPisoSolicitante());
			so.setRazonSocialSolicitante(es.getRazonSocialSolicitante());
			so.setSegundoApellidoSolicitante(es.getSegundoApellidoSolicitante());
			so.setSegundoNombreSolicitante(es.getSegundoNombreSolicitante());
			so.setTelefono(es.getTelefono());
			so.setTercerApellidoSolicitante(es.getTercerApellidoSolicitante());
			so.setTercerNombreSolicitante(es.getTercerNombreSolicitante());

			s.setSolicitante(so);
			e.setSolicitudIniciadora(s);

			respuesta.add(e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convertirSolrResponseAEE(List<ExpedienteElectronicoIndex>) - end - return value={}", respuesta);
		}
		return respuesta;

	}

	public void setSolrService(ISolrService solrService) {
		this.solrService = solrService;
	}

	public ISolrService getSolrService() {
		return solrService;
	}

	@Override
	public void regularizarActividadesStatement() {
		if (logger.isDebugEnabled()) {
			logger.debug("regularizarActividadesStatement() - start");
		}

		ejecutarStatement(ConstantesCore.SQL_ACTIVIDAD_REGULARIZADOR);

		if (logger.isDebugEnabled()) {
			logger.debug("regularizarActividadesStatement() - end");
		}
	}


	public void generarHistorialMigracionStatement(String motivo, String username, String codigoSectorOrigen,
			String codigoReparticionDestino) {
		if (logger.isDebugEnabled()) {
			logger.debug("generarHistorialMigracionStatement(motivo={}, username={}, codigoSectorOrigen={}, codigoReparticionDestino={}) - start", motivo, username, codigoSectorOrigen, codigoReparticionDestino);
		}

		String plSqlBlockQuery = ConstantesCore.GENERAR_HISTORIAL_MIGRACION;
		
		Query query =sessionFactory.getCurrentSession().createQuery(plSqlBlockQuery.toString());
		query.setParameter(1, username);
		query.setParameter(1, username);
		query.setParameter(2, username + ".conjunta");
		query.setParameter(3, motivo);
		query.setParameter(4, username);
		query.setParameter(5, codigoSectorOrigen);
		query.setParameter(6, codigoReparticionDestino);
		query.setParameter(7, username);
		query.setParameter(8, username);
		query.list();

		if (logger.isDebugEnabled()) {
			logger.debug("generarHistorialMigracionStatement(String, String, String, String) - end");
		}
	}

	public void ejecutarMigracionSectorStatement(String motivo, String usuario, String sectorUsuarioOrigen,
			String reparticionUsuarioOrigen, String repSecDestino) {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarMigracionSectorStatement(motivo={}, usuario={}, sectorUsuarioOrigen={}, reparticionUsuarioOrigen={}, repSecDestino={}) - start", motivo, usuario, sectorUsuarioOrigen, reparticionUsuarioOrigen, repSecDestino);
		}

		try {
			String plSqlBlockQuery = ConstantesCore.MIGRACION_SECTOR_STATEMENT;
			Query query =sessionFactory.getCurrentSession().createQuery(plSqlBlockQuery.toString());
			
			query.setParameter(1, reparticionUsuarioOrigen + "-" + sectorUsuarioOrigen);
			query.setParameter(2, reparticionUsuarioOrigen + "-" + sectorUsuarioOrigen + ".conjunta");
			query.setParameter(3, motivo);
			query.setParameter(4, usuario);
			query.setParameter(5, sectorUsuarioOrigen);
			query.setParameter(6, reparticionUsuarioOrigen);
			query.setParameter(7, repSecDestino);
			query.setParameter(8, usuario);
			query.setParameter(9, repSecDestino + ".conjunta");
			query.setParameter(10, repSecDestino);
			
			query.list();
		} catch (Exception e) {
			logger.error("Error al ejecutar proceso de actualizacion de tareas", e);
			throw new TeRuntimeException("Error al ejecutar migracion del sector: " + reparticionUsuarioOrigen + "-"
					+ sectorUsuarioOrigen + " via Script razon: " + e.getMessage(), e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarMigracionSectorStatement(String, String, String, String, String) - end");
		}
	}

	private void ejecutarStatement(String statement) {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarStatement(statement={}) - start", statement);
		}
		
		try {
			
			String plSqlBlockQuery = statement;
			Query query =sessionFactory.getCurrentSession().createQuery(plSqlBlockQuery.toString());
			query.list();

		} catch (NegocioException sqle) {
			logger.error("Error al ejecutar proceso de actualizacion de tareas", sqle);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarStatement(String) - end");
		}
	}

	@Override
	public void actualizarWorkflowsEnGuardaTemporalCallaBleStatement() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarWorkflowsEnGuardaTemporalCallaBleStatement() - start");
		}

		//this.ejecutarStatement(Constantes.PLSQL);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarWorkflowsEnGuardaTemporalCallaBleStatement() - end");
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> obtenerIdsDeTaskEnGrupo(String grupo) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerIdsDeTaskEnGrupo(grupo={}) - start", grupo);
		}

		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("SELECT t.EXECUTION_ID_ FROM JBPM4_PARTICIPATION p JOIN"
				+ " JBPM4_TASK t ON t.DBID_ = p.TASK_ WHERE GROUPID_ = :group");
		query.setParameter("group", grupo);
		List<String> returnList = (List<String>) query.list();
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerIdsDeTaskEnGrupo(String) - end - return value={}", returnList);
		}
		return returnList;
	}

	@Override
	public void actualizarReserva(String repVieja, String repNueva) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReserva(repVieja={}, repNueva={}) - start", repVieja, repNueva);
		}

		try {
			
			Query query =sessionFactory.getCurrentSession().createQuery(ConstantesCore.ACTUALIZAR_RESERVA_REPARTICION_RECTORA);
			query.setParameter(1, repNueva);
			query.setParameter(2, repVieja);
			query.list();
		} catch (Exception e) {
			logger.error("actualizarReserva(String, String)", e);

			throw new TeRuntimeException("Ha ocurrido un error en la actualizacion de la reserva. " + e.getMessage(),e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReserva(String, String) - end");
		}
	}

	public void actualizarReservaEnSectores(String repOrigen, String secOrigen, String repDestino, String secDestino) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservaEnSectores(repOrigen={}, secOrigen={}, repDestino={}, secDestino={}) - start", repOrigen, secOrigen, repDestino, secDestino);
		}

		try {
			
			Query query =sessionFactory.getCurrentSession().createQuery(ConstantesCore.ACTUALIZAR_RESERVA_SECTORES);
			query.setParameter(1, repDestino);
			query.setParameter(2, secDestino);
			query.setParameter(3, repOrigen);
			query.setParameter(4, secOrigen);
			query.list();
		} catch (Exception e) {
			logger.error("actualizarReservaEnSectores(String, String, String, String)", e);

			throw new TeRuntimeException("Ha ocurrido un error en la actualizacion de la reserva. " + e.getMessage(), e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservaEnSectores(String, String, String, String) - end");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> consultaExpedientesPorSistemaOrigenReparticion(String sistemaOrigen, String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("consultaExpedientesPorSistemaOrigenReparticion(sistemaOrigen={}, reparticion={}) - start", sistemaOrigen, reparticion);
		}

		try {
			String reparticionBloqueado = reparticion + ".bloqueado";

			Session session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String query = "SELECT (e.TIPO_DOCUMENTO || '-' || e.ANIO || '-' || lpad(e.NUMERO,8 , 0) || "
					+ "'-   -' || e.CODIGO_REPARTICION_ACTUACION || '-' || "
					+ "e.CODIGO_REPARTICION_USUARIO) AS CODIGO_SADE " + "FROM jbpm4_participation par "
					+ "join jbpm4_task task on task.dbid_ = par.task_ "
					+ "join ee_expediente_electronico e on e.ID_WORKFLOW = task.EXECUTION_ID_ "
					+ "where SISTEMA_CREADOR = :sistemaOrigen " + "and (GROUPID_   = :reparticion "
					+ "OR GROUPID_   = :reparticionBloqueado )" + " ";

			Query hQuery = session.createSQLQuery(query);
			hQuery.setParameter("sistemaOrigen", sistemaOrigen);
			hQuery.setParameter("reparticion", reparticion);
			hQuery.setParameter("reparticionBloqueado", reparticionBloqueado);

			List<String> returnList = (List<String>) hQuery.list();
			if (logger.isDebugEnabled()) {
				logger.debug("consultaExpedientesPorSistemaOrigenReparticion(String, String) - end - return value={}", returnList);
			}
			return returnList;
		} catch (Exception e) {
		  loger.error("Error al buscar los expedientes", e);
			logger.error(e.getMessage(), e.getCause());
			logger.error("Error al buscar los expedientes de la reparticion: " + reparticion + " Y sistema origen: "
					+ sistemaOrigen);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> consultaExpedientesPorSistemaOrigenUsuario(String sistemaOrigen, String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("consultaExpedientesPorSistemaOrigenUsuario(sistemaOrigen={}, usuario={}) - start", sistemaOrigen, usuario);
		}

		try {
			usuario = usuario.trim();
			String usuarioBloqueado = usuario + ".bloqueado";
			Session session = (Session) this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String query = "SELECT (e.TIPO_DOCUMENTO || '-' || e.ANIO || '-' || lpad(e.NUMERO,8 , 0) || '-   -' "
					+ "|| e.CODIGO_REPARTICION_ACTUACION || '-' || e.CODIGO_REPARTICION_USUARIO) " + "AS CODIGO_SADE "
					+ "FROM jbpm4_task task " + "join ee_expediente_electronico e "
					+ "on e.ID_WORKFLOW = task.EXECUTION_ID_ " + "WHERE SISTEMA_CREADOR = :sistemaOrigen "
					+ "AND (task.ASSIGNEE_ = :usuario " + "OR task.ASSIGNEE_ = :usuarioBloqueado) " + " ";
			Query hQuery = session.createSQLQuery(query);
			hQuery.setParameter("usuario", usuario);
			hQuery.setParameter("usuarioBloqueado", usuarioBloqueado);
			hQuery.setParameter("sistemaOrigen", sistemaOrigen);

			List<String> returnList = (List<String>) hQuery.list();
			if (logger.isDebugEnabled()) {
				logger.debug("consultaExpedientesPorSistemaOrigenUsuario(String, String) - end - return value={}", returnList);
			}
			return returnList;
		} catch (Exception e) {
		 loger.error("Error al buscar los expedientes del usuario", e);
			logger.error(e.getMessage(), e.getCause());
			logger.error(
					"Error al buscar los expedientes del usuario: " + usuario + " Y sistema origen: " + sistemaOrigen);
			return null;
		}
	}
	
}
