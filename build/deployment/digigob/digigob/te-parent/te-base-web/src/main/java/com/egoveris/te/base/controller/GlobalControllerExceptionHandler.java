package com.egoveris.te.base.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.terasoluna.plus.common.exception.ApplicationException;

/**
* Global exception handler that will convert any Throwable to a ResponseEntity
* with 500 Interal Server Error and the exception message.
* @author everis
*/
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
        
        /** The SLF4J Logger */
        private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

        @ExceptionHandler(ApplicationException.class)
        public ResponseEntity<String> handleException(ApplicationException exception) {

                LOGGER.info("exception_handler", exception);
                return new ResponseEntity<String>(exception.getCodes()[0], HttpStatus.INTERNAL_SERVER_ERROR);
        }
           
}