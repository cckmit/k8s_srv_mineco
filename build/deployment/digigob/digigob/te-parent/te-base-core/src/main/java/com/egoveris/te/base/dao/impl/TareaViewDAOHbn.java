package com.egoveris.te.base.dao.impl;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.egoveris.te.base.dao.TareaViewDAO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.service.ExternalServiceAbstract;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

/**
 * @author arquitectura
 */
@Deprecated
@Repository
public class TareaViewDAOHbn extends ExternalServiceAbstract implements TareaViewDAO {
	private transient static Logger logger = LoggerFactory.getLogger(TareaViewDAOHbn.class);
	/* esta clase cachea los query's que son llamados con mayor frecuencia */
	private DataSource dataSource = null;

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private TareaParaleloService tareaParaleloService;
	@Autowired
	private TrataService trataService;
	@Autowired
	ExpedienteElectronicoService expedienteElectronicoService;

	public ExpedienteElectronicoService getExpedienteElectronicoService() {
		return expedienteElectronicoService;
	}

	public void setExpedienteElectronicoService(ExpedienteElectronicoService expedienteElectronicoService) {
		this.expedienteElectronicoService = expedienteElectronicoService;
	}

	public TareaViewDAOHbn() {

	}

	public int numeroTotalTareasSupervisadas(String assignee) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareasSupervisadas(assignee={}) - start", assignee);
		}

		int returnint = numeroTotalTareaDistPorCriterio(assignee, null);
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareasSupervisadas(String) - end - return value={}", returnint);
		}
		return returnint;
	}

	public int numeroTotalTareaDistPorCriterio(String assignee, String estado) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorCriterio(assignee={}, estado={}) - start", assignee, estado);
		}

		Integer numeroTotal = Integer.valueOf(0);

		try (Connection connection = this.getDataSource().getConnection();) {

			StringBuilder sqlQueryString = new StringBuilder("");
			sqlQueryString.append(
					" SELECT COUNT(*) AS NUMERO_TOTAL FROM JBPM4_TASK taskimpl1_ WHERE taskimpl1_.ASSIGNEE_ = ? ");

			if (estado != null) {
				sqlQueryString.append(" AND taskimpl1_.ACTIVITY_NAME_ = ? ");
			}

			try (PreparedStatement stmt = connection.prepareStatement(sqlQueryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
				stmt.setString(1, assignee);
				if (estado != null) {
					stmt.setString(2, estado);
				}

				try (ResultSet rs = stmt.executeQuery()) {

					if (rs.next()) {
						numeroTotal = rs.getInt("NUMERO_TOTAL");
					}
				}
			}

		} catch (SQLException sqle) {
			logger.error(
					"[ThreadId=" + Thread.currentThread().getId()
							+ "] Hubo un error en el método numeroTotalTareaDistPorCriterio " + sqle.getMessage(),
					sqle);
			throw new SQLDataException(sqle.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorCriterio(String, String) - end - return value={}", numeroTotal);
		}
		return numeroTotal;
	}

	public int numeroTotalTareaDistPorGrupo(String candidate, String estado) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorGrupo(candidate={}, estado={}) - start", candidate, estado);
		}

		Integer numeroTotal = new Integer(0);

		try (Connection connection = this.getDataSource().getConnection();) {

			StringBuilder sqlQueryString = new StringBuilder("");
			sqlQueryString.append(
					" SELECT COUNT(*) AS NUMERO_TOTAL FROM JBPM4_TASK taskimpl1_ WHERE participat0_.GROUPID_ = ? ");

			if (estado != null) {
				sqlQueryString.append(" AND taskimpl1_.ACTIVITY_NAME_ = ? ");
			}

			try (PreparedStatement stmt = connection.prepareStatement(sqlQueryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
				stmt.setString(1, candidate);
				if (estado != null) {
					stmt.setString(2, estado);
				}

				try (ResultSet rs = stmt.executeQuery()) {

					if (rs.next()) {
						numeroTotal = rs.getInt("NUMERO_TOTAL");
					}
				}
			}
		} catch (SQLException sqle) {
			logger.error("[ThreadId=" + Thread.currentThread().getId()
					+ "] Hubo un error en el método numeroTotalTareaDistPorGrupo ", sqle);
			throw new SQLDataException(sqle.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorGrupo(String, String) - end - return value={}", numeroTotal);
		}
		return numeroTotal;
	}

	public List<Tarea> buscarTareaDistPorTrata(final String expedienteEstado, final String expedienteUsuarioAsignado,
			final List<String> codigosTrataList) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarTareaDistPorTrata(expedienteEstado={}, expedienteUsuarioAsignado={}, codigosTrataList={}) - start",
					expedienteEstado, expedienteUsuarioAsignado, codigosTrataList);
		}

		List<Tarea> tareas;
		List<ExpedienteElectronicoDTO> ee = this.expedienteElectronicoService
				.buscarExpedienteElectronicoporTrataEnSolr(expedienteEstado, codigosTrataList);
		if (ee != null) {
			tareas = this.buscarTareaDistPorTratasolr(ee, expedienteEstado, expedienteUsuarioAsignado);

		} else {
			tareas = this.buscarTareaDistPorTrataBD(expedienteEstado, expedienteUsuarioAsignado, codigosTrataList);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareaDistPorTrata(String, String, List<String>) - end - return value={}", tareas);
		}
		return tareas;

	};

	private List<Tarea> buscarTareaDistPorTratasolr(List<ExpedienteElectronicoDTO> ee, String expedienteEstado,
			String expedienteUsuarioAsignado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarTareaDistPorTratasolr(ee={}, expedienteEstado={}, expedienteUsuarioAsignado={}) - start", ee,
					expedienteEstado, expedienteUsuarioAsignado);
		}

		List<Tarea> tareas = new ArrayList<>();
		for (ExpedienteElectronicoDTO e : ee) {
			if (expedienteUsuarioAsignado != null || "null".equalsIgnoreCase(expedienteUsuarioAsignado)
					|| "".equalsIgnoreCase(expedienteUsuarioAsignado)) {
				Set<String> setVariables = new HashSet<>();
				setVariables.add("motivo");
				setVariables.add("descripcion");
				setVariables.add("usuarioAnterior");
				setVariables.add("utlimaModificacion");
				Map<String, Object> variables = processEngine.getExecutionService().getVariables(e.getIdWorkflow(),
						setVariables);
				Task workingTask = null;
				try {
					workingTask = obtenerWorkingTask(e);
				} catch (ParametroIncorrectoException e1) {
					logger.error("Error en el metodo buscarTareaDistPorTratasolr ", e1);
					tareas = null;
				}
				if (variables != null && workingTask != null && workingTask.getAssignee() != null
						&& (workingTask.getAssignee().replace(".bloqueado", "")).equals(expedienteUsuarioAsignado)) {
					Tarea t = new Tarea();
					t.setCodigoExpediente(e.getCodigoCaratula());
					t.setCodigoTrata(e.getTrata().getCodigoTrata());
					t.setEstado(e.getEstado());
					t.setDescripcionTrata(String.valueOf(variables.get("descripcion")));
					t.setUsuarioAnterior(String.valueOf(variables.get("usuarioAnterior")));
					t.setMotivo(String.valueOf(variables.get("motivo")));
					t.setFechaModificacion(String.valueOf(variables.get("utlimaModificacion")));
					if (tareas != null) {
						tareas.add(t);
					} else {
						logger.info("No se encuentra la tarea solicitada");
					}

				}
				// return tareas;
			} else {
				Set<String> setVariables = new HashSet<>();
				setVariables.add("motivo");
				setVariables.add("descripcion");
				setVariables.add("usuarioAnterior");
				setVariables.add("utlimaModificacion");
				Map<String, Object> variables = processEngine.getExecutionService().getVariables(e.getIdWorkflow(),
						setVariables);
				Task workingTask = null;
				try {
					workingTask = obtenerWorkingTask(e);
				} catch (ParametroIncorrectoException e1) {
					logger.error("Error en el metodo buscarTareaDistPorTratasolr ", e1);
					tareas = null;
				}
				if (variables != null && workingTask != null && workingTask.getAssignee() != null) {
					Tarea t = new Tarea();
					t.setCodigoExpediente(e.getCodigoCaratula());
					t.setCodigoTrata(e.getTrata().getCodigoTrata());
					t.setEstado(e.getEstado());
					t.setDescripcionTrata(String.valueOf(variables.get("descripcion")));
					t.setUsuarioAnterior(String.valueOf(variables.get("usuarioAnterior")));
					t.setMotivo(String.valueOf(variables.get("motivo")));
					t.setFechaModificacion(String.valueOf(variables.get("utlimaModificacion")));
					tareas.add(t);

				}
				// return tareas;

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarTareaDistPorTratasolr(List<ExpedienteElectronico>, String, String) - end - return value={}",
					tareas);
		}
		return tareas;
	}

	// EE-REFACTORME (Martes 15-Marzo-2014) grinberg.
	// JIRA: https://quark.everis.com/jira/browse/BISADE-3132,
	// https://quark.everis.com/jira/browse/BISADE-3624
	@Deprecated
	private List<Tarea> buscarTareaDistPorTrataBD(final String expedienteEstado, final String expedienteUsuarioAsignado,
			final List<String> codigosTrataList) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarTareaDistPorTrataBD(expedienteEstado={}, expedienteUsuarioAsignado={}, codigosTrataList={}) - start",
					expedienteEstado, expedienteUsuarioAsignado, codigosTrataList);
		}

		List<Tarea> tareasList = new ArrayList<>();
		Tarea tarea = new Tarea();

		try (Connection connection = this.getDataSource().getConnection();) {

			StringBuilder sqlQueryString = new StringBuilder("");

			sqlQueryString.append(" SELECT ")
					.append(" taskimpl1_.NAME_ AS STRING14_1_1_, CAST(variables0_.KEY_ as varchar2(100)) AS KEY4_1_,  ")
					.append(" CAST(variables0_.STRING_VALUE_ as varchar2(100)) AS STRING14_1_0_, ")
					.append(" expediente_electronico0_.ESTADO AS STRING14_1_1_ ")
					.append(" FROM JBPM4_TASK taskimpl1_  ")
					.append(" INNER JOIN EE_EXPEDIENTE_ELECTRONICO expediente_electronico0_ ")
					.append(" ON expediente_electronico0_.ID_WORKFLOW = taskimpl1_.EXECUTION_ID_ ")
					.append(" INNER JOIN TRATA trata0_ ON trata0_.ID = expediente_electronico0_.ID_TRATA ")
					.append(" INNER JOIN JBPM4_EXECUTION executioni0_ ")
					.append(" ON executioni0_.DBID_ = taskimpl1_.EXECUTION_ ")
					.append(" INNER JOIN JBPM4_VARIABLE variables0_ ON variables0_.EXECUTION_ = taskimpl1_.EXECUTION_ ")
					.append(" AND trata0_.CODIGO_TRATA IN (" + getTrataCodigos(codigosTrataList) + ") ")
					.append(" AND expediente_electronico0_.ESTADO = ? ");
			if (expedienteUsuarioAsignado != null || "null".equalsIgnoreCase(expedienteUsuarioAsignado)
					|| "".equalsIgnoreCase(expedienteUsuarioAsignado))
				
				sqlQueryString.append(" AND expediente_electronico0_.USUARIO_MODIFICACION  =  ? ");
			
			try (PreparedStatement stmt = connection.prepareStatement(sqlQueryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

				stmt.setString(1, expedienteEstado);
				if (expedienteUsuarioAsignado != null || "null".equalsIgnoreCase(expedienteUsuarioAsignado)
						|| "".equalsIgnoreCase(expedienteUsuarioAsignado))
					stmt.setString(2, expedienteUsuarioAsignado);

				try (ResultSet rs = stmt.executeQuery()) {

					if (rs.next()) {
						do {
							String KEY4_1_ = rs.getString("KEY4_1_");

							if ("codigoTrata".equalsIgnoreCase(KEY4_1_)) {
								tarea.setCodigoTrata(getterString(rs));
							} else if ("motivo".equalsIgnoreCase(KEY4_1_)) {
								tarea.setMotivo(getterString(rs));
							} else if ("codigoExpediente".equalsIgnoreCase(KEY4_1_)) {
								tarea.setCodigoExpediente(getterString(rs));
							} else if ("estado".equalsIgnoreCase(KEY4_1_)) {
								tarea.setEstado(rs.getString("STRING14_1_1_"));
							} else if ("descripcion".equalsIgnoreCase(KEY4_1_)) {
								tarea.setDescripcionTrata(getterString(rs));
							} else if ("usuarioAnterior".equalsIgnoreCase(KEY4_1_)) {
								tarea.setUsuarioAnterior(getterString(rs));
							} else if ("utlimaModificacion".equalsIgnoreCase(KEY4_1_)) {
								tarea.setFechaModificacion(getterString(rs));
							}
							if ((tarea.getCodigoTrata() != null) && (tarea.getMotivo() != null)
									&& (tarea.getCodigoExpediente() != null) && (tarea.getEstado() != null)
									&& (tarea.getDescripcionTrata() != null) && (tarea.getUsuarioAnterior() != null)
									&& (tarea.getFechaModificacion() != null)) {

								addTareaList(tarea, tareasList);
							}
						} while (rs.next());
					}
				}
			}
		} catch (SQLException sqle) {
			logger.error("[ThreadId=" + Thread.currentThread().getId()
					+ "] Hubo un error en el método buscarTareaDistPorTrata ", sqle);
			throw new SQLDataException(sqle.getMessage());
		}
		

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareaDistPorTrataBD(String, String, List<String>) - end - return value={}", tareasList);
		}
		return tareasList;
	}

	public Boolean verificarNoExisteTarea(final String idWorkflow) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarNoExisteTarea(idWorkflow={}) - start", idWorkflow);
		}

		Boolean ret = new Boolean(false);
		
		try (Connection connection = this.getDataSource().getConnection();) {

			StringBuilder sqlQueryString = new StringBuilder("");
			sqlQueryString.append(
					" SELECT COUNT(*) AS NUMERO_TOTAL FROM EE_EXPEDIENTE_ELECTRONICO ee where ee.ID_WORKFLOW = ?  ");

			try (PreparedStatement stmt = connection.prepareStatement(sqlQueryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
				stmt.setString(1, idWorkflow);
				
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						Integer numeroTotal = rs.getInt("NUMERO_TOTAL");
						ret = numeroTotal >= 1;
					}
				}
			}
		} catch (SQLException sqle) {
			logger.error("[ThreadId=" + Thread.currentThread().getId()
					+ "] Hubo un error en el método buscarTareaExistente. ", sqle);
			throw new SQLDataException(sqle.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarNoExisteTarea(String) - end - return value={}", ret);
		}
		return ret;
	}

	/* metodo auxiliar */
	private static void addTareaList(Tarea tarea, List<Tarea> tareasList) {
		if (logger.isDebugEnabled()) {
			logger.debug("addTareaList(tarea={}, tareasList={}) - start", tarea, tareasList);
		}

		if ((tarea.getCodigoTrata() != null) && (tarea.getMotivo() != null) && (tarea.getCodigoExpediente() != null)
				&& (tarea.getEstado() != null) && (tarea.getDescripcionTrata() != null)
				&& (tarea.getUsuarioAnterior() != null) && (tarea.getFechaModificacion() != null)) {
			tareasList.add(new Tarea(tarea.getCodigoExpediente(), tarea.getEstado(), tarea.getUsuarioAnterior(),
					tarea.getMotivo(), tarea.getCodigoTrata(), tarea.getDescripcionTrata(),
					tarea.getFechaModificacion()));

			tarea.setCodigoExpediente(null);
			tarea.setEstado(null);
			tarea.setUsuarioAnterior(null);
			tarea.setMotivo(null);
			tarea.setCodigoTrata(null);
			tarea.setDescripcionTrata(null);
			tarea.setFechaModificacion(null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("addTareaList(Tarea, List<Tarea>) - end");
		}
	}

	/* metodo auxiliar */
	private String getTrataCodigos(final List<String> codigosTrataList) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTrataCodigos(codigosTrataList={}) - start", codigosTrataList);
		}

		StringBuilder stringOut = new StringBuilder("");

		int i = 1;
		for (String codigo : codigosTrataList) {
			stringOut.append("'" + codigo + "'");
			if (i < codigosTrataList.size()) {
				stringOut.append(", ");
			}
			i++;
		}
		String returnString = stringOut.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getTrataCodigos(List<String>) - end - return value={}", returnString);
		}
		return returnString;
	}

	/* metodo auxiliar */
	private static String getterString(ResultSet rs) {
		if (logger.isDebugEnabled()) {
			logger.debug("getterString(rs={}) - start", rs);
		}

		StringBuilder stringOut = new StringBuilder("");
		BufferedReader bufferedReader = null;
		String strAux = null;

		try {
			strAux = rs.getString("STRING14_1_0_");
			stringOut.append(strAux);
		} catch (Exception e) {
			logger.error("getterString(ResultSet)", e);

			try {
				bufferedReader = new BufferedReader(rs.getClob("STRING14_1_0_").getCharacterStream());
				if (!rs.wasNull()) {
					while ((strAux = bufferedReader.readLine()) != null) {
						stringOut.append(strAux);
					}
				}
				String returnString = stringOut.toString();
				if (logger.isDebugEnabled()) {
					logger.debug("getterString(ResultSet) - end - return value={}", returnString);
				}
				return returnString;
			} catch (Exception e1) {
				logger.error("getterString(ResultSet)", e1);

				logger.error(e1.getMessage());
				return stringOut.toString();
			}
		}

		String returnString = stringOut.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getterString(ResultSet) - end - return value={}", returnString);
		}
		return returnString;
	}

	public static class TareaMappingSpec {
		public static String getAttribute(String key) {
			if (logger.isDebugEnabled()) {
				logger.debug("getAttribute(key={}) - start", key);
			}

			Map<String, String> tareaMappingAttribute = new HashMap<>();
			tareaMappingAttribute.put("nombreTarea", "NOMBRE_TAREA");
			tareaMappingAttribute.put("codigoExpediente", "CODIGO_EXPEDIENTE");
			tareaMappingAttribute.put("idSolicitud", "ID_SOLICITUD");
			tareaMappingAttribute.put("usuarioAnterior", "USUARIO_ANTERIOR");
			tareaMappingAttribute.put("motivo", "MOTIVO");
			tareaMappingAttribute.put("codigoTrata", "CODIGO_TRATA");
			tareaMappingAttribute.put("descripcionTrata", "DESC_TRATA");
			tareaMappingAttribute.put("fechaCreacion", "FECHA_CREACION");
			tareaMappingAttribute.put("fechaModificacion", "FECHA_ULTIMA_MODIFICACION");
			tareaMappingAttribute.put("estado", "ESTADO");
			tareaMappingAttribute.put("tareaGrupal", "TAREA_GRUPAL");

			if ("createTime".equalsIgnoreCase(key) || !tareaMappingAttribute.containsKey(key)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getAttribute(String) - end - return value={}", "FECHA_ULTIMA_MODIFICACION");
				}
				return "FECHA_ULTIMA_MODIFICACION";
			}
			String returnString = tareaMappingAttribute.get(key);
			if (logger.isDebugEnabled()) {
				logger.debug("getAttribute(String) - end - return value={}", returnString);
			}
			return returnString;
		}
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public TareaParaleloService getTareaParaleloService() {
		return tareaParaleloService;
	}

	public void setTareaParaleloService(TareaParaleloService tareaParaleloService) {
		this.tareaParaleloService = tareaParaleloService;
	}

	public TrataService getTrataService() {
		return trataService;
	}

	public void setTrataService(TrataService trataService) {
		this.trataService = trataService;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
