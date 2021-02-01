
package com.egoveris.trade.ws.fusedemoservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ExternalRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ExternalRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idTransaction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="typeForm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalRequest", propOrder = {
    "idTransaction",
    "idMessage",
    "typeForm"
})
public class ExternalRequest {

    @XmlElement(required = true)
    protected String idTransaction;
    @XmlElement(required = true)
    protected String idMessage;
    @XmlElement(required = true)
    protected String typeForm;

    /**
     * Obtiene el valor de la propiedad idTransaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTransaction() {
        return idTransaction;
    }

    /**
     * Define el valor de la propiedad idTransaction.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTransaction(String value) {
        this.idTransaction = value;
    }

    /**
     * Obtiene el valor de la propiedad idMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMessage() {
        return idMessage;
    }

    /**
     * Define el valor de la propiedad idMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMessage(String value) {
        this.idMessage = value;
    }

    /**
     * Obtiene el valor de la propiedad typeForm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeForm() {
        return typeForm;
    }

    /**
     * Define el valor de la propiedad typeForm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeForm(String value) {
        this.typeForm = value;
    }

}
