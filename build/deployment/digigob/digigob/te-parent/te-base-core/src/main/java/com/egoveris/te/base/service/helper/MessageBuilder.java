package com.egoveris.te.base.service.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageBuilder {
	private static final Logger logger = LoggerFactory.getLogger(MessageBuilder.class);

    private StringBuilder messages = new StringBuilder("");

    //***********************************************
	//** Constructor
	//***********************************************
	public MessageBuilder() {
	}
    
    /**
     * Crea un mensaje que no tiene variables
     */
	public MessageBuilder(String message) {
		this.addMessage(message);
	}	

	public MessageBuilder addMessage(String message) {
		if (logger.isDebugEnabled()) {
			logger.debug("addMessage(message={}) - start", message);
		}
	    
	    this.messages.append(message);

		if (logger.isDebugEnabled()) {
			logger.debug("addMessage(String) - end - return value={}", this);
		}
		return this;
    }
    
    public String getMessage(){
		if (logger.isDebugEnabled()) {
			logger.debug("getMessage() - start");
		}

    	if(this.messages==null)
    		this.messages = new StringBuilder("");
		String returnString = this.messages.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getMessage() - end - return value={}", returnString);
		}
        return returnString;
    }   
}
