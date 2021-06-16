package com.egoveris.te.base.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.te.base.model.rest.FormsWsDTO;
import com.egoveris.te.base.model.rest.HistoryWsDTO;
import com.egoveris.te.base.model.rest.MaskWsDTO;
import com.egoveris.te.base.model.rest.PossibleStatesWsDTO;
import com.egoveris.te.base.model.rest.ResultTypeWsDTO;

public class TareaAppDTO {

	private static final Logger logger = LoggerFactory.getLogger(TareaAppDTO.class);

	private String procedureDesc;
	private String procedureCode;
	private String taskCode;
	private String lastModificationDate;
	private String reason;
	private String lastUser;
	// resultado de la tarea	
	private ResultTypeWsDTO taskResult;
	// siguiente estado de la tarea
	private String nextStatus;
	private String statusTask;
	private List<DocumentoWsDTO> documentoWs;
	// Lista de posibles siguientes estados
	private List<PossibleStatesWsDTO> possibleStates;
	// Lista de resultados
	private List<ResultTypeWsDTO> resultType;
	private MaskWsDTO mask;
	private HistoryWsDTO history;
	private FormsWsDTO forms;
	private GeoLocationInfoDTO geoLocationInfo;

	public TareaAppDTO(String procedureDesc, String procedureCode, String taskCode, String lastModificationDate,
			String reason, String statusTask) {
		this.procedureDesc = procedureDesc;
		this.procedureCode = procedureCode;
		this.taskCode = taskCode;
		this.lastModificationDate = lastModificationDate;
		this.reason = reason;
		this.statusTask = statusTask;
	}

	/**
	 * @return the procedureDesc
	 */
	public String getProcedureDesc() {
		return procedureDesc;
	}

	/**
	 * @param procedureDesc
	 *            the procedureDesc to set
	 */
	public void setProcedureDesc(String procedureDesc) {
		this.procedureDesc = procedureDesc;
	}

	/**
	 * @return the procedureCode
	 */
	public String getProcedureCode() {
		return procedureCode;
	}

	/**
	 * @param procedureCode
	 *            the procedureCode to set
	 */
	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	/**
	 * @return the taskCode
	 */
	public String getTaskCode() {
		return taskCode;
	}

	/**
	 * @param taskCode
	 *            the taskCode to set
	 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	/**
	 * @return the lastModificationDate
	 */
	public String getLastModificationDate() {
		return lastModificationDate;
	}

	/**
	 * @param lastModificationDate
	 *            the lastModificationDate to set
	 */
	public void setLastModificationDate(String lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @return the statusTask
	 */
	public String getStatusTask() {
		return statusTask;
	}

	/**
	 * @param statusTask
	 *            the statusTask to set
	 */
	public void setStatusTask(String statusTask) {
		this.statusTask = statusTask;
	}

	/**
	 * @return the documentoWs
	 */
	public List<DocumentoWsDTO> getDocumentoWs() {
		return documentoWs;
	}

	/**
	 * @param documentoWs
	 *            the documentoWs to set
	 */
	public void setDocumentoWs(List<DocumentoWsDTO> documentoWs) {
		this.documentoWs = documentoWs;
	}

	/**
	 * @return the mask
	 */
	public MaskWsDTO getMask() {
		return mask;
	}

	/**
	 * @param mask
	 *            the mask to set
	 */
	public void setMask(MaskWsDTO mask) {
		this.mask = mask;
	}

	/**
	 * @return the history
	 */
	public HistoryWsDTO getHistory() {
		return history;
	}

	/**
	 * @param history
	 *            the history to set
	 */
	public void setHistory(HistoryWsDTO history) {
		this.history = history;
	}

	/**
	 * @return the forms
	 */
	public FormsWsDTO getForms() {
		return forms;
	}

	/**
	 * @param forms
	 *            the forms to set
	 */
	public void setForms(FormsWsDTO forms) {
		this.forms = forms;
	}

	/**
	 * @return the lastUser
	 */
	public String getLastUser() {
		return lastUser;
	}

	/**
	 * @param lastUser
	 *            the lastUser to set
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public List<ResultTypeWsDTO> getResultType() {
		return resultType;
	}

	public void setResultType(List<ResultTypeWsDTO> resultType) {
		this.resultType = resultType;
	}

	public List<PossibleStatesWsDTO> getPossibleStates() {
		return possibleStates;
	}

	public void setPossibleStates(List<PossibleStatesWsDTO> possibleStates) {
		this.possibleStates = possibleStates;
	}

	public void add(TareaAppDTO transformTask) {
		this.procedureDesc = transformTask.getProcedureDesc();
		this.procedureCode = transformTask.getProcedureCode();
		this.taskCode = transformTask.getTaskCode();
		this.lastModificationDate = transformTask.getLastModificationDate();
		this.reason = transformTask.getReason();
		this.statusTask = transformTask.getStatusTask();
	}

	public TareaAppDTO() {
	}

	public ResultTypeWsDTO getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(ResultTypeWsDTO taskResult) {
		this.taskResult = taskResult;
	}

	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}

	public GeoLocationInfoDTO getGeoLocationInfo() {
		return geoLocationInfo;
	}

	public void setGeoLocationInfo(GeoLocationInfoDTO geoLocationInfo) {
		this.geoLocationInfo = geoLocationInfo;
	}

}