package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class BuscarDocumentosGedoServiceException extends ApplicationException{

		private static final long serialVersionUID = 318688850491100822L;
		
		
		public BuscarDocumentosGedoServiceException(String message){
			super(message);
		}
		
		public BuscarDocumentosGedoServiceException(String message, Throwable cause){
			super(message, cause);
		}

}
