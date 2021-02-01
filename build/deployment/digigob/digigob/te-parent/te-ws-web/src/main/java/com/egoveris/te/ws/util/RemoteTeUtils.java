package com.egoveris.te.ws.util;

import java.rmi.RemoteException;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.plus.core.util.ApplicationContextUtil;

import com.egoveris.te.base.service.OperacionService;
import com.egoveris.trade.common.model.ResponseMessageDTO;

public class RemoteTeUtils {
	
	private OperacionService operacionService;
	private static RemoteTeUtils instance;
	
	public RemoteTeUtils(){}
	
	public static RemoteTeUtils getInstance(){
		if(instance == null){
			instance = new  RemoteTeUtils();
		}
		return instance;
	}
	
	public void validateEntry(ResponseMessageDTO operation) throws RemoteException{
	
		
	}
	
	public boolean isValidTransaction(String idTransaction){
		return getOperacionService().isValidTransaction(idTransaction);
	}
	
	
	public String getIdTransaction(ResponseMessageDTO response){
		if(response != null && StringUtils.isNotBlank(response.getIdTransaction())){
			return response.getIdTransaction();
		}
		return "";
	}

	public OperacionService getOperacionService() {
		if(operacionService == null){
			operacionService = (OperacionService) ApplicationContextUtil.getBean("operacionService");
		}
		return operacionService;
	}

	
	
	
}
