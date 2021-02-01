
package com.egoveris.trade.ws.fusedemoservice;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.egoveris.trade.ws.fusedemoservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.egoveris.trade.ws.fusedemoservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FuseDemoCallback }
     * 
     */
    public FuseDemoCallback createFuseDemoCallback() {
        return new FuseDemoCallback();
    }

    /**
     * Create an instance of {@link ExternalRequest }
     * 
     */
    public ExternalRequest createExternalRequest() {
        return new ExternalRequest();
    }

    /**
     * Create an instance of {@link FuseDemoCallbackResponse }
     * 
     */
    public FuseDemoCallbackResponse createFuseDemoCallbackResponse() {
        return new FuseDemoCallbackResponse();
    }

    /**
     * Create an instance of {@link Acknowledge }
     * 
     */
    public Acknowledge createAcknowledge() {
        return new Acknowledge();
    }

}
