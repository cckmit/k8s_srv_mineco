
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
 *         &lt;element name="acknowledge" type="{http://trade.egoveris.com/ws/FuseDemoService/}Acknowledge"/>
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
    "acknowledge"
})
@XmlRootElement(name = "FuseDemoCallbackResponse")
public class FuseDemoCallbackResponse {

    @XmlElement(required = true)
    protected Acknowledge acknowledge;

    /**
     * Obtiene el valor de la propiedad acknowledge.
     * 
     * @return
     *     possible object is
     *     {@link Acknowledge }
     *     
     */
    public Acknowledge getAcknowledge() {
        return acknowledge;
    }

    /**
     * Define el valor de la propiedad acknowledge.
     * 
     * @param value
     *     allowed object is
     *     {@link Acknowledge }
     *     
     */
    public void setAcknowledge(Acknowledge value) {
        this.acknowledge = value;
    }

}
