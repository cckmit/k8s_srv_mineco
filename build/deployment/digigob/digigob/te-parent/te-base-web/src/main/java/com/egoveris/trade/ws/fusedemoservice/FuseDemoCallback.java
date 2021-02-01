
package com.egoveris.trade.ws.fusedemoservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalRequest" type="{http://trade.egoveris.com/ws/FuseDemoService/}ExternalRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "externalRequest"
})
@XmlRootElement(name = "FuseDemoCallback")
public class FuseDemoCallback {

    @XmlElement(required = true)
    protected ExternalRequest externalRequest;

    /**
     * Obtiene el valor de la propiedad externalRequest.
     * 
     * @return
     *     possible object is
     *     {@link ExternalRequest }
     *     
     */
    public ExternalRequest getExternalRequest() {
        return externalRequest;
    }

    /**
     * Define el valor de la propiedad externalRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalRequest }
     *     
     */
    public void setExternalRequest(ExternalRequest value) {
        this.externalRequest = value;
    }

}
